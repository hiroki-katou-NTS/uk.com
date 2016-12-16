__viewContext.ready(function () {
    var ScreenModel = (function () {
        //        selectedCode: KnockoutObservable<string>;
        //        selectedCodes: KnockoutObservableArray<string>;
        //        isEnable: KnockoutObservable<boolean>;
        //        isRequired: KnockoutObservable<boolean>;
        function ScreenModel() {
            this.items = ko.observableArray([
                new ItemModel('001', '基本給', "description 1"),
                new ItemModel('150', '役職手当', "description 2"),
                new ItemModel('ABC', '基本給', "description 3")
            ]);
            this.columns = ko.observableArray([
                { headerText: 'コード', prop: 'code', width: 100 },
                { headerText: '名称', prop: 'name', width: 200 },
                { headerText: '説明', prop: 'description', width: 200 }
            ]);
            this.currentCode = ko.observable();
            this.currentCodeList = ko.observableArray([]);
            //            self.itemName = ko.observable('');
            //            self.currentCode = ko.observable(3);
            //            self.selectedCode = ko.observable(null)
            //            self.isEnable = ko.observable(true);
            //            self.isRequired = ko.observable(true);
            //            self.selectedCodes = ko.observableArray([]);
            //            $('#list-box').on('selectionChanging', function(event) {
            //                console.log('Selecting value:' + (<any>event.originalEvent).detail);
            //                //Cancel event.
            //                //event.preventDefault();
            //                //return false;
            //            })
            //            $('#list-box').on('selectionChanged', function(event: any) {
            //                console.log('Selected value:' + (<any>event.originalEvent).detail)
            //            })
        }
        ScreenModel.prototype.selectSomeItems = function () {
            this.currentCode('150');
            this.currentCodeList.removeAll();
            this.currentCodeList.push('001');
            this.currentCodeList.push('ABC');
        };
        ScreenModel.prototype.deselectAll = function () {
            this.currentCode(null);
            this.currentCodeList.removeAll();
        };
        return ScreenModel;
    }());
    var ItemModel = (function () {
        function ItemModel(code, name, description) {
            this.code = code;
            this.name = name;
            this.description = description;
        }
        return ItemModel;
    }());
    this.bind(new ScreenModel());
});
