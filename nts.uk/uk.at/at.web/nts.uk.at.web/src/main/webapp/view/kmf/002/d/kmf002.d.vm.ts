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

            commonTableMonthDaySet: KnockoutObservable<any>;

            constructor() {
                let _self = this;
                _self.selectedCode = ko.observable();
                _self.multiSelectedCode = ko.observableArray(['0', '1', '4']);
                _self.isShowAlreadySet = ko.observable(true);
                _self.alreadySettingList = ko.observableArray([
                    { code: '1', isAlreadySetting: true },
                    { code: '2', isAlreadySetting: true }
                ]);
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
                _self.commonTableMonthDaySet = new nts.uk.at.view.kmf002.viewmodel.CommonTableMonthDaySet();
                _self.commonTableMonthDaySet.fiscalYear.subscribe(function(newValue) {
                    // change year
                    _self.getDataFromService();
                });
                _self.commonTableMonthDaySet.visibleInfoSelect(true);
                _self.commonTableMonthDaySet.infoSelect1(nts.uk.resource.getText("Com_Employment"));
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
                    _self.commonTableMonthDaySet.infoSelect2(codeEmployee);
                    _self.commonTableMonthDaySet.infoSelect3(_self.findEmploymentSelect(codeEmployee));
                });
            }

            /**
             * init default data when start page
             */
            public start_page(): JQueryPromise<void> {
                let _self = this;
                $('#empt-list-setting').ntsListComponent(_self.listComponentOption);
                _self.catchChangeSelectEmp();
                _self.getDataFromService();
                nts.uk.ui.errors.clearAll();
                var dfd = $.Deferred<void>();

                dfd.resolve();
                return dfd.promise();
            }

            private save(): void {
                let _self = this;
                //               var dfd = $.Deferred<void>();
                service.save(_self.commonTableMonthDaySet.fiscalYear(), _self.commonTableMonthDaySet.arrMonth(), _self.selectedCode()).done((data) => {
                    _self.getDataFromService();
                    nts.uk.ui.dialog.info({ messageId: "Msg_15" });
                });
            }

            private remove(): void {
                let _self = this;
                nts.uk.ui.dialog.confirm({ messageId: "Msg_18" }).ifYes(() => {
                    service.remove(_self.commonTableMonthDaySet.fiscalYear(), _self.selectedCode()).done((data) => {
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
                service.find(_self.commonTableMonthDaySet.fiscalYear(), _self.selectedCode()).done((data) => {
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