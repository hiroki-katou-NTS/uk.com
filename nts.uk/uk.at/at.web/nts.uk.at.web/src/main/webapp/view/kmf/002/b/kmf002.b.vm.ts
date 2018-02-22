module nts.uk.at.view.kmf002.b {
    
    import service = nts.uk.at.view.kmf002.b.service;
    import blockUI = nts.uk.ui.block;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    
    export module viewmodel {
        export class ScreenModel {
            commonTableMonthDaySet: KnockoutObservable<nts.uk.at.view.kmf002.viewmodel.CommonTableMonthDaySet>;
            multiSelectedWorkplaceId: KnockoutObservableArray<any>;
            baseDate: KnockoutObservable<Date>;
            alreadySettingList: KnockoutObservableArray<UnitAlreadySettingModel[]>;
            treeGrid: TreeComponentOption;
            workplaceNameSelected: KnockoutObservable<string>;
            enableSave: KnockoutObservable<boolean>;
            enableDelete: KnockoutObservable<boolean>;
            isContinueRecurFindName: KnockoutObservable<boolean>;

            constructor(){
                let _self = this;
                _self.commonTableMonthDaySet = ko.observable(new nts.uk.at.view.kmf002.viewmodel.CommonTableMonthDaySet());
                _self.commonTableMonthDaySet().visibleInfoSelect(true);
                _self.commonTableMonthDaySet().infoSelect1(nts.uk.resource.getText("Com_Workplace"));
                _self.enableSave = ko.observable(true);
                _self.enableDelete = ko.observable(true)
                _self.workplaceNameSelected = ko.observable("");
                _self.isContinueRecurFindName = ko.observable(true);
                
                _self.baseDate = ko.observable(new Date());
                _self.multiSelectedWorkplaceId = ko.observableArray([]);
                _self.alreadySettingList = ko.observableArray([]);
                _self.treeGrid = {
                        isShowAlreadySet: true,
                        isMultiSelect: false,
                        treeType: TreeType.WORK_PLACE,
                        selectedWorkplaceId: _self.multiSelectedWorkplaceId,
                        baseDate: _self.baseDate,
                        selectType: SelectionType.SELECT_FIRST_ITEM,
                        isShowSelectButton: true,
                        isDialog: false,
                        alreadySettingList: _self.alreadySettingList,
                        maxRows: 20,
                        tabindex: 3,
                        systemType : SystemType.EMPLOYMENT
                };
                
                _self.multiSelectedWorkplaceId.subscribe(function(newValue) {
                    try {
                        _self.commonTableMonthDaySet().infoSelect2($('#tree-grid').getRowSelected()[0].workplaceCode);
                        _self.getNameWkpSelect($('#tree-grid').getDataList(), 
                                               $('#tree-grid').getRowSelected()[0].workplaceCode);
                        
                        _self.commonTableMonthDaySet().infoSelect3(_self.workplaceNameSelected());
                        _self.getDataFromService();
                        _self.isContinueRecurFindName(true);
                        
                        if (!_.isNull(newValue) && !_.isEmpty(newValue)) {
                            _self.enableSave(true);    
                        }
                    }
                    catch (e){
                    } 
                });
                
                _self.commonTableMonthDaySet().fiscalYear.subscribe(function(newValue) {
                    // change year
                    if (!nts.uk.ui.errors.hasError()) {
                        _self.getDataFromService();    
                    }
                });
            }
            
            private getNameWkpSelect(data: any, codeSelect: string): void {
                let _self = this;
                if (_self.isContinueRecurFindName() == false) {
                    return;
                }
                _.forEach(data, function(obj: any) {
                    if (obj.code == codeSelect) {
                        _self.workplaceNameSelected(obj.name);
                        _self.isContinueRecurFindName(false);
                        return false;
                    } else {
                        if (typeof obj.childs !== "undefined" && obj.childs.length > 0) {
                            if (obj.code == codeSelect) {
                                _self.workplaceNameSelected(obj.name);
                                _self.isContinueRecurFindName(false);
                                return;
                            } else {
                               _self.getNameWkpSelect(obj.childs, codeSelect);    
                            }
                        }    
                    }
                });                       
            }
            
            /**
             * init default data when start page
             */
            public start_page(): JQueryPromise<void> {
                var dfd = $.Deferred<void>();
                var _self = this;
               $('#tree-grid').ntsTreeComponent(_self.treeGrid).done(() => {
                    _self.getDataFromService();
                   _self.baseDate(new Date(_self.commonTableMonthDaySet().fiscalYear(), _self.commonTableMonthDaySet().arrMonth()[0].month()-1, 2));
                    
                   if (_.isEmpty($('#tree-grid').getRowSelected())) {
                        _self.enableSave(false);
                   } else {
                       _self.multiSelectedWorkplaceId.valueHasMutated();
                   }
                   
                    dfd.resolve();    
               });
               return dfd.promise();   
            }
            
            public saveWorkpalce(): void {
                let _self = this;
                if (!nts.uk.ui.errors.hasError()) {
                    service.save(_self.commonTableMonthDaySet().fiscalYear(), 
                                    _self.commonTableMonthDaySet().arrMonth(), 
                                    $('#tree-grid').getRowSelected()[0].workplaceId).done((data) => {
                    _self.getDataFromService();
                    nts.uk.ui.dialog.info({ messageId: "Msg_15" });
                });    
                }  
            }
            
            public removeWorkplace(): void {
                let _self = this;
                nts.uk.ui.dialog.confirm({ messageId: "Msg_18" }).ifYes(() => {
                    service.remove(_self.commonTableMonthDaySet().fiscalYear(), 
                                    $('#tree-grid').getRowSelected()[0].workplaceId).done(() => {
                        _self.getDataFromService();
                        nts.uk.ui.dialog.info({ messageId: "Msg_16" });
                    });  
                }).ifNo(() => {
                }).ifCancel(() => {
                }).then(() => {
                });   
            }
            
            public getDataFromService(): void {
                let _self = this;
                if ($('#tree-grid').getRowSelected()[0] != null) {
                    $.when(service.find(_self.commonTableMonthDaySet().fiscalYear(),$('#tree-grid').getRowSelected()[0].workplaceId), 
                            service.findFirstMonth(),
                            service.findAll()).done(function(data: any, data2: any, data3: any) {
                        _self.alreadySettingList.removeAll();
                        _.forEach(data3, function(wkpID) {
                            _self.alreadySettingList.push({'workplaceId': wkpID, 'isAlreadySetting': true});
                        });
                        if (typeof data === "undefined") {
                            /** 
                             *   create value null for prepare create new 
                            **/
                            _.forEach(_self.commonTableMonthDaySet().arrMonth(), function(value: any) {
                                value.day(0);
                            });
                            _self.enableDelete(false);
                        } else {
                            _self.commonTableMonthDaySet().arrMonth.removeAll();
                            for (let i=data2.startMonth-1; i<12; i++) {
                                _self.commonTableMonthDaySet().arrMonth.push({'month': ko.observable(data.publicHolidayMonthSettings[i].month), 
                                                                              'day': ko.observable(data.publicHolidayMonthSettings[i].inLegalHoliday), 
                                                                              'enable': ko.observable(true)});    
                            }
                            for (let i=0; i<data2.startMonth-1; i++) {
                                _self.commonTableMonthDaySet().arrMonth.push({'month': ko.observable(data.publicHolidayMonthSettings[i].month), 
                                                                              'day': ko.observable(data.publicHolidayMonthSettings[i].inLegalHoliday), 
                                                                              'enable': ko.observable(true)});    
                            } 
                            _self.enableDelete(true);
                        }         
                    });
                    
                } else {
                    _self.dataDefault();
                    _self.enableDelete(false);
                }
            }
            
            private dataDefault(): void {
                let _self = this;
                _.forEach(_self.commonTableMonthDaySet().arrMonth(), function(value: any) {
                    value.day(0);
                });
                _self.commonTableMonthDaySet().infoSelect2('');
                _self.commonTableMonthDaySet().infoSelect3('');
            }
       }
    
        class SystemType {
            // 個人情報
            static PERSONAL_INFORMATION: number = 1;
             // 就業
            static EMPLOYMENT: number = 2;
             // 給与
            static SALARY: number = 3;
            // 人事
            static HUMAN_RESOURCES: number = 4;
            // 管理者
            static ADMINISTRATOR: number = 5;
        }  

        export class TreeType {
             static WORK_PLACE = 1;
         }

        export class SelectionType {
            static SELECT_BY_SELECTED_CODE = 1;
            static SELECT_ALL = 2;
            static SELECT_FIRST_ITEM = 3;
            static NO_SELECT = 4;
        }
    }
}