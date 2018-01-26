module nts.uk.at.view.kmf004 {
    import getText = nts.uk.resource.getText;
    import alert = nts.uk.ui.dialog.alert;
    import confirm = nts.uk.ui.dialog.confirm;
    import href = nts.uk.request.jump;
    import modal = nts.uk.ui.windows.sub.modal;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;

    let __viewContext: any = window["__viewContext"] || {};

    export module viewmodel {
        export class TabScreenModel {
            title: KnockoutObservable<string> = ko.observable('');
            removeAble: KnockoutObservable<boolean> = ko.observable(true);
            tabs: KnockoutObservableArray<TabModel> = ko.observableArray([
                new TabModel({ id: 'B', name: getText('Com_Company'), active: true }),
                new TabModel({ id: 'C', name: getText('Com_Person') })
            ]);
            currentTab: KnockoutObservable<string> = ko.observable('B');
            //radio

            constructor() {
                let self = this;
                //get use setting 

                self.tabs().map((t) => {
                    // set title for tab

                    if (t.active() == true) {
                        self.title(t.name);
                        self.changeTab(t);
                    }
                });
            }

            changeTab(tab: TabModel) {
                let self = this,
                    view: any = __viewContext.viewModel,
                    oldtab: TabModel = _.find(self.tabs(), t => t.active());
                
                // cancel action if tab self click
                if (oldtab.id == tab.id) {
                    return;
                }
                //set not display remove button first when change tab
                //__viewContext.viewModel.tabView.removeAble(false);
                tab.active(true);
                self.title(tab.name);
                self.tabs().map(t => (t.id != tab.id) && t.active(false));

                // call start function on view at here
                switch (tab.id) {
                    case 'B':
                        self.currentTab('B');
                        if (!!view.viewmodelB && typeof view.viewmodelB.start == 'function') {
                            view.viewmodelB.start();
                            nts.uk.ui.errors.clearAll();
                        }
                        break;
                    case 'C':
                        self.currentTab('C');
                        if (!!view.viewmodelC && typeof view.viewmodelC.start == 'function') {
                            view.viewmodelC.start();
                            nts.uk.ui.errors.clearAll();
                        }
                        break;
                }
            }
        }

        interface ITabModel {
            id: string;
            name: string;
            active?: boolean;
            display?: boolean;
        }

        class TabModel {
            id: string;
            name: string;
            active: KnockoutObservable<boolean> = ko.observable(false);
            display: KnockoutObservable<boolean> = ko.observable(true);

            constructor(param: ITabModel) {
                this.id = param.id;
                this.name = param.name;
                this.active(param.active || false);
                this.display(param.display || true);
            }

            changeTab() {
                // call parent view action
                __viewContext.viewModel.tabView.changeTab(this);
            }
        }
    }

    export module b.viewmodel {
        export class ScreenModel {
            itemList: KnockoutObservableArray<any>;
            selectedId: KnockoutObservable<number>;
            value: KnockoutObservable<string>;
            enable: KnockoutObservable<boolean>;
            items: KnockoutObservableArray<Item>;
            specialHolidayCode: KnockoutObservable<string>;
            monthDaysReq: KnockoutObservable<boolean>;
            
            constructor() {
                let self = this;
                self.itemList = ko.observableArray([
                    new BoxModel(0, nts.uk.resource.getText("KMF004_75")),
                    new BoxModel(1, nts.uk.resource.getText("KMF004_77")),
                    new BoxModel(2, nts.uk.resource.getText("KMF004_78"))
                ]);

                self.value = ko.observable('');
                self.enable = ko.observable(true);
                self.monthDaysReq = ko.observable(true);
                self.selectedId = ko.observable(0);
                self.items = ko.observableArray([]);
                
                self.selectedId.subscribe(function(value) {
                    if(value == 0){
                        self.enable(true);
                        self.monthDaysReq(true);
                    } else {
                        self.enable(false);
                        self.monthDaysReq(false);
                        self.value("");
                        $("#month-day-input").ntsError("clear");
                    }
                }); 
                
                // Get special holiday code from A screen
                self.specialHolidayCode = ko.observable(getShared("KMF004B_SPHD_CD"));
                
                self.start();
            }

            /**
             * Start page.
             */
            start() {
                var self = this;
                var dfd = $.Deferred();
                
                service.getComByCode(self.specialHolidayCode()).done(function(data){
                    self.bindData(data);                
                    dfd.resolve();
                }).fail(function(res) {
                    dfd.reject(res);    
                });
    
                return dfd.promise();
            }
            
            /**
             * Bind data.
             */
            bindData(data: any) {
                var self = this;
            
                if(data != undefined) {
                    self.items.removeAll();
                    
                    self.selectedId(data.grantDateAtr);
                    self.value(data.grantDate);
                    
                    service.getAllSetByCode(data.specialHolidayCode).done(function(data: Array<any>){
                        for(var i = 0; i < data.length; i++){
                            var item : IItem = {
                                year: data[i].grantDateYear,
                                month: data[i].grantDateMonth
                            };
                            
                            self.items.push(new Item(item));
                        }
                        
                        for(var i = data.length; i < 20; i++) {
                            var item : IItem = {
                                year: null,
                                month: null
                            };
                            
                            self.items.push(new Item(item));    
                        }
                    }).fail(function(res) {
                          
                    });
                } else {
                    self.selectedId(0);
                    self.value("");
                    self.items.removeAll();
                    
                    for(var i = 0; i < 20; i++) {
                        var item : IItem = {
                            year: null,
                            month: null
                        };
                        
                        self.items.push(new Item(item));    
                    }
                }
            }
                        
            /**
             * Save data to db.
             */
            saveData(){
                var self = this;
                
                if (self.selectedId() === 0) {
                    if (self.value() == null || self.value() == "" || self.value() == undefined) {
                        self.monthDaysReq(true);
                    }
                    $("#month-day-input").trigger("validate");
                }
                                
                if (nts.uk.ui.errors.hasError()) {
                    return;    
                }
                
                nts.uk.ui.block.invisible();
                
                var setData = [];
                var index = 1;
                
                _.forEach(self.items(), function(item) {
                    if(item.month() || item.year()) {
                        if(item.month() !== 0 || item.year() !== 0) {
                            setData.push({
                                specialHolidayCode: self.specialHolidayCode(),
                                grantDateNo: index,
                                grantDateMonth: Number(item.month()),
                                grantDateYear: Number(item.year())
                            });
                        }
                    }
                    
                    index++;
                });
                
                var dataItem : service.ComItem = {
                    specialHolidayCode: self.specialHolidayCode(),
                    grantDateAtr: self.selectedId(),
                    grantDate: new Date(self.value()),
                    grantDateSets: ko.toJS(setData)
                }; 
                
                service.addGrantDateCom(dataItem).done(function(errors){
                    if (!errors || errors.length == 0) {
                        self.bindData(dataItem);
                        nts.uk.ui.dialog.info({ messageId: "Msg_15" });
                    } else {
                        self.addListError(errors);
                    }
                    
                }).fail(function(error) {
                    nts.uk.ui.dialog.alertError(error.message);    
                }).always(function() {
                    nts.uk.ui.block.clear();    
                });
            }
            
            /**
             * Close dialog.
             */
            cancel() {
                nts.uk.ui.windows.close();
            }
            
             /**
             * Add list error.
             */
            addListError(errorsRequest: Array<string>) {
                var messages = {};
                _.forEach(errorsRequest, function(err) {
                    messages[err] = nts.uk.resource.getMessage(err);
                });
    
                var errorVm = {
                    messageId: errorsRequest,
                    messages: messages
                };
    
                nts.uk.ui.dialog.bundledErrors(errorVm);
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
        
        export class Item {
            year: KnockoutObservable<number>;
            month: KnockoutObservable<number>;
            
            constructor(param: IItem) {
                var self = this;
                self.year = ko.observable(param.year);
                self.month = ko.observable(param.month);
        
            }
        }
        
        export interface IItem {
            year: number;
            month: number;
        }
    }
}