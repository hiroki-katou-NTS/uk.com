module nts.uk.pr.view.qmm008.c {
    export module viewmodel {
        import InsuranceOfficeItem = service.model.finder.InsuranceOfficeItemDto;
        import RoundingDto = service.model.finder.RoundingDto;
        import RoundingItemDto = service.model.finder.RoundingItemDto;
        import Enum = service.model.finder.Enum;
        import OfficeItemDto = service.model.finder.OfficeItemDto;
        import PensionRateDto = service.model.finder.PensionRateDto;
        import PensionRateItemDto = service.model.finder.PensionRateItemDto;
        import FundRateItemDto = service.model.finder.FundRateItemDto;
        import ScreenBaseModel = base.simplehistory.viewmodel.ScreenBaseModel;

        import iservice = nts.uk.pr.view.qmm008.i.service;
        import PensionAvgearnDto = iservice.model.PensionAvgearnDto;
        import commonService = nts.uk.pr.view.qmm008._0.common.service;
        import AvgEarnLevelMasterSettingDto = nts.uk.pr.view.qmm008._0.common.service.model.AvgEarnLevelMasterSettingDto;

        export class ScreenModel extends ScreenBaseModel<service.model.Office, service.model.Pension>{
            pensionModel: KnockoutObservable<PensionRateModel>
            pensionInsuranceOfficeList: KnockoutObservableArray<InsuranceOfficeItem>;

            selectedInsuranceOfficeId: KnockoutObservable<string>;

            //list rounding options
            roundingList: KnockoutObservableArray<Enum>;
            //numberInputOptions
            Rate2: KnockoutObservable<nts.uk.ui.option.NumberEditorOption>;
            pensionFilteredData: KnockoutObservableArray<any>;
            //for pension fund switch button
            pensionFundInputOptions: KnockoutObservableArray<any>;
            //for pension auto calculate switch button
            pensionCalculateOptions: KnockoutObservableArray<any>;
            //for control data after close dialog
            isTransistReturnData: KnockoutObservable<boolean>;
            fundInputEnable: KnockoutObservable<boolean>;

            // Flags
            isLoading: KnockoutObservable<boolean>;
            currentOfficeCode: KnockoutObservable<string>;
            japanYear: KnockoutObservable<string>;
            sendOfficeData: KnockoutObservable<string>;

            listAvgEarnLevelMasterSetting: Array<AvgEarnLevelMasterSettingDto>;
            listPensionAvgearnModel: KnockoutObservableArray<PensionAvgearnModel>;
            errorList: KnockoutObservableArray<any>;
            dirty: nts.uk.ui.DirtyChecker;
            constructor() {
                super({
                    functionName: '厚生年金',
                    service: service.instance,
                    removeMasterOnLastHistoryRemove: false
                });
                var self = this;
                //init model
                self.pensionModel = ko.observable(new PensionRateModel());

                // init insurance offices list
                self.pensionInsuranceOfficeList = ko.observableArray<InsuranceOfficeItem>([]);


                self.pensionFilteredData = ko.observableArray(nts.uk.util.flatArray(self.pensionInsuranceOfficeList(), "childs"));
                //init rounding list
                self.roundingList = ko.observableArray<Enum>([]);
                self.Rate2 = ko.mapping.fromJS(new nts.uk.ui.option.NumberEditorOption({
                    grouplength: 3,
                    decimallength: 2
                }));
                //pension fund switch 
                self.pensionFundInputOptions = ko.observableArray([
                    { code: '1', name: '有' },
                    { code: '0', name: '無' }
                ]);
                //pension calculate switch 
                self.pensionCalculateOptions = ko.observableArray([
                    { code: '0', name: 'する' },
                    { code: '1', name: 'しない' }
                ]);
                // add history dialog
                self.isTransistReturnData = ko.observable(false);

                self.fundInputEnable = ko.observable(false);

                self.isLoading = ko.observable(true);
                self.currentOfficeCode = ko.observable('');
                self.japanYear = ko.observable('');
                self.sendOfficeData = ko.observable('');

                self.listAvgEarnLevelMasterSetting = [];
                self.listPensionAvgearnModel = ko.observableArray<PensionAvgearnModel>([]);

                self.pensionModel().fundInputApply.subscribe(function() {
                    //change select -> hide fund input table
                    if (self.pensionModel().fundInputApply() != 1) {
                        self.fundInputEnable(true);
                    } else {
                        self.fundInputEnable(false);
                    }
                });
                self.errorList = ko.observableArray([
                    { messageId: "ER001", message: "＊が入力されていません。" },
                    { messageId: "ER007", message: "＊が選択されていません。" },
                    { messageId: "ER005", message: "入力した＊は既に存在しています。\r\n ＊を確認してください。" },
                    { messageId: "ER008", message: "選択された＊は使用されているため削除できません。" },
                    { messageId: "AL001", message: "変更された内容が登録されていません。\r\n よろしいですか。" }
                ]);
                self.dirty = new nts.uk.ui.DirtyChecker(ko.observable(''));
            } //end constructor

            // Start
            public start(): JQueryPromise<any> {
                var self = this;
                var dfd = $.Deferred<any>();
                self.getAllRounding().done(function() {
                    // Resolve
                    dfd.resolve(null);
                });
                commonService.getAvgEarnLevelMasterSettingList().done(function(data) {
                    self.listAvgEarnLevelMasterSetting = data;
                    dfd.resolve();
                });
                // Return.
                return dfd.promise();
            }

            //load All rounding method
            public getAllRounding(): JQueryPromise<any> {
                var self = this;
                var dfd = $.Deferred<any>();
                // Invoked service method
                service.findAllRounding().done(function(data: Array<Enum>) {
                    // Set list.
                    self.roundingList(data);
                    dfd.resolve(data);
                });
                // Return.
                return dfd.promise();
            }

            //string rounding to value
            public convertRounding(stringRounding: string) {
                switch (stringRounding) {
                    case Rounding.ROUNDUP: return "0";
                    case Rounding.TRUNCATION: return "1";
                    case Rounding.DOWN4_UP5: return "2";
                    case Rounding.ROUNDDOWN: return "3";
                    case Rounding.DOWN5_UP6: return "4";
                    default: return "0";
                }
            }

            //value to string rounding
            public convertToRounding(stringValue: string) {
                switch (stringValue) {
                    case "0": return Rounding.ROUNDUP;
                    case "1": return Rounding.TRUNCATION;
                    case "2": return Rounding.DOWN4_UP5;
                    case "3": return Rounding.ROUNDDOWN;
                    case "4": return Rounding.DOWN5_UP6;
                    default: return Rounding.ROUNDUP;
                }
            }

            public loadPension(data: PensionRateDto) {
                var self = this;
                if (data == null) {
                    return;
                }
                //Set pension detail.
                self.pensionModel().historyId = data.historyId;
                self.pensionModel().companyCode = data.companyCode;
                self.pensionModel().officeCode(data.officeCode);
                self.pensionModel().startMonth(nts.uk.time.formatYearMonth(parseInt(data.startMonth)));
                self.pensionModel().endMonth(nts.uk.time.formatYearMonth(parseInt(data.endMonth)));
                self.japanYear("(" + nts.uk.time.yearmonthInJapanEmpire(data.startMonth).toString() + ")");
                self.pensionModel().autoCalculate(data.autoCalculate);
                self.pensionModel().fundInputApply(data.fundInputApply);

                data.premiumRateItems.forEach(function(item, index) {
                    if (item.payType == PaymentType.SALARY && item.genderType == InsuranceGender.MALE) {
                        self.pensionModel().rateItems().pensionSalaryPersonalSon(item.personalRate);
                        self.pensionModel().rateItems().pensionSalaryCompanySon(item.companyRate);
                    }
                    if (item.payType == PaymentType.BONUS && item.genderType == InsuranceGender.MALE) {
                        self.pensionModel().rateItems().pensionBonusPersonalSon(item.personalRate);
                        self.pensionModel().rateItems().pensionBonusCompanySon(item.companyRate);
                    }
                    if (item.payType == PaymentType.SALARY && item.genderType == InsuranceGender.FEMALE) {
                        self.pensionModel().rateItems().pensionSalaryPersonalDaughter(item.personalRate);
                        self.pensionModel().rateItems().pensionSalaryCompanyDaughter(item.companyRate);
                    }
                    if (item.payType == PaymentType.BONUS && item.genderType == InsuranceGender.FEMALE) {
                        self.pensionModel().rateItems().pensionBonusPersonalDaughter(item.personalRate);
                        self.pensionModel().rateItems().pensionBonusCompanyDaughter(item.companyRate);
                    }
                    if (item.payType == PaymentType.SALARY && item.genderType == InsuranceGender.UNKNOW) {
                        self.pensionModel().rateItems().pensionSalaryPersonalUnknown(item.personalRate);
                        self.pensionModel().rateItems().pensionSalaryCompanyUnknown(item.companyRate);
                    }
                    if (item.payType == PaymentType.BONUS && item.genderType == InsuranceGender.UNKNOW) {
                        self.pensionModel().rateItems().pensionBonusPersonalUnknown(item.personalRate);
                        self.pensionModel().rateItems().pensionBonusCompanyUnknown(item.companyRate);
                    }
                });

                data.fundRateItems.forEach(function(item, index) {
                    if (item.payType == PaymentType.SALARY && item.genderType == InsuranceGender.MALE) {
                        self.pensionModel().fundRateItems().salaryPersonalSonBurden(item.burdenChargePersonalRate);
                        self.pensionModel().fundRateItems().salaryCompanySonBurden(item.burdenChargeCompanyRate);
                        self.pensionModel().fundRateItems().salaryPersonalSonExemption(item.exemptionChargePersonalRate);
                        self.pensionModel().fundRateItems().salaryCompanySonExemption(item.exemptionChargeCompanyRate);
                    }
                    if (item.payType == PaymentType.BONUS && item.genderType == InsuranceGender.MALE) {
                        self.pensionModel().fundRateItems().bonusPersonalSonBurden(item.burdenChargePersonalRate);
                        self.pensionModel().fundRateItems().bonusCompanySonBurden(item.burdenChargeCompanyRate);
                        self.pensionModel().fundRateItems().bonusPersonalSonExemption(item.exemptionChargePersonalRate);
                        self.pensionModel().fundRateItems().bonusCompanySonExemption(item.exemptionChargeCompanyRate);
                    }
                    if (item.payType == PaymentType.SALARY && item.genderType == InsuranceGender.FEMALE) {
                        self.pensionModel().fundRateItems().salaryPersonalDaughterBurden(item.burdenChargePersonalRate);
                        self.pensionModel().fundRateItems().salaryCompanyDaughterBurden(item.burdenChargeCompanyRate);
                        self.pensionModel().fundRateItems().salaryPersonalDaughterExemption(item.exemptionChargePersonalRate);
                        self.pensionModel().fundRateItems().salaryCompanyDaughterExemption(item.exemptionChargeCompanyRate);
                    }
                    if (item.payType == PaymentType.BONUS && item.genderType == InsuranceGender.FEMALE) {
                        self.pensionModel().fundRateItems().bonusPersonalDaughterBurden(item.burdenChargePersonalRate);
                        self.pensionModel().fundRateItems().bonusCompanyDaughterBurden(item.burdenChargeCompanyRate);
                        self.pensionModel().fundRateItems().bonusPersonalDaughterExemption(item.exemptionChargePersonalRate);
                        self.pensionModel().fundRateItems().bonusCompanyDaughterExemption(item.exemptionChargeCompanyRate);
                    }
                    if (item.payType == PaymentType.SALARY && item.genderType == InsuranceGender.UNKNOW) {
                        self.pensionModel().fundRateItems().salaryPersonalUnknownBurden(item.burdenChargePersonalRate);
                        self.pensionModel().fundRateItems().salaryCompanyUnknownBurden(item.burdenChargeCompanyRate);
                        self.pensionModel().fundRateItems().salaryPersonalUnknownExemption(item.exemptionChargePersonalRate);
                        self.pensionModel().fundRateItems().salaryCompanyUnknownExemption(item.exemptionChargeCompanyRate);
                    }
                    if (item.payType == PaymentType.BONUS && item.genderType == InsuranceGender.UNKNOW) {
                        self.pensionModel().fundRateItems().bonusPersonalUnknownBurden(item.burdenChargePersonalRate);
                        self.pensionModel().fundRateItems().bonusCompanyUnknownBurden(item.burdenChargeCompanyRate);
                        self.pensionModel().fundRateItems().bonusPersonalUnknownExemption(item.exemptionChargePersonalRate);
                        self.pensionModel().fundRateItems().bonusCompanyUnknownExemption(item.exemptionChargeCompanyRate);
                    }
                });

                //set rounding list
                self.pensionModel().roundingMethods().pensionSalaryPersonalComboBox(self.roundingList());
                self.pensionModel().roundingMethods().pensionSalaryCompanyComboBox(self.roundingList());
                self.pensionModel().roundingMethods().pensionBonusPersonalComboBox(self.roundingList());
                self.pensionModel().roundingMethods().pensionBonusCompanyComboBox(self.roundingList());

                //Set selected rounding method
                data.roundingMethods.forEach(function(item, index) {
                    if (item.payType == PaymentType.SALARY) {
                        self.pensionModel().roundingMethods().pensionSalaryPersonalComboBoxSelectedCode(self.convertRounding(item.roundAtrs.personalRoundAtr));
                        self.pensionModel().roundingMethods().pensionSalaryCompanyComboBoxSelectedCode(self.convertRounding(item.roundAtrs.companyRoundAtr));
                    }
                    else {
                        self.pensionModel().roundingMethods().pensionBonusPersonalComboBoxSelectedCode(self.convertRounding(item.roundAtrs.personalRoundAtr));
                        self.pensionModel().roundingMethods().pensionBonusCompanyComboBoxSelectedCode(self.convertRounding(item.roundAtrs.companyRoundAtr));
                    }
                });

                self.pensionModel().maxAmount(data.maxAmount);
                self.pensionModel().childContributionRate(data.childContributionRate);
            }

            private pensionCollectData() {
                var self = this;
                var rates = self.pensionModel().rateItems();

                var rateItems: Array<PensionRateItemDto> = [];
                rateItems.push(new PensionRateItemDto(PaymentType.SALARY, InsuranceGender.MALE, rates.pensionSalaryPersonalSon(), rates.pensionSalaryCompanySon()));
                rateItems.push(new PensionRateItemDto(PaymentType.SALARY, InsuranceGender.FEMALE, rates.pensionSalaryPersonalDaughter(), rates.pensionSalaryCompanyDaughter()));
                rateItems.push(new PensionRateItemDto(PaymentType.SALARY, InsuranceGender.UNKNOW, rates.pensionSalaryPersonalUnknown(), rates.pensionSalaryCompanyUnknown()));
                rateItems.push(new PensionRateItemDto(PaymentType.BONUS, InsuranceGender.MALE, rates.pensionBonusPersonalSon(), rates.pensionBonusCompanySon()));
                rateItems.push(new PensionRateItemDto(PaymentType.BONUS, InsuranceGender.FEMALE, rates.pensionBonusPersonalDaughter(), rates.pensionBonusCompanyDaughter()));
                rateItems.push(new PensionRateItemDto(PaymentType.BONUS, InsuranceGender.UNKNOW, rates.pensionBonusPersonalUnknown(), rates.pensionBonusCompanyUnknown()));

                var fundRates = self.pensionModel().fundRateItems();
                var fundRateItems: Array<FundRateItemDto> = [];
                fundRateItems.push(new FundRateItemDto(PaymentType.SALARY, InsuranceGender.MALE, fundRates.salaryPersonalSonBurden(), fundRates.salaryCompanySonBurden(), fundRates.salaryPersonalSonExemption(), fundRates.salaryCompanySonExemption()));
                fundRateItems.push(new FundRateItemDto(PaymentType.SALARY, InsuranceGender.FEMALE, fundRates.salaryPersonalDaughterBurden(), fundRates.salaryCompanyDaughterBurden(), fundRates.salaryPersonalDaughterExemption(), fundRates.salaryCompanyDaughterExemption()));
                fundRateItems.push(new FundRateItemDto(PaymentType.SALARY, InsuranceGender.UNKNOW, fundRates.salaryPersonalUnknownBurden(), fundRates.salaryCompanyUnknownBurden(), fundRates.salaryPersonalUnknownExemption(), fundRates.salaryCompanyUnknownExemption()));
                fundRateItems.push(new FundRateItemDto(PaymentType.BONUS, InsuranceGender.MALE, fundRates.bonusPersonalSonBurden(), fundRates.bonusCompanySonBurden(), fundRates.bonusPersonalSonExemption(), fundRates.bonusCompanySonExemption()));
                fundRateItems.push(new FundRateItemDto(PaymentType.BONUS, InsuranceGender.FEMALE, fundRates.bonusPersonalDaughterBurden(), fundRates.bonusCompanyDaughterBurden(), fundRates.bonusPersonalDaughterExemption(), fundRates.bonusCompanyDaughterExemption()));
                fundRateItems.push(new FundRateItemDto(PaymentType.BONUS, InsuranceGender.UNKNOW, fundRates.bonusPersonalUnknownBurden(), fundRates.bonusCompanyUnknownBurden(), fundRates.bonusPersonalUnknownExemption(), fundRates.bonusCompanyUnknownExemption()));

                var roundingMethods: Array<RoundingDto> = [];
                var rounding = self.pensionModel().roundingMethods();
                roundingMethods.push(new RoundingDto(PaymentType.SALARY, new RoundingItemDto(self.convertToRounding(self.pensionModel().roundingMethods().pensionSalaryPersonalComboBoxSelectedCode()), self.convertToRounding(self.pensionModel().roundingMethods().pensionSalaryCompanyComboBoxSelectedCode()))));
                roundingMethods.push(new RoundingDto(PaymentType.BONUS, new RoundingItemDto(self.convertToRounding(self.pensionModel().roundingMethods().pensionBonusPersonalComboBoxSelectedCode()), self.convertToRounding(self.pensionModel().roundingMethods().pensionBonusCompanyComboBoxSelectedCode()))));

                return new service.model.finder.PensionRateDto(self.pensionModel().historyId, self.pensionModel().companyCode, self.currentOfficeCode(), self.pensionModel().startMonth(), self.pensionModel().endMonth(), self.pensionModel().autoCalculate(), self.pensionModel().fundInputApply(), rateItems, fundRateItems, roundingMethods, self.pensionModel().maxAmount(), self.pensionModel().childContributionRate());
            }

            //get current item office 
            public getDataOfPensionSelectedOffice(): InsuranceOfficeItem {
                var self = this;
                var saveVal: InsuranceOfficeItem = null;
                // Set parent value
                self.pensionInsuranceOfficeList().forEach(function(item, index) {
                    if (self.currentOfficeCode() == item.code) {
                        saveVal = item;
                    }
                });
                return saveVal;
            }

            public save() {
                var self = this;
                //check auto calculate
                if (self.pensionModel().autoCalculate() == AutoCalculateType.Auto) {
                    nts.uk.ui.dialog.confirm("自動計算が行われます。登録しますか？").ifYes(function() {
                        self.dirty = new nts.uk.ui.DirtyChecker(self.pensionModel);
                        iservice.updatePensionAvgearn(self.collectData(), self.pensionCollectData().officeCode);
                        //update pension
                        service.updatePensionRate(self.pensionCollectData()).done(function() {
                        });
                    }).ifNo(function() {
                    });
                }
            }
            /**
            * Collect data from input.
            */
            private collectData(): PensionAvgearnDto {
                var self = this;
                var data: any = [];
                self.listAvgEarnLevelMasterSetting.forEach(item => {
                    self.listPensionAvgearnModel.push(self.calculateHealthInsuranceAvgEarnModel(item));
                });
                self.listPensionAvgearnModel().forEach(item => {
                    data.push(ko.toJS(item));
                });
                return data;
            }

            /**
             * Calculate the healthInsuranceAvgearn
             */
            private calculateHealthInsuranceAvgEarnModel(levelMasterSetting: AvgEarnLevelMasterSettingDto): PensionAvgearnModel {
                var self = this;
                var historyId = self.pensionModel().historyId;
                var pensionRateItems: PensionRateItemModel = self.pensionModel().rateItems();
                var fundRateItems: FunRateItemModel = self.pensionModel().fundRateItems();
                var roundingMethods: PensionRateRoundingModel = self.pensionModel().roundingMethods();
                var personalRounding = self.convertToRounding(roundingMethods.pensionSalaryPersonalComboBoxSelectedCode());
                var companyRounding = self.convertToRounding(roundingMethods.pensionSalaryCompanyComboBoxSelectedCode());
                var rate = levelMasterSetting.avgEarn / 1000;
                var autoCalculate = self.pensionModel().autoCalculate();
                if (autoCalculate == AutoCalculateType.Auto) {
                    return new PensionAvgearnModel(
                        historyId,
                        levelMasterSetting.code,
                        new PensionAvgearnValueModel(
                            self.rounding(companyRounding, fundRateItems.salaryCompanySonExemption() * rate),
                            self.rounding(companyRounding, fundRateItems.salaryCompanyDaughterExemption() * rate),
                            self.rounding(companyRounding, fundRateItems.salaryCompanyUnknownExemption() * rate)),
                        new PensionAvgearnValueModel(
                            self.rounding(companyRounding, fundRateItems.salaryCompanySonBurden() * rate),
                            self.rounding(companyRounding, fundRateItems.salaryCompanyDaughterBurden() * rate),
                            self.rounding(companyRounding, fundRateItems.salaryCompanyUnknownBurden() * rate)),
                        new PensionAvgearnValueModel(
                            self.rounding(companyRounding, pensionRateItems.pensionSalaryCompanySon() * rate),
                            self.rounding(companyRounding, pensionRateItems.pensionSalaryCompanyDaughter() * rate),
                            self.rounding(companyRounding, pensionRateItems.pensionSalaryCompanyUnknown() * rate)),
                        new PensionAvgearnValueModel(
                            self.rounding(personalRounding, fundRateItems.salaryPersonalSonExemption() * rate),
                            self.rounding(personalRounding, fundRateItems.salaryPersonalDaughterExemption() * rate),
                            self.rounding(personalRounding, fundRateItems.salaryPersonalUnknownExemption() * rate)),
                        new PensionAvgearnValueModel(
                            self.rounding(personalRounding, fundRateItems.salaryPersonalSonBurden() * rate),
                            self.rounding(personalRounding, fundRateItems.salaryPersonalDaughterBurden() * rate),
                            self.rounding(personalRounding, fundRateItems.salaryPersonalUnknownBurden() * rate)),
                        new PensionAvgearnValueModel(
                            self.rounding(companyRounding, pensionRateItems.pensionSalaryPersonalSon() * rate),
                            self.rounding(companyRounding, pensionRateItems.pensionSalaryPersonalDaughter() * rate),
                            self.rounding(companyRounding, pensionRateItems.pensionSalaryPersonalUnknown() * rate)),
                        self.pensionModel().childContributionRate() * rate
                    );
                }
                else {
                    return new PensionAvgearnModel(
                        historyId,
                        levelMasterSetting.code,
                        new PensionAvgearnValueModel(Number.Zero, Number.Zero, Number.Zero),
                        new PensionAvgearnValueModel(Number.Zero, Number.Zero, Number.Zero),
                        new PensionAvgearnValueModel(Number.Zero, Number.Zero, Number.Zero),
                        new PensionAvgearnValueModel(Number.Zero, Number.Zero, Number.Zero),
                        new PensionAvgearnValueModel(Number.Zero, Number.Zero, Number.Zero),
                        new PensionAvgearnValueModel(Number.Zero, Number.Zero, Number.Zero),
                        self.pensionModel().childContributionRate() * rate
                    );
                }
            }
            // rounding 
            private rounding(roudingMethod: string, roundValue: number) {
                var self = this;
                var backupValue = roundValue;
                switch (roudingMethod) {
                    case Rounding.ROUNDUP: return Math.ceil(backupValue);
                    case Rounding.TRUNCATION: return Math.floor(backupValue);
                    case Rounding.ROUNDDOWN:
                        if ((backupValue * 10) % 10 > 5)
                            return Math.ceil(backupValue);
                        else
                            return Math.floor(backupValue);
                    case Rounding.DOWN4_UP5: return self.roudingDownUp(backupValue, 4);
                    case Rounding.DOWN5_UP6: return self.roudingDownUp(backupValue, 5);
                }
            }
            private roudingDownUp(value: number, down: number) {
                var newVal = Math.round(value * 10) / 10;
                if ((newVal * 10) % 10 > down)
                    return Math.ceil(value);
                else
                    return Math.floor(value);
            }

            /**
            * Load History detail.
            */
            onSelectHistory(id: string): JQueryPromise<void> {
                var self = this;
                var dfd = $.Deferred<void>();
                self.isLoading(true);
                self.isClickHistory(true);
                self.currentOfficeCode(self.getCurrentOfficeCode(id));
                service.instance.findHistoryByUuid(id).done(dto => {
                    self.loadPension(dto);
                    self.dirty = new nts.uk.ui.DirtyChecker(self.pensionModel);
                    self.isLoading(false);
                    $('.save-error').ntsError('clear');
                    dfd.resolve();
                });
                return dfd.promise();
            }

            onSave(): JQueryPromise<string> {
                var self = this;
                var dfd = $.Deferred<string>();
                if (nts.uk.ui._viewModel.errors.isEmpty()) {
                    self.save();
                }
                else {
                    alert('TODO has error! ERR001');
                    //TODO if has error 
                }
                return dfd.promise();
            }

            /**
              * On select master data.
              */
            onSelectMaster(code: string): void {
                var self = this;
                self.isClickHistory(false);
            }

            /**
             * Clear all input and switch to new mode.
             */
            onRegistNew(): void {
                var self = this;
                //                $('.save-error').ntsError('clear');
                self.OpenModalOfficeRegister();
            }

            isDirty(): boolean {
                var self = this;
                if (self.dirty.isDirty()) {
                    self.dirty.reset();
                    return true;
                }
                else {
                    return false;
                }
            }

            public getCurrentOfficeCode(childId: string): string {
                var self = this;
                var returnValue: string;
                if (self.masterHistoryList.length > 0) {
                    self.masterHistoryList.forEach(function(parentItem) {
                        if (parentItem.historyList) {
                            parentItem.historyList.forEach(function(childItem) {
                                if (childItem.uuid == childId) {
                                    self.sendOfficeData(parentItem.name);
                                    returnValue = parentItem.code;
                                }
                            });
                        } else {
                            return parentItem.code;
                        }
                    });
                }
                return returnValue;
            }

            private OpenModalOfficeRegisterWithDirtyCheck() {
                var self = this;
                if (self.dirty.isDirty()) {
                    nts.uk.ui.dialog.confirm(self.errorList()[4].message).ifYes(function() {
                        self.OpenModalOfficeRegister();
                        self.dirty.reset();
                    }).ifCancel(function() {
                    });
                }
                else {
                    self.OpenModalOfficeRegister();
                }
            }

            //open office register dialog
            private OpenModalOfficeRegister() {
                var self = this;
                // Set parent value
                nts.uk.ui.windows.setShared("isTransistReturnData", this.isTransistReturnData());
                nts.uk.ui.windows.sub.modal("/view/qmm/008/e/index.xhtml", { title: "会社保険事業所の登録＞事業所の登録", dialogClass: 'no-close' }).onClosed(() => {
                    //when close dialog -> reload office list
                    self.loadMasterHistory();
                    var codeOfNewOffice = nts.uk.ui.windows.getShared("codeOfNewOffice");
                    //                    self.igGridSelectedHistoryUuid(codeOfNewOffice);
                });
            }

            public OpenModalStandardMonthlyPricePensionWithDirtyCheck() {
                var self = this;
                if (self.dirty.isDirty()) {
                    nts.uk.ui.dialog.confirm(self.errorList()[4].message).ifYes(function() {
                        self.OpenModalStandardMonthlyPricePension();
                        self.dirty.reset();
                    }).ifCancel(function() {
                    });
                }
                else {
                    self.OpenModalStandardMonthlyPricePension();
                }
            }

            //open modal standard monthly price pension 
            public OpenModalStandardMonthlyPricePension() {
                // Set parent value
                nts.uk.ui.windows.setShared("officeName", this.sendOfficeData());
                nts.uk.ui.windows.setShared("pensionModel", this.pensionModel());

                nts.uk.ui.windows.setShared("isTransistReturnData", this.isTransistReturnData());
                nts.uk.ui.windows.sub.modal("/view/qmm/008/i/index.xhtml", { title: "会社保険事業所の登録＞標準報酬月額保険料額表", dialogClass: 'no-close' }).onClosed(() => {
                    // Get child value
                    var returnValue = nts.uk.ui.windows.getShared("listOfficeOfChildValue");
                });
            }

            //jump back to health
            public goToHealth() {
                nts.uk.request.jump("/view/qmm/008/b/index.xhtml");
            }
        }

        export class PensionRateModel {
            historyId: string;
            companyCode: string;
            officeCode: KnockoutObservable<string>;
            startMonth: KnockoutObservable<string>;
            endMonth: KnockoutObservable<string>;
            fundInputApply: KnockoutObservable<number>;
            autoCalculate: KnockoutObservable<number>;
            rateItems: KnockoutObservable<PensionRateItemModel>;
            fundRateItems: KnockoutObservable<FunRateItemModel>;
            roundingMethods: KnockoutObservable<PensionRateRoundingModel>;
            maxAmount: KnockoutObservable<number>;
            childContributionRate: KnockoutObservable<number>;
            constructor() {
                this.startMonth = ko.observable('');
                this.endMonth = ko.observable('');
                this.officeCode = ko.observable('');
                this.fundInputApply = ko.observable(0);
                this.autoCalculate = ko.observable(0);
                this.rateItems = ko.observable(new PensionRateItemModel());
                this.fundRateItems = ko.observable(new FunRateItemModel());
                this.roundingMethods = ko.observable(new PensionRateRoundingModel());
                this.maxAmount = ko.observable(0);
                this.childContributionRate = ko.observable(0);
            }

        }

        export class PensionRateItemModel {
            pensionSalaryPersonalSon: KnockoutObservable<number>;
            pensionSalaryCompanySon: KnockoutObservable<number>;
            pensionBonusPersonalSon: KnockoutObservable<number>;
            pensionBonusCompanySon: KnockoutObservable<number>;

            pensionSalaryPersonalDaughter: KnockoutObservable<number>;
            pensionSalaryCompanyDaughter: KnockoutObservable<number>;
            pensionBonusPersonalDaughter: KnockoutObservable<number>;
            pensionBonusCompanyDaughter: KnockoutObservable<number>;

            pensionSalaryPersonalUnknown: KnockoutObservable<number>;
            pensionSalaryCompanyUnknown: KnockoutObservable<number>;
            pensionBonusPersonalUnknown: KnockoutObservable<number>;
            pensionBonusCompanyUnknown: KnockoutObservable<number>;
            constructor() {
                this.pensionSalaryPersonalSon = ko.observable(0);
                this.pensionSalaryCompanySon = ko.observable(0);
                this.pensionBonusPersonalSon = ko.observable(0);
                this.pensionBonusCompanySon = ko.observable(0);

                this.pensionSalaryPersonalDaughter = ko.observable(0);
                this.pensionSalaryCompanyDaughter = ko.observable(0);
                this.pensionBonusPersonalDaughter = ko.observable(0);
                this.pensionBonusCompanyDaughter = ko.observable(0);

                this.pensionSalaryPersonalUnknown = ko.observable(0);
                this.pensionSalaryCompanyUnknown = ko.observable(0);
                this.pensionBonusPersonalUnknown = ko.observable(0);
                this.pensionBonusCompanyUnknown = ko.observable(0);
            }
        }

        export class FunRateItemModel {
            salaryPersonalSonExemption: KnockoutObservable<number>;
            salaryCompanySonExemption: KnockoutObservable<number>;
            bonusPersonalSonExemption: KnockoutObservable<number>;
            bonusCompanySonExemption: KnockoutObservable<number>;

            salaryPersonalSonBurden: KnockoutObservable<number>;
            salaryCompanySonBurden: KnockoutObservable<number>;
            bonusPersonalSonBurden: KnockoutObservable<number>;
            bonusCompanySonBurden: KnockoutObservable<number>;

            salaryPersonalDaughterExemption: KnockoutObservable<number>;
            salaryCompanyDaughterExemption: KnockoutObservable<number>;
            bonusPersonalDaughterExemption: KnockoutObservable<number>;
            bonusCompanyDaughterExemption: KnockoutObservable<number>;

            salaryPersonalDaughterBurden: KnockoutObservable<number>;
            salaryCompanyDaughterBurden: KnockoutObservable<number>;
            bonusPersonalDaughterBurden: KnockoutObservable<number>;
            bonusCompanyDaughterBurden: KnockoutObservable<number>;

            salaryPersonalUnknownExemption: KnockoutObservable<number>;
            salaryCompanyUnknownExemption: KnockoutObservable<number>;
            bonusPersonalUnknownExemption: KnockoutObservable<number>;
            bonusCompanyUnknownExemption: KnockoutObservable<number>;

            salaryPersonalUnknownBurden: KnockoutObservable<number>;
            salaryCompanyUnknownBurden: KnockoutObservable<number>;
            bonusPersonalUnknownBurden: KnockoutObservable<number>;
            bonusCompanyUnknownBurden: KnockoutObservable<number>;

            constructor() {
                this.salaryPersonalSonExemption = ko.observable(0);
                this.salaryCompanySonExemption = ko.observable(0);
                this.bonusPersonalSonExemption = ko.observable(0);
                this.bonusCompanySonExemption = ko.observable(0);


                this.salaryPersonalSonBurden = ko.observable(0);
                this.salaryCompanySonBurden = ko.observable(0);
                this.bonusPersonalSonBurden = ko.observable(0);
                this.bonusCompanySonBurden = ko.observable(0);

                this.salaryPersonalDaughterExemption = ko.observable(0);
                this.salaryCompanyDaughterExemption = ko.observable(0);
                this.bonusPersonalDaughterExemption = ko.observable(0);
                this.bonusCompanyDaughterExemption = ko.observable(0);

                this.salaryPersonalDaughterBurden = ko.observable(0);
                this.salaryCompanyDaughterBurden = ko.observable(0);
                this.bonusPersonalDaughterBurden = ko.observable(0);
                this.bonusCompanyDaughterBurden = ko.observable(0);

                this.salaryPersonalUnknownExemption = ko.observable(0);
                this.salaryCompanyUnknownExemption = ko.observable(0);
                this.bonusPersonalUnknownExemption = ko.observable(0);
                this.bonusCompanyUnknownExemption = ko.observable(0);

                this.salaryPersonalUnknownBurden = ko.observable(0);
                this.salaryCompanyUnknownBurden = ko.observable(0);
                this.bonusPersonalUnknownBurden = ko.observable(0);
                this.bonusCompanyUnknownBurden = ko.observable(0);
            }
        }
        export class PensionRateRoundingModel {
            pensionSalaryPersonalComboBox: KnockoutObservableArray<Enum>;
            pensionSalaryPersonalComboBoxItemName: KnockoutObservable<string>;
            pensionSalaryPersonalComboBoxCurrentCode: KnockoutObservable<number>
            pensionSalaryPersonalComboBoxSelectedCode: KnockoutObservable<string>;

            pensionSalaryCompanyComboBox: KnockoutObservableArray<Enum>;
            pensionSalaryCompanyComboBoxItemName: KnockoutObservable<string>;
            pensionSalaryCompanyComboBoxCurrentCode: KnockoutObservable<number>
            pensionSalaryCompanyComboBoxSelectedCode: KnockoutObservable<string>;

            pensionBonusPersonalComboBox: KnockoutObservableArray<Enum>;
            pensionBonusPersonalComboBoxItemName: KnockoutObservable<string>;
            pensionBonusPersonalComboBoxCurrentCode: KnockoutObservable<number>
            pensionBonusPersonalComboBoxSelectedCode: KnockoutObservable<string>;

            pensionBonusCompanyComboBox: KnockoutObservableArray<Enum>;
            pensionBonusCompanyComboBoxItemName: KnockoutObservable<string>;
            pensionBonusCompanyComboBoxCurrentCode: KnockoutObservable<number>
            pensionBonusCompanyComboBoxSelectedCode: KnockoutObservable<string>;
            constructor() {
                this.pensionSalaryPersonalComboBox = ko.observableArray<Enum>(null);
                this.pensionSalaryPersonalComboBoxItemName = ko.observable('');
                this.pensionSalaryPersonalComboBoxCurrentCode = ko.observable(1);
                this.pensionSalaryPersonalComboBoxSelectedCode = ko.observable('');

                this.pensionSalaryCompanyComboBox = ko.observableArray<Enum>(null);
                this.pensionSalaryCompanyComboBoxItemName = ko.observable('');
                this.pensionSalaryCompanyComboBoxCurrentCode = ko.observable(3);
                this.pensionSalaryCompanyComboBoxSelectedCode = ko.observable('002');

                this.pensionBonusPersonalComboBox = ko.observableArray<Enum>(null);
                this.pensionBonusPersonalComboBoxItemName = ko.observable('');
                this.pensionBonusPersonalComboBoxCurrentCode = ko.observable(3);
                this.pensionBonusPersonalComboBoxSelectedCode = ko.observable('002');

                this.pensionBonusCompanyComboBox = ko.observableArray<Enum>(null);
                this.pensionBonusCompanyComboBoxItemName = ko.observable('');
                this.pensionBonusCompanyComboBoxCurrentCode = ko.observable(3);
                this.pensionBonusCompanyComboBoxSelectedCode = ko.observable('002');
            }
        }
    }

    export class HealthInsuranceAvgearn {
        levelCode: KnockoutObservable<number>;
        personalAvg: KnockoutObservable<any>;
        companyAvg: KnockoutObservable<any>;
    }

    export class ChargeRateItem {
        companyRate: KnockoutObservable<number>;
        personalRate: KnockoutObservable<number>;
    }

    export class PensionAvgearnModel {
        historyId: string;
        levelCode: number;
        companyFund: PensionAvgearnValueModel;
        companyFundExemption: PensionAvgearnValueModel;
        companyPension: PensionAvgearnValueModel;
        personalFund: PensionAvgearnValueModel;
        personalFundExemption: PensionAvgearnValueModel;
        personalPension: PensionAvgearnValueModel;
        childContributionAmount: KnockoutObservable<number>;
        constructor(
            historyId: string,
            levelCode: number,
            companyFund: PensionAvgearnValueModel,
            companyFundExemption: PensionAvgearnValueModel,
            companyPension: PensionAvgearnValueModel,
            personalFund: PensionAvgearnValueModel,
            personalFundExemption: PensionAvgearnValueModel,
            personalPension: PensionAvgearnValueModel,
            childContributionAmount: number) {
            this.historyId = historyId;
            this.levelCode = levelCode;
            this.companyFund = companyFund;
            this.companyFundExemption = companyFundExemption;
            this.companyPension = companyPension;
            this.personalFund = personalFund;
            this.personalFundExemption = personalFundExemption;
            this.personalPension = personalPension;
            this.childContributionAmount = ko.observable(childContributionAmount);
        }
    }

    export class PensionAvgearnValueModel {
        maleAmount: KnockoutObservable<number>;
        femaleAmount: KnockoutObservable<number>;
        unknownAmount: KnockoutObservable<number>;
        constructor(maleAmount: number, femaleAmount: number, unknownAmount: number) {
            this.maleAmount = ko.observable(maleAmount);
            this.femaleAmount = ko.observable(femaleAmount);
            this.unknownAmount = ko.observable(unknownAmount);
        }
    }

    export class PaymentType {
        static SALARY = 'Salary';
        static BONUS = 'Bonus'
    }

    export class HealthInsuranceType {
        static GENERAL = 'General';
        static NURSING = 'Nursing';
        static BASIC = 'Basic';
        static SPECIAL = 'Special'
    }
    export class Rounding {
        static ROUNDUP = 'RoundUp';
        static TRUNCATION = 'Truncation';
        static ROUNDDOWN = 'RoundDown';
        static DOWN5_UP6 = 'Down5_Up6';
        static DOWN4_UP5 = 'Down4_Up5'
    }
    export class InsuranceGender {
        static MALE = "Male";
        static FEMALE = "Female";
        static UNKNOW = "Unknow";
    }
    export class AutoCalculate {
        static AUTO = "Auto";
        static MANUAL = "Manual";
    }
    export enum Number {
        Zero = 0,
        One = 1,
        Three = 3
    }
    export enum AutoCalculateType {
        Auto = 0,
        Manual = 1
    }
}
