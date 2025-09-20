package io.github.marattim.vacuumjetbrainsplugin;

import com.intellij.openapi.project.Project;
import com.redhat.devtools.lsp4ij.LanguageServerFactory;
import com.redhat.devtools.lsp4ij.server.StreamConnectionProvider;

public class VacuumLanguageServerFactory implements LanguageServerFactory {
    @Override
    public StreamConnectionProvider createConnectionProvider(Project project) {
        return new VacuumLanguageServer();
    }

}
