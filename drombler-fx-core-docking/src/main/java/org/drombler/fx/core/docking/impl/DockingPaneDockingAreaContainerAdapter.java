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

import java.util.ArrayList;
import java.util.List;
import javafx.collections.SetChangeListener;
import javafx.scene.Node;
import org.drombler.acp.core.docking.spi.DockingAreaContainer;
import org.drombler.acp.core.docking.spi.DockingAreaContainerDockingAreaEvent;
import org.drombler.acp.core.docking.spi.DockingAreaContainerListener;
import org.drombler.commons.client.docking.DockingAreaDescriptor;
import org.drombler.commons.fx.docking.DockingPane;
import org.drombler.commons.fx.docking.FXDockableData;
import org.drombler.commons.fx.docking.FXDockableEntry;

/**
 *
 * @author puce
 */
public class DockingPaneDockingAreaContainerAdapter implements
        DockingAreaContainer<Node, FXDockableData, FXDockableEntry> {

    private final List<DockingAreaContainerListener<FXDockableEntry>> listeners = new ArrayList<>();
    private final DockingPane dockingPane;

    public DockingPaneDockingAreaContainerAdapter(DockingPane dockingPane) {
        this.dockingPane = dockingPane;
        dockingPane.getDockingAreaDescriptors().addListener(
                (SetChangeListener.Change<? extends DockingAreaDescriptor> change) -> {
                    if (change.wasAdded()) {
                        fireDockingAreaAdded(change.getElementAdded().getId());
                    } else if (change.wasRemoved()) {
                        fireDockingAreaRemoved(change.getElementRemoved().getId());
                    }
                });
    }

    @Override
    public void addDockingAreaContainerListener(DockingAreaContainerListener<FXDockableEntry> listener) {
        listeners.add(listener);
    }

    @Override
    public void removeDockingAreaContainerListener(DockingAreaContainerListener<FXDockableEntry> listener) {
        listeners.remove(listener);
    }

    private void fireDockingAreaAdded(String dockingAreaId) {
        DockingAreaContainerDockingAreaEvent<FXDockableEntry> event = new DockingAreaContainerDockingAreaEvent<>(this,
                dockingAreaId);
        listeners.forEach(listener -> listener.dockingAreaAdded(event));
    }

    private void fireDockingAreaRemoved(String dockingAreaId) {
        DockingAreaContainerDockingAreaEvent<FXDockableEntry> event = new DockingAreaContainerDockingAreaEvent<>(this,
                dockingAreaId);
        listeners.forEach(listener -> listener.dockingAreaRemoved(event));
    }

    @Override
    public boolean addDockingArea(DockingAreaDescriptor dockingAreaDescriptor) {
        return dockingPane.getDockingAreaDescriptors().add(dockingAreaDescriptor);
    }

    @Override
    public boolean addDockable(FXDockableEntry dockableEntry) {
        return dockingPane.getDockables().add(dockableEntry);
    }
}
