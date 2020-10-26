module nts.uk.at.view.kmk008.f {
    import getText = nts.uk.resource.getText;
    import alertError = nts.uk.ui.dialog.alertError;

    
    export module viewmodel {
        export class ScreenModel {
            timeOfClassification: KnockoutObservable<TimeOfClassificationModel>;
            isUpdate: boolean;
            laborSystemAtr: number = 0;
            currentClassificationName: KnockoutObservable<string>;
            textOvertimeName: KnockoutObservable<string>;

            maxRows: number;
            listComponentOption: any;
            selectedCode: KnockoutObservable<string>;
            isShowAlreadySet: KnockoutObservable<boolean>;
            alreadySettingList: KnockoutObservableArray<UnitAlreadySettingModel>;
            isDialog: KnockoutObservable<boolean>;
            isShowNoSelectRow: KnockoutObservable<boolean>;
            isMultiSelect: KnockoutObservable<boolean>;
            classificationList: KnockoutObservableArray<UnitModel>;
            isRemove: KnockoutObservable<boolean>;
            
            // nameErrorWeek: KnockoutObservable<string> = ko.observable(getText("KMK008_22") + getText("KMK008_42"));
            // nameAlarmWeek: KnockoutObservable<string> = ko.observable(getText("KMK008_22") + getText("KMK008_43"));
            // nameLimitWeek: KnockoutObservable<string> = ko.observable(getText("KMK008_22") + getText("KMK008_44"));
            // nameErrorTwoWeeks: KnockoutObservable<string> = ko.observable(getText("KMK008_23") + getText("KMK008_42"));
            // nameAlarmTwoWeeks: KnockoutObservable<string> = ko.observable(getText("KMK008_23") + getText("KMK008_43"));
            // nameLimitTwoWeeks: KnockoutObservable<string> = ko.observable(getText("KMK008_23") + getText("KMK008_44"));
            // nameErrorFourWeeks: KnockoutObservable<string> = ko.observable(getText("KMK008_24") + getText("KMK008_42"));
            // nameAlarmFourWeeks: KnockoutObservable<string> = ko.observable(getText("KMK008_24") + getText("KMK008_43"));
            // nameLimitFourWeeks: KnockoutObservable<string> = ko.observable(getText("KMK008_24") + getText("KMK008_44"));
            // nameErrorOneMonth: KnockoutObservable<string> = ko.observable(getText("KMK008_25") + getText("KMK008_42"));
            // nameAlarmOneMonth: KnockoutObservable<string> = ko.observable(getText("KMK008_25") + getText("KMK008_43"));
            // nameLimitOneMonth: KnockoutObservable<string> = ko.observable(getText("KMK008_25") + getText("KMK008_44"));
            // nameErrorTwoMonths: KnockoutObservable<string> = ko.observable(getText("KMK008_26") + getText("KMK008_42"));
            // nameAlarmTwoMonths: KnockoutObservable<string> = ko.observable(getText("KMK008_26") + getText("KMK008_43"));
            // nameLimitTwoMonths: KnockoutObservable<string> = ko.observable(getText("KMK008_26") + getText("KMK008_44"));
            // nameErrorThreeMonths: KnockoutObservable<string> = ko.observable(getText("KMK008_27") + getText("KMK008_42"));
            // nameAlarmThreeMonths: KnockoutObservable<string> = ko.observable(getText("KMK008_27") + getText("KMK008_43"));
            // nameLimitThreeMonths: KnockoutObservable<string> = ko.observable(getText("KMK008_27") + getText("KMK008_44"));
            // nameErrorOneYear: KnockoutObservable<string> = ko.observable(getText("KMK008_28") + getText("KMK008_42"));
            // nameAlarmOneYear: KnockoutObservable<string> = ko.observable(getText("KMK008_28") + getText("KMK008_43"));
            // nameLimitOneYear: KnockoutObservable<string> = ko.observable(getText("KMK008_28") + getText("KMK008_44"));
            // nameUpperMonth: KnockoutObservable<string> = ko.observable(getText("KMK008_120"));
            // nameUpperMonthAverage: KnockoutObservable<string> = ko.observable(getText("KMK008_122"));
            
            constructor(laborSystemAtr: number) {
                let self = this;
                self.laborSystemAtr = laborSystemAtr;
                self.isUpdate = true;
                self.timeOfClassification = ko.observable(new TimeOfClassificationModel(null));
                self.currentClassificationName = ko.observable("");
                self.textOvertimeName = ko.observable(getText("KMK008_12", ['#KMK008_8', '#Com_Class']));

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
                    listType: 2,
                    selectType: 1,
                    selectedCode: self.selectedCode,
                    isDialog: self.isDialog(),
                    isShowNoSelectRow: self.isShowNoSelectRow(),
                    alreadySettingList: self.alreadySettingList
                };
                self.classificationList = ko.observableArray<UnitModel>([]);
                self.selectedCode.subscribe(newValue => {
                    if (nts.uk.text.isNullOrEmpty(newValue)) return;
                    self.getDetail(newValue);
                    let empSelect = _.find(self.classificationList(), emp => {
                        return emp.code == newValue;
                    });
                    if (empSelect) {
                        self.currentClassificationName(empSelect.name);
                        self.isRemove(empSelect.isAlreadySetting);
                    }

                });
            }

            startPage(): JQueryPromise<any> {
                let self = this;
                let dfd = $.Deferred();
                nts.uk.ui.errors.clearAll();
                if (self.laborSystemAtr == 0) {
                    self.textOvertimeName(getText("KMK008_12", ['{#KMK008_8}', '{#Com_Class}']));
                } else {
                    self.textOvertimeName(getText("KMK008_12", ['{#KMK008_9}', '{#Com_Class}']));
                }
                self.selectedCode('');
                self.getalreadySettingList();
                $('#empt-list-setting-screen-f').ntsListComponent(self.listComponentOption).done(function() {
                    self.classificationList($('#empt-list-setting-screen-f').getDataList());
                    if (self.classificationList().length > 0) {
                        self.selectedCode(self.classificationList()[0].code);
                    }
                    dfd.resolve();
                });
                return dfd.promise();
            }

            getalreadySettingList() {
                let self = this;
                self.alreadySettingList([]);
                new service.Service().getList(self.laborSystemAtr).done(data => {
                    if (data.classificationCodes.length > 0) {
                        self.alreadySettingList(_.map(data.classificationCodes, item => { return new UnitAlreadySettingModel(item.toString(), true); }));
                        _.defer(() => self.classificationList($('#empt-list-setting-screen-f').getDataList()));
                    }
                })
                self.isRemove(self.isShowAlreadySet());
            }

            addUpdateDataClassification() {
                let self = this;
                
                if(self.classificationList().length == 0) return;
                
                let indexCodealreadySetting = _.findIndex(self.alreadySettingList(), item => { return item.code == self.selectedCode() });
                let timeOfClassificationNew = new UpdateInsertTimeOfClassificationModel(self.timeOfClassification(), self.laborSystemAtr, self.selectedCode());
                nts.uk.ui.block.invisible();
                if (self.selectedCode() != "") {
                    if (indexCodealreadySetting != -1) {
                        new service.Service().updateAgreementTimeOfClassification(timeOfClassificationNew).done(listError => {
                            if (listError.length > 0) {
                                self.showDialogError(listError);
                                nts.uk.ui.block.clear();
                                return;
                            }
                            nts.uk.ui.dialog.info({ messageId: "Msg_15" });
                            self.getDetail(self.selectedCode());
                            nts.uk.ui.block.clear();

                        });
                        return;
                    }
                    new service.Service().addAgreementTimeOfClassification(timeOfClassificationNew).done(listError => {
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
                }
            }

            removeDataClassification() {
                let self = this;
                nts.uk.ui.dialog.confirm(nts.uk.resource.getMessage("Msg_18", []))
                    .ifYes(() => {
                        let deleteModel = new DeleteTimeOfClassificationModel(self.laborSystemAtr, self.selectedCode());
                        new service.Service().removeAgreementTimeOfEmployment(deleteModel).done(function() {
                            self.getalreadySettingList();
                            self.getDetail(self.selectedCode());
                            self.isRemove(false);
                        });
                        nts.uk.ui.dialog.info(nts.uk.resource.getMessage("Msg_16", []));
                    });

            }

            getDetail(classificationCode: string) {
                let self = this;
                new service.Service().getDetail(self.laborSystemAtr, classificationCode).done(data => {
                    self.timeOfClassification(new TimeOfClassificationModel(data));
                }).fail(error => {

                });
            }

            setSelectCodeAfterRemove(currentSelectCode: string) {
                let self = this;
                let empLength = self.classificationList().length;
                if (empLength == 0) {
                    self.selectedCode("");
                    return;
                }
                let empSelectIndex = _.findIndex(self.classificationList(), emp => {
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
                    self.selectedCode(self.classificationList()[empSelectIndex + 1].code);
                    return;
                }

                if (empSelectIndex < empLength - 1) {
                    self.selectedCode(self.classificationList()[empSelectIndex + 1].code);
                    return;
                }
                if (empSelectIndex == empLength - 1) {
                    self.selectedCode(self.classificationList()[empSelectIndex - 1].code);
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

        export class TimeOfClassificationModel {
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

		export class UpdateInsertTimeOfClassificationModel {
            laborSystemAtr: number = 0;
			overMaxTimes: number = 0;
			classificationCode: string = "";

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

            constructor(data: TimeOfEmploymentModel, laborSystemAtr: number, workPlaceId: string) {
                let self = this;
                self.laborSystemAtr = laborSystemAtr;
				self.classificationCode = classificationCode;

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

        export class DeleteTimeOfClassificationModel {
            laborSystemAtr: number = 0;
            classificationCode: string;
            constructor(laborSystemAtr: number, classificationCode: string) {
                let self = this;
                self.laborSystemAtr = laborSystemAtr;
                self.classificationCode = classificationCode;
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
