module cps003.a.vm {
    import info = nts.uk.ui.dialog.info;
    import alert = nts.uk.ui.dialog.alert;
    import text = nts.uk.resource.getText;
    import format = nts.uk.text.format;
    import confirm = nts.uk.ui.dialog.confirm;
    import modal = nts.uk.ui.windows.sub.modal;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import showDialog = nts.uk.ui.dialog;
    import hasError = nts.uk.ui.errors.hasError;
    import clearError = nts.uk.ui.errors.clearAll;
    import liveView = nts.uk.request.liveView;

    const REPL_KEY = '__REPLACE',
        __viewContext: any = window['__viewContext'] || {},
        block = window["nts"]["uk"]["ui"]["block"]["grayout"],
        unblock = window["nts"]["uk"]["ui"]["block"]["clear"],
        invisible = window["nts"]["uk"]["ui"]["block"]["invisible"];

    export class ViewModel {
        ccgcomponent: any = {
            /** Common properties */
            systemType: 1, // シスッ�区�
            showEmployeeSelection: true, // 検索タイ�
            showQuickSearchTab: true, // クイヂ�検索
            showAdvancedSearchTab: true, // 詳細検索
            showBaseDate: false, // 基準日利用
            showClosure: false, // 就業�め日利用
            showAllClosure: true, // 全�め表示
            showPeriod: false, // 対象期間利用
            periodFormatYM: true, // 対象期間精度

            /** Required parame*/
            baseDate: moment.utc().toISOString(), // 基準日
            periodStartDate: moment.utc("1900/01/01", "YYYY/MM/DD").toISOString(), // 対象期間開始日
            periodEndDate: moment.utc("9999/12/31", "YYYY/MM/DD").toISOString(), // 対象期間終亗�
            inService: true, // 在職区�
            leaveOfAbsence: true, // 休�区�
            closed: true, // 休業区�
            retirement: false, // 退職区�

            /** Quick search tab options */
            showAllReferableEmployee: true, // 参�可能な社員すべて
            showOnlyMe: true, // 自刁��
            showSameWorkplace: true, // 同じ職場の社員
            showSameWorkplaceAndChild: true, // 同じ職場とそ�配下�社員

            /** Advanced search properties */
            showEmployment: true, // 雔�条件
            showWorkplace: true, // 職場条件
            showClassification: true, // 刡�条件
            showJobTitle: true, // 職位条件
            showWorktype: false, // 勤種条件
            isMutipleCheck: true, // 選択モー�

            /** Return data */
            returnDataFromCcg001: (data: any) => {
                let self = this;

                self.employees(data.listEmployee);
            }
        };

        gridList = {
            inData: {
                employees: ko.observableArray([]),
                itemDefitions: ko.observableArray([])
            },
            outData: ko.observableArray([])
        }

        gridOptions: any = {};

        baseDate: KnockoutObservable<Date> = ko.observable();

        category: {
            catId: KnockoutObservable<string>;
            items: KnockoutObservableArray<any>;
        } = {
            catId: ko.observable(''),
            items: ko.observableArray([])
        };

        settings: ISettingData = {
            matrixDisplay: ko.observable({}),
            perInfoData: ko.observableArray([])
        };

        // for employee info.
        employees: KnockoutObservableArray<IEmployee> = ko.observableArray([]);

        constructor() {
            let self = this;

            self.baseDate(moment().format("YYYY/MM/DD"));

            //fetch all category by login 
            service.fetch.category(__viewContext.user.employeeId)
                .done(data => self.category.items(data));

            self.category.catId.subscribe((cid: string) => {
                if (cid) {
                    // fetch all setting
                    service.fetch.setting(cid).done((data: ISettingData) => {
                        if (ko.isObservable(self.settings.matrixDisplay)) {
                            if (_.size(self.settings.matrixDisplay()) == 0) {
                                self.settings.matrixDisplay(data.matrixDisplay);
                            }
                        }

                        if (ko.isObservable(self.settings.perInfoData)) {
                            self.settings.perInfoData(data.perInfoData);
                        }
                    });

                    // fetch data (Manh code)
                }
            });

            $('#ccgcomponent').ntsGroupComponent(self.ccgcomponent).done(() => { });
            //            self.requestData();
        }

        start() {
            let self = this;
        }

        saveData() {
            let self = this,
                command: {
                };

            // trigger change of all control in layout
            _.each(__viewContext.primitiveValueConstraints, x => {
                if (_.has(x, "itemCode")) {
                    $('#' + x.itemCode).trigger('change');
                }
            })

            if (hasError()) {
                $('#func-notifier-errors').trigger('click');
                return;
            }

            // push data to webservice
            block();
            service.push.data(command).done(() => {
                info({ messageId: "Msg_15" }).then(function() {
                    unblock();
                    self.start();
                });
            }).fail((mes) => {
                unblock();
                alert(mes.message);
            });
        }

        openBDialog() {
            let self = this,
                params = {
                    systemDate: "2018/12/21",
                    categoryId: "111",
                    categoryName: "AAAA",
                    mode: 1,
                    columnChange: [],
                    sids: []
                };

            block();
            setShared('CPS003B_VALUE', params);

            modal("/view/cps/003/b/index.xhtml").onClosed(() => {
            });
        }

        loadGrid() {
            let self = this;
            new nts.uk.ui.mgrid.MGrid($("#grid")[0], {
                width: "1000px",
                height: "800px",
                headerHeight: "80px",
                dataSource: self.gridOptions.dataSource,
                primaryKey: "id",
                virtualization: true,
                virtualizationMode: "continuous",
                enter: "right",
                autoFitWindow: true,
                errorColumns: [],
                idGen: (id) => id + "",
                columns: self.gridOptions.columns,
                features: self.gridOptions.features,
                ntsControls: self.gridOptions.ntsControls
            });
        }

        requestData() {
            // { categoryId: 'COM1_00000000000000000000000_CS00020', employeeIds: [], baseDate: '2818/01/01' };
            let self = this;
            let employeeIds = _.map(self.employees(), e => e.employeeId),
                param = { categoryId: self.category.catId(), employeeIds: employeeIds, baseDate: self.baseDate() };
            nts.uk.request.ajax('com', 'ctx/pereg/grid-layout/get-data', param).done(data => {
                self.convertData(data);
                self.loadGrid();
            });
        }

        convertData(data) {

        }

        settingColumns() {
            let self = this,
                id = self.category.catId(),
                ctg = _.first(self.category.items(), m => m.id == id);

            setShared('CPS003D_PARAM', {
                id: id,
                name: ctg.categoryName
            });

            modal("/view/cps/003/d/index.xhtml").onClosed(() => {
               console.log(getShared('CPS003D_VALUE'));    
            });
        }
    }

    interface IEmployee {
    }

    class Employee {
    }

    /* Dữ liệu nhận về từ  */
    interface IRequestData {
        baseDate: string;
        categoryId: string;
        headDatas: IDataHead[];
        bodyDatas: IDataBody[];
    }

    /* Dữ liệu  */
    interface IDataHead {
        itemCode: string;
        itemName: string;
        itemOrder: number;
        itemParentCode: string;
        itemTypeState: ISingleItem;
        required: boolean;
        resourceId: string;
    }

    /* Dữ liệu body (điều chỉnh thêm) */
    interface IDataBody {
        personId: string;
        employeeId: string;
        items: IColumnData[];
    }

    /* Dữ liệu tương ứng từng cột */
    interface IColumnData {
        actionRole: ACTION_ROLE;
        itemCode: string;
        itemParentCode: string;

        lstComboBoxValue: any[]; // list data để validate 

        recordId: string | null; // id bản ghi trong db
        textValue: string | null; // giá trị hiển thị 
        value: Object | null; // giá trị
    }

    enum ACTION_ROLE {
        HIDDEN = <any>"HIDDEN",
        VIEW_ONLY = <any>"VIEW_ONLY",
        EDIT = <any>"EDIT"
    }

    // define ITEM_SINGLE_TYPE
    // type of item if it's single item
    enum ITEM_SINGLE_TYPE {
        STRING = 1,
        NUMERIC = 2,
        DATE = 3,
        TIME = 4,
        TIMEPOINT = 5,
        SELECTION = 6,
        SEL_RADIO = 7,
        SEL_BUTTON = 8,
        READONLY = 9,
        RELATE_CATEGORY = 10,
        NUMBERIC_BUTTON = 11,
        READONLY_BUTTON = 12
    }

    // define ITEM_STRING_DATA_TYPE
    enum ITEM_STRING_DTYPE {
        FIXED_LENGTH = 1, // fixed length
        VARIABLE_LENGTH = 2 // variable length
    }

    enum ITEM_STRING_TYPE {
        ANY = 1,
        // 2:全ての半角文字(AnyHalfWidth)
        ANYHALFWIDTH = 2,
        // 3:半角英数字(AlphaNumeric)
        ALPHANUMERIC = 3,
        // 4:半角数字(Numeric)
        NUMERIC = 4,
        // 5:全角カタカナ(Kana)
        KANA = 5,
        // 6: カードNO
        CARDNO = 6,
        // 7: 社員コード
        EMPLOYEE_CODE = 7
    }

    // define ITEM_SELECT_TYPE
    // type of item if it's selection item
    enum ITEM_SELECT_TYPE {
        // 1:専用マスタ(DesignatedMaster)
        DESIGNATED_MASTER = <any>"DESIGNATED_MASTER",
        // 2:コード名称(CodeName)
        CODE_NAME = <any>"CODE_NAME",
        // 3:列挙型(Enum)
        ENUM = <any>"ENUM"
    }

    enum DateType {
        YEARMONTHDAY = 1,
        YEARMONTH = 2,
        YEAR = 3
    }

    interface ISingleItem {
        itemType: number;
        dataTypeState?: IItemDefinitionData // Single item value
    }

    interface IItemDefinitionData extends IItemTime, IItemDate, IItemString, IItemTimePoint, IItemNumeric, IItemSelection {
        dataTypeValue: ITEM_SINGLE_TYPE; // type of value of item
    }

    interface IItemTime {
        min?: number;
        max?: number;
    }

    interface IItemDate {
        dateItemType?: DateType;
    }

    interface IItemString {
        stringItemDataType?: ITEM_STRING_DTYPE;
        stringItemLength?: number;
        stringItemType?: ITEM_STRING_TYPE;
    }

    interface IItemTimePoint {
        timePointItemMin?: number;
        timePointItemMax?: number;
    }

    interface IItemNumeric {
        numericItemMinus?: number;
        numericItemAmount?: number;
        integerPart?: number;
        decimalPart?: number;
        numericItemMin?: number;
        numericItemMax?: number;
    }

    interface IItemSelection extends IItemMasterSelection, IItemEnumSelection, IItemCodeNameSelection {
        referenceType?: ITEM_SELECT_TYPE;
    }

    interface IItemMasterSelection {
        masterType?: string;
    }

    interface IItemEnumSelection {
        typeCode?: string;
    }

    interface IItemCodeNameSelection {
        enumName?: string;
    }

    interface ISettingData {
        "perInfoData": KnockoutObservableArray<IPersonInfoSetting> | Array<IPersonInfoSetting>;
        "matrixDisplay": KnockoutObservable<IMatrixDisplay> | IMatrixDisplay;
    }

    interface IPersonInfoSetting {
        "perInfoItemDefID": string;
        "itemCD": string;
        "itemName": string;
        "regulationAtr": boolean;
        "dispOrder": number;
        "width": number;
        "required": boolean;
    }

    interface IMatrixDisplay {
        "companyID"?: String;
        "userID"?: String;
        "cursorDirection": CURSOR_DIRC,
        "clsATR": IUSE_SETTING;
        "jobATR": IUSE_SETTING;
        "workPlaceATR": IUSE_SETTING,
        "departmentATR": IUSE_SETTING;
        "employmentATR": IUSE_SETTING;
    }

    enum IUSE_SETTING {
        USE = <any>'USE',
        NOT_USE = <any>'NOT_USE'
    }

    enum CURSOR_DIRC {
        VERTICAL = <any>'VERTICAL',
        HORIZONTAL = <any>'HORIZONTAL'
    }
}