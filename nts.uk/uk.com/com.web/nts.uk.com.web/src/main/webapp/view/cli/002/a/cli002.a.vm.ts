/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.com.view.cli002.a {
    import service = nts.uk.com.view.cli002.a.service;

    @bean()
    export class ScreenModel extends ko.ViewModel {
        public logSettings: KnockoutObservableArray<LogSetting> = ko.observableArray([]);

        public systemList: KnockoutObservableArray<systemType> = ko.observableArray([
            new systemType(0, this.$i18n("Enum_SystemType_PERSON_SYSTEM")),
            new systemType(1, this.$i18n("Enum_SystemType_ATTENDANCE_SYSTEM")),
            new systemType(2, this.$i18n("Enum_SystemType_PAYROLL_SYSTEM")),
            new systemType(3, this.$i18n("Enum_SystemType_OFFICE_HELPER")),
        ]);

        public dataSourceItem: KnockoutObservableArray<any> = ko.observableArray([]);
        public selectedSystemCode: KnockoutObservable<number> = ko.observable(0);

        public systemColumns = [
            {
                headerText: "",
                prop: "index",
                width: 160,
                hidden: true,
            },
            {
                headerText:this.$i18n("CLI002_2"),
                prop: "localizedName",
                width: 160,
            }
        ];

        public columnsItem = [
            {
                headerText: this.$i18n(""),
                prop: "index",
                width: 30,
            },
            {
                headerText: this.$i18n("CLI002_7"),
                prop: "functionName",
                width: 180,
            },
            {
                headerText: this.$i18n("CLI002_4"),
                prop: "loginHistory",
                width: 180,
            },
            {
                headerText: this.$i18n("CLI002_5"),
                prop: "bootHistory",
                width: 180,
            },
            {
                headerText: this.$i18n("CLI002_6"),
                prop: "revisionHistory",
                width: 180,
            },
        ];

        constructor() {
            super();
            const vm = this;
            vm.getData(vm.selectedSystemCode());
        }

        mounted() {
            const vm = this;
            vm.selectedSystemCode.subscribe((newValue) => {
                vm.getData(newValue);
            });

            vm.getItemList();
        }

        public register() {
            const vm = this;
            vm.logSettings = ko.observableArray([]);
            _.forEach(vm.dataSourceItem, function(item: PGList) {
                vm.logSettings.push(new LogSetting(vm.selectedSystemCode(), item.programId, item.menuClassification, item.loginHistoryRecord.usageCategory,
                    item.editHistoryRecord.usageCategory, item.bootHistoryRecord.usageCategory));
            });
            service.updateLogSetting(vm.logSettings);
        }

        private getData(systemType: number) {
            const vm = this;
            vm.dataSourceItem = ko.observableArray([]);
            vm.$blockui("grayout");
            service.findBySystem(systemType).done((response: Array<PGList>) => {
                var statesTable = [];
                let listPG: any[] = response.map((item, index) => {
                    if (item.loginHistoryRecord.activeCategory == 0) {
                      statesTable.push(
                        new CellState(index, "logLoginDisplay", [
                          (nts.uk.ui as any).mgrid.color.Alarm,
                          (nts.uk.ui as any).mgrid.color.Reflect,
                          (nts.uk.ui as any).mgrid.color.Disable,
                        ])
                      );
                    }
                    if (item.bootHistoryRecord.activeCategory == 0) {
                      statesTable.push(
                        new CellState(index, "logStartDisplay", [
                          (nts.uk.ui as any).mgrid.color.Alarm,
                          (nts.uk.ui as any).mgrid.color.Reflect,
                          (nts.uk.ui as any).mgrid.color.Disable,
                        ])
                      );
                    }
                    if (item.editHistoryRecord.activeCategory == 0) {
                      statesTable.push(
                        new CellState(index, "logUpdateDisplay", [
                          (nts.uk.ui as any).mgrid.color.Alarm,
                          (nts.uk.ui as any).mgrid.color.Reflect,
                          (nts.uk.ui as any).mgrid.color.Disable,
                        ])
                      );
                    }
                    return new PGInfomation(index + 1, item.functionName, false, false, false, item.programId, item.menuClassification);
                });
                vm.dataSourceItem(listPG);
                vm.getItemList();

            }).always(() => vm.$blockui("clear")); 
        }

        private getItemList() {
            const vm = this;
            if ($("#item-list").data("mGrid")) $("#item-list").mGrid("destroy");
            new (nts.uk.ui as any).mgrid.MGrid($("#item-list")[0], {
                height: "400px",
                headerHeight: '60px',
                primaryKey: "displayName",
                primaryKeyDataType: "number",
                rowVirtualization: true,
                virtualization: true,
                virtualizationMode: 'continuous',
                enter: 'right',
                autoFitWindow: false,
                dataSource: vm.dataSourceItem(),
                columns: [
                    { headerText: "", key: "rowNumber", dataType: "number", width: "30px"},
                    { headerText: this.$i18n("CLI002_7"), key: "functionName", dataType: "string", width: "180px"},
                    { headerText: this.$i18n("CLI002_4"),
                        group: [
                            {headerText: "", key: "logLoginDisplay", dataType: "boolean", width: "180px", ntsControl: "Checkbox", checkbox: true, border: false}
                        ] 
                    },
                    { headerText: this.$i18n("CLI002_5"),
                        group: [
                            {headerText: "", key: "logStartDisplay", dataType: "boolean", width: "180px", ntsControl: "Checkbox", checkbox: true, border: false}
                        ] 
                    },
                    { headerText: this.$i18n("CLI002_6"),
                        group: [
                            {headerText: "", key: "logUpdateDisplay", dataType: "boolean", width: "180px", ntsControl: "Checkbox", checkbox: true, border: false}
                        ]
                    },
                ],
                ntsControls: [
                    { name: 'Checkbox', options: { value: 1, text: '' }, optionsValue: 'value', optionsText: 'text', controlType: 'CheckBox', enable: true, onChange: function() {}}
                ],
                features: [
                    { name: 'ColumnFixing', fixingDirection: 'left',
                                            showFixButtons: false,
                                            columnSettings: [
                                                                { columnKey: 'rowNumber', isFixed: true },
                                                                { columnKey: 'functionName', isFixed: true }
                                                            ]}
                ]
            }).create();
        }
    }
    class systemType {
        index: number;
        localizedName: string;
        constructor(index: number, localizedName: string) {
            this.index = index;
            this.localizedName = localizedName;
        }
    }

    export interface TargetSetting {
        usageCategory: number,
        activeCategory: number
    }

    export interface PGList {
        functionName: string,
        loginHistoryRecord: TargetSetting,
        bootHistoryRecord: TargetSetting,
        editHistoryRecord: TargetSetting,
        programId: string,
        menuClassification: number
    }

    class PGInfomation {
        rowNumber: number;
        functionName: string;
        logLoginDisplay: boolean;
        logStartDisplay: boolean;
        logUpdateDisplay: boolean;
        programId: string;
        menuClassification: number;
        constructor(rowNumber: number, functionName: string, logLoginDisplay: boolean, logStartDisplay: boolean, logUpdateDisplay: boolean, programId: string, menuClassification: number) {
            this.rowNumber = rowNumber;
            this.functionName = functionName;
            this.logLoginDisplay = logLoginDisplay;
            this.logStartDisplay = logStartDisplay;
            this.logUpdateDisplay = logUpdateDisplay;
            this.programId = programId;
            this.menuClassification = menuClassification;
        }
    }

    class LogSetting {
        system: number;
        programId: string;
        menuClassification: number;
        loginHistoryRecord: number;
        editHistoryRecord: number;
        bootHistoryRecord: number;
        constructor(system: number,
            programId: string,
            menuClassification: number,
            loginHistoryRecord: number,
            editHistoryRecord: number,
            bootHistoryRecord: number) {
                this.system = system;
                this.programId = programId;
                this.menuClassification = menuClassification;
                this.loginHistoryRecord = loginHistoryRecord;
                this.editHistoryRecord = editHistoryRecord;
                this.bootHistoryRecord= bootHistoryRecord;
        }
    }

    class CellState {
        rowId: number;
        columnKey: string;
        state: Array<any>
        constructor(rowId: number, columnKey: string, state: Array<any>) {
            this.rowId = rowId;
            this.columnKey = columnKey;
            this.state = state;
        }
    }
}
