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
 * Copyright 2015 Drombler.org. All Rights Reserved.
 *
 * Contributor(s): .
 */
package tutorial.docking;

import javafx.scene.layout.GridPane;
import org.drombler.acp.core.docking.EditorDocking;

@EditorDocking(contentType = FooHandler.class)
public class DockableEditor extends GridPane {

    private final FooHandler fooHandler;

    public DockableEditor(FooHandler fooHandler) {
        this.fooHandler = fooHandler;
    }

}
