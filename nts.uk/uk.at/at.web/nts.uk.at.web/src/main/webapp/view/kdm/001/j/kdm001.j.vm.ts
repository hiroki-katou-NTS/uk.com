module nts.uk.at.view.kdm001.j.viewmodel {
    import getShared = nts.uk.ui.windows.getShared;
    import setShared = nts.uk.ui.windows.setShared;
    import model     = kdm001.share.model;
    import dialog    = nts.uk.ui.dialog;
    import block     = nts.uk.ui.block;
    import getText   = nts.uk.resource.getText;
    export class ScreenModel {
        itemsSelected: KnockoutObservableArray<ItemModel> = ko.observableArray([]);
        items: KnockoutObservableArray<ItemModel> = ko.observableArray([]);
        sumDay: KnockoutObservable<any> = ko.observable('');
        columns: KnockoutObservableArray<NtsGridListColumn>;
        currentCodeList: KnockoutObservableArray<any> = ko.observableArray([]);
        workCode: KnockoutObservable<string> = ko.observable('');
        workPlaceName: KnockoutObservable<string> = ko.observable('');
        employeeCode: KnockoutObservable<string> = ko.observable('');
        employeeName: KnockoutObservable<string> = ko.observable('');
        dateHoliday: KnockoutObservable<any> = ko.observable('');
        numberDay: KnockoutObservable<any> = ko.observable('');
        residualDay: KnockoutObservable<any> = ko.observable(model.formatterDay('0'));
        leaveId: KnockoutObservable<any> = ko.observable('');
        employeeId: KnockoutObservable<any> = ko.observable('');
        numberDayParam: KnockoutObservable<any> = ko.observable('');
        disables: KnockoutObservableArray<any> = ko.observableArray([]);
        
        constructor() {
            block.invisible();
            let self = this,
                info = getShared("KDM001_J_PARAMS");
            self.initScreen(info);
            self.residualDay(model.formatterDay(self.numberDay()));

            self.callService(self.leaveId(), self.employeeId());
            self.columns = ko.observableArray([
                { headerText: 'コード', dataType: 'string', key: 'comDayOffID', width: 100, hidden: true },
                { headerText: getText("KDM001_95"), key: 'dayOff', width: 110 },
                { headerText: getText("KDM001_96"), key: 'remainDays', formatter:model.formatterDay, width: 100}
            ]);

            self.currentCodeList.subscribe(function(codesSelect) {
                nts.uk.ui.errors.clearAll();
                self.itemsSelected.removeAll();
                let sumNum = 0;
                if (codesSelect.length > 0) {
                    _.each(codesSelect, x => {
                        let item = _.find(self.items(), x1 => { return x === x1.comDayOffID });
                        if (item) {
                            self.itemsSelected.push(item);
                        }
                    });
                    _.each(self.itemsSelected(), x => {
                        if (self.dateHoliday() === x.dayOff && self.dateHoliday() != null && x.dayOff != null) {
                            self.currentCodeList.remove(x.comDayOffID);
                            $('#multi-list_container').ntsError('set', { messageId: "Msg_730" });
                        } else {
                            let iNum = parseFloat(x.remainDays), day = parseFloat(self.numberDay());
                            sumNum = sumNum + iNum;
                            self.residualDay(model.formatterDay(day - sumNum));

                            if (parseFloat(self.residualDay()) < 0) {
                                $("#J7_2").css("color", "red");
                            } else {
                                $("#J7_2").css("color", "");
                            }
                        }
                    });
                } else {
                    self.residualDay(model.formatterDay(self.numberDay()));
                    if (parseFloat(self.residualDay()) < 0) {
                        $("#J7_2").css("color", "red");
                    } else {
                        $("#J7_2").css("color", "");
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
                self.leaveId(info.row.id);
                self.dateHoliday(info.row.dayOffDate);
                self.numberDay(model.formatterDay(info.row.occurredDays));
                self.numberDayParam(parseFloat(info.row.occurredDays).toFixed(1));
            }
        }

        /**
         * closeDialog
         */
        public closeDialog():void {
            nts.uk.ui.windows.close();
        }
        
        /**
         * update
         */
        public update():void {
            block.invisible();
            let self = this;
            nts.uk.ui.errors.clearAll();
            service.update(new UpdateModel(self.employeeId(), self.leaveId(), self.itemsSelected(), self.numberDayParam())).done(function(data) {
                if (data.length > 0) {
                    let messageId = data[0];
                    if (messageId === 'Msg_15') {

                        dialog.info({ messageId: "Msg_15" }).then(() => {
                            setShared('KDM001_J_PARAMS_RES', { isChanged: true });
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
        
        
        public callService(leaveId,employeeId):void {
            
            block.invisible();
            let self = this;
            service.getAll(leaveId, employeeId).done(function(data) {

                if (data.errorCode != null) {
                    dialog.alertError({ messageId: data.errorCode });
                }
                for (let i = 0; i < data.listDayOff.length; i++) {
                    self.items.push(new ItemModel(data.listDayOff[i].comDayOffId, data.listDayOff[i].dateHoliday, data.listDayOff[i].numberDay));
                    if (data.listDayOff[i].usedDay == true) {
                        self.currentCodeList.push(data.listDayOff[i].comDayOffId);
                    }
                    
                    if(data.listDayOff[i].numberDay > self.numberDayParam()) {
                        self.disables.push(data.listDayOff[i].comDayOffId);
                    }
                    
                }
                block.clear();
            }).fail(function(error) {
                alert(error);
                block.clear();
            });

        }


    }

    class ItemModel {
        comDayOffID: string;
        dayOff: string;
        remainDays: string;
        constructor(comDayOffID: string, dayOff: string, remainDays: string) {
            this.comDayOffID = comDayOffID;
            this.dayOff = dayOff;
            this.remainDays = remainDays;
        }
        
    }
    
    class UpdateModel {
        employeeId: string;
        leaveId: string;
        comDayOffManaDtos: Array<ItemModel>;
        numberDayParam: string;
        constructor(employeeId: string,leaveId: string, comDayOffManaDtos: Array<ItemModel>, numberDayParam: string) {
            this.employeeId = employeeId;
            this.leaveId = leaveId;
            this.comDayOffManaDtos = comDayOffManaDtos;
            this.numberDayParam = numberDayParam;
        }
    }
    
}