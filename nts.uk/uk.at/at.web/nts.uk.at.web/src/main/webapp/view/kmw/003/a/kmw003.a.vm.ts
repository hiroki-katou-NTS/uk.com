module nts.uk.at.view.kmw003.a.viewmodel {
    export interface EmployeeSearchDto {
        employeeId: string;
        employeeCode: string;
        employeeName: string;
        workplaceCode: string;
        workplaceId: string;
        workplaceName: string;
    }
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
        comment: KnockoutObservable<string> = ko.observable('');
        closureName: KnockoutObservable<string> = ko.observable('');
        // date ranger component
        dateRanger: KnockoutObservable<any> = ko.observable(null);
       

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
        
        dataAll: KnockoutObservable<any> = ko.observable(null);
        hasLstHeader : boolean  =  true;
        dPErrorDto: KnockoutObservable<any> = ko.observable();
        listCareError: KnockoutObservableArray<any> = ko.observableArray([]);
        listCareInputError: KnockoutObservableArray<any> = ko.observableArray([]);
        employIdLogin: any;
        dialogShow: any;
        //contain data share
        screenModeApproval: KnockoutObservable<any> = ko.observable(null);
        changePeriod: KnockoutObservable<any> = ko.observable(true);
        errorReference: KnockoutObservable<any> = ko.observable(true);
        activationSourceRefer: KnockoutObservable<any> = ko.observable(null);
        tighten: KnockoutObservable<any> = ko.observable(null);
        yearMonth: KnockoutObservable<number>;
        selectedDate: KnockoutObservable<string> = ko.observable(null);
        constructor() {
            var self = this;
            self.yearMonth = ko.observable(Number(moment(new Date()).format("YYYYMM")));
            self.initLegendButton();
            //self.initDisplayFormat();
            self.headersGrid = ko.observableArray([]);
            self.fixColGrid = ko.observableArray([]);
            
        }
         
        initLegendButton() {
            var self = this;
            self.legendOptions = {
                items: [
                    { colorCode: '#94B7FE', labelText: '手修正（本人）' },
                    { colorCode: '#CEE6FF', labelText: '手修正（他人）' },
                    { colorCode: '#DDDDD2', labelText: nts.uk.resource.getText("KMW003_33") },
                ]
            };
        }

       

        initDisplayFormat(actualTimes: Array<any>) {
            let self = this;
            if(actualTimes && actualTimes.length > 0){
                let actualTimeDatas = ko.observableArray();            
                for (let i = 0; i < actualTimes.length; i++) {
                    actualTimeDatas.push({ code: i, name: actualTimes[i].startDate + "～" + actualTimes[i].endDate });
                }
                self.displayFormatOptions = actualTimeDatas;
            }            
            self.displayFormat(0);
        }
        
        startPage(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            let dateRangeParam = nts.uk.ui.windows.getShared('DateRangeKMW003');
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
                self.dataAll(data);
                self.itemValueAll(data.itemValues);
                self.comment(data.comment != null ? '■ ' + data.comment : null);
                self.closureName(data.closureName);

                self.yearMonth(data.processDate);
                self.initDisplayFormat(data.lstActualTimes);
                self.employmentCode(data.employmentCode);
                // Fixed Header
                self.setFixedHeader(data.lstFixedHeader);
               
                self.lstEmployee(_.orderBy(data.lstEmployee, ['code'], ['asc']));
                self.receiveData(data);
                let employeeLogin: any = _.find(self.lstEmployee(), function(data){
                    return data.loginUser == true;
                });
                self.employIdLogin = employeeLogin;
                //self.selectedEmployee(employeeLogin.id);
                self.extractionData();
                self.loadGrid();
                //  self.extraction();
                self.initCcg001();
                self.loadCcg001();
                nts.uk.ui.block.clear();
                dfd.resolve();
            });
            return dfd.promise();
        }
        
        setFixedHeader(fixHeader: Array<any>){
            let self = this;            
            let fixedData = _.map(fixHeader, function(item: any)  
            { 
                let fixedItem = { columnKey: item.key, isFixed: true };
                return fixedItem;
            });
            self.fixHeaders(fixedData);
        }
        receiveData(data) {
            var self = this;
            self.dpData = data.lstData;
            self.cellStates(data.lstCellState);
            self.optionalHeader = data.lstControlDisplayItem.lstHeader;
            self.sheetsGrid(data.lstControlDisplayItem.lstSheet);
            self.sheetsGrid.valueHasMutated();
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
        
        getPrimitiveValue(value : any, atr: any): string{
            var self = this;
            let valueResult : string = "";
            if (atr != undefined && atr != null) {
                    if (atr == 6) {
                        // Time
                        valueResult = value == "" ? null : String(self.getHoursAll(value));
                    } else if(atr == 5){
                         valueResult =  value == "" ? null : String(self.getHoursTime(value));
                    } else{
                         valueResult = value;
                    }
            } else {
                valueResult = value;
            }
            return valueResult;
        }
        
        getHours(value: any) : number{
            return Number(value.split(':')[0]) * 60 + Number(value.split(':')[1]);
        }
        //time
        getHoursTime(value) : number{
              var self = this;
            if (value.indexOf(":") != -1) {
                if (value.indexOf("-") != -1) {
                    let valueTemp = value.split('-')[1];
                    return 0 - self.getHours(valueTemp);
                } else {
                    return self.getHours(value);
                }
            } else {
                return value;
            }
        }
        
        //time day
        getHoursAll(value: any) : number{
             var self = this;
            if (value.indexOf(":") != -1) {
                if (value.indexOf("-") != -1) {
                    let valueTemp = value.split('-')[1];
                    return self.getHours(valueTemp) - 24 * 60;
                } else {
                    return self.getHours(value);
                }
            } else {
                return value;
            }
        }
        
        //check data item in care and childCare 
        // child care = 0 , care = 1, other = 2;
        checkItemCare(itemId : any): number {
            if(itemId == 759 || itemId == 760 || itemId == 761 || itemId == 762){
               return 0; 
            }else if(itemId == 763 || itemId == 764 || itemId == 765 || itemId == 766){
               return 1; 
            }else{
               return 2; 
            }
        }
        
        // check data error group care , child care
        checkErrorData(group : number, data: any, dataSource :any ) : boolean{
            var self = this;
            if (group != 2) {
                let rowItemSelect: any = _.find(dataSource, function(value: any) {
                        return value.id == data.rowId;
                    });
                data["itemId"] = data.columnKey.substring(1, data.columnKey.length);
                self.checkInputCare(data, rowItemSelect);
                if (data.value != "") {
                    if (group == 0) {
                        if ((rowItemSelect.A763 != undefined && rowItemSelect.A763 != "") || (rowItemSelect.A763 != undefined && rowItemSelect.A763 != "")
                            || (rowItemSelect.A763 != undefined && rowItemSelect.A763 != "") || (rowItemSelect.A763 != undefined && rowItemSelect.A763 != "")) {
                            // alert Error
                            self.listCareError.push(data);
                            return false;
                            // nts.uk.ui.dialog.alertError({ messageId: "Msg_996" })
                        }

                    } else if (group == 1) {
                        if ((rowItemSelect.A759 != undefined && rowItemSelect.A759 != "") || (rowItemSelect.A760 != undefined && rowItemSelect.A760 != "")
                            || (rowItemSelect.A761 != undefined && rowItemSelect.A761 != "") || (rowItemSelect.A762 != undefined && rowItemSelect.A762 != "")) {
                            // alert Error
                            self.listCareError.push(data);
                            return false;
                            //nts.uk.ui.dialog.alertError({ messageId: "Msg_996" })
                        }
                    }
                }
            }
            return true;
        }
        
        checkInputCare(data: any, rowItemSelect : any){
            var self = this;
            switch(Number(data.itemId)){
                case 759:
                    if(!self.isNNUE(rowItemSelect.A760) || data.value ==""){
                       data["itemId"] = data.columnKey.substring(1,data.columnKey.length);
                       data["group"] = 760;
                       self.listCareInputError.push(data); 
                    }
                    break;
                case 760:
                 if(!self.isNNUE(rowItemSelect.A759)||data.value ==""){
                       data["itemId"] = data.columnKey.substring(1,data.columnKey.length);
                     data["group"] = 759;
                       self.listCareInputError.push(data); 
                    }
                    break;
                case 761:
                 if(!self.isNNUE(rowItemSelect.A762)||data.value ==""){
                       data["itemId"] = data.columnKey.substring(1,data.columnKey.length);
                       data["group"] = 762;
                       self.listCareInputError.push(data); 
                    }
                    break;
                case 762:
                 if(!self.isNNUE(rowItemSelect.A761)||data.value ==""){
                       data["itemId"] = data.columnKey.substring(1,data.columnKey.length);
                     data["group"] = 761;
                       self.listCareInputError.push(data); 
                    }
                    break;
                case 763:
                 if(!self.isNNUE(rowItemSelect.A764)||data.value ==""){
                       data["itemId"] = data.columnKey.substring(1,data.columnKey.length);
                       data["group"] = 764;
                       self.listCareInputError.push(data); 
                    }
                    break;
                case 764:
                 if(!self.isNNUE(rowItemSelect.A763)||data.value ==""){
                       data["itemId"] = data.columnKey.substring(1,data.columnKey.length);
                       data["group"] = 763;
                       self.listCareInputError.push(data); 
                    }
                    break;
                case 765:
                 if(!self.isNNUE(rowItemSelect.A766)||data.value ==""){
                       data["itemId"] = data.columnKey.substring(1,data.columnKey.length);
                       data["group"] = 766;
                       self.listCareInputError.push(data); 
                    }
                    break;
                case 766:
                 if(!self.isNNUE(rowItemSelect.A765)||data.value ==""){
                       data["itemId"] = data.columnKey.substring(1,data.columnKey.length);
                       data["group"] = 765;
                       self.listCareInputError.push(data); 
                    }
                    break;
            }
        }
        
        isNNUE(value : any) : boolean{
            if(value != undefined && value != "" && value != null) return true;
            else return false;
        }
        
        hideComponent() {
            var self = this;
            if (self.displayFormat() == 0) {
                $('#fixed-table').show();
               //  $("#content-grid").attr('style', 'top: 244px !IMPORTANT');
            } else if (self.displayFormat() == 1) {
                $('#fixed-table').hide();
               // $("#content-grid").attr('style', 'top: 225px !IMPORTANT');
            } else {
                $('#fixed-table').hide();
               // $("#content-grid").attr('style', 'top: 180px !IMPORTANT');
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
        

        extractionData() {
            var self = this;
            self.headersGrid([]);
            self.fixColGrid = self.fixHeaders;
            
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
            $("#dpGrid").ntsGrid("checkAll", "sign");
            $("#dpGrid").ntsGrid("checkAll", "approval");
        }
        releaseAll() {
            var self = this;
            $("#dpGrid").ntsGrid("uncheckAll", "sign");
            $("#dpGrid").ntsGrid("uncheckAll", "approval");
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
                    employeeName: data.employeeName,
                    workplaceId : data.workplaceId,
                    employmentCode : data.employmentCode,
                    dateDetail : moment(data.date, "YYYY/MM/DD"),
                    typeGroup : data.typeGroup
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
            //$('#emp-component').ntsLoadListComponent(kcp009Options);
        }

        //init ccg001
        initCcg001() {
             let self = this;
            if ($('.ccg-sample-has-error').ntsError('hasError')) {
                return;
            }
            if (false && false &&  false) {
                nts.uk.ui.dialog.alertError("Base Date or Closure or Period must be shown!");
                return;
            }
            
            self.ccg001 = {
                /** Common properties */
                systemType: 2,
                showEmployeeSelection: false, // 検索タイプ
                showQuickSearchTab: true, // クイック検索
                showAdvancedSearchTab: true, // 詳細検索
                showBaseDate: false, // 基準日利用
                showClosure: true, // 就業締め日利用
                showAllClosure: false, // 全締め表示
                showPeriod: true, // 対象期間利用
                periodFormatYM: false, // 対象期間精度

                /** Required parameter */
                //baseDate: self.baseDate().toISOString(), // 基準日
                //periodStartDate: new Date(self.dateRanger().startDate).toISOString(), // 対象期間開始日
                //periodEndDate: new Date(self.dateRanger().endDate).toISOString(), // 対象期間終了日
                inService: true, // 在職区分
                leaveOfAbsence: true, // 休職区分
                closed: true, // 休業区分
                retirement: false, // 退職区分

                /** Quick search tab options */
                showAllReferableEmployee: true, // 参照可能な社員すべて
                showOnlyMe: true, // 自分だけ
                showSameWorkplace: true, // 同じ職場の社員
                showSameWorkplaceAndChild: true, // 同じ職場とその配下の社員

                /** Advanced search properties */
                showEmployment: true, // 雇用条件
                showWorkplace: true, // 職場条件
                showClassification: true, // 分類条件
                showJobTitle: true, // 職位条件
                showWorktype: true, // 勤種条件
                isMutipleCheck: false,// 選択モード

                /** Return data */
                returnDataFromCcg001: function(dataList: any) {
                   // self.selectedEmployee(dataList.listEmployee);
                    self.lstEmployee(dataList.listEmployee.map((data: EmployeeSearchDto) => {
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
                },
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
            //Dummy data
            let items = (function () {
                var list = [];
                for (var i = 0; i < 400; i++) {
                    list.push(new GridItem(i));
                }
                return list;
            })();
            //End dummy
            self.setHeaderColor();
            self.setColorWeekend();
            $("#dpGrid").ntsGrid({
                width: (window.screen.availWidth - 200) + "px",
                height: '650px',
                dataSource: items,
                dataSourceAdapter: function(ds) {
                    return ds;
                },
                primaryKey: 'id',
                rowVirtualization: true,
                virtualization: true,
                virtualizationMode: 'continuous',
                enter: self.selectedDirection() == 0 ? 'below' : 'right',
                autoFitWindow: true,
                preventEditInError: false,
                hidePrimaryKey: true,
                userId: "4",
                getUserId: function(k) { return String(k); },
                errorColumns: ["ruleCode"],

                columns: self.headersGrid(),
                features: self.getGridFeatures(),
                ntsFeatures: self.getNtsFeatures(),
                ntsControls: self.getNtsControls()
            });
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
        /**
         * Grid Setting features
         */
        getGridFeatures(): Array<any>{
            let self = this;
            let features = [
                    {
                        name: 'Resizing',
                        columnSettings: [{
                            columnKey: 'id', allowResizing: false, minimumWidth: 0
                        }]
                    },
                    { name: 'MultiColumnHeaders' },
                    {
                        name: 'Paging',
                        pageSize: 100,
                        currentPageIndex: 0
                    },
                    {
                        name: 'ColumnFixing', fixingDirection: 'left',
                        showFixButtons: false,
                        columnSettings: self.fixHeaders()
                    },
                    {
                        name: 'Summaries',
                        showSummariesButton: false,
                        showDropDownButton: false,
                        columnSettings: [
                            {
                                columnKey: 'id', allowSummaries: false,
                                summaryOperands: [{ type: "custom", order: 0, summaryCalculator: function() { return "合計"; } }]
                            },
                            {
                                columnKey: 'state', allowSummaries: true,
                                summaryOperands: [{ type: "custom", order: 0, summaryCalculator: function() { return "合計"; } }]
                            },
                            { columnKey: 'error', allowSummaries: false },
                            { columnKey: 'employeeCode', allowSummaries: false },
                            { columnKey: 'employeeName', allowSummaries: false },
                            { columnKey: 'picture-person', allowSummaries: false },
                            { columnKey: 'identify', allowSummaries: false },
                            { columnKey: 'dailyconfirm', allowSummaries: false },
                            { columnKey: 'dailyperformace', allowSummaries: false },
                            {
                                columnKey: 'time', allowSummaries: true,
                                summaryOperands: [{
                                    rowDisplayLabel: "",
                                    type: 'custom',
                                    summaryCalculator: 1,
                                    order: 0
                                }]
                            },
                            
                            { columnKey: 'alert', allowSummaries: false }
                        ],
                        resultTemplate: '{1}'
                    }
                ];
            return features;
        }
        getNtsFeatures(): Array<any>{
            let self = this;
            //Dummy data
            let statesTable = [];
            statesTable.push(new CellState(0, "address1", [nts.uk.ui.jqueryExtentions.ntsGrid.color.Error, nts.uk.ui.jqueryExtentions.ntsGrid.color.Alarm]));
            statesTable.push(new CellState(0, "time", [nts.uk.ui.jqueryExtentions.ntsGrid.color.ManualEditTarget, nts.uk.ui.jqueryExtentions.ntsGrid.color.ManualEditOther]));
            statesTable.push(new CellState(1, "time", [nts.uk.ui.jqueryExtentions.ntsGrid.color.Reflect, nts.uk.ui.jqueryExtentions.ntsGrid.color.Calculation]));
            statesTable.push(new CellState(5, "time", [nts.uk.ui.jqueryExtentions.ntsGrid.color.Disable]));
            statesTable.push(new CellState(6, "header0", [nts.uk.ui.jqueryExtentions.ntsGrid.color.Disable]));
            for (let i = 1; i < 100; i++) {
                if (i % 2 === 0) {
                    statesTable.push(new CellState(i, "address1", [nts.uk.ui.jqueryExtentions.ntsGrid.color.Alarm, nts.uk.ui.jqueryExtentions.ntsGrid.color.Reflect]));
                    statesTable.push(new CellState(i, "comboCode1", [nts.uk.ui.jqueryExtentions.ntsGrid.color.Disable]));
                    statesTable.push(new CellState(i, "combo", [nts.uk.ui.jqueryExtentions.ntsGrid.color.Disable]));
                }
            }
            let rowStates = [];
            for (let i = 9; i < 10; i++) {
                rowStates.push(new RowState(i, true));
            }
            
            let keys = [];
            for (let i = 0; i < 300; i++) {
                keys.push(i);
            }
            let colorsTable = [];
            for (let i = 0; i < 400; i++) {
                if (i % 7 === 0) {
                    colorsTable.push(new TextColor(i, "id", "text-color1"));
                } else if (i % 3 === 0) {
                    colorsTable.push(new TextColor(i, "id", "#B3AEF1"));    
                }
            }
            let features: Array<any> = [{ name: 'CopyPaste' },
                    { name: 'CellEdit' },
                     {
                        name: 'CellColor', columns: [
                            {
                                key: 'ruleCode',
                                parse: function(value) {
                                    return value;
                                },
                                map: function(result) {
                                    if (result <= 1) return "#00b050";
                                    else if (result === 2) return "pink";
                                    else return "#0ff";
                                }
                            }
                        ]
                    },
                    {
                        name: 'CellState',
                        rowId: 'rowId',
                        columnKey: 'columnKey',
                        state: 'state',
                        states: statesTable
                    },
                    {
                        name: 'RowState',
                        rows: rowStates
                    },
                    {
                        name: 'TextColor',
                        rowId: 'rowId',
                        columnKey: 'columnKey',
                        color: 'color',
                        colorsTable: colorsTable
                    },
                    {
                        name: 'HeaderStyles',
                        columns: [
                            { key: 'time', color: '#E9AEF1' }
                        ]
                    },                    
                ];
                if (self.sheetsGrid().length > 0) {
                    features.push({
                        name: "Sheet",
                        initialDisplay: self.sheetsGrid()[0].name,
                        sheets: self.sheetsGrid()
                    });
                }
            return features;
        }
        /**
         * Create NtsControls
         */
        getNtsControls(): Array<any>{            
            let ntsControls: Array<any> = [
                { name: 'Checkbox', options: { value: 1, text: '' }, optionsValue: 'value', optionsText: 'text', controlType: 'CheckBox', enable: true },
                { name: 'Button', controlType: 'Button', text:  nts.uk.resource.getText("KMW003_29"), enable: true, click: function() { 
                        let self = this;
                        nts.uk.ui.windows.sub.modal("/view/kdw/003/a/index.xhtml").onClosed(() => {
                                                        
                        });
                
                    } 
                },
                { name: 'FlexImage', source: 'ui-icon ui-icon-locked', click: function() { alert('Show!'); }, controlType: 'FlexImage' },
                { name: 'Image', source: 'ui-icon ui-icon-info', controlType: 'Image' },
                { name: 'TextEditor', controlType: 'TextEditor', constraint: { valueType: 'Integer', required: true, format: "Number_Separated" } }
                ];
            return ntsControls;
        }
        reloadGrid() {
            var self = this;
            nts.uk.ui.block.invisible();
             nts.uk.ui.block.grayout();
            setTimeout(function() {
                 //self.createSumColumn(self.dataAll());
                    self.columnSettings(self.dataAll().lstControlDisplayItem.columnSettings);
                    self.receiveData(self.dataAll());
                    self.extractionData();
                    self.loadGrid();
                nts.uk.ui.block.clear();
            }, 500);
        }

        

        loadHeader(mode) {
            var self = this;
            let tempList = [];
            self.dislayNumberHeaderText();
            _.forEach(self.optionalHeader, (header) => {
                if (header.constraint == null || header.constraint == undefined) {
                    delete header.constraint;
                }else{
                    header.constraint["cDisplayType"] = header.constraint.cdisplayType;
                    if (header.constraint.cDisplayType != null && header.constraint.cDisplayType != undefined) {
                        if (header.constraint.cDisplayType != "Primitive" && header.constraint.cDisplayType != "Combo") {
                            if (header.constraint.cDisplayType.indexOf("Currency") != -1) {
                                header["columnCssClass"] = "currency-symbol";
                                header.constraint["min"] = "0";
                                header.constraint["max"] = "9999999999"
                            } else if (header.constraint.cDisplayType == "Clock") {
                                header["columnCssClass"] = "right-align";
                                header.constraint["min"] = "-10:00";
                                header.constraint["max"] = "100:30"
                            } else if (header.constraint.cDisplayType == "Integer") {
                                header["columnCssClass"] = "right-align";
                            }
                            delete header.constraint.primitiveValue;
                        } else {
                           
                            if (header.constraint.cDisplayType == "Primitive") {
                                delete header.group[0].constraint.cDisplayType
                            } else if (header.constraint.cDisplayType == "Combo") {
                                    header.group[0].constraint["min"] = 0;
                                    header.group[0].constraint["max"] = Number(header.group[0].constraint.primitiveValue);
                                    header.group[0].constraint["cDisplayType"] =  header.group[0].constraint.cdisplayType;
                                   delete header.group[0].constraint.cdisplayType
                                   delete header.group[0].constraint.primitiveValue;
                            }
                            delete header.constraint;
                            delete header.group[1].constraint;
                        }
                    }
                    if(header.constraint != undefined) delete header.constraint.cdisplayType;
                }
                if (header.group != null && header.group != undefined) {
                    if (header.group.length > 0) {
                       if( header.group[0].constraint == undefined) delete header.group[0].constraint;
                       delete header.group[1].constraint;
                        delete header.group[0].group;
                        delete header.key;
                        delete header.dataType;
                       // delete header.width;
                        delete header.ntsControl;
                        delete header.changedByOther;
                        delete header.changedByYou;
                       // delete header.color;
                        delete header.hidden;
                        delete header.ntsType;
                        delete header.onChange;
                        delete header.group[1].ntsType;
                        delete header.group[1].onChange;
                        if (header.group[0].dataType == "String") {
                            //header.group[0].onChange = self.search;
                           // delete header.group[0].onChange;
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
            return self.headersGrid(tempList);
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
            } else {
                _.remove(self.dateModeHeader, function(header) {
                    return header.key === "picture-person";
                });
            }
        }

        dislayNumberHeaderText() {
            var self = this;
            if (self.showHeaderNumber()) {
                self.optionalHeader.map((header) => {
                    if (header.headerText) {
                        if (header.group == undefined || header.group == null) {
                            header.headerText = header.headerText + " " + header.key.substring(1, header.key.length);
                        }else{
                            header.headerText = header.headerText + " " + header.group[1].key.substring(4, header.group[1].key.length)
                        }
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

        
        getDataShare(data : any){
            var self = this;
           var param = {
                dateRange: data.dateRangeParam? {
                    startDate: moment(data.dateRangeParam.startDate).utc().toISOString(),
                    endDate: moment(data.dateRangeParam.endDate).utc().toISOString()
                }: null,
                displayFormat : 0,
                initScreen: 0,
                lstEmployee: [],
                formatCodes: self.formatCodes()
            }; 
        }
        /**
         * 「KDW003_D_抽出条件の選択」を起動する
         * 起動モード：月別 (start mode: mothly)
         * 選択済項目：選択している「月別実績のエラーアラームコード」
         */
        extractCondition(){
            let self = this;
            nts.uk.ui.windows.sub.modal("/view/kdw/003/d/index.xhtml").onClosed(() => {
                
            });
        }
        
        /**
         * 「KDW003_C_表示フォーマットの選択」を起動する
         * 起動モード：月別
         * 選択済項目：選択している「月別実績のフォーマットコード」
         */
        displayItem(){
             let self = this;
            nts.uk.ui.windows.sub.modal("/view/kdw/003/c/index.xhtml").onClosed(() => {
                
            });
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
            this.compensation = nts.uk.resource.getText("KMW003_8", [compensation])
            this.substitute = nts.uk.resource.getText("KMW003_8",[substitute])
            this.paidYear = nts.uk.resource.getText("KMW003_8", [paidYear])
//            this.paidHalf = nts.uk.resource.getText("KMW003_10", paidHalf)
//            this.paidHours = nts.uk.resource.getText("KMW003_11", paidHours)
            this.fundedPaid = nts.uk.resource.getText("KMW003_8", [fundedPaid])
        }
      
    }
    class RowState {
            rowId: number;
            disable: boolean;
            constructor(rowId: number, disable: boolean) {
                this.rowId = rowId;
                this.disable = disable;
            }
        }
    class TextColor {
            rowId: number;
            columnKey: string;
            color: string;
            constructor(rowId: any, columnKey: string, color: string) {
                this.rowId = rowId;
                this.columnKey = columnKey;
                this.color = color;
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
    class GridItem {
            id: number;
            flag: boolean;
            ruleCode: number;
            time: string;
            addressCode1: string;
            addressCode2: string;
            address1: string;
            address2: string;
            comboCode1: number;
            combo: string;
            header0: string;
            comboCode2: number;
            header01: string;
            header02: string;
            header1: string;
            header2: string;
            header3: number;
            header4: string;
            header5: string;
            header6: string;
            alert: string;
            constructor(index: number) {
                this.id = index;
                this.flag = index % 2 == 0;
                this.ruleCode = index;
                this.time = "13:36";
                this.addressCode1 = "001";
                this.addressCode2 = "002";
                this.address1 = "HN";
                this.address2 = "愛知県日本";
                this.comboCode1 = index % 3 + 1;
                this.combo = String(index % 3 + 1);
                this.header0 = "Out";
                this.comboCode2 = index % 3 + 4;
                this.header01 = String(index % 3 + 4);
                this.header02 = String(index % 3 + 1);
                this.header1 = "001";
                this.header2 = "内容１２";
                this.header3 = index % 9;
                this.header4 = "内容４";
                this.header5 = "002"; 
                this.header6 = "内容５６";
                this.alert = "Act";
            }
        }
}