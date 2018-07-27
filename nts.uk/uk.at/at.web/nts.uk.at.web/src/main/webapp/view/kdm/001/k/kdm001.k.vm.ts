module nts.uk.at.view.kdm001.k.viewmodel {
    import getShared = nts.uk.ui.windows.getShared;
    import setShared = nts.uk.ui.windows.setShared;
    import model     = kdm001.share.model;
    import dialog    = nts.uk.ui.dialog;
    import block     = nts.uk.ui.block;
    import getText   = nts.uk.resource.getText;
    export class ScreenModel {
        itemsSelected: KnockoutObservableArray<ItemModel> = ko.observableArray([]);
        items: KnockoutObservableArray<ItemModel> = ko.observableArray([]);
        columns: KnockoutObservableArray<NtsGridListColumn>;
        currentCodeList: KnockoutObservableArray<any> = ko.observableArray([]);
        workCode: KnockoutObservable<string> = ko.observable('');
        workPlaceName: KnockoutObservable<string> = ko.observable('');
        employeeCode: KnockoutObservable<string> = ko.observable('');
        employeeName: KnockoutObservable<string> = ko.observable('');
        dateHoliday: KnockoutObservable<any> = ko.observable('');
        numberDay: KnockoutObservable<any> = ko.observable('');
        residualDay: KnockoutObservable<any> = ko.observable(model.formatterDay('0'));
        comDayOffId: KnockoutObservable<any> = ko.observable('');
        employeeId: KnockoutObservable<any> = ko.observable('');
        numberDayParam: KnockoutObservable<any> = ko.observable('');
        disables: KnockoutObservableArray<any> = ko.observableArray([]);

        constructor() {
            block.invisible();
            let self = this;
            let info = getShared("KDM001_K_PARAMS");
            self.initScreen(info);
            self.residualDay(model.formatterDay(0 - parseFloat(self.numberDay())));
            $("#K7_2").css("color", "red");
            self.callService(self.comDayOffId(), self.employeeId());

            self.columns = ko.observableArray([
                { headerText: 'コード', dataType: 'string', key: 'leaveManaID', width: 100, hidden: true },
                { headerText: getText("KDM001_95"), key: 'dayOff', width: 100 },
                { headerText: getText("KDM001_96"), key: 'remainDays', formatter:model.formatterDay, width: 100 }
            ]);


            self.currentCodeList.subscribe(function(codesSelect) {
                self.itemsSelected.removeAll();
                let sumNum = 0;
                if (codesSelect.length > 0) {
                    _.each(codesSelect, x => {
                        let item = _.find(self.items(), x1 => { return x === x1.leaveManaID });
                        if (item) {
                            self.itemsSelected.push(item);
                        }
                    });
                    _.each(self.itemsSelected(), x => {

                        if (self.dateHoliday() === x.dayOff && self.dateHoliday() != null && x.dayOff != null) {
                            self.currentCodeList.remove(x.leaveManaID);
                            $('#multi-list_container').ntsError('set', { messageId: "Msg_730" });
                        } else {
                            let iNum = parseFloat(x.remainDays);
                            let day = parseFloat(self.numberDay());
                            sumNum = sumNum + iNum;
                            self.residualDay(model.formatterDay(sumNum - day));
                            if (parseFloat(self.residualDay()) < 0) {
                                $("#K7_2").css("color", "red");
                            } else {
                                $("#K7_2").css("color", "");
                            }
                        }
                    });
                } else {
                    self.residualDay(model.formatterDay(0 - parseFloat(self.numberDay())));
                    if (parseFloat(self.residualDay()) < 0) {
                        $("#K7_2").css("color", "red");
                    } else {
                        $("#K7_2").css("color", "");
                    }
                }
            });
            block.clear();
        }

        public initScreen(info): void {
            let self = this;
            if (info) {
                self.workCode(info.selectedEmployee.workplaceCode);
                self.workPlaceName(info.selectedEmployee.workplaceName);
                self.employeeId(info.selectedEmployee.employeeId);
                self.employeeCode(info.selectedEmployee.employeeCode);
                self.employeeName(info.selectedEmployee.employeeName);
                self.comDayOffId(info.row.comDayOffID);
                self.dateHoliday(info.row.dayOffDate);
                self.numberDay(model.formatterDay(info.row.requireDays));
                self.numberDayParam(parseFloat(info.row.requireDays).toFixed(1));
            }
            
        }

        /**
         * closeDialog
         */
        public closeDialog(): void {
            nts.uk.ui.windows.close();
        }
        
        
        /**
         * update
         */
        public update():void {
            block.invisible();
            let self = this;
            nts.uk.ui.errors.clearAll();
            service.update(new UpdateModel(self.employeeId(), self.comDayOffId(), self.itemsSelected(), self.numberDayParam())).done(function(data) {
                if (data.length > 0) {
                    let messageId = data[0];
                    if (messageId === 'Msg_15') {

                        dialog.info({ messageId: "Msg_15" }).then(() => {
                            setShared('KDM001_K_PARAMS_RES', { isChanged: true });
                            nts.uk.ui.windows.close();
                        });

                    } else {
                        $('#multi-list_container').ntsError('set', { messageId: messageId });
                    }
                    block.clear();
                }
            }).fail(function(error) {
                alert(error);
            });
            block.clear();
        }
        
         public callService(comDayOffId,employeeId):void {
             block.invisible();
             let self = this;
             service.getAll(comDayOffId, employeeId).done(function(data) {
                 
                 if (data.errorCode != null) {
                     dialog.alertError({ messageId: data.errorCode });
                 }
                 
                 for (let i = 0; i < data.listLeaveMana.length; i++) {
                     self.items.push(new ItemModel(data.listLeaveMana[i].leaveManaID, data.listLeaveMana[i].dateHoliday, data.listLeaveMana[i].numberDay));
                     if (data.listLeaveMana[i].usedDay == true) {
                         self.currentCodeList.push(data.listLeaveMana[i].leaveManaID);
                     }
                    if(data.listLeaveMana[i].numberDay > self.numberDayParam()) {
                        self.disables.push(data.listLeaveMana[i].leaveManaID);
                    }
                 }
                 
             }).fail(function(error) {
                 alert(error);
             });
            block.clear();
        }
        
        
    }

    class ItemModel {
        leaveManaID: string;
        dayOff: string;
        remainDays: string;
        constructor(leaveManaID: string, dayOff: string, remainDays: string) {
            this.leaveManaID = leaveManaID;
            this.dayOff = dayOff;
            this.remainDays = remainDays;
        }
    }
    
    class UpdateModel {
        employeeId: string;
        comDayOffID: string;
        leaveManaDtos: Array<ItemModel>;
        numberDayParam : string;
        constructor(employeeId: string,comDayOffID: string, leaveManaDtos: Array<ItemModel>, numberDayParam : string) {
            this.employeeId = employeeId;
            this.comDayOffID = comDayOffID;
            this.leaveManaDtos = leaveManaDtos;
            this.numberDayParam = numberDayParam;
        }
    }
    
}