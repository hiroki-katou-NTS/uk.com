/// <reference path='../../../../lib/nittsu/viewcontext.d.ts' />

module nts.uk.com.view.cmf003.i {
  import getText = nts.uk.resource.getText;

  @bean()
  export class ScreenModel extends ko.ViewModel {
    dateValue: KnockoutObservable<any> = ko.observable({
      startDate: null,
      endDate: null,
    });
    searchItems: KnockoutObservableArray<SaveSetHistoryDto> = ko.observableArray([
      { rowNumber: 1, patternCode: '', saveName: 'すべて' }
    ]);
    searchValue: KnockoutObservable<any> = ko.observable();
    resultItems: KnockoutObservableArray<any> = ko.observableArray([]);
    resultValue: KnockoutObservable<any> = ko.observable(null);
    columnHeaders: any[] = [
      { headerText: '', key: 'rowNumber', width: '40px' },
      { headerText: getText("CMF003_306"), key: 'patternCode', width: '100px', ntsControl: 'Label' },
      { headerText: getText('CMF003_307'), key: 'saveName', width: '200px', ntsControl: 'Label' }
    ];
    columnHeadersRes: any[] = [
      { headerText: '', key: 'rowNumber', width: '30px' },
      { headerText: getText('CMF003_309'), key: 'deletedFiles', width: '75px', dataType: 'string', ntsControl: "Button" },
      { headerText: getText('CMF003_310'), key: 'deletedFiles', width: '75px', dataType: 'string', ntsControl: "FlexImage" },
      { headerText: getText('CMF003_311'), key: 'save', width: '75px', dataType: 'string', ntsControl: "Label" },
      { headerText: getText('CMF003_312'), key: 'saveStartDateTime', width: '150px', dataType: 'string', ntsControl: "Label" },
      { headerText: getText('CMF003_313'), key: 'practitioner', width: '150px', dataType: 'string', ntsControl: "Label" },
      { headerText: getText('CMF003_314'), key: 'saveName', width: '150px', dataType: 'string', ntsControl: "Label" },
      { headerText: getText('CMF003_315'), key: 'saveForm', width: '75px', dataType: 'string', ntsControl: "Label" },
      { headerText: getText('CMF003_316'), key: 'targetNumberPeople', width: '75px', dataType: 'string', ntsControl: "Label" },
      { headerText: getText('CMF003_317'), key: 'saveFileName', width: '250px', dataType: 'string', ntsControl: "Label" },
      { headerText: getText('CMF003_318'), key: 'fileSize', width: '150px', dataType: 'string', ntsControl: "Label" },
    ];
    states: State[] = [];
    dataGrid: any;

    created() {
      const vm = this;
      vm.dateValue.subscribe((value: any) => {
        vm.findSaveSet(value.startDate, value.endDate);
      });
      // vm.loadDataGrid();
    }

    mounted() {
      const vm = this;
      const previousDateText: string = moment.utc().subtract(1, 'months').add(1, 'days').format("YYYY/MM/DD");
      const currentDateText: string = moment.utc().format("YYYY/MM/DD");
      vm.dateValue({
        startDate: previousDateText,
        endDate: currentDateText
      });
    }

    private findSaveSet(from: string, to: string) {
      const vm = this;
      vm.$blockui("grayout");
      const momentFrom = moment.utc(from, "YYYY/MM/DD hh:mm:ss").toISOString();
      const momentTo = moment.utc(to, "YYYY/MM/DD hh:mm:ss").add(1, 'days').subtract(1, 'seconds').toISOString();
      service.findSaveSetHistory(momentFrom, momentTo)
        .then((data: SaveSetHistoryDto[]) => {
          const res: SaveSetHistoryDto[] = [
            { rowNumber: 1, patternCode: '', saveName: 'すべて' }
          ];
          if (data && data.length) {
            _.each(data, (x, i) => {
              x.rowNumber = i + 2;
              res.push(x);
            });
            vm.searchItems(res);
          }
          //Create green rowNumber column
          $("document").ready(() => {
            $("#J3_1 tbody td:first-child").each(function (index) {
              $(this).css('background-color', '#cff1a5');
            });
          })
        })
        .always(() => vm.$blockui("hide"));
    }

    public findData() {
      const vm = this;
      vm.$blockui("grayout");
      let arr: FindDataHistoryDto[] = [];
      let searchValue: SaveSetHistoryDto;
      if (vm.searchValue() === '1') {
        arr = _.map(_.filter(vm.searchItems(), data => data.rowNumber !== 1), data => new FindDataHistoryDto(data.patternCode, data.saveName));
      } else {
        searchValue = vm.getSearchValue(vm.searchValue());
        arr.push(new FindDataHistoryDto(searchValue.patternCode, searchValue.saveName));
      }
      service.findData(arr).then((data: Array<DataDto>) => {
        const res: DataDto[] = [];
        if (data && data.length) {
          _.each(data, (x, i) => {
            x.rowNumber = i + 1;
            x.id = nts.uk.util.randomId();
            x.restoreCount += "人";
            x.saveCount += "人";
            res.push(x);

            if (x.executionResult === '異常終了') {
              _.each(vm.columnHeadersRes, col => {
                vm.states.push(new State(x.id, col.key, ["red-color"]));
              })
            }
          });
        }
        vm.resultItems(res);
        vm.loadDataGrid();
      }).always(() => vm.$blockui("hide"));
    }

    public getSearchValue(val: any): SaveSetHistoryDto {
      const vm = this;
      return _.filter(vm.searchItems(), data => data.rowNumber === Number(val)).pop();
    }

    public loadDataGrid() {
      const vm = this;
      if ($("#J6_1").data("mGrid")) {
        $("#J6_1").mGrid("destroy");
      }
      vm.dataGrid = new (nts.uk.ui as any).mgrid.MGrid($("#I6_1")[0], {
        height: 800,
        headerHeight: "40px",
        autoFitWindow: true,
        dataSource: vm.resultItems(),
        primaryKey: 'id',
        primaryKeyDataType: 'number',
        value: vm.resultValue(),
        rowVirtualization: true,
        virtualization: true,
        virtualizationMode: 'continuous',
        enter: 'right',
        useOptions: true,
        idGen: (id: any) => id + "_" + nts.uk.util.randomId(),
        columns: vm.columnHeadersRes,
        features: [
          {
            name: 'Paging',
            pageSize: 100,
            currentPageIndex: 0,
            loaded: () => { }
          },
          {
            name: 'ColumnFixing', fixingDirection: 'left',
            showFixButtons: true,
            columnSettings: [
              { columnKey: 'rowNumber', isFixed: true }
            ]
          },
          {
            name: 'Resizing'
          },
          {
            name: 'CellStyles',
            states: vm.states
          },
        ],
        ntsControls: [
          { name: 'Button', controlType: 'Button', text: getText('#CMF003_319'), enable: true, click: vm.deleteFile() },
          { name: 'FlexImage', source: '../resource/102.png', click: vm.download(), controlType: 'FlexImage' },
        ]
      }).create();
    }

    private download() {
      
    }

    private deleteFile() {
      
    }
  }

  export class FindDataHistoryDto {
    patternCode: string;
    saveName: string;

    constructor(patternCode: string, saveName: string) {
      this.patternCode = patternCode;
      this.saveName = saveName;
    }
  }

  export class State {
    rowId: string;
    columnKey: string;
    state: any[];

    constructor(rowId: string, columnKey: string, state: any[]) {
      this.rowId = rowId;
      this.columnKey = columnKey;
      this.state = state;
    }
  }

  export interface SaveSetHistoryDto {
    rowNumber: number;
    patternCode: string;
    saveName: string;
  }

  export class DataDto {
    storeProcessingId: string;
    cid: string;
    fileSize: number;
    saveSetCode: string;
    saveFileName: string;
    saveName: string;
    saveForm: number;
    saveStartDatetime: string;
    saveEndDatetime: string;
    deletedFiles: number;
    practitioner: string;
    targetNumberPeople: number;
    saveStatus: number;
    fileId: string;
    save: string = getText('CMF003_330');
  }
}
