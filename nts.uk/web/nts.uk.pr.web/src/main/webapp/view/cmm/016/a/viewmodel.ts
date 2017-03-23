module cmmhoa013.a.viewmodel {

    export class ScreenModel {
        //A_label_x
        label_002: KnockoutObservable<Labels>;
        label_003: KnockoutObservable<Labels>;
        label_004: KnockoutObservable<Labels>;
        label_005: KnockoutObservable<Labels>;
        label_006: KnockoutObservable<Labels>;
        //A_inp_x
        inp_002: KnockoutObservable<string>;
        inp_002_enable: KnockoutObservable<boolean>;
        inp_003: KnockoutObservable<string>;
        inp_005: KnockoutObservable<string>;
        //A_lst_001 - list history
        listbox: KnockoutObservableArray<service.model.JobHistDto>;
        itemName: KnockoutObservable<string>;
        selectedCode: KnockoutObservable<string>;
        isEnable: KnockoutObservable<boolean>;
        itemHist: KnockoutObservable<service.model.JobHistDto>;
        index_selected: KnockoutObservable<string>;
        //lst_002 - list job title 
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
        checkRegister: KnockoutObservable<string>;
        //A_BTN_007
        startDateUpdate: KnockoutObservable<string>;
        endDateUpdate: KnockoutObservable<string>;
        historyIdUpdate: KnockoutObservable<string>;
        startDateUpdateNew: KnockoutObservable<string>;
        startDatePre: KnockoutObservable<string>;
        jobHistory: KnockoutObservable<service.model.JobHistDto>;
        sDateLast: KnockoutObservable<string>;
        jobHistNew: KnockoutObservable<service.model.ListPositionDto>;
        checkCoppyJtitle: KnockoutObservable<string>;
        checkAddJhist: KnockoutObservable<string>;
        checkAddJtitle: KnockoutObservable<string>;
        checkUpdateOrDelete: KnockoutObservable<string>;
        checkUpdate: KnockoutObservable<string>;
        checkDelete: KnockoutObservable<string>;
        constructor() {
            var self = this;
            self.label_002 = ko.observable(new Labels());
            self.label_003 = ko.observable(new Labels());
            self.label_004 = ko.observable(new Labels());
            self.label_005 = ko.observable(new Labels());
            self.label_006 = ko.observable(new Labels());
            self.inp_002 = ko.observable(null);
            self.inp_002_enable = ko.observable(null);
            self.inp_003 = ko.observable(null);
            self.inp_005 = ko.observable(null);
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
            self.checkRegister = ko.observable('0');
            //A_BTN_007
            self.startDateUpdate = ko.observable(null);
            self.endDateUpdate = ko.observable(null);
            self.historyIdUpdate = ko.observable(null);
            self.startDateUpdateNew = ko.observable(null);
            self.startDatePre = ko.observable(null);
            self.jobHistory = ko.observable(null);
            self.sDateLast = ko.observable(null);
            self.jobHistNew = ko.observable(null);
            self.checkCoppyJtitle = ko.observable('0');
            self.checkAddJhist = ko.observable('0');
            self.checkAddJtitle = ko.observable('0');
            self.checkUpdateOrDelete = ko.observable('0');
            self.checkUpdate = ko.observable('0');
            self.checkDelete = ko.observable('0');
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
            self.selectedCode.subscribe((function(codeChanged) {
                if(self.checkChangeData()==false || self.checkChangeData()===undefined){
                    self.itemHist(self.findHist(codeChanged));
                    self.selectedCode(codeChanged);
                    if (self.itemHist() != null) {
                        self.index_selected(self.itemHist().historyId);
                        self.startDateUpdate(self.itemHist().startDate);
                        self.endDateUpdate(self.itemHist().endDate);
                        self.historyIdUpdate(self.itemHist().historyId);
                        //get position by historyId
                        var dfd = $.Deferred();
                        if(self.checkCoppyJtitle()=='1' && codeChanged == self.listbox()[0].startDate){
                            self.itemHist(self.findHist(self.listbox()[1].startDate));
                            self.index_selected(self.itemHist().historyId);    
                        }
                        service.getAllJobTitle(self.index_selected())
                        .done(function(position_arr: Array<service.model.ListPositionDto>){
                            self.dataSource(position_arr);
                            if (self.dataSource() === undefined || self.dataSource().length == 0) {
                                self.initRegisterPosition();
                            } else {
                                self.currentCode(self.dataSource()[0].jobCode);
                                self.inp_002(self.dataSource()[0].jobCode);
                                self.inp_003(self.dataSource()[0].jobName);
                                self.inp_005(self.dataSource()[0].memo);
                                self.selectedId(self.dataSource()[0].presenceCheckScopeSet);
                            }
                        }).fail(function(error) {
                            alert(error.message);

                        })
                    }
                }
            }));
            //inp_x - get detail position
            self.currentCode.subscribe((function(codeChanged) {
                self.currentItem(self.findJobTitle(codeChanged));
                if (self.currentItem() != null) {
                    self.inp_002(self.currentItem().jobCode);
                    self.inp_003(self.currentItem().jobName);
                    self.inp_005(self.currentItem().memo);
                    self.selectedId(self.currentItem().presenceCheckScopeSet);
                    self.inp_002_enable(false);
                }
                else self.inp_002_enable(true);
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
            service.getAllHistory().done(function(lstHistory: Array<service.model.JobHistDto>) {
                if (lstHistory === undefined || lstHistory.length === 0) {
                    self.openCDialog();
                    dfd.resolve();
                } else {
                    self.listbox(lstHistory);
                    var historyFirst = _.first(lstHistory);
                    var historyLast = _.last(lstHistory);
                    self.selectedCode(historyFirst.startDate);
                    self.startDateUpdate(historyFirst.startDate);
                    self.endDateUpdate(historyFirst.endDate);
                    self.historyIdUpdate(historyFirst.historyId);
                    self.startDateLast(historyFirst.startDate);
                    self.historyIdLast(historyFirst.historyId);
                    self.sDateLast(historyLast.startDate);//start date jHistLast
                    dfd.resolve(lstHistory);
                }
            })
            return dfd.promise();
        }

        // register position: job history and job title
        //1. check xem co can phai them ls hoac thay doi lich su hay ko
        //2. check la them hay sua title
        registerPosition(): any {
            var self = this;
            var dfd = $.Deferred<any>();
            self.startDateAddNew(nts.uk.ui.windows.getShared('cmm013C_startDateNew'));
            self.checkCoppyJtitle(nts.uk.ui.windows.getShared('cmm013C_copy'));
            //checkUpdateOrDelete: update (2)/delete(1)
            self.checkUpdateOrDelete(nts.uk.ui.windows.getShared('cmm013D_check'));
            //check add job history or edit job history
            if ((self.startDateAddNew() != null && self.startDateAddNew()!== undefined && self.startDateAddNew() != '')
                                        || (self.checkInput()==true && self.inp_002_enable()==true)) {
                //add job history
                self.addJobHistory();
            }
            else
            if(self.checkUpdateOrDelete()=='1'||self.checkUpdateOrDelete()=='2'){
                //update job history
                self.updateJobHistory();                
            }
            dfd.resolve();
            return dfd.promise();
        }

        /**
         * update JobHist
         * checkUpdateOrDelete: update (2)/delete(1)
         * check update jHist: not update (0)/update L.i (1)/update L.n (2)
         * check delete jHist: not delete (0)/delete L.i (1)/delete L.n (2)
         * 1.0: update jHist L.i and update eDate L.(i+1) = sDate L.i - 1
         * 2.0: update jHist L.n
         * 0.1: delete jHist L.1 and update eDate L.2 = eDate L.1
         */
        updateJobHistory() {
            var self = this;
            if(self.checkRegister()!='0'){
                var dfd = $.Deferred<any>();
                self.startDateUpdateNew(nts.uk.ui.windows.getShared('cmm013D_startDateUpdateNew'));
                if (self.checkUpdateOrDelete() == '1'){
                    var jobHist = new service.model.JobHistDto(self.startDateUpdate(),'', self.endDateUpdate(), self.historyIdUpdate());    
                    //delete jobHist 1
                    self.checkDelete('1');
                }else
                if(self.checkUpdateOrDelete()=='2'){
                    var jobHist = new service.model.JobHistDto(self.startDateUpdate(),self.startDateUpdateNew(), self.endDateUpdate(), self.historyIdUpdate());
                    if(self.sDateLast() == self.startDateUpdate()){
                        //update jHist last
                        self.checkUpdate('2');
                    }else{
                        self.checkUpdate('1');    
                    }
                }
                var updateHandler = new service.model.UpdateHandler(jobHist,self.checkUpdate(),self.checkDelete());
                service.updateJobHist(updateHandler).done(function() {
                    alert('update thanh cong');
                    nts.uk.ui.windows.setShared('cmm013D_startDateUpdateNew', '', true)
                    self.getAllJobHistAfterHandler();
                }).fail(function(res) {
                    dfd.reject(res);
                })
                }
        }

        /**
         * Add Job History
         * check add job history (checkAddJhist): 
         *  -not add(0)/L.1(1)/L.n(2)
         * check add job title (checkAddJtitle) :
         *  -add old(copy)(1)/add new(2)/add new or update(3)
         * +1.2: add jHist(1) and add jTitle new
         * +2.1: add jHist(n) and add jTitle copy
         * +2.2: add jHist(n) and add jTitle new
         * +0.3: add jTitle new or update jTitle into jHist
         */
        addJobHistory() {
            var self = this;
            var dfd = $.Deferred<any>();
            var jTitle = new service.model.ListPositionDto(self.inp_002(),self.inp_003(),self.selectedId(),self.inp_005());
            //set checkAddJhist        
            if(self.listbox()[0].historyId =='1'){
                //set checkAddJtitle
                if(self.checkCoppyJtitle()=='1'){
                    self.checkAddJtitle('1');//copy job title
                }else
                if(self.checkInput()==true){
                    self.checkAddJtitle('2');//job title new
                } 
                if(self.listbox().length == 1){
                    var jHist = new service.model.JobHistDto('',self.startDateAddNew(),'',self.listbox()[0].historyId);
                    self.checkAddJhist('1');//add jHist 1
                }else
                if(self.listbox().length >= 1){
                   var jHist = new service.model.JobHistDto('',self.startDateAddNew(),'',self.listbox()[1].historyId); 
                    self.checkAddJhist('2');//add jHist n
                }
            }else{
                self.checkAddJhist('0');// not add jobHist
                if(self.checkInput()==true){
                    self.checkAddJtitle('3'); //add jTitle new or update jTitle into jHist  
                    var jHist = new service.model.JobHistDto('',self.startDateAddNew(),'',self.itemHist().historyId); 
                }
            }
            
             var addHandler = new service.model.AddHandler(jHist,jTitle,self.checkAddJhist(),self.checkAddJtitle());
            if(self.checkRegister()!='0'||(self.checkAddJtitle()=='3' && self.checkAddJhist()=='0')){
                service.addJobHist(addHandler).done(function() {
                    alert('add thanh cong');
                    nts.uk.ui.windows.setShared('cmm013C_startDateNew', '', true);
                    self.checkRegister('0');
                    if(self.checkAddJtitle()=='3'){
                        self.getAllJobTitleNew();
                    }else{
                        self.getAllJobHistAfterHandler();
                    }
                }).fail(function(res) {
                    alert('fail');
                    dfd.reject(res);
                })
            }
        }
        getAllJobTitleNew(){
            var self = this;
            var dfd = $.Deferred<any>();
            self.currentCode(self.inp_002());
            service.getAllJobTitle(self.index_selected()).done(function(position_arr: Array<service.model.ListPositionDto>) {
                self.dataSource(position_arr);
                self.inp_002(self.dataSource()[0].jobCode);
                self.inp_003(self.dataSource()[0].jobName);
                self.inp_005(self.dataSource()[0].memo);
                dfd.resolve();
            }).fail(function(error) {
                alert(error.message);
            })
            dfd.resolve();
            return dfd.promise();    
        }
        /**
         * find job history is selected
         */
        findHist(value: string): any {
            let self = this;
            var itemModel = null;
            return _.find(self.listbox(), function(obj: service.model.JobHistDto) {
                return obj.startDate == value;
            })
        }
        getAllJobHistAfterHandler(): any {
            var self = this;
            var dfd = $.Deferred<any>();
            self.listbox = ko.observableArray([]);
            self.listbox([]);
            self.selectedCode = ko.observable('');
            self.selectedCode('');

            service.getAllHistory().done(function(lstHistory: Array<service.model.JobHistDto>) {
                if (lstHistory === undefined || lstHistory.length === 0) {
                    self.openCDialog();
                    dfd.resolve();
                } else {
                    self.listbox(lstHistory);
                    var historyFirst = _.first(lstHistory);
                    var historyLast = _.last(lstHistory);
                    self.selectedCode(historyFirst.startDate);
                    self.startDateUpdate(historyFirst.startDate);
                    self.endDateUpdate(historyFirst.endDate);
                    self.historyIdUpdate(historyFirst.historyId);
                    self.startDateLast(historyFirst.startDate);
                    self.historyIdLast(historyFirst.historyId);
                    self.sDateLast(historyLast.startDate);
                    dfd.resolve(lstHistory);
                }
            })
            return dfd.promise();
        }
        //A_BTN_001 dky moi
        initRegisterPosition() {
            var self = this;
            if(self.checkChangeData()==false || self.checkChangeData()===undefined){
                self.inp_002_enable(true);
                self.inp_002("");
                self.inp_003("");
                self.selectedId(1);
                self.inp_005("");
                self.currentCode(null);
            }
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
        //check thay doi du lieu
        checkChangeData():boolean{
            var self = this;
            if(self.checkRegister()=='1'){
                var retVal = confirm("Data will be changed. Do you want to register?");
                if( retVal == true ){
                    self.registerPosition();
                    return true;
                }else{
                    self.startDateAddNew('');  
                    self.checkRegister('0');
                    return false; 
                }
            }
        }
        //find position need to show detail
        findJobTitle(value: string): any {
            let self = this;
            return _.find(self.dataSource(), function(obj: service.model.ListPositionDto) {
                return obj.jobCode == value;
            })
        }

        /**
         * open sub windows add jobHist(画面ID：C) when click button A_BTN_006 (履歴追加)
         */
        openCDialog() {
            var self = this;
            if(self.checkChangeData()==false || self.checkChangeData()===undefined){
                var lstTmp = [];
                lstTmp = self.listbox();
                nts.uk.ui.windows.setShared('CMM013_historyId', self.index_selected(),true);
                nts.uk.ui.windows.setShared('CMM013_startDateLast', self.startDateLast(),true);
                nts.uk.ui.windows.sub.modal('/view/cmm/013Hoa/c/index.xhtml', { title: '画面ID：C', }).onClosed(function(): any {
                    self.startDateUpdateNew('');
                    self.startDateAddNew(nts.uk.ui.windows.getShared('cmm013C_startDateNew'));
                    self.checkCoppyJtitle(nts.uk.ui.windows.getShared('cmm013C_copy'));
                    let add = new service.model.JobHistDto('', self.startDateAddNew(), '9999-12-31', '1');
                    lstTmp.unshift(add)
                    console.log(lstTmp);
                    self.listbox = ko.observableArray([]);     
                    self.listbox([]);     
                    if(lstTmp.length>1){     
                        let startDate = new Date(self.startDateAddNew());
                        startDate.setDate(startDate.getDate() - 1);
                        let strStartDate = startDate.getFullYear() + '-' + (startDate.getMonth() + 1)+ '-' + startDate.getDate();
                        lstTmp[1].endDate = strStartDate;
                    }
                    self.listbox(lstTmp);
                    self.selectedCode(self.startDateAddNew());
                    self.checkRegister('1');
                    
                });
            }
        }


        /**
         * open sub windows update jobHist(画面ID：D) when click button A_BTN_007 (履歴編集)
         */
        openDDialog() {
            var self = this;
            if(self.checkChangeData()==false || self.checkChangeData()===undefined){
                var lstTmp = [];            
                self.startDateUpdateNew('');
                console.log(self.endDateUpdate());
                var cDelete = 0;
                if(self.startDateUpdate()==self.listbox()[0].startDate){
                    cDelete = 1;//option delete
                }else{
                    cDelete = 2;//not option delete
                }
                nts.uk.ui.windows.setShared('CMM013_delete',cDelete,true);
                nts.uk.ui.windows.setShared('CMM013_startDateUpdate', self.startDateUpdate(),true);
                nts.uk.ui.windows.setShared('CMM013_endDateUpdate', self.endDateUpdate(),true);
                nts.uk.ui.windows.sub.modal('/view/cmm/013Hoa/d/index.xhtml', { title: '画面ID：D', }).onClosed(function(){
                    var checkUpdate = nts.uk.ui.windows.getShared('cmm013D_check');
                    self.startDateUpdateNew(nts.uk.ui.windows.getShared('cmm013D_startDateUpdateNew'));
                    var i = 0;    
                    for(i=0;i<self.listbox().length;i++){
                        if(self.listbox()[i].startDate == self.startDateUpdate()){
                            break;    
                        }
                    }
                    if(checkUpdate=='1'){
                        //delete
                        var j = 0; 
                        var k=0;   
                        for(j=0;j<self.listbox().length;j++){
                            if(j!=i){
                                lstTmp[k]=self.listbox()[j];
                                k++;
                            }
                        }
                        lstTmp[i].endDate = self.endDateUpdate();
                        self.listbox = ko.observableArray([]);
                        self.listbox(lstTmp);
                        //self.selectedCode(self.listbox()[0].startDate);
                        console.log(self.listbox());
                    }else
                    if(checkUpdate=='2'){
                        //update
                        lstTmp = self.listbox();
                        lstTmp[i].startDate = self.startDateUpdateNew();
                        var eDate = new Date(self.startDateUpdateNew());
                        eDate.setDate(eDate.getDate() - 1);
                        let strEndDate = eDate.getFullYear() + '-' + (eDate.getMonth() + 1)+ '-' + eDate.getDate();
                        lstTmp[1].endDate = strEndDate;
                        self.listbox(lstTmp);
                    }    
                    self.checkRegister('1');
                });
            }
        }
        
        //delete position is selected
        deletePosition() {
            var self = this;
            if(self.checkChangeData()==false || self.checkChangeData()===undefined){
                var dfd = $.Deferred<any>();
                var item = new service.model.DeleteJobTitle(self.itemHist().historyId,self.currentItem().jobCode);
                self.index_of_itemDelete = self.dataSource().indexOf(self.currentItem());
                service.deleteJobTitle(item).done(function(res) {
                   self.getPositionList_afterDelete();
                }).fail(function(res) {
                    dfd.reject(res);
                })
            }
        }
                //get list position after Delete 1 position
        getPositionList_afterDelete(): any {
            var self = this;
            var dfd = $.Deferred<any>();
            service.getAllJobTitle(self.index_selected()).done(function(position_arr: Array<service.model.ListPositionDto>) {
                self.dataSource = ko.observableArray([]);
                self.dataSource(position_arr);
                    
                if (self.dataSource().length > 0) {
                    if (self.index_of_itemDelete === self.dataSource().length) {
                        self.currentCode(self.dataSource()[self.index_of_itemDelete - 1].jobCode)
                        self.inp_002(self.dataSource()[self.index_of_itemDelete - 1].jobCode);
                        self.inp_003(self.dataSource()[self.index_of_itemDelete - 1].jobName);
                        self.inp_005(self.dataSource()[self.index_of_itemDelete - 1].memo);
                    } else {
                        self.currentCode(self.dataSource()[self.index_of_itemDelete].jobCode)
                        self.inp_002(self.dataSource()[self.index_of_itemDelete].jobCode);
                        self.inp_003(self.dataSource()[self.index_of_itemDelete].jobName);
                        self.inp_005(self.dataSource()[self.index_of_itemDelete].memo);
                    }

                } else {
                    self.initRegisterPosition();
                }

                dfd.resolve();
            }).fail(function(error) {
                alert(error.message);
            })
            dfd.resolve();
            return dfd.promise();

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
}