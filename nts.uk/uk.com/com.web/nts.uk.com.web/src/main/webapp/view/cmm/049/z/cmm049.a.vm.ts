module nts.uk.com.view.cmm049.z {

  @bean()
  export class ScreenModel extends ko.ViewModel {
    $grid!: JQuery;

    public tabs: KnockoutObservableArray<any>;
    public selectedTab: KnockoutObservable<string>;

    public profileCheckList: KnockoutObservableArray<CheckboxModel> = ko.observableArray([]);
    public profileSelectedId: KnockoutObservable<number>;

    public passwordCheckList: KnockoutObservableArray<CheckboxModel> = ko.observableArray([]);
    public passwordSelectedId: KnockoutObservable<number>;

    public noticeCheckList: KnockoutObservableArray<CheckboxModel> = ko.observableArray([]);
    public noticeSelectedId: KnockoutObservable<number>;

    public speechCheckList: KnockoutObservableArray<CheckboxModel> = ko.observableArray([]);
    public speechSelectedId: KnockoutObservable<number>;

    public A4_4_33_Value: KnockoutObservable<string> = ko.observable('');
    public A4_4_36_Value: KnockoutObservable<string> = ko.observable('');
    public A4_4_39_Value: KnockoutObservable<string> = ko.observable('');
    public A4_4_42_Value: KnockoutObservable<string> = ko.observable('');
    public A4_4_45_Value: KnockoutObservable<string> = ko.observable('');

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
}
