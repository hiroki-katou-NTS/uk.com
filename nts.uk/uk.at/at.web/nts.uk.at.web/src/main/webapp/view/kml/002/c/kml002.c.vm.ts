/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kml002.c {
  const PATH = {
    personalCounterGetById: 'ctx/at/schedule/budget/personalCounter/getById',
    personalCounterRegister: 'ctx/at/schedule/budget/workplaceCounter/register'
  }

  @bean()
  class ViewModel extends ko.ViewModel {

    estimatedMonthlySalary: KnockoutObservable<number> = ko.observable(Usage.Use);
    estimatedAnnualSalary: KnockoutObservable<number> = ko.observable(Usage.Use);
    comparisonStandardWorkingHours: KnockoutObservable<number> = ko.observable(Usage.Use);
    workingTime: KnockoutObservable<number> = ko.observable(Usage.Use);
    nightShiftTime: KnockoutObservable<number> = ko.observable(Usage.Use);
    weeklyHolidayDays: KnockoutObservable<number> = ko.observable(Usage.Use);
    attendanceHolidayDays: KnockoutObservable<number> = ko.observable(Usage.Use);

    count1: KnockoutObservable<number> = ko.observable(Usage.Use);
    count1Details: KnockoutObservableArray<any> = ko.observableArray([]);//回数集計 1 
    count2: KnockoutObservable<number> = ko.observable(Usage.Use);
    count2Details: KnockoutObservableArray<any> = ko.observableArray([]);//回数集計	2	
    count3: KnockoutObservable<number> = ko.observable(Usage.Use);
    count3Details: KnockoutObservableArray<any> = ko.observableArray([]);//回数集計	3	

    switchOptions: KnockoutObservableArray<any> = ko.observableArray([]);
    constructor(params: any) {
      super();
      const vm = this;

      vm.switchOptions = ko.observableArray([
        { code: Usage.Use, name: vm.$i18n('KML002_20') },
        { code: Usage.NotUse, name: vm.$i18n('KML002_21') }
      ]);
      vm.personalCounterGetById();
    }

    created(params: any) {
      const vm = this;
      //_.extend(window, { vm });
    }

    mounted() {
      const vm = this;

      $('#C322').focus();
    }

    openDialogScreenG(count: number) {
      const vm = this;

      let params = vm.count1Details();

      switch (count) {
        case 1:
          params = vm.count1Details();
          break;
        case 2:
          params = vm.count2Details();
          break;
        case 3:
          params = vm.count3Details();
          break;
      }

      vm.$window.storage('KWL002_SCREEN_G_INPUT', params).then(() => {
        vm.$window.modal('/view/kml/002/g/index.xhtml').then(() => {
          vm.$window.storage('KWL002_SCREEN_G_OUTPUT').then((data) => {
            if (!_.isNil(data)) {
              switch (count) {
                case 1:
                  vm.count1Details(data);
                  break;
                case 2:
                  vm.count2Details(data);
                  break;
                case 3:
                  vm.count3Details(data);
                  break;
              }
            }
          });
        });
      });
    }

    //スケジュール職場計情報を登録する時
    registerSchedulePersonalInfor() {
      const vm = this;

      vm.personalCounterRegister();

      //スケジュール職場計情報を登録する時
      //Workplace Total Categor
      /* ・「人件費・時間」の利用区分＝＝利用するが「人件費・時間」の詳細設定はまだ設定られない。
      ・「回数集計」の利用区分＝＝利用するが「回数集計」の詳細設定はまだ設定られない。
      ・「時間帯人数」の利用区分＝＝利用するが「時間帯人数」の詳細設定はまだ設定られない。
      */

      if ((vm.count1() === Usage.Use && vm.count1Details().length === 0)
        && (vm.count2() === Usage.Use && vm.count2Details().length === 0)
        && (vm.count3() === Usage.Use && vm.count3Details().length === 0)) {
        let errorParams = [];
        errorParams.push(vm.$i18n('KML002_119') + vm.$i18n('KML002_69'));
        errorParams.push(vm.$i18n('KML002_119') + vm.$i18n('KML002_72'));
        errorParams.push(vm.$i18n('KML002_119') + vm.$i18n('KML002_75'));

        vm.$dialog.error({
          messageId: 'Msg_1850',
          messageParams: errorParams
        }).then(() => {
          $('#btnRegister').focus();
        });
        return;
      }

      if ((vm.count1() === Usage.Use && vm.count1Details().length > 0)
        && (vm.count2() === Usage.Use && vm.count2Details().length === 0)
        && (vm.count3() === Usage.Use && vm.count3Details().length === 0)) {
        let errorParams = [];
        errorParams.push(vm.$i18n('KML002_119') + vm.$i18n('KML002_72'));
        errorParams.push(vm.$i18n('KML002_119') + vm.$i18n('KML002_75'));
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
    personalCounterGetById() {
      const vm = this;

      vm.$ajax(PATH.personalCounterGetById).done((data) => {
        console.log(data);
      })
        .fail()
        .always();
    }

    /**
     * 
     */
    personalCounterRegister() {
      const vm = this;

      let params = { personalCategory: [0,1,2,3,5,6,7] };
      vm.$ajax(PATH.personalCounterRegister, params).done((data) => {
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