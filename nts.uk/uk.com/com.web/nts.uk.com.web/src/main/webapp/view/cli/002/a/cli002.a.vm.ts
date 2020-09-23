/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.com.view.cli002.a {

  const API = {
    findBySystem: "sys/portal/pginfomation/findBySystem",
    updateLogSetting: "sys/portal/logsettings/update"
  }

  const mgrid = nts.uk.ui.mgrid as any;
  const { MGrid, color } = mgrid as Mgrid;

  @bean()
  export class ScreenModel extends ko.ViewModel {
    public systemList: KnockoutObservableArray<SystemTypeModel> = ko.observableArray([
      new SystemTypeModel({
        index: 0,
        localizedName: this.$i18n("Enum_SystemType_PERSON_SYSTEM"),
      }),
      new SystemTypeModel({
        index: 1,
        localizedName: this.$i18n("Enum_SystemType_ATTENDANCE_SYSTEM"),
      }),
      new SystemTypeModel({
        index: 2,
        localizedName: this.$i18n("Enum_SystemType_PAYROLL_SYSTEM"),
      }),
      new SystemTypeModel({
        index: 3,
        localizedName: this.$i18n("Enum_SystemType_OFFICE_HELPER"),
      }),
    ]);

    public dataSourceItem: KnockoutObservableArray<PGInfomationModel> = ko.observableArray([]);
    public selectedSystemCode: KnockoutObservable<number> = ko.observable(null);

    public systemColumns = [
      {
        headerText: "",
        prop: "index",
        width: 160,
        hidden: true,
      },
      {
        headerText: this.$i18n("CLI002_3"),
        prop: "localizedName",
        width: 160,
      }
    ];

    mounted() {
      const vm = this;
      vm.selectedSystemCode.subscribe((newValue) => {
        if ($("#item-list").data("mGrid")) {
          $("#item-list").mGrid("destroy");
        }
        // アルゴリズム「ログ設定画面表示」を実行する
        vm.getData(newValue);
      });
      vm.selectedSystemCode(0);
    }

    /**
     *
     */
    public register() {
      const vm = this;
      const logSettings: LogSettingSaveDto[] = _.map(vm.dataSourceItem(), item => new LogSettingSaveDto({
        system: vm.selectedSystemCode(),
        programId: item.programId,
        menuClassification: item.menuClassification,
        loginHistoryRecord: item.logLoginDisplay ? 1 : 0,
        startHistoryRecord: item.logStartDisplay ? 1 : 0,
        updateHistoryRecord: item.logUpdateDisplay ? 1 : 0,
      }));
      const command = new LogSettingSaveCommand({
        logSettings: logSettings
      });
      // ログ設定更新
      vm.$blockui('grayout');
      vm.$ajax(API.updateLogSetting, command)
        .then(() => {
          vm.$blockui('clear');
          // 情報メッセージ（Msg_15）を表示する
          vm.$dialog.alert({ messageId: 'Msg_15' });
        })
        .always(() => vm.$blockui("clear"));
    }

    /**
     * ログ設定画面を表示する
     * @param systemType
     */
    private getData(systemType: number) {
      const vm = this;
      vm.$blockui("grayout");
      vm.$ajax(`${API.findBySystem}/${systemType}`)
        .then((response: PGListDto[]) => {
          const listPG: PGInfomationModel[] = _.map(response, (item, index) => new PGInfomationModel({
            rowNumber: index + 1,
            functionName: item.functionName,
            logLoginDisplay: item.loginHistoryRecord.usageCategory === 1,
            logStartDisplay: item.bootHistoryRecord.usageCategory === 1,
            logUpdateDisplay: item.editHistoryRecord.usageCategory === 1,
            programId: item.programId,
            menuClassification: item.menuClassification
          }));
          vm.dataSourceItem(listPG);
          vm.initGrid(response);
        })
        .always(() => vm.$blockui("clear"));
    }

    public updateData(rowNumber: number, columnName: string, val: any) {
      const vm = this;
      const newArray = _.map(vm.dataSourceItem(), (item: PGInfomationModel) => {
        if (item.rowNumber === rowNumber) {
          if (columnName === 'logLoginDisplay') {
            item.logLoginDisplay = val;
          } else if (columnName === 'logStartDisplay') {
            item.logStartDisplay = val;
          } else if (columnName === 'logUpdateDisplay') {
            item.logUpdateDisplay = val;
          }
        }
        return item;
      });
      vm.dataSourceItem(newArray);
    }

    private initGrid(response: PGListDto[]) {
      const vm = this;
      const $mgrid = $("#item-list");
      const statesTable = [];
      response.forEach((item: PGListDto, index) => {
        // ※１ PG一覧．PG情報．ログイン履歴の記録．活性区分　＝　True
        if (item.loginHistoryRecord.activeCategory === 0) {
          statesTable.push(new CellStateModel({
            rowId: index + 1,
            columnKey: "logLoginDisplay",
            state: [color.Lock],
          }));
        }
        // ※2 PG一覧．PG情報．起動履歴記録．活性区分　＝　True
        if (item.bootHistoryRecord.activeCategory === 0) {
          statesTable.push(new CellStateModel({
            rowId: index + 1,
            columnKey: "logStartDisplay",
            state: [color.Lock],
          }));
        }
        // ※3 PG一覧．PG情報．起動履歴記録．活性区分　＝　True
        if (item.editHistoryRecord.activeCategory === 0) {
          statesTable.push(new CellStateModel({
            rowId: index + 1,
            columnKey: "logUpdateDisplay",
            state: [color.Lock],
          }));
        }
      });

      new MGrid($mgrid.get(0), {
        height: "900px",
        width: "600px",
        headerHeight: '60px',
        primaryKey: "rowNumber",
        primaryKeyDataType: "number",
        rowVirtualization: true,
        virtualization: true,
        virtualizationMode: 'continuous',
        enter: 'right',
        dataSource: vm.dataSourceItem(),
        columns: [
          { headerText: "", key: "rowNumber", dataType: "number", width: "30px" },
          { headerText: this.$i18n("CLI002_7"), key: "functionName", dataType: "string", width: "365x", ntsControl: 'Label' },
          {
            headerText: this.$i18n("CLI002_4"),
            group: [
              { headerText: "", key: "logLoginDisplay", dataType: "boolean", width: "200px", ntsControl: "Checkbox", checkbox: true, hidden: false }
            ]
          },
          {
            headerText: this.$i18n("CLI002_5"),
            group: [
              { headerText: "", key: "logStartDisplay", dataType: "boolean", width: "200px", ntsControl: "Checkbox", checkbox: true, hidden: false }
            ]
          },
          {
            headerText: this.$i18n("CLI002_6"),
            group: [
              { headerText: "", key: "logUpdateDisplay", dataType: "boolean", width: "200px", ntsControl: "Checkbox", checkbox: true, hidden: false }
            ]
          },
        ],
        features: [
          {
            name: 'CellStyles',
            states: statesTable
          },
          // {
          //     name: 'ColumnFixing',
          //     fixingDirection: '',
          //     showFixButtons: false,
          //     columnSettings: [
          //         { columnKey: 'rowNumber', isFixed: true }
          //     ]
          // }
        ],
        ntsFeatures: [],
        ntsControls: [
          {
            name: 'Checkbox', options: { value: 1, text: '' }, optionsValue: 'value', optionsText: 'text', controlType: 'CheckBox', enable: true,
            onChange: (rowNumber: number, columnName: string, val: any) => vm.updateData(rowNumber, columnName, val)
          }
        ],
      }).create();
    }
  }

  interface Mgrid {
    color: COLOR;
    MGrid: { new(el: HTMLElement, option: any): { create: () => void } };
  }

  interface COLOR {
    ALL: string[];
    Alarm: "mgrid-alarm";
    Calculation: "mgrid-calc";
    Disable: "mgrid-disable";
    Error: "mgrid-error";
    HOVER: "ui-state-hover";
    Hide: "mgrid-hide";
    Lock: "mgrid-lock";
    ManualEditOther: "mgrid-manual-edit-other";
    ManualEditTarget: "mgrid-manual-edit-target";
    Reflect: "mgrid-reflect";
  }

  export interface TargetSettingDto {
    usageCategory: number,
    activeCategory: number
  }

  export interface PGListDto {
    functionName: string,
    loginHistoryRecord: TargetSettingDto,
    bootHistoryRecord: TargetSettingDto,
    editHistoryRecord: TargetSettingDto,
    programId: string,
    menuClassification: number,
  }

  export class PGInfomationModel {
    rowNumber: number;
    functionName: string;
    logLoginDisplay: boolean;
    logStartDisplay: boolean;
    logUpdateDisplay: boolean;
    programId: string;
    menuClassification: number;

    constructor(init?: Partial<PGInfomationModel>) {
      $.extend(this, init);
    }
  }

  export class LogSettingSaveDto {
    system: number;
    programId: string;
    menuClassification: number;
    loginHistoryRecord: number;
    startHistoryRecord: number;
    updateHistoryRecord: number;

    constructor(init?: Partial<LogSettingSaveDto>) {
      $.extend(this, init);
    }
  }

  export class LogSettingSaveCommand {
    logSettings: LogSettingSaveDto[];

    constructor(init?: Partial<LogSettingSaveCommand>) {
      $.extend(this, init);
    }
  }

  export class SystemTypeModel {
    index: number;
    localizedName: string;

    constructor(init?: Partial<SystemTypeModel>) {
      $.extend(this, init);
    }
  }

  export class CellStateModel {
    rowId: number;
    columnKey: string;
    state: any[];

    constructor(init?: Partial<CellStateModel>) {
      $.extend(this, init);
    }
  }
}
