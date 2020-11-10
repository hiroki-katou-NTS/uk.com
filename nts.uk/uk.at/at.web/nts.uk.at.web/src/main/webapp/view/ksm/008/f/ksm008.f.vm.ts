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
        // Flag
        deleteEnable: KnockoutObservable<boolean> = ko.observable(false);
        updateEnable: KnockoutObservable<boolean> = ko.observable(false);
        modifyEnable: KnockoutObservable<boolean> = ko.observable(false);
        isVisible: KnockoutObservable<boolean>;

        // Init
        //getStartupInfoBanHoliday
        code: KnockoutObservable<string> = ko.observable(""); //勤務予定のアラームチェック条件.コード
        conditionName: KnockoutObservable<string> = ko.observable(""); //条件名
        explanation: KnockoutObservable<string> = ko.observable(""); //サブ条件リスト.説明
        unit: KnockoutObservable<number> = ko.observable(); //対象組織情報.単位
        workplaceId: KnockoutObservable<string> = ko.observable(""); //対象組織情報.職場ID
        workplaceGroupId: KnockoutObservable<string> = ko.observable(""); //対象組織情報.職場グループID
        orgCode: KnockoutObservable<string> = ko.observable(""); //組織の表示情報.コード
        orgDisplayName: KnockoutObservable<string> = ko.observable(""); //組織の表示情報.表示名
        listBanHolidayTogetherCodeName: KnockoutObservableArray<BanHolidayTogetherCodeName> = ko.observableArray([]); //同日休日禁止

        //getEmployeeInfo
        selectableEmployeeList: KnockoutObservableArray<PersonInfo> = ko.observableArray([]); //社員データ管理情報
        selectedableCodes: KnockoutObservableArray<string> = ko.observableArray([]);
        selectableEmployeeComponentOption: any;

        //getBanHolidayByCode
        checkDayReference: KnockoutObservable<boolean> = ko.observable(false); //稼働日のみとする
        selectedWorkDayReference: KnockoutObservable<number> = ko.observable(1); //稼働日の参照先
        businessDaysCalendarTypeEnum: KnockoutObservableArray<ComboBoxModel> = ko.observableArray([]); //営業日カレンダー種類
        minOfWorkingEmpTogether: KnockoutObservable<number> = ko.observable(); //最低限出勤すべき人数
        workplaceInfoId: KnockoutObservable<string> = ko.observable(""); //職場ID
        classificationOrWorkplaceCode: KnockoutObservable<string> = ko.observable(""); //分類コード / 職場コード
        classificationOrWorkplaceName: KnockoutObservable<string> = ko.observable(""); //分類名称 / 職場表示名
        empsCanNotSameHolidays: KnockoutObservableArray<string> = ko.observableArray([]); //同日の休日取得を禁止する社員

        targetEmployeeList: KnockoutObservableArray<PersonInfo> = ko.observableArray([]);
        targetEmployeeComponentOption: any;

        selectedCodeDisplay: KnockoutObservable<string> = ko.observable('');
        selectedCode: KnockoutObservable<string> = ko.observable('');
        selectedName: KnockoutObservable<string> = ko.observable('');

        codeAndConditionName: KnockoutObservable<string>;

        constructor(params: any) {
            super();
            const vm = this;

            // vm.code(params.code);
            vm.code('03');

            vm.codeAndConditionName = ko.computed(() => {
                return vm.code() + " " + vm.conditionName();
            }, this);

            vm.isVisible = ko.computed(() => {
                return vm.checkDayReference() && vm.selectedWorkDayReference() != 0;
            });

            vm.initData();

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
                selectedCode: vm.empsCanNotSameHolidays,
                isDialog: true,
                isShowNoSelectRow: false,
                isShowSelectAllButton: false,
                disableSelection: false,
                maxRows: 10
            };
        }

        initData() {
            const vm = this;
            vm.$blockui("invisible");

            vm.$ajax(PATH_API.getStartupInfoBanHoliday, {code: vm.code()})
                .done(data => {
                    if (data) {
                        vm.code(data.code);
                        vm.conditionName(data.conditionName);

                        let explanation: string = "";
                        _.forEach(data.explanationList, (item) => {
                            explanation += item;
                        });
                        vm.explanation(explanation);

                        vm.unit(data.unit);
                        vm.workplaceId(data.workplaceId);
                        vm.workplaceGroupId(data.workplaceGroupId);
                        vm.orgCode(data.orgCode);
                        vm.orgDisplayName(data.orgDisplayName);

                        if (!_.isEmpty(data.listBanHolidayTogetherCodeName)) {
                            data.listBanHolidayTogetherCodeName = _.orderBy(data.listBanHolidayTogetherCodeName, ['banHolidayTogetherCode'], ['asc']);
                            vm.listBanHolidayTogetherCodeName(data.listBanHolidayTogetherCodeName.map((item: BanHolidayTogetherCodeName) => {
                                return new BanHolidayTogetherCodeName(item.banHolidayTogetherCode, item.banHolidayTogetherName);
                            }));

                            vm.selectedCode(vm.listBanHolidayTogetherCodeName()[0].banHolidayTogetherCode);
                        } else {
                            vm.setNewMode();
                        }

                        vm.businessDaysCalendarTypeEnum(data.businessDaysCalendarTypeEnum);

                        vm.$ajax(PATH_API.getEmployeeInfo,
                            {
                                unit: vm.unit(),
                                workplaceId: vm.workplaceId(),
                                workplaceGroupId: vm.workplaceGroupId()
                            })
                            .done(data => {
                                if (!_.isEmpty(data)) {
                                    vm.selectableEmployeeList(data.map((item: any) => {
                                        return new PersonInfo(item.employeeID, item.employeeCode, item.BusinessName);
                                    }));
                                }
                            })
                            .fail(res => {
                                vm.$dialog.error(res.message);
                            });
                    }
                })
                .fail(res => {
                    vm.$dialog.error(res.message).then(() => {
                        vm.setNewMode();
                    })
                })
                .always(() => {
                    vm.$blockui("clear");
                });

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
                console.log(!nts.uk.text.isNullOrEmpty(newValue));
                if (!nts.uk.text.isNullOrEmpty(newValue)) {
                    vm.selectedCodeDisplay(newValue);
                    vm.getDetail(vm.selectedCode());
                } else {
                    vm.selectedCodeDisplay("");
                    vm.selectedCode("");
                    vm.selectedName("");
                    vm.modifyEnable(true);
                    $("#input-workTypeCode").focus();
                }
            });
        }

        moveItemToRight() {
            const vm = this;

            let currentSelectableList = ko.toJS(vm.selectableEmployeeList());
            let currentTagretList = ko.toJS(vm.targetEmployeeList);
            let selectedableCode = ko.toJS(vm.selectedableCodes());

            vm.selectedableCodes([]);
            vm.empsCanNotSameHolidays([]);

            _.each(selectedableCode, (code: string) => {

                let selectedItem = _.filter(currentSelectableList, (i: PersonInfo) => i.code == code);

                _.remove(currentSelectableList, (i: PersonInfo) => i.code == code);

                currentTagretList.push(selectedItem[0]);

                vm.selectableEmployeeList(currentSelectableList);
                vm.targetEmployeeList(currentTagretList);
            });
        }

        moveItemToLeft() {
            const vm = this;

            let currentSelectableList = ko.toJS(vm.selectableEmployeeList());
            let currentTagretList = ko.toJS(vm.targetEmployeeList());
            let selectedTargetList = ko.toJS(vm.empsCanNotSameHolidays());

            vm.empsCanNotSameHolidays([]);
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
            vm.$blockui("invisible");

            vm.$ajax(PATH_API.getBanHolidayByCode,
                {
                    unit: vm.unit(),
                    workplaceId: vm.workplaceId(),
                    workplaceGroupId: vm.workplaceGroupId(),
                    banHolidayTogetherCode: selectedCode
                })
                .done(data => {
                    if (data) {
                        vm.selectedName(data.banHolidayTogetherName);
                        vm.checkDayReference(data.checkDayReference);
                        vm.minOfWorkingEmpTogether(data.minOfWorkingEmpTogether);
                        vm.empsCanNotSameHolidays(data.empsCanNotSameHolidays);
                        vm.moveItemToRight();

                        if (data.checkDayReference) {
                            vm.selectedWorkDayReference(data.selectedWorkDayReference);

                            if (data.selectedWorkDayReference == 2) {
                                vm.classificationOrWorkplaceCode(data.classificationCode);
                                vm.classificationOrWorkplaceName(data.classificationName);
                            }
                            if (data.selectedWorkDayReference == 1) {
                                vm.workplaceInfoId(data.workplaceInfoId);
                                vm.classificationOrWorkplaceCode(data.workplaceInfoCode);
                                vm.classificationOrWorkplaceName(data.workplaceInfoName);
                            }
                        }

                        vm.updateEnable(true);
                        vm.deleteEnable(true);
                        vm.modifyEnable(false);
                    }
                })
                .fail(res => {
                    vm.$dialog.error(res.message);
                })
                .always(() => {
                    $("#input-workTypeName").focus();
                    vm.$blockui("clear");
                });
        }

        insertOrUpdateClick() {
            const vm = this;

            if(vm.targetEmployeeList.length < vm.minOfWorkingEmpTogether()){
                vm.$dialog.alert({
                    messageId: "Msg_1794",
                    messageParams: [vm.minOfWorkingEmpTogether().toString()]
                }).then(() => {
                    $("#kcp005-component-right").focus();
                });

                return;
            }

            if(vm.targetEmployeeList.length < 2){
                vm.$dialog.alert({
                    messageId: "Msg_1875",
                    messageParams: []
                }).then(() => {
                    $("#kcp005-component-right").focus();
                });

                return;
            }

            if((vm.selectedWorkDayReference() == 2 || vm.selectedWorkDayReference() == 1) &&_.isEmpty(vm.classificationOrWorkplaceCode)){
                vm.$dialog.alert({
                    messageId: "Msg_1795",
                    messageParams: [vm.businessDaysCalendarTypeEnum()[vm.selectedWorkDayReference()].localizedName]
                }).then(() => {
                    $("#btn-cdl").focus();
                });

                return;
            }

            vm.$validate().then((valid: boolean) => {
                if (valid) {
                    if (vm.updateEnable()) {
                        vm.updateData();
                    } else {
                        vm.insertData();
                    }
                }
            });
        }

        setNewMode() {
            const vm = this;
            vm.selectedCode('');
            vm.checkDayReference(true);
            vm.selectedWorkDayReference(0);
            vm.minOfWorkingEmpTogether();
            //F9_6 null
            vm.updateEnable(false);
            vm.deleteEnable(false);
            vm.modifyEnable(true);
            $("#input-workTypeCode").focus();
        }

        insertData() {
            const vm = this;
            vm.$blockui("invisible");

            vm.$ajax(PATH_API.insert,
                {
                    unit: vm.unit(),
                    workplaceId: vm.workplaceId(),
                    workplaceGroupId: vm.workplaceGroupId(),
                    banHolidayTogetherCode: vm.selectedCodeDisplay(),
                    banHolidayTogetherName: vm.selectedName(),
                    checkDayReference: vm.checkDayReference(),
                    selectedWorkDayReference: vm.selectedWorkDayReference(),
                    minNumberOfEmployeeToWork: vm.minOfWorkingEmpTogether(),
                    workplaceInfoId: vm.workplaceInfoId(),
                    classificationOrWorkplaceCode: vm.classificationOrWorkplaceCode(),
                    empsCanNotSameHolidays: vm.targetEmployeeList().map((item: PersonInfo) => {
                        return item.code
                    })
                })
                .done(() => {
                    vm.$dialog.info({messageId: "Msg_15"}).then(() => {
                        vm.getAllBanHolidayTogether();
                    });
                })
                .fail(res => {
                    vm.$dialog.error(res.message).then(() => {
                        $("#input-workTypeCode").focus();
                    })
                })
                .always(() => {
                    vm.$blockui("clear");
                });
        }

        updateData() {
            const vm = this;
            vm.$blockui("invisible");

            vm.$ajax(PATH_API.update,
                {
                    unit: vm.unit(),
                    workplaceId: vm.workplaceId(),
                    workplaceGroupId: vm.workplaceGroupId(),
                    banHolidayTogetherCode: vm.selectedCodeDisplay(),
                    banHolidayTogetherName: vm.selectedName(),
                    checkDayReference: vm.checkDayReference(),
                    selectedWorkDayReference: vm.selectedWorkDayReference(),
                    minNumberOfEmployeeToWork: vm.minOfWorkingEmpTogether(),
                    workplaceInfoId: vm.workplaceInfoId(),
                    classificationOrWorkplaceCode: vm.classificationOrWorkplaceCode(),
                    empsCanNotSameHolidays: vm.targetEmployeeList().map((item: PersonInfo) => {
                        return item.code
                    })
                })
                .done(() => {
                    vm.$dialog.info({messageId: "Msg_15"}).then(() => {
                        vm.getAllBanHolidayTogether();
                    });
                })
                .fail(res => {
                    vm.$dialog.error(res.message).then(() => {
                        $("#input-workTypeName").focus();
                    })
                })
                .always(() => {
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
                            unit: vm.unit(),
                            workplaceId: vm.workplaceId(),
                            workplaceGroupId: vm.workplaceGroupId(),
                            banHolidayTogetherCode: vm.orgCode(),
                        })
                        .done(() => {
                            vm.$dialog.info({messageId: "Msg_16"}).then(() => {
                                vm.getAllBanHolidayTogether();
                            })
                        })
                        .fail(res => {
                            vm.$dialog.error(res.message);
                        })
                        .always(() => {
                            vm.$blockui("clear");
                        });
                } else {
                    $("#input-workTypeName").focus();
                }
            });
        }

        getAllBanHolidayTogether() {
            const vm = this;
            vm.$blockui("invisible");
            vm.$ajax(PATH_API.getAllBanHolidayTogether,
                {
                    unit: vm.unit(),
                    workplaceId: vm.workplaceId(),
                    workplaceGroupId: vm.workplaceGroupId()
                })
                .done(data => {
                    if (data) {
                        if (!_.isEmpty(data)) {
                            data.listBanHolidayTogetherCodeName = _.orderBy(data.listBanHolidayTogetherCodeName, ['banHolidayTogetherCode'], ['asc']);
                            vm.listBanHolidayTogetherCodeName(data.map((item: BanHolidayTogetherCodeName) => {
                                return new BanHolidayTogetherCodeName(item.banHolidayTogetherCode, item.banHolidayTogetherName);
                            }));

                            vm.selectedCode(vm.listBanHolidayTogetherCodeName()[0].banHolidayTogetherCode);
                        } else {
                            vm.setNewMode();
                        }
                    }
                })
                .fail(res => {
                    vm.$dialog.error(res.message).then(() => {
                    })
                })
                .always(() => {
                    vm.$blockui("clear");
                });
        }

        openDiaglogKDL046() {
            const vm = this;

            setShared("dataShareDialog046", {
                unit: vm.unit(),
                workplaceId: vm.workplaceId(),
                workplaceGroupId: vm.workplaceGroupId()
            });

            vm.$window.modal('../../../kdl/046/a/index.xhtml').then(() => {
                vm.$blockui("invisible");

                let dto: any = getShare("dataShareKDL046");
                if (dto.unit === 1) {
                    vm.unit(1);
                    vm.workplaceGroupId(dto.workplaceGroupID);
                    vm.orgCode(dto.workplaceGroupCode);
                    vm.orgDisplayName(dto.workplaceGroupName);
                } else {
                    vm.unit(0);
                    vm.workplaceId(dto.workplaceId);
                    vm.orgCode(dto.workplaceCode);
                    vm.orgDisplayName(dto.workplaceName);
                }

                vm.getAllBanHolidayTogether();
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
                    let dto: any = getShared('outputCDL003');
                    if (dto) {
                        vm.classificationOrWorkplaceCode(dto);
                    }
                });
            }

            //workplace
            if (vm.selectedWorkDayReference() == 1) {
                setShared('inputCDL008', {
                    selectedCodes: vm.classificationOrWorkplaceCode(),
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
                    if (getShared('CDL008Cancel')) {
                        return;
                    }
                    //view all code of selected item
                    let dto: any = getShared('outputCDL008');
                    vm.workplaceInfoId(dto);
                });
            }
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
