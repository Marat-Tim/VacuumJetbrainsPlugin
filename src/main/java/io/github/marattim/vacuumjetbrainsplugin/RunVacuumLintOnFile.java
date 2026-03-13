package io.github.marattim.vacuumjetbrainsplugin;

import com.intellij.openapi.actionSystem.ActionUpdateThread;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.terminal.ui.TerminalWidget;
import org.jetbrains.plugins.terminal.TerminalToolWindowManager;

public class RunVacuumLintOnFile extends AnAction {
    @Override
    public void update(AnActionEvent e) {
        super.update(e);
        Project project = e.getProject();
        VirtualFile virtualFile = e.getData(CommonDataKeys.VIRTUAL_FILE);
        e.getPresentation().setEnabledAndVisible(project != null && virtualFile != null && !virtualFile.isDirectory());
    }

    @Override
    public ActionUpdateThread getActionUpdateThread() {
        return ActionUpdateThread.BGT;
    }

    @Override
    public void actionPerformed(AnActionEvent e) {
        Project project = e.getProject();
        VirtualFile virtualFile = e.getData(CommonDataKeys.VIRTUAL_FILE);
        if (project == null || virtualFile == null || virtualFile.isDirectory()) {
            return;
        }
        TerminalToolWindowManager terminalManager = TerminalToolWindowManager.getInstance(project);
        TerminalWidget vacuum = terminalManager.createShellWidget(
                project.getBasePath(),
                "Vacuum",
                true,
                true
        );
        VacuumSettings.State state = VacuumSettings.getInstance().getState();
        String cmd = "%s lint %s -p %s %s".formatted(
                state.vacuumPath(),
                virtualFile.getPath(),
                virtualFile.getParent().getPath(),
                state.additionalArgs()
        );
        vacuum.sendCommandToExecute(cmd);

    }

}
