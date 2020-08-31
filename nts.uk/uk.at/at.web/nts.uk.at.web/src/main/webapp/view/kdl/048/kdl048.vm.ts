/// <reference path='../../../lib/nittsu/viewcontext.d.ts' />
module nts.uk.at.view.kdl048.screenModel {
  import dialog = nts.uk.ui.dialog.info;
  import text = nts.uk.resource.getText;
  import formatDate = nts.uk.time.formatDate;
  import block = nts.uk.ui.block;
  import jump = nts.uk.request.jump;
  import getShared = nts.uk.ui.windows.getShared;
  import setShared = nts.uk.ui.windows.setShared;
  import alError = nts.uk.ui.dialog.alertError;
  import ListType = kcp.share.list.ListType;
  import UnitModel = kcp.share.list.UnitModel;

  @bean()
  export class ViewModel extends ko.ViewModel {
    //
    initParam: any;
    objectDisplay: Display;
    //A1_1,2,3,4
    titleLine:  KnockoutObservable<TitleLineObject> = ko.observable();
    //A3_1,2
    itemNameLine: KnockoutObservable<ItemNameLineObject> = ko.observable();
    // A3_2
    texteditor: any;
    attendanceRecordName: KnockoutObservable<string> = ko.observable("");
    // A4
    ntsPanel: any;
    // A5_2
    ntsComboBox:any
    attributeObject: KnockoutObservable<AttributeObject> = ko.observable();
    valueCb:KnockoutObservable<number> = ko.observable(0); 
    // A5_3
    ntsSearchBox: any;
    currentCodeList:KnockoutObservableArray<any> = ko.observableArray([]);
    headers: any = ko.observableArray([text('KDL048_6'),text('KDL048_7')]);
    columns: KnockoutObservableArray<any>;
    currentCode: KnockoutObservable<any> = ko.observable();
    // A6
    ntsTreeGridView: any;
    diligenceData: KnockoutObservableArray<DiligenceProject> = ko.observableArray([]);
    tableDataA6: DiligenceProject[] = [];

    // A9
    columnSelectedItemList: KnockoutObservableArray<any> = ko.observableArray([
      { headerText: text('KDL048_11'), prop: 'operator', width: 55, columnCssClass:"text-center" },
      { headerText: text('KDL048_6'), prop: 'itemId', width: 70 },
      { headerText: text('KDL048_7'), prop: 'name', width: 200 },
      ]);
    paramSelectedTimeList: KnockoutObservableArray<SelectedTimeListParam> = ko.observableArray([]);
    dataSelectedItemList: KnockoutObservableArray<SelectedTimeList> = ko.observableArray([]);
    currentCodeList2: KnockoutObservableArray<any> = ko.observableArray([]);

    created(){
      const vm = this;
      //get params
      vm.initParam = getShared('attendanceItem');
      vm.objectDisplay = vm.initParam;
      //Setting data
      vm.diligenceData(_.orderBy(vm.initParam.diligenceProjectList, ['id'], ['asc']));
      vm.titleLine(vm.initParam.titleLine);
      vm.attributeObject(vm.initParam.attribute);
      vm.itemNameLine(vm.initParam.itemNameLine);
      vm.paramSelectedTimeList(vm.initParam.selectedTimeList);
      vm.attendanceRecordName(vm.initParam.itemNameLine.name)
     
      //data for table
      vm.tableDataA6 = vm.initParam.diligenceProjectList;
      // A3
      vm.texteditor = {
        name: text('KDL048_3'),
        value: vm.attendanceRecordName,
        constraint: 'ItemName',
        option: ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
        textmode: "text",
        width: "100px",
        textalign: "left"
        })),
        enable: vm.itemNameLine().displayInputCategory === 2,
        readonly: vm.itemNameLine().displayInputCategory !== 2
      };

      // A4
      vm.ntsPanel = {
          direction: 'none',
          showIcon: true,
          visible: true
        };
        
      // A5_3
      vm.ntsSearchBox =  {
        searchMode: 'filter',
        targetKey: 'id', 
        comId: 'multi-list', 
        items: vm.diligenceData, 
        selected: vm.currentCodeList, 
        selectedKey: 'id', 
        fields: ['name', 'id'], 
        mode: 'igGrid'
      };

      // A5_2
      vm.ntsComboBox = {
          options: vm.attributeObject().attributeList,
          optionsValue: 'attendanceTypeCode',
          visibleItemsCount: 4,
          value: vm.valueCb,
          optionsText: 'attendanceTypeName',
          enable: vm.attributeObject().selectionCategory === 2,
          columns: [
            { prop: 'attendanceTypeName', length: 10 }
          ]
        }
      //Columns of table A6
      vm.columns = ko.observableArray([
        { headerText: text('KDL048_6'), prop: 'id', width: 70 },
        { headerText: text('KDL048_7'), prop: 'name', width: 200 },
      ]);
      
      //event when change itemCombo
      vm.valueCb.subscribe(function(codeChange) { vm.onChangeItemCombo(codeChange); });
    }

    mounted(){
      // Fill data to combobox
      const vm = this;
      let list :KnockoutObservableArray<any> = ko.observableArray([]);
      vm.valueCb(vm.attributeObject().selected);
      // fill data to table
      _.each(vm.paramSelectedTimeList(),(item2:SelectedTimeListParam)=>{
        _.each(vm.diligenceData(),(item1: DiligenceProject) => {
        if(parseInt(item1.id) === parseInt(item2.itemId) ){
          let data: SelectedTimeList = new SelectedTimeList();
          let data1: DiligenceProject = new DiligenceProject();
          data.name = item1.name;
          data.itemId = item2.itemId;
          data.operator = item2.operator;
          data1.id = item1.id;
          vm.dataSelectedItemList.push(data)
          list.push(data1);
         }
        })
      })
      // vm.diligenceData().remove(list);
      _.each(list(), (item) => {
        vm.diligenceData().filter((items) => {
          if (items.id === parseInt(item.id)) {
            vm.diligenceData.remove(items);
          };
        })
      });
    }

    // event when change item combo box
    private onChangeItemCombo(codeChange) {
      const vm = this;
      if (codeChange === 0) {
        return;
      }
      vm.currentCodeList2([]);
      vm.dataSelectedItemList([]);
      let name;
      _.each(vm.attributeObject().attributeList, (itemcb) => {
        if(itemcb.attendanceTypeCode === codeChange){
          name = itemcb.attendanceTypeName
        }
      });
      const tableDatas = _.orderBy(
        vm.tableDataA6.filter(data => data.name.indexOf(name) != -1),
          ['code'], ['asc']
        );
      if (tableDatas.length === 0) {
        vm.diligenceData([]);
      }else{
        vm.diligenceData(tableDatas);
        vm.diligenceData(_.orderBy(vm.diligenceData(), ['id'], ['asc']));
        vm.currentCodeList.push(vm.diligenceData()[0].id);
      }
    }
  
    // Event when click +
    onClickPlus (){
      const vm = this;
      vm.currentCodeList2.removeAll();
      _.each(vm.currentCodeList(), (code) => {
        vm.diligenceData().filter((e: DiligenceProject) => {
          if (e.id === parseInt(code)) {
            let data: SelectedTimeList = new SelectedTimeList();
            let dataParam: SelectedTimeListParam = new SelectedTimeListParam();
            data.itemId = e.id;
            data.operator = text('KDL048_8');
            data.name = e.name;
            dataParam.itemId = e.id;
            dataParam.operator = text('KDL048_8');
            vm.dataSelectedItemList.push(data);
            vm.paramSelectedTimeList.push(dataParam);
            vm.currentCodeList2().push(e.id);
            vm.diligenceData.remove(e);
          };
        })
      });
      vm.dataSelectedItemList(_.orderBy(vm.dataSelectedItemList(), ['itemId'], ['asc']));
    }

     // Event when click -
     onClickMinus() {
      const vm = this;
      vm.currentCodeList2.removeAll();
        _.each(vm.currentCodeList(), (code) => {
          vm.diligenceData().filter((e: DiligenceProject) => {
            if (e.id === parseInt(code)) {
              let data: SelectedTimeList = new SelectedTimeList();
              let dataParam: SelectedTimeListParam = new SelectedTimeListParam();
              data.itemId = e.id;
              data.operator = text('KDL048_9');
              data.name = e.name;
              dataParam.itemId = e.id;
              dataParam.operator = text('KDL048_9');
              vm.dataSelectedItemList.push(data);
              vm.paramSelectedTimeList.push(dataParam);
              vm.currentCodeList2().push(e.id);
              vm.diligenceData.remove(e);
            };
          })
        });
      vm.dataSelectedItemList(_.orderBy(vm.dataSelectedItemList(), ['itemId'], ['asc']));
    }

    // Event when click <<
    onClickPrevAll(){
      const vm = this;
      _.each(vm.currentCodeList2(),(code)=>{
        vm.dataSelectedItemList().filter((e: SelectedTimeList) => {
        if(e.itemId === parseInt(code)){
          let data: DiligenceProject = new DiligenceProject();
          let dataParam: SelectedTimeListParam = new SelectedTimeListParam();
          data.id = e.itemId;
          data.name = e.name;
          dataParam.itemId = e.itemId;
          dataParam.operator = e.operator;
          vm.diligenceData.push(data)
          vm.dataSelectedItemList.remove(e);
          vm.paramSelectedTimeList.remove(dataParam);
          }
        })
      })
      vm.diligenceData(_.orderBy(vm.diligenceData(), ['id'], ['asc']));
    }
  
    onClickOk() {
      const vm = this;
      $('#A3_2').trigger('validate');
      _.defer(() => {
        if (!$('#A3_2').ntsError('hasError')|| vm.attendanceRecordName() === '') {
          // 項目名行の表示フラグ == True：表示すると表示入力区分 == ２：入力可能
          if (vm.itemNameLine().displayFlag && vm.itemNameLine().displayInputCategory === 2) {
            // shared with value of A3_2, A5_2, A9_2_1, A9_2_2
            vm.objectDisplay.itemNameLine.name = vm.attendanceRecordName();
            vm.objectDisplay.attribute.selected = vm.valueCb();
            vm.objectDisplay.selectedTimeList  = vm.paramSelectedTimeList();
            setShared('attendanceRecordExport', vm.objectDisplay);
          }
          // 項目名行の表示フラグ == False：表示しない
          // 項目名行の表示フラグ == True：表示すると表示入力区分 == １：表示のみ
          if (!vm.itemNameLine().displayFlag || vm.itemNameLine().displayInputCategory === 1) {
            // shared with value of A5_2, A9_2_1, A9_2_2
            vm.objectDisplay.attribute.selected = vm.valueCb();
            vm.objectDisplay.selectedTimeList   = vm.paramSelectedTimeList();
            setShared('attendanceRecordExport',  vm.objectDisplay);
          }
          vm.closeDialog();
        }
      });
    }

    closeDialog() {
        nts.uk.ui.windows.close();
    }
  }

  export class ListType {
    static EMPLOYMENT = 1;

    static Classification = 2;
    static JOB_TITLE = 3;
    static EMPLOYEE = 4;
  }

  export interface UnitModel {
    id?: string;
        code: string;
        name?: string;
        workplaceName?: string;
        isAlreadySetting?: boolean;
        optionalColumn?: any;
  }
  
  export class SelectType {
    static SELECT_BY_SELECTED_CODE = 1;
    static SELECT_ALL = 2;
    static SELECT_FIRST_ITEM = 3;
    static NO_SELECT = 4;
  }

   // Display object mock
  export class Display {
    // タイトル行
    titleLine: TitleLineObject = new TitleLineObject();
    // 項目名行
    itemNameLine: ItemNameLineObject = new ItemNameLineObject();
    // 属性
    attribute: AttributeObject = new AttributeObject();
    // List<勤怠項目>
    diligenceProjectList: DiligenceProject[] = [];
    // List<選択済み勤怠項目>
    selectedTimeList:  SelectedTimeListParam[] = [];

constructor(init?: Partial<Display>) {
  $.extend(this, init);
}
  }
  
  export class SelectedTimeList {
    // 項目ID
    itemId: any | null = null;
    // 演算子
    operator: String | null = null;

    name: String | null = null;
    constructor(init?: Partial<TitleLineObject>) {
      $.extend(this, init);
    }
}

export class SelectedTimeListParam {
  // 項目ID
  itemId: any | null = null;
  // 演算子
  operator: String | null = null;

  constructor(init?: Partial<TitleLineObject>) {
    $.extend(this, init);
  }
}

  // タイトル行
  export class TitleLineObject {
    // 表示フラグ
    displayFlag: boolean = false;
    // 出力項目コード
    layoutCode: String | null = null;
    // 出力項目名
    layoutName: String | null = null;
    // コメント
    directText: String | null = null;

constructor(init?: Partial<TitleLineObject>) {
  $.extend(this, init);
}
  }

  // 項目名行
  export class ItemNameLineObject {
    // 表示フラグ
    displayFlag: boolean = false;
    // 表示入力区分
    displayInputCategory: number = 1;
    // 名称
    name: string | null = null;

constructor(init?: Partial<ItemNameLineObject>) {
  $.extend(this, init);
}
  }

  // 属性
  export class AttributeObject {
    // 選択区分
    selectionCategory: number = 2;
    // List<属性>
    attributeList: Attribute[] = [];
    // 選択済み
    selected: number = 1;

constructor(init?: Partial<AttributeObject>) {
  $.extend(this, init);
}
  }

  export class Attribute {
    attendanceTypeCode: number;
    attendanceTypeName: string;

constructor(init?: Partial<Attribute>) {
  $.extend(this, init);
}
  }

  export class DiligenceProject {
    // ID
    id: any;
    // 名称
    name: any;
    // 属性
    attributes: any;
    // 表示番号
    indicatesNumber: any;

constructor(init?: Partial<DiligenceProject>) {
  $.extend(this, init);
}
  }
}