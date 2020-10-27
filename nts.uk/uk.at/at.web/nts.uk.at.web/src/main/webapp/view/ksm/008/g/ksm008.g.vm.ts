/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.ksm008.g {
    import setShared = nts.uk.ui.windows.setShared;
    import getShare = nts.uk.ui.windows.getShared;

    const PATH_API = {
        getStartupInfoCom: "screen/at/ksm008/g/getStartupInfoCom",
        registerCom: "consecutivework/consecutiveattendance/com/register",
        deleteCom: "consecutivework/consecutiveattendance/com/delete",

        getStartupInfoOrg: "screen/at/ksm008/h/getStartupInfoOrg",
        getMaxConsDays: "consecutivework/consecutiveattendance/org/getMaxConsDays",
        registerOrg: "consecutivework/consecutiveattendance/org/register",
        deleteOrg: "consecutivework/consecutiveattendance/org/delete",
    };

    @bean()
    export class KSM008GViewModel extends ko.ViewModel {
        // Flag
        isComSelected: KnockoutObservable<boolean> = ko.observable(false);
        isOrgSelected: KnockoutObservable<boolean> = ko.observable(false);
        deleteEnable: KnockoutObservable<boolean> = ko.observable(false);

        // Init
        code: KnockoutObservable<string> = ko.observable(""); //勤務予定のアラームチェック条件.コード
        conditionName: KnockoutObservable<string> = ko.observable(""); //勤務予定のアラームチェック条件.条件名
        explanation: KnockoutObservable<string> = ko.observable(""); //勤務予定のアラームチェック条件.サブ条件リスト.説明
        maxConsDaysCom: KnockoutObservable<number> = ko.observable(); //会社の連続出勤できる上限日数.日数.日数

        maxConsDaysOrg: KnockoutObservable<number> = ko.observable(); //組織の連続出勤できる上限日数.日数.日数
        unit: KnockoutObservable<number> = ko.observable(); //対象組織情報.単位
        workplaceId: KnockoutObservable<string> = ko.observable(""); //対象組織情報.職場ID
        workplaceGroupId: KnockoutObservable<string> = ko.observable(""); //対象組織情報.職場グループID
        orgCode: KnockoutObservable<string> = ko.observable(""); //組織の表示情報.コード
        displayName: KnockoutObservable<string> = ko.observable(""); //組織の表示情報.表示名

        codeAndConditionName: KnockoutObservable<string>;

        constructor(data: any) {
            super();
            const vm = this;

            vm.code(data.code);

            vm.codeAndConditionName = ko.computed(() => {
                return vm.code() + " " + vm.conditionName();
            }, this);
        }

        created() {
            const vm = this;

            vm.onSelectCom();

            _.extend(window, {vm});
        }

        mounted() {
        }

        /**
         * on click tab panel company action event
         */
        onSelectCom() {
            const vm = this;
            $(".nts-input").ntsError("clear");
            vm.$blockui("invisible");

            // Update flags.
            vm.isComSelected(true);
            vm.isOrgSelected(false);
            vm.deleteEnable(false);

            vm.$ajax(PATH_API.getStartupInfoCom, {code: vm.code()})
                .done(data => {
                    if (data) {
                        vm.code(data.code);
                        vm.conditionName(data.conditionName);

                        let explanation: string = "";
                        _.forEach(data.explanationList, (item) => {
                            explanation += item + "\n";
                        });
                        explanation = explanation.replace(/\\r/g, "\r");
                        explanation = explanation.replace(/\\n/g, "\n");
                        vm.explanation(explanation);

                        vm.maxConsDaysCom(data.maxConsDays);
                        if (data.maxConsDays != null) {
                            vm.deleteEnable(true);
                        }
                    }
                })
                .fail(res => {
                    vm.$dialog.error(res.message);
                })
                .always(() => {
                    $(".cons-day").focus();
                    vm.$blockui("clear");
                });
        }

        /**
         * on click tab panel Organization action event
         */
        onSelectOrg() {
            const vm = this;
            $(".nts-input").ntsError("clear");
            vm.$blockui("invisible");

            // Update flags.
            vm.isComSelected(false);
            vm.isOrgSelected(true);
            vm.deleteEnable(false);

            vm.$ajax(PATH_API.getStartupInfoOrg)
                .done(data => {
                    if (data) {
                        vm.unit(data.unit);
                        vm.workplaceId(data.workplaceId);
                        vm.workplaceGroupId(data.workplaceGroupId);
                        vm.orgCode(data.code);
                        vm.displayName(data.displayName);
                        vm.maxConsDaysOrg(data.maxConsDays);

                        if (data.maxConsDays != null) {
                            vm.deleteEnable(true);
                        }
                    }
                })
                .fail(res => {
                    vm.$dialog.error(res.message);
                })
                .always(() => {
                    $(".cons-day").focus();
                    vm.$blockui("clear");
                });
        }

        /**
         * function on click saveConsecutiveDays action
         */
        saveConsecutiveDays() {
            const vm = this;
            vm.$validate('.nts-editor')
                .then((valid: boolean) => {
                    if (!valid) {
                        return;
                    }
                });

            vm.$blockui("invisible");
            if (vm.isComSelected()) {
                if(vm.maxConsDaysCom() == null){
                    vm.$blockui("clear");
                    return;
                }

                vm.$ajax(PATH_API.registerCom, {maxConsDays: vm.maxConsDaysCom()})
                    .done(() => {
                        vm.$dialog.info({messageId: "Msg_15"}).then(() => {
                            vm.deleteEnable(true);
                        })
                    })
                    .fail(res => {
                        vm.$dialog.error(res.message);
                    })
                    .always(() => {
                        $(".cons-day").focus();
                        vm.$blockui("clear");
                    });
            }
            else if (vm.isOrgSelected()) {
                if(vm.maxConsDaysOrg() == null){
                    vm.$blockui("clear");
                    return;
                }

                vm.$ajax(PATH_API.registerOrg,
                    {
                        unit: vm.unit(),
                        workplaceId: vm.workplaceId(),
                        workplaceGroupId: vm.workplaceGroupId(),
                        maxConsDays: vm.maxConsDaysOrg()
                    })
                    .done(() => {
                        vm.$dialog.info({messageId: "Msg_15"}).then(() => {
                            vm.deleteEnable(true);
                        })
                    })
                    .fail(res => {
                        vm.$dialog.error(res.message);
                    })
                    .always(() => {
                        $(".cons-day").focus();
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
                                vm.$dialog.info({messageId: "Msg_16"}).then(() => {
                                    vm.maxConsDaysCom(null);
                                    vm.deleteEnable(false);
                                })
                            })
                            .fail(res => {
                                vm.$dialog.error(res.message);
                            })
                            .always(() => {
                                $(".cons-day").focus();
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
                                vm.$dialog.info({messageId: "Msg_16"}).then(() => {
                                    vm.maxConsDaysOrg(null);
                                    vm.deleteEnable(false);
                                })
                            })
                            .fail(res => {
                                vm.$dialog.error(res.message);
                            })
                            .always(() => {
                                $(".cons-day").focus();
                                vm.$blockui("clear");
                            });
                    }
                } else {
                    $(".cons-day").focus();
                }
            });
        }

        openDiaglog() {
            const vm = this;

            setShared("dataShareDialog046", {
                unit: vm.unit(),
                workplaceId: vm.workplaceId(),
                workplaceGroupId: vm.workplaceGroupId()
            });

            vm.$window.modal('../../../kdl/046/a/index.xhtml').then(() => {
                vm.$blockui("invisible");

                let dto: any = getShare("dataShareKDL046");
                if(_.isEmpty(dto)){
                    vm.$blockui("clear");
                    return;
                }

                if (dto.unit === 1) {
                    vm.unit(1);
                    vm.workplaceGroupId(dto.workplaceGroupID);
                    vm.orgCode(dto.workplaceGroupCode);
                    vm.displayName(dto.workplaceGroupName);
                } else {
                    vm.unit(0);
                    vm.workplaceId(dto.workplaceId);
                    vm.orgCode(dto.workplaceCode);
                    vm.displayName(dto.workplaceName);
                }

                vm.deleteEnable(false);
                vm.$ajax(PATH_API.getMaxConsDays,
                    {
                        unit: vm.unit(),
                        workplaceId: vm.workplaceId(),
                        workplaceGroupId: vm.workplaceGroupId()
                    })
                    .done(data => {
                        if (data) {
                            vm.maxConsDaysOrg(data.maxConsDays);
                            vm.deleteEnable(data.maxConsDays != null);
                        }
                    })
                    .fail(res => {
                        vm.$dialog.error(res.message);
                    })
                    .always(() => {
                        $(".cons-day").focus();
                        vm.$blockui("clear");
                    });
            });
        }

        close() {
            const vm = this;
            vm.$window.close();
        }
    }
}
