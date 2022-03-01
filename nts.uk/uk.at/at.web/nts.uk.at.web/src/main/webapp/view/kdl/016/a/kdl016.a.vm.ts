/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />
module nts.uk.at.view.kdl016.a {
    import Moment = moment.Moment;

    const API = {
        init: "screen/at/kdl016//a/init",
        get: "screen/at/kdl016//a/init",
        delete: "screen/at/kdl016/a/delete"
    };

    @bean()
    export class ViewModel extends ko.ViewModel {
        unit: KnockoutObservable<number> = ko.observable(0);
        orgUnitName: KnockoutComputed<string>;
        orgDisplayName: KnockoutObservable<string> = ko.observable("OrgName");
        periodStart: KnockoutObservable<string> = ko.observable("2022/02/01");
        periodEnd: KnockoutObservable<string> = ko.observable("2022/02/28");
        displayPeriod: KnockoutComputed<string>;

        supportModes: KnockoutObservableArray<any>;
        selectedMode: any;

        igGridHeader: Array<IgGridColumnModel> = [];
        selectedRow: KnockoutObservableArray<number> = ko.observableArray([]);
        selectedCode: KnockoutObservableArray<number> = ko.observableArray([]);
        igGridDataSource: ISupportInformation[] = [];

        constructor(params: IScreenParameter) {
            super();
            const vm = this;

            vm.orgUnitName = ko.computed(() => {
                return vm.unit() == TARGET_ORG.WORKPLACE ? vm.$i18n('Com_Workplace') : vm.$i18n('Com_WorkplaceGroup');
            });

            vm.displayPeriod = ko.computed(() => {
                if (vm.periodStart() && vm.periodEnd())
                    return moment.utc(vm.periodStart()).format("YYYY/MM/DD") + " ～ " + moment.utc(vm.periodEnd()).format("YYYY/MM/DD");
                else if (vm.periodStart() && !vm.periodEnd())
                    return moment.utc(vm.periodStart()).format("YYYY/MM/DD") + " ～ ";
                else return "";
            });

            vm.supportModes = ko.observableArray([
                {code: '1', name: vm.$i18n('KDL016_12')},
                {code: '2', name: vm.$i18n('KDL016_13')}
            ]);
            vm.selectedMode = ko.observable('2');

            vm.loadSupportInfo();
        }

        created(params: any) {
            const vm = this;
        }

        mounted() {
            const vm = this;
            $('#grid').focus();
            $('#grid_scroll').focus();
            $('#A1_1').focus();
        }

        loadSupportInfo(): void {
            const vm = this;

            // Fake data
            let data: ISupportInformation[] = [
                {
                    id: 1,
                    employeeId: 'employeeId1',
                    periodStart: '2022/01/01',
                    periodEnd: '2022/01/31',
                    employeeCode: 'EmpCode1',
                    employeeName: 'EmpName1',
                    supportOrgName: '3SI',
                    supportOrgId: '3siId',
                    supportOrgUnit: 0,
                    supportType: 1,
                    timeSpan: {
                        start: 100,
                        end: 450
                    },
                    supportTypeName: '時間帯',
                    periodDisplay: '2022/01/01 - 2022/01/31',
                    employeeDisplay: '0000001 Elangaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa',
                    timeSpanDisplay: '8:00 - 10:00',
                    displayMode: 1
                },
                {
                    id: 2,
                    employeeId: 'employeeId2',
                    periodStart: '2022/01/01',
                    periodEnd: '2022/01/31',
                    employeeCode: 'EmpCode2',
                    employeeName: 'EmpName2',
                    supportOrgName: '3SI',
                    supportOrgId: '3siId',
                    supportOrgUnit: 0,
                    supportType: 1,
                    timeSpan: {
                        start: 100,
                        end: 450
                    },
                    supportTypeName: '終日',
                    periodDisplay: '2022/01/01 - 2022/01/31',
                    employeeDisplay: '0000001 Elanga',
                    timeSpanDisplay: '8:00 - 10:00',
                    displayMode: 1
                },
                {
                    id: 3,
                    employeeId: 'employeeId1',
                    periodStart: '2022/01/01',
                    periodEnd: '2022/01/31',
                    employeeCode: 'EmpCode1',
                    employeeName: 'EmpName1',
                    supportOrgName: '3SI',
                    supportOrgId: '3siId',
                    supportOrgUnit: 0,
                    supportType: 1,
                    timeSpan: {
                        start: 100,
                        end: 450
                    },
                    supportTypeName: '時間帯',
                    periodDisplay: '2022/01/01 - 2022/01/31',
                    employeeDisplay: '0000001 Elanga',
                    timeSpanDisplay: '8:00 - 10:00',
                    displayMode: 1
                },
                {
                    id: 4,
                    employeeId: 'employeeId2',
                    periodStart: '2022/01/01',
                    periodEnd: '2022/01/31',
                    employeeCode: 'EmpCode2',
                    employeeName: 'EmpName2',
                    supportOrgName: '3SI',
                    supportOrgId: '3siId',
                    supportOrgUnit: 0,
                    supportType: 1,
                    timeSpan: {
                        start: 100,
                        end: 450
                    },
                    supportTypeName: '終日',
                    periodDisplay: '2022/01/01 - 2022/01/31',
                    employeeDisplay: '0000001 Elanga',
                    timeSpanDisplay: '8:00 - 10:00',
                    displayMode: 1
                },
                {
                    id: 5,
                    employeeId: 'employeeId1',
                    periodStart: '2022/01/01',
                    periodEnd: '2022/01/31',
                    employeeCode: 'EmpCode1',
                    employeeName: 'EmpName1',
                    supportOrgName: '3SI',
                    supportOrgId: '3siId',
                    supportOrgUnit: 0,
                    supportType: 1,
                    timeSpan: {
                        start: 100,
                        end: 450
                    },
                    supportTypeName: '時間帯',
                    periodDisplay: '2022/01/01 - 2022/01/31',
                    employeeDisplay: '0000001 Elanga',
                    timeSpanDisplay: '8:00 - 10:00',
                    displayMode: 1
                },
                {
                    id: 6,
                    employeeId: 'employeeId2',
                    periodStart: '2022/01/01',
                    periodEnd: '2022/01/31',
                    employeeCode: 'EmpCode2',
                    employeeName: 'EmpName2',
                    supportOrgName: '3SI',
                    supportOrgId: '3siId',
                    supportOrgUnit: 0,
                    supportType: 1,
                    timeSpan: {
                        start: 100,
                        end: 450
                    },
                    supportTypeName: '終日',
                    periodDisplay: '2022/01/01 - 2022/01/31',
                    employeeDisplay: '0000001 Elanga',
                    timeSpanDisplay: '8:00 - 10:00',
                    displayMode: 1
                },
                {
                    id: 7,
                    employeeId: 'employeeId1',
                    periodStart: '2022/01/01',
                    periodEnd: '2022/01/31',
                    employeeCode: 'EmpCode1',
                    employeeName: 'EmpName1',
                    supportOrgName: '3SI',
                    supportOrgId: '3siId',
                    supportOrgUnit: 0,
                    supportType: 1,
                    timeSpan: {
                        start: 100,
                        end: 450
                    },
                    supportTypeName: '終日',
                    periodDisplay: '2022/01/01 - 2022/01/31',
                    employeeDisplay: '0000001 Elanga',
                    timeSpanDisplay: '8:00 - 10:00',
                    displayMode: 1
                },
                {
                    id: 8,
                    employeeId: 'employeeId2',
                    periodStart: '2022/01/01',
                    periodEnd: '2022/01/31',
                    employeeCode: 'EmpCode2',
                    employeeName: 'EmpName2',
                    supportOrgName: '3SI',
                    supportOrgId: '3siId',
                    supportOrgUnit: 0,
                    supportType: 1,
                    timeSpan: {
                        start: 100,
                        end: 450
                    },
                    supportTypeName: '時間帯',
                    periodDisplay: '2022/01/01 - 2022/01/31',
                    employeeDisplay: '0000002 Ronaldo',
                    timeSpanDisplay: '8:00 - 10:00',
                    displayMode: 1
                },
                {
                    id: 9,
                    employeeId: 'employeeId1',
                    periodStart: '2022/01/01',
                    periodEnd: '2022/01/31',
                    employeeCode: 'EmpCode1',
                    employeeName: 'EmpName1',
                    supportOrgName: '3SI',
                    supportOrgId: '3siId',
                    supportOrgUnit: 0,
                    supportType: 1,
                    timeSpan: {
                        start: 100,
                        end: 450
                    },
                    supportTypeName: '終日',
                    periodDisplay: '2022/01/01 - 2022/01/31',
                    employeeDisplay: '0000001 Elanga',
                    timeSpanDisplay: '8:00 - 10:00',
                    displayMode: 1
                },
                {
                    id: 10,
                    employeeId: 'employeeId2',
                    periodStart: '2022/01/01',
                    periodEnd: '2022/01/31',
                    employeeCode: 'EmpCode2',
                    employeeName: 'EmpName2',
                    supportOrgName: '3SI',
                    supportOrgId: '3siId',
                    supportOrgUnit: 0,
                    supportType: 1,
                    timeSpan: {
                        start: 100,
                        end: 450
                    },
                    supportTypeName: '時間帯',
                    periodDisplay: '2022/01/01 - 2022/01/31',
                    employeeDisplay: '0000002 Ronaldo',
                    timeSpanDisplay: '8:00 - 10:00',
                    displayMode: 1
                },
                {
                    id: 11,
                    employeeId: 'employeeId1',
                    periodStart: '2022/01/01',
                    periodEnd: '2022/01/31',
                    employeeCode: 'EmpCode1',
                    employeeName: 'EmpName1',
                    supportOrgName: '3SI',
                    supportOrgId: '3siId',
                    supportOrgUnit: 0,
                    supportType: 1,
                    timeSpan: {
                        start: 100,
                        end: 450
                    },
                    supportTypeName: '終日',
                    periodDisplay: '2022/01/01 - 2022/01/31',
                    employeeDisplay: '0000001 Elanga',
                    timeSpanDisplay: '8:00 - 10:00',
                    displayMode: 1
                }
            ];
            vm.igGridDataSource = data;

            vm.initGrid(vm.igGridDataSource);

            window.onresize = function (evt: any) {
                $('#grid_displayContainer').height(window.innerHeight - 285);
                $('#grid_container').height(window.innerHeight - 285);
                $('#grid_virtualContainer').height(window.innerHeight - 285);
                $('#grid_scrollContainer').height(window.innerHeight - 285);
            }
        }

        private initGrid(dataSource: ISupportInformation[]) {
            let vm = this,
                selectMode = vm.selectedMode(),
                supportType = [
                    {id: '', Name: '空白'},
                    {id: '終日', Name: '終日'},
                    {id: '時間帯', Name: '時間帯'},
                ];
            const $grid = $("#grid");
            let primaryKeyName: string = "id";
            $grid.igGrid({
                // width: "1020px",
                height: window.innerHeight - 275,
                dataSource: dataSource,
                dataSourceType: "json",
                primaryKey: primaryKeyName,
                autoGenerateColumns: false,
                responseDataKey: "results",
                // tabIndex: 1,
                // rowVirtualization: true,
                // virtualization: true,
                // virtualizationMode: 'continuous',
                // hidePrimaryKey: true,
                columns: [
                    {headerText: '', key: 'id', dataType: 'number', width: '0px', hidden: true},
                    {headerText: 'DisplayMode', key: 'displayMode', dataType: 'number', width: '0px', hidden: true},
                    // {headerText: '', key: "edit", dataType: "string", width: "30px", unbound: true, ntsControl: 'EditButton'},
                    {
                        key: "edit", width: "70px", headerText: '', dataType: "string", unbound: true,
                        template: "<input type= \"button\"  onclick = \"nts.uk.at.view.kdl016.a.redirectEditModal(${id}, ${displayMode}) \" value= \" " + vm.$i18n('KDL016_19') + " \" />"
                    },
                    {
                        headerText: vm.$i18n('KDL016_14'),
                        key: "periodDisplay",
                        dataType: "string",
                        width: '200px',
                        template: '<div style="float:left">${periodDisplay} </div>'
                    },
                    {headerText: vm.$i18n('KDL016_15'), key: "supportOrgName", width: '80px', dataType: "string"},
                    {
                        headerText: vm.$i18n('KDL016_16'),
                        key: "employeeDisplay",
                        dataType: "string",
                        width: "250px",
                        template: '<div class="limited-label" style="float:left">${employeeDisplay} </div>'
                    },
                    {headerText: vm.$i18n('KDL016_17'), key: "supportTypeName", width: '80px', dataType: "string"},
                    {headerText: vm.$i18n('KDL016_18'), key: "timeSpanDisplay", width: "110px", dataType: "string"}
                ],
                features: [
                    {
                        name: "Filtering",
                        type: "local",
                        caseSensitive: false,
                        // mode: "simple",
                        // filterDialogContainment: "window",
                        // filterSummaryAlwaysVisible: false,
                        // dropDownClosing: vm.dropDownClosing,

                        // filterDropDownItemIcons: false,
                        // filterDropDownWidth: 200,
                        // filterDialogHeight: "390px",
                        // filterDialogWidth: "515px",
                        columnSettings: [
                            {columnKey: primaryKeyName, allowFiltering: false},
                            {columnKey: 'edit', allowFiltering: false},
                            {
                                columnKey: 'periodDisplay',
                                conditionList: ["equal", "afterAndEqual", "beforeAndEqual"],
                                customConditions: {
                                    equal: {
                                        labelText: vm.$i18n('KDL016_39'),
                                        expressionText: vm.$i18n('KDL016_40'),
                                        requireExpr: true,
                                        filterFunc: vm.equal
                                    },
                                    afterAndEqual: {
                                        labelText: vm.$i18n('KDL016_41'),
                                        expressionText: vm.$i18n('KDL016_42'),
                                        requireExpr: true,
                                        filterFunc: vm.afterAndEqual
                                    },
                                    beforeAndEqual: {
                                        labelText: vm.$i18n('KDL016_43'),
                                        expressionText: vm.$i18n('KDL016_44'),
                                        requireExpr: true,
                                        filterFunc: vm.beforeAndEqual
                                    }
                                },
                                defaultExpressions: [
                                    {cond: "equal"}
                                ]
                            },
                            {columnKey: 'supportOrgName', allowFiltering: false},
                            {
                                columnKey: 'employeeDisplay',
                                conditionList: ["contain", "notContain"],
                                customConditions: {
                                    contain: {
                                        labelText: vm.$i18n('KDL016_45'),
                                        expressionText: vm.$i18n('KDL016_45'),
                                        requireExpr: true,
                                        filterFunc: vm.contain
                                    },
                                    notContain: {
                                        labelText: vm.$i18n('KDL016_46'),
                                        expressionText: vm.$i18n('KDL016_46'),
                                        requireExpr: true,
                                        filterFunc: vm.notContain
                                    }
                                }
                            },
                            {
                                columnKey: "supportTypeName", editorType: 'combo',
                                conditionList: [
                                    "equals"
                                ],
                                editorOptions: {
                                    mode: "dropdown",
                                    dataSource: supportType,
                                    textKey: "Name",
                                    valueKey: "id",
                                    selectionChanged: function (e: any, args: any) {
                                        //Handle khi thay đổi data của combobox
                                    }
                                }
                            },
                            {columnKey: 'timeSpanDisplay', allowFiltering: false},
                        ]
                    },
                    {
                        name: "RowSelectors",
                        enableCheckBoxes: true,
                        enableRowNumbering: false,
                        enableSelectAllForPaging: false,
                        checkBoxStateChanging: function (event: any, ui: any) {
                            if (!_.isEqual(ui.currentState, ui.newState)) {
                                if (ui.newState == 'on')
                                    vm.selectedCode(vm.selectedCode().concat(ui.rowKey));
                                else
                                    vm.selectedCode(_.filter(vm.selectedCode(), item => item !== ui.rowKey));
                            }
                        },
                        // checkBoxStateChanged: function(evt : any, ui : any) {
                        //     // Handle event
                        // }
                    },
                    {
                        name: 'Selection',
                        mode: 'row',
                        multipleSelection: true,
                        // rowSelectionChanging: function() {},
                        // rowSelectionChanged: function(event: any, ui: any) {}
                    },
                    {
                        name: "Sorting",
                        type: "local"
                    },
                    {
                        name: "Resizing",
                        deferredResizing: false,
                        allowDoubleClickToResize: true
                    },
                    // {
                    //     name: "Filtering",
                    //     type: "local",
                    //     filterDropDownItemIcons: false,
                    //     filterDropDownWidth: 200,
                    //     filterDialogHeight: "390px",
                    //     filterDialogWidth: "515px",
                    //     columnSettings: [
                    //         {columnKey: primaryKeyName, allowFiltering: false},
                    //         {columnKey: 'edit', allowFiltering: false},
                    //         {columnKey: 'timeSpanDisplay', allowFiltering: false}
                    //     ]
                    // }
                ],
                // ntsControls: [
                //     { name: 'EditButton', text: vm.$i18n('KDL016_19'), click: function (value: any) { vm.openEditPopup(value) }, controlType: 'Button', enable: true }
                // ]
            });

            // $('#igGridSupportInfo').igGridSelection('selectRow', vm.selectedRow());
            // vm.$blockui('hide');
            $('#grid_scroll').focus();
            $('#grid').focus();
            // $('input:first').attr('placeholder', vm.$i18n('KSU001_4057'));
            // $("table thead tr td:nth-child(3)").css('padding', "0px !important");
            // $("td").eq(2).css('padding', "0px !important");
        }

        register() {
            const vm = this;
            if (vm.selectedMode() == DISPLAY_MODE.GO_TO_SUPPORT) {
                nts.uk.ui.windows.sub.modal("/view/kdl/016/b/index.xhtml").onClosed(() => {

                });
            } else {
                nts.uk.ui.windows.sub.modal("/view/kdl/016/c/index.xhtml").onClosed(() => {

                });
            }

        }

        openEditPopup(value: any) {

        }

        remove() {

        }

        closeDialog(): void {
            const vm = this;
            vm.$window.close();
        }

        findById(id: number): ISupportInformation[] {
            let vm = this;
            return _.find(vm.igGridDataSource, (value: any) => {
                return value.id == id;
            });
        }

        dropDownClosing(e, arg) {
            $('input:first').attr('placeholder', $('input:first').attr('placeholder').split('<=').join('').split('>=').join('').split('＝').join(''));
        }

        equal(value, expression, dataType, ignoreCase, preciseDateFormat) {
            if (isNaN(parseInt(expression))) {
                return parseInt(value.split('/').join('')) == 99999999;
            }
            return parseInt(value.split('/').join('')) == parseInt(expression.split('/').join(''));
        }

        beforeAndEqual(value, expression, dataType, ignoreCase, preciseDateFormat) {
            if (isNaN(parseInt(expression))) {
                return parseInt(value.split('/').join('')) == 99999999;
            }
            return parseInt(value.split('/').join('')) <= parseInt(expression.split('/').join(''));
        }

        afterAndEqual(value, expression, dataType, ignoreCase, preciseDateFormat) {
            if (isNaN(parseInt(expression))) {
                return parseInt(value.split('/').join('')) == 99999999;
            }
            return parseInt(value.split('/').join('')) >= parseInt(expression.split('/').join(''));
        }

        contain(value, expression, dataType, ignoreCase, preciseDateFormat) {
            return value.indexOf(expression) !== -1;
        }

        notContain(value, expression, dataType, ignoreCase, preciseDateFormat) {
            return value.indexOf(expression) == -1;
        }

        notFilter(value, expression, dataType, ignoreCase, preciseDateFormat) {
            return value;
        }

        clearFilter() {
            $("#grid").igGridFiltering("filter", [], true);
        }
    }

    export function redirectEditModal(id: number, mode: number) {
        const vm = this;
        let dataSource = $("#grid").igGrid("option", "dataSource");
        let dataShare = _.find(dataSource, (value: any) => {
            return value.id == id;
        });

        // var checkboxes = $('#igGridSupportInfo').igGridRowSelectors("option", "enableCheckBoxes");

        nts.uk.ui.windows.setShared("shareFromKdl016a", dataShare);
        if (mode.toString() == DISPLAY_MODE.GO_TO_SUPPORT) {
            nts.uk.ui.windows.sub.modal("/view/kdl/016/d/index.xhtml").onClosed(() => {

            });
        } else {
            nts.uk.ui.windows.sub.modal("/view/kdl/016/e/index.xhtml").onClosed(() => {

            });
        }
    }

    interface ISupportInformation {
        id: number;
        employeeId: string;
        periodStart: string;
        periodEnd: string;
        employeeCode: string;
        employeeName: string;
        supportOrgName: string;
        supportOrgId: string;
        supportOrgUnit: number;
        supportType: number;
        timeSpan: ITimeSpan;
        supportTypeName: string;
        periodDisplay: string;
        employeeDisplay: string;
        timeSpanDisplay: string;
        displayMode: number;
    }

    interface ITimeSpan {
        start: number;
        end: number;
    }

    interface IScreenParameter {
        targetOrg: ITargetOrganization;
        startDate: string;
        endDate: string;
        employeeIds: string[]
    }

    interface ITargetOrganization {
        id: string;
        code: string;
        unit: number
    }

    class IgGridColumnModel {
        headerText: string;
        key: string;
        dataType: string;
        hidden: boolean;
        width: string;
        template: string;
        itemName: string;

        constructor(headerText: string, key: string, dataType: string, hidden: boolean, width?: string, template?: string) {
            this.headerText = headerText;
            this.key = key;
            this.dataType = dataType;
            this.hidden = hidden;
            this.itemName = headerText;
            this.width = width;
            this.template = template;
        }
    }

    enum DISPLAY_MODE {
        GO_TO_SUPPORT = '1',
        COME_TO_SUPPORT = '2',
    }

    enum TARGET_ORG {
        WORKPLACE = 0,
        WORKPLACE_GROUP = 1,
    }
}