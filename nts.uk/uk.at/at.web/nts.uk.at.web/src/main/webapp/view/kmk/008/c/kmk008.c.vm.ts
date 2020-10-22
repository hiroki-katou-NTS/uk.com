/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kmk008.c {
    import getText = nts.uk.resource.getText;
    import alertError = nts.uk.ui.dialog.alertError;

    export module viewmodel {
		//@bean()
        export class ScreenModel {
            timeOfCompany: KnockoutObservable<TimeOfCompanyModel>;
            isUpdate: boolean;
            laborSystemAtr: number = 0;
            textOvertimeName: KnockoutObservable<string>;
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

			selectedLimit: KnockoutObservable<number> = ko.observable(4); // Default 4 times
            
            constructor(laborSystemAtr: number) {
                let self = this;
                self.laborSystemAtr = laborSystemAtr;
                self.isUpdate = true;
                self.timeOfCompany = ko.observable(new TimeOfCompanyModel(null));
                self.textOvertimeName = ko.observable(getText("KMK008_12", ['#KMK008_8', '#Com_Company']));
            }

            startPage(): JQueryPromise<any> {
                let self = this;
                let dfd = $.Deferred();
                
                nts.uk.ui.errors.clearAll();
                if (self.laborSystemAtr == 0) {
                    self.textOvertimeName(getText("KMK008_12", ['{#KMK008_8}', '{#Com_Company}']));
                } else {
                    self.textOvertimeName(getText("KMK008_12", ['{#KMK008_9}', '{#Com_Company}']));
                }
                new service.Service().getAgreementTimeOfCompany(self.laborSystemAtr).done(data => {
                    self.timeOfCompany(new TimeOfCompanyModel(data));
                    if (data.updateMode) {
                        self.isUpdate = true;
                    } else {
                        self.isUpdate = false;
                    }
                    $("#errorCheckInput").focus();
                    dfd.resolve();
                }).fail(error => {

                });
                return dfd.promise();
            }

            addUpdateData() {
                let self = this;
                let timeOfCompanyNew = new UpdateInsertTimeOfCompanyModel(self.timeOfCompany(), self.laborSystemAtr);
                nts.uk.ui.block.invisible();
                if (self.isUpdate) {
                    new service.Service().updateAgreementTimeOfCompany(timeOfCompanyNew).done(function(listError) {
                        if (listError.length > 0) {
                            self.showDialogError(listError);
                            nts.uk.ui.block.clear();
                            return;
                        }
                        nts.uk.ui.dialog.info({ messageId: "Msg_15" }).then(function(data) {
                            self.startPage();
                        });
                    });
                    nts.uk.ui.block.clear();
                    return;
                }
                new service.Service().addAgreementTimeOfCompany(timeOfCompanyNew).done(function(listError) {
                    if (listError.length > 0) {
                        self.showDialogError(listError);
                        nts.uk.ui.block.clear();
                        return;
                    }
                    nts.uk.ui.dialog.info({ messageId: "Msg_15" }).then(function(data) {
                        self.startPage();
                    });
                    nts.uk.ui.block.clear();
                });
                nts.uk.ui.block.clear();
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

        //old: response from at/record/agreementTimeOfCompany/getAgreementTimeOfCompany/{0}
		// new : screen/at/kmk008/b/get
		// {
		// 	"overMaxTimes": 4,
		// 	"limitOneMonth": 4800,
		// 	"errorOneMonth": 1200,
		// 	"alarmOneMonth": 600,
		// 	"limitTwoMonths": 6000,
		// 	"errorTwoMonths": 0,
		// 	"alarmTwoMonths": 0,
		// 	"errorOneYear": 43200,
		// 	"alarmOneYear": 21600,
		// 	"limitOneYear": 0,
		// 	"errorTwoYear": 0,
		// 	"alarmTwoYear": 0,
		// 	"errorMonthAverage": 4800,
		// 	"alarmMonthAverage": 0
		// }
		export class TimeOfCompanyModel {
			overMaxTimes: KnockoutObservable<string> = ko.observable(null);// add
			// alarmWeek: KnockoutObservable<string> = ko.observable(null);
			// errorWeek: KnockoutObservable<string> = ko.observable(null);
			// limitWeek: KnockoutObservable<string> = ko.observable(null);
			// alarmTwoWeeks: KnockoutObservable<string> = ko.observable(null);
			// errorTwoWeeks: KnockoutObservable<string> = ko.observable(null);
			// limitTwoWeeks: KnockoutObservable<string> = ko.observable(null);
			// alarmFourWeeks: KnockoutObservable<string> = ko.observable(null);
			// errorFourWeeks: KnockoutObservable<string> = ko.observable(null);
			// limitFourWeeks: KnockoutObservable<string> = ko.observable(null);
			// alarmOneMonth: KnockoutObservable<string> = ko.observable(null);
			alarmOneMonth: KnockoutObservable<number> = ko.observable(NaN);
			errorOneMonth: KnockoutObservable<string> = ko.observable(null);
			limitOneMonth: KnockoutObservable<string> = ko.observable(null);
			alarmTwoMonths: KnockoutObservable<string> = ko.observable(null);
			errorTwoMonths: KnockoutObservable<string> = ko.observable(null);
			limitTwoMonths: KnockoutObservable<string> = ko.observable(null);
			// alarmThreeMonths: KnockoutObservable<string> = ko.observable(null);
			// errorThreeMonths: KnockoutObservable<string> = ko.observable(null);
			// limitThreeMonths: KnockoutObservable<string> = ko.observable(null);
			alarmOneYear: KnockoutObservable<string> = ko.observable(null);
			errorOneYear: KnockoutObservable<string> = ko.observable(null);
			limitOneYear: KnockoutObservable<string> = ko.observable(null);
			// upperMonth: KnockoutObservable<string> = ko.observable(null);
			// upperMonthAverage: KnockoutObservable<string> = ko.observable(null);
			errorTwoYear: KnockoutObservable<string> = ko.observable(null);// add
			alarmTwoYear: KnockoutObservable<string> = ko.observable(null);// add
			errorMonthAverage: KnockoutObservable<string> = ko.observable(null);// add
			alarmMonthAverage: KnockoutObservable<string> = ko.observable(null);// add
            
            constructor(data: any) {
                let self = this;
                if (!data) return;
				self.overMaxTimes(data.overMaxTimes);
                // self.alarmWeek(data.alarmWeek);
                // self.errorWeek(data.errorWeek);
                // self.limitWeek(data.limitWeek);
                // self.alarmTwoWeeks(data.alarmTwoWeeks);
                // self.errorTwoWeeks(data.errorTwoWeeks);
                // self.limitTwoWeeks(data.limitTwoWeeks);
                // self.alarmFourWeeks(data.alarmFourWeeks);
                // self.errorFourWeeks(data.errorFourWeeks);
                // self.limitFourWeeks(data.limitFourWeeks);
                self.alarmOneMonth(data.alarmOneMonth);
                self.errorOneMonth(data.errorOneMonth);
                self.limitOneMonth(data.limitOneMonth);
                self.alarmTwoMonths(data.alarmTwoMonths);
                self.errorTwoMonths(data.errorTwoMonths);
                self.limitTwoMonths(data.limitTwoMonths);
                // self.alarmThreeMonths(data.alarmThreeMonths);
                // self.errorThreeMonths(data.errorThreeMonths);
                // self.limitThreeMonths(data.limitThreeMonths);
                self.alarmOneYear(data.alarmOneYear);
                self.errorOneYear(data.errorOneYear);
                self.limitOneYear(data.limitOneYear);
                // self.upperMonth(data.upperMonth);
                // self.upperMonthAverage(data.upperMonthAverage);
				self.errorTwoYear(data.errorTwoYear);
				self.alarmTwoYear(data.alarmTwoYear);
				self.errorMonthAverage(data.errorMonthAverage);
				self.alarmMonthAverage(data.alarmMonthAverage);
            }
        }

        export class UpdateInsertTimeOfCompanyModel {
            laborSystemAtr: number = 0;
			overMaxTimes: number = 0; // add
            // alarmWeek: number = 0;
            // errorWeek: number = 0;
            // limitWeek: number = 0;
            // alarmTwoWeeks: number = 0;
            // errorTwoWeeks: number = 0;
            // limitTwoWeeks: number = 0;
            // alarmFourWeeks: number = 0;
            // errorFourWeeks: number = 0;
            // limitFourWeeks: number = 0;
            alarmOneMonth: number = 0;
            errorOneMonth: number = 0;
            limitOneMonth: number = 0;
            alarmTwoMonths: number = 0;
            errorTwoMonths: number = 0;
            limitTwoMonths: number = 0;
            // alarmThreeMonths: number = 0;
            // errorThreeMonths: number = 0;
            // limitThreeMonths: number = 0;
            alarmOneYear: number = 0;
            errorOneYear: number = 0;
            limitOneYear: number = 0;
            // upperMonth: number = 0;
            // upperMonthAverage: number = 0;
			errorTwoYear: number = 0;
			alarmTwoYear: number = 0;
			errorMonthAverage: number = 0;
			alarmMonthAverage: number = 0;

            constructor(data: TimeOfCompanyModel, laborSystemAtr: number) {
                let self = this;
                self.laborSystemAtr = laborSystemAtr;
                if (!data) return;
				self.overMaxTimes = +data.overMaxTimes()||0; // add
                // self.alarmWeek = +data.alarmWeek() || 0;
                // self.errorWeek = +data.errorWeek() || 0;
                // self.limitWeek = +data.limitWeek() || 0;
                // self.alarmTwoWeeks = +data.alarmTwoWeeks() || 0;
                // self.errorTwoWeeks = +data.errorTwoWeeks() || 0;
                // self.limitTwoWeeks = +data.limitTwoWeeks() || 0;
                // self.alarmFourWeeks = +data.alarmFourWeeks() || 0;
                // self.errorFourWeeks = +data.errorFourWeeks() || 0;
                // self.limitFourWeeks = +data.limitFourWeeks() || 0;
                self.alarmOneMonth = +data.alarmOneMonth() || 0;
                self.errorOneMonth = +data.errorOneMonth() || 0;
                self.limitOneMonth = +data.limitOneMonth() || 0;
                self.alarmTwoMonths = +data.alarmTwoMonths() || 0;
                self.errorTwoMonths = +data.errorTwoMonths() || 0;
                self.limitTwoMonths = +data.limitTwoMonths() || 0;
                // self.alarmThreeMonths = +data.alarmThreeMonths() || 0;
                // self.errorThreeMonths = +data.errorThreeMonths() || 0;
                // self.limitThreeMonths = +data.limitThreeMonths() || 0;
                self.alarmOneYear = +data.alarmOneYear() || 0;
                self.errorOneYear = +data.errorOneYear() || 0;
                self.limitOneYear = +data.limitOneYear() || 0;
                // self.upperMonth = +data.upperMonth() || 0;
                // self.upperMonthAverage = +data.upperMonthAverage() || 0;
				self.errorTwoYear = +data.errorTwoYear() || 0;
				self.alarmTwoYear = +data.alarmTwoYear() || 0;
				self.errorMonthAverage = +data.errorMonthAverage() || 0;
				self.alarmMonthAverage = +data.alarmMonthAverage() || 0;
            }
        }
    }
}
