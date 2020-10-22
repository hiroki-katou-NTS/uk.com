module nts.uk.at.ksm008.d {

    const PATH_API = {
        getStartupInfoCom: "screen/at/ksm008/d/getStartupInfo",

        getStartupInfoOrg: "screen/at/ksm008/e/getStartupInfo"
    };

    @bean()
    export class KSM008DViewModel extends ko.ViewModel {

        isComSelected: KnockoutObservable<boolean> = ko.observable(false);
        isOrgSelected: KnockoutObservable<boolean> = ko.observable(false);

        workplace: Workplace = new Workplace(null, "","", "", "");

        code: KnockoutObservable<string> = ko.observable();
        name: KnockoutObservable<string> = ko.observable();

        // D3_1 勤務予定のアラームチェック条件: コード + 条件名
        conditionCodeAndName: KnockoutObservable<string> = ko.observable();
        // D5_2 勤務予定のアラームチェック条件.サブ条件リスト.説明
        conditionDescription: KnockoutObservable<string> = ko.observable();

        // D6_3 就業時間帯の設定
        targetWorkMethods: KnockoutObservableArray<ItemModel>;
        dScreenCurrentCode: KnockoutObservable<any>;

        constructor(params: any) {
            super();
            const vm = this;
            vm.targetWorkMethods = ko.observableArray([]);

            vm.conditionCodeAndName = ko.computed(() => {
                return vm.code() + " " + vm.name();
            });
        }

        created() {
            const vm = this;
            vm.dScreenCurrentCode.subscribe((newValue: any) => {
                vm.$errors("clear");
            });
        }

        mounted() {

        }

        /**
         * on click tab panel company action event
         */
        onSelectCom() {
            const vm = this;
            vm.$blockui("invisible");

            // Update flags.
            vm.isComSelected(true);
            vm.isOrgSelected(false);

            vm.$ajax(PATH_API.getStartupInfoCom).done(data => {
                if (data) {

                }
            }).fail(res => {
                vm.$dialog.error(res.message);
            }).always(() => {
                vm.$blockui("clear");
            });
        }

        /**
         * on click tab panel Organization action event
         */
        onSelectOrg() {
            const vm = this;
            vm.$blockui("invisible");

            // Update flags.
            vm.isComSelected(false);
            vm.isOrgSelected(true);

            vm.$ajax(PATH_API.getStartupInfoOrg).done(data => {
                if (data) {
                    if (data.orgInfoDto){
                        vm.code(data.orgInfoDto.code);
                        vm.name(data.orgInfoDto.displayName);
                        vm.workplace = new Workplace(data.orgInfoDto.unit, data.orgInfoDto.workplaceId, data.orgInfoDto.workplaceGroupId, data.orgInfoDto.code, data.orgInfoDto.displayName);
                    }

                    vm.targetWorkMethods(_.map(data.workTimeSettings, function (item: any) {
                        return new ItemModel(item.worktimeCode, item.workTimeDisplayName)
                    }));
                }
            }).fail(res => {
                vm.$dialog.error(res.message);
            }).always(() => {
                vm.$blockui("clear");
            });
        }

        /**
         * Call model KDL046
         */
        openModalKDL046() {
            const vm = this
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
    }

    class ItemModel {
        code: KnockoutObservable<string>;
        name: KnockoutObservable<string>;
        display: KnockoutObservable<string>;

        constructor(code: string, name: string) {
            this.code = ko.observable(code);
            this.name = ko.observable(name);
            this.display = ko.observable(code + " " + name);
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