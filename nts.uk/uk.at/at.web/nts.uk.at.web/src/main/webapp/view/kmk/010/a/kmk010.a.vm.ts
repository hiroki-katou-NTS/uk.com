module nts.uk.at.view.kmk010.a {

    import EnumConstantDto = service.model.EnumConstantDto;
    import OvertimeDto = service.model.OvertimeDto;
    import OutsideOTBRDItemDto = service.model.OutsideOTBRDItemDto;
    import OutsideOTSettingDto = service.model.OutsideOTSettingDto;
    import PremiumExtra60HRateDto = service.model.PremiumExtra60HRateDto;
    import SuperHD60HConMedDto = service.model.SuperHD60HConMedDto;
    import OvertimeNameLangDto = service.model.OvertimeNameLangDto;
    import getText = nts.uk.resource.getText;

    export module viewmodel {

        export class ScreenModel extends ko.ViewModel {
            calculationMethods: KnockoutObservableArray<EnumConstantDto>;
            outsideOTSettingModel: OutsideOTSettingModel;
            superHD60HConMedModel: SuperHD60HConMedModel;
            useClassification: KnockoutObservableArray<any>;
            lstUnit: EnumConstantDto[];
            lstRoundingSet: KnockoutObservableArray<EnumConstantDto>;
            lstRounding: EnumConstantDto[];
            lstRoundingSub: EnumConstantDto[];
            checkRounding : KnockoutObservable<number>;
            languageId: string;
            isManage : KnockoutObservable<boolean>;
            static LANGUAGE_ID_JAPAN = 'ja';
            tabFinalArray: KnockoutObservable<number>;
            isEnableSettingOverTime: KnockoutObservable<boolean> = ko.observable(true);

            constructor() {
                super();

                var self = this;
                self.calculationMethods = ko.observableArray<EnumConstantDto>([]);
                self.outsideOTSettingModel = new OutsideOTSettingModel();
                self.superHD60HConMedModel = new SuperHD60HConMedModel();
                self.lstRoundingSet = ko.observableArray<EnumConstantDto>([]);
                self.languageId = 'ja';
                self.isManage = ko.observable(true);
                self.checkRounding = ko.observable(0);     
                self.outsideOTSettingModel.roundingUnit.subscribe(function(selectUnit: number) {         
                    self.updateSelectUnitRounding(selectUnit);
                });
                self.tabFinalArray = ko.observable(12);
                
                self.tabFinalArray.subscribe(item => {
                    $('.selectUnit').attr('data-tab', self.tabFinalArray());
                    $('.selectRounding').attr('data-tab', self.tabFinalArray() + 1);
                });
            }                   

            /**
             * start page data 
             */
            startPage(): JQueryPromise<any> {
                var self = this;
                nts.uk.ui.block.grayout();
                var dfd = $.Deferred();

                // find all unit
                service.findAllOvertimeUnit().done(function(data) {
                    self.lstUnit = data;
                    self.checkRounding(data[0].value);
                });

                // find all rounding
                service.findAllOvertimeRounding().done(function(lstRounding) {
                    self.lstRounding = lstRounding;
                      // find all rounding sub
                    service.findAllOvertimeRoundingSub().done(function(lstRoundingSub) {
                        self.lstRoundingSub = lstRoundingSub;
                        //self.updateSelectUnitRounding(self.superHD60HConMedModel.roundingTime());
                        self.outsideOTSettingModel.roundingUnit.valueHasMutated();
                    });
                });
                
                // check manage call service
                service.checkManageSixtyHourVacationSetting().done(function(data){
                    self.isManage(data.manage);
                });
                
                service.findByIdOutsideOTSetting().done(function(dataOutsideOTSetting) {
                    self.outsideOTSettingModel.updateData(dataOutsideOTSetting);

                    for (var brdItem of self.outsideOTSettingModel.breakdownItems()) {
                        var rateBRDItems: PremiumExtra60HRateModel[] = [];
                        for (var overtimeItem of self.outsideOTSettingModel.overtimes()) {
                            var rateModel: PremiumExtra60HRateModel = new PremiumExtra60HRateModel();
                            rateModel.updateInfo(brdItem.breakdownItemNo(), overtimeItem.overtimeNo());
                            rateBRDItems.push(rateModel);
                        }
                        brdItem.updateRateData(rateBRDItems);
                    }

                    //ドメインモデル「60H超休管理設定」の「管理区分」＝Falseの場合は非活性, 休暇発生＝trueが0件に場合も非活性
                    //A1_6
                    let overTimeCol = _.filter(self.outsideOTSettingModel.overtimes(), (item: any)  => item.superHoliday60HOccurs() === true );                                        
                    self.isEnableSettingOverTime(overTimeCol.length > 0 && self.isManage());

                    service.findAllOvertimeCalculationMethod().done(function(dataMethod) {
                        self.calculationMethods(dataMethod);
                        var tabIndex = 11;
                        service.findByIdSuperHD60HConMed().done(function(dataSuper) {
                            _.each(dataSuper.premiumExtra60HRates, function (item) {
                                item.tabIndex = tabIndex;
                            });
                            self.superHD60HConMedModel.updateData(dataSuper);
                            self.updateDataSuperHolidayMethod(dataSuper);
                        });
                        self.updateEnableInputRate();
                        self.applyChangeEnableInputRate();
                        self.updateLanguage();

                        //self.updateSelectUnitRounding(dataOutsideOTSetting.roundingUnit);                       
                        //self.outsideOTSettingModel.roundingUnit.valueHasMutated();
                        nts.uk.ui.block.clear();
                        dfd.resolve(self);
                    });
                }).fail(function(error) {
                    nts.uk.ui.dialog.alertError(error);
                });

                $('#selectMethodOutsideOT').focus();

                return dfd.promise();
            }
            /**
             * update enable input rate
             */
            private updateEnableInputRate():void{
                var self = this;
                for (var overtime of self.outsideOTSettingModel.overtimes()){
                    for (var brdItem of self.outsideOTSettingModel.breakdownItems()) {
                        for (var rateBRDItem of brdItem.rateBRDItems()) {
                           if(rateBRDItem.overtimeNo() == overtime.overtimeNo()){
                               rateBRDItem.updateEnable(overtime.superHoliday60HOccurs());
                           } 
                        }
                    }
                }    
            }
            /**
             * apply change enable input rate
             */
            
            private applyChangeEnableInputRate(): void{
                var self = this;
                for (var overtime of self.outsideOTSettingModel.overtimes()) {
                    overtime.superHoliday60HOccurs.subscribe(function(){
                       self.updateEnableInputRate(); 
                    });
                }
            }

            /**
             * update data super holiday method
             */
            private updateDataSuperHolidayMethod(dto: SuperHD60HConMedDto) {
                if (dto.premiumExtra60HRates && dto.premiumExtra60HRates.length > 0) {
                    var self = this;
                    for (var brdItem of self.outsideOTSettingModel.breakdownItems()) {
                        for (var rateBRDItem of brdItem.rateBRDItems()) {
                            for (var rateBRDItemUpdate of dto.premiumExtra60HRates) {
                                if (rateBRDItemUpdate.overtimeNo == rateBRDItem.overtimeNo()
                                    && rateBRDItemUpdate.breakdownItemNo == rateBRDItem.breakdownItemNo()) {
                                    rateBRDItem.updateData(rateBRDItemUpdate);
                                }
                            }
                        }
                    }
                }
            }
            /**
             * function on click button open dialog overtime setting
             */
            private openDialogOutsideOTSetting(): void {
                var self = this;
                nts.uk.ui.windows.setShared("languageId", self.languageId);
                nts.uk.ui.windows.sub.modal("/view/kmk/010/b/index.xhtml").onClosed(function() {
                    var isSave: number = nts.uk.ui.windows.getShared("isSave");
                    if (isSave && isSave == 1) {
                        nts.uk.ui.errors.clearAll();
                        self.startPage().done(() => {
                            service.initTooltip();
                        });
                    }
                });
            }
            /**
             * function on click button open dialog overtime break down item
             */
            private openDialogOutsideOTBRDItem(): void {
                var self = this;
                nts.uk.ui.windows.setShared("languageId", self.languageId);
                nts.uk.ui.windows.sub.modal("/view/kmk/010/c/index.xhtml").onClosed(function() {
                    var isSave: number = nts.uk.ui.windows.getShared("isSave");
                    if (isSave && isSave == 1) {
                        nts.uk.ui.errors.clearAll();
                        self.startPage().done(() => {
                            service.initTooltip();
                        });
                    }
                });
            }

            /**
             * convert array 
             */
            private toArrayRateDto(): PremiumExtra60HRateDto[] {
                var dataRate: PremiumExtra60HRateDto[] = [];
                var self = this;
                for (var brdItem of self.outsideOTSettingModel.breakdownItems()) {
                    for (var rateItem of brdItem.rateBRDItems()) {
                        dataRate.push(rateItem.toDto());
                    }
                }
                return dataRate;
            }

            /**
             * function on click save overtime setting
             */
            private saveOutsideOTSetting(): void {
                var self = this;
                // validate
                $('.nts-input').trigger("validate");
          
                // check exist error
                if (!nts.uk.ui.errors.hasError()) {
                    nts.uk.ui.block.invisible();
                    var dtoSuper: SuperHD60HConMedDto = self.superHD60HConMedModel.toDto();
                    dtoSuper.premiumExtra60HRates = self.toArrayRateDto();                                      
                    
                    // check attendance item of breakdown item
                    var setAttendaceIDs = new Set();
                    var stopLoop = true;
                    _.forEach(self.outsideOTSettingModel.breakdownItems(), function(value) {
                        _.forEach(value.attendanceItemIds(), function(id){
                            if (setAttendaceIDs.has(id)) {
                                nts.uk.ui.dialog.alert({ messageId: "Msg_487" }).then(() => {
                                });
                                stopLoop = false;
                                return stopLoop;    
                            } else {
                                setAttendaceIDs.add(id);
                            }
                        })
                        return stopLoop;
                    });
                    
                    if (!stopLoop) {
                        nts.uk.ui.block.clear();
                        return;    
                    }
                    
                    // save all
                    service.saveOutsideOTSettingAndSupperHD60H(self.outsideOTSettingModel.toDto(), dtoSuper).done(function() {
                        nts.uk.ui.dialog.info({ messageId: "Msg_15" }).then(function() {
                            self.startPage().done(() => {
                                service.initTooltip();                               
                                nts.uk.ui.block.clear();
                            });
                        });
                    }).fail(function(error) {
                        nts.uk.ui.dialog.alertError(error);
                        nts.uk.ui.block.clear();
                    });
                }
            }

            /**
             * add view language
             */
            public addViewLanguage(): void {
                var self = this;
                $("#switch-language").ntsSwitchMasterLanguage();

                $("#switch-language").on("selectionChanged", function(event: any, arg1, arg2) {
                    self.languageId = event.detail.languageId;
                    self.updateLanguage();
                });
            }
            /**
             * update language by select language
             */
            public updateLanguage(): void{
                var self = this;
                var loadOverTimeDfd = $.Deferred<void>();
                var loadOutsideDfd = $.Deferred<void>();
                if(self.languageId === ScreenModel.LANGUAGE_ID_JAPAN){
                    service.findAllOvertime().done(function(dataOvertime){
                        for (var overtime of self.outsideOTSettingModel.overtimes()) {
                            for(var dtoOvertime of dataOvertime){
                                if(overtime.overtimeNo() == dtoOvertime.overtimeNo){
                                    overtime.languageName(dtoOvertime.name);    
                                }    
                            }
                        }
                        loadOverTimeDfd.resolve();
                    });    
                    service.findAllOutsideOTBRDItem().done(function(dataOvertimeBRD){
                        for (var overtime of self.outsideOTSettingModel.breakdownItems()) {
                            for(var dtoOvertime of dataOvertimeBRD){
                                if(overtime.breakdownItemNo() == dtoOvertime.breakdownItemNo){
                                    overtime.languageName(dtoOvertime.name);    
                                }    
                            }
                        }
                        loadOutsideDfd.resolve();
                    });
                }else {
                    service.findAllOvertimeNameLanguage(self.languageId).done(function(dataOvertimeLang){
                        for (var overtime of self.outsideOTSettingModel.overtimes()) {
                            for(var dtoOvertime of dataOvertimeLang){
                                if(overtime.overtimeNo() == dtoOvertime.overtimeNo){
                                    overtime.languageName(dtoOvertime.name);    
                                }    
                            }
                        }
                        loadOverTimeDfd.resolve();
                    });    
                    
                    service.findAllOvertimeLanguageBRDItem(self.languageId).done(function(dataOvertimeBRDLang){
                        for (var overtime of self.outsideOTSettingModel.breakdownItems()) {
                            for(var dtoOvertime of dataOvertimeBRDLang){
                                if(overtime.breakdownItemNo() == dtoOvertime.breakdownItemNo){
                                    overtime.languageName(dtoOvertime.name);    
                                }    
                            }
                        }
                        loadOutsideDfd.resolve();
                    });
                }
                // When load done -> re init tooltip.
                $.when(loadOverTimeDfd.done(), loadOutsideDfd.done()).done(() => {
                    service.initTooltip();
                })
            }
            
            /**
             * function on click button export file excel
             */
            private exportFileExcelOutsideOTSetting(): void {
                var self = this;
                service.exportOutsideOTSettingExcelMasterList(self.languageId);
            }
            /**
             * function update select unit rounding
             */
            private updateSelectUnitRounding(selectUnit: number){
                var self = this;
                // if rounding time is 15 or 30 minute             
                // if (selectUnit == 4 || selectUnit == 6) {
                    self.lstRoundingSet(self.lstRounding);
                // } else {
                //     self.lstRoundingSet(self.lstRoundingSub);
                // }
            }

            /**
             * function on click button open dialog vacation conversion settings
             */
            private openDialogVCSettings(): void {
                var self = this;
                //nts.uk.ui.windows.setShared("languageId", self.languageId);
                self.$window.modal("/view/kmk/010/d/index.xhtml").then((data) => {                         
                    if (data && data.isSave) {
                        nts.uk.ui.errors.clearAll();
                        self.startPage().done(() => {
                            service.initTooltip();
                        });
                    }             
                });
            }
        }
        export class OvertimeModel {
            name: KnockoutObservable<string>;
            languageName: KnockoutObservable<string>;
            overtime: KnockoutObservable<number>;
            overtimeNo: KnockoutObservable<number>;
            useClassification: KnockoutObservable<boolean>;
            superHoliday60HOccurs: KnockoutObservable<boolean>;
            requiredText: KnockoutObservable<boolean>;
            requiredOverTime: KnockoutObservable<boolean>;

            constructor() {
                this.name = ko.observable('');
                this.languageName = ko.observable('');
                this.overtime = ko.observable(0);
                this.overtimeNo = ko.observable(0);
                this.useClassification = ko.observable(true);
                this.superHoliday60HOccurs = ko.observable(true);
                this.requiredText = ko.observable(true);
                this.requiredOverTime = ko.observable(true);                
            }

            updateData(dto: OvertimeDto) {
                this.name(dto.name);
                this.languageName(dto.name);
                this.overtime(dto.overtime);
                this.overtimeNo(dto.overtimeNo);
                this.useClassification(dto.useClassification);
                this.superHoliday60HOccurs(dto.superHoliday60HOccurs && dto.useClassification);
            }

            updateEnableCheck(enableCheckbox: boolean) : void{
                this.requiredText(this.useClassification() && enableCheckbox);
                this.requiredOverTime(this.useClassification() && enableCheckbox);
            }
            
            setUpdateData(enableCheckbox: boolean): void {
                var self = this;
                self.useClassification.subscribe(function(use: boolean) {
                    self.requiredText(use && enableCheckbox);
                    self.superHoliday60HOccurs(use && self.superHoliday60HOccurs());
                    self.requiredOverTime(use && enableCheckbox);
                    if( !use && enableCheckbox && self.overtime.length === 0 ) {
                        self.overtime(0); //default in DB
                    }
                });
                self.requiredText.subscribe(function(use: boolean) {
                    $('#overtimeNo_' + self.overtimeNo()).ntsError("clear");
                    $('#overtimeNum_' + self.overtimeNo()).ntsError("clear");
                });
            }

            toDto(): OvertimeDto {
                var dto: OvertimeDto = {
                    name: this.name() == "" ? " " : this.name(),
                    overtime: this.overtime(),
                    overtimeNo: this.overtimeNo(),
                    useClassification: this.useClassification(),
                    superHoliday60HOccurs: this.superHoliday60HOccurs()
                };
                return dto;
            }            
        }
        

        export class OutsideOTBRDItemModel {
            useClassification: KnockoutObservable<boolean>;
            breakdownItemNo: KnockoutObservable<number>;
            name: KnockoutObservable<string>;
            languageName: KnockoutObservable<string>;
            productNumber: KnockoutObservable<number>;
            rateBRDItems: KnockoutObservableArray<PremiumExtra60HRateModel>;
            attendanceItemIds: KnockoutObservableArray<number>;
            attendanceItemName: KnockoutObservable<string>;
            requiredText: KnockoutObservable<boolean>;

            constructor() {
                this.useClassification = ko.observable(true);
                this.breakdownItemNo = ko.observable(0);
                this.name = ko.observable('');
                this.languageName = ko.observable('');
                this.productNumber = ko.observable(0);
                this.rateBRDItems = ko.observableArray([]);
                this.attendanceItemIds = ko.observableArray([]);
                this.attendanceItemName = ko.observable('');
                this.requiredText = ko.observable(true);
            }
            public updateData(dto: OutsideOTBRDItemDto, isUpdate: boolean, listAllIdTemp: any, dataTemp: any) {
                var self = this;
                this.useClassification(dto.useClassification);
                this.breakdownItemNo(dto.breakdownItemNo);
                this.name(dto.name);
                this.languageName(dto.name);
                this.productNumber(dto.productNumber);
                self.attendanceItemIds(dto.attendanceItemIds);
                let listIdNotUse: Array<number> = [];
                if (isUpdate && self.attendanceItemIds() && self.attendanceItemIds().length > 0) {
                    //set source                              
                    let listAllId: any = listAllIdTemp;
                    listIdNotUse = _.difference(self.attendanceItemIds(), listAllId);
                    var selectedName: string[] = [];
                    for (var item of dataTemp) {
                        for (var id of self.attendanceItemIds()) {
                            if (id == item.attendanceItemId) {
                                let count = 0;
                                for (let i = 0; i < listIdNotUse.length; i++) {
                                    if (listIdNotUse[i] === id) {
                                        count++;
                                        break;
                                    }
                                }
                                if (count === 0) {
                                    selectedName.push(item.attendanceItemName);
                                } else {
                                    selectedName.push(getText('KMK010_90'));
                                }
                            }
                        }
                        self.attendanceItemName(selectedName.join(' + '));
                    }

                }
            }

            public toDto(): OutsideOTBRDItemDto {
                var dto: OutsideOTBRDItemDto = {
                    useClassification: this.useClassification(),
                    breakdownItemNo: this.breakdownItemNo(),
                    name: this.name() == "" ? " " : this.name(),
                    productNumber: this.productNumber(),
                    attendanceItemIds: this.attendanceItemIds()
                };
                return dto;
            }
            public updateRateData(rateBRDItems: PremiumExtra60HRateModel[]): void {
                this.rateBRDItems(rateBRDItems);
            }
            /**
             * function on click button show dialog KDL021  
             */
            public openDialogDailyAttendanceItems(): void {
                var self = this;

                nts.uk.ui.block.grayout();  

                nts.uk.at.view.kmk010.a.service.findAllMonthlyAttendanceItem().done(function(dataAllItem){
                    // Map to model
                    nts.uk.at.view.kmk010.a.service.findAllAttendanceItemOvertime().done(function(dataCanSelecte: any) {
                        dataCanSelecte = _.filter(dataCanSelecte, function(o: any) { return o.attendanceItemType == 2; }); // 時間外超過
                        dataCanSelecte = dataCanSelecte.map(o => o.attendanceItemId);
                        nts.uk.ui.windows.setShared('AllAttendanceObj', dataCanSelecte);
                        nts.uk.ui.windows.setShared('SelectedAttendanceId', self.attendanceItemIds());
                        nts.uk.ui.windows.setShared('Multiple', true);
                        nts.uk.ui.windows.setShared('MonthlyMode', true);
                        nts.uk.ui.windows.sub.modal('/view/kdl/021/a/index.xhtml').onClosed(function(): any {
                            var resId: string[] = nts.uk.ui.windows.getShared('selectedChildAttendace');
                            if (resId && resId.length > 0) {
                                var lstDailyAttendanceId: number[] = [];
                                for (var res of resId) {
                                    if (res && res != '') {
                                        lstDailyAttendanceId.push(parseInt(res));
                                    }
                                }
                                self.attendanceItemIds(lstDailyAttendanceId);
                                var selectedName: string[] = [];
                                for (var item of dataAllItem) {
                                    for (var id of lstDailyAttendanceId) {
                                        if (id == item.attendanceItemId) {
                                            selectedName.push(item.attendanceItemName);
                                        }
                                    }
                                }
                                self.attendanceItemName(selectedName.join(' + '));
                                service.initTooltip();
                            }
                            
                            nts.uk.ui.block.clear();
                        });
                    });
                }).fail(function(error){
                    nts.uk.ui.dialog.alertError(error);
                });
            }
            updateEnableCheck(enableCheckbox: boolean): void {
                this.requiredText(this.useClassification() && enableCheckbox);
            }

            setUpdateData(enableCheckbox: boolean): void {
                var self = this;
                self.useClassification.subscribe(function(use: boolean) {
                    self.requiredText(use && enableCheckbox);
                });
                self.requiredText.subscribe(function(use: boolean) {
                    $('#breakdownItemNo_' + self.breakdownItemNo()).ntsError("clear");
                });
            }
        }
        export class OutsideOTSettingModel {
            note: KnockoutObservable<string>;
            calculationMethod: KnockoutObservable<number>;
            overtimes: KnockoutObservableArray<OvertimeModel>;
            breakdownItems: KnockoutObservableArray<OutsideOTBRDItemModel>;
            roundingUnit:  KnockoutObservable<number>;
            roundingProcess:  KnockoutObservable<number>;
            // Start: Add data temp
            listAllIdTemp: any;
            dataTemp: any;
            // End: Add data temp

            constructor() {
                this.note = ko.observable('');
                this.calculationMethod = ko.observable(0);
                this.overtimes = ko.observableArray([]);
                this.breakdownItems = ko.observableArray([]);                
                this.roundingProcess = ko.observable(0);
                this.roundingUnit = ko.observable(0);
            }

            updateData(dto: OutsideOTSettingDto) {
                const self = this;
                self.note(dto.note);
                self.calculationMethod(dto.calculationMethod);
                self.overtimes();
                var dataOvertimeModel: OvertimeModel[] = [];
                for (var overtime of dto.overtimes) {
                    var model: OvertimeModel = new OvertimeModel();
                    model.updateData(overtime);
                    dataOvertimeModel.push(model);
                }
                self.overtimes(dataOvertimeModel);
                var dataBreakdownItemModel: OutsideOTBRDItemModel[] = [];
                self.fetchAttendanceData().then(() => {
                    for (var overtimeBRD of dto.breakdownItems) {
                        var modelBRD: OutsideOTBRDItemModel = new OutsideOTBRDItemModel();
                        modelBRD.updateData(overtimeBRD, true, self.listAllIdTemp, self.dataTemp);
                        dataBreakdownItemModel.push(modelBRD);
                    }
                    self.breakdownItems(dataBreakdownItemModel);
                    self.roundingUnit(dto.roundingUnit);
                    self.roundingProcess(dto.roundingProcess);
                });
            }

            fetchAttendanceData() : JQueryPromise<any> {
                const self = this;
                const dfd = $.Deferred();
                service.findAllMonthlyAttendanceItem().done(function (data) {
                    self.dataTemp = data;
                    service.findAllAttendanceItemOvertime().done(function (dataCanSelecte: any) {
                        dataCanSelecte = _.filter(dataCanSelecte, function (o: any) { return o.attendanceItemType == 2; }); // 時間外超過
                        dataCanSelecte = dataCanSelecte.map(o => o.attendanceItemId);
                        service.getMonthlyAttendanceDivergenceName(dataCanSelecte).done(function (lstItem: Array<any>) {
                            // set source
                            self.listAllIdTemp = lstItem.map(x => x.attendanceItemId);
                            dfd.resolve();
                        }).fail(function (res) {
                            nts.uk.ui.dialog.alert(res.message);
                            dfd.reject();
                        });
                    }).fail(function (error) {
                        nts.uk.ui.dialog.alertError(error);
                        dfd.reject();
                    });
                }).fail(function (error) {
                    nts.uk.ui.dialog.alertError(error);
                    dfd.reject();
                }).always(function () {
                    nts.uk.ui.block.clear();
                });
                return dfd.promise();
            }

            toDto(): OutsideOTSettingDto {
                var overtimes: OvertimeDto[] = [];
                var breakdownItems: OutsideOTBRDItemDto[] = [];

                for (var modelOvertime of this.overtimes()) {
                    overtimes.push(modelOvertime.toDto());
                }

                for (var modelBreakdown of this.breakdownItems()) {
                    breakdownItems.push(modelBreakdown.toDto());
                }

                var dto: OutsideOTSettingDto = {
                    note: this.note(),
                    calculationMethod: this.calculationMethod(),
                    breakdownItems: breakdownItems,
                    overtimes: overtimes,
                    roundingUnit: this.roundingUnit(),
                    roundingProcess: this.roundingProcess() 
                };

                return dto;
            }
        }

        export class PremiumExtra60HRateModel {
            overtimeNo: KnockoutObservable<number>;
            breakdownItemNo: KnockoutObservable<number>;
            premiumRate: KnockoutObservable<number>;
            stashpremiumRate: KnockoutObservable<number>;
            enableInput: KnockoutObservable<boolean>;
            tabIndex: KnockoutObservable<number>;
            constructor() {
                this.overtimeNo = ko.observable(0);
                this.breakdownItemNo = ko.observable(0);
                this.premiumRate = ko.observable(0);
                this.stashpremiumRate = ko.observable(0);
                this.enableInput = ko.observable(true);
                this.tabIndex = ko.observable(0);
            }

            updateData(dto: PremiumExtra60HRateDto) {
                this.overtimeNo(dto.overtimeNo);
                this.breakdownItemNo(dto.breakdownItemNo);
                this.premiumRate(dto.premiumRate);
                this.stashpremiumRate(dto.premiumRate);
                this.stashpremiumRate(dto.premiumRate);
                this.tabIndex(dto.tabIndex);
            }

            updateInfo(breakdownItemNo: number, overtimeNo: number) {
                this.overtimeNo(overtimeNo);
                this.breakdownItemNo(breakdownItemNo);
            }
            toDto(): PremiumExtra60HRateDto {
                var dto: PremiumExtra60HRateDto = {
                    overtimeNo: this.overtimeNo(),
                    breakdownItemNo: this.breakdownItemNo(),
                    premiumRate: this.premiumRate(),
                    tabIndex: this.tabIndex()
                };
                return dto;
            }
            updateEnable(enableInput: boolean): void {
                this.enableInput(enableInput);
                if (!enableInput) {
                    this.premiumRate(this.stashpremiumRate());
                }
            }
            
        }

        export class SuperHD60HConMedModel {
            roundingTime: KnockoutObservable<number>;
            rounding: KnockoutObservable<number>;
            superHolidayOccurrenceUnit: KnockoutObservable<number>;
            premiumExtra60HRates: PremiumExtra60HRateModel[];

            constructor() {
                this.roundingTime = ko.observable(0);
                this.rounding = ko.observable(0);
                this.superHolidayOccurrenceUnit = ko.observable(0);
                this.premiumExtra60HRates = [];
            }

            updateData(dto: SuperHD60HConMedDto): void {
                if (dto.setting) {
                    this.roundingTime(dto.roundingTime);
                    this.rounding(dto.rounding);
                    this.superHolidayOccurrenceUnit(dto.superHolidayOccurrenceUnit);
                    this.premiumExtra60HRates = [];
                    if (dto.premiumExtra60HRates && dto.premiumExtra60HRates.length > 0) {
                        for (var itemDto of dto.premiumExtra60HRates) {
                            var model: PremiumExtra60HRateModel = new PremiumExtra60HRateModel();
                            model.updateData(itemDto);
                            this.premiumExtra60HRates.push(model);
                        }
                    }
                }
            }

            toDto(): SuperHD60HConMedDto {
                var premiumExtra60HRates: PremiumExtra60HRateDto[] = [];
                for (var model of this.premiumExtra60HRates) {
                    premiumExtra60HRates.push(model.toDto());
                }
                var dto: SuperHD60HConMedDto = {
                    roundingTime: this.roundingTime(),
                    rounding: this.rounding(),
                    setting: true,
                    superHolidayOccurrenceUnit: this.superHolidayOccurrenceUnit(),
                    premiumExtra60HRates: premiumExtra60HRates
                };
                return dto;
            }
        }

    }
}
