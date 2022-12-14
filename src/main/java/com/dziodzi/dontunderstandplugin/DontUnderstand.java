package com.dziodzi.dontunderstandplugin;

import com.intellij.ide.BrowserUtil;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.ui.Messages;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class DontUnderstand extends AnAction {
    @Override
    public void actionPerformed(AnActionEvent event) {
        Editor editor = event.getData(PlatformDataKeys.EDITOR);

        String selectedText = editor.getSelectionModel().getSelectedText();

        if (selectedText != null) {
            String encoded = "";
            try {
                encoded = URLEncoder.encode(selectedText, StandardCharsets.UTF_8.toString());
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            String fixed = encoded.replaceAll("\\s+", "+");

            String url = String.format("https://yandex.ru/search/?clid=2456108&text=%s+что+это&l10n=ru&lr=2", fixed);
            BrowserUtil.browse(url);
        } else {
            Messages.showMessageDialog("Selection is empty, could you please select something?", "DontUnderstandPlugin", Messages.getInformationIcon());
        }
    }


    @Override
    public boolean isDumbAware() {
        return false;
    }
}
