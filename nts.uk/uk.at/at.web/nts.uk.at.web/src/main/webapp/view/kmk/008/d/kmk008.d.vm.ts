module nts.uk.at.view.kmk008.d {
    import getText = nts.uk.resource.getText;
    import alertError = nts.uk.ui.dialog.alertError;
    
    export module viewmodel {
        export class ScreenModel {
            timeOfEmployment: KnockoutObservable<TimeOfEmploymentModel>;
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
			
			limitOptions: any;
			
			selectedLimit: KnockoutObservable<number> = ko.observable(4); // Default 4 times
            
            constructor(laborSystemAtr: number) {
                let self = this;
                self.laborSystemAtr = laborSystemAtr;
                self.timeOfEmployment = ko.observable(new TimeOfEmploymentModel(null));
                self.currentEmpName = ko.observable("");
                self.textOvertimeName = ko.observable(getText("KMK008_12", ['#KMK008_8', '#Com_Employment']));
				
				self.limitOptions = [
					{code: 0, name : getText('KMK008_190')},
					{code: 1, name : getText('KMK008_191')},
					{code: 2, name : getText('KMK008_192')},
					{code: 3, name : getText('KMK008_193')},
					{code: 4, name : getText('KMK008_194')},
					{code: 5, name : getText('KMK008_195')},
					{code: 6, name : getText('KMK008_196')},
					{code: 7, name : getText('KMK008_197')},
					{code: 8, name : getText('KMK008_198')},
					{code: 9, name : getText('KMK008_199')},
					{code: 10, name : getText('KMK008_200')},
					{code: 11, name : getText('KMK008_201')},
					{code: 12, name : getText('KMK008_202')}
				];

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

                self.getalreadySettingList();
                $('#empt-list-setting').ntsListComponent(self.listComponentOption).done(function() {
                    self.employmentList($('#empt-list-setting').getDataList());
                    if (self.employmentList().length > 0) {
						if (self.selectedCode() == '') {
							self.selectedCode(self.employmentList()[0].code);
						}
                    }
                    dfd.resolve();
                });
                return dfd.promise();
            }

            addUpdateData() {
                let self = this;
                
                if(self.employmentList().length == 0) return;

                let timeOfEmploymentNew = new UpdateInsertTimeOfEmploymentModel(self.timeOfEmployment(), self.laborSystemAtr, self.selectedCode());
                console.log (self.selectedCode());
                nts.uk.ui.block.invisible();

                new service.Service().addAgreementTimeOfEmployment(timeOfEmploymentNew).done(() => {
						nts.uk.ui.dialog.info({ messageId: "Msg_15" }).then(function() {
							self.startPage();
						});
                    }).fail((error)=>{
						error.parameterIds.unshift("Q&A 34201");
						alertError({ messageId: error.messageId, messageParams: error.parameterIds});
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
                });
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
            overMaxTimes: KnockoutObservable<string> = ko.observable(null);

			limitOneMonth: KnockoutObservable<string> = ko.observable(null);
			alarmOneMonth: KnockoutObservable<string> = ko.observable(null);
			errorOneMonth: KnockoutObservable<string> = ko.observable(null);

			limitTwoMonths: KnockoutObservable<string> = ko.observable(null);
			alarmTwoMonths: KnockoutObservable<string> = ko.observable(null);
			errorTwoMonths: KnockoutObservable<string> = ko.observable(null);

			alarmOneYear: KnockoutObservable<string> = ko.observable(null);
			errorOneYear: KnockoutObservable<string> = ko.observable(null);

			limitOneYear: KnockoutObservable<string> = ko.observable(null);
			errorTwoYear: KnockoutObservable<string> = ko.observable(null);
			alarmTwoYear: KnockoutObservable<string> = ko.observable(null);

			errorMonthAverage: KnockoutObservable<string> = ko.observable(null);
			alarmMonthAverage: KnockoutObservable<string> = ko.observable(null);

            constructor(data: any) {
                let self = this;
                if (!data) return;
				self.overMaxTimes(data.overMaxTimes);

				self.limitOneMonth(data.limitOneMonth);
                self.alarmOneMonth(data.alarmOneMonth);
                self.errorOneMonth(data.errorOneMonth);

                self.limitTwoMonths(data.limitTwoMonths);
                self.alarmTwoMonths(data.alarmTwoMonths);
                self.errorTwoMonths(data.errorTwoMonths);

                self.alarmOneYear(data.alarmOneYear);
                self.errorOneYear(data.errorOneYear);

				self.limitOneYear(data.limitOneYear);
				self.errorTwoYear(data.errorTwoYear);
				self.alarmTwoYear(data.alarmTwoYear);

				self.errorMonthAverage(data.errorMonthAverage);
				self.alarmMonthAverage(data.alarmMonthAverage);
            }
        }

        export class UpdateInsertTimeOfEmploymentModel {
            laborSystemAtr: number = 0;
			overMaxTimes: number = 0;
			employmentCD: string = "";

			limitOneMonth: number = 0;
			alarmOneMonth: number = 0;
            errorOneMonth: number = 0;

			limitTwoMonths: number = 0;
            alarmTwoMonths: number = 0;
            errorTwoMonths: number = 0;

            alarmOneYear: number = 0;
            errorOneYear: number = 0;

			limitOneYear: number = 0;
			errorTwoYear: number = 0;
			alarmTwoYear: number = 0;

			upperMonthAverageError: number = 0;
			upperMonthAverageAlarm: number = 0;

            constructor(data: TimeOfEmploymentModel, laborSystemAtr: number, employmentCD: string) {
                let self = this;
                self.laborSystemAtr = laborSystemAtr;
				self.employmentCD = employmentCD;

                if (!data) return;

				self.overMaxTimes = +data.overMaxTimes()||0;

				self.limitOneMonth = +data.limitOneMonth() || 0;
                self.alarmOneMonth = +data.alarmOneMonth() || 0;
                self.errorOneMonth = +data.errorOneMonth() || 0;

				self.limitTwoMonths = +data.limitTwoMonths() || 0;
                self.alarmTwoMonths = +data.alarmTwoMonths() || 0;
                self.errorTwoMonths = +data.errorTwoMonths() || 0;

                self.alarmOneYear = +data.alarmOneYear() || 0;
                self.errorOneYear = +data.errorOneYear() || 0;

				self.limitOneYear = +data.limitOneYear() || 0;
				self.errorTwoYear = +data.errorTwoYear() || 0;
				self.alarmTwoYear = +data.alarmTwoYear() || 0;

				self.upperMonthAverageError = +data.errorMonthAverage() || 0;
				self.upperMonthAverageAlarm = +data.alarmMonthAverage() || 0;
            }
        }

        export class DeleteTimeOfEmploymentModel {
            laborSystemAtr: number = 0;
			employmentCD: string;
            constructor(laborSystemAtr: number, employmentCD: string) {
                let self = this;
                self.laborSystemAtr = laborSystemAtr;
                self.employmentCD = employmentCD;
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
