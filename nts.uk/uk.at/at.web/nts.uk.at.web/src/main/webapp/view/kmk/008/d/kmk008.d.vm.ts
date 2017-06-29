module nts.uk.at.view.kmk008.d {
    export module viewmodel {
        export class ScreenModel {
            timeOfCompany: KnockoutObservable<TimeOfEmploymentModel>;
            isUpdate: boolean;
            laborSystemAtr: number = 0;
            currentEmpName:  KnockoutObservable<string>;
            
            listComponentOption: any;
            selectedCode: KnockoutObservable<string>;
            isShowAlreadySet: KnockoutObservable<boolean>;
            alreadySettingList: KnockoutObservableArray<UnitAlreadySettingModel>;
            isDialog: KnockoutObservable<boolean>;
            isShowNoSelectRow: KnockoutObservable<boolean>;
            isMultiSelect: KnockoutObservable<boolean>;
            employmentList: KnockoutObservableArray<UnitModel>;
            constructor(laborSystemAtr: number) {
                let self = this;
                self.laborSystemAtr = laborSystemAtr;
                self.isUpdate = true;
                self.timeOfCompany = ko.observable(new TimeOfEmploymentModel(null));
                self.currentEmpName = ko.observable("");
                
                self.selectedCode = ko.observable("");
                self.isShowAlreadySet = ko.observable(true);
                self.alreadySettingList = ko.observableArray([
                    { code: '1', isAlreadySetting: true },
                    { code: '10', isAlreadySetting: true },
                    { code: '11', isAlreadySetting: true },
                ]);
                
                self.isDialog = ko.observable(false);
                self.isShowNoSelectRow = ko.observable(false);
                self.isMultiSelect = ko.observable(false);
                self.listComponentOption = {
                    isShowAlreadySet: self.isShowAlreadySet(),
                    isMultiSelect: self.isMultiSelect(),
                    listType: ListType.EMPLOYMENT,
                    selectType: SelectType.SELECT_BY_SELECTED_CODE,
                    selectedCode: self.selectedCode,
                    isDialog: self.isDialog(),
                    isShowNoSelectRow: self.isShowNoSelectRow(),
                    alreadySettingList: self.alreadySettingList
                };
                self.employmentList = ko.observableArray<UnitModel>([]);
                self.selectedCode.subscribe(newValue => {
                    if (nts.uk.text.isNullOrEmpty(newValue)) return;
                    self.getDetail(newValue);
                    let empSelect = _.find(self.employmentList(), emp => {
                        return emp.code == newValue;
                    });
                    if(empSelect){ self.currentEmpName(empSelect.name);}
                   
                });
                
                self.startPage();
            }

            startPage(): JQueryPromise<any> {
                let self = this;
                let dfd = $.Deferred();
                self.alreadySettingList([]);
                new service.Service().getList(self.laborSystemAtr).done(data => {
                    if (data.length > 0) {
                        self.alreadySettingList(_.map(data, item => { return { code: item, isAlreadySetting: true } }));
                    }
                })       
                $('#empt-list-setting').ntsListComponent(self.listComponentOption).done(function(){
                    self.employmentList($('#empt-list-setting').getDataList());
                    if(self.employmentList().length > 0){
                         self.selectedCode(self.employmentList()[0].code);
                    }
                    dfd.resolve();
                });             
                return dfd.promise();
            }

            addUpdateData() {
                let self = this;
                let indexCodealreadySetting = _.findIndex(self.alreadySettingList(), code => { return code == self.selectedCode() });
                let timeOfCompanyNew = new UpdateInsertTimeOfEmploymentModel(self.timeOfCompany(), self.laborSystemAtr, self.selectedCode());

                if (indexCodealreadySetting != -1) {
                    new service.Service().updateAgreementTimeOfEmployment(timeOfCompanyNew).done(function() {
                        self.getDetail();
                    });
                    return;
                }
                new service.Service().addAgreementTimeOfEmployment(timeOfCompanyNew).done(function() {
                    self.getDetail();
                });
            }

            removeData() {

            }

            getDetail(employmentCategoryCode: string) {
                let self = this;
                new service.Service().getDetail(self.laborSystemAtr, employmentCategoryCode).done(data => {
                    self.timeOfCompany(new TimeOfEmploymentModel(data));
                }).fail(error => {

                });
            }
        }

        export class TimeOfEmploymentModel {
            alarmWeek: KnockoutObservable<string> = ko.observable(null);
            errorWeek: KnockoutObservable<string> = ko.observable(null);
            limitWeek: KnockoutObservable<string> = ko.observable(null);
            alarmTwoWeeks: KnockoutObservable<string> = ko.observable(null);
            errorTwoWeeks: KnockoutObservable<string> = ko.observable(null);
            limitTwoWeeks: KnockoutObservable<string> = ko.observable(null);
            alarmFourWeeks: KnockoutObservable<string> = ko.observable(null);
            errorFourWeeks: KnockoutObservable<string> = ko.observable(null);
            limitFourWeeks: KnockoutObservable<string> = ko.observable(null);
            alarmOneMonth: KnockoutObservable<string> = ko.observable(null);
            errorOneMonth: KnockoutObservable<string> = ko.observable(null);
            limitOneMonth: KnockoutObservable<string> = ko.observable(null);
            alarmTwoMonths: KnockoutObservable<string> = ko.observable(null);
            errorTwoMonths: KnockoutObservable<string> = ko.observable(null);
            limitTwoMonths: KnockoutObservable<string> = ko.observable(null);
            alarmThreeMonths: KnockoutObservable<string> = ko.observable(null);
            errorThreeMonths: KnockoutObservable<string> = ko.observable(null);
            limitThreeMonths: KnockoutObservable<string> = ko.observable(null);
            alarmOneYear: KnockoutObservable<string> = ko.observable(null);
            errorOneYear: KnockoutObservable<string> = ko.observable(null);
            limitOneYear: KnockoutObservable<string> = ko.observable(null);
            constructor(data: any) {
                let self = this;
                if (!data) return;
                self.alarmWeek(data.alarmWeek);
                self.errorWeek(data.errorWeek);
                self.limitWeek(data.limitWeek);
                self.alarmTwoWeeks(data.alarmTwoWeeks);
                self.errorTwoWeeks(data.errorTwoWeeks);
                self.limitTwoWeeks(data.limitTwoWeeks);
                self.alarmFourWeeks(data.alarmFourWeeks);
                self.errorFourWeeks(data.errorFourWeeks);
                self.limitFourWeeks(data.limitFourWeeks);
                self.alarmOneMonth(data.alarmOneMonth);
                self.errorOneMonth(data.errorOneMonth);
                self.limitOneMonth(data.limitOneMonth);
                self.alarmTwoMonths(data.alarmTwoMonths);
                self.errorTwoMonths(data.errorTwoMonths);
                self.limitTwoMonths(data.limitTwoMonths);
                self.alarmThreeMonths(data.alarmThreeMonths);
                self.errorThreeMonths(data.errorThreeMonths);
                self.limitThreeMonths(data.limitThreeMonths);
                self.alarmOneYear(data.alarmOneYear);
                self.errorOneYear(data.errorOneYear);
                self.limitOneYear(data.limitOneYear);
            }
        }

        export class UpdateInsertTimeOfEmploymentModel {
            laborSystemAtr: number = 0;
            employmentCategoryCode: string = "";
            alarmWeek: number = 0;
            errorWeek: number = 0;
            limitWeek: number = 0;
            alarmTwoWeeks: number = 0;
            errorTwoWeeks: number = 0;
            limitTwoWeeks: number = 0;
            alarmFourWeeks: number = 0;
            errorFourWeeks: number = 0;
            limitFourWeeks: number = 0;
            alarmOneMonth: number = 0;
            errorOneMonth: number = 0;
            limitOneMonth: number = 0;
            alarmTwoMonths: number = 0;
            errorTwoMonths: number = 0;
            limitTwoMonths: number = 0;
            alarmThreeMonths: number = 0;
            errorThreeMonths: number = 0;
            limitThreeMonths: number = 0;
            alarmOneYear: number = 0;
            errorOneYear: number = 0;
            limitOneYear: number = 0;
            constructor(data: TimeOfEmploymentModel, laborSystemAtr: number, employmentCategoryCode: string) {
                let self = this;
                self.laborSystemAtr = laborSystemAtr;
                self.employmentCategoryCode = employmentCategoryCode;
                if (!data) return;
                self.alarmWeek = data.alarmWeek() || 0;
                self.errorWeek = data.errorWeek() || 0;
                self.limitWeek = data.limitWeek() || 0;
                self.alarmTwoWeeks = data.alarmTwoWeeks() || 0;
                self.errorTwoWeeks = data.errorTwoWeeks() || 0;
                self.limitTwoWeeks = data.limitTwoWeeks() || 0;
                self.alarmFourWeeks = data.alarmFourWeeks() || 0;
                self.errorFourWeeks = data.errorFourWeeks() || 0;
                self.limitFourWeeks = data.limitFourWeeks() || 0;
                self.alarmOneMonth = data.alarmOneMonth() || 0;
                self.errorOneMonth = data.errorOneMonth() || 0;
                self.limitOneMonth = data.limitOneMonth() || 0;
                self.alarmTwoMonths = data.alarmTwoMonths() || 0;
                self.errorTwoMonths = data.errorTwoMonths() || 0;
                self.limitTwoMonths = data.limitTwoMonths() || 0;
                self.alarmThreeMonths = data.alarmThreeMonths() || 0;
                self.errorThreeMonths = data.errorThreeMonths() || 0;
                self.limitThreeMonths = data.limitThreeMonths() || 0;
                self.alarmOneYear = data.alarmOneYear() || 0;
                self.errorOneYear = data.errorOneYear() || 0;
                self.limitOneYear = data.limitOneYear() || 0;
            }
        }

        export class DeleteTimeOfEmploymentModel {
            laborSystemAtr: number = 0;
            employmentCategoryCode: string;
            constructor(laborSystemAtr: number, employmentCategoryCode: string) {
                let self = this;
                self.laborSystemAtr = laborSystemAtr;
                self.employmentCategoryCode = employmentCategoryCode;
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
