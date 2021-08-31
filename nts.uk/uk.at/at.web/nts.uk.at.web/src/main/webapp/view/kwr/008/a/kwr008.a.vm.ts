module nts.uk.at.view.kwr008.a {
    import message = nts.uk.resource.getMessage;
    import Ccg001ReturnedData = nts.uk.com.view.ccg.share.ccg.service.model.Ccg001ReturnedData;
    import EmployeeSearchDto = nts.uk.com.view.ccg.share.ccg.service.model.EmployeeSearchDto;
    import GroupOption = nts.uk.com.view.ccg.share.ccg.service.model.GroupOption;
    import share = nts.uk.at.view.kwr008.share.model;
    import alertError = nts.uk.ui.dialog.alertError;
    import error = nts.uk.ui.dialog.error;
    import block = nts.uk.ui.block;
    export module viewmodel {
        export class ScreenModel {
            ccgcomponent: GroupOption;
            systemTypes: KnockoutObservableArray<any>;

            // Options
            isQuickSearchTab: KnockoutObservable<boolean>;
            isAdvancedSearchTab: KnockoutObservable<boolean>;
            isAllReferableEmployee: KnockoutObservable<boolean>;
            isOnlyMe: KnockoutObservable<boolean>;
            isEmployeeOfWorkplace: KnockoutObservable<boolean>;
            isEmployeeWorkplaceFollow: KnockoutObservable<boolean>;
            isMutipleCheck: KnockoutObservable<boolean>;
            showDepartment: KnockoutObservable<boolean>; // 部門条件
            isSelectAllEmployee: KnockoutObservable<boolean>;
            periodStartDate: KnockoutObservable<moment.Moment>;
            periodEndDate: KnockoutObservable<moment.Moment>;
            baseDate: KnockoutObservable<moment.Moment>;
            selectedEmployee: KnockoutObservableArray<EmployeeSearchDto>;
            
            showEmployment: KnockoutObservable<boolean>; // 雇用条件
            showWorkplace: KnockoutObservable<boolean>; // 職場条件
            showClassification: KnockoutObservable<boolean>; // 分類条件
            showJobTitle: KnockoutObservable<boolean>; // 職位条件
            showWorktype: KnockoutObservable<boolean>; // 勤種条件
            inService: KnockoutObservable<boolean>; // 在職区分
            leaveOfAbsence: KnockoutObservable<boolean>; // 休職区分
            closed: KnockoutObservable<boolean>; // 休業区分
            retirement: KnockoutObservable<boolean>; // 退職区分
            systemType: KnockoutObservable<number>;
            showClosure: KnockoutObservable<boolean>; // 就業締め日利用
            showBaseDate: KnockoutObservable<boolean>; // 基準日利用
            showAllClosure: KnockoutObservable<boolean>; // 全締め表示
            showPeriod: KnockoutObservable<boolean>; // 対象期間利用
            periodFormatYM: KnockoutObservable<boolean>; // 対象期間精度

            user: any = __viewContext.user;
            // Employee tab
            lstPersonComponentOption: any;
            selectedEmployeeCode: KnockoutObservableArray<string>;
            employeeName: KnockoutObservable<string>;
            employeeList: KnockoutObservableArray<UnitModel> = ko.observableArray([]);
            alreadySettingPersonal: KnockoutObservableArray<UnitAlreadySettingModel>;
            ccgcomponentPerson: GroupOption;

            isEmployeeCharge: KnockoutObservable<boolean> = ko.observable(false);
            // date
            date: KnockoutObservable<string>;
            maxDaysCumulationByEmp: KnockoutObservable<number>;
            periodDate: KnockoutObservable<any>;
            //A3
            dateValue: KnockoutObservable<any> = ko.observable({ startDate: '', endDate: '' });
            startDateString: KnockoutObservable<string> = ko.observable('');
            endDateString: KnockoutObservable<string> = ko.observable('');
            //A4
            outputItem: KnockoutObservableArray<share.SetOutputItemOfAnnualWorkSchDto> = ko.observableArray([]);
            selectedOutputItem: KnockoutObservable<string> = ko.observable(null);
            enabledStandardMode: KnockoutObservable<boolean> = ko.observable(true);

            outputItemsFreeSetting: KnockoutObservableArray<share.SetOutputItemOfAnnualWorkSchDto> = ko.observableArray([]);
            selectedOutputItemFree: KnockoutObservable<string> = ko.observable(null);
            enabledFreeMode: KnockoutObservable<boolean> = ko.observable(false);

            //A6 
            breakPage: KnockoutObservableArray<share.ItemModel> = ko.observableArray([]);
            selectedBreakPage: KnockoutObservable<number> = ko.observable(null);

            selectionType: KnockoutObservable<number> = ko.observable(share.SelectionClassification.STANDARD);

            //年間勤務表印刷形式
            listSheetPrintingForm: KnockoutObservableArray<any> = ko.observableArray([
                { code: 0, name: nts.uk.resource.getText('KWR008_53') },
                { code: 1, name: nts.uk.resource.getText('KWR008_54') }
            ]);

            printFormat: KnockoutObservable<number> = ko.observable(0);

            listSheetExcludedEmp: KnockoutObservableArray<any> = ko.observableArray([
                { code: 0, name: nts.uk.resource.getText('KWR008_60') },
                { code: 1, name: nts.uk.resource.getText('KWR008_61') }
            ]);

            excludeEmp: KnockoutObservable<number> = ko.observable(0);

            fiscalYear: KnockoutObservable<string> = ko.observable((new Date()).getFullYear().toString());

            //A11
            standardMonth: any;
            
            curentMonth: KnockoutObservable<string> = ko.observable(null);
            
            selectAverage: KnockoutObservable<boolean> = ko.observable(false);
            
            ccg001Date: KnockoutObservable<string> = ko.observable(moment().format("YYYY/MM/DD"));

            baseMonth: KnockoutObservable<number> = ko.observable(0);
            
            enableAuthority: KnockoutObservable<boolean> = ko.observable(false);

            selectedRestore: string = '';

            startMonth: KnockoutObservable<any> = ko.observable(null);
            
            constructor() {
                var self = this;

                // dump
                self.selectedEmployee = ko.observableArray([]);
                self.periodDate = ko.computed(() => {
                  if (self.printFormat() === 0) {
                    return self.dateValue();
                  } else {
                    const startDate = self.fiscalYear() + self.startMonth();
                    const endDate = moment.utc(startDate, "YYYYMM").add(11, 'M');
                    return {
                      startDate: startDate,
                      endDate: endDate.format("YYYYMM")
                    };
                  }
                })
                // initial ccg options
                self.setDefaultCcg001Option();

                // Init component.
                self.reloadCcg001();

                self.startDateString.subscribe(function(value) {
                    self.dateValue().startDate = value;
                    self.dateValue.valueHasMutated();
                });

                self.endDateString.subscribe(function(value) {
                    self.dateValue().endDate = value;
                    self.dateValue.valueHasMutated();
                });

                self.periodFormatYM.subscribe(item => {
                    if (item) {
                        self.showClosure(true);
                    }
                });

                self.printFormat.subscribe(item => {
                    nts.uk.ui.errors.clearAll();
                    $.when(self.findAllStandardSetting(), self.findAllFreeSetting()).done(() => {
                        self.selectedOutputItem(_.isEmpty(self.outputItem()) ? null : self.outputItem()[0].layoutId);
                        self.selectedOutputItemFree(_.isEmpty(self.outputItemsFreeSetting()) ? null : self.outputItemsFreeSetting()[0].layoutId);
                        
                        // Restore selectedItem
                        if (_.findIndex(self.outputItem(), item => item.layoutId === self.selectedRestore) >= 0) {
                            self.selectedOutputItem(self.selectedRestore);
                            self.selectedRestore = '';
                        } else if (_.findIndex(self.outputItemsFreeSetting(), item => item.layoutId === self.selectedRestore) >= 0) {
                            self.selectedOutputItemFree(self.selectedRestore);
                            self.selectedRestore = '';
                        }
                    });
                    if (self.selectAverage() && self.printFormat() === share.AnnualWorkSheetPrintingForm.AGREEMENT_CHECK_36) {
                        self.getCurentMonth();
                    }
                });
                const outputSubsFunc = (settingId: any, selectionType: number) => {
                    if (selectionType !== self.selectionType()) {
                        return;
                    }
                    self.checkAverage(settingId);
                    if (self.selectAverage() && self.printFormat() === share.AnnualWorkSheetPrintingForm.AGREEMENT_CHECK_36) {
                        self.getCurentMonth();
                    }
                };
                self.selectedOutputItemFree.subscribe(settingId => {
                    outputSubsFunc(settingId, share.SelectionClassification.FREE_SETTING);
                });
                self.selectedOutputItem.subscribe(settingId => {
                    outputSubsFunc(settingId, share.SelectionClassification.STANDARD);
                });
                self.selectAverage.subscribe(() => {
                    if (self.selectAverage() && self.printFormat() === share.AnnualWorkSheetPrintingForm.AGREEMENT_CHECK_36) {
                        self.getCurentMonth();
                    }
                    nts.uk.ui.errors.clearAll();
                });

                self.selectedEmployeeCode = ko.observableArray([]);
                self.alreadySettingPersonal = ko.observableArray([]);
                self.maxDaysCumulationByEmp = ko.observable(0);
                
                self.standardMonth = {
                    option: (ko as any).mapping.fromJS(new nts.uk.ui.option.NumberEditorOption({
                        width: "20px",
                        textalign: "right"
                    })),
                    required: ko.observable(true)
                };

                self.selectionType.subscribe((value) => {
                    self.enabledStandardMode(value === share.SelectionClassification.STANDARD);
                    self.enabledFreeMode(value === share.SelectionClassification.FREE_SETTING);
                    self.selectedOutputItem.valueHasMutated();
                    self.selectedOutputItemFree.valueHasMutated();
                    nts.uk.ui.errors.clearAll();
                });
            }
            
            checkAverage(settingId: string) {
                var self = this;
                if (_.isNil(settingId) || _.isEmpty(settingId)) {
                    self.selectAverage(false)
                    return;
                }
                block.invisible();
                service.checkAverage(settingId).done(data => {
                    self.selectAverage(data);
                }).always(() => {
                    block.clear();
                });
            }

            exportReport() {
                var self = this;
                if (self.validate()) return;
                block.invisible();
                var data = new model.EmployeeDto();
                if (self.printFormat() == 0) {
                    data.startYearMonth = self.dateValue().startDate;
                    data.endYearMonth = self.dateValue().endDate;
                } else {
                    let year = self.fiscalYear();
                    if (year.match(/\d{4}/)) {
                        data.fiscalYear = year;
                    } else {
                        data.fiscalYear = moment.utc(year).get('year').toString();
                    }
                }
                if (self.selectAverage() && self.printFormat() == 1) {
                    data.curentMonth = self.curentMonth();  
                }
                data.setItemsOutputLayoutId = self.selectionType() === share.SelectionClassification.STANDARD 
                                        ? self.selectedOutputItem()
                                        : self.selectedOutputItemFree();
                data.breakPage = self.selectedBreakPage().toString();
                data.printFormat = self.printFormat();
                data.employees = [];
                data.excludeEmp = self.excludeEmp();
                data.baseDate = self.baseDate().format("YYYY/MM/DD");
                for (var employeeCode of self.selectedEmployeeCode()) {
                    let emp = self.findByCodeEmployee(employeeCode);
                    if (emp) data.employees.push(emp);
                }
                //ユーザ固有情報「年間勤務表（36チェックリスト）」を更新する
                self.saveOutputConditionAnnualWorkSchedule(
                    new model.OutputConditionAnnualWorkScheduleChar(
                        self.selectedBreakPage()
                        , self.printFormat()
                        , self.excludeEmp()
                        , self.selectionType()
                        , self.selectionType() === share.SelectionClassification.STANDARD
                            ? self.selectedOutputItem()
                            : self.selectedOutputItemFree()
                    )
                );
                nts.uk.request.exportFile('at/function/annualworkschedule/export', data).done((res: any) => {
                    let msgId = self.getAsyncData(res.taskDatas, "messageId").valueAsString;
                    if (msgId == "") return;
                    let totalEmpErr = self.getAsyncData(res.taskDatas, "totalEmpErr").valueAsNumber;
                    let msgEmpErr = self.getMsgEmpError(res.taskDatas, totalEmpErr);
                    //alertError({ messageId: msgId, message: message(msgId) + msgEmpErr });
                }).fail(err => {
                    alertError(err);
                }).always(() => {
                    block.clear();
                });
            }

            private getMsgEmpError(data: Array<any>, totalErr: number) {
                let self = this;
                let msgEmpErr = "";
                for (let i = 0; i < totalErr; i++) {
                    msgEmpErr += "\n" + self.getAsyncData(data, "empErr" + i).valueAsString;
                }
                return msgEmpErr;
            }

            private getAsyncData(data: Array<any>, key: string): any {
                var result = _.find(data, (item) => {
                    return item.key == key;
                });
                return result || { valueAsString: "", valueAsNumber: 0, valueAsBoolean: false };
            }

            openKWR008B() {
                let self = this;
                block.invisible();
                let param = {
                    selectedCd: self.selectionType() === share.SelectionClassification.STANDARD
                                    ? self.selectedOutputItem()
                                    : self.selectedOutputItemFree(),
                    printFormat: self.printFormat(),
                    selectionType: self.selectionType()
                }
                nts.uk.ui.windows.setShared("KWR008_B_Param", param);
                nts.uk.ui.windows.sub.modal("at", "/view/kwr/008/b/index.xhtml").onClosed(() => {
                    //reload A4_2
                    let resultData = nts.uk.ui.windows.getShared("KWR008_B_Result");
                    if (resultData.selectedCd && self.selectionType() === share.SelectionClassification.STANDARD) {
                        self.findAllStandardSetting().done(() => {
                            self.selectedOutputItem(resultData.selectedCd);
                            self.selectedOutputItem.valueHasMutated();
                        });
                    } else if (self.selectionType() === share.SelectionClassification.FREE_SETTING) {
                        self.findAllFreeSetting().done(() => {
                            self.selectedOutputItemFree(resultData.selectedCd);
                            self.selectedOutputItemFree.valueHasMutated();
                        });
                    }
                });
            }

            /**
             * Set default ccg001 options
             */
            public setDefaultCcg001Option(): void {
                let self = this;
                self.isQuickSearchTab = ko.observable(true);
                self.isAdvancedSearchTab = ko.observable(true);
                self.isAllReferableEmployee = ko.observable(true);
                self.isOnlyMe = ko.observable(true);
                self.isEmployeeOfWorkplace = ko.observable(true);
                self.isEmployeeWorkplaceFollow = ko.observable(true);
                self.isMutipleCheck = ko.observable(true);
                self.showDepartment = ko.observable(false); // 部門条件
                self.isSelectAllEmployee = ko.observable(false); //社員リスト
                self.baseDate = ko.observable(moment());
                self.periodStartDate = ko.observable(moment());
                self.periodEndDate = ko.observable(moment());
                self.showEmployment = ko.observable(true); // 雇用条件
                self.showWorkplace = ko.observable(true); // 職場条件
                self.showClassification = ko.observable(true); // 分類条件
                self.showJobTitle = ko.observable(true); // 職位条件
                self.showWorktype = ko.observable(true); // 勤種条件
                
                // update task : #102968
                /**
                 * ・在職区分：TRUE
                                           ・休職区分：TRUE
                                           ・休業区分：TRUE
                                           ・退職区分：FALSE
                 */
                self.inService = ko.observable(true); // 在職区分
                self.leaveOfAbsence = ko.observable(true); // 休職区分
                self.closed = ko.observable(true); // 休業区分
                self.retirement = ko.observable(false); // 退職区分
                
                self.systemType = ko.observable(2); //ok -
                self.showClosure = ko.observable(true); // 就業締め日利用
                self.showBaseDate = ko.observable(false); // 基準日利用
                self.showAllClosure = ko.observable(true); // 全締め表示
                self.showPeriod = ko.observable(true); // 対象期間利用
                self.periodFormatYM = ko.observable(true); // 対象期間精度
            }

            /**
             * Reload component CCG001
             */
            public reloadCcg001(): void {
                var self = this;
                var periodStartDate, periodEndDate: string;
                if (self.showBaseDate()) {
                    periodStartDate = moment(self.periodStartDate()).format("YYYY-MM-DD");
                    periodEndDate = moment(self.periodEndDate()).format("YYYY-MM-DD");
                } else {
                    periodStartDate = moment(self.periodStartDate()).format("YYYY-MM");
                    periodEndDate = moment(self.periodEndDate()).format("YYYY-MM"); // 対象期間終了日
                }

                if (!self.showBaseDate() && !self.showClosure() && !self.showPeriod()) {
                    alertError("Base Date or Closure or Period must be shown!");
                    return;
                }
                self.ccgcomponent = {
                    /** Common properties */
                    systemType: self.systemType(), // システム区分
                    showEmployeeSelection: self.isSelectAllEmployee(), // 検索タイプ
                    showQuickSearchTab: self.isQuickSearchTab(), // クイック検索
                    showAdvancedSearchTab: self.isAdvancedSearchTab(), // 詳細検索
                    showBaseDate: self.showBaseDate(), // 基準日利用
                    showClosure: self.showClosure(), // 就業締め日利用
                    showAllClosure: self.showAllClosure(), // 全締め表示
                    showPeriod: self.showPeriod(), // 対象期間利用
                    periodFormatYM: self.periodFormatYM(), // 対象期間精度
                    maxPeriodRange: "1", // 最長期間

                    /** Required parameter */
                    dateRangePickerValue: self.periodDate,
                    inService: self.inService(), // 在職区分
                    leaveOfAbsence: self.leaveOfAbsence(), // 休職区分
                    closed: self.closed(), // 休業区分
                    retirement: self.retirement(), // 退職区分

                    /** Quick search tab options */
                    showAllReferableEmployee: self.isAllReferableEmployee(), // 参照可能な社員すべて
                    showOnlyMe: self.isOnlyMe(), // 自分だけ
                    showSameWorkplace: self.isEmployeeOfWorkplace(), // 同じ職場の社員
                    showSameWorkplaceAndChild: self.isEmployeeWorkplaceFollow(), // 同じ職場とその配下の社員

                    /** Advanced search properties */
                    showEmployment: self.showEmployment(), // 雇用条件
                    showDepartment: self.showDepartment(), // 部門条件 not covered
                    showWorkplace: self.showWorkplace(), // 職場条件
                    showClassification: self.showClassification(), // 分類条件
                    showJobTitle: self.showJobTitle(), // 職位条件
                    showWorktype: self.showWorktype(), // 勤種条件
                    isMutipleCheck: self.isMutipleCheck(), // 選択モード

                    /** Return data */
                    returnDataFromCcg001: function(data: Ccg001ReturnedData) {
                        self.selectedEmployee(data.listEmployee);
                        self.applyKCP005ContentSearch(data.listEmployee);
                        self.baseDate(moment(data.baseDate.toDateTime()));
                        
                        if (self.printFormat() === 0) {
                          self.dateValue().startDate = moment(data.periodStart).format("YYYYMM");
                          self.dateValue().endDate = moment(data.periodEnd).format("YYYYMM");
                          self.dateValue.valueHasMutated();
                        } else {
                          self.fiscalYear(moment(data.periodStart).format("YYYY"));
                        }
                    }
                }
                //$('#ccgcomponent').ntsGroupComponent(self.ccgcomponent);
            }
            
            private getCurentMonth(): void {
                var self = this;
                block.invisible();
                service.getCurentMonth().done((data) => {
                    self.curentMonth(data.toString().substring(4));
                }).fail(() => {
                    error({ messageId: "Msg_1134"}).then(function() { 
                        block.clear(); 
                    });
                }).always(() => {
                    block.clear();
                });
            }
            /**
           * start page data 
           */
            public startPage(): JQueryPromise<any> {
                var self = this;
                var dfd = $.Deferred();
                var getCurrentLoginerRole = service.getCurrentLoginerRole().done((role: any) => {
                    self.isEmployeeCharge(role.employeeCharge);
                });
                //A3
                var getPeriod = service.getPeriod().done((data) => {
                    if (data) {
                        self.startDateString(data.startYearMonth);
                        self.endDateString(data.endYearMonth);
                        self.fiscalYear(moment.utc(data.startYearMonth, 'YYYYMM').get('year').toString());
                    }
                });

                //A4
                var restoreOutputConditionAnnualWorkSchedule;
                $.when(self.findAllFreeSetting(), self.findAllStandardSetting()).done(() => {
                    // A6
                    restoreOutputConditionAnnualWorkSchedule = self.restoreOutputConditionAnnualWorkSchedule()
                            .done((data: model.OutputConditionAnnualWorkScheduleChar) => {
                                if (data) {
                                    self.selectedBreakPage(data.breakPage);
                                    self.printFormat(data.printFormat);
                                    self.excludeEmp(data.excludeEmp);
                                    self.selectionType(data.settingType || 0);
                                    self.selectedRestore = data.layoutId;
                                } else if (self.selectionType() === share.SelectionClassification.STANDARD && self.outputItem().length) {
                                    self.selectedOutputItem(self.outputItem()[0].cd);
                                } else if (self.selectionType() === share.SelectionClassification.FREE_SETTING && self.outputItemsFreeSetting().length) {
                                    self.selectedOutputItemFree(self.outputItemsFreeSetting()[0].cd);
                                }
                                if (!self.outputItem().length) {
                                    self.selectedOutputItem(null);
                                }
                                if (!self.outputItemsFreeSetting().length) {
                                    self.selectedOutputItem(null);
                                }
                                if (self.printFormat() === share.AnnualWorkSheetPrintingForm.TIME_CHECK_LIST) {
                                    self.selectedOutputItem.valueHasMutated();
                                    self.printFormat.valueHasMutated();
                                }
                            });
                });

                var getPageBreakSelection = service.getPageBreakSelection().done((enumRes) => {
                    for (let i of enumRes) {
                        self.breakPage.push({ code: i.value, name: i.localizedName });
                    }
                    self.selectedBreakPage(enumRes.length > 0 ? enumRes[0].value : 0);
                }).fail((enumError) => {
                    console.log(`fail : ${enumError}`);
                });

                const getAuthorityOfWorkPerformance = service.getAuthorityOfWorkPerformance().done(dto => {
                    if (!_.isNil(dto)) {
                        self.enableAuthority(dto.freeSetting);
                    }
                })

                block.invisible();
                $.when(getCurrentLoginerRole,
                    getPeriod,
                    restoreOutputConditionAnnualWorkSchedule,
                    getPageBreakSelection,
                    getAuthorityOfWorkPerformance).done(() => {
                        dfd.resolve(self);
                        $('#A1_1').focus();
                    }).always(() => {
                        block.clear();
                    });

                service.getStartMonth().then(data => self.startMonth(data));
                return dfd.promise();
            }
            public validate(): boolean {
                let self = this;
                if (self.printFormat() === 0) {
                    $('#period .ntsDatepicker').trigger('validate');
                } else {
                    // $('.nts-input').trigger('validate');
                    $('#A9_2').trigger('validate');
                }
                if (self.selectionType() === share.SelectionClassification.STANDARD) {
                    $('#outputItem').trigger('validate');
                    if (!self.selectedOutputItem()) {
                        error({ messageId: "Msg_1783"});
                        return true;
                    }
                }
                if (self.selectionType() === share.SelectionClassification.FREE_SETTING) {
                    $('#outputItemFreeSetting').trigger('validate');
                    if (!self.selectedOutputItemFree()) {
                        error({ messageId: "Msg_1784"});
                        return true;
                    }
                }
                if (self.selectAverage()) {
                    $('#C11_2').trigger('validate');
                }
                $('#outputItem').trigger('validate');
                return nts.uk.ui.errors.hasError();
            }
            /**
            * apply ccg001 search data to kcp005
            */
            public applyKCP005ContentSearch(dataList: EmployeeSearchDto[]): void {
                var self = this;
                //self.employeeList([]);
                var employeeSearchs: UnitModel[] = [];
                for (var employeeSearch of dataList) {
                    var employee: UnitModel = {
                        employeeId: employeeSearch.employeeId,
                        code: employeeSearch.employeeCode,
                        name: employeeSearch.employeeName,
                        affiliationName: employeeSearch.affiliationName
                    };
                    employeeSearchs.push(employee);
                }
                self.employeeList(employeeSearchs);
                
                self.lstPersonComponentOption = {
                    isShowAlreadySet: false,
                    isMultiSelect: true,
                    listType: ListType.EMPLOYEE,
                    employeeInputList: self.employeeList,
                    selectType: SelectType.SELECT_ALL,
                    selectedCode: self.selectedEmployeeCode,
                    isDialog: true,
                    isShowNoSelectRow: false,
                    alreadySettingList: self.alreadySettingPersonal,
                    isShowWorkPlaceName: true,
                    isShowSelectAllButton: false,
                    maxWidth: 550,
                    maxRows: 16,
                    isSelectAllAfterReload: true
                };/*
                _.defer(() => {
                    var empCodeList = dataList.map((employee) => employee.employeeCode );
                    self.selectedEmployeeCode(empCodeList);
                });*/
            }

            /**
             * update selected employee kcp005 => detail
             */
            public findByCodeEmployee(employeeCode: string): UnitModel {
                var employee: UnitModel;
                var self = this;
                for (var employeeSelect of self.employeeList()) {
                    if (employeeSelect.code === employeeCode) {
                        employee = employeeSelect;
                        break;
                    }
                }
                return employee;
            }

            /**
             * convert string to date
             */
            private toDate(strDate: string): Date {
                return moment(strDate, 'YYYY/MM/DD').toDate();
            }

            /**
             * save to client service PersonalSchedule by employeeId
            */
            private restoreOutputConditionAnnualWorkSchedule(): JQueryPromise<model.OutputConditionAnnualWorkScheduleChar> {
                var self = this;
                return (nts.uk as any).characteristics.restore("OutputConditionAnnualWorkScheduleChar_" + self.user.employeeId);
            }
            /**
             * save to client service PersonalSchedule by employeeId
            */
            private saveOutputConditionAnnualWorkSchedule(data: model.OutputConditionAnnualWorkScheduleChar): void {
                var self = this;
                (nts.uk as any).characteristics.save("OutputConditionAnnualWorkScheduleChar_" + self.user.employeeId, data);
            }

            private findAllStandardSetting() {
                const self = this;
                const dfd = $.Deferred();
                service.findAllBySettingType(share.SelectionClassification.STANDARD, self.printFormat())
                  .done((dataArr: Array<share.SetOutputItemOfAnnualWorkSchDto>) => {
                    self.outputItem(_.orderBy(dataArr, ['cd'], ['asc']));
                    dfd.resolve();
                });
                return dfd.promise();
            }

            private findAllFreeSetting() {
                const self = this;
                const dfd = $.Deferred();
                service.findAllBySettingType(share.SelectionClassification.FREE_SETTING, self.printFormat())
                .done((dataArr: Array<share.SetOutputItemOfAnnualWorkSchDto>) => {
                    self.outputItemsFreeSetting(_.orderBy(dataArr, ['cd'], ['asc']));
                    dfd.resolve();
                });
                return dfd.promise();
            }
        }

        export class ListType {
            static EMPLOYMENT = 1;
            static Classification = 2;
            static JOB_TITLE = 3;
            static EMPLOYEE = 4;
        }

        export interface UnitModel {
            employeeId: string;
            code: string;
            name?: string;
            affiliationName?: string;
            isAlreadySetting?: boolean;
        }

        export class SelectType {
            static SELECT_BY_SELECTED_CODE = 1;
            static SELECT_ALL = 2;
            static SELECT_FIRST_ITEM = 3;
            static NO_SELECT = 4;
        }

        export interface UnitAlreadySettingModel {
            code: string;
            isAlreadySetting: boolean;
        }

        /** model */
        export module model {
            export interface PeriodDto {
                startYearMonth: Date;
                endYearMonth: Date;
            }

            export class OutputConditionAnnualWorkScheduleChar {
                /** A6_2 改頁選択 */
                breakPage: number;

                printFormat: number;

                excludeEmp: number;

                settingType: number;

                layoutId: string;

                constructor(breakPage: number
                          , printFormat: number
                          , excludeEmp: number
                          , settingType: number
                          , layoutId: string) {
                    this.breakPage = breakPage;
                    this.printFormat = printFormat;
                    this.excludeEmp = excludeEmp;
                    this.settingType = settingType;
                    this.layoutId = layoutId;
                }
            }

            export class EmployeeDto {
                employees: Array<UnitModel>;
                startYearMonth: string;
                endYearMonth: string;
                setItemsOutputLayoutId: string;
                breakPage: string;
                fiscalYear: string = '';
                printFormat: number = 0;
                curentMonth: string = '';
                baseDate: string = '';
                excludeEmp: number = 0;
                constructor() { }
            }
        }
    }
}