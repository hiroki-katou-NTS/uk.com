/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />
module nts.uk.at.view.ksm007.b {
  import getShared = nts.uk.ui.windows.getShared;
  import setShared = nts.uk.ui.windows.setShared;

  const PATH = {
    getData: '',
    saveData: ''
  }

  @bean()
  class ViewModel extends ko.ViewModel {

    //model - left
    workplaceGroupCode: KnockoutObservable<string> = ko.observable('WPGC0001');
    workplaceGroupName: KnockoutObservable<string> = ko.observable('WPGC000N');
    historyListItems: KnockoutObservableArray<HistoryItem> = ko.observableArray([]);
    historyCurrentItem: KnockoutObservable<string> = ko.observable(null);
    historyCurrentObject: KnockoutObservable<HistoryItem> = ko.observable();

    startDate: KnockoutObservable<string> = ko.observable(moment().format('YYYY/MM/DD'));
    nightShiftOperation: KnockoutObservable<number> = ko.observable(1);
    nightShiftHours1: KnockoutObservable<number> = ko.observable(1800);
    nightShiftHours2: KnockoutObservable<number> = ko.observable(800);

    isNewMode: KnockoutObservable<boolean> = ko.observable(true);
    inputScreenB: KnockoutObservable<any> = ko.observable(null);
    isLastItem: KnockoutObservable<boolean> = ko.observable(true);

    constructor(params: any) {
      super();
      const vm = this;

      vm.historyListItems.push(new HistoryItem('2021/01/02', '9999/12/31', '001'));
      vm.historyListItems.push(new HistoryItem('2020/12/02', '2021/01/01', '002'));
      vm.historyListItems.push(new HistoryItem('2020/11/02', '2021/12/01', '003'));
      vm.historyListItems.push(new HistoryItem('2020/10/02', '2021/11/01', '004'));
      vm.historyListItems.push(new HistoryItem('2020/09/02', '2021/10/01', '005'));
      vm.historyListItems.push(new HistoryItem('2020/08/02', '2021/09/01', '006'));
      vm.historyListItems.push(new HistoryItem('2020/07/02', '2021/08/01', '007'));
      vm.historyListItems.push(new HistoryItem('2020/06/02', '2021/07/01', '008'));
      vm.historyListItems.push(new HistoryItem('2020/05/02', '2021/06/01', '009'));
      vm.historyListItems.push(new HistoryItem('2020/04/02', '2021/05/01', '010'));
      vm.historyListItems.push(new HistoryItem('2020/03/02', '2021/04/01', '011'));

      vm.loadData();

      vm.inputScreenB(nts.uk.ui.windows.getShared('inputScreenB'));
      vm.workplaceGroupCode(vm.inputScreenB().wpGroupCode);
      vm.workplaceGroupName(vm.inputScreenB().wpGroupName);


      vm.historyCurrentItem.subscribe((value) => {
        vm.loadData(value);
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

      vm.$window.close({});
    }

    saveData() {
      const vm = this;

      if (vm.nightShiftHours1() > vm.nightShiftHours2()) {
        vm.$dialog.error({ messageId: 'Msg_307' }).then(() => { });
        return;
      }

      if (vm.nightShiftHours1() < 1320 || vm.nightShiftHours1() > 1740 || vm.nightShiftHours2() > 1740) {
        vm.$dialog.error({ messageId: 'Msg_2090' }).then(() => {
          $('#nightShiftHours1').focus();
        });
        return;
      }

      vm.$ajax(PATH.saveData, {}).done(() => {
        vm.$dialog.info({ messageId: 'Msg_15' }).then(() => {
          vm.$window.close({});
        });
      });
    }

    clearData() {
      const vm = this;
      vm.startDate(null);
      vm.nightShiftOperation(1);
      vm.nightShiftHours1(null);
      vm.nightShiftHours2(null);
    }

    openDialogScreenC() {
      const vm = this;

      let lastItem = _.head(vm.historyListItems());
      if (_.isNil(lastItem)) {
        lastItem = new HistoryItem(moment().format('YYYY/MM/DD'), '9999/12/31');
      }

      const params = { 
        ...lastItem, 
        wpGroupId: vm.inputScreenB().wpGroupId,
        startTime:vm.nightShiftHours1(),
        endTime: vm.nightShiftHours2(),
        nsOperationCls: vm.nightShiftOperation()
      };
      vm.$window.modal('/view/ksm/007/c/index.xhtml', params).done((data) => {
        if( data && data.isSave) {
          vm.loadData();
        }
      });
    }

    openDialogScreenD() {
      const vm = this;

      let lastItem = _.head(vm.historyListItems());
      if (_.isNil(lastItem)) {
        lastItem = new HistoryItem(moment().format('YYYY/MM/DD'), '9999/12/31');
      }

      let params = {
        wpGroupId: vm.inputScreenB().wpGroupId, //職場グループID
        startDate: lastItem.startDate, //有効開始日
        isDelete: vm.historyListItems().length > 1, //履歴一覧, B3_2「履歴一覧」に一つ履歴しかない場合は、履歴削除可能＝false
      };

      vm.$window.modal('/view/ksm/007/d/index.xhtml', params).done((data) => {

      });
    }

    getLatestItem() {
      const vm = this;
      let item = _.head(vm.historyListItems());
      return item;
    }

    getData() {
      const vm = this;
      vm.$blockui('grayout');
      vm.$ajax(PATH.getData, { wpGroupId: null }).done((data) => {
        if (data) {

        }
        vm.$blockui('hide');
      });
    }

    loadData(startDate?: string) {
      const vm = this;

      /* vm.$ajax(PATH.getData).done((data) => {
        if(data) {
          vm.historyListItems(data); 
        }
      }); */

      if (vm.historyListItems().length === 0) {
        vm.isNewMode(true); //New
        vm.clearData();
        return;
      } else vm.isNewMode(false); //Edit

      let lastItem = _.head(vm.historyListItems());
      if (startDate) {
        vm.historyCurrentItem(startDate);
        vm.isLastItem(lastItem.startDate === startDate);
        let historyItem = _.find(vm.historyListItems(), (item) => item.startDate === startDate);
        vm.bindData(historyItem);
      } else {
        vm.historyCurrentItem(lastItem.startDate);
        vm.isLastItem(true);
        vm.bindData(lastItem);
      }
    }

    bindData(obj: HistoryItem, data?: any) {
      const vm = this;

      vm.startDate(obj.startDate);
      vm.nightShiftOperation(1);
      vm.nightShiftHours1(2000);
      vm.nightShiftHours2(2500);
    }

  }


  export class HistoryItem {
    startDate: string;
    endDate: string;
    display: string;
    historyId: string;

    constructor(
      startDate: string,
      endDate: string,
      historyId?: string,
      display?: string) {
      this.startDate = startDate;
      this.endDate = endDate;
      this.historyId = historyId;
      this.display = display ? display : startDate + nts.uk.resource.getText('KSM007_24') + endDate;
    }
  }
}