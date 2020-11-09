/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kml002.e {

  @bean()
  class ViewModel extends ko.ViewModel {

    listOfStartTimes: KnockoutObservableArray<any> = ko.observableArray([]);
    selectedAll: KnockoutObservable<boolean> = ko.observable(false);

    isEnableAddNew: KnockoutObservable<boolean> = ko.observable(true);
    isEnableDelete: KnockoutObservable<boolean> = ko.observable(false);

    constructor(params: any) {
      super();
      const vm = this;

      //vm.createListOfStartTimes();

      vm.selectedAll.subscribe((newValue) => {
        if (newValue === null) return;
        _.forEach(vm.listOfStartTimes(), (row) => {
          row.isChecked(newValue);
        })
      });

      vm.listOfStartTimes.subscribe((newList) => {
        if (!newList || newList.length <= 0) {
          vm.selectedAll(false);
          return;
        }

        //check to show delete
        let least1ItemSelected: any = vm.listOfStartTimes().some(item => item.isChecked() === true);
        vm.isEnableDelete(least1ItemSelected);

        let isSelectedAll: any = vm.listOfStartTimes().every(item => item.isChecked() === true);
        //there is least one item which is not checked
        if (isSelectedAll === false) isSelectedAll = null;
        vm.selectedAll(isSelectedAll);
      });

      vm.$window.storage('TIME_ZONE_NUMBER_PEOPLE_DETAILS').then((data) => {
        vm.createListOfStartTimes(data);
      });
    }

    created(params: any) {
      const vm = this;
      //_.extend(window, { vm });
    }

    mounted() {
      const vm = this;

      $("#fixed-table").ntsFixedTable({ height: 222 });
      $('#addNewItem').focus();
    }

    openDialogScreenF() {
      const vm = this;
      vm.$window.modal('/view/kml/002/f/index.xhtml').then(() => {
        vm.$window.storage('REGISTER_TIME_ZONE').then((data) => {
          if (!_.isNil(data)) {
            let startTime: number = data.startTime,
              endTime: number = data.endTime;

            vm.listOfStartTimes([]);
            for (let h = startTime; h < endTime; h += 60) {
              let item: StartTime = new StartTime(h, false, h);
              vm.addItem(item);
            }

            if (startTime < endTime) {
              let item: StartTime = new StartTime(endTime, false, endTime);
              vm.addItem(item);
            }

            let firstItem = _.head(vm.listOfStartTimes());
            $('#starttime-' + firstItem.id).focus();
          }
        });
      });
    }

    closeDialog() {
      const vm = this;
      vm.$window.close();
    }

    createListOfStartTimes(data: Array<any>) {
      const vm = this;

      if (data) {
        data = _.orderBy(data, 'time', 'asc');
        _.forEach(data, (item, index) => {
          vm.addItem(new StartTime(index + 1, false, item.time));
        });
      }
    }

    removeItem() {
      const vm = this;
      vm.listOfStartTimes(vm.listOfStartTimes().filter(item => item.isChecked() === false));
      vm.isEnableDelete(false);
      vm.isEnableAddNew(true);
    }

    addNewItem() {
      const vm = this;
      let newItem: StartTime = new StartTime(0, false, 1400);
      vm.addItem(newItem);
    }

    addItem(item: StartTime, isNew: boolean = false) {
      const vm = this;
      let lastItem = _.last(vm.listOfStartTimes()),
        id: number = 1;
      if (!_.isNil(lastItem)) id = lastItem.id + 1;

      item.id = id;
      item.isChecked.subscribe((value) => {
        vm.listOfStartTimes.valueHasMutated();
      });

      item.time.subscribe((value) => {
        if (value) $('#starttime-' + item.id).removeClass('error-input');
        //vm.listOfStartTimes.valueHasMutated();
      });

      vm.listOfStartTimes.push(item);
      if (isNew) $('#starttime-' + id).focus();

      //「開始時刻一覧」の行数　＞＝　24行
      let show: boolean = vm.listOfStartTimes().length >= 24;
      vm.isEnableAddNew(!show);
    }

    registerTimeZone() {
      const vm = this;

      //時間帯一覧は1～24で指定してください。
      if (vm.listOfStartTimes().length <= 0 || vm.listOfStartTimes().length > 24) {
        vm.$dialog.error({ messageId: 'Msg_1819' }).then(() => {
          $('.gridList').focus();
        });
        return;
      }

      //時間帯一覧は重複しないように指定してください。overlap
      $("input[id^='starttime-']").removeClass('error-input');
      let newTimeZone: any = vm.getDuplicateItem(vm.listOfStartTimes());
      let errors: boolean = false;
      if (newTimeZone.length < vm.listOfStartTimes().length) {
        //find duplicate to set error
        let newDuplicateItems = _.filter(vm.listOfStartTimes(), (element, index, self) => {
          return index !== _.findIndex(self, (x) => { return x.time() === element.time(); });
        });

        _.forEach(newDuplicateItems, (item) => {
          $('#starttime-' + item.id).addClass('error-input');
          let duplicateItem = _.find(vm.listOfStartTimes(), (x) => x.time() === item.time());
          if (!_.isNil(duplicateItem)) {          
            $('#starttime-' + duplicateItem.id).addClass('error-input').focus();
          }
        });

        vm.$dialog.error({ messageId: 'Msg_1820' }).then(() => {
          let firstItem = _.head(newDuplicateItems);
        });
      }

      //入力時間は15分刻みの数値以外の場合 -> Msg_1845
      let timeZoneList: Array<any> = [];
      errors = false;
      _.forEach(vm.listOfStartTimes(), (item, index) => {
        timeZoneList.push({ id: index, time: item.time() });
        let start15m = item.time() - _.floor(item.time() / 60) * 60;
        if (start15m % 15 !== 0) {
          $('#starttime-' + item.id).ntsError('set', { messageId: "Msg_1845" }).focus();
          errors = true;
        }
      });

      if (errors) return;

      //OK
      vm.$window.storage('TIME_ZONE_NUMBER_PEOPLE_DETAILS', timeZoneList).then(() => {
        vm.$dialog.error({ messageId: 'Msg_15' }).then(() => {
          vm.$window.close();
        });
      })
    }

    getDuplicateItem(listItems: Array<any>): Array<any> {
      if (listItems.length <= 0) return [];
      let newListItems = _.filter(listItems, (element, index, self) => {
        return index === _.findIndex(self, (x) => { return x.time() === element.time(); });
      });

      return newListItems;
    }
  }

  export class StartTime {
    id: number;
    isChecked: KnockoutObservable<boolean> = ko.observable(false);
    time?: KnockoutObservable<number> = ko.observable(null);
    constructor(id: number, isChecked?: boolean, time?: number) {
      this.isChecked(isChecked);
      if (!_.isNil(time)) this.time(time);
      this.id = id;
    }
  }
}