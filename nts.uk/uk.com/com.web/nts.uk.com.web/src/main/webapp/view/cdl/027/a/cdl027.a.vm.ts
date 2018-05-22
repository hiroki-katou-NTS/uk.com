module nts.uk.com.view.cdl027.a.viewmodel {
    import block = nts.uk.ui.block;
    import getText = nts.uk.resource.getText;
    import alertError = nts.uk.ui.dialog.alertError;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import modal = nts.uk.ui.windows.sub.modal;

    export class ScreenModel {
        items: KnockoutObservableArray<DataCorrectionLog>;
        columnsByDate: Array<any> = [
            { headerText: getText('CDL027_7'), key: 'targetDate', dataType: 'string', width: '120px' },
            { headerText: getText('CDL027_4'), key: 'targetUser', dataType: 'string', width: '120px' },
            { headerText: getText('CDL027_8'), key: 'item', dataType: 'string', width: '120px' },
            { headerText: getText('CDL027_9'), key: 'valueBefore', dataType: 'string', width: '120px' },
            { headerText: getText('CDL027_10'), key: 'arrow', dataType: 'string', width: '20px' },
            { headerText: getText('CDL027_11'), key: 'valueAfter', dataType: 'string', width: '120px' },
            { headerText: getText('CDL027_12'), key: 'modifiedPerson', dataType: 'string', width: '120px' },
            { headerText: getText('CDL027_13'), key: 'modifiedDateTime', dataType: 'string', width: '120px' },
            { headerText: getText('CDL027_14'), key: 'correctionAttr', dataType: 'string', width: '120px' }
        ];
        columnsByIndividual: Array<any> = [
            { headerText: getText('CDL027_4'), key: 'targetUser', dataType: 'string', width: '120px' },
            { headerText: getText('CDL027_7'), key: 'targetDate', dataType: 'string', width: '120px' },
            { headerText: getText('CDL027_8'), key: 'item', dataType: 'string', width: '120px' },
            { headerText: getText('CDL027_9'), key: 'valueBefore', dataType: 'string', width: '120px' },
            { headerText: getText('CDL027_10'), key: 'arrow', dataType: 'string', width: '20px' },
            { headerText: getText('CDL027_11'), key: 'valueAfter', dataType: 'string', width: '120px' },
            { headerText: getText('CDL027_12'), key: 'modifiedPerson', dataType: 'string', width: '120px' },
            { headerText: getText('CDL027_13'), key: 'modifiedDateTime', dataType: 'string', width: '120px' },
            { headerText: getText('CDL027_14'), key: 'correctionAttr', dataType: 'string', width: '120px' }
        ];
        params: any;

        constructor() {
            var self = this;
            self.items = ko.observableArray([]);
            self.params = getShared("CDL027Params");
            switch (self.params.functionId) {
                case 2: 
                case 5:
                case 6:
                case 7:
                    break;
                default: 
                    self.columnsByDate.pop();
                    self.columnsByIndividual.pop();
                    break;
            }
        }

        startPage(): JQueryPromise<any> {
            let self = this, dfd = $.Deferred();
            block.invisible();
            service.getLogInfor(self.formatParams()).done((result: Array<any>) => {
                if (result && result.length) {
                    for (var i = 0; i < result.length; i++) {
                        let r = result[i];
                        self.items.push(new DataCorrectionLog(r.targetDate, r.targetUser, r.item, r.valueBefore, r.valueAfter, r.modifiedPerson, r.modifiedDateTime, r.correctionAttr));
                    }
                }
                self.initIGrid();
                dfd.resolve();
            }).fail((error) => {
                dfd.reject();
                alertError(error);
            }).always(() => {
                block.clear();
            });
            return dfd.promise();
        }
        
        private exportCsv(): void {
            let self = this;
            block.invisible();
            service.exportCsv(self.formatParams()).always(() => {
                block.clear();
            });
        }

        private closeDialog() {
            nts.uk.ui.windows.close();
        }
        
        private formatParams(): any {
            let self = this;
            let _params = {
                functionId: self.params.functionId, 
                listEmployeeId: self.params.listEmployeeId, 
                displayFormat: self.params.displayFormat, 
                startYmd: null, endYmd: null, 
                startYm: null, endYm: null, 
                startY: null, endY: null
            };
            
            switch (self.params.functionId) {
                case 1:
                case 2:
                    _params.startYmd = moment.utc(self.params.period.startDate, "YYYY/MM/DD").toISOString();
                    _params.endYmd = moment.utc(self.params.period.endDate, "YYYY/MM/DD").toISOString();
                    return _params;
                case 3:
                case 4:
                case 5:
                case 6:
                case 8:
                case 9:
                    _params.startYm = parseInt(moment.utc(self.params.period.startDate, "YYYY/MM").format("YYYYMM"), 10);
                    _params.endYm = parseInt(moment.utc(self.params.period.endDate, "YYYY/MM").format("YYYYMM"), 10);
                    return _params;
                default:
                    _params.startY = parseInt(self.params.period.startDate, 10);
                    _params.endY = parseInt(self.params.period.endDate, 10);
                    return _params;
            }
        }

        private initIGrid() {
            let self = this;
            $("#list").igGrid({
                height: '400px',
                width: '1000px',
                dataSource: self.items(),
                columns: self.params.displayFormat == DISPLAY_FORMAT.BY_DATE ? self.columnsByDate : self.columnsByIndividual,
                features: [
                    {
                        name: 'Paging',
                        type: "local",
                        pageSize: 15,
                        currentPageIndex: 0,
                        showPageSizeDropDown: true,
                        pageCountLimit: 20
                    },
                    {
                        name: "Filtering",
                        type: "local",
                        mode: "simple"
                    }
                ]
            });
        }
        
    }

    class DataCorrectionLog {
        targetDate: string;
        targetUser: string;
        item: string;
        valueBefore: string;
        arrow: string = getText('CDL027_10');
        valueAfter: string;
        modifiedPerson: string;
        modifiedDateTime: string;
        correctionAttr: string;

        constructor(targetDate, targetUser, item, valueBefore, valueAfter, modifiedPerson, modifiedDateTime, correctionAttr) {
            this.targetDate = targetDate;
            this.targetUser = targetUser;
            this.item = item;
            this.valueBefore = valueBefore;
            this.valueAfter = valueAfter;
            this.modifiedPerson = modifiedPerson;
            this.modifiedDateTime = modifiedDateTime;
            this.correctionAttr = correctionAttr;
        }
    }

    enum DISPLAY_FORMAT {
        BY_DATE = 0,
        BY_INDIVIDUAL = 1
    }

    enum FUNCTION_ID {
        Schedule = 1,
        Daily = 2,
        Monthly = 3,
        Any_period = 4,
        Salary = 5,
        Bonus = 6,
        Year_end_adjustment = 7,
        Monthly_calculation = 8,
        Raising_rising_back = 9
    }

    enum CORRECTION_ATTR {
    
    }

    enum PERIOD_TYPE {
        Y = 0,
        YM = 1,
        YMD = 2
    }

}

