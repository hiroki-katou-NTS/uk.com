module nts.uk.com.view.cmm048.a {

  const API = {
    find: "query/cmm048userinformation/find",
  };
  @bean()
  export class ViewModel extends ko.ViewModel {

    //general
    tabs: KnockoutObservableArray<any> = ko.observableArray([
      { id: 'tab-1', title: this.generateTitleTab(this.$i18n('CMM048_92'), 'setting'), content: '.tab-content-1', enable: ko.observable(true), visible: ko.observable(true) },
      { id: 'tab-2', title: this.generateTitleTab(this.$i18n('CMM048_93'), 'security'), content: '.tab-content-2', enable: ko.observable(true), visible: ko.observable(true) },
      { id: 'tab-3', title: this.generateTitleTab(this.$i18n('CMM048_94'), 'notice'), content: '.tab-content-3', enable: ko.observable(true), visible: ko.observable(true) },
      { id: 'tab-4', title: this.generateTitleTab(this.$i18n('CMM048_95'), 'language'), content: '.tab-content-4', enable: ko.observable(true), visible: ko.observable(true) }
    ]);
    selectedTab: KnockoutObservable<string> = ko.observable('tab-1');

    //A
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
    //B
    B3_2_Value: KnockoutObservable<string> = ko.observable('');
    B4_2_Value: KnockoutObservable<string> = ko.observable('');
    B5_2_Value: KnockoutObservable<string> = ko.observable('');

    //C
    C2_2_Value: KnockoutObservable<string> = ko.observable('');
    C2_3_Value: KnockoutObservable<string> = ko.observable('');
    C2_4_Value: KnockoutObservable<string> = ko.observable('');
    C2_6_Value: KnockoutObservable<string> = ko.observable('');
    C2_6_Options: KnockoutObservableArray<any> = ko.observableArray([
      new ItemCbx(REMIND_DATE.BEFORE_ZERO_DAY, "当日"),
      new ItemCbx(REMIND_DATE.BEFORE_ONE_DAY, "１日前"),
      new ItemCbx(REMIND_DATE.BEFORE_TWO_DAY, "２日前"),
      new ItemCbx(REMIND_DATE.BEFORE_THREE_DAY, "３日前"),
      new ItemCbx(REMIND_DATE.BEFORE_FOUR_DAY, "４日前"),
      new ItemCbx(REMIND_DATE.BEFORE_FIVE_DAY, "５日前"),
      new ItemCbx(REMIND_DATE.BEFORE_SIX_DAY, "６日前"),
      new ItemCbx(REMIND_DATE.BEFORE_SEVEN_DAY, "７日前"),
    ]);
    listAnniversary: KnockoutObservableArray<AnniversaryNotification> = ko.observableArray([
      new AnniversaryNotification("", "", "", 0)
    ]);

    //D
    D2_2_Value: KnockoutObservable<string> = ko.observable('');
    D2_2_Options: KnockoutObservableArray<any> = ko.observableArray([
      new ItemCbx(LANGUAGE.JAPANESE, "日本語"),
      new ItemCbx(LANGUAGE.ENGLISH, "英語"),
      new ItemCbx(LANGUAGE.OTHER, "その他"),
    ]);

    mounted() {
      const vm = this;
      vm.$blockui('grayout')
      vm.listAnniversary.push(new AnniversaryNotification("", "", "", 0));
      vm.$ajax(API.find).then(data => console.log(data))
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
      vm.listAnniversary.push(new AnniversaryNotification("", "", "", 0));
    }

    public removeAnniversary(anniversary: AnniversaryNotification) {
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

  class ItemCbx {
    constructor(
      public code: number,
      public name: string
    ) { }
  }

  class AnniversaryNotification {
    public anniversaryDay!: KnockoutObservable<string>;
    public anniversaryName!: KnockoutObservable<string>;
    public anniversaryRemark!: KnockoutObservable<string>;
    public anniversaryNoticeBefore!: KnockoutObservable<number>;

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
    useOfNotice: number;

    /**
     * パスワードの利用
     */
    useOfPassword: number;

    /**
     * プロフィールの利用
     */
    useOfProfile: number;

    /**
     * 言語の利用
     */
    useOfLanguage: number;

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
    emailClassification: number;

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
    contactUsageSetting: number;

    /**
     * 更新可能
     */
    updatable: number;
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
    contactUsageSetting: number;

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
    specialUser: number;

    /**
     * 複数会社を兼務する
     */
    multiCompanyConcurrent: number;

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
    passStatus: number;

    /**
     * 言語
     */
    language: number;
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
    bloodType: number;

    /**
     * 性別
     */
    gender: number;

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
    deletedStatus: number;

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
