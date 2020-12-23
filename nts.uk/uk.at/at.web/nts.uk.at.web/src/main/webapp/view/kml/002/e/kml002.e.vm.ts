/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kml002.e {

  const PATH = {
    wkpTimeZonebyId: 'ctx/at/schedule/budget/wkpTimeZone/getById',
    wkpTimeZoneRegister: 'ctx/at/schedule/budget/wkpTimeZone/register'
  }

  @bean()
  class ViewModel extends ko.ViewModel {

    listOfStartTimes: KnockoutObservableArray<any> = ko.observableArray([]);
    selectedAll: KnockoutObservable<boolean> = ko.observable(false);

    isEnableAddNew: KnockoutObservable<boolean> = ko.observable(true);
    isEnableDelete: KnockoutObservable<boolean> = ko.observable(false);
    isTimeZoneSetting: KnockoutObservable<any> = ko.observable(null);

    constructor(params: any) {
      super();
      const vm = this;

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

      //get time zone that registered before
      vm.getWorkplaceTimeZoneById();
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

            if (nts.uk.ui.errors.hasError()) nts.uk.ui.errors.clearAll();

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
      vm.$window.storage('KML002_SCREEN_E_OUTPUT', { setting: vm.isTimeZoneSetting() }).then(() => {
        vm.$window.close();
      });      
    }

    createListOfStartTimes(data: Array<any>) {
      const vm = this;

      if (data) {
        data = _.orderBy(data, 'timeZone', 'asc');
        _.forEach(data, (item, index) => {
          vm.addItem(new StartTime(index + 1, false, item.timeZone));
        });
      }
    }

    removeItem() {
      const vm = this;

      if (nts.uk.ui.errors.hasError()) nts.uk.ui.errors.clearAll();

      vm.listOfStartTimes(vm.listOfStartTimes().filter(item => item.isChecked() === false));
      vm.isEnableDelete(false);
      vm.isEnableAddNew(true);
    }

    addNewItem() {
      const vm = this;
      let newItem: StartTime = new StartTime(0, false, null);
      vm.addItem(newItem);
      if (nts.uk.ui.errors.hasError()) nts.uk.ui.errors.clearAll();
      if (vm.listOfStartTimes().length <= 24) {
        let last = _.last(vm.listOfStartTimes());
        $('#starttime-' + last.id).focus();
      }
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

      vm.listOfStartTimes.push(item);
      if (isNew) $('#starttime-' + id).focus();

      //「開始時刻一覧」の行数　＞＝　24行
      let show: boolean = vm.listOfStartTimes().length >= 24;
      vm.isEnableAddNew(!show);
    }

    registerTimeZone() {
      const vm = this;

      $('.time-zone').trigger('validate');
      if(nts.uk.ui.errors.hasError()) return;
      
      //時間帯一覧は1～24で指定してください。
      if (vm.listOfStartTimes().length <= 0 || vm.listOfStartTimes().length > 24) {
        vm.$dialog.error({ messageId: 'Msg_1819' }).then(() => {
          //$('.gridList').focus();
        });
        return;
      }

      //check null: KML002_100
      let errors = false;
      _.forEach(vm.listOfStartTimes(), (item) => {
        if (_.isNil(item.time())) {
          $('#starttime-' + item.id).ntsError('set', { messageId: "MsgB_1", messageParams: [vm.$i18n('KML002_100')] });
          errors = true;
        }
      });


      //時間帯一覧は重複しないように指定してください。overlap
      if (!errors) {
        $("input[id^='starttime-']").removeClass('error-input');
        let newTimeZone: any = vm.getDuplicateItem(vm.listOfStartTimes());
        if (newTimeZone.length < vm.listOfStartTimes().length) {
          //find duplicate to set error
          let newDuplicateItems = _.filter(vm.listOfStartTimes(), (element, index, self) => {
            return index !== _.findIndex(self, (x) => { return x.time() === element.time() });
          });

          vm.$dialog.error({ messageId: 'Msg_1820' }).then(() => {
            let item = _.head(newDuplicateItems);
            $('#starttime-' + item.id).focus();
          });

          errors = true;
        }
      }

      //入力時間は15分刻みの数値以外の場合 -> Msg_1845
      if (!errors) {
        let timeZoneList: Array<any> = [];
        _.forEach(vm.listOfStartTimes(), (item, index) => {
          timeZoneList.push({ id: index, time: item.time() });
          let start15m = item.time() - _.floor(item.time() / 60) * 60;
          if (start15m % 15 !== 0) {
            $('#starttime-' + item.id).ntsError('set', { messageId: "Msg_1845" }).focus();
            errors = true;
          } else if (_.isNil(item.time())) {
            $('#starttime-' + item.id).focus();
            errors = true;
          }
        });
      }

      if (!errors) vm.workplaceTimeZoneRegister(vm.listOfStartTimes());
    }

    getDuplicateItem(listItems: Array<any>): Array<any> {
      if (listItems.length <= 0) return [];
      let newListItems = _.filter(listItems, (element, index, self) => {
        return index === _.findIndex(self, (x) => { return x.time() === element.time() });
      });

      return newListItems;
    }

    getWorkplaceTimeZoneById() {
      const vm = this;
      vm.$blockui('show');
      vm.listOfStartTimes([]);
      vm.$ajax(PATH.wkpTimeZonebyId).done((data) => {
        if (!_.isNil(data) && data.length > 0) {
          vm.createListOfStartTimes(data);
          vm.isTimeZoneSetting(true);
          vm.$blockui('hide');
        }

      }).fail().always(() => vm.$blockui('hide'));
    }

    /**
     * Register time zone for workplace
     * @param timeZoneList 
     */
    workplaceTimeZoneRegister(timeZoneList: any) {
      const vm = this;

      vm.$blockui('show');

      let params: Array<any> = [];
      _.forEach(timeZoneList, (x) => {
        params.push(x.time());
      });

      vm.$ajax(PATH.wkpTimeZoneRegister, { 'timeZone': params }).done(() => {            
        vm.$dialog.info({ messageId: 'Msg_15' }).then(() => {
          //vm.isTimeZoneSetting(true);
          vm.getWorkplaceTimeZoneById();          
          vm.$blockui('hide');         
        });
      }).fail().always(() => vm.$blockui('hide'));
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