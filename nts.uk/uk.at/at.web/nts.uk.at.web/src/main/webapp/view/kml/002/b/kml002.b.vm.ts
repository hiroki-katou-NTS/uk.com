/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kml002.b {

  @bean()
  class ViewModel extends ko.ViewModel {

    laborCostTime: KnockoutObservable<number> = ko.observable(UsageClassification.Use);
    laborCostTimeDetails: KnockoutObservable<any> = ko.observable(null);//人件費・時間	
    countingNumberTimes: KnockoutObservable<number> = ko.observable(UsageClassification.Use);
    countingNumberTimesDetails: KnockoutObservableArray<any> = ko.observableArray([]);//回数集計		
    timeZoneNumberPeople: KnockoutObservable<number> = ko.observable(UsageClassification.Use);
    timeZoneNumberPeopleDetails: KnockoutObservableArray<any> = ko.observableArray([]); //時間帯人数			

    externalBudgetResults: KnockoutObservable<number> = ko.observable(UsageClassification.Use);
    numberPassengersWorkingHours: KnockoutObservable<number> = ko.observable(UsageClassification.Use);
    numberOfEmployees: KnockoutObservable<number> = ko.observable(UsageClassification.Use);
    numberOfPeople: KnockoutObservable<number> = ko.observable(UsageClassification.Use);
    numberOfPositions: KnockoutObservable<number> = ko.observable(UsageClassification.Use);

    switchOptions: KnockoutObservableArray<any> = ko.observableArray([]);

    constructor(params: any) {
      super();
      const vm = this;

      vm.switchOptions = ko.observableArray([
        { code: UsageClassification.Use, name: vm.$i18n('KML002_20') },
        { code: UsageClassification.NotUse, name: vm.$i18n('KML002_21') }
      ]);
    }

    created(params: any) {
      const vm = this;
      _.extend(window, { vm });
    }

    mounted() {
      const vm = this;
      $('#B322').focus();
    }

    openDialogKDL024() {
      const vm = this;

      vm.$window.modal('/view/kdl/024/a/index.xhtml').then(() => {
      });
    }

    openDialogScreenD() {
      const vm = this;

      vm.$window.storage('LABOR_COST_TIME_DETAILS', vm.laborCostTimeDetails()).then(() => {
        vm.$window.modal('/view/kml/002/d/index.xhtml').then(() => {
          vm.$window.storage('LABOR_COST_TIME_DETAILS').then((data) => {
            if (!_.isNil(data)) vm.laborCostTimeDetails(data);
          });
        });
      });
    }

    openDialogScreenG() {
      const vm = this;

      vm.$window.storage('KWL002_SCREEN_G_INPUT', vm.countingNumberTimesDetails()).then(() => {
        vm.$window.modal('/view/kml/002/g/index.xhtml').then(() => {
          vm.$window.storage('KWL002_SCREEN_G_OUTPUT').then((data) => {
            if (!_.isNil(data)) vm.countingNumberTimesDetails(data);
          });
        });
      });
    }

    openDialogScreenE() {
      const vm = this;

      vm.$window.storage('TIME_ZONE_NUMBER_PEOPLE_DETAILS', vm.timeZoneNumberPeopleDetails()).then(() => {
        vm.$window.modal('/view/kml/002/e/index.xhtml').then(() => {
          vm.$window.storage('TIME_ZONE_NUMBER_PEOPLE_DETAILS').then((data) => {
            if (!_.isNil(data)) vm.timeZoneNumberPeopleDetails(data);
          });
        });
      });
    }

    //スケジュール職場計情報を登録する時
    registerScheduleRosterInfor() {
      const vm = this;

      //Msg_1850
      if (_.isNil(vm.laborCostTimeDetails())
        || _.isNil(vm.countingNumberTimesDetails())
        || _.isNil(vm.timeZoneNumberPeopleDetails())
      ) {
        vm.$dialog.error({ messageId: 'Msg_1850', message: [] });
      }
    }
  }

  export enum UsageClassification {
    NotUse = 0,
    Use = 1
  }
}