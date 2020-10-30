/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kml002.d {

  @bean()
  class ViewModel extends ko.ViewModel {

    d31totalUsage: KnockoutObservable<number> = ko.observable(UsageClassification.Use);
    d31TimeReference: KnockoutObservable<number> = ko.observable(UsageClassification.Use);
    d31LaborCosts: KnockoutObservable<number> = ko.observable(UsageClassification.Use);
    d31Budget: KnockoutObservable<number> = ko.observable(UsageClassification.Use);

    d41WorkingHours: KnockoutObservable<number> = ko.observable(UsageClassification.Use);
    d41TimeReference: KnockoutObservable<number> = ko.observable(UsageClassification.Use);
    d41LaborCosts: KnockoutObservable<number> = ko.observable(UsageClassification.Use);

    d51Overtime: KnockoutObservable<number> = ko.observable(UsageClassification.Use);
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

      vm.$window.storage('LABOR_COST_TIME_DETAILS').then((data) => {
        if (!_.isNil(data)) {
          vm.d31totalUsage(data.d31totalUsage);
          vm.d31TimeReference(data.d31TimeReference);
          vm.d31LaborCosts(data.d31LaborCosts);
          vm.d31Budget(data.d31Budget);
          vm.d41WorkingHours(data.d41WorkingHours);
          vm.d41TimeReference(data.d41TimeReference);
          vm.d41LaborCosts(data.d41LaborCosts);
          vm.d51Overtime(data.d51Overtime);
          vm.d51TimeReference(data.d51TimeReference);
          vm.d51LaborCosts(data.d51LaborCosts);
        }
      });

    }

    created(params: any) {
      const vm = this;
      _.extend(window, { vm });
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
        && vm.d41LaborCosts() === UsageClassification.NotUse ) {
        vm.$dialog.error({ messageId: 'Msg_1953' }).then(() => {
          $('#D42').focus();
        });
        return;
      }

      if (vm.d51Overtime()
        && vm.d51TimeReference() === UsageClassification.NotUse
        && vm.d51LaborCosts() === UsageClassification.NotUse ) {
        vm.$dialog.error({ messageId: 'Msg_1953' }).then(() => {
          $('#D52').focus();
        });
        return;
      }

      let params = {
        d31totalUsage: vm.d31totalUsage(),
        d31TimeReference: vm.d31TimeReference(),
        d31LaborCosts: vm.d31LaborCosts(),
        d31Budget: vm.d31Budget(),
        d41WorkingHours: vm.d41WorkingHours(),
        d41TimeReference: vm.d41TimeReference(),
        d41LaborCosts: vm.d41LaborCosts(),
        d51Overtime: vm.d51Overtime(),
        d51TimeReference: vm.d51TimeReference(),
        d51LaborCosts: vm.d51LaborCosts(),
      };

      vm.$window.storage('LABOR_COST_TIME_DETAILS', params).then(() => {
        vm.$dialog.error({ messageId: 'Msg_15' }).then(() => {
          vm.$window.close();
        });
      })
    }
  }

  export enum UsageClassification {
    NotUse = 0,
    Use = 1
  }
}