/*
 * Copyright 2000-2019 Vaadin Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.vaadin.flow.component.grid;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.DomEvent;
import com.vaadin.flow.component.EventData;
import elemental.json.JsonValue;

@DomEvent("column-reorder")
public class ColumnReorderEvent<T> extends ComponentEvent<Grid<T>> {

    public ColumnReorderEvent(Grid<T> source, boolean fromClient/*,
                              @EventData("event.detail.columns") JsonValue columns*/) {
        super(source, fromClient);
    //    source.getColumns().forEach(c -> System.out.println(c.getInternalId()));
        source.getColumns().forEach(c -> System.out.println(c.getKey()));
    }

}
