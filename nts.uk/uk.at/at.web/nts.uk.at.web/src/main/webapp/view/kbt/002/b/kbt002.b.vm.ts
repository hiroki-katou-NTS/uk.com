module nts.uk.at.view.kbt002.b {
    export module viewmodel {
        import alert = nts.uk.ui.dialog.alert;
        import modal = nts.uk.ui.windows.sub.modal;
        import setShared = nts.uk.ui.windows.setShared;
        import getShared = nts.uk.ui.windows.getShared;
        import block = nts.uk.ui.block;
        import dialog = nts.uk.ui.dialog;
        import getText = nts.uk.resource.getText;
        
        export class ScreenModel {
            execItemList: KnockoutObservableArray<any> = ko.observableArray([]);
            workplaceList: KnockoutObservableArray<any> = ko.observableArray([]);
            selectedExecCd: KnockoutObservable<string> = ko.observable('');
            currentExecItem: KnockoutObservable<ExecutionItem> = ko.observable(new ExecutionItem(null));

            // Screen mode
            isNewMode: KnockoutObservable<boolean> = ko.observable(false);

            // Display text
            wkpListText: KnockoutObservable<string> = ko.observable('');
            targetDateText: KnockoutObservable<string> = ko.observable('');

            // Enumeration list
            targetMonthList: KnockoutObservableArray<EnumConstantDto> = ko.observableArray([]);
            targetDailyPerfItemList: KnockoutObservableArray<EnumConstantDto> = ko.observableArray([]);
            repeatContentItemList: KnockoutObservableArray<EnumConstantDto> = ko.observableArray([]);
            monthDaysList: KnockoutObservableArray<EnumConstantDto> = ko.observableArray([]);

            settingEnum: ExecItemEnumDto;

            //targetGroupClass: KnockoutObservableArray<any>;
            constructor() {
                var self = this;
                /*
                  self.targetGroupClass = ko.observableArray([
                 new TargetGroupClass(0, getText('KBT002_162')),
                new TargetGroupClass(1, getText('KBT002_163'))
            ]);
                */
                self.selectedExecCd.subscribe(execItemCd => {
                    self.initProcExec();
                    if (nts.uk.text.isNullOrEmpty(execItemCd)) {
                        self.isNewMode(true);

                    } else {
                        // set update mode
                        self.isNewMode(false);
                        let data = _.filter(self.execItemList(), function(o) { return o.execItemCd == execItemCd; });
                        if (data[0]) {
                            self.currentExecItem(new ExecutionItem(data[0]));
                            //                            self.wkpListText(self.buildWorkplaceStr(_.sortBy(self.currentExecItem().workplaceList())));
                            self.buildWorkplaceStr(self.currentExecItem().workplaceList());
                            if (self.currentExecItem().perScheduleCls()) {
                                self.targetDateText(self.buildTargetDateStr(self.currentExecItem()));
                            }
                        }
                    }
                    setTimeout(function() { self.focusInput(); }, 100);
                    //                    self.currentExecItem().refDate(moment(new Date()).toDate());
                });
                
               
            }

            // Start page
            start() {
                let self = this;
                let dfd = $.Deferred<void>();
                service.getEnumDataList().done(function(setting) {
                    self.settingEnum = setting;
                    self.targetMonthList(setting.targetMonth);
                    self.targetDailyPerfItemList(setting.dailyPerfItems);
                    self.repeatContentItemList(setting.repeatContentItems);
                    self.monthDaysList(setting.monthDays);
                    service.findWorkplaceTree(moment(new Date()).toDate(), 2).done(function(dataList: Array<WorkplaceSearchData>) {
                        //                        self.workplaceList(_.sortBy(self.convertTreeToArray(dataList), function(wkp) {
                        //                            return parseInt(wkp.hierarchyCode);
                        //                        }));
                        self.workplaceList(self.convertTreeToArray(dataList));
                        $.when(self.getProcExecList()).done(() => {
                            dfd.resolve();
                        });
                    }).fail(function(res) {
                        nts.uk.ui.dialog.alert({ messageId: "Msg_7" });
                    });
                });

                // set ntsFixedTable style
                return dfd.promise();
            }

            /**
             * Button on screen
             */
            // 新規 button
            createProcExec() {
                let self = this;
                nts.uk.ui.errors.clearAll();
                self.isNewMode(true);
                self.selectedExecCd('');
            }

            // 登録 button
            saveProcExec() {
                let self = this;

                // validate
                if (self.validate()) {
                    return;
                }

                // get JsObject
                //                let command: any = ko.toJS(self.currentExecItem);
                let command: any = self.toJsonObject();
                //                command.refDate = command.refDate == '' ? null : command.refDate;
                nts.uk.ui.block.grayout();

                // insert or update process execution
                service.saveProcExec(command).done(function(savedProcExecCd) {
                    nts.uk.ui.block.clear();

                    // notice success
                    nts.uk.ui.dialog.info({ messageId: "Msg_15" }).then(() => {
                        // Get process execution list
                        self.getProcExecList(savedProcExecCd);
                        setTimeout(function() { self.focusInput(); }, 100);
                    });
                }).fail((res: any) => {
                    nts.uk.ui.block.clear();
                    self.showMessageError(res);
                });
            }

            // 削除 button
            delProcExec() {
                let self = this,
                    currentItem = self.currentExecItem(),
                    oldIndex = _.findIndex(self.execItemList(), x => x.execItemCd == currentItem.execItemCd()),
                    lastIndex = self.execItemList().length - 1;

                // show message confirm
                nts.uk.ui.dialog.confirm({ messageId: 'Msg_18' }).ifYes(() => {
                    let command: any = {};
                    command.execItemCd = currentItem.execItemCd();

                    nts.uk.ui.block.grayout();
                    service.deleteProcExec(command).done(() => {
                        // Get process exection list
                        nts.uk.ui.dialog.info({ messageId: "Msg_16" }).then(function() {
                            $.when(self.getProcExecList()).done(() => {
                                if (self.execItemList().length > 0) {
                                    if (oldIndex == lastIndex) {
                                        oldIndex--;
                                    }
                                    self.selectedExecCd(self.execItemList()[oldIndex].execItemCd);
                                }
                            });
                        });
                    }).fail((res: any) => {
                        //                        nts.uk.ui.dialog.bundledErrors(res);
                        self.showMessageError(res);
                    })
                    nts.uk.ui.block.clear();
                });
            }

            // 実行タスク設定 button
            openDialogC() {
                let self = this;
                block.grayout();
                setShared('inputDialogC',
                    {
                        execItemCd: self.currentExecItem().execItemCd(),
                        execItemName: self.currentExecItem().execItemName(),
                        //                            repeatContent: self.repeatContentItemList(),
                        //                            monthDays: self.monthDaysList()
                    });
                modal("/view/kbt/002/c/index.xhtml").onClosed(function() {
                    block.clear();
                });
            }

            // 職場選択 button
            openDialogCDL008() {
                let self = this;
                block.grayout();
                let canSelected = self.currentExecItem().workplaceList() ? self.currentExecItem().workplaceList() : [];
                setShared('inputCDL008', { 
                baseDate: moment(new Date()).toDate(), 
                isMultiple: true, 
                selectedCodes: canSelected,
                selectedSystemType: 2,
                isrestrictionOfReferenceRange: true,
                showNoSelection:  false,
                isShowBaseDate:  false });
                modal("com", "/view/cdl/008/a/index.xhtml").onClosed(function() {
                    block.clear();
                    let data = getShared('outputCDL008');
                    if (data == null || data === undefined) {
                        return;
                    } else {
                        self.currentExecItem().workplaceList(data);
                        self.buildWorkplaceStr(data);
                    }
                });
            }

            /*
             * Common functions
             */
            private getProcExecList(savedExecItemCd?: string): JQueryPromise<void> {
                let self = this;
                let dfd = $.Deferred<void>();

                self.execItemList([]);
                service.getProcExecList().done(function(procExecList) {
                    if (procExecList && procExecList.length > 0) {
                        self.execItemList(procExecList);
                        if (nts.uk.text.isNullOrEmpty(savedExecItemCd)) {
                            self.selectedExecCd(procExecList[0].execItemCd);
                        } else {
                            self.selectedExecCd(savedExecItemCd);
                        }

                    } else {
                        self.createProcExec();
                    }
                    dfd.resolve();
                });
                return dfd.promise();
            }

            private initProcExec() {
                let self = this;
                nts.uk.ui.errors.clearAll();
                self.currentExecItem(new ExecutionItem(null));
                self.wkpListText('');
                self.targetDateText('');
                
            }

            private buildWorkplaceStr(wkpIdList: any) {
                let self = this;
                var wkpText;
                //                wkpList = _.sortBy(wkpList, 'hierarchyCode');
                if (wkpIdList) {
                    if (wkpIdList.length == 0) {
                        wkpText = '';
                    } else if (wkpIdList.length == 1) {
                        var wkpId = wkpIdList[0];
                        var wkp = _.find(self.workplaceList(), function(o) { return o.workplaceId == wkpId; });
                        wkpText = wkp.hierarchyCode + ' ' + wkp.name;
                    } else {
                        var workplaceList = [];
                        //                        var firstWkpId = wkpIdList[0];
                        //                        var lastWkpId = wkpIdList[wkpIdList.length - 1];
                        //                        var firstWkp = _.find(self.workplaceList(), function(o) { return o.workplaceId == firstWkpId; });
                        //                        var lastWkp = _.find(self.workplaceList(), function(o) { return o.workplaceId == lastWkpId; });
                        _.each(wkpIdList, wkpId => {
                            var workplace = _.find(self.workplaceList(), function(o) { return o.workplaceId == wkpId; });
                            workplaceList.push(workplace);
                        });
                        workplaceList = _.sortBy(workplaceList, function(wkp) {
                            return parseInt(wkp.hierarchyCode);
                        });
                        var firstWkp = workplaceList[0];
                        var lastWkp = workplaceList[workplaceList.length - 1];
                        wkpText = firstWkp.hierarchyCode + ' ' + firstWkp.name + ' ～ ' + lastWkp.hierarchyCode + ' ' + lastWkp.name;
                    }
                }
                self.wkpListText(wkpText);
                //                self.wkpListText(self.buildWorkplaceStr(_.sortBy(self.currentExecItem().workplaceList())));
                //                return '';
            }
            
            private buildTargetDateStr(execItem : any) {
                let self = this;
                var startTargetDate;
                var endTargetDate;
                let today = moment();
                var currentDate = moment([today.year(), today.month(), today.date()]);
                let targetDateStr = getText('KBT002_25') + today.format("YYYY/MM/DD") + getText('KBT002_26');

                // Calculate start target date
                if (execItem.targetMonth() == 0) {
                    // If target date < current date then set date by current date
                    startTargetDate = moment([today.year(), today.month(), execItem.targetDate()]);
                    /*
                    if (startTargetDate.diff(currentDate, 'days') < 0) {
                        startTargetDate = currentDate;
                    }
                    */
                } else if (execItem.targetMonth() == 1) {
                    startTargetDate = moment([today.year(), today.month(), execItem.targetDate()]).add(1, 'months');
                    
                } else if (execItem.targetMonth() == 2) {
                    startTargetDate = moment([today.year(), today.month(), execItem.targetDate()]).add(2, 'months');
                }
                if(startTargetDate._isValid){
                    targetDateStr += startTargetDate.format("YYYY/MM/DD");
                }
                

                // Calculate end target date
                if (self.currentExecItem().targetDate() == 1) {
                    if(execItem.creationPeriod() == 1){
                        endTargetDate = startTargetDate.endOf('month');
                    }else{
                        endTargetDate = startTargetDate.add(execItem.creationPeriod()-1, 'months').endOf('month');
                    }
                } else {
                    endTargetDate = startTargetDate.add(execItem.creationPeriod(), 'months');
                }
                targetDateStr += '～';
                 if(startTargetDate._isValid){
                    targetDateStr += endTargetDate.format("YYYY/MM/DD");
                }
                 targetDateStr += 'です';
                
                return targetDateStr;
            }

            private validate(): boolean {
                let self = this;

                // clear error
                self.clearError();
                // validate
                //                $(".nts-input ").ntsEditor('validate');
                $("#execItemCd").ntsEditor('validate');
                $("#execItemName").ntsEditor('validate');
                if (self.currentExecItem().perScheduleCls()) {
                    $("#targetDate").ntsEditor('validate');
                    $("#creationPeriod").ntsEditor('validate');
                }
                return $('.nts-input').ntsError('hasError');
            }

            /**
             * clearError
             */
            private clearError() {
                nts.uk.ui.errors.clearAll();
            }

            /**
             * toJsonObject
             */
            private toJsonObject(): any {
                let self = this;

                // to JsObject
                let command: any = {};
                command.newMode = self.isNewMode();
                command.companyId = self.currentExecItem().companyId();
                command.execItemCd = self.currentExecItem().execItemCd();
                command.execItemName = self.currentExecItem().execItemName();
                command.perScheduleCls = self.currentExecItem().perScheduleCls();
                command.targetMonth = self.currentExecItem().targetMonth();
                command.targetDate = self.currentExecItem().targetDate();
                command.creationPeriod = self.currentExecItem().creationPeriod();
                command.creationTarget = self.currentExecItem().creationTarget();
                command.recreateWorkType = self.currentExecItem().recreateWorkType();
                command.manualCorrection = self.currentExecItem().manualCorrection();
                command.createEmployee = self.currentExecItem().createEmployee();
                command.recreateTransfer = self.currentExecItem().recreateTransfer();
                command.dailyPerfCls = self.currentExecItem().dailyPerfCls();
                command.dailyPerfItem = self.currentExecItem().dailyPerfItem();
                command.midJoinEmployee = self.currentExecItem().midJoinEmployee();
                command.reflectResultCls = self.currentExecItem().reflectResultCls();
                command.monthlyAggCls = self.currentExecItem().monthlyAggCls();
                command.indvAlarmCls = self.currentExecItem().indvAlarmCls();
                command.indvMailPrin = self.currentExecItem().indvMailPrin();
                command.indvMailMng = self.currentExecItem().indvMailMng();
                command.wkpAlarmCls = self.currentExecItem().wkpAlarmCls();
                command.wkpMailMng = self.currentExecItem().wkpMailMng();
                command.execScopeCls = self.currentExecItem().execScopeCls();
                command.refDate = nts.uk.text.isNullOrEmpty(self.currentExecItem().refDate()) ? null : new Date(self.currentExecItem().refDate());
                command.workplaceList = self.currentExecItem().workplaceList();
                command.recreateTypeChangePerson = self.currentExecItem().recreateTypeChangePerson();
                command.recreateTransfers =  self.currentExecItem().recreateTransfers();
                return command;
            }

            /**
             * showMessageError
             */
            private showMessageError(res: any) {
                if (res.businessException) {
                    nts.uk.ui.dialog.alertError({ messageId: res.messageId, messageParams: res.parameterIds });
                }
            }

            private focusInput() {
                let self = this;
                if (self.isNewMode()) {
                    $('#execItemCd').focus();
                } else {
                    $('#execItemName').focus();
                }
            }

            /**
             * Convert tree data to array.
             */
            private convertTreeToArray(dataList: Array<any>): Array<any> {
                let self = this;
                let res = [];
                _.forEach(dataList, function(item) {
                    if (item.childs && item.childs.length > 0) {
                        res = res.concat(self.convertTreeToArray(item.childs));
                    }
                    res.push({ workplaceId: item.workplaceId, hierarchyCode: item.hierarchyCode, name: item.name });
                })
                return res;
            }
        }

        export interface IExecutionItem {
            companyId: string;
            execItemCd: string;
            execItemName: string;
            perScheduleCls: boolean;
            targetMonth: number;
            targetDate: number;
            creationPeriod: number;
            creationTarget: number;
            recreateWorkType: boolean;
            manualCorrection: boolean;
            createEmployee: boolean;
            recreateTransfer: boolean;
            dailyPerfCls: boolean;
            dailyPerfItem: number;
            //            lastProcDate:        string;
            midJoinEmployee: boolean;
            reflectResultCls: boolean;
            monthlyAggCls: boolean;
            indvAlarmCls: boolean;
            indvMailPrin: boolean;
            indvMailMng: boolean;
            wkpAlarmCls: boolean;
            wkpMailMng: boolean;
            execScopeCls: number;
            refDate: string;
            workplaceList: Array<string>;
            recreateTypeChangePerson: boolean;
            recreateTransfers: boolean;
        }

        export class ExecutionItem {
            companyId: KnockoutObservable<string> = ko.observable('');
            execItemCd: KnockoutObservable<string> = ko.observable('');
            execItemName: KnockoutObservable<string> = ko.observable('');
            perScheduleCls: KnockoutObservable<boolean> = ko.observable(false);
            targetMonth: KnockoutObservable<number> = ko.observable(null);
            targetDate: KnockoutObservable<number> = ko.observable(null);
            creationPeriod: KnockoutObservable<number> = ko.observable(null);
            creationTarget: KnockoutObservable<number> = ko.observable(null);
            recreateWorkType: KnockoutObservable<boolean> = ko.observable(false);
            manualCorrection: KnockoutObservable<boolean> = ko.observable(false);
            createEmployee: KnockoutObservable<boolean> = ko.observable(false);
            recreateTransfer: KnockoutObservable<boolean> = ko.observable(false);
            dailyPerfCls: KnockoutObservable<boolean> = ko.observable(false);
            dailyPerfItem: KnockoutObservable<number> = ko.observable(null);
            midJoinEmployee: KnockoutObservable<boolean> = ko.observable(false);
            reflectResultCls: KnockoutObservable<boolean> = ko.observable(false);
            monthlyAggCls: KnockoutObservable<boolean> = ko.observable(false);
            indvAlarmCls: KnockoutObservable<boolean> = ko.observable(false);
            indvMailPrin: KnockoutObservable<boolean> = ko.observable(false);
            indvMailMng: KnockoutObservable<boolean> = ko.observable(false);
            wkpAlarmCls: KnockoutObservable<boolean> = ko.observable(false);
            wkpMailMng: KnockoutObservable<boolean> = ko.observable(false);
            execScopeCls: KnockoutObservable<number> = ko.observable(null);
            refDate: KnockoutObservable<string> = ko.observable('');
            workplaceList: KnockoutObservableArray<string> = ko.observableArray([]);
            recreateTypeChangePerson: KnockoutObservable<boolean> = ko.observable(false);
            recreateTransfers: KnockoutObservable<boolean> = ko.observable(false);
            constructor(param: IExecutionItem) {
                let self = this;
                if (param && param != null) {
                    self.companyId(param.companyId);
                    self.execItemCd(param.execItemCd || '');
                    self.execItemName(param.execItemName || '');
                    self.perScheduleCls(param.perScheduleCls || false);
                    self.targetMonth(param.targetMonth);
                    self.targetDate(param.targetDate);
                    self.creationPeriod(param.creationPeriod);
                    self.creationTarget(param.creationTarget);
                    self.recreateWorkType(param.recreateWorkType || false);
                    self.manualCorrection(param.manualCorrection || false);
                    self.createEmployee(param.createEmployee || false);
                    self.recreateTransfer(param.recreateTransfer || false);
                    self.dailyPerfCls(param.dailyPerfCls || false);
                    self.dailyPerfItem(param.dailyPerfItem);
                    self.midJoinEmployee(param.midJoinEmployee || false);
                    self.reflectResultCls(param.reflectResultCls || false);
                    self.monthlyAggCls(param.monthlyAggCls || false);
                    self.indvAlarmCls(param.indvAlarmCls || false);
                    self.indvMailPrin(param.indvMailPrin || false);
                    self.indvMailMng(param.indvMailMng || false);
                    self.wkpAlarmCls(param.wkpAlarmCls || false);
                    self.wkpMailMng(param.wkpMailMng || false);
                    self.execScopeCls(param.execScopeCls);
                    self.refDate(param.refDate || moment().format("YYYY/MM/DD"));
                    self.workplaceList(param.workplaceList || []);
                    self.recreateTypeChangePerson(param.recreateTypeChangePerson||false);
                    self.recreateTransfers(param.recreateTransfers||false)
                } else {
                    self.companyId('');
                    self.execItemCd('');
                    self.execItemName('');
                    self.perScheduleCls(false);
                    self.targetMonth(0);
                    self.targetDate(null);
                    self.creationPeriod(null);
                    self.creationTarget(0);
                    self.recreateWorkType(false);
                    self.manualCorrection(false);
                    self.createEmployee(false);
                    self.recreateTransfer(false);
                    self.dailyPerfCls(false);
                    self.dailyPerfItem(0);
                    self.midJoinEmployee(false);
                    self.reflectResultCls(false);
                    self.monthlyAggCls(false);
                    self.indvAlarmCls(false);
                    self.indvMailPrin(false);
                    self.indvMailMng(false);
                    self.wkpAlarmCls(false);
                    self.wkpMailMng(false);
                    self.execScopeCls(0);
                    self.refDate(moment().format("YYYY/MM/DD"));
                    self.workplaceList([]);
                    self.recreateTypeChangePerson(false);
                    self.recreateTransfers(false)
                }
                
                  self.targetDate.subscribe(x=>{
                    var data =  this;
                       __viewContext.viewModel.targetDateText(__viewContext.viewModel.buildTargetDateStr(data)); 
                });
                   self.creationPeriod.subscribe(x=>{
                    var data =  this;
                       __viewContext.viewModel.targetDateText(__viewContext.viewModel.buildTargetDateStr(data)); 
                });
                 self.targetMonth.subscribe(x=>{
                    var data =  this;
                       __viewContext.viewModel.targetDateText(__viewContext.viewModel.buildTargetDateStr(data)); 
                });
            }
        }

        export interface ExecItemEnumDto {
            targetMonth: Array<EnumConstantDto>;
            dailyPerfItems: Array<EnumConstantDto>;
            repeatContentItems: Array<EnumConstantDto>;
            monthDays: Array<EnumConstantDto>;
        }

        export interface EnumConstantDto {
            value: number;
            fieldName: string;
            localizedName: string;
        }

        export class WorkplaceSearchData {
            workplaceId: string;
            code: string;
            name: string;
        }

        export class TargetGroupClass {
            targetId: number;
            targetName: string;
            constructor(targetId, targetName) {
                var self = this;
                self.targetId = targetId;
                self.targetName = targetName;
            }
        }

    }
}