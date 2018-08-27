module nts.uk.at.view.kmk008.c {
    export module viewmodel {
        export class ScreenModel {
            timeOfCompany: KnockoutObservable<TimeOfCompanyModel>;
            isUpdate: boolean;
            laborSystemAtr: number = 0;
            textOvertimeName: KnockoutObservable<string>;
            constructor(laborSystemAtr: number) {
                let self = this;
                self.laborSystemAtr = laborSystemAtr;
                self.isUpdate = true;
                self.timeOfCompany = ko.observable(new TimeOfCompanyModel(null));
                self.textOvertimeName = ko.observable(nts.uk.resource.getText("KMK008_12", ['#KMK008_8', '#Com_Company']));
            }

            startPage(): JQueryPromise<any> {
                let self = this;
                let dfd = $.Deferred();
                nts.uk.ui.errors.clearAll();
                if (self.laborSystemAtr == 0) {
                    self.textOvertimeName(nts.uk.resource.getText("KMK008_12", ['{#KMK008_8}', '{#Com_Company}']));
                } else {
                    self.textOvertimeName(nts.uk.resource.getText("KMK008_12", ['{#KMK008_9}', '{#Com_Company}']));
                }
                new service.Service().getAgreementTimeOfCompany(self.laborSystemAtr).done(data => {
                    if (data) {
                        self.timeOfCompany(new TimeOfCompanyModel(data));
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
                            let errorCode = _.split(listError[0], ',');
                            let periodName = nts.uk.resource.getText(errorCode[1]);
                            let param1 = "期間: " + nts.uk.resource.getText(errorCode[1]) + "<br>" + nts.uk.resource.getText(errorCode[2]);
                            nts.uk.ui.dialog.alertError({ messageId: errorCode[0], messageParams: [param1, nts.uk.resource.getText(errorCode[3])] });
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
                        let errorCode = _.split(listError[0], ',');
                        let periodName = nts.uk.resource.getText(errorCode[1]);
                        let param1 = "期間: " + nts.uk.resource.getText(errorCode[1]) + "<br>" + nts.uk.resource.getText(errorCode[2]);
                        nts.uk.ui.dialog.alertError({ messageId: errorCode[0], messageParams: [param1, nts.uk.resource.getText(errorCode[3])] });
                        return;
                    }
                    nts.uk.ui.dialog.info({ messageId: "Msg_15" }).then(function(data) {
                        self.startPage();
                    });
                     nts.uk.ui.block.clear();
                });
                nts.uk.ui.block.clear();
            }

        }

        export class TimeOfCompanyModel {
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

        export class UpdateInsertTimeOfCompanyModel {
            laborSystemAtr: number = 0;
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
            constructor(data: TimeOfCompanyModel, laborSystemAtr: number) {
                let self = this;
                self.laborSystemAtr = laborSystemAtr;
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
            }
        }
    }
}
