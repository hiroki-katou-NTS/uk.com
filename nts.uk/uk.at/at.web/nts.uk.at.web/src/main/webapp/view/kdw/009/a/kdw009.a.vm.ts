module nts.uk.at.view.kdw009.a.viewmodel {
    
    export class ScreenModel {
        lstBusinessType: KnockoutObservableArray<BusinessType>;
        gridListColumns: KnockoutObservableArray<any>;
        code: KnockoutObservable<any>;
        selectedCode: KnockoutObservable<string>;
        selectedName: KnockoutObservable<string>;
        constructor() {
            let self = this;
            self.gridListColumns = ko.observableArray([
                { headerText: nts.uk.resource.getText("KDW009_6"), key: 'businessTypeCode', width: 45 },
                { headerText: nts.uk.resource.getText("KDW009_7"), key: 'businessTypeName', width: 280 }
            ]);
            self.lstBusinessType = ko.observableArray([]);
            self.code = ko.observable();
            self.selectedCode = ko.observable("");
            self.selectedName = ko.observable("");
//            self.selectedCode.subscribe((businessTypeCode) => {
//                if (businessTypeCode) {
//                    let foundItem = _.find(self.lstBusinessType(), (item: BusinessType) => {
//                        return item.businessTypeCode == businessTypeCode;
//                    });
//                }
//            });
        }

        /** get data number "value" in list **/
        getListStandardMenu(value) {
        }

        /** get data when start dialog **/
        startPage(): JQueryPromise<any> {
            let self = this;
            let dfd = $.Deferred();
            let array=[];
            let list=[];    
            service.getAll().done((lstData: Array<viewmodel.BusinessType>) => {
                let sortedData = _.orderBy(lstData, ['businessTypeCode'], ['asc']);
                self.lstBusinessType(sortedData);
//                self.selectedOption(self.lstBusinessType()[0]);
                self.selectedCode(self.lstBusinessType()[0].businessTypeCode.toString());
                self.selectedName(self.lstBusinessType()[0].businessTypeName.toString());
                dfd.resolve();
            }).fail(function(error){
                    dfd.reject();
                    alert(error.message);
                })        
            return dfd.promise();
        }  
        
        /** update data when click button register **/
        register() {
           
        }          
    }
    export class BusinessType{
        businessTypeCode: KnockoutObservable<string>=ko.observable("");
        businessTypeName: KnockoutObservable<string>=ko.observable("");  
        constructor(businessTypeCode: string, businessTypeName: string){
            this.businessTypeCode = ko.observable(businessTypeCode);
            this.businessTypeName = ko.observable(businessTypeName);
        }
        
    }
}




