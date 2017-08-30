module cmm018.k.viewmodel{
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import windows = nts.uk.ui.windows;
    import resource = nts.uk.resource;
    import UnitModel = kcp.share.list.UnitModel;
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
        selectedEmpl : KnockoutObservableArray<Approver> = ko.observableArray([]);
        currentIdEmp: KnockoutObservableArray<any> = ko.observableArray([]);
        columns: KnockoutObservableArray<any> = ko.observableArray([]);
        //確定者の選択
        itemListCbb:KnockoutObservableArray<any> = ko.observableArray([]);
        cbbEnable: KnockoutObservable<boolean> = ko.observable(true);
        selectedId : KnockoutObservable<string> = ko.observable("");
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
        //選択可能な承認者一覧
        listComponentOption = {
            isShowAlreadySet: false,
            isMultiSelect: true,
            listType: 4,
            employeeInputList: this.employeeList,
            selectType: 2,
            selectedCode: ko.observableArray([]),
            isDialog: false,
            isShowNoSelectRow: false,
            alreadySettingList: false,
            isShowWorkPlaceName: false,
            isShowSelectAllButton: false
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
                //承認者一覧
                self.selectedEmpl(data.approverInfor);
                
            }
            //基準日
            this.standardDate(new Date());
            //承認者指定種類
            self.typeSetting.push(new ButtonSelect(0, resource.getText('CMM018_56')));
            self.typeSetting.push(new ButtonSelect(1, resource.getText('CMM018_57')));
            //承認形態
            self.formSetting.push(new ButtonSelect(0, resource.getText('CMM018_63')));
            self.formSetting.push(new ButtonSelect(1, resource.getText('CMM018_66')));
            //確定者の選択
            self.itemListCbb.push(new Approver('','','指定しない'));
            //選択された承認者一覧
            self.columns = ko.observableArray([
                    { headerText: 'id', prop: 'id', width: '0%' },
                    { headerText: resource.getText('CMM018_69'), prop: 'code', width: '40%' },
                    { headerText: resource.getText('CMM018_70'), prop: 'name', width: '60%' }
                ])
            //change 個人設定　or 職位設定
            self.selectTypeSet.subscribe(function(newValue){
                if(newValue === 0){
                    //承認者一覧リストが表示
                    $('#component-items-list').show();
                    //職位一覧 が非表示
                    $('#component-items-list-job').hide();
                }else{
                    //承認者一覧リストが非表示
                    $('#component-items-list').hide();
                    //職位一覧 が表示
                    $('#component-items-list-job').show();
                }
            })
            //職場リスト            
            self.treeGrid.selectedWorkplaceId.subscribe(function(newValues){
                //個人設定 (employee setting)
                if(self.selectTypeSet() === 0){
                    var employeeSearch = new service.model.EmployeeSearchInDto();
                    employeeSearch.baseDate = self.standardDate();
                    employeeSearch.workplaceCodes = newValues;
                    service.searchModeEmployee(employeeSearch).done(function(data: any){
                        self.listComponentOption.employeeInputList(self.toUnitModelList(data));
                    }).fail(function(res: any){
                        nts.uk.ui.dialog.alert(res.messageId);
                    })
                }else{
                //職位設定(job setting)
                    
                }
                
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
            
        }// end constructor
        
        //決定 button click
        submitClickButton(){
            
        }
        
        //キャンセル button click
        closeDialog(){
            nts.uk.ui.windows.close();    
        }
        //→ button click
        leftSelect(){
            
        }
        //← button click
        rightSelect(){
            
        }
        //↑ button click
        topSelect(){
            
        }
        //↓ button click
        bottomSelect(){
            
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
    export class Approver{
        id: string;
        code: string;
        name: string;
        constructor(id: string, code: string, name: string){
            this.id = id;
            this.code = code;
            this.name = name;    
        }
    }
    
}