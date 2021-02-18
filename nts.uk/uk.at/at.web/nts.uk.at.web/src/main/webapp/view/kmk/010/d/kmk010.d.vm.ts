/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kmk010.d {

  const PATH = {
    findAllOvertimeUnit: "ctx/at/shared/outsideot/setting/findAll/unit",
    findAllOvertimeRounding: "ctx/at/shared/outsideot/setting/findAll/rounding",
    findAllPremiumExtra60H: "ctx/at/shared/outsideot/setting/findAllPremiumExtra60H",
    saveAllPremiumExtra60H: 'ctx/at/shared/outsideot/setting/save/allPremiumExtra60H'
  }

  @bean()
  class ViewModel extends ko.ViewModel {

    breakdownItems: KnockoutObservableArray<any> = ko.observableArray([]);
    overTimeHeader: KnockoutObservableArray<any> = ko.observableArray([]);
    columnSpan: KnockoutObservable<number> = ko.observable(0);
    lstUnit: KnockoutObservableArray<IEnumConstantDto> = ko.observableArray([]);
    lstRounding: KnockoutObservableArray<IEnumConstantDto> = ko.observableArray([]);
    lstRoundingBk: KnockoutObservableArray<IEnumConstantDto> = ko.observableArray([]);
    vacationConversion: KnockoutObservable<VacationConversionDto>;

    constructor(params: any) {
      super();
      const vm = this;

      vm.vacationConversion = ko.observable(new VacationConversionDto([], 0, 0, 0));

      vm.getEnumConstant().done(() => {
        vm.getVacationConversionList();

        vm.vacationConversion().unit.subscribe((newValue) => {
          if (newValue !== 4 && newValue !== 6) {
            vm.lstRounding(_.filter(vm.lstRounding(), (item) => item.value !== 2));
          } else {
            vm.lstRounding(_.cloneDeep(vm.lstRoundingBk()));
          }
        });
      });
    }

    created(params: any) {
      const vm = this;
    }

    mounted() {
      const vm = this;
      vm.focusControl();
    }

    focusControl() {
      $('.premium-rate').each(function (index) {
        $(this).attr('id', 'PremiumRate-' + index);
      });

      $('#PremiumRate-0').focus();
    }

    saveData() {
      const vm = this;

      let outputPremiumRate = [];
      _.forEach(vm.vacationConversion().conversionRate(), (x: any, row) => {
        _.forEach(x.premiumExtra60HRates(), (o: any) => {
          outputPremiumRate.push({
            premiumRate: parseInt(o.premiumRate()),
            breakdownItemNo: x.breakdownItemNo,
            overtimeNo: o.overtimeNo
          });
        });
      });

      let params = {
        roundingTime: vm.vacationConversion().unit(),
        rounding: vm.vacationConversion().rounding(),
        superHolidayOccurrenceUnit: vm.vacationConversion().unitForVacation(),
        premiumExtra60HRates: outputPremiumRate
      }

      vm.$blockui('grayout');
      vm.$ajax(PATH.saveAllPremiumExtra60H, params).done(() => {
        vm.$dialog.info({ messageId: 'Msg_15' }).then(() => {
          vm.$window.close({ isSave: true });
          vm.$blockui('hide');
        });
      }).fail((error) => { 
        vm.$dialog.error({ messageId: error.messageId }). then(() => {
          vm.$blockui('hide'); 
        });        
      });
    }

    closeDialog() {
      const vm = this;
      vm.$window.close({ isSave: false });
    }

    getEnumConstant(): JQueryPromise<any> {
      const vm = this;
      const def = $.Deferred();
      // find all unit
      vm.$ajax(PATH.findAllOvertimeUnit).done((data) => {
        vm.lstUnit(data);
      });

      // find all rounding
      vm.$ajax(PATH.findAllOvertimeRounding).done((data) => {
        vm.lstRounding(data);
        vm.lstRoundingBk(data);
      });

      def.resolve();
      return def.promise();
    }

    getVacationConversionList() {
      const vm = this;

      vm.breakdownItems.removeAll();
      vm.$blockui('grayout');

      vm.$ajax(PATH.findAllPremiumExtra60H).done((data) => {

        if (data.overtimes.length > 0) {
          //60H超休が発生する＝trueの超過時間のみ表示                    
          data.overtimes = _.filter(data.overtimes, (e: any) => { return e.superHoliday60HOccurs === true });
          vm.overTimeHeader(_.orderBy(data.overtimes, 'overtimeNo', 'asc'));
          vm.columnSpan(vm.overTimeHeader().length);
        }

        if (data.breakdownItems.length > 0) {
          //使用区分＝trueの内訳項目のみ表示        
          //data.breakdownItems = _.filter(data.breakdownItems, (e: any) => { return e.useClassification === true });
          _.forEach(data.breakdownItems, (item, row: number) => {
            let premiumExtra60HRates: Array<PremiumItem> = [];
            if (item.premiumExtra60HRates.length === 0) {
              _.forEach(vm.overTimeHeader(), (x, index) => {
                premiumExtra60HRates.push(new PremiumItem(item.breakdownItemNo, 0, x.overtimeNo));
              })
            } else {
              _.forEach(vm.overTimeHeader(), (col: any, index) => {
                const findPremiumItem = _.find(item.premiumExtra60HRates, (o: any) => { return o.overtimeNo === col.overtimeNo });
                const premiumRate: number = (findPremiumItem) ? findPremiumItem.premiumRate : 0;
                premiumExtra60HRates.push(new PremiumItem(item.breakdownItemNo, premiumRate, col.overtimeNo));
              });
            }

            let breakdownItem: BreakdownItem = new BreakdownItem(
              item.name,
              _.orderBy(premiumExtra60HRates, 'overtimeNo', 'asc'),
              item.breakdownItemNo
            );
            vm.breakdownItems.push(breakdownItem);
          });
        }

        //creat model object
        vm.vacationConversion().update(
          vm.breakdownItems(),
          data.roundingTime,
          data.rounding,
          data.superHolidayOccurrenceUnit,
        );

        if (data.roundingTime === 4 || data.roundingTime === 6) {
          vm.vacationConversion().unit.valueHasMutated();
        }

        vm.focusControl();
        vm.$blockui('hide');

      })
      .always(() => vm.$blockui('hide') )
      .fail((error) => { 
        vm.$dialog.error({ messageId: error.messageId }).then(() => {
          vm.$blockui('hide'); 
        });        
      });
    }
  }

  //休暇換算の設定
  export class VacationConversionDto {
    //休暇発生時の換算率の設定
    conversionRate: KnockoutObservableArray<BreakdownItem> = ko.observableArray([]);
    //休暇発生時の換算方法
    unit: KnockoutObservable<number> = ko.observable(0);
    rounding: KnockoutObservable<number> = ko.observable(0);
    unitForVacation: KnockoutObservable<number> = ko.observable(0);
    constructor(conversionRate: Array<BreakdownItem>, unit: number, rounding: number, unitForVacation: number) {
      this.conversionRate(conversionRate);
      this.unit(unit);
      this.rounding(rounding);
      this.unitForVacation(unitForVacation);
    }

    update(conversionRate: Array<BreakdownItem>, unit: number, rounding: number, unitForVacation: number) {
      this.conversionRate(conversionRate);
      this.unit(unit);
      this.rounding(rounding);
      this.unitForVacation(unitForVacation);
    }
  }

  export class BreakdownItem {
    name: string;
    premiumExtra60HRates: KnockoutObservableArray<PremiumItem> = ko.observableArray([]);
    breakdownItemNo: number;
    
    constructor(name: string, rate: Array<PremiumItem>, itemNo?: number) {
      this.name = name;
      this.premiumExtra60HRates(rate);
      this.breakdownItemNo = itemNo;
    }
  }

  export interface IEnumConstantDto {
    value: number;
    fieldName: string;
    localizedName: string;
  }

  export class PremiumItem {
    breakdownItemNo: number;
    premiumRate: KnockoutObservable<number> = ko.observable(0);
    overtimeNo: number;
    constructor(ItemNo: number, rate: number, order: number) {
      this.breakdownItemNo = ItemNo;
      this.premiumRate(rate);
      this.overtimeNo = order;
    }
  }
}