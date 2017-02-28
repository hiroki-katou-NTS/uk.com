// TreeGrid Node
module qpp011.b {

    export class ScreenModel {
        RadioItemList: KnockoutObservableArray<any>;
        selectedId: KnockoutObservable<number>;
        enable: KnockoutObservable<boolean>;
        //combobox
        ComboBoxItemList: KnockoutObservableArray<ComboboxItemModel>;
        itemName: KnockoutObservable<string>;
        ComboBoxCurrentCode: KnockoutObservable<number>
        selectedCode: KnockoutObservable<string>;
        isEnable: KnockoutObservable<boolean>;
        isEditable: KnockoutObservable<boolean>;
        //
        selectedValue_B_LST_001: any;
        selectedValue_C_LST_001: any;
        //gridlist
        B_LST_001_Data: any;
        columns: any;
        B_INP_001_yearMonth: any;
        B_INP_002_yearMonth: any;
        B_INP_003_yearMonth: any;
        yearInJapanEmpire_LBL_005: any;
        yearInJapanEmpire_LBL_008: any;
        yearInJapanEmpire_LBL_010: any;
        //number editter
        numbereditor: any;
        constructor() {
            var self = this;
            //start radiogroup data
            self.RadioItemList = ko.observableArray([
                new BoxModel(1, '本社'),
                new BoxModel(2, '法定調書出力用会社')
            ]);
            self.selectedId = ko.observable(1);
            self.enable = ko.observable(true);
            //end radiogroup data
            //start combobox data
            self.ComboBoxItemList = ko.observableArray([
                new ComboboxItemModel('0001', 'Item1'),
                new ComboboxItemModel('0002', 'Item2'),
                new ComboboxItemModel('0003', 'Item3')
            ]);
            self.ComboBoxCurrentCode = ko.observable(1);
            self.selectedCode = ko.observable('0001')
            self.isEnable = ko.observable(true);
            self.isEditable = ko.observable(true);
            //end combobox data
            // start gridlist
            //B_LST_001
            //end gridlist
            //start number editer
            self.numbereditor = {
                value: ko.observable(),
                constraint: '',
                option: ko.mapping.fromJS(new nts.uk.ui.option.NumberEditorOption({
                    grouplength: 0,
                    decimallength: 0,
                    placeholder: "",
                    width: "",
                    textalign: "right"
                })),
                required: ko.observable(true),
                enable: ko.observable(true),
                readonly: ko.observable(false)
            }
            self.B_LST_001_Data = ko.observable([]);
            //end number editer
            service.findAllResidential().done(function(data: Array<any>) {
                self.B_LST_001_Data(data);
                BindTreeGrid("#B_LST_001", self.B_LST_001_Data());
                BindTreeGrid("#C_LST_001", self.B_LST_001_Data());
            }).fail(function(res) {
                // Alert message
                alert(res);
            });

            self.columns = [
                { headerText: "resiTaxCode", key: "resiTaxCode", width: "250px", dataType: "string", hidden: true },
                { headerText: "companyCode", key: "companyCode", width: "300px", dataType: "string", hidden: true },
                { headerText: "companySpecifiedNo", key: "companySpecifiedNo", width: "130px", dataType: "string" },
                { headerText: "registeredName", key: "registeredName", width: "230px", dataType: "string" },
                { headerText: "prefectureCode", key: "prefectureCode", width: "130px", dataType: "string" }
            ];
            function BindTreeGrid(gridID, Data) {
                $(gridID).igTreeGrid({
                    width: "480px",
                    height: "500px",
                    dataSource: Data,
                    autoGenerateColumns: false,
                    primaryKey: "resiTaxCode",
                    columns: self.columns,
                    childDataKey: "files",
                    initialExpandDepth: 2,
                    features: [
                        {
                            name: "Selection",
                            multipleSelection: true,
                            rowSelectionChanged: function(evt: any, ui: any) {
                                var selectedRows: Array<any> = ui.selectedRows;
                                self.selectedValue_B_LST_001(_.map(selectedRows, function(row) {
                                    return row.id;
                                }));
                            }
                        },
                        {
                            name: "RowSelectors",
                            enableCheckBoxes: true,
                            checkBoxMode: "biState",
                            enableSelectAllForPaging: true,
                            enableRowNumbering: false
                        }]
                });
                $(gridID).setupSearchScroll("igTreeGrid");
            }

            self.selectedValue_B_LST_001 = ko.observableArray([]);
            let $B_LST_001 = $("#B_LST_001");
            self.selectedValue_B_LST_001.subscribe(function(newValue) {
                let selectedRows = _.map($B_LST_001.igTreeGridSelection("selectedRows"), function(row) {
                    if (row != undefined)
                        return row.id;
                });
                if (!_.isEqual(selectedRows, newValue)) {
                    $B_LST_001.igTreeGridSelection("clearSelection");
                    newValue.forEach(function(id) {
                        $B_LST_001.igTreeGridSelection("selectRowById", id);
                    });
                }
            });
            self.selectedValue_C_LST_001 = ko.observableArray([]);
            let $C_LST_001 = $("#B_LST_001");
            self.selectedValue_C_LST_001.subscribe(function(newValue) {
                let selectedRows = _.map($C_LST_001.igTreeGridSelection("selectedRows"), function(row) {
                    return row.id;
                });
                if (!_.isEqual(selectedRows, newValue)) {
                    $C_LST_001.igTreeGridSelection("clearSelection");
                    newValue.forEach(function(id) {
                        $C_LST_001.igTreeGridSelection("selectRowById", id);
                    });
                }
            });
            //001
            self.B_INP_001_yearMonth = ko.observable('2016/12');
            self.B_INP_001_yearMonth.subscribe(function(newValue) {
                self.yearInJapanEmpire_LBL_005("(" + nts.uk.time.yearmonthInJapanEmpire(self.B_INP_001_yearMonth()).toString() + ")");
            });
            self.yearInJapanEmpire_LBL_005 = ko.observable();
            self.yearInJapanEmpire_LBL_005("(" + nts.uk.time.yearmonthInJapanEmpire(self.B_INP_001_yearMonth()).toString() + ")");
            //002
            self.B_INP_002_yearMonth = ko.observable('2016/12');
            self.B_INP_002_yearMonth.subscribe(function(newValue) {
                self.yearInJapanEmpire_LBL_008("(" + nts.uk.time.yearmonthInJapanEmpire(self.B_INP_002_yearMonth()).toString() + ")");
            });
            self.yearInJapanEmpire_LBL_008 = ko.observable();
            self.yearInJapanEmpire_LBL_008("(" + nts.uk.time.yearmonthInJapanEmpire(self.B_INP_002_yearMonth()).toString() + ")");
            //003
            self.B_INP_003_yearMonth = ko.observable(new Date('2016/12/01'));
            self.B_INP_003_yearMonth.subscribe(function(newValue) {
                self.yearInJapanEmpire_LBL_010("(" + nts.uk.time.yearmonthInJapanEmpire(self.B_INP_003_yearMonth()).toString() + ")");
            });
            self.yearInJapanEmpire_LBL_010 = ko.observable();
            self.yearInJapanEmpire_LBL_010("(" + nts.uk.time.yearmonthInJapanEmpire(self.B_INP_003_yearMonth()).toString() + ")");
        }
        openFDialog() {
            nts.uk.ui.windows.sub.modal('/view/qpp/011/f/index.xhtml', { height: 550, width: 740, dialogClass: 'no-close' }).onClosed(function(): any {
            });
        }
        openDDialog() {
            let self = this;
            nts.uk.ui.windows.setShared("selectedValue_B_LST_001", self.selectedValue_B_LST_001);
            nts.uk.ui.windows.setShared("files", self.B_LST_001_Data());
            nts.uk.ui.windows.sub.modal('/view/qpp/011/d/index.xhtml', { height: 550, width: 1020, dialogClass: 'no-close' }).onClosed(function(): any {
            });
        }
    }

    export class BoxModel {
        id: number;
        name: string;
        constructor(id, name) {
            var self = this;
            self.id = id;
            self.name = name;
        }
    }
    export class ComboboxItemModel {
        code: string;
        name: string;

        constructor(code: string, name: string) {
            this.code = code;
            this.name = name;
        }
    }
    export class GridItemModel_C_LST_001 {
        code: string;
        name: string;

        constructor(code: string, name: string) {
            this.code = code;
            this.name = name;
        }
    }

    export class GridItemModel_B_LST_001 {
        code: string;
        name: string;

        constructor(code: string, name: string) {
            this.code = code;
            this.name = name;
        }

    }


};
