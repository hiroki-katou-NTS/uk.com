module nts.uk.at.view.kmf004.h.viewmodel {
    
    export class ScreenModel {
        // list business type A2_2
        lstRelationship: KnockoutObservableArray<Relationship>;
        // column in list
        gridListColumns: KnockoutObservableArray<any>;
        // selected code 
        selectedCode: KnockoutObservable<string>;
        // selected item
        selectedOption: KnockoutObservable<Relationship>;    
        // binding to text box name A3_3
        selectedName: KnockoutObservable<string>;
        // binding to text box code A3_2
        codeObject: KnockoutObservable<string>;
        // check new mode or not
        check: KnockoutObservable<boolean>;
        // check update or insert
        checkUpdate: KnockoutObservable<boolean>;
        constructor() {
            let self = this;
            self.gridListColumns = ko.observableArray([
                { headerText: nts.uk.resource.getText("KMF004_7"), key: 'relationshipCd', width: 100 },
                { headerText: nts.uk.resource.getText("KMF004_8"), key: 'relationshipName', width: 200, formatter: _.escape}
            ]);
            self.lstRelationship = ko.observableArray([]);
            self.selectedCode = ko.observable("");
            self.selectedName = ko.observable("");
            self.selectedOption = ko.observable(null);
            self.check = ko.observable(false);
            self.codeObject = ko.observable("");
            self.checkUpdate = ko.observable(true);
            self.selectedCode.subscribe((relationshipCd) => {
                if (relationshipCd) {
                    let foundItem = _.find(self.lstRelationship(), (item: Relationship) => {
                        return item.relationshipCd == relationshipCd;
                    });
                    self.checkUpdate(true);
                    self.selectedOption(foundItem);
                    self.selectedName(self.selectedOption().relationshipName);
                    self.codeObject(self.selectedOption().relationshipCd)
                    self.check(false);
                }
            });
            self.startPage();
        }

        /** get data to list **/
        getData(): JQueryPromise<any>{
            let self = this;
            let dfd = $.Deferred();
            service.getAll().done((lstData: Array<viewmodel.Relationship>) => {
                let sortedData = _.orderBy(lstData, ['relationshipCd'], ['asc']);
                self.lstRelationship(sortedData);
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
                if(self.lstRelationship().length == 0){
                self.newMode();
                return;
                }
                else{
                    self.selectedCode(self.lstRelationship()[0].relationshipCd.toString());
                }
            });
            return dfd.promise();
        }  
        
        /** update or insert data when click button register **/
        register() {
            let self = this;
            let code = "";  
            $("#inpPattern").trigger("validate");
            let updateOption = new Relationship(self.selectedCode(), self.selectedName()); 
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
                        let obj = new Relationship(self.codeObject(), self.selectedName());
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
            var t0 = performance.now(); 
            let self = this;
            self.check(true);
            self.checkUpdate(false);
            self.selectedCode("");
            self.codeObject("");
            self.selectedName("");
            $("#inpCode").focus(); 
            $("#inpCode").ntsError('clear');
            nts.uk.ui.errors.clearAll();                 
            var t1 = performance.now();
            console.log("Selection process " + (t1 - t0) + " milliseconds.");
        }
        /** remove item from list **/
        remove(){
            let self = this;
            let count = 0;
            for (let i = 0; i <= self.lstRelationship().length; i++){
                if(self.lstRelationship()[i].relationshipCd == self.selectedCode()){
                    count = i;
                    break;
                }
            }
            nts.uk.ui.dialog.confirm({ messageId: "Msg_18" }).ifYes(() => { 
                service.remove(self.selectedOption()).done(function(){
                    self.getData().done(function(){
                        // if number of item from list after delete == 0 
                        if(self.lstRelationship().length==0){
                            self.newMode();
                            return;
                        }
                        // delete the last item
                        if(count == ((self.lstRelationship().length))){
                            self.selectedCode(self.lstRelationship()[count-1].relationshipCd);
                            return;
                        }
                        // delete the first item
                        if(count == 0 ){
                            self.selectedCode(self.lstRelationship()[0].relationshipCd);
                            return;
                        }
                        // delete item at mediate list 
                        else if(count > 0 && count < self.lstRelationship().length){
                            self.selectedCode(self.lstRelationship()[count].relationshipCd);    
                            return;
                        }
                    })
                })
                nts.uk.ui.dialog.info({ messageId: "Msg_16" });
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
    export class Relationship{
        relationshipCd: string;
        relationshipName: string;  
        constructor(relationshipCd: string, relationshipName: string){
            this.relationshipCd = relationshipCd;
            this.relationshipName =relationshipName;
        }
    }
}




