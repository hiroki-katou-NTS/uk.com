/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.ksm008.f {
    import setShared = nts.uk.ui.windows.setShared;
    import getShare = nts.uk.ui.windows.getShared;

    const PATH_API = {
        /**
         * 初期起動情報を取得する
         * 初期起動の情報取得する
         */
        getStartupInfoBanHoliday: "screen/at/ksm008/f/getStartupInfoBanHoliday",

        /**
         * 組織の社員情報を取得する
         */
        getEmployeeInfo: "screen/at/ksm008/c/getEmployeeInfo",

        /**
         * 同時休日禁止リストを取得する
         * 同日休日禁止リストを取得する
         */
        getAllBanHolidayTogether: "banholidaytogether/getAllBanHolidayTogether",

        /**
         * 同日休日禁止明細を表示する
         * コードを指定して同日休日禁止を取得する
         */
        getBanHolidayByCode: "banholidaytogether/getBanHolidayByCode",

        insert: "banholidaytogether/insert",
        update: "banholidaytogether/update",
        delete: "banholidaytogether/delete",
    };

    @bean()
    export class KSM008FViewModel extends ko.ViewModel {
        //init -> getEmployeeInfo -> selectedCode.subscribe -> getDetail -> moveItemToRight
        //insert -> getAllBanHolidayTogether -> selectedCode.subscribe -> getDetail -> moveItemToRight
        //update -> getAllBanHolidayTogether -> selectedCode.subscribe -> getDetail -> moveItemToRight
        //deleta -> getAllBanHolidayTogether -> selectedCode.subscribe -> getDetail -> moveItemToRight
        //openDiaglogKDL046 -> getEmployeeInfo -> getAllBanHolidayTogether -> selectedCode.subscribe -> getDetail -> moveItemToRight

        // Flag
        deleteEnable: KnockoutObservable<boolean> = ko.observable(false);
        updateEnable: boolean = false;
        modifyEnable: KnockoutObservable<boolean> = ko.observable(false);
        isVisible: KnockoutObservable<boolean>;
        isClearReference: boolean = false;

        // Init
        //getStartupInfoBanHoliday
        code: string = ""; //勤務予定のアラームチェック条件.コード
        conditionName: KnockoutObservable<string> = ko.observable(""); //条件名
        explanation: KnockoutObservable<string> = ko.observable(""); //サブ条件リスト.説明
        unit: number = null; //対象組織情報.単位
        workplaceId: string = ""; //対象組織情報.職場ID
        workplaceGroupId: string = ""; //対象組織情報.職場グループID
        orgCode: KnockoutObservable<string> = ko.observable(""); //組織の表示情報.コード
        orgDisplayName: KnockoutObservable<string> = ko.observable(""); //組織の表示情報.表示名
        listBanHolidayTogetherCodeName: KnockoutObservableArray<BanHolidayTogetherCodeName> = ko.observableArray([]); //同日休日禁止

        //getEmployeeInfo
        originalSelectableEmployeeList = new Array<any>([]);
        selectableEmployeeList: KnockoutObservableArray<PersonInfo> = ko.observableArray([]); //社員データ管理情報
        selectedableCodes: KnockoutObservableArray<string> = ko.observableArray([]);
        selectableEmployeeComponentOption: any;

        //getBanHolidayByCode
        checkDayReference: KnockoutObservable<boolean> = ko.observable(false); //稼働日のみとする
        selectedWorkDayReference: KnockoutObservable<number> = ko.observable(null); //稼働日の参照先
        businessDaysCalendarTypeEnum: KnockoutObservableArray<ComboBoxModel> = ko.observableArray([]); //営業日カレンダー種類
        minOfWorkingEmpTogether: KnockoutObservable<number> = ko.observable(); //最低限出勤すべき人数
        workplaceInfoId: KnockoutObservable<string> = ko.observable(""); //職場ID
        classificationOrWorkplaceCode: KnockoutObservable<string> = ko.observable(""); //分類コード / 職場コード
        classificationOrWorkplaceName: KnockoutObservable<string> = ko.observable(""); //分類名称 / 職場表示名
        targetEmployeeList: KnockoutObservableArray<PersonInfo> = ko.observableArray([]); //同日の休日取得を禁止する社員

        selectedTargetCode: KnockoutObservableArray<string> = ko.observableArray([]);
        targetEmployeeComponentOption: any;

        selectedCodeDisplay: KnockoutObservable<string> = ko.observable('');
        selectedCode: KnockoutObservable<string> = ko.observable('');
        selectedName: KnockoutObservable<string> = ko.observable('');

        codeAndConditionName: KnockoutObservable<string>;

        constructor(params: any) {
            super();
            const vm = this;

            if(params == null){
                vm.$jump('/view/ksm/008/a/index.xhtml');
            }
            vm.code = params.code;

            vm.codeAndConditionName = ko.computed(() => {
                return vm.code + " " + vm.conditionName();
            }, this);

            vm.isVisible = ko.computed(() => {
                return vm.checkDayReference() && vm.selectedWorkDayReference() != 0;
            });

            vm.initData().done(() => {
                vm.getEmployeeInfo().done(() => {
                    if (!_.isEmpty(vm.listBanHolidayTogetherCodeName())) {
                        vm.selectedCode(vm.listBanHolidayTogetherCodeName()[0].banHolidayTogetherCode);
                    }
                });
            });

            vm.selectableEmployeeComponentOption = {
                isMultiSelect: true,
                listType: 4,
                employeeInputList: vm.selectableEmployeeList,
                selectType: 1,
                selectedCode: vm.selectedableCodes,
                isDialog: true,
                isShowNoSelectRow: false,
                isShowSelectAllButton: false,
                disableSelection: false,
                maxRows: 10
            };

            vm.targetEmployeeComponentOption = {
                listType: 4,
                employeeInputList: vm.targetEmployeeList,
                isMultiSelect: true,
                selectType: 1,
                selectedCode: vm.selectedTargetCode,
                isDialog: true,
                isShowNoSelectRow: false,
                isShowSelectAllButton: false,
                disableSelection: false,
                maxRows: 10
            };
        }

        initData() {
            const vm = this;
            let dfd = $.Deferred();

            vm.$blockui("invisible");
            vm.$ajax(PATH_API.getStartupInfoBanHoliday, {code: vm.code})
                .done(data => {
                    if (data) {
                        vm.conditionName(data.conditionName);

                        let explanation: string = "";
                        _.forEach(data.explanationList, (item) => {
                            explanation += item + "\n";
                        });
                        explanation = explanation.replace(/\\r/g, "\r");
                        explanation = explanation.replace(/\\n/g, "\n");
                        vm.explanation(explanation);

                        vm.unit = data.unit;
                        vm.workplaceId = data.workplaceId;
                        vm.workplaceGroupId = data.workplaceGroupId;
                        vm.orgCode(data.orgCode);
                        vm.orgDisplayName(data.orgDisplayName);
                        vm.businessDaysCalendarTypeEnum(data.businessDaysCalendarTypeEnum);

                        if (!_.isEmpty(data.listBanHolidayTogetherCodeName)) {
                            data.listBanHolidayTogetherCodeName = _.orderBy(data.listBanHolidayTogetherCodeName, ['banHolidayTogetherCode'], ['asc']);
                            vm.listBanHolidayTogetherCodeName(data.listBanHolidayTogetherCodeName.map((item: BanHolidayTogetherCodeName) => {
                                return new BanHolidayTogetherCodeName(item.banHolidayTogetherCode, item.banHolidayTogetherName);
                            }));
                        } else {
                            vm.setNewMode();
                        }
                    }
                })
                .fail(res => {
                    vm.$dialog.error(res.message).then(() => {
                        vm.setNewMode();
                    })
                })
                .always(() => {
                    dfd.resolve();
                    vm.$blockui("clear");
                });
            return dfd.promise();
        }

        created() {
            const vm = this;

            $("#kcp005-component-left").ntsListComponent(vm.selectableEmployeeComponentOption);
            $("#kcp005-component-right").ntsListComponent(vm.targetEmployeeComponentOption);

            _.extend(window, {vm});
        }

        mounted() {
            const vm = this;

            vm.selectedCode.subscribe(newValue => {
                if (!nts.uk.text.isNullOrEmpty(newValue)) {
                    vm.selectedCodeDisplay(newValue);
                    vm.getDetail(vm.selectedCode());
                } else {
                    vm.setNewMode();
                }
            });

            vm.isVisible.subscribe(newValue => {
                if (!newValue) {
                    $("#F8_5").ntsError("clear");
                }
            });

            vm.minOfWorkingEmpTogether.subscribe(() => {
                    vm.$validate('.nts-editor').then((valid: boolean) => {
                        if (valid) {
                            if (vm.targetEmployeeList().length < vm.minOfWorkingEmpTogether()) {
                                return;
                            }
                            if (vm.targetEmployeeList().length < 2) {
                                return
                            }
                            $("#kcp005-component-right").ntsError("clear");
                        }
                    });
                }
            );

            vm.classificationOrWorkplaceCode.subscribe((newValue) => {
                if (!_.isEmpty(newValue)) {
                    $("#F8_5").ntsError("clear");
                }
            });

            vm.selectedWorkDayReference.subscribe((newValue) => {
                if (vm.isClearReference) {
                    vm.setNewReference();
                }
                vm.isClearReference = true;
            })
        }

        moveItemToRight() {
            const vm = this;
            vm.$blockui("invisible");

            let currentSelectableList = ko.toJS(vm.selectableEmployeeList());
            let currentTagretList = ko.toJS(vm.targetEmployeeList());
            let selectedableCode = ko.toJS(vm.selectedableCodes());

            vm.selectedableCodes([]);
            vm.selectedTargetCode([]);

            _.each(selectedableCode, (code: string) => {

                let selectedItem = _.filter(currentSelectableList, (i: PersonInfo) => i.code == code);

                _.remove(currentSelectableList, (i: PersonInfo) => i.code == code);

                currentTagretList.push(selectedItem[0]);

                vm.selectableEmployeeList(currentSelectableList);
                vm.targetEmployeeList(currentTagretList);
            });

            if (vm.targetEmployeeList().length < vm.minOfWorkingEmpTogether()) {
                return;
            }
            if (vm.targetEmployeeList().length < 2) {
                return
            }
            $("#kcp005-component-right").ntsError("clear");
            vm.$blockui("clear");
        }

        moveItemToLeft() {
            const vm = this;

            let currentSelectableList = ko.toJS(vm.selectableEmployeeList());
            let currentTagretList = ko.toJS(vm.targetEmployeeList());
            let selectedTargetList = ko.toJS(vm.selectedTargetCode());

            vm.selectedTargetCode([]);
            vm.selectedableCodes([]);

            _.each(selectedTargetList, (item: any) => {

                let selectedItem = _.filter(currentTagretList, (i: any) => i.code == item);

                _.remove(currentTagretList, (i: any) => i.code == item);

                currentSelectableList.push(selectedItem[0]);

                vm.selectableEmployeeList(currentSelectableList);
                vm.targetEmployeeList(currentTagretList)
            });
        }

        getDetail(selectedCode: string) {
            const vm = this;
            vm.$errors("clear");

            vm.setNewEmpsCanNotSameHolidays();

            vm.$ajax(PATH_API.getBanHolidayByCode,
                {
                    unit: vm.unit,
                    workplaceId: vm.workplaceId,
                    workplaceGroupId: vm.workplaceGroupId,
                    banHolidayTogetherCode: selectedCode
                })
                .done(data => {
                    if (data) {
                        vm.selectedName(data.banHolidayTogetherName);
                        vm.checkDayReference(data.checkDayReference);
                        vm.minOfWorkingEmpTogether(data.minOfWorkingEmpTogether);

                        if (!_.isEmpty(data.empsCanNotSameHolidays)) {
                            let empsCanNotSameHolidays = new Array<string>();
                            _.each(data.empsCanNotSameHolidays, (dataId: string) => {
                                empsCanNotSameHolidays.push(_.find(vm.selectableEmployeeList(), i => {
                                    return i.id == dataId
                                }).code)
                            });
                            vm.selectedableCodes(empsCanNotSameHolidays);

                            vm.moveItemToRight();
                        }

                        if (data.checkDayReference) {
                            if (data.selectedWorkDayReference == 2) {
                                vm.isClearReference = false;

                                vm.workplaceInfoId("");
                                vm.classificationOrWorkplaceCode(data.classificationCode);
                                vm.classificationOrWorkplaceName(data.classificationName);
                            } else if (data.selectedWorkDayReference == 1) {
                                vm.isClearReference = false;
                                vm.workplaceInfoId(data.workplaceInfoId);
                                vm.classificationOrWorkplaceCode(data.workplaceInfoCode);
                                vm.classificationOrWorkplaceName(data.workplaceInfoName);
                            } else {
                                vm.isClearReference = true;
                                vm.setNewReference();
                            }
                            vm.selectedWorkDayReference(data.selectedWorkDayReference);
                        } else {
                            vm.selectedWorkDayReference(null);
                            vm.isClearReference = true;
                            vm.setNewReference();
                        }

                        //update flag
                        vm.updateEnable = true;
                        vm.deleteEnable(true);
                        vm.modifyEnable(false);
                    }
                })
                .fail(res => {
                    vm.$dialog.error(res.message);
                })
                .always(() => {
                    $("#input-workTypeName").focus();
                });
        }

        insertOrUpdateClick() {
            const vm = this;

            if (vm.targetEmployeeList().length < vm.minOfWorkingEmpTogether()) {
                vm.$errors({
                    "#kcp005-component-right": {
                        messageId: "Msg_1794",
                        messageParams: [vm.minOfWorkingEmpTogether().toString()]
                    }
                });
            }

            if (vm.targetEmployeeList().length < 2) {
                vm.$errors({
                    "#kcp005-component-right": {messageId: "Msg_1875"}
                });
            }

            if (vm.checkDayReference() && (vm.selectedWorkDayReference() == 2 || vm.selectedWorkDayReference() == 1) && _.isEmpty(vm.classificationOrWorkplaceCode())) {
                vm.$errors({
                    "#F8_5": {
                        messageId: "Msg_1795",
                        messageParams: [vm.businessDaysCalendarTypeEnum()[vm.selectedWorkDayReference()].localizedName]
                    }
                });
            }

            vm.$validate('.nts-editor', "#kcp005-component-right", "#F8_5").then((valid: boolean) => {
                if (valid) {
                    if (vm.updateEnable) {
                        vm.updateData();
                    } else {
                        vm.insertData();
                    }
                }
            });
        }

        setNewEmpsCanNotSameHolidays() {
            const vm = this;
            vm.selectableEmployeeList(vm.originalSelectableEmployeeList);
            vm.targetEmployeeList([]);
            vm.selectedTargetCode([]);
            vm.selectedableCodes([]);
        }

        setNewReference() {
            const vm = this;
            vm.workplaceInfoId("");
            vm.classificationOrWorkplaceCode("");
            vm.classificationOrWorkplaceName("");
        }

        setNewMode() {
            const vm = this;

            //clear data
            vm.selectedCode('');
            vm.selectedCodeDisplay("");
            vm.selectedName("");
            vm.checkDayReference(true);
            vm.selectedWorkDayReference(0);
            vm.minOfWorkingEmpTogether(null);

            vm.setNewReference();
            vm.setNewEmpsCanNotSameHolidays();

            //new flags
            vm.updateEnable = false;
            vm.deleteEnable(false);
            vm.modifyEnable(true);

            //clear error
            vm.$errors("clear");


            $("#input-workTypeCode").focus();
        }

        insertData() {
            const vm = this;
            vm.$blockui("invisible");

            vm.$ajax(PATH_API.insert,
                {
                    unit: vm.unit,
                    workplaceId: vm.workplaceId,
                    workplaceGroupId: vm.workplaceGroupId,
                    banHolidayTogetherCode: vm.selectedCodeDisplay(),
                    banHolidayTogetherName: vm.selectedName(),
                    checkDayReference: vm.checkDayReference(),
                    selectedWorkDayReference: vm.selectedWorkDayReference(),
                    minNumberOfEmployeeToWork: vm.minOfWorkingEmpTogether(),
                    workplaceInfoId: vm.workplaceInfoId(),
                    classificationOrWorkplaceCode: vm.classificationOrWorkplaceCode(),
                    empsCanNotSameHolidays: vm.targetEmployeeList().map((item: PersonInfo) => {
                        return item.id
                    })
                })
                .done(() => {
                    vm.$dialog.info({messageId: "Msg_15"}).then(() => {
                        vm.getAllBanHolidayTogether().done(() => {
                            vm.selectedCode(vm.selectedCodeDisplay());
                        });
                    });
                })
                .fail(res => {
                    vm.$dialog.error(res.message);
                })
                .always(() => {
                    $("#input-workTypeName").focus();
                    vm.$blockui("clear");
                });
        }

        updateData() {
            const vm = this;
            vm.$blockui("invisible");

            vm.$ajax(PATH_API.update,
                {
                    unit: vm.unit,
                    workplaceId: vm.workplaceId,
                    workplaceGroupId: vm.workplaceGroupId,
                    banHolidayTogetherCode: vm.selectedCodeDisplay(),
                    banHolidayTogetherName: vm.selectedName(),
                    checkDayReference: vm.checkDayReference(),
                    selectedWorkDayReference: vm.selectedWorkDayReference(),
                    minNumberOfEmployeeToWork: vm.minOfWorkingEmpTogether(),
                    workplaceInfoId: vm.workplaceInfoId(),
                    classificationOrWorkplaceCode: vm.classificationOrWorkplaceCode(),
                    empsCanNotSameHolidays: vm.targetEmployeeList().map((item: PersonInfo) => {
                        return item.id
                    })
                })
                .done(() => {
                    vm.$dialog.info({messageId: "Msg_15"}).then(() => {
                        vm.getAllBanHolidayTogether();
                    });
                })
                .fail(res => {
                    vm.$dialog.error(res.message);
                })
                .always(() => {
                    $("#input-workTypeName").focus();
                    vm.$blockui("clear");
                });
        }

        deletedata() {
            const vm = this;
            vm.$dialog.confirm({messageId: "Msg_18"}).then((result: 'no' | 'yes' | 'cancel') => {
                if (result === 'yes') {
                    vm.$blockui("invisible");

                    vm.$ajax(PATH_API.delete,
                        {
                            unit: vm.unit,
                            workplaceId: vm.workplaceId,
                            workplaceGroupId: vm.workplaceGroupId,
                            banHolidayTogetherCode: vm.selectedCode(),
                        })
                        .done(() => {
                            vm.$dialog.info({messageId: "Msg_16"}).then(() => {
                                vm.getAllBanHolidayTogether(true);
                            })
                        })
                        .fail(res => {
                            vm.$dialog.error(res.message);
                        })
                        .always(() => {
                            $("#input-workTypeName").focus();
                            vm.$blockui("clear");
                        });
                } else {
                    $("#input-workTypeName").focus();
                }
            });
        }

        getAllBanHolidayTogether(isRemove: boolean = false) {
            const vm = this;

            let oldSelectIndex = _.findIndex(vm.listBanHolidayTogetherCodeName(), item => {
                return item.banHolidayTogetherCode == vm.selectedCode();
            });

            let dfd = $.Deferred();
            vm.$ajax(PATH_API.getAllBanHolidayTogether,
                {
                    unit: vm.unit,
                    workplaceId: vm.workplaceId,
                    workplaceGroupId: vm.workplaceGroupId
                })
                .done(data => {
                    if (!_.isEmpty(data)) {
                        data = _.orderBy(data, ['banHolidayTogetherCode'], ['asc']);
                        vm.listBanHolidayTogetherCodeName(data.map((item: BanHolidayTogetherCodeName) => {
                            return new BanHolidayTogetherCodeName(item.banHolidayTogetherCode, item.banHolidayTogetherName);
                        }));

                        if (isRemove) {
                            vm.selectedCode(vm.getNewSelectRemove(oldSelectIndex));
                        }
                    } else {
                        vm.listBanHolidayTogetherCodeName([]);
                        vm.setNewMode();
                    }
                })
                .fail(res => {
                    vm.$dialog.error(res.message);
                })
                .always(() => {
                    dfd.resolve();
                });
            return dfd.promise();
        }

        getEmployeeInfo() {
            const vm = this;
            let dfd = $.Deferred();

            vm.$ajax(PATH_API.getEmployeeInfo,
                {
                    unit: vm.unit,
                    workplaceId: vm.workplaceId,
                    workplaceGroupId: vm.workplaceGroupId
                })
                .done(data => {
                    if (!_.isEmpty(data)) {
                        vm.selectableEmployeeList(data.map((item: any) => {
                            return new PersonInfo(item.employeeID, item.employeeCode, item.businessName);
                        }));

                        vm.originalSelectableEmployeeList = vm.selectableEmployeeList();
                    }
                })
                .fail(res => {
                    vm.$dialog.error(res.message);
                })
                .always(() => {
                    dfd.resolve();
                });

            return dfd.promise();
        }

        openDiaglogKDL046() {
            const vm = this;

            setShared("dataShareDialog046", {
                unit: vm.unit,
                workplaceId: vm.workplaceId,
                workplaceGroupId: vm.workplaceGroupId
            });

            vm.$window.modal('../../../kdl/046/a/index.xhtml').then(() => {
                vm.$blockui("invisible");
                $(".nts-input").ntsError("clear");

                let dto: any = getShare("dataShareKDL046");
                if (_.isEmpty(dto)) {
                    vm.$blockui("clear");
                    return;
                }

                if (dto.unit === 1) {
                    vm.unit = 1;
                    vm.workplaceGroupId = dto.workplaceGroupID;
                    vm.orgCode(dto.workplaceGroupCode);
                    vm.orgDisplayName(dto.workplaceGroupName);
                } else {
                    vm.unit = 0;
                    vm.workplaceId = dto.workplaceId;
                    vm.orgCode(dto.workplaceCode);
                    vm.orgDisplayName(dto.workplaceName);
                }

                vm.getEmployeeInfo().done(() => {
                    vm.getAllBanHolidayTogether().done(() => {
                        if (!_.isEmpty(vm.listBanHolidayTogetherCodeName())) {
                            vm.selectedCode(vm.listBanHolidayTogetherCodeName()[0].banHolidayTogetherCode);
                        }
                        vm.$blockui("clear");
                    });
                });
            });
        }

        openDiaglogCDL() {
            const vm = this;

            //classification
            if (vm.selectedWorkDayReference() == 2) {
                setShared('inputCDL003', {
                    selectedCodes: vm.classificationOrWorkplaceCode(),
                    showNoSelection: false,
                    isMultiple: false
                }, true);

                vm.$window.modal('com', '/view/cdl/003/a/index.xhtml').then(() => {
                    // Check is cancel.
                    if (getShare('CDL003Cancel')) {
                        setShared('CDL008Cancel', null);
                        return;
                    }

                    let dto: any = getShare('classificationInfoCDL003');
                    if (_.isEmpty(dto)) {
                        vm.setNewReference();
                        return;
                    }

                    vm.workplaceInfoId("");
                    vm.classificationOrWorkplaceCode(dto.code);
                    vm.classificationOrWorkplaceName(dto.name);
                });
            }

            //workplace
            if (vm.selectedWorkDayReference() == 1) {
                setShared('inputCDL008', {
                    selectedCodes: vm.workplaceInfoId(),
                    baseDate: moment(new Date()).toDate(),
                    isMultiple: false,
                    selectedSystemType: 2,
                    isrestrictionOfReferenceRange: true,
                    showNoSelection: false,
                    isShowBaseDate: true,
                    startMode: 0
                }, true);

                vm.$window.modal('com', '/view/cdl/008/a/index.xhtml').then(() => {
                    // Check is cancel.
                    if (getShare('CDL008Cancel')) {
                        setShared('CDL008Cancel', null);
                        return;
                    }
                    //view all code of selected item
                    let dto: any = getShare('workplaceInfor');
                    if (_.isEmpty(dto)) {
                        vm.setNewReference();
                        return;
                    }

                    vm.workplaceInfoId(dto[0].id);
                    vm.classificationOrWorkplaceCode(dto[0].code);
                    vm.classificationOrWorkplaceName(dto[0].name);
                });
            }
        }

        getNewSelectRemove(oldSelectIndex: number): string {
            const vm = this;
            let dataLength = vm.listBanHolidayTogetherCodeName().length;
            if (dataLength == 1 || oldSelectIndex > dataLength) {
                return vm.listBanHolidayTogetherCodeName()[0].banHolidayTogetherCode;
            }
            if (oldSelectIndex <= dataLength - 1) {
                return vm.listBanHolidayTogetherCodeName()[oldSelectIndex].banHolidayTogetherCode;
            }
            if (oldSelectIndex == dataLength) {
                return vm.listBanHolidayTogetherCodeName()[oldSelectIndex - 1].banHolidayTogetherCode;
            }
            return null;
        }

        close() {
            let vm = this;
            vm.$window.close();
        }
    }

    export class BanHolidayTogetherCodeName {
        banHolidayTogetherCode: string;
        banHolidayTogetherName: string;

        constructor(banHolidayTogetherCode: string, banHolidayTogetherName: string) {
            this.banHolidayTogetherCode = banHolidayTogetherCode;
            this.banHolidayTogetherName = banHolidayTogetherName;
        }
    }

    export class PersonInfo {
        id: string;
        code: string;
        name: string;

        constructor(id: string, code: string, name: string) {
            this.id = id;
            this.code = code;
            this.name = name;
        }
    }

    class ComboBoxModel {
        value: number;
        localizedName: string;

        constructor(value: number, localizedName: string) {
            const vm = this;
            vm.value = value;
            vm.localizedName = localizedName;
        }
    }
}
