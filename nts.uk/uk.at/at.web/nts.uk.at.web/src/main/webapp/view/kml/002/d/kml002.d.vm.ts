/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kml002.d {
  const PATH = {
    wkpLaborCostAndTimeGetById: 'ctx/at/schedule/budget/wkpLaborCostAndTime/getById',
    wkpLaborCostAndTimeRegister: 'ctx/at/schedule/budget/wkpLaborCostAndTime/register'
  }

  @bean()
  class ViewModel extends ko.ViewModel {

    d31totalUsage: KnockoutObservable<number> = ko.observable(UsageClassification.NotUse);
    d31TimeReference: KnockoutObservable<number> = ko.observable(UsageClassification.Use);
    d31LaborCosts: KnockoutObservable<number> = ko.observable(UsageClassification.Use);
    d31Budget: KnockoutObservable<number> = ko.observable(UsageClassification.Use);

    d41WorkingHours: KnockoutObservable<number> = ko.observable(UsageClassification.NotUse);
    d41TimeReference: KnockoutObservable<number> = ko.observable(UsageClassification.Use);
    d41LaborCosts: KnockoutObservable<number> = ko.observable(UsageClassification.Use);

    d51Overtime: KnockoutObservable<number> = ko.observable(UsageClassification.NotUse);
    d51TimeReference: KnockoutObservable<number> = ko.observable(UsageClassification.Use);
    d51LaborCosts: KnockoutObservable<number> = ko.observable(UsageClassification.Use);

    switchOptions: KnockoutObservableArray<any> = ko.observableArray([]);
    laborCostTimeDetails: KnockoutObservableArray<any> = ko.observableArray([]);

    constructor(params: any) {
      super();
      const vm = this;

      vm.switchOptions = ko.observableArray([
        { code: UsageClassification.Use, name: vm.$i18n('KML002_20') },
        { code: UsageClassification.NotUse, name: vm.$i18n('KML002_21') },
      ]);

      //表示したい項目を設定してください。
      vm.wkpLaborCostAndTimeGetById();
    }

    created(params: any) {
      const vm = this;
      //_.extend(window, { vm });
    }

    mounted() {
      const vm = this;

      $('#D32').focus();
    }

    closeDialog() {
      const vm = this;
      vm.$window.close();
    }

    proceed() {
      const vm = this;

      //以下のうち1つ以上が「利用する」であること。
      if (vm.d31totalUsage() === UsageClassification.NotUse
        && vm.d41WorkingHours() === UsageClassification.NotUse
        && vm.d51Overtime() === UsageClassification.NotUse) {
        vm.$dialog.error({ messageId: 'Msg_1836' }).then(() => {
          $('#D32').focus();
        });
        return;
      }

      //メイン項目の設定は「利用する」ですけど全部サブ項目の設定は「利用しない」です。
      if (vm.d31totalUsage()
        && vm.d31TimeReference() === UsageClassification.NotUse
        && vm.d31LaborCosts() === UsageClassification.NotUse
        && vm.d31Budget() === UsageClassification.NotUse) {
        vm.$dialog.error({ messageId: 'Msg_1953' }).then(() => {
          $('#D32').focus();
        });
        return;
      }

      if (vm.d41WorkingHours()
        && vm.d41TimeReference() === UsageClassification.NotUse
        && vm.d41LaborCosts() === UsageClassification.NotUse) {
        vm.$dialog.error({ messageId: 'Msg_1953' }).then(() => {
          $('#D42').focus();
        });
        return;
      }

      if (vm.d51Overtime()
        && vm.d51TimeReference() === UsageClassification.NotUse
        && vm.d51LaborCosts() === UsageClassification.NotUse) {
        vm.$dialog.error({ messageId: 'Msg_1953' }).then(() => {
          $('#D52').focus();
        });
        return;
      }

      //表示したい項目を設定してください。
      vm.wkpLaborCostAndTimeRegister();
    }

    /**
     * 
     */
    wkpLaborCostAndTimeGetById() {
      const vm = this;
      vm.$blockui('show');

      vm.$ajax(PATH.wkpLaborCostAndTimeGetById).done((data) => {
        if (!_.isNil(data)) {
          //sort asc
          let laborCostTimeBugget: any = _.orderBy(data, 'laborCostAndTimeType', 'asc');

          //合計
          if (!_.isNil(laborCostTimeBugget[0])) {
            let total = laborCostTimeBugget[0].laborCostAndTimeDtos;
            if (!_.isNil(total.useClassification)) vm.d31totalUsage(total.useClassification);
            if (!_.isNil(total.time)) vm.d31TimeReference(total.time);
            if (!_.isNil(total.laborCost)) vm.d31LaborCosts(total.laborCost);
            if (!_.isNil(total.budget)) vm.d31Budget(total.budget);
          }
          //就業時間
          if (!_.isNil(laborCostTimeBugget[1])) {
            let wkhours = laborCostTimeBugget[1].laborCostAndTimeDtos;
            if (!_.isNil(wkhours.useClassification)) vm.d41WorkingHours(wkhours.useClassification);
            if (!_.isNil(wkhours.time)) vm.d41TimeReference(wkhours.time);
            if (!_.isNil(wkhours.laborCost)) vm.d41LaborCosts(wkhours.laborCost);
          }
          //時間外時間
          if (!_.isNil(laborCostTimeBugget[2])) {
            let overtime = laborCostTimeBugget[2].laborCostAndTimeDtos;
            if (!_.isNil(overtime.useClassification)) vm.d51Overtime(overtime.useClassification);
            if (!_.isNil(overtime.time)) vm.d51TimeReference(overtime.time);
            if (!_.isNil(overtime.laborCost)) vm.d51LaborCosts(overtime.laborCost);
          }
        }
        vm.$blockui('hide');
      })
        .fail((err) => {})
        .always(() => vm.$blockui('hide'));
    }

    /**
     * 
     */
    wkpLaborCostAndTimeRegister() {
      const vm = this;

      vm.$blockui('show');

      let params = [
        {
          LaborCostAndTimeType: 0,
          useClassification: vm.d31totalUsage(),
          time: vm.d31TimeReference(),
          laborCost: vm.d31LaborCosts(),
          budget: vm.d31Budget(),
        },
        {
          LaborCostAndTimeType: 1,
          useClassification: vm.d41WorkingHours(),
          time: vm.d41TimeReference(),
          laborCost: vm.d41LaborCosts(),
          budget: null,
        },
        {
          LaborCostAndTimeType: 2,
          useClassification: vm.d51Overtime(),
          time: vm.d51TimeReference(),
          laborCost: vm.d51LaborCosts(),
          budget: null,
        },
      ];

      vm.$ajax(PATH.wkpLaborCostAndTimeRegister, { laborCostAndTimes: params }).done((data) => {
        vm.$dialog.info({ messageId: 'Msg_15' }).then(() => {
          vm.$blockui('hide');
          vm.$window.close();
        });
      })
        .fail((error) => { })
        .always(() => vm.$blockui('hide'));
    }

  }

  export enum UsageClassification {
    NotUse = 0,
    Use = 1
  }
}