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
        items: KnockoutObservableArray<EvalOrder>;
        // column order in the right
        newColumns: KnockoutObservableArray<any>;
        // columns in the left
        columns : KnockoutObservableArray<any>;
        // selected in the left list
        currentCodeList: KnockoutObservableArray<number>;
        // selected in the right list
        newCurrentCodeList: KnockoutObservableArray<number>;
        // list in the right
        newItems: KnockoutObservableArray<EvalOrder>;
        // list trung gian lay name tu list trai sang list phai
        list: KnockoutObservableArray<EvalOrder>;
        // list ben trai sau khi loai bo nhung phan tu trong list phai
        evalItems: KnockoutObservableArray<EvalOrder>;
        // list co dinh ben trai lay tu DB
        listEval: KnockoutObservableArray<EvalOrder>;
        // list CalDaySet
        calDaySetList: KnockoutObservableArray<CalDaySet>;
        // cal day set object selected
        calSetObject: KnockoutObservable<CalDaySet>;
        
        constructor() {
            let self = this;
            self.gridListColumns = ko.observableArray([
                { headerText: nts.uk.resource.getText("KML004_6"), key: 'categoryCode', width: 50 },
                { headerText: nts.uk.resource.getText("KML004_7"), key: 'categoryName', width: 250, formatter: _.escape}
            ]);
            
            self.columns = ko.observableArray([
                { headerText: nts.uk.resource.getText("KML004_14"), key: 'totalItemNo', width: 70 },
                { headerText: nts.uk.resource.getText("KML004_15"), key: 'totalItemName', width: 150, formatter: _.escape}
            ]);
            
            self.newColumns = ko.observableArray([
                { headerText: nts.uk.resource.getText("KML004_17"), key: 'totalItemNo', width: 70 },
                { headerText: nts.uk.resource.getText("KML004_18"), key: 'totalItemName', width: 150},
                { headerText: nts.uk.resource.getText(""), key: 'totalItemName', width: 70}
            ]);   
            
            self.lstCate = ko.observableArray([]);
            self.selectedCode = ko.observable("");
            self.selectedOption = ko.observable(null);
            self.check = ko.observable(false);
            self.checkUpdate = ko.observable(true);
            self.checkDelete = ko.observable(true);
            self.calSetObject = ko.observable(null);
            self.items = ko.observableArray([]);
            self.currentCodeList = ko.observableArray([]);
            self.newCurrentCodeList = ko.observableArray([]);
            self.newItems = ko.observableArray([]);
            self.list = ko.observableArray([]);
            self.evalItems = ko.observableArray([]);
            self.listEval = ko.observableArray([]);
            self.calDaySetList = ko.observableArray([]);
            self.selectedCode.subscribe((value) => {
                self.list([]);
                if (value) {
                    let foundItem = _.find(self.lstCate(), (item: ITotalCategory) => {
                        return item.categoryCode == value;
                    });
                    nts.uk.ui.errors.clearAll();
                    self.checkUpdate(true);
                    self.checkDelete(true);
                    self.selectedOption(new TotalCategory(foundItem));
                    self.newItems(self.selectedOption().totalEvalOrders());
                    for(let k = 0; k<self.newItems().length; k++){
                        for(let i = 0; i<self.listEval().length; i++){
                            if(self.listEval()[i].totalItemNo == self.newItems()[k].totalItemNo){
                                self.list().push(self.listEval()[i]);    
                            }    
                        }
                    }
                    self.newItems(self.list());
                    var totalItemNoList = _.map(self.newItems(), function(item) { return item.totalItemNo; });
                    self.items(_.filter(self.evalItems(), function(item) {
                       return _.indexOf(totalItemNoList, item.totalItemNo) < 0;   
                    }));
                    _.forEach(self.calDaySetList(), function(a){
                        if(a.categoryCode == value){
                            self.calSetObject(a);    
                        }
                    })
                    console.log(self.calSetObject());
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
                dfd.resolve();
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
                _.forEach(sortedData, function(item: EvalOrder){
                    self.items.push(item);
                    self.evalItems.push(item);
                    self.listEval.push(item);
                });
                if(lstItem.length == 0){
                    nts.uk.ui.dialog.info({ messageId: "Msg_458" });    
                }
                dfd.resolve();
            });
            return dfd.promise();
        }

        /** get cal day set */
        getCal(): JQueryPromise<any>{
            let self = this;
            let dfd = $.Deferred();
            service.getSet().done((lstSet) => {
                self.calDaySetList(lstSet); 
                dfd.resolve();
            });
            return dfd.promise();
        }
        
        /** get data when start dialog **/
        startPage(): JQueryPromise<any> {
            let self = this;
            let dfd = $.Deferred();
            let array=[];
            let list=[];
            self.getCal().done(function(data3){
                self.getEvalItem().done(function(data1){
                    self.getData().done(function(data2){
                        if(self.lstCate().length == 0){
                            self.clearForm();
                            self.checkDelete(false);  
                        }
                        else{
                            self.selectedCode(self.lstCate()[0].categoryCode);
                        }
                        dfd.resolve();
                    });
                    dfd.resolve();
                });
                dfd.resolve();
            });
            return dfd.promise();
        }
        
        /** change item from left list to right list */
        add(){           
            let self = this;
            let arr = self.items();
            let lst = [];
            _.forEach(self.currentCodeList(), function(item) {
                let a = _.find(self.items(), function(o){
                    return o.totalItemNo == parseInt(item);
                });
                let convert = new EvalOrder(self.selectedOption().categoryCode(), a.totalItemNo, a.totalItemName, 1);
                self.newItems().push(convert);
                _.remove(arr, function(n){
                     return n.totalItemNo == parseInt(item);
                });  
            });
            self.items(arr);
            for(let i = 0; i<self.newItems().length; i++){
                self.newItems()[i].dispOrder = i;    
            }
            self.currentCodeList([]);
            self.newCurrentCodeList([]);
        }
        
        /** change item from right list to left list */
        del(){
            let self = this;
            let arr = self.newItems();
            _.forEach(self.newCurrentCodeList(), function(item){
                let a = _.find(self.newItems(), function(o){
                    return o.totalItemNo == parseInt(item);    
                });
                self.items.push(a);
                _.remove(arr, function(n){
                    return n.totalItemNo == parseInt(item); 
                    nts.uk.ui.windows.close();   
                }); 
            });
            self.newItems(arr);    
            self.currentCodeList([]);
            self.newCurrentCodeList([]);
        }
        
        /** update or insert data when click button register **/
        register() {
            let self = this;
            let code = "";  
            $("#code-text").trigger("validate");
            $("#name-text").trigger("validate");
            let param: ITotalCategory ={
                categoryCode: self.selectedOption().categoryCode(),
                categoryName: self.selectedOption().categoryName(),
                memo: self.selectedOption().memo(),
                totalEvalOrders: self.newItems(),  
            }
            _.defer(() => {
                if (nts.uk.ui.errors.hasError() === false) {
                    // update item to list  
                    if(self.checkUpdate() == true){
                        service.update(ko.toJS(param)).done(function(){
                            self.lstCate([]);
                            self.getData().done(function(){
                                self.selectedCode(param.categoryCode);
                                nts.uk.ui.dialog.info({ messageId: "Msg_15" }); 
                            });
                        }).fail(function(res){
                            nts.uk.ui.dialog.alertError(res.message);
                        });
                    }
                    else{
                        let obj: ITotalCategory ={
                            categoryCode: self.selectedOption().categoryCode(),
                            categoryName: self.selectedOption().categoryName(),
                            memo: self.selectedOption().memo(),
                            totalEvalOrders: self.selectedOption().totalEvalOrders(),  
                        }
                        // insert item to list
                        service.add(ko.toJS(obj)).done(function(){
                            self.lstCate([]);
                            self.getData().done(function(){
                                nts.uk.ui.dialog.info({ messageId: "Msg_15" });
                                self.selectedCode(param.categoryCode);  
                            });
                        }).fail(function(res){
                            $('#code-text').ntsError('set', res);
                        });
                    }
                }
            });    
//            self.checkUpdate(true);
            $("#name-text").focus();        
        } 
        //  new mode  
        newMode(){               
            let self = this;
            $("#code-text").ntsError('clear');  
            self.clearForm();
            self.checkDelete(false);
        }
        
        clearForm() {
            let self = this;
            self.selectedOption().categoryCode("");
            self.selectedOption().categoryName("");
            self.selectedOption().memo("");
            self.selectedOption().totalEvalOrders(null);
            self.check(true);
            self.checkUpdate(false);
            self.selectedCode("");
            $("#code-text").focus(); 
            nts.uk.ui.errors.clearAll();                 
        }
        
        /** remove item from list **/
        remove(){
            let self = this;
            let count = 0;
            for (let i = 0; i <= self.lstCate().length; i++){
                if(self.lstCate()[i].categoryCode == self.selectedCode()){
                    count = i;
                    break;
                }
            }
            nts.uk.ui.dialog.confirm({ messageId: "Msg_18" }).ifYes(() => { 
                service.remove(ko.toJS(self.selectedOption())).done(function(){
                    self.lstCate([]);
                    self.getData().done(function(){
                        // if number of item from list after delete == 0 
                        if(self.lstCate().length==0){
                            self.newMode();
                            self.checkDelete(false);
                            return;
                        }
                        // delete the last item
                        if(count == ((self.lstCate().length))){
                            self.selectedCode(self.lstCate()[count-1].categoryCode);
                            return;
                        }
                        // delete the first item
                        if(count == 0 ){
                            self.selectedCode(self.lstCate()[0].categoryCode);
                            return;
                        }
                        // delete item at mediate list 
                        else if(count > 0 && count < self.lstCate().length){
                            self.selectedCode(self.lstCate()[count].categoryCode);    
                            return;
                        }
                    })
                 nts.uk.ui.dialog.info({ messageId: "Msg_16" });
                })
            }).ifCancel(() => {     
            }); 
            $("#code-text").focus();
        }
        
        close(){
            var t0 = performance.now();               
            nts.uk.ui.windows.close();
            var t1 = performance.now();
            console.log("Selection process " + (t1 - t0) + " milliseconds.");
        } 
        
        openBDialog() {
            let self = this;
            nts.uk.ui.windows.setShared('KML004B_DAY_SET', self.calSetObject());
            nts.uk.ui.windows.sub.modal('/view/kml/004/b/index.xhtml').onClosed(function(): any {
            });
        }
        
    }   
    
    export interface ITotalCategory{
        categoryCode: string;
        categoryName: string;
        memo: string;
        totalEvalOrders: Array<EvalOrder>;     
    }
    
    export class TotalCategory{
        categoryCode: KnockoutObservable<string>;
        categoryName: KnockoutObservable<string>;
        memo: KnockoutObservable<string>;
        totalEvalOrders: KnockoutObservableArray<any>;
        constructor(param: ITotalCategory){
            let self = this;
            this.categoryCode = ko.observable(param.categoryCode);
            this.categoryName = ko.observable(param.categoryName);
            this.memo = ko.observable(param.memo); 
            this.totalEvalOrders = ko.observableArray(param.totalEvalOrders);
        } 
    }
    
    export class EvalOrder{
        categoryCode: string;
        totalItemNo: number;
        totalItemName: string;
        dispOrder: number;
        constructor(categoryCode: string, totalItemNo: number, totalItemName: string, dispOrder: number){
            this.categoryCode = categoryCode;
            this.totalItemNo = totalItemNo;
            this.totalItemName = totalItemName;
            this.dispOrder = dispOrder;     
        }
    }
    
    export class CalDaySet{
        categoryCode: string;
        halfDay: number;
        yearHd: number;
        specialHoliday: number;
        heavyHd: number;
        constructor(categoryCode: string, halfDay: number, yearHd: number, specialHoliday: number, heavyHd: number){
            this.categoryCode = categoryCode;
            this.halfDay = halfDay;
            this.yearHd = yearHd;
            this.specialHoliday = specialHoliday; 
            this.heavyHd = heavyHd;
        }  
    }
}




