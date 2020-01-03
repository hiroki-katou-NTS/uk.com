module nts.uk.com.view.cmm018.k.viewmodel{
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import windows = nts.uk.ui.windows;
    import resource = nts.uk.resource;
    import vmbase = cmm018.shr.vmbase;
    import service = cmm018.k.service;
    import block = nts.uk.ui.block;
    import error = nts.uk.ui.dialog.alertError;
    export class ScreenModel{
        //==========
        lstJob: KnockoutObservableArray<any> = ko.observableArray([]);
        
        //承認形態
        formSetting: KnockoutObservableArray<ButtonSelect> = ko.observableArray([
                new ButtonSelect(1, resource.getText('CMM018_63')),//全員承認
                new ButtonSelect(2, resource.getText('CMM018_66'))//誰か一人
                ]);
        //CA NHAN
        columns: KnockoutObservableArray<any> = ko.observableArray([
                    { headerText: 'id', prop: 'id', width: '0px', hidden: true },
                    { headerText: resource.getText('CMM018_69'), prop: 'code', width: '60px' },
                    { headerText: resource.getText('CMM018_70'), prop: 'name', width: '175px' }
                ]);
        selectFormSet: KnockoutObservable<number> = ko.observable(1);
        //GROUP
        columns2: KnockoutObservableArray<any> = ko.observableArray([
                    { headerText: resource.getText('CMM018_69'), prop: 'code', width: '70px' },
                    { headerText: resource.getText('CMM018_70'), prop: 'name', width: '310px' }
                ]);
        selectFormSetG: KnockoutObservable<number> = ko.observable(1);
        lstJobG: KnockoutObservableArray<any> = ko.observableArray([]);
        lstGroup: KnockoutObservableArray<any> = ko.observableArray([]);
        lstGroup1: KnockoutObservableArray<any> = ko.observableArray([]);
        lstGroup2: KnockoutObservableArray<any> = ko.observableArray([]);
        selectG: KnockoutObservable<string> = ko.observable("");
        selectG1: KnockoutObservable<string> = ko.observable("");
        selectG2: KnockoutObservable<string> = ko.observable("");
        //CHI DINH
        columns3: KnockoutObservableArray<any> = ko.observableArray([
                    { headerText: resource.getText('CMM018_69'), prop: 'code', width: '60px' },
                    { headerText: resource.getText('CMM018_70'), prop: 'name', width: '185px' }
                ]);
        selectFormSetS: KnockoutObservable<number> = ko.observable(1);
        lstJobGS: KnockoutObservableArray<any> = ko.observableArray([]);
        lstSpec1: KnockoutObservableArray<any> = ko.observableArray([]);
        lstSpec2: KnockoutObservableArray<any> = ko.observableArray([]);
        selectSpec1: KnockoutObservable<string> = ko.observable("");
        selectSpec2: KnockoutObservable<string> = ko.observable("");
        //==========
        
        
        //enable list workplace
        enableListWp: KnockoutObservable<boolean> = ko.observable(true);
        appType: KnockoutObservable<String> = ko.observable('');
        standardDate: KnockoutObservable<Date> = ko.observable(moment(new Date()).toDate());
        //承認者指定種類
        typeSetting: KnockoutObservableArray<ButtonSelect> = ko.observableArray([]);
        selectTypeSet: KnockoutObservable<number> = ko.observable(null);

        
        currentCalendarWorkPlace: KnockoutObservableArray<SimpleObject> = ko.observableArray([]);
        employeeList: KnockoutObservableArray<vmbase.ApproverDtoK> = ko.observableArray([]);
       
        multiSelectedWorkplaceId: KnockoutObservableArray<string> = ko.observableArray([]);
        approverList : KnockoutObservableArray<vmbase.ApproverDtoK> = ko.observableArray([]);
        currentApproveCodeLst: KnockoutObservableArray<any> = ko.observableArray([]);
        currentEmployeeCodeLst: KnockoutObservableArray<any> = ko.observableArray([]);
        items: KnockoutObservableArray<any> = ko.observableArray([]);
        //確定者の選択
        itemListCbb:KnockoutObservableArray<any> = ko.observableArray([]);
        cbbEnable: KnockoutObservable<boolean> = ko.observable(false);
        selectedCbbCode : KnockoutObservable<string> = ko.observable("");
        //↓ & ↑
        currentCodeListSwap: KnockoutObservableArray<any> = ko.observableArray([]);
        //→ & ←
        itemsSwapLR:  KnockoutObservableArray<any> = ko.observableArray([]);
        currentCodeListSwapLR:  KnockoutObservableArray<any> = ko.observableArray([]);
        confirm: string = '';
        //職場リスト
        treeGrid: ITreeGrid = {
                treeType: 1,
                selectType: 4,
                isDialog: true,
                isMultiSelect: false,
                isShowAlreadySet: false,
                isShowSelectButton: true,
                baseDate: ko.observable(this.standardDate()),
                selectedWorkplaceId: ko.observableArray(_.map(this.currentCalendarWorkPlace(), o => o.key)),
                alreadySettingList: ko.observableArray([]),
                systemType : 2,
                width: 310
        };
        //選択可能な職位一覧
        //承認者の登録(個人別)
        personTab : number = 2;
        //設定種類
        personSetting: number = 0; //個人設定
        
        lstTmp: KnockoutObservableArray<any> = ko.observableArray([]);
        constructor(){
            var self = this;
            //設定対象: get param from main: approverInfor(id & approvalAtr)
            let data: any = nts.uk.ui.windows.getShared('CMM018K_PARAM');
            let specLabel = data.systemAtr == 0 ? resource.getText('CMM018_100') : resource.getText('CMM018_101'); 
            //承認者指定種類
            self.typeSetting([new ButtonSelect(1, resource.getText('CMM018_57')),
                                new ButtonSelect(2, specLabel),
                                new ButtonSelect(0, resource.getText('CMM018_56'))]);
            self.selectTypeSet(data.typeSetting);
            self.confirm = data.confirmedPerson;
            //change 承認形態
            self.selectFormSet.subscribe(function(newValues){
                //承認形態が誰か一人を選択する場合
                if(newValues === vmbase.ApprovalForm.SINGLE_APPROVED){
                    self.cbbEnable(true);
                }else{
                    //承認形態が全員承認を選択する場合
                    self.cbbEnable(false);    
                }
            });
            self.getData();
            if(data !== undefined){
                //設定する対象申請名
                self.appType(data.appTypeName);
                //承認形態
                if(data.typeSetting == TypeSet.PERSON){
                    self.selectFormSet(data.formSetting);
                }else if(data.typeSetting == TypeSet.GROUP){
                    self.selectFormSetG(data.formSetting);
                }else{
                    self.selectFormSetS(data.formSetting);
                }
                
                //承認者の登録(個人別): 非表示
                if(data.tab === self.personTab){
                    $('#typeSetting').hide();
                }else{
                    $('#typeSetting').show();
                }
            }
            //基準日
            this.standardDate(moment(new Date()).toDate());
            
            //Subscribe PERSON - CHI DINH - GROUP
            self.selectTypeSet.subscribe(function(newValue){
                if(self.checkEmpty(newValue)) return;
                self.bindData(newValue);
            });
            
            //職場リスト            
            self.treeGrid.selectedWorkplaceId.subscribe(function(newValues){
                if(self.checkEmpty(newValues)) return;
                if(self.selectTypeSet() == TypeSet.PERSON){
                    block.invisible();
                    self.getDataWpl().done(function(lstA){
                        block.clear();
                        self.employeeList(lstA);
                    }).fail(()=>{
                        block.clear();
                    });
                }
            })
            //確定者(K2_21)の選択肢を承認者一覧(K2_15)と合わせる(update item cua control 確定者(K2_21)  theo 承認者一覧(K2_15))
            self.approverList.subscribe(function(){
                self.setDataForCbb();
            })
            
            //check 
            $('#swap-list').on("swaplistgridsizeexceed", function(evt, $list, max) { 
                error({ messageId: 'Msg_300'});
            })
        }// end constructor
        
        checkEmpty(value: any){
            if(nts.uk.util.isNullOrUndefined(value) || nts.uk.util.isNullOrEmpty(value)) return true;
            return false;
        }
        setDataForCbb(){
            var self = this;
            //add item in  dropdownlist
            self.itemListCbb.removeAll();
            self.itemListCbb.push(new vmbase.ApproverDtoK('','','指定しない',0,0));
            if(self.approverList().length > 0){
                _.forEach(self.approverList(),function(item: vmbase.ApproverDtoK){
                    self.itemListCbb.push(item);    
                })
                self.selectedCbbCode(self.confirm); 
            }
        }
        getData(): JQueryPromise<any>{
            let self = this;
            let dfd = $.Deferred();
            let data: any = nts.uk.ui.windows.getShared('CMM018K_PARAM'); 
             //承認者一覧              
            if(data.approverInfor.length > 0){
                _.forEach(data.approverInfor, function(sID){
                    if(sID.approvalAtr === 0){
                        service.getPersonInfor(sID.id).done(function(data: any){
                            self.approverList.push(new vmbase.ApproverDtoK(data.sid, data.employeeCode, data.employeeName, 0,sID.dispOrder));
                            self.approverList(_.orderBy(self.approverList(),["dispOrder"], ["asc"]));
                        })
                    }else{
                        let job = new service.model.JobtitleInfor;
                        job.positionId = sID.id;
                        job.startDate = self.standardDate();
                        job.companyId = "";
                        job.positionCode = "";
                        job.positionName = "";
                        job.sequenceCode = "";
                        job.endDate = moment(new Date()).toDate();
                        service.getJobTitleName(job).done(function(data: any){
                            self.approverList.push(new vmbase.ApproverDtoK(data.positionId, data.positionCode, data.positionName, 1,sID.dispOrder));
                            self.approverList(_.orderBy(self.approverList(),["dispOrder"], ["asc"]));
                        })    
                    }
                })    
            }else{                    
                self.setDataForCbb();    
            }
            dfd.resolve();
            return dfd.promise();    
        }
        
        //bind data
        bindData(selectTypeSet: number){
            var self = this;
            if(selectTypeSet == TypeSet.PERSON){//PERSON
                self.enableListWp(true);
                $('#tree-grid').ntsTreeComponent(self.treeGrid);
            }else if(selectTypeSet == TypeSet.SPEC_WKP){//CHI DINH
                self.enableListWp(true);
                $('#tree-grid').ntsTreeComponent(self.treeGrid);
                if(self.lstJobGS().length > 0) return; //データがある 
                if(self.lstJob().length > 0){
                    self.lstJobGS(_.clone(self.lstJob()));
                    self.lstSpec1(_.clone(self.lstJob()));
                }else{
                    block.invisible();
                    service.jobGroup().done(function(data: any){
                        self.lstJob(_.clone(data));
                        self.lstJobGS(_.clone(data));
                        self.lstSpec1(_.clone(data));
                        block.clear(); 
                    }).fail(function(res: any){
                        block.clear();
                    });
                }
                
            }else{//GROUP
                self.enableListWp(false);
                if(self.lstJobG().length > 0) return;//データがある
                if(self.lstJob().length > 0){
                    self.lstJobG(_.clone(self.lstJob()));
                    self.lstGroup(_.clone(self.lstJob()));
                }else{
                    block.invisible();
                    service.jobGroup().done(function(data: any){
                        self.lstJob(_.clone(data));
                        self.lstJobG(_.clone(data));
                        self.lstGroup(_.clone(data));
                        block.clear(); 
                    }).fail(function(res: any){
                        block.clear();
                    });
                }
            }
        }
         
        getDataWpl(): JQueryPromise<any>{
            let self = this;
            let dfd = $.Deferred();
            var employeeSearch = new service.model.EmployeeSearchInDto();
            employeeSearch.baseDate = self.standardDate();
            let lstWkp1 = [self.treeGrid.selectedWorkplaceId()];
            let lstA = [];
            let UNIT = 100;
            for(let i = 0; i < lstWkp1.length; i += UNIT){
                if(i + UNIT > lstWkp1.length){
                    employeeSearch.workplaceIds = lstWkp1.slice(i, lstWkp1.length);
                }else{
                    employeeSearch.workplaceIds = lstWkp1.slice(i, i + UNIT);
                }
                service.searchModeEmployee(employeeSearch).done(function(data: any){
                    let lstTmp = self.toUnitModelList(data);
                    _.each(lstTmp, function(item){
                        lstA.push(new vmbase.ApproverDtoK(item.id, item.code, item.name, item.approvalAtr,0))
                    });
                    
                    if(i + UNIT > lstWkp1.length) {
                        dfd.resolve(lstA);
                    }
                    
                })
            }
            if(lstWkp1.length == 0){
                dfd.resolve(lstA);
            }
            return dfd.promise();
        }
        //決定 button click
        submitClickButton(){
            let self = this;
            let lstApprover = [];
            let approvalForm = 1;
            let confirmSet = '';
            if(self.selectTypeSet() == TypeSet.PERSON){//PERSON
                lstApprover = self.approverList();
                approvalForm = self.selectFormSet();
                confirmSet = self.selectedCbbCode();
            }else if(self.selectTypeSet() == TypeSet.SPEC_WKP){//CHI DINH
                lstApprover = self.lstSpec2();
                approvalForm = self.selectFormSetS();
            }else{//GROUP
                lstApprover = self.lstGroup1();
                _.each(self.lstGroup2(), function(job){
                    lstApprover.push(job);
                });
                approvalForm = self.selectFormSetG();
            }
            let data: vmbase.KData = {  appType: self.appType(), //設定する対象申請名 
                                        formSetting: approvalForm,//承認形態
                                        approverInfor: lstApprover,//承認者一覧
                                        confirmedPerson: confirmSet, //確定者
                                        selectTypeSet: self.selectTypeSet(),//承認者指定種類 ( 個人,  職格, 特定職場)
                                        approvalFormName: approvalForm == vmbase.ApprovalForm.EVERYONE_APPROVED ? 
                                                        resource.getText('CMM018_63') : resource.getText('CMM018_66')
                                        }
            console.log(data);
            setShared("CMM018K_DATA",data );
            nts.uk.ui.windows.close();
        }
        
        //キャンセル button click
        closeDialog(){
             setShared("CMM018K_DATA",null);
            nts.uk.ui.windows.close();
        }       

        /**
         * function convert dto to model init data 
         */        
        public toUnitModelList(dataList: service.model.EmployeeSearchDto[]): Array<vmbase.ApproverDtoK> {
            var dataRes: vmbase.ApproverDtoK[] = [];

            for (var item: service.model.EmployeeSearchDto of dataList) {
                let a:vmbase.ApproverDtoK = {
                    id: item.sid,
                    code: item.scd,
                    name: item.pname,
                    approvalAtr: 0
                }
                dataRes.push(a);
            }
            return dataRes;
        }
        //when click button → 
        nextItem(){
            let self = this;
            if(self.selectTypeSet() == TypeSet.SPEC_WKP){//CHI DINH
                if(self.checkEmpty(self.selectSpec1())) return;
                if(self.lstSpec2().length >= 5){
                    error({ messageId: 'Msg_300'});
                    return;
                }
                let itemSl = self.findJobGSelect(self.lstSpec1(), self.selectSpec1());
                _.remove(self.lstSpec1(), function(item) {
                      return item.code == self.selectSpec1();
                    });
                self.lstSpec2.push(itemSl);
                self.selectSpec1('');
            }else{//GROUP
                if(self.checkEmpty(self.selectG())) return;
                if(self.lstGroup1().length + self.lstGroup2().length >= 5){
                    error({ messageId: 'Msg_300'});
                    return;
                }
                let itemSl = self.findJobGSelect(self.lstGroup(), self.selectG());
                _.remove(self.lstGroup(), function(item) {
                      return item.code == self.selectG();
                });
                if(self.lstGroup1().length == 0){
                    self.lstGroup1.push(itemSl);
                }else{
                    self.lstGroup2.push(itemSl);
                }
                self.selectG('');
            }
        }
        //when click button ←
        prevItem(){
            let self = this;
            if(self.selectTypeSet() == TypeSet.SPEC_WKP){//CHI DINH
                if(self.checkEmpty(self.selectSpec2())) return;
                let itemSl = self.findJobGSelect(self.lstSpec2(), self.selectSpec2());
                _.remove(self.lstSpec2(), function(item) {
                      return item.code == self.selectSpec2();
                    });
                self.lstSpec1.push(itemSl);
                self.selectSpec2('');
            }else{//GROUP
                if(self.checkEmpty(self.selectG1())) return;
                let itemSl = self.findJobGSelect(self.lstGroup1(), self.selectG1());
                if(itemSl !== undefined){//List G1
                    _.remove(self.lstGroup1(), function(item) {
                      return item.code == self.selectG1();
                    });
                }else{//List G2
                    itemSl = self.findJobGSelect(self.lstGroup2(), self.selectG1());
                    _.remove(self.lstGroup2(), function(item) {
                      return item.code == self.selectG1();
                    });
                }
                self.lstGroup.push(itemSl);
                self.selectG1('');
            }
        }
        //find jobG selected
        findJobGSelect(lstItem: Array<any>, selected: string){
            return _.find(lstItem, function(jobG){
               return jobG.code == selected; 
            });
        }

    }//end ScreenModel
    interface ITreeGrid {
            treeType: number;
            selectType: number;
            isDialog: boolean;
            isMultiSelect: boolean;
            isShowAlreadySet: boolean;
            isShowSelectButton: boolean;
            baseDate: KnockoutObservable<any>;
            selectedWorkplaceId: KnockoutObservable<any>;
            alreadySettingList: KnockoutObservableArray<any>;
            systemType : number;
            width?: number;
        }
    class SimpleObject {
            key: KnockoutObservable<string>;
            name: KnockoutObservable<string>;
            constructor(key: string, name: string){
                this.key = ko.observable(key);
                this.name = ko.observable(name);
            }      
        }
    export class ButtonSelect{
        code: number;
        name: String;
        constructor(code: number, name: String){
            this.code = code;
            this.name = name;    
        }
    }
    enum TypeSet {
        /** 個人*/
        PERSON = 0,
        /** 職格*/
        GROUP = 1,
        /** 特定職場*/
        SPEC_WKP =2
    }
}