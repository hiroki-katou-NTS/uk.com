module nts.uk.at.view.ksc001.g {
    import blockUI = nts.uk.ui.block;
    export module viewmodel {
        export class ScreenModel {
            enable: KnockoutObservable<boolean>;
            required: KnockoutObservable<boolean>;
            dateValue: KnockoutObservable<any>;
            startDateString: KnockoutObservable<string>;
            endDateString: KnockoutObservable<string>;
            items: KnockoutObservableArray<GridItem>;
            constructor() {
                let self = this;
                self.enable = ko.observable(true);
                self.required = ko.observable(true);

                self.startDateString = ko.observable("");
                self.endDateString = ko.observable("");
                self.dateValue = ko.observable({
                    //get previous 1 year
                    startDate: moment.utc(moment().subtract(1, 'years').add(1, 'days').format('YYYYMMDD')).toISOString(),
                    endDate: moment.utc(moment().format('YYYYMMDD')).add(1, 'days').subtract(1, 'seconds').toISOString()
                });

                self.startDateString.subscribe(function(value) {
                    self.dateValue().startDate = value;
                    self.dateValue.valueHasMutated();
                });

                self.endDateString.subscribe(function(value) {
                    self.dateValue().endDate = value;
                    self.dateValue.valueHasMutated();
                });
                self.items = ko.observableArray([]);
                
                window.onresize = function(event) {
                	$(".ksc001-panel").height(window.innerHeight - 250);
                	$("#gridTable_displayContainer").height(window.innerHeight - 345);
                	$("#gridTable_scrollContainer").height(window.innerHeight - 345);
                };
            }
            
            /**
             * get data on start page
             */
            startPage(): JQueryPromise<any> {
                let self = this;
                let dfd = $.Deferred();
                let data = {
                    startDate: new Date(self.dateValue().startDate),
                    endDate: new Date(self.dateValue().endDate)
                };
                blockUI.invisible();
                service.findExecutionList(data).done(function(data: any) {
                    self.pushDataToList(data);
                    self.setHeight();
                    dfd.resolve();
                    blockUI.clear();
                });
                return dfd.promise();
            }
            
            /**
             * Init list data
             */
            pushDataToList(data: Array<any>) {
                var self = this;
                self.items([]);
                if (data && data.length > 0) {
                    data.forEach(function(item, index) {
                        let dateString = moment.utc(item.executionDateTime.executionStartDate).format('YYYY/MM/DD').toString();
                        self.items.push(new GridItem(dateString, item.employeeCode, item.employeeName, item.period, item.completionStatus, item.executionId));
                    });
                }
            }
            
            /**
             * search by date
             */
            private search() {
                if (!nts.uk.ui.errors.hasError()) {
                    var self = this;
                    var date = {
                        startDate: moment.utc(self.dateValue().startDate, 'YYYY/MM/DD').toISOString(),
                        endDate: moment.utc(self.dateValue().endDate, 'YYYY/MM/DD')
                            .add(1, 'days')
                            .subtract(1, 'seconds')
                            .toISOString()
                    };
                    //block UI
                    blockUI.invisible();
                    service.findExecutionList(date).done(function(data: any) {
                        self.pushDataToList(data);
                        self.loadGridTable(self);
                        //clear block
                        blockUI.clear();
                    });
                }
            }

            /**
             * Load data to grid table
             */
            loadGridTable(screenModel: ScreenModel) {
                var self = this;
                $("#gridTable").ntsGrid({
                    width: null,
                    height: window.innerHeight - 285 + 'px',
                    dataSource: screenModel.items(),
                    primaryKey: 'id',
                    virtualization: true,
                    virtualizationMode: 'continuous',
                    columns: [
                        { headerText: '', key: 'id', dataType: 'string', width: '150px', hidden: true },
                        { headerText: nts.uk.resource.getText('KSC001_66'), key: 'exeDay', dataType: 'string', width: '100px' },
                        { headerText: nts.uk.resource.getText('KSC001_67'), key: 'exeEmployeeCode', dataType: 'string', width: '150px' },
                        { headerText: nts.uk.resource.getText('KSC001_68'), key: 'exeEmployeeName', dataType: 'string', width: '200px' },
                        { headerText: nts.uk.resource.getText('KSC001_31'), key: 'targetPeriod', dataType: 'string', width: '220px' },
                        { headerText: nts.uk.resource.getText('KSC001_69'), key: 'status', dataType: 'string', width: '150px' },
                        { headerText: nts.uk.resource.getText('KSC001_70'), key: 'exeId', dataType: 'string', width: '100px', columnCssClass: 'align_detail_btn', unbound: true, ntsControl: 'Button' }
                    ],
                    features: [],
                    ntsFeatures: [{ name: 'CopyPaste' }],
                    ntsControls: [
                        {
                            name: 'Button', text: nts.uk.resource.getText('KSC001_71'), click: function(data: any) {
                                nts.uk.ui.windows.setShared("executionData",{executionId: data.id});
                                nts.uk.ui.windows.sub.modal("/view/ksc/001/h/index.xhtml").onClosed(() => {
                                });
                            },
                            controlType: 'Button'
                        }
                    ]
                });
                $("#fixed-table").ntsFixedTable({ height: 430 });
            }
            
            setHeight(): void {
            	$(".ksc001-panel").width(window.innerHeight - 210);
            }
        }

        export class GridItem {
            id: string;
            exeDay: string;
            exeEmployeeCode: string;
            exeEmployeeName: string;
            targetPeriod: string;
            status: string;
            exeId: string;

            constructor(exeDay: any, exeEmployeeCode: string, exeEmployeeName: string, targetPeriod: any, status: string, exeId: string) {
                this.id = exeId;
                this.exeDay = exeDay;
                this.exeEmployeeCode = exeEmployeeCode;
                this.exeEmployeeName = exeEmployeeName;
                this.targetPeriod = nts.uk.resource.getText("KSC001_46", [targetPeriod.startDate, targetPeriod.endDate]);
                this.status = status;
                this.exeId = exeId;
            }
        }
    }
}