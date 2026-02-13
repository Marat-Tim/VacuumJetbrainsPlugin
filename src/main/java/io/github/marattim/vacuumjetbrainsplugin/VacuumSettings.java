package io.github.marattim.vacuumjetbrainsplugin;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.RoamingType;
import com.intellij.openapi.components.Service;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;

@Service(Service.Level.APP)
@State(
        name = "VacuumSettings",
        storages = @Storage(value = "vacuum.xml", roamingType = RoamingType.DISABLED)
)
public final class VacuumSettings implements PersistentStateComponent<VacuumSettings.State> {
    private State state = new State();

    public static VacuumSettings getInstance() {
        return ApplicationManager.getApplication().getService(VacuumSettings.class);
    }

    @Override
    public State getState() {
        return state;
    }

    @Override
    public void loadState(VacuumSettings.State state) {
        this.state = state;
    }


    public static class State {
        public String vacuumPath;
        public String additionalArgs;

        public State(String vacuumPath, String additionalArgs) {
            this.vacuumPath = vacuumPath;
            this.additionalArgs = additionalArgs;
        }

        public State() {
            this("", "");
        }

        public String vacuumPath() {
            return vacuumPath;
        }

        public String additionalArgs() {
            return additionalArgs;
        }
    }


}
