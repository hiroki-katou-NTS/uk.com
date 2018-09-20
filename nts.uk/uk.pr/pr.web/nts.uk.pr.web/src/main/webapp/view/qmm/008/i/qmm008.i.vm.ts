module nts.uk.pr.view.qmm008.i.viewmodel {
    import getShared = nts.uk.ui.windows.getShared;
    import setShared = nts.uk.ui.windows.setShared;
    import dialog = nts.uk.ui.dialog;
    import getText = nts.uk.resource.getText;
    import modal = nts.uk.ui.windows.sub.modal;
    import block = nts.uk.ui.block;
    import model = nts.uk.pr.view.qmm008.share.model;
    import service = nts.uk.pr.view.qmm008.i.service;
    export class ScreenModel {
        isUpdateMode: KnockoutObservable<boolean> = ko.observable(true);
        isOnStartUp: boolean = true;
        // History Tree Grid C1_1 -> C1_12 
        socialInsuranceOfficeList: KnockoutObservableArray<model.SocialInsuranceOffice> = ko.observableArray([]);
        isSelectedHistory: KnockoutObservable<boolean> = ko.observable(false);
        contributionRateTreeList: KnockoutObservableArray<model.TreeGridNode> = ko.observableArray([]);
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
            $("#B3").ntsFixedTable({});
            $("#B4").ntsFixedTable({});
            self.watchDataChanged();
            self.initBlankData();

            self.bonusHealthInsuranceRate().individualBurdenRatio().healthInsuranceRate.subscribe(item => {
                console.log(item);
                console.log(ko.toJS(self.bonusHealthInsuranceRate));
            });


            self.healthInsuranceMonthlyFee().healthInsuranceRate().individualBurdenRatio().healthInsuranceRate.subscribe(item => {
                console.log(item);
                console.log(ko.toJS(self.healthInsuranceMonthlyFee));
            });
        }

        initBlankData() {
            let self = this;
            self.bonusHealthInsuranceRate(new model.BonusHealthInsuranceRate(null));
            self.healthInsuranceMonthlyFee(new model.HealthInsuranceMonthlyFee(null));
        }

        register() {
            let self = this;
            nts.uk.ui.errors.clearAll();
            $('.nts-input').trigger("validate");
            if (nts.uk.ui.errors.hasError()) {
                return;
            }
            block.invisible();
            // Register data
            let command = {
                officeCode: self.selectedOffice.socialInsuranceCode,
                bonusHealthInsuranceRate: ko.toJS(self.bonusHealthInsuranceRate),
                healthInsuranceMonthlyFee: ko.toJS(self.healthInsuranceMonthlyFee),
                yearMonthHistoryItem: ko.toJS(self.selectedHistoryPeriod)
            }
            // Update individualExcemtionRate and employeeExemtionRate to null if not join fund
            // Update 個人免除率, 事業主免除率



            // Update historyId for case clone previous data
            command.bonusHealthInsuranceRate.historyId = command.yearMonthHistoryItem.historyId;
            command.healthInsuranceMonthlyFee.historyId = command.yearMonthHistoryItem.historyId;
            service.addEmployeeHealthInsurance(command).done(function(data) {
                block.clear();
                dialog.info({ messageId: 'Msg_15' });
                self.isUpdateMode(true);
            }).fail(function(err) {
                block.clear();
                dialog.alertError(err.message);
            });
        }
        printPDF() {
            // TODO
            console.log('TODO');
        }

        registerBusinessEstablishment() {
            let self = this;
            modal("/view/qmm/008/d/index.xhtml").onClosed(() => {
                if(getShared("QMM008_D_RES_PARAMS")) self.showAllOfficeAndHistory();
            });
        }

        standardRemunerationMonthlyAmount() {
            let self = this;
            let selectedOffice = self.selectedOffice, selectedHistoryId = self.selectedHistoryId;
            let selectedHistory = ko.toJS(self.selectedHistoryPeriod);
            setShared("QMM008_E_PARAMS", { selectedOffice: self.selectedOffice, selectedHistory: selectedHistory, history: selectedOffice.welfareInsuranceRateHistory.history });
            modal("/view/qmm/008/e/index.xhtml").onClosed(() => {
                if(getShared("QMM008_E_RES_PARAMS")) self.showAllOfficeAndHistory();
            });
        }

        masterCorrectionLog() {
            // TODO
            console.log('TODO');
        }
        startPage(): JQueryPromise<any> {
            let self = this;
            return self.showAllOfficeAndHistory();
        }

        showAllOfficeAndHistory(): JQueryPromise<any> {
            let self = this, dfd = $.Deferred();
            block.invisible();
            service.findAllOfficeAndHistory().done(function(data) {
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
                    if (self.isOnStartUp) {
                        self.isOnStartUp = false;
                        let firstOffice = data[0].contributionRateHistory
                        if (firstOffice.history.length > 0) self.selectedHealthInsurance(firstOffice.socialInsuranceCode + "___" + firstOffice.history[0].historyId);
                        else self.selectedHealthInsurance(firstOffice.socialInsuranceCode);
                    } else self.changeBySelectedValue();
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
                        self.isSelectedHistory(false);
                        self.initBlankData();
                    }
                }
            });
        }

        showByHistory() {
            let self = this;
            if (!self.isUpdateMode()) {
                self.isUpdateMode(true);
                self.showAllOfficeAndHistory();
            } else {
                self.changeBySelectedValue();
            }
        }

        changeBySelectedValue() {
            let self = this;
            if (self.selectedHealthInsurance()) {
                let selectedInsuranceCode = self.selectedHealthInsurance().split('___')[0],
                    selectedHistoryId = self.selectedHealthInsurance().split('___')[1],
                    selectedHistoryPeriod = null,
                    listInsuranceOffice = ko.toJS(self.socialInsuranceOfficeList);
                self.selectedHistoryId = selectedHistoryId;
                self.selectedOffice = _.find(listInsuranceOffice, { socialInsuranceCode: selectedInsuranceCode });
                if (selectedHistoryId) {
                    let selectedHistoryPeriod;
                    if (self.selectedOffice) {
                        selectedHistoryPeriod = _.find(self.selectedOffice.contributionRateHistory.history, { historyId: selectedHistoryId });
                    }
                    if (selectedHistoryPeriod) {
                        selectedHistoryPeriod.displayStart = self.convertYearMonthToDisplayYearMonth(selectedHistoryPeriod.startMonth);
                        selectedHistoryPeriod.displayEnd = self.convertYearMonthToDisplayYearMonth(selectedHistoryPeriod.endMonth);
                        self.selectedHistoryPeriod(selectedHistoryPeriod);
                        //アルゴリズム「選択処理」を実行する
                        self.showContributionRateByHistoryId(self.selectedHistoryId);
                    } else {
                        self.selectedHistoryPeriod({ displayStart: '', displayEnd: '' });
                    }
                }
            }
        }

        showContributionRateByHistoryId(historyId) {
            let self = this;
            block.invisible();
            service.findContributionRateByHistoryId(historyId).done(function(data) {
                if (data) {
                    self.healthInsuranceMonthlyFee(new model.HealthInsuranceMonthlyFee(data.healthInsuranceMonthlyFeeDto));
                    self.bonusHealthInsuranceRate(new model.BonusHealthInsuranceRate(data.bonusHealthInsuranceRateDto));
                } else {
                    self.isUpdateMode(true);
                }
                block.clear();
            }).fail(function(err) {
                block.clear();
                dialog.alertError(err.message);
            });
        }

        convertToTreeGridList() {
            let self = this,
                healthInsuranceList = ko.toJS(self.socialInsuranceOfficeList),
                displayHealthInsuranceList: Array<model.TreeGridNode> = [],
                pensionItem = {};
            healthInsuranceList.forEach(function(office) {
                let healthInsuranceItem = new model.TreeGridNode(office.socialInsuranceCode, office.socialInsuranceCode + ' ' + office.socialInsuranceName, []);
                if (office.contributionRateHistory) {
                    let displayStart, displayEnd = "";
                    office.contributionRateHistory.history.forEach(function(history) {
                        displayStart = self.convertYearMonthToDisplayYearMonth(history.startMonth);
                        displayEnd = self.convertYearMonthToDisplayYearMonth(history.endMonth);
                        // ___ is for child contain office code and history id
                        healthInsuranceItem.child.push(new model.TreeGridNode(office.socialInsuranceCode + '___' + history.historyId, displayStart + ' ~ ' + displayEnd, []));
                    });
                }
                displayHealthInsuranceList.push(healthInsuranceItem);
            });
            self.contributionRateTreeList(displayHealthInsuranceList);
        }

        convertYearMonthToDisplayYearMonth(yearMonth) {
            return String(yearMonth).substring(0, 4) + "/" + String(yearMonth).substring(4, 6);
        }

        createNewHistory() {
            let self = this;
            let selectedOffice = self.selectedOffice;
            setShared("QMM008_G_PARAMS", { selectedOffice: selectedOffice, history: selectedOffice.healthInsuranceFeeRateHistory.history });
            modal("/view/qmm/008/g/index.xhtml").onClosed(() => {
                let params = getShared("QMM008_G_RES_PARAMS");
                if (params) {
                    let socialInsuranceOfficeList = ko.toJS(self.socialInsuranceOfficeList);
                    let historyId = nts.uk.util.randomId();
                    socialInsuranceOfficeList.forEach(office => {
                        if (office.socialInsuranceCode == selectedOffice.socialInsuranceCode) {
                            let history = office.healthInsuranceFeeRateHistory.history;
                            if (history.length > 0) {
                                let beforeLastestMonth = moment(params.startMonth, 'YYYYMM').subtract(1, 'month');
                                history[history.length - 1].endMonth = beforeLastestMonth.format('YYYYMM');
                            }
                            history.push({ historyId: historyId, startMonth: params.startMonth, endMonth: '999912' });
                            office.healthInsuranceFeeRateHistory.history = history;
                            office = new model.SocialInsuranceOffice(office);
                        }
                    });
                    self.socialInsuranceOfficeList(socialInsuranceOfficeList);
                    self.convertToTreeGridList();
                    self.selectedHealthInsurance(selectedOffice.socialInsuranceCode + "___" + historyId);
                    if (params.takeoverMethod == model.TAKEOVER_METHOD.FROM_BEGINNING) {

                    } else {

                    }
                    self.isUpdateMode(false);
                }
                $("#B2_7").focus();
            });
        }

        editHistory() {
            let self = this;
            let selectedOffice = self.selectedOffice, selectedHistoryId = self.selectedHistoryId;
            let selectedHistory = ko.toJS(self.selectedHistoryPeriod);
            setShared("QMM008_H_PARAMS", { selectedOffice: self.selectedOffice, selectedHistory: selectedHistory, history: selectedOffice.healthInsuranceFeeRateHistory.history });
            modal("/view/qmm/008/h/index.xhtml").onClosed(() => {
                $("#B1_5").focus();
                let params = getShared("QMM008_H_RES_PARAMS");
                if (params) {
                    let selectedCode = self.selectedHealthInsurance();
                    self.selectedHealthInsurance(null);
                    let socialInsuranceOfficeList = ko.toJS(self.socialInsuranceOfficeList);
                    socialInsuranceOfficeList.forEach(office => {
                        if (office.socialInsuranceCode == selectedOffice.socialInsuranceCode) {
                            let history = office.healthInsuranceFeeRateHistory.history;
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
                            office.healthInsuranceFeeRateHistory.history = history;
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
    }
}

