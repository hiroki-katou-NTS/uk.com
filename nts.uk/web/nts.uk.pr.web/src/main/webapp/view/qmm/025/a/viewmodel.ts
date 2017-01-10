module qmm025.a.viewmodel {
    export class ScreenModel {

        texteditor1: any;
        nwCustomersWithOrders: any;
        constructor() {
            var self = this;
            var nwCustomersWithOrders = [{ "ID": "ALFKI", "CompanyName": "Alfreds Futterkiste", "ContactName": "Maria Anders", "ContactTitle": "Sales Representative", "Address": "Obere Str. 57", "City": "Berlin", "Region": null, "PostalCode": "12209", "Country": "Germany", "Phone": "030-0074321", "Fax": "030-0076545", "Orders": [{ "OrderID": 10643, "CustomerID": "ALFKI", "EmployeeID": 6, "OrderDate": "1997-08-25T00:00:00", "RequiredDate": "1997-09-22T00:00:00", "ShippedDate": "1997-09-02T00:00:00", "ShipVia": 1, "Freight": 29.4600, "ShipName": "Alfreds Futterkiste", "ShipAddress": "Obere Str. 57", "ShipCity": "Berlin", "ShipRegion": null, "ShipPostalCode": "12209", "ShipCountry": "Germany", "ContactName": "Maria Anders", "EmployeeName": "Michael Suyama", "ShipperID": 1, "ShipperName": "Speedy Express", "TotalPrice": 1086.0000, "TotalItems": 38 }, { "OrderID": 10692, "CustomerID": "ALFKI", "EmployeeID": 4, "OrderDate": "1997-10-03T00:00:00", "RequiredDate": "1997-10-31T00:00:00", "ShippedDate": "1997-10-13T00:00:00", "ShipVia": 2, "Freight": 61.0200, "ShipName": "Alfred's Futterkiste", "ShipAddress": "Obere Str. 57", "ShipCity": "Berlin", "ShipRegion": null, "ShipPostalCode": "12209", "ShipCountry": "Germany", "ContactName": "Maria Anders", "EmployeeName": "Margaret Peacock", "ShipperID": 2, "ShipperName": "United Package", "TotalPrice": 878.0000, "TotalItems": 20 }, { "OrderID": 10702, "CustomerID": "ALFKI", "EmployeeID": 4, "OrderDate": "1997-10-13T00:00:00", "RequiredDate": "1997-11-24T00:00:00", "ShippedDate": "1997-10-21T00:00:00", "ShipVia": 1, "Freight": 23.9400, "ShipName": "Alfred's Futterkiste", "ShipAddress": "Obere Str. 57", "ShipCity": "Berlin", "ShipRegion": null, "ShipPostalCode": "12209", "ShipCountry": "Germany", "ContactName": "Maria Anders", "EmployeeName": "Margaret Peacock", "ShipperID": 1, "ShipperName": "Speedy Express", "TotalPrice": 330.0000, "TotalItems": 21 }, { "OrderID": 10835, "CustomerID": "ALFKI", "EmployeeID": 1, "OrderDate": "1998-01-15T00:00:00", "RequiredDate": "1998-02-12T00:00:00", "ShippedDate": "1998-01-21T00:00:00", "ShipVia": 3, "Freight": 69.5300, "ShipName": "Alfred's Futterkiste", "ShipAddress": "Obere Str. 57", "ShipCity": "Berlin", "ShipRegion": null, "ShipPostalCode": "12209", "ShipCountry": "Germany", "ContactName": "Maria Anders", "EmployeeName": "Nancy Davolio", "ShipperID": 3, "ShipperName": "Federal Shipping", "TotalPrice": 851.0000, "TotalItems": 17 }, { "OrderID": 10952, "CustomerID": "ALFKI", "EmployeeID": 1, "OrderDate": "1998-03-16T00:00:00", "RequiredDate": "1998-04-27T00:00:00", "ShippedDate": "1998-03-24T00:00:00", "ShipVia": 1, "Freight": 40.4200, "ShipName": "Alfred's Futterkiste", "ShipAddress": "Obere Str. 57", "ShipCity": "Berlin", "ShipRegion": null, "ShipPostalCode": "12209", "ShipCountry": "Germany", "ContactName": "Maria Anders", "EmployeeName": "Nancy Davolio", "ShipperID": 1, "ShipperName": "Speedy Express", "TotalPrice": 491.2000, "TotalItems": 18 }, { "OrderID": 11011, "CustomerID": "ALFKI", "EmployeeID": 3, "OrderDate": "1998-04-09T00:00:00", "RequiredDate": "1998-05-07T00:00:00", "ShippedDate": "1998-04-13T00:00:00", "ShipVia": 1, "Freight": 1.2100, "ShipName": "Alfred's Futterkiste", "ShipAddress": "Obere Str. 57", "ShipCity": "Berlin", "ShipRegion": null, "ShipPostalCode": "12209", "ShipCountry": "Germany", "ContactName": "Maria Anders", "EmployeeName": "Janet Leverling", "ShipperID": 1, "ShipperName": "Speedy Express", "TotalPrice": 960.0000, "TotalItems": 60 }] }, { "ID": "ANATR", "CompanyName": "Ana Trujillo Emparedados y helados", "ContactName": "Ana Trujillo", "ContactTitle": "Owner", "Address": "Avda. de la Constitución 2222", "City": "México D.F.", "Region": null, "PostalCode": "05021", "Country": "Mexico", "Phone": "(5) 555-4729", "Fax": "(5) 555-3745", "Orders": [{ "OrderID": 10308, "CustomerID": "ANATR", "EmployeeID": 7, "OrderDate": "1996-09-18T00:00:00", "RequiredDate": "1996-10-16T00:00:00", "ShippedDate": "1996-09-24T00:00:00", "ShipVia": 3, "Freight": 1.6100, "ShipName": "Ana Trujillo Emparedados y helados", "ShipAddress": "Avda. de la Constitución 2222", "ShipCity": "México D.F.", "ShipRegion": null, "ShipPostalCode": "05021", "ShipCountry": "Mexico", "ContactName": "Ana Trujillo", "EmployeeName": "Robert King", "ShipperID": 3, "ShipperName": "Federal Shipping", "TotalPrice": 88.8000, "TotalItems": 6 }, { "OrderID": 10625, "CustomerID": "ANATR", "EmployeeID": 3, "OrderDate": "1997-08-08T00:00:00", "RequiredDate": "1997-09-05T00:00:00", "ShippedDate": "1997-08-14T00:00:00", "ShipVia": 1, "Freight": 43.9000, "ShipName": "Ana Trujillo Emparedados y helados", "ShipAddress": "Avda. de la Constitución 2222", "ShipCity": "México D.F.", "ShipRegion": null, "ShipPostalCode": "05021", "ShipCountry": "Mexico", "ContactName": "Ana Trujillo", "EmployeeName": "Janet Leverling", "ShipperID": 1, "ShipperName": "Speedy Express", "TotalPrice": 479.7500, "TotalItems": 18 }, { "OrderID": 10759, "CustomerID": "ANATR", "EmployeeID": 3, "OrderDate": "1997-11-28T00:00:00", "RequiredDate": "1997-12-26T00:00:00", "ShippedDate": "1997-12-12T00:00:00", "ShipVia": 3, "Freight": 11.9900, "ShipName": "Ana Trujillo Emparedados y helados", "ShipAddress": "Avda. de la Constitución 2222", "ShipCity": "México D.F.", "ShipRegion": null, "ShipPostalCode": "05021", "ShipCountry": "Mexico", "ContactName": "Ana Trujillo", "EmployeeName": "Janet Leverling", "ShipperID": 3, "ShipperName": "Federal Shipping", "TotalPrice": 320.0000, "TotalItems": 10 }, { "OrderID": 10926, "CustomerID": "ANATR", "EmployeeID": 4, "OrderDate": "1998-03-04T00:00:00", "RequiredDate": "1998-04-01T00:00:00", "ShippedDate": "1998-03-11T00:00:00", "ShipVia": 3, "Freight": 39.9200, "ShipName": "Ana Trujillo Emparedados y helados", "ShipAddress": "Avda. de la Constitución 2222", "ShipCity": "México D.F.", "ShipRegion": null, "ShipPostalCode": "05021", "ShipCountry": "Mexico", "ContactName": "Ana Trujillo", "EmployeeName": "Margaret Peacock", "ShipperID": 3, "ShipperName": "Federal Shipping", "TotalPrice": 514.4000, "TotalItems": 29 }] },
            ];


        }

        startPage(): JQueryPromise<any> {
            var self = this;
            self.texteditor1 = {
                value: ko.observable(''),
                constraint: 'ResidenceCode',
                option: ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                    textmode: "text",
                    placeholder: "",
                    width: "45px",
                    textalign: "left"
                })),
                required: ko.observable(true),
                enable: ko.observable(true),
                readonly: ko.observable(false)
            };

            var dfd = $.Deferred();
            service.getPaymentDateProcessingList().done(function() {
                dfd.resolve();
            }).fail(function(res) {

            });
            return dfd.promise();
        }
    }
}