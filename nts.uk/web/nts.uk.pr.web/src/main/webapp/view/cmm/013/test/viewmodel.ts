module cmmhoa013.a.viewmodel {

    export class ScreenModel {
        //A_label_x
        label_002: KnockoutObservable<Labels>;
        label_003: KnockoutObservable<Labels>;
        label_004: KnockoutObservable<Labels>;
        label_005: KnockoutObservable<Labels>;
        label_006: KnockoutObservable<Labels>;
        test: KnockoutObservable<string>;
        
        //A_inp_x
        inp_002: KnockoutObservable<string>;
        inp_002_enable: KnockoutObservable<boolean>;
        inp_003: KnockoutObservable<string>;
        inp_005: KnockoutObservable<string>;
        
        sel_0021: KnockoutObservable<SwitchButton>;
        sel_0022: KnockoutObservable<SwitchButton>;
        sel_0023: KnockoutObservable<SwitchButton>;
        sel_0024: KnockoutObservable<SwitchButton>;
        
        //A_lst_001 - list history
        listbox: KnockoutObservableArray<service.model.JobHistDto>;
        itemName: KnockoutObservable<string>;
        selectedCode: KnockoutObservable<any>
        isEnable: KnockoutObservable<boolean>;
        itemHist: KnockoutObservable<service.model.JobHistDto>;
        index_selected: KnockoutObservable<string>;
        
        //lst_002 - list position 
        currentCode: KnockoutObservable<any>;        
        dataSource: KnockoutObservableArray<service.model.ListPositionDto>;
        columns: KnockoutObservableArray<nts.uk.ui.NtsGridListColumn>;       
                
        currentCodeList: KnockoutObservableArray<any>;
        currentItem: KnockoutObservable<service.model.ListPositionDto>;
        index_of_itemDelete: any;
        
        //A_SEL_001
        itemList: KnockoutObservableArray<any>;
        selectedId: KnockoutObservable<number>;
        enable: KnockoutObservable<boolean>;
        
        //A_BTN_006
        startDateLast: KnockoutObservable<string>;
        historyIdLast: KnockoutObservable<string>;
        length: KnockoutObservable<number>;
        startDateAddNew: KnockoutObservable<string>;
        //A_BTN_007
        startDateUpdate: KnockoutObservable<string>;
        endDateUpdate: KnockoutObservable<string>;
        historyIdUpdate: KnockoutObservable<string>;
        startDateUpdateNew: KnockoutObservable<string>;
        startDatePre: KnockoutObservable<string>;
        jobHistory: KnockoutObservable<service.model.JobHistDto>;
        constructor() {
            var self = this;
            
            self.label_002 = ko.observable(new Labels());
            self.label_003 = ko.observable(new Labels());
            self.label_004 = ko.observable(new Labels());
            self.label_005 = ko.observable(new Labels());
            self.label_006 = ko.observable(new Labels());
            
            self.inp_002 = ko.observable(null);
            self.inp_002_enable = ko.observable(false);
            self.inp_003 = ko.observable(null);
            self.inp_005 = ko.observable(null);
            self.sel_0021 = ko.observable(new SwitchButton);
            self.sel_0022 = ko.observable(new SwitchButton);
            self.sel_0023 = ko.observable(new SwitchButton);
            self.sel_0024 = ko.observable(new SwitchButton);
            self.test = ko.observable('2');
            
            //lst_001
            self.listbox = ko.observableArray([]);
            self.selectedCode = ko.observable(''); 
            self.isEnable = ko.observable(true);    
            self.itemHist = ko.observable(null);
            self.itemName = ko.observable('');
            self.index_selected = ko.observable('');
            
            //A_BTN_006
            self.startDateLast = ko.observable(null);
            self.historyIdLast = ko.observable(null);
            self.length = ko.observable(0);
            self.startDateAddNew = ko.observable("");
            //A_BTN_007
            self.startDateUpdate = ko.observable(null);
            self.endDateUpdate = ko.observable(null);
            self.historyIdUpdate = ko.observable(null);
            self.startDateUpdateNew = ko.observable(null);
            self.startDatePre = ko.observable(null);
            self.jobHistory = ko.observable(null);
            //lst_002
            self.dataSource = ko.observableArray([]);
            self.currentItem = ko.observable(null);
            self.itemName = ko.observable('');
            self.currentCode = ko.observable();           
            self.currentCodeList = ko.observableArray([]);
            self.columns = ko.observableArray([
                { headerText: 'コード', key: 'jobCode', width: 80 },
                { headerText: '名称', key: 'jobName', width: 100 }

            ]);
            
            //A_SEL_001
            self.itemList = ko.observableArray([
                new BoxModel(0, '全員参照可能'),
                new BoxModel(1, '全員参照不可'),
                new BoxModel(2, 'ロール毎に設定')
            ]);
            self.selectedId = ko.observable(0);
            self.enable = ko.observable(true);
            /**
             * get job history selected
             */
            self.selectedCode.subscribe((function(codeChanged){
            self.itemHist(self.findHist(codeChanged));
            if (self.itemHist() != null) {
                self.index_selected(self.itemHist().historyId);
                self.startDateUpdate(self.itemHist().startDate);
                self.endDateUpdate(self.itemHist().endDate);
                self.historyIdUpdate(self.itemHist().historyId);
             }
            })); 
        }
        /**
         * start page
         * get all jobHist
         * jobHist entity: open dialog C (add job history)
         */
        startPage(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            service.getAllHistory().done(function(lstHistory: Array<service.model.JobHistDto>){
                if(lstHistory === undefined || lstHistory.length === 0){
                    self.openCDialog();
                }else{
                self.listbox(lstHistory);

                var historyFirst = _.first(lstHistory);
                self.selectedCode(historyFirst.startDate);
                self.startDateUpdate(historyFirst.startDate);
                self.endDateUpdate(historyFirst.endDate);
                self.historyIdUpdate(historyFirst.historyId);
                self.startDateLast(historyFirst.startDate);
                self.historyIdLast(historyFirst.historyId);
                dfd.resolve(lstHistory);
                    }
            })                      
            return dfd.promise();           
        }

     
        //delete position is selected
        deletePosition() {
            alert("vui vai");
        }
        // register position without change history
        //1. check xem co can phai them ls hoac thay doi lich su hay ko
        //2. check la them hay sua title
        registerPosition(): any {

            var self = this;
            var dfd = $.Deferred<any>();
            /**
             * add job history
             */
            alert('bat dau xu ly register');
            self.startDateAddNew(nts.uk.ui.windows.getShared('cmm013C_startDateNew'));
            self.startDateUpdateNew(nts.uk.ui.windows.getShared('cmm013D_startDateUpdateNew'));
            var checkUpdate = nts.uk.ui.windows.getShared('cmm013D_check');
            var checkAddHist = nts.uk.ui.windows.getShared('cmm013C_copy');
            //check add job history
            if(self.startDateAddNew() == null||self.startDateAddNew() === undefined){
                //check update job history
                if(self.startDateUpdateNew() == null || self.startDateUpdateNew()==''){
                    if(checkUpdate==1){
                        var jobHistDelete = new service.model.JobHistDto(self.startDateUpdate(),'',self.endDateUpdate(),self.historyIdUpdate());
                        var dfd = $.Deferred<any>();
                        service.deleteJobHist(jobHistDelete).done(function(res){
                            alert('xoa thanh cong');
                            checkUpdate==0;
                            self.getAllJobHistAfterHandler();
                        }).fail(function(res){
                            dfd.reject(res);
                        })
                    }else 
                        return;    
                }
                else{
                    //update job history (update start date of jobHist current and update end date of jobHist previous)
                    var jobHistUpdateSdate = new service.model.JobHistDto(self.startDateUpdate(),self.startDateUpdateNew(),self.endDateUpdate(),self.historyIdUpdate());
                    self.updateStartDate(jobHistUpdateSdate);
                    self.getAllJobHistAfterHandler();
                        
                }
            }
            else {     
                //add job history        
                if(self.listbox() ===undefined || self.listbox()==null|| self.listbox.length ==0){
                    //add lan dau   
                    var jobHistNew = new service.model.JobHistDto('1',self.startDateAddNew(),'','');
                }else{
                    //add lan n
                    var jobHistNew = new service.model.JobHistDto('0',self.startDateAddNew(),'','');
                }
                service.addJobHist(jobHistNew).done(function() {
                    self.getAllJobHistAfterHandler();
                    alert('add thanh cong');
                    }).fail(function(res) {
                        alert('fail');
                        dfd.reject(res);
                    })
                //update endDate after add job history new
                var jobHist = new service.model.JobHistDto('1',self.startDateLast(),self.startDateAddNew(),self.historyIdLast());
                self.updateEndDate(jobHist);
                self.getAllJobHistAfterHandler();
            }          

            dfd.resolve();
            return dfd.promise();
        }
        /**
         * update end date of job history before job history new
         */
        updateEndDate(jobHist: service.model.JobHistDto){
            var self = this;
            var dfd = $.Deferred<any>();
            service.updateJobHist(jobHist).done(function(){
                self.startDateUpdateNew(null);
                self.startDateAddNew(null);
                console.log('update successful');
                self.getAllJobHistAfterHandler();
            }).fail(function(res){
                dfd.reject(res);    
            })
        }
         /**
         * update start date of job history before job history new
         */
        updateStartDate(jobHist: service.model.JobHistDto){
            var self = this;
            var dfd = $.Deferred<any>();
            service.updateJobHist(jobHist).done(function(){
                self.getAllJobHistAfterHandler();
            }).fail(function(res){
                dfd.reject(res);    
            })
        }

        /**
         * find job history is selected
         */
        findHist(value: string): any {
            let self = this;
            var itemModel = null;
            _.find(self.listbox(), function(obj: service.model.historyDto) {
                if (obj.startDate == value) {
                    itemModel = obj;
                }
            })
            return itemModel;
        }
        getAllJobHistAfterHandler(): any{
            var self = this;
            var dfd = $.Deferred<any>();
            self.selectedCode('');
            self.listbox([]);
            service.getAllHistory().done(function(lstHistory: Array<service.model.JobHistDto>){
                if(lstHistory === undefined || lstHistory.length === 0)
                    return;
                self.listbox(lstHistory);
                _.forEach(lstHistory, function(strHistory){
                    self.listbox.push(strHistory);
                })
                var historyFirst = _.first(lstHistory);
                self.selectedCode(historyFirst.startDate);
                self.startDateUpdate(historyFirst.startDate);
                self.endDateUpdate(historyFirst.endDate);
                self.historyIdUpdate(historyFirst.historyId);
                self.startDateLast(historyFirst.startDate);
                self.historyIdLast(historyFirst.historyId);
                dfd.resolve(lstHistory);
            })   
            return dfd.promise();    
        }
        
        /**
         * check input: A_INP_002(jobCode) and A_INP_003(jobName)
         */
        checkInput(): boolean {
            var self = this;
            if (self.inp_002() == '' || self.inp_003() == '') {
                alert("nhap day du thong tin");
                return false;
            } else {
                return true;
            }
        }
        
        /**
         * open sub windows add jobHist(画面ID：C) when click button A_BTN_006 (履歴追加)
         */
        openCDialog() {
            var self = this;
            nts.uk.ui.windows.setShared('CMM013_historyId', self.index_selected());
            nts.uk.ui.windows.setShared('CMM013_startDateLast',self.startDateLast());
            nts.uk.ui.windows.sub.modal('/view/cmm/013/c/index.xhtml', { title: '画面ID：C', });
        }
        
        /**
         * open sub windows update jobHist(画面ID：D) when click button A_BTN_007 (履歴編集)
         */
        openDDialog() {
            var self = this;
            console.log(self.endDateUpdate());
            nts.uk.ui.windows.setShared('CMM013_startDateUpdate', self.startDateUpdate());
            nts.uk.ui.windows.setShared('CMM013_endDateUpdate', self.endDateUpdate());
            nts.uk.ui.windows.sub.modal('/view/cmm/013/d/index.xhtml', { title: '画面ID：D', });
        }
    
    }
    export class Labels {
        constraint: string = 'LayoutCode';
        inline: KnockoutObservable<boolean>;
        required: KnockoutObservable<boolean>;
        enable: KnockoutObservable<boolean>;
        constructor() {
            var self = this;
            self.inline = ko.observable(true);
            self.required = ko.observable(true);
            self.enable = ko.observable(true);
        }
    }


   export class BoxModel {
        id: number;
        name: string;
        constructor(id, name) {
            var self = this;
            self.id = id;
            self.name = name;
        }
    }

    export class SwitchButton {
        roundingRules: KnockoutObservableArray<any>;
        selectedRuleCode: any;

        constructor() {
            var self = this;
            self.roundingRules = ko.observableArray([
                { code: '1', name: '可能' },
                { code: '2', name: '不可' },
            ]);
            self.selectedRuleCode = ko.observable(1);
        }
    }


}