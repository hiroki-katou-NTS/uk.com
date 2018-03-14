module nts.uk.at.view.kdw004.a.viewmodel {
    var SATURDAY_BACKGROUND_COLOR = "#8BD8FF";
    var SUNDAY_BACKGROUND_COLOR = "#FABF8F";
    var SATURDAY_TEXT_COLOR = "#0031FF";
    var SUNDAY_TEXT_COLOR = "#FF0000";
    enum ApprovalStatus {
        Approved = 0,
        UnApproved = 1,
        CannotApproved = 2,
        Disable = 3,
    }
    var getTemplateDisplayStt = (headerTxtId) => {
        return "{{if ${" + headerTxtId + "} == '" + ApprovalStatus.Approved + "' }}" +
            "<a class='approved' href='#'>○</a>" +
            "{{elseif ${" + headerTxtId + "} == '" + ApprovalStatus.UnApproved + "' }}" +
            "<a class='unapproved' href='#'>！</a>" +
            "{{elseif ${" + headerTxtId + "} == '" + ApprovalStatus.CannotApproved + "' }}" +
            "<a class='cannotApproved' href='#'>＿</a>" +
            "{{elseif ${" + headerTxtId + "} == '" + ApprovalStatus.Disable + "' }}" +
            "<span class='disable'></span>" +
            "{{/if}}";
    };
    export class ScreenModelKDW004A {

        legendOptions: any = {
            items: [
                { className: 'approved-img', labelText: nts.uk.resource.getText('KDW004_8') },
                { className: 'unapproved-img', labelText: nts.uk.resource.getText('KDW004_9') },
                { className: 'cannotApproved-img', labelText: nts.uk.resource.getText('KDW004_10') },
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

        constructor() {
            var self = this;

            self.datePeriod.subscribe(() => {
                self.generateColumns();
                self.setHeadersColor();
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
                self.loadGrid();
                self.setHeadersColor();
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
                self.lstData = self.convertToGridData(result.lstEmployee);
                self.loadGrid();
                self.setHeadersColor();
                nts.uk.ui.block.clear();
            }).fail((error) => {
                nts.uk.ui.block.grayout();
                nts.uk.ui.dialog.alert({ messageId: error.messageId });
            });
        }

        convertToGridData(lstEmployee: Array<ApprovalEmployee>): Array<any> {
            let results = [];
            for (let i = 0; i < lstEmployee.length; i++) {
                let data = {};
                data['employeeCode'] = lstEmployee[i].employeeCode;
                data['employeeName'] = lstEmployee[i].employeeName;
                for (let j = 0; j < lstEmployee[i].lstStatus.length; j++) {
                    data[moment(lstEmployee[i].lstStatus[j].date).format('YYYYMMDD')] = lstEmployee[i].lstStatus[j].status;
                }
                results.push(data);
            }
            return results;
        }

        loadGrid() {
            var self = this;
            $("#approvalSttGrid").igGrid({
                primaryKey: "employeeCode",
                height: 400,
                dataSource: self.lstData,
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
                    }
                ]
            });
        }

        generateColumns() {
            var self = this;
            self.lstColumns = [
                { key: "employeeCode", width: "150px", headerText: nts.uk.resource.getText('KDW004_7'), dataType: "string" },
                { key: "employeeName", width: "150px", headerText: nts.uk.resource.getText('KDW004_8'), dataType: "string" }
            ];
            self.lstHeaderColor = [];
            let index = moment(self.datePeriod().startDate);
            while (!index.isAfter(self.datePeriod().endDate)) {
                let dayName = index.format("dd");
                let headerNoId = index.format("YYYYMMDD"+'i');
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
                        key: headerNoId, width: "30px", headerText: index.format("DD"), dataType: "string", group: [
                            { key: headerTxtId, width: "30px", headerText: dayName, dataType: "string", template: getTemplateDisplayStt(headerTxtId) }
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
    }

    export class ApprovalEmployee {
        employeeCode: string;
        employeeName: string;
        lstStatus: Array<DateApprovalStatus>;
    }

    export class DateApprovalStatus {
        date: string;
        status: number;
    }
}