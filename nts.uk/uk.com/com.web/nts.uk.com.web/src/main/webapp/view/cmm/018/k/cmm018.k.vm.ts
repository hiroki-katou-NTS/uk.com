module nts.uk.com.view.cmm018.k.viewmodel{
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import windows = nts.uk.ui.windows;
    import resource = nts.uk.resource;
    import shrVm = cmm018.shr.vmbase;
    import service = cmm018.k.service;
    import block = nts.uk.ui.block;
    export class ScreenModel{
        //enable list workplace
        enableListWp: KnockoutObservable<boolean> = ko.observable(true);
        appType: KnockoutObservable<String> = ko.observable('');
        standardDate: KnockoutObservable<Date> = ko.observable(moment(new Date()).toDate());
        //承認者指定種類
        typeSetting: KnockoutObservableArray<ButtonSelect> = ko.observableArray([]);
        selectTypeSet: KnockoutObservable<number> = ko.observable(null);
        //承認形態
        formSetting: KnockoutObservableArray<ButtonSelect> = ko.observableArray([]);
        selectFormSet: KnockoutObservable<number> = ko.observable(null);
        currentCalendarWorkPlace: KnockoutObservableArray<SimpleObject> = ko.observableArray([]);
        employeeList: KnockoutObservableArray<shrVm.ApproverDtoK> = ko.observableArray([]);
        multiSelectedWorkplaceId: KnockoutObservableArray<string> = ko.observableArray([]);
        approverList : KnockoutObservableArray<shrVm.ApproverDtoK> = ko.observableArray([]);
        currentApproveCodeLst: KnockoutObservableArray<any> = ko.observableArray([]);
        columns: KnockoutObservableArray<any> = ko.observableArray([]);
        currentEmployeeCodeLst: KnockoutObservableArray<any> = ko.observableArray([]);
        items: KnockoutObservableArray<any> = ko.observableArray([]);
        //確定者の選択
        itemListCbb:KnockoutObservableArray<any> = ko.observableArray([]);
        cbbEnable: KnockoutObservable<boolean> = ko.observable(true);
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
                isMultiSelect: true,
                isShowAlreadySet: false,
                isShowSelectButton: true,
                baseDate: ko.observable(this.standardDate()),
                selectedWorkplaceId: ko.observableArray(_.map(this.currentCalendarWorkPlace(), o => o.key)),
                alreadySettingList: ko.observableArray([]),
                systemType : 2
        };
        //選択可能な職位一覧
        //承認者の登録(個人別)
        personTab : number = 2;
        //設定種類
        personSetting: number = 0; //個人設定
        //承認形態
        formOne : number = 2; //誰か一人
        formAll: number = 1; //全員承認
        
        lstTmp: KnockoutObservableArray<any> = ko.observableArray([]);
        constructor(){
            var self = this;
            //設定対象: get param from main: approverInfor(id & approvalAtr)
            let data: any = nts.uk.ui.windows.getShared('CMM018K_PARAM'); 
            self.confirm = data.confirmedPerson;
            //change 承認形態
            self.selectFormSet.subscribe(function(newValues){
                //承認形態が誰か一人を選択する場合
                if(newValues === self.formOne){
                    self.cbbEnable(true);
                }else{
                    //承認形態が全員承認を選択する場合
                    self.cbbEnable(false);    
                }
            });
            self.selectTypeSet(0);
            self.getData();
            if(data !== undefined){
                //設定する対象申請名
                self.appType(data.appTypeName);
                //承認形態
                self.selectFormSet(data.formSetting);                
                
                //承認者の登録(個人別): 非表示
                if(data.tab === self.personTab){
                    $('#typeSetting').hide();
                }else{
                    $('#typeSetting').show();
                }
            }
            //基準日
            this.standardDate(moment(new Date()).toDate());
            //承認者指定種類
            self.typeSetting.push(new ButtonSelect(0, resource.getText('CMM018_56')));
            self.typeSetting.push(new ButtonSelect(1, resource.getText('CMM018_57')));
            //承認形態
            self.formSetting.push(new ButtonSelect(1, resource.getText('CMM018_63')));
            self.formSetting.push(new ButtonSelect(2, resource.getText('CMM018_66')));
            
            //選択された承認者一覧
            self.columns = ko.observableArray([
                    { headerText: 'id', prop: 'id', width: '0px', hidden: true },
                    { headerText: resource.getText('CMM018_69'), prop: 'code', width: '120px' },
                    { headerText: resource.getText('CMM018_70'), prop: 'name', width: '210px' }
                ])
            //change 個人設定　or 職位設定
            self.selectTypeSet.subscribe(function(newValue){
                
                nts.uk.ui.errors.clearAll();
                $("#work-place-base-date").trigger("validate");
                if(nts.uk.ui.errors.hasError()){
                    self.treeGrid.baseDate(moment(new Date()).toDate());
                    nts.uk.ui.errors.clearAll();
                }
                $("#standardDate").trigger("validate");
                if(nts.uk.ui.errors.hasError()){
                    self.standardDate(moment(new Date()).toDate());
                    nts.uk.ui.errors.clearAll();
                }
                self.employeeList.removeAll();
                if(newValue == 0){
                    self.enableListWp(true);
                    $('#tree-grid').ntsTreeComponent(self.treeGrid);
                }else{
                    self.setDataForSwapList(newValue);
                    self.enableListWp(false);
                }
            });
            //職場リスト            
            self.treeGrid.selectedWorkplaceId.subscribe(function(newValues){
                self.setDataForSwapList(self.selectTypeSet());                
            })
            //確定者(K2_21)の選択肢を承認者一覧(K2_15)と合わせる(update item cua control 確定者(K2_21)  theo 承認者一覧(K2_15))
            self.approverList.subscribe(function(){
                self.setDataForCbb();
            })
            
            //check 
            $('#swap-list').on("swaplistgridsizeexceed", function(evt, $list, max) { 
                nts.uk.ui.dialog.alertError({ messageId: 'Msg_300'});
            })
        }// end constructor
        
        setDataForCbb(){
            var self = this;
            //add item in  dropdownlist
            self.itemListCbb.removeAll();
            self.itemListCbb.push(new shrVm.ApproverDtoK('','','指定しない',0,0));
            if(self.approverList().length > 0){
                _.forEach(self.approverList(),function(item: shrVm.ApproverDtoK){
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
                            self.approverList.push(new shrVm.ApproverDtoK(data.sid, data.employeeCode, data.employeeName, 0,sID.dispOrder));
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
                            self.approverList.push(new shrVm.ApproverDtoK(data.positionId, data.positionCode, data.positionName, 1,sID.dispOrder));
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
        //set data in swap-list
        setDataForSwapList(selectTypeSet: number){
            var self = this;
            //clear data before push employee new
            self.employeeList([]);
            //個人設定 (employee setting)
            if(selectTypeSet === self.personSetting){
                block.invisible();
                self.getDataWpl().done(function(lstA){
                    block.clear();
                    console.log(lstA);
                    self.employeeList(lstA);
                }).fail(()=>{
                    block.clear();
                });
                
                
                
                

            //職位設定(job setting)
            }else{
                block.invisible();
                service.getJobTitleInfor(self.standardDate()).done(function(data: string){
                    let tmp = []
                    _.forEach(data, function(value: service.model.JobtitleInfor){
                        var job = new shrVm.ApproverDtoK(value.positionId,value.positionCode,value.positionName, 1,0);
                        tmp.push(job);
                    });
                    self.employeeList(tmp);
                    block.clear(); 
                }).fail(function(res: any){
                    block.clear();
                    nts.uk.ui.dialog.alert(res.messageId);
                })
            }    
        } 
        getDataWpl(): JQueryPromise<any>{
            let self = this;
            let dfd = $.Deferred();
            var employeeSearch = new service.model.EmployeeSearchInDto();
            employeeSearch.baseDate = self.standardDate();
            let lstWkp1 = self.treeGrid.selectedWorkplaceId();
            let lstA = [];
            for(let i = 0; i < lstWkp1.length; i += 10){
                if(i + 10 > lstWkp1.length){
                    employeeSearch.workplaceIds = lstWkp1.slice(i, lstWkp1.length);
                }else{
                    employeeSearch.workplaceIds = lstWkp1.slice(i, i + 10);
                }
                service.searchModeEmployee(employeeSearch).done(function(data: any){
                    let lstTmp = self.toUnitModelList(data);
                    _.each(lstTmp, function(item){
                        lstA.push(new shrVm.ApproverDtoK(item.id, item.code, item.name, item.approvalAtr,0))
                    });
                    
                    if(i + 10 > lstWkp1.length) {
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
            var self = this;
            let data: shrVm.KData = { appType: self.appType(), //設定する対象申請名 
                                        formSetting: self.selectFormSet(),//承認形態
                                        approverInfor: self.approverList(),//承認者一覧
                                        confirmedPerson: self.selectedCbbCode(), //確定者
                                        selectTypeSet: self.selectTypeSet(),
                                        approvalFormName: self.selectFormSet() == self.formAll ? resource.getText('CMM018_63') : resource.getText('CMM018_66')
                                        }
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
        public toUnitModelList(dataList: service.model.EmployeeSearchDto[]): Array<shrVm.ApproverDtoK> {
            var dataRes: shrVm.ApproverDtoK[] = [];

            for (var item: service.model.EmployeeSearchDto of dataList) {
                let a:shrVm.ApproverDtoK = {
                    id: item.sid,
                    code: item.scd,
                    name: item.pname,
                    approvalAtr: 0
                }
                dataRes.push(a);
            }
            return dataRes;
        }
        /**
         * load data new when base date is changed 
         */
        applyDataSearch(): void {
            let self = this;
            block.invisible();
            if (nts.uk.ui.errors.hasError()){return;}
            if(self.selectTypeSet() == 0){
                self.treeGrid.baseDate(this.standardDate());
                $('#tree-grid').ntsTreeComponent(self.treeGrid);
                block.clear();
            }else{
                self.employeeList([]);
                service.getJobTitleInfor(self.standardDate()).done(function(data: string){
                    _.forEach(data, function(value: service.model.JobtitleInfor){
                        var job = new shrVm.ApproverDtoK(value.positionId,value.positionCode,value.positionName, 1);
                        self.employeeList.push(job);
                    });
                    block.clear();    
                }).fail(function(res: any){
                    block.clear();
                    nts.uk.ui.dialog.alert(res.messageId);
                });
            }
             
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
    
}