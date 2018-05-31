module nts.uk.at.view.kmf022.m.viewmodel {
    var lstWkp = [];

    export class ScreenModel {

        enumUseAtr = ko.observableArray([
            { code: 1, name: nts.uk.resource.getText("KAF022_100") },
            { code: 0, name: nts.uk.resource.getText("KAF022_101") }
        ]);
        prerequisiteUseAtr = ko.observableArray([
            { code: 0, name: nts.uk.resource.getText("KAF022_291") },
            { code: 1, name: nts.uk.resource.getText("KAF022_292") }
        ]);
        otAppSettingFlgAtr = ko.observableArray([
            { code: 0, name: nts.uk.resource.getText("KAF022_291") },
            { code: 1, name: nts.uk.resource.getText("KAF022_292") }
        ]);
        timeCalculationUseAtr = ko.observableArray([
            { code: 0, name: nts.uk.resource.getText("KAF022_295") },
            { code: 1, name: nts.uk.resource.getText("KAF022_296") }
        ]);
        atWorkAtr = ko.observableArray([
            { code: 0, name: nts.uk.resource.getText("KAF022_37") },
            { code: 1, name: nts.uk.resource.getText("KAF022_301") },
            { code: 2, name: nts.uk.resource.getText("KAF022_302") },
            { code: 3, name: nts.uk.resource.getText("KAF022_303") }
        ]);
        timeInputUseAtr = ko.observableArray([
            { code: 1, name: nts.uk.resource.getText("KAF022_308") },
            { code: 0, name: nts.uk.resource.getText("KAF022_309") }
        ]);

        lateOrLeaveAppCancelAtr = ko.observableArray([
            { code: 1, name: nts.uk.resource.getText("KAF022_311") },
            { code: 0, name: nts.uk.resource.getText("KAF022_312") }
        ]);

        lateOrLeaveAppSettingAtr = ko.observableArray([
            { code: 0, name: nts.uk.resource.getText("KAF022_313") },
            { code: 1, name: nts.uk.resource.getText("KAF022_314") }
        ]);
        isUpdateMode: KnockoutObservable<any>;
        kcp004WorkplaceListOption: any;
        baseDate: KnockoutObservable<Date>;
        selectedWorkplaceId: KnockoutObservableArray<String>;
        alreadySettingList: KnockoutObservableArray<any>;
        lstAppApprovalSettingWkp: Array<IApplicationApprovalSettingWkp>;
        selectedSetting: ApplicationApprovalSettingWkp;

        constructor() {
            var self = this;
            self.baseDate = ko.observable(new Date());
            self.selectedWorkplaceId = ko.observableArray([]);
            self.alreadySettingList = ko.observableArray([]);
            self.lstAppApprovalSettingWkp = [];
            self.selectedSetting = new ApplicationApprovalSettingWkp(null);
            self.kcp004WorkplaceListOption = {
                isShowAlreadySet: true,
                isMultipleUse: false,
                isMultiSelect: false,
                selectedWorkplaceId: self.selectedWorkplaceId,
                baseDate: self.baseDate,
                selectType: 3,
                isShowSelectButton: true,
                isDialog: false,
                alreadySettingList: self.alreadySettingList,
                maxRows: 10,
                tabindex: 1,
                systemType: 2,
                treeType: 1
            };
            self.selectedWorkplaceId.subscribe((val) => {
                let exsistedSetting = _.find(self.lstAppApprovalSettingWkp, (setting: IApplicationApprovalSettingWkp) => {
                    return val === setting.wkpId;
                });
                if (exsistedSetting) {
                    self.selectedSetting.update(exsistedSetting);
                } else {
                    self.selectedSetting.update(null);
                }
                self.selectedSetting.wkpId(val);
            });
            self.isUpdateMode = ko.computed(() => {
                for (let i = 0; i < self.alreadySettingList().length; i++) {
                    if (self.alreadySettingList()[i].workplaceId === self.selectedWorkplaceId()) {
                        return true;
                    }
                }
                return false;
            });
            $('#wkp-list').ntsTreeComponent(self.kcp004WorkplaceListOption).done(() => {
                self.reloadData();
                $('#wkp-list').focusTreeGridComponent();
            });
            $("#fixed-table-wkp-setting").ntsFixedTable({});
        }

        reloadData() {
            var self = this;
            lstWkp = self.flattenWkpTree(_.cloneDeep($('#wkp-list').getDataList()));
            self.selectedSetting.wkpId.valueHasMutated();
            nts.uk.ui.block.invisible();
            service.getAll(lstWkp.map((wkp) => { return wkp.workplaceId; })).done((dataResults: Array<IApplicationApprovalSettingWkp>) => {
                self.lstAppApprovalSettingWkp = dataResults;
                self.alreadySettingList(dataResults.map((data) => { return { workplaceId: data.wkpId, isAlreadySetting: true }; }));
                self.selectedWorkplaceId.valueHasMutated();
                nts.uk.ui.block.clear();
            });
        }

        flattenWkpTree(wkpTree) {
            return wkpTree.reduce((wkp, x) => {
                wkp = wkp.concat(x);
                if (x.childs && x.childs.length > 0) {
                    wkp = wkp.concat(this.flattenWkpTree(x.childs));
                    x.childs = [];
                }
                return wkp;
            }, []);
        }

        start(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            dfd.resolve();
            return dfd.promise();
        }

        openDialogCopy() {
            let self = this;
            let wkp = _.find(lstWkp, (wkp) => { return wkp.workplaceId == $('#wkp-list').getRowSelected()[0].workplaceId; });
            let param = {
                code: $('#wkp-list').getRowSelected()[0].workplaceCode,
                name: wkp ? wkp.name : '',
                targetType: 4,
                itemListSetting: nts.uk.ui._viewModel.content.viewmodelM.alreadySettingList().map((alreadySetting) => { return alreadySetting.workplaceId; }),
                baseDate: nts.uk.ui._viewModel.content.viewmodelM.baseDate()
            };
            nts.uk.ui.windows.setShared("CDL023Input", param);

            // open dialog CDL023
            nts.uk.ui.windows.sub.modal('com', '/view/cdl/023/a/index.xhtml').onClosed(() => {
                // get data respond
                let lstSelection: Array<string> = nts.uk.ui.windows.getShared("CDL023Output");
                let lstCommand = [];
                if (lstSelection && lstSelection.length > 0) {
                    nts.uk.ui.dialog.confirm({ messageId: "Msg_19" }).ifYes(() => {
                        _.forEach(lstSelection, (wkpIdToCopy) => {
                            let copySetting = ko.mapping.toJS(nts.uk.ui._viewModel.content.viewmodelM.selectedSetting);
                            copySetting.approvalFunctionSettingDtoLst.forEach((setting) => {
                                setting.breakInputFieldDisFlg = setting.breakInputFieldDisFlg ? 1 : 0;
                                setting.goOutTimeBeginDisFlg = setting.goOutTimeBeginDisFlg ? 1 : 0;
                                setting.requiredInstructionFlg = setting.requiredInstructionFlg ? 1 : 0;
                            });
                            copySetting.wkpId = wkpIdToCopy;
                            lstCommand.push(copySetting);
                        });
                        nts.uk.at.view.kmf022.m.service.update(lstCommand).done(() => {
                            nts.uk.ui.dialog.info({ messageId: "Msg_15" }).then(() => {
                                nts.uk.ui._viewModel.content.viewmodelM.reloadData();
                            });
                        })
                    });
                } else if (lstSelection && lstSelection.length == 0) {
                    nts.uk.ui.dialog.alert({ messageId: "Msg_888" })
                }
            });
        }

        update() {
            let command = ko.mapping.toJS(nts.uk.ui._viewModel.content.viewmodelM.selectedSetting);
            command.approvalFunctionSettingDtoLst.forEach((setting) => {
                setting.breakInputFieldDisFlg = setting.breakInputFieldDisFlg ? 1 : 0;
                setting.goOutTimeBeginDisFlg = setting.goOutTimeBeginDisFlg ? 1 : 0;
                setting.requiredInstructionFlg = setting.requiredInstructionFlg ? 1 : 0;
            });
            let lstCommand = [];
            lstCommand.push(command);
            nts.uk.at.view.kmf022.m.service.update(lstCommand).done(() => {
                nts.uk.ui.dialog.info({ messageId: "Msg_15" }).then(() => {
                    nts.uk.ui._viewModel.content.viewmodelM.reloadData();
                });
            }).fail(() => {
                nts.uk.ui.dialog.alert({ messageId: "Msg_59" });
            });
        }

        remove() {
            let command = ko.mapping.toJS(nts.uk.ui._viewModel.content.viewmodelM.selectedSetting);
            nts.uk.ui.dialog.confirm({ messageId: "Msg_18" }).ifYes(() => {
                nts.uk.at.view.kmf022.m.service.remove(command).done(() => {
                    nts.uk.ui.dialog.info({ messageId: 'Msg_16' }).then(function() {
                       nts.uk.ui._viewModel.content.viewmodelM.reloadData();
                    });
                });
            });
        }

    }

    export interface IApplicationApprovalSettingWkp {
        // 会社ID
        companyId: string;
        // 職場ID
        wkpId: string;
        // 選択
        selectionFlg: number;
        // 申請承認機能設定
        approvalFunctionSettingDtoLst: Array<IApprovalFunctionSetting>;
    }

    export class ApplicationApprovalSettingWkp {

        // 会社ID
        companyId: KnockoutObservable<string>;
        // 職場ID
        wkpId: KnockoutObservable<string>;
        // 職場Name
        wkpName: KnockoutObservable<string> = ko.observable('');
        // 選択
        selectionFlg: KnockoutObservable<number>;
        // 申請承認機能設定
        approvalFunctionSettingDtoLst: KnockoutObservableArray<ApprovalFunctionSetting>;

        constructor(param: IApplicationApprovalSettingWkp) {
            let self = this;
            self.companyId = ko.observable(param ? param.companyId : __viewContext.user.companyId);
            self.wkpId = ko.observable(param ? param.wkpId : '');
            self.wkpId.subscribe((val) => {
                let wkp = _.find(lstWkp, (wkp) => { return wkp.workplaceId == $('#wkp-list').getRowSelected()[0].workplaceId; });
                self.wkpName(wkp ? wkp.name : '');
            });
            self.selectionFlg = ko.observable(param ? param.selectionFlg : 0);
            self.approvalFunctionSettingDtoLst = ko.observableArray(this.initSettingList());
            if (param) {
                _.forEach(param.approvalFunctionSettingDtoLst, (setting: IApprovalFunctionSetting) => {
                    let foundSetting = _.find(self.approvalFunctionSettingDtoLst(), (targetSetting: ApprovalFunctionSetting) => {
                        return targetSetting.appType() == setting.appType;
                    });
                    if (foundSetting) {
                        foundSetting.update(setting);
                    }
                });
            }
        }

        update(param: IApplicationApprovalSettingWkp) {
            let self = this;
            self.companyId(param ? param.companyId : __viewContext.user.companyId);
            self.wkpId(param ? param.wkpId : '');
            self.selectionFlg(param ? param.selectionFlg : 0);
            self.approvalFunctionSettingDtoLst(this.initSettingList());
            if (param) {
                _.forEach(param.approvalFunctionSettingDtoLst, (setting: IApprovalFunctionSetting) => {
                    let foundSetting = _.find(self.approvalFunctionSettingDtoLst(), (targetSetting: ApprovalFunctionSetting) => {
                        return targetSetting.appType() == setting.appType;
                    });
                    if (foundSetting) {
                        foundSetting.update(setting);
                    }
                });
            }
        }

        initSettingList() {
            let settingList = [];
            settingList.push(new ApprovalFunctionSetting(null, 0));
            settingList.push(new ApprovalFunctionSetting(null, 1));
            settingList.push(new ApprovalFunctionSetting(null, 2));
            settingList.push(new ApprovalFunctionSetting(null, 3));
            settingList.push(new ApprovalFunctionSetting(null, 4));
            settingList.push(new ApprovalFunctionSetting(null, 6));
            settingList.push(new ApprovalFunctionSetting(null, 8));
            settingList.push(new ApprovalFunctionSetting(null, 9));
            settingList.push(new ApprovalFunctionSetting(null, 7));
            settingList.push(new ApprovalFunctionSetting(null, 10));
            settingList.push(new ApprovalFunctionSetting(null, 14));
            return settingList;
        }
    }

    export interface IApprovalFunctionSetting {
        //申請種類
        appType: number;
        //備考
        memo: string;
        //利用区分
        useAtr: number;
        //休出時間申請の事前必須設定
        prerequisiteForpauseFlg: number;
        // 残業申請の事前必須設定
        otAppSettingFlg: number;
        //時間年休申請の時刻計算を利用する
        holidayTimeAppCalFlg: number;
        // 遅刻早退取消申請の実績取消
        lateOrLeaveAppCancelFlg: number;
        //遅刻早退取消申請の実績取消を申請時に選択
        lateOrLeaveAppSettingFlg: number;
        //休憩入力欄を表示する
        breakInputFieldDisFlg: number;
        //休憩時間を表示する
        breakTimeDisFlg: number;
        //出退勤時刻初期表示区分
        atworkTimeBeginDisFlg: number;
        //実績から外出を初期表示する
        goOutTimeBeginDisFlg: number;
        // 時刻計算利用区分
        timeCalUseAtr: number;
        //時間入力利用区分
        timeInputUseAtr: number;
        //退勤時刻初期表示区分
        timeEndDispFlg: number;
        //指示が必須
        requiredInstructionFlg: number;
        //指示利用設定 - 指示区分
        instructionAtr: number;
        //指示利用設定 - 備考
        instructionMemo: string;
        //指示利用設定 - 利用区分
        instructionUseAtr: number;
    }

    export class ApprovalFunctionSetting {
        //申請種類
        appType: KnockoutObservable<number>;
        //備考
        memo: KnockoutObservable<string>;
        //利用区分
        useAtr: KnockoutObservable<number>;
        //休出時間申請の事前必須設定
        prerequisiteForpauseFlg: KnockoutObservable<number>;
        // 残業申請の事前必須設定
        otAppSettingFlg: KnockoutObservable<number>;
        //時間年休申請の時刻計算を利用する
        holidayTimeAppCalFlg: KnockoutObservable<number>;
        // 遅刻早退取消申請の実績取消
        lateOrLeaveAppCancelFlg: KnockoutObservable<number>;
        //遅刻早退取消申請の実績取消を申請時に選択
        lateOrLeaveAppSettingFlg: KnockoutObservable<number>;
        //休憩入力欄を表示する
        breakInputFieldDisFlg: KnockoutObservable<number>;
        //休憩時間を表示する
        breakTimeDisFlg: KnockoutObservable<number>;
        //出退勤時刻初期表示区分
        atworkTimeBeginDisFlg: KnockoutObservable<number>;
        //実績から外出を初期表示する
        goOutTimeBeginDisFlg: KnockoutObservable<number>;
        // 時刻計算利用区分
        timeCalUseAtr: KnockoutObservable<number>;
        //時間入力利用区分
        timeInputUseAtr: KnockoutObservable<number>;
        //退勤時刻初期表示区分
        timeEndDispFlg: KnockoutObservable<number>;
        //指示が必須
        requiredInstructionFlg: KnockoutObservable<number>;
        //指示利用設定 - 指示区分
        instructionAtr: KnockoutObservable<number>;
        //指示利用設定 - 備考
        instructionMemo: KnockoutObservable<string>;
        //指示利用設定 - 利用区分
        instructionUseAtr: KnockoutObservable<number>;

        constructor(param: IApprovalFunctionSetting, appTypeParam?: number) {
            let self = this;
            self.appType = ko.observable(param && !nts.uk.util.isNullOrUndefined(param.appType) ? param.appType : appTypeParam);
            self.memo = ko.observable(param && !nts.uk.util.isNullOrUndefined(param.appType) ? param.memo : '');
            self.useAtr = ko.observable(param && !nts.uk.util.isNullOrUndefined(param.appType) ? param.useAtr : 0);
            self.prerequisiteForpauseFlg = ko.observable(param && !nts.uk.util.isNullOrUndefined(param.appType) ? param.prerequisiteForpauseFlg : 0);
            self.otAppSettingFlg = ko.observable(param && !nts.uk.util.isNullOrUndefined(param.appType) ? param.otAppSettingFlg : 0);
            self.holidayTimeAppCalFlg = ko.observable(param && !nts.uk.util.isNullOrUndefined(param.appType) ? param.holidayTimeAppCalFlg : 0);
            self.lateOrLeaveAppCancelFlg = ko.observable(param && !nts.uk.util.isNullOrUndefined(param.appType) ? param.lateOrLeaveAppCancelFlg : 0);
            self.lateOrLeaveAppSettingFlg = ko.observable(param && !nts.uk.util.isNullOrUndefined(param.appType) ? param.lateOrLeaveAppSettingFlg : 0);
            self.breakInputFieldDisFlg = ko.observable(param && !nts.uk.util.isNullOrUndefined(param.appType) ? param.breakInputFieldDisFlg : 0);
            self.breakTimeDisFlg = ko.observable(param && !nts.uk.util.isNullOrUndefined(param.appType) ? param.breakTimeDisFlg : 0);
            self.atworkTimeBeginDisFlg = ko.observable(param && !nts.uk.util.isNullOrUndefined(param.appType) ? param.atworkTimeBeginDisFlg : 0);
            self.goOutTimeBeginDisFlg = ko.observable(param && !nts.uk.util.isNullOrUndefined(param.appType) ? param.goOutTimeBeginDisFlg : 0);
            self.timeCalUseAtr = ko.observable(param && !nts.uk.util.isNullOrUndefined(param.appType) ? param.timeCalUseAtr : 0);
            self.timeInputUseAtr = ko.observable(param && !nts.uk.util.isNullOrUndefined(param.appType) ? param.timeInputUseAtr : 0);
            self.timeEndDispFlg = ko.observable(param && !nts.uk.util.isNullOrUndefined(param.appType) ? param.timeEndDispFlg : 0);
            self.requiredInstructionFlg = ko.observable(param && !nts.uk.util.isNullOrUndefined(param.appType) ? param.requiredInstructionFlg : 0);
            self.instructionAtr = ko.observable(param && !nts.uk.util.isNullOrUndefined(param.appType) ? param.instructionAtr : 0);
            self.instructionMemo = ko.observable(param && !nts.uk.util.isNullOrUndefined(param.appType) ? param.instructionMemo : '');
            self.instructionUseAtr = ko.observable(param && !nts.uk.util.isNullOrUndefined(param.appType) ? param.instructionUseAtr : 0);
        }

        update(param: IApprovalFunctionSetting, appTypeParam?: number) {
            let self = this;
            self.appType(param && !nts.uk.util.isNullOrUndefined(param.appType) ? param.appType : appTypeParam);
            self.memo(param && !nts.uk.util.isNullOrUndefined(param.appType) ? param.memo : '');
            self.useAtr(param && !nts.uk.util.isNullOrUndefined(param.appType) ? param.useAtr : 0);
            self.prerequisiteForpauseFlg(param && !nts.uk.util.isNullOrUndefined(param.appType) ? param.prerequisiteForpauseFlg : 0);
            self.otAppSettingFlg(param && !nts.uk.util.isNullOrUndefined(param.appType) ? param.otAppSettingFlg : 0);
            self.holidayTimeAppCalFlg(param && !nts.uk.util.isNullOrUndefined(param.appType) ? param.holidayTimeAppCalFlg : 0);
            self.lateOrLeaveAppCancelFlg(param && !nts.uk.util.isNullOrUndefined(param.appType) ? param.lateOrLeaveAppCancelFlg : 0);
            self.lateOrLeaveAppSettingFlg(param && !nts.uk.util.isNullOrUndefined(param.appType) ? param.lateOrLeaveAppSettingFlg : 0);
            self.breakInputFieldDisFlg(param && !nts.uk.util.isNullOrUndefined(param.appType) ? param.breakInputFieldDisFlg : 0);
            self.breakTimeDisFlg(param && !nts.uk.util.isNullOrUndefined(param.appType) ? param.breakTimeDisFlg : 0);
            self.atworkTimeBeginDisFlg(param && !nts.uk.util.isNullOrUndefined(param.appType) ? param.atworkTimeBeginDisFlg : 0);
            self.goOutTimeBeginDisFlg(param && !nts.uk.util.isNullOrUndefined(param.appType) ? param.goOutTimeBeginDisFlg : 0);
            self.timeCalUseAtr(param && !nts.uk.util.isNullOrUndefined(param.appType) ? param.timeCalUseAtr : 0);
            self.timeInputUseAtr(param && !nts.uk.util.isNullOrUndefined(param.appType) ? param.timeInputUseAtr : 0);
            self.timeEndDispFlg(param && !nts.uk.util.isNullOrUndefined(param.appType) ? param.timeEndDispFlg : 0);
            self.requiredInstructionFlg(param && !nts.uk.util.isNullOrUndefined(param.appType) ? param.requiredInstructionFlg : 0);
            self.instructionAtr(param && !nts.uk.util.isNullOrUndefined(param.appType) ? param.instructionAtr : 0);
            self.instructionMemo(param && !nts.uk.util.isNullOrUndefined(param.appType) ? param.instructionMemo : '');
            self.instructionUseAtr(param && !nts.uk.util.isNullOrUndefined(param.appType) ? param.instructionUseAtr : 0);
        }
    }
}