__viewContext.ready(function () {
    var ScreenModel = (function () {
        //        selectedCode: KnockoutObservable<string>;
        //        selectedCodes: KnockoutObservableArray<string>;
        //        isEnable: KnockoutObservable<boolean>;
        //        isRequired: KnockoutObservable<boolean>;
        function ScreenModel() {
            this.items = ko.observableArray([
                new ItemModel('001', '基本給', "description 1", "other1"),
                new ItemModel('150', '役職手当', "description 2", "other2"),
                new ItemModel('ABC', '基12本ghj給', "description 3", "other3")
            ]);
            this.columns = ko.observableArray([
                { headerText: 'group1', key: 'group1', group: [
                        { headerText: 'コード', key: 'code', width: 100 },
                        { headerText: '名称', key: 'name', width: 150 }] },
                { headerText: '説明', key: 'description', width: 150 },
                { headerText: 'group2', group: [{ headerText: '説明1', key: 'other1', width: 150 },
                        { headerText: '説明2', key: 'other2', width: 150 }] }
            ]);
            this.columns2 = ko.observableArray([
                { headerText: 'group1', key: 'group1', group: [
                        { headerText: 'コード', key: 'code', width: 100 },
                        { headerText: '名称', key: 'name', width: 150 }] },
                { headerText: '説明', key: 'description', width: 150 },
                { headerText: '<button onclick="toggleColumns()">-</button>', group: [{ headerText: '説明1', key: 'other1', width: 150 },
                        { headerText: '説明2', key: 'other2', width: 150 }] }
            ]);
            this.currentCode = ko.observable();
            this.currentCodeList = ko.observableArray([]);
            this.currentCodeListSwap = ko.observableArray([]);
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
        function ItemModel(code, name, description, other1, other2) {
            this.code = code;
            this.name = name;
            this.description = description;
            this.other1 = other1;
            this.other2 = other2 || other1;
        }
        return ItemModel;
    }());
    this.bind(new ScreenModel());
});
var toggle = true;
function toggleColumns() {
    if (toggle) {
        $('#single-list').igGridHiding("hideMultiColumns", ["other1", "other2"]);
    }
    else {
        $('#single-list').igGridHiding("showMultiColumns", ["other1", "other2"]);
    }
    toggle = !toggle;
}
