module nts.uk.at.view.kdw007.a.viewmodel {
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    export class ScreenModel {
        isUpdateMode: KnockoutObservable<boolean>;

        listUseAtr: KnockoutObservableArray<any>;
        listTypeAtr: KnockoutObservableArray<any>;
        gridListColumns: KnockoutObservableArray<any>;
        lstErrorAlarm: KnockoutObservableArray<any>;
        selectedErrorAlarm: KnockoutObservable<any>;
        selectedErrorAlarmCode: KnockoutObservable<string>;
        tabs: KnockoutObservableArray<nts.uk.ui.NtsTabPanelModel>;
        selectedTab: KnockoutObservable<string>;
        //Tab 2
        displayListEmployment: KnockoutObservable<string> = ko.observable("");;
        displayListClassification: KnockoutObservable<string> = ko.observable("");
        displayListJobTitle: KnockoutObservable<string> = ko.observable("");
        displayListBusinessType: KnockoutObservable<string> = ko.observable("");
        //Tab3
        enumFilterByCompare: KnockoutObservableArray<any> = ko.observableArray([
            { code: 0, name: '予定と実績の比較をしない' },
            { code: 1, name: '予定と実績が同じものを抽出する' },
            { code: 2, name: '予定と実績が異なるものを抽出する' }
        ]);
        enumLogicalOperator: ko.observableArray([
            { code: 0, name: 'AND' },
            { code: 1, name: 'OR' }
        ]);
        //        enumFilterByCompare: KnockoutObservableArray<any> = ko.observableArray([
        //            { code: 0, name: nts.uk.resource.getText("Enum_FilterByCompare_NotCompare") },
        //            { code: 1, name: nts.uk.resource.getText("Enum_FilterByCompare_Extract_Same") },
        //            { code: 2, name: nts.uk.resource.getText("Enum_FilterByCompare_Extract_Different") }
        //        ]);
        displayListWorkTypePlan: KnockoutObservable<string> = ko.observable("");
        displayListWorkTimePlan: KnockoutObservable<string> = ko.observable("");
        displayListWorkTypeActual: KnockoutObservable<string> = ko.observable("");
        displayListWorkTimeActual: KnockoutObservable<string> = ko.observable("");
        // Tab 5
        lstApplicationType = ko.observableArray([
            { code: 0, name: "残業申請" },
            { code: 1, name: "休暇申請" },
            { code: 2, name: "勤務変更申請" },
            { code: 3, name: "出張申請" },
            { code: 4, name: "直行直帰申請" },
            { code: 6, name: "休出時間申請" },
            { code: 7, name: "打刻申請" },
            { code: 8, name: "時間年休申請" },
            { code: 9, name: "遅刻早退取消申請" },
            { code: 10, name: "振休振出申請" },
            { code: 11, name: "打刻申請（NR形式）" },
            { code: 12, name: "連続出張申請" },
            { code: 13, name: "出張申請オフィスヘルパー" },
            { code: 14, name: "３６協定時間申請" }
        ]);
        appTypeGridlistColumns = ko.observableArray([
            { headerText: 'コード', key: 'code', width: 100, hidden: true },
            { headerText: nts.uk.resource.getText("KDW007_82"), key: 'name', width: 300 },
        ]);

        constructor() {
            let self = this;
            self.isUpdateMode = ko.observable(false);
            self.listUseAtr = ko.observableArray([
                { code: '0', name: nts.uk.resource.getText("Enum_UseAtr_NotUse") },
                { code: '1', name: nts.uk.resource.getText("Enum_UseAtr_Use") }
            ]);
            self.listTypeAtr = ko.observableArray([
                { code: '0', name: nts.uk.resource.getText("Enum_ErrorAlarmClassification_Error") },
                { code: '1', name: nts.uk.resource.getText("Enum_ErrorAlarmClassification_Alarm") },
                { code: '2', name: nts.uk.resource.getText("Enum_ErrorAlarmClassification_Other") }
            ]);
            self.gridListColumns = ko.observableArray([
                { headerText: nts.uk.resource.getText("KDW007_6"), key: 'code', width: 45 },
                { headerText: nts.uk.resource.getText("KDW007_7"), key: 'name', width: 280 }
            ]);
            self.tabs = ko.observableArray([
                { id: 'tab-1', title: nts.uk.resource.getText("KDW007_9"), content: '.settingTab', enable: ko.observable(true), visible: ko.observable(true) },
                { id: 'tab-2', title: nts.uk.resource.getText("KDW007_83"), content: '.checkScopeTab', enable: ko.observable(true), visible: ko.observable(true) },
                { id: 'tab-3', title: nts.uk.resource.getText("KDW007_84"), content: '.conditionSettingTab1', enable: ko.observable(true), visible: ko.observable(true) },
                { id: 'tab-4', title: nts.uk.resource.getText("KDW007_85"), content: '.conditionSettingTab2', enable: ko.observable(true), visible: ko.observable(true) },
                { id: 'tab-5', title: nts.uk.resource.getText("KDW007_86"), content: '.applicationTab', enable: ko.observable(true), visible: ko.observable(true) }
            ]);
            self.selectedTab = ko.observable('tab-1');
            self.lstErrorAlarm = ko.observableArray([]);
            self.selectedErrorAlarm = ko.observable(ko.mapping.fromJS(new ErrorAlarmWorkRecord()));
            self.selectedErrorAlarmCode = ko.observable(null);
            self.selectedErrorAlarmCode.subscribe((code) => {
                if (code) {
                    let foundItem = _.find(self.lstErrorAlarm(), (item: ErrorAlarmWorkRecord) => {
                        return item.code == code;
                    });
                    if (foundItem) {
                        self.isUpdateMode(false);
                        self.changeSelectedErrorAlarm(foundItem);
                    }
                }
            });
            self.selectedErrorAlarm.subscribe((eral) => {
                eral.alCheckTargetCondition.getEmploymentTextDisplay().done((result) => {
                    self.displayListEmployment(result);
                });
                eral.alCheckTargetCondition.getClassificationTextDisplay().done((result) => {
                    self.displayListClassification(result);
                });
                eral.alCheckTargetCondition.getJobTitleTextDisplay().done((result) => {
                    self.displayListJobTitle(result);
                });
                eral.alCheckTargetCondition.getBusinessTypeTextDisplay().done((result) => {
                    self.displayListBusinessType(result);
                });
                eral.workTypeCondition.getTextDisplay("plan").done((result) => {
                    self.displayListWorkTypePlan(result);
                });
                eral.workTimeCondition.getTextDisplay("plan").done((result) => {
                    self.displayListWorkTimePlan(result);
                });
                eral.workTypeCondition.getTextDisplay("actual").done((result) => {
                    self.displayListWorkTypeActual(result);
                });
                eral.workTimeCondition.getTextDisplay("actual").done((result) => {
                    self.displayListWorkTimeActual(result);
                });
            });
        }

        changeSelectedErrorAlarm(foundItem) {
            let self = this;
            $(".nts-input").ntsError("clear");
            self.selectedErrorAlarm(ko.mapping.fromJS(new ErrorAlarmWorkRecord(foundItem)));
        }

        startPage(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            service.getAll().done((lstData) => {
                let sortedData = _.orderBy(lstData, ['code'], ['asc']);
                self.lstErrorAlarm(sortedData);
                self.selectedErrorAlarmCode(sortedData[0].code);
                dfd.resolve();
            });
            return dfd.promise();
        }

        update() {
            let self = this;
            self.selectedErrorAlarm().boldAtr() ? self.selectedErrorAlarm().boldAtr(1) : self.selectedErrorAlarm().boldAtr(0);
            if (self.selectedErrorAlarm().name().trim() == '') {
                self.selectedErrorAlarm().name('');
                $("#errorAlarmWorkRecordName").focus();
                return;
            }
            if (self.selectedErrorAlarm().displayMessage().trim() == '') {
                self.selectedErrorAlarm().displayMessage('');
                $("#messageDisplay").focus();
                return;
            }
            service.update(ko.mapping.toJS(self.selectedErrorAlarm)).done(() => {
                nts.uk.ui.dialog.info({ messageId: "Msg_15" });
                let currentItem = self.selectedErrorAlarmCode();
                self.startPage().done(() => {
                    self.selectedErrorAlarmCode(currentItem);
                });
            });
        }

        /* Tab 2 */
        chooseEmployment() {
            let self = this;
            setShared('CDL002Params', {
                isMultiple: true,
                selectedCodes: self.selectedErrorAlarm().alCheckTargetCondition.lstEmployment(),
                showNoSelection: false,
            }, true);

            nts.uk.ui.windows.sub.modal("com", "/view/cdl/002/a/index.xhtml").onClosed(function() {
                var output = getShared('CDL002Output');
                if (output) {
                    self.selectedErrorAlarm().alCheckTargetCondition.lstEmployment(output);
                    self.selectedErrorAlarm.valueHasMutated();
                }
            });
        }

        chooseClassification() {
            let self = this;
            setShared('inputCDL003', {
                selectedCodes: self.selectedErrorAlarm().alCheckTargetCondition.lstClassification(),
                showNoSelection: false,
                isMultiple: true
            }, true);

            nts.uk.ui.windows.sub.modal("com", '/view/cdl/003/a/index.xhtml').onClosed(function(): any {
                var output = getShared('outputCDL003');
                if (output) {
                    self.selectedErrorAlarm().alCheckTargetCondition.lstClassification(output);
                    self.selectedErrorAlarm.valueHasMutated();
                }
            })
        }

        chooseJobTitle() {
            let self = this;
            nts.uk.ui.windows.setShared('inputCDL004', {
                baseDate: moment()._d,
                selectedCodes: self.selectedErrorAlarm().alCheckTargetCondition.lstJobTitle(),
                showNoSelection: false,
                isMultiple: true
            }, true);

            nts.uk.ui.windows.sub.modal("com", '/view/cdl/004/a/index.xhtml').onClosed(function(): any {
                let output = nts.uk.ui.windows.getShared('outputCDL004');
                if (output) {
                    self.selectedErrorAlarm().alCheckTargetCondition.lstJobTitle(output);
                    self.selectedErrorAlarm.valueHasMutated();
                }
            })
        }

        chooseBusinessType() {
            let self = this,
                param = {
                    codeList: self.selectedErrorAlarm().alCheckTargetCondition.lstBusinessType()
                };
            nts.uk.ui.windows.setShared("CDL024", param);
            nts.uk.ui.windows.sub.modal("com", "/view/cdl/024/index.xhtml").onClosed(() => {
                let output = getShared("currentCodeList");
                if (output) {
                    self.selectedErrorAlarm().alCheckTargetCondition.lstBusinessType(output);
                    self.selectedErrorAlarm.valueHasMutated();
                }
            });
        }
        /* End Tab 2 */
        /* Tab 3 */
        chooseWorkType(planOrActual) {
            service.getAllWorkType().done((lstWorkType: Array<WorkTypeDto>) => {
                let self = this;
                let workTypeCondition = self.selectedErrorAlarm().workTypeCondition;
                let lstSelectedCode = planOrActual === "plan" ? workTypeCondition.planLstWorkType() : workTypeCondition.actualLstWorkType();
                setShared('KDL002_Multiple', true, true);
                setShared('KDL002_AllItemObj', _.map(lstWorkType, (workType) => { return workType.workTypeCode; }), true);
                setShared('KDL002_SelectedItemId', lstSelectedCode, true);
                nts.uk.ui.windows.sub.modal('/view/kdl/002/a/index.xhtml', { title: '乖離時間の登録＞対象項目', }).onClosed(() => {
                    let results = getShared('KDL002_SelectedNewItem');
                    if (results) {
                        if (planOrActual === "plan") {
                            self.selectedErrorAlarm().workTypeCondition.planLstWorkType(results.map((result) => { return result.code; }));
                        } else {
                            self.selectedErrorAlarm().workTypeCondition.actualLstWorkType(results.map((result) => { return result.code; }));
                        }
                        self.selectedErrorAlarm.valueHasMutated();
                    }
                });
            });
        }

        chooseWorkTime(planOrActual) {
            let self = this;
            service.getAllWorkTime().done((lstWorkTime) => {
                let workTimeCondition = self.selectedErrorAlarm().workTimeCondition;
                let lstSelectedCode = planOrActual === "plan" ? workTimeCondition.planLstWorkTime() : workTimeCondition.actualLstWorkTime();
                setShared('kml001multiSelectMode', true);
                nts.uk.ui.windows.setShared('kml001selectAbleCodeList', lstWorkTime.map((worktime) => { return worktime.code; }));
                nts.uk.ui.windows.setShared('kml001selectedCodeList', lstSelectedCode);
                nts.uk.ui.windows.sub.modal("/view/kdl/001/a/index.xhtml", { title: "割増項目の設定"}).onClosed(function() {
                    let results = getShared("kml001selectedCodeList");
                    if (results) {
                        if (planOrActual === "plan") {
                            self.selectedErrorAlarm().workTimeCondition.planLstWorkTime(results.sort());
                        } else {
                            self.selectedErrorAlarm().workTimeCondition.actualLstWorkTime(results.sort());
                        }
                        self.selectedErrorAlarm.valueHasMutated();
                    }
                });
            });
        }
        /* End Tab 3 */
        /* Tab 4 */
        openAtdItemConditionDialog() {
            let self = this,
                param = {
                };
            nts.uk.ui.windows.setShared("kdw007B", param);
            nts.uk.ui.windows.sub.modal("at", "/view/kdw/007/b/index.xhtml", { title: "計算式の設定"}).onClosed(() => {
                let output = getShared("kdw007BResult");
                if (output) {
                    debugger;
                }
            });
        }
        /* End Tab 4 */
    }

    export class ErrorAlarmWorkRecord {
        /* 会社ID */
        companyId: string;
        /* コード */
        code: string;
        /* 名称 */
        name: string;
        /* システム固定とする */
        fixedAtr: number;
        /* 使用する */
        useAtr: number;
        /* 区分 */
        typeAtr: number;
        /* 表示メッセージ */
        displayMessage: string;
        /* メッセージを太字にする */
        boldAtr: number;
        /* メッセージの色 */
        messageColor: string;
        /* エラーアラームを解除できる */
        cancelableAtr: number;
        /* エラー表示項目 */
        errorDisplayItem: number;
        /* チェック条件 */
        alCheckTargetCondition: AlarmCheckTargetCondition;
        /* 勤務種類の条件*/
        workTypeCondition: WorkTypeCondition;
        /* 就業時間帯の条件*/
        workTimeCondition: WorkTimeCondition;
        operatorBetweenPlanActual: number;
        lstApplicationTypeCode: Array<number>;
        operatorBetweenGroups: number;
        operatorGroup1: number;
        operatorGroup2: number;
        erAlAtdItemCondition: ErAlAtdItemCondition;

        constructor(param?: ErrorAlarmWorkRecord) {
            this.companyId = param && param.companyId ? param.companyId : '';
            this.code = param && param.code ? param.code : '';
            this.name = param && param.name ? param.name : '';
            this.fixedAtr = param && param.fixedAtr ? param.fixedAtr : 0;
            this.useAtr = param && param.useAtr ? param.useAtr : 0;
            this.typeAtr = param && param.typeAtr ? param.typeAtr : 0;
            this.displayMessage = param && param.displayMessage ? param.displayMessage : '';
            this.boldAtr = param && param.boldAtr ? param.boldAtr : 0;
            this.messageColor = param && param.messageColor ? param.messageColor : '';
            this.cancelableAtr = param && param.cancelableAtr ? param.cancelableAtr : 0;
            this.errorDisplayItem = param && param.errorDisplayItem ? param.errorDisplayItem : null;
            this.alCheckTargetCondition = param && param.alCheckTargetCondition ? param.alCheckTargetCondition : new AlarmCheckTargetCondition();
            this.workTypeCondition = param && param.workTypeCondition ? param.workTypeCondition : new WorkTypeCondition();
            this.workTimeCondition = param && param.workTimeCondition ? param.workTimeCondition : new WorkTimeCondition();
            this.operatorBetweenPlanActual = param && param.operatorBetweenPlanActual ? param.operatorBetweenPlanActual : 0;
            this.lstApplicationTypeCode = param && param.lstApplicationTypeCode ? param.lstApplicationTypeCode : [];
            this.operatorBetweenGroups = param && param.operatorBetweenGroups ? param.operatorBetweenGroups : 0;
            this.operatorGroup1 = param && param.operatorGroup1 ? param.operatorGroup1 : 0;
            this.operatorGroup2 = param && param.operatorGroup2 ? param.operatorGroup2 : 0;
            this.erAlAtdItemCondition = param && param.erAlAtdItemCondition ? param.erAlAtdItemCondition : new ErAlAtdItemCondition();
        }
    }

    export class AlarmCheckTargetCondition {
        /* 勤務種別でしぼり込む */
        filterByBusinessType: boolean;
        /* 職位でしぼり込む */
        filterByJobTitle: boolean;
        /* 雇用でしぼり込む */
        filterByEmployment: boolean;
        /* 分類でしぼり込む */
        filterByClassification: boolean;
        /* 対象勤務種別*/
        lstBusinessType: Array<string>;
        /* 対象職位 */
        lstJobTitle: Array<string>;
        /* 対象雇用*/
        lstEmployment: Array<string>;
        /* 対象分類 */
        lstClassification: Array<string>;

        constructor() {
            this.filterByBusinessType = false;
            this.filterByJobTitle = false;
            this.filterByEmployment = false;
            this.filterByClassification = false;
            this.lstBusinessType = [];
            this.lstJobTitle = [];
            this.lstEmployment = [];
            this.lstClassification = [];
        }

        getEmploymentTextDisplay() {
            let self = this;
            let dfd = $.Deferred();
            let lstEmpt = self.lstEmployment();
            if (lstEmpt && lstEmpt.length > 0) {
                let displayText = "";
                for (let i = 0; i < lstEmpt.length; i++) {
                    service.getEmploymentByCode(lstEmpt[i]).done((empt) => {
                        if (empt && empt.name) {
                            if (displayText !== "") {
                                displayText = displayText + ", " + empt.name;
                            } else {
                                displayText = displayText + empt.name;
                            }
                            if (i === lstEmpt.length - 1) {
                                dfd.resolve(displayText);
                            }
                        }
                    });
                }
            } else {
                dfd.resolve("");
            }
            return dfd;
        }

        getClassificationTextDisplay() {
            let self = this;
            let dfd = $.Deferred();
            let lstClss = self.lstClassification();
            if (lstClss && lstClss.length > 0) {
                let displayText = "";
                for (let i = 0; i < lstClss.length; i++) {
                    service.getClassificationByCode(lstClss[i]).done((clss) => {
                        if (clss && clss.name) {
                            if (displayText !== "") {
                                displayText = displayText + ", " + clss.name;
                            } else {
                                displayText = displayText + clss.name;
                            }
                            if (i === lstClss.length - 1) {
                                dfd.resolve(displayText);
                            }
                        }
                    });
                }
            } else {
                dfd.resolve("");
            }
            return dfd;
        }

        getJobTitleTextDisplay() {
            let self = this;
            let dfd = $.Deferred();
            let lstJobTitle = self.lstJobTitle();
            if (lstJobTitle && lstJobTitle.length > 0) {
                let displayText = "";
                let allJobTitle = [];
                service.findAllJobTitle().done((data) => {
                    if (data && data.length > 0) {
                        allJobTitle = data;
                    }
                }).then(() => {
                    for (let i = 0; i < lstJobTitle.length; i++) {
                        for (let jobTitle of allJobTitle) {
                            if (lstJobTitle[i] === jobTitle.id) {
                                if (displayText !== "") {
                                    displayText = displayText + ", " + jobTitle.name;
                                } else {
                                    displayText = displayText + jobTitle.name;
                                }
                                if (i === lstJobTitle.length - 1) {
                                    dfd.resolve(displayText);
                                }
                            }
                        }
                    }
                });
            } else {
                dfd.resolve("");
            }
            return dfd;
        }

        getBusinessTypeTextDisplay() {
            let self = this;
            let dfd = $.Deferred();
            let lstBussinessType = self.lstBusinessType();
            if (lstBussinessType && lstBussinessType.length > 0) {
                let displayText = "";
                for (let i = 0; i < lstBussinessType.length; i++) {
                    service.getBusinessTypeByCode(lstBussinessType[i]).done((businessType) => {
                        if (businessType && businessType.businessTypeName) {
                            if (displayText !== "") {
                                displayText = displayText + ", " + businessType.businessTypeName;
                            } else {
                                displayText = displayText + businessType.businessTypeName;
                            }
                            if (i === lstBussinessType.length - 1) {
                                dfd.resolve(displayText);
                            }
                        }
                    });
                }
            } else {
                dfd.resolve("");
            }
            return dfd;
        }

    }

    export class WorkTypeDto {
        workTypeCode: string;
        name: string;
    }

    export class WorkTypeCondition {

        useAtr: boolean;
        comparePlanAndActual: number;
        planFilterAtr: boolean;
        planLstWorkType: Array<string>;
        actualFilterAtr: boolean;
        actualLstWorkType: Array<string>;

        constructor() {
            this.useAtr = false;
            this.comparePlanAndActual = 0;
            this.planFilterAtr = false;
            this.planLstWorkType = [];
            this.actualFilterAtr = false;
            this.actualLstWorkType = [];
        }

        getTextDisplay(planOrActual) {
            let self = this;
            let dfd = $.Deferred();
            let lstWorkTypeCode = planOrActual === "plan" ? self.planLstWorkType() : self.actualLstWorkType();
            if (lstWorkTypeCode && lstWorkTypeCode.length > 0) {
                let displayText = "";
                let lstWorkType = [];
                service.getWorkTypeByListCode(lstWorkTypeCode).done((data) => {
                    if (data && data.length > 0) {
                        lstWorkType = data;
                    }
                }).then(() => {
                    for (let i = 0; i < lstWorkTypeCode.length; i++) {
                        for (let workType of lstWorkType) {
                            if (lstWorkTypeCode[i] === workType.workTypeCode) {
                                if (displayText !== "") {
                                    displayText = displayText + ", " + workType.name;
                                } else {
                                    displayText = displayText + workType.name;
                                }
                                if (i === lstWorkTypeCode.length - 1) {
                                    dfd.resolve(displayText);
                                }
                            }
                        }
                    }
                });
            } else {
                dfd.resolve("");
            }
            return dfd;
        }
    }

    export class WorkTypeDto {
        code: string;
        name: string;
    }

    export class WorkTimeCondition {

        useAtr: boolean;
        comparePlanAndActual: number;
        planFilterAtr: number;
        planLstWorkTime: Array<string>;
        actualFilterAtr: number;
        actualLstWorkTime: Array<string>;

        constructor() {
            this.useAtr = false;
            this.comparePlanAndActual = 0;
            this.planFilterAtr = 0;
            this.planLstWorkTime = [];
            this.actualFilterAtr = 0;
            this.actualLstWorkTime = [];
        }

        getTextDisplay(planOrActual) {
            let self = this;
            let dfd = $.Deferred();
            let lstWorkTimeCode = planOrActual === "plan" ? self.planLstWorkTime() : self.actualLstWorkTime();
            if (lstWorkTimeCode && lstWorkTimeCode.length > 0) {
                let displayText = "";
                let lstWorkTime = [];
                service.getWorkTimeByListCode(lstWorkTimeCode).done((data) => {
                    if (data && data.length > 0) {
                        lstWorkTime = data;
                    }
                }).then(() => {
                    for (let i = 0; i < lstWorkTimeCode.length; i++) {
                        for (let workTime of lstWorkTime) {
                            if (lstWorkTimeCode[i] === workTime.code) {
                                if (displayText !== "") {
                                    displayText = displayText + ", " + workTime.name;
                                } else {
                                    displayText = displayText + workTime.name;
                                }
                                if (i === lstWorkTimeCode.length - 1) {
                                    dfd.resolve(displayText);
                                }
                            }
                        }
                    }
                });
            } else {
                dfd.resolve("");
            }
            return dfd;
        }

    }

    export class ErAlAtdItemCondition {
        NO: number;
        conditionAtr: number;
        useAtr: boolean;
        uncountableAtdItem: number;
        countableAddAtdItems: Array<number>;
        countableSubAtdItems: Array<number>;
        conditionType: number;
        compareOperator: number;
        singleAddAtdItems: Array<number>;
        singleSubAtdItems: Array<number>;
        compareStartValue: number;
        compareEndValue: number;

        constructor() {
            this.NO = 0;
            this.conditionAtr = 0;
            this.useAtr = false;
            this.uncountableAtdItem = null;
            this.countableAddAtdItems = [];
            this.countableSubAtdItems = [];
            this.conditionType = 0;
            this.singleAddAtdItems = [];
            this.singleSubAtdItems = [];
            this.compareStartValue = 0;
            this.compareEndValue = 0;
            this.compareOperator = 0;
        }
    }

}