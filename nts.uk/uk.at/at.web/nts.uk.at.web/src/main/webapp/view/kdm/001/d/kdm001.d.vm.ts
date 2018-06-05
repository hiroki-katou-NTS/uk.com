module nts.uk.at.view.kdm001.d.viewmodel {
    import model = kdm001.share.model;
    import getShared = nts.uk.ui.windows.getShared;
    import setShared = nts.uk.ui.windows.setShared;
    import block = nts.uk.ui.block;
    import dialog    = nts.uk.ui.dialog;
    export class ScreenModel {
        workCode: KnockoutObservable<string>                      = ko.observable('');
        workplaceName: KnockoutObservable<string>                 = ko.observable('');
        employeeCode: KnockoutObservable<string>                  = ko.observable('');
        employeeId: KnockoutObservable<string>                    = ko.observable('');
        employeeName: KnockoutObservable<string>                  = ko.observable('');
        remainDays: KnockoutObservable<number>                    = ko.observable(0);
        unit: KnockoutObservable<string>                          = ko.observable('');
        lawAtr: KnockoutObservable<number>                        = ko.observable(null);
        pickUp: KnockoutObservable<boolean>                       = ko.observable(true);;
        pause: KnockoutObservable<boolean>                        = ko.observable(true);
        checkedSplit: KnockoutObservable<boolean>                 = ko.observable(false);
        dayOff: KnockoutObservable<string>                        = ko.observable('');
        expiredDate: KnockoutObservable<string>                   = ko.observable('');
        subDayoffDate: KnockoutObservable<string>                 = ko.observable('');
        holidayDate: KnockoutObservable<string>                   = ko.observable('');
        requiredDays: KnockoutObservable<number>                  = ko.observable(null);
        typeHoliday: KnockoutObservableArray<model.ItemModel>     = ko.observableArray(model.getTypeHoliday());
        itemListHoliday: KnockoutObservableArray<model.ItemModel> = ko.observableArray(model.getNumberOfDays());
        occurredDays: KnockoutObservable<number>                  = ko.observable(null);
        itemListSubHoliday: KnockoutObservableArray<model.ItemModel> = ko.observableArray(model.getNumberOfDays());
        subDays: KnockoutObservable<number>           = ko.observable(null);
        itemListOptionSubHoliday: KnockoutObservableArray<model.ItemModel> = ko.observableArray(model.getNumberOfDays());
        isOptionSubHolidayEnable: KnockoutObservable<boolean>              = ko.observable(false);
        closureId: KnockoutObservable<number> = ko.observable(0);
        totalDay: KnockoutObservable<number> = ko.observable(0);
        enableSplit: KnockoutObservable<boolean>              = ko.observable(true);
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
                self.calRemainDays();
            });
            self.subDays.subscribe((x) => {
                self.calRemainDays();
            });
            self.requiredDays.subscribe((x) => {
                self.calRemainDays();
            });
            self.pickUp.subscribe((v) => {
                self.calRemainDays();
            });
            self.pause.subscribe((v) => {
                self.setSplit();
                self.calRemainDays();
            });
            self.checkedSplit.subscribe((v) => {
                self.calRemainDays();
            });
            self.lawAtr(self.typeHoliday()[0].code);
  
        }
        
        public calRemainDays(){
            let self = this;
            if (self.pickUp()) {
                if (self.pause()) {
                    if (self.checkedSplit()) {
                        self.remainDays(self.occurredDays() - self.subDays() - self.requiredDays());
                    } else {
                        self.remainDays(self.occurredDays() - self.subDays());
                    }
                } else {
                    self.remainDays(self.occurredDays());
                }
            } else {
                if (self.pause()) {
                    if (self.checkedSplit()) {
                        self.remainDays(self.totalDay() - self.subDays() - self.requiredDays());
                    } else {
                        self.remainDays(self.totalDay() - self.subDays());
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
            if (self.pause()) {
                self.enableSplit(true); 
            } else {
                self.checkedSplit(false);
                self.enableSplit(false);
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
                remainDays: Math.abs(self.remainDays()),
                checkedSplit: self.checkedSplit(),
                closureId: self.closureId(),
                holidayDate: moment.utc(self.holidayDate(), 'YYYY/MM/DD').toISOString(),
                subDays: self.subDays()
            };
            
            service.save(data).done(result => {
                if (result && result.length > 0) {
                    for (let error of result) { 
                        if (error === "Msg_737_PayMana") {
                            $('#D6_1').ntsError('set', { messageId: "Msg_737" });
                        }
                        if (error === "Msg_737_SubPay") {
                            $('#D11_1').ntsError('set', { messageId: "Msg_737" });
                        }
                        if (error === "Msg_737_splitMana") {
                            $('#D12_2').ntsError('set', { messageId: "Msg_737" });
                        }
                        if (error === "Msg_740") {
                            $('#D6_1').ntsError('set', { messageId: "Msg_740" });
                        }
                        if (error === "Msg_744") {
                            $('#D11_1').ntsError('set', { messageId: "Msg_744" });
                        }
                        if (error === "Msg_744_Split") {
                            $('#D12_2').ntsError('set', { messageId: "Msg_744" });
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
                        if (error === "Msg_729_Split") {
                            $('#D12_2').ntsError('set', { messageId: "Msg_729" });
                        }
                        if (error === "Msg_729") {
                            $('#D12_2').ntsError('set', { messageId: "Msg_729" });
                        }
                        if (error === "Msg_729_SubMana") {
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
            nts.uk.ui.errors.clearAll();
            $("#D6_1").trigger("validate");
            $("#D8_1").trigger("validate");
            $("#D11_1").trigger("validate");
            $("#D12_2").trigger("validate");
            if (!nts.uk.ui.errors.hasError()) {
                if (this.checked()) {
                    dialog.info({ messageId: "Msg_725" }).then(() => {
                 });
                } else {
                    this.submitForm();
                }
            }
        }
    }
}