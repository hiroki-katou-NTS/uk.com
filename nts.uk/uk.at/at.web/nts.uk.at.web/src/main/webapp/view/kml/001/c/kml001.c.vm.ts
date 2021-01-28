module nts.uk.at.view.kml001.c {
  export module viewmodel {
    import servicebase = kml001.shr.servicebase;
    import vmbase = kml001.shr.vmbase;
    export class ScreenModel extends ko.ViewModel {
      copyDataFlag: KnockoutObservable<boolean>;
      lastStartDate: KnockoutObservable<string>;
      beginStartDate: KnockoutObservable<string>;
      newStartDate: KnockoutObservable<string>;
      size: KnockoutObservable<number>;
      textKML001_47: KnockoutObservable<string>;
      latestHistory: KnockoutObservable<any> = ko.observable(null);
      currentPersonCost: KnockoutObservable<any> = ko.observable(null);

      constructor() {
        super();
        const self = this;
        self.latestHistory(nts.uk.ui.windows.getShared('PERSONAL_HISTORY'));     
        self.copyDataFlag = ko.observable(true);
        self.lastStartDate = ko.observable(self.latestHistory().latestHistory.startDate);        
        self.newStartDate = ko.observable(null);
        self.beginStartDate = ko.observable(vmbase.ProcessHandler.getOneDayAfter(self.lastStartDate()));        
        self.size = ko.observable(self.latestHistory().size);        
        self.textKML001_47 = ko.observable(nts.uk.resource.getText('KML001_47', [self.lastStartDate()]));            
      }

      /**
       * process parameter and close dialog 
       */
      submitAndCloseDialog(): void {
        const self = this;
        self.createClonePersonalCost();
      }

      /**
       * close dialog and do nothing
       */
      closeDialog(): void {
        const self = this;
        $("#startDateInput").ntsError('clear');
        nts.uk.ui.windows.setShared('PERSONAL_CLONED', null);
        self.$blockui('hide');
        nts.uk.ui.windows.close();
      }

      createClonePersonalCost() {
        const self = this;

        //check error Msg_102
        
        $("#startDateInput").trigger('validate');
        
        if( !moment(self.newStartDate()).isSameOrAfter(moment(self.beginStartDate()))) {
          $("#startDateInput").ntsError('set', { messageId: "Msg_102" }); //C1_3
        }

        if(nts.uk.ui.errors.hasError()) return;
        
        self.currentPersonCost(self.latestHistory().personalCost);
        let premiumSettingList: Array<any> = [];
        _.forEach(self.currentPersonCost().premiumSets, (item) => {

          let attendanceItems = [];
          _.forEach(item.attendanceItems, (x) => {
            attendanceItems.push(x.shortAttendanceID);
          });

          premiumSettingList.push({
            iD: item.displayNumber,
            name: item.name,
            rate: item.rate,
            unitPrice: item.unitPrice,
            useAtr: item.useAtr,
            attendanceItems: attendanceItems
          });
        });

        let personCostRoundingSetting: any = {
          unitPriceRounding: self.copyDataFlag() ? self.currentPersonCost().roundingUnitPrice : vmbase.UnitPriceRounding.ROUND_UP,//単価の丸め
          unit: self.copyDataFlag() ? self.currentPersonCost().unit : vmbase.AmountUnit.ONE_YEN, //金額の丸め
          rounding: self.copyDataFlag() ? self.currentPersonCost().inUnits : vmbase.InUnits.TRUNCATION //単位で
        };

        let startDate = moment.utc(self.newStartDate(), 'YYYY-MM-DD').toISOString(); 
        let params: any = {
          startDate: startDate,
          historyID: null,
          unitPrice: self.copyDataFlag() ? self.currentPersonCost().unitPrice : vmbase.UnitPrice.Price_1, //計算用単価
          howToSetUnitPrice: self.copyDataFlag() ? self.currentPersonCost().calculationSetting : vmbase.CalculationSetting.Premium_Rate,//割増金額の計算設定
          workingHoursUnitPrice: self.copyDataFlag() ? self.currentPersonCost().workingHour : vmbase.UnitPrice.Price_1, //計算用単価
          memo: self.copyDataFlag() ? self.currentPersonCost().memo : null, //備考
          personCostRoundingSetting: personCostRoundingSetting,
          premiumSets: self.copyDataFlag() ? premiumSettingList : []
        };

        self.$blockui('show');

        servicebase.personCostCalculationInsert(params)
        .done(() => {
          self.$dialog.info({ messageId: 'Msg_15'}).then( () => {            
            self.$blockui('hide');
            nts.uk.ui.windows.setShared('PERSONAL_CLONED', self.copyDataFlag());            
            nts.uk.ui.windows.close();            
          });          
        })
        .fail((error) => {
          self.$dialog.error({ messageId: error.messageId}).then( () => {   
            $('#startDateInput').focus();
            self.$blockui('hide');
          });
        });
      }
    }
  }
}