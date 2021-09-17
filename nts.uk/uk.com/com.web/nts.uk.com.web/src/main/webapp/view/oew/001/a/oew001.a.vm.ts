/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.com.view.oew001.a {
  import model = oew001.share.model;

  const API = {
    initEquipmentInfo: "com/screen/oew001/initEquipmentInfo",
    initEquipmentSetting: "com/screen/oew001/initEquipmentSetting",
    getResultHistory: "com/screen/oew001/getResultHistory",
    getEquipmentInfoList: "com/screen/oew001/getEquipmentInfoList",
  };

  @bean()
  export class ScreenModel extends ko.ViewModel {
    selectedEquipmentClsCode: KnockoutObservable<string> = ko.observable("");
    equipmentClsName: KnockoutObservable<string> = ko.observable("");
    equipmentInfoList: KnockoutObservableArray<any> = ko.observableArray([]);
    selectedEquipmentInfoCode: KnockoutObservable<string> = ko.observable("");
    yearMonth: KnockoutObservable<string> = ko.observable("");

    // dto
    equipmentClassification: KnockoutObservable<model.EquipmentClassificationDto> = ko.observable(null);
    equipmentInformationList: KnockoutObservableArray<model.EquipmentInformationDto> = ko.observableArray([]);
    itemSettings: KnockoutObservableArray<model.EquipmentUsageRecordItemSettingDto> = ko.observableArray([]);
    formatSetting: KnockoutObservable<model.EquipmentPerformInputFormatSettingDto> = ko.observable(null);

    // grid
    columns: KnockoutObservableArray<any> = ko.observableArray([]);
    staticColumns: any[];
    dataSource: KnockoutObservableArray<DisplayData> = ko.observableArray([]);

    created() {
      const vm = this;
      vm.staticColumns = [
        { headerText: "", key: "id", dataType: "string", ntsControl: "Label" },
        { headerText: "", key: "editRecord", width: "80px", dataType: "string", ntsControl: "EditButton" },
        { headerText: vm.$i18n("OEW001_19"), key: "useDate", width: "100px", dataType: "string" },
        { headerText: vm.$i18n("OEW001_20"), key: "employeeName", width: "200px", dataType: "string" }
      ];
      vm.columns.push(_.clone(vm.staticColumns));

      // subscribe
      vm.equipmentClassification.subscribe(value => {
        vm.selectedEquipmentClsCode(value.code);
        vm.equipmentClsName(value.name);
      });
      vm.equipmentInformationList.subscribe(value => vm.equipmentInfoList(_.clone(value)));
    }

    mounted() {
      const vm = this;
      vm.yearMonth(moment.utc().format("YYYYMM"));
      vm.initScreen();
    }

    // Ａ：設備利用実績の一覧の初期表示
    private initScreen() {
      const vm = this;
      vm.$blockui("grayout");
      const param = vm.restoreCharacteristic();
      vm.initEquipmentInfo(param).then(() => vm.initEquipmentSetting())
        .always(() => vm.$blockui("clear"));
    }

    // Ａ1：設備分類と設備情報を取得する
    private initEquipmentInfo(param: any): JQueryPromise<any> {
      const vm = this;
      return vm.$ajax(API.initEquipmentInfo, param).then(result => {
        if (result) {
          vm.equipmentClassification(result.equipmentClassification);
          vm.equipmentInformationList(result.equipmentInformationList);
        }
      }).fail(err => vm.$dialog.error(err.msgId));
    }

    // Ａ2：「設備利用実績の項目設定」を取得する
    private initEquipmentSetting(): JQueryPromise<any> {
      const vm = this;
      return vm.$ajax(API.initEquipmentSetting).then(result => {
        if (result) {
          vm.itemSettings(result.itemSettings);
          vm.formatSetting(result.formatSetting);

          // Init empty grid
          vm.columns(_.clone(vm.staticColumns));
          _.each(vm.itemSettings(), (data, index) => {
            const displaySetting = _.find(vm.formatSetting().itemDisplaySettings, { "itemNo": data.itemNo });
            vm.columns().push(vm.getColumnHeader(index, data.items.itemName, data.inputControl.itemCls, displaySetting.displayWidth));
          });
          vm.initGrid();
        }
      }).fail(err => vm.$dialog.error(err.msgId));
    }

    private initGrid() {
      const vm = this;
      $("#A6").ntsGrid({
        // width: "1220px",
        height: '259px',
        rows: 10,
        dataSource: vm.dataSource(),
        primaryKey: 'id',
        rowVirtualization: true,
        virtualization: true,
        virtualizationMode: 'continuous',
        hidePrimaryKey: true,
        columns: vm.columns(),
        ntsControls: [
          { name: 'EditButton', text: vm.$i18n("OEW001_21"), click: (item: any) => vm.openDialogB(item), controlType: 'Button', enable: true }
        ],
        features: [],
        ntsFeatures: []
      });
    }

    public openDialogB(item?: any) {
      const vm = this;
      vm.$window.modal("/view/oew/001/b/index.xhtml")
        .then(result => {
          
        });
    }

    public openDialogC() {
      const vm = this;
      vm.$window.modal("/view/oew/001/c/index.xhtml")
        .then(result => {
          
        });
    }

    public openDialogD() {
      const vm = this;
      vm.$window.modal("/view/oew/001/d/index.xhtml", vm.selectedEquipmentClsCode())
        .then(result => {
          vm.selectedEquipmentClsCode(result.code);
          vm.equipmentClsName(result.name);
        });
    }

    public performSearchData() {
      const vm = this;
      const currentSid = __viewContext.user.employeeId;
      vm.$blockui("grayout");
      const param = {
        equipmentClsCode: vm.selectedEquipmentClsCode(),
        equipmentCode: vm.selectedEquipmentInfoCode(),
        ym: vm.yearMonth()
      };
      vm.$ajax(API.getResultHistory, param).then(result => {
        // Create data source
        vm.dataSource(_.map(result.equipmentDatas, (data: model.EquipmentDataDto) => {
          let displayData = new DisplayData(data, result.employeeInfos, currentSid);
          displayData.createOptionalItems(data, vm.itemSettings(), vm.formatSetting());
          return displayData;
        }));
        // Sort by 日付、社員CD、入力日
        vm.dataSource(_.orderBy(vm.dataSource(), ['useDate', 'scd', 'inputDate']));
        if (vm.dataSource().length > 0) {
          // Get first data for column format reference
          const optionalItems = vm.dataSource()[0].optionalItems;
          // Create column info
          vm.columns(_.clone(vm.staticColumns));
          _.each(optionalItems, (data, index) => vm.columns().push(vm.getColumnHeader(index, data.itemName, data.itemCls, data.width)));
          // Init grid
          vm.initGrid();
        }
      }).always(() => vm.$blockui("clear"));
    }
    
    private saveCharacteristic() {
      const vm = this;
      (nts.uk as any).characteristics.save("OEW001_設備利用実績の入力", {
        equipmentCode: vm.selectedEquipmentInfoCode(),
        equipmentClsCode: vm.selectedEquipmentClsCode()
      });
    }

    private restoreCharacteristic(): any {
      return (nts.uk as any).characteristics.restore("OEW001_設備利用実績の入力");
    }

    private getColumnHeader(index: number, headerText: string, itemCls: number, width: number): any {
      const key = `optionalItems[${index}]`;
      const columnHeader = { 
        headerText: headerText, 
        template: '<div class="limited-label">${' + key + '}</div>', 
        dataType: model.getDataType(itemCls), key: key, width: `${width}px`
      };
      return columnHeader;
    }
  }

  export class DisplayData {
    id: string = nts.uk.util.randomId();
    editRecord: number;
    inputDate: string;
    useDate: string;
    sid: string;
    scd: string;
    employeeName: string;
    optionalItems: OptionalItem[];

    constructor(data: model.EquipmentDataDto, employees: model.EmployeeInfoDto[], currentSid: string) {
      const employeeInfo = _.find(employees, { employeeId: data.sid });
      this.editRecord = data.sid === currentSid ? 1 : 0;
      this.inputDate = data.inputDate;
      this.useDate = data.useDate;
      this.sid = data.sid;
      this.scd = employeeInfo.employeeCode;
      this.employeeName = employeeInfo.businessName;
    }

    // Create grid optional datas from acquired datas
    public createOptionalItems(data: model.EquipmentDataDto, itemSettings: model.EquipmentUsageRecordItemSettingDto[],
      formatSetting: model.EquipmentPerformInputFormatSettingDto): void {

      // Map datas to grid
      this.optionalItems = _.chain(data.itemDatas).map(itemData => {
        const itemSetting = _.find(itemSettings, { itemNo: itemData.itemNo });
        const itemDisplay = _.find(formatSetting.itemDisplaySettings, { itemNo: itemData.itemNo });
        const itemCls = itemSetting.inputControl.itemCls;

        return new OptionalItem({
          itemName: itemSetting.items.itemName,
          itemCls: itemCls,
          value: itemData.actualValue,
          unit: itemSetting.items.unit,
          // TODO: gán tạm bằng 項目表示.表示幅
          width: itemDisplay.displayWidth,
          displayOrder: itemDisplay.displayOrder
        });
        // Sort optionalItems by 表示順番
      }).orderBy("displayOrder").value();
    }
  }

  export class OptionalItem {
    itemName: string;
    itemCls: number;
    value: string;
    unit: string;
    width: number;
    displayOrder: number;

    constructor(init?: Partial<OptionalItem>) {
      $.extend(this, init);
    }
  }
}
