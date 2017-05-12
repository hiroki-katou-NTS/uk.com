module kml001.a.viewmodel {
    import servicebase = kml001.shr.servicebase;
    import vmbase = kml001.shr.vmbase;
    export class ScreenModel {
        gridPersonCostList: KnockoutObservableArray<vmbase.GridPersonCostCalculation>;
        currentGridPersonCost: KnockoutObservable<vmbase.GridPersonCostCalculation>;
        personCostList: KnockoutObservableArray<vmbase.PersonCostCalculation>;
        currentPersonCost: KnockoutObservable<vmbase.PersonCostCalculation>;
        isInsert: KnockoutObservable<Boolean>;
        constructor() {
            var self = this;
            self.personCostList = ko.observableArray([]);
            self.currentPersonCost = ko.observable(new vmbase.PersonCostCalculation('','','',0,"","9999/12/31",[]));
            self.gridPersonCostList = ko.observableArray([]);
            self.currentGridPersonCost = ko.observable(); 
            self.isInsert = ko.observable(true);
        }

        startPage(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            servicebase.personCostCalculationSelect()
                .done(function(res: Array<any>) {
                    if(res.length){
                        self.loadData(res);
                        self.currentGridPersonCost.subscribe(function(value){
                            self.currentPersonCost(_.find(self.personCostList(), function(o) { return o.startDate() == _.split(value, ' ', 1)[0]; }));
                            $("#startDateInput").ntsError('clear');
                            self.isInsert(false);
                        });
                        self.currentPersonCost().startDate.subscribe(function(value){
                            if(self.isInsert()) {
                                if(vmbase.ProcessHandler.validateDateInput(self.currentPersonCost().startDate(),_.last(self.personCostList()).startDate())) {
                                    $("#startDateInput").ntsError('set', "ERR");    
                                } else { $("#startDateInput").ntsError('clear'); }      
                            }
                        });
                        self.isInsert(false);
                    }
                    dfd.resolve(res);
                })
                .fail(function(res) {
                    dfd.reject(res);
                })
            return dfd.promise();
        }
        
        loadData(res: Array<any>){
            var self = this;
            res.forEach(function(personCostCalc){
                self.personCostList.push(vmbase.ProcessHandler.fromObjectPerconCost(personCostCalc));
            });
            self.currentPersonCost((_.first(self.personCostList())==null)?new vmbase.PersonCostCalculation('','','',0,"","9999/12/31",[]):_.first(self.personCostList()));
            self.personCostList().forEach(function(item) { self.gridPersonCostList.push({dateRange: item.startDate()+" ~ "+item.endDate()})});
            self.currentGridPersonCost(self.currentPersonCost().startDate()+" ~ "+self.currentPersonCost().endDate()); 
        }
        
        saveData() {
            var self = this;
            if(self.isInsert()){
                let lastStartDate;
                if(_.size(self.personCostList())==0) {
                    lastStartDate = "1900/01/01"
                }else {
                    lastStartDate = _.last(self.personCostList()).startDate();         
                } 
                if((self.currentPersonCost().startDate()!="")||!vmbase.ProcessHandler.validateDateInput(self.currentPersonCost().startDate(),lastStartDate)){
                    servicebase.personCostCalculationInsert(vmbase.ProcessHandler.toObjectPersonCost(self.currentPersonCost()))
                        .done(function(res: Array<any>) {
                            servicebase.personCostCalculationSelect()
                                .done(function(res: Array<any>) {
                                    self.personCostList.removeAll();
                                    self.gridPersonCostList.removeAll();
                                    self.loadData(res);
                                }).fail(function(res) {
                            
                                });
                        }).fail(function(res) {
                            
                        });
                }
            } else {
                servicebase.personCostCalculationUpdate(vmbase.ProcessHandler.toObjectPersonCost(self.currentPersonCost()))
                        .done(function(res: Array<any>) {
                            self.personCostList.removeAll();
                            self.gridPersonCostList.removeAll();
                            self.loadData(res);
                        }).fail(function(res) {
                            
                        });    
            }
        }
        
        /**
         * 
         */
        premiumDialog() {
            var self = this;
            nts.uk.ui.windows.setShared('isInsert', self.isInsert());
            nts.uk.ui.windows.sub.modal("/view/kml/001/b/index.xhtml", { title: "割増項目の設定", dialogClass: "no-close" }).onClosed(function() {
                if(nts.uk.ui.windows.getShared('updatePremiumSeting')==true){
                    servicebase.personCostCalculationSelect()
                        .done(function(res: Array<any>) {
                            let processItem;
                            if(self.isInsert()){
                                processItem = self.currentPersonCost();
                            }
                            self.personCostList.removeAll();
                            self.gridPersonCostList.removeAll();
                            self.loadData(res);
                            if(self.isInsert()){
                                self.currentPersonCost(processItem);
                                let lastItem = _.last(self.personCostList());
                                self.currentPersonCost().premiumSets().forEach(function(item, index){
                                    item.useAtr(lastItem.premiumSets()[index].useAtr());    
                                });
                            }
                        }).fail(function(res) {
                            
                        });
                }
            });
        }
        
        /**
         * 
         */
        createDialog() {
            var self = this;
            let lastestHistory = _.last(self.personCostList());
            nts.uk.ui.windows.setShared('lastestStartDate', lastestHistory==null?"1900/1/1":lastestHistory.startDate());
            nts.uk.ui.windows.sub.modal("/view/kml/001/c/index.xhtml", { title: "履歴の追加", dialogClass: "no-close" }).onClosed(function() {
                let newStartDate: string = nts.uk.ui.windows.getShared('newStartDate');
                if(newStartDate!=null) {
                    let copyDataFlag: boolean = nts.uk.ui.windows.getShared('copyDataFlag'); 
                    if(!copyDataFlag) {
                        if(_.size(self.personCostList())==0){
                            self.currentPersonCost(new vmbase.PersonCostCalculation('','','',0,newStartDate,"9999/12/31",[]));
                            let newPremiumSets = [];
                            for (let i = 1; i <= 10; i++) { 
                                newPremiumSets.push(new vmbase.PremiumSetting("", "", i.toString(), "", i.toString(), 1, 0, []));
                            }
                            self.currentPersonCost().premiumSets(newPremiumSets);
                        } else {
                            let premiumItemSetting = _.cloneDeep(self.currentPersonCost().premiumSets());
                            self.currentPersonCost().companyID('');
                            self.currentPersonCost().historyID('');
                            self.currentPersonCost().memo('');
                            self.currentPersonCost().unitPrice(0);
                            self.currentPersonCost().startDate(newStartDate);
                            self.currentPersonCost().endDate("9999/12/31");
                            self.currentPersonCost().premiumSets([]);
                            let newPremiumSets = [];
                            for (let i = 1; i <= 10; i++) { 
                                newPremiumSets.push(new vmbase.PremiumSetting("", "", i.toString(), "", i.toString(), premiumItemSetting[i-1].useAtr(), 0, []));
                            }
                            self.currentPersonCost().premiumSets(newPremiumSets);
                        }
                        self.isInsert(true);
                    } else {
                        let oldPremiumSets = self.currentPersonCost().premiumSets();
                        self.currentPersonCost().startDate(newStartDate);
                        self.currentPersonCost().endDate("9999/12/31");
                        self.currentPersonCost().premiumSets(oldPremiumSets);
                        self.isInsert(true);
                    }        
                }   
            });
        }
        
        /**
         * 
         */
        editDialog() {
            var self = this;
            nts.uk.ui.windows.setShared('personCostList', self.personCostList());
            nts.uk.ui.windows.setShared('currentPersonCost', self.currentPersonCost());
            nts.uk.ui.windows.sub.modal("/view/kml/001/d/index.xhtml", { title: "履歴の編集", dialogClass: "no-close" }).onClosed(function() {
                if(nts.uk.ui.windows.getShared('isEdited')==true){
                    servicebase.personCostCalculationSelect()
                        .done(function(res: Array<any>) {
                            self.personCostList.removeAll();
                            self.gridPersonCostList.removeAll();
                            self.loadData(res);
                        }).fail(function(res) {
                            
                        });
                }
            });;
        }
        
        selectDialog() {
            var self = this;
            nts.uk.ui.windows.sub.modal("/view/kml/001/b/index.xhtml", { title: "割増項目の設定", dialogClass: "no-close" }).onClosed(function() {
                  
            });
        }
    }
}