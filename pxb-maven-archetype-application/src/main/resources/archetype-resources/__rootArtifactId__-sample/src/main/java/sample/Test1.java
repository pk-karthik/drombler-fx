#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ${package}.sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import org.richclientplatform.core.action.Action;
import org.richclientplatform.core.action.MenuEntry;

/**
 *
 * @author puce
 */
@Action(id = "test1", category = "test", displayName = "#test1.displayName")
@MenuEntry(path = "File", position = 1200)
public class Test1 implements EventHandler<ActionEvent> {

    @Override
    public void handle(ActionEvent t) {
        System.out.println("hello world 1!");
    }
}
