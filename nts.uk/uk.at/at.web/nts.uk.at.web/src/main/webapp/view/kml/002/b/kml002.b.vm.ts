/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kml002.b {

  const PATH = {
    workplaceCounterGetById: 'ctx/at/schedule/budget/workplaceCounter/getById',
    workplaceCounterRegister: 'ctx/at/schedule/budget/workplaceCounter/register',
    getLaborCostTimeDetails: 'ctx/at/schedule/budget/wkpLaborCostAndTime/getById', //screen D
    getTimeZonDetails: 'ctx/at/schedule/budget/wkpTimeZone/getById', //scree E
    getNumberCounterDetails: 'screen/at/kml002/g/getInfo', //screen G
  }

  @bean()
  class ViewModel extends ko.ViewModel {

    laborCostTime: KnockoutObservable<number> = ko.observable(Usage.Use);
    laborCostTimeDetails: KnockoutObservable<any> = ko.observable(null);//人件費・時間	
    countingNumberTimes: KnockoutObservable<number> = ko.observable(Usage.Use);
    countingNumberTimesDetails: KnockoutObservableArray<any> = ko.observableArray([]);//回数集計		
    timeZoneNumberPeople: KnockoutObservable<number> = ko.observable(Usage.Use);
    timeZoneNumberPeopleDetails: KnockoutObservableArray<any> = ko.observableArray([]); //時間帯人数			

    externalBudgetResults: KnockoutObservable<number> = ko.observable(Usage.Use);
    numberPassengersWorkingHours: KnockoutObservable<number> = ko.observable(Usage.Use);
    numberOfEmployees: KnockoutObservable<number> = ko.observable(Usage.Use);
    numberOfPeopleClassified: KnockoutObservable<number> = ko.observable(Usage.Use);
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
      vm.$window.modal('/view/kml/002/d/index.xhtml').then(() => {
        vm.$window.storage('LABOR_COST_TIME_DETAILS').then((data) => {
          if (!_.isNil(data)) vm.laborCostTimeDetails(data);
        });
      });
    }

    openDialogScreenG() {
      const vm = this;
      vm.$window.storage('KWL002_SCREEN_G_INPUT', { countingType: 0 }).then(() => {
        vm.$window.modal('/view/kml/002/g/index.xhtml').then(() => {
          vm.$window.storage('KWL002_SCREEN_G_OUTPUT').then((data) => {
            if (!_.isNil(data)) vm.countingNumberTimesDetails(data);
          });
        });
      });
    }

    openDialogScreenE() {
      const vm = this;
      vm.$window.modal('/view/kml/002/e/index.xhtml').then(() => {
        vm.$window.storage('TIME_ZONE_NUMBER_PEOPLE_DETAILS').then((data) => {
          if (!_.isNil(data)) vm.timeZoneNumberPeopleDetails(data);
        });
      });
    }

    //スケジュール職場計情報を登録する時
    registerScheduleRosterInfor() {
      const vm = this;

      //スケジュール職場計情報を登録する時

      /* 
      ・「人件費・時間」の利用区分＝＝利用するが「人件費・時間」の詳細設定はまだ設定られない。
      ・「回数集計」の利用区分＝＝利用するが「回数集計」の詳細設定はまだ設定られない。
      ・「時間帯人数」の利用区分＝＝利用するが「時間帯人数」の詳細設定はまだ設定られない。
      */

      if ((vm.laborCostTime() === Usage.Use && vm.laborCostTimeDetails().length === 0)
        || (vm.countingNumberTimes() === Usage.Use && vm.countingNumberTimesDetails().length === 0)
        || (vm.timeZoneNumberPeople() === Usage.Use && vm.timeZoneNumberPeopleDetails().length === 0)
      ) {
        let errorParams = [];
        
        if (vm.laborCostTimeDetails().length === 0)
          errorParams.push(vm.$i18n('KML002_119') + vm.$i18n('KML002_18'));
        else errorParams.push('');

        if (vm.countingNumberTimesDetails().length === 0)
          errorParams.push(vm.$i18n('KML002_119') + vm.$i18n('KML002_27'));
        else errorParams.push('');

        if (vm.timeZoneNumberPeopleDetails().length === 0)
          errorParams.push(vm.$i18n('KML002_119') + vm.$i18n('KML002_33'));
        else errorParams.push('');

        vm.$dialog.error({
          messageId: 'Msg_1850',
          messageParams: errorParams
        }).then(() => {
          $('#btnRegister').focus();
        });
        return;
      }

      /* 
      ・「人件費・時間」の利用区分＝＝利用するが「人件費・時間」の詳細設定はまだ設定られた。
      ・「回数集計」の利用区分＝＝利用するが「回数集計」の詳細設定はまだ設定られた。
      ・「時間帯人数」の利用区分＝＝利用するが「時間帯人数」の詳細設定はまだ設定られた。 */

      vm.workplaceCounterRegister();

    }

    /**
     * 
     */
    workplaceCounterGetById() {
      const vm = this;
      vm.$blockui('show');
      vm.$ajax(PATH.workplaceCounterGetById).done((data) => {
        vm.fillDataToGrid(data);
        vm.$blockui('hide');
      })
        .fail(() => vm.$blockui('hide'))
        .always(() => vm.$blockui('hide'));

      vm.getLaborCostTimeDetails();
      vm.getWorkplaceTimeZoneById();
      vm.getNumberCounterDetails();
    }

    /**
     * 
     */
    workplaceCounterRegister() {
      const vm = this;
      vm.$blockui('show');
      let wpCategory = vm.createParamsToSave();
      let params = { workplaceCategory: wpCategory }
      vm.$ajax(PATH.workplaceCounterRegister, params).done((data) => {
        vm.$dialog.info({ messageId: 'Msg_15' }).then(() => {
          vm.$blockui('hide');
          $('#B322').focus();
        });
      })
        .fail()
        .always(() => vm.$blockui('show'));
    }

    /**
     * Gets labor cost time details
     */
    getLaborCostTimeDetails() {
      const vm = this;
      vm.$ajax(PATH.getLaborCostTimeDetails).done((data) => {
        if (!_.isNil(data)) {
          vm.laborCostTimeDetails(data);
        } else
          vm.laborCostTimeDetails(null);
      });
    }
    /**
     * Gets workplace time zone by id
     */
    getWorkplaceTimeZoneById() {
      const vm = this;
      vm.$ajax(PATH.getTimeZonDetails).done((data) => {
        if (!_.isNil(data)) {
          vm.timeZoneNumberPeopleDetails(data);
        } else
          vm.timeZoneNumberPeopleDetails(null);
      }).fail().always();
    }

    /**
     * Gets number counter details
     */
    getNumberCounterDetails() {
      const vm = this;
      vm.$ajax(PATH.getNumberCounterDetails, { countType: 0 }).done((data) => {
        if (!_.isNil(data) && data.numberOfTimeTotalDtos.length > 0) {
          vm.countingNumberTimesDetails(data.numberOfTimeTotalDtos);
        }
      }).fail().always();
    }

    fillDataToGrid(data: any) {
      const vm = this;

      if (!_.isNil(data) && data.length > 0) {
        //人件費・時間
        vm.laborCostTime(data[0].use ? Usage.Use : Usage.NotUse);
        //外部予算実績
        vm.externalBudgetResults(data[1].use ? Usage.Use : Usage.NotUse);
        //回数集計
        vm.countingNumberTimes(data[2].use ? Usage.Use : Usage.NotUse);
        //就業時間帯別の利用人数
        vm.numberPassengersWorkingHours(data[3].use ? Usage.Use : Usage.NotUse);
        //時間帯人数
        vm.timeZoneNumberPeople(data[4].use ? Usage.Use : Usage.NotUse);
        //雇用人数
        vm.numberOfEmployees(data[5].use ? Usage.Use : Usage.NotUse);
        //分類人数        
        vm.numberOfPeopleClassified(data[6].use ? Usage.Use : Usage.NotUse);
        //職位人数
        vm.numberOfPositions(data[7].use ? Usage.Use : Usage.NotUse);
      }
    }

    createParamsToSave() {
      const vm = this;

      let wpCategory: any = [];
      //人件費・時間
      if (vm.laborCostTime() === Usage.Use) wpCategory.push(0);
      //外部予算実績
      if (vm.externalBudgetResults() === Usage.Use) wpCategory.push(1);
      //回数集計
      if (vm.countingNumberTimes() === Usage.Use) wpCategory.push(2);
      //就業時間帯別の利用人数
      if (vm.numberPassengersWorkingHours() === Usage.Use) wpCategory.push(3);
      //時間帯人数
      if (vm.timeZoneNumberPeople() === Usage.Use) wpCategory.push(4);
      //雇用人数
      if (vm.numberOfPeopleClassified() === Usage.Use) wpCategory.push(5);
      //分類人数
      if (vm.numberOfEmployees() === Usage.Use) wpCategory.push(6);
      //職位人数
      if (vm.numberOfPositions() === Usage.Use) wpCategory.push(7);

      return wpCategory;
    }
  }

  export enum Usage {
    NotUse = 0,
    Use = 1
  }
}