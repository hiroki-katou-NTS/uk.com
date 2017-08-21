module nts.uk.at.view.kmf004.g.viewmodel {
    
    export class ScreenModel {
        // list grant relationship A2_2
        lstGrantRelationship: KnockoutObservableArray<GrantRelationship>;
        // column in list
        gridListColumns: KnockoutObservableArray<any>;
        // selected code 
        selectedCode: KnockoutObservable<string>;
        // selected item
        selectedOption: KnockoutObservable<GrantRelationship>;    
        // binding to text box name A3_3
        selectedName: KnockoutObservable<string>;
        // binding to text box code A3_2         
        codeObject: KnockoutObservable<string>;
        // check new mode or not
        check: KnockoutObservable<boolean>;
        // check update or insert
        checkUpdate: KnockoutObservable<boolean>;
        // check enable delete button
        checkDelete: KnockoutObservable<boolean>;
        // check column
        isAlreadySetting: KnockoutObservable<boolean>;
        constructor() {
            let self = this;
            self.gridListColumns = ko.observableArray([
                { headerText: nts.uk.resource.getText("KMF004_7"), key: 'relationshipCode', width: 100 },
                { headerText: nts.uk.resource.getText("KMF004_8"), key: 'relationshipName', width: 200, formatter: _.escape},
                { headerText: nts.uk.resource.getText("KMF004_129"), width: 100 }
            ]);
            self.lstGrantRelationship = ko.observableArray([]);
            self.selectedCode = ko.observable("");
            self.selectedName = ko.observable("");
            self.selectedOption = ko.observable(null);
            self.check = ko.observable(false);
            self.codeObject = ko.observable("");
            self.checkUpdate = ko.observable(true);
            self.checkDelete = ko.observable(true);
            self.
            self.selectedCode.subscribe((value) => {
                if (value) {
                    let foundItem = _.find(self.lstGrantRelationship(), (item: GrantRelationship) => {
                        return item.relationshipCode == value;
                    });
                    self.checkUpdate(true);
                    self.checkDelete(true);
                    self.selectedOption(foundItem);
                    self.selectedName(self.selectedOption().relationshipName);
                    self.codeObject(self.selectedOption().relationshipCode)
                    self.check(false);
                }
            });
            
        }

        /** get data to list **/
        getData(): JQueryPromise<any>{
            let self = this;
            let dfd = $.Deferred();
            service.findAll().done((lstData: Array<viewmodel.GrantRelationship>) => {
                let sortedData = _.orderBy(lstData, ['relationshipCode'], ['asc']);
                self.lstGrantRelationship(sortedData);
                dfd.resolve();
            }).fail(function(error){
                    dfd.reject();
                    alert(error.message);
                }) 
              return dfd.promise();      
        }

        /** get data when start dialog **/
        startPage(): JQueryPromise<any> {
            let self = this;
            let dfd = $.Deferred();
            let array=[];
            let list=[];
            self.getData().done(function(){
                if(self.lstGrantRelationship().length == 0){
                    self.clearFrom();
                    self.checkDelete(false);
                }
                else{
                    self.selectedCode(self.lstGrantRelationship()[0].relationshipCode);
                }
                
                dfd.resolve();
            });
            return dfd.promise();
        }  
        
        /** update or insert data when click button register **/
        register() {
            let self = this;
            let code = "";  
            $("#inpPattern").trigger("validate");
            let updateOption = new GrantRelationship(self.selectedCode(), self.selectedName()); 
            code = self.codeObject();
            _.defer(() => {
                if (nts.uk.ui.errors.hasError() === false) {
                    // update item to list  
                    if(self.checkUpdate() == true){
                        service.update(updateOption).done(function(){
                            self.getData().done(function(){
                                self.selectedCode(code);
                                nts.uk.ui.dialog.info({ messageId: "Msg_15" }); 
                            });
                        });
                    }
                    else{
                        code = self.codeObject();
                        self.selectedOption(null);
                        let obj = new GrantRelationship(self.codeObject(), self.selectedName());
                        // insert item to list
                        service.insert(obj).done(function(){
                            self.getData().done(function(){
                                nts.uk.ui.dialog.info({ messageId: "Msg_15" });
                                self.selectedCode(code);  
                            });
                        }).fail(function(res){
                            $('#inpCode').ntsError('set', res);
                        });
                    }
                }
            });    
            $("#inpPattern").focus();        
        } 
        //  new mode 
        newMode(){ 
            let self = this;
            $("#inpCode").ntsError('clear');
            self.clearFrom();
            self.checkDelete(false);
        }
        
        clearFrom() {
            let self = this;
            self.check(true);
            self.checkUpdate(false);
            self.selectedCode("");
            self.codeObject("");
            self.selectedName("");
            $("#inpCode").focus(); 
            nts.uk.ui.errors.clearAll();                 
        }
        
        /** remove item from list **/
        remove(){
            let self = this;
            let count = 0;
            for (let i = 0; i <= self.lstGrantRelationship().length; i++){
                if(self.lstGrantRelationship()[i].relationshipCode == self.selectedCode()){
                    count = i;
                    break;
                }
            }
            nts.uk.ui.dialog.confirm({ messageId: "Msg_18" }).ifYes(() => { 
                service.remove(self.selectedOption()).done(function(){
                    self.getData().done(function(){
                        // if number of item from list after delete == 0 
                        if(self.lstGrantRelationship().length==0){
                            self.newMode();
                            self.checkDelete(false);
                            return;
                        }
                        // delete the last item
                        if(count == ((self.lstGrantRelationship().length))){
                            self.selectedCode(self.lstGrantRelationship()[count-1].relationshipCode);
                            return;
                        }
                        // delete the first item
                        if(count == 0 ){
                            self.selectedCode(self.lstGrantRelationship()[0].relationshipCode);
                            return;
                        }
                        // delete item at mediate list 
                        else if(count > 0 && count < self.lstGrantRelationship().length){
                            self.selectedCode(self.lstGrantRelationship()[count].relationshipCode);    
                            return;
                        }
                    })
                 nts.uk.ui.dialog.info({ messageId: "Msg_16" });
                })
            }).ifCancel(() => {     
            }); 
            $("#inpPattern").focus();
        }
        
        close(){
            var t0 = performance.now();                
            var t1 = performance.now();
            nts.uk.ui.windows.close();
            console.log("Selection process " + (t1 - t0) + " milliseconds.");
        }
        
    }
    export class GrantRelationship{
        specialHolidayCode: number;
        relationshipName: string;
        relationshipCode: string; 
        grantRelationshipDay: number;
        morningHour: number; 
        constructor(specialHolidayCode: number, relationshipName: string, relationshipCode: string, grantRelationshipDay: number, morningHour: number){
            this.specialHolidayCode = specialHolidayCode;
            this.relationshipName = relationshipName;
            this.relationshipCode = relationshipCode;
            this.grantRelationshipDay = grantRelationshipDay;
            this.morningHour = morningHour;
        }
    }
}




