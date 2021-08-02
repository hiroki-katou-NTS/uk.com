/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />
module nts.uk.at.view.kal012.b {

    const API = {
        //TODO API path
        getAllMailSet: "at/function/alarm/mailsetting/getinformailseting",
        addMailSet: "at/function/alarm/mailSetting/addMailSetting"
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
            alert("register")
        }

        openCDL025() {
            let self = this;
            let param = {
                currentCode: '03',
                roleType: 0,
                multiple: true
            };
            nts.uk.ui.windows.setShared("paramCdl025", param);
            nts.uk.ui.windows.sub.modal("com", "/view/cdl/025/index.xhtml").onClosed(() => {
                let data: Array<string> = nts.uk.ui.windows.getShared("dataCdl025");
                if (!nts.uk.util.isNullOrUndefined(data)) {

                }
                //self.listRoleID(data);
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

        setPara() {
            nts.uk.ui.windows.setShared("SetCC", true);
            nts.uk.ui.windows.setShared("SetBCC", true);
            nts.uk.ui.windows.setShared("SetReply", true);
            nts.uk.ui.windows.setShared("SetSubject", true);
            nts.uk.ui.windows.setShared("SetBody", true);
            nts.uk.ui.windows.setShared("wording", "");

        }

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

        constructor() {
            this.isMailAlreadySet = ko.observable(false);
            this.isMailSet = ko.observable(false);
            this.b41 = ko.observable("");
            this.b42 = ko.observable("");
            this.checked = ko.observable(true);
            this.selectedRuleCode = ko.observable(1);
            this.targetRuleName = ko.observable('');
            this.targetRuleRequired = ko.observable(true);
        }
    }
}