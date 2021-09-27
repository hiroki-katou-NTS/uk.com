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
    selectedEquipmentInfoCode: KnockoutObservable<string> = ko.observable("");
    yearMonth: KnockoutObservable<string> = ko.observable("");
    hasExtractData: KnockoutObservable<boolean> = ko.observable(false);

    // dto
    equipmentClassification: KnockoutObservable<model.EquipmentClassificationDto> = ko.observable(null);
    equipmentInformationList: KnockoutObservableArray<model.EquipmentInformationDto> = ko.observableArray([]);
    itemSettings: KnockoutObservableArray<model.EquipmentUsageRecordItemSettingDto> = ko.observableArray([]);
    formatSetting: KnockoutObservable<model.EquipmentPerformInputFormatSettingDto> = ko.observable(null);

    // grid
    columns: KnockoutObservableArray<any> = ko.observableArray([]);
    staticColumns: any[];
    dataSource: KnockoutObservableArray<DisplayData> = ko.observableArray([]);
    optionalItems: KnockoutObservableArray<model.OptionalItem> = ko.observableArray([]);

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
    }

    mounted() {
      const vm = this;
      vm.yearMonth(moment.utc().format("YYYYMM"));
      vm.initScreen();
      vm.$nextTick(() => $("#A5_1").focus());
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
          vm.selectedEquipmentClsCode.valueHasMutated();
        }
      }).fail(err => vm.$dialog.error({ messageId: err.messageId }));
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
            if (vm.formatSetting().itemDisplaySettings) {
              const displaySetting = _.find(vm.formatSetting().itemDisplaySettings, { "itemNo": data.itemNo });
              vm.columns().push(vm.getColumnHeader(index, data.items.itemName, data.inputControl.itemCls, displaySetting.displayWidth));
            }
          });
          vm.$nextTick(() => vm.initGrid());
        }
      }).fail(err => vm.$dialog.error({ messageId: err.messageId }));
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
        features: [
          {
            name: 'Selection',
            mode: 'row',
            multipleSelection: false
          }
        ],
        ntsFeatures: []
      });
    }

    public openDialogB(item?: DisplayData) {
      const vm = this;
      const param = new model.Oew001BData({
        isNewMode: !item,
        equipmentClsCode: vm.selectedEquipmentClsCode(),
        equipmentClsName: vm.equipmentClsName(),
        equipmentInfoCode: vm.selectedEquipmentInfoCode(),
        equipmentInfoName: _.find(vm.equipmentInformationList(), { "code": vm.selectedEquipmentInfoCode() }).name,
        sid: __viewContext.user.employeeId,
        employeeName: ko.observable(null)
      });
      if (param.isNewMode) {
        param.inputDate = moment.utc().format(model.constants.YYYY_MM_DD);
        param.useDate = ko.observable(moment.utc().format(model.constants.YYYY_MM_DD));
        param.optionalItems = ko.observableArray(vm.optionalItems());
      } else {
        param.inputDate = item.inputDate,
        param.useDate = ko.observable(item.useDate),
        param.optionalItems = ko.observableArray(item.optionalItems)
      }
      
      vm.$window.modal("/view/oew/001/b/index.xhtml", ko.toJS(param))
        .then((result: any) => {
          if (!!result) {
            vm.performSearchData();
            if (result.isSaveSuccess) {
              vm.saveCharacteristic();
            }
          }
          vm.$nextTick(() => $("#A5_1").focus());
        });
    }

    public openDialogC() {
      const vm = this;
      const param = {
        equipmentClsCode: vm.selectedEquipmentClsCode(),
        equipmentClsName: vm.equipmentClsName(),
        equipmentCode: vm.selectedEquipmentInfoCode(),
        yearMonth: vm.yearMonth()
      }
      vm.$window.modal("/view/oew/001/c/index.xhtml", param);
    }

    public openDialogD() {
      const vm = this;
      const paramDialogD = {
        equipmentClsCode: vm.selectedEquipmentClsCode(),
        isOpenFromA: true
      }
      vm.$window.modal("/view/oew/001/d/index.xhtml", paramDialogD)
        .then(result => {
          if (!!result) {
            vm.$blockui("grayout");
            vm.selectedEquipmentClsCode(result.code);
            vm.equipmentClsName(result.name);

            const param = {
              equipmentClsCode: vm.selectedEquipmentClsCode(),
              baseDate: moment.utc().toISOString(),
              isInput: true
            };
            vm.$ajax(API.getEquipmentInfoList, param).then(result => vm.equipmentInformationList(result)).always(() => vm.$blockui("clear"));
            vm.$nextTick(() => $("#A4_3").focus());
          }
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
        vm.hasExtractData(true);
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
          // Init default optionalItems
          _.forEach(optionalItems, data => {
            data.value(null);
            vm.optionalItems.push(data);
          });
          // Create column info
          vm.columns(_.clone(vm.staticColumns));
          _.each(optionalItems, (data, index) => vm.columns().push(vm.getColumnHeader(index, data.itemName, data.itemCls, data.width)));
          // Init grid
          $("#A6").ntsGrid("destroy");
          vm.initGrid();
        }
      }).always(() => vm.$blockui("clear"));
    }
    
    private saveCharacteristic() {
      const vm = this;
      (nts.uk as any).characteristics.save("OEW001_設備利用実績の入力"
      + "_companyId_" + __viewContext.user.companyId
      + "_userId_" + __viewContext.user.employeeId, {
        equipmentCode: vm.selectedEquipmentInfoCode(),
        equipmentClsCode: vm.selectedEquipmentClsCode()
      });
    }

    private restoreCharacteristic(): any {
      return (nts.uk as any).characteristics.restore("OEW001_設備利用実績の入力"
      + "_companyId_" + __viewContext.user.companyId
      + "_userId_" + __viewContext.user.employeeId);
    }

    private getColumnHeader(index: number, headerText: string, itemCls: number, width: number): any {
      const key = `optionalItems[${index}]`;
      const columnHeader = { 
        headerText: headerText, 
        template: '<div class="limited-label">${' + key + '}</div>', 
        dataType: model.getDataType(itemCls), key: `${key}.value()`, width: `${width}px`
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
    optionalItems: model.OptionalItem[];

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
      if (itemSettings.length === 0 || !formatSetting.itemDisplaySettings) {
        this.optionalItems = [];
        return;
      }

      // Map datas to grid
      this.optionalItems = _.chain(data.itemDatas).map(itemData => {
        const itemSetting = _.find(itemSettings, { itemNo: itemData.itemNo });
        const itemDisplay = _.find(formatSetting.itemDisplaySettings, { itemNo: itemData.itemNo });
        const itemCls = itemSetting.inputControl.itemCls;
        const actualValue = itemCls === model.enums.ItemClassification.TIME ? (nts.uk.time as any).format.byId("Time_Short_HM", itemData.actualValue)
                                                                            : itemData.actualValue;

        return new model.OptionalItem({
          itemName: itemSetting.items.itemName,
          itemCls: itemCls,
          value: ko.observable(actualValue),
          width: itemDisplay.displayWidth * model.constants.FIXED_VALUE_A,
          displayOrder: itemDisplay.displayOrder,
        });
        // Sort optionalItems by 表示順番
      }).orderBy("displayOrder").value();
    }
  }
}
