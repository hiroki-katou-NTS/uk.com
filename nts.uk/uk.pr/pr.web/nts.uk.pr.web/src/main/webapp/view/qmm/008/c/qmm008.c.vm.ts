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
        selectedHistoryId: string = "";

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
                    pensionItem.history.push({ historyID: '00000' + j, start: '20180' + (j + 5), end: '20180' + (j + 6) });
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
                maleInsuPremium: 33.33,
                femaleInsuPremium: 44.44,
                maleExemptionInsu: 55.55,
                femaleExemptionInsu: 66.66
            }
            let insuContributionFee: model.IContributionFee = {
                maleInsuPremium: 33.33,
                femaleInsuPremium: 44.44,
                maleExemptionInsu: 55.55,
                femaleExemptionInsu: 66.66
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
                historyID: '000001',
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
                historyID: '000001'
            }
            this.bonusEmployeePensionInsuranceRate(new model.BonusEmployeePensionInsuranceRate(bonusEmployeePensionInsuranceRate));
        }

        initWelfareInsuranceCls() {
            let self = this;
            self.welfareInsuranceClassification(new model.WelfarePensionInsuranceClassification({ fundClassification: 0, historyID: '0005' }));
        }


        watchDataChanged() {
            let self = this;
            self.selectedWelfareInsurance.subscribe(function(selectedValue: any) {
                self.showByHistory();
                if (selectedValue.contains('history')) {
                    self.isSelectedHistory(true);
                } else {
                    self.isSelectedHistory(false);
                    self.welfareInsuranceRateHistory(null);
                }
            });
        }

        showByHistory() {
            let self = this,
                selectedInsurenceCode = self.selectedWelfareInsurance().split('history')[0],
                selectedHistoryID = self.selectedWelfareInsurance().split('history')[1],
                selectedHistoryPeriod = null,
                listInsuranceOffice = ko.toJS(self.socialInsuranceOfficeList);
            self.selectedHistoryId = selectedHistoryID;
            self.selectedOffice = listInsuranceOffice.find(insuranceOffice => insuranceOffice.code == selectedInsurenceCode);
            if (selectedHistoryID) {
                let selectedHistoryPeriod = null;
                if (self.selectedOffice) {
                    selectedHistoryPeriod = self.selectedOffice.welfareInsuranceRateHistory.history.find(historyItem =>
                    { return historyItem.historyID === selectedHistoryID; }
                    );
                }
                selectedHistoryPeriod.displayStart = selectedHistoryPeriod.start.substring(0, 4) + "/" + selectedHistoryPeriod.start.substring(4, 6);
                selectedHistoryPeriod.displayEnd = selectedHistoryPeriod.end.substring(0, 4) + "/" + selectedHistoryPeriod.end.substring(4, 6);
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
                maleInsuPremium: null,
                femaleInsuPremium: null,
                maleExemptionInsu: null,
                femaleExemptionInsu: null
            }
            let insuContributionFee: model.IContributionFee = {
                maleInsuPremium: null,
                femaleInsuPremium: null,
                maleExemptionInsu: null,
                femaleExemptionInsu: null
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
                historyID: '000999',
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
                historyID: '000999'
            }
            this.bonusEmployeePensionInsuranceRate(new model.BonusEmployeePensionInsuranceRate(bonusEmployeePensionInsuranceRate));
        }

        initBlankWelfareInsuranceCls() {
            let self = this;
            self.welfareInsuranceClassification(new model.WelfarePensionInsuranceClassification({ fundClassification: 0, historyID: '0005' }));
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
                        pensionItem.child.push(new model.TreeGridNode(office.code + 'history' + history.historyID, displayStart + ' ~ ' + displayEnd, []));
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
        
        selectLastestHistory(){
            let self = this, selectedOffice = self.selectedOffice;
            ko.toJS(self.welfareInsuranceRateTreeList).forEach(treeGridItem => {
                if (treeGridItem.code == selectedOffice.code){
                    if (treeGridItem.child.length == 1){
                        self.selectedWelfareInsurance(selectedOffice.code);
                    } else {
                        self.selectedWelfareInsurance(treeGridItem.child[treeGridItem.child.length-1].code);
                    }
                }    
            });
        }

        createNewHistory() {
            let self = this;
            let selectedOffice = self.selectedOffice;
            setShared("QMM008_G_PARAMS", { selectedOffice: selectedOffice });
            modal("/view/qmm/008/g/index.xhtml").onClosed(() => {
                let params = getShared("QMM008_G_RES_PARAMS");
                if (params) {
                    let socialInsuranceOfficeList = ko.toJS(self.socialInsuranceOfficeList);
                    socialInsuranceOfficeList.forEach(office => {
                        if (office.code == selectedOffice.code) {
                            let history = office.welfareInsuranceRateHistory.history;
                            if (history.length > 0) {
                                let beforeLastestDate = moment(params.startDate, 'YYYYMM').subtract(1, 'month');
                                history[history.length - 1].end = beforeLastestDate.format('YYYYMM');
                            }
                            history.push({ historyID: nts.uk.util.randomId(), start: params.startDate, end: '999912' });
                            office.welfareInsuranceRateHistory.history = history;
                            office = new model.SocialInsuranceOffice(office);
                        }
                    });
                    self.socialInsuranceOfficeList(socialInsuranceOfficeList);
                    self.convertToTreeGridList();
                    if (params.takeoverMethod == model.TAKEOVER_METHOD.FROM_BEGINNING) {
                        self.initBlankData();
                    } else {
                        self.initExistedData();
                    }
                    self.selectLastestHistory();
                }
            });
        }

        editHistory() {
            let self = this;
            let selectedOffice = self.selectedOffice;
            let selectedHistory = ko.toJS(self.selectedHistoryPeriod);
            setShared("QMM008_H_PARAMS", { selectedOffice: self.selectedOffice, selectedHistory: selectedHistory });
            modal("/view/qmm/008/h/index.xhtml").onClosed(() => {
                let params = getShared("QMM008_H_RES_PARAMS");
                if (params) {
                    let socialInsuranceOfficeList = ko.toJS(self.socialInsuranceOfficeList);
                    socialInsuranceOfficeList.forEach(office => {
                        if (office.code == selectedOffice.code) {
                            let history = office.welfareInsuranceRateHistory.history;
                            if (history.length > 0) {
                                if (params.modifyMethod == model.MOFIDY_METHOD.UPDATE) {
                                    history.forEach((historyItem, index) => {
                                        if (selectedHistory.historyID == historyItem.historyID) {
                                            let currentPreviousDate = moment(params.startDate, 'YYYYMM').subtract(1, 'month');
                                            historyItem.start = params.startDate;
                                            if (index > 0) history[index - 1].end = currentPreviousDate.format('YYYYMM');
                                        }
                                    });
                                } else {
                                    if (history.length > 1) history[history.length-2].end = '999912';
                                    history.pop();
                                }
                                
                            }
                            office.welfareInsuranceRateHistory.history = history;
                            office = new model.SocialInsuranceOffice(office);
                        }
                    });
                    self.socialInsuranceOfficeList(socialInsuranceOfficeList);
                    if (params.takeoverMethod == model.TAKEOVER_METHOD.FROM_BEGINNING) {
                        self.initBlankData();
                    } else {
                        self.initDataByLastestHistory();
                    }
                    self.convertToTreeGridList();
                    self.selectLastestHistory();
                }
            });
        }
        changeMode() {
            let self = this;
            self.isUpdateMode(self.isUpdateMode() ? false : true);
        }
    }
}

