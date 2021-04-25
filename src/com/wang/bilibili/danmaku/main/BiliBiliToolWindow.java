package com.wang.bilibili.danmaku.main;

import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.wang.bilibili.danmaku.action.BiliBiliHttpUtils;
import com.wang.bilibili.danmaku.json.JsonUtils;

import javax.swing.*;
import java.util.concurrent.TimeUnit;

public class BiliBiliToolWindow {

    private JPanel myToolWindowContent;
    private JButton summitRoomId;
    private JTextArea danMaKuText;
    private JScrollPane danMaKuScrollPanel;
    private JTextField roomId;

    private String roomIdStr = "8223873";

    public BiliBiliToolWindow(Project project, ToolWindow toolWindow) {
        summitRoomId.addActionListener(even -> {
            roomIdStr = roomId.getText();
        });
        danMaKuScrollPanel.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        danMaKuText.setLineWrap(true);
        danMaKuText.disable();
        new Thread(() -> {
            try {
                while (true) {
                    if (project != null) {
                        WriteCommandAction.runWriteCommandAction(project, () -> {
                            try {
                                String historyDanMaKu = BiliBiliHttpUtils.getHistory(roomIdStr);
                                danMaKuText.setText(JsonUtils.parseDanMaKu(historyDanMaKu));
                            } catch (RuntimeException e) {
                                roomIdStr = "8223873";
                            }
                        });
                    }
                    TimeUnit.SECONDS.sleep(2);
                }
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }
        }).start();
    }


    public JPanel getContent() {
        return myToolWindowContent;
    }
}
