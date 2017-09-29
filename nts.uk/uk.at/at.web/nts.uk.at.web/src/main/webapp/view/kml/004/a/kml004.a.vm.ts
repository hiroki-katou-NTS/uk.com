module nts.uk.at.view.kml004.a.viewmodel {
    
    export class ScreenModel {
        // list Category A2_1
        lstCate: KnockoutObservableArray<ITotalCategory>;
        // column in list
        gridListColumns: KnockoutObservableArray<any>;
        // selected code 
        selectedCode: KnockoutObservable<string>;
        // selected item
        selectedOption: KnockoutObservable<TotalCategory>;    
        // check new mode or not
        check: KnockoutObservable<boolean>;
        // check update or insert
        checkUpdate: KnockoutObservable<boolean>;
        // check enable delete button
        checkDelete: KnockoutObservable<boolean>;
        // list eval item in the left
        items: KnockoutObservableArray<EvalItem>;
        // columns in the left
        columns : KnockoutObservableArray<any>;
        // selected in the left list
        currentCodeList: KnockoutObservable<number>;
        constructor() {
            let self = this;
            self.gridListColumns = ko.observableArray([
                { headerText: nts.uk.resource.getText("KML004_6"), key: 'categoryCode', width: 50 },
                { headerText: nts.uk.resource.getText("KML004_7"), key: 'categoryName', width: 250, formatter: _.escape}
            ]);
            
            self.columns = ko.observableArray([
                { headerText: nts.uk.resource.getText("KML004_14"), key: 'totalItemNo', width: 50 },
                { headerText: nts.uk.resource.getText("KML004_15"), key: 'totalItemName', width: 250, formatter: _.escape}
            ]);
            
            self.lstCate = ko.observableArray([]);
            self.selectedCode = ko.observable("");
            self.selectedOption = ko.observable(null);
            self.check = ko.observable(false);
            self.checkUpdate = ko.observable(true);
            self.checkDelete = ko.observable(true);
            self.items = ko.observableArray([]);
            self.currentCodeList = ko.observable(null);
            self.selectedCode.subscribe((value) => {
                if (value) {
                    let foundItem = _.find(self.lstCate(), (item: ITotalCategory) => {
                        return item.categoryCode == value;
                    });
                    self.checkUpdate(true);
                    self.checkDelete(true);
                    self.selectedOption(new TotalCategory(foundItem));
                    self.check(false);
                }
            });
        }

        /** get data to list **/
        getData(): JQueryPromise<any>{
            let self = this;
            let dfd = $.Deferred();
            service.getAll().done((lstData) => {
                let sortedData = _.orderBy(lstData, ['totalItemNo'], ['asc']);
                _.forEach(sortedData, function(item: ITotalCategory){
                    self.lstCate.push(item);
                });
                console.log(self.lstCate());
//                dfd.resolve();
            }).fail(function(error){
                    dfd.reject();
                    alert(error.message);
                }) 
              return dfd.promise();      
        }
        
        /** get list eval item **/
        getEvalItem(): JQueryPromise<any>{
            let self = this;
            let dfd = $.Deferred();
            service.getItem().done((lstItem) => {
                let sortedData = _.orderBy(lstItem, ['totalItemNo'], ['asc']);
                _.forEach(sortedData, function(item: EvalItem){
                    self.items.push(item);
                });
            });
            return dfd.promise();
        }

        /** get data when start dialog **/
        startPage(): JQueryPromise<any> {
            let self = this;
            let dfd = $.Deferred();
            let array=[];
            let list=[];
            self.getEvalItem().done(function(){
                self.currentCodeList(self.items()[0].totalItemNo);  
                // get list category
                self.getData().done(function(){
                    if(self.lstCate().length == 0){
                        self.clearFrom();
                        self.checkDelete(false);  
                    }
                    else{
                        self.selectedCode(self.lstCate()[0].categoryCode);
                    }
                    dfd.resolve();
                });
            });
            return dfd.promise();
        }  
        
        /** update or insert data when click button register **/
        register() {
//            let self = this;
//            let code = "";  
//            $("#inpPattern").trigger("validate");
//            let updateOption = new Relationship(self.selectedCode(), self.selectedName()); 
//            code = self.codeObject();
//            _.defer(() => {
//                if (nts.uk.ui.errors.hasError() === false) {
//                    // update item to list  
//                    if(self.checkUpdate() == true){
//                        service.update(updateOption).done(function(){
//                            self.getData().done(function(){
//                                self.selectedCode(code);
//                                nts.uk.ui.dialog.info({ messageId: "Msg_15" }); 
//                            });
//                        });
//                    }
//                    else{
//                        code = self.codeObject();
//                        self.selectedOption(null);
//                        let obj = new Relationship(self.codeObject(), self.selectedName());
//                        // insert item to list
//                        service.insert(obj).done(function(){
//                            self.getData().done(function(){
//                                nts.uk.ui.dialog.info({ messageId: "Msg_15" });
//                                self.selectedCode(code);  
//                            });
//                        }).fail(function(res){
//                            $('#inpCode').ntsError('set', res);
//                        });
//                    }
//                }
//            });    
//            $("#inpPattern").focus();        
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
            self.selectedOption(null);
            self.check(true);
            self.checkUpdate(false);
            self.selectedCode("");
            $("#inpCode").focus(); 
            nts.uk.ui.errors.clearAll();                 
        }
        
        /** remove item from list **/
        remove(){
//            let self = this;
//            let count = 0;
//            for (let i = 0; i <= self.lstCate().length; i++){
//                if(self.lstCate()[i].relationshipCode == self.selectedCode()){
//                    count = i;
//                    break;
//                }
//            }
//            nts.uk.ui.dialog.confirm({ messageId: "Msg_18" }).ifYes(() => { 
//                service.remove(self.selectedOption()).done(function(){
//                    self.getData().done(function(){
//                        // if number of item from list after delete == 0 
//                        if(self.lstCate().length==0){
//                            self.newMode();
//                            self.checkDelete(false);
//                            return;
//                        }
//                        // delete the last item
//                        if(count == ((self.lstCate().length))){
//                            self.selectedCode(self.lstCate()[count-1].relationshipCode);
//                            return;
//                        }
//                        // delete the first item
//                        if(count == 0 ){
//                            self.selectedCode(self.lstCate()[0].relationshipCode);
//                            return;
//                        }
//                        // delete item at mediate list 
//                        else if(count > 0 && count < self.lstCate().length){
//                            self.selectedCode(self.lstCate()[count].relationshipCode);    
//                            return;
//                        }
//                    })
//                 nts.uk.ui.dialog.info({ messageId: "Msg_16" });
//                })
//            }).ifCancel(() => {     
//            }); 
//            $("#inpPattern").focus();
        }
        
        close(){
            var t0 = performance.now();               
            nts.uk.ui.windows.close();
            var t1 = performance.now();
            console.log("Selection process " + (t1 - t0) + " milliseconds.");
        }
        
    }
    
    export interface ITotalCategory{
        categoryCode: string;
        categoryName: string;
        memo: string;     
    }
    
    export class TotalCategory{
        categoryCode: KnockoutObservable<string>;
        categoryName: KnockoutObservable<string>;
        memo: KnockoutObservable<string>;
        constructor(param: ITotalCategory){
            let self = this;
            this.categoryCode = ko.observable(param.categoryCode);
            this.categoryName = ko.observable(param.categoryName);
            this.memo = ko.observable(param.memo);   
        } 
    }
    
    export class EvalItem{
        totalItemNo: number;
        totalItemName: string;
        constructor(totalItemNo: number, totalItemName: string){
            this.totalItemNo = totalItemNo;
            this.totalItemName = totalItemName
        }     
    }
}




