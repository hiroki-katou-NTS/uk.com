module nts.uk.at.view.kmk011.d {
    
    import viewModelScreenE = nts.uk.at.view.kmk011.e.viewmodel;
    
    import CompanyDivergenceReferenceTimeHistoryDto = nts.uk.at.view.kmk011.d.model.CompanyDivergenceReferenceTimeHistoryDto;
    import ComDivergenceTimeSettingDto = nts.uk.at.view.kmk011.d.model.ComDivergenceTimeSettingDto;
    import DivergenceTimeDto = nts.uk.at.view.kmk011.d.model.DivergenceTimeDto;
    
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import blockUI = nts.uk.ui.block;
  
    export module viewmodel {

        export class ScreenModel {
            screenE: KnockoutObservable<any>;
            
            useUnitSetting: KnockoutObservable<boolean>;
            
            //divergence time setting
            roundingRules: KnockoutObservableArray<any>;
            required: KnockoutObservable<boolean>;
            enable: KnockoutObservable<boolean>;
            mapObj: KnockoutObservable<Map<number, ComDivergenceTimeSettingDto>>;
            mapObj2: KnockoutObservable<Map<number, DivergenceTimeDto>>;
            
            enableSwitch: KnockoutObservableArray<any>;
            
            //history screen
            enable_button_creat: KnockoutObservable<boolean>;
            enable_button_edit: KnockoutObservable<boolean>;
            enable_button_delete: KnockoutObservable<boolean>;
            histList: KnockoutObservableArray<any>;
            histName: KnockoutObservable<string>;
            selectedHist: KnockoutObservable<string>;
            isEnableListHist: KnockoutObservable<boolean>;
            
            time2: KnockoutObservable<number>;
            
            constructor() {
                var _self = this;
                _self.screenE = ko.observable(new viewModelScreenE.ScreenModel());
                _self.useUnitSetting = ko.observable(true);
                
                _self.time2 = ko.observable(1200);
                
                //divergence time setting
                _self.roundingRules = ko.observableArray([
                    { code: 0, name: nts.uk.resource.getText('Enum_UseAtr_NotUse') },
                    { code: 1, name:  nts.uk.resource.getText('Enum_UseAtr_Use')}
                ]);
                _self.enable = ko.observable(true);
                _self.required = ko.observable(true);
                _self.mapObj = new Map<number, ComDivergenceTimeSettingDto>();
                _self.mapObj2 = new Map<number, DivergenceTimeDto>();
                
                _self.enableSwitch = ko.observableArray([]);
                
                 //history screen
                _self.enable_button_creat = ko.observable(true);
                _self.enable_button_edit = ko.observable(true);
                _self.enable_button_delete = ko.observable(true);
                _self.histList = ko.observableArray([]);
                _self.histName = ko.observable('');
                _self.selectedHist = ko.observable(null)
                _self.isEnableListHist = ko.observable(false);
                
                _self.selectedHist.subscribe((value) => {
                    console.log(value);
                });
            }
            
            public start_page(typeStart: number) : JQueryPromise<any> {
                let _self = this;
                var dfd = $.Deferred<any>();
                
                // load all
                if (typeStart == SideBarTabIndex.FIRST) {
                    nts.uk.ui.errors.clearAll()
                    blockUI.grayout();
                    $.when(_self.fillListHistory(), _self.findAllManageUseUnit()).done(function() {
                        _self.fillListItemSetting().done(() => {
                            for (let i = 0; i < 10; i++) {
                                if(_self.isDisableAllRow(i)){
                                    _self.enableSwitch.push(false);
                                }else {
                                    _self.enableSwitch.push(true);
                                }
                            }
                            dfd.resolve(_self);
                            blockUI.clear();
                        });
                    });    
                } else {
                    // Process for screen E (Mother of all screen)
                    nts.uk.ui.errors.clearAll()
                    blockUI.grayout();
                    $.when(_self.screenE().start_page()).done(function() {
                        dfd.resolve(_self);    
                        blockUI.clear();
                    });
                }
                return dfd.promise();
            }
            
            public isDisableAllRow(diverNo: number) : boolean {
                let _self = this;
                if (_self.mapObj2.get(diverNo).divergenceTimeUseSet == DivergenceTimeUseSet.NOT_USE){
                    return false;    
                }
                return true;
            }
            
            public checkStatusEnable(diverNo: number) : boolean {
                let _self = this;
                if (_self.mapObj2.get(diverNo).divergenceTimeUseSet == DivergenceTimeUseSet.NOT_USE){
                    return false;    
                } else {
                    if (_self.mapObj.get(diverNo).notUseAtr() == DivergenceTimeUseSet.NOT_USE) {
                        return false;    
                    }
                     return true;
                }
            }
            
            private fillListItemSetting(): JQueryPromise<any> {
                let _self = this;
                var dfd = $.Deferred<any>();
                let objTemp1: ComDivergenceTimeSettingDto;
                let objTemp2: DivergenceTimeDto;
                
                $.when( service.getAllItemSetting(_self.selectedHist()), service.getAllDivergenceTime()).done((response1: any, response2: any) => {
                    if(response1 != null) {
                         response1.forEach((item: any) => {
                             objTemp1 = new ComDivergenceTimeSettingDto(item.divergenceTimeNo, item.notUseAtr, item.historyId, 
                                                    item.divergenceReferenceTimeValue.alarmTime, item.divergenceReferenceTimeValue.errorTime);
                            _self.mapObj.set(item.divergenceTimeNo, objTemp1);  
//                            _self.mapObj.get(item.divergenceTimeNo).divergenceTimeNo(item.divergenceTimeNo);
//                            _self.mapObj.get(item.divergenceTimeNo).noUseAtr(item.notUseAtr);
//                            _self.mapObj.get(item.divergenceTimeNo).alarmTime(item.alarmTime);
//                            _self.mapObj.get(item.divergenceTimeNo).errorTime(item.errorTime);
                        });
                    }
                    if (response2 != null) {
                          response2.forEach((item1: any) => {
                            objTemp2 = new DivergenceTimeDto(item1.divergenceTimeNo, item1.divergenceTimeName, item1.divergenceTimeUseSet);
                            _self.mapObj2.set(item1.divergenceTimeNo, objTemp2);  
//                        _self.mapObj2.get(item1.divergenceTimeNo).divergenceTimeNo(item1.divergenceTimeNo);
//                        _self.mapObj2.get(item1.divergenceTimeNo).divergenceTimeUseSet(item1.divergenceTimeUseSet);
//                        _self.mapObj2.get(item1.divergenceTimeNo).alarmTime(item1.divergenceTimeName);
                         }); 
                    }

                    dfd.resolve();
                });
                return dfd.promise();
            }
            
            private fillListHistory() : JQueryPromise<any> {
                let _self = this;
                var dfd = $.Deferred<any>();
                var historyData: any = [];
                var textDisplay = "";
                
                //fill list history
                service.getAllHistory().done((response: any) => {
                    if (response != null){
                        response.forEach(function(item: CompanyDivergenceReferenceTimeHistoryDto) {
                              textDisplay = item.startDate + " " + nts.uk.resource.getText("CMM011_26") + " " + item.endDate;
                              historyData.push(new HistModel(item.historyId, textDisplay));
                        });
                        _self.selectedHist(historyData[0].historyId);
                        _self.enable_button_edit(true);
                        _self.enable_button_delete(true);
                    } else {
                        _self.enable_button_edit(false);
                        _self.enable_button_delete(false);  
                    }
                     
                    _self.histList(historyData);
                    dfd.resolve();
                });
                
                return dfd.promise();
            }
            
            private findAllManageUseUnit() : JQueryPromise<any> {
                let _self = this;
                var dfd = $.Deferred<any>();
                service.getUseUnitSetting().done((response) => {
                    _self.useUnitSetting(response.workTypeUseSet);
                });
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
                let _self = this;
                nts.uk.ui.windows.sub.modal("/view/kmk/011/f/index.xhtml").onClosed(function() {
                      _self.start_page(SideBarTabIndex.FIRST);
                });
            }
            public editMode() : void {
                let _self = this;
                nts.uk.ui.windows.setShared('history', _self.selectedHist());
                nts.uk.ui.windows.sub.modal("/view/kmk/011/g/index.xhtml").onClosed(function() {
                       _self.start_page(SideBarTabIndex.FIRST);
                });
            }
            public deleteMode() : void {
                let _self = this;
                var command: any = {};
                command.historyId = _self.selectedHist()
                nts.uk.ui.dialog.confirm({ messageId: "Msg_18" }).ifYes(() => {
                    service.deleteHistory(command).done(() => {
                        nts.uk.ui.dialog.info({ messageId: "Msg_16" }).then(() => {
                              _self.start_page(SideBarTabIndex.FIRST);  
                        });
                    });
                });
            }
        }
        
        export enum SideBarTabIndex {
            FIRST = 0,
            SECOND = 1,
        }
        
        export enum DivergenceTimeUseSet {
            NOT_USE = 0,
            USE = 1,
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