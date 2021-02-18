/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kmt001.a {

  const PATH = {
    saveRegistrationWork: '',
    deleteRegistrationWork: '',
    getRegistrationWork: '',
  };

  @bean()
  class ViewModel extends ko.ViewModel {

    selectedWorkCode: KnockoutObservable<string> = ko.observable(null);
    currentCode: KnockoutObservable<string> = ko.observable(null);
    registrationWorkList: KnockoutObservableArray<any> = ko.observableArray(null);
    externalCodeList: KnockoutObservableArray<any> = ko.observableArray([]);
    externalCode: Array<any> = [];
    workList: KnockoutObservable<any> = ko.observable([]);

    model: KnockoutObservable<ModelItem> = ko.observable(null);
    isNewMode: KnockoutObservable<boolean> = ko.observable(true);

    constructor(params: any) {
      super();
      const vm = this;

      vm.externalCodeList([
        { code: 'KMT001_36', value: ko.observable(null) },
        { code: 'KMT001_37', value: ko.observable(null) },
        { code: 'KMT001_38', value: ko.observable(null) },
        { code: 'KMT001_39', value: ko.observable(null) },
        { code: 'KMT001_40', value: ko.observable(null) },
      ]);

      //init mode
      vm.getRegistrationWorkList();
      vm.getWorkList();

      vm.model(new ModelItem());
      vm.addNewRegistrationWork();

      vm.selectedWorkCode.subscribe((newValue) => {
        nts.uk.ui.errors.clearAll();
        vm.loadRegistrationWork();
      });

      vm.currentCode.subscribe((newValue) => {
        nts.uk.ui.errors.clearAll();
        vm.loadRegistrationWork();
      });
    }

    created(params: any) {
      const vm = this;
    }

    mounted() {
      const vm = this;
    }

    addNewRegistrationWork() {
      const vm = this;
      let currentDate = moment(new Date()).format('YYYY/DD/MM');
      vm.externalCode = [
        ko.observable(null), ko.observable(null), ko.observable(null), ko.observable(null), ko.observable(null)
      ];
      vm.model().update(null, null, null, null, currentDate, '9999/12/31', null, vm.externalCode);

      vm.isNewMode(true);
      $('#KMT001_21').focus();
    }

    saveRegistrationWork() {
      const vm = this;
       /*  vm.$blockui('show');
       vm.$ajax(PATH.saveRegistrationWork, { parrams: null }).done(() => {
         vm.$dialog.info({messageId: 'Mgs_15'}).then(() => {
           vm.getWorkList();
           vm.$blockui('hide');
         });
       }).fail((error) => {
         vm.$dialog.error({messageId: error.messageId}).then(() => {
           vm.$blockui('hide');
         });
       }); */
    }

    deleteRegistrationWork() {
      const vm = this;   
      vm.getNextPreviousItem(vm.currentCode());
      /*  vm.$blockui('show');
       vm.$ajax(PATH.getRegistrationWork, { workId: vm.currentCode() }).done((data) => {
         vm.$dialog.info({messageId: 'Msg_16'}).then(() => {
           vm.getNextPreviousItem(vm.currentCode());
           vm.$blockui('hide');
         });
       }).fail((error) => {
         vm.$dialog.error({messageId: error.messageId}).then(() => {
           vm.$blockui('hide');
         });
       }); */
    }

    loadRegistrationWork() {
      const vm = this;
      /*vm.$blockui('show');
      vm.$ajax(PATH.getRegistrationWork, { workId: value }).done((data) => {
        if(data) {          
          vm.model().update(data);
          vm.isNewMode(false);
        }
        vm.$blockui('hide');
      }).fail((error) => {
        vm.$dialog.error({messageId: error.messageId}).then(() => {
          vm.$blockui('hide');
        });
      }); */

      vm.model().update(vm.currentCode(), 'B', 'C', '#bf0', '2002/12/31', '9999/12/31', 'tets', vm.externalCode);
      vm.isNewMode(false);
    }

    getRegistrationWorkList() {
      const vm = this;
      vm.registrationWorkList([
        { code: 'A0000000000000000001', name: 'Text 1' },
        { code: 'A0000000000000000002', name: 'Text 2' },
        { code: 'A0000000000000000003', name: 'Text 3 + 作業 1 + 作業 1 + 作業 1 + 作業 1 + 作業 1 + 作業 1 + 作業 1 + 作業 1' },
      ]);

      if (_.isNil(vm.currentCode()) && vm.registrationWorkList().length > 0) {
        let firstItem = _.head(vm.registrationWorkList());
        vm.currentCode(firstItem.code);
      }
    }

    getWorkList() {
      const vm = this;

      vm.workList = ko.observableArray([
        { code: '001', name: '作業 1' },
        { code: '002', name: '作業 2' },
        { code: '003', name: '作業 3' },
      ])
    }

    getNextPreviousItem(codeBeforeRemove: string) {
      const vm = this;
      let currentCode: string = null;
      //delete from DB

      let findIndex = _.findIndex(vm.registrationWorkList(), (x) => { return x.code === codeBeforeRemove });
      if(vm.registrationWorkList().length > 1) {
        if( findIndex === vm.registrationWorkList().length - 1) 
          findIndex = findIndex - 1;
        else 
          findIndex = findIndex + 1;

        let nextItem = vm.registrationWorkList()[findIndex];
        if(!_.isNil(nextItem)) {
          currentCode = nextItem.code;
        }

        let registrationList = _.filter(vm.registrationWorkList(), (x) => { return x.code !== codeBeforeRemove });
        vm.registrationWorkList.removeAll();
        vm.registrationWorkList(registrationList);
        vm.currentCode(currentCode);
      } else {
        vm.registrationWorkList.removeAll();
        vm.currentCode(null);
        vm.addNewRegistrationWork();
      }
    }

  }

  export class ModelItem {
    code: KnockoutObservable<string> = ko.observable(null);
    name: KnockoutObservable<string> = ko.observable(null);
    abbreviatedName: KnockoutObservable<string> = ko.observable(null);
    color: KnockoutObservable<string> = ko.observable(null);
    expStartDate: KnockoutObservable<string> = ko.observable(null);
    expEndDate: KnockoutObservable<string> = ko.observable(null);
    remarks: KnockoutObservable<string> = ko.observable(null);
    externalCode: KnockoutObservable<any> = ko.observable(null);

    constructor(code?: string, name?: string, abbreviatedName?: string, color?: string,
      expStartDate?: string, expEndDate?: string, remarks?: string, externalCode?: Array<string>) {
      this.code(code);
      this.name(name);
      this.abbreviatedName(abbreviatedName);
      this.color(color);
      this.expStartDate(expStartDate);
      this.expEndDate(expEndDate);
      this.remarks(remarks);
      this.externalCode(externalCode);
    }

    update(code?: string, name?: string, abbreviatedName?: string, color?: string,
      expStartDate?: string, expEndDate?: string, remarks?: string, externalCode?: Array<string>) {
      this.code(code);
      this.name(name);
      this.abbreviatedName(abbreviatedName);
      this.color(color);
      this.expStartDate(expStartDate);
      this.expEndDate(expEndDate);
      this.remarks(remarks);
      this.externalCode(externalCode);
    }
  }
}