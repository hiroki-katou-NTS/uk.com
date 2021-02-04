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
      defaultPremiumSettings: KnockoutObservableArray<any> = ko.observableArray([]);
      latestPersonalData: KnockoutObservable<any> = ko.observable(null);

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
        self.getOneDefaultPremiumSetting();        
        self.getLatestPersonal(self.latestHistory());
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
        
        self.currentPersonCost(self.latestPersonalData());
        let premiumSettingList: Array<any> = [];
        
        if( !_.isNil(self.currentPersonCost().premiumSets)) {
          _.forEach(self.currentPersonCost().premiumSets, (item) => {
            premiumSettingList.push({
              iD: item.id,
              name: item.name,
              rate: item.rate,
              unitPrice: item.unitPrice,
              useAtr: item.useAtr,
              attendanceItems: item.attendanceItems
            });
          });
        } else 
          premiumSettingList = self.defaultPremiumSettings();

        let personCostRoundingSetting: any = null;
        if( self.copyDataFlag() && !_.isNil(self.currentPersonCost().personCostRoundingSetting)) {
          personCostRoundingSetting = self.currentPersonCost().personCostRoundingSetting;
        } else {
          personCostRoundingSetting = {
            unitPriceRounding: vmbase.UnitPriceRounding.ROUND_UP,
            unit: vmbase.AmountUnit.ONE_YEN,
            rounding: vmbase.InUnits.TRUNCATION
          }
        }
      
        let startDate = moment.utc(self.newStartDate(), 'YYYY-MM-DD').toISOString(); 
        let params: any = {
          startDate: startDate,
          historyID: null,
          unitPrice: self.copyDataFlag() ? self.currentPersonCost().unitPrice : vmbase.UnitPrice.Price_1, //計算用単価
          howToSetUnitPrice: self.copyDataFlag() ? self.currentPersonCost().howToSetUnitPrice : vmbase.CalculationSetting.Premium_Rate,//割増金額の計算設定
          workingHoursUnitPrice: self.copyDataFlag() ? self.currentPersonCost().workingHoursUnitPrice : vmbase.UnitPrice.Price_1, //計算用単価
          memo: self.copyDataFlag() ? self.currentPersonCost().memo : null, //備考
          personCostRoundingSetting: personCostRoundingSetting,
          premiumSets: self.copyDataFlag() ? premiumSettingList : self.defaultPremiumSettings()
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
          $('#startDateInput').ntsError('set', {messageId: error.messageId});
          self.$blockui('hide');
        });

      }

      getOneDefaultPremiumSetting() {
        const self = this;
        var premiumItemSelect = servicebase.premiumItemSelect();
        $.when(premiumItemSelect).done((data) => {
          let premiumItemSelectData: any = _.orderBy(data, ['displayNumber'], ['asc']);
          if (premiumItemSelectData.length > 0) {          
            _.forEach(premiumItemSelectData, (item) => {
              if (item.useAtr) {               
                self.defaultPremiumSettings.push({
                  iD: item.displayNumber,
                  name: item.name,
                  rate: 100,
                  unitPrice: 0,
                  useAtr: item.useAtr,
                  attendanceItems: []
                });
              }
            });
          }
        });
      }

      getLatestPersonal() {
        const self = this;        
        let historyId = !_.isNil(self.latestHistory().latestHistory.historyId) 
                            ? self.latestHistory().latestHistory.historyId 
                            : '';        
        servicebase.findByHistoryID({ historyID: historyId }).done((data) => {
          self.latestPersonalData(data);
        }).fail(res => { return null });
      }
    }
  }
}