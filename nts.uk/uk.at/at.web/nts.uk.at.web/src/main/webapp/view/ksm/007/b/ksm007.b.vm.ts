/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />
module nts.uk.at.view.ksm007.b {
  import getShared = nts.uk.ui.windows.getShared;
  import setShared = nts.uk.ui.windows.setShared;

  const PATH = {
    getListNightShiftInformation: 'bs/schedule/employeeinfo/workplacegroup/getnightshiftinformation',
    getListNightShiftInforHist: 'bs/schedule/employeeinfo/workplacegroup/getlistnightshiftinforhist',
    saveNightShiftInFor: 'bs/schedule/employeeinfo/workplacegroup/addnightshiftinfor'
  }

  @bean()
  class ViewModel extends ko.ViewModel {

    //model - left
    workplaceGroupCode: KnockoutObservable<string> = ko.observable('WPGC0001');
    workplaceGroupName: KnockoutObservable<string> = ko.observable('WPGC000N');
    historyListItems: KnockoutObservableArray<HistoryItem> = ko.observableArray([]);
    historyCurrentItem: KnockoutObservable<string> = ko.observable(null);
    historyCurrentObject: KnockoutObservable<HistoryItem> = ko.observable(null);

    startDate: KnockoutObservable<string> = ko.observable(null);
    nightShiftOperation: KnockoutObservable<number> = ko.observable(0);
    nightShiftHours1: KnockoutObservable<any> = ko.observable(null);//22h
    nightShiftHours2: KnockoutObservable<any> = ko.observable(null); //05h next day

    isNewMode: KnockoutObservable<boolean> = ko.observable(true);
    inputScreenB: KnockoutObservable<any> = ko.observable(null);
    isLastItem: KnockoutObservable<boolean> = ko.observable(true);
    isEnableSave: KnockoutObservable<boolean> = ko.observable(false);
    isSave: KnockoutObservable<boolean> = ko.observable(false);
    requiredNightShiftTime: KnockoutObservable<boolean> = ko.observable(true);

    constructor(params: any) {
      super();
      const vm = this;

      vm.inputScreenB(nts.uk.ui.windows.getShared('inputScreenB'));
      vm.workplaceGroupCode(vm.inputScreenB().wpGroupCode);
      vm.workplaceGroupName(vm.inputScreenB().wpGroupName);

      vm.getListNightShiftInfor();

      vm.historyCurrentItem.subscribe((historyId) => {
        if (historyId === null) return;
        vm.getHistoryData(historyId);
      });

      vm.nightShiftOperation.subscribe(value => {
        const vm = this;
          vm.requiredNightShiftTime(false);
        if (value == 1){
          vm.requiredNightShiftTime(true);
        }
        nts.uk.ui.errors.clearAll();
      });
    }

    created(params: any) {
      const vm = this;
    }

    mounted() {
      const vm = this;
    }

    closeDialog() {
      const vm = this;
      setShared('outputScreenB', { isSave: vm.isSave() });
      vm.$window.close();
    }

    saveData() {
      const vm = this;

      $('.nightShiftHours').trigger('validate');
      if (nts.uk.ui.errors.hasError()) return;
      if (vm.nightShiftHours1() > vm.nightShiftHours2()) {
        vm.$dialog.error({ messageId: 'Msg_307' }).then(() => { });
        return;
      }

      if (vm.requiredNightShiftTime()) {
          if (vm.nightShiftHours1() < 1320 || vm.nightShiftHours1() > 1740 || vm.nightShiftHours2() > 1740) {
              vm.$dialog.error({ messageId: 'Msg_2090' }).then(() => {
                  $('#nightShiftHours1').focus();
              });
              return;
          }
      }

      let params = {
        workplaceGroupId: vm.inputScreenB().wpGroupId, // 職場グループID: 職場グループID.
        historyId: vm.historyCurrentObject().historyId, // 履歴ID: .        
        nightShiftOperationAtr: vm.nightShiftOperation(), // 夜勤運用ルール: 夜勤運用ルール.
        ClockHourMinuteStart: vm.nightShiftHours1(), // HH:mm
        ClockHourMinuteEnd: vm.nightShiftHours2() // HH:mm
      };

      vm.$blockui('grayout');
      vm.$ajax('com', PATH.saveNightShiftInFor, params).done(() => {
        vm.$dialog.info({ messageId: 'Msg_15' }).then(() => {
          setShared('outputScreenB', { startDate: vm.nightShiftHours1(), endDate: vm.nightShiftHours2() });
          vm.isSave(true);
          vm.$blockui('hide');
          $('#nightShiftHours1').focus();
        });
      }).fail((error) => {
        vm.$dialog.error({ messageId: error.messageId }).then(() => {
          vm.$blockui('hide');
        });
      });
    }

    clearData(startDate?: string) {
      const vm = this;
      vm.startDate(startDate);
      vm.nightShiftOperation(1);
      vm.nightShiftHours1(null);
      vm.nightShiftHours2(null);
      $('#nightShiftHours1').focus();
    }

    openDialogScreenC() {
      const vm = this;

      nts.uk.ui.errors.clearAll();

      let lastItem = _.head(vm.historyListItems());

      const params = {
        lastItem: vm.historyListItems().length > 0 ? lastItem : null,
        wpGroupId: vm.inputScreenB().wpGroupId,
        startTime: vm.nightShiftHours1(),
        endTime: vm.nightShiftHours2(),
        nsOperationCls: 0 //new mode
      };

      vm.$window.modal('/view/ksm/007/c/index.xhtml', params).done((data) => {
        if (data && data.isSave) {
          vm.getListNightShiftInfor();
        } else {
          $('#nightShiftHours1').focus();
        }
      });
    }

    openDialogScreenD() {
      const vm = this;

      let lastItem = _.head(vm.historyListItems());
      if (_.isNil(lastItem)) {
        lastItem = new HistoryItem(moment().format('YYYY/MM/DD'), '9999/12/31');
      }

      // let limitDate = lastItem.startDate;
      // if (vm.historyListItems().length > 1) {
      //   limitDate = vm.historyListItems()[1].startDate;
      // }

      let params = {
        wpGroupId: vm.inputScreenB().wpGroupId, //職場グループID
        historyId: lastItem.historyId,
        startDate: lastItem.startDate, //有効開始日
        startDateLimit: vm.historyListItems().length > 1 ? vm.historyListItems()[1].startDate : null, //有効開始日
        isDelete: vm.historyListItems().length > 1, //履歴一覧, B3_2「履歴一覧」に一つ履歴しかない場合は、履歴削除可能＝false
      };

      vm.$window.modal('/view/ksm/007/d/index.xhtml', params).done((data) => {
        if (data && data.isSave) {
          vm.getListNightShiftInfor();
        }
      });
    }

    getLatestItem() {
      const vm = this;
      let item = _.head(vm.historyListItems());
      return item;
    }

    getHistoryData(historyId?: string) {
      const vm = this;

      nts.uk.ui.errors.clearAll();

      let historyItem: any = _.find(vm.historyListItems(), (x) => { return x.historyId === historyId });
      vm.historyCurrentObject(historyItem);

      let lastItem = _.head(vm.historyListItems());
      vm.isLastItem(lastItem.historyId === historyItem.historyId);

      vm.$blockui('show');
      vm.$ajax('com', PATH.getListNightShiftInformation, { id: historyId }).done((data) => {
        if (data) {
          vm.bindData(historyItem, data);
        } else
          vm.clearData(historyItem.startDate);

        $('#nightShiftHours1').focus();
        vm.$blockui('hide');
      }).fail(error => {
        vm.$dialog.error({ messageId: error.messageId }).then(() => {
          vm.$blockui('hide');
        });
      });
    }

    loadData(historyId?: string) {
      const vm = this;

      if (vm.historyListItems().length === 0) {
        vm.isNewMode(true); //New
        vm.isLastItem(false);
        vm.clearData();
        return;
      } else vm.isNewMode(false); //Edit

      let lastItem = _.head(vm.historyListItems());
      if (historyId) {
        vm.isLastItem(lastItem.historyId === historyId);
        vm.historyCurrentItem(historyId);
      } else {
        vm.isLastItem(true);
        historyId = lastItem.historyId;
        vm.historyCurrentItem(lastItem.historyId);
      }

      vm.getHistoryData(historyId);

    }

    bindData(obj: HistoryItem, data?: any) {
      const vm = this;

      vm.startDate(obj.startDate);
      vm.nightShiftOperation(data.nightShiftOperationAtr);
      
      let time: any = null;
      if (data.clockHourMinuteStart && data.clockHourMinuteStart.length > 0) {
        time = data.clockHourMinuteStart;
        time = _.split(time, ':');    
        vm.nightShiftHours1(parseInt(time[0])*60 + parseInt(time[1]));
      } else 
        vm.nightShiftHours1(null);

      if (data.clockHourMinuteEnd &&  data.clockHourMinuteEnd.length > 0 ) {
        time = data.clockHourMinuteEnd;
        time = _.split(time, ':');      
        vm.nightShiftHours2(parseInt(time[0])*60 + parseInt(time[1]));
      } else 
        vm.nightShiftHours2(null);
      
      $('#nightShiftHours1').focus();

    }

    getListNightShiftInformation() {
      const vm = this;
      vm.$ajax(PATH.getListNightShiftInformation, { id: vm.workplaceGroupCode() }).done((data) => {
        if (data) {
          vm.historyListItems(data);
        }
      });
    }

    getListNightShiftInfor() {
      const vm = this;

      vm.$blockui('grayout');
      vm.$ajax('com', PATH.getListNightShiftInforHist, { id: vm.inputScreenB().wpGroupId }).done((data) => {
        if (data) {
          let historyItem: Array<HistoryItem> = [];
          _.forEach(data, (x) => {
            historyItem.push(new HistoryItem(x.startDate, x.endDate, x.historyId, null, x.workplaceGroupId));
          });

          vm.historyListItems.removeAll();
          vm.historyListItems(_.orderBy(historyItem, 'startDate', 'desc'));

          vm.isEnableSave(vm.historyListItems().length > 0);
          //load detail
          vm.loadData();
          vm.$blockui('hide');
        }

      }).fail(error => {
        vm.$dialog.error({ messageId: error.messageId }).then(() => {
          vm.$blockui('hide');
        });
      });
    }
  }

  export class HistoryItem {
    startDate: string;
    endDate: string;
    display: string;
    historyId: string;
    workplaceGroupId: string;
    constructor(
      startDate: string,
      endDate: string,
      historyId?: string,
      display?: string,
      wpGroupId?: string) {
      this.startDate = startDate;
      this.endDate = endDate;
      this.historyId = historyId;
      this.workplaceGroupId = wpGroupId;
      this.display = display ? display : startDate + nts.uk.resource.getText('KSM007_24') + endDate;
    }
  }
}