module nts.uk.com.view.cmm048.a {

  const API = {
    find: "query/cmm048userinformation/find",
  };
  @bean()
  export class ViewModel extends ko.ViewModel {

    //general
    tabs: KnockoutObservableArray<any> = ko.observableArray([
      { id: 'tab-1', title: this.generateTitleTab(this.$i18n('CMM048_92'), 'profile'), content: '.tab-content-1', enable: ko.observable(true), visible: ko.observable(true) },
      { id: 'tab-2', title: this.generateTitleTab(this.$i18n('CMM048_93'), 'password'), content: '.tab-content-2', enable: ko.observable(true), visible: ko.observable(true) },
      { id: 'tab-3', title: this.generateTitleTab(this.$i18n('CMM048_94'), 'notification'), content: '.tab-content-3', enable: ko.observable(true), visible: ko.observable(true) },
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
    C2_2_Value : KnockoutObservable<string> = ko.observable('');
    C2_3_Value : KnockoutObservable<string> = ko.observable('');
    C2_4_Value : KnockoutObservable<string> = ko.observable('');
    C2_6_Value : KnockoutObservable<string> = ko.observable('');
    C2_6_Options : KnockoutObservableArray<any> = ko.observableArray([
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


    private generateTitleTab(rsCode: string, icon: string): string {
      return (
        `<span>
        <img class="tab-icon" src="./resource/`+icon+`.svg" />
        <span>`+rsCode+`</span>
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
      vm.$blockui('grayout')
      vm.listAnniversary.push(new AnniversaryNotification("", "", "", 0));
      vm.$ajax(API.find).then(data =>  console.log(data))
      .fail(error => {
        vm.$blockui('clear')
        vm.$dialog.error(error);
      })
      .always(() => {
        vm.$blockui('clear');
      });
    }

    public removeAnniversary(anniversary: AnniversaryNotification) {
      const vm = this;
      vm.listAnniversary.remove(anniversary);
    }

    public save() {
      const vm = this;
      console.log(1)

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
}