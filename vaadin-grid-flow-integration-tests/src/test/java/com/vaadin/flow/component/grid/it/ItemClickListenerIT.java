/*
 * Copyright 2000-2018 Vaadin Ltd.
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

import org.hamcrest.CoreMatchers;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;

import com.vaadin.flow.component.AbstractNoW3c;
import com.vaadin.flow.component.grid.testbench.GridElement;
import com.vaadin.flow.component.grid.testbench.GridTRElement;
import com.vaadin.flow.testutil.TestPath;
import com.vaadin.testbench.TestBenchElement;

@TestPath("item-click-listener")
public class ItemClickListenerIT extends AbstractNoW3c {

    private GridElement grid;

    @Before
    public void init() {
        open();
        grid = $(GridElement.class).first();
    }

    @Test
    public void doubleClickGoesWithSingleClicks() throws InterruptedException {
        GridTRElement firstRow = grid.getRow(0);
        firstRow.doubleClick();

        Assert.assertEquals("foofoo", getClickMessage());

        String yCoord = getDoubleClickMessage();

        Assert.assertThat(Integer.parseInt(yCoord),
                CoreMatchers.allOf(
                        Matchers.greaterThan(firstRow.getLocation().getY()),
                        Matchers.lessThan(firstRow.getLocation().getY()
                                + firstRow.getSize().getHeight())));
    }

    @Test
    public void clickCheckboxInCell_noItemClickEventFired() {
        TestBenchElement checkbox = grid.getCell(0, 1).$("vaadin-checkbox")
                .first();
        checkbox.click();
        Assert.assertEquals("", getClickMessage());
    }

    @Test
    public void clickCell_clickCheckboxInCell_onlyOneClickEventFired() {
        grid.getCell(0, 0).click();
        TestBenchElement checkbox = grid.getCell(0, 1).$("vaadin-checkbox")
                .first();
        checkbox.click();
        Assert.assertEquals("foo", getClickMessage());
    }

    @Test
    public void doubleClickCheckboxInCell_noEventsFired() {
        TestBenchElement checkbox = grid.getCell(0, 1).$("vaadin-checkbox")
                .first();
        checkbox.doubleClick();
        Assert.assertEquals("", getClickMessage());
        Assert.assertEquals("", getDoubleClickMessage());
    }

    private String getClickMessage() {
        return findElement(By.id("clickMsg")).getText();
    }

    private String getDoubleClickMessage() {
        return findElement(By.id("dblClickMsg")).getText();
    }

}
