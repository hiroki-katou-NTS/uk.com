module nts.uk.at.view.kmf004.b {
    import flat = nts.uk.util.flatArray;
    import getText = nts.uk.resource.getText;
    import alert = nts.uk.ui.dialog.alert;
    import confirm = nts.uk.ui.dialog.confirm;
    import modal = nts.uk.ui.windows.sub.modal;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    
    let __viewContext: any = window["__viewContext"] || {};

    export module viewmodel {
        export class ScreenModel {
            //search
            baseDate: KnockoutObservable<Date> = ko.observable(new Date());

            //Grid data
            columns: KnockoutObservable<any>;
            singleSelectedCode: KnockoutObservable<any>;
            items: KnockoutObservableArray<ItemModel>;

            model: KnockoutObservable<BonusPaySetting> = ko.observable(new BonusPaySetting({ id: '', name: '' }));
            
            code: KnockoutObservable<string>;
            editMode: KnockoutObservable<boolean>;
            name: KnockoutObservable<string>;
            itemList: KnockoutObservableArray<any>;
            selectedBaseDateId: any;
            standardDate: KnockoutObservable<string>;
            dataItems: KnockoutObservableArray<Item>;
            specialHolidayCode: KnockoutObservable<string>;
            standardDateEnable: KnockoutObservable<boolean>;
            standardDateReq: KnockoutObservable<boolean>;
            provisionCheck: KnockoutObservable<boolean>;
            provisionDeactive: KnockoutObservable<boolean>;
            
            constructor() {
                let self = this,
                   
                model = self.model();

                //Grid data
                self.items = ko.observableArray([]);
                
                self.columns = ko.observableArray([
                    { headerText: nts.uk.resource.getText("KMF004_7"), prop: 'code', width: 50 },
                    { headerText: nts.uk.resource.getText("KMF004_8"), prop: 'name', width: 200, formatter: _.escape }
                ]);
                
                self.singleSelectedCode = ko.observable("");
                
                self.provisionCheck = ko.observable(false);  
                self.provisionDeactive = ko.observable(true);
                
                self.code = ko.observable("");
                self.editMode = ko.observable(true);  
                self.name = ko.observable(""); 
                
                self.itemList = ko.observableArray([
                    { code: 0, name: nts.uk.resource.getText("KMF004_75") },
                    { code: 1, name: nts.uk.resource.getText("KMF004_77") },
                    { code: 2, name: nts.uk.resource.getText("KMF004_78") }
                ]);
                
                self.selectedBaseDateId = ko.observable(0); 
                self.standardDate = ko.observable("");
                self.standardDateEnable = ko.observable(true);
                
                self.standardDateReq = ko.observable(true);
                
                self.dataItems = ko.observableArray([]);
                
                // Get codes from A screen
                self.specialHolidayCode = ko.observable(getShared("KMF004B_SPHD_CD"));
                
                //Bind data to from when user select item on grid
                self.singleSelectedCode.subscribe(function(value) {
                    // clear all error
                    nts.uk.ui.errors.clearAll();
                    
                    if(value.length > 0){
                        service.getPerByCode(self.specialHolidayCode(), value).done(function(data) {
                            self.editMode(false);
                            self.code(data.personalGrantDateCode);
                            self.name(data.personalGrantDateName);
                            self.provisionCheck(data.provision == 1 ? true : false);
                            if(data.provision == 1) {
                                self.provisionDeactive(false);
                            } else {
                                self.provisionDeactive(true);
                            }
                            self.selectedBaseDateId(data.grantDateAtr),
                            self.standardDate(data.grantDateAtr != 0 ? "" : data.grantDate),
                            self.bindPerSetData(data.personalGrantDateCode)
                            $("#input-name").focus();
                            nts.uk.ui.errors.clearAll();
                        }).fail(function(res) {
                              
                        });
                    }
                });  
                
                self.selectedBaseDateId.subscribe(function(value) {
                    if(value == 0){
                        self.standardDateEnable(true);
                        self.standardDateReq(true);
                    } else {
                        self.standardDateEnable(false);
                        self.standardDateReq(false);
                        self.standardDate("");
                        $("#standard-date-input").ntsError("clear");
                    }
                }); 
                
                self.bindPerSetData("");
            }

            /**
             * Bind Per Set data.
             */
            bindPerSetData(personalGrantDateCode: string){
                var self = this;
                
                if(personalGrantDateCode != "") {
                    self.dataItems.removeAll();
                    
                    service.getPerSetByCode(self.specialHolidayCode(), personalGrantDateCode).done(function(data){
                        for(var i = 0; i < data.length; i++){
                            var item : IItem = {
                                year: data[i].grantDateYear,
                                month: data[i].grantDateMonth,
                                setNo: data[i].grantDateNo
                            };
                            
                            self.dataItems.push(new Item(item));
                        }
                        
                        for(var i = data.length; i < 20; i++) {
                            var item : IItem = {
                                year: null,
                                month: null,
                                setNo: i + 1
                            };
                            
                            self.dataItems.push(new Item(item));    
                        }          
                    }).fail(function(res) {
                          
                    });
                } else {
                    for(var i = 0; i < 20; i++) {
                        var item : IItem = {
                            year: null,
                            month: null,
                            setNo: i + 1
                        };
                        
                        self.dataItems.push(new Item(item));    
                    }
                }
            }
            
            /**
             * Start page.
             */
            start() {
                var self = this;
                var dfd = $.Deferred();
                
                $.when(self.getData()).done(function() {
                                    
                    if (self.items().length > 0) {
                        self.singleSelectedCode(self.items()[0].code);
                    } else {
                        self.newBtn();    
                    }
                    
                    dfd.resolve();
                }).fail(function(res) {
                    dfd.reject(res);    
                });
    
                return dfd.promise();
            }
            
            /**
             * Get data.
             */
            getData(): JQueryPromise<any> {
                var self = this;
                var dfd = $.Deferred();
                self.items([]);
                service.getAllPerByCode(self.specialHolidayCode()).done(function(data) {
                    _.forEach(data, function(item) {
                        self.items.push(new ItemModel(item.personalGrantDateCode, item.personalGrantDateName));
                    });
                    
                    dfd.resolve(data);
                }).fail(function(res) {
                    dfd.reject(res);    
                });
                
                return dfd.promise();
            }
            
            /**
             * New function.
             */
            newBtn(){
                var self = this;
                
                self.code("");
                self.editMode(true);  
                self.name(""); 
                self.provisionCheck(false);
                self.provisionDeactive(true);
                self.selectedBaseDateId(0); 
                self.standardDate("");
                self.standardDateReq(true);
                
                self.dataItems.removeAll();
                
                for(var i = 0; i < 20; i++) {
                    var item : IItem = {
                        year: null,
                        month: null,
                        setNo: i + 1
                    };
                    
                    self.dataItems.push(new Item(item));    
                }
                
                self.singleSelectedCode("");
                $("#input-code").focus();
            }
            
            /**
             * Register function.
             */
            registerBtn(){
                var self = this;
                nts.uk.ui.errors.clearAll();
                var perSetData = [];
                var index = 1;
                
                $("#input-code").trigger("validate");
                $("#input-name").trigger("validate");
                if (self.selectedBaseDateId() === 0) {
                    if (self.standardDate() == null || self.standardDate() == "" || self.standardDate() == undefined) {
                        self.standardDateReq(true);
                    }
                    $("#standard-date-input").trigger("validate");
                }
                
                if (nts.uk.ui.errors.hasError()) {
                    return;    
                }
                
                nts.uk.ui.block.invisible();
                
                _.forEach(self.dataItems(), function(item) {
                    if(item.month() || item.year()) {
                        if(item.month() !== 0 || item.year() !== 0) {
                            perSetData.push({
                                specialHolidayCode: self.specialHolidayCode(),
                                personalGrantDateCode: self.code(),
                                grantDateNo: index,
                                grantDateMonth: Number(item.month()),
                                grantDateYear: Number(item.year())
                            });
                        }
                    }
                    
                    index++;
                });
                
                var perItem : service.GrantDatePerItem = {
                    specialHolidayCode: self.specialHolidayCode(),
                    personalGrantDateCode: self.code(),
                    personalGrantDateName: self.name(),
                    provision: self.provisionCheck() ? 1 : 0,
                    grantDate: new Date(self.standardDate()),
                    grantDateAtr: self.selectedBaseDateId(),
                    grantDatePerSet: ko.toJS(perSetData)
                };
                
                if(!self.editMode() && self.code() !== ""){
                    service.UpdatePer(perItem).done(function(errors) {
                        if (errors && errors.length > 0) {
                            self.addListError(errors);    
                        } else {  
                            self.getData();         
                            self.singleSelectedCode(self.code());
                            self.singleSelectedCode.valueHasMutated();
                            
                            nts.uk.ui.dialog.info({ messageId: "Msg_15" }).then(() => { 
                                $("#input-name").focus();
                            });
                        }
                    }).fail(function(res) {
                        nts.uk.ui.dialog.alertError(res.message);
                    }).always(function() {
                        nts.uk.ui.block.clear();
                    });
                } else {
                    service.addPer(perItem).done(function(errors) {
                        if (errors && errors.length > 0) {
                            self.addListError(errors);    
                        } else {  
                            self.getData();         
                            self.singleSelectedCode(self.code());
                            
                            nts.uk.ui.dialog.info({ messageId: "Msg_15" }).then(() => { 
                                $("#input-name").focus();
                            });
                        }
                    }).fail(function(res) {
                        nts.uk.ui.dialog.alertError(res.message);
                    }).always(function() {
                        nts.uk.ui.block.clear();
                    });
                }
            }
            
            /**
             * Delete function.
             */
            deleteBtn(){
                var self = this;
                
                
                let count = 0;
                for (let i = 0; i <= self.items().length; i++){
                    if(self.items()[i].code == self.singleSelectedCode()){
                        count = i;
                        break;
                    }
                }
                
                if(self.provisionCheck()) {
                    nts.uk.ui.dialog.alertError({ messageId: "Msg_1219" });
                    nts.uk.ui.block.clear();
                    return;
                }
                
                nts.uk.ui.dialog.confirm({ messageId: "Msg_18" }).ifYes(() => {
                    service.removePer(self.specialHolidayCode(), self.code()).done(function() {
                        self.getData().done(function(){
                            // if number of item from list after delete == 0 
                            if(self.items().length==0){
                                self.newBtn();
                                return;
                            }
                            // delete the last item
                            if(count == ((self.items().length))){
                                self.singleSelectedCode(self.items()[count-1].code);
                                return;
                            }
                            // delete the first item
                            if(count == 0 ){
                                self.singleSelectedCode(self.items()[0].code);
                                return;
                            }
                            // delete item at mediate list 
                            else if(count > 0 && count < self.items().length){
                                self.singleSelectedCode(self.items()[count].code);    
                                return;
                            }
                        });
                        
                        nts.uk.ui.dialog.info({ messageId: "Msg_16" });
                    }).fail(function(error) {
                        nts.uk.ui.dialog.alertError({ messageId: error.messageId });
                    }).always(function() {
                        nts.uk.ui.block.clear();      
                    });
                })
            }
            
            /**
             * Close dialog function.
             */
            closeBtn(){
                nts.uk.ui.windows.close();
            }
            
            /**
             * Add list error.
             */
            addListError(errorsRequest: Array<string>) {
                var self = this;
                var errors = [];
                _.forEach(errorsRequest, function(err) {
                    errors.push({ message: nts.uk.resource.getMessage(err), messageId: err, supplements: {} });
                });
    
                nts.uk.ui.dialog.bundledErrors({ errors: errors });
            }
        }
        
        class ItemModel {
            code: string;
            name: string;
            constructor(code: string, name: string) {
                this.code = code;
                this.name = name;       
            }
        } 
        
        export class Item {
            year: KnockoutObservable<number>;
            month: KnockoutObservable<number>;
            setNo: KnockoutObservable<number>;
            
            constructor(param: IItem) {
                var self = this;
                self.year = ko.observable(param.year);
                self.month = ko.observable(param.month);
                self.setNo = ko.observable(param.setNo);
            }
        }
        
        export interface IItem {
            year: number;
            month: number;
            setNo: number;
        }

        interface IBonusPaySetting {
            id: string;
            name: string;
            wid?: string; // workplace id
            wname?: string; // workplace name
        }

        class BonusPaySetting {
            id: KnockoutObservable<string> = ko.observable('');
            name: KnockoutObservable<string> = ko.observable('');
            wid: KnockoutObservable<string> = ko.observable('');
            wname: KnockoutObservable<string> = ko.observable('');

            constructor(param: IBonusPaySetting) {
                let self = this;

                self.id(param.id);
                self.name(param.name);
                self.wid(param.wid || '');
                self.wname(param.wname || '');

                self.id.subscribe(x => {
                    let view = __viewContext.viewModel && __viewContext.viewModel.tabView,
                        acts: any = view && _.find(view.tabs(), (t: any) => t.active());
                    if (acts && acts.id == 'H') {
                        view.removeAble(!!x);
                    }
                });
            }
        }

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
    }
}