module nts.uk.at.view.kdw004.a.viewmodel {
    var SATURDAY_BACKGROUND_COLOR = "#8BD8FF";
    var SUNDAY_BACKGROUND_COLOR = "#FABF8F";
    var SATURDAY_TEXT_COLOR = "#0031FF";
    var SUNDAY_TEXT_COLOR = "#FF0000";
    var TEMPLATE_EMPLOYEE_NAME_HEADER = "<span class='limited-label' style='width: 200px; text-decoration: underline; cursor: pointer;' data-bind=\"click: clickNameJumpToKdw003.bind($data, '${employeeId}')\">${employeeName}</span>";
    enum ApprovalStatus {
        Approved = 0,
        UnApproved = 1,
        CannotApproved = 2,
        Disable = 3
    }
    var getTemplateDisplayStt = (headerTxtId,startDate) => {
        return "{{if ${" + headerTxtId + "} == '" + ApprovalStatus.Approved + "' }}" +
            "<a class='approved' href='#' data-bind=\"click: clickStatusJumpToKdw003.bind($data,'${employeeId}','"+startDate+"')\">○</a>" +
            "{{elseif ${" + headerTxtId + "} == '" + ApprovalStatus.UnApproved + "' }}" +
            "<a class='unapproved' href='#' data-bind=\"click: clickStatusJumpToKdw003.bind($data,'${employeeId}','"+startDate+"')\">！</a>" +
            "{{elseif ${" + headerTxtId + "} == '" + ApprovalStatus.CannotApproved + "' }}" +
            "<a class='cannotApproved'>＿</a>" +
            "{{elseif ${" + headerTxtId + "} == '" + ApprovalStatus.Disable + "' }}" +
            "<span class='disable'></span>" +
            "{{/if}}";
    };
    export class ScreenModelKDW004A {

        legendOptions: any = {
            items: [
                { className: 'approved-img', labelText: nts.uk.resource.getText('KDW004_8') },
                { className: 'cannotApproved-img', labelText: nts.uk.resource.getText('KDW004_9') },
                { className: 'unapproved-img', labelText: nts.uk.resource.getText('KDW004_10') },
                { className: 'disable-img', labelText: nts.uk.resource.getText('KDW004_11') },
                { className: 'nodata-img', labelText: nts.uk.resource.getText('KDW004_12') }
            ],
            template: '<div class="#{className}"></div><div class="legend-item-label">#{labelText}</div>'
        };
        lstClosure: KnockoutObservableArray<any> = ko.observableArray([]);
        selectedClosure: KnockoutObservable<any> = ko.observable(null);
        datePeriod: KnockoutObservable<any> = ko.observable(null);
        lstColumns: Array<any> = [];
        lstData: Array<any> = [];
        lstHeaderColor: Array<any> = [];
        currentPageSize: KnockoutObservable<any> = ko.observable(12);

        constructor() {
            var self = this;
            self.selectedClosure.subscribe((val) => {
                if (val) {
                  let currentYearMonth;
                  let closures = self.lstClosure();
                   closures.forEach(x=>{
                       if(x.closureId==val){
                        currentYearMonth =  x.currentYearMonth;  
                       }
                   });
                    service.getDateRange(val,currentYearMonth).done((dateRange) => {
                        self.datePeriod({ startDate: moment(dateRange.startDate).format("YYYY/MM/DD"), endDate: moment(dateRange.endDate).format("YYYY/MM/DD") });
                        //disable tabIndex button date range
                        document.getElementsByClassName("ntsDateRangeButton")[0].tabIndex = -1;
                        document.getElementsByClassName("ntsDateRangeButton")[1].tabIndex = -1;
                    });
                }
            });
        }

        startPage(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            nts.uk.ui.block.grayout();
            service.startscreen().done((result: OneMonthApprovalStatus) => {
                self.lstClosure(result.lstClosure);
                self.selectedClosure(result.lstClosure[0].closureId);
                self.datePeriod({ startDate: moment(result.startDate).format("YYYY/MM/DD"), endDate: moment(result.endDate).format("YYYY/MM/DD") });
                self.lstData = self.convertToGridData(result.lstEmployee);
                self.generateColumns();
                self.loadGrid();
                self.addClickEventDateHeader();
                dfd.resolve();
                nts.uk.ui.block.clear();
            }).fail((error) => {
                nts.uk.ui.block.clear();
                dfd.reject(error.messageId);
            });
            return dfd.promise();
        }

        extract() {
            var self = this;
            let param = {
                closureIdParam: self.selectedClosure(),
                startDateParam: moment(self.datePeriod().startDate).utc().toISOString(),
                endDateParam: moment(self.datePeriod().endDate).utc().toISOString(),
            };
            nts.uk.ui.block.grayout();
            service.extractApprovalStatusData(param).done((result: OneMonthApprovalStatus) => {
                var igridContent = $("#approvalSttGrid")[0];
                ko.cleanNode(igridContent) ;
                self.lstData = self.convertToGridData(result.lstEmployee);
                self.generateColumns();
                self.loadGrid();
                self.setHeadersColor();
                self.addClickEventDateHeader();
                ko.applyBindings(self,igridContent);
                ko.applyBindings(self,approvalSttGrid_headers);
                nts.uk.ui.block.clear();
            }).fail((error) => {
                nts.uk.ui.block.clear();
                nts.uk.ui.dialog.alert({ messageId: error.messageId });
            });
        }
        clickDateJumpToKdw003(date) {
            var self = this;
            let initParam = new DPCorrectionInitParam(DPCorrectionScreenMode.APPROVAL, self.lstData.map((data) => { return data.employeeId; }), false, false, self.selectedClosure());
            initParam.transitionDesScreen = '/view/kdw/004/a/index.xhtml';
            let extractionParam = new DPCorrectionExtractionParam(DPCorrectionDisplayFormat.DATE, date, date, self.lstData.map((data) => { return data.employeeId; }));
            extractionParam.dateTarget = moment(date).format("YYYY/MM/DD");
            nts.uk.request.jump("at", "/view/kdw/003/a/index.xhtml", {initParam: initParam, extractionParam: extractionParam});
        }

        clickStatusJumpToKdw003(employeeId,startDate) {
            var self = this;
            let lstEmpId =[];
            lstEmpId.push(employeeId);
            let initParam = new DPCorrectionInitParam(DPCorrectionScreenMode.APPROVAL, lstEmpId, false, false, self.selectedClosure());
            initParam.transitionDesScreen = '/view/kdw/004/a/index.xhtml';
            let extractionParam = new DPCorrectionExtractionParam(DPCorrectionDisplayFormat.INDIVIDUAl, startDate, startDate, employeeId);
            extractionParam.individualTarget = employeeId;
            nts.uk.request.jump("at", "/view/kdw/003/a/index.xhtml", {initParam: initParam, extractionParam: extractionParam});
        }

        clickNameJumpToKdw003(employeeId) {
            var self = this;
            let initParam = new DPCorrectionInitParam(DPCorrectionScreenMode.APPROVAL, self.lstData.map((data) => { return data.employeeId; }), false, false, self.selectedClosure());
            initParam.transitionDesScreen = '/view/kdw/004/a/index.xhtml';
            let extractionParam = new DPCorrectionExtractionParam(DPCorrectionDisplayFormat.INDIVIDUAl, self.datePeriod().startDate, self.datePeriod().endDate, employeeId);
            extractionParam.individualTarget = employeeId;
            nts.uk.request.jump("at", "/view/kdw/003/a/index.xhtml", {initParam: initParam, extractionParam: extractionParam});
        }

        convertToGridData(lstEmployee: Array<ApprovalEmployee>): Array<any> {
            let results = [];
            for (let i = 0; i < lstEmployee.length; i++) {
                let data = {};
                data['employeeId'] = lstEmployee[i].employeeId;
                data['employeeCode'] = lstEmployee[i].employeeCode;
                data['employeeName'] = lstEmployee[i].employeeName;
                for (let j = 0; j < lstEmployee[i].lstStatus.length; j++) {
                    data[moment(lstEmployee[i].lstStatus[j].date).format('YYYYMMDD')] = lstEmployee[i].lstStatus[j].status;
                }
                results.push(data);
            }
            return results;
        }

        loadGrid(index? : any,pageIndex? : any) {
            var self = this;
            $("#approvalSttGrid").igGrid({
                primaryKey: "employeeCode",
                dataSource: self.lstData,
                width: 1245,
                height: 426,
                autofitLastColumn: false,
                autoGenerateColumns: false,
                alternateRowStyles: false,
                dataSourceType: "json",
                autoCommit: true,
                rowVirtualization: false,
                virtualizationMode: "fixed",
                columns: self.lstColumns,
                features: [
                    {
                        name: 'MultiColumnHeaders'
                    },
                    {
                        name: 'Paging',
                        type: "local",
                        currentPageIndex:pageIndex||0,  
                        pageSize: index||12,
                        pageSizeChanging: (ui, args)=>{
                            ko.cleanNode(approvalSttGrid) ;
                            self.currentPageSize(args.newPageSize);
                             self.loadGrid(args.newPageSize);
                             self.setHeadersColor();
                             self.addClickEventDateHeader();
                             ko.applyBindings(self,approvalSttGrid);
                            ko.applyBindings(self,approvalSttGrid_headers);
                        },
                        pageIndexChanging:(ui, args)=>{
                            ko.cleanNode(approvalSttGrid) ;
                             self.loadGrid(self.currentPageSize(),args.newPageIndex);
                             self.setHeadersColor();
                             self.addClickEventDateHeader();
                             ko.applyBindings(self,approvalSttGrid);
                            ko.applyBindings(self,approvalSttGrid_headers);
                        } 
                    }
                ]
            });
        }

        generateColumns() {
            var self = this;
            self.lstColumns = [
                { key: "employeeId", width: "115px", headerText: nts.uk.resource.getText('KDW004_7'), dataType: "string", hidden: true },
                { key: "employeeCode", width: "115px", headerText: nts.uk.resource.getText('KDW004_13'), dataType: "string" },
                { key: "employeeName", width: "200px", headerText: nts.uk.resource.getText('KDW004_14'), dataType: "string", template: TEMPLATE_EMPLOYEE_NAME_HEADER }
            ];
            self.lstHeaderColor = [];
            let index = moment(self.datePeriod().startDate);
            while (!index.isAfter(self.datePeriod().endDate)) {
                let dayName = index.format("dd");
                let headerNoId = index.format("YYYYMMDD" + 'i');
                let headerTxtId = index.format("YYYYMMDD");
                // if Saturday
                if (index.format("d") == "6") {
                    self.lstHeaderColor.push(
                        { id: headerNoId, background_color: SATURDAY_BACKGROUND_COLOR, text_color: SATURDAY_TEXT_COLOR }
                    );
                    self.lstHeaderColor.push(
                        { id: headerTxtId, background_color: SATURDAY_BACKGROUND_COLOR, text_color: SATURDAY_TEXT_COLOR }
                    );
                    // if Sunday
                } else if (index.format("d") == "0") {
                    self.lstHeaderColor.push(
                        { id: headerNoId, background_color: SUNDAY_BACKGROUND_COLOR, text_color: SUNDAY_TEXT_COLOR }
                    );
                    self.lstHeaderColor.push(
                        { id: headerTxtId, background_color: SUNDAY_BACKGROUND_COLOR, text_color: SUNDAY_TEXT_COLOR }
                    );
                }
                self.lstColumns.push(
                    {
                        key: headerNoId, width: "30px", headerText: index.format("D"), dataType: "string", group: [
                            { key: headerTxtId, width: "30px", headerText: dayName, dataType: "string", template: getTemplateDisplayStt(headerTxtId,index.format("YYYY/MM/DD")) }
                        ]
                    }
                );
                index = index.add(1, "d");
            }
        }

        setHeadersColor() {
            var self = this;
            _.forEach(self.lstHeaderColor, (headerColor) => {
                let header = document.getElementById("approvalSttGrid_" + headerColor.id);
                header.style.backgroundColor = headerColor.background_color;
                header.style.color = headerColor.text_color;
            });
        }

        addClickEventDateHeader() {
            var self = this;
            ko.cleanNode(approvalSttGrid_headers);
            let index = moment(self.datePeriod().startDate);
            while (!index.isAfter(self.datePeriod().endDate)) {
                let header = document.getElementById("approvalSttGrid_" + moment(index).format("YYYYMMDD") + "i").firstChild;
                header.style.textDecoration = "underline";
                header.style.cursor = "pointer";
                header.setAttribute("data-bind", "click: clickDateJumpToKdw003.bind($data, '" + moment(index).format("YYYY/MM/DD") + "')");
                index = index.add(1, "d");
            }
        }
    }

    export class OneMonthApprovalStatus {
        lstClosure: Array<Closure>;
        startDate: string;
        endDate: string;
        lstEmployee: Array<ApprovalEmployee>;
    }

    export class Closure {
        closureId: number;
        closureName: string;
        currentYearMonth: number;
    }

    export class ApprovalEmployee {
        employeeId: string;
        employeeCode: string;
        employeeName: string;
        lstStatus: Array<DateApprovalStatus>;
    }

    export class DateApprovalStatus {
        date: string;
        status: number;
    }

    export class DPCorrectionInitParam {
        //画面モード
        screenMode: DPCorrectionScreenMode;
        //社員一覧
        lstEmployee: any;
        //エラー参照を起動する
        errorRefStartAtr: boolean;
        //期間を変更する
        changePeriodAtr: boolean;
        //処理締め
        targetClosue: number;
        //Optional
        //打刻初期値
        initClock: any;
        //遷移先の画面
        transitionDesScreen: any;

        constructor(screenMode, lstEmployee, errorRefStartAtr, changePeriodAtr, targetClosue) {
            var self = this;
            self.screenMode = screenMode;
            self.lstEmployee = lstEmployee;
            self.errorRefStartAtr = errorRefStartAtr;
            self.changePeriodAtr = changePeriodAtr;
            self.targetClosue = targetClosue;
        }
    }

    export class DPCorrectionExtractionParam {
        //表示形式
        displayFormat: DPCorrectionDisplayFormat;
        //期間
        startDate: string;
        endDate: string;
        //抽出した社員一覧
        lstExtractedEmployee: any;
        //Optional
        //日付別で起動
        dateTarget: any;
        individualTarget: any;

        constructor(displayFormat, startDate, endDate, lstExtractedEmployee) {
            var self = this;
            self.displayFormat = displayFormat;
            self.startDate = startDate;
            self.endDate = endDate;
            self.lstExtractedEmployee = lstExtractedEmployee;
        }
    }

    export enum DPCorrectionScreenMode {
        //通常
        NORMAL = 0,
        //承認
        APPROVAL = 1
    }

    export enum DPCorrectionDisplayFormat {
        //個人別
        INDIVIDUAl = 0,
        //日付別
        DATE = 1,
        //エラー・アラーム
        ErrorAlarm = 2
    }
}