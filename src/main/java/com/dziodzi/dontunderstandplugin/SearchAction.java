package com.dziodzi.dontunderstandplugin;

import com.intellij.ide.BrowserUtil;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.CaretModel;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.wm.ToolWindow;

import javax.swing.*;

public class SearchAction extends AnAction
{
    public static Integer currentCounter = 0;
    public static String lastSelected = "none";
    private JButton refreshToolWindowButton;
    private JButton hideToolWindowButton;
    private JLabel curSelected;
    private JLabel curCounter;

    private JPanel myToolWindowContent;
    @Override
    public void actionPerformed(AnActionEvent e)
    {
        final Editor editor = e.getRequiredData(CommonDataKeys.EDITOR);
        CaretModel caretModel = editor.getCaretModel();

        if (caretModel.getCurrentCaret().hasSelection())
        {
            String fixed = caretModel.getCurrentCaret().getSelectedText().replace(' ', '+');
            String url = String.format("https://yandex.ru/search/?clid=2456108&text=%s+what+is+it&l10n=ru&lr=2", fixed);
            BrowserUtil.browse(url);
            lastSelected = caretModel.getCurrentCaret().getSelectedText();
            currentCounter++;
        }

        else {
            Messages.showMessageDialog("Selection is empty, could you please select something?", "DontUnderstandPlugin", Messages.getInformationIcon());
        }
    }

    public SearchAction() {
    }

    public SearchAction(ToolWindow toolWindow) {
        hideToolWindowButton.addActionListener(ev -> toolWindow.hide(null));
        refreshToolWindowButton.addActionListener(ev -> currentSelected());
        currentSelected();
    }

    public void currentSelected() {
        String str = "Last selected text was: " + lastSelected;
        curSelected.setText(str);
        str = "Number of invokes: " + currentCounter;
        curCounter.setText(str);
    }

    public JPanel getContent() {
        return myToolWindowContent;
    }
    @Override
    public void update(AnActionEvent e)
    {
        final Editor editor = e.getRequiredData(CommonDataKeys.EDITOR);
        CaretModel caretModel = editor.getCaretModel();
        e.getPresentation().setEnabledAndVisible(caretModel.getCurrentCaret().hasSelection());
    }
}
