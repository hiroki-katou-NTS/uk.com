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
        isSelectFirstOfficeAndHistory: boolean = true;
        jumpTopPageIfNoData: boolean = false;
        // History Tree Grid C1_1 -> C1_12 
        socialInsuranceOfficeList: KnockoutObservableArray<model.SocialInsuranceOffice> = ko.observableArray([]);
        isSelectedHistory: KnockoutObservable<boolean> = ko.observable(false);
        welfareInsuranceRateTreeList: KnockoutObservableArray<model.TreeGridNode> = ko.observableArray([]);
        selectedWelfareInsurance: KnockoutObservable<string> = ko.observable(null);
        // Office and history info
        selectedOffice: any = null;
        selectedHistoryId: string = "";

        // Welfare Item
        selectedHistoryPeriod: KnockoutObservable<model.GenericHistoryYearMonthPeiod> = ko.observable({ displayStart: '', displayJapanYearMonth: '', displayEnd: '' });
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
                block.clear();
                if (data) {
                    if (data.length == 0) {
                        if (self.jumpTopPageIfNoData) {
                            nts.uk.request.jump("com", "/view/ccg/008/a/index.xhtml");
                        }
                        self.registerBusinessEstablishment();
                    } else {
                        // from json to model
                        let socailInsuranceOfficeList: Array<model.SocialInsuranceOffice> = [];
                        data.forEach(office => {
                            socailInsuranceOfficeList.push(new model.SocialInsuranceOffice(office));
                        });
                        self.socialInsuranceOfficeList(socailInsuranceOfficeList);
                        self.convertToTreeGridList();
                        // select first office and last history
                        if (self.isSelectFirstOfficeAndHistory) {
                            self.isSelectFirstOfficeAndHistory = false;
                            let firstOffice = data[0].welfareInsuranceRateHistory
                            if (firstOffice.history.length > 0) self.selectedWelfareInsurance(firstOffice.socialInsuranceCode + "___" + firstOffice.history[0].historyId);
                            else self.selectedWelfareInsurance(firstOffice.socialInsuranceCode);
                        } else {
                            self.changeBySelectedValue();
                        }
                    }
                }
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
            self.selectedWelfareInsurance.subscribe(function(selectedValue: any) {
                nts.uk.ui.errors.clearAll();
                if (selectedValue) {
                    self.changeBySelectedValue();
                    if (!self.isUpdateMode()) {
                        self.isUpdateMode(true);
                        self.showAllOfficeAndHistory();
                    }
                    // if select history
                    if (selectedValue.length >= 36) {
                        self.isSelectedHistory(true);
                    } else { // if select office
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
                        selectedHistoryPeriod.displayJapanYearMonth = "(" + self.convertYearMonthToDisplayJpanYearMonth(selectedHistoryPeriod.startMonth) + ")";
                        selectedHistoryPeriod.displayStart = self.convertYearMonthToDisplayYearMonth(selectedHistoryPeriod.startMonth);
                        selectedHistoryPeriod.displayEnd = self.convertYearMonthToDisplayYearMonth(selectedHistoryPeriod.endMonth);
                        self.selectedHistoryPeriod(selectedHistoryPeriod);
                    }
                    self.showEmployeePensionByHistoryId(self.selectedHistoryId);
                } else {
                    self.selectedHistoryPeriod({ displayStart: '', displayJapanYearMonth: '', displayEnd: '' });
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
                }
                if (!self.isUpdateMode()) $('#C2_7').focus();
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

            // each office
            pensionList.forEach(function(office) {
                let pensionItem = new model.TreeGridNode(office.socialInsuranceCode, office.socialInsuranceCode + ' ' + office.socialInsuranceName, [], office.socialInsuranceCode, office.socialInsuranceName);
                if (office.welfareInsuranceRateHistory) {
                    let displayStart, displayEnd = "";
                    // each history
                    office.welfareInsuranceRateHistory.history.forEach(function(history) {
                        displayStart = self.convertYearMonthToDisplayYearMonth(history.startMonth);
                        displayEnd = self.convertYearMonthToDisplayYearMonth(history.endMonth);
                        // ___ is for child contain office code and history id
                        pensionItem.child.push(new model.TreeGridNode(office.socialInsuranceCode + '___' + history.historyId, displayStart + ' ~ ' + displayEnd, [], "", ""));
                    });
                }
                displayPensionList.push(pensionItem);
            });
            self.welfareInsuranceRateTreeList(displayPensionList);
        }

        convertYearMonthToDisplayYearMonth(yearMonth) {
            return nts.uk.time.formatYearMonth(Number(yearMonth));
        }

        convertYearMonthToDisplayJpanYearMonth(yearMonth) {
            return nts.uk.time.yearmonthInJapanEmpire(Number(yearMonth)).toString().split(' ').join('');
        }

        register() {
            let self = this;
            nts.uk.ui.errors.clearAll();
            $('.tab-c .nts-input').trigger("validate");
            if (nts.uk.ui.errors.hasError()) {
                return;
            }
            // Register data
            let command = {
                officeCode: self.selectedOffice.socialInsuranceCode,
                bonusEmployeePensionInsuranceRate: ko.toJS(self.bonusEmployeePensionInsuranceRate),
                employeesPensionMonthlyInsuranceFee: ko.toJS(self.employeeMonthlyInsuFee),
                welfarePensionInsuranceClassification: ko.toJS(self.welfareInsuranceClassification),
                yearMonthHistoryItem: ko.toJS(self.selectedHistoryPeriod)
            }
            if (command.welfarePensionInsuranceClassification.fundClassification == model.FUND_CLASSIFICATION.JOIN) {
                self.validateRemainRatio(command);
                if (nts.uk.ui.errors.hasError()) {
                    return;
                }
            }
            block.invisible();
            // Update historyId for case clone previous data
            command.bonusEmployeePensionInsuranceRate.historyId = command.yearMonthHistoryItem.historyId;
            command.employeesPensionMonthlyInsuranceFee.historyId = command.yearMonthHistoryItem.historyId;
            command.welfarePensionInsuranceClassification.historyId = command.yearMonthHistoryItem.historyId;
            if (self.isUpdateMode()) {
                service.checkWelfarePensionInsuranceGradeFeeChange(command).done(function(data) {
                    block.clear();
                    if (data) {
                        dialog.confirm({ messageId: "MsgQ_209" }).ifYes(function() {
                            self.registerIfValid(command);
                        });
                    } else {
                        self.registerIfValid(command);
                    }
                }).fail(function(err) {
                    block.clear();
                    dialog.alertError(err.message);
                });
            } else {
                self.registerIfValid(command);
            }
        }

        registerIfValid(command) {
            let self = this;
            block.invisible();
            service.registerEmployeePension(command).done(function(data) {
                block.clear();
                dialog.info({ messageId: 'Msg_15' }).then(function() {
                    $('#C2_7').focus();
                });
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
                self.isSelectFirstOfficeAndHistory = true;
                self.jumpTopPageIfNoData = true;
                self.showAllOfficeAndHistory();
            });
        }

        standardRemunerationMonthlyAmount() {
            let self = this;
            let selectedOffice = self.selectedOffice, selectedHistoryId = self.selectedHistoryId;
            let selectedHistory = ko.toJS(self.selectedHistoryPeriod);
            setShared("QMM008_F_PARAMS", { selectedOffice: self.selectedOffice, selectedHistory: selectedHistory, history: selectedOffice.welfareInsuranceRateHistory.history });
            modal("/view/qmm/008/f/index.xhtml").onClosed(() => {
            });
        }

        masterCorrectionLog() {
            // TODO
            console.log('TODO');
        }

        createNewHistory() {
            let self = this;
            let selectedOffice = self.selectedOffice;
            let history = selectedOffice.welfareInsuranceRateHistory.history;
            setShared("QMM008_G_PARAMS", { selectedOffice: selectedOffice, history: history });
            modal("/view/qmm/008/g/index.xhtml").onClosed(() => {
                let params = getShared("QMM008_G_RES_PARAMS");
                if (params) {
                    let socialInsuranceOfficeList = ko.toJS(self.socialInsuranceOfficeList);
                    let historyId = nts.uk.util.randomId();
                    // update previous history
                    if (history.length > 0) {
                        let beforeLastestMonth = moment(params.startMonth, 'YYYYMM').subtract(1, 'month');
                        history[0].endMonth = beforeLastestMonth.format('YYYYMM');
                    }
                    // add new history
                    history.unshift({ historyId: historyId, startMonth: params.startMonth, endMonth: '999912' });
                    // each office
                    socialInsuranceOfficeList.forEach(office => {
                        if (office.socialInsuranceCode == selectedOffice.socialInsuranceCode) {
                            // add new history and update previous history
                            office.welfareInsuranceRateHistory.history = history;
                            office = new model.SocialInsuranceOffice(office);
                        }
                    });
                    self.socialInsuranceOfficeList(socialInsuranceOfficeList);
                    self.convertToTreeGridList();
                    self.selectedWelfareInsurance(selectedOffice.socialInsuranceCode + "___" + historyId);
                    // init data
                    if (params.takeoverMethod == model.TAKEOVER_METHOD.FROM_LASTEST_HISTORY && history.length > 1) {
                        self.showEmployeePensionByHistoryId(history[1].historyId);
                    } else {
                        self.initBlankData();
                    }
                    self.isUpdateMode(false);
                }
                $("#C2_7").focus();
            });
        }

        editHistory() {
            let self = this;
            let selectedOffice = self.selectedOffice, selectedHistoryId = self.selectedHistoryId, history = selectedOffice.welfareInsuranceRateHistory.history;
            let selectedHistory = ko.toJS(self.selectedHistoryPeriod);
            setShared("QMM008_H_PARAMS", { screen: "C", selectedOffice: self.selectedOffice, selectedHistory: selectedHistory, history: history });
            modal("/view/qmm/008/h/index.xhtml").onClosed(() => {
                $("#C1_5").focus();
                let params = getShared("QMM008_H_RES_PARAMS");
                if (params) {
                    self.showAllOfficeAndHistory();
                    if (params.modifyMethod == model.MOFIDY_METHOD.DELETE) {
                        // select office if no history
                        if (history.length <= 1) {
                            self.selectedWelfareInsurance(selectedOffice.socialInsuranceCode);
                        } else {
                            self.selectedWelfareInsurance(selectedOffice.socialInsuranceCode + "___" + history[1].historyId)
                        }
                    }
                }
            });
        }
        validateRemainRatio(command) {
            let bonusPensionInsurance = command.bonusEmployeePensionInsuranceRate;
            let salaryPensuinInrurance = command.employeesPensionMonthlyInsuranceFee.salaryEmployeesPensionInsuranceRate;
            // C3_9, C3_11 - C4_19, C4_21
            // C3_9 - C4_19
            let salaryMaleContributionRate = salaryPensuinInrurance.maleContributionRate;
            if (Number(salaryMaleContributionRate.individualBurdenRatio) < Number(salaryMaleContributionRate.individualExemptionRate)) $('#C3_9').ntsError('set', { messageId: "MsgQ_219" });
            // C3_11 - C4_21
            if (Number(salaryMaleContributionRate.employeeContributionRatio) < Number(salaryMaleContributionRate.employeeExemptionRate)) $('#C3_11').ntsError('set', { messageId: "MsgQ_220" });
            // C3_18, C3_20 - C4_38, C4_40
            let salaryFemaleContributionRate = salaryPensuinInrurance.femaleContributionRate;
            // C3_18 - C4_38
            if (Number(salaryFemaleContributionRate.individualBurdenRatio) < Number(salaryFemaleContributionRate.individualExemptionRate)) $('#C3_18').ntsError('set', { messageId: "MsgQ_221" });
            // C3_20 - C4_40
            if (Number(salaryFemaleContributionRate.employeeContributionRatio) < Number(salaryFemaleContributionRate.employeeExemptionRate)) $('#C3_20').ntsError('set', { messageId: "MsgQ_222" });
            // C3_13, C3_15 - C4_23, C4_25
            let bonusMaleContributionRate = bonusPensionInsurance.maleContributionRate;
            // C3_13 - C4_23
            if (Number(bonusMaleContributionRate.individualBurdenRatio) < Number(bonusMaleContributionRate.individualExemptionRate)) $('#C3_13').ntsError('set', { messageId: "MsgQ_223" });
            // C3_15 - C4_25
            if (Number(bonusMaleContributionRate.employeeContributionRatio) < Number(bonusMaleContributionRate.employeeExemptionRate)) $('#C3_15').ntsError('set', { messageId: "MsgQ_224" });
            // C3_22, C3_24 - C4_42, C4_44
            let bonusFemaleContributionRate = bonusPensionInsurance.femaleContributionRate;
            // C3_22 - C4_42
            if (Number(bonusFemaleContributionRate.individualBurdenRatio) < Number(bonusFemaleContributionRate.individualExemptionRate)) $('#C3_22').ntsError('set', { messageId: "MsgQ_223" });
            // C3_24 - C4_44
            if (Number(bonusFemaleContributionRate.employeeContributionRatio) < Number(bonusFemaleContributionRate.employeeExemptionRate)) $('#C3_24').ntsError('set', { messageId: "MsgQ_224" });

        }
    }
}

