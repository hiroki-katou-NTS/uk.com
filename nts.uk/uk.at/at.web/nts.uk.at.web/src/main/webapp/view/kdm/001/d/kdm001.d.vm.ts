module nts.uk.at.view.kdm001.d.viewmodel {
    import model = kdm001.share.model;
    import getShared = nts.uk.ui.windows.getShared;
    import block = nts.uk.ui.block;
    import dialog    = nts.uk.ui.dialog;
    export class ScreenModel {
        workCode: KnockoutObservable<string>      = ko.observable('');
        workplaceName: KnockoutObservable<string> = ko.observable('');
        employeeCode: KnockoutObservable<string>  = ko.observable('');
        employeeId: KnockoutObservable<string>  = ko.observable('');
        employeeName: KnockoutObservable<string>  = ko.observable('');
        remainDays: KnockoutObservable<number>    = ko.observable('');
        unit: KnockoutObservable<string>          = ko.observable('');
        lawAtr: KnockoutObservable<number>        = ko.observable('');
        pickUp: KnockoutObservable<boolean>    = ko.observable(false);;
        pause: KnockoutObservable<boolean> = ko.observable(false);
        checkedSplit: KnockoutObservable<boolean>      = ko.observable(false);
        dayOff: KnockoutObservable<string>        = ko.observable('');
        expiredDate: KnockoutObservable<string>     = ko.observable('');
        subDayoffDate: KnockoutObservable<string>     = ko.observable('');
        holidayDate: KnockoutObservable<string>    = ko.observable('');
        requiredDays: KnockoutObservable<number> = ko.observable('');
        typeHoliday: KnockoutObservableArray<model.ItemModel>     = ko.observableArray(model.getTypeHoliday());
        itemListHoliday: KnockoutObservableArray<model.ItemModel>    = ko.observableArray(model.getNumberOfDays());
        occurredDays: KnockoutObservable<number>              = ko.observable('');
        itemListSubHoliday: KnockoutObservableArray<model.ItemModel> = ko.observableArray(model.getNumberOfDays());
        selectedCodeSubHoliday: KnockoutObservable<string>           = ko.observable('');
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
        }

        initScreen(): void {
            block.invisible();
            let self = this,
                info = getShared("KDM001_A_PARAMS");
            if (info) {
                self.workCode(info.selectedEmployee.workplaceCode);
                self.workplaceName(info.selectedEmployee.workplaceName);
                self.employeeCode(info.selectedEmployee.Code);
                self.employeeId(info.selectedEmployee.Id);
                self.employeeName(info.selectedEmployee.employeeName);
            }
            block.clear();
            self.remainDays(0.5);
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
                payoutId: "00000000000",
                cID: "999999999",
                subOfHDID: "77777",
                employeeId: "000000000",
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
            service.save(data).done(() => {
                dialog.info({ messageId: "Msg_725" }).then(() => {
         
                });
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