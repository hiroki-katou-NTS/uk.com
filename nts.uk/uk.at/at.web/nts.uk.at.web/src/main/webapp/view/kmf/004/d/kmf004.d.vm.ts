module nts.uk.at.view.kmf004.d.viewmodel {
    export class ScreenModel {
        // radio box
        itemList: KnockoutObservableArray<any>;
        // selected radio box
        selectedId: KnockoutObservable<number>;
        display: KnockoutObservable<boolean>;
        // list business type A2_2
        lstPer: KnockoutObservableArray<Per>;
        // column in list
        gridListColumns: KnockoutObservableArray<any>;
        // selected code 
        selectedCode: KnockoutObservable<string>;
        // selected item
        selectedOption: KnockoutObservable<Per>;
        // binding to text box name A3_3
        selectedName: KnockoutObservable<string>;
        // binding to text box code A3_2
        codeObject: KnockoutObservable<string>;
        // check new mode or not
        check: KnockoutObservable<boolean>;
        // check update or insert
        checkUpdate: KnockoutObservable<boolean>;
        // year month date
        items: KnockoutObservableArray<Item>;
        provisionCheck: KnockoutObservable<boolean>;
        editMode: KnockoutObservable<boolean>;
        provisionDeactive: KnockoutObservable<boolean>;
        
        constructor() {
            let self = this;
            self.itemList = ko.observableArray([
                    new BoxModel(0, nts.uk.resource.getText("KMF004_95")),
                    new BoxModel(1, nts.uk.resource.getText("KMF004_96"))
                ]);
            self.gridListColumns = ko.observableArray([
                { headerText: nts.uk.resource.getText("KMF004_7"), key: 'yearServiceCode', width: 60 },
                { headerText: nts.uk.resource.getText("KMF004_8"), key: 'yearServiceName', width: 160, formatter: _.escape}
            ]);
            self.provisionCheck = ko.observable(false);
            self.provisionDeactive = ko.observable(true);
            self.selectedId = ko.observable(0);
            self.lstPer = ko.observableArray([]);
            self.selectedCode = ko.observable("");
            self.selectedName = ko.observable("");
            self.selectedOption = ko.observable(null);
            self.check = ko.observable(false);
            self.codeObject = ko.observable("");
            self.checkUpdate = ko.observable(false);
            self.display = ko.observable(false);
            self.items = ko.observableArray([]);
            self.editMode = ko.observable(true); 
            self.selectedCode.subscribe((code) => {   
                if (code) {
                    let foundItem: Per = _.find(self.lstPer(), (item: Per) => {
                        return (ko.toJS(item.yearServiceCode) == code);
                    });
                    self.checkUpdate(true);
                    $("#inpPattern").focus();
                    self.selectedOption(foundItem);
                    self.selectedId(self.selectedOption().yearServiceCls);
                    self.selectedOption().yearServicePerSets;
                    self.items([]);
                    _.forEach(self.selectedOption().yearServicePerSets, o => {
                        self.items.push(ko.mapping.fromJS(o));    
                    });
                    for (let i = 0; i < 20; i++) {
                        if(self.items()[i] == undefined){
                            let t : item = {
                            yearServiceNo: ko.mapping.fromJS(i),
                            month: ko.mapping.fromJS(null),
                            year: ko.mapping.fromJS(null),
                            date: ko.mapping.fromJS(null)
                            }
                            self.items.push(new Item(t));
                        }
                    }
                    self.selectedName(self.selectedOption().yearServiceName);
                    self.codeObject(ko.toJS(self.selectedOption().yearServiceCode));
                    self.check(false);
                    self.provisionCheck(self.selectedOption().provision == 1 ? true : false);
                    if(self.selectedOption().provision == 1) {
                        self.provisionDeactive(false);
                    } else {
                        self.provisionDeactive(true);
                    }
                    self.editMode(false);
                    nts.uk.ui.errors.clearAll();   
                }
            });
        }

        /** get data to list **/
        getData(): JQueryPromise<any>{
            let self = this;  
            let dfd = $.Deferred();
            service.getAll(nts.uk.ui.windows.getShared('KMF004D_SPHD_CD')).done((lstData: Array<viewmodel.Per>) => {
                if(lstData.length == 0){
                    self.check(true);
                    self.selectedId(0);
                }
                let sortedData = _.orderBy(lstData, ['yearServiceCode'], ['asc']);
                self.lstPer(sortedData);
                dfd.resolve();
            }).fail(function(error){
                    dfd.reject();
                    $('#inpCode').ntsError('set', error);
                });
              return dfd.promise();      
        }

        /** get data when start dialog **/
        startPage(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            let specialHolidayCode = nts.uk.ui.windows.getShared('KMF004D_SPHD_CD');
            service.getAll(specialHolidayCode).done((lstData: Array<Per>) => {
                nts.uk.ui.errors.clearAll();
                if(lstData.length == 0){
                    self.selectedId(0);
                    self.check(true);
                    self.codeObject(null);
                    self.selectedName(null);
                    self.checkUpdate(false);
                    self.provisionCheck(false);
                    self.provisionDeactive(true);
                    self.items([]);
                    for (let i = 0; i < 20; i++) {
                        if(self.items()[i] == undefined){
                            let t : item = {
                                yearServiceNo: ko.mapping.fromJS(i),
                                month: ko.mapping.fromJS(null),
                                year: ko.mapping.fromJS(null),
                                date: ko.mapping.fromJS(null)
                            }
                            self.items.push(new Item(t));
                        }
                    }
                    
                    $("#inpCode").focus();
                } else {
                    let sortedData : KnockoutObservableArray<any> = ko.observableArray([]);
                    sortedData(_.orderBy(lstData, ['yearServiceCode'], ['asc']));
                     $("#inpPattern").focus();
                    self.lstPer(sortedData());
                    self.selectedOption(self.lstPer()[0]);
                    self.selectedId(self.selectedOption().yearServiceCls);
                    self.selectedCode(ko.toJS(self.lstPer()[0].yearServiceCode));
                    self.selectedName(self.lstPer()[0].yearServiceName);
                    self.provisionCheck(self.lstPer()[0].provision == 1 ? true : false);
                    if(self.lstPer()[0].provision == 1) {
                        self.provisionDeactive(false);
                    } else {
                        self.provisionDeactive(true);
                    }
                    self.codeObject(ko.toJS(self.lstPer()[0].yearServiceCode));                   
                }
                
                dfd.resolve();
            }).fail(function(error) {
                dfd.reject();
                $('#inpCode').ntsError('set', error);
            });
            return dfd.promise();
        }  
        
        getListItems(){
            let self = this;
            let data = self.items();
            let lstReturn = _.filter(data, function(item){
                    return item.date() || item.month() || item.year();
                });
            for(let i = 0; i < lstReturn.length; i++){
                lstReturn[i].yearServiceNo(i);
            }  
            return lstReturn;
        }
        
        /** update or insert data when click button register **/
        register() {   
            nts.uk.ui.block.invisible();
            
            let self = this;
            $("#inpCode").trigger("validate");
            $("#inpPattern").trigger("validate");
            if (nts.uk.ui.errors.hasError()) {
                return;       
            }
            let code = "";    
            var items = _.filter(self.items(), function(tam: item) {
                    return tam.date() || tam.month() || tam.year();
                });
            let listSet = {
                specialHolidayCode: nts.uk.ui.windows.getShared('KMF004D_SPHD_CD'),
                lengthServiceYearAtr: self.selectedId(),
                yearServiceSets: ko.toJS(items)
            }
            let dataTransfer = {
                specialHolidayCode: nts.uk.ui.windows.getShared('KMF004D_SPHD_CD'),
                yearServiceCode: ko.toJS(self.codeObject()), 
                yearServiceName: self.selectedName(),
                provision: self.provisionCheck() ? 1 : 0,
                yearServiceCls: self.selectedId(),
                yearServicePerSets: ko.toJS(self.getListItems()),   
            }
            
            code = self.codeObject();
            
            if(self.lstPer().length <= 0) {
                self.checkUpdate(false);
            }
          
            if (nts.uk.ui.errors.hasError() === false) {
                // update item to list  
                if(self.checkUpdate()){
                    $("#inpPattern").focus(); 
                      
                    service.update(dataTransfer).done(function(errors: Array<string>){
                        self.getData().done(function(){
                            if (errors.length > 0) {
                               self.addListError(errors);
                            } else {
                                nts.uk.ui.dialog.info({ messageId: "Msg_15" }).then(function(){
                                    self.selectedCode(code); 
                                    self.selectedCode.valueHasMutated(); 
                                    $("#inpPattern").focus();
                                });
                            }
                        });
          
                    }).fail(function(res){
                        $('#inpCode').ntsError('set', res);
                    }).always(function(){
                        nts.uk.ui.block.clear();    
                    });
                } else {
                    self.selectedOption(null);
                    // insert item to list
                    service.add(dataTransfer).done(function(errors: Array<string>){
                        self.getData().done(function(){
                            if (errors.length > 0) {
                               self.addListError(errors);
                            }else{
                                nts.uk.ui.dialog.info({ messageId: "Msg_15" }).then(function(){
                                    self.selectedCode(code); 
                                    $("#inpPattern").focus();
                                });
                            }
                        });
                    }).fail(function(res){
                        nts.uk.ui.dialog.alertError({ messageId: "Msg_3" }).then(() => { 
                            $('#inpCode').focus();
                        });
                    }).always(function(){
                        nts.uk.ui.block.clear();    
                    });
                }
            }   
        } 
        
        //  new mode 
        newMode() {
//            var t0 = performance.now(); 
            let self = this;
            self.check(true);
            self.checkUpdate(false);
            $("#inpCode").focus(); 
            self.selectedCode("");
            self.codeObject("");
            self.selectedName("");
            self.items([]);
            self.provisionCheck(false);
            self.provisionDeactive(true);
            self.selectedId(0);
            self.editMode(true);  
            
            for (let i = 0; i < 20; i++) {
                    let t : item = {
                    yearServiceNo: ko.mapping.fromJS(i),
                    month: ko.mapping.fromJS(null),
                    year: ko.mapping.fromJS(null),
                    date: ko.mapping.fromJS(null)
                    }
                    self.items.push(new Item(t));
            }
            
            nts.uk.ui.errors.clearAll();                 
        }
        
        /** remove item from list **/
        remove() {
            let self = this;
            let count = 0;
            for (let i = 0; i <= self.lstPer().length; i++){
                if(ko.toJS(self.lstPer()[i].yearServiceCode) == self.selectedCode()){
                    count = i;
                    break;
                }
            }
            
            // Add error before check remove
            if(self.provisionCheck()) {
                nts.uk.ui.dialog.alertError({ messageId: "Msg_1219" });
                return;
            }
            
            nts.uk.ui.dialog.confirm({ messageId: "Msg_18" }).ifYes(() => { 
                service.remove(self.selectedOption()).done(function(){
                    self.getData().done(function(){
                        // if number of item from list after delete == 0 
                        if(self.lstPer().length==0){
                            self.newMode();
                            return;
                        }
                        // delete the last item
                        if(count == ((self.lstPer().length))){
                            self.selectedCode(ko.toJS(self.lstPer()[count-1].yearServiceCode));
                            return;
                        }
                        // delete the first item
                        if(count == 0 ){
                            self.selectedCode(ko.toJS(self.lstPer()[0].yearServiceCode));
                            return;
                        }
                        // delete item at mediate list 
                        else if(count > 0 && count < self.lstPer().length){
                            self.selectedCode(ko.toJS(self.lstPer()[count].yearServiceCode));    
                            return;
                        }                        
                    });
                    
                    nts.uk.ui.dialog.info({ messageId: "Msg_16" });
                }).fail(function(error) {
                    nts.uk.ui.dialog.alertError(error.message);
                });
                
                
            }).ifCancel(() => {  
               
            }); 
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
    export interface Per{
        specialHolidayCode: string
        yearServiceCode: string;
        yearServiceName: string;
        provision: number;
        yearServiceCls: number;
        yearServicePerSets: Array<Item>;
    }
    
    export interface item{
        yearServiceNo: KnockoutObservable<number>;
        month: KnockoutObservable<number>;
        year: KnockoutObservable<number>;
        date: KnockoutObservable<number>;
    }
    
    export class Item {
        yearServiceNo: KnockoutObservable<number>;
        month: KnockoutObservable<number>;
        year: KnockoutObservable<number>;
        date: KnockoutObservable<number>;

        constructor(param: item) {
            var self = this;
            self.yearServiceNo = ko.observable(param.yearServiceNo());
            self.month = ko.observable(param.month());
            self.year = ko.observable(param.year());
            self.date = ko.observable(param.date());
        }
    }
    
    class BoxModel {
        id: number;
        name: string;
        constructor(id, name) {
            var self = this;
            self.id = id;
            self.name = name;
        }
    }
}




