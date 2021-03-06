#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ${package}.sample.impl;

import ${package}.sample.ColoredRectangle;
import org.drombler.acp.core.action.ToggleAction;
import org.drombler.acp.core.action.ToggleMenuEntry;
import org.drombler.acp.core.action.ToolBarToggleEntry;

@ToggleAction(id = "blueRectangle", category = "test", displayName = "%blueRectangle.displayName", accelerator = "Shortcut+B",
icon = "blue-rectangle.png")
@ToggleMenuEntry(path = "Custom", position = 1240)
@ToolBarToggleEntry(toolBarId = "rectangle", position = 170)
public class BlueRectangleAction extends AbstractColoredRectangleAction {

    public BlueRectangleAction() {
        super(ColoredRectangle.BLUE);
    }
}
