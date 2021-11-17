/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.kmr001.b {

    @bean()
    class ViewModel extends ko.ViewModel {
        reservationFrameName1: KnockoutObservable<string> = ko.observable("");
        reservationStartTime1: KnockoutObservable<number> = ko.observable();
        reservationEndTime1: KnockoutObservable<number> = ko.observable();
        name2CheckBox: KnockoutObservable<boolean> = ko.observable(false);
        reserveContentChangeList: any[] = [
            { 'id': ContentChangeDeadline.ALLWAY_FIXABLE, 'name': this.$i18n("KMR001_97") },
            { 'id': ContentChangeDeadline.MODIFIED_DURING_RECEPTION_HOUR, 'name': this.$i18n("KMR001_98") },
            { 'id': ContentChangeDeadline.MODIFIED_FROM_ORDER_DATE, 'name': this.$i18n("KMR001_99") },
        ];
        selectedContent: KnockoutObservable<number> = ko.observable(ContentChangeDeadline.ALLWAY_FIXABLE);

        constructor() {
            super();
            const vm = this;

        }

        mounted() {
            const vm = this;
        }

        registerBentoReserveSetting() {

        }
    }

    const API = {

    }

    enum ContentChangeDeadline {
        // 常に修正可能
        ALLWAY_FIXABLE = 0,
        // 受付時間内は修正可能
        MODIFIED_DURING_RECEPTION_HOUR = 1, 
        // 注文日からの○日間修正可能
        MODIFIED_FROM_ORDER_DATE = 2
    }
}