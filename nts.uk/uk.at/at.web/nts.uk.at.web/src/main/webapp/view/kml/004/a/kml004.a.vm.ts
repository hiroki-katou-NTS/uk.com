import setSharedA = nts.uk.ui.windows.setShared;
import getSharedA = nts.uk.ui.windows.getShared;
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
        // cal day set object selected send to dialog B
        calSetObject: KnockoutObservable<CalDaySet>;
        //  cal day set received from dialog B
        calSetReceive: KnockoutObservable<CalDaySet>;
        // cnt set received from dialog D
        cntSetls: KnockoutObservableArray<any>;
        // list D screen send to A screen
        cntReceived: KnockoutObservableArray<any>;
        // list cnt set 
        cntSetAll: KnockoutObservableArray<any>;
        // list id 21: 回数集計1 & id 22: 回数集計2
        id2122: KnockoutObservableArray<any>;
        
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
                { headerText: nts.uk.resource.getText(""), key: 'totalItemName', width: 90, unbound: true, dataType: "string", 
                     template : '{{if ${totalItemNo} == "3" || ${totalItemNo} == "21" || ${totalItemNo} == "22"}} <button class="setting" onclick="openDlg(this)" data-code="${totalItemNo}" data-name="${totalItemName}" style="margin-left: 7px;">設定</button> {{/if}}',    
                }
            ]);   
            
            self.lstCate = ko.observableArray([]);
            self.selectedCode = ko.observable("");
            let param = {
                categoryCode: "",
                categoryName: "",
                memo: "",
                totalEvalOrders: [],
            }
            self.selectedOption = ko.observable(new TotalCategory(param));
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
            self.calSetReceive = ko.observable(null);
            self.cntSetls = ko.observableArray(null);
            self.cntReceived = ko.observableArray([]);
            self.cntSetAll = ko.observableArray([]);
            self.id2122 = ko.observableArray([]);
            
            self.selectedCode.subscribe((value) => {
                self.list([]);
                self.items([]);
                let lstTemp = [];
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
                        if(self.newItems()[k] != undefined){
                            for(let i = 0; i<self.listEval().length; i++){
                                if(self.newItems()[k].totalItemNo == self.listEval()[i].totalItemNo){
                                    self.list().push(self.listEval()[i]);    
                                }  
                            }
                        }
                    }
                    self.newItems(self.list());
                    var totalItemNoList = _.map(self.newItems(), function(item) { return item.totalItemNo; });
                    self.items(_.filter(self.evalItems(), function(item) {
                       return _.indexOf(totalItemNoList, item.totalItemNo) < 0;   
                    }));
                    self.check(false);
                    self.calSetObject(_.find(self.calDaySetList(), function(a){
                        return (a.categoryCode == self.selectedOption().categoryCode() && a.totalItemNo == 3);
                    }));
                    
                    self.id2122(_.filter(self.cntSetAll(), function(obj){
                        return (obj.categoryCode == self.selectedOption().categoryCode() && (obj.totalItemNo == 21 || obj.totalItemNo == 22));
                    }))
                    
                    _.map(foundItem.totalEvalOrders, function(item){
                         if(item != undefined){
                            lstTemp.push({
                                categoryCode: item.categoryCode,
                                totalItemNo: item.totalItemNo,
                                dispOrder: item.dispOrder,
                                horiCalDaysSet: self.calSetObject(),                            
                                cntSetls: self.id2122(), 
                            }) 
                         } 
                    })
                    foundItem.totalEvalOrders = lstTemp;
                    self.selectedOption(new TotalCategory(foundItem));
//                    foundItem.totalEvalOrders.horiCalDaysSet = self.calSetObject();
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
            let array = [];
            service.getItem().done((lstItem) => {
                if(lstItem.length == 0){
                    nts.uk.ui.dialog.info({ messageId: "Msg_458" });    
                } else {
                    let sortedData = _.orderBy(lstItem, ['totalItemNo'], ['asc']);
                    _.forEach(sortedData, function(item: EvalOrder){
                        self.items.push(item);
                        self.evalItems.push(item);
                        self.listEval.push(item);
                    });
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
        
        /** get all cnt set */
        getCNT():JQueryPromise<any>{
            let self = this;
            let dfd = $.Deferred();
            service.getCNT().done((lstCnt) => {
                self.cntSetAll(lstCnt); 
                dfd.resolve();
            });
            return dfd.promise();
        }
        
        /** get data when start dialog **/
        startPage(): JQueryPromise<any> {
            let self = this;
            let dfd = $.Deferred();
            let array=[];
            $.when(self.getCal(), self.getCNT(), self.getEvalItem(), self.getData()).done(function(){
                if(self.checkUpdate() == true){
                    
                }
                if(self.lstCate().length == 0){
                    self.clearForm();
                    self.checkDelete(false);  
                }else{
                    self.selectedCode(self.lstCate()[0].categoryCode);
                    _.map(self.lstCate(), function(item) {
                        array.push({
                            categoryCode: item.categoryCode,
                            categoryName: item.categoryName,
                            memo: item.memo,
                            totalEvalOrders: item.totalEvalOrders
                        });
                    }); 
                    
                    self.lstCate(array); 
                }
                dfd.resolve();
            }).fail(function() {
                dfd.reject();    
            });
            return dfd.promise();
        }
        
        /** update or insert data when click button register **/
        register() {
            let self = this;
            let code = "";  
            let temp = [];
            let array = [];
            let part = [];
            let pic = null;
            $("#code-text").trigger("validate");
            $("#nameCtg").trigger("validate");
            
            for(let i = 0; i < self.newItems().length; i++){
                self.newItems()[i].dispOrder = i;
                let tam = self.selectedOption().totalEvalOrders();
                if(self.cntSetls().length > 0){
                    for(let k = 0; k < self.cntSetls().length; k ++){
                        if(self.cntSetls()[k].totalItemNo == self.newItems()[i].totalItemNo){
                            array.push(self.cntSetls()[k]);
                        }    
                    }
                    self.newItems()[i].cntSetls = array;
                    if(tam != null && tam.length > 0){
                        tam[i].cntcntSetls = array;
                        self.newItems(tam);
                    }
                    array =[];
                    if(self.calSetReceive() != null && (self.newItems()[i].totalItemNo == self.calSetReceive().totalItemNo)){
                        self.newItems()[i].horiCalDaysSet = self.calSetReceive();   
                    } 
                }
                else if(self.calSetReceive() != null && (self.newItems()[i].totalItemNo == self.calSetReceive().totalItemNo)){
                    self.newItems()[i].horiCalDaysSet = self.calSetReceive();   
                    if(self.cntSetls().length > 0){
                        for(let k = 0; k < self.cntSetls().length; k ++){
                            if(self.cntSetls()[k].totalItemNo == self.newItems()[i].totalItemNo){
                                array.push(self.cntSetls()[k]);
                            }    
                        }
                        self.newItems()[i].cntSetls = array;
                        if(tam != null && tam.length > 0){
                            tam[i].cntcntSetls = array;
                            self.newItems(tam);
                        }
                        array =[];
                        if(self.calSetReceive() != null && (self.newItems()[i].totalItemNo == self.calSetReceive().totalItemNo)){
                            self.newItems()[i].horiCalDaysSet = self.calSetReceive();   
                        } 
                    }
                } 
                else {
                    self.newItems(self.selectedOption().totalEvalOrders()); 
                }
            }
            
            _.map(self.newItems(), function(item) {
                part.push({
                    categoryCode: self.selectedOption().categoryCode(),
                    totalItemNo: item.totalItemNo,
                    totalItemName: item.totalItemName,
                    dispOrder: item.dispOrder,
                    horiCalDaysSet: item.horiCalDaysSet,
                    cntSetls: item.cntSetls,   
                });
            });
            self.newItems(part);
           
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
                                self.selectedCode(null);
                                self.selectedCode(param.categoryCode);
                                nts.uk.ui.dialog.info({ messageId: "Msg_15" }); 
                                 $("#nameCtg").focus();
                            });
                        }).fail(function(res){
                            nts.uk.ui.dialog.alertError(res.message);
                        });
                    }
                    else{
                        // insert item to list
                        service.add(ko.toJS(param)).done(function(){
                            self.lstCate([]);
                            self.getData().done(function(){
                                nts.uk.ui.dialog.info({ messageId: "Msg_15" });
                                self.selectedCode(null);
                                self.selectedCode(param.categoryCode); 
                                 $("#nameCtg").focus(); 
                            });
                        }).fail(function(res){
                            $('#code-text').ntsError('set', res);
                        });
                    }
                }
            });    
            self.cntReceived([]);     
        } 
        //  new mode  
        newMode(){               
            let self = this;
            let dfd = $.Deferred();
            $("#code-text").ntsError('clear');  
            self.clearForm();
            self.checkDelete(false);
            self.items(self.items());
            self.newItems([]);
            service.getItem().done((lstItem) => {
                if(lstItem.length == 0){
                    nts.uk.ui.dialog.info({ messageId: "Msg_458" });
                     nts.uk.ui.windows.close();    
                } else {
                    let sortedData = _.orderBy(lstItem, ['totalItemNo'], ['asc']);
                    _.forEach(sortedData, function(item: EvalOrder){
                        self.items.push(item);
                    });
                }
                dfd.resolve();
            });
            return dfd.promise();
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
        
        /** click close button **/
        close(){
            var t0 = performance.now();               
            nts.uk.ui.windows.close();
            var t1 = performance.now();
            console.log("Selection process " + (t1 - t0) + " milliseconds.");
        } 
        
        /** click 設定 button **/
        openBDialog() {
            let self = this;
            nts.uk.ui.windows.sub.modal('/view/kml/004/b/index.xhtml').onClosed(function(): any {
               self.calSetReceive(getSharedA("KML004B_DAY_SET")); 
            });
        }
           
        /** click 設定 button **/
        openDDialog() {
            let self = this;
            setSharedA('KML004A_CNT_SET_CD', self.selectedOption().categoryCode());
            nts.uk.ui.windows.sub.modal('/view/kml/004/d/index.xhtml').onClosed(function(): any {
                var sets = getSharedA("KML004D_CNT_SET");
                for (let k = 0; k< self.cntReceived().length; k++){
                    for(let a = 0; a < sets.length; a++){
                        _.remove(self.cntReceived(), function(n){
                            return (n.categoryCode == sets[a].categoryCode && n.totalItemNo == sets[a].totalItemNo); 
                        });     
                    }
                }
                for(let i = 0; i < sets.length; i ++){
                    self.cntReceived().push(sets[i]);    
                }
                self.cntSetls([]);
                _.map(self.cntReceived(), function(item) {
                    self.cntSetls.push({
                        categoryCode: item.categoryCode,
                        totalItemNo: item.totalItemNo,
                        totalTimeNo: item.totalTimeNo     
                    });
                });    
            });
        }    
        
        openDialog(id, name) {
            var self = this;
            if ("3" == id) {
                self.calSetObject(_.find(self.calDaySetList(), function(a){
                    return (a.categoryCode == self.selectedOption().categoryCode() && a.totalItemNo == id);
                }));
                
                if(self.calSetObject() == undefined || self.checkUpdate()==false || self.calSetObject() == null){
                    self.calSetObject(new CalDaySet(self.selectedOption().categoryCode(), id, 0, 0, 0, 0));
                }
                
                setSharedA('KML004A_DAY_SET', self.calSetObject());   
                self.openBDialog();
            } else if("21" == id || "22" == id) {
                setSharedA('KML004A_CNT_SET_ID', id);
                self.openDDialog();
            }
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
    
    
    export interface IEvalOrder{
        categoryCode: string;
        totalItemNo: number;
        totalItemName: string;
        dispOrder: number;
        horiCalDaysSet: CalDaySet;
        cntSetls: Array<any>;
    }
    
    export class EvalOrder{
        categoryCode: string;
        totalItemNo: number;
        totalItemName: string;
        dispOrder: number;
        horiCalDaysSet: KnockoutObservable<any> = ko.observable(null);
        cntSetls: KnockoutObservableArray<any> = ko.observableArray([]);
        constructor(param : IEvalOrder){
            this.categoryCode = param.categoryCode;
            this.totalItemNo = param.totalItemNo;
            this.totalItemName = param.totalItemName;
            this.dispOrder = param.dispOrder;
            this.horiCalDaysSet(param.horiCalDaysSet);
            this.cntSetls(param.cntSetls);     
        }
    }
    
    export class CalDaySet{
        categoryCode: string;
        totalItemNo: number;
        halfDay: number;
        yearHd: number;
        specialHoliday: number;
        heavyHd: number;
        constructor(categoryCode: string, totalItemNo: number,  halfDay: number, yearHd: number, specialHoliday: number, heavyHd: number){
            this.categoryCode = categoryCode;
            this.totalItemNo = totalItemNo;
            this.halfDay = halfDay;
            this.yearHd = yearHd;
            this.specialHoliday = specialHoliday; 
            this.heavyHd = heavyHd;
        }  
    }
}

function openDlg(element){
    var itemNo = $(element).data("code");
    var itemName = $(element).data("name");
    nts.uk.ui._viewModel.content.openDialog(itemNo, itemName);
}



