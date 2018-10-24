module nts.uk.at.view.kmf002.d {

    import service = nts.uk.at.view.kmf002.d.service;
    import blockUI = nts.uk.ui.block;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import CommonTableMonthDaySet = nts.uk.at.view.kmf002.viewmodel.CommonTableMonthDaySet;
    export module viewmodel {

        export class ScreenModel {
            listComponentOption: any;
            selectedCode: KnockoutObservable<string>;
            multiSelectedCode: KnockoutObservableArray<string[]>;
            isShowAlreadySet: KnockoutObservable<boolean>;
            alreadySettingList: KnockoutObservableArray<UnitAlreadySettingModel[]>;
            isDialog: KnockoutObservable<boolean>;
            isShowNoSelectRow: KnockoutObservable<boolean>;
            isMultiSelect: KnockoutObservable<boolean>;
            employmentList: KnockoutObservableArray<UnitModel>;

            commonTableMonthDaySet: KnockoutObservable<CommonTableMonthDaySet>;
            enableSave: KnockoutObservable<boolean>;
            enableDelete: KnockoutObservable<boolean>;

            constructor() {
                let _self = this;
                _self.enableSave = ko.observable(true);
                _self.enableDelete= ko.observable(false);
                _self.selectedCode = ko.observable("");
                _self.multiSelectedCode = ko.observableArray([]);
                _self.isShowAlreadySet = ko.observable(true);
                _self.alreadySettingList = ko.observableArray([]);
                _self.isDialog = ko.observable(false);
                _self.isShowNoSelectRow = ko.observable(false);
                _self.isMultiSelect = ko.observable(false);
                _self.listComponentOption = {
                    isShowAlreadySet: _self.isShowAlreadySet(),
                    isMultiSelect: _self.isMultiSelect(),
                    listType: ListType.EMPLOYMENT,
                    selectType: SelectType.SELECT_FIRST_ITEM,
                    selectedCode: _self.selectedCode,
                    isDialog: _self.isDialog(),
                    isShowNoSelectRow: _self.isShowNoSelectRow(),
                    alreadySettingList: _self.alreadySettingList,
                    maxRows: 25
                };
                _self.employmentList = ko.observableArray<UnitModel>([]);
                _self.commonTableMonthDaySet = ko.observable(new CommonTableMonthDaySet());
                _self.commonTableMonthDaySet().fiscalYear(moment().format('YYYY'));
                _self.commonTableMonthDaySet().fiscalYear.subscribe(function(newValue) {
                    // change year
                    if (!nts.uk.ui.errors.hasError()) {
                        _self.getDataFromService();
                        service.findAllEmpRegister(_self.commonTableMonthDaySet().fiscalYear()).done(function(data: any) {
                            _self.alreadySettingList.removeAll();
                            _.forEach(data, function(code) {
                                _self.alreadySettingList.push({code: code, isAlreadySetting: true});
                            });                            
                        })
                    }
                });
                _self.commonTableMonthDaySet().visibleInfoSelect(true);
                _self.commonTableMonthDaySet().infoSelect1(nts.uk.resource.getText("Com_Employment"));
            }

            private findEmploymentSelect(codeEmployee: string): string {
                let nameEmpSelected: string;

                _.each($('#empt-list-setting').getDataList(), function(value) {
                    if (value.code == codeEmployee) {
                        nameEmpSelected = value.name;
                        return true;
                    }
                })

                return nameEmpSelected;
            }
            
            private setDefaultMonthDay(): void {
                let _self = this;
                for (let i=0; i<_self.commonTableMonthDaySet().arrMonth().length; i++) {
                    _self.commonTableMonthDaySet().arrMonth()[i].day(0); 
                }     
            }

            private catchChangeSelectEmp(): void {
                let _self = this;
                _self.selectedCode.subscribe(function(codeEmployee) {
                    _self.commonTableMonthDaySet().infoSelect2(codeEmployee);
                    _self.commonTableMonthDaySet().infoSelect3(_self.findEmploymentSelect(codeEmployee));
                    _self.getDataFromService();
                    
                    if (_.isUndefined(_self.selectedCode()) || _.isEmpty(_self.selectedCode()) 
                            || _.isNull(_self.selectedCode())) {
                        _self.enableDelete(false);
                        _self.enableSave(false);
                        _self.setDefaultMonthDay();    
                    } else {
                        _self.enableDelete(true);
                        _self.enableSave(true);
                    }
                });
                
                _self.selectedCode.valueHasMutated();
            }

            /**
             * init default data when start page
             */
            public start_page(): JQueryPromise<void> {
                let _self = this;
                var dfd = $.Deferred<void>();
                $.when($('#empt-list-setting').ntsListComponent(_self.listComponentOption),
                        service.findAllEmpRegister(_self.commonTableMonthDaySet().fiscalYear())).done(function(data: any, data2: any){
                    _self.catchChangeSelectEmp();
//                    _self.getDataFromService();
                    _.forEach(data2, function(code) {
                        _self.alreadySettingList.push({code: code, isAlreadySetting: true});
                    });
                    dfd.resolve();    
                })
                return dfd.promise();
            }

            private save(): void {
                let _self = this;
                if (!nts.uk.ui.errors.hasError()) {
                    _self.enableSave(false);
                    blockUI.invisible();
                    service.save(_self.commonTableMonthDaySet().fiscalYear(), _self.commonTableMonthDaySet().arrMonth(), _self.selectedCode()).done((data) => {
                        _self.getDataFromService();
                        _self.alreadySettingList.push({code: _self.selectedCode(), isAlreadySetting: true});
                        nts.uk.ui.dialog.info({ messageId: "Msg_15" }).then(() => {
                            _self.enableSave(true);
                        });
                    }).always(()=> blockUI.clear());    
                }
            }

            private remove(): void {
                let _self = this;
                nts.uk.ui.dialog.confirm({ messageId: "Msg_18" }).ifYes(() => {
                    service.remove(_self.commonTableMonthDaySet().fiscalYear(), _self.selectedCode()).done(() => {
                        _self.alreadySettingList.remove(function(s) { return s.code == _self.selectedCode() });
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
                if (nts.uk.ui.errors.hasError()) {
                    _self.setDefaultMonthDay();
                    return;
                }
                 $.when(service.find(_self.commonTableMonthDaySet().fiscalYear(), _self.selectedCode()), 
                                    service.findFirstMonth()).done(function(data: any, data2: any) {
                    if (typeof data === "undefined") {
                        /** 
                         *   create value null for prepare create new 
                        **/
                        _self.commonTableMonthDaySet().arrMonth.removeAll();
                        for (let i=data2.startMonth-1; i<12; i++) {
                            _self.commonTableMonthDaySet().arrMonth.push({'month': ko.observable(i+1), 'day': ko.observable(0), 'enable': ko.observable(true)});    
                        }
                        for (let i=0; i<data2.startMonth-1; i++) {
                            _self.commonTableMonthDaySet().arrMonth.push({'month': ko.observable(i+1), 'day': ko.observable(0), 'enable': ko.observable(true)});    
                        } 
                        _self.enableDelete(false);
                    } else {
                        _self.commonTableMonthDaySet().arrMonth.removeAll();
                        for (let i=data2.startMonth-1; i<12; i++) {
                            _self.commonTableMonthDaySet().arrMonth.push({'month': ko.observable(data.publicHolidayMonthSettings[i].month), 'day': ko.observable(data.publicHolidayMonthSettings[i].inLegalHoliday), 'enable': ko.observable(true)});    
                        }
                        for (let i=0; i<data2.startMonth-1; i++) {
                            _self.commonTableMonthDaySet().arrMonth.push({'month': ko.observable(data.publicHolidayMonthSettings[i].month), 'day': ko.observable(data.publicHolidayMonthSettings[i].inLegalHoliday), 'enable': ko.observable(true)});    
                        } 
                        _self.enableDelete(true);
                    }          
                });
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