package io.github.marattim.vacuumjetbrainsplugin;

import java.awt.*;
import javax.swing.*;

import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory;
import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.ui.TextBrowseFolderListener;
import com.intellij.openapi.ui.TextFieldWithBrowseButton;
import com.intellij.util.ui.JBUI;
import org.jetbrains.annotations.Nls;
import org.jspecify.annotations.Nullable;

public class VacuumSettingsConfigurable implements Configurable {
    private @Nullable JPanel panel;
    private TextFieldWithBrowseButton vacuumPathField;
    private JTextField additionalArgsField;
    private VacuumSettings.State state;

    @Nls(capitalization = Nls.Capitalization.Title)
    @Override
    public String getDisplayName() {
        return "Vacuum";
    }

    @Override
    public JComponent createComponent() {
        state = VacuumSettings.getInstance().getState();

        panel = new JPanel(new BorderLayout());
        JPanel content = new JPanel(new GridBagLayout());
        panel.add(content, BorderLayout.NORTH);
        panel.add(new JPanel(), BorderLayout.CENTER);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = JBUI.insets(5);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0;
        content.add(new JLabel("Path to vacuum executable:"), gbc);
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        vacuumPathField = new TextFieldWithBrowseButton();
        vacuumPathField.addBrowseFolderListener(new TextBrowseFolderListener(FileChooserDescriptorFactory.createSingleFileDescriptor()));
        vacuumPathField.setText(state.vacuumPath());
        content.add(vacuumPathField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0;
        content.add(new JLabel("Additional arguments:"), gbc);
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        additionalArgsField = new JTextField();
        additionalArgsField.setText(state.additionalArgs());
        content.add(additionalArgsField, gbc);

        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        content.add(new JLabel(""), gbc);
        gbc.gridy = 1;
        content.add(new JLabel(""), gbc);

        return panel;
    }


    @Override
    public boolean isModified() {
        return !vacuumPathField.getText().equals(state.vacuumPath()) ||
                !additionalArgsField.getText().equals(state.additionalArgs());
    }

    @Override
    public void apply() {
        VacuumSettings.getInstance().loadState(
                new VacuumSettings.State(
                        vacuumPathField.getText(),
                        additionalArgsField.getText()
                )
        );
        state = VacuumSettings.getInstance().getState();
    }

    @Override
    public void reset() {
        vacuumPathField.setText(state.vacuumPath());
        additionalArgsField.setText(state.additionalArgs());
    }

    @Override
    public void disposeUIResources() {
        panel = null;
    }

}
