/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />
module nts.uk.at.view.kal012.b {

    const API = {
        //TODO API path
        getAllMailSet: "at/function/alarm/mailsetting/getinformailseting",
        addMailSet: "at/function/alarm/mailSetting/addMailSetting",
        register: "at/function/alarm/exmail/settings/register",
        init: "at/function/alarm/exmail/settings/init",
        GET_ROLENAME: "ctx/sys/auth/role/get/rolename/by/roleids"
    };

    @bean()
    class ViewModel extends ko.ViewModel {
        tabs: KnockoutObservableArray<nts.uk.ui.NtsTabPanelModel>;
        selectedTab: KnockoutObservable<string>;
        isUpdateMode: KnockoutObservable<boolean>;
        model: Model = new Model();
        roundingRules: KnockoutObservableArray<any>;
        selectedRuleCode: any;
        MailAutoAndNormalDto: MailAutoAndNormalDto;
        exMailSettings: ExMailSettings;

        constructor(params: any) {
            super();
            const vm = this;
            vm.tabs = ko.observableArray([
                {
                    id: 'tab-1',
                    title: vm.$i18n('KAL012_16'),
                    content: '.tab-content-1',
                    enable: ko.observable(true),
                    visible: ko.observable(true)
                },
                {
                    id: 'tab-2',
                    title: vm.$i18n('KAL012_17'),
                    content: '.tab-content-2',
                    enable: ko.observable(true),
                    visible: ko.observable(true)
                },
            ]);
            vm.selectedTab = ko.observable('tab-1');
            vm.model.b42(vm.$i18n('KAL012_14'));
            vm.model.b41(vm.$i18n('KAL012_14'));
            vm.roundingRules = ko.observableArray([
                {code: 1, name: vm.$i18n('KAL012_19')},
                {code: 2, name: vm.$i18n('KAL012_20')}
            ]);
            vm.selectedRuleCode = ko.observable(1);
            vm.isUpdateMode = ko.observable(false);
            vm.getAllMailSet();
            vm.init();
        }

        created() {
            const vm = this;
            _.extend(window, {vm});
            vm.model.isMailAlreadySet.subscribe((newValue: any) => {
                if (newValue) {
                    vm.model.b41(vm.$i18n('KAL012_14'))
                } else {
                    vm.model.b41(vm.$i18n('KAL012_13'))
                }
            });
            vm.model.selectedRuleCode.subscribe((newValue: any) => {
                if (newValue === 1) {
                    vm.model.targetRuleRequired(true)
                } else {
                    vm.model.targetRuleRequired(false)
                }
            });

            vm.model.rolesId.subscribe((value: Array<string>) => {
                vm.$ajax("com", API.GET_ROLENAME, value)
                    .done(function (listRole: Array<RoleDto>) {
                        vm.model.targetRuleName(_.join(_.map(listRole, i => i.name), '、'));
                    });
            })
        }

        /**
         * @param command
         */
        getAllMailSet(): JQueryPromise<any> {
            const vm = this;
            let dfd = $.Deferred<any>();
            vm.$blockui("invisible");
            var MailSettingsDefault = ({subject: "", text: "", mailAddressCC: [], mailAddressBCC: [], mailRely: ""});
            vm.$ajax(API.getAllMailSet).done((data) => {
                if (data) {
                    data.mailSettingNormalDto.mailSettings = data.mailSettingNormalDto.mailSettings == null ? MailSettingsDefault : data.mailSettingNormalDto.mailSettings;
                    data.mailSettingNormalDto.mailSettingAdmins = data.mailSettingNormalDto.mailSettingAdmins == null ? MailSettingsDefault : data.mailSettingNormalDto.mailSettingAdmins;
                    data.mailSettingAutomaticDto.mailSettings = data.mailSettingAutomaticDto.mailSettings == null ? MailSettingsDefault : data.mailSettingAutomaticDto.mailSettings;
                    data.mailSettingAutomaticDto.mailSettingAdmins = data.mailSettingAutomaticDto.mailSettingAdmins == null ? MailSettingsDefault : data.mailSettingAutomaticDto.mailSettingAdmins;

                    vm.MailAutoAndNormalDto = data;
                }
                dfd.resolve();
            }).fail(function (error) {
                dfd.reject();
            }).always(() => {
                vm.$blockui("clear");
            });
            return dfd.promise();
        }

        /**
         * init screen
         */
        init(): JQueryPromise<any> {
            const vm = this;
            let dfd = $.Deferred<any>();
            vm.$blockui("invisible");
            vm.$ajax(API.init).done((data) => {
                if (data) {
                    vm.exMailSettings = data;
                    vm.model.isMailAlreadySet(vm.exMailSettings.mailSettings.preConfigured);
                    if (vm.exMailSettings.sendingRole.roleSetting) {
                        vm.model.selectedRuleCode(1)
                    } else {
                        vm.model.selectedRuleCode(2)
                    }
                    vm.model.checked(vm.exMailSettings.sendingRole.sendResult);
                    vm.model.rolesId(vm.exMailSettings.sendingRole.roleIds);
                }
                dfd.resolve();
            }).fail(function (error) {
                dfd.reject();
            }).always(() => {
                vm.$blockui("clear");
            });
            return dfd.promise();
        }

        mounted() {
            const vm = this;
        }

        /**
         * function for back to screen
         */
        backToAScreen() {
            let vm = this;
        }

        clickRegistrationButton() {
            let vm = this;
            vm.$validate(".nts-input").then((valid) => {
                if (valid) {
                    let command = {
                        mailSettingList: [{
                            individualWkpClassify: 1,
                            normalAutoClassify: 0,
                            personalManagerClassify: 1,
                            contentMailSettings: {
                                subject: "test subject",
                                text: "test text",
                                mailAddressBCC: ["mail1@gmail.com", "mail3@gmail.com"],
                                mailAddressCC: ["mail2@gmail.com"],
                                mailRely: "mailss@gmail.com"
                            },
                            senderAddress: "mailsds@gmail.com",
                            sendResult: false
                        }],
                        sendingRole: {
                            individualWkpClassify: 1,
                            roleSetting: true,
                            sendResult: true,
                            roleIds: ["120"]
                        }
                    }
                    vm.$blockui("grayout");
                    vm.$ajax(API.register, command).done((res) => {
                        vm.$dialog.info({messageId: "Msg_15"}).then(() => {

                        });
                    }).fail((err) => {
                        vm.$dialog.error(err);
                    }).always(() => vm.$blockui("clear"));
                }
            });
        }

        openCDL025() {
            let vm = this;
            let param = {
                currentCode: vm.model.rolesId(),
                roleType: 3,
                multiple: true
            };
            nts.uk.ui.windows.setShared("paramCdl025", param);
            nts.uk.ui.windows.sub.modal("com", "/view/cdl/025/index.xhtml").onClosed(() => {
                let data: Array<string> = nts.uk.ui.windows.getShared("dataCdl025");
                if (!nts.uk.util.isNullOrUndefined(data)) {
                    vm.model.rolesId(data);
                }
            });
        }

        setMailAutoAd() {
            var vm = this;
            nts.uk.ui.windows.setShared("sendingAddressCheck", true);
            vm.setPara();
            nts.uk.ui.windows.setShared("senderAddress", vm.MailAutoAndNormalDto.mailSettingAutomaticDto.senderAddress)
            nts.uk.ui.windows.setShared("MailSettings", vm.MailAutoAndNormalDto.mailSettingAutomaticDto.mailSettingAdmins);
            nts.uk.ui.windows.sub.modal("com", "view/ccg/027/a/index.xhtml").onClosed(() => {
                let data = nts.uk.ui.windows.getShared("MailSettings");
                if (data != null) {
                    vm.MailAutoAndNormalDto.mailSettingAutomaticDto.mailSettingAdmins = data;
                    vm.MailAutoAndNormalDto.mailSettingAutomaticDto.senderAddress = nts.uk.ui.windows.getShared("senderAddress");
                    //console.log(self.MailAutoAndNormalDto);
                }
            });
        }

        setMailManual() {
            var vm = this;
            nts.uk.ui.windows.setShared("sendingAddressCheck", true);
            vm.setPara();
            nts.uk.ui.windows.setShared("senderAddress", vm.MailAutoAndNormalDto.mailSettingAutomaticDto.senderAddress)
            nts.uk.ui.windows.setShared("MailSettings", vm.MailAutoAndNormalDto.mailSettingAutomaticDto.mailSettings);
            nts.uk.ui.windows.sub.modal("com", "view/ccg/027/a/index.xhtml").onClosed(() => {
                let data = nts.uk.ui.windows.getShared("MailSettings");
                if (data != null) {
                    vm.MailAutoAndNormalDto.mailSettingAutomaticDto.mailSettings = data;
                    vm.MailAutoAndNormalDto.mailSettingAutomaticDto.senderAddress = nts.uk.ui.windows.getShared("senderAddress");
                    //console.log(self.MailAutoAndNormalDto);
                }
            });
        }

        setPara() {
            nts.uk.ui.windows.setShared("SetCC", true);
            nts.uk.ui.windows.setShared("SetBCC", true);
            nts.uk.ui.windows.setShared("SetReply", true);
            nts.uk.ui.windows.setShared("SetSubject", true);
            nts.uk.ui.windows.setShared("SetBody", true);
            nts.uk.ui.windows.setShared("wording", "");

        }

    }

    interface RoleDto {
        // Id
        roleId: string;
        // コード
        roleCode: string;
        // ロール種類
        roleType: number;
        // 参照範囲
        employeeReferenceRange: number;
        // ロール名称
        name: string;
        // 契約コード
        contractCode: string;
        // 担当区分
        assignAtr: number;
        // 会社ID
        companyId: string;
    }

    export interface SendingRole {
        individualWkpClassify?: number;
        roleSetting?: boolean;
        sendResult: boolean;
        roleIds: Array<string>;
    }

    export interface MailSettings {
        preConfigured?: boolean;
    }

    export interface ExMailSettings {
        sendingRole?: SendingRole;
        mailSettings?: MailSettings;
    }


    export interface MailSettingsDto {
        subject?: string;
        text?: string;
        mailAddressCC: Array<string>;
        mailAddressBCC: Array<string>;
        mailRely?: string;
    }

    export interface MailSettingNormalDto {
        mailSettings?: MailSettingsDto;
        mailSettingAdmins?: MailSettingsDto;
    }

    export interface MailSettingAutomaticDto {
        mailSettings?: MailSettingsDto;
        mailSettingAdmins?: MailSettingsDto;
        senderAddress?: string;
    }

    export interface MailAutoAndNormalDto {
        mailSettingAutomaticDto: MailSettingAutomaticDto;
        mailSettingNormalDto: MailSettingNormalDto;
    }

    class Model {
        isMailAlreadySet: KnockoutObservable<boolean>;
        isMailSet: KnockoutObservable<boolean>;
        b41: KnockoutObservable<any>;
        b42: KnockoutObservable<any>;
        checked: KnockoutObservable<any>;
        selectedRuleCode: KnockoutObservable<any>;
        targetRuleName: KnockoutObservable<any>;
        targetRuleRequired: KnockoutObservable<boolean>;
        rolesId: KnockoutObservableArray<any>;

        constructor() {
            this.isMailAlreadySet = ko.observable(false);
            this.isMailSet = ko.observable(false);
            this.b41 = ko.observable("");
            this.b42 = ko.observable("");
            this.checked = ko.observable(true);
            this.selectedRuleCode = ko.observable(1);
            this.targetRuleName = ko.observable('');
            this.targetRuleRequired = ko.observable(true);
            this.rolesId = ko.observableArray([]);
        }
    }
}