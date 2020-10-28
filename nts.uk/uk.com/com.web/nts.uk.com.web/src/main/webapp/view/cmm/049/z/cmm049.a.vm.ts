module nts.uk.com.view.cmm049.z {
  const API = {
    findByCid: "query/cmm049userinformationsetting/get",
    insertOrUpdate: "sys/env/userinfousermethod/insertorupdate",
  };

  @bean()
  export class ScreenModel extends ko.ViewModel {
    $grid!: JQuery;

    public tabs: KnockoutObservableArray<any>;
    public selectedTab: KnockoutObservable<string>;

    public profileCheckList: KnockoutObservableArray<
      CheckboxModel
    > = ko.observableArray([]);
    public profileSelectedId: KnockoutObservable<number> = ko.observable(1);


    public passwordCheckList: KnockoutObservableArray<
      CheckboxModel
    > = ko.observableArray([]);
    public passwordSelectedId: KnockoutObservable<number> = ko.observable(1);

    public noticeCheckList: KnockoutObservableArray<
      CheckboxModel
    > = ko.observableArray([]);
    public noticeSelectedId: KnockoutObservable<number> = ko.observable(1);

    public speechCheckList: KnockoutObservableArray<
      CheckboxModel
    > = ko.observableArray([]);
    public speechSelectedId: KnockoutObservable<number> = ko.observable(1);

    public A4_4_33_Value: KnockoutObservable<string> = ko.observable("");
    public A4_4_36_Value: KnockoutObservable<string> = ko.observable("");
    public A4_4_39_Value: KnockoutObservable<string> = ko.observable("");
    public A4_4_42_Value: KnockoutObservable<string> = ko.observable("");
    public A4_4_45_Value: KnockoutObservable<string> = ko.observable("");

    public companyMobilePhoneDisplay: KnockoutObservable<
      boolean
    > = ko.observable(true);
    public companyMobilePhoneUpdatable: KnockoutObservable<
      boolean
    > = ko.observable(true);
    public companyMobilePhoneIndividual: KnockoutObservable<
      boolean
    > = ko.observable(true);

    public personalMobilePhoneDisplay: KnockoutObservable<
      boolean
    > = ko.observable(true);
    public personalMobilePhoneUpdatable: KnockoutObservable<
      boolean
    > = ko.observable(true);
    public personalMobilePhoneIndividual: KnockoutObservable<
      boolean
    > = ko.observable(true);

    public emergencyNumber1Display: KnockoutObservable<boolean> = ko.observable(
      true
    );
    public emergencyNumber1Updatable: KnockoutObservable<
      boolean
    > = ko.observable(true);
    public emergencyNumber1Individual: KnockoutObservable<
      boolean
    > = ko.observable(true);

    public emergencyNumber2Display: KnockoutObservable<boolean> = ko.observable(
      true
    );
    public emergencyNumber2Updatable: KnockoutObservable<
      boolean
    > = ko.observable(true);
    public emergencyNumber2Individual: KnockoutObservable<
      boolean
    > = ko.observable(true);

    public dialInNumberDisplay: KnockoutObservable<boolean> = ko.observable(
      true
    );
    public dialInNumberUpdatable: KnockoutObservable<boolean> = ko.observable(
      true
    );
    public dialInNumberIndividual: KnockoutObservable<boolean> = ko.observable(
      true
    );

    public extensionNumberDisplay: KnockoutObservable<boolean> = ko.observable(
      true
    );
    public extensionNumberUpdatable: KnockoutObservable<
      boolean
    > = ko.observable(true);
    public extensionNumberIndividual: KnockoutObservable<
      boolean
    > = ko.observable(true);

    public companyEmailAddressDisplay: KnockoutObservable<
      boolean
    > = ko.observable(true);
    public companyEmailAddressUpdatable: KnockoutObservable<
      boolean
    > = ko.observable(true);
    public companyEmailAddressIndividual: KnockoutObservable<
      boolean
    > = ko.observable(true);

    public companyMobileEmailAddressDisplay: KnockoutObservable<
      boolean
    > = ko.observable(true);
    public companyMobileEmailAddressUpdatable: KnockoutObservable<
      boolean
    > = ko.observable(true);
    public companyMobileEmailAddressIndividual: KnockoutObservable<
      boolean
    > = ko.observable(true);

    public personalEmailAddressDisplay: KnockoutObservable<
      boolean
    > = ko.observable(true);
    public personalEmailAddressUpdatable: KnockoutObservable<
      boolean
    > = ko.observable(true);
    public personalEmailAddressIndividual: KnockoutObservable<
      boolean
    > = ko.observable(true);

    public personalMobileEmailAddressDisplay: KnockoutObservable<
      boolean
    > = ko.observable(true);
    public personalMobileEmailAddressUpdatable: KnockoutObservable<
      boolean
    > = ko.observable(true);
    public personalMobileEmailAddressIndividual: KnockoutObservable<
      boolean
    > = ko.observable(true);

    public otherContact1Display: KnockoutObservable<boolean> = ko.observable(
      true
    );
    public otherContact1ContactName: KnockoutObservable<string> = ko.observable(
      ""
    );
    public otherContact1Individual: KnockoutObservable<boolean> = ko.observable(
      true
    );

    public otherContact2Display: KnockoutObservable<boolean> = ko.observable(
      true
    );
    public otherContact2ContactName: KnockoutObservable<string> = ko.observable(
      ""
    );
    public otherContact2Individual: KnockoutObservable<boolean> = ko.observable(
      true
    );

    public otherContact3Display: KnockoutObservable<boolean> = ko.observable(
      true
    );
    public otherContact3ContactName: KnockoutObservable<string> = ko.observable(
      ""
    );
    public otherContact3Individual: KnockoutObservable<boolean> = ko.observable(
      true
    );

    public otherContact4Display: KnockoutObservable<boolean> = ko.observable(
      true
    );
    public otherContact4ContactName: KnockoutObservable<string> = ko.observable(
      ""
    );
    public otherContact4Individual: KnockoutObservable<boolean> = ko.observable(
      true
    );

    public otherContact5Display: KnockoutObservable<boolean> = ko.observable(
      true
    );
    public otherContact5ContactName: KnockoutObservable<string> = ko.observable(
      ""
    );
    public otherContact5Individual: KnockoutObservable<boolean> = ko.observable(
      true
    );

    public mailFunctionDtos: KnockoutObservableArray<MailFunctionDto> = ko.observableArray([]);
    public userInfoUseMethod_Dto: KnockoutObservable<
      UserInfoUseMethod_Dto
    > = ko.observable();
    public otherContact1: KnockoutObservable<string> = ko.observable("");
    public otherContact2: KnockoutObservable<string> = ko.observable("");
    public otherContact3: KnockoutObservable<string> = ko.observable("");
    public otherContact4: KnockoutObservable<string> = ko.observable("");
    public otherContact5: KnockoutObservable<string> = ko.observable("");

    public selectedEmailAddress: KnockoutObservable<number> = ko.observable(0);

    // Key: emailClassification, value: listfunctionId
    public emailData: any = {};
    // Giá trị emailClassification đang được chọn
    public selectedEmailClassification: number = null;
    // Các functionId được check theo key emailClassification
    public mailFunctionDataSource: MailFunctionModel[] = [];

    public emailColumns: any[] = [
      {
        headerText: "",
        prop: "index",
        width: 160,
        hidden: true,
      },
      {
        headerText: this.$i18n("CMM049_66"),
        prop: "emailAddress",
        width: 180,
      },
    ];

    public emailDataSource: KnockoutObservableArray<
      EmailModel
    > = ko.observableArray([
      new EmailModel({ index: 0, emailAddress: this.$i18n("CMM049_49") }),
      new EmailModel({ index: 1, emailAddress: this.$i18n("CMM049_50") }),
      new EmailModel({ index: 2, emailAddress: this.$i18n("CMM049_51") }),
      new EmailModel({ index: 3, emailAddress: this.$i18n("CMM049_52") }),
    ]);

    // constructor() {
    //   super();
    //   const vm = this;
    //   vm.tabs = ko.observableArray (
    //     {
    //       id: "tab-1",
    //       title: vm.$i18n("CMM049_24"),
    //       content: "#A4",
    //       enable: ko.observable(true),
    //       visible: ko.observable(true),
    //     },
    //     {
    //       id: "tab-2",
    //       title: vm.$i18n("CMM049_25"),
    //       content: "#A5",
    //       enable: ko.observable(true),
    //       visible: ko.observable(true),
    //     },
    //     {
    //       id: "tab-3",
    //       title: vm.$i18n("CMM049_26"),
    //       content: "#A6",
    //       enable: ko.observable(true),
    //       visible: ko.observable(true),
    //     },
    //     {
    //       id: "tab-4",
    //       title: vm.$i18n("CMM049_27"),
    //       content: "#A7",
    //       enable: ko.observable(true),
    //       visible: ko.observable(true),
    //     },
    //     {
    //       id: "tab-5",
    //       title: vm.$i18n("CMM049_28"),
    //       content: "#A8",
    //       enable: ko.observable(true),
    //       visible: ko.observable(true),
    //     },
    //   ]);
    //   vm.selectedTab = ko.observable("tab-1");

    //   // tab 1 - profile function
    //   vm.profileCheckList = ko.observableArray([
    //     new CheckboxModel({
    //       id: 1,
    //       name: vm.$i18n("CMM049_31"),
    //     }),
    //     new CheckboxModel({
    //       id: 2,
    //       name: vm.$i18n("CMM049_32"),
    //     }),
    //   ]);
    //   vm.profileSelectedId = ko.observable(1);

    //   // tab 2 - password
    //   vm.passwordCheckList = ko.observableArray([
    //     new CheckboxModel({
    //       id: 1,
    //       name: vm.$i18n("CMM049_31"),
    //     }),
    //     new CheckboxModel({
    //       id: 2,
    //       name: vm.$i18n("CMM049_32"),
    //     }),
    //   ]);
    //   vm.passwordSelectedId = ko.observable(1);

    //   // tab 3 - notice
    //   vm.noticeCheckList = ko.observableArray([
    //     new CheckboxModel({
    //       id: 1,
    //       name: vm.$i18n("CMM049_31"),
    //     }),
    //     new CheckboxModel({
    //       id: 2,
    //       name: vm.$i18n("CMM049_32"),
    //     }),
    //   ]);
    //   vm.noticeSelectedId = ko.observable(1);

    //   // tab 4 - speech
    //   vm.speechCheckList = ko.observableArray([
    //     new CheckboxModel({
    //       id: 1,
    //       name: vm.$i18n("CMM049_31"),
    //     }),
    //     new CheckboxModel({
    //       id: 2,
    //       name: vm.$i18n("CMM049_32"),
    //     }),
    //   ]);
    //   vm.speechSelectedId = ko.observable(1);
    // }
 
    mounted() {
      const vm = this;

      vm.$grid = $("#A8_4");

      vm.selectedEmailAddress.subscribe((newValue) => {
        /**
         * Lọc toàn bộ các functionId được check theo emailClassification
         */
        vm.emailData[vm.selectedEmailClassification] = _.chain(vm.mailFunctionDataSource)
          .filter((item) => item.isChecked)
          .map((item) => item.functionId)
          .value();
        if (vm.$grid.data("igGrid")) {
          vm.$grid.ntsGrid("destroy");
        }
        vm.initGrid(newValue, vm.mailFunctionDtos());
      });

      vm.getData();
    }

    public getData() {
      const vm = this;
      vm.$blockui("grayout")
        .then(() => vm.$ajax(API.findByCid))
        .then((response: UserInformationSettingDto) => {
          console.log(response);
          vm.mailFunctionDtos(response.mailFunctionDtos);
          vm.userInfoUseMethod_Dto(response.userInfoUseMethod_Dto);

          // binding button group (tab-1-2-3-4)
          vm.profileSelectedId(response.userInfoUseMethod_Dto.useOfProfile);
          vm.passwordSelectedId(response.userInfoUseMethod_Dto.useOfPassword);
          vm.noticeSelectedId(response.userInfoUseMethod_Dto.useOfNotice);
          vm.speechSelectedId(response.userInfoUseMethod_Dto.useOfLanguage);

          // binding contact name input (tab-1)
          vm.A4_4_33_Value(vm.getOtherContact(1, response).contactName);
          vm.A4_4_36_Value(vm.getOtherContact(2, response).contactName);
          vm.A4_4_39_Value(vm.getOtherContact(3, response).contactName);
          vm.A4_4_42_Value(vm.getOtherContact(4, response).contactName);
          vm.A4_4_45_Value(vm.getOtherContact(5, response).contactName);

          // binding data (tab-1)
          // line 1
          const companyMobilePhone =
            response.userInfoUseMethod_Dto.settingContactInformation
              .companyMobilePhone.contactUsageSetting;
          switch (companyMobilePhone) {
            case 0: {
              vm.companyMobilePhoneDisplay(false);
              break;
            }
            case 1: {
              vm.companyMobilePhoneDisplay(true);
              vm.companyMobilePhoneIndividual(false);
              break;
            }
            case 2: {
              vm.companyMobilePhoneDisplay(true);
              vm.companyMobilePhoneIndividual(true);
              break;
            }
          }
          vm.companyMobilePhoneUpdatable(
            response.userInfoUseMethod_Dto.settingContactInformation
              .companyMobilePhone.updatable === 1
          );

          // line 2
          const personalMobilePhone =
            response.userInfoUseMethod_Dto.settingContactInformation
              .personalMobilePhone.contactUsageSetting;
          switch (personalMobilePhone) {
            case 0: {
              vm.personalMobilePhoneDisplay(false);
              break;
            }
            case 1: {
              vm.personalMobilePhoneDisplay(true);
              vm.personalMobilePhoneIndividual(false);
              break;
            }
            case 2: {
              vm.personalMobilePhoneDisplay(true);
              vm.personalMobilePhoneIndividual(true);
              break;
            }
          }
          vm.personalMobilePhoneUpdatable(
            response.userInfoUseMethod_Dto.settingContactInformation
              .personalMobilePhone.updatable === 1
          );

          // line 3
          const emergencyNumber1 =
            response.userInfoUseMethod_Dto.settingContactInformation
              .emergencyNumber1.contactUsageSetting;
          switch (emergencyNumber1) {
            case 0: {
              vm.emergencyNumber1Display(false);
              break;
            }
            case 1: {
              vm.emergencyNumber1Display(true);
              vm.emergencyNumber1Individual(false);
              break;
            }
            case 2: {
              vm.emergencyNumber1Display(true);
              vm.emergencyNumber1Individual(true);
              break;
            }
          }
          vm.emergencyNumber1Updatable(
            response.userInfoUseMethod_Dto.settingContactInformation
              .emergencyNumber1.updatable === 1
          );

          // line 4
          const emergencyNumber2 =
            response.userInfoUseMethod_Dto.settingContactInformation
              .emergencyNumber2.contactUsageSetting;
          switch (emergencyNumber2) {
            case 0: {
              vm.emergencyNumber2Display(false);
              break;
            }
            case 1: {
              vm.emergencyNumber2Display(true);
              vm.emergencyNumber2Individual(false);
              break;
            }
            case 2: {
              vm.emergencyNumber2Display(true);
              vm.emergencyNumber2Individual(true);
              break;
            }
          }
          vm.emergencyNumber2Updatable(
            response.userInfoUseMethod_Dto.settingContactInformation
              .emergencyNumber2.updatable === 1
          );

          // line 5
          const dialInNumber =
            response.userInfoUseMethod_Dto.settingContactInformation
              .dialInNumber.contactUsageSetting;
          switch (dialInNumber) {
            case 0: {
              vm.dialInNumberDisplay(false);
              break;
            }
            case 1: {
              vm.dialInNumberDisplay(true);
              vm.dialInNumberIndividual(false);
              break;
            }
            case 2: {
              vm.dialInNumberDisplay(true);
              vm.dialInNumberIndividual(true);
              break;
            }
          }
          vm.dialInNumberUpdatable(
            response.userInfoUseMethod_Dto.settingContactInformation
              .dialInNumber.updatable === 1
          );

          // line 6
          const extensionNumber =
            response.userInfoUseMethod_Dto.settingContactInformation
              .extensionNumber.contactUsageSetting;
          switch (extensionNumber) {
            case 0: {
              vm.extensionNumberDisplay(false);
              break;
            }
            case 1: {
              vm.extensionNumberDisplay(true);
              vm.extensionNumberIndividual(false);
              break;
            }
            case 2: {
              vm.extensionNumberDisplay(true);
              vm.extensionNumberIndividual(true);
              break;
            }
          }
          vm.extensionNumberUpdatable(
            response.userInfoUseMethod_Dto.settingContactInformation
              .extensionNumber.updatable === 1
          );

          // line 7
          const companyEmailAddress =
            response.userInfoUseMethod_Dto.settingContactInformation
              .companyEmailAddress.contactUsageSetting;
          switch (companyEmailAddress) {
            case 0: {
              vm.companyEmailAddressDisplay(false);
              break;
            }
            case 1: {
              vm.companyEmailAddressDisplay(true);
              vm.companyEmailAddressIndividual(false);
              break;
            }
            case 2: {
              vm.companyEmailAddressDisplay(true);
              vm.companyEmailAddressIndividual(true);
              break;
            }
          }
          vm.companyEmailAddressUpdatable(
            response.userInfoUseMethod_Dto.settingContactInformation
              .companyEmailAddress.updatable === 1
          );

          // line 8
          const companyMobileEmailAddress =
            response.userInfoUseMethod_Dto.settingContactInformation
              .companyMobileEmailAddress.contactUsageSetting;
          switch (companyMobileEmailAddress) {
            case 0: {
              vm.companyMobileEmailAddressDisplay(false);
              break;
            }
            case 1: {
              vm.companyMobileEmailAddressDisplay(true);
              vm.companyMobileEmailAddressIndividual(false);
              break;
            }
            case 2: {
              vm.companyMobileEmailAddressDisplay(true);
              vm.companyMobileEmailAddressIndividual(true);
              break;
            }
          }
          vm.companyMobileEmailAddressUpdatable(
            response.userInfoUseMethod_Dto.settingContactInformation
              .companyMobileEmailAddress.updatable === 1
          );

          // line 9
          const personalEmailAddress =
            response.userInfoUseMethod_Dto.settingContactInformation
              .personalEmailAddress.contactUsageSetting;
          switch (personalEmailAddress) {
            case 0: {
              vm.personalEmailAddressDisplay(false);
              break;
            }
            case 1: {
              vm.personalEmailAddressDisplay(true);
              vm.personalEmailAddressIndividual(false);
              break;
            }
            case 2: {
              vm.personalEmailAddressDisplay(true);
              vm.personalEmailAddressIndividual(true);
              break;
            }
          }
          vm.personalEmailAddressUpdatable(
            response.userInfoUseMethod_Dto.settingContactInformation
              .personalEmailAddress.updatable === 1
          );

          // line 10
          const personalMobileEmailAddress =
            response.userInfoUseMethod_Dto.settingContactInformation
              .personalMobileEmailAddress.contactUsageSetting;
          switch (personalMobileEmailAddress) {
            case 0: {
              vm.personalMobileEmailAddressDisplay(false);
              break;
            }
            case 1: {
              vm.personalMobileEmailAddressDisplay(true);
              vm.personalMobileEmailAddressIndividual(false);
              break;
            }
            case 2: {
              vm.personalMobileEmailAddressDisplay(true);
              vm.personalMobileEmailAddressIndividual(true);
              break;
            }
          }
          vm.personalMobileEmailAddressUpdatable(
            response.userInfoUseMethod_Dto.settingContactInformation
              .personalMobileEmailAddress.updatable === 1
          );

          // line 11
          const otherContact1 = vm.getOtherContact(1, response);
          switch (otherContact1.contactUsageSetting) {
            case 0: {
              vm.otherContact1Display(false);
              break;
            }
            case 1: {
              vm.otherContact1Display(true);
              vm.otherContact1Individual(false);
              break;
            }
            case 2: {
              vm.otherContact1Display(true);
              vm.otherContact1Individual(true);
              break;
            }
          }

          // line 12
          const otherContact2 = vm.getOtherContact(2, response);
          switch (otherContact2.contactUsageSetting) {
            case 0: {
              vm.otherContact2Display(false);
              break;
            }
            case 1: {
              vm.otherContact2Display(true);
              vm.otherContact2Individual(false);
              break;
            }
            case 2: {
              vm.otherContact2Display(true);
              vm.otherContact2Individual(true);
              break;
            }
          }

          // line 13
          const otherContact3 = vm.getOtherContact(3, response);
          switch (otherContact3.contactUsageSetting) {
            case 0: {
              vm.otherContact3Display(false);
              break;
            }
            case 1: {
              vm.otherContact3Display(true);
              vm.otherContact3Individual(false);
              break;
            }
            case 2: {
              vm.otherContact3Display(true);
              vm.otherContact3Individual(true);
              break;
            }
          }

          // line 14
          const otherContact4 = vm.getOtherContact(4, response);
          switch (otherContact4.contactUsageSetting) {
            case 0: {
              vm.otherContact4Display(false);
              break;
            }
            case 1: {
              vm.otherContact4Display(true);
              vm.otherContact4Individual(false);
              break;
            }
            case 2: {
              vm.otherContact4Display(true);
              vm.otherContact4Individual(true);
              break;
            }
          }

          // line 15
          const otherContact5 = vm.getOtherContact(5, response);
          switch (otherContact5.contactUsageSetting) {
            case 0: {
              vm.otherContact5Display(false);
              break;
            }
            case 1: {
              vm.otherContact5Display(true);
              vm.otherContact5Individual(false);
              break;
            }
            case 2: {
              vm.otherContact5Display(true);
              vm.otherContact5Individual(true);
              break;
            }
          }

          /**
           * Gán key (emailClassification) và value (List functionIds) cho emailData
           */
          _.forEach(
            response.userInfoUseMethod_Dto.emailDestinationFunctions,
            (email: EmailDestinationFunctionDto) =>
              (vm.emailData[email.emailClassification] = email.functionIds)
          );

          vm.initGrid(vm.selectedEmailAddress(), vm.mailFunctionDtos());
        })
        .always(() => vm.$blockui("clear"));
    }

    private getOtherContact(no: number, response: any): OtherContactDto {
      return _.find(
        response.userInfoUseMethod_Dto.settingContactInformation.otherContacts,
        (item: any) => item.no === no
      );
    }

    /**
     * Create Table target email
     * @param emailClassification // email đang được chọn
     * @param mailFunctionList // List mailFunction (dữ liệu bảng bên phải)
     */
    private initGrid(emailClassification: number, mailFunctionList: MailFunctionDto[]) {
      const vm = this;
      /**
       * Tìm List functionId theo key emailClassification
       */
      const selectedFundtionIds: number[] = vm.emailData[emailClassification];

      /**
       * Lưu giá trị emailClassification đang được chọn
       */
      vm.selectedEmailClassification = emailClassification;

      /**
       * Lưu những functionId được check
       */
      vm.mailFunctionDataSource = _.map(mailFunctionList, (item) => {
        const model = new MailFunctionModel(item);
        model.isChecked = selectedFundtionIds.indexOf(item.functionId) !== -1;
        return model;
      });
      vm.$grid.ntsGrid({
        primaryKey: "functionId",
        height: "270px",
        dataSource: vm.mailFunctionDataSource,
        rowVirtualization: true,
        virtualization: true,
        virtualizationMode: "continuous",
        columns: [
          {
            headerText: "",
            key: "functionId",
            dataType: "number",
            hidden: true,
          },
          {
            headerText: "",
            key: "isChecked",
            dataType: "boolean",
            width: "35px",
            ntsControl: "Checkbox",
            showHeaderCheckbox: true,
          },
          {
            headerText: vm.$i18n("CMM049_21"),
            key: "targetMenu",
            dataType: "string",
            width: "300x",
          },
        ],
        features: [
          {
            name: "Selection",
            mode: "row",
            multipleSelection: true,
            activation: false,
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
            enable: true,
          },
        ],
      });
    }

    public closeDialog() {
      // set shared
      nts.uk.ui.windows.setShared("KEY_OF_SHARED_DATA", {
        /* body of data */
      });

      nts.uk.ui.windows.close();
    }

    public settingValue(boo1: boolean, boo2: boolean): number {
      return !boo1 ? 0 : !boo2 ? 1 : 2;
    }

    public register() {
      const vm = this;
      vm.emailData[vm.selectedEmailClassification] = _.chain(vm.mailFunctionDataSource)
          .filter((item) => item.isChecked)
          .map((item) => item.functionId)
          .value();
      const otherContactDtos: OtherContactDto[] = [
        new OtherContactDto({
          no: 1,
          contactName: vm.A4_4_33_Value(),
          contactUsageSetting: vm.settingValue(
            vm.otherContact1Display(),
            vm.otherContact1Individual()
          ),
        }),
        new OtherContactDto({
          no: 2,
          contactName: vm.A4_4_36_Value(),
          contactUsageSetting: vm.settingValue(
            vm.otherContact2Display(),
            vm.otherContact2Individual()
          ),
        }),
        new OtherContactDto({
          no: 3,
          contactName: vm.A4_4_39_Value(),
          contactUsageSetting: vm.settingValue(
            vm.otherContact3Display(),
            vm.otherContact3Individual()
          ),
        }),
        new OtherContactDto({
          no: 4,
          contactName: vm.A4_4_42_Value(),
          contactUsageSetting: vm.settingValue(
            vm.otherContact4Display(),
            vm.otherContact4Individual()
          ),
        }),
        new OtherContactDto({
          no: 5,
          contactName: vm.A4_4_45_Value(),
          contactUsageSetting: vm.settingValue(
            vm.otherContact5Display(),
            vm.otherContact5Individual()
          ),
        }),
      ];
      const userInfoUseMethod_Dto: UserInfoUseMethod_Dto = new UserInfoUseMethod_Dto(
        {
          useOfProfile: vm.profileSelectedId() == 1 ? 1 : 0,
          useOfPassword: vm.passwordSelectedId() == 1 ? 1 : 0,
          useOfNotice: vm.noticeSelectedId() == 1 ? 1 : 0,
          useOfLanguage: vm.speechSelectedId() == 1 ? 1 : 0,
          companyId: vm.userInfoUseMethod_Dto().companyId,
          settingContactInformation: new SettingContactInformationDto({
            companyEmailAddress: new ContactSettingDto({
              contactUsageSetting: vm.settingValue(
                vm.companyEmailAddressDisplay(),
                vm.companyEmailAddressIndividual()
              ),
              updatable: vm.companyEmailAddressUpdatable() ? 1 : 0,
            }),
            companyMobileEmailAddress: new ContactSettingDto({
              contactUsageSetting: vm.settingValue(
                vm.companyMobileEmailAddressDisplay(),
                vm.companyMobileEmailAddressIndividual()
              ),
              updatable: vm.companyMobileEmailAddressUpdatable() ? 1 : 0,
            }),
            companyMobilePhone: new ContactSettingDto({
              contactUsageSetting: vm.settingValue(
                vm.companyMobilePhoneDisplay(),
                vm.companyMobilePhoneIndividual()
              ),
              updatable: vm.companyMobilePhoneUpdatable() ? 1 : 0,
            }),
            dialInNumber: new ContactSettingDto({
              contactUsageSetting: vm.settingValue(
                vm.dialInNumberDisplay(),
                vm.dialInNumberIndividual()
              ),
              updatable: vm.dialInNumberUpdatable() ? 1 : 0,
            }),
            emergencyNumber1: new ContactSettingDto({
              contactUsageSetting: vm.settingValue(
                vm.emergencyNumber1Display(),
                vm.emergencyNumber1Individual()
              ),
              updatable: vm.emergencyNumber1Updatable() ? 1 : 0,
            }),
            emergencyNumber2: new ContactSettingDto({
              contactUsageSetting: vm.settingValue(
                vm.emergencyNumber2Display(),
                vm.emergencyNumber2Individual()
              ),
              updatable: vm.emergencyNumber2Updatable() ? 1 : 0,
            }),
            extensionNumber: new ContactSettingDto({
              contactUsageSetting: vm.settingValue(
                vm.extensionNumberDisplay(),
                vm.extensionNumberIndividual()
              ),
              updatable: vm.extensionNumberUpdatable() ? 1 : 0,
            }),
            personalEmailAddress: new ContactSettingDto({
              contactUsageSetting: vm.settingValue(
                vm.personalEmailAddressDisplay(),
                vm.personalEmailAddressIndividual()
              ),
              updatable: vm.personalEmailAddressUpdatable() ? 1 : 0,
            }),
            personalMobileEmailAddress: new ContactSettingDto({
              contactUsageSetting: vm.settingValue(
                vm.personalMobileEmailAddressDisplay(),
                vm.personalMobileEmailAddressIndividual()
              ),
              updatable: vm.personalMobileEmailAddressUpdatable() ? 1 : 0,
            }),
            personalMobilePhone: new ContactSettingDto({
              contactUsageSetting: vm.settingValue(
                vm.personalMobilePhoneDisplay(),
                vm.personalMobilePhoneIndividual()
              ),
              updatable: vm.personalMobilePhoneUpdatable() ? 1 : 0,
            }),
            otherContacts: otherContactDtos,
          }),
          emailDestinationFunctions: [
            new EmailDestinationFunctionDto({
              emailClassification: 0,
              functionIds: vm.emailData[0]
            }),
            new EmailDestinationFunctionDto({
              emailClassification: 1,
              functionIds: vm.emailData[1]
            }),
            new EmailDestinationFunctionDto({
              emailClassification: 2,
              functionIds: vm.emailData[2]
            }),
            new EmailDestinationFunctionDto({
              emailClassification: 3,
              functionIds: vm.emailData[3]
            }),
          ],
        }
      );

      const command = new UserInfoUseMethod_SaveCommand({
        userInfoUseMethod_Dto: userInfoUseMethod_Dto,
      });
      console.log(command);

      vm.$blockui("grayout");
      vm.$ajax(API.insertOrUpdate, command)
        .always(() => vm.$blockui("clear"));
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
    index: number;
    emailAddress: string;

    constructor(init?: Partial<EmailModel>) {
      $.extend(this, init);
    }
  }

  export class UserInformationSettingDto {
    userInfoUseMethod_Dto: UserInfoUseMethod_Dto;
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

  export interface MailFunctionDto {
    //機能ID
    functionId: number;

    //機能名
    functionName: string;

    //メール送信設定可否区分
    proprietySendMailSettingAtr: boolean;

    //並び順
    sortOrder: number;
  }

  export class MailFunctionModel {
    //機能ID
    functionId: number;

    //機能名
    functionName: string;

    //メール送信設定可否区分
    proprietySendMailSettingAtr: boolean;

    //並び順
    sortOrder: number;

    isChecked: boolean;

    constructor(init?: Partial<MailFunctionModel>) {
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

  export class MailFunctionData {
    functionId: number;
    dataAvailable: boolean;
    targetMenu: string;

    constructor(init?: Partial<MailFunctionData>) {
      $.extend(this, init);
    }
  }

  export class UserInfoUseMethod_SaveCommand {
    userInfoUseMethod_Dto: UserInfoUseMethod_Dto;

    constructor(init?: Partial<UserInfoUseMethod_SaveCommand>) {
      $.extend(this, init);
    }
  }
}
