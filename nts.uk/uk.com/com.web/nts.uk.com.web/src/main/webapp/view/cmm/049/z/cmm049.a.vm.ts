module nts.uk.com.view.cmm049.z {
  const API = {
    findByCid: "query/cmm049userinformationsetting/get"
  };

  @bean()
  export class ScreenModel extends ko.ViewModel {
    $grid!: JQuery;

    public tabs: KnockoutObservableArray<any>;
    public selectedTab: KnockoutObservable<string>;

    public profileCheckList: KnockoutObservableArray<CheckboxModel> = ko.observableArray([]);
    public profileSelectedId: KnockoutObservable<number> = ko.observable(1);

    public passwordCheckList: KnockoutObservableArray<CheckboxModel> = ko.observableArray([]);
    public passwordSelectedId: KnockoutObservable<number> = ko.observable(1);

    public noticeCheckList: KnockoutObservableArray<CheckboxModel> = ko.observableArray([]);
    public noticeSelectedId: KnockoutObservable<number> = ko.observable(1);

    public speechCheckList: KnockoutObservableArray<CheckboxModel> = ko.observableArray([]);
    public speechSelectedId: KnockoutObservable<number> = ko.observable(1);

    public A4_4_33_Value: KnockoutObservable<string> = ko.observable('');
    public A4_4_36_Value: KnockoutObservable<string> = ko.observable('');
    public A4_4_39_Value: KnockoutObservable<string> = ko.observable('');
    public A4_4_42_Value: KnockoutObservable<string> = ko.observable('');
    public A4_4_45_Value: KnockoutObservable<string> = ko.observable('');

    public companyMobilePhone: KnockoutObservable<boolean> = ko.observable(true);

    public emailColumns: any[] = [
      {
        headerText: this.$i18n("CMM049_66"),
        prop: "emailAddress",
        width: 180,
      }
    ]

    public emailDataSource: KnockoutObservableArray<EmailModel> = ko.observableArray([
      new EmailModel({emailAddress: this.$i18n("CMM049_49"),}),
      new EmailModel({emailAddress: this.$i18n("CMM049_50"),}),
      new EmailModel({emailAddress: this.$i18n("CMM049_51"),}),
      new EmailModel({emailAddress: this.$i18n("CMM049_52"),})
    ]);

    public selectedEmailAddress: KnockoutObservable<string> = ko.observable();

    constructor() {
      super();
      const vm = this;
      vm.tabs = ko.observableArray([
        {
          id: "tab-1",
          title: this.$i18n("CMM049_24"),
          content: "#A4",
          enable: ko.observable(true),
          visible: ko.observable(true),
        },
        {
          id: "tab-2",
          title: this.$i18n("CMM049_25"),
          content: "#A5",
          enable: ko.observable(true),
          visible: ko.observable(true),
        },
        {
          id: "tab-3",
          title: this.$i18n("CMM049_26"),
          content: "#A6",
          enable: ko.observable(true),
          visible: ko.observable(true),
        },
        {
          id: "tab-4",
          title: this.$i18n("CMM049_27"),
          content: "#A7",
          enable: ko.observable(true),
          visible: ko.observable(true),
        },
        {
          id: "tab-5",
          title: this.$i18n("CMM049_28"),
          content: "#A8",
          enable: ko.observable(true),
          visible: ko.observable(true),
        },
      ]);
      vm.selectedTab = ko.observable("tab-1");

      // tab 1 - profile function
      vm.profileCheckList = ko.observableArray([
        new CheckboxModel({
          id: 1,
          name: this.$i18n("CMM049_31"),
        }),
        new CheckboxModel({
          id: 2,
          name: this.$i18n("CMM049_32"),
        }),
      ]);
      vm.profileSelectedId = ko.observable(1);

      // tab 2 - password
      vm.passwordCheckList = ko.observableArray([
        new CheckboxModel({
          id: 1,
          name: this.$i18n("CMM049_31"),
        }),
        new CheckboxModel({
          id: 2,
          name: this.$i18n("CMM049_32"),
        }),
      ]);
      vm.passwordSelectedId = ko.observable(1);

      // tab 3 - notice
      vm.noticeCheckList = ko.observableArray([
        new CheckboxModel({
          id: 1,
          name: this.$i18n("CMM049_31"),
        }),
        new CheckboxModel({
          id: 2,
          name: this.$i18n("CMM049_32"),
        }),
      ]);
      vm.noticeSelectedId = ko.observable(1);

      // tab 4 - speech
      vm.speechCheckList = ko.observableArray([
        new CheckboxModel({
          id: 1,
          name: this.$i18n("CMM049_31"),
        }),
        new CheckboxModel({
          id: 2,
          name: this.$i18n("CMM049_32"),
        }),
      ]);
      vm.speechSelectedId = ko.observable(1);
    }

    mounted() {
      const vm = this;
      vm.$grid = $("#A8_4");
      vm.$grid.ntsGrid({
        primaryKey: 'dataAvailable',
        height: "270px",
        dataSource: [],
        rowVirtualization: true,
        virtualization: true,
        virtualizationMode: "continuous",
        columns: [
          {
            headerText: '',
            key: "dataAvailable",
            dataType: "boolean",
            width: "35px",
            ntsControl: "Checkbox",
            showHeaderCheckbox: true,
          },
          {
            headerText: this.$i18n("CMM049_21"),
            key: "targetMenu",
            dataType: "string",
            width: "200x",
          }
        ],
        features: [
          {
            name: "Selection",
            mode: "row",
            multipleSelection: false,
            activation: false,
            rowSelectionChanged: function () { },
          },
        ],
        ntsFeatures: [],
        ntsControls: [
          {
            name: "Checkbox",
            options: { value: 1, text: "" },
            optionsValue: "value",
            optionsText: "text",
            controlType: "CheckBox",
            enable: true
          },
        ],
      })
    }

    public closeDialog() {
      // set shared
      nts.uk.ui.windows.setShared("KEY_OF_SHARED_DATA", {
        /* body of data */
      });

      nts.uk.ui.windows.close();
    }
  }

  export class CheckboxModel {
    id: number;
    name: string;

    constructor(init?: Partial<CheckboxModel>) {
      $.extend(this, init);
    }
  }

  export class EmailModel {
    emailAddress: string;

    constructor(init?: Partial<EmailModel>) {
      $.extend(this, init);
    }
  }

  export class UserInformationSettingDto {
    infoUseMethodDto: UserInfoUseMethod_Dto;
    mailFunctionDtos: MailFunctionDto[];

    constructor(init?: Partial<UserInformationSettingDto>) {
      $.extend(this, init);
    }
  }

  export class UserInfoUseMethod_Dto {
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

    constructor(init?: Partial<UserInfoUseMethod_Dto>) {
      $.extend(this, init);
    }
  }

  export class MailFunctionDto {
    //機能ID
    functionId: number;

    //機能名
    functionName: string;
    
    //メール送信設定可否区分
    proprietySendMailSettingAtr: boolean;
    
    //並び順
    sortOrder: number;

    constructor(init?: Partial<MailFunctionDto>) {
      $.extend(this, init);
    }
  }

  export class EmailDestinationFunctionDto {
    /**
     * メール分類
     */
    emailClassification: number;

    /**
     * 機能ID
     */
    functionIds: number[];

    constructor(init?: Partial<EmailDestinationFunctionDto>) {
      $.extend(this, init);
    }
  }

  export class SettingContactInformationDto {
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

    constructor(init?: Partial<SettingContactInformationDto>) {
      $.extend(this, init);
    }
  }

  export class ContactSettingDto {
     /**
     * 連絡先利用設定
     */
    contactUsageSetting: number;

    /**
     * 更新可能
     */
    updatable: number;

    constructor(init?: Partial<ContactSettingDto>) {
      $.extend(this, init);
    }
  }

  export class OtherContactDto {
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

    constructor(init?: Partial<OtherContactDto>) {
      $.extend(this, init);
    }
  }
}
