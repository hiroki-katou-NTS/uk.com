/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />
module nts.uk.at.view.kmt010.a {

  @bean()
  class ViewModel extends ko.ViewModel {

    tabsList: KnockoutObservableArray<any> = ko.observable([]);
    tabs: KnockoutObservableArray<nts.uk.ui.NtsTabPanelModel>;
	  selectedTab: KnockoutObservable<string>;

    multiSelectedId: KnockoutObservable<any> = ko.observableArray([]);
		baseDate: KnockoutObservable<Date>;
		alreadySettingList: KnockoutObservableArray<UnitAlreadySettingModel> = ko.observableArray([]);
		treeGrid: TreeComponentOption;

    workplaceName: KnockoutObservable<string> = ko.observable(null);
    workplaceDataSource: KnockoutObservableArray<UnitModel> = ko.observableArray([]);


    tabWorkSetting: KnockoutObservableArray<WorkItem> = ko.observableArray([]);
    tabWorkSettings: KnockoutObservableArray<any>;
    workFrameNoSelection: KnockoutObservable<number> = ko.observable(0);

    constructor(params: any) {
      super();
      const vm = this;

      vm.tabs = ko.observableArray([
        {id: 'tab-1', title: '作業 1', content: '.tab-content-1', enable: ko.observable(true), visible: ko.observable(true)},
        {id: 'tab-2', title: '作業 2', content: '.tab-content-2', enable: ko.observable(true), visible: ko.observable(true)},
        {id: 'tab-3', title: '作業 3', content: '.tab-content-3', enable: ko.observable(true), visible: ko.observable(true)},
        {id: 'tab-4', title: '作業 4', content: '.tab-content-4', enable: ko.observable(true), visible: ko.observable(true)},
        {id: 'tab-5', title: '作業 5', content: '.tab-content-5', enable: ko.observable(true), visible: ko.observable(true)}
      ]);
      vm.selectedTab = ko.observable('tab-1');

      vm.tabWorkSettings = ko.observableArray([
        ko.observable([]), ko.observable([]), ko.observable([]), ko.observable([]), ko.observable([])
      ]);
      //-----------------------------------------     

      $.when(vm.getAlreadySettingList()).done(() => {

        vm.baseDate = ko.observable(new Date());
        //vm.multiSelectedId = ko.observableArray([]);
        //vm.alreadySettingList = ko.observableArray([]);
        vm.treeGrid = {
            isShowAlreadySet: true,
            isMultipleUse: false,
            isMultiSelect: false,
            startMode: StartMode.WORKPLACE,
            selectedId: vm.multiSelectedId,
            baseDate: vm.baseDate,
            selectType: SelectionType.SELECT_FIRST_ITEM,
            isShowSelectButton: true,
            isDialog: false,
            alreadySettingList: vm.alreadySettingList,
            maxRows: 15,
            tabindex: 5,
            systemType : SystemType.EMPLOYMENT //2
        };

        $('#kcp004').ntsTreeComponent(vm.treeGrid).done(() => {
          vm.workplaceDataSource($('#kcp004').getDataList());        
          vm.multiSelectedId.subscribe((workplaceId) => {                
            vm.getWorkPlaceDetails();
          });
  
          vm.getWorkPlaceDetails();       
        });
      });      
    }

    created(params: any) {
      const vm = this;
    }

    mounted() {
      const vm = this;
    }

    saveRegistrationWork() {

    }

    deleteRegistrationWork() {

    }

    openDialogCDL023() {
      const vm = this;
      console.log( vm.tabWorkSettings());
    }

    openDialogKDL012( tabIndex: number) {
      
    }

    findElement(listItems: Array<any>, wpId: string) {
      const vm = this;
      _.forEach(listItems, (x) => {
        if (wpId === x.id) {
          vm.workplaceName(x.name);
        } else if (x.children.length > 0) {
          vm.findElement(x.children, wpId);
        }
      });
    }

    getWorkPlaceDetails() {
      const vm = this;
      vm.$blockui('grayout');
      
      const workPlaceSelected = $('#kcp004').getRowSelected();        
      if (workPlaceSelected.length > 0 && workPlaceSelected[0].id !== '') {
        vm.findElement( vm.workplaceDataSource(), workPlaceSelected[0].id);
        $('#single-tree-grid-kcp004_container').focus();
      }
      //tabs
      for( let i = 0; i < vm.tabWorkSettings().length; i++) {
        //create data foreach tab
        let tabWorkSetting: Array<any> = [];
        for( let j = 0; j < 5 + i; j++) {
          tabWorkSetting.push(new WorkItem('コード0000000000' + j, vm.workplaceName() + ' ' + j, vm.randomDate(), vm.randomDate()));
        }

        vm.tabWorkSettings()[i](tabWorkSetting);        
      }      

      vm.$blockui('hide');
    } 

    randomDate() {
      let start =  new Date();
      let end =  new Date(9999, 12, 31);

      let date__ = new Date(start.getTime() + Math.random() * (end.getTime() - start.getTime()));
      return moment(date__).format('YYYY/MM/DD');
    }

    getAlreadySettingList() {
      const vm = this;
      vm.alreadySettingList.push({ workplaceId: vm.multiSelectedId(), isAlreadySetting: true });      
    }

    goback() {
      const vm = this;
      vm.$jump("/view/kmt/011/a/index.xhtml", {screen: "KMT010"});
    }
  }

  export enum StartMode {
    WORKPLACE = 0,
    DEPARTMENT = 1
  }

  export enum SelectionType {
    SELECT_BY_SELECTED_CODE = 1,
    SELECT_ALL = 2,
    SELECT_FIRST_ITEM = 3,
    NO_SELECT = 4
  }
	
  export enum SystemType {
    // 個人情報
    PERSONAL_INFORMATION = 1,
     // 就業
    EMPLOYMENT = 2,
     // 給与
    SALARY = 3,
    // 人事
    HUMAN_RESOURCES = 4,
    // 管理者
    ADMINISTRATOR = 5,
  }
      
  export interface UnitAlreadySettingModel {
    workplaceId: string;
    isAlreadySetting: boolean;
  }

  export interface UnitModel {
    id: string;
    code: string;
    name: string;
    nodeText?: string;
    level: number;
    heirarchyCode: string;
    isAlreadySetting?: boolean;
    children: Array<UnitModel>;
  }
	
  export interface RowSelection {
    id: string;
    code: string;
  }

  export class WorkItem {
    code: string;
    name: string;
    startDate: string;
    endDate: string;
    display: string;

    constructor( 
      code?: string,
      name?: string,
      startDate?: string,
      endDate?: string) {

        this.code = code;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.display = startDate + ' ~ ' + endDate;
    }
  }
}