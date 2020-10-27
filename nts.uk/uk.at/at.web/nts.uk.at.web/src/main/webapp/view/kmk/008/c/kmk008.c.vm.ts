/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kmk008.c {
    import getText = nts.uk.resource.getText;
    import alertError = nts.uk.ui.dialog.alertError;

    export module viewmodel {
		//@bean()
        export class ScreenModel {
            timeOfCompany: KnockoutObservable<TimeOfCompanyModel>;
            laborSystemAtr: number = 0;
            textOvertimeName: KnockoutObservable<string>;
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

            limitOptions: any;

			selectedLimit: KnockoutObservable<number> = ko.observable(4); // Default 4 times
            
            constructor(laborSystemAtr: number) {
                let self = this;
                self.laborSystemAtr = laborSystemAtr;
                self.timeOfCompany = ko.observable(new TimeOfCompanyModel(null));
                self.textOvertimeName = ko.observable(getText("KMK008_12", ['#KMK008_8', '#Com_Company']));
				self.limitOptions = [
					{code: 0 ,name : getText('KMK008_190')},
					{code: 1 ,name : getText('KMK008_191')},
					{code: 2 ,name : getText('KMK008_192')},
					{code: 3 ,name : getText('KMK008_193')},
					{code: 4 ,name : getText('KMK008_194')},
					{code: 5 ,name : getText('KMK008_195')},
					{code: 6,name : getText('KMK008_196')},
					{code: 7,name : getText('KMK008_197')},
					{code: 8,name : getText('KMK008_198')},
					{code: 9,name : getText('KMK008_199')},
					{code: 10, name : getText('KMK008_200')},
					{code: 11, name : getText('KMK008_201')},
					{code: 12, name : getText('KMK008_202')}
				];
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

				self.initSubscribers();

                new service.Service().getAgreementTimeOfCompany(self.laborSystemAtr).done(data => {
                    self.timeOfCompany(new TimeOfCompanyModel(data));
                    $("#errorCheckInput").focus();
                    dfd.resolve();
                }).fail(error => {

                });
                return dfd.promise();
            }

			initSubscribers() {
			}

            addUpdateData() {
                let self = this;
                let timeOfCompanyNew = new UpdateInsertTimeOfCompanyModel(self.timeOfCompany(), self.laborSystemAtr);
                nts.uk.ui.block.invisible();
                new service.Service().addAgreementTimeOfCompany(timeOfCompanyNew).done((listError) => {
                    nts.uk.ui.dialog.info({ messageId: "Msg_15" }).then(function() {
                        self.startPage();
                    });
                    nts.uk.ui.block.clear();
                }).fail((error)=>{
					error.parameterIds.unshift("Q&A 34201");
					alertError({ messageId: error.messageId, messageParams: error.parameterIds});
					nts.uk.ui.block.clear();
				});
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

		export class TimeOfCompanyModel {
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

        export class UpdateInsertTimeOfCompanyModel {
            laborSystemAtr: number = 0;
			overMaxTimes: number = 0;

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

            constructor(data: TimeOfCompanyModel, laborSystemAtr: number) {
                let self = this;
                self.laborSystemAtr = laborSystemAtr;
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
    }
}
