/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javafxplatform.core.action;

import javafx.collections.ObservableList;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import org.richclientplatform.core.action.spi.MenuItemContainerListener;
import org.richclientplatform.core.action.spi.MenuItemRootContainer;

/**
 *
 * @author puce
 */
public class MenuBarMenuContainer extends AbstractMenuItemRootContainer {

    private final MenuBar menuBar;

    public MenuBarMenuContainer(MenuBar menuBar) {
        super(false);
        this.menuBar = menuBar;
    }

    @Override
    protected ObservableList<? super Menu> getMenus() {
        return menuBar.getMenus();
    }

    @Override
    protected ObservableList<MenuItem> getItems() {
        throw new UnsupportedOperationException("Not supported.");
    }
}
