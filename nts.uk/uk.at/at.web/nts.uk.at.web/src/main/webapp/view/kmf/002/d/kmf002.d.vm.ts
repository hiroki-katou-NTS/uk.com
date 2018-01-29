module nts.uk.at.view.kmf002.d {

    import service = nts.uk.at.view.kmf002.d.service;
    
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

            commonTableMonthDaySet: KnockoutObservable<nts.uk.at.view.kmf002.viewmodel.CommonTableMonthDaySet>;
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
                    selectType: SelectType.NO_SELECT,
                    selectedCode: _self.selectedCode,
                    isDialog: _self.isDialog(),
                    isShowNoSelectRow: _self.isShowNoSelectRow(),
                    alreadySettingList: _self.alreadySettingList,
                    maxRows: 25
                };
                _self.employmentList = ko.observableArray<UnitModel>([]);
                _self.commonTableMonthDaySet = ko.observable(new nts.uk.at.view.kmf002.viewmodel.CommonTableMonthDaySet());
                _self.commonTableMonthDaySet().fiscalYear.subscribe(function(newValue) {
                    // change year
                    _self.getDataFromService();
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

            private catchChangeSelectEmp(): void {
                let _self = this;
                _self.selectedCode.subscribe(function(codeEmployee) {
                    _self.commonTableMonthDaySet().infoSelect2(codeEmployee);
                    _self.commonTableMonthDaySet().infoSelect3(_self.findEmploymentSelect(codeEmployee));
                    _self.getDataFromService();
                    
                    if (_.isUndefined(_self.selectedCode()) || _.isEmpty(_self.selectedCode())) {
                        _self.enableDelete(false);    
                    } else {
                        _self.enableDelete(true);
                    }
                });
            }

            /**
             * init default data when start page
             */
            public start_page(): JQueryPromise<void> {
                let _self = this;
                var dfd = $.Deferred<void>();
                $('#empt-list-setting').ntsListComponent(_self.listComponentOption).done(function(){
                    _self.catchChangeSelectEmp();
                    _self.getDataFromService();
                    nts.uk.ui.errors.clearAll();
                    dfd.resolve();    
                });
                return dfd.promise();
            }

            private save(): void {
                let _self = this;
//                _self.validateInput();
                if (!nts.uk.ui.errors.hasError()) {
                    service.save(_self.commonTableMonthDaySet().fiscalYear(), _self.commonTableMonthDaySet().arrMonth(), _self.selectedCode()).done((data) => {
                        _self.getDataFromService();
                        nts.uk.ui.dialog.info({ messageId: "Msg_15" });
                    });    
                }
            }

            private remove(): void {
                let _self = this;
                nts.uk.ui.dialog.confirm({ messageId: "Msg_18" }).ifYes(() => {
                    service.remove(_self.commonTableMonthDaySet().fiscalYear(), _self.selectedCode()).done(() => {
                        _self.getDataFromService();
                        nts.uk.ui.dialog.info({ messageId: "Msg_16" });
                    });
                }).ifNo(() => {
                }).ifCancel(() => {
                }).then(() => {
                });   
            }
            
            private validateInput(): void {
                $('.validateInput').ntsEditor("validate");        
            }
            

            public getDataFromService(): void {
                let _self = this;
                
                 $.when(service.find(_self.commonTableMonthDaySet().fiscalYear(), _self.selectedCode()), 
                                    service.findFirstMonth(),
                                    service.findAllEmpRegister()).done(function(data: any, data2: any, data3: any) {
                    _self.alreadySettingList.removeAll();
                    _.forEach(data3, function(code) {
                        _self.alreadySettingList.push({code: code, isAlreadySetting: true});
                    });
                    if (typeof data === "undefined") {
                        /** 
                         *   create value null for prepare create new 
                        **/
                        _.forEach(_self.commonTableMonthDaySet().arrMonth(), function(value) {
                            value.day(0);
                        });
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