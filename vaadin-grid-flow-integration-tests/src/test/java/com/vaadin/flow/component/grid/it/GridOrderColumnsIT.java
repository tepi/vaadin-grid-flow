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
package com.vaadin.flow.component.grid.it;

import com.vaadin.flow.component.grid.testbench.GridElement;
import com.vaadin.flow.testutil.AbstractComponentIT;
import com.vaadin.flow.testutil.TestPath;
import com.vaadin.testbench.TestBenchElement;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * Tests reorder of columns
 */
@TestPath("grid-order-columns")
public class GridOrderColumnsIT extends AbstractComponentIT {

    private GridElement grid;

    @Before
    public void init() {
        open();
        grid = $(GridElement.class).first();
    }

    @Test
    public void gridOrder_123() {
        WebElement element = findElement(By.id("column-keys-in-order"));
        Assert.assertEquals("123", element.getText());
    }

    @Test
    public void gridOrder_321() {
        // click on the button 321
        findElement(By.id("button-321")).click();
        WebElement element = findElement(By.id("column-keys-in-order"));
        Assert.assertEquals("321", element.getText());
    }

    /// TODO Try to simulate swap from the client side
/*
    @Test
    public void gridOrder_132() {
        // swap columns
        swapColumns("Col3","Col2");
        WebElement element = findElement(By.id("column-keys-in-order"));
        Assert.assertEquals("132", element.getText());
    }

    private void swapColumns(String header, String header2) {
        executeScript("_swapColumnOrders(arguments[0], arguments[1])", grid.getColumn(header), grid.getColumn(header2));
    }
    private void fireDragStart(String header) {
        executeScript("fireDragStart(arguments[0])", grid.getHeaderCell(2));
    }

    private void fireDragEnd(String header) {
        executeScript("fireDragEnd(arguments[0])",  grid.getHeaderCell(1));
    }*/

}