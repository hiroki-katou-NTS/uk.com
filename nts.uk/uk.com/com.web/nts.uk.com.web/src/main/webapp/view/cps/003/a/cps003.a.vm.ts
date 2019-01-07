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
                self.requestData();
            }
        };

        gridList = {
            inData: {
                employees: ko.observableArray([]),
                itemDefitions: ko.observableArray([])
            },
            outData: ko.observableArray([])
        }

        gridOptions: any = { columns: [], ntsControls: [], dataSource: [] };
        dataTypes: any = {};

        baseDate: KnockoutObservable<Date> = ko.observable();

        category: {
            catId: KnockoutObservable<string>;
            catCode: KnockoutObservable<string>;
            items: KnockoutObservableArray<any>;
        } = {
            catId: ko.observable(''),
            catCode: ko.observable(''),
            items: ko.observableArray([])
        };

        // for employee info.
        employees: KnockoutObservableArray<IEmployee> = ko.observableArray([]);

        constructor() {
            let self = this;

            cps003.control.selectButton();
            cps003.control.relateButton();
            //fetch all category by login 
            service.fetch.category(__viewContext.user.employeeId)
                .done(data => {
                    self.category.items(data);
                    self.baseDate(moment.utc().toISOString());
                    self.requestData();
                });

            $('#ccgcomponent').ntsGroupComponent(self.ccgcomponent).done(() => {
            });
            
            self.category.catId.subscribe(id => {
                if (_.isNil(id) || id === "") return;
                let cate = _.find(self.category.items(), c => c.id === self.category.catId());
                if (cate) {
                    self.category.catCode(cate.categoryCode);
                }
                self.requestData();
            });

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
            if ($("#grid").data("mGrid")) $("#grid").mGrid("destroy");
            new nts.uk.ui.mgrid.MGrid($("#grid")[0], {
                width: "1000px",
                height: "800px",
                headerHeight: "80px",
                subHeight: "380px",
                subWidth: "160px",
                dataSource: self.gridOptions.dataSource,
                primaryKey: "id",
                virtualization: true,
                virtualizationMode: "continuous",
                enter: "right",
                autoFitWindow: true,
                errorColumns: [],
                idGen: (id) => id + "_" + nts.uk.util.randomId(),
                columns: self.gridOptions.columns,
                features: self.gridOptions.features,
                ntsControls: self.gridOptions.ntsControls
            }).create();
        }

        requestData() {
            // { categoryId: 'COM1_00000000000000000000000_CS00020', lstEmployee: [], standardDate: '2818/01/01' };
            let self = this;
            block();
            let employeeIds = _.map(self.employees(), e => e.employeeId),
                param = { categoryId: self.category.catId(), lstEmployee: employeeIds, standardDate: moment.utc(self.baseDate(), "YYYY/MM/DD").toISOString() };
            
            if (self.category.catCode()) {
                param.categoryCode = self.category.catCode();
            }
            
            nts.uk.request.ajax('com', 'ctx/pereg/grid-layout/get-data', param).done(data => {
                self.convertData(data).done(() => {
                    self.loadGrid();
                    self.disableCS00035();
                    unblock();
                });
            });
        }
        
        disableCS00035() {
            let self = this;
            if (self.category.catCode() !== "CS00035") return;
            let $grid = $("#grid");
            _.forEach(self.gridOptions.dataSource, s => {
                cps003.control.fetch.check_remain_days(s.employeeId).done(x => {
                    $grid.mGrid(x ? "enableNtsControlAt" : "disableNtsControlAt", s.id, "IS00366");
                });
                
                cps003.control.fetch.check_remain_left(s.employeeId).done(x => {
                    $grid.mGrid(x ? "enableNtsControlAt": "disableNtsControlAt", s.id, "IS00368");
                });
            });
        }

        convertData(data: IRequestData) {
            let self = this, dfd = $.Deferred();
            if (data.headDatas) {
                data.headDatas.sort((a, b) => {
                    if (a.itemOrder === b.itemOrder) {
                        let cmp = a.itemParentCode.compareTo(b.itemParentCode);
                        if (!cmp) {
                            return a.itemCode.compareTo(b.itemCode);
                        }
                        return cmp;
                    }
                    
                    return a.itemOrder - b.itemOrder;
                });
                
                let item, control, parent = {};
                self.dataTypes = {};
                self.gridOptions.columns = [
                    { headerText: "", key: "rowNumber", dataType: "number", width: "30px" },
                    { headerText: "登録対象", key: "register", dataType: "boolean", width: "30px", ntsControl: "RegCheckBox", bound: true },
                    { headerText: "印刷対象", key: "print", dataType: "boolean", width: "30px", ntsControl: "PrintCheckBox", bound: true },
                    { headerText: "表示制御", key: "showControl", dataType: "string", width: "40px", ntsControl: "ImageShow" },
                    { headerText: "社員CD", key: "employeeCode", dataType: "string", width: "40px", ntsControl: "Label" },
                    { headerText: "氏名", key: "employeeName", dataType: "string", width: "40px", ntsControl: "Label" },
                    { headerText: "行の追加", key: "rowAdd", dataType: "string", width: "40px", ntsControl: "RowAdd" },
                    { headerText: "部門", key: "deptName", dataType: "string", width: "40px", ntsControl: "Label" },
                    { headerText: "職場", key: "workplaceName", dataType: "string", width: "40px", ntsControl: "Label" },
                    { headerText: "職位", key: "positionName", dataType: "string", width: "40px", ntsControl: "Label" },
                    { headerText: "雇用", key: "employmentName", dataType: "string", width: "40px", ntsControl: "Label" },
                    { headerText: "分類", key: "className", dataType: "string", width: "40px", ntsControl: "Label" } 
                ];
                
                self.gridOptions.ntsControls = [
                    { name: 'RegCheckBox', options: { value: 1, text: '' }, optionsValue: 'value', optionsText: 'text', controlType: 'CheckBox', enable: true },
                    { name: 'PrintCheckBox', options: { value: 1, text: '' }, optionsValue: 'value', optionsText: 'text', controlType: 'CheckBox', enable: true },
                    { name: "ImageShow", source: "hidden-button", controlType: "Image" },
                    { name: "RowAdd", source: "plus-button", controlType: "Image", copy: true }];
                let headerStyles = { name: "HeaderStyles", columns: [] };
                _.forEach(data.headDatas, (d: IDataHead) => {
                    let controlType = d.itemTypeState.dataTypeState;
                    if (controlType) {
                        let name;
                        
                        if (_.isNil(d.itemParentCode) || d.itemParentCode === "") {
                            parent[d.itemCode] = d.itemName + d.itemCode;
                            name = d.itemName + d.itemCode;
                        } else {
                            name = parent[d.itemParentCode] + "-" + d.itemName + d.itemCode;
                            parent[d.itemCode] = name;
                        }
                        
                        item = { headerText: name, itemId: d.itemId, key: d.itemCode, required: d.required, parentCode: d.itemParentCode, dataType: "string", width: "100px", perInfoTypeState: controlType };
                        controlType.required = d.required;
                        control = self.getControlType(controlType, item);
                        self.gridOptions.columns.push(item);
                        
                        if (control) {
                            self.gridOptions.ntsControls.push(control);
                            let combo = cps003.control.COMBOBOX[self.category.catCode() + "_" + d.itemCode]; 
                            if (combo) {
                                control.inputProcess = combo;
                            }
                        }
                        
                        if (d.required) {
                            headerStyles.columns.push({ key: d.itemCode, colors: ["required"] });
                        }
                        
                        cps003.control.writePrimitiveConstraint(d);
                        if (item.constraint.primitiveValue === "StampNumber") {
                            cps003.control.fetch.get_stc_setting().done((stc: StampCardEditing) => {
                                let pv = (__viewContext.primitiveValueConstraints || {}).StampNumber; 
                                if (!pv) return;
                                if (!_.isNil(stc.digitsNumber)) {
                                    __viewContext.primitiveValueConstraints.StampNumber.maxLength = stc.digitsNumber;
                                }
                                
                                switch (stc.method) {
                                    case EDIT_METHOD.PreviousZero:
                                        pv.formatOption = { autoFill: true, fillDirection: "left", fillCharacter: "0" };
                                        break;
                                    case EDIT_METHOD.AfterZero:
                                        pv.formatOption = { autoFill: true, fillDirection: "right", fillCharacter: "0" };
                                        break;
                                    case EDIT_METHOD.PreviousSpace:
                                        pv.formatOption = { autoFill: true, fillDirection: "left", fillCharacter: " " };
                                        break;
                                    case EDIT_METHOD.AfterSpace:
                                        pv.formatOption = { autoFill: true, fillDirection: "right", fillCharacter: " " };
                                        break;
                                }
                            });
                        }
                    } else {
                        parent[d.itemCode] = d.itemName + d.itemCode;
                    }
                });
                
                self.gridOptions.features = [{ name: "Resizing" }, { name: "ColumnMoving" }, { name: "Copy" }, { name: "Tooltip", error: true }];
                // TODO: Get fixed columns
                let columnFixing = { name: "ColumnFixing", columnSettings: [] };
                _.forEach([ "rowNumber", "register", "print", "showControl", "employeeCode", "employeeName", 
                            "rowAdd", "deptName", "workplaceName", "positionName", "employmentName", "className" ], f => {
                    columnFixing.columnSettings.push({ columnKey: f, isFixed: true });
                });
                
                self.gridOptions.features.push(columnFixing);
                self.gridOptions.features.push(headerStyles);
            }
            
            if (data.bodyDatas) {
                self.gridOptions.dataSource = [];
                let states = [], workTimeCodes = [], nullWorkTimeCodes = [], workTimeItems = [], nullWorkTimeItems = [], codes = {};
                _.forEach(data.bodyDatas, (d: IDataBody, ri: any) => {
                    let record = new Record(d);
                    _.forEach(d.items, (item: IColumnData, i: number) => {
                        let dt = self.dataTypes[item.itemCode];
                        if (!dt) return;
                        if (dt.cls.dataTypeValue === ITEM_SINGLE_TYPE.DATE && dt.cls.dateItemType === DateType.YEARMONTHDAY) {
                            record[item.itemCode] = _.isNil(item.value) || item.value === "" ? item.value : moment.utc(item.value, "YYYY/MM/DD").toDate();
                            if (self.category.catCode() === "CS00070" && (item.itemCode === "IS00781" || item.itemCode === "IS00782")) {
                                states.push(new State(record.id, item.itemCode, ["mgrid-disable"]));
                            }
                        } else if (dt.cls.dataTypeValue === ITEM_SINGLE_TYPE.TIMEPOINT && !_.isNil(item.value)) {
                            record[item.itemCode] = nts.uk.time.minutesBased.clock.dayattr.create(item.value).shortText;
                        } else if (dt.cls.dataTypeValue === ITEM_SINGLE_TYPE.TIME && !_.isNil(item.value)) {
                            record[item.itemCode] = nts.uk.time.parseTime(item.value, true).format();
                        } else if (dt.cls.dataTypeValue === ITEM_SINGLE_TYPE.READONLY) {
                            if (self.category.catCode() === "CS00024" && item.itemCode === "IS00289") {
                                record[item.itemCode] = !_.isNil(item.value) && item.value !== "" ? nts.uk.time.parseTime(item.value).format() : "";
                            } else {
                                record[item.itemCode] = item.value;
                            }
                        } else {
                            record[item.itemCode] = item.value;
                        }
                        
                        if (dt.cls.dataTypeValue === ITEM_SINGLE_TYPE.SELECTION || dt.cls.dataTypeValue === ITEM_SINGLE_TYPE.SEL_RADIO) {
                            if (dt.cls.referenceType === ITEM_SELECT_TYPE.ENUM || dt.cls.referenceType === ITEM_SELECT_TYPE.CODE_NAME 
                                || (dt.cls.referenceType === ITEM_SELECT_TYPE.DESIGNATED_MASTER && item.itemCode !== "IS00079")) {
                                dt.specs.options = item.lstComboBoxValue;
                            } else if (!dt.specs.options) {
                                dt.specs.options = item.lstComboBoxValue;  
                            } else {
                                dt.specs.pattern.push(item.lstComboBoxValue);
                                dt.specs.list[item.recordId] = dt.specs.pattern.length - 1;
                            }
                            
                            self.combobox(record.id, item, states);
                        } else if (dt.cls.dataTypeValue === ITEM_SINGLE_TYPE.SEL_BUTTON) {
                            dt.specs.pattern.push(item.lstComboBoxValue);
                            dt.specs.list[item.recordId] = dt.specs.pattern.length - 1;
                            if (cps003.control.WORK_TIME[item.itemCode]) {
                                if (!_.isNil(item.value)) {
                                    workTimeCodes.push(item.value);
                                    workTimeItems.push(item.itemCode);
                                } else {
                                    nullWorkTimeCodes.push(item.value);
                                    nullWorkTimeItems.push(item.itemCode);
                                }
                                
                                if (_.has(codes, item.value)) {
                                    codes[item.value].push(item.recordId);
                                } else {
                                    codes[item.value] = [ item.recordId ];
                                }
                            }
                        }
                    });
                    
                    self.gridOptions.dataSource.push(record);
                });
                
                if (workTimeCodes.length > 0) {
                    cps003.control.fetch.check_mt_se({ workTimeCodes: workTimeCodes }).done(mt => {
                        _.forEach(workTimeCodes, (c, i) => {
                            let head = _.find(mt, f => f.workTimeCode === c),
                                itemCode = workTimeItems[i],
                                workTime = cps003.control.WORK_TIME[itemCode];
                            if (head) {
                                if (workTime.firstTimes && !head.startEnd) {
                                    _.forEach(codes[c], r => {
                                        states.push(new State(r, workTime.firstTimes.start, ["mgrid-disable"]));
                                        states.push(new State(r, workTime.firstTimes.end, ["mgrid-disable"]));
                                    });
                                }
                                
                                if (workTime.secondTimes && (!head.startEnd || !head.multiTime)) {
                                    _.forEach(codes[c], r => {
                                        states.push(new State(r, workTime.secondTimes.start, ["mgrid-disable"]));
                                        states.push(new State(r, workTime.secondTimes.end, ["mgrid-disable"]));
                                    });
                                }
                            } else {
                                if (workTime.firstTimes) {
                                    _.forEach(codes[c], r => {
                                        states.push(new State(r, workTime.firstTimes.start, ["mgrid-disable"]));
                                        states.push(new State(r, workTime.firstTimes.end, ["mgrid-disable"]));
                                    });
                                }
                                
                                if (workTime.secondTimes) {
                                    _.forEach(codes[c], r => {
                                        states.push(new State(r, workTime.secondTimes.start, ["mgrid-disable"]));
                                        states.push(new State(r, workTime.secondTimes.end, ["mgrid-disable"]));
                                    });
                                }
                            }
                        });
                        
                        dfd.resolve();
                    });
                } else dfd.resolve();
                
                _.forEach(nullWorkTimeCodes, (c, i) => {
                    let itemCode = nullWorkTimeItems[i],
                        workTime = cps003.control.WORK_TIME[itemCode];    
                    if (workTime.firstTimes) {
                        _.forEach(codes[c], r => {
                            states.push(new State(r, workTime.firstTimes.start, ["mgrid-disable"]));
                            states.push(new State(r, workTime.firstTimes.end, ["mgrid-disable"]));
                        });
                    }
                    
                    if (workTime.secondTimes) {
                        _.forEach(codes[c], r => {
                            states.push(new State(r, workTime.secondTimes.start, ["mgrid-disable"]));
                            states.push(new State(r, workTime.secondTimes.end, ["mgrid-disable"]));
                        });
                    }
                });
                
                self.gridOptions.features.push({ name: "CellStyles", states: states });
            }
            
            return dfd.promise();
        }
        
        redisplayEmp() {
            $("#grid").mGrid("showHiddenRows");
        }
        
        combobox(id: any, item: IColumnData, states) {
            switch (this.category.catCode()) {
                case "CS00020":
                    switch (item.itemCode) {
                        case "IS00248":
                            if (item.value === "0") {
                                states.push(new State(id, "IS00249", ["mgrid-disable"]));
                                states.push(new State(id, "IS00250", ["mgrid-disable"]));
                                states.push(new State(id, "IS00251", ["mgrid-disable"]));
                            }
                            break;
                        case "IS00121":
                            if (item.value === "0") {
                                states.push(new State(id, "IS00123", ["mgrid-disable"]));
                                states.push(new State(id, "IS00124", ["mgrid-disable"]));
                                states.push(new State(id, "IS00125", ["mgrid-disable"]));
                                states.push(new State(id, "IS00126", ["mgrid-disable"]));
                                states.push(new State(id, "IS00127", ["mgrid-disable"])); 
                            }
                            break;
                        case "IS00123":
                            if (item.value === "0") {
                                _.remove(states, s => s.columnKey === "IS00124" || s.columnKey === "IS00125" || s.columnKey === "IS00126");
                                if (!_.find(states, s => s.columnKey === "IS00127")) {
                                    states.push(new State(id, "IS00127", ["mgrid-disable"]));
                                }
                            } else if (item.value === "1") {
                                if (!_.find(states, s => s.columnKey === "IS00124")) {
                                    states.push(new State(id, "IS00124", ["mgrid-disable"]));
                                }
                                if (!_.find(states, s => s.columnKey === "IS00125")) {
                                    states.push(new State(id, "IS00125", ["mgrid-disable"]));
                                }
                                _.remove(states, s => s.columnKey === "IS00126" || s.columnKey === "IS00127");
                            } else if (item.value === "2") {
                                if (!_.find(states, s => s.columnKey === "IS00124")) {
                                    states.push(new State(id, "IS00124", ["mgrid-disable"]));
                                }
                                if (!_.find(states, s => s.columnKey === "IS00125")) {
                                    states.push(new State(id, "IS00125", ["mgrid-disable"]));
                                }
                                if (!_.find(states, s => s.columnKey === "IS00126")) {
                                    states.push(new State(id, "IS00126", ["mgrid-disable"]));
                                }
                                if (!_.find(states, s => s.columnKey === "IS00127")) {
                                    states.push(new State(id, "IS00127", ["mgrid-disable"]));
                                }   
                            }
                            break;
                    }
                    break;
                case "CS00025":
                    switch (item.itemCode) {
                        case "IS00296":
                            if (item.value === "0") {
                                states.push(new State(id, "IS00297", ["mgrid-disable"]));
                                states.push(new State(id, "IS00298", ["mgrid-disable"]));
                                states.push(new State(id, "IS00299", ["mgrid-disable"]));
                                states.push(new State(id, "IS00300", ["mgrid-disable"]));
                                states.push(new State(id, "IS00301", ["mgrid-disable"]));
                            }
                            break;
                    }
                    break;
                case "CS00026":
                    switch (item.itemCode) {
                        case "IS00303":
                            if (item.value === "0") {
                                states.push(new State(id, "IS00304", ["mgrid-disable"]));
                                states.push(new State(id, "IS00305", ["mgrid-disable"]));
                                states.push(new State(id, "IS00306", ["mgrid-disable"]));
                                states.push(new State(id, "IS00307", ["mgrid-disable"]));
                                states.push(new State(id, "IS00308", ["mgrid-disable"]));
                            }
                            break;
                    }
                    break;
                case "CS00027":
                    switch (item.itemCode) {
                        case "IS00310":
                            if (item.value === "0") {
                                states.push(new State(id, "IS00311", ["mgrid-disable"]));
                                states.push(new State(id, "IS00312", ["mgrid-disable"]));
                                states.push(new State(id, "IS00313", ["mgrid-disable"]));
                                states.push(new State(id, "IS00314", ["mgrid-disable"]));
                                states.push(new State(id, "IS00315", ["mgrid-disable"]));
                            }
                            break;
                    }
                    break;
                case "CS00028":
                    switch (item.itemCode) {
                        case "IS00317":
                            if (item.value === "0") {
                                states.push(new State(id, "IS00318", ["mgrid-disable"]));
                                states.push(new State(id, "IS00319", ["mgrid-disable"]));
                                states.push(new State(id, "IS00320", ["mgrid-disable"]));
                                states.push(new State(id, "IS00321", ["mgrid-disable"]));
                                states.push(new State(id, "IS00322", ["mgrid-disable"]));
                            }
                            break;
                    }
                    break;
                case "CS00029":
                    switch (item.itemCode) {
                        case "IS00324":
                            if (item.value === "0") {
                                states.push(new State(id, "IS00325", ["mgrid-disable"]));
                                states.push(new State(id, "IS00326", ["mgrid-disable"]));
                                states.push(new State(id, "IS00327", ["mgrid-disable"]));
                                states.push(new State(id, "IS00328", ["mgrid-disable"]));
                                states.push(new State(id, "IS00329", ["mgrid-disable"]));
                            }
                            break;
                    }
                    break;
                case "CS00030":
                    switch (item.itemCode) {
                        case "IS00331":
                            if (item.value === "0") {
                                states.push(new State(id, "IS00332", ["mgrid-disable"]));
                                states.push(new State(id, "IS00333", ["mgrid-disable"]));
                                states.push(new State(id, "IS00334", ["mgrid-disable"]));
                                states.push(new State(id, "IS00335", ["mgrid-disable"]));
                                states.push(new State(id, "IS00336", ["mgrid-disable"]));
                            }
                            break;
                    }
                    break;
                case "CS00031":
                    switch (item.itemCode) {
                        case "IS00338":
                            if (item.value === "0") {
                                _.forEach(['IS00339', 'IS00340', 'IS00341', 'IS00342', 'IS00343'], code => {
                                    states.push(new State(id, code, ["mgrid-disable"]));
                                });
                            }
                            break;
                    }
                    break;
                case "CS00032":
                    switch (item.itemCode) {
                        case "IS00345":
                            if (item.value === "0") {
                                _.forEach(['IS00346', 'IS00347', 'IS00348', 'IS00349', 'IS00350'], code => {
                                    states.push(new State(id, code, ["mgrid-disable"]));
                                });
                            }
                            break;
                    }
                    break;
                case "CS00033":
                    switch (item.itemCode) {
                        case "IS00352":
                            if (item.value === "0") {
                                _.forEach(['IS00353', 'IS00354', 'IS00355', 'IS00356', 'IS00357'], code => {
                                    states.push(new State(id, code, ["mgrid-disable"]));
                                });
                            }
                            break;
                    }
                    break;
                case "CS00034":
                    switch (item.itemCode) {
                        case "IS00359":
                            if (item.value === "0") {
                                _.forEach(['IS00360', 'IS00361', 'IS00362', 'IS00363', 'IS00364'], code => {
                                    states.push(new State(id, code, ["mgrid-disable"]));
                                });
                            }
                            break;
                    }
                    break;
                case "CS00035":
                    switch (item.itemCode) {
                        case "IS00370":
                            if (item.value === "0") {
                                _.forEach(['IS00371', 'IS00372', 'IS00374'], code => {
                                    states.push(new State(id, code, ["mgrid-disable"]));
                                });
                            }
                            break;
                    }
                    break;
                case "CS00036":
                    switch (item.itemCode) {
                        case "IS00375":
                            if (item.value === "0") {
                                _.forEach(['IS00376', 'IS00377', 'IS00378', 'IS00379'], code => {
                                    states.push(new State(id, code, ["mgrid-disable"]));
                                });
                            }
                            break;
                        case "IS00380":
                            if (item.value === "0") {
                                _.forEach(['IS00381', 'IS00382', 'IS00383', 'IS00384'], code => {
                                    states.push(new State(id, code, ["mgrid-disable"]));
                                });
                            }
                            break;
                    }
                    break;
                case "CS00049":
                    switch (item.itemCode) {
                        case "IS00560":
                            if (item.value === "0") {
                                _.forEach(['IS00561', 'IS00562', 'IS00563', 'IS00564', 'IS00565'], code => {
                                    states.push(new State(id, code, ["mgrid-disable"]));
                                });
                            }
                            break;
                    }
                    break;
                case "CS00050":
                    switch (item.itemCode) {
                        case "IS00567":
                            if (item.value === "0") {
                                _.forEach(['IS00568', 'IS00569', 'IS00570', 'IS00571', 'IS00572'], code => {
                                    states.push(new State(id, code, ["mgrid-disable"]));
                                });
                            }
                            break;
                    }
                    break;
                case "CS00051":
                    switch (item.itemCode) {
                        case "IS00574":
                            if (item.value === "0") {
                                _.forEach(['IS00575', 'IS00576', 'IS00577', 'IS00578', 'IS00579'], code => {
                                    states.push(new State(id, code, ["mgrid-disable"]));
                                });
                            }
                            break;
                    }
                    break;
                case "CS00052":
                    switch (item.itemCode) {
                        case "IS00581":
                            if (item.value === "0") {
                                _.forEach(['IS00582', 'IS00583', 'IS00584', 'IS00585', 'IS00586'], code => {
                                    states.push(new State(id, code, ["mgrid-disable"]));
                                });
                            }
                            break;
                    }
                    break;
                case "CS00053":
                    switch (item.itemCode) {
                        case "IS00588":
                            if (item.value === "0") {
                                _.forEach(['IS00589', 'IS00590', 'IS00591', 'IS00592', 'IS00593'], code => {
                                    states.push(new State(id, code, ["mgrid-disable"]));
                                });
                            }
                            break;
                    }
                    break;
                case "CS00054":
                    switch (item.itemCode) {
                        case "IS00595":
                            if (item.value === "0") {
                                _.forEach(['IS00596', 'IS00597', 'IS00598', 'IS00599', 'IS00600'], code => {
                                    states.push(new State(id, code, ["mgrid-disable"]));
                                });
                            }
                            break;
                    }
                    break;
                case "CS00055":
                    switch (item.itemCode) {
                        case "IS00602":
                            if (item.value === "0") {
                                _.forEach(['IS00603', 'IS00604', 'IS00605', 'IS00606', 'IS00607'], code => {
                                    states.push(new State(id, code, ["mgrid-disable"]));
                                });
                            }
                            break;
                    }
                    break;
                case "CS00056":
                    switch (item.itemCode) {
                        case "IS00609":
                            if (item.value === "0") {
                                _.forEach(['IS00610', 'IS00611', 'IS00612', 'IS00613', 'IS00614'], code => {
                                    states.push(new State(id, code, ["mgrid-disable"]));
                                });
                            }
                            break;
                    }
                    break;
                case "CS00057":
                    switch (item.itemCode) {
                        case "IS00616":
                            if (item.value === "0") {
                                _.forEach(['IS00617', 'IS00618', 'IS00619', 'IS00620', 'IS00621'], code => {
                                    states.push(new State(id, code, ["mgrid-disable"]));
                                });
                            }
                            break;
                    }
                    break;
                case "CS00058":
                    switch (item.itemCode) {
                        case "IS00623":
                            if (item.value === "0") {
                                _.forEach(['IS00624', 'IS00625', 'IS00626', 'IS00627', 'IS00628'], code => {
                                    states.push(new State(id, code, ["mgrid-disable"]));
                                });
                            }
                            break;
                    }
                    break;
            }
        }
        
        getControlType(controlType: IItemDefinitionData, item: any) {
            if (_.isNil(controlType)) return;
            let self = this, control, name;
            self.dataTypes[item.key] = { cls: controlType };
            if (item.key === "IS00779") {
                item.constraint = { primitiveValue: "StampNumber", isCheckExpression: true };
            } else {
                item.constraint = { primitiveValue: item.itemId.replace(/[-_]/g, "") };
            }
            switch (controlType.dataTypeValue) {
                case ITEM_SINGLE_TYPE.STRING:
                    break;
                case ITEM_SINGLE_TYPE.NUMERIC:
                    let timeNumber = cps003.control.NUMBER[self.category.catCode() + "_" + item.key];
                    if (timeNumber) item.inputProcess = timeNumber;
                    break;
                case ITEM_SINGLE_TYPE.DATE:
                    item.columnCssClass = "halign-right";
                    if (controlType.dateItemType === DateType.YEARMONTHDAY) {
                        name = "DatePickerYMD" + item.key;
                        item.constraint.type = "ymd";
                        control = { name: name, format: "ymd", controlType: "DatePicker" };
                        let dp = cps003.control.DATE_TIME[self.category.catCode() + "_" + item.key];
                        if (dp) control.inputProcess = dp;
                    } else if (controlType.dateItemType === DateType.YEARMONTH) {
                        name = "DatePickerYM" + item.key;
                        item.constraint.type = "ym";
                        control = { name: name, format: "ym", controlType: "DatePicker" };
                    } else {
                        name = "DatePickerY" + item.key;
                        item.constraint.type = "y";
                        control = { name: name, format: "y", controlType: "DatePicker" };
                    }
                    
                    item.ntsControl = name;
                    break;
                case ITEM_SINGLE_TYPE.TIME:
                    item.columnCssClass = "halign-right";
                    timeNumber = cps003.control.NUMBER[self.category.catCode() + "_" + item.key];
                    if (timeNumber) item.inputProcess = timeNumber;
                    break;    
                case ITEM_SINGLE_TYPE.TIMEPOINT:
                    item.columnCssClass = "halign-right";
                    timeNumber = cps003.control.NUMBER[self.category.catCode() + "_" + item.key];
                    if (timeNumber) item.inputProcess = timeNumber;
                    break;
                case ITEM_SINGLE_TYPE.SELECTION:
                case ITEM_SINGLE_TYPE.SEL_RADIO:
                    name = "Combobox" + item.key;
                    control = { name: name, optionsValue: "optionValue", optionsText: "optionText", displayMode: "name", enable: true, controlType: "ComboBox" };
                    if (controlType.referenceType === ITEM_SELECT_TYPE.CODE_NAME
                        || (controlType.referenceType === ITEM_SELECT_TYPE.DESIGNATED_MASTER && item.key === "IS00079")) {
                        control.pattern = [];
                        control.list = {};
                    }
                    
                    self.dataTypes[item.key].specs = control;
                    item.ntsControl = name; 
                    break;
                case ITEM_SINGLE_TYPE.SEL_BUTTON:
                    name = "ReferButton" + item.key;
                    let notFoundMes = nts.uk.resource.getText("CPS001_107"); 
                    control = { name: name, enable: true, optionsValue: "optionValue", optionsText: "optionText", text: "参照", notFound: notFoundMes, pattern: [], list: {}, controlType: "ReferButton" };
                    let selectBtn = cps003.control.SELECT_BUTTON[self.category.catCode() + "_" + item.key];
                    control.click = selectBtn && selectBtn.bind(null, item.required);
                    self.dataTypes[item.key].specs = control;
                    item.ntsControl = name;
                    break;
                case ITEM_SINGLE_TYPE.READONLY:
                    item.ntsControl = "Label";
                    break;
                case ITEM_SINGLE_TYPE.RELATE_CATEGORY:
                    name = "RelateButton" + item.key;
                    control = { name: name, enable: true, text: "詳細情報", labelPosition: "before", controlType: "ReferButton" };
                    let selectBtn = cps003.control.RELATE_BUTTON[self.category.catCode() + "_" + item.key];
                    control.click = selectBtn && selectBtn.bind(null);
                    item.ntsControl = name;
                    break;
                case ITEM_SINGLE_TYPE.NUMBERIC_BUTTON:
                    break;
                case ITEM_SINGLE_TYPE.READONLY_BUTTON:
                    break;
            }
            
            return control;
        }
        
        selectButtonClick(itemCode: string) {
            let selectButtonDlg = {};
            let itemCodes = [ "IS00130", "IS00131", "IS00128", "IS00139", "IS00140", "IS00157", "IS00158",
                "IS00166", "IS00167", "IS00175", "IS00176", "IS00148", "IS00149", "IS00193", "IS00194", "IS00202",
                "IS00203", "IS00211", "IS00212", "IS00220", "IS00221", "IS00229", "IS00230", "IS00238", "IS00239",
                "IS00184", "IS00185", "IS00084", "IS00085" ];
            
            _.forEach(itemCodes, code => {
                selectButtonDlg[code] = () => {
                    // Param KDL002
                    // List code to display in grid (lstComboboxValue)
                    nts.uk.ui.windows.setShared("KDL002_AllItemObj");
                    // Selected code list (value)
                    nts.uk.ui.windows.setShared("KDL002_SelectedItemId");
                    // Single/multiple (boolean)
                    nts.uk.ui.windows.setShared("KDL002_Multiple", false);
                    // Not select -> click button 決定 -> show msg10 or not (boolean)
                    nts.uk.ui.windows.setShared("KDL002_isAcceptSelectNone", false);
                    // Show item "選択なし" or not
                    nts.uk.ui.windows.setShared("KDL002_isShowNoSelectRow", false);
                    nts.uk.ui.windows.sub.modal("/view/kdl/002/a/index.xhtml", { title: "" }).onClosed(() => {
                        // Selected result
                        let ls = nts.uk.ui.windows.getShared('KDL002_SelectedNewItem');
                    });
                };      
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
    export interface IDataHead {
        itemId: string;
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
        classification: ICodeName;
        department: ICodeName;
        employee: ICodeName;
        employeeBirthday: string;
        employment: ICodeName;
        position: ICodeName;
        workplace: ICodeName;
        items: IColumnData[];
    }

    interface ICodeName {
        code: string;
        name: string;
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

    export enum ACTION_ROLE {
        HIDDEN = <any>"HIDDEN",
        VIEW_ONLY = <any>"VIEW_ONLY",
        EDIT = <any>"EDIT"
    }

    // define ITEM_SINGLE_TYPE
    // type of item if it's single item
    export enum ITEM_SINGLE_TYPE {
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
    export enum ITEM_STRING_DTYPE {
        FIXED_LENGTH = 1, // fixed length
        VARIABLE_LENGTH = 2 // variable length
    }

    export enum ITEM_STRING_TYPE {
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
    export enum ITEM_SELECT_TYPE {
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
    
    class State {
        rowId: number;
        columnKey: string;
        state: Array<any>
        constructor(rowId: string, columnKey: string, state: Array<any>) {
            this.rowId = rowId;
            this.columnKey = columnKey;
            this.state = state;
        }
    }
    
    class Record {
        id: string;
        employeeId: string;
        employeeCode: string;
        employeeName: string;
        classCode: string;
        className: string;
        deptCode: string;
        deptName: string;
        employmentCode: string;
        employmentName: string;
        positionCode: string;
        positionName: string;
        workplaceCode: string;
        workplaceName: string;
        register: boolean;
        print: boolean;
        
        constructor(data: IDataBody) {
            this.id = (data.items && data.items[0] && data.items[0].recordId) || nts.uk.util.randomId() + "_noData";
            this.employeeId = data.employeeId;
            this.employeeCode = data.employee.code;
            this.employeeName = data.employee.name;
            this.classCode = data.classification.code;
            this.className = data.classification.name;
            this.deptCode = data.department.code;
            this.deptName = data.department.name;
            this.employmentCode = data.employment.code;
            this.employmentName = data.employment.name;
            this.positionCode = data.position.code;
            this.positionName = data.position.name;
            this.workplaceCode = data.workplace.code;
            this.workplaceName = data.workplace.name;
            this.register = false;
            this.print = false;
        }
    }
    
    interface StampCardEditing {
        method: EDIT_METHOD;
        digitsNumber: number;
    }
    
    enum EDIT_METHOD {
        PreviousZero = 1,
        AfterZero = 2,
        PreviousSpace = 3,
        AfterSpace = 4
    }
}