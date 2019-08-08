package com.vaadin.flow.component.grid.it;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.grid.HeaderRow;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
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

        Button orderCol31Button = new Button("order only the columns 3 and 1 ",e -> {
            grid.setColumnOrder(column3,column1);
        });
        orderCol321Button.setId("button-31");
        add(new HorizontalLayout(orderCol123Button, orderCol321Button, orderCol31Button, columnKeysInOrder));


        addGridWithGroupHeader();

    }

    private void addGridWithGroupHeader() {

        Grid<Integer> grid = new Grid<>();
        grid.setId("group-grid");
        Div groupColumnKeysInOrder = new Div();

        groupColumnKeysInOrder.setId("group-column-keys-in-order");

        groupColumnKeysInOrder.add(grid.getColumns().stream().map(Grid.Column::getKey).reduce(String::concat).orElse(""));

        grid.addColumnReorderListener(e -> {
            groupColumnKeysInOrder.removeAll();
            groupColumnKeysInOrder.add(grid.getColumns().stream().map(Grid.Column::getKey).reduce(String::concat).orElse(""));
        });

        ListDataProvider<Integer> dataProvider = DataProvider.ofItems(1,2,3,4,5);
        grid.setDataProvider(dataProvider);
        add(grid);

        grid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);
        // Add 6 columns
        List<Grid.Column<Integer>> columns = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            columns.add(i, grid.addColumn(value -> "V"+value).setHeader("H"+i).setKey("K"+i));
        }
        // | H0 | H1 | H2 | H3 | H4 | H5 |
        // create a new header row for grouping columns
        HeaderRow groupRow = grid.prependHeaderRow();
        // |    |    |    |    |    |    |
        // | H0 | H1 | H2 | H3 | H4 | H5 |
        // merge cells 0-1-2
        groupRow.join(columns.get(0),columns.get(1),columns.get(2))
                .setText("H012");
        // |     H012     |    |    |    |
        // | H0 | H1 | H2 | H3 | H4 | H5 |
        // merge cells 3 - 4 (index 1 - 2)
        groupRow.join(columns.get(3),columns.get(4))
                .setText("H34");
        // |     H012     |   H34   |    |
        // | H0 | H1 | H2 | H3 | H4 | H5 |
        // Add header on the last column
        groupRow.getCells().get(2).setText("H5");
        // |     H012     |   H34   | H5 |
        // | H0 | H1 | H2 | H3 | H4 | H5 |


        grid.setColumnReorderingAllowed(true);

        grid.addColumnReorderListener(e -> {
            System.out.println("columnReordered");
            groupColumnKeysInOrder.removeAll();
            groupColumnKeysInOrder.add(grid.getColumns().stream().map(Grid.Column::getKey).reduce(String::concat).orElse(""));
        });

        Button orderCol543210Button = new Button("Col 5 4 3 2 1 0",e -> {
            grid.setColumnOrder(columns.get(5),columns.get(4),columns.get(3),columns.get(2),columns.get(1),columns.get(0));
        });

        Button orderCol254310Button = new Button("Col 2 4 3 5 1 0",e -> {
            // should be forbidden
            grid.setColumnOrder(columns.get(2),columns.get(4),columns.get(3),columns.get(5),columns.get(1),columns.get(0));
        });
        Button orderCol120345Button = new Button("Col 1 2 0 Only",e -> {
            grid.setColumnOrder(columns.get(1),columns.get(2),columns.get(0));
        });
        orderCol254310Button.addThemeVariants(ButtonVariant.LUMO_ERROR);

        add(new HorizontalLayout(orderCol254310Button,orderCol543210Button,orderCol120345Button,groupColumnKeysInOrder));
    }

}
