package com.vaadin.flow.component.grid.it;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.renderer.NativeButtonRenderer;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.Route;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Route(value = "grid-order-columns")
public class GridOrderColumnsPage extends VerticalLayout
{
    public GridOrderColumnsPage()
    {
        Grid<Integer> grid = new Grid<>();

        Grid.Column<Integer> column1 = grid.addColumn(value -> "col1 "+value).setHeader("Col1").setKey("1");
        Grid.Column<Integer> column2 = grid.addColumn(value -> "col2 "+value).setHeader("Col2").setKey("2");
        Grid.Column<Integer> column3 = grid.addColumn(value -> "col3 "+value).setHeader("Col3").setKey("3");

        ListDataProvider<Integer> dataProvider = DataProvider.ofItems(1,2,3,4,5);
        grid.setDataProvider(dataProvider);
        add(grid);


        grid.setColumnReorderingAllowed(true);

        Div columnKeysInOrder = new Div();

        columnKeysInOrder.setId("column-keys-in-order");

        columnKeysInOrder.add(grid.getColumns().stream().map(Grid.Column::getKey).reduce(String::concat).orElse(""));

        add(columnKeysInOrder);
    //    grid.setColumnOrder(column1,column2,column3);

        grid.addColumnReorderListener(e -> {
            columnKeysInOrder.removeAll();
            columnKeysInOrder.add(grid.getColumns().stream().map(Grid.Column::getKey).reduce(String::concat).orElse(""));
        });

        Button orderCol123Button = new Button("Col 1 2 3 ",e -> {
            grid.setColumnOrder(column1,column2,column3);
        });
        orderCol123Button.setId("button-123");
        Button orderCol321Button = new Button("Col 3 2 1 ",e -> {
            grid.setColumnOrder(column3,column2,column1);
        });
        orderCol321Button.setId("button-321");
        add(orderCol123Button, orderCol321Button);
    }

}
