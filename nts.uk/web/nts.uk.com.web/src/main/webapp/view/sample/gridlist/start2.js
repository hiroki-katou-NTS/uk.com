__viewContext.ready(function () {
    var ScreenModel = (function () {
        function ScreenModel() {
            this.items = ko.observableArray([]);
            this.currentCode = ko.observable();
        }
        ScreenModel.prototype.test = function () {
            var array = [];
            for (var i = 0; i < 10000; i++) {
                array.push(new ItemModel("test" + i, '基本給', "description", "other"));
            }
            this.items(array);
        };
        ScreenModel.prototype.test2 = function () {
            var array = [];
            for (var i = 0; i < 10000; i++) {
                array.push(new ItemModel2("test" + i, '基本給', "description", "other"));
            }
            $("#test2").igGrid({
                dataSource: array,
                primaryKey: 'ID',
                width: '1000px',
                height: '500px',
                autoGenerateColumns: false,
                features: [{
                        name: 'Updating',
                        enableAddRow: false,
                        enableDeleteRow: false
                    },
                    {
                        name: 'Selection',
                        mode: 'row',
                        multipleSelection: true
                    }
                ],
                columns: [
                    { headerText: 'ID', key: 'ID', dataType: 'string', width: 110 },
                    { headerText: 'Contact Name', key: 'ContactName', dataType: 'string', width: 210 },
                    { headerText: 'City', key: 'City', dataType: 'string', width: 120 },
                    { headerText: 'Country', key: 'Country', dataType: 'string', width: 120 }
                ]
            });
        };
        return ScreenModel;
    }());
    var ItemModel = (function () {
        function ItemModel(ID, ContactName, City, Country) {
            this.ID = ko.observable(ID);
            this.ContactName = ko.observable(ContactName);
            this.City = ko.observable(City);
            this.Country = ko.observable(Country);
        }
        return ItemModel;
    }());
    var ItemModel2 = (function () {
        function ItemModel2(ID, ContactName, City, Country) {
            this.ID = ID;
            this.ContactName = ContactName;
            this.City = City;
            this.Country = Country;
        }
        return ItemModel2;
    }());
    this.bind(new ScreenModel());
});
//# sourceMappingURL=start2.js.map