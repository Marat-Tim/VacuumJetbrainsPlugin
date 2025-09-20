package io.github.marattim.vacuumjetbrainsplugin;

import com.intellij.execution.configurations.GeneralCommandLine;
import com.redhat.devtools.lsp4ij.server.OSProcessStreamConnectionProvider;

public class VacuumLanguageServer extends OSProcessStreamConnectionProvider {
    public VacuumLanguageServer() {
        GeneralCommandLine commandLine = new GeneralCommandLine(
                "vacuum",
                "language-server"
        );
        super.setCommandLine(commandLine);
    }

}
