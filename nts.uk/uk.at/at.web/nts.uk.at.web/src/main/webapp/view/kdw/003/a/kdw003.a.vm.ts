module nts.uk.at.view.kdw003.a.viewmodel {
    export interface EmployeeSearchDto {
        employeeId: string;
        employeeCode: string;
        employeeName: string;
        workplaceCode: string;
        workplaceId: string;
        workplaceName: string;
    }

    var LIST_FIX_HEADER = [
        { headerText: 'ID', key: 'id', dataType: 'String', width: '30px', ntsControl: 'Label' },
        { headerText: '状<br/>態', key: 'state', dataType: 'String', width: '30px', ntsControl: 'Label' },
        { headerText: 'ER/AL', key: 'error', dataType: 'String', width: '60px', ntsControl: 'Label' },
        { headerText: nts.uk.resource.getText("KDW003_41"), key: 'date', dataType: 'String', width: '90px', ntsControl: 'Label' },
        { headerText: nts.uk.resource.getText("KDW003_42"), key: 'sign', dataType: 'boolean', width: '35px', ntsControl: 'Checkbox' },
        { headerText: nts.uk.resource.getText("KDW003_32"), key: 'employeeCode', dataType: 'String', width: '120px', ntsControl: 'Label' },
        { headerText: nts.uk.resource.getText("KDW003_33"), key: 'employeeName', dataType: 'String', width: '190px', ntsControl: 'Label' },
        { headerText: '', key: "picture-person", dataType: "string", width: '35px', ntsControl: 'Image' }
    ];

    export class ScreenModel {
        fixHeaders: KnockoutObservableArray<any> = ko.observableArray([]);

        legendOptions: any;
        //grid user setting
        cursorMoveDirections: KnockoutObservableArray<any> = ko.observableArray([
            { code: 0, name: "縦" },
            { code: 1, name: "横" }
        ]);
        selectedDirection: KnockoutObservable<any> = ko.observable(0);
        displayWhenZero: KnockoutObservable<boolean> = ko.observable(false);
        showHeaderNumber: KnockoutObservable<boolean> = ko.observable(false);
        showProfileIcon: KnockoutObservable<boolean> = ko.observable(false);
        //ccg001 component: search employee
        ccg001: any;
        baseDate: KnockoutObservable<Date> = ko.observable(new Date());
        //kcp009 component: employee picker
        selectedEmployee: KnockoutObservable<any> = ko.observable(null);
        lstEmployee: KnockoutObservableArray<any> = ko.observableArray([]);;
        //data grid
        displayFormatOptions: KnockoutObservableArray<any>;
        displayFormat: KnockoutObservable<number> = ko.observable(null);
        headersGrid: KnockoutObservableArray<any>;
        columnSettings: KnockoutObservableArray<any> = ko.observableArray([]);
        sheetsGrid: KnockoutObservableArray<any> = ko.observableArray([]);
        fixColGrid: KnockoutObservableArray<any>;
        dailyPerfomanceData: KnockoutObservableArray<any> = ko.observableArray([]);
        cellStates: KnockoutObservableArray<any> = ko.observableArray([]);
        rowStates: KnockoutObservableArray<any> = ko.observableArray([]);
        dpData: Array<any> = [];
        headerColors: KnockoutObservableArray<any> = ko.observableArray([]);
        textColors: KnockoutObservableArray<any> = ko.observableArray([]);
        lstDate: KnockoutObservableArray<any> = ko.observableArray([]);
        optionalHeader: Array<any> = [];
        employeeModeHeader: Array<any> = [];
        dateModeHeader: Array<any> = [];
        errorModeHeader: Array<any> = [];
        formatCodes: KnockoutObservableArray<any> = ko.observableArray([]);
        lstAttendanceItem: KnockoutObservableArray<any> = ko.observableArray([]);
        //A13_1 コメント
        comment: KnockoutObservable<any> = ko.observable(null);
        employeeModeFixCol: Array<any> = [
            { columnKey: 'id', isFixed: true },
            { columnKey: 'state', isFixed: true },
            { columnKey: 'error', isFixed: true },
            { columnKey: 'date', isFixed: true }
        ];
        dateModeFixCol: Array<any> = [
            { columnKey: 'id', isFixed: true },
            { columnKey: 'state', isFixed: true },
            { columnKey: 'error', isFixed: true },
            { columnKey: 'employeeCode', isFixed: true },
            { columnKey: 'employeeName', isFixed: true }
        ];
        errorModeFixCol: Array<any> = [
            { columnKey: 'id', isFixed: true },
            { columnKey: 'state', isFixed: true },
            { columnKey: 'error', isFixed: true },
            { columnKey: 'employeeCode', isFixed: true },
            { columnKey: 'employeeName', isFixed: true },
            { columnKey: 'date', isFixed: true }
        ];
        // date ranger component
        dateRanger: KnockoutObservable<any> = ko.observable(null);
        // date picker component
        selectedDate: KnockoutObservable<any> = ko.observable(null);

        showButton: KnockoutObservable<AuthorityDetailModel> = ko.observable(null);

        referenceVacation: KnockoutObservable<ReferenceVacation> = ko.observable(null);

        employmentCode: KnockoutObservable<any> = ko.observable("");

        editValue: KnockoutObservableArray<InfoCellEdit> = ko.observableArray([]);
        
        itemValueAll: KnockoutObservableArray<any> = ko.observableArray([]);
        
        itemValueAllTemp: KnockoutObservableArray<any> = ko.observableArray([]);
        
        lockMessage: KnockoutObservable<any> = ko.observable("");

        dataHoliday: KnockoutObservable<DataHoliday> =  ko.observable(new DataHoliday("0","0","0","0","0","0"));
        comboItems: KnockoutObservableArray<any> = ko.observableArray([new ItemModel('1', '基本給'),
            new ItemModel('2', '役職手当'),
            new ItemModel('3', '基本給2')]);

        comboColumns: KnockoutObservableArray<any> = ko.observableArray([{ prop: 'code', length: 1 },
            { prop: 'name', length: 2 }]);
        comboColumnsCalc: KnockoutObservableArray<any> = ko.observableArray([{ prop: 'code', length: 1 },
            { prop: 'name', length: 8 }]);
        comboItemsDoWork: KnockoutObservableArray<any> = ko.observableArray([]);
        comboItemsReason: KnockoutObservableArray<any> = ko.observableArray([]);
        comboItemsCalc: KnockoutObservableArray<any> = ko.observableArray([]);
        showPrincipal: KnockoutObservable<any> = ko.observable(true);
        dataAll: KnockoutObservable<any> = ko.observable(null);
        hasLstHeader : boolean  =  true;

        constructor() {
            var self = this;
            self.initLegendButton();
            self.initDateRanger();
            self.initDisplayFormat();
            self.headersGrid = ko.observableArray(self.employeeModeHeader);
            self.fixColGrid = ko.observableArray(self.employeeModeFixCol);
            // show/hide header number
            self.showHeaderNumber.subscribe((val) => {
                $.when(self.destroyGrid()).then( data => {
                    self.reloadGrid();
                });
               
            });
            // show/hide profile icon
            self.showProfileIcon.subscribe((val) => {
                self.reloadGrid();
            });
            //$("#fixed-table").ntsFixedTable({ height: 50, width: 300 });
            $(document).mouseup(function(e) {
                var container = $(".ui-tooltip");
                if (!container.is(e.target) &&
                    container.has(e.target).length === 0) {
                     $("#tooltip").hide();
                }
            });
        }
         helps(event, data){
             var self = this;
             $('#tooltip').css({
                 'left': event.pageX+40,
                 'top':  event.pageY-20
             });
             self.lockMessage(data);
             $("#tooltip").show();
         }
        initLegendButton() {
            var self = this;
            self.legendOptions = {
                items: [
                    { colorCode: '#94B7FE', labelText: '手修正（本人）' },
                    { colorCode: '#CEE6FF', labelText: '手修正（他人）' },
                    { colorCode: '#BFEA60', labelText: '申請反映' },
                    { colorCode: '#F69164', labelText: '計算値' },
                    { colorCode: '#FD4D4D', labelText: nts.uk.resource.getText("KDW003_44") },
                    { colorCode: '#F6F636', labelText: nts.uk.resource.getText("KDW003_45") },
                ]
            };
        }

        initDateRanger() {
            var self = this;
            self.dateRanger.subscribe((dateRange) => {
                if (dateRange && dateRange.startDate && dateRange.endDate) {
                    self.selectedDate(dateRange.startDate);
                    var elementDate = dateRange.startDate;
                    while (!moment(elementDate, "YYYY/MM/DD").isAfter(dateRange.endDate)) {
                        self.lstDate.push({ date: elementDate });
                        elementDate = moment(elementDate, "YYYY/MM/DD").add(1, 'd').format("YYYY/MM/DD");
                    }
                }
            });
            self.dateRanger({
                startDate: moment().add(-1, "M").add(1 ,"d").format("YYYY/MM/DD"),
                endDate: moment().format("YYYY/MM/DD")
            });
        }

        initDisplayFormat() {
            var self = this;
            self.displayFormatOptions = ko.observableArray([
                { code: 0, name: nts.uk.resource.getText("Enum_DisplayFormat_Individual") },
                { code: 1, name: nts.uk.resource.getText("Enum_DisplayFormat_ByDate") },
                { code: 2, name: nts.uk.resource.getText("Enum_DisplayFormat_ErrorAlarm") }
            ]);
            self.displayFormat(0);
        }
        createSumColumn(data : any){
            var self = this;
            _.each(data.lstControlDisplayItem.columnSettings, function(item) {
                if (self.displayFormat() == 0) {
                    if (item.columnKey == "date") {
                        item.allowSummaries = true;
                        item['summaryOperands'] = [{ type: "custom", order: 0, summaryCalculator: function() { return "合計"; } }];
                    }
                } else {
                    if (item.columnKey == "employeeCode") {
                        item.allowSummaries = true;
                        item['summaryOperands'] = [{ type: "custom", order: 0, summaryCalculator: function() { return "合計"; } }];
                    }
                }
                if (item.typeFormat !=  null && item.typeFormat != undefined) {
                    if (item.typeFormat == 2) {
                        //so lan
                        item.allowSummaries = true;
                        item['summaryOperands'] = [{
                            rowDisplayLabel: "合計",
                            type: "custom",
                            summaryCalculator: $.proxy(self.totalNumber, this),
                            order: 0
                        }]
                    }
                    else if (item.typeFormat == 5) {
                        //thoi gian
                        item.allowSummaries = true;
                        item['summaryOperands'] = [{
                            rowDisplayLabel: "合計",
                            type: "custom",
                            summaryCalculator: $.proxy(self.totalTime, this),
                            order: 0
                        }]
                    }
                    else if (item.typeFormat == 3) {
                        //so tien 
                        item.allowSummaries = true;
                        item['summaryOperands'] = [{
                            rowDisplayLabel: "合計",
                            type: "custom",
                            summaryCalculator: $.proxy(self.totalMoney, this),
                            order: 0
                        }]
                    }
                }
                delete item.typeFormat;
                self.columnSettings(data.lstControlDisplayItem.columnSettings);
            });
        }
        startPage(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            let dateRangeParam = nts.uk.ui.windows.getShared('DateRangeKDW003');
            var param = {
                dateRange: dateRangeParam? {
                    startDate: moment(dateRangeParam.startDate).utc().toISOString(),
                    endDate: moment(dateRangeParam.endDate).utc().toISOString()
                }: null,
                displayFormat : 0,
                initScreen: 0,
                lstEmployee: [],
                formatCodes: self.formatCodes()
            };
            nts.uk.ui.block.invisible();
            nts.uk.ui.block.grayout();
            service.startScreen(param).done((data) => {
                console.log(data);
                self.dateRanger().startDate = data.dateRange.startDate;
                self.dateRanger().endDate = data.dateRange.endDate;
                self.dateRanger.valueHasMutated();
                self.dataAll(data);
                self.itemValueAll(data.itemValues);
                self.comment(data.comment != null ? '■ ' + data.comment : null);
                self.formatCodes(data.lstControlDisplayItem.formatCode);
//                _.each(data.lstControlDisplayItem.lstSheet, function(item) {
//                    item.columns.unshift("sign");
//                });
                self.createSumColumn(data);
                // combo box
                self.comboItemsCalc(data.lstControlDisplayItem.comboItemCalc);
                self.comboItemsReason(data.lstControlDisplayItem.comboItemReason);
                self.comboItemsDoWork(data.lstControlDisplayItem.comboItemDoWork);
                
                self.employmentCode(data.employmentCode);
                self.lstAttendanceItem(data.lstControlDisplayItem.lstAttendanceItem);
                self.showButton = ko.observable(new AuthorityDetailModel(data.authorityDto, data.lstControlDisplayItem.settingUnit));
                self.referenceVacation(new ReferenceVacation(data.yearHolidaySettingDto == null ? false : data.yearHolidaySettingDto.manageAtr, data.substVacationDto == null ? false : data.substVacationDto.manageAtr, data.compensLeaveComDto == null ? false : data.compensLeaveComDto.manageAtr, data.com60HVacationDto == null ? false : data.com60HVacationDto.manageAtr, self.showButton()));
                // Fixed Header
                self.fixHeaders(data.lstFixedHeader);
                self.showPrincipal(data.showPrincipal);
                self.showPrincipal(false);
                if(data.lstControlDisplayItem.lstHeader.length == 0) self.hasLstHeader = false;
                if (data.showPrincipal || data.lstControlDisplayItem.lstHeader.length == 0) {
                    self.employeeModeHeader = [self.fixHeaders()[0], self.fixHeaders()[1], self.fixHeaders()[2], self.fixHeaders()[3], self.fixHeaders()[4]];
                    self.dateModeHeader = [self.fixHeaders()[0], self.fixHeaders()[1], self.fixHeaders()[2], self.fixHeaders()[5], self.fixHeaders()[6], self.fixHeaders()[4]];
                    self.errorModeHeader = [self.fixHeaders()[0], self.fixHeaders()[1], self.fixHeaders()[2], self.fixHeaders()[5], self.fixHeaders()[6], self.fixHeaders()[3], self.fixHeaders()[4]];
                } else {
                    self.employeeModeHeader = [self.fixHeaders()[0], self.fixHeaders()[1], self.fixHeaders()[2], self.fixHeaders()[3]];
                    self.dateModeHeader = [self.fixHeaders()[0], self.fixHeaders()[1], self.fixHeaders()[2], self.fixHeaders()[5], self.fixHeaders()[6]];
                    self.errorModeHeader = [self.fixHeaders()[0], self.fixHeaders()[1], self.fixHeaders()[2], self.fixHeaders()[5], self.fixHeaders()[6], self.fixHeaders()[3]];
                }
                self.lstEmployee(_.orderBy(data.lstEmployee, ['code'], ['asc']));
                self.receiveData(data);
                let employeeLogin: any = _.find(self.lstEmployee(), function(data){
                    return data.loginUser == true;
                });
                self.selectedEmployee(employeeLogin.id);
                self.extractionData();
                self.loadGrid();
                //  self.extraction();
                self.initCcg001();
                self.loadCcg001();
                nts.uk.ui.block.clear();
                dfd.resolve();
            }).fail(function(error) {
                if (error.messageId == "KDW/003/a") {
                    //self.selectDisplayItem();
                nts.uk.ui.windows.setShared("selectedPerfFmtCodeList", "");
                nts.uk.ui.windows.sub.modal("/view/kdw/003/c/index.xhtml").onClosed(() => {
                    var dataTemp = nts.uk.ui.windows.getShared('dailyPerfFmtList');
                    if (dataTemp != undefined) {
                        let data = [dataTemp.dailyPerformanceFormatCode()];
                        
                        let param = {
                            dateRange: {
                                startDate: moment(self.dateRanger().startDate).utc().toISOString(),
                                endDate: moment(self.dateRanger().endDate).utc().toISOString()
                            },
                            lstEmployee: [],
                            formatCodes: data
                        };
                        nts.uk.ui.block.invisible();
                        nts.uk.ui.block.grayout();
                        service.selectFormatCode(param).done((data) => {
                            self.formatCodes(data.lstControlDisplayItem.formatCode);
//                            _.each(data.lstControlDisplayItem.lstSheet, function(item) {
//                                item.columns.unshift("sign");
//                            });
                             self.createSumColumn(data);
                            self.employmentCode(data.employmentCode);
                            self.lstAttendanceItem(data.lstControlDisplayItem.lstAttendanceItem);
                            self.showButton(new AuthorityDetailModel(data.authorityDto, data.lstControlDisplayItem.settingUnit));
                            self.referenceVacation(new ReferenceVacation(data.yearHolidaySettingDto == null ? false : data.yearHolidaySettingDto.manageAtr, data.substVacationDto == null ? false : data.substVacationDto.manageAtr, data.compensLeaveComDto == null ? false : data.compensLeaveComDto.manageAtr, data.com60HVacationDto == null ? false : data.com60HVacationDto.manageAtr, self.showButton()));
                            // Fixed Header
                            self.fixHeaders(data.lstFixedHeader);
                            self.showPrincipal(data.showPrincipal);
                             self.showPrincipal(false);
                            if (data.showPrincipal) {
                                self.employeeModeHeader = [self.fixHeaders()[0], self.fixHeaders()[1], self.fixHeaders()[2], self.fixHeaders()[3], self.fixHeaders()[4]];
                                self.dateModeHeader = [self.fixHeaders()[0], self.fixHeaders()[1], self.fixHeaders()[2], self.fixHeaders()[5], self.fixHeaders()[6], self.fixHeaders()[4]];
                                self.errorModeHeader = [self.fixHeaders()[0], self.fixHeaders()[1], self.fixHeaders()[2], self.fixHeaders()[5], self.fixHeaders()[6], self.fixHeaders()[3], self.fixHeaders()[4]];
                            } else {
                                self.employeeModeHeader = [self.fixHeaders()[0], self.fixHeaders()[1], self.fixHeaders()[2], self.fixHeaders()[3]];
                                self.dateModeHeader = [self.fixHeaders()[0], self.fixHeaders()[1], self.fixHeaders()[2], self.fixHeaders()[5], self.fixHeaders()[6]];
                                self.errorModeHeader = [self.fixHeaders()[0], self.fixHeaders()[1], self.fixHeaders()[2], self.fixHeaders()[5], self.fixHeaders()[6], self.fixHeaders()[3]];
                            }
                            self.lstEmployee(_.orderBy(data.lstEmployee, ['code'], ['asc']));
                            self.receiveData(data);
                            let employeeLogin: any = _.find(self.lstEmployee(), function(data) {
                                return data.loginUser == true;
                            });
                            self.selectedEmployee(employeeLogin.id);
                            self.extractionData();
                            self.loadGrid();
                            //  self.extraction();
                            self.initCcg001();
                            self.loadCcg001();
                            nts.uk.ui.block.clear();
                            dfd.resolve();
                        }).fail(function(error) {
                            nts.uk.ui.dialog.alert(error.message);
                            nts.uk.ui.block.clear();
                        });
                    }
                });
                }
                else {
                    nts.uk.ui.dialog.alert(error.message);
                }
                nts.uk.ui.block.clear();
            });
            return dfd.promise();
        }

        receiveData(data) {
            var self = this;
            self.dpData = data.lstData;
            self.cellStates(data.lstCellState);
            self.optionalHeader = data.lstControlDisplayItem.lstHeader;
            self.sheetsGrid(data.lstControlDisplayItem.lstSheet);
            self.sheetsGrid.valueHasMutated();
        }
        proceed() {
            nts.uk.ui.block.invisible();
            nts.uk.ui.block.grayout();
            var self = this;
            let dataChange: any = $("#dpGrid").ntsGrid("updatedCells");
            let dataChangeProcess: any = [];
            _.each(dataChange, (data: any) => {
                if (data.columnKey != "sign" && data.value != "") {
                    let dataTemp = _.find(self.dpData, (item: any) => {
                        return item.id == data.rowId.substring(1, data.rowId.length);
                    });
                    if (data.columnKey.indexOf("Code") == -1 && data.columnKey.indexOf("NO") == -1) {
                        if (data.columnKey.indexOf("Name") != -1) {
                            // todo
                        } else {
                            //get layout , and type
                            let layoutAndType: any = _.find(self.itemValueAll(), (item: any) => {
                                return item.itemId == data.columnKey.substring(1, data.columnKey.length);
                            });
                            let value: any;
                            value = self.getPrimitiveValue(data.value);
                            let dataMap = new InfoCellEdit(data.rowId, data.columnKey.substring(1, data.columnKey.length), value, layoutAndType == undefined ? "" : layoutAndType.valueType, layoutAndType == undefined ? "" : layoutAndType.layoutCode, dataTemp.employeeId, moment(dataTemp.date).utc().toISOString(), 0);
                            dataChangeProcess.push(dataMap);
                        }
                    } else {
                        let columnKey: any;
                        let item: any;
                        if (data.columnKey.indexOf("Code") != -1) {
                            columnKey = data.columnKey.substring(4, data.columnKey.length);
                        } else {
                            columnKey = data.columnKey.substring(2, data.columnKey.length);
                        }
                        //TO Thanh: move find logic out if condition
                        item = _.find(self.lstAttendanceItem(), (data) => {
                            return String(data.id) === columnKey;
                        })

                        let layoutAndType: any = _.find(self.itemValueAll(), (item: any) => {
                            return item.itemId == columnKey;
                        });
                        let dataMap = new InfoCellEdit(data.rowId, columnKey, String(data.value), layoutAndType.valueType, layoutAndType.layoutCode, dataTemp.employeeId, moment(dataTemp.date).utc().toISOString(), item.typeGroup);
                        dataChangeProcess.push(dataMap);
                    }
                }
            });
            let param = { itemValues: dataChangeProcess }
            if (dataChangeProcess.length > 0) {
                let dfd = $.Deferred();
                service.addAndUpdate(dataChangeProcess).done((data) => {
                    // alert("done");
                    dataChange = {};
                    self.btnExtraction_Click();
                    // nts.uk.ui.block.clear();
                    dfd.resolve();
                }).fail((data) => {
                    alert("fail");
                     nts.uk.ui.block.clear();
                    dfd.resolve();
                });
                dfd.promise();
            }else{
                 nts.uk.ui.block.clear(); 
            }
            debugger;
        }
        
        proceedSave() {
            nts.uk.ui.block.invisible();
            nts.uk.ui.block.grayout();
            var self = this;
            let dataChange: any = $("#dpGrid").ntsGrid("updatedCells");
            let dataChangeProcess: any = [];
            _.each(dataChange, (data: any) => {
                if (data.columnKey != "sign"  && data.value != "") {
                    let dataTemp = _.find(self.dpData, (item: any) => {
                        return item.id == data.rowId.substring(1, data.rowId.length);
                    });
                    if (data.columnKey.indexOf("Code") == -1 && data.columnKey.indexOf("NO") == -1) {
                        if (data.columnKey.indexOf("Name") != -1) {
                            // todo
                        } else {
                            //get layout , and type
                            let layoutAndType: any = _.find(self.itemValueAll(), (item: any) => {
                                return item.itemId == data.columnKey.substring(1, data.columnKey.length);
                            });
                            let value: any;
                            value = self.getPrimitiveValue(data.value);
                            let dataMap = new InfoCellEdit(data.rowId, data.columnKey.substring(1, data.columnKey.length), value, layoutAndType == undefined ? "" : layoutAndType.valueType, layoutAndType == undefined ? "" : layoutAndType.layoutCode, dataTemp.employeeId, moment(dataTemp.date).utc().toISOString(), 0);
                            dataChangeProcess.push(dataMap);
                        }
                    } else {
                        let columnKey: any;
                        let item: any;
                        if (data.columnKey.indexOf("Code") != -1) {
                            columnKey = data.columnKey.substring(4, data.columnKey.length);
                        } else {
                            columnKey = data.columnKey.substring(2, data.columnKey.length);
                        }
                        //TO Thanh: move find logic out if condition
                        item = _.find(self.lstAttendanceItem(), (data) => {
                            return String(data.id) === columnKey;
                        })

                        let layoutAndType: any = _.find(self.itemValueAll(), (item: any) => {
                            return item.itemId == columnKey;
                        });
                        let dataMap = new InfoCellEdit(data.rowId, columnKey, String(data.value), layoutAndType.valueType, layoutAndType.layoutCode, dataTemp.employeeId, moment(dataTemp.date).utc().toISOString(), item.typeGroup);
                        dataChangeProcess.push(dataMap);
                    }
                }
            });
            let param = { itemValues: dataChangeProcess }
            if (dataChangeProcess.length > 0) {
                let dfd = $.Deferred();
                service.addAndUpdate(dataChangeProcess).done((data) => {
                    // alert("done");
                    dataChange = {};
                    self.btnExtraction_Click();
                    dfd.resolve();
                }).fail((data) => {
                    alert("fail");
                    nts.uk.ui.block.clear();
                    dfd.resolve();
                });
                dfd.promise();
            }else{
                  nts.uk.ui.block.clear();
            }
            debugger;
        }
        checkIsColumn(dataCell: any, key: any): boolean {
            let check = false;
          _.each(dataCell, (item: any) =>{
              if (item.columnKey.indexOf("NO" + key) != -1) {
                  check = true;
                  return;
              }
              });
            return check;
        }
        
        getPrimitiveValue(value : any): string{
            var self = this;
            let valueResult : string = "";
            if(String(value).indexOf(":") != -1){
                // Time
                valueResult = String(self.getHoursAll(value));
            }else{
                valueResult = value;
            }
            return valueResult;
        }
        
        getHours(value: any) : number{
            return Number(value.split(':')[0]) * 60 + Number(value.split(':')[1]);
        }
        
        getHoursAll(value: any) : number{
            var self = this;
            if (value.indexOf(":") != -1) {
                if (value.indexOf("-") != -1) {
                    let valueTemp = value.split('-')[1];
                    return Number("-"+self.getHours(valueTemp));
                } else {
                    return self.getHours(value);
                }
            }else{
               return value;
            }
        }
        hideComponent() {
            var self = this;
            if (self.displayFormat() == 0) {
                $("#emp-component").css("display", "block");
                $("#cbListDate").css("display", "none");
                $('#numberHoliday').show();
                $('#fixed-table').show();
               //  $("#content-grid").attr('style', 'top: 244px !IMPORTANT');
            } else if (self.displayFormat() == 1) {
                $("#cbListDate").css("display", "block");
                $("#emp-component").css("display", "none");
                $('#numberHoliday').hide();
                $('#fixed-table').hide();
               // $("#content-grid").attr('style', 'top: 225px !IMPORTANT');
            } else {
                $("#cbListDate").css("display", "none");
                $("#emp-component").css("display", "none");
                $('#numberHoliday').hide();
                $('#fixed-table').hide();
               // $("#content-grid").attr('style', 'top: 180px !IMPORTANT');
            }
        }
        btnExtraction_Click() {
            var self = this;
            console.log(self.dailyPerfomanceData());
            if (!nts.uk.ui.errors.hasError()) {
                self.hideComponent();
                let lstEmployee = [];
                if (self.displayFormat() === 0) {
                    lstEmployee.push(_.find(self.lstEmployee(), (employee) => {
                        return employee.id === self.selectedEmployee();
                    }));
                } else {
                    lstEmployee = self.lstEmployee();
                }
                let param = {
                    dateRange: {
                        startDate:  self.displayFormat() === 1 ?  moment(self.selectedDate()) : moment(self.dateRanger().startDate).utc().toISOString(),
                        endDate:  self.displayFormat() === 1 ?  moment(self.selectedDate()) : moment(self.dateRanger().endDate).utc().toISOString()
                    },
                    displayFormat : self.displayFormat(),
                    initScreen: 1,
                    lstEmployee: lstEmployee,
                    formatCodes: self.formatCodes()
                };
                nts.uk.ui.block.invisible();
                nts.uk.ui.block.grayout();
                service.startScreen(param).done((data) => {
                    self.formatCodes(data.lstControlDisplayItem.formatCode);
                    let idC = self.createKeyLoad();
                    //TO Thanh: set data for list attendance item after load by extract click
                    self.lstAttendanceItem(data.lstControlDisplayItem.lstAttendanceItem);
                    self.itemValueAll(data.itemValues);
//                    _.each(data.lstControlDisplayItem.lstSheet, function(item) {
//                        item.columns.unshift("sign");
//                    });
                    self.createSumColumn(data);
                    self.columnSettings(data.lstControlDisplayItem.columnSettings);
                    self.showPrincipal(data.showPrincipal);
                     self.showPrincipal(false);
                    if (data.lstControlDisplayItem.lstHeader.length == 0) self.hasLstHeader = false;
                    if (data.showPrincipal || data.lstControlDisplayItem.lstHeader.length == 0) {
                        self.employeeModeHeader = [self.fixHeaders()[0], self.fixHeaders()[1], self.fixHeaders()[2], self.fixHeaders()[3], self.fixHeaders()[4]];
                        self.dateModeHeader = [self.fixHeaders()[0], self.fixHeaders()[1], self.fixHeaders()[2], self.fixHeaders()[5], self.fixHeaders()[6], self.fixHeaders()[4]];
                        self.errorModeHeader = [self.fixHeaders()[0], self.fixHeaders()[1], self.fixHeaders()[2], self.fixHeaders()[5], self.fixHeaders()[6], self.fixHeaders()[3], self.fixHeaders()[4]];
                    } else {
                        self.employeeModeHeader = [self.fixHeaders()[0], self.fixHeaders()[1], self.fixHeaders()[2], self.fixHeaders()[3]];
                        self.dateModeHeader = [self.fixHeaders()[0], self.fixHeaders()[1], self.fixHeaders()[2], self.fixHeaders()[5], self.fixHeaders()[6]];
                        self.errorModeHeader = [self.fixHeaders()[0], self.fixHeaders()[1], self.fixHeaders()[2], self.fixHeaders()[5], self.fixHeaders()[6], self.fixHeaders()[3]];
                    }
                    self.receiveData(data);
                    self.extraction();
                    nts.uk.ui.block.clear();
                }).fail(function(error) {
                    nts.uk.ui.dialog.alert(error.message);
                    nts.uk.ui.block.clear();
                });
            }
        }

        showErrorDialog() {
            var self = this;
            let lstEmployee = [];
            let uiErrors = nts.uk.ui.errors.getErrorList();
            let errorValidateScreeen : any = [];
            if (self.displayFormat() === 0) {
                _.each(uiErrors, value => {
                    let dateCon = _.find(self.dpData, (item: any) => {
                        return item.id == value.rowId.substring(1, value.rowId.length);
                    });

                    let object = { date: dateCon.date , employeeCode: dateCon.employeeCode, employeeName: dateCon.employeeName, message: value.message, itemName: "", columnKey: value.columnKey };
                    let item = _.find(self.optionalHeader, (data) => {
                        return String(data.key) === value.columnKey;
                    })
                    object.itemName = (item == undefined) ? "" : item.headerText; 
                    errorValidateScreeen.push(object);
                });
            } else {
                _.each(uiErrors, value => {
                    let dateCon = _.find(self.dpData, (item: any) => {
                        return item.id == value.rowId.substring(1, value.rowId.length);
                    });

                    let object = { date: dateCon.date, employeeCode: dateCon.employeeCode, employeeName: dateCon.employeeName, message: value.message, itemName: "", columnKey: value.columnKey };
                    let item = _.find(self.optionalHeader, (data) => {
                        return String(data.key) === value.columnKey;
                    })
                    object.itemName = (item == undefined) ? "" : item.headerText;
                    errorValidateScreeen.push(object);
                });
            };
            if (self.displayFormat() === 0) {
                lstEmployee.push(_.find(self.lstEmployee(), (employee) => {
                    return employee.id === self.selectedEmployee();
                }));
            } else {
                lstEmployee = self.lstEmployee();
            }
            let param = {
                dateRange: {
                    startDate: moment(self.dateRanger().startDate).utc().toISOString(),
                    endDate: moment(self.dateRanger().endDate).utc().toISOString()
                },
                lstEmployee: lstEmployee
            };
            nts.uk.ui.windows.setShared("paramToGetError", param);
            nts.uk.ui.windows.setShared("errorValidate", errorValidateScreeen);
            nts.uk.ui.windows.sub.modal("/view/kdw/003/b/index.xhtml").onClosed(() => {
            });
        }
        changeExtractionCondition() {
            var self = this;
            if (!nts.uk.ui.errors.hasError()) {
                self.hideComponent();
                let lstEmployee = [];
                if (self.displayFormat() === 0) {
                    lstEmployee.push(_.find(self.lstEmployee(), (employee) => {
                        return employee.id === self.selectedEmployee();
                    }));
                } else {
                    lstEmployee = self.lstEmployee();
                }
                //  let errorCodes =["0001","0002","003"];      
                nts.uk.ui.windows.sub.modal("/view/kdw/003/d/index.xhtml").onClosed(() => {
                    nts.uk.ui.block.clear();
                    let errorCodes = nts.uk.ui.windows.getShared('errorAlarmList');
                    if (errorCodes != undefined) {
                        let param = {
                            dateRange: {
                                startDate: self.displayFormat() === 1 ? moment(self.selectedDate()) : moment(self.dateRanger().startDate).utc().toISOString(),
                                endDate: self.displayFormat() === 1 ? moment(self.selectedDate()) : moment(self.dateRanger().endDate).utc().toISOString()
                            },
                            lstEmployee: lstEmployee,
                            displayFormat : self.displayFormat(),
                            errorCodes: errorCodes,
                            formatCodes: self.formatCodes()
                        };
                        nts.uk.ui.block.invisible();
                        nts.uk.ui.block.grayout();
                        service.selectErrorCode(param).done((data) => {
//                            _.each(data.lstControlDisplayItem.lstSheet, function(item) {
//                                item.columns.unshift("sign");
//                            });
                            self.createSumColumn(data);
                            self.columnSettings(data.lstControlDisplayItem.columnSettings);
                            self.receiveData(data);
                            self.extraction();
                            nts.uk.ui.block.clear();
                        }).fail(function(error) {
                            nts.uk.ui.dialog.alert(error.message);
                            nts.uk.ui.block.clear();
                        });
                    }
                });
            }
        }

        selectDisplayItem() {
            var self = this;
            if (!nts.uk.ui.errors.hasError()) {
                nts.uk.ui.windows.setShared("selectedPerfFmtCodeList", self.formatCodes());
                nts.uk.ui.windows.sub.modal("/view/kdw/003/c/index.xhtml").onClosed(() => {
                    var dataTemp = nts.uk.ui.windows.getShared('dailyPerfFmtList');
                    if (dataTemp != undefined) {
                        let data = [dataTemp.dailyPerformanceFormatCode()];
                       self.hideComponent();
                        let lstEmployee = [];
                        if (self.displayFormat() === 0) {
                            lstEmployee.push(_.find(self.lstEmployee(), (employee) => {
                                return employee.id === self.selectedEmployee();
                            }));
                        } else {
                            lstEmployee = self.lstEmployee();
                        }
                        let param = {
                           dateRange: {
                                startDate: self.displayFormat() === 1 ? moment(self.selectedDate()) : moment(self.dateRanger().startDate).utc().toISOString(),
                                endDate: self.displayFormat() === 1 ? moment(self.selectedDate()) : moment(self.dateRanger().endDate).utc().toISOString()
                            },
                            lstEmployee: lstEmployee,
                            displayFormat : self.displayFormat(),
                            formatCodes: data
                        };
                        nts.uk.ui.block.invisible();
                        nts.uk.ui.block.grayout();
                        service.selectFormatCode(param).done((data) => {
//                            _.each(data.lstControlDisplayItem.lstSheet, function(item) {
//                                item.columns.unshift("sign");
//                            });
                            self.createSumColumn(data);
                            self.columnSettings(data.lstControlDisplayItem.columnSettings);
                            self.receiveData(data);
                            self.extraction();
                            nts.uk.ui.block.clear();
                        }).fail(function(error) {
                            nts.uk.ui.dialog.alert(error.message);
                            nts.uk.ui.block.clear();
                        });
                    }
                });
            }
        }

        referencesActualResult() {
            var self = this;
            if (!nts.uk.ui.errors.hasError()) {
                let lstEmployee = [];
                if (self.displayFormat() === 0) {
                    lstEmployee.push(_.find(self.lstEmployee(), (employee) => {
                        return employee.id === self.selectedEmployee();
                    }));
                    nts.uk.ui.windows.setShared("KDL014A_PARAM", {
                        startDate: moment(self.dateRanger().startDate).utc().toISOString(),
                        endDate: moment(self.dateRanger().startDate).utc().toISOString(),
                        employeeID: lstEmployee[0].code
                    });
                    nts.uk.ui.windows.sub.modal("/view/kdl/014/a/index.xhtml").onClosed(() => {
                    });

                } else if (self.displayFormat() === 1) {
                    lstEmployee = self.lstEmployee().map((data) => {
                        return data.code;
                    });
                     nts.uk.ui.windows.setShared("KDL014B_PARAM", {
                        startDate: moment(self.dateRanger().startDate).utc().toISOString(),
                        endDate: moment(self.dateRanger().startDate).utc().toISOString(),
                        lstEmployee: lstEmployee
                    });
                    nts.uk.ui.windows.sub.modal("/view/kdl/014/b/index.xhtml").onClosed(() => {
                    });
                }
            }
        }

        employmentOk() {
            let _self = this;
            if (!nts.uk.ui.errors.hasError()) {
                nts.uk.ui.block.grayout();
                nts.uk.ui.windows.sub.modal("/view/kdl/006/a/index.xhtml").onClosed(() => {
                    nts.uk.ui.block.clear();
                });
            }
        }
        btnSetting_Click() {
            var container = $("#setting-content");
            if (container.css("visibility") === 'hidden') {
                container.css("visibility", "visible");
                container.css("top", "0px");
                container.css("left", "0px");
            }
            $(document).mouseup(function(e) {
                // if the target of the click isn't the container nor a descendant of the container
                if (!container.is(e.target) && container.has(e.target).length === 0) {
                    container.css("visibility", "hidden");
                    container.css("top", "-9999px");
                    container.css("left", "-9999px");
                }
            });
        }

        btnVacationRemaining_Click() {
            var container = $("#vacationRemaining-content");
            if (container.css("visibility") === 'hidden') {
                container.css("visibility", "visible");
                container.css("top", "0px");
                container.css("left", "0px");
            }
            $(document).mouseup(function(e) {
                // if the target of the click isn't the container nor a descendant of the container
                if (!container.is(e.target) && container.has(e.target).length === 0) {
                    container.css("visibility", "hidden");
                    container.css("top", "-9999px");
                    container.css("left", "-99990px");
                }
            });
        }

        btnSaveColumnWidth_Click() {
            var self = this;
            let command = {
                lstHeader: {},
                formatCode: self.formatCodes()
            };
            let jsonColumnWith = localStorage.getItem(window.location.href + '/dpGrid');
            let valueTemp = 0;
            _.forEach($.parseJSON(jsonColumnWith), (value, key) => {
                if (key.indexOf('A') != -1) {
                    if (nts.uk.ntsNumber.isNumber(key.substring(1, key.length))) {
                        command.lstHeader[key.substring(1, key.length)] = value;
                    }
                }
                if (key.indexOf('Code') != -1 || key.indexOf('NO') != -1) {
                    valueTemp = value;
                } else if (key.indexOf('Name') != -1) {
                    command.lstHeader[key.substring(4, key.length)] = value + valueTemp;
                    valueTemp = 0;
                }
            });
            service.saveColumnWidth(command);
        }

        extractionData() {
            var self = this;
            self.headersGrid([]);
            self.fixColGrid([]);
            if (self.displayFormat() == 0) {
                self.fixColGrid(self.employeeModeFixCol);
            } else if (self.displayFormat() == 1) {
                self.fixColGrid(self.dateModeFixCol);
            } else if (self.displayFormat() == 2) {
                self.fixColGrid(self.errorModeFixCol);
            }
            if(self.showPrincipal() && self.hasLstHeader){
                let sign = _.find( self.fixColGrid(), (data: any) =>{
                    return data.columnKey === 'sign';
                }
                )
                if(sign == undefined) self.fixColGrid.push({ columnKey: 'sign', isFixed: true });
            }
            self.loadHeader(self.displayFormat());
            self.dailyPerfomanceData(self.filterData(self.displayFormat()));
        }
        
        extraction() {
            var self = this;
            self.destroyGrid();
            let start = performance.now();
            self.extractionData();
             console.log("calc load extractionData :" + (start- performance.now()));
            self.loadGrid();
        }

        isDisableRow(id) {
            var self = this;
            for (let i = 0; i < self.rowStates().length; i++) {
                return self.rowStates()[i].rowId == id;
            }
        }

        isDisableSign(id) {
            var self = this;
            for (let i = 0; i < self.cellStates().length; i++) {
                return self.cellStates()[i].rowId == id && self.cellStates()[i].columnKey == 'sign';
            }
        }

        signAll() {
            var self = this;
            _.forEach(self.dailyPerfomanceData(), (data) => {
                if (!self.isDisableRow(data.id)) {
                    data.sign = !data.sign;
                    $("#dpGrid").ntsGrid("updateRow", "_" + data.id, { sign: data.sign });
                }
            });
            self.dailyPerfomanceData.valueHasMutated();
            //            self.destroyGrid();
            //            self.loadGrid();
        }

        destroyGrid() {
            $("#dpGrid").ntsGrid("destroy");
            $("#dpGrid").remove();
            $(".nts-grid-sheet-buttons").remove();
            $('<table id="dpGrid"></table>').appendTo('#gid');
        }
        setColorWeekend() {
            var self = this;
            self.textColors([]);
            _.forEach(self.dailyPerfomanceData(), (data) => {
                if (moment(data.date, "YYYY/MM/DD").day() == 6) {
                    self.textColors.push({
                        rowId: "_" + data.id,
                        columnKey: 'date',
                        color: '#4F81BD'
                    });
                } else if (moment(data.date, "YYYY/MM/DD").day() == 0) {
                    self.textColors.push({
                        rowId: "_" + data.id,
                        columnKey: 'date',
                        color: '#e51010'
                    });
                }
            });
        }

        setHeaderColor() {
            var self = this;
            self.headerColors([]);
            _.forEach(self.headersGrid(), (header) => {
                if (header.color) {
                    self.headerColors.push({
                        key: header.key,
                        color: header.color
                    });
                }
                if (header.group !=  null && header.group != undefined && header.group.length > 0) {
                    self.headerColors.push({
                        key: header.group[0].key,
                        color: header.group[0].color
                    });
                    self.headerColors.push({
                        key: header.group[1].key,
                        color: header.group[1].color
                    });
                }
            });
        }

        formatDate(lstData) {
            var self = this;
            let start = performance.now();
            let data =  lstData.map((data) => {
                var object = {
                    id: "_" + data.id,
                    state: data.state,
                    error: data.error,
                    date: moment(data.date, "YYYY/MM/DD").format("MM/DD(dd)"),
                    sign: data.sign,
                    employeeId: data.employeeId,
                    employeeCode: data.employeeCode,
                    employeeName: data.employeeName
                }
                _.each(data.cellDatas, function(item) { object[item.columnKey] = item.value });
                return object;
            });
             console.log("calc load source :" + (start- performance.now()));
            return data;
        }

        filterData(mode: number) {
            var self = this;
            if (mode == 0) {
                return _.filter(self.dpData, (data) => { return data.employeeId == self.selectedEmployee() });
            } else if (mode == 1) {
                return _.filter(self.dpData, (data) => { return data.date === moment(self.selectedDate()).format('YYYY/MM/DD') });
            } else if (mode == 2) {
                return _.filter(self.dpData, (data) => { return data.error !== '' });
            }
        }
        
        totalNumber(data) {
            let total = 0;
            let currentPageIndex = $("#dpGrid").igGridPaging("option", "currentPageIndex");
            let pageSize = $("#dpGrid").igGridPaging("option", "pageSize");
            let startIndex: any = currentPageIndex * pageSize;
            let endIndex: any = startIndex + pageSize;
            _.forEach(data, function(d, i) {
                if (i < startIndex || i >= endIndex) return;
                let n = parseInt(d);
                if (!isNaN(n)) total += n;
            });
            return total;
        }
        totalTime(data) {
            let currentPageIndex = $("#dpGrid").igGridPaging("option", "currentPageIndex");
            let pageSize = $("#dpGrid").igGridPaging("option", "pageSize");
            let startIndex: any = currentPageIndex * pageSize;
            let endIndex: any = startIndex + pageSize;
            let total = moment.duration("0");
            _.forEach(data, function(d, i) {
                if (i < startIndex || i >= endIndex) return;
                total.add(moment.duration(d));
            });
            let time = total.asHours();
            let hour = Math.floor(time);
            let minute = (time - hour) * 60;
            let roundMin = Math.round(minute);
            let minuteStr = roundMin < 10 ? ("0" + roundMin) : String(roundMin);
            return hour + ":" + minuteStr;
        }
        
         totalMoney(data) {
            let total = 0;
            let currentPageIndex = $("#dpGrid").igGridPaging("option", "currentPageIndex");
            let pageSize = $("#dpGrid").igGridPaging("option", "pageSize");
            let startIndex: any = currentPageIndex * pageSize;
            let endIndex: any = startIndex + pageSize;
            _.forEach(data, function(d, i) {
                 let  valueResult = "";
                if (i < startIndex || i >= endIndex) return;
                if (String(d).indexOf(",") != -1) {
                    for (let i = 0; i < String(d).split(',').length; i++) {
                        valueResult += String(d).split(',')[i];
                    }
                    let n = parseFloat(valueResult);
                    if (!isNaN(n)) total += n;
                }else{
                    let n = parseFloat(d);
                    if (!isNaN(n)) total += n;
                }
            });
            return total.toLocaleString('en');
        }
        //load kcp009 component: employee picker
        loadKcp009() {
            let self = this;
            var kcp009Options = {
                systemReference: 1,
                isDisplayOrganizationName: true,
                employeeInputList: self.lstEmployee,
                targetBtnText: nts.uk.resource.getText("KCP009_3"),
                selectedItem: self.selectedEmployee,
                tabIndex: 1
            };
            // Load listComponent
            $('#emp-component').ntsLoadListComponent(kcp009Options);
        }

        //init ccg001
        initCcg001() {
            let self = this;
            self.ccg001 = {
                baseDate: self.baseDate,
                //Show/hide options
                isQuickSearchTab: true,
                isAdvancedSearchTab: true,
                isAllReferableEmployee: true,
                isOnlyMe: true,
                isEmployeeOfWorkplace: true,
                isEmployeeWorkplaceFollow: true,
                isMutipleCheck: true,
                isSelectAllEmployee: false,
                /**
                * @param dataList: list employee returned from component.
                * Define how to use this list employee by yourself in the function's body.
                */
                onSearchAllClicked: function(dataList: EmployeeSearchDto[]) {
                    self.lstEmployee(dataList.map((data: EmployeeSearchDto) => {
                        return {
                            id: data.employeeId,
                            code: data.employeeCode,
                            businessName: data.employeeName,
                            workplaceName: data.workplaceName,
                            workplaceId: data.workplaceId,
                            depName: '',
                            isLoginUser: false
                        };
                    }));
                    self.lstEmployee(_.orderBy(self.lstEmployee(), ['code'], ['asc']));
                    self.selectedEmployee(self.lstEmployee()[0].id);
                    self.loadKcp009();
                    self.btnExtraction_Click();
                },
                onSearchOnlyClicked: function(data: EmployeeSearchDto) {
                    self.lstEmployee([]);
                    self.lstEmployee.push({
                        id: data.employeeId,
                        code: data.employeeCode,
                        businessName: data.employeeName,
                        workplaceName: data.workplaceName,
                        workplaceId: data.workplaceId,
                        depName: '',
                        isLoginUser: false
                    });
                    self.lstEmployee(_.orderBy(self.lstEmployee(), ['code'], ['asc']));
                    self.selectedEmployee(self.lstEmployee()[0].id);
                    self.loadKcp009();
                    self.btnExtraction_Click();
                },
                onSearchOfWorkplaceClicked: function(dataList: EmployeeSearchDto[]) {
                    self.lstEmployee(dataList.map((data: EmployeeSearchDto) => {
                        return {
                            id: data.employeeId,
                            code: data.employeeCode,
                            businessName: data.employeeName,
                            workplaceName: data.workplaceName,
                            workplaceId: data.workplaceId,
                            depName: '',
                            isLoginUser: false
                        };
                    }));
                    self.lstEmployee(_.orderBy(self.lstEmployee(), ['code'], ['asc']));
                    self.selectedEmployee(self.lstEmployee()[0].id);
                    self.loadKcp009();
                    self.btnExtraction_Click();
                },
                onSearchWorkplaceChildClicked: function(dataList: EmployeeSearchDto[]) {
                    self.lstEmployee(dataList.map((data: EmployeeSearchDto) => {
                        return {
                            id: data.employeeId,
                            code: data.employeeCode,
                            businessName: data.employeeName,
                            workplaceName: data.workplaceName,
                            workplaceId: data.workplaceId,
                            depName: '',
                            isLoginUser: false
                        };
                    }));
                    self.lstEmployee(_.orderBy(self.lstEmployee(), ['code'], ['asc']));
                    self.selectedEmployee(self.lstEmployee()[0].id);
                    self.loadKcp009();
                    self.btnExtraction_Click();
                },
                onApplyEmployee: function(dataList: EmployeeSearchDto[]) {
                    self.lstEmployee(dataList.map((data: EmployeeSearchDto) => {
                        return {
                            id: data.employeeId,
                            code: data.employeeCode,
                            businessName: data.employeeName,
                            workplaceName: data.workplaceName,
                            depName: '',
                            isLoginUser: false
                        };
                    }));
                    self.selectedEmployee(self.lstEmployee()[self.lstEmployee().length - 1].id);
                    self.loadKcp009();
                    self.btnExtraction_Click();
                }
            }
        }
        
        createKeyLoad() : any {
            var self = this;
            let lstEmployee = [];
            let data = [];
            if (self.displayFormat() === 0) {
                lstEmployee.push(_.find(self.lstEmployee(), (employee) => {
                    return employee.id === self.selectedEmployee();
                }));
            } else {
                lstEmployee = self.lstEmployee();
            }
            let format = "YYYY-MM-DD";
             moment(self.dateRanger().startDate).format(format)
            let startDate = self.displayFormat() === 1 ? moment(self.selectedDate()).format(format) : moment(self.dateRanger().startDate).format(format);
            let endDate = self.displayFormat() === 1 ? moment(self.selectedDate()).format(format) : moment(self.dateRanger().endDate).format(format);
            let i = 0;
            if (self.displayFormat() === 0) {
                _.each(self.createDateList(self.dateRanger().startDate, (self.dateRanger().endDate)), value => {
                    data.push(self.displayFormat() + "_" + self.lstEmployee()[0].id + "_" + self.convertDateToString(value) + "_" + endDate + "_" + i)
                    i++;
                });
            } else if (self.displayFormat() === 1) {
                _.each(lstEmployee, value => {
                    data.push(self.displayFormat() + "_" + value.id + "_" + startDate + "_" + endDate)
                    i++;
                });
            } else {
                _.each(lstEmployee, value => {
                    _.each(self.createDateList(self.dateRanger().startDate, (self.dateRanger().endDate)), value2 => {
                        data.push(self.displayFormat() + "_" + value.id + "_" + self.convertDateToString(value2) + "_" + endDate + "_" + i)
                        i++;
                    });
                });
            }
          return data;
        }
        
        createDateList(startDate: any, endDate: any) :any{
             var dateArray = [];
            var currentDate : any  = moment(startDate);
            var stopDate : any= moment(endDate);
            while (currentDate <= stopDate) {
                dateArray.push(moment(currentDate).format('YYYY-MM-DD'))
                currentDate = moment(currentDate).add(1, 'days');
            }
            return dateArray;
        }
        convertDateToString(date :any) : any{
          return moment(date).format('YYYY-MM-DD');
        }
        //load ccg001 component: search employee
        loadCcg001() {
            var self = this;
            $('#ccg001').ntsGroupComponent(self.ccg001);
        }

        loadGrid() {
            var self = this;
            var summary: ISummaryColumn = {
                columnKey: 'salary',
                allowSummaries: true
            }
            var summaries = [];
            var rowState = {
                rowId: 0,
                disable: true
            }
            self.setHeaderColor();
            self.setColorWeekend();
            console.log(self.formatDate(self.dailyPerfomanceData()));
            let start = performance.now();
            $("#dpGrid").ntsGrid({
                width: (window.screen.availWidth - 200) + "px",
                height: '650px',
                dataSource: self.formatDate(self.dailyPerfomanceData()),
                dataSourceAdapter: function(ds) {
                    let start = performance.now();
                    let data = ds.map((data) => {
                        var object = {
                            id: data.id,
                            state: data.state,
                            error: data.error,
                            date: moment(data.date, "YYYY/MM/DD").format("MM/DD(dd)"),
                            sign: data.sign,
                            employeeId: data.employeeId,
                            employeeCode: data.employeeCode,
                            employeeName: data.employeeName
                        }
                        _.each(data.cellDatas, function(item) { object[item.columnKey] = item.value });
                        return object;
                    });
                    console.log("calc load source :" + (start - performance.now()));
                    return data;
                },
                primaryKey: 'id',
                rowVirtualization: true,
                virtualization: true,
                virtualizationMode: 'continuous',
                enter: self.selectedDirection() == 0 ? 'below' : 'right',
                autoFitWindow: false,
                preventEditInError: false,
                columns: self.headersGrid(),
                hidePrimaryKey: true,
               // recordKeys: self.createKeyLoad(),
                features: [
                    { name: 'Paging', pageSize: 31, currentPageIndex: 0 },
                    { name: 'ColumnFixing', fixingDirection: 'left', showFixButtons: false, columnSettings: self.fixColGrid() },
                    { name: 'Resizing', columnSettings: [{ columnKey: 'id', allowResizing: false, minimumWidth: 0 }] },
                    { name: 'MultiColumnHeaders' },
                    {
                        name: 'Summaries',
                        showSummariesButton: false,
                        showDropDownButton: false,
                        columnSettings: self.columnSettings(),
                        resultTemplate: '{1}'
                    }
                ],
                ntsFeatures: self.createNtsFeatures(),
                ntsControls: [
                    { name: 'Checkbox', options: { value: 1, text: '' }, optionsValue: 'value', optionsText: 'text', controlType: 'CheckBox', enable: true },
                    {
                        name: 'Link2',
                        click: function(rowId, key, event) {
                           // self.helps(event);
                            //let cell : any = $("#dpGrid").igGrid("activeCell");
                           // if (cell) {
                                let value = $("#dpGrid").igGrid("getCellValue", rowId, "Code" + key.substring(4, key.length));
                                let dialog: TypeDialog = new TypeDialog(key.substring(4, key.length), self.lstAttendanceItem(), value, rowId);
                                dialog.showDialog(self);
                                nts.uk.ui.block.clear();
                          //  }
                        },
                        controlType: 'LinkLabel'
                    },
                    { name: 'TextEditorNumberSeparated', controlType: 'TextEditor', constraint: { valueType: 'Integer', required: true, format: "Number_Separated" } },
                    { name: 'TextEditorTimeShortHM', controlType: 'TextEditor', constraint: { valueType: 'Time', required: true, format: "Time_Short_HM" } },
                    { name: 'ComboboxCalc',  width: '250px', options: self.comboItemsCalc(), optionsValue: 'code', optionsText: 'name', columns: self.comboColumnsCalc(), editable: false, displayMode: 'codeName', controlType: 'ComboBox', enable: true, spaceSize: 'small' },
                    { name: 'ComboboxReason',  width: '120px',  options: self.comboItemsReason(), optionsValue: 'code', optionsText: 'name', columns: self.comboColumns(), editable: false, displayMode: 'codeName', controlType: 'ComboBox', enable: true, spaceSize: 'small' },
                    { name: 'ComboboxDoWork',  width: '120px', options: self.comboItemsDoWork(), optionsValue: 'code', optionsText: 'name', columns: self.comboColumns(), editable: false, displayMode: 'codeName', controlType: 'ComboBox', enable: true, spaceSize: 'small' },
                    {
                        name: 'FlexImage', source: 'ui-icon ui-icon-locked', click: function(key, rowId, evt) {
                            let data = $("#dpGrid").igGrid("getCellValue", rowId, key);
                            if(data!= ""){
                                 let lock = data.split("|");
                                 let tempD = "<span>";
                                 for (let i =1 ; i< lock.length ; i++){
                                     if(lock[i]=="D") tempD +=  nts.uk.resource.getText("KDW003_69")+'<br/>'; 
                                     if(lock[i]=="M") tempD += nts.uk.resource.getText("KDW003_68")+'<br/>'; 
                                     if(lock[i]=="C") tempD += nts.uk.resource.getText("KDW003_67")+'</span>'; 
                                     $('#textLock').html(tempD);
                                }
                                }
                            self.helps(evt, "");
                        },
                        controlType: 'FlexImage'
                    },
                ]
            });
            console.log("load grid ALL" + (start- performance.now()));
            $(document).delegate("#dpGrid", 'iggridupdatingeditcellending', function(evt, ui) {
                //information data edit 
                let data: InfoCellEdit = self.pushDataEdit(evt, ui);
                let dfd = $.Deferred();
                //                nts.uk.ui.block.invisible();
                //                nts.uk.ui.block.grayout();
                if (ui.columnKey.indexOf("Code") != -1) {
                    let item = _.find(self.lstAttendanceItem(), function(data) {
                        return data.id == ui.columnKey.substring(4, ui.columnKey.length);
                    })
                    if (item.typeGroup) {
                        let param = {
                            typeDialog: item.typeGroup,
                            param: {
                                workTypeCode: ui.value,
                                employmentCode: "",
                                workplaceId: "",
                                date: moment(),
                                selectCode: ui.value
                            }
                        }
                        switch (item.typeGroup) {
                            case 5:
                            case 7:
                                let dateCon = _.find(self.dpData, (item: any) => {
                                    return item.id == ui.rowID.substring(1, ui.rowID.length);
                                });
                                param.param.date = moment(dateCon.date);
                                break;
                            case 1:
                                param.param.workTypeCode = ui.value;
                                param.param.selectCode = ui.value;
                                param.param.employmentCode = self.employmentCode();
                                break
                            case 2:
                                let employeeIdSelect: any = _.find(self.dailyPerfomanceData(), function(item: any) {
                                    return item.id == ui.rowID.substring(1, ui.rowID.length);
                                });
                                let employee: any = _.find(self.lstEmployee(), function(item: any) {
                                    return item.id == employeeIdSelect.employeeId;
                                });
                                param.param.workplaceId = employee.workplaceId;
                                break
                        }
                        $.when(service.findCodeName(param)).done((data) => {
                           // $("#dpGrid").igGridUpdating("setCellValue", ui.rowID, "Name" + ui.columnKey.substring(4, ui.columnKey.length), (data == undefined ? "Not found" : data.name));
                           // nts.uk.ui.block.clear();
                            var object = {};
                            object["Name" + ui.columnKey.substring(4, ui.columnKey.length)] = (data == undefined ? nts.uk.resource.getText("KDW003_81") : data.name);
                            $("#dpGrid").ntsGrid("updateRow", ui.rowID, object);
                            dfd.resolve();
                        });
                        dfd.promise();
                    }
                } 
            });
        }
        
        reloadGrid() {
            var self = this;
            nts.uk.ui.block.invisible();
             nts.uk.ui.block.grayout();
            setTimeout(function() {
                 self.createSumColumn(self.dataAll());
                    self.columnSettings(self.dataAll().lstControlDisplayItem.columnSettings);
                    self.receiveData(self.dataAll());
                 self.loadGrid();
                    self.extractionData();
                    self.loadGrid();
                nts.uk.ui.block.clear();
            }, 500);
        }

        createNtsFeatures() {
            var self = this;
            let lstNtsFeature = [
                { name: 'CopyPaste' },
                { name: 'CellEdit' },
                { name: 'CellState', rowId: 'rowId', columnKey: 'columnKey', state: 'state', states: self.cellStates() },
                { name: 'RowState', rows: self.rowStates() },
                { name: 'TextColor', rowId: 'rowId', columnKey: 'columnKey', color: 'color', colorsTable: self.textColors() },
                { name: 'HeaderStyles', columns: self.headerColors() }
            ];
//            let lzyLoad = {
//                name: "LoadOnDemand",
//                allKeysPath: "/kdw003/lazyload/keys",
//                pageRecordsPath: "/kdw003/lazyload/data",
//            }
//            if(self.displayFormat() == 1){
//               lstNtsFeature.push(lzyLoad); 
//            }
            if (self.sheetsGrid().length > 0) {
                lstNtsFeature.push({
                    name: "Sheet",
                    initialDisplay: self.sheetsGrid()[0].name,
                    sheets: self.sheetsGrid()
                });
            }
            return lstNtsFeature;
        }

        loadHeader(mode) {
            var self = this;
            let tempList = [];
            if (mode == 0) {
                _.forEach(self.employeeModeHeader, (header) => {
                    delete header.group;
                     if (header.constraint == null) {
                        delete header.constraint;
                    }
                    tempList.push(header);
                });
            } else if (mode == 1) {
                self.displayProfileIcon();
                _.forEach(self.dateModeHeader, (header) => {
                    if (header.constraint == null) {
                        delete header.constraint;
                    }
                    delete header.group;
                    tempList.push(header);
                });
            } else if (mode == 2) {
                _.forEach(self.errorModeHeader, (header) => {
                    if (header.constraint == null) {
                        delete header.constraint;
                    }
                    delete header.group;
                    tempList.push(header);
                });
            }
            self.dislayNumberHeaderText();
            _.forEach(self.optionalHeader, (header) => {
                if (header.constraint == null || header.constraint == undefined) {
                    delete header.constraint;
                }else{
                    header.constraint["cDisplayType"] = header.constraint.cdisplayType;
                    if(header.constraint.cDisplayType != null && header.constraint.cDisplayType != undefined && header.constraint.cDisplayType.indexOf("Currency") != -1){
                       header["columnCssClass"] =  "currency-symbol";
                    }
                    delete header.constraint.cdisplayType;
                }
                if (header.group != null && header.group != undefined) {
                    if (header.group.length > 0) {
                       delete header.group[0].constraint;
                       delete header.group[1].constraint;
                        delete header.group[0].group;
                        //delete header.key;
                        delete header.dataType;
                       // delete header.width;
                        delete header.ntsControl;
                        delete header.changedByOther;
                        delete header.changedByYou;
                       // delete header.color;
                        delete header.hidden;
                        delete header.ntsType;
                        delete header.onChange;
                        if (header.group[0].dataType == "String") {
                            // header.group[0].onChange = self.search;
                            delete header.group[0].onChange;
                            delete header.group[0].ntsControl;
                        } else {
                            delete header.group[0].onChange;
                            delete header.group[0].ntsControl;
                        }
                        delete header.group[1].group;
                    } else {
                        delete header.group;
                    }
                }
                tempList.push(header);
            });
            self.headersGrid(tempList);
        }
        
          pushDataEdit(evt, ui) : InfoCellEdit {
            var self = this;
            var dataEdit: InfoCellEdit
            let edit: InfoCellEdit = _.find(self.editValue, function(item: any) {
                return item.rowID == ui.rowID && item.columnKey == ui.columnKey;
            });

            if (edit) {
                edit.value = ui.value;
                return edit;
            }
            else {
                let dateCon = _.find(self.dpData, (item: any) => {
                    return item.id == ui.rowID.substring(1, ui.rowID.length);
                });

                let employeeIdSelect: any = _.find(self.dailyPerfomanceData(), function(item: any) {
                    return item.id == ui.rowID.substring(1, ui.rowID.length);
                });
                dataEdit = new InfoCellEdit(ui.rowID, ui.columnKey, ui.value, 1, "", employeeIdSelect.employeeId,  moment(dateCon.date).utc().toISOString(), 0);
                self.editValue.push(dataEdit);
                return dataEdit;
            }
        }
        
        displayProfileIcon() {
            var self = this;
            if (self.showProfileIcon()) {
                _.remove(self.dateModeHeader, function(header) {
                    return header.key === "picture-person";
                });
                _.remove(self.dateModeFixCol, function(header) {
                    return header.columnKey === "picture-person";
                });
                self.dateModeHeader.splice(5, 0, LIST_FIX_HEADER[7]);
                self.dateModeFixCol.push({ columnKey: 'picture-person', isFixed: true });
            } else {
                _.remove(self.dateModeHeader, function(header) {
                    return header.key === "picture-person";
                });
                _.remove(self.dateModeFixCol, function(header) {
                    return header.columnKey === "picture-person";
                });
            }
        }

        dislayNumberHeaderText() {
            var self = this;
            if (self.showHeaderNumber()) {
                self.optionalHeader.map((header) => {
                    if (header.headerText) {
                        header.headerText = header.headerText + " " + header.key.substring(1, header.key.length);
                    }
                    return header;
                });
            } else {
                self.optionalHeader.map((header) => {
                    if (header.headerText) {
                        header.headerText = header.headerText.split(" ")[0];
                    }
                    return header;
                });
            }
        }

        search(val) {
            var self = this;
            let dfd = $.Deferred();
            let i = 0;
            let result = "Not found";
            let cell = $("#dpGrid").igGridUpdating("endEdit");
            let param = {
                typeDialog: 1,
                param: {
                    workTypeCode: val,
                    employmentCode: self.employmentCode,
                    workplaceId: "",
                    date: new Date(),
                    selectCode: val
                }
            }
            $.when(service.findCodeName(param)).done((data) => {
                result = (data == undefined ? "Not found" : data.name);
                dfd.resolve(result);
            });
            return dfd.promise();
        }
    }
    export class AuthorityDetailModel {
        available1: KnockoutObservable<boolean> = ko.observable(true);
        available4: KnockoutObservable<boolean> = ko.observable(true);
        available2: KnockoutObservable<boolean> = ko.observable(true);
        available3: KnockoutObservable<boolean> = ko.observable(true);
        available5: KnockoutObservable<boolean> = ko.observable(true);
        available8: KnockoutObservable<boolean> = ko.observable(true);
        available8Authority: KnockoutObservable<boolean> = ko.observable(true);
        available22: KnockoutObservable<boolean> = ko.observable(true);
        available24: KnockoutObservable<boolean> = ko.observable(true);
        available7: KnockoutObservable<boolean> = ko.observable(true);
        available23: KnockoutObservable<boolean> = ko.observable(true);
        available25: KnockoutObservable<boolean> = ko.observable(true);
        available17: KnockoutObservable<boolean> = ko.observable(true);
        available18: KnockoutObservable<boolean> = ko.observable(true);
        available19: KnockoutObservable<boolean> = ko.observable(true);
        available20: KnockoutObservable<boolean> = ko.observable(true);
        available21: KnockoutObservable<boolean> = ko.observable(true);
        constructor(data: Array<DailyPerformanceAuthorityDto>, authority : any) {
            var self = this;
            if (!data) return;
            this.available1(self.checkAvailable(data, 1));
            this.available4(self.checkAvailable(data, 4));
            this.available2(self.checkAvailable(data, 2));
            this.available3(self.checkAvailable(data, 3));
            this.available5(self.checkAvailable(data, 5));
            this.available8(self.checkAvailable(data, 8));
            this.available22(self.checkAvailable(data, 22));
            this.available24(self.checkAvailable(data, 24));
            this.available7(self.checkAvailable(data, 7));
            this.available23(self.checkAvailable(data, 23));
            this.available25(self.checkAvailable(data, 25));
            if(self.checkAvailable(data, 25)){
                $("#btn-signAll").css("visibility", "visible"); 
            }else{
                $("#btn-signAll").css("visibility", "hidden");
            }
            $("#btn-signAll").css("visibility", "hidden");
            this.available17(self.checkAvailable(data, 17));
            this.available18(self.checkAvailable(data, 18));
            this.available19(self.checkAvailable(data, 19));
            this.available20(self.checkAvailable(data, 20));
            this.available21(self.checkAvailable(data, 21));
            this.available8Authority(this.available8() && authority)

        }
        checkAvailable(data: Array<DailyPerformanceAuthorityDto>, value: number): boolean {
            let self = this;
            var check = _.find(data, function(o) {
                return o.functionNo === value;
            })
            if (check == null) return false;
            else return check.availability;
        };
    }
    class ItemModel {
        code: string;
        name: string;

        constructor(code: string, name: string) {
            this.code = code;
            this.name = name;
        }
    }
    export interface DailyPerformanceAuthorityDto {
        isDefaultInitial: number;
        roleID: string;
        functionNo: number;
        availability: boolean;
    }

    export class ReferenceVacation {
        yearHoliday20: KnockoutObservable<boolean> = ko.observable(true);
        yearHoliday19: KnockoutObservable<boolean> = ko.observable(true);
        substVacation: KnockoutObservable<boolean> = ko.observable(true);
        compensLeave: KnockoutObservable<boolean> = ko.observable(true);
        com60HVacation: KnockoutObservable<boolean> = ko.observable(true);
        authentication: KnockoutObservable<AuthorityDetailModel> = ko.observable(null);
        allVacation: KnockoutObservable<boolean> = ko.observable(true);

        constructor(yearHoliday: any, substVacation: any, compensLeave: any, com60HVacation: any, authentication: AuthorityDetailModel) {
            var self = this;
            this.yearHoliday20(yearHoliday && authentication.available20());
            this.substVacation(substVacation && authentication.available18());
            this.yearHoliday19(yearHoliday && authentication.available19());
            this.compensLeave(compensLeave && authentication.available17());
            this.com60HVacation(com60HVacation && authentication.available21());
            this.allVacation(this.yearHoliday20() || this.substVacation() || this.yearHoliday19() || this.compensLeave() ||  this.com60HVacation());
        }
    }

    export class TypeDialog {

        attendenceId: string;
        data: Array<DPAttendanceItem>;
        alreadySettingList: KnockoutObservableArray<UnitAlreadySettingModel>;
        selectedCode: KnockoutObservable<string>;
        listCode: KnockoutObservableArray<any>;
        rowId: KnockoutObservable<any>;
        constructor(attendenceId: string, data: Array<DPAttendanceItem>, selectedCode: string, rowId: any) {
            var self = this;
            self.attendenceId = attendenceId;
            self.data = data;
            self.selectedCode = ko.observable(selectedCode);
            self.listCode = ko.observableArray([]);
            self.rowId = ko.observable(rowId);
        };

        showDialog(parent: any) {
            var self = this;
            var selfParent = parent;
            let item: DPAttendanceItem;
            let codeName: any;
            nts.uk.ui.block.invisible();
            nts.uk.ui.block.grayout();
            let dfd = $.Deferred();
            item = _.find(self.data, function(data) {
                return data.id == self.attendenceId;
            })
            switch (item.typeGroup) {
                case 1:
                    // KDL002  
                    let param1 = {
                        typeDialog: 1,
                        param: {
                            workTypeCode: self.selectedCode(),
                            employmentCode: selfParent.employmentCode(),
                            workplaceId: "",
                            date: new Date(),
                            selectCode: self.selectedCode()
                        }
                    }
                    service.findAllCodeName(param1).done((data) => {
                        self.listCode([]);
                        self.listCode(_.map(data, 'code'));
                        nts.uk.ui.windows.setShared('KDL002_Multiple', false, true);
                        //all possible items
                        nts.uk.ui.windows.setShared('KDL002_AllItemObj', nts.uk.util.isNullOrEmpty(self.listCode()) ? [] : self.listCode(), true);
                        //selected items
                        nts.uk.ui.windows.setShared('KDL002_SelectedItemId', [self.selectedCode()], true);
                        nts.uk.ui.windows.sub.modal('/view/kdl/002/a/index.xhtml', { title: '乖離時間の登録＞対象項目', }).onClosed(function(): any {
                            var lst = nts.uk.ui.windows.getShared('KDL002_SelectedNewItem');
                            if (lst) {
                                var objectName = {};
                                objectName["Name" + self.attendenceId] = lst[0].name;
                                var objectCode = {};
                                objectCode["Code" + self.attendenceId] = lst[0].code;

                                $.when($("#dpGrid").ntsGrid("updateRow", self.rowId(), objectName),
                                    $("#dpGrid").ntsGrid("updateRow", self.rowId(), objectCode)
                                ).done(() => {
                                    nts.uk.ui.block.clear();
                                    dfd.resolve();
                                })
                            } else {
                                 nts.uk.ui.block.clear();
                                dfd.resolve();
                            }
                        })
                    });
                    dfd.promise();
                    break;
                case 2:
                    //KDL001 
                    let employeeIdSelect: any = _.find(selfParent.dailyPerfomanceData(), function(item: any) {
                        return item.id == self.rowId().substring(1, self.rowId().length);
                    });
                    let employee: any = _.find(selfParent.lstEmployee(), function(item: any) {
                        return item.id == employeeIdSelect.employeeId;
                    });
                    let param2 = {
                        typeDialog: 2,
                        param: {
                            workplaceId: employee.workplaceId
                        }
                    }
                    service.findAllCodeName(param2).done((data) => {
                        self.listCode([]);
                        self.listCode(_.map(data, 'code'));
                        nts.uk.ui.windows.setShared('kml001multiSelectMode', false);
                        nts.uk.ui.windows.setShared('kml001selectAbleCodeList', nts.uk.util.isNullOrEmpty(self.listCode()) ? [] : self.listCode());
                        nts.uk.ui.windows.setShared('kml001selectedCodeList', []);
                        nts.uk.ui.windows.sub.modal("/view/kdl/001/a/index.xhtml", { title: "割増項目の設定", dialogClass: "no-close" }).onClosed(function() {
                            let codes: any = nts.uk.ui.windows.getShared("kml001selectedCodeList");
                            if (codes) {
                                codeName = _.find(data, (item: any) => {
                                    return item.code == codes[0];
                                });
                                var objectName = {};
                                objectName["Name" + self.attendenceId] = codeName.name;
                                var objectCode = {};
                                objectCode["Code" + self.attendenceId] = codeName.code;

                                $.when($("#dpGrid").ntsGrid("updateRow", self.rowId(), objectName),
                                    $("#dpGrid").ntsGrid("updateRow", self.rowId(), objectCode)
                                ).done(() => {
                                    nts.uk.ui.block.clear();
                                    dfd.resolve();
                                })
                            }
                        });
                    });
                    dfd.promise()
                    break;
                case 3:
                    //KDL010 
                    nts.uk.ui.block.invisible();
                    nts.uk.ui.windows.setShared('KDL010SelectWorkLocation', self.selectedCode());
                    nts.uk.ui.windows.sub.modal("/view/kdl/010/a/index.xhtml", { dialogClass: "no-close" }).onClosed(() => {
                        var self = this;
                        var returnWorkLocationCD = nts.uk.ui.windows.getShared("KDL010workLocation");
                        if (returnWorkLocationCD !== undefined) {
                            let dataKDL: any;
                            let param3 = {
                                typeDialog: 3
                            }
                            service.findAllCodeName(param3).done((data: any) => {
                                codeName = _.find(data, (item: any) => {
                                    return item.code == returnWorkLocationCD;
                                });
                                var objectName = {};
                                objectName["Name" + self.attendenceId] = codeName.name;
                                var objectCode = {};
                                objectCode["Code" + self.attendenceId] = codeName.code;

                                $.when($("#dpGrid").ntsGrid("updateRow", self.rowId(), objectName),
                                    $("#dpGrid").ntsGrid("updateRow", self.rowId(), objectCode)
                                ).done(() => {
                                    nts.uk.ui.block.clear();
                                    dfd.resolve();
                                })
                            });
                        }
                        else {
                            nts.uk.ui.block.clear();
                        }
                    });
                    dfd.promise()
                    break;
                case 4:
                    //KDL032 
                    break;
                case 5:
                    //CDL008 
                    let dateCon = _.find(selfParent.dpData, (item: any) => {
                        return item.id == self.rowId().substring(1, self.rowId().length);
                    });
                    
                     let param5 = {
                            typeDialog: 5,
                            param: {
                                date: moment(dateCon.date)
                            }
                        }
                    let data5 : any;
                    let dfd1 = $.Deferred();
                    service.findAllCodeName(param5).done((data: any) => {
                        data5 = data;
                        codeName = _.find(data5, (item: any) => {
                            return item.code == self.selectedCode();
                        });
                        nts.uk.ui.windows.setShared('inputCDL008', {
                            selectedCodes: codeName == undefined ? "" : codeName.id,
                            baseDate: moment(dateCon.date),
                            isMultiple: false
                    }, true);
                        dfd1.resolve()
                    });
                    dfd1.promise();
                    nts.uk.ui.windows.sub.modal('com', '/view/cdl/008/a/index.xhtml').onClosed(function(): any {
                        // Check is cancel.
                        if (nts.uk.ui.windows.getShared('CDL008Cancel')) {
                            return;
                        }
                        //view all code of selected item 
                        var output = nts.uk.ui.windows.getShared('outputCDL008');
                            codeName = _.find(data5, (item: any) => {
                                return item.id == output;
                            });
                            var objectName = {};
                            objectName["Name" + self.attendenceId] = codeName.name;
                            var objectCode = {};
                            objectCode["Code" + self.attendenceId] = codeName.code;

                            $.when($("#dpGrid").ntsGrid("updateRow", self.rowId(), objectName),
                                $("#dpGrid").ntsGrid("updateRow", self.rowId(), objectCode)
                            ).done(() => {
                                nts.uk.ui.block.clear();
                                dfd.resolve();
                            })
                    })
                    break;
                case 6:
                    //KCP002
                    nts.uk.ui.windows.setShared('inputCDL003', {
                        selectedCodes: self.selectedCode(),
                        showNoSelection: false,
                        isMultiple: false
                    }, true);

                    nts.uk.ui.windows.sub.modal('com', '/view/cdl/003/a/index.xhtml').onClosed(function(): any {
                        //view all code of selected item 
                        var output = nts.uk.ui.windows.getShared('outputCDL003');
                        if (output) {
                            let param6 = {
                                typeDialog: 6
                            }
                            service.findAllCodeName(param6).done((data: any) => {
                                codeName = _.find(data, (item: any) => {
                                    return item.code == output;
                                });
                                var objectName = {};
                                objectName["Name" + self.attendenceId] = codeName.name;
                                var objectCode = {};
                                objectCode["Code" + self.attendenceId] = codeName.code;

                                $.when($("#dpGrid").ntsGrid("updateRow", self.rowId(), objectName),
                                    $("#dpGrid").ntsGrid("updateRow", self.rowId(), objectCode)
                                ).done(() => {
                                    nts.uk.ui.block.clear();
                                    dfd.resolve();
                                })
                            });
                        }
                    })
                    break;
                case 7:
                    //KCP003 
                    let dateCon7 = _.find(selfParent.dpData, (item: any) => {
                        return item.id == self.rowId().substring(1, self.rowId().length);
                    });
                     let param7 = {
                            typeDialog: 7,
                            param: {
                                date: moment(dateCon7.date)
                            }
                        }
                    let data7 : any;
                    let dfd7 = $.Deferred();
                    service.findAllCodeName(param7).done((data: any) => {
                        data7 = data;
                        codeName = _.find(data, (item: any) => {
                            return item.code == self.selectedCode();
                        });
                        nts.uk.ui.windows.setShared('inputCDL004', {
                            baseDate: moment(dateCon7.date),
                            selectedCodes: codeName == undefined ? "" : codeName.id,
                            showNoSelection: false,
                            isMultiple: false
                    }, true);
                         dfd7.resolve();
                    });
                    dfd7.promise();
                    nts.uk.ui.windows.sub.modal('com', '/view/cdl/004/a/index.xhtml').onClosed(function(): any {
                        var isCancel = nts.uk.ui.windows.getShared('CDL004Cancel');
                        if (isCancel) {
                            return;
                        }
                        var output = nts.uk.ui.windows.getShared('outputCDL004');
                            codeName = _.find(data7, (item: any) => {
                                return item.id == output;
                            });
                            var objectName = {};
                            objectName["Name" + self.attendenceId] = codeName.name;
                            var objectCode = {};
                            objectCode["Code" + self.attendenceId] = codeName.code;

                            $.when($("#dpGrid").ntsGrid("updateRow", self.rowId(), objectName),
                                $("#dpGrid").ntsGrid("updateRow", self.rowId(), objectCode)
                            ).done(() => {
                                nts.uk.ui.block.clear();
                                dfd.resolve();
                            })
                    })
                    break;
                case 8:
                    nts.uk.ui.windows.setShared('CDL002Params', {
                        isMultiple: false,
                        selectedCodes: self.selectedCode(),
                        showNoSelection: false
                    }, true);

                    nts.uk.ui.windows.sub.modal('com', '/view/cdl/002/a/index.xhtml').onClosed(function(): any {
                       // nts.uk.ui.block.clear();
                        var isCancel = nts.uk.ui.windows.getShared('CDL002Cancel');
                        if (isCancel) {
                            return;
                        }
                        var output = nts.uk.ui.windows.getShared('CDL002Output');
                        let param8 = {
                            typeDialog: 8,
                        }
                        service.findAllCodeName(param8).done((data: any) => {
                            nts.uk.ui.block.clear();
                            codeName = _.find(data, (item: any) => {
                                return item.code == output;
                            });
                            var objectName = {};
                            objectName["Name" + self.attendenceId] = codeName.name;
                            var objectCode = {};
                            objectCode["Code" + self.attendenceId] = codeName.code;

                            $.when($("#dpGrid").ntsGrid("updateRow", self.rowId(), objectName),
                                $("#dpGrid").ntsGrid("updateRow", self.rowId(), objectCode)
                            ).done(() => {
                                nts.uk.ui.block.clear();
                                dfd.resolve();
                            })
                        });
                    })
                    break;
            }
            nts.uk.ui.block.clear();
        }
    }
    export interface DPAttendanceItem {
        id: string;
        name: string;
        displayNumber: number;
        userCanSet: boolean;
        lineBreakPosition: number;
        attendanceAtr: number;
        /*DUTY(1, "勤務種類"),
        WORK_HOURS(2, "就業時間帯"), 
        SERVICE_PLACE(3, "勤務場所"), 
        REASON(4, "乖離理由"),
        WORKPLACE(5, "職場"),--
        CLASSIFICATION(6, "分類") 
        POSSITION(7, "職位")-- 
        EMPLOYMENT(8, "雇用区分") */
        typeGroup: number;
    }

    class InfoCellEdit {
        rowId: any;
        itemId: any;
        value: any;
        valueType: number;
        layoutCode: string;
        employeeId: string;
        date: any;
        typeGroup : number;
        constructor(rowId: any, itemId: any, value: any, valueType: number, layoutCode: string, employeeId: string, date: any, typeGroup: number) {
            this.rowId = rowId;
            this.itemId = itemId;
            this.value = value;
            this.valueType = valueType;
            this.layoutCode = layoutCode;
            this.employeeId = employeeId;
            this.date = date;
            this.typeGroup = typeGroup;
        }
    }
    
    class DataHoliday {
        compensation: string;
        substitute: string;
        paidYear: string;
        paidHalf: string;
        paidHours: string;
        fundedPaid: string;
        constructor(compensation: string, substitute: string, paidYear: string, paidHalf: string, paidHours: string, fundedPaid: string) {
            this.compensation = nts.uk.resource.getText("KDW003_8", [compensation])
            this.substitute = nts.uk.resource.getText("KDW003_8",[substitute])
            this.paidYear = nts.uk.resource.getText("KDW003_8", [paidYear])
//            this.paidHalf = nts.uk.resource.getText("KDW003_10", paidHalf)
//            this.paidHours = nts.uk.resource.getText("KDW003_11", paidHours)
            this.fundedPaid = nts.uk.resource.getText("KDW003_8", [fundedPaid])
        }
      
    }
}