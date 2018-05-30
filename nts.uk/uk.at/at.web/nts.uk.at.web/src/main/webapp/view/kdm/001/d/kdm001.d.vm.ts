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
        lawAtr: KnockoutObservable<number>        = ko.observable();
        pickUp: KnockoutObservable<boolean>    = ko.observable(true);;
        pause: KnockoutObservable<boolean> = ko.observable(true);
        checkedSplit: KnockoutObservable<boolean>      = ko.observable(false);
        dayOff: KnockoutObservable<string>        = ko.observable('');
        expiredDate: KnockoutObservable<string>     = ko.observable('');
        subDayoffDate: KnockoutObservable<string>     = ko.observable('');
        holidayDate: KnockoutObservable<string>    = ko.observable('');
        requiredDays: KnockoutObservable<number> = ko.observable();
        typeHoliday: KnockoutObservableArray<model.ItemModel>     = ko.observableArray(model.getTypeHoliday());
        itemListHoliday: KnockoutObservableArray<model.ItemModel>    = ko.observableArray(model.getNumberOfDays());
        occurredDays: KnockoutObservable<number>              = ko.observable();
        itemListSubHoliday: KnockoutObservableArray<model.ItemModel> = ko.observableArray(model.getNumberOfDays());
        subDay: KnockoutObservable<number>           = ko.observable();
        itemListOptionSubHoliday: KnockoutObservableArray<model.ItemModel> = ko.observableArray(model.getNumberOfDays());
        isOptionSubHolidayEnable: KnockoutObservable<boolean>              = ko.observable(false);
        closureId: KnockoutObservable<number> = ko.observable(0);
        totalDay: KnockoutObservable<number> = ko.observable(0);
        enableSplit: KnockoutObservable<boolean>              = ko.observable(false);
        constructor() {
            let self = this;
            self.initScreen();
            self.checkedSplit.subscribe((v) => {
                if (v) {
                    self.isOptionSubHolidayEnable(true);
                } else {
                    self.isOptionSubHolidayEnable(false);
                }
             });
            self.occurredDays.subscribe((x) => {
                self.setSplit();
                self.calRemainDays();
            });
            self.subDay.subscribe((x) => {
                self.calRemainDays();
            });
            self.requiredDays.subscribe((x) => {
                self.calRemainDays();
            });
            self.pickUp.subscribe((v) => {
                self.calRemainDays();
            });
            self.pause.subscribe((v) => {
                self.calRemainDays();
            });
            self.checkedSplit.subscribe((v) => {
                self.calRemainDays();
            });
        }
        
        public calRemainDays(){
            let self = this;
            if (self.pickUp()) {
                if (self.pause()) {
                    if (self.checkedSplit()) {
                        self.remainDays(self.occurredDays() - self.subDay() - self.requiredDays());
                    } else {
                        self.remainDays(self.occurredDays() - self.subDay());
                    }
                } else {
                    self.remainDays(self.occurredDays());
                }
            } else {
                if (self.pause()) {
                    if (self.checkedSplit()) {
                        self.remainDays(self.totalDay() - self.subDay() - self.requiredDays());
                    } else {
                        self.remainDays(self.totalDay() - self.subDay());
                    }
                } else {
                    if (self.checkedSplit()) {
                        self.remainDays(self.totalDay() - self.requiredDays());
                    } else {
                        self.remainDays(0);
                    }
                }
            }
        }
        
        public setSplit(){
            let self = this;
            if (self.occurredDays() == 1) {
                if (self.pickUp()) {
                    self.checkedSplit(false);
                    self.enableSplit(false);
                } 
            } else {
                self.enableSplit(true);
               
             }
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
                self.closureId(info.closureId);
            }
            block.clear();
            self.unit("日");
        }
        
        /**
         * closeDialog
         */
        public closeDialog() {
            setShared('KDM001_A_PARAMS', {isSuccess: false});
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
                checkedSplit: self.checkedSplit(),
                closureId: self.closureId(),
                holidayDate: moment.utc(self.holidayDate(), 'YYYY/MM/DD').toISOString()
            };
            
            console.log(data);
            service.save(data).done(result => {
                console.log(result);
                if (result && result.length > 0) {
                    for (let error of result) { 
                        if (error === "Msg_737_PayMana") {
                            $('#D6_1').ntsError('set', { messageId: "Msg_737" });
                        }
                        if (error === "Msg_737_SubPay") {
                            $('#D11_1').ntsError('set', { messageId: "Msg_737" });
                        }
                        if (error === "Msg_740") {
                            $('#D6_3').ntsError('set', { messageId: "Msg_740" });
                        }
                        if (error === "Msg_744") {
                            $('#D8_1').ntsError('set', { messageId: "Msg_744" });
                        }
                        if (error === "Msg_744_Split") {
                            $('#D12_2').ntsError('set', { messageId: "Msg_744" });
                        }
                        if (error === "Msg_1256_PayMana") {
                            $('#D6_1').ntsError('set', { messageId: "Msg_1256" });
                        }
                        if (error === "Msg_1256_PayMana") {
                            $('#D8_1').ntsError('set', { messageId: "Msg_1256" });
                        }
                        if (error === "Msg_1256_OccurredDays") {
                            $('#D6_3').ntsError('set', { messageId: "Msg_1256" });
                        }
                        if (error === "Msg_1257_OccurredDays") {
                            $('#D6_3').ntsError('set', { messageId: "Msg_1257" });
                        }
                        if (error === "Msg_1256_SubDays") {
                            $('#D11_1').ntsError('set', { messageId: "Msg_1256" });
                        }
                        if (error === "Msg_1256_RequiredDays") {
                            $('#D12_2').ntsError('set', { messageId: "Msg_1256" });
                        }
                        if (error === "Msg_1257") {
                            $('#D6_1').ntsError('set', { messageId: "Msg_1257" });
                        }
                        if (error === "Msg_729_Split") {
                            $('#D12_2').ntsError('set', { messageId: "Msg_729" });
                        }
                        if (error === "Msg_729") {
                            $('#D11_1').ntsError('set', { messageId: "Msg_729" });
                        }
                    }
                    return;
                } else {
                    dialog.info({ messageId: "Msg_15" }).then(() => {
                        setShared('KDM001_A_PARAMS', {isSuccess: true});
                        nts.uk.ui.windows.close();
                    });
                }
            }).fail(function(res: any) {
                dialog.info({ messageId: "Msg_737" }).then(() => {
                    setShared('KDM001_A_PARAMS', {isSuccess: false});
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