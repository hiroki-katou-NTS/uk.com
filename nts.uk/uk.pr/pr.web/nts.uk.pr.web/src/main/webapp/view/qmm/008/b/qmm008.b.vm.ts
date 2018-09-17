module nts.uk.pr.view.qmm008.b.viewmodel {
    import getShared = nts.uk.ui.windows.getShared;
    import setShared = nts.uk.ui.windows.setShared;
    import dialog = nts.uk.ui.dialog;
    import getText = nts.uk.resource.getText;
    import modal = nts.uk.ui.windows.sub.modal;
    import block = nts.uk.ui.block;
    import model = nts.uk.pr.view.qmm008.share.model;
    import service = nts.uk.pr.view.qmm008.b.service;
    export class ScreenModel {
        isUpdateMode: KnockoutObservable<boolean> = ko.observable(true);
        // History Tree Grid C1_1 -> C1_12 
        socialInsuranceOfficeList: KnockoutObservableArray<model.SocialInsuranceOffice> = ko.observableArray([]);
        isSelectedHistory: KnockoutObservable<boolean> = ko.observable(false);
        healthInsuranceRateTreeList: KnockoutObservableArray<model.TreeGridNode> = ko.observableArray([]);
        // Office and history info
        selectedOffice: any = null;
        selectedHistoryId: string = "";
        selectedHealthInsurance: KnockoutObservable<number> = ko.observable(null);
        
        // Health insurance item
        selectedHistoryPeriod: KnockoutObservable<model.GenericHistoryYearMonthPeiod> = ko.observable({ displayStart: '', displayEnd: '' });
        bonusHealthInsuranceRate: KnockoutObservable<model.BonusHealthInsuranceRate> = ko.observable(null);
        healthInsuranceMonthlyFee: KnockoutObservable<model.HealthInsuranceMonthlyFee> = ko.observable(null);
        constructor() {
            let self = this;
            let params = getShared("QMM008_G_PARAMS");
            self.watchDataChanged();
            self.initBlankData();
        }
        
        initBlankData () {
            let self = this;
            self.bonusHealthInsuranceRate(new model.BonusHealthInsuranceRate(null)); 
            self.healthInsuranceMonthlyFee(new model.HealthInsuranceMonthlyFee(null));
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
        startPage(): JQueryPromise<any> {
            let self = this, dfd = $.Deferred();
            block.invisible();
            service.findAllOffice().done(function(data) {
                if (data) {
                    if (data.length == 0) {
                        self.registerBusinessEstablishment();
                        return;
                    }
                    let socailInsuranceOfficeList: Array<model.SocialInsuranceOffice> = [];
                    data.forEach(office => {
                        socailInsuranceOfficeList.push(new model.SocialInsuranceOffice(office));
                    });
                    self.socialInsuranceOfficeList(socailInsuranceOfficeList);
                    self.convertToTreeGridList();
                }
                block.clear();
                dfd.resolve();
            }).fail(function(err) {
                dfd.reject();
                block.clear();
                dialog.alertError(err.message);
            });
            return dfd.promise();
        }
        
        watchDataChanged() {
            let self = this;
            self.selectedHealthInsurance.subscribe(function(selectedValue: any) {
                if (selectedValue) {
                    self.showByHistory();
                    if (selectedValue.length >= 36) {
                        self.isSelectedHistory(true);
                    } else {
                        self.isSelectedHistory(true);
                        self.initBlankData();
                    }
                }
            });
        }

        showByHistory() {

            let self = this,
                selectedInsuranceCode = self.selectedHealthInsurance().split('___')[0],
                selectedHistoryId = self.selectedHealthInsurance().split('___')[1],
                selectedHistoryPeriod = null,
                listInsuranceOffice = ko.toJS(self.socialInsuranceOfficeList);
            self.selectedHistoryId = selectedHistoryId;
            self.selectedOffice = _.find(listInsuranceOffice, { socialInsuranceCode: selectedInsuranceCode });
            if (selectedHistoryId) {
                let selectedHistoryPeriod;
                if (self.selectedOffice) {
                    selectedHistoryPeriod = _.find(self.selectedOffice.healthInsuranceFeeHistory.history, { historyId: selectedHistoryId });
                }
                if (selectedHistoryPeriod) {
                    selectedHistoryPeriod.displayStart = self.convertYearMonthToDisplayYearMonth(selectedHistoryPeriod.startMonth);
                    selectedHistoryPeriod.displayEnd = self.convertYearMonthToDisplayYearMonth(selectedHistoryPeriod.endMonth);
                    self.selectedHistoryPeriod(selectedHistoryPeriod);
                }
            }
        }
        
        convertToTreeGridList() {
            let self = this,
                pensionList = ko.toJS(self.socialInsuranceOfficeList),
                displayPensionList: Array<model.TreeGridNode> = [],
                pensionItem = {};
            pensionList.forEach(function(office) {
                let pensionItem = new model.TreeGridNode(office.socialInsuranceCode, office.socialInsuranceCode + ' ' + office.socialInsuranceName, []);
                if (office.healthInsuranceFeeHistory) {
                    let displayStart, displayEnd = "";
                    office.healthInsuranceFeeHistory.history.forEach(function(history) {
                        displayStart = self.convertYearMonthToDisplayYearMonth(history.startMonth);
                        displayEnd = self.convertYearMonthToDisplayYearMonth(history.endMonth);
                        // ___ is for child contain office code and history id
                        pensionItem.child.push(new model.TreeGridNode(office.socialInsuranceCode + '___' + history.historyId, displayStart + ' ~ ' + displayEnd, []));
                    });
                }
                displayPensionList.push(pensionItem);
            });
            self.healthInsuranceRateTreeList(displayPensionList);
        }
        
        convertYearMonthToDisplayYearMonth (yearMonth) {
            return String(yearMonth).substring(0, 4) + "/" + String(yearMonth).substring(4, 6);
        }
        
        createNewHistory() {
            let self = this;
            let selectedOffice = self.selectedOffice;
            setShared("QMM008_G_PARAMS", { selectedOffice: selectedOffice, history: selectedOffice.healthInsuranceFeeHistory.history  });
            modal("/view/qmm/008/g/index.xhtml").onClosed(() => {
                let params = getShared("QMM008_G_RES_PARAMS");
                if (params) {
                    let socialInsuranceOfficeList = ko.toJS(self.socialInsuranceOfficeList);
                    let historyId = nts.uk.util.randomId();
                    socialInsuranceOfficeList.forEach(office => {
                        if (office.socialInsuranceCode == selectedOffice.socialInsuranceCode) {
                            let history = office.healthInsuranceFeeHistory.history;
                            if (history.length > 0) {
                                let beforeLastestMonth = moment(params.startMonth, 'YYYYMM').subtract(1, 'month');
                                history[history.length - 1].endMonth = beforeLastestMonth.format('YYYYMM');
                            }
                            history.push({ historyId: historyId, startMonth: params.startMonth, endMonth: '999912' });
                            office.healthInsuranceFeeHistory.history = history;
                            office = new model.SocialInsuranceOffice(office);
                        }
                    });
                    self.socialInsuranceOfficeList(socialInsuranceOfficeList);
                    self.convertToTreeGridList();
                    self.selectedHealthInsurance(selectedOffice.socialInsuranceCode + "___" + historyId);
                    if (params.takeoverMethod == model.TAKEOVER_METHOD.FROM_BEGINNING) {
                        
                    } else {
                        
                    }
                    self.isUpdateMode(true);
                }
                $("#B2_7").focus();
            });
        }

        editHistory() {
            let self = this;
            let selectedOffice = self.selectedOffice, selectedHistoryId = self.selectedHistoryId;
            let selectedHistory = ko.toJS(self.selectedHistoryPeriod);
            setShared("QMM008_H_PARAMS", { selectedOffice: self.selectedOffice, selectedHistory: selectedHistory, history: selectedOffice.healthInsuranceFeeHistory.history });
            modal("/view/qmm/008/h/index.xhtml").onClosed(() => {
                $("#B1_5").focus();
                let params = getShared("QMM008_H_RES_PARAMS");
                if (params) {
                    let selectedCode = self.selectedHealthInsurance();
                    self.selectedHealthInsurance(null);
                    let socialInsuranceOfficeList = ko.toJS(self.socialInsuranceOfficeList);
                    socialInsuranceOfficeList.forEach(office => {
                        if (office.socialInsuranceCode == selectedOffice.socialInsuranceCode) {
                            let history = office.healthInsuranceFeeHistory.history;
                            if (history.length > 0) {
                                if (params.modifyMethod == model.MOFIDY_METHOD.UPDATE) {
                                    history.forEach((historyItem, index) => {
                                        if (selectedHistory.historyId == historyItem.historyId) {
                                            let currentPreviousMonth = moment(params.startMonth, 'YYYYMM').subtract(1, 'month');
                                            historyItem.startMonth = params.startMonth;
                                            if (index > 0) history[index - 1].endMonth = currentPreviousMonth.format('YYYYMM');
                                        }
                                    });
                                } else {
                                    history.pop();
                                    if (history.length > 0) {
                                        history[history.length - 1].endMonth = '999912';
                                        selectedCode = office.socialInsuranceCode + "___" + history[history.length - 1].historyId;
                                    } else {
                                        selectedCode = office.socialInsuranceCode;
                                    }
                                }

                            }
                            office.healthInsuranceFeeHistory.history = history;
                            office = new model.SocialInsuranceOffice(office);
                        }
                    });
                    self.socialInsuranceOfficeList(socialInsuranceOfficeList);
                    self.convertToTreeGridList();
                    self.selectedHealthInsurance(selectedCode);
                    if (params.takeoverMethod == model.TAKEOVER_METHOD.FROM_BEGINNING) {
                        
                    } else {
                        
                    }
                }
            });
        }
        
        changeMode() {
            let self = this;
            self.isUpdateMode(self.isUpdateMode() ? false : true);
        }
    }
}

