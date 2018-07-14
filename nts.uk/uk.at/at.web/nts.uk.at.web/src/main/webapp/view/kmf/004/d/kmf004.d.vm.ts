module nts.uk.at.view.kmf004.d.viewmodel {
    export class ScreenModel {
        lstGrantDate: KnockoutObservableArray<Per>;
        columns: KnockoutObservableArray<any>;
        selectedCode: KnockoutObservable<string>;
        grantDateCode: KnockoutObservable<string>;
        grantDateName: KnockoutObservable<string>;
        provisionCheck: KnockoutObservable<boolean>;
        provisionDeactive: KnockoutObservable<boolean>;
        items: KnockoutObservableArray<Item>;
        editMode: KnockoutObservable<boolean>;

        constructor() {
            let self = this;
            
            self.columns = ko.observableArray([
                { headerText: nts.uk.resource.getText("KMF004_5"), key: 'yearServiceCode', width: 60 },
                { headerText: nts.uk.resource.getText("KMF004_6"), key: 'yearServiceName', width: 160, formatter: _.escape}
            ]);
            
            self.lstGrantDate = ko.observableArray([]);
            self.selectedCode = ko.observable("");

            self.grantDateCode = ko.observable("");
            self.grantDateName = ko.observable("");

            provisionCheck = ko.observable(false);
            provisionDeactive = ko.observable(true);

            self.items = ko.observableArray([]);
            self.editMode = ko.observable(true); 

        }

        /** get data when start dialog **/
        startPage(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();

                    self.items([]);
                    for (let i = 0; i < 20; i++) {
                        if(self.items()[i] == undefined){
                            let t : item = {
                                grantDateNo: ko.mapping.fromJS(i),
                                months: ko.mapping.fromJS(null),
                                years: ko.mapping.fromJS(null),
                                grantedDays: ko.mapping.fromJS(null)
                            }
                            self.items.push(new Item(t));
                        }
                    }

            
//            service.getAll(specialHolidayCode).done((lstData: Array<Per>) => {
//                nts.uk.ui.errors.clearAll();
//                if(lstData.length == 0){
//                    self.selectedId(0);
//                    self.check(true);
//                    self.codeObject(null);
//                    self.selectedName(null);
//                    self.checkUpdate(false);
//                    self.provisionCheck(false);
//                    self.provisionDeactive(true);
//                    self.items([]);
//                    for (let i = 0; i < 20; i++) {
//                        if(self.items()[i] == undefined){
//                            let t : item = {
//                                yearServiceNo: ko.mapping.fromJS(i),
//                                month: ko.mapping.fromJS(null),
//                                year: ko.mapping.fromJS(null),
//                                date: ko.mapping.fromJS(null)
//                            }
//                            self.items.push(new Item(t));
//                        }
//                    }
//                    
//                    $("#inpCode").focus();
//                } else {
//                    let sortedData : KnockoutObservableArray<any> = ko.observableArray([]);
//                    sortedData(_.orderBy(lstData, ['yearServiceCode'], ['asc']));
//                     $("#inpPattern").focus();
//                    self.lstPer(sortedData());
//                    self.selectedOption(self.lstPer()[0]);
//                    self.selectedId(self.selectedOption().yearServiceCls);
//                    self.selectedCode(ko.toJS(self.lstPer()[0].yearServiceCode));
//                    self.selectedName(self.lstPer()[0].yearServiceName);
//                    self.provisionCheck(self.lstPer()[0].provision == 1 ? true : false);
//                    if(self.lstPer()[0].provision == 1) {
//                        self.provisionDeactive(false);
//                    } else {
//                        self.provisionDeactive(true);
//                    }
//                    self.codeObject(ko.toJS(self.lstPer()[0].yearServiceCode));                   
//                }
//                
//                dfd.resolve();
//            }).fail(function(error) {
//                dfd.reject();
//                $('#inpCode').ntsError('set', error);
//            });

            dfd.resolve();
            return dfd.promise();
        }
        
        /** update or insert data when click button register **/
        register() {  
            let self = this; 
            nts.uk.ui.block.invisible();
                        
            $("#inpCode").trigger("validate");
            $("#inpPattern").trigger("validate");
                
            if (nts.uk.ui.errors.hasError()) {
                return;       
            }
            
            
        } 
        
        //  new mode 
        newMode() {
            let self = this;
            $("#inpCode").focus();
            
            nts.uk.ui.errors.clearAll();                 
        }
        
        /** remove item from list **/
        remove() {
            let self = this;
            
            
        } 
        
        
        closeDialog(){
            nts.uk.ui.windows.close();
        }
        
        /**
             * Set error
             */
        addListError(errorsRequest: Array<string>) {
            var errors = [];
            _.forEach(errorsRequest, function(err) {
                errors.push({message: nts.uk.resource.getMessage(err), messageId: err, supplements: {} });
            });
            
            nts.uk.ui.dialog.bundledErrors({ errors: errors});
        }
    }

    export interface IItem{
        grantDateNo: KnockoutObservable<number>;
        months: KnockoutObservable<number>;
        years: KnockoutObservable<number>;
        grantedDays: KnockoutObservable<number>;
    }

    export class Item {
        grantDateNo: KnockoutObservable<number>;
        months: KnockoutObservable<number>;
        years: KnockoutObservable<number>;
        grantedDays: KnockoutObservable<number>;

        constructor(param: IItem) {
            var self = this;
            self.grantDateNo = ko.observable(param.grantDateNo());
            self.months = ko.observable(param.months());
            self.years = ko.observable(param.years());
            self.grantedDays = ko.observable(param.grantedDays());
        }
    }
}