$(function () {
    $("#hierarchicalGrid").igHierarchicalGrid({
        width: "100%",
        autoGenerateColumns: false,
        dataSource: northwind,
        responseDataKey: "results",
        dataSourceType: "json",
        caption: "Orders By Employee",
        features: [
            {
                name: "Responsive",
                enableVerticalRendering: false,
                columnSettings: [
                    {
                        columnKey: "Title",
                        classes: "ui-hidden-phone"
                    },
                    {
                        columnKey: "Address",
                        classes: "ui-hidden-phone"
                    }
                ]
            },
            {
                name: "Sorting",
                inherit: true
            },
            {
                name: "Paging",
                pageSize: 5,
                type: "local",
                inherit: true
            }
        ],
        columns: [
           { key: "EmployeeID", headerText: "Employee ID", dataType: "number", width: "0%", hidden: true },
            { key: "FirstName", headerText: "First Name", dataType: "string", width: "20%" },
           { key: "LastName", headerText: "Last Name", dataType: "string", width: "20%" },
           { key: "Title", headerText: "Title", dataType: "string", width: "20%" },
           { key: "Address", headerText: "Address", dataType: "string", width: "25%" },
           { key: "City", headerText: "City", dataType: "string", width: "15%" }
        ],
        autoGenerateLayouts: false,
        columnLayouts: [
            {
                key: "Orders",
                responseDataKey: "results",
                width: "100%",
                autoGenerateColumns: false,
                primaryKey: "OrderID",
                columns: [
                    { key: "OrderID", headerText: "Order ID", dataType: "number", width: "20%" },
                    { key: "CustomerID", headerText: "Customer ID", dataType: "string", width: "0%", hidden: true },
                    { key: "ShipName", headerText: "Ship Name", dataType: "string", width: "20%" },
                    { key: "ShipAddress", headerText: "Ship Address", dataType: "string", width: "20%" },
                    { key: "ShipCity", headerText: "Ship City", dataType: "string", width: "20%" },
                    { key: "ShipCountry", headerText: "Ship Country", dataType: "string", width: "20%" }
                ],
                features: [
                     {
                         name: "Responsive",
                         enableVerticalRendering: false,
                         columnSettings: [
                             {
                                 columnKey: "ShipAddress",
                                 classes: "ui-hidden-phone"
                             },
                             {
                                 columnKey: "ShipCity",
                                 classes: "ui-hidden-phone"
                             }
                         ]
                     },
                   
                ]
            }
        ]
    });


});