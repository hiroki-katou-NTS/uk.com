module nts.uk.at.view.kdm001.d.viewmodel {
    import model = kdm001.share.model;
    import getShared = nts.uk.ui.windows.getShared;
    import setShared = nts.uk.ui.windows.setShared;
    import block = nts.uk.ui.block;
    import dialog    = nts.uk.ui.dialog;
    import getText = nts.uk.resource.getText;
    export class ScreenModel {
        workCode: KnockoutObservable<string>                      = ko.observable('');
        workplaceName: KnockoutObservable<string>                 = ko.observable('');
        employeeCode: KnockoutObservable<string>                  = ko.observable('');
        employeeId: KnockoutObservable<string>                    = ko.observable('');
        employeeName: KnockoutObservable<string>                  = ko.observable('');
        remainDays: KnockoutObservable<number>                    = ko.observable(null);
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
        itemListHoliday: KnockoutObservableArray<model.ItemModel> = ko.observableArray(model.getDaysNumber());
        occurredDays: KnockoutObservable<number>                  = ko.observable(null);;
        itemListSubHoliday: KnockoutObservableArray<model.ItemModel> = ko.observableArray(model.getDaysNumber());
        subDays: KnockoutObservable<number>           = ko.observable(null);
        itemListOptionSubHoliday: KnockoutObservableArray<model.ItemModel> = ko.observableArray(model.getDaysNumber());
        isOptionSubHolidayEnable: KnockoutObservable<boolean>              = ko.observable(false);
        closureId: KnockoutObservable<number> = ko.observable(0);
        enableSplit: KnockoutObservable<boolean>              = ko.observable(true);
        unit: KnockoutObservable<string> = ko.observable(getText('KDM001_27'));
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
            

            
            self.remainDays(null);
        }
        
        public calRemainDays(){
            let self = this;
            if ((!self.pickUp() && !self.pause()) ||  (!self.occurredDays() && !self.subDays())) {
                self.remainDays(null);
                return;
            }
            let value1 = !self.pickUp() || !self.occurredDays()? 0 : self.occurredDays();
            let value2 = !self.pause() || !self.subDays() ? 0 : self.subDays();
            let value3 = !self.pause() || !self.checkedSplit() || !self.requiredDays() ? 0 : self.requiredDays();
            self.remainDays(value1 - value2 - value3);
            if (self.remainDays() !== 0){
                self.remainDays(self.remainDays().toFixed(1));
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
                        if (error === "Msg_1435") {
                            $('#D6_1').ntsError('set', { messageId: "Msg_1435" });
                        }
                        if (error === "Msg_1436") {
                            $('#D11_1').ntsError('set', { messageId: "Msg_1436" });
                        }
                        if (error === "Msg_1437") {
                            $('#D12_2').ntsError('set', { messageId: "Msg_1437" });
                        }
                        if ( error === "Msg_1438") {
                            $('#D12_2').ntsError('set', { messageId: "Msg_1438" });
                        }
                        if (error === "Msg_1256_OccurredDays") {
                            $('#D6_3').ntsError('set', { messageId: "Msg_1256" });
                        }
                        if (error === "Msg_1257_OccurredDays") {
                            $('#D6_3').ntsError('set', { messageId: "Msg_1257" });
                        }
                        if (error === "Msg_1256_SubDays") {
                            $('#D11_3').ntsError('set', { messageId: "Msg_1256" });
                        }
                        if (error === "Msg_1256_RequiredDays") {
                            $('#D12_4').ntsError('set', { messageId: "Msg_1256" });
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
            if (this.pickUp()){
                $("#D6_1").trigger("validate");
                $("#D8_1").trigger("validate");
                $("#D7_1").trigger("validate");
                $("#D6_3").trigger("validate");
            }
            if (this.pause()) {
                $("#D11_1").trigger("validate");
                $("#D11_3").trigger("validate");
            }
            if (this.checkedSplit()) {
                $("#D12_2").trigger("validate");
                $("#D12_4").trigger("validate");
            }
           
            
           
            if (!nts.uk.ui.errors.hasError()) {
                if (this.checked()) {
                     $('#D4').ntsError('set', { messageId: "Msg_727" });
                } else {
                    this.submitForm();
                }
            }
        }
    }
}