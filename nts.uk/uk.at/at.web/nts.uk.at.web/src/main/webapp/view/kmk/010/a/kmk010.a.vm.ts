module nts.uk.at.view.kmk010.a {

    import EnumConstantDto = service.model.EnumConstantDto;
    import OvertimeDto = service.model.OvertimeDto;
    import OvertimeBRDItemDto = service.model.OvertimeBRDItemDto;
    import OvertimeSettingDto = service.model.OvertimeSettingDto;
    import PremiumExtra60HRateDto = service.model.PremiumExtra60HRateDto;
    import SuperHD60HConMedDto = service.model.SuperHD60HConMedDto;
    import OvertimeLangNameDto = service.model.OvertimeLangNameDto;

    export module viewmodel {

        export class ScreenModel {
            calculationMethods: KnockoutObservableArray<EnumConstantDto>;
            overtimeSettingModel: OvertimeSettingModel;
            superHD60HConMedModel: SuperHD60HConMedModel;
            useClassification: KnockoutObservableArray<any>;
            lstUnit: EnumConstantDto[];
            lstRounding: EnumConstantDto[];
            languageId: string;
            static LANGUAGE_ID_JAPAN = 'ja';

            constructor() {
                var self = this;
                self.calculationMethods = ko.observableArray<EnumConstantDto>([]);
                self.overtimeSettingModel = new OvertimeSettingModel();
                self.superHD60HConMedModel = new SuperHD60HConMedModel();
                self.languageId = 'ja';
            }

            /**
             * start page data 
             */
            startPage(): JQueryPromise<any> {
                var self = this;
                var dfd = $.Deferred();

                // find all unit
                service.findAllOvertimeUnit().done(function(data) {
                    self.lstUnit = data;
                });

                // find all rounding
                service.findAllOvertimeRounding().done(function(data) {
                    self.lstRounding = data;
                });

                service.findByIdOvertimeSetting().done(function(dataOvertimeSetting) {
                    self.overtimeSettingModel.updateData(dataOvertimeSetting);
                    for (var brdItem of self.overtimeSettingModel.breakdownItems) {
                        var rateBRDItems: PremiumExtra60HRateModel[] = [];
                        for (var overtimeItem of self.overtimeSettingModel.overtimes) {
                            var rateModel: PremiumExtra60HRateModel = new PremiumExtra60HRateModel();
                            rateModel.updateInfo(brdItem.breakdownItemNo(), overtimeItem.overtimeNo());
                            rateBRDItems.push(rateModel);
                        }
                        brdItem.updateRateData(rateBRDItems);
                    }
                    service.findAllOvertimeCalculationMethod().done(function(dataMethod) {
                        self.calculationMethods(dataMethod);
                        service.findByIdSuperHD60HConMed().done(function(dataSuper) {
                            self.superHD60HConMedModel.updateData(dataSuper);
                            self.updateDataSuperHolidayMethod(dataSuper);
                        });
                        dfd.resolve(self);
                    });
                });
                return dfd.promise();
            }

            /**
             * update data super holiday method
             */
            private updateDataSuperHolidayMethod(dto: SuperHD60HConMedDto) {
                if (dto.premiumExtra60HRates && dto.premiumExtra60HRates.length > 0) {
                    var self = this;
                    for (var brdItem of self.overtimeSettingModel.breakdownItems) {
                        for (var rateBRDItem of brdItem.rateBRDItems) {
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
            private openDialogOvertimeSetting(): void {
                var self = this;
                nts.uk.ui.windows.setShared("languageId", self.languageId);
                nts.uk.ui.windows.sub.modal("/view/kmk/010/b/index.xhtml").onClosed(function() {

                });
            }
            /**
             * function on click button open dialog overtime break down item
             */
            private openDialogOvertimeBRDItem(): void {
                var self = this;
                nts.uk.ui.windows.setShared("languageId", self.languageId);
                nts.uk.ui.windows.sub.modal("/view/kmk/010/c/index.xhtml").onClosed(function() {

                });
            }

            /**
             * convert array 
             */
            private toArrayRateDto(): PremiumExtra60HRateDto[] {
                var dataRate: PremiumExtra60HRateDto[] = [];
                var self = this;
                for (var brdItem of self.overtimeSettingModel.breakdownItems) {
                    for (var rateItem of brdItem.rateBRDItems) {
                        dataRate.push(rateItem.toDto());
                    }
                }
                return dataRate;
            }

            /**
             * function on click save overtime setting
             */
            private saveOvertimeSetting(): void {
                var self = this;
                service.saveOvertimeSetting(self.overtimeSettingModel.toDto()).done(function() {
                    var dto: SuperHD60HConMedDto = self.superHD60HConMedModel.toDto();
                    dto.premiumExtra60HRates = self.toArrayRateDto();
                    service.saveSuperHD60HConMed(dto).done(function() {
                        nts.uk.ui.dialog.info({ messageId: "Msg_15" }).then(function() {
                            // reload pa    
                        });
                    });
                }).fail(function(error) {
                    nts.uk.ui.dialog.alertError(error);
                });
            }

            /**
             * add view language
             */
            public addViewLanguage(): void {
                var self = this;
                $("#switch-language").ntsSwitchMasterLanguage();

                $("#switch-language").on("selectionChanged", function(event, arg1, arg2) {
                    self.languageId = event.detail.languageId;
                    self.updateLanguage();
                });
            }
            /**
             * update language by select language
             */
            public updateLanguage(): void {
                var self = this;
                if(self.languageId === ScreenModel.LANGUAGE_ID_JAPAN){
                    service.findAllOvertime().done(function(dataOvertime){
                        for (var overtime of self.overtimeSettingModel.overtimes) {
                            for(var dtoOvertime of dataOvertime){
                                if(overtime.overtimeNo() == dtoOvertime.overtimeNo){
                                    overtime.name(dtoOvertime.name);    
                                }    
                            }
                        } 
                    });    
                    service.findAllOvertimeBRDItem().done(function(dataOvertimeBRD){
                        for (var overtime of self.overtimeSettingModel.breakdownItems) {
                            for(var dtoOvertime of dataOvertimeBRD){
                                if(overtime.breakdownItemNo() == dtoOvertime.breakdownItemNo){
                                    overtime.name(dtoOvertime.name);    
                                }    
                            }
                        } 
                    });
                }else {
                    service.findAllOvertimeLanguageName(self.languageId).done(function(dataOvertimeLang){
                        for (var overtime of self.overtimeSettingModel.overtimes) {
                            for(var dtoOvertime of dataOvertimeLang){
                                if(overtime.overtimeNo() == dtoOvertime.overtimeNo){
                                    overtime.name(dtoOvertime.name);    
                                }    
                            }
                        }
                    });    
                    
                    service.findAllOvertimeLanguageBRDItem(self.languageId).done(function(dataOvertimeBRDLang){
                        for (var overtime of self.overtimeSettingModel.breakdownItems) {
                            for(var dtoOvertime of dataOvertimeBRDLang){
                                if(overtime.breakdownItemNo() == dtoOvertime.breakdownItemNo){
                                    overtime.name(dtoOvertime.name);    
                                }    
                            }
                        } 
                    });
                }
            }
        }
        export class OvertimeModel {
            name: KnockoutObservable<string>;
            overtime: KnockoutObservable<number>;
            overtimeNo: KnockoutObservable<number>;
            useClassification: KnockoutObservable<boolean>;

            constructor() {
                this.name = ko.observable('');
                this.overtime = ko.observable(0);
                this.overtimeNo = ko.observable(0);
                this.useClassification = ko.observable(true);
            }

            updateData(dto: OvertimeDto) {
                this.name(dto.name);
                this.overtime(dto.overtime);
                this.overtimeNo(dto.overtimeNo);
                this.useClassification(dto.useClassification);
            }

            toDto(): OvertimeDto {
                var dto: OvertimeDto = {
                    name: this.name(),
                    overtime: this.overtime(),
                    overtimeNo: this.overtimeNo(),
                    useClassification: this.useClassification()
                };
                return dto;
            }
        }

        export class OvertimeBRDItemModel {
            useClassification: KnockoutObservable<boolean>;
            breakdownItemNo: KnockoutObservable<number>;
            name: KnockoutObservable<string>;
            productNumber: KnockoutObservable<number>;
            rateBRDItems: PremiumExtra60HRateModel[];

            constructor() {
                this.useClassification = ko.observable(true);
                this.breakdownItemNo = ko.observable(0);
                this.name = ko.observable('');
                this.productNumber = ko.observable(0);
                this.rateBRDItems = [];
            }

           public updateData(dto: OvertimeBRDItemDto) {
                this.useClassification(dto.useClassification);
                this.breakdownItemNo(dto.breakdownItemNo);
                this.name(dto.name);
                this.productNumber(dto.productNumber);
            }

            public toDto(): OvertimeBRDItemDto {
                var dto: OvertimeBRDItemDto = {
                    useClassification: this.useClassification(),
                    breakdownItemNo: this.breakdownItemNo(),
                    name: this.name(),
                    productNumber: this.productNumber()
                };
                return dto;
            }
            public updateRateData(rateBRDItems: PremiumExtra60HRateModel[]): void {
                this.rateBRDItems = rateBRDItems;
            }
            /**
             * function on click button show dialog KDL021  
             */
            public openDialogDailyAttendanceItems(): void {
                alert(this.breakdownItemNo());
            }
        }
        export class OvertimeSettingModel {
            note: KnockoutObservable<string>;
            calculationMethod: KnockoutObservable<number>;
            overtimes: OvertimeModel[];
            breakdownItems: OvertimeBRDItemModel[]

            constructor() {
                this.note = ko.observable('');
                this.calculationMethod = ko.observable(0);
                this.overtimes = [];
                this.breakdownItems = [];
            }

            updateData(dto: OvertimeSettingDto) {
                this.note(dto.note);
                this.calculationMethod(dto.calculationMethod);
                this.overtimes = [];
                for (var overtime of dto.overtimes) {
                    var model: OvertimeModel = new OvertimeModel();
                    model.updateData(overtime);
                    this.overtimes.push(model);
                }
                this.breakdownItems = [];
                for (var overtimeBRD of dto.breakdownItems) {
                    var modelBRD: OvertimeBRDItemModel = new OvertimeBRDItemModel();
                    modelBRD.updateData(overtimeBRD);
                    this.breakdownItems.push(modelBRD);
                }

            }

            toDto(): OvertimeSettingDto {
                var overtimes: OvertimeDto[] = [];
                var breakdownItems: OvertimeBRDItemDto[] = [];

                for (var modelOvertime of this.overtimes) {
                    overtimes.push(modelOvertime.toDto());
                }

                for (var modelBreakdown of this.breakdownItems) {
                    breakdownItems.push(modelBreakdown.toDto());
                }

                var dto: OvertimeSettingDto = {
                    note: this.note(),
                    calculationMethod: this.calculationMethod(),
                    breakdownItems: breakdownItems,
                    overtimes: overtimes
                };

                return dto;
            }
        }

        export class PremiumExtra60HRateModel {
            overtimeNo: KnockoutObservable<number>;
            breakdownItemNo: KnockoutObservable<number>;
            premiumRate: KnockoutObservable<number>;

            constructor() {
                this.overtimeNo = ko.observable(0);
                this.breakdownItemNo = ko.observable(0);
                this.premiumRate = ko.observable(0);
            }

            updateData(dto: PremiumExtra60HRateDto) {
                this.overtimeNo(dto.overtimeNo);
                this.breakdownItemNo(dto.breakdownItemNo);
                this.premiumRate(dto.premiumRate);
            }

            updateInfo(breakdownItemNo: number, overtimeNo: number) {
                this.overtimeNo(overtimeNo);
                this.breakdownItemNo(breakdownItemNo);
            }
            toDto(): PremiumExtra60HRateDto {
                var dto: PremiumExtra60HRateDto = {
                    overtimeNo: this.overtimeNo(),
                    breakdownItemNo: this.breakdownItemNo(),
                    premiumRate: this.premiumRate()
                };
                return dto;
            }

        }

        export class SuperHD60HConMedModel {
            roundingTime: KnockoutObservable<number>;
            rounding: KnockoutObservable<number>;
            superHolidayOccurrenceUnit: KnockoutObservable<number>;
            premiumExtra60HRates: PremiumExtra60HRateModel[];

            constructor() {
                this.roundingTime = ko.observable(1);
                this.rounding = ko.observable(1);
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
                    superHolidayOccurrenceUnit: this.superHolidayOccurrenceUnit(),
                    premiumExtra60HRates: premiumExtra60HRates
                };
                return dto;
            }
        }

    }
}