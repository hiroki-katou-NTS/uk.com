/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kml002.b {

  const PATH = {
    workplaceCounterGetById: 'ctx/at/schedule/budget/workplaceCounter/getById',
    workplaceCounterRegister: 'ctx/at/schedule/budget/workplaceCounter/register'
  }

  @bean()
  class ViewModel extends ko.ViewModel {

    laborCostTime: KnockoutObservable<number> = ko.observable(Usage.Use);
    laborCostTimeDetails: KnockoutObservable<any> = ko.observable([]);//人件費・時間	
    countingNumberTimes: KnockoutObservable<number> = ko.observable(Usage.Use);
    countingNumberTimesDetails: KnockoutObservableArray<any> = ko.observableArray([]);//回数集計		
    timeZoneNumberPeople: KnockoutObservable<number> = ko.observable(Usage.Use);
    timeZoneNumberPeopleDetails: KnockoutObservableArray<any> = ko.observableArray([]); //時間帯人数			

    externalBudgetResults: KnockoutObservable<number> = ko.observable(Usage.Use);
    numberPassengersWorkingHours: KnockoutObservable<number> = ko.observable(Usage.Use);
    numberOfEmployees: KnockoutObservable<number> = ko.observable(Usage.Use);
    numberOfPeople: KnockoutObservable<number> = ko.observable(Usage.Use);
    numberOfPositions: KnockoutObservable<number> = ko.observable(Usage.Use);

    switchOptions: KnockoutObservableArray<any> = ko.observableArray([]);

    constructor(params: any) {
      super();
      const vm = this;

      vm.switchOptions = ko.observableArray([
        { code: Usage.Use, name: vm.$i18n('KML002_20') },
        { code: Usage.NotUse, name: vm.$i18n('KML002_21') }
      ]);

      vm.workplaceCounterGetById();
    }

    created(params: any) {
      const vm = this;
      //_.extend(window, { vm });
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

      //スケジュール職場計情報を登録する時
      //Workplace Total Categor
      /* ・「人件費・時間」の利用区分＝＝利用するが「人件費・時間」の詳細設定はまだ設定られない。
      ・「回数集計」の利用区分＝＝利用するが「回数集計」の詳細設定はまだ設定られない。
      ・「時間帯人数」の利用区分＝＝利用するが「時間帯人数」の詳細設定はまだ設定られない。
      */

      if ((vm.laborCostTime() === Usage.Use && vm.laborCostTimeDetails().length === 0)
        && (vm.countingNumberTimes() === Usage.Use && vm.countingNumberTimesDetails().length === 0)
        && (vm.timeZoneNumberPeople() === Usage.Use && vm.timeZoneNumberPeopleDetails().length === 0)
      ) {
        let errorParams = [];
        errorParams.push(vm.$i18n('KML002_119') + vm.$i18n('KML002_18'));
        errorParams.push(vm.$i18n('KML002_119') + vm.$i18n('KML002_27'));
        errorParams.push(vm.$i18n('KML002_119') + vm.$i18n('KML002_33'));

        vm.$dialog.error({
          messageId: 'Msg_1850',
          messageParams: errorParams
        }).then(() => {
          $('#btnRegister').focus();
        });
        return;
      }

      if ((vm.laborCostTime() === Usage.Use && vm.laborCostTimeDetails().length === 0)
        && (vm.countingNumberTimes() === Usage.Use && vm.countingNumberTimesDetails().length > 0)
        && (vm.timeZoneNumberPeople() === Usage.Use && vm.timeZoneNumberPeopleDetails().length === 0)
      ) {
        let errorParams = [];
        errorParams.push(vm.$i18n('KML002_119') + vm.$i18n('KML002_18'));
        errorParams.push(vm.$i18n('KML002_119') + vm.$i18n('KML002_33'));
        errorParams.push('');

        vm.$dialog.error({
          messageId: 'Msg_1850',
          messageParams: errorParams
        }).then(() => {
          $('#btnRegister').focus();
        });
        return;
      }

      vm.$dialog.error({ messageId: 'Msg_15' }).then(() => {
        $('#btnRegister').focus();
      });
    }

    /**
     * 
     */
    workplaceCounterGetById() {
      const vm = this;

      vm.$ajax(PATH.workplaceCounterGetById).done((data) => {
        console.log(data);
      })
        .fail()
        .always();
    }

    /**
     * 
     */
    workplaceCounterRegister() {
      const vm = this;

      let params = [
        { workplaceCategory: 1 },
        { workplaceCategory: 1 },
        { workplaceCategory: 1 },
        { workplaceCategory: 1 },
        { workplaceCategory: 1 },
        { workplaceCategory: 1 },
      ]
      vm.$ajax(PATH.workplaceCounterRegister).done((data) => {
        console.log(data);
      })
        .fail()
        .always();
    }
  }

  export enum Usage {
    NotUse = 0,
    Use = 1
  }
}