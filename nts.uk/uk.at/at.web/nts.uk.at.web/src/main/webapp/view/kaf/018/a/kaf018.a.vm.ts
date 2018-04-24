module nts.uk.at.view.kaf018.a.viewmodel {
    import text = nts.uk.resource.getText;
    import character = nts.uk.characteristics;
    var lstWkp = [];
    export class ScreenModel {
        targets: KnockoutObservableArray<any> = ko.observableArray([]);
        selectTarget: KnockoutObservable<any>;
        items: KnockoutObservableArray<any>;
        selectedId: KnockoutObservable<number>;
        enable: KnockoutObservable<boolean>;
        checked: KnockoutObservable<boolean>;
        selectedValue: KnockoutObservable<number> = ko.observable(0);
        isDailyComfirm: KnockoutObservable<boolean>;
        startDate: KnockoutObservable<Date>;
        endDate: KnockoutObservable<Date>;
        approvalConfirm: KnockoutObservable<model.ApprovalComfirm> = ko.observable(null);
        processingYm: KnockoutObservable<string> = ko.observable('');
        processYm: KnockoutObservable<number> = ko.observable(0);
        closureName: KnockoutObservable<string>;
        listEmployeeCode: KnockoutObservableArray<any> = ko.observableArray([]);

        //Control component
        baseDate: KnockoutObservable<Date>;
        selectType: KnockoutObservable<number> = ko.observable(1);
        multiSelectedWorkplaceId: KnockoutObservableArray<string>;
        alreadySettingList: KnockoutObservableArray<any>;
        treeGrid: any;
        isMultiSelect: KnockoutObservable<boolean> = ko.observable(true);
        isBindingTreeGrid: KnockoutObservable<boolean>;
        constructor() {
            var self = this;
            self.items = ko.observableArray([
                { code: 0, name: text('KAF018_12') },
                { code: 1, name: text('KAF018_13') }
            ]);
            self.isDailyComfirm = ko.observable(false);
            self.startDate = ko.observable(new Date());
            self.endDate = ko.observable(new Date());
            self.enable = ko.observable(true);
            self.checked = ko.observable(true);
            self.selectTarget = ko.observable(1);
            self.closureName = ko.observable('');

            //Control component
            self.baseDate = ko.observable(new Date());
            self.multiSelectedWorkplaceId = ko.observableArray([]);
            self.alreadySettingList = ko.observableArray([]);
            self.treeGrid = {
                isShowAlreadySet: false,
                isMultipleUse: true,
                isMultiSelect: self.isMultiSelect,
                treeType: 1,
                selectedWorkplaceId: self.multiSelectedWorkplaceId,
                baseDate: self.baseDate,
                selectType: 1,
                isShowSelectButton: true,
                isDialog: true,
                showIcon: true,
                alreadySettingList: self.alreadySettingList,
                maxRows: 10,
                tabindex: 1,
                systemType: 2
            };
            self.isBindingTreeGrid = ko.observable(true);
            //character.save('NewWorkplaceListOption', kaf018WorkplaceListOption);

            $('#tree-grid').ntsTreeComponent(self.treeGrid).done(() => {
                self.reloadData();
                $('#tree-grid').focusTreeGridComponent();
            });
            
            service.findAllClosure().done((data: any) => {
                self.targets(data.closuresDto);
                let closures = _.find(data.closuresDto, x => { return x.closureId === data.selectedClosureId });
                self.startDate(new Date(data.startDate));
                self.endDate(new Date(data.endDate));
                self.processYm = data.processingYm;
                self.processingYm(nts.uk.time.formatYearMonth(data.processingYm));
                self.baseDate(new Date(data.endDate));
                self.closureName(closures.closeName);
                self.listEmployeeCode(data.employeesCode);
                $('#tree-grid').ntsTreeComponent(self.treeGrid).done(() => {
                    self.reloadData();
                    $('#tree-grid').focusTreeGridComponent();
                });
            });
            
            self.selectTarget.subscribe((value) => {
                service.getApprovalStatusPerior(value, self.processYm).done((data: any) => {
                    self.startDate(new Date(data.startDate));
                    self.endDate(new Date(data.endDate));
                    self.listEmployeeCode(data.listEmployeeCode);
                    self.baseDate(new Date(data.endDate));
                    self.processingYm(nts.uk.time.formatYearMonth(data.yearMonth));
                    $('#tree-grid').ntsTreeComponent(self.treeGrid).done(() => {
                        self.reloadData();
                        $('#tree-grid').focusTreeGridComponent();
                    });
                });
            });
        }


        reloadData() {
            var self = this;
            lstWkp = self.flattenWkpTree(_.cloneDeep($('#tree-grid').getDataList()));
            nts.uk.ui.block.invisible();
            nts.uk.at.view.kaf018.a.service.getAll(lstWkp.map((wkp) => { return wkp.workplaceId; })).done((dataResults: Array<model.IApplicationApprovalSettingWkp>) => {
                self.alreadySettingList(dataResults.map((data) => { return { workplaceId: data.wkpId, isAlreadySetting: true}; }));
                self.multiSelectedWorkplaceId.valueHasMutated();
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

        gotoH() {
            var self = this;
            nts.uk.ui.windows.sub.modal("/view/kaf/018/h/index.xhtml");
        }

        choiceNextScreen() {
            var self = this;
            let params = {
                closureId: self.selectTarget(),
                processingYm: self.processingYm(),
                startDate: self.startDate(),
                endDate: self.endDate(),
                closureName: self.closureName(),
                listWorkplaceId: self.multiSelectedWorkplaceId(),
                isConfirmData: self.isDailyComfirm,
                listEmployeeCode: self.listEmployeeCode(),
            };
            if (self.selectedValue() == 0) {
                nts.uk.request.jump('/view/kaf/018/b/index.xhtml', params);
            } else {
                nts.uk.request.jump('/view/kaf/018/e/index.xhtml', params);
            }
        }
    }

    export module model {
        export class ApprovalComfirm {
            /** The closure id. */
            closureId: number;

            /** The start date. */
            startDate: string;

            /** The end date. */
            endDate: string;

            /** The closure name. */
            closureName: string;

            /** The closure date. */
            //処理年月
            closureDate: number;

            employeeCodes: Array<String>;

            constructor(closureId: number, closureName: string, startDate: string, endDate: string, closureDate: number, employeeCodes: Array<String>) {
                this.closureId = closureId;
                this.closureName = closureName;
                this.startDate = startDate;
                this.endDate = endDate;
                this.closureDate = closureDate;
                this.employeeCodes = employeeCodes;
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
    }
}