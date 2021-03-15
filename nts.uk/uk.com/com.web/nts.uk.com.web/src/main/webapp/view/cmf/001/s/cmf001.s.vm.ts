let moment: any = window['moment'];

module nts.uk.com.view.cmf001.s.viewmodel {
    import close = nts.uk.ui.windows.close;
    import model = cmf001.share.model;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import alertError = nts.uk.ui.dialog.alertError;   
    import block = nts.uk.ui.block; 
    import getText = nts.uk.resource.getText;
    
    export class ScreenModel {
        dateFrom: KnockoutObservable<Date>;
        dateTo: KnockoutObservable<Date>;
        conditionList: KnockoutObservableArray<model.ImExConditonSetting>;
        executeResultLogList: KnockoutObservableArray<model.ImExExecuteResultLog> = ko.observableArray([]);
        currentConditionCode: KnockoutObservable<string> = ko.observable('');
        numberOfList: KnockoutObservable<number> = ko.observable(8);
        totalPage: KnockoutObservable<number> = ko.observable(2);
        currentPage: KnockoutObservable<number> = ko.observable(1);
        totalCondList: Array<any> = [];
        executeResultLog: KnockoutObservable<model.ImExExecuteResultLog> = ko.observable(null);
        
        columnHeadersRes: any[] = [
            { headerText: getText('CMF001_542'), key: 'displayNo', width: '40px' },
            { headerText: getText('会社コード'), key: 'companyCd', width: '80px', dataType: 'string', ntsControl: "Label" },
            { headerText: getText('CMF001_544'), key: 'userCodeName', width: '270px', dataType: 'string', ntsControl: "Label" },
            { headerText: getText('CMF001_545'), key: 'processStartDatetime', width: '240px', dataType: 'string', ntsControl: "Label" },
            { headerText: getText('CMF001_547'), key: 'executeForm', width: '120px', dataType: 'string', ntsControl: "Label" },
            { headerText: getText('CMF001_548'), key: 'targetCount', width: '120px', dataType: 'number', ntsControl: "Label" },
            { headerText: getText('CMF001_549'), key: 'errorCount', width: '120px', dataType: 'number', ntsControl: "Label" },
            { headerText: getText('CMF001_550'), key: '', width: '80px', dataType: 'string', ntsControl: "Button" },
            { headerText: getText('CMF001_551'), key: 'fileName', width: '270px', dataType: 'string', ntsControl: "Label" },
        ];
        
        states: State[] = [];
        dataGrid: any;
        storageSize: KnockoutObservable<number> = ko.observable(0);
        
        constructor() {
            var self = this;
            self.dateFrom = ko.observable(moment().day(moment().day()-2).toDate());
            self.dateTo = ko.observable(moment().day(moment().day()+1).toDate());
            self.conditionList = ko.observableArray([]);
                        
            self.currentConditionCode.subscribe((condCode) => {
                self.executeResultLogList([]);
                if(condCode){
                    let listLog = _.filter(self.totalCondList, item => {return item.conditionSetCd === condCode});
                    
                    for( let i = 0; i < listLog.length; i++){
                        self.executeResultLogList().push(new model.ImExExecuteResultLog(i+1, listLog[i].companyCd, listLog[i].userCode, listLog[i].userName, 
                                                        listLog[i].processStartDatetime, listLog[i].executeForm, listLog[i].targetCount, 
                                                        listLog[i].errorCount, listLog[i].fileName, listLog[i].externalProcessId,
                                                        listLog[i].conditionSetName));
                    }
                    self.loadDataGrid();
                }else{
                    for( let i = 0; i < self.totalCondList.length; i++){
                        self.executeResultLogList().push(new model.ImExExecuteResultLog(i+1, self.totalCondList[i].companyCd, self.totalCondList[i].userCode, self.totalCondList[i].userName, 
                                                        self.totalCondList[i].processStartDatetime, self.totalCondList[i].executeForm, 
                                                        self.totalCondList[i].targetCount, self.totalCondList[i].errorCount,
                                                        self.totalCondList[i].fileName, self.totalCondList[i].externalProcessId,
                                                        self.totalCondList[i].conditionSetName));
                    }
                    self.loadDataGrid();
                }
            });

        }      
        
        start(): JQueryPromise<any> {
            let self = this;
            var dfd = $.Deferred();

            let time = {
                start:  moment.utc(self.dateFrom()).format(),
                end: moment.utc(self.dateTo()).format()
            }
            let listCondition = [new model.ImExConditonSetting('', '全て')];

            service.getLogResultsList(time).done(function(data: Array<any>) {
                
                if (data && data.length) {
                    self.totalCondList = data;
                    
                    _.forEach(data, item => {
                        listCondition.push(new model.ImExConditonSetting(item.conditionSetCd, item.conditionSetName));
                    });
                    _.sortBy(listCondition, [function(o) { return o.conditionCode; }]);
                    
                    self.conditionList(_.uniqWith(listCondition, _.isEqual));
                    self.currentConditionCode(self.conditionList()[0].dispConditionCode);
                    self.currentConditionCode.valueHasMutated();
                }
                self.loadDataGrid();
                dfd.resolve();
            }).fail(function(error) {
                alertError(error);
                dfd.reject();
            }).always(() => {
                block.clear();
            });
            return dfd.promise();
        }
        
        
        public loadDataGrid() {
            const vm = this;
            
            if ($("#S3_1").data("mGrid")) {
                $("#S3_1").mGrid("destroy");
            }
            vm.states = [];
            
            _.forEach(vm.executeResultLogList(), item => {
                vm.states.push(new State(item.displayNo.toString(), '', ['hidden-item']));
            });            
            
             vm.dataGrid = new (nts.uk.ui as any).mgrid.MGrid($("#S3_1")[0], {
                height:  '800px',
                subHeight: '485px',
//                subWidth:  '285px',
                headerHeight: "40px",
                autoFitWindow: true,
                dataSource: vm.executeResultLogList(),
                primaryKey: 'displayNo',
                primaryKeyDataType: 'number',
                value: vm.executeResultLog(),
                rowVirtualization: true,
                virtualization: true,
                virtualizationMode: 'continuous',
                enter: 'right',
                useOptions: true,
                idGen: (id: any) => id + "_" + nts.uk.util.randomId(),
                columns: vm.columnHeadersRes,
                virtualrecordsrender: (e: any, args: any) => vm.updateGridUI(),
                features: [
                    {
                        name: 'Paging',
                        pageSize: 25,
                        currentPageIndex: 0,
                        loaded: () => { }
                    },
                    {
                        name: 'ColumnFixing', fixingDirection: 'left',
                        showFixButtons: true,
                        columnSettings: [
                            { columnKey: 'displayNo', isFixed: true }
                        ]
                    },
                    {
                        name: 'Resizing'
                    },
                    {
                        name: 'CellStyles',
                        states: vm.states
                    },                    
                ],
                ntsControls: [
                    { name: 'Button', controlType: 'Button', text: getText('CMF001_552'), enable: true, click: (i: any) => vm.openRdialog(i) }
                ]
            }).create();

            $("#S3_1").ready(function() {
                vm.updateGridUI();
                vm.updateStorageLabelPos();
            });

            $(window).on('resize', () => {
                vm.updateStorageLabelPos();
            })

            $("#S3_1 .mgrid-free").scroll(function() {
                vm.updateGridUI();
            });
        }
        
        // S4_10
        private openRdialog(value: any){
            var self = this;
            let conditionShare ={
                code: self.currentConditionCode(),
                imexProcessId: value.externalProcessId,
                nameSetting: value.dispConditionName
            }
            setShared("CMF001-R", conditionShare);
            nts.uk.ui.windows.sub.modal("/view/cmf/001/r/index.xhtml");
        }
        
        private updateGridUI() {
            $("#S3_1 .mcell").addClass("halign-center");
        }
        
        private updateStorageLabelPos() {
            let totalHeight = 0;
            $("#S3_1 > div").each(function(index) {
                if (index % 2)
                    totalHeight += $(this).height();
            });
            $(".storage-size").css("margin-top", totalHeight + 75);
        }
        
        private filter(){
            let self = this;
            self.start();
        }
        
    }//end screenModel
    
    export class State {
        rowId: string;
        columnKey: string;
        state: any[];

        constructor(rowId: string, columnKey: string, state: any[]) {
            this.rowId = rowId;
            this.columnKey = columnKey;
            this.state = state;
        }
    }
    
}//end module