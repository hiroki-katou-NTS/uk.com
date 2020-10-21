/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.ksm008.g {

    const PATH_API = {
        getStartupInfoCom: "screen/at/ksm008/g/getStartupInfoCom",
        registerCom: "consecutivework/consecutiveattendance/com/register",
        deleteCom: "consecutivework/consecutiveattendance/com/delete",

        getStartupInfoOrg: "screen/at/ksm008/h/getStartupInfoOrg",
        registerOrg: "consecutivework/consecutiveattendance/org/register",
        deleteOrg: "consecutivework/consecutiveattendance/org/delete",
    };

    @bean()
    export class KSM008GViewModel extends ko.ViewModel {
        // Flag
        isComSelected: KnockoutObservable<boolean> = ko.observable(false);
        isOrgSelected: KnockoutObservable<boolean> = ko.observable(false);
        deleteEnable: boolean = false;

        // Init
        code: KnockoutObservable<string> = ko.observable(); //勤務予定のアラームチェック条件.コード

        //会社の連続出勤できる上限日数.日数.日数
        //組織の連続出勤できる上限日数.日数.日数
        maxConsDays: KnockoutObservable<number> = ko.observable();

        conditionName: KnockoutObservable<string> = ko.observable(); //勤務予定のアラームチェック条件.条件名
        explanation: KnockoutObservable<string> = ko.observable(); //勤務予定のアラームチェック条件.サブ条件リスト.説明

        unit: KnockoutObservable<number> = ko.observable(); //対象組織情報.単位
        workplaceId: KnockoutObservable<string> = ko.observable(); //対象組織情報.職場ID
        workplaceGroupId: KnockoutObservable<string> = ko.observable(); //対象組織情報.職場グループID
        displayName: KnockoutObservable<string> = ko.observable(); //組織の表示情報.表示名

        codeAndConditionName: KnockoutObservable<string>;

        constructor(params: any) {
            super();
            const vm = this;

            vm.codeAndConditionName = ko.computed(() => {
                return vm.code() + " " + vm.conditionName();
            }, this);
        }

        created() {
            const vm = this;

            vm.onSelectCom();

            vm.maxConsDays.subscribe(newValue => {
                if (nts.uk.text.isNullOrEmpty(newValue)) return;

                vm.$errors("clear");
            });

            _.extend(window, {vm});
        }

        mounted() {
            const vm = this;
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

            vm.$ajax(PATH_API.getStartupInfoCom)
                .done(data => {
                    if (data) {
                        vm.code(data.code);
                        vm.conditionName(data.conditionName);
                        vm.explanation(data.explanation);
                        vm.maxConsDays(data.maxConsDays);

                        if(vm.maxConsDays())
                            vm.deleteEnable = true;
                    }
                })
                .fail(res => {
                    vm.$dialog.error(res.message);
                })
                .always(() => {
                    $("#G5_2").focus();
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

            vm.$ajax(PATH_API.getStartupInfoOrg)
                .done(data => {
                    if (data) {
                        vm.unit(data.unit);
                        vm.workplaceId(data.workplaceId);
                        vm.workplaceGroupId(data.workplaceGroupId);
                        vm.code(data.code);
                        vm.displayName(data.displayName);
                        vm.maxConsDays(data.maxConsDays);

                        if(vm.maxConsDays())
                            vm.deleteEnable = true;
                    }
                })
                .fail(res => {
                    vm.$dialog.error(res.message);
                })
                .always(() => {
                    $("#H2_2").focus();
                    vm.$blockui("clear");
                });
        }

        /**
         * function on click saveConsecutiveDays action
         */
        saveConsecutiveDays() {
            const vm = this;
            vm.$validate('input.nts-input');
            vm.$validate('.nts-editor')
                .then((valid: boolean) => {
                    if (!valid) {
                        return;
                    }
                });

            vm.$blockui("invisible");
            if (vm.isComSelected()) {
                vm.$ajax(PATH_API.registerCom, {maxConsDays: vm.maxConsDays()})
                    .done(() => {
                        vm.$dialog.info({messageId: "Msg_15"});
                    })
                    .fail(res => {
                        vm.$dialog.error(res.message);
                    })
                    .always(() => {
                        $("#G5_2").focus();
                        vm.$blockui("clear");
                    });
            }
            else if (vm.isOrgSelected()) {
                vm.$ajax(PATH_API.registerOrg,
                    {
                        unit: vm.unit(),
                        workplaceId: vm.workplaceId(),
                        workplaceGroupId: vm.workplaceGroupId(),
                        maxConsDays: vm.maxConsDays()
                    })
                    .done(() => {
                        vm.$dialog.info({messageId: "Msg_15"});
                    })
                    .fail(res => {
                        vm.$dialog.error(res.message);
                    })
                    .always(() => {
                        $("#H2_2").focus();
                        vm.$blockui("clear");
                    });
            }
        }

        /**
         * function on click deleteConsecutiveDays action
         */
        deleteConsecutiveDays() {
            const vm = this;
            vm.$dialog.confirm({messageId: "Msg_18"}).then((result: 'no' | 'yes' | 'cancel') => {
                if (result === 'yes') {
                    vm.$blockui("invisible");

                    if (vm.isComSelected()) {
                        vm.$ajax(PATH_API.deleteCom)
                            .done(() => {
                                vm.$dialog.info({messageId: "Msg_15"});
                            })
                            .fail(res => {
                                vm.$dialog.error(res.message);
                            })
                            .always(() => {
                                $("#G5_2").focus();
                                vm.$blockui("clear");
                            });
                    }
                    else if (vm.isOrgSelected()) {
                        vm.$ajax(PATH_API.deleteOrg,
                            {
                                unit: vm.unit(),
                                workplaceId: vm.workplaceId(),
                                workplaceGroupId: vm.workplaceGroupId(),
                            })
                            .done(() => {
                                vm.$dialog.info({messageId: "Msg_15"});
                            })
                            .fail(res => {
                                vm.$dialog.error(res.message);
                            })
                            .always(() => {
                                $("#H2_2").focus();
                                vm.$blockui("clear");
                            });
                    }
                }
            });
        }

        close() {
            const vm = this;
            vm.$window.close();
        }
    }
}
