module nts.uk.at.view.kmf002.b {
    
    import service = nts.uk.at.view.kmf002.b.service;
    
    export module viewmodel {
        export class ScreenModel {
            commonTableMonthDaySet: KnockoutObservable<any>;
            multiSelectedWorkplaceId: KnockoutObservable<string>;
            baseDate: KnockoutObservable<Date>;
            alreadySettingList: KnockoutObservableArray<UnitAlreadySettingModel>;
            treeGrid: TreeComponentOption;
            workplaceNameSelected: KnockoutObservable<string>;

            constructor(){
                let _self = this;
                _self.commonTableMonthDaySet = new nts.uk.at.view.kmf002.viewmodel.CommonTableMonthDaySet();
                _self.commonTableMonthDaySet.visibleInfoSelect(true);
                _self.commonTableMonthDaySet.infoSelect1(nts.uk.resource.getText("Com_Workplace"));
                
                
                _self.baseDate = ko.observable(new Date());
                _self.multiSelectedWorkplaceId = ko.observableArray([]);
                _self.alreadySettingList = ko.observableArray([]);
                _self.treeGrid = {
                        isShowAlreadySet: true,
                        isMultiSelect: false,
                        treeType: TreeType.WORK_PLACE,
                        selectedWorkplaceId: _self.multiSelectedWorkplaceId,
                        baseDate: _self.baseDate,
                        selectType: SelectionType.NO_SELECT,
                        isShowSelectButton: true,
                        isDialog: false,
                        alreadySettingList: _self.alreadySettingList,
                        maxRows: 10,
                        tabindex: 1,
                        systemType : SystemType.EMPLOYMENT
                };
                
                _self.multiSelectedWorkplaceId.subscribe(function(newValue) {
                    try {
                        _self.commonTableMonthDaySet.infoSelect2($('#tree-grid').getRowSelected()[0].workplaceCode);
                    
                        _self.getNameWkpSelect($('#tree-grid').getDataList(), 
                                               $('#tree-grid').getRowSelected()[0].workplaceCode);
                        
                        _self.commonTableMonthDaySet.infoSelect3(_self.workplaceNameSelected());
                        _self.getDataFromService();
                    }
                    catch (e){
                    }
                    
                });
                
                _self.workplaceNameSelected = ko.observable();
            }
            
            private getNameWkpSelect(data: any, codeSelect: string): string {
                let _self = this;
                _.forEach(data, function(value) {
                    if (value.code == codeSelect) {
                        _self.workplaceNameSelected(value.name);
                        return false;
                    } else {
                        if (typeof value.childs !== "undefined" && value.childs.length > 0) {
                            for (var i=0; i<value.childs.length; i++) {
                                _self.getNameWkpSelect(new Array(value.childs[i]), codeSelect);    
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
               });
                
                nts.uk.ui.errors.clearAll();
                dfd.resolve();
                return dfd.promise();
            }
            
            public saveWorkpalce(): void {
                let _self = this;
                service.save(_self.commonTableMonthDaySet.fiscalYear(), _self.commonTableMonthDaySet.arrMonth(), $('#tree-grid').getRowSelected()[0].workplaceId).done((data) => {
                    _self.getDataFromService();
                    nts.uk.ui.dialog.info({ messageId: "Msg_15" });
                });  
            }
            
            public removeWorkplace(): void {
                let _self = this;
                nts.uk.ui.dialog.confirm({ messageId: "Msg_18" }).ifYes(() => { 
                    alert("Yes");
                    service.remove(_self.commonTableMonthDaySet.fiscalYear(), $('#tree-grid').getRowSelected()[0].workplaceId).done((data) => {
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
                    service.find(_self.commonTableMonthDaySet.fiscalYear(), $('#tree-grid').getRowSelected()[0].workplaceId).done((data) => {
                        if (typeof data === "undefined") {
                            /** 
                             *   create value null for prepare create new 
                            **/
                            _.forEach(_self.commonTableMonthDaySet.arrMonth(), function(value) {
                                value.day('');
                            });
                        } else {
                            for (let i=0; i<data.publicHolidayMonthSettings.length; i++) {
                                _self.commonTableMonthDaySet.arrMonth()[i].day(data.publicHolidayMonthSettings[i].inLegalHoliday);
                            }
                        }
                    });    
                } else {
                    _self.dataDefault();
                }
            }
            
            private dataDefault(): void {
                let _self = this;
                _.forEach(_self.commonTableMonthDaySet.arrMonth(), function(value) {
                    value.day('');
                });
                _self.commonTableMonthDaySet.infoSelect2('');
                _self.commonTableMonthDaySet.infoSelect3('');
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

        class TreeType {
             static WORK_PLACE = 1;
         }

        class SelectionType {
            static SELECT_BY_SELECTED_CODE = 1;
            static SELECT_ALL = 2;
            static SELECT_FIRST_ITEM = 3;
            static NO_SELECT = 4;
        }
    }
}