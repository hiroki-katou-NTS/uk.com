module nts.uk.at.view.kbt002.d {
  export module viewmodel {
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import windows = nts.uk.ui.windows;

    export class ScreenModel {
      gridListColumns: KnockoutObservableArray<any>;
      monthDaysList: KnockoutObservableArray<any> = ko.observableArray([]);
      selectedDaysList: KnockoutObservableArray<number> = ko.observableArray([]);
      constructor() {
        let self = this;
        self.gridListColumns = ko.observableArray([
          { headerText: '', key: 'value', width: 1, hidden: true },
          { headerText: nts.uk.resource.getText("KBT002_108"), key: 'localizedName', width: 210 }
        ]);
      }

      // Start page
      start() {
        let self = this;
        var dfd = $.Deferred();
        var sharedData = getShared('inputDialogD');
        if (sharedData) {
          self.selectedDaysList(sharedData.repeatMonthDateList);
        }
        service.getEnumDataList().done(function (setting) {
          self.monthDaysList(setting.monthDayList);
          dfd.resolve();
        });

        return dfd.promise();
      }

      select() {
        let self = this;
        if (self.selectedDaysList().length == 0) {
          nts.uk.ui.dialog.alert({ messageId: "Msg_846" });
        } else {
          // set return value
          setShared('outputDialogD', { selectedDays: self.selectedDaysList() });

          // close dialog.
          windows.close();
        }
      }

      closeDialog() {
        windows.close();
      }
    }
  }
}