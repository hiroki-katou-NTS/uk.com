module kdl021.a.viewmodel {
    export class ScreenModel {
        // Ver.6
        isMonthly: boolean;
        isMulti: boolean;
        items: KnockoutObservableArray<ItemModel>;
        columns: KnockoutObservableArray<any>;
        currentCodeList: KnockoutObservableArray<any>;
        posibleItems: Array<number>;
        dataSoure: Array<ItemModel>;

        constructor() {
            var self = this;
            self.isMulti = true;
            self.isMonthly = true;
            self.isMulti = nts.uk.ui.windows.getShared('Multiple');
            self.isMonthly = nts.uk.ui.windows.getShared('MonthlyMode');
            self.items = ko.observableArray([]);
            //header
            self.columns = ko.observableArray([
                { headerText: nts.uk.resource.getText("KDL021_3"), prop: 'displayCode', width: 90 },
                { headerText: nts.uk.resource.getText("KDL021_4"), prop: 'name', width: 245, formatter: _.escape }
            ]);
            self.currentCodeList = ko.observableArray();
            self.posibleItems = [];
            self.dataSoure = [];
            self.start();
            //Fix Bug #84017
            _.defer(() => { $(".ntsSearchBox").focus(); });
        }
        //load data
        start() {
            var self = this;
            nts.uk.ui.block.invisible();
            //all possible attendance items
            self.posibleItems = nts.uk.ui.windows.getShared('AllAttendanceObj');
            //selected attendace items
            self.currentCodeList(nts.uk.ui.windows.getShared('SelectedAttendanceId'));
            //the fist item 
            if(!self.isMulti){
                self.dataSoure.push(new ItemModel("", "選択なし", ""));    
            }
            //set source
            if (self.posibleItems.length > 0) {
                if (self.isMonthly) {
                    service.getMonthlyAttendanceDivergenceName(self.posibleItems).done(function(lstItem: Array<any>) {
                        for (let i in lstItem) {
                            self.dataSoure.push(new ItemModel(
                                lstItem[i].attendanceItemId.toString(), 
                                lstItem[i].attendanceItemName.toString(),
                                lstItem[i].attendanceItemDisplayNumber.toString()));
                        };
                        //set source
                        let data = _.sortBy(self.dataSoure, ['code']);
                        self.items(data);
                    }).fail(function(res) {
                        nts.uk.ui.dialog.alert(res.message);
                    }).always(function() {
                        nts.uk.ui.block.clear();
                    });
                } else {
                    service.getPossibleItem(self.posibleItems).done(function(lstItem: Array<any>) {
                        for (let i in lstItem) {
                            self.dataSoure.push(new ItemModel(
                                lstItem[i].attendanceItemId.toString(), 
                                lstItem[i].attendanceItemName.toString(),
                                lstItem[i].attendanceItemDisplayNumber.toString()));
                        };
                        //set source
                        let data = _.sortBy(self.dataSoure, ['code']);
                        self.items(data);
                    }).fail(function(res) {
                        nts.uk.ui.dialog.alert(res.message);
                    }).always(function() {
                        nts.uk.ui.block.clear();
                    });
                }
            }else{
                nts.uk.ui.block.clear();
            }
            //勤怠項目の指定が0件の場合
            //set source
//            self.items(self.dataSoure);
        }
        //event When click to 設定 ボタン
        register() {
            var self = this;
            if (self.currentCodeList().length == 0 && self.isMulti) {
                nts.uk.ui.dialog.alert(nts.uk.resource.getMessage('Msg_78'));
            } else {
                nts.uk.ui.windows.setShared('selectedChildAttendace', self.currentCodeList());
                nts.uk.ui.windows.close();
            }

        }
        //検索条件をクリアする
        clearClick() {
            var self = this;
            self.items([]);
            let data = _.sortBy(self.dataSoure, ['code']);
            self.items(data);
            //selected attendace items
            self.currentCodeList(nts.uk.ui.windows.getShared('SelectedAttendanceId'));
        }
        //Close Dialog
        close() {
            nts.uk.ui.windows.close();
        }
    }
    class ItemModel {
        code: string;
        name: string;
        displayCode: string;
        constructor(code: string, name: string, displayCode: string) {
            this.code = code;
            this.name = name;
            this.displayCode = displayCode;
        }
    }


}