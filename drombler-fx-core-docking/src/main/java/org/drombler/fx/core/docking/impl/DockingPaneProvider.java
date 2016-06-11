/*
 *         COMMON DEVELOPMENT AND DISTRIBUTION LICENSE (CDDL) Notice
 *
 * The contents of this file are subject to the COMMON DEVELOPMENT AND DISTRIBUTION LICENSE (CDDL)
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. A copy of the License is available at
 * http://www.opensource.org/licenses/cddl1.txt
 *
 * The Original Code is Drombler.org. The Initial Developer of the
 * Original Code is Florian Brunner (Sourceforge.net user: puce).
 * Copyright 2012 Drombler.org. All Rights Reserved.
 *
 * Contributor(s): .
 */
package org.drombler.fx.core.docking.impl;

import java.text.MessageFormat;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import org.apache.felix.scr.annotations.Activate;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Deactivate;
import org.apache.felix.scr.annotations.Service;
import org.drombler.acp.core.docking.spi.DockingAreaContainer;
import org.drombler.acp.core.docking.spi.DockingAreaContainerProvider;
import org.drombler.commons.action.command.Savable;
import org.drombler.commons.client.util.ResourceBundleUtils;
import org.drombler.commons.context.ActiveContextProvider;
import org.drombler.commons.context.ApplicationContextProvider;
import org.drombler.commons.context.Context;
import org.drombler.commons.context.ContextManager;
import org.drombler.commons.context.Contexts;
import org.drombler.commons.docking.fx.DockingPane;
import org.drombler.commons.docking.fx.FXDockableData;
import org.drombler.commons.docking.fx.FXDockableEntry;
import org.drombler.commons.docking.fx.context.DockableDataModifiedManager;
import org.drombler.commons.docking.fx.context.DockingManager;
import org.drombler.fx.startup.main.ApplicationContentProvider;
import org.osgi.service.component.ComponentContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author puce
 */
@Component
@Service
public class DockingPaneProvider implements ApplicationContentProvider,
        DockingAreaContainerProvider<Node, FXDockableEntry>,
        ActiveContextProvider, ApplicationContextProvider {

    private static final Logger LOG = LoggerFactory.getLogger(DockingPaneProvider.class);

    private final ResourceBundle resourceBundle = ResourceBundleUtils.getClassResourceBundle(DockingPaneProvider.class);

    // TODO: move ContextManager to ACP
    private final ContextManager contextManager = new ContextManager();
    private DockingPane dockingPane;
    private DockingAreaContainer<Node, FXDockableEntry> dockingAreaContainer;
    private DockingManager dockingManager;
    private DockableDataModifiedManager dockableDataModifiedManager;

    @Activate
    protected void activate(ComponentContext context) {
        dockingPane = new DockingPane();
        dockingPane.setOnDockableCloseRequest(event -> {
            FXDockableEntry dockableEntry = event.getDockableEntry();
            FXDockableData dockableData = dockableEntry.getDockableData();
            LOG.debug("Closing Dockable: {} (event={})...", dockableData.getTitle(), event);
            if (stopClosingDockable(dockableEntry)) {
                event.consume();
                LOG.debug("DockableCloseRequestEvent consumed for: {}", dockableData.getTitle());
            }
        });
        dockingAreaContainer = new DockingPaneDockingAreaContainerAdapter(dockingPane);
        dockingManager = new DockingManager(dockingPane, contextManager);
        dockableDataModifiedManager = new DockableDataModifiedManager(dockingPane);
    }

    @Deactivate
    protected void deactivate(ComponentContext context) {
        dockingManager.close();
        dockableDataModifiedManager.close();
        dockingAreaContainer = null;
        dockingPane = null;
    }

    @Override
    public Node getContentPane() {
        return dockingPane;
    }

    @Override
    public DockingAreaContainer<Node, FXDockableEntry> getDockingAreaContainer() {
        return dockingAreaContainer;
    }

    @Override
    public Context getApplicationContext() {
        return contextManager.getApplicationContext();
    }

    @Override
    public Context getActiveContext() {
        return contextManager.getActiveContext();
    }

    private boolean stopClosingDockable(FXDockableEntry dockableEntry) {
        Node dockable = dockableEntry.getDockable();
        Savable savable = Contexts.find(dockable, Savable.class);
        LOG.debug("stopClosingDockable: {}", savable != null);
        if (savable != null) {
            ButtonType saveButton = new ButtonType(resourceBundle.getString("saveButtonType.text"), ButtonBar.ButtonData.YES);
            ButtonType discardButton = new ButtonType(resourceBundle.getString("discardButtonType.text"), ButtonBar.ButtonData.APPLY);
            ButtonType cancelButton = new ButtonType(resourceBundle.getString("cancelButtonType.text"), ButtonBar.ButtonData.CANCEL_CLOSE);
            MessageFormat messageFormat = new MessageFormat(resourceBundle.getString("alert.contentTextFormat"));
            String contentText = messageFormat.format(new Object[]{dockableEntry.getDockableData().getTitle()});
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, contentText, saveButton, discardButton, cancelButton);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent()) {
                if (result.get() == saveButton) {
                    savable.save();
                    return Contexts.find(dockable, Savable.class) != null;
                } else {
                    return result.get() == cancelButton;
                }
            } else {
                LOG.debug("stopClosingDockable: no result!");
                return true;
            }
        }
        return false;
    }

}
