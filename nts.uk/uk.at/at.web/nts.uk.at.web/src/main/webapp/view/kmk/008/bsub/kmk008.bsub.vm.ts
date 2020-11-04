/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kmk008.bsub {
    import getText = nts.uk.resource.getText;
    import alertError = nts.uk.ui.dialog.alertError;
	import master = nts.uk.at.view.kmk008.b;

    export module viewmodel {
        export class ScreenModel  extends ko.ViewModel{
            timeOfCompany: KnockoutObservable<CpnTimeSetting>;
            laborSystemAtr: number = 0;
            textOvertimeName: KnockoutObservable<string>;

            constructor(laborSystemAtr: number) {
                super();
                let self = this;
                self.laborSystemAtr = laborSystemAtr;
                self.timeOfCompany = ko.observable(new CpnTimeSetting(null));
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
                    self.timeOfCompany(new CpnTimeSetting(data));
                    self.initFocus();
                    dfd.resolve();
                }).fail(error => {
					alertError({ messageId: error.messageId, messageParams: error.parameterIds});
					nts.uk.ui.block.clear();
					dfd.reject();
                });

                return dfd.promise();
            }

			initFocus() {
				_.defer(()=> {
					$('#B3_14 input').focus();
				});
			}

            persisData() {
                let self = this;
                let cpnTimeSettingForPersis = new CpnTimeSettingForPersis(self.timeOfCompany(), self.laborSystemAtr);
                let validateErr = master.validateTimeSetting(cpnTimeSettingForPersis);
                if (validateErr) {
					alertError(validateErr).then(()=>{
						$("#B3_" + validateErr.errorPosition + " input").focus();
					});
                	return;
				}

                nts.uk.ui.block.invisible();
                new service.Service().addAgreementTimeOfCompany(cpnTimeSettingForPersis).done((listError) => {
                    nts.uk.ui.dialog.info({ messageId: "Msg_15" }).then(function() {
                        self.startPage();
                    });
                    nts.uk.ui.block.clear();
                }).fail((error)=>{
					alertError({ messageId: error.messageId, messageParams: error.parameterIds});
					nts.uk.ui.block.clear();
				});
            }
        }

		export class CpnTimeSetting {
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
			errorMonthAverage2: KnockoutObservable<string> = ko.observable(null);
			isSubscribe: boolean = false;
			isSubscribe2: boolean = false;

			alarmMonthAverage: KnockoutObservable<string> = ko.observable(null);

            constructor(data: any) {
                let self = this;
				if (!data) {
					data = master.INIT_DEFAULT;
				}
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
				self.errorMonthAverage2(data.errorMonthAverage);

				self.errorMonthAverage.subscribe(newValue => {
					if (self.isSubscribe) {
						self.isSubscribe = false;
						return;
					}
					self.isSubscribe2 = true;

					if ($("#B3_33").ntsError("hasError")) {
						self.errorMonthAverage2(null);
						return;
					}

					$('#B3_34').ntsError('clear');
					self.errorMonthAverage2(newValue);
				});
				self.errorMonthAverage2.subscribe(newValue => {
					if (self.isSubscribe2) {
						self.isSubscribe2 = false;
						return;
					}
					self.isSubscribe = true;

					if ($("#B3_34").ntsError("hasError")) {
						self.errorMonthAverage(null);
						return;
					}

					$('#B3_33').ntsError('clear');
					self.errorMonthAverage(newValue);
				});

				self.alarmMonthAverage(data.alarmMonthAverage);
            }
        }

        export class CpnTimeSettingForPersis {
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

            constructor(data: CpnTimeSetting, laborSystemAtr: number) {
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
