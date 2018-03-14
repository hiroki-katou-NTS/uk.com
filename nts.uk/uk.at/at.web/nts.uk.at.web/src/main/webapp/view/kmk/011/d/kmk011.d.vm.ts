module nts.uk.at.view.kmk011.d {
    
    import viewModelScreenE = nts.uk.at.view.kmk011.e.viewmodel;
    
    import CompanyDivergenceReferenceTimeHistoryDto = nts.uk.at.view.kmk011.d.model.CompanyDivergenceReferenceTimeHistoryDto;
    
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import blockUI = nts.uk.ui.block;
  
    export module viewmodel {

        export class ScreenModel {
            screenE: KnockoutObservable<any>;
            
            //divergence time setting
            emailAuth: KnockoutObservable<string>;
            myMessage: KnockoutObservable<string>;
            selectedRuleCode: KnockoutObservable<any>;
            roundingRules: KnockoutObservableArray<any>;
            required: KnockoutObservable<boolean>;
            enable: KnockoutObservable<boolean>;
            
            //history screen
            enable_button_creat: KnockoutObservable<boolean>;
            enable_button_edit: KnockoutObservable<boolean>;
            enable_button_delete: KnockoutObservable<boolean>;
            histList: KnockoutObservableArray<any>;
            histName: KnockoutObservable<string>;
            currentHist: KnockoutObservable<number>
            selectedHist: KnockoutObservable<string>;
            isEnableListHist: KnockoutObservable<boolean>;
            
            constructor() {
                var _self = this;
                _self.screenE = ko.observable(new viewModelScreenE.ScreenModel());
                
                //divergence time setting
                _self.emailAuth =  ko.observable("test");
                _self.myMessage = ko.observable("test21");
                _self.roundingRules = ko.observableArray([
                    { code: '1', name: '四捨五入' },
                    { code: '2', name: '切り上げ' }
                ]);
                _self.selectedRuleCode = ko.observable(1);
                _self.required = ko.observable(true);
                _self.enable = ko.observable(true);
                
                 //history screen
                _self.enable_button_creat = ko.observable(true);
                _self.enable_button_edit = ko.observable(true);
                _self.enable_button_delete = ko.observable(true);
                _self.histList = ko.observableArray([]);
                _self.histName = ko.observable('');
                _self.currentHist = ko.observable(null);
                _self.selectedHist = ko.observable(null)
                _self.isEnableListHist = ko.observable(false);
            }
            
            public start_page(typeStart: number) : JQueryPromise<any> {
                let _self = this;
                var dfd = $.Deferred<any>();
                
                // load all
                if (typeStart == SideBarTabIndex.FIRST) {
                    nts.uk.ui.errors.clearAll()
                    blockUI.grayout();
                    $.when(_self.fillListHistory(), _self.findAllManageUseUnit(), _self.fillListItemSetting()).done(function() {
                        dfd.resolve(_self);
                        blockUI.clear();
                    });    
                } else {
                    // Process for screen D (Mother of all screen)
                    nts.uk.ui.errors.clearAll()
                    blockUI.grayout();
                    $.when(_self.screenE().start_page()).done(function() {
                        dfd.resolve(_self);    
                        blockUI.clear();
                    });
                }
                
                dfd.resolve();
                return dfd.promise();
            }
            
            private fillListItemSetting(): JQueryPromise<any> {
                let _self = this;
                var dfd = $.Deferred<any>();
                service.getAllItemSetting().done(() => {
                    
                });
                dfd.resolve();
                return dfd.promise();
            }
            
            private fillListHistory() : JQueryPromise<any> {
                let _self = this;
                var dfd = $.Deferred<any>();
                var historyData: any = [];
                var textDisplay = "";
                
                //fill list history
                service.getAllHistory().done((response: any) => {
                     response.forEach(function(item: CompanyDivergenceReferenceTimeHistoryDto){
                              textDisplay = item.startDate + " " + nts.uk.resource.getText("CMM011_26") + " " + item.endDate;
                              historyData.push(new HistModel(item.historyId, textDisplay));
                     });
                    _self.histList(historyData);
                    dfd.resolve();
                });
                
                return dfd.promise();
            }
            
            private findAllManageUseUnit() : JQueryPromise<any> {
                let _self = this;
                var dfd = $.Deferred<any>();
                
                dfd.resolve();
                return dfd.promise();
            }
            
            private isDisableTab() : boolean {
                return false;    
            }
            
             /**
             * on select tab handle
             */
            public onSelectTabOne(): void {
                $("#sidebar").ntsSideBar("init", {
                    active: SideBarTabIndex.FIRST,
                    activate: (event: any, info: any) => {
                        let _self = this;
                        _self.start_page(SideBarTabIndex.FIRST);
                    }
                });
            }
            public onSelectTabTwo(): void {
                let _self = this;
                if (_self.isDisableTab() == false) {
                    $("#sidebar").ntsSideBar("init", {
                        active: SideBarTabIndex.SECOND,
                        activate: (event: any, info: any) => {
                            _self.start_page(SideBarTabIndex.SECOND);
                        }
                    });
                }
            }
            
            // history mode
            public createMode() : void {
                nts.uk.ui.windows.sub.modal("/view/kmk/011/f/index.xhtml").onClosed(function() {
                       
                });
            }
            public editMode() : void {
                 nts.uk.ui.windows.sub.modal("/view/kmk/011/g/index.xhtml").onClosed(function() {
                       
                });
            }
            public deleteMode() : void {
                
            }
        }
        
        export enum SideBarTabIndex {
            FIRST = 0,
            SECOND = 1,
        }
        
        export class HistModel {
            historyId: string;
            textDisplay: string;
            
            constructor(historyId: string, textDisplay: string) {
                this.historyId = historyId;
                this.textDisplay = textDisplay;
            }
        }
        
    }
}