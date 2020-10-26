module nts.uk.at.ksm008.d {

    import modal = nts.uk.ui.windows.sub.modal;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;

    const PATH_API = {
        getStartupInfoCom: "screen/at/ksm008/d/getStartupInfo",
        getDetails: "screen/at/ksm008/d/getDetails",
        registerScreenD: "work/method/company/register",
        updateScreenD: "work/method/company/update",


        getStartupInfoOrg: "screen/at/ksm008/e/getStartupInfo",
        getAllWorkingHours: 'at/shared/worktimesetting/findAll',
    };

    @bean()
    export class KSM008DViewModel extends ko.ViewModel {
        backButon: string = "/view/ksm/008/a/index.xhtml";
        isComSelected: KnockoutObservable<boolean> = ko.observable(false);
        isOrgSelected: KnockoutObservable<boolean> = ko.observable(false);

        workplace: Workplace = new Workplace(null, "", "", "", "");

        code: KnockoutObservable<string> = ko.observable("");
        name: KnockoutObservable<string> = ko.observable("");

        // D3_1 勤務予定のアラームチェック条件: コード + 条件名
        conditionCodeAndName: KnockoutObservable<string> = ko.observable("");
        // D5_2 勤務予定のアラームチェック条件.サブ条件リスト.説明
        conditionDescription: KnockoutObservable<string> = ko.observable("");

        // D6_3 就業時間帯の設定
        targetWorkMethods: KnockoutObservableArray<ItemModel> = ko.observableArray([]);
        dScreenCurrentCode: KnockoutObservable<string> = ko.observable("");
        //dScreenCurrentName: KnockoutObservable<string> = ko.observable("");

        // D7_2 対象の勤務方法の種類
        workMethodType: KnockoutObservable<string> = ko.observable("1");

        // D7_7 対象の就業時間コード
        targetWorkMethodCode: KnockoutObservable<string> = ko.observable("001");
        // D7_8 対象の就業時間名称
        targetWorkMethodName: KnockoutObservable<string> = ko.observable("Name");

        // D8_3 関係性の指定方法
        nextDayWorkMethod: KnockoutObservable<string> = ko.observable("0");

        // D10 翌日の勤務方法の種類
        nextDayWorkMethodType: KnockoutObservable<string> = ko.observable("1");

        // D12 翌日の就業時間帯一覧
        nextDayWorkHourCodes: KnockoutObservableArray<string> = ko.observableArray([]);
        nextDayWorkHours: KnockoutObservableArray<ItemModel> = ko.observableArray([]);

        // 選択可能な就業時間帯コードリスト
        selectableWorkingHoursCode: KnockoutObservableArray<string> = ko.observableArray([]);

        constructor(params: any) {
            super();
            const vm = this;

            vm.conditionCodeAndName = ko.computed(() => {
                return vm.code() + " " + vm.name();
            });

            vm.$blockui("invisible");
            vm.$ajax(PATH_API.getStartupInfoCom, {code: "03"}).done(data => {
                if (data) {
                    vm.code(data.conditionCode);
                    vm.name(data.conditionName);
                    vm.conditionDescription(data.subConditions);
                }
                if (data.workTimeSettings) {
                    vm.selectableWorkingHoursCode(data.workTimeSettings.map(function (item: any) {
                        return item.code;
                    }));
                    vm.targetWorkMethods(data.workTimeSettings.map(function (item: any) {
                            return new ItemModel(item.code, item.name);
                        })
                    );
                    if (vm.targetWorkMethods().length > 0) {
                        vm.dScreenCurrentCode(vm.targetWorkMethods()[0].code);
                    }
                }
                else {
                    vm.targetWorkMethods([]);
                    vm.selectableWorkingHoursCode([]);
                }
            }).fail(res => {
                vm.$dialog.error(res.message);
            }).always(() => vm.$blockui("clear"));
        }

        created() {
            const vm = this;

            vm.dScreenCurrentCode.subscribe((newValue: any) => {
                vm.$errors("clear");
                let item = _.find(vm.targetWorkMethods(), i => {
                    return i.code == newValue;
                });
                vm.targetWorkMethodCode(newValue);
                vm.targetWorkMethodName(item.name);

                if (newValue) {
                    vm.$ajax(PATH_API.getDetails, {workTimeCode: vm.dScreenCurrentCode()}).done(data => {
                        if (data) {
                            vm.workMethodType(data.typeWorkMethod);
                            vm.nextDayWorkMethod(data.specifiedMethod);
                            vm.nextDayWorkMethodType(data.typeOfWorkMethods);

                            if (data.workTimeSettings) {
                                vm.nextDayWorkHours(_.map(data.workTimeSettings, function (item: any) {
                                    return new ItemModel(item.code, item.name);
                                }));
                            }
                        }
                    });
                }
                else {

                }
            });
        }

        mounted() {

        }

        /**
         * on click tab panel company action event
         */
        onSelectCom() {
            const vm = this;
            //vm.$blockui("invisible");

            // Update flags.
            vm.isComSelected(true);
            vm.isOrgSelected(false);

            // vm.$ajax(PATH_API.getStartupInfoCom).done(data => {
            //     if (data) {
            //
            //     }
            // }).fail(res => {
            //     vm.$dialog.error(res.message);
            // }).always(() => {
            //     vm.$blockui("clear");
            // });
        }

        /**
         * on click tab panel Organization action event
         */
        onSelectOrg() {
            const vm = this;
            //vm.$blockui("invisible");

            // Update flags.
            vm.isComSelected(false);
            vm.isOrgSelected(true);

            // vm.$ajax(PATH_API.getStartupInfoOrg).done(data => {
            //     if (data) {
            //         if (data.orgInfoDto){
            //             vm.code(data.orgInfoDto.code);
            //             vm.name(data.orgInfoDto.displayName);
            //             vm.workplace = new Workplace(data.orgInfoDto.unit, data.orgInfoDto.workplaceId, data.orgInfoDto.workplaceGroupId, data.orgInfoDto.code, data.orgInfoDto.displayName);
            //         }
            //
            //         vm.targetWorkMethods(_.map(data.workTimeSettings, function (item: any) {
            //             return new ItemModel(item.worktimeCode, item.workTimeDisplayName)
            //         }));
            //     }
            // }).fail(res => {
            //     vm.$dialog.error(res.message);
            // }).always(() => {
            //     vm.$blockui("clear");
            // });
        }

        /**
         * Call model KDL001 Single Select
         */
        openKdl001SingleSelect() {
            const vm = this;
            setShared("kml001multiSelectMode", false);
            setShared("kml001selectedCodeList", [vm.dScreenCurrentCode()]);
            setShared("kml001isSelection", false);
            setShared("kml001selectAbleCodeList", vm.selectableWorkingHoursCode());
            modal('at', '/view/kdl/001/a/index.xhtml').onClosed(() => {
                vm.$errors("clear");
                let shareWorkCode: Array<string> = getShared('kml001selectedCodeList');
                if (shareWorkCode && shareWorkCode.length >= 1) {
                    vm.dScreenCurrentCode(shareWorkCode[0]);
                }
            });
        }

        /**
         * Call model KDL001 Multi Select
         */
        openKdl001MultiSelect() {
            const vm = this;
            let codes = vm.nextDayWorkHours().map(function (item: any) {
                return item.code;
            });
            setShared("kml001multiSelectMode", true);
            setShared("kml001selectedCodeList", codes);
            setShared("kml001isSelection", false);
            setShared("kml001selectAbleCodeList", vm.selectableWorkingHoursCode());
            modal('at', '/view/kdl/001/a/index.xhtml').onClosed(() => {
                vm.$errors("clear");
                let shareWorkCode: Array<string> = getShared('kml001selectedCodeList');
                if (shareWorkCode) {
                    let selectedItem = _.filter(vm.targetWorkMethods(), i => {
                        return shareWorkCode.indexOf(i.code) >= 0;
                    });
                    vm.nextDayWorkHours(selectedItem);
                }
            });
        }

        /**
         * Call model KDL046
         */
        openModalKDL046() {
            const vm = this;
            let request: any = {
                unit: vm.workplace.unit()
            };
            if (request.unit === 1) {
                request.workplaceGroupId = vm.workplace.workplaceGroupId();
                request.workplaceGroupCode = vm.workplace.workplaceCode();
                request.workplaceGroupName = vm.workplace.workplaceName();
            } else {
                request.workplaceId = vm.workplace.workplaceId();
                request.enableDate = true;
                request.workplaceCode = vm.workplace.workplaceCode();
                request.workplaceName = vm.workplace.workplaceName();
            }
            const data = {
                dataShareDialog046: request
            };
            setShared('dataShareDialog046', request);
            vm.$window.modal('/view/kdl/046/a/index.xhtml')
                .then((result: any) => {
                    let selectedData = nts.uk.ui.windows.getShared('dataShareDialog046');
                    vm.workplace.unit(selectedData.unit);
                    // Todo
                });
        }

        /**
         * remove next day work hours
         */
        removeNextDayWorkHours() {
            const vm = this;

            vm.nextDayWorkHours(_.filter(vm.nextDayWorkHours(), function (item: any) {
                return vm.nextDayWorkHourCodes().indexOf(item.code) < 0;
            }));
        }

        newScreenD() {

        }

        registerScreenD() {
            const vm = this;
            let workMethodCodes = vm.nextDayWorkHours().map(function (item: any) {
                return item.code;
            });
            let command = {
                typeWorkMethod: vm.workMethodType(),
                workTimeCode: vm.targetWorkMethodCode(),
                specifiedMethod: vm.nextDayWorkMethod(),
                typeOfWorkMethods: vm.nextDayWorkMethodType(),
                workMethods: workMethodCodes
            };
            vm.$blockui("invisible");
            vm.$ajax(PATH_API.registerScreenD, command).done((data) => {
                vm.$dialog.info({messageId: "Msg_15"});
            }).fail(res => {
                vm.$dialog.error(res.message);
            }).always(() => {
                vm.$blockui("clear");
            });
        }

        deleteScreenD() {

        }
    }

    class ItemModel {
        code: string;
        name: string;
        display: string;

        constructor(code: string, name: string) {
            this.code = code;
            this.name = name;
            this.display = code + " " + name;
        }
    }

    class Workplace {
        /**
         * 対象組織情報.単位
         */
        unit: KnockoutObservable<number>;

        /**
         * 対象組織情報.職場ID
         */
        workplaceId: KnockoutObservable<string>;

        /**
         * 対象組織情報.職場グループID
         */
        workplaceGroupId: KnockoutObservable<string>;

        /**
         * 組織の表示情報.コード
         */
        workplaceCode: KnockoutObservable<string>;

        /**
         * 組織の表示情報.表示名
         */
        workplaceName: KnockoutObservable<string>;

        constructor(unit: number, workplaceId: string, workplaceGroupId: string, workplaceCode: string, workplaceName: string) {
            this.unit = ko.observable(unit);
            this.workplaceId = ko.observable(workplaceId);
            this.workplaceGroupId = ko.observable(workplaceGroupId);
            this.workplaceCode = ko.observable(workplaceCode);
            this.workplaceName = ko.observable(workplaceName);
        }
    }
}