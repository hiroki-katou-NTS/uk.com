module nts.uk.com.view.cmm048.a {

  const API = {
    find: "query/cmm048userinformation/find",
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
    A9_1_Value: KnockoutObservable<boolean> = ko.observable(false);
    A9_3_Value: KnockoutObservable<boolean> = ko.observable(false);
    A9_5_Value: KnockoutObservable<boolean> = ko.observable(false);
    A9_7_Value: KnockoutObservable<boolean> = ko.observable(false);
    A9_9_Value: KnockoutObservable<boolean> = ko.observable(false);
    A9_11_Value: KnockoutObservable<boolean> = ko.observable(false);
    A9_13_Value: KnockoutObservable<boolean> = ko.observable(false);
    A9_15_Value: KnockoutObservable<boolean> = ko.observable(false);
    A9_17_Value: KnockoutObservable<boolean> = ko.observable(false);
    A9_19_Value: KnockoutObservable<boolean> = ko.observable(false);
    ListOtherContact: KnockoutObservable<OtherContactModel[]> = ko.observable([]);

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
    C2_6_Options: KnockoutObservable<ItemCbx[]> = ko.observableArray([
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
    D2_2_Options: KnockoutObservable<ItemCbx[]> = ko.observableArray([
      new ItemCbx(LANGUAGE.JAPANESE, "日本語"),
      new ItemCbx(LANGUAGE.ENGLISH, "英語"),
      new ItemCbx(LANGUAGE.OTHER, "その他"),
    ]);

    //condition to show off
    isInCharge: KnockoutObservable<boolean> = ko.observable(false);

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

    mounted() {
      const vm = this;
      vm.$blockui('grayout')
      vm.$ajax(API.find).then((data: UserInformationDto) => {

        //set data for tab A
        vm.A4_1_Value(data.userAvatar.fileId);
        vm.A5_2_Value(data.person.personNameGroup.businessName);
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
        //TODO
        if (data.personalContact.otherContacts.length > 0) {
          _.map(data.personalContact.otherContacts, (contact: OtherContactDtoPs) => {
            const otherContactSet: OtherContactDto = _.filter(data.settingInformation.settingContactInformation.otherContacts,
              (contactSetting: OtherContactDto) => contactSetting.no === contact.otherContactNo)[0];
            //vm.ListOtherContact().push(new otherContact(contact.otherContactNo, otherContactSet ? otherContactSet.contactName : "", contact.address, contact.isDisplay))
          });
        }
        vm.A9_1_Value(data.employeeContact.isCellPhoneNumberDisplay);
        vm.A9_3_Value(data.personalContact.isPhoneNumberDisplay);
        vm.A9_5_Value(data.personalContact.isEmergencyContact1Display);
        vm.A9_7_Value(data.personalContact.isEmergencyContact2Display);
        vm.A9_9_Value(data.employeeContact.isSeatDialInDisplay);
        vm.A9_11_Value(data.employeeContact.isSeatExtensionNumberDisplay);
        vm.A9_13_Value(data.employeeContact.isMailAddressDisplay);
        vm.A9_15_Value(data.employeeContact.isMobileMailAddressDisplay);
        vm.A9_17_Value(data.personalContact.isMailAddressDisplay);
        vm.A9_19_Value(data.personalContact.isMobileEmailAddressDisplay);

        //set data for tab B
        if (data.passwordChangeLog) {
          const today = moment().utc();
          const changePassDay = moment(data.passwordChangeLog.modifiedDate).utc();
          const lastChangePass = moment.duration(today.diff(changePassDay)).humanize();
          if (data.passwordPolicy.validityPeriod) {
            const cmm4897: string = vm.$i18n('CMM048_97', [lastChangePass]);
            vm.B2_2_Value(cmm4897);
          } else {
            const timeLeft = data.passwordPolicy.validityPeriod - moment.duration(today.diff(changePassDay)).asDays();
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
        if (data.anniversaryNotices) {
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

        // vm.A6_1_A6_2_Display(data.settingInformation.);
        
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
          vm.$dialog.error(error);
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
      vm.$window.modal("/view/cmm/048/e/index.xhtml").then(() => {
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

    public save() {
      const vm = this;
      console.log(1)

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

  enum CONTACT_USAGE_SET {
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

  class OtherContactModel {
    contactNo: number;
    contactName: string;
    contactAdress: string;
    contactDisplay: boolean;

    constructor(init?: Partial<OtherContactModel>) {
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
    contactUsageSetting: CONTACT_USAGE_SET;

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
    contactUsageSetting: CONTACT_USAGE_SET;

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
