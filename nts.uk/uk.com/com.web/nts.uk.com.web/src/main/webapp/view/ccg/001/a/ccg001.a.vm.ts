module nts.uk.com.view.ccg001.a {  

    import ListType = kcp.share.list.ListType;
    import SelectType = kcp.share.list.SelectType;
    import UnitModel = kcp.share.list.UnitModel;
    import PersonModel = nts.uk.com.view.ccg.share.ccg.PersonModel;
    export module viewmodel {
        export class ScreenModel {
           
            ccgcomponent: any;
            personinfo: any;
            selectedCode: KnockoutObservableArray<string>;
            constructor() {
                var self = this;
                self.selectedCode = ko.observableArray([]);
                self.ccgcomponent = {
                    isMultiSelect: true,
                    onSearchAllClicked: function(dataList: any) {
                        self.personinfo = {
                            isShowAlreadySet: false,
                            isMultiSelect: self.ccgcomponent.isMultiSelect,
                            listType: ListType.EMPLOYEE,
                            employeeInputList: self.toUnitModel(dataList),
                            selectType: SelectType.SELECT_BY_SELECTED_CODE,
                            selectedCode: self.selectedCode,
                            isDialog: false,
                            isShowNoSelectRow: false,
                        }
                        $('#personinfo').ntsListComponent(self.personinfo);
                    }
                }
                $('#ccgcomponent').ntsGroupComponent(self.ccgcomponent);
                
                
            }

            public startPage(): JQueryPromise<any> {
                let dfd = $.Deferred<any>();
                dfd.resolve();
                return dfd.promise();
            }
            
            public toUnitModel(dataList: PersonModel[]): KnockoutObservableArray<UnitModel>{
                var dataRes: UnitModel[] = [];    
                
                for(var item: PersonModel of dataList){
                    dataRes.push({
                        code: item.personId,
                        name: item.personName
                    }); 
                }
                return ko.observableArray(dataRes);
            }
        }
    }
}