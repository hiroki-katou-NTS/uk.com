module nts.uk.at.view.kdm001.f.viewmodel {
    import getShared = nts.uk.ui.windows.getShared;
    import setShared = nts.uk.ui.windows.setShared;
    import model     = kdm001.share.model;
    import dialog    = nts.uk.ui.dialog;
    import block     = nts.uk.ui.block;
    import getText   = nts.uk.resource.getText;
    export class ScreenModel {
        items: KnockoutObservableArray<ItemModel> = ko.observableArray([]);
        columns: KnockoutObservableArray<any>;
        currentCodeList: KnockoutObservableArray<any> = ko.observableArray([]);
        currentList: KnockoutObservableArray<ItemModel> = ko.observableArray([]);
        workCode: KnockoutObservable<string> = ko.observable('');
        workPlaceName: KnockoutObservable<string> = ko.observable('');
        employeeCode: KnockoutObservable<string> = ko.observable('');
        employeeName: KnockoutObservable<string> = ko.observable('');
        dateHoliday: KnockoutObservable<any> = ko.observable('');
        numberDay: KnockoutObservable<any> = ko.observable('');
        residualDay: KnockoutObservable<any> = ko.observable(0);
        residualDayDisplay: KnockoutObservable<any> = ko.observable(model.formatterDay('0'));
        info: any = getShared("KDM001_EFGH_PARAMS");
        disables: KnockoutObservableArray<any> = ko.observableArray([]);
        constructor() {
            let self = this;
            // set init value from screen a
            if (self.info) {
                self.workCode(self.info.selectedEmployee.workplaceCode);
                self.workPlaceName(self.info.selectedEmployee.workplaceName);
                self.employeeCode(self.info.selectedEmployee.employeeCode);
                self.employeeName(self.info.selectedEmployee.employeeName);
                self.dateHoliday(self.info.rowValue.dayoffDateSub);
                self.numberDay(model.formatterDay(self.info.rowValue.requiredDays));
            }

            self.columns = ko.observableArray([
                { headerText: 'コード', dataType: 'string', key: 'payoutId', width: 100, hidden: true },
                { headerText: getText("KDM001_95"), key: 'dayoffDate', width: 110 },
                { headerText: getText("KDM001_96"), key: 'unUsedDays', formatter:model.formatterDay, width: 100 },
            ]);
            self.initScreen();

            self.currentCodeList.subscribe(() => {
                nts.uk.ui.errors.clearAll();
                if (self.currentList().length > 0) {
                    self.currentList.removeAll();
                }
                _.forEach(self.currentCodeList(), function(item) {
                    let code = _.find(self.items(), function(currentItem: ItemModel) {
                        return currentItem.payoutId === item;
                    });
                    if (code) {
                        self.currentList.push(code);
                    }
                })
                self.caculRemainNumber();
            });
        }

        private caculRemainNumber(): void {
            let sumNum = 0, self = this, day = parseFloat(self.numberDay()), residualValue = 0 - day;
            self.residualDay(residualValue);
            self.residualDayDisplay(model.formatterDay(residualValue));
            _.each(self.currentList(), function(x) {
                if (self.dateHoliday() === x.dayoffDate && self.dateHoliday() != null && x.dayoffDate != null) {
                    $('#multi-list').ntsError('set', { messageId: "Msg_766" });
                } else {
                    sumNum = sumNum + x.unUsedDays;
                    residualValue = (sumNum - day);
                    self.residualDay(residualValue);
                    self.residualDayDisplay(model.formatterDay(residualValue));
                }
            });
            if (self.residualDay() < 0) {
                $("#F7_2").css("color", "red");
            } else {
                $("#F7_2").css("color", "black");
            }
        }

        public initScreen(): void {
            block.invisible();
            let self = this;
            self.caculRemainNumber();
            service.getBySidDatePeriod(self.info.selectedEmployee.employeeId, self.info.rowValue.id).done((data: Array<ItemModel>) => {
                if (data && data.length > 0) {
                    _.forEach(data, function(item) {
                        self.items.push(new ItemModel(item.payoutId, item.dayoffDate,item.unUsedDays));
                        if (item.linked){
                            self.currentCodeList.push(item.payoutId);
                        }
                    });
                    let sortData = _.sortBy(self.items(), o => o.dayoffDate,'asc');
                    self.items(sortData);
                    _.forEach(self.items(), function(item: ItemModel) {
                        if(parseFloat(item.unUsedDays) > parseFloat(self.info.rowValue.requiredDays)) {
                            self.disables.push(item.payoutId);    
                        }    
                    })
                }
                else {
                      dialog.info({messageId: 'Msg_1071'});   
                }
                block.clear();
            }).fail((res) => {
                dialog.alertError({ messageId: res.messageId });
                block.clear();
            })
        }

        public create(): void {
            nts.uk.ui.errors.clearAll();
            let self = this;
            let command = new SubOfHDPayoutManaDataCommand(self.info.selectedEmployee.employeeId, self.info.rowValue.id, Math.abs(self.residualDay()), self.currentList());
    
            if (!self.validate()) {
                return;
            }
            if (!nts.uk.ui.errors.hasError()) {
                block.invisible();
                service.insertpayoutMan(command).done(() => {
                    dialog.info({ messageId: "Msg_15" }).then(() => {
                        setShared('KDM001_A_PARAMS', { isSuccess: true });
                        nts.uk.ui.windows.close();
                    });
                    block.clear();
                }).fail((res) => {
                    $('#multi-list').ntsError('set', { messageId: res.messageId });
                    block.clear();
                })

            }
        }

        private validate(): boolean {
            let self = this;

            if (self.currentCodeList().length >= 3) {
                $('#multi-list').ntsError('set', { messageId: "Msg_743" });
                return false;
            } else if (self.currentCodeList().length == 1 && self.currentList()[0].unUsedDays > parseFloat(self.numberDay())) {
                $('#multi-list').ntsError('set', { messageId: "Msg_732" });
                return false;
            } else if (self.currentCodeList().length == 2) {
                if (self.currentList()[0].unUsedDays === 1) {
                    $('#multi-list').ntsError('set', { messageId: "Msg_743" });
                    return false;
                }
                if (parseFloat(self.numberDay()) !== (self.currentList()[0].unUsedDays + self.currentList()[1].unUsedDays)) {
                    $('#multi-list').ntsError('set', { messageId: "Msg_743" });
                    return false;
                }
            }
            return true;
        }
        /**
         * closeDialog
         */
        public closeDialog(): void {
            setShared('KDM001_A_PARAMS', { isSuccess: false });
            nts.uk.ui.windows.close();
        }
    }

    class ItemModel {
        payoutId: string;
        dayoffDate: string;
        unUsedDays: string;
        constructor(code: string, date: string, unUsedDays: string) {
            this.payoutId = code;
            this.dayoffDate = date;
            this.unUsedDays = unUsedDays;
        }
    }

    class SubOfHDPayoutManaDataCommand {
        sid: string;
        subOfHDID: string;
        remainNumber: number;
        payout: Array<ItemModel>;
        constructor(sid: string, subOfHDID: string, remainNumber: number, payout: Array<ItemModel>) {
            this.subOfHDID = subOfHDID;
            this.sid = sid;
            this.payout = payout;
            this.remainNumber = remainNumber;
        }
    }
}