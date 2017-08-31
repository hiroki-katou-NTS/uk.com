module nts.uk.com.view.cmm018.k.viewmodel{
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import windows = nts.uk.ui.windows;
    import resource = nts.uk.resource;
    import UnitModel = kcp.share.list.UnitModel;
    import shrVm = cmm018.shr.vmbase;
    import service = cmm018.k.service;
    export class ScreenModel{
        appType: KnockoutObservable<String> = ko.observable('');
        standardDate: KnockoutObservable<Date> = ko.observable(new Date());
        //承認者指定種類
        typeSetting: KnockoutObservableArray<ButtonSelect> = ko.observableArray([]);
        selectTypeSet: KnockoutObservable<number> = ko.observable(0);
        //承認形態
        formSetting: KnockoutObservableArray<ButtonSelect> = ko.observableArray([]);
        selectFormSet: KnockoutObservable<number> = ko.observable(0);
        currentCalendarWorkPlace: KnockoutObservableArray<SimpleObject> = ko.observableArray([]);
        employeeList: KnockoutObservableArray<UnitModel> = ko.observableArray([]);
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
        //職場リスト
        treeGrid: ITreeGrid = {
                treeType: 1,
                selectType: 4,
                isDialog: false,
                isMultiSelect: true,
                isShowAlreadySet: false,
                isShowSelectButton: true,
                baseDate: ko.observable(this.standardDate()),
                selectedWorkplaceId: ko.observableArray(_.map(this.currentCalendarWorkPlace(), o => o.key)),
                //selectedWorkplaceId: this.multiSelectedWorkplaceId,
                alreadySettingList: ko.observableArray([])
        };
        //選択可能な職位一覧
        
        
        constructor(){
            var self = this;            
            //設定対象
            let data: any = getShared('CMM008K_PARAM');            
            if(data !== undefined){
                //設定する対象申請名
                self.appType(data.appType);
                //承認形態
                self.selectFormSet(data.formSetting);                
                //設定種類
                self.selectTypeSet(data.selectTypeSet);
                self.setDataForSwapList(self.selectTypeSet());
                //承認者一覧
                self.approverList(data.approverInfor);
                self.setDataForCbb();
                //確定者
                var index = self.approverList().indexOf(data.confirmedPerson());
                if(index != -1){
                    self.selectedCbbCode(data.confirmedPerson());    
                }else{
                    self.selectedCbbCode("");
                }
            }
            //基準日
            this.standardDate(new Date());
            //承認者指定種類
            self.typeSetting.push(new ButtonSelect(0, resource.getText('CMM018_56')));
            self.typeSetting.push(new ButtonSelect(1, resource.getText('CMM018_57')));
            //承認形態
            self.formSetting.push(new ButtonSelect(0, resource.getText('CMM018_63')));
            self.formSetting.push(new ButtonSelect(1, resource.getText('CMM018_66')));
            
            //選択された承認者一覧
            self.columns = ko.observableArray([
                    { headerText: 'id', prop: 'id', width: '0%' },
                    { headerText: resource.getText('CMM018_69'), prop: 'code', width: '40%' },
                    { headerText: resource.getText('CMM018_70'), prop: 'name', width: '60%' }
                ])
            //change 個人設定　or 職位設定
            self.selectTypeSet.subscribe(function(newValue){
                self.setDataForSwapList(newValue);
            })
            //職場リスト            
            self.treeGrid.selectedWorkplaceId.subscribe(function(newValues){
                self.setDataForSwapList(self.selectTypeSet());                
            })
            //change 承認形態
            self.selectFormSet.subscribe(function(newValues){
                //承認形態が誰か一人を選択する場合
                if(newValues === 0){
                    self.cbbEnable(true);
                }else{
                    //承認形態が全員承認を選択する場合
                    self.cbbEnable(false);    
                }
            })
            
            
            self.approverList.subscribe(function(){
                self.setDataForCbb();
            })
            
        }// end constructor
        
        setDataForCbb(){
            var self = this;
            //add item in  dropdownlist
            self.itemListCbb.removeAll();
            self.itemListCbb.push(new shrVm.ApproverDtoK('','','指定しない'));
            if(self.approverList().length > 0){
                _.forEach(self.approverList(),function(item){
                    self.itemListCbb.push(item);    
                })
            }
        }
        //set data in swap-list
        setDataForSwapList(selectTypeSet: number){
            var self = this;
            //個人設定 (employee setting)
            if(selectTypeSet === 0){
                var employeeSearch = new service.model.EmployeeSearchInDto();
                employeeSearch.baseDate = self.standardDate();
                employeeSearch.workplaceCodes = self.treeGrid.selectedWorkplaceId();
                service.searchModeEmployee(employeeSearch).done(function(data: any){
                    self.employeeList(self.toUnitModelList(data));
                }).fail(function(res: any){
                    nts.uk.ui.dialog.alert(res.messageId);
                })
            //職位設定(job setting)
            }else{
            
                
            }    
        } 
        
        
        //決定 button click
        submitClickButton(){
            var self = this;
            setShared("CMM008K_PARAM", { 
                                        appType: self.appType(), //設定する対象申請名 
                                        formSetting: self.selectFormSet(),//承認形態
                                        approverInfor: self.approverList(),//承認者一覧
                                        confirmedPerson: self.selectedCbbCode(), //確定者
                                        selectTypeSet: self.selectTypeSet()
                                        });
            nts.uk.ui.windows.close();
        }
        
        //キャンセル button click
        closeDialog(){
            nts.uk.ui.windows.close();
        }       

        /**
         * function convert dto to model init data 
         */        
        public toUnitModelList(dataList: service.model.EmployeeSearchDto[]): Array<UnitModel> {
            var dataRes: UnitModel[] = [];

            for (var item: service.model.EmployeeSearchDto of dataList) {
                dataRes.push({
                    code: item.scd,
                    name: item.pName
                });
            }
            return dataRes;
        }
        
        // start function
        start(): JQueryPromise<any>{
            var self = this;
            var dfd = $.Deferred<any>();
            
            
            return dfd.promise();
        }//end start
        
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