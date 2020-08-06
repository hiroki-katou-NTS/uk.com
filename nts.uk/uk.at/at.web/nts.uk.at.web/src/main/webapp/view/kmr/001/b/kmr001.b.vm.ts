/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.kmr001.b {

	// const API = {
	// 	SETTING: 'at/record/stamp/management/personal/startPage',
	// 	HIGHTLIGHT: 'at/record/stamp/management/personal/stamp/getHighlightSetting'
	// };

	const PATH = {
	    REDIRECT: '/view/ccg/008/a/index.xhtml'
    }

	@bean()
	export class Kmr001BVmViewModel extends ko.ViewModel {

        //selectedId: KnockoutObservable<number>;
        //reservationChange: KnockoutObservable<ReservationChange> = ko.observable();
        enable: KnockoutObservable<boolean>;

        // timePeriod:  KnockoutObservable<TimePeriod> = ko.observable();
        itemsReservationChange: KnockoutObservableArray<ReservationChange> = ko.observableArray([]);
        itemsTimePeriod: KnockoutObservableArray<TimePeriod> = ko.observableArray([]);

        //B19_3
        //name1Textbox: KnockoutObservable<string> = ko.observable("");
        selectedCode: KnockoutObservable<string>;
        isEnable: KnockoutObservable<boolean>;
        isEditable: KnockoutObservable<boolean>;
        model : Reservation = new Reservation;

        constructor() {
        	super();
            var vm = this;

            // vm.itemList2 ([
            //     {code: "1", name: "基本給"},
            //     {code: "2", name: "役職手当"},
            //     {code: "3", name: "基本給ながい文字列ながい文字列ながい文字列"}
            // ]);

            vm.selectedCode = ko.observable('1');
            vm.isEnable = ko.observable(true);
            vm.isEditable = ko.observable(true);

            //combo box B10_2
            vm.itemsReservationChange([
                {appId: 1, appName: 'option 1'},
                {appId: 2, appName: 'option 2'},
                {appId: 3, appName: 'option 3'}
            ]);

            //combo box B10_3
            vm.itemsTimePeriod([
                {appId: 1, appValue: 1},
                {appId: 2, appValue: 2},
                {appId: 3, appValue: 3}
            ]);

            vm.enable = ko.observable(true);
        }

		created() {
			const vm = this;

			_.extend(window, { vm });
		}

	}


    interface ReservationChange{
        appId: number;
        appName: string;
    }

    interface TimePeriod{
        appId: number;
        appValue: number;
    }

    // class for kmr001 b
    class Reservation{
        operationClassification: KnockoutObservable<number> = ko.observable(-1);
        reservationChange: KnockoutObservable<ReservationChange> = ko.observable();
        timePeriod:  KnockoutObservable<TimePeriod> = ko.observable();
        name1Textbox: KnockoutObservable<string> = ko.observable("");
        selectedId: KnockoutObservable<number> = ko.observable(-1);
        constructor(){

        }
    }

}
