__viewContext.ready(function () {
    var ScreenModel = (function () {
        function ScreenModel() {
            this.items = ko.observableArray();
            this.first = ko.observable({});
        }
        ScreenModel.prototype.rebind = function () {
            ko.cleanNode($("#grid")[0]);
            var newArray = testdata.createRandomHogeArray(100);
            this.first(newArray[0]);
            this.items.removeAll();
            this.items(newArray);
            var gridOptions = {
                primaryKey: 'code',
                dataSource: this.items,
                autoCommit: true,
                width: '400px',
                height: '300px',
                autoGenerateColumns: false,
                features: [
                    {
                        name: 'Updating',
                        editMode: 'cell',
                        columnSettings: [
                            { columnKey: 'code', readOnly: true }
                        ]
                    }
                ],
                columns: [
                    { headerText: 'Code', key: 'code', dataType: 'string', width: 100 },
                    { headerText: 'Name', key: 'name', dataType: 'string', width: 200 },
                ]
            };
            ko.applyBindingsToNode($('#grid')[0], { igGrid: gridOptions }, this);
        };
        return ScreenModel;
    }());
    this.bind(new ScreenModel());
});
//# sourceMappingURL=start.js.map