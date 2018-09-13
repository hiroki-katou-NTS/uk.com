module nts.uk.com.view.qmm008.c.viewmodel {
    import getShared = nts.uk.ui.windows.getShared;
    import setShared = nts.uk.ui.windows.setShared;
    import dialog = nts.uk.ui.dialog;
    import getText = nts.uk.resource.getText;
    import modal = nts.uk.ui.windows.sub.modal;
    import block = nts.uk.ui.block;
    import model = nts.uk.com.view.qmm008.share.model;
    export class ScreenModel {

        isUpdateMode: KnockoutObservable<boolean> = ko.observable(true);
        // History Tree Grid C1_1 -> C1_12 
        socialInsuranceOfficeList: KnockoutObservableArray<model.SocialInsuranceOffice> = ko.observableArray([]);
        isSelectedHistory: KnockoutObservable<boolean> = ko.observable(false);
        welfareInsuranceRateTreeList: KnockoutObservableArray<model.TreeGridNode> = ko.observableArray([]);
        selectedWelfareInsurance: KnockoutObservable<string> = ko.observable(null);
        // Office and history info
        selectedOffice: any = null;
        selectedhistoryId: string = "";

        // Welfare Item
        selectedHistoryPeriod: KnockoutObservable<model.GenericHistoryYearMonthPeiod> = ko.observable({ displayStart: '', displayEnd: '' });
        welfareInsuranceRateHistory: KnockoutObservable<model.WelfarePensionInsuranceRateHistory> = ko.observable({});
        employeeMonthlyInsuFee: KnockoutObservable<model.EmployeePensionMonthlyInsuFee> = ko.observable(null);
        welfareInsuranceClassification: KnockoutObservable<model.WelfarePensionInsuranceClassification> = ko.observable(null);
        bonusEmployeePensionInsuranceRate: KnockoutObservable<model.BonusEmployeePensionInsuranceRate> = ko.observable(null);


        constructor() {
            let self = this;
            $("#C3").ntsFixedTable({});
            $("#C4").ntsFixedTable({});
            $("#C5").ntsFixedTable({});
            let socailInsuranceOfficeList: Array<model.SocialInsuranceOffice> = [];
            for (let i = 0; i < 5; i++) {
                let pensionItem: model.IWelfarePensionInsuranceRateHistory = { socialInsuranceCode: "0001" + i, companyID: 'Name' + i, history: [] };
                for (let j = 0; j < i; j++) {
                    pensionItem.history.push({ historyId: nts.uk.util.randomId(), start: '20180' + (j + 5), end: '20180' + (j + 6) });
                }
                let officeItem: model.ISocialInsuranceOffice = {
                    code: '000' + i,
                    name: 'Workplace' + i,
                    basicInfomation: null,
                    insuranceMasterInfomation: null,
                    companyID: '000000000000-0001',
                    welfareInsuranceRateHistory: pensionItem
                }
                socailInsuranceOfficeList.push(new model.SocialInsuranceOffice(officeItem));
            }
            self.socialInsuranceOfficeList(socailInsuranceOfficeList);
            self.convertToTreeGridList();
            self.watchDataChanged();
            self.initExistedData();
        }
        initExistedData() {
            let self = this;
            self.initWelfareInsuranceCls();
            self.initEmployeeMonthlyInsuFee();
            self.initBonusPensionInsuranceRate();
        }
        initEmployeeMonthlyInsuFee() {
            let self = this;
            let empContributionFee: model.IContributionFee = {
                maleInsurancePremium: 33.33,
                femaleInsurancePremium: 44.44,
                maleExemptionInsurance: 55.55,
                femaleExemptionInsurance: 66.66
            }
            let insuContributionFee: model.IContributionFee = {
                maleInsurancePremium: 33.33,
                femaleInsurancePremium: 44.44,
                maleExemptionInsurance: 55.55,
                femaleExemptionInsurance: 66.66
            }
            let gradeWelfareInsuPremium: model.IGradeWelfarePensionInsurancePremium = {
                welfarePensionGrade: 1,
                employeeBurden: empContributionFee,
                insuredBurden: insuContributionFee

            }
            let employeePensionClassification: model.IEmployeePensionClassification = {
                personalFraction: 0,
                businessOwnerFraction: 1
            };
            let maleContributionRate: model.IEmployeePensionContributionRate = {
                individualBurdenRatio: 40,
                employeeContributionRatio: 50,
                individualExcemtionRate: 60,
                employeeExcemtionRate: 70
            }
            let femaleContributionRate: model.IEmployeePensionContributionRate = {
                individualBurdenRatio: 45,
                employeeContributionRatio: 55,
                individualExcemtionRate: 65,
                employeeExcemtionRate: 76
            }
            let salaryEmployeePensionInsurate: model.ISalaryEmployeePensionInsuRate = {
                employeeShareAmountMethod: 0,
                femaleContributionRate: femaleContributionRate,
                maleContributionRate: maleContributionRate,
                fractionClassification: employeePensionClassification
            }

            self.employeeMonthlyInsuFee(new model.EmployeePensionMonthlyInsuFee({
                autoCalculation: 0,
                pensionInsurancePremium: gradeWelfareInsuPremium,
                historyId: nts.uk.util.randomId(),
                salaryEmployeePensionInsuranceRate: salaryEmployeePensionInsurate
            }));
        }

        initBonusPensionInsuranceRate() {
            let self = this;
            let maleContributionRate: model.IEmployeePensionContributionRate = {
                individualBurdenRatio: 400,
                employeeContributionRatio: 500,
                individualExcemtionRate: 600,
                employeeExcemtionRate: 700
            }
            let femaleContributionRate: model.IEmployeePensionContributionRate = {
                individualBurdenRatio: 450,
                employeeContributionRatio: 550,
                individualExcemtionRate: 650,
                employeeExcemtionRate: 760
            }
            let employeePensionClassification: model.IEmployeePensionClassification = {
                personalFraction: 2,
                businessOwnerFraction: 3
            };
            let bonusEmployeePensionInsuranceRate: model.IBonusEmployeePensionInsuranceRate = {
                employeeShareAmountMethod: 0,
                fractionClassification: employeePensionClassification,
                femaleContributionRate: femaleContributionRate,
                maleContributionRate: maleContributionRate,
                historyId: nts.uk.util.randomId()
            }
            this.bonusEmployeePensionInsuranceRate(new model.BonusEmployeePensionInsuranceRate(bonusEmployeePensionInsuranceRate));
        }

        initWelfareInsuranceCls() {
            let self = this;
            self.welfareInsuranceClassification(new model.WelfarePensionInsuranceClassification({ fundClassification: 0, historyId: '0005' }));
        }


        watchDataChanged() {
            let self = this;
            self.selectedWelfareInsurance.subscribe(function(selectedValue: any) {
                if (selectedValue) {
                    self.showByHistory();
                    if (selectedValue.length >= 36) {
                        self.isSelectedHistory(true);
                    } else {
                        self.isSelectedHistory(false);
                        self.welfareInsuranceRateHistory(null);
                    }
                }
            });
        }

        showByHistory() {

            let self = this,
                selectedInsurenceCode = self.selectedWelfareInsurance().split('___')[0],
                selectedhistoryId = self.selectedWelfareInsurance().split('___')[1],
                selectedHistoryPeriod = null,
                listInsuranceOffice = ko.toJS(self.socialInsuranceOfficeList);
            self.selectedhistoryId = selectedhistoryId;
            listInsuranceOffice.forEach((office, index) => {

            });
            self.selectedOffice = listInsuranceOffice.find(insuranceOffice => insuranceOffice.code == selectedInsurenceCode);
            if (selectedhistoryId) {
                let selectedHistoryPeriod;
                if (self.selectedOffice) {
                    selectedHistoryPeriod = self.selectedOffice.welfareInsuranceRateHistory.history.find(historyItem =>
                    { return historyItem.historyId === selectedhistoryId; }
                    );
                }
                if (selectedHistoryPeriod) {
                    selectedHistoryPeriod.displayStart = selectedHistoryPeriod.start.substring(0, 4) + "/" + selectedHistoryPeriod.start.substring(4, 6);
                    selectedHistoryPeriod.displayEnd = selectedHistoryPeriod.end.substring(0, 4) + "/" + selectedHistoryPeriod.end.substring(4, 6);
                }
                self.selectedHistoryPeriod(selectedHistoryPeriod);
            }
        }
        initDataByLastestHistory() {
            let self = this;
        }
        initBlankData() {
            let self = this;
            self.initBlankEmployeeMonthlyInsuFee();
            self.initBlankBonusPensionInsuranceRate();
            self.initBlankWelfareInsuranceCls();
        }
        initBlankEmployeeMonthlyInsuFee() {
            let self = this;
            let empContributionFee: model.IContributionFee = {
                maleInsurancePremium: null,
                femaleInsurancePremium: null,
                maleExemptionInsurance: null,
                femaleExemptionInsurance: null
            }
            let insuContributionFee: model.IContributionFee = {
                maleInsurancePremium: null,
                femaleInsurancePremium: null,
                maleExemptionInsurance: null,
                femaleExemptionInsurance: null
            }
            let gradeWelfareInsuPremium: model.IGradeWelfarePensionInsurancePremium = {
                welfarePensionGrade: 1,
                employeeBurden: empContributionFee,
                insuredBurden: insuContributionFee

            }
            let employeePensionClassification: model.IEmployeePensionClassification = {
                personalFraction: null,
                businessOwnerFraction: null
            };
            let maleContributionRate: model.IEmployeePensionContributionRate = {
                individualBurdenRatio: null,
                employeeContributionRatio: null,
                individualExcemtionRate: null,
                employeeExcemtionRate: null
            }
            let femaleContributionRate: model.IEmployeePensionContributionRate = {
                individualBurdenRatio: null,
                employeeContributionRatio: null,
                individualExcemtionRate: null,
                employeeExcemtionRate: null
            }
            let salaryEmployeePensionInsurate: model.ISalaryEmployeePensionInsuRate = {
                employeeShareAmountMethod: 0,
                femaleContributionRate: femaleContributionRate,
                maleContributionRate: maleContributionRate,
                fractionClassification: employeePensionClassification
            }

            self.employeeMonthlyInsuFee(new model.EmployeePensionMonthlyInsuFee({
                autoCalculation: 0,
                pensionInsurancePremium: gradeWelfareInsuPremium,
                historyId: nts.uk.util.randomId(),
                salaryEmployeePensionInsuranceRate: salaryEmployeePensionInsurate
            }));
        }

        initBlankBonusPensionInsuranceRate() {
            let self = this;
            let maleContributionRate: model.IEmployeePensionContributionRate = {
                individualBurdenRatio: null,
                employeeContributionRatio: null,
                individualExcemtionRate: null,
                employeeExcemtionRate: null
            }
            let femaleContributionRate: model.IEmployeePensionContributionRate = {
                individualBurdenRatio: null,
                employeeContributionRatio: null,
                individualExcemtionRate: null,
                employeeExcemtionRate: null
            }
            let employeePensionClassification: model.IEmployeePensionClassification = {
                personalFraction: null,
                businessOwnerFraction: null
            };
            let bonusEmployeePensionInsuranceRate: model.IBonusEmployeePensionInsuranceRate = {
                employeeShareAmountMethod: 0,
                fractionClassification: employeePensionClassification,
                femaleContributionRate: femaleContributionRate,
                maleContributionRate: maleContributionRate,
                historyId: nts.uk.util.randomId()
            }
            this.bonusEmployeePensionInsuranceRate(new model.BonusEmployeePensionInsuranceRate(bonusEmployeePensionInsuranceRate));
        }

        initBlankWelfareInsuranceCls() {
            let self = this;
            self.welfareInsuranceClassification(new model.WelfarePensionInsuranceClassification({ fundClassification: 0, historyId: '0005' }));
        }
        convertToTreeGridList() {
            let self = this,
                pensionList = ko.toJS(this.socialInsuranceOfficeList),
                displayPensionList: Array<model.TreeGridNode> = [],
                pensionItem = {};
            pensionList.forEach(function(office) {
                let pensionItem = new model.TreeGridNode(office.code, office.code + ' ' + office.name, []);
                if (office.welfareInsuranceRateHistory) {
                    let displayStart, displayEnd = "";
                    office.welfareInsuranceRateHistory.history.forEach(function(history) {
                        displayStart = history.start.substring(0, 4) + "/" + history.start.substring(4, 6);
                        displayEnd = history.end.substring(0, 4) + "/" + history.end.substring(4, 6);
                        // ___ is for child contain office code and history id
                        pensionItem.child.push(new model.TreeGridNode(office.code + '___' + history.historyId, displayStart + ' ~ ' + displayEnd, []));
                    });
                    displayPensionList.push(pensionItem);
                }

            });
            self.welfareInsuranceRateTreeList(displayPensionList);
        }

        convertYMPeriodToDisplayYMPeriod() {

        }

        register() {
            console.log('register');
        }

        printPDF() {
            console.log('printPDF');
        }

        registerBusinessEstablishment() {
            console.log('registerBusinessEstablishment');
        }

        standardRemunerationMonthlyAmount() {
            console.log('standardRemunerationMonthlyAmount');
        }

        masterCorrectionLog() {
            console.log('masterCorrectionLog');
        }

        createNewHistory() {
            let self = this;
            let selectedOffice = self.selectedOffice;
            setShared("QMM008_G_PARAMS", { selectedOffice: selectedOffice });
            modal("/view/qmm/008/g/index.xhtml").onClosed(() => {
                $("#C2_7").focus();
                let params = getShared("QMM008_G_RES_PARAMS");
                if (params) {
                    let socialInsuranceOfficeList = ko.toJS(self.socialInsuranceOfficeList);
                    let historyId = nts.uk.util.randomId();
                    socialInsuranceOfficeList.forEach(office => {
                        if (office.code == selectedOffice.code) {
                            let history = office.welfareInsuranceRateHistory.history;
                            if (history.length > 0) {
                                let beforeLastestDate = moment(params.startDate, 'YYYYMM').subtract(1, 'month');
                                history[history.length - 1].end = beforeLastestDate.format('YYYYMM');
                            }
                            history.push({ historyId: historyId, start: params.startDate, end: '999912' });
                            office.welfareInsuranceRateHistory.history = history;
                            office = new model.SocialInsuranceOffice(office);
                        }
                    });
                    self.socialInsuranceOfficeList(socialInsuranceOfficeList);
                    self.convertToTreeGridList();
                    self.selectedWelfareInsurance(selectedOffice.code + "___" + historyId);
                    if (params.takeoverMethod == model.TAKEOVER_METHOD.FROM_BEGINNING) {
                        self.initBlankData();
                    } else {
                        self.initExistedData();
                    }
                    self.isUpdateMode(false);
                }
            });
        }

        editHistory() {
            let self = this;
            let selectedOffice = self.selectedOffice, selectedhistoryId = self.selectedhistoryId;
            let selectedHistory = ko.toJS(self.selectedHistoryPeriod);
            setShared("QMM008_H_PARAMS", { selectedOffice: self.selectedOffice, selectedHistory: selectedHistory });
            modal("/view/qmm/008/h/index.xhtml").onClosed(() => {
                $("#C1_5").focus();
                let params = getShared("QMM008_H_RES_PARAMS");
                if (params) {
                    let selectedCode = self.selectedWelfareInsurance();
                    self.selectedWelfareInsurance(null);
                    let socialInsuranceOfficeList = ko.toJS(self.socialInsuranceOfficeList);
                    socialInsuranceOfficeList.forEach(office => {
                        if (office.code == selectedOffice.code) {
                            let history = office.welfareInsuranceRateHistory.history;
                            if (history.length > 0) {
                                if (params.modifyMethod == model.MOFIDY_METHOD.UPDATE) {
                                    history.forEach((historyItem, index) => {
                                        if (selectedHistory.historyId == historyItem.historyId) {
                                            let currentPreviousDate = moment(params.startDate, 'YYYYMM').subtract(1, 'month');
                                            historyItem.start = params.startDate;
                                            if (index > 0) history[index - 1].end = currentPreviousDate.format('YYYYMM');
                                        }
                                    });
                                } else {
                                    history.pop();
                                    if (history.length > 0) {
                                        history[history.length - 1].end = '999912';
                                        selectedCode = office.code + "___" + history[history.length - 1].historyId;
                                    } else {
                                        selectedCode = office.code;
                                    }
                                }

                            }
                            office.welfareInsuranceRateHistory.history = history;
                            office = new model.SocialInsuranceOffice(office);
                        }
                    });
                    self.socialInsuranceOfficeList(socialInsuranceOfficeList);
                    self.convertToTreeGridList();
                    self.selectedWelfareInsurance(selectedCode);
                    if (params.takeoverMethod == model.TAKEOVER_METHOD.FROM_BEGINNING) {
                        self.initBlankData();
                    } else {
                        self.initDataByLastestHistory();
                    }
                }
            });
        }
        changeMode() {
            let self = this;
            self.isUpdateMode(self.isUpdateMode() ? false : true);
            self.convertToTreeGridList();
        }
    }
}

