module nts.uk.at.view.kdm001.d.viewmodel {
    import model = kdm001.share.model;
    import getShared = nts.uk.ui.windows.getShared;
    import setShared = nts.uk.ui.windows.setShared;
    import block = nts.uk.ui.block;
    import dialog    = nts.uk.ui.dialog;
    export class ScreenModel {
        workCode: KnockoutObservable<string>      = ko.observable('');
        workplaceName: KnockoutObservable<string> = ko.observable('');
        employeeCode: KnockoutObservable<string>  = ko.observable('');
        employeeId: KnockoutObservable<string>  = ko.observable('');
        employeeName: KnockoutObservable<string>  = ko.observable('');
        remainDays: KnockoutObservable<number>    = ko.observable(0);
        unit: KnockoutObservable<string>          = ko.observable('');
        lawAtr: KnockoutObservable<number>        = ko.observable('');
        pickUp: KnockoutObservable<boolean>    = ko.observable(false);;
        pause: KnockoutObservable<boolean> = ko.observable(false);
        checkedSplit: KnockoutObservable<boolean>      = ko.observable(false);
        dayOff: KnockoutObservable<string>        = ko.observable('');
        expiredDate: KnockoutObservable<string>     = ko.observable('');
        subDayoffDate: KnockoutObservable<string>     = ko.observable('');
        holidayDate: KnockoutObservable<string>    = ko.observable('');
        requiredDays: KnockoutObservable<number> = ko.observable(0);
        typeHoliday: KnockoutObservableArray<model.ItemModel>     = ko.observableArray(model.getTypeHoliday());
        itemListHoliday: KnockoutObservableArray<model.ItemModel>    = ko.observableArray(model.getNumberOfDays());
        occurredDays: KnockoutObservable<number>              = ko.observable('');
        itemListSubHoliday: KnockoutObservableArray<model.ItemModel> = ko.observableArray(model.getNumberOfDays());
        subDay: KnockoutObservable<number>           = ko.observable(0);
        itemListOptionSubHoliday: KnockoutObservableArray<model.ItemModel> = ko.observableArray(model.getNumberOfDays());
        isOptionSubHolidayEnable: KnockoutObservable<boolean>              = ko.observable(false);

        constructor() {
            let self = this;
            self.initScreen();
            self.checkedSplit.subscribe((v) => {
                if (v == true) {
                    self.isOptionSubHolidayEnable(true);
                } else {
                    self.isOptionSubHolidayEnable(false);
                }
             });
            self.occurredDays.subscribe((x) =>{
                self.remainDays(x - self.subDay() - self.requiredDays());
            });
            self.subDay.subscribe((x) =>{
                self.remainDays(self.occurredDays() - x - self.requiredDays());
            });
            self.requiredDays.subscribe((x) =>{
                self.remainDays(self.occurredDays() - x - self.subDay());
            });
        }

        initScreen(): void {
            block.invisible();
            let self = this,
                info = getShared("KDM001_D_PARAMS");
            if (info) {
                self.workCode(info.selectedEmployee.workplaceCode);
                self.workplaceName(info.selectedEmployee.workplaceName);
                self.employeeCode(info.selectedEmployee.employeeCode);
                self.employeeId(info.selectedEmployee.employeeId);
                self.employeeName(info.selectedEmployee.employeeName);
            }
            block.clear();
            self.remainDays(0);
            self.unit("日");
        }
        
        /**
         * closeDialog
         */
        public closeDialog() {
            nts.uk.ui.windows.close();
        }
        
        public submitForm() {
            let self = this;
            let data = {
                employeeId: self.employeeId(),
                pickUp: self.pickUp(),
                dayOff: moment.utc(self.dayOff(), 'YYYY/MM/DD').toISOString(),
                occurredDays: self.occurredDays(),
                expiredDate: moment.utc(self.expiredDate(), 'YYYY/MM/DD').toISOString(),
                pause: self.pause(),
                subDayoffDate: moment.utc(self.subDayoffDate(), 'YYYY/MM/DD').toISOString(),
                lawAtr: self.lawAtr(),
                requiredDays: self.requiredDays(),
                remainDays: self.remainDays(),
                holidayDate: moment.utc(self.holidayDate(), 'YYYY/MM/DD').toISOString()
            };
            
            console.log(data);
            service.save(data).done(result => {
                if (result && result.length > 0) {
                    for (let error of result) { 
                        if (error === "Msg_737_PayMana") {
                            $('#D6_1').ntsError('set', { messageId: "Msg_737" });
                        }
                        
                        if (error === "Msg_737_SubPay") {
                            $('#D11_1').ntsError('set', { messageId: "Msg_737" });
                        }
                    }
                    return;
                } else {
                    dialog.info({ messageId: "Msg_15" }).then(() => {
                        nts.uk.ui.windows.close();
                    });
                }
            }).fail(function(res: any) {
                dialog.info({ messageId: "Msg_737" }).then(() => {
                });
            });
        }
        
        public checked() {
            let self = this;
            if (!self.pickUp() && !self.pause())
                return true;
            return false;
        }
        
        public createData(){
            if (this.checked()) {
                    dialog.info({ messageId: "Msg_725" }).then(() => {

                });
            } else {
                this.submitForm();
            }
        }
    }
}