module nts.uk.com.view.cmm048.a {

  const API = {
    find: "query/cmm048userinformation/find",
    update: "ctx/sys/auth/user/information/update"
  };
  @bean()
  export class ViewModel extends ko.ViewModel {

    //A
    A4_1_Value: KnockoutObservable<string> = ko.observable('');
    A5_2_Value: KnockoutObservable<string> = ko.observable('');
    A6_2_Value: KnockoutObservable<string> = ko.observable('');
    A6_4_Value: KnockoutObservable<string> = ko.observable('');
    A6_6_Value: KnockoutObservable<string> = ko.observable('');
    A6_8_Value: KnockoutObservable<string> = ko.observable('');
    A7_3_Value: KnockoutObservable<string> = ko.observable('');
    A7_5_Value: KnockoutObservable<string> = ko.observable('');
    A7_7_Value: KnockoutObservable<string> = ko.observable('');
    A7_9_Value: KnockoutObservable<string> = ko.observable('');
    A7_11_Value: KnockoutObservable<string> = ko.observable('');
    A7_13_Value: KnockoutObservable<string> = ko.observable('');
    A7_15_Value: KnockoutObservable<string> = ko.observable('');
    A7_17_Value: KnockoutObservable<string> = ko.observable('');
    A7_19_Value: KnockoutObservable<string> = ko.observable('');
    A7_21_Value: KnockoutObservable<string> = ko.observable('');
    A9_1_Value: KnockoutObservable<boolean> = ko.observable(true);
    A9_3_Value: KnockoutObservable<boolean> = ko.observable(true);
    A9_5_Value: KnockoutObservable<boolean> = ko.observable(true);
    A9_7_Value: KnockoutObservable<boolean> = ko.observable(true);
    A9_9_Value: KnockoutObservable<boolean> = ko.observable(true);
    A9_11_Value: KnockoutObservable<boolean> = ko.observable(true);
    A9_13_Value: KnockoutObservable<boolean> = ko.observable(true);
    A9_15_Value: KnockoutObservable<boolean> = ko.observable(true);
    A9_17_Value: KnockoutObservable<boolean> = ko.observable(true);
    A9_19_Value: KnockoutObservable<boolean> = ko.observable(true);
    ListOtherContact: KnockoutObservableArray<OtherContactViewModel> = ko.observableArray([]);

    //B
    B3_2_Value: KnockoutObservable<string> = ko.observable('');
    B4_2_Value: KnockoutObservable<string> = ko.observable('');
    B5_2_Value: KnockoutObservable<string> = ko.observable('');
    B2_2_Value: KnockoutObservable<string> = ko.observable('');
    //// Password policy
    B6_4_Value: KnockoutObservable<string> = ko.observable('');
    B6_6_Value: KnockoutObservable<string> = ko.observable('');
    B6_7_Value: KnockoutObservable<string> = ko.observable('');
    B6_8_Value: KnockoutObservable<string> = ko.observable('');
    B6_10_Value: KnockoutObservable<string> = ko.observable('');
    B6_12_Value: KnockoutObservable<string> = ko.observable('');

    //C
    C2_6_Options: KnockoutObservableArray<ItemCbx> = ko.observableArray([
      new ItemCbx(REMIND_DATE.BEFORE_ZERO_DAY, "当日"),
      new ItemCbx(REMIND_DATE.BEFORE_ONE_DAY, "１日前"),
      new ItemCbx(REMIND_DATE.BEFORE_TWO_DAY, "２日前"),
      new ItemCbx(REMIND_DATE.BEFORE_THREE_DAY, "３日前"),
      new ItemCbx(REMIND_DATE.BEFORE_FOUR_DAY, "４日前"),
      new ItemCbx(REMIND_DATE.BEFORE_FIVE_DAY, "５日前"),
      new ItemCbx(REMIND_DATE.BEFORE_SIX_DAY, "６日前"),
      new ItemCbx(REMIND_DATE.BEFORE_SEVEN_DAY, "７日前"),
    ]);
    listAnniversary: KnockoutObservableArray<AnniversaryNotificationViewModel> = ko.observableArray([]);

    //D
    D2_2_Value: KnockoutObservable<number> = ko.observable(0);
    D2_2_Options: KnockoutObservableArray<ItemCbx> = ko.observableArray([
      new ItemCbx(LANGUAGE.JAPANESE, "日本語"),
      new ItemCbx(LANGUAGE.ENGLISH, "英語"),
      new ItemCbx(LANGUAGE.OTHER, "その他"),
    ]);

    //condition to show off
    isInCharge: KnockoutObservable<boolean> = ko.observable(false);
    A11_Condition: KnockoutObservable<boolean> = ko.observable(false);
    A12_Condition: KnockoutObservable<boolean> = ko.observable(false);
    A13_Condition: KnockoutObservable<boolean> = ko.observable(false);
    A14_Condition: KnockoutObservable<boolean> = ko.observable(false);
    A15_Condition: KnockoutObservable<boolean> = ko.observable(false);
    A16_Condition: KnockoutObservable<boolean> = ko.observable(false);
    A17_Condition: KnockoutObservable<boolean> = ko.observable(false);
    A18_Condition: KnockoutObservable<boolean> = ko.observable(false);
    A19_Condition: KnockoutObservable<boolean> = ko.observable(false);
    A20_Condition: KnockoutObservable<boolean> = ko.observable(false);
    A21_Condition: KnockoutObservable<boolean> = ko.observable(false);
    A22_Condition: KnockoutObservable<boolean> = ko.observable(false);
    A23_Condition: KnockoutObservable<boolean> = ko.observable(false);
    A24_Condition: KnockoutObservable<boolean> = ko.observable(false);
    A25_Condition: KnockoutObservable<boolean> = ko.observable(false);
    A26_Condition: KnockoutObservable<boolean> = ko.observable(false);
    A27_Condition: KnockoutObservable<boolean> = ko.observable(false);
    A28_Condition: KnockoutObservable<boolean> = ko.observable(false);
    A29_Condition: KnockoutObservable<boolean> = ko.observable(false);
    A30_Condition: KnockoutObservable<boolean> = ko.observable(false);
    A31_Condition: KnockoutObservable<boolean> = ko.observable(false);
    A11_1_Condition: KnockoutObservable<boolean> = ko.observable(false);
    A12_1_Condition: KnockoutObservable<boolean> = ko.observable(false);
    A13_1_Condition: KnockoutObservable<boolean> = ko.observable(false);
    A14_1_Condition: KnockoutObservable<boolean> = ko.observable(false);
    A15_1_Condition: KnockoutObservable<boolean> = ko.observable(false);
    A16_1_Condition: KnockoutObservable<boolean> = ko.observable(false);
    A17_1_Condition: KnockoutObservable<boolean> = ko.observable(false);
    A18_1_Condition: KnockoutObservable<boolean> = ko.observable(false);
    A19_1_Condition: KnockoutObservable<boolean> = ko.observable(false);
    A20_1_Condition: KnockoutObservable<boolean> = ko.observable(false);
    A21_1_Condition: KnockoutObservable<boolean> = ko.observable(false);
    A22_1_Condition: KnockoutObservable<boolean> = ko.observable(false);
    A23_1_Condition: KnockoutObservable<boolean> = ko.observable(false);
    A24_1_Condition: KnockoutObservable<boolean> = ko.observable(false);
    A25_1_Condition: KnockoutObservable<boolean> = ko.observable(false);
    A26_1_Condition: KnockoutObservable<boolean> = ko.observable(false);
    A27_1_Condition: KnockoutObservable<boolean> = ko.observable(false);
    A28_1_Condition: KnockoutObservable<boolean> = ko.observable(false);
    A29_1_Condition: KnockoutObservable<boolean> = ko.observable(false);
    A30_1_Condition: KnockoutObservable<boolean> = ko.observable(false);
    A31_1_Condition: KnockoutObservable<boolean> = ko.observable(false);
    A32_1_Condition: KnockoutObservable<boolean> = ko.observable(false);
    A33_1_Condition: KnockoutObservable<boolean> = ko.observable(false);
    A34_1_Condition: KnockoutObservable<boolean> = ko.observable(false);
    A35_1_Condition: KnockoutObservable<boolean> = ko.observable(false);
    A36_1_Condition: KnockoutObservable<boolean> = ko.observable(false);
    A37_1_Condition: KnockoutObservable<boolean> = ko.observable(false);
    A38_1_Condition: KnockoutObservable<boolean> = ko.observable(false);
    A39_1_Condition: KnockoutObservable<boolean> = ko.observable(false);
    A40_1_Condition: KnockoutObservable<boolean> = ko.observable(false);
    A41_1_Condition: KnockoutObservable<boolean> = ko.observable(false);
    A42_1_Condition: KnockoutObservable<boolean> = ko.observable(false);
    A11_2_Condition: KnockoutObservable<boolean> = ko.observable(false);
    A12_2_Condition: KnockoutObservable<boolean> = ko.observable(false);
    A13_2_Condition: KnockoutObservable<boolean> = ko.observable(false);
    A14_2_Condition: KnockoutObservable<boolean> = ko.observable(false);
    A15_2_Condition: KnockoutObservable<boolean> = ko.observable(false);
    A16_2_Condition: KnockoutObservable<boolean> = ko.observable(false);
    A17_2_Condition: KnockoutObservable<boolean> = ko.observable(false);
    A18_2_Condition: KnockoutObservable<boolean> = ko.observable(false);
    A19_2_Condition: KnockoutObservable<boolean> = ko.observable(false);
    A20_2_Condition: KnockoutObservable<boolean> = ko.observable(false);
    A21_2_Condition: KnockoutObservable<boolean> = ko.observable(false);
    A22_2_Condition: KnockoutObservable<boolean> = ko.observable(false);
    A23_2_Condition: KnockoutObservable<boolean> = ko.observable(false);
    A24_2_Condition: KnockoutObservable<boolean> = ko.observable(false);
    A25_2_Condition: KnockoutObservable<boolean> = ko.observable(false);
    A26_2_Condition: KnockoutObservable<boolean> = ko.observable(false);
    A27_2_Condition: KnockoutObservable<boolean> = ko.observable(false);
    A28_2_Condition: KnockoutObservable<boolean> = ko.observable(false);
    A29_2_Condition: KnockoutObservable<boolean> = ko.observable(false);
    A30_2_Condition: KnockoutObservable<boolean> = ko.observable(false);
    A31_2_Condition: KnockoutObservable<boolean> = ko.observable(false);
    A32_2_Condition: KnockoutObservable<boolean> = ko.observable(false);
    A33_2_Condition: KnockoutObservable<boolean> = ko.observable(false);
    A34_2_Condition: KnockoutObservable<boolean> = ko.observable(false);
    A35_2_Condition: KnockoutObservable<boolean> = ko.observable(false);
    A36_2_Condition: KnockoutObservable<boolean> = ko.observable(false);
    A37_2_Condition: KnockoutObservable<boolean> = ko.observable(false);
    A38_2_Condition: KnockoutObservable<boolean> = ko.observable(false);
    A39_2_Condition: KnockoutObservable<boolean> = ko.observable(false);
    A40_2_Condition: KnockoutObservable<boolean> = ko.observable(false);
    A41_2_Condition: KnockoutObservable<boolean> = ko.observable(false);
    A42_2_Condition: KnockoutObservable<boolean> = ko.observable(false);
    A11_3_Condition: KnockoutObservable<boolean> = ko.observable(false);
    A12_3_Condition: KnockoutObservable<boolean> = ko.observable(false);
    A13_3_Condition: KnockoutObservable<boolean> = ko.observable(false);
    A14_3_Condition: KnockoutObservable<boolean> = ko.observable(false);
    A15_3_Condition: KnockoutObservable<boolean> = ko.observable(false);
    A16_3_Condition: KnockoutObservable<boolean> = ko.observable(false);
    A17_3_Condition: KnockoutObservable<boolean> = ko.observable(false);
    A18_3_Condition: KnockoutObservable<boolean> = ko.observable(false);
    A19_3_Condition: KnockoutObservable<boolean> = ko.observable(false);
    A20_3_Condition: KnockoutObservable<boolean> = ko.observable(false);
    A21_3_Condition: KnockoutObservable<boolean> = ko.observable(false);
    A22_3_Condition: KnockoutObservable<boolean> = ko.observable(false);
    A23_3_Condition: KnockoutObservable<boolean> = ko.observable(false);
    A24_3_Condition: KnockoutObservable<boolean> = ko.observable(false);
    A25_3_Condition: KnockoutObservable<boolean> = ko.observable(false);
    A26_3_Condition: KnockoutObservable<boolean> = ko.observable(false);
    A27_3_Condition: KnockoutObservable<boolean> = ko.observable(false);
    A28_3_Condition: KnockoutObservable<boolean> = ko.observable(false);
    A29_3_Condition: KnockoutObservable<boolean> = ko.observable(false);
    A30_3_Condition: KnockoutObservable<boolean> = ko.observable(false);
    A31_3_Condition: KnockoutObservable<boolean> = ko.observable(false);
    A32_3_Condition: KnockoutObservable<boolean> = ko.observable(false);
    A33_3_Condition: KnockoutObservable<boolean> = ko.observable(false);
    A34_3_Condition: KnockoutObservable<boolean> = ko.observable(false);
    A35_3_Condition: KnockoutObservable<boolean> = ko.observable(false);
    A36_3_Condition: KnockoutObservable<boolean> = ko.observable(false);
    A37_3_Condition: KnockoutObservable<boolean> = ko.observable(false);
    A38_3_Condition: KnockoutObservable<boolean> = ko.observable(false);
    A39_3_Condition: KnockoutObservable<boolean> = ko.observable(false);
    A40_3_Condition: KnockoutObservable<boolean> = ko.observable(false);
    A41_3_Condition: KnockoutObservable<boolean> = ko.observable(false);
    A42_3_Condition: KnockoutObservable<boolean> = ko.observable(false);


    //general
    tabs: KnockoutObservableArray<any> = ko.observableArray([{
      id: 'tab-1',
      title: this.generateTitleTab(this.$i18n('CMM048_92'), 'setting'),
      content: '.tab-content-1',
      enable: ko.observable(true),
      visible: ko.observable(true),
    },
    {
      id: 'tab-2',
      title: this.generateTitleTab(this.$i18n('CMM048_93'), 'security'),
      content: '.tab-content-2',
      enable: ko.observable(true),
      visible: ko.observable(true),
    },
    {
      id: 'tab-3',
      title: this.generateTitleTab(this.$i18n('CMM048_94'), 'notice'),
      content: '.tab-content-3',
      enable: ko.observable(true),
      visible: ko.observable(true),
    },
    {
      id: 'tab-4',
      title: this.generateTitleTab(this.$i18n('CMM048_95'), 'language'),
      content: '.tab-content-4',
      enable: ko.observable(true),
      visible: ko.observable(true),
    }]);
    selectedTab: KnockoutObservable<string> = ko.observable('tab-1');
    //code
    companyId: string = '';
    employeeId: string = '';
    personId: string = '';

    mounted() {
      const vm = this;
      vm.init();
    }

    private init() {
      const vm = this;
      vm.$blockui('grayout')
      vm.$ajax(API.find).then((data: UserInformationDto) => {
        //set code
        vm.companyId = data.employeeDataMngInfo.companyId;
        vm.employeeId = data.employeeDataMngInfo.employeeId;
        vm.personId = data.employeeDataMngInfo.personId;

        //set data for tab A
        ////Handle avatar
        vm.A4_1_Value(data.userAvatar.fileId);
        const businessName: string = data.person.personNameGroup.businessName;
        const avatarFileId: string = data.userAvatar.fileId;
        if (avatarFileId) {
          $("#avatar-change").append(
            $("<img/>")
              .attr("alt", 'Avatar')
              .attr("class", 'avatar')
              .attr("id", 'A4_1')
              .attr("src", (nts.uk.request as any).liveView(avatarFileId))
          );
        } else {
          $("#avatar-change").ready(() => {
            $("#avatar-change").append(
              "<div class='avatar' id='A4_1_no_avatar'>" + businessName.substring(0, 2) + "</div>"
            );
          });
        }
        vm.A5_2_Value(businessName);
        vm.A6_2_Value(data.employeeDataMngInfo.employeeCode);
        vm.A6_4_Value(data.wkpDisplayName);
        vm.A6_6_Value(data.positionName);
        vm.A6_8_Value(data.hireDate);
        vm.A7_3_Value(data.employeeContact.cellPhoneNumber);
        vm.A7_5_Value(data.personalContact.phoneNumber);
        vm.A7_7_Value(data.personalContact.emergencyContact1.phoneNumber);
        vm.A7_9_Value(data.personalContact.emergencyContact2.phoneNumber);
        vm.A7_11_Value(data.employeeContact.seatDialIn);
        vm.A7_13_Value(data.employeeContact.seatExtensionNumber);
        vm.A7_15_Value(data.employeeContact.mailAddress);
        vm.A7_17_Value(data.employeeContact.mobileMailAddress);
        vm.A7_19_Value(data.personalContact.mailAddress);
        vm.A7_21_Value(data.personalContact.mobileEmailAddress);
        const listOtherContactPs = data.personalContact.otherContacts;
        const listOtherContactSetting = data.settingInformation.settingContactInformation.otherContacts;
        for (let i = 1; i < 6; i++) {
          const OtherContactSetting: OtherContactDto = _.find(listOtherContactSetting, (contact: OtherContactDto) => contact.no === i);
          const OtherContactPs: OtherContactDtoPs = _.find(listOtherContactPs, (contact: OtherContactDtoPs) => contact.otherContactNo === i);
          if (OtherContactPs) {
            vm.ListOtherContact.push(
              new OtherContactViewModel(
                i,
                OtherContactSetting.contactName,
                OtherContactPs.address,
                OtherContactSetting.contactUsageSetting === 2,
                OtherContactSetting.contactUsageSetting !== 0,
                OtherContactPs.isDisplay
              )
            )
          } else {
            vm.ListOtherContact.push(
              new OtherContactViewModel(
                i,
                OtherContactSetting.contactName,
                '',
                OtherContactSetting.contactUsageSetting === 2,
                OtherContactSetting.contactUsageSetting !== 0,
                true
              )
            )
          }

        };
        vm.A9_1_Value(data.employeeContact.isCellPhoneNumberDisplay == null ? true : data.employeeContact.isCellPhoneNumberDisplay);
        vm.A9_3_Value(data.personalContact.isPhoneNumberDisplay == null ? true : data.personalContact.isPhoneNumberDisplay);
        vm.A9_5_Value(data.personalContact.isEmergencyContact1Display == null ? true : data.personalContact.isEmergencyContact1Display);
        vm.A9_7_Value(data.personalContact.isEmergencyContact2Display == null ? true : data.personalContact.isEmergencyContact2Display);
        vm.A9_9_Value(data.employeeContact.isSeatDialInDisplay == null ? true : data.employeeContact.isSeatDialInDisplay);
        vm.A9_11_Value(data.employeeContact.isSeatExtensionNumberDisplay == null ? true : data.employeeContact.isSeatExtensionNumberDisplay);
        vm.A9_13_Value(data.employeeContact.isMailAddressDisplay == null ? true : data.employeeContact.isMailAddressDisplay);
        vm.A9_15_Value(data.employeeContact.isMobileMailAddressDisplay == null ? true : data.employeeContact.isMobileMailAddressDisplay);
        vm.A9_17_Value(data.personalContact.isMailAddressDisplay == null ? true : data.personalContact.isMailAddressDisplay);
        vm.A9_19_Value(data.personalContact.isMobileEmailAddressDisplay == null ? true : data.personalContact.isMobileEmailAddressDisplay);

        //set data for tab B
        if (data.passwordChangeLog) {
          const today = moment().utc();
          const changePassDay = moment(data.passwordChangeLog.modifiedDate, 'YYYY/MM/DD HH:mm:ss').utc();
          const lastChangePass = moment.duration(today.diff(changePassDay)).humanize();
          if (data.passwordPolicy.validityPeriod) {
            const cmm4897: string = vm.$i18n('CMM048_97', [lastChangePass]);
            vm.B2_2_Value(cmm4897);
          } else {
            const timeLeft = Math.round(data.passwordPolicy.validityPeriod - moment.duration(today.diff(changePassDay)).asDays());
            const cmm4899: string = vm.$i18n('CMM048_99', [lastChangePass, String(timeLeft)]);
            vm.B2_2_Value(cmm4899);
          }
        } else {
          vm.B2_2_Value(vm.$i18n('CMM048_98'));
        }

        vm.B6_4_Value(vm.$i18n('CMM048_13', [String(data.passwordPolicy.validityPeriod)]));
        vm.B6_6_Value(vm.$i18n('CMM048_15', [String(data.passwordPolicy.lowestDigits)]));
        vm.B6_7_Value(vm.$i18n('CMM048_16', [String(data.passwordPolicy.alphabetDigit)]));
        vm.B6_8_Value(vm.$i18n('CMM048_17', [String(data.passwordPolicy.numberOfDigits)]));
        vm.B6_10_Value(vm.$i18n('CMM048_19', [String(data.passwordPolicy.symbolCharacters)]));
        vm.B6_12_Value(vm.$i18n('CMM048_21', [String(data.passwordPolicy.validityPeriod)]));

        //set data for tab C
        if (data.anniversaryNotices.length !== 0) {
          _.map(data.anniversaryNotices, (anniversary: AnniversaryNoticeDto) => {
            const newItem: AnniversaryNotificationViewModel =
              new AnniversaryNotificationViewModel(
                anniversary.anniversary,
                anniversary.anniversaryTitle,
                anniversary.notificationMessage,
                anniversary.noticeDay
              );
            vm.listAnniversary.push(newItem);
          });
        } else {
          vm.listAnniversary.push(new AnniversaryNotificationViewModel("", "", "", 0));
        }

        //set data for tab D
        vm.D2_2_Value(data.user.language);

        //condition to show off
        vm.isInCharge(data.isInCharge);

        const isUseOfProfile: boolean = data.settingInformation.useOfProfile === IS_USE.USE;
        const isUseOfPassword: boolean = data.settingInformation.useOfPassword === IS_USE.USE;
        const isUseOfNotice: boolean = data.settingInformation.useOfNotice === IS_USE.USE;
        const isUseOfLanguage: boolean = data.settingInformation.useOfLanguage === IS_USE.USE;

        const displaySetting = data.settingInformation.settingContactInformation;
        vm.A11_Condition(displaySetting.companyMobilePhone.contactUsageSetting !== CONTACT_USAGE.DO_NOT_USE);
        vm.A12_Condition(displaySetting.companyMobilePhone.updatable === IS_USE.USE);
        vm.A13_Condition(displaySetting.personalMobilePhone.contactUsageSetting !== CONTACT_USAGE.DO_NOT_USE);
        vm.A14_Condition(displaySetting.personalMobilePhone.updatable === IS_USE.USE);
        vm.A15_Condition(displaySetting.emergencyNumber1.contactUsageSetting !== CONTACT_USAGE.DO_NOT_USE);
        vm.A16_Condition(displaySetting.emergencyNumber1.updatable === IS_USE.USE);
        vm.A17_Condition(displaySetting.emergencyNumber2.contactUsageSetting !== CONTACT_USAGE.DO_NOT_USE);
        vm.A18_Condition(displaySetting.emergencyNumber2.updatable === IS_USE.USE);
        vm.A19_Condition(displaySetting.dialInNumber.contactUsageSetting !== CONTACT_USAGE.DO_NOT_USE);
        vm.A20_Condition(displaySetting.dialInNumber.updatable === IS_USE.USE);
        vm.A21_Condition(displaySetting.extensionNumber.contactUsageSetting !== CONTACT_USAGE.DO_NOT_USE);
        vm.A22_Condition(displaySetting.extensionNumber.updatable === IS_USE.USE);
        vm.A23_Condition(displaySetting.companyEmailAddress.contactUsageSetting !== CONTACT_USAGE.DO_NOT_USE);
        vm.A24_Condition(displaySetting.companyEmailAddress.updatable === IS_USE.USE);
        vm.A25_Condition(displaySetting.personalEmailAddress.contactUsageSetting !== CONTACT_USAGE.DO_NOT_USE);
        vm.A26_Condition(displaySetting.personalEmailAddress.updatable === IS_USE.USE);
        vm.A27_Condition(displaySetting.companyMobileEmailAddress.contactUsageSetting !== CONTACT_USAGE.DO_NOT_USE);
        vm.A28_Condition(displaySetting.companyMobileEmailAddress.updatable === IS_USE.USE);
        vm.A29_Condition(displaySetting.personalMobileEmailAddress.contactUsageSetting !== CONTACT_USAGE.DO_NOT_USE);
        vm.A30_Condition(displaySetting.personalMobileEmailAddress.updatable === IS_USE.USE);
        //A31_Condition and A32_Condition in ListOtherContact
        vm.A33_1_Condition(displaySetting.companyMobilePhone.contactUsageSetting === CONTACT_USAGE.DO_NOT_USE);
        vm.A33_2_Condition(displaySetting.companyMobilePhone.contactUsageSetting === CONTACT_USAGE.USE);
        vm.A33_3_Condition(displaySetting.companyMobilePhone.contactUsageSetting === CONTACT_USAGE.INDIVIDUAL_SELECT);
        vm.A34_1_Condition(displaySetting.personalMobilePhone.contactUsageSetting === CONTACT_USAGE.DO_NOT_USE);
        vm.A34_2_Condition(displaySetting.personalMobilePhone.contactUsageSetting === CONTACT_USAGE.USE);
        vm.A34_3_Condition(displaySetting.personalMobilePhone.contactUsageSetting === CONTACT_USAGE.INDIVIDUAL_SELECT);
        vm.A35_1_Condition(displaySetting.emergencyNumber1.contactUsageSetting === CONTACT_USAGE.DO_NOT_USE);
        vm.A35_2_Condition(displaySetting.emergencyNumber1.contactUsageSetting === CONTACT_USAGE.USE);
        vm.A35_3_Condition(displaySetting.emergencyNumber1.contactUsageSetting === CONTACT_USAGE.INDIVIDUAL_SELECT);
        vm.A36_1_Condition(displaySetting.emergencyNumber2.contactUsageSetting === CONTACT_USAGE.DO_NOT_USE);
        vm.A36_2_Condition(displaySetting.emergencyNumber2.contactUsageSetting === CONTACT_USAGE.USE);
        vm.A36_3_Condition(displaySetting.emergencyNumber2.contactUsageSetting === CONTACT_USAGE.INDIVIDUAL_SELECT);
        vm.A37_1_Condition(displaySetting.dialInNumber.contactUsageSetting === CONTACT_USAGE.DO_NOT_USE);
        vm.A37_2_Condition(displaySetting.dialInNumber.contactUsageSetting === CONTACT_USAGE.USE);
        vm.A37_3_Condition(displaySetting.dialInNumber.contactUsageSetting === CONTACT_USAGE.INDIVIDUAL_SELECT);
        vm.A38_1_Condition(displaySetting.extensionNumber.contactUsageSetting === CONTACT_USAGE.DO_NOT_USE);
        vm.A38_2_Condition(displaySetting.extensionNumber.contactUsageSetting === CONTACT_USAGE.USE);
        vm.A38_3_Condition(displaySetting.extensionNumber.contactUsageSetting === CONTACT_USAGE.INDIVIDUAL_SELECT);
        vm.A39_1_Condition(displaySetting.companyEmailAddress.contactUsageSetting === CONTACT_USAGE.DO_NOT_USE);
        vm.A39_2_Condition(displaySetting.companyEmailAddress.contactUsageSetting === CONTACT_USAGE.USE);
        vm.A39_3_Condition(displaySetting.companyEmailAddress.contactUsageSetting === CONTACT_USAGE.INDIVIDUAL_SELECT);
        vm.A40_1_Condition(displaySetting.personalEmailAddress.contactUsageSetting === CONTACT_USAGE.DO_NOT_USE);
        vm.A40_2_Condition(displaySetting.personalEmailAddress.contactUsageSetting === CONTACT_USAGE.USE);
        vm.A40_3_Condition(displaySetting.personalEmailAddress.contactUsageSetting === CONTACT_USAGE.INDIVIDUAL_SELECT);
        vm.A41_1_Condition(displaySetting.companyMobileEmailAddress.contactUsageSetting === CONTACT_USAGE.DO_NOT_USE);
        vm.A41_2_Condition(displaySetting.companyMobileEmailAddress.contactUsageSetting === CONTACT_USAGE.USE);
        vm.A41_3_Condition(displaySetting.companyMobileEmailAddress.contactUsageSetting === CONTACT_USAGE.INDIVIDUAL_SELECT);
        vm.A42_1_Condition(displaySetting.personalMobileEmailAddress.contactUsageSetting === CONTACT_USAGE.DO_NOT_USE);
        vm.A42_2_Condition(displaySetting.personalMobileEmailAddress.contactUsageSetting === CONTACT_USAGE.USE);
        vm.A42_3_Condition(displaySetting.personalMobileEmailAddress.contactUsageSetting === CONTACT_USAGE.INDIVIDUAL_SELECT);


        //Make tab visible
        _.map(vm.tabs(), (tab: any) => {
          if (tab.id === 'tab-1') {
            tab.enable(isUseOfProfile);
            tab.visible(isUseOfProfile);
          } else if (tab.id === 'tab-2') {
            tab.enable(isUseOfPassword);
            tab.visible(isUseOfPassword);
          } else if (tab.id === 'tab-3') {
            tab.enable(isUseOfNotice);
            tab.visible(isUseOfNotice);
          } else if (tab.id === 'tab-4') {
            tab.enable(isUseOfLanguage);
            tab.visible(isUseOfLanguage);
          }
        });
      })
        .fail(error => {
          vm.$blockui('clear')
          if (error.messageId === "Msg_1775") {
            vm.$dialog.error(error).then(() => {
              vm.openDialogCmm049();
            });
          } else {
            vm.$window.modal("/view/cmm/008/a/index.xhtml");
          }
        })
        .always(() => {
          vm.$blockui('clear');
        });
    }

    generateTitleTab(rsCode: string, icon: string): string {
      return (
        `<span>
        <img class="tab-icon" src="./resource/`+ icon + `.png" />
        <span>`+ rsCode + `</span>
        </span>`
      )
    }

    public openDialogE() {
      const vm = this;
      vm.$window.modal("/view/cmm/048/e/index.xhtml", vm.A4_1_Value()).then((fileId: string) => {
        if (fileId) {
          vm.A4_1_Value(fileId);
          $("#avatar-change").html("").ready(() => {
            $("#avatar-change").append(
              $("<img/>")
                .attr("alt", 'Avatar')
                .attr("class", 'avatar')
                .attr("id", 'A4_1')
                .attr("src", (nts.uk.request as any).liveView(vm.A4_1_Value()))
            );
          });
        }
      });
    }

    public openDialogCmm049() {
      const vm = this;
      vm.$window.modal("/view/cmm/049/a/index.xhtml").then(() => {
        $("#avatar-change").html("");
        vm.listAnniversary([]);
        vm.ListOtherContact([]);
        vm.init();
      });
    }

    public addNewAnniversary() {
      const vm = this;
      vm.listAnniversary.push(new AnniversaryNotificationViewModel("", "", "", 0));
    }

    public removeAnniversary(anniversary: AnniversaryNotificationViewModel) {
      const vm = this;
      vm.listAnniversary.remove(anniversary);
    }
    private getUserCommand(): UserCommand {
      const vm = this;
      return new UserCommand({
        currentPassword: vm.B3_2_Value(),
        newPassword: vm.B4_2_Value(),
        confirmPassword: vm.B5_2_Value(),
        language: vm.D2_2_Value()
      });
    }

    private getUserAvatarCommand(): UserAvatarCommand {
      const vm = this;
      return new UserAvatarCommand({
        personalId: vm.personId,
        fileId: vm.A4_1_Value()
      });
    }

    private getAnniversaryNoticeCommandList(): AnniversaryNoticeCommand[] {
      const vm = this;
      const list: AnniversaryNoticeCommand[] = [];
      _.map(vm.listAnniversary(), (item: AnniversaryNotificationViewModel) => {
        let anniversary = item.anniversaryDay();
        //handle monthDay
        if (Number(anniversary) < 1000) {
          anniversary = '0' + anniversary;
        }
        if (anniversary.length > 2) {
          list.push(new AnniversaryNoticeCommand({
            personalId: vm.personId,
            noticeDay: item.anniversaryNoticeBefore(),
            anniversary: anniversary,
            anniversaryTitle: item.anniversaryName(),
            notificationMessage: item.anniversaryRemark(),
          }));
        }
      })
      return list;
    }

    private getPersonalContactCommand(): PersonalContactCommand {
      const vm = this;
      const list: OtherContactCommand[] = [];
      _.map(vm.ListOtherContact(), (contact: OtherContactViewModel) => {
        list.push(new OtherContactCommand({
          otherContactNo: contact.contactNo,
          isDisplay: contact.isContactDisplayOnOther(),
          address: contact.contactAdress()
        }));
      })
      return new PersonalContactCommand({
        personalId: vm.personId,
        mailAddress: vm.A7_19_Value(),
        isMailAddressDisplay: vm.A9_17_Value(),
        mobileEmailAddress: vm.A7_21_Value(),
        isMobileEmailAddressDisplay: vm.A9_19_Value(),
        phoneNumber: vm.A7_5_Value(),
        isPhoneNumberDisplay: vm.A9_3_Value(),
        emergencyContact1: new EmergencyContactCommand({
          phoneNumber: vm.A7_7_Value()
        }),
        isEmergencyContact1Display: vm.A9_5_Value(),
        emergencyContact2: new EmergencyContactCommand({
          phoneNumber: vm.A7_9_Value()
        }),
        isEmergencyContact2Display: vm.A9_7_Value(),
        otherContacts: list,
      });
    }

    private getEmployeeContactCommand(): EmployeeContactCommand {
      const vm = this;
      return new EmployeeContactCommand({
        employeeId: vm.employeeId,
        mailAddress: vm.A7_15_Value(),
        isMailAddressDisplay: vm.A9_13_Value(),
        seatDialIn: vm.A7_11_Value(),
        isSeatDialInDisplay: vm.A9_9_Value(),
        seatExtensionNumber: vm.A7_13_Value(),
        isSeatExtensionNumberDisplay: vm.A9_11_Value(),
        mobileMailAddress: vm.A7_17_Value(),
        isMobileMailAddressDisplay: vm.A9_15_Value(),
        cellPhoneNumber: vm.A7_3_Value(),
        isCellPhoneNumberDisplay: vm.A9_1_Value()
      });
    }

    public save() {
      const vm = this;
      const userChange = vm.getUserCommand();
      const avatar = vm.getUserAvatarCommand();
      const listAnniversary = vm.getAnniversaryNoticeCommandList();
      const personalContact = vm.getPersonalContactCommand();
      const employeeContact = vm.getEmployeeContactCommand();

      const command = new AccountInformationCommand({
        userChange: userChange,
        avatar: avatar,
        anniversaryNotices: listAnniversary,
        personalContact: personalContact,
        employeeContact: employeeContact
      });

      console.log(command);
      vm.$blockui('grayout');
      vm.$ajax(API.update, command)
        .then(() => {
          vm.$blockui('clear');
          vm.$dialog.info({ messageId: 'Msg_15' });
        })
        .fail(error => {
          vm.$blockui('clear')
          vm.$dialog.error(error);
        })
        .always(() => vm.$blockui('clear'));
    }
  }
  enum LANGUAGE {
    JAPANESE = 0,
    ENGLISH = 1,
    OTHER = 2
  }

  enum REMIND_DATE {
    BEFORE_ZERO_DAY = 0,
    BEFORE_ONE_DAY = 1,
    BEFORE_TWO_DAY = 2,
    BEFORE_THREE_DAY = 3,
    BEFORE_FOUR_DAY = 4,
    BEFORE_FIVE_DAY = 5,
    BEFORE_SIX_DAY = 6,
    BEFORE_SEVEN_DAY = 7,
  }

  enum CONTACT_USAGE {
    // 利用しない
    DO_NOT_USE = 0,

    // 利用する
    USE = 1,

    // 個人選択可
    INDIVIDUAL_SELECT = 2
  }

  enum IS_USE {
    NOT_USE = 0,
    USE = 1
  }

  enum DELETE_STATUS {
    /** 0 - 削除していない **/
    NOTDELETED = 0,
    /** 1 - 一時削除 **/
    TEMPDELETED = 1,
    /** 2 - 完全削除 **/
    PURGEDELETED = 2
  }


  enum BLOOD_TYPE {
    /* O RH+ */
    ORhPlus = 3,
    /* O RH- */
    ORhSub = 7,
    /* A RH+ */
    ARhPlus = 1,
    /* A RH- */
    ARhSub = 5,
    /* B RH+ */
    BRhPlus = 2,
    /* B RH- */
    BRhSub = 6,
    /* AB RH+ */
    ABRhPlus = 4,
    /* AB RH- */
    ABRhSub = 8
  }

  enum GENDER {
    /* 男 */
    Male = 1,

    /* 女 */
    Female = 2
  }


  enum DISABLED_SEGMENT {
    FALSE = 0, // なし
    TRUE = 1 // あり
  }

  enum PASSWORD_STATUS {
    /** 正式 */
    Official = 0,
    /** 初期パスワード */
    InitPasswor = 1,
    /** リセット */
    Reset = 2
  }

  enum EMAIL_CLASSIFICATION {
    /**
   * 会社メールアドレス
   */
    COMPANY_EMAIL_ADDRESS = 0,

    /**
     * 会社携帯メールアドレス
     */
    COMPANY_MOBILE_EMAIL_ADDRESS = 1,

    /**
     * 個人メールアドレス
     */
    PERSONAL_EMAIL_ADDRESS = 2,

    /**
     * 個人携帯メールアドレス
     */
    PERSONAL_MOBILE_EMAIL_ADDRESS = 3
  }
  class ItemCbx {
    constructor(
      public code: number,
      public name: string
    ) { }
  }

  class AnniversaryNotificationViewModel {
    anniversaryDay!: KnockoutObservable<string>;
    anniversaryName!: KnockoutObservable<string>;
    anniversaryRemark!: KnockoutObservable<string>;
    anniversaryNoticeBefore!: KnockoutObservable<number>;

    constructor(
      anniversaryDay: string,
      anniversaryName: string,
      anniversaryRemark: string,
      anniversaryNoticeBefore: number
    ) {
      this.anniversaryDay = ko.observable(anniversaryDay);
      this.anniversaryName = ko.observable(anniversaryName);
      this.anniversaryRemark = ko.observable(anniversaryRemark);
      this.anniversaryNoticeBefore = ko.observable(anniversaryNoticeBefore)
    }
  }

  class OtherContactViewModel {
    contactNo: number;
    contactName!: string;
    contactAdress!: KnockoutObservable<string>;
    contactUsage!: boolean;
    isContactDisplay!: boolean;
    isContactDisplayOnOther!: KnockoutObservable<boolean>;
    constructor(
      contactNo: number,
      contactName: string,
      contactAdress: string,
      contactUsage: boolean,
      isContactDisplay: boolean,
      isContactDisplayOnOther: boolean
    ) {
      this.contactNo = contactNo;
      this.contactName = contactName;
      this.contactAdress = ko.observable(contactAdress);
      this.contactUsage = contactUsage;
      this.isContactDisplay = isContactDisplay;
      this.isContactDisplayOnOther = ko.observable(isContactDisplayOnOther);
    }
  }

  /**
   * Command アカウント情報を登録する
   */
  class AccountInformationCommand {

    /**
     * ユーザを変更する
     */
    userChange: UserCommand;

    /**
     * 個人の顔写真を登録する
     */
    avatar: UserAvatarCommand;

    /**
     * 記念日を削除する + 個人の記念日情報を登録する
     */
    anniversaryNotices: AnniversaryNoticeCommand[];

    /**
     * 個人連絡先を登録する
     */
    personalContact: PersonalContactCommand;

    /**
     * 社員連絡先を登録する
     */
    employeeContact: EmployeeContactCommand;

    constructor(init?: Partial<AccountInformationCommand>) {
      $.extend(this, init);
    }
  }

  /**
   * Command dto ユーザ
   */
  class UserCommand {

    /**
     * 現行のパスワード
     */
    currentPassword: string;

    /**
     * 新しいパスワード
     */
    newPassword: string;

    /**
     * 新しいパスワード（確認）
     */
    confirmPassword: string;

    /**
     * 言語
     */
    language: number;

    constructor(init?: Partial<UserCommand>) {
      $.extend(this, init);
    }
  }

  /**
   * Command dto 個人の顔写真
   */
  class UserAvatarCommand {

    /**
     * 個人ID
     */
    personalId: string;

    /**
     * 顔写真ファイルID
     */
    fileId: string;

    constructor(init?: Partial<UserAvatarCommand>) {
      $.extend(this, init);
    }
  }

  /**
   * Command dto 個人の記念日情報
   */
  class AnniversaryNoticeCommand {
    /**
     * 個人ID
     */
    personalId: string;

    /**
     * 日数前の通知
     */
    noticeDay: number;

    /**
     * 最後見た記念日
     */
    seenDate: string;

    /**
     * 記念日
     */
    anniversary: string;

    /**
     * 記念日のタイトル
     */
    anniversaryTitle: string;

    /**
     * 記念日の内容
     */
    notificationMessage: string;

    constructor(init?: Partial<AnniversaryNoticeCommand>) {
      $.extend(this, init);
    }
  }

  /**
   * Command dto 個人連絡先
   */
  class OtherContactCommand {
    /**
     * NO
     */
    otherContactNo: number;

    /**
     * 在席照会に表示するか
     */
    isDisplay: boolean;

    /**
     * 連絡先のアドレス
     */
    address: string;

    constructor(init?: Partial<OtherContactCommand>) {
      $.extend(this, init);
    }
  }
  /**
   * Command dto 社員連絡先
   */
  class EmployeeContactCommand {
    /**
     * 社員ID
     */
    employeeId: string;

    /**
     * メールアドレス
     */
    mailAddress: string;

    /**
     * メールアドレスが在席照会に表示するか
     */
    isMailAddressDisplay: boolean;

    /**
     * 座席ダイヤルイン
     */
    seatDialIn: string;

    /**
     * 座席ダイヤルインが在席照会に表示するか
     */
    isSeatDialInDisplay: boolean;

    /**
     * 座席内線番号
     */
    seatExtensionNumber: string;

    /**
     * 座席内線番号が在席照会に表示するか
     */
    isSeatExtensionNumberDisplay: boolean;

    /**
     * 携帯メールアドレス
     */
    mobileMailAddress: string;

    /**
     * 携帯メールアドレスが在席照会に表示するか
     */
    isMobileMailAddressDisplay: boolean;

    /**
     * 携帯電話番号
     */
    cellPhoneNumber: string;

    /**
     * 携帯電話番号が在席照会に表示するか
     */
    isCellPhoneNumberDisplay: boolean;

    constructor(init?: Partial<EmployeeContactCommand>) {
      $.extend(this, init);
    }
  }

  /**
   * Command dto 個人連絡先
   */
  class PersonalContactCommand {

    /**
     * 個人ID
     */
    personalId: string;

    /**
     * メールアドレス
     */
    mailAddress: string;

    /**
     * メールアドレスが在席照会に表示するか
     */
    isMailAddressDisplay: boolean;

    /**
     * 携帯メールアドレス
     */
    mobileEmailAddress: string;

    /**
     * 携帯メールアドレスが在席照会に表示するか
     */
    isMobileEmailAddressDisplay: boolean;

    /**
     * 携帯電話番号
     */
    phoneNumber: string;

    /**
     * 携帯電話番号が在席照会に表示するか
     */
    isPhoneNumberDisplay: boolean;

    /**
     * 緊急連絡先１
     */
    emergencyContact1: EmergencyContactCommand;

    /**
     * 緊急連絡先１が在席照会に表示するか
     */
    isEmergencyContact1Display: boolean;

    /**
     * 緊急連絡先２
     */
    emergencyContact2: EmergencyContactCommand;

    /**
     * 緊急連絡先２が在席照会に表示するか
     */
    isEmergencyContact2Display: boolean;

    /**
     * 他の連絡先
     */
    otherContacts: OtherContactCommand[];

    constructor(init?: Partial<PersonalContactCommand>) {
      $.extend(this, init);
    }
  }

  /**
 * Command dto 個人連絡先
 */
  class EmergencyContactCommand {
    /**
     * メモ
     */
    remark: string;

    /**
     * 連絡先名
     */
    contactName: string;

    /**
     * 電話番号
     */
    phoneNumber: string;

    constructor(init?: Partial<EmergencyContactCommand>) {
      $.extend(this, init);
    }
  }

  /**
   * Dto ユーザ情報の表示
   */
  interface UserInformationDto {
    /**
  * パスワードポリシー
  */
    passwordPolicy: PasswordPolicyDto;

    /**
     * ログイン者が担当者か
     */
    isInCharge: boolean;

    /**
     * ユーザー情報の使用方法
     */
    settingInformation: UserInfoUseMethodDto;

    /**
     * 入社日
     */
    hireDate: string;

    /**
     * ユーザ
     */
    user: UserDto;

    /**
     * 個人
     */
    person: PersonDto;

    /**
     * 個人連絡先
     */
    personalContact: PersonalContactDto;

    /**
     * 社員データ管理情報
     */
    employeeDataMngInfo: EmployeeDataMngInfoDto;

    /**
     * 社員連絡先
     */
    employeeContact: EmployeeContactDto;

    /**
     * パスワード変更ログ
     */
    passwordChangeLog: PasswordChangeLogDto;

    /**
     * 個人の記念日情報
     */
    anniversaryNotices: AnniversaryNoticeDto[];

    /**
     * 個人の顔写真
     */
    userAvatar: UserAvatarDto;

    /**
     * 職位名称
     */
    positionName: string;

    /**
     * 職場表示名
     */
    wkpDisplayName: string;
  }

  /**
   * Dto パスワードポリシー
   */
  interface PasswordPolicyDto {
    /**
     * 契約コード
     */
    contractCode: string;

    /**
     * パスワード変更通知
     */
    notificationPasswordChange: number;

    /**
     * ログイン時にパスワードに従っていない場合変更させる
     */
    loginCheck: boolean;

    /**
     * 最初のパスワード変更
     */
    initialPasswordChange: boolean;

    /**
     * 利用する
     */
    isUse: boolean;

    /**
     * 履歴回数
     */
    historyCount: number;

    /**
     * 最低桁数
     */
    lowestDigits: number;

    /**
     * 有効期間
     */
    validityPeriod: number;

    /**
     * 複雑さ
     */
    numberOfDigits: number;

    /**
     *
     */
    symbolCharacters: number;

    /**
     *
     */
    alphabetDigit: number;
  }

  /**
   * Dto ユーザー情報の使用方法
   */
  interface UserInfoUseMethodDto {
    /**
    * お知らせの利用
    */
    useOfNotice: IS_USE;

    /**
     * パスワードの利用
     */
    useOfPassword: IS_USE;

    /**
     * プロフィールの利用
     */
    useOfProfile: IS_USE;

    /**
     * 言語の利用
     */
    useOfLanguage: IS_USE;

    /**
     * 会社ID
     */
    companyId: string;

    /**
     * メール送信先機能
     */
    emailDestinationFunctions: EmailDestinationFunctionDto[];

    /**
     * 連絡先情報の設定
     */
    settingContactInformation: SettingContactInformationDto;
  }

  /**
   * Dto メール送信先機能
   */
  interface EmailDestinationFunctionDto {
    /**
     * メール分類
     */
    emailClassification: EMAIL_CLASSIFICATION;

    /**
     * 機能ID
     */
    functionIds: number[];
  }



  /**
 * Dto 連絡先情報の設定
 */
  interface SettingContactInformationDto {
    /**
   * ダイヤルイン番号
   */
    dialInNumber: ContactSettingDto;

    /**
     * 会社メールアドレス
     */
    companyEmailAddress: ContactSettingDto;

    /**
     * 会社携帯メールアドレス
     */
    companyMobileEmailAddress: ContactSettingDto;

    /**
     * 個人メールアドレス
     */
    personalEmailAddress: ContactSettingDto;

    /**
     * 個人携帯メールアドレス
     */
    personalMobileEmailAddress: ContactSettingDto;

    /**
     * 内線番号
     */
    extensionNumber: ContactSettingDto;

    /**
     * 携帯電話（会社用）
     */
    companyMobilePhone: ContactSettingDto;

    /**
     * 携帯電話（個人用）
     */
    personalMobilePhone: ContactSettingDto;

    /**
     * 緊急電話番号1
     */
    emergencyNumber1: ContactSettingDto;

    /**
     * 緊急電話番号2
     */
    emergencyNumber2: ContactSettingDto;

    /**
     * 他の連絡先
     */
    otherContacts: OtherContactDto[];
  }

  /**
 * Dto 連絡先の設定
 */
  interface ContactSettingDto {
    /**
   * 連絡先利用設定
   */
    contactUsageSetting: CONTACT_USAGE;

    /**
     * 更新可能
     */
    updatable: IS_USE;
  }

  /**
   * Dto 他の連絡先
   */
  interface OtherContactDto {
    /**
   * NO
   */
    no: number;

    /**
     * 連絡先利用設定
     */
    contactUsageSetting: CONTACT_USAGE;

    /**
     * 連絡先名
     */
    contactName: string;
  }

  /**
 * Dto ユーザ
 */
  interface UserDto {
    /**
    * ユーザID
    */
    userId: string;

    /**
     * デフォルトユーザ
     */
    defaultUser: boolean;

    /**
     * パスワード
     */
    password: string;

    /**
     * ログインID
     */
    loginID: string;

    /**
     * 契約コード
     */
    contractCode: string;

    /**
     * 有効期限
     */
    expirationDate: string;

    /**
     * 特別利用者
     */
    specialUser: DISABLED_SEGMENT;

    /**
     * 複数会社を兼務する
     */
    multiCompanyConcurrent: DISABLED_SEGMENT;

    /**
     * メールアドレス
     */
    mailAddress: string;

    /**
     * ユーザ名
     */
    userName: string;

    /**
     * 紐付け先個人ID
     */
    associatedPersonID: string;

    /**
     * パスワード状態
     */
    passStatus: PASSWORD_STATUS;

    /**
     * 言語
     */
    language: LANGUAGE;
  }


  /**
   * Dto 個人
   */
  interface PersonDto {
    /**
     * 個人ID
     */
    personId: string;

    /**
     * 生年月日
     */

    birthDate: string;

    /**
     * 血液型
     */
    bloodType: BLOOD_TYPE;

    /**
     * 性別
     */
    gender: GENDER;

    /**
     * 個人名グループ
     */
    personNameGroup: PersonNameGroupDto;
  }

  /**
 * Dto 個人名グループ
 */
  interface PersonNameGroupDto {
    /**
    * ビジネスネーム
    */
    businessName: string;

    /**
     * ビジネスネームカナ
     */
    businessNameKana: string;

    /**
     * ビジネスネームその他
     */
    businessOtherName: string;

    /**
     * ビジネスネーム英語
     */
    businessEnglishName: string;

    /**
     * 個人名
     */
    personName: FullNameSetDto;

    /**
     * 個人名多言語
     */
    PersonalNameMultilingual: FullNameSetDto;

    /**
     * 個人名ローマ字
     */
    personRomanji: FullNameSetDto;

    /**
     * 個人届出名称
     */
    todokedeFullName: FullNameSetDto;

    /**
     * 個人旧氏名
     */
    oldName: FullNameSetDto;
  }

  interface FullNameSetDto {
    /**
   * 氏名
   */
    fullName: string;

    /**
     * 氏名カナ
     */
    fullNameKana: string;
  }

  /**
   * Dto 個人連絡先
   */
  interface PersonalContactDto {
    /**
   * 個人ID
   */
    personalId: string;

    /**
     * メールアドレス
     */
    mailAddress: string;

    /**
     * メールアドレスが在席照会に表示するか
     */
    isMailAddressDisplay: boolean;

    /**
     * 携帯メールアドレス
     */
    mobileEmailAddress: string;

    /**
     * 携帯メールアドレスが在席照会に表示するか
     */
    isMobileEmailAddressDisplay: boolean;

    /**
     * 携帯電話番号
     */
    phoneNumber: string;

    /**
     * 携帯電話番号が在席照会に表示するか
     */
    isPhoneNumberDisplay: boolean;

    /**
     * 緊急連絡先１
     */
    emergencyContact1: EmergencyContactDto;

    /**
     * 緊急連絡先１が在席照会に表示するか
     */
    isEmergencyContact1Display: boolean;

    /**
     * 緊急連絡先２
     */
    emergencyContact2: EmergencyContactDto;

    /**
     * 緊急連絡先２が在席照会に表示するか
     */
    isEmergencyContact2Display: boolean;

    /**
     * 他の連絡先
     */
    otherContacts: OtherContactDtoPs[];
  }

  /**
   * Dto 緊急連絡先
   */
  interface EmergencyContactDto {
    /**
      * メモ
      */
    remark: string;

    /**
     * 連絡先名
     */
    contactName: string;

    /**
     * 電話番号
     */
    phoneNumber: string;
  }

  /**
   * Dto 他の連絡先
   */
  interface OtherContactDtoPs {
    /**
    * NO
    */
    otherContactNo: number;

    /**
     * 在席照会に表示するか
     */
    isDisplay: boolean;

    /**
     * 連絡先のアドレス
     */
    address: string;
  }

  /**
 * Dto 社員データ管理情報
 */
  interface EmployeeDataMngInfoDto {
    /**
    * 会社ID
    */
    companyId: string;

    /**
     * 個人ID
     */
    personId: string;

    /**
     * 社員ID
     */
    employeeId: string;

    /**
     * 社員コード
     */
    employeeCode: string;

    /**
     * 削除状況
     */
    deletedStatus: DELETE_STATUS;

    /**
     * 一時削除日時
     */
    deleteDateTemporary: string;

    /**
     * 削除理由
     */
    removeReason: string;

    /**
     * 外部コード
     */
    externalCode: string;
  }

  /**
   * Dto 社員連絡先
   */
  interface EmployeeContactDto {
    /**
   * 社員ID
   */
    employeeId: string;

    /**
     * メールアドレス
     */
    mailAddress: string;

    /**
     * メールアドレスが在席照会に表示するか
     */
    isMailAddressDisplay: boolean;

    /**
     * 座席ダイヤルイン
     */
    seatDialIn: string;

    /**
     * 座席ダイヤルインが在席照会に表示するか
     */
    isSeatDialInDisplay: boolean;

    /**
     * 座席内線番号
     */
    seatExtensionNumber: string;

    /**
     * 座席内線番号が在席照会に表示するか
     */
    isSeatExtensionNumberDisplay: boolean;

    /**
     * 携帯メールアドレス
     */
    mobileMailAddress: string;

    /**
     * 携帯メールアドレスが在席照会に表示するか
     */
    isMobileMailAddressDisplay: boolean;

    /**
     * 携帯電話番号
     */
    cellPhoneNumber: string;

    /**
     * 携帯電話番号が在席照会に表示するか
     */
    isCellPhoneNumberDisplay: boolean;
  }

  /**
   * Dto パスワード変更ログ
   */
  interface PasswordChangeLogDto {
    /**
   * ログID
   */
    logId: string;

    /**
     * ユーザID
     */
    userId: string;

    /**
     * 変更日時
     */
    modifiedDate: string;

    /**
     * パスワード
     */
    password: string;
  }

  /**
   * Dto 個人の記念日情報
   */
  interface AnniversaryNoticeDto {
    /**
    * 個人ID
    */
    personalId: string;

    /**
     * 日数前の通知
     */
    noticeDay: REMIND_DATE;

    /**
     * 最後見た記念日
     */
    seenDate: string;

    /**
     * 記念日
     */
    anniversary: string;

    /**
     * 記念日のタイトル
     */
    anniversaryTitle: string;

    /**
     * 記念日の内容
     */
    notificationMessage: string;
  }

  /**
   * Dto 個人の顔写真
   */
  interface UserAvatarDto {
    /**
   * 個人ID
   */
    personalId: string;

    /**
     * 顔写真ファイルID
     */
    fileId: string;
  }
}
