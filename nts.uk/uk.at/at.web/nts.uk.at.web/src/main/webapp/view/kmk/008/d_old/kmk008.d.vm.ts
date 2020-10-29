module nts.uk.at.view.kmk008.d_old {
    import getText = nts.uk.resource.getText;
    import alertError = nts.uk.ui.dialog.alertError;
    
    export module viewmodel {
        export class ScreenModel {
            timeOfEmployment: KnockoutObservable<TimeOfEmploymentModel>;
            isUpdate: boolean;
            laborSystemAtr: number = 0;
            currentEmpName: KnockoutObservable<string>;
            textOvertimeName: KnockoutObservable<string>;

            maxRows: number;
            listComponentOption: any;
            selectedCode: KnockoutObservable<string>;
            isShowAlreadySet: KnockoutObservable<boolean>;
            alreadySettingList: KnockoutObservableArray<UnitAlreadySettingModel>;
            isDialog: KnockoutObservable<boolean>;
            isShowNoSelectRow: KnockoutObservable<boolean>;
            isMultiSelect: KnockoutObservable<boolean>;
            employmentList: KnockoutObservableArray<UnitModel>;
            isRemove: KnockoutObservable<boolean>;

            nameErrorWeek: KnockoutObservable<string> = ko.observable(getText("KMK008_22") + getText("KMK008_42"));
            nameAlarmWeek: KnockoutObservable<string> = ko.observable(getText("KMK008_22") + getText("KMK008_43"));
            nameLimitWeek: KnockoutObservable<string> = ko.observable(getText("KMK008_22") + getText("KMK008_44"));
            nameErrorTwoWeeks: KnockoutObservable<string> = ko.observable(getText("KMK008_23") + getText("KMK008_42"));
            nameAlarmTwoWeeks: KnockoutObservable<string> = ko.observable(getText("KMK008_23") + getText("KMK008_43"));
            nameLimitTwoWeeks: KnockoutObservable<string> = ko.observable(getText("KMK008_23") + getText("KMK008_44"));
            nameErrorFourWeeks: KnockoutObservable<string> = ko.observable(getText("KMK008_24") + getText("KMK008_42"));
            nameAlarmFourWeeks: KnockoutObservable<string> = ko.observable(getText("KMK008_24") + getText("KMK008_43"));
            nameLimitFourWeeks: KnockoutObservable<string> = ko.observable(getText("KMK008_24") + getText("KMK008_44"));
            nameErrorOneMonth: KnockoutObservable<string> = ko.observable(getText("KMK008_25") + getText("KMK008_42"));
            nameAlarmOneMonth: KnockoutObservable<string> = ko.observable(getText("KMK008_25") + getText("KMK008_43"));
            nameLimitOneMonth: KnockoutObservable<string> = ko.observable(getText("KMK008_25") + getText("KMK008_44"));
            nameErrorTwoMonths: KnockoutObservable<string> = ko.observable(getText("KMK008_26") + getText("KMK008_42"));
            nameAlarmTwoMonths: KnockoutObservable<string> = ko.observable(getText("KMK008_26") + getText("KMK008_43"));
            nameLimitTwoMonths: KnockoutObservable<string> = ko.observable(getText("KMK008_26") + getText("KMK008_44"));
            nameErrorThreeMonths: KnockoutObservable<string> = ko.observable(getText("KMK008_27") + getText("KMK008_42"));
            nameAlarmThreeMonths: KnockoutObservable<string> = ko.observable(getText("KMK008_27") + getText("KMK008_43"));
            nameLimitThreeMonths: KnockoutObservable<string> = ko.observable(getText("KMK008_27") + getText("KMK008_44"));
            nameErrorOneYear: KnockoutObservable<string> = ko.observable(getText("KMK008_28") + getText("KMK008_42"));
            nameAlarmOneYear: KnockoutObservable<string> = ko.observable(getText("KMK008_28") + getText("KMK008_43"));
            nameLimitOneYear: KnockoutObservable<string> = ko.observable(getText("KMK008_28") + getText("KMK008_44"));
            nameUpperMonth: KnockoutObservable<string> = ko.observable(getText("KMK008_120"));
            nameUpperMonthAverage: KnockoutObservable<string> = ko.observable(getText("KMK008_122"));
            
            constructor(laborSystemAtr: number) {
                let self = this;
                self.laborSystemAtr = laborSystemAtr;
                self.isUpdate = true;
                self.timeOfEmployment = ko.observable(new TimeOfEmploymentModel(null));
                self.currentEmpName = ko.observable("");
                self.textOvertimeName = ko.observable(getText("KMK008_12", ['#KMK008_8', '#Com_Employment']));

                self.selectedCode = ko.observable("");
                self.isShowAlreadySet = ko.observable(true);
                self.alreadySettingList = ko.observableArray([]);
                self.isRemove = ko.observable(false);

                self.isDialog = ko.observable(false);
                self.isShowNoSelectRow = ko.observable(false);
                self.isMultiSelect = ko.observable(false);
                self.listComponentOption = {
                    maxRows: 15,
                    isShowAlreadySet: self.isShowAlreadySet(),
                    isMultiSelect: self.isMultiSelect(),
                    listType: 1,
                    selectType: 1,
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
                    if (empSelect) {
                        self.currentEmpName(empSelect.name);
                        self.isRemove(empSelect.isAlreadySetting);
                    }
                });
            }

            startPage(): JQueryPromise<any> {
                let self = this;
                let dfd = $.Deferred();
                nts.uk.ui.errors.clearAll();
                if (self.laborSystemAtr == 0) {
                    self.textOvertimeName(getText("KMK008_12", ['{#KMK008_8}', '{#Com_Employment}']));
                } else {
                    self.textOvertimeName(getText("KMK008_12", ['{#KMK008_9}', '{#Com_Employment}']));
                }
                self.selectedCode('');
                self.getalreadySettingList();
                $('#empt-list-setting').ntsListComponent(self.listComponentOption).done(function() {
                    self.employmentList($('#empt-list-setting').getDataList());
                    if (self.employmentList().length > 0) {
                        self.selectedCode(self.employmentList()[0].code);
                    }
                    dfd.resolve();
                });
                return dfd.promise();
            }

            addUpdateData() {
                let self = this;
                
                if(self.employmentList().length == 0) return;
                
                let indexCodealreadySetting = _.findIndex(self.alreadySettingList(), item => { return item.code == self.selectedCode() });
                let timeOfEmploymentNew = new UpdateInsertTimeOfEmploymentModel(self.timeOfEmployment(), self.laborSystemAtr, self.selectedCode());
                nts.uk.ui.block.invisible();
                if (indexCodealreadySetting != -1) {
                    new service.Service().updateAgreementTimeOfEmployment(timeOfEmploymentNew).done(listError => {
                        if (listError.length > 0) {
                            self.showDialogError(listError);
                            nts.uk.ui.block.clear();
                            return;
                        }
                        nts.uk.ui.dialog.info({ messageId: "Msg_15" });
                        self.getDetail(self.selectedCode());
                    });
                    nts.uk.ui.block.clear();
                    return;
                }
                new service.Service().addAgreementTimeOfEmployment(timeOfEmploymentNew).done(listError => {
                    if (listError.length > 0) {
                        self.showDialogError(listError);
                        nts.uk.ui.block.clear();
                        return;
                    }
                    nts.uk.ui.dialog.info({ messageId: "Msg_15" });
                    self.getalreadySettingList();
                    self.getDetail(self.selectedCode());
                    nts.uk.ui.block.clear();
                });
                nts.uk.ui.block.clear();
            }

            removeData() {
                let self = this;
                nts.uk.ui.dialog.confirm(nts.uk.resource.getMessage("Msg_18", []))
                    .ifYes(() => {
                        let deleteModel = new DeleteTimeOfEmploymentModel(self.laborSystemAtr, self.selectedCode());
                        new service.Service().removeAgreementTimeOfEmployment(deleteModel).done(function() {
                            self.getalreadySettingList();
                            self.getDetail(self.selectedCode());
                            self.isRemove(false);
                        });
                        nts.uk.ui.dialog.info(nts.uk.resource.getMessage("Msg_16", []));
                    });
            }

            getalreadySettingList() {
                let self = this;
                self.alreadySettingList([]);
                new service.Service().getList(self.laborSystemAtr).done(data => {
                    if (data.employmentCategoryCodes.length > 0) {
                        self.alreadySettingList(_.map(data.employmentCategoryCodes, item => {
                            return new UnitAlreadySettingModel(item.toString(), true);
                        }));
                        _.defer(() => self.employmentList($('#empt-list-setting').getDataList()));
                    }
                })
                self.isRemove(self.isShowAlreadySet());
            }

            getDetail(employmentCategoryCode: string) {
                let self = this;
                new service.Service().getDetail(self.laborSystemAtr, employmentCategoryCode).done(data => {
                    self.timeOfEmployment(new TimeOfEmploymentModel(data));
                }).fail(error => {

                });
            }

            setSelectCodeAfterRemove(currentSelectCode: string) {
                let self = this;
                let empLength = self.employmentList().length;
                if (empLength == 0) {
                    self.selectedCode("");
                    return;
                }
                let empSelectIndex = _.findIndex(self.employmentList(), emp => {
                    return emp.code == self.selectedCode();
                });
                if (empSelectIndex == -1) {
                    self.selectedCode("");
                    return;
                }
                if (empSelectIndex == 0 && empLength == 1) {
                    self.getDetail(currentSelectCode);
                    return;
                }
                if (empSelectIndex == 0 && empLength > 1) {
                    self.selectedCode(self.employmentList()[empSelectIndex + 1].code);
                    return;
                }

                if (empSelectIndex < empLength - 1) {
                    self.selectedCode(self.employmentList()[empSelectIndex + 1].code);
                    return;
                }
                if (empSelectIndex == empLength - 1) {
                    self.selectedCode(self.employmentList()[empSelectIndex - 1].code);
                    return;
                }
            }
            
            showDialogError(listError: any) {
                let errorCode = _.split(listError[0], ',');
                if (errorCode[0] === 'Msg_59') {
                    let periodName = getText(errorCode[1]);
                    let param1 = "期間: " + getText(errorCode[1]) + "<br>" + getText(errorCode[2]);
                    alertError({ messageId: errorCode[0], messageParams: [param1, getText(errorCode[3])] });
                } else {
                    alertError({ messageId: errorCode[0], messageParams: [getText(errorCode[1]), getText(errorCode[2]), getText(errorCode[3])] });
                }
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
            upperMonth: KnockoutObservable<string> = ko.observable(null);
            upperMonthAverage: KnockoutObservable<string> = ko.observable(null);

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
                self.upperMonth(data.upperMonth);
                self.upperMonthAverage(data.upperMonthAverage);
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
            upperMonth: number = 0;
            upperMonthAverage: number = 0;

            constructor(data: TimeOfEmploymentModel, laborSystemAtr: number, employmentCategoryCode: string) {
                let self = this;
                self.laborSystemAtr = laborSystemAtr;
                self.employmentCategoryCode = employmentCategoryCode;
                if (!data) return;
                self.alarmWeek = +data.alarmWeek() || 0;
                self.errorWeek = +data.errorWeek() || 0;
                self.limitWeek = +data.limitWeek() || 0;
                self.alarmTwoWeeks = +data.alarmTwoWeeks() || 0;
                self.errorTwoWeeks = +data.errorTwoWeeks() || 0;
                self.limitTwoWeeks = +data.limitTwoWeeks() || 0;
                self.alarmFourWeeks = +data.alarmFourWeeks() || 0;
                self.errorFourWeeks = +data.errorFourWeeks() || 0;
                self.limitFourWeeks = +data.limitFourWeeks() || 0;
                self.alarmOneMonth = +data.alarmOneMonth() || 0;
                self.errorOneMonth = +data.errorOneMonth() || 0;
                self.limitOneMonth = +data.limitOneMonth() || 0;
                self.alarmTwoMonths = +data.alarmTwoMonths() || 0;
                self.errorTwoMonths = +data.errorTwoMonths() || 0;
                self.limitTwoMonths = +data.limitTwoMonths() || 0;
                self.alarmThreeMonths = +data.alarmThreeMonths() || 0;
                self.errorThreeMonths = +data.errorThreeMonths() || 0;
                self.limitThreeMonths = +data.limitThreeMonths() || 0;
                self.alarmOneYear = +data.alarmOneYear() || 0;
                self.errorOneYear = +data.errorOneYear() || 0;
                self.limitOneYear = +data.limitOneYear() || 0;
                self.upperMonth = +data.upperMonth() || 0;
                self.upperMonthAverage = +data.upperMonthAverage() || 0;
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

        export interface UnitModel {
            code: string;
            name?: string;
            affiliationName?: string;
            isAlreadySetting?: boolean;

        }


        export class UnitAlreadySettingModel {
            code: string;
            isAlreadySetting: boolean;
            constructor(code: string, isAlreadySetting: boolean) {
                this.code = code;
                this.isAlreadySetting = isAlreadySetting;
            }
        }


    }
}
