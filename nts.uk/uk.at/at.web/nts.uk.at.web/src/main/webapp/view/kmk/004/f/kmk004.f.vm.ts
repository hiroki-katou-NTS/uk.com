/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kmk004.f {

    @bean()
    export class ViewModel extends ko.ViewModel {

        public timeDay: KnockoutObservable<string> = ko.observable('0:00');
        public timeWeek: KnockoutObservable<string> = ko.observable('0:00');
        public constraint: KnockoutObservable<string> = ko.observable('OneDayTime');
        public swInsurrance: ISwitch[] = [{ code: '0', name: this.$i18n('KMK004_244')},
        { code: '1', name: this.$i18n('KMK004_245') }];
        public valueInsurrance: KnockoutObservable<string> = ko.observable('0');
        public legalOvertimeInsurrance: KnockoutObservable<boolean> = ko.observable(true);
        public nonStatutoryLeaveInsurrance: KnockoutObservable<boolean> = ko.observable(true);
        public surcharges: ISwitch[] = [{ code: '0', name: this.$i18n('KMK004_250')},
        { code: '1', name: this.$i18n('KMK004_251') }];
        public valueSurcharges: KnockoutObservable<string> = ko.observable('0');
        public legalOvertimeSurcharges: KnockoutObservable<boolean> = ko.observable(true);
        public nonStatutoryLeaveSurcharges: KnockoutObservable<boolean> = ko.observable(true);

        create() {
            const vm = this;

        }

        mounted() {

        }

        init() {
            const vm = this;

        }
    }
}

interface ISwitch {
    code: string;
    name: string;
}