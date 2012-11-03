#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ${package}.sample.impl;

import javafx.scene.control.Label;
import org.drombler.acp.core.docking.ViewDocking;
import org.drombler.acp.core.docking.WindowMenuEntry;
import org.drombler.fx.core.docking.DockablePane;
/**
 *
 * @author puce
 */
@ViewDocking(areaId = "right", position = 10, displayName = "#RightTestPane.displayName",
menuEntry =
@WindowMenuEntry(path = "Other", position = 40))
public class RightTestPane extends DockablePane{

    public RightTestPane() {
        setContent(new Label("right"));
    }
    
}
