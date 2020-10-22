/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kml002.e {

  @bean()
  class ViewModel extends ko.ViewModel {    

    listOfStartTimes: KnockoutObservableArray<any> = ko.observableArray([]);

    constructor(params: any) {
      super();
      const vm = this;    
      
      vm.createListOfStartTimes(); 
    }

    created(params: any) {
      const vm = this;
      _.extend(window, { vm });
    }

    mounted(params: any) {
      const vm = this;
    }

    openDialogScreenF() {
      const vm = this;

      vm.$window.modal('/view/kml/002/f/index.xhtml').then(() => {
      });
    }

    closeDialog() {
      const vm = this;
      vm.$window.close();
    }

    createListOfStartTimes() {
      const vm = this;
      var array = [];
      for( let i= 0; i < 24; i++) {
        vm.listOfStartTimes.push( new StartTime(i, false, (i < 10 ? '0' + i : i).toString()));
      }
    }

    removeItem() {

    }
    
    addNewItem() {
      
    }

  }

  export class StartTime {
    id: number = 1;
    isChecked: boolean;
    time: string = '00:00';
    constructor(id?: number, isChecked?: boolean, time?: string) {
        this.isChecked = isChecked;
        this.time = time;
        this.id = id;
    }
  }
}