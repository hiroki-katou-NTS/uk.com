module nts.uk.at.view.kdm001.d.viewmodel {
    import model = kdm001.share.model;
    import getShared = nts.uk.ui.windows.getShared;
    import setShared = nts.uk.ui.windows.setShared;
    import block = nts.uk.ui.block;
    import dialog    = nts.uk.ui.dialog;
    import getText = nts.uk.resource.getText;
    import modal = nts.uk.ui.windows.sub.modal;

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
        baseDate: KnockoutObservable<string> = ko.observable('');
        isDisableOpenKDL035: KnockoutObservable<boolean> = ko.observable(true);
        payoutManagementDatas: PayoutManagementData[] = [];

        // Update ver 48
        residualNumber: KnockoutObservable<number> = ko.observable(0);
        kdl035Shared: KnockoutObservableArray<HolidayWorkSubHolidayLinkingMng> = ko.observableArray([]);
        displayLinkingDate: KnockoutComputed<any[]> = ko.computed(() => {
            let displayLinkingDate: any[] = [];
            if (!this.pause()) {
                return displayLinkingDate;
            }
            if (this.pickUp()) {
                displayLinkingDate = !_.isEmpty(this.dayOff())
                    ? [{outbreakDay: moment.utc(this.dayOff()).format('YYYY/MM/DD'), dayNumberUsed: 0}]
                    : [];
            } else if (!_.isEmpty(this.kdl035Shared())) {
                displayLinkingDate = this.kdl035Shared();
            } else {
                if (this.subDays() === 0.5) {
                    let item: PayoutManagementData = _.find(this.payoutManagementDatas, item => item.unUsedDays === 0.5);
                    if (_.isNil(item)) {
                        item = _.find(this.payoutManagementDatas, item => item.unUsedDays === 1);
                        if (!_.isNil(item)) {
                            displayLinkingDate = [{outbreakDay: moment.utc(item.dayoffDate).format('YYYY/MM/DD'), dayNumberUsed: 1.0}];
                        }
                    } else {
                        displayLinkingDate = [{outbreakDay: moment.utc(item.dayoffDate).format('YYYY/MM/DD'), dayNumberUsed: 0.5}];
                    }
                }

                if (this.subDays() === 1.0) {
                    const item: PayoutManagementData = _.find(this.payoutManagementDatas, item => item.unUsedDays === 1);
                    if (!_.isNil(item)) {
                        return [{outbreakDay: moment.utc(item.dayoffDate).format('YYYY/MM/DD'), dayNumberUsed: 1.0}];
                    }
                    displayLinkingDate = [];
                    _.forEach(this.payoutManagementDatas, item => {
                        if (displayLinkingDate.length <= 2 && item.unUsedDays === 0.5) {
                            displayLinkingDate.push({
                                outbreakDay: moment.utc(item.dayoffDate).format('YYYY/MM/DD'),
                                dayNumberUsed: item.unUsedDays
                            });
                        }
                    });
                }
            }
            return displayLinkingDate;
        });

        linkingDate: KnockoutComputed<number> = ko.computed(() => {
            if (this.pickUp()) {
                return 0.0;
            }
            let total = 0.0;
            _.forEach(this.kdl035Shared(), (item: any) => total += item.dayNumberUsed);
            return total;
        });

        displayRemainDays: KnockoutComputed<number> = ko.computed(() => this.residualNumber() + this.remainDays());
        // End Update Ver48

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
                if(v) {
                  self.baseDate = self.dayOff;
                }
                if(!v && self.pause()) {
                  self.isDisableOpenKDL035(false);
                }else {
                  self.isDisableOpenKDL035(true);
                }
            });
            self.pause.subscribe((v) => {
                self.setSplit();
                self.calRemainDays();
                if(v && !self.pickUp()) {
                  self.isDisableOpenKDL035(false);
                }else {
                  self.isDisableOpenKDL035(true);
                }
            });
            self.checkedSplit.subscribe((v) => {
                self.calRemainDays();
            });

            self.remainDays(null);
        }

        public oldRemainDays() {
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

        public calRemainDays() {
          const vm = this;
          const value1 = !vm.pickUp() || !vm.occurredDays() ? 0 : vm.occurredDays();
          const value2 = !vm.pause() || !vm.subDays() ? 0 : vm.subDays();
          const value3 = !vm.pause() || !vm.checkedSplit() || !vm.requiredDays() ? 0 : vm.requiredDays();
          const remainDays = value1 - (value2 + value3);
          vm.remainDays(remainDays);
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
            let vm = this,
                info = getShared("KDM001_D_PARAMS");
            if (info) {
                vm.workCode(info.selectedEmployee.workplaceCode);
                vm.workplaceName(info.selectedEmployee.workplaceName);
                vm.employeeCode(info.selectedEmployee.employeeCode);
                vm.employeeId(info.selectedEmployee.employeeId);
                vm.employeeName(info.selectedEmployee.employeeName);
                vm.closureId(info.closureId);
                vm.residualNumber(info.residualNumber);

                // //Fix lỗi không bind data (#117524)
                // vm.workCode.valueHasMutated();
                // vm.workplaceName.valueHasMutated();
                // vm.employeeCode.valueHasMutated();
                // vm.employeeId.valueHasMutated();
                // vm.employeeName.valueHasMutated();
                // vm.closureId.valueHasMutated();
                // vm.residualNumber.valueHasMutated();
            }

            service.getByIdAndUnUse(vm.employeeId(),info.closureId)
            .then(response => {
                if (response) {
                    vm.payoutManagementDatas = response;
                }
            });

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
            let vm = this;
            block.invisible();
            const linkingDates = _.map(vm.displayLinkingDate(), item => moment.utc(item.outbreakDay).format('YYYY-MM-DD'));
            const occurredDays: number = vm.pickUp() ? vm.occurredDays() : 0;
            const subDays: number = vm.pause() ? vm.subDays() : 0.0;
            const requiredDays: number = vm.checkedSplit() ? vm.requiredDays() : 0;
            const linkingDate: number = _.reduce(vm.displayLinkingDate(), (sum, item) => sum + item.dayNumberUsed, 0);
            const dayRemaining = linkingDate + occurredDays - subDays - requiredDays;
            let data = {
                employeeId: vm.employeeId(),
                pickUp: vm.pickUp(),
                dayOff: moment.utc(vm.dayOff(), 'YYYY/MM/DD').toISOString(),
                occurredDays: vm.occurredDays(),
                expiredDate: moment.utc(vm.expiredDate(), 'YYYY/MM/DD').toISOString(),
                pause: vm.pause(),
                subDayoffDate: moment.utc(vm.subDayoffDate(), 'YYYY/MM/DD').toISOString(),
                lawAtr: vm.lawAtr(),
                requiredDays: vm.requiredDays(),
                remainDays: dayRemaining,
                checkedSplit: vm.checkedSplit(),
                closureId: vm.closureId(),
                holidayDate: moment.utc(vm.holidayDate(), 'YYYY/MM/DD').toISOString(),
                subDays: vm.subDays(),
                linkingDates: linkingDates,
                linkingDate: vm.linkingDate(),
                displayRemainDays: vm.displayRemainDays()
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
            })
            .fail((res: any) => {
                dialog.info(res).then(() => setShared('KDM001_A_PARAMS', {isSuccess: false}));
            })
            .always(() => block.clear());
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

        public openKDL035() {
            const vm = this;
            $("#D11_1").trigger("validate");
            if (!nts.uk.ui.errors.hasError()) {
                let info = getShared("KDM001_D_PARAMS");
                const params: any = {
                    employeeId: info.selectedEmployee.employeeId,
                    period: {
                        startDate: moment.utc(vm.subDayoffDate()).format('YYYY/MM/DD'),
                        endDate: moment.utc(vm.subDayoffDate()).format('YYYY/MM/DD')
                    },
                    daysUnit: vm.subDays(),
                    targetSelectionAtr: TargetSelectionAtr.MANUAL,
                    actualContentDisplayList: [],
                    managementData: vm.kdl035Shared()
                };
                setShared('KDL035_PARAMS', params);
                modal("/view/kdl/035/a/index.xhtml").onClosed(() => {
                    // get List<振休振出紐付け管理> from KDL035
                    const kdl035Shared = getShared('KDL035_RESULT');
                    vm.kdl035Shared(kdl035Shared);
                    vm.calRemainDays();
                });
            }
        }
    }

    interface HolidayWorkSubHolidayLinkingMng {
        employeeId: string;
        outbreakDay: string;
        dateOfUse: string;
        dayNumberUsed: number;
        targetSelectionAtr: number;
    }

    interface PayoutManagementData {
	    payoutId: string;
	    sID: string;
	    dayoffDate: any;
	    occurredDays: number;
	    unUsedDays: number;
	    stateAtr: number;
    }

    enum TargetSelectionAtr {
        AUTOMATIC = 0,
        REQUEST = 1,
        MANUAL = 2
    }
}
