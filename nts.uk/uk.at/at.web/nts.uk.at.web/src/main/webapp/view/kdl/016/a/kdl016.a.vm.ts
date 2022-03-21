/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />
module nts.uk.at.view.kdl016.a {
    const API = {
        init: "screen/at/kdl016/a/init",
        get: "screen/at/kdl016/a/get",
        delete: "screen/at/kdl016/a/delete"
    };

    @bean()
    export class ViewModel extends ko.ViewModel {
        targetOrg: KnockoutObservable<ITargetOrganization> = ko.observable(null);
        employeeIds: KnockoutObservableArray<string> = ko.observableArray([]);
        targetOrgUnitName: KnockoutComputed<string>;
        periodStart: KnockoutObservable<string> = ko.observable("");
        periodEnd: KnockoutObservable<string> = ko.observable("");
        displayPeriod: KnockoutComputed<string>;

        supportModes: KnockoutObservableArray<any>;
        selectedMode: any;
        selectedCode: KnockoutObservableArray<number> = ko.observableArray([]);
        igGridDataSource: any[] = [];
        enableSupportTimezone: KnockoutObservable<boolean> = ko.observable(false);

        canDelete: KnockoutObservable<boolean> = ko.observable(false);

        constructor(params: IScreenParameter) {
            super();
            const vm = this;
            if(!_.isNil(nts.uk.ui.windows.getShared('dataShareKDL016'))){
                let dataShare = nts.uk.ui.windows.getShared('dataShareKDL016');
                vm.targetOrg(dataShare.targetOrg);
                vm.periodStart(dataShare.startDate);
                vm.periodEnd(dataShare.endDate);
                vm.employeeIds(dataShare.employeeIds);

            } else if (!_.isNil(params)) {
                vm.targetOrg(params.targetOrg);
                vm.periodStart(params.startDate);
                vm.periodEnd(params.endDate);
                vm.employeeIds(params.employeeIds);
            }

            vm.targetOrgUnitName = ko.computed(() => {
                return vm.targetOrg().unit == TARGET_ORG.WORKPLACE ? vm.$i18n('Com_Workplace') : vm.$i18n('Com_WorkplaceGroup');
            });

            vm.displayPeriod = ko.computed(() => {
                if (vm.periodStart() && vm.periodEnd())
                    return moment.utc(vm.periodStart()).format("YYYY/MM/DD") + "  ～  " + moment.utc(vm.periodEnd()).format("YYYY/MM/DD");
                else if (vm.periodStart() && !vm.periodEnd())
                    return moment.utc(vm.periodStart()).format("YYYY/MM/DD") + "  ～";
                else return "";
            });

            vm.supportModes = ko.observableArray([
                {code: 1, name: vm.$i18n('KDL016_12')},
                {code: 2, name: vm.$i18n('KDL016_13')}
            ]);
            vm.selectedMode = ko.observable(1);

            vm.initialData();

            vm.selectedMode.subscribe((newValue: number) => {
                vm.loadSupportInfo(newValue);
            });

            vm.selectedCode.subscribe((newValue: any) => {
                vm.selectedCode().length > 0 ? vm.canDelete(true) : vm.canDelete(false);
            });
        }

        created(params: any) {
            const vm = this;
        }

        mounted() {
            const vm = this;
            $('#A1_1').focus();
        }

        initialData() {
            const vm = this, dfd = $.Deferred();
            vm.$blockui("show");
            const request = {
                employeeIds: vm.employeeIds(),
                periodStart: vm.periodStart(),
                periodEnd: vm.periodEnd()
            };

            vm.$ajax(API.init, request).done(response => {
                let data = response.supportInfoList || [];
                vm.enableSupportTimezone(response.enableSupportInTimezone);
                vm.igGridDataSource = data.map((ds: any) => new SupportInformationModel(ds, vm.enableSupportTimezone()));
                vm.initGrid(vm.igGridDataSource);
                dfd.resolve();
            }).fail(error => {
                vm.$dialog.error(error).then(() => {
                    vm.$window.close();
                });
                dfd.reject();
            }).always(() => {
                vm.$blockui("hide");
            });

            return dfd.promise();
        }

        loadSupportInfo(mode: number) {
            const vm = this, dfd = $.Deferred();
            vm.$blockui("show");
            const request = {
                targetOrg: {
                    orgId: vm.targetOrg().id,
                    orgUnit: vm.targetOrg().unit
                },
                employeeIds: vm.employeeIds(),
                startDate: vm.periodStart(),
                endDate: vm.periodEnd(),
                displayMode: mode
            };

            vm.$ajax(API.get, request).done(response => {
                let data = response || [];
                vm.igGridDataSource = data.map((ds: any) => new SupportInformationModel(ds, vm.enableSupportTimezone()));
                vm.initGrid(vm.igGridDataSource);
                vm.selectedCode([]);
                dfd.resolve();
            }).fail(error => {
                vm.$dialog.error(error).then(() => {
                    vm.$window.close();
                });
                dfd.reject();
            }).always(() => {
                vm.$blockui("hide");
            });

            return dfd.promise();
        }

        private initGrid(dataSource: ISupportInformation[]) {
            const vm = this,
                supportType = [
                    {id: '', Name: vm.$i18n('KDL016_48')},
                    {id: '終日', Name: vm.$i18n('KDL016_37')},
                    {id: '時間帯', Name: vm.$i18n('KDL016_38')},
                ];
            let orgHeader = vm.selectedMode() === DISPLAY_MODE.GO_TO_SUPPORT ?  vm.$i18n('KDL016_15') : vm.$i18n('KDL016_20');

            $("#grid").igGrid({
                // width: "929px",
                height: "352px",
                dataSource: dataSource,
                dataSourceType: "json",
                primaryKey: 'id',
                virtualization: true,
                virtualizationMode: 'continuous',
                columns: [
                    {headerText: '', key: 'id', dataType: 'number', width: '0px', hidden: true},
                    { headerText: '', key: "editState", width: "50px", hidden: true, dataType: "string" },
                    // {headerText: '', key: "edit", dataType: "string", width: "30px", unbound: true, ntsControl: 'EditButton'},
                    {
                        key: "edit", width: "60px", headerText: '', dataType: "string", unbound: true,
                        template: "<input type= \"button\"  onclick = \"nts.uk.at.view.kdl016.a.openEditModal(${id}) \" value= \" " + vm.$i18n('KDL016_19') + " \" ${editState} />"
                    },
                    {
                        headerText: vm.$i18n('KDL016_14'),
                        key: "periodDisplay",
                        dataType: "string",
                        width: '175px',
                        template: '<div style="float:left">${periodDisplay} </div>'
                    },
                    {
                        headerText: orgHeader,
                        key: "supportOrgName",
                        dataType: "string",
                        width: "115px",
                        template: '<div class="limited-label">${supportOrgName} </div>'
                    },
                    {
                        headerText: vm.$i18n('KDL016_16'),
                        key: "employeeDisplay",
                        dataType: "string",
                        width: "260px",
                        template: '<div class="limited-label">${employeeDisplay} </div>'
                    },
                    {headerText: vm.$i18n('KDL016_17'), key: "supportTypeName", width: '95px', dataType: "string"},
                    {headerText: vm.$i18n('KDL016_18'), key: "timeSpanDisplay", width: "120px", dataType: "string"}
                ],
                features: [
                    {
                        name: "Filtering",
                        type: "local",
                        caseSensitive: false,
                        columnSettings: [
                            {columnKey: 'id', allowFiltering: false},
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
                                }
                            },
                            {
                                columnKey: 'supportOrgName',
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
                                    vm.selectedCode(_.uniq(vm.selectedCode().concat(ui.rowKey)));
                                else
                                    vm.selectedCode(_.uniq(_.filter(vm.selectedCode(), item => item !== ui.rowKey)));
                            }
                        },
                        // checkBoxStateChanged: function(evt : any, ui : any) {}
                    },
                    {
                        name: 'Selection',
                        mode: 'cell',
                        multipleSelection: true,
                        // rowSelectionChanging: function() {},
                        // rowSelectionChanged: function(event: any, ui: any) {}
                    },
                    {
                        name: "Resizing",
                        deferredResizing: false,
                        allowDoubleClickToResize: true
                    }
                ],
                // ntsControls: [
                //     { name: 'EditButton', text: vm.$i18n('KDL016_19'), click: function (value: any) { vm.openEditPopup(value) }, controlType: 'Button', enable: true }
                // ]
            });

            // $('#igGridSupportInfo').igGridSelection('selectRow', vm.selectedRow());
            // var checkboxes = $('#grid').igGridRowSelectors("option", "enableCheckBoxes");
            $('#A1_1').focus();
        }

        register() {
            const vm = this;
            let param: any = {};
            if (vm.selectedMode() == DISPLAY_MODE.GO_TO_SUPPORT) {
                param = {
                    employeeIds: vm.employeeIds(),
                    targetOrg: {
                        orgId: vm.targetOrg().id,
                        orgUnit: vm.targetOrg().unit
                    },
                    enableSupportTimezone: vm.enableSupportTimezone()
                };
                vm.$blockui("invisible");
                vm.$window.modal("/view/kdl/016/b/index.xhtml", param).then((result: any) => {
                    if (result && !_.isEmpty(result)) {
                        if (result.reloadable) {
                            vm.loadSupportInfo(DISPLAY_MODE.GO_TO_SUPPORT);
                        }
                    }
                });
            } else {
                param = {
                    targetOrg: {
                        orgId: vm.targetOrg().id,
                        orgUnit: vm.targetOrg().unit
                    },
                    startDate: vm.periodStart(),
                    endDate: vm.periodEnd(),
                    enableSupportTimezone: vm.enableSupportTimezone()
                };
                vm.$window.modal("/view/kdl/016/c/index.xhtml", param).then((result: any) => {
                    if (result && !_.isEmpty(result)) {
                        if (result && !_.isEmpty(result)) {
                            if (result.reloadable) {
                                vm.loadSupportInfo(DISPLAY_MODE.COME_TO_SUPPORT);
                            }
                        }
                    }
                });
            }
        }

        remove(): void {
            const vm = this;
            vm.$blockui("invisible");

            let selectedRows = $("#grid").igGridSelection('selectedRows');
            if (typeof (selectedRows) != 'undefined' && selectedRows != null) {
                let listEmpIdSelected: any = [];
                let selectedRowIds = _.map(selectedRows, (row: any) => {
                    return row.id;
                });

                _.forEach(vm.igGridDataSource, function (value: any) {
                    if (_.includes(selectedRowIds, value.id)) {
                        listEmpIdSelected.push(value.employeeId);
                    }
                });

                let command = {
                    employeeIds: listEmpIdSelected
                };

                vm.$dialog.confirm({messageId: 'Msg_18'}).then((result: 'no' | 'yes') => {
                    vm.$blockui("invisible");
                    if (result === 'yes') {
                        vm.$ajax(API.delete, command).then((data: any) => {
                            if (!data.error) {
                                vm.$dialog.info({messageId: 'Msg_16'}).then(function () {
                                    vm.loadSupportInfo(vm.selectedMode());
                                    vm.canDelete(false);
                                    $("#grid").igGridSelection("clearSelection");
                                    vm.selectedCode([]);
                                });
                            } else {
                                let errorResults = data.errorResults;
                                let dataError: any = [];
                                for (let i = 0; i < errorResults.length; i++) {
                                    let err = errorResults[i];
                                    dataError.push(
                                        {
                                            id: i + 1,
                                            periodDisplay: err.periodDisplay,
                                            employeeDisplay: err.employeeDisplay,
                                            errorMessage: err.errorMessage,
                                        }
                                    );
                                }
                                let resultObj = {
                                    action: 3,
                                    gridItems: dataError
                                };

                                vm.$window.modal("/view/kdl/016/f/index.xhtml", resultObj).then((result: any) => {
                                    // vm.closeDialog();
                                });
                            }
                        }).fail(error => {
                            vm.$dialog.error(error);
                        }).always(() => {
                            vm.$blockui("clear");
                        });
                    }

                    if (result === 'no') {
                        vm.$blockui("hide");
                    }
                });
            }
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


        equal(value: any, expression: any, dataType: any, ignoreCase: any, preciseDateFormat: any) {
            if (isNaN(parseInt(expression))) {
                return parseInt(value.split('/').join('')) == 99999999;
            }

            let dateVale = value.split('～');
            if (dateVale.length == 1) {
                return parseInt(dateVale[0].split('/').join('')) == parseInt(expression.split('/').join(''));
            }
            if (dateVale.length == 2) {
                return parseInt(dateVale[0].split('/').join('')) <= parseInt(expression.split('/').join('')) &&
                    parseInt(dateVale[1].split('/').join('')) >= parseInt(expression.split('/').join(''));
            }
        }

        beforeAndEqual(value: any, expression: any, dataType: any, ignoreCase: any, preciseDateFormat: any) {
            if (isNaN(parseInt(expression))) {
                return parseInt(value.split('/').join('')) == 99999999;
            }

            let dateVale = value.split('～');
            if (dateVale.length == 1) {
                return parseInt(dateVale[0].split('/').join('')) <= parseInt(expression.split('/').join(''));
            }
            if (dateVale.length == 2) {
                return parseInt(dateVale[1].split('/').join('')) <= parseInt(expression.split('/').join(''))
                    || (parseInt(expression.split('/').join('')) >= parseInt(dateVale[0].split('/').join('')) &&
                    parseInt(expression.split('/').join('')) <= parseInt(dateVale[1].split('/').join('')));
            }
        }

        afterAndEqual(value: any, expression: any, dataType: any, ignoreCase: any, preciseDateFormat: any) {
            if (isNaN(parseInt(expression))) {
                return parseInt(value.split('/').join('')) == 99999999;
            }

            let dateVale = value.split('～');
            if (dateVale.length == 1) {
                return parseInt(dateVale[0].split('/').join('')) >= parseInt(expression.split('/').join(''));
            }
            return parseInt(dateVale[0].split('/').join('')) >= parseInt(expression.split('/').join(''))
                || (parseInt(expression.split('/').join('')) >= parseInt(dateVale[0].split('/').join('')) &&
                    parseInt(expression.split('/').join('')) <= parseInt(dateVale[1].split('/').join('')));
        }

        contain(value: any, expression: any, dataType: any, ignoreCase: any, preciseDateFormat: any) {
            return value.indexOf(expression) !== -1;
        }

        notContain(value: any, expression: any, dataType: any, ignoreCase: any, preciseDateFormat: any) {
            return value.indexOf(expression) == -1;
        }
    }

    export function openEditModal(id: number) {
        let vm = nts.uk.ui._viewModel.content;
        let dataSource = $("#grid").igGrid("option", "dataSource");
        let dataShare = _.find(dataSource, (value: any) => {
            return value.id == id;
        });
        dataShare.displayMode = vm.selectedMode();

        nts.uk.ui.windows.setShared("shareFromKdl016a", dataShare);
        if (dataShare.supportType === SUPPORT_TYPE.ALLDAY) {
            nts.uk.ui.windows.sub.modal("/view/kdl/016/d/index.xhtml").onClosed(() => {
                let res = nts.uk.ui.windows.getShared("shareKdl016D");
                if (!_.isNil(res) && res.reloadable) {
                    vm.loadSupportInfo(vm.selectedMode());
                }
            });
        } else {
            nts.uk.ui.windows.sub.modal("/view/kdl/016/e/index.xhtml").onClosed(() => {
                let res = nts.uk.ui.windows.getShared("shareKdl016E");
                if (!_.isNil(res) && res.reloadable) {
                    vm.loadSupportInfo(vm.selectedMode());
                }
            });
        }
    }

    class SupportInformationModel {
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
        editState: string;

        constructor(data: ISupportInformation, enableSupportTimezone: boolean) {
            const vm = this;
            vm.id = data.id;
            vm.employeeId = data.employeeId;
            vm.periodStart = data.periodStart;
            vm.periodEnd = data.periodEnd;
            vm.employeeCode = data.employeeCode;
            vm.employeeName = data.employeeName;
            vm.supportOrgName = data.supportOrgName;
            vm.supportOrgId = data.supportOrgId;
            vm.supportOrgUnit = data.supportOrgUnit;
            vm.supportType = data.supportType;
            vm.timeSpan = data.timeSpan;
            vm.supportTypeName = data.supportTypeName;
            vm.periodDisplay = data.periodDisplay;
            vm.employeeDisplay = data.employeeDisplay;
            vm.timeSpanDisplay = data.timeSpanDisplay;
            vm.displayMode = data.displayMode;
            vm.editState = enableSupportTimezone === true ? '' : 'disabled';
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
        unit: number;
        name: string;
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
        GO_TO_SUPPORT = 1,
        COME_TO_SUPPORT = 2,
    }

    enum TARGET_ORG {
        WORKPLACE = 0,
        WORKPLACE_GROUP = 1,
    }

    enum SUPPORT_TYPE {
        ALLDAY = 0,
        TIMEZONE = 1
    }
}