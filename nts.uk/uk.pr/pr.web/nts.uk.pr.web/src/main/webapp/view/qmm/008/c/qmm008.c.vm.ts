module nts.uk.pr.view.qmm008.c.viewmodel {
    import getShared = nts.uk.ui.windows.getShared;
    import setShared = nts.uk.ui.windows.setShared;
    import dialog = nts.uk.ui.dialog;
    import getText = nts.uk.resource.getText;
    import modal = nts.uk.ui.windows.sub.modal;
    import block = nts.uk.ui.block;
    import model = nts.uk.pr.view.qmm008.share.model;
    import service = nts.uk.pr.view.qmm008.c.service;
    export class ScreenModel {

        isUpdateMode: KnockoutObservable<boolean> = ko.observable(true);
        isOnStartUp = true;
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
        welfareInsuranceRateHistory: KnockoutObservable<model.WelfarePensionInsuranceRateHistory> = ko.observable(null);
        employeeMonthlyInsuFee: KnockoutObservable<model.EmployeePensionMonthlyInsuFee> = ko.observable(null);
        welfareInsuranceClassification: KnockoutObservable<model.WelfarePensionInsuranceClassification> = ko.observable(null);
        bonusEmployeePensionInsuranceRate: KnockoutObservable<model.BonusEmployeePensionInsuranceRate> = ko.observable(null);


        constructor() {
            let self = this;
            $("#C3").ntsFixedTable({});
            $("#C4").ntsFixedTable({});
            $("#C5").ntsFixedTable({});
            self.initBlankData();
            self.watchDataChanged();
        }


        startPage(): JQueryPromise<any> {
            let self = this, dfd = $.Deferred();
            return self.showAllOfficeAndHistory();
        }

        showAllOfficeAndHistory(): JQueryPromise<any> {
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
                    if (self.isOnStartUp) {
                        self.isOnStartUp = false;
                        let firstOffice = data[0].welfareInsuranceRateHistory
                        if (firstOffice.history.length > 0) self.selectedWelfareInsurance(firstOffice.socialInsuranceCode + "___" + firstOffice.history[0].historyId);
                        else self.selectedWelfareInsurance(firstOffice.socialInsuranceCode);
                    } else {
                        self.changeBySelectedValue();
                    }
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

        showByHistory() {
            let self = this;
            if (!self.isUpdateMode()) {
                self.isUpdateMode(true);
                self.showAllOfficeAndHistory();
            } else {
                self.changeBySelectedValue();
            }
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
                        self.initBlankData();
                    }
                }
            });
        }

        changeBySelectedValue() {
            let self = this;
            // ___ represent office code and history id
            if (self.selectedWelfareInsurance()) {
                let selectedInsuranceCode = self.selectedWelfareInsurance().split('___')[0],
                    selectedHistoryId = self.selectedWelfareInsurance().split('___')[1],
                    selectedHistoryPeriod = null,
                    listInsuranceOffice = ko.toJS(self.socialInsuranceOfficeList);
                self.selectedHistoryId = selectedHistoryId;
                // find office
                self.selectedOffice = _.find(listInsuranceOffice, { socialInsuranceCode: selectedInsuranceCode });
                if (selectedHistoryId) {
                    let selectedHistoryPeriod;
                    // find history
                    if (self.selectedOffice) {
                        selectedHistoryPeriod = _.find(self.selectedOffice.welfareInsuranceRateHistory.history, { historyId: selectedHistoryId });
                    }
                    // 201809 -> 2018/09
                    if (selectedHistoryPeriod) {
                        selectedHistoryPeriod.displayStart = self.convertYearMonthToDisplayYearMonth(selectedHistoryPeriod.startMonth);
                        selectedHistoryPeriod.displayEnd = self.convertYearMonthToDisplayYearMonth(selectedHistoryPeriod.endMonth);
                        self.selectedHistoryPeriod(selectedHistoryPeriod);
                    }
                    self.showEmployeePensionByHistoryId(self.selectedHistoryId);
                } else {
                    self.selectedHistoryPeriod({ displayStart: '', displayEnd: '' });
                }
            }
        }

        showEmployeePensionByHistoryId(historyId) {
            let self = this;
            block.invisible();
            service.findEmployeePensionByHistoryId(historyId).done(function(data) {
                if (data) {
                    self.welfareInsuranceClassification(new model.WelfarePensionInsuranceClassification(data.welfarePensionInsuranceClassification));
                    self.employeeMonthlyInsuFee(new model.EmployeePensionMonthlyInsuFee(data.employeesPensionMonthlyInsuranceFee));
                    self.bonusEmployeePensionInsuranceRate(new model.BonusEmployeePensionInsuranceRate(data.bonusEmployeePensionInsuranceRate));
                } else {
                    self.isUpdateMode(true);
                }
                block.clear();
            }).fail(function(err) {
                block.clear();
                dialog.alertError(err.message);
            });
        }

        initDataByLastestHistory() {
            let self = this;
        }
        initBlankData() {
            let self = this;
            self.employeeMonthlyInsuFee(new model.EmployeePensionMonthlyInsuFee(null));
            self.bonusEmployeePensionInsuranceRate(new model.BonusEmployeePensionInsuranceRate(null));
            self.welfareInsuranceClassification(new model.WelfarePensionInsuranceClassification(null));
        }
        convertToTreeGridList() {
            let self = this,
                pensionList = ko.toJS(self.socialInsuranceOfficeList),
                displayPensionList: Array<model.TreeGridNode> = [],
                pensionItem = {};
            pensionList.forEach(function(office) {
                let pensionItem = new model.TreeGridNode(office.socialInsuranceCode, office.socialInsuranceCode + ' ' + office.socialInsuranceName, []);
                if (office.welfareInsuranceRateHistory) {
                    let displayStart, displayEnd = "";
                    office.welfareInsuranceRateHistory.history.forEach(function(history) {
                        displayStart = self.convertYearMonthToDisplayYearMonth(history.startMonth);
                        displayEnd = self.convertYearMonthToDisplayYearMonth(history.endMonth);
                        // ___ is for child contain office code and history id
                        pensionItem.child.push(new model.TreeGridNode(office.socialInsuranceCode + '___' + history.historyId, displayStart + ' ~ ' + displayEnd, []));
                    });
                }
                displayPensionList.push(pensionItem);
            });
            self.welfareInsuranceRateTreeList(displayPensionList);
        }

        convertYearMonthToDisplayYearMonth(yearMonth) {
            return String(yearMonth).substring(0, 4) + "/" + String(yearMonth).substring(4, 6);
        }

        convertYMPeriodToDisplayYMPeriod() {

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
                bonusEmployeePensionInsuranceRate: ko.toJS(self.bonusEmployeePensionInsuranceRate),
                employeesPensionMonthlyInsuranceFee: ko.toJS(self.employeeMonthlyInsuFee),
                welfarePensionInsuranceClassification: ko.toJS(self.welfareInsuranceClassification),
                yearMonthHistoryItem: ko.toJS(self.selectedHistoryPeriod)
            }
            // Update individualExcemtionRate and employeeExemtionRate to null if not join fund
            // Update 個人免除率, 事業主免除率



            // Update historyId for case clone previous data
            command.bonusEmployeePensionInsuranceRate.historyId = command.yearMonthHistoryItem.historyId;
            command.employeesPensionMonthlyInsuranceFee.historyId = command.yearMonthHistoryItem.historyId;
            command.welfarePensionInsuranceClassification.historyId = command.yearMonthHistoryItem.historyId;
            service.addEmployeePension(command).done(function(data) {
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
            setShared("QMM008_F_PARAMS", { selectedOffice: self.selectedOffice, selectedHistory: selectedHistory, history: selectedOffice.welfareInsuranceRateHistory.history });
            modal("/view/qmm/008/f/index.xhtml").onClosed(() => {
                if(getShared("QMM008_F_RES_PARAMS")) self.showAllOfficeAndHistory();
            });
        }

        masterCorrectionLog() {
            // TODO
            console.log('TODO');
        }

        createNewHistory() {
            let self = this;
            let selectedOffice = self.selectedOffice;
            setShared("QMM008_G_PARAMS", { selectedOffice: selectedOffice, history: selectedOffice.welfareInsuranceRateHistory.history });
            modal("/view/qmm/008/g/index.xhtml").onClosed(() => {
                let params = getShared("QMM008_G_RES_PARAMS");
                if (params) {
                    let socialInsuranceOfficeList = ko.toJS(self.socialInsuranceOfficeList);
                    let historyId = nts.uk.util.randomId();
                    let previousHistoryId;
                    // each office
                    socialInsuranceOfficeList.forEach(office => {
                        if (office.socialInsuranceCode == selectedOffice.socialInsuranceCode) {
                            // add new history and update previous history
                            let history = office.welfareInsuranceRateHistory.history;
                            if (history.length > 0) {
                                let beforeLastestMonth = moment(params.startMonth, 'YYYYMM').subtract(1, 'month');
                                history[0].endMonth = beforeLastestMonth.format('YYYYMM');
                                previousHistoryId = history[0].historyId;
                            }
                            history.unshift({ historyId: historyId, startMonth: params.startMonth, endMonth: '999912' });
                            office.welfareInsuranceRateHistory.history = history;
                            office = new model.SocialInsuranceOffice(office);
                        }
                    });
                    self.socialInsuranceOfficeList(socialInsuranceOfficeList);
                    self.convertToTreeGridList();
                    self.selectedWelfareInsurance(selectedOffice.socialInsuranceCode + "___" + historyId);
                    // init data
                    if (params.takeoverMethod == model.TAKEOVER_METHOD.FROM_BEGINNING) {
                        self.initBlankData();
                    } else {
                        if (previousHistoryId) {
                            self.showEmployeePensionByHistoryId(previousHistoryId);
                        } else {
                            self.initBlankData();
                        }
                    }
                    self.isUpdateMode(false);
                }
                $("#C2_7").focus();
            });
        }

        editHistory() {
            let self = this;
            let selectedOffice = self.selectedOffice, selectedHistoryId = self.selectedHistoryId;
            let selectedHistory = ko.toJS(self.selectedHistoryPeriod);
            setShared("QMM008_H_PARAMS", {screen: "C", selectedOffice: self.selectedOffice, selectedHistory: selectedHistory, history: selectedOffice.welfareInsuranceRateHistory.history });
            modal("/view/qmm/008/h/index.xhtml").onClosed(() => {
                $("#C1_5").focus();
                let params = getShared("QMM008_H_RES_PARAMS");
                if (params) {
                    self.isUpdateMode(false);
                    let selectedCode = self.selectedWelfareInsurance();
                    let newCode;
                    let socialInsuranceOfficeList = ko.toJS(self.socialInsuranceOfficeList);
                    // each office
                    socialInsuranceOfficeList.forEach(office => {
                        if (office.socialInsuranceCode == selectedOffice.socialInsuranceCode) {
                            let history = office.welfareInsuranceRateHistory.history;
                            if (history.length > 0) {
                                // edit selected history and previous history
                                if (params.modifyMethod == model.MOFIDY_METHOD.UPDATE) {
                                    history.forEach((historyItem, index) => {
                                        if (selectedHistory.historyId == historyItem.historyId) {
                                            let currentPreviousMonth = moment(params.startMonth, 'YYYYMM').subtract(1, 'month');
                                            historyItem.startMonth = params.startMonth;
                                            if (index > 0) history[index - 1].endMonth = currentPreviousMonth.format('YYYYMM');
                                        }
                                    });
                                } else {
                                    // delete history and update previous history
                                    history.pop();
                                    if (history.length > 0) {
                                        history[history.length - 1].endMonth = '999912';
                                        newCode = office.socialInsuranceCode + "___" + history[history.length - 1].historyId;
                                    } else {
                                        newCode = office.socialInsuranceCode;
                                    }
                                }

                            }
                            office.welfareInsuranceRateHistory.history = history;
                            office = new model.SocialInsuranceOffice(office);
                        }
                    });
                    self.socialInsuranceOfficeList(socialInsuranceOfficeList);
                    if (newCode && newCode != selectedCode) {
                        self.selectedWelfareInsurance(newCode);

                    } else {
                        self.selectedWelfareInsurance.valueHasMutated();
                    }
                    self.convertToTreeGridList();
                    if (params.takeoverMethod == model.TAKEOVER_METHOD.FROM_BEGINNING) {
                        self.initBlankData();
                    } else {
                        self.initDataByLastestHistory();
                    }
                }
            });
        }
    }
}

