module nts.uk.at.view.kmf002.d {
    
//    import commonTableMonthDaySet1 = nts.uk.at.view.kmf002.viewmodel;
    
    export module viewmodel {
        
        export class ScreenModel {
            listComponentOption: any;
            selectedCode: KnockoutObservable<string>;
            multiSelectedCode: KnockoutObservableArray<string>;
            isShowAlreadySet: KnockoutObservable<boolean>;
            alreadySettingList: KnockoutObservableArray<UnitAlreadySettingModel>;
            isDialog: KnockoutObservable<boolean>;
            isShowNoSelectRow: KnockoutObservable<boolean>;
            isMultiSelect: KnockoutObservable<boolean>;
            employmentList: KnockoutObservableArray<UnitModel>;
            
//            commonTableMonthDaySet: KnockoutObservable<any>;
            
            constructor() {
                let _self = this;
                _self.selectedCode = ko.observable('1');
                _self.multiSelectedCode = ko.observableArray(['0', '1', '4']);
                _self.isShowAlreadySet = ko.observable(true);
                _self.alreadySettingList = ko.observableArray([
                    {code: '1', isAlreadySetting: true},
                    {code: '2', isAlreadySetting: true}
                ]);
                _self.isDialog = ko.observable(true);
                _self.isShowNoSelectRow = ko.observable(false);
                _self.isMultiSelect = ko.observable(false);
                _self.listComponentOption = {
                    isShowAlreadySet: _self.isShowAlreadySet(),
                    isMultiSelect: _self.isMultiSelect(),
                    listType: ListType.EMPLOYMENT,
                    selectType: SelectType.SELECT_BY_SELECTED_CODE,
                    selectedCode: _self.selectedCode,
                    isDialog: _self.isDialog(),
                    isShowNoSelectRow: _self.isShowNoSelectRow(),
                    alreadySettingList: _self.alreadySettingList,
                    maxRows: 12
                };
                
                _self.employmentList = ko.observableArray<UnitModel>([]);
                
                _self.commonTableMonthDaySet = new nts.uk.at.view.kmf002.viewmodel.CommonTableMonthDaySet();
                
                
                _self.commonTableMonthDaySet.fiscalYear.subscribe(function(newValue) {
                    // change year
                    _self.getDataFromService();
                });
                
                _self.commonTableMonthDaySet.visibleInfoSelect(true);
                _self.commonTableMonthDaySet.infoSelect1( nts.uk.resource.getText("Com_Employment"));
                _self.commonTableMonthDaySet.infoSelect1( '123');
                _self.commonTableMonthDaySet.infoSelect1( '456');
                
            }
            
            /**
             * init default data when start page
             */
            public start_page(): JQueryPromise<void> {
                let _self = this;
                $('#empt-list-setting').ntsListComponent(_self.listComponentOption);
                var dfd = $.Deferred<void>();
                
                dfd.resolve();
                return dfd.promise();
            }
            
            public getDataFromService(): void {
                
            }
        }
        export class ListType {
            static EMPLOYMENT = 1;
            static Classification = 2;
            static JOB_TITLE = 3;
            static EMPLOYEE = 4;
        }

        export interface UnitModel {
            code: string;
            name?: string;
            workplaceName?: string;
            isAlreadySetting?: boolean;
        }
        
        export class SelectType {
            static SELECT_BY_SELECTED_CODE = 1;
            static SELECT_ALL = 2;
            static SELECT_FIRST_ITEM = 3;
            static NO_SELECT = 4;
        }
        
        export interface UnitAlreadySettingModel {
            code: string;
            isAlreadySetting: boolean;
        }
    }
}