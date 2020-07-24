/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.kmr001.b {

    import getText = nts.uk.resource.getText;

	const API = {
		SETTING: 'at/record/stamp/management/personal/startPage',
		HIGHTLIGHT: 'at/record/stamp/management/personal/stamp/getHighlightSetting'
	};

	@bean()
	export class Kmr001BVmViewModel extends ko.ViewModel {
        itemList: KnockoutObservableArray<BoxModel>;
        selectedId: KnockoutObservable<number>;
        enable: KnockoutObservable<boolean>;
        reservationChange: KnockoutObservable<ReservationChange> = ko.observable();
        timePeriod:  KnockoutObservable<TimePeriod> = ko.observable();
        itemsReservationChange: KnockoutObservableArray<ReservationChange>;
        itemsTimePeriod: KnockoutObservableArray<TimePeriod> = ko.observableArray([]);
        orderedDataList: KnockoutObservableArray<BoxModel>;
        orderDeadline: KnockoutObservableArray<BoxModel>;
        monthlyPerformanceCalList: KnockoutObservableArray<BoxModel>;
        dailyActualCalList: KnockoutObservableArray<BoxModel>;

        //B19_3
        name1Textbox: KnockoutObservable<string> = ko.observable("");

        itemList2: KnockoutObservableArray<ItemModel>;
        selectedCode: KnockoutObservable<string>;
        isEnable: KnockoutObservable<boolean>;
        isEditable: KnockoutObservable<boolean>;

        constructor() {
        	super();
            var vm = this;
            //radio button B3_2
            vm.itemList = ko.observableArray([
                new BoxModel(1, getText('KMR002_6')),
                new BoxModel(2, getText('KMR002_7')),
            ]);

            vm.itemList2 = ko.observableArray([
                new ItemModel('1', '基本給'),
                new ItemModel('2', '役職手当'),
                new ItemModel('3', '基本給ながい文字列ながい文字列ながい文字列')
            ]);

            vm.selectedCode = ko.observable('1');
            vm.isEnable = ko.observable(true);
            vm.isEditable = ko.observable(true);

            //combo box B10_2
            vm.itemsReservationChange = ko.observableArray([
                new ReservationChange(1, 'option 1'),
                new ReservationChange(2, 'option 2'),
                new ReservationChange(3, 'option 3')
            ]);

            //combo box B10_3
            vm.itemsTimePeriod = ko.observableArray([
                new TimePeriod(1, 1),
                new TimePeriod(2, 2),
                new TimePeriod(3, 3)
            ]);

            //radio button B20_2
            vm.orderedDataList = ko.observableArray([
                new BoxModel(1, getText('KMR002_78')),
                new BoxModel(2, getText('KMR002_79')),
            ]);

            //radio button B14_2
            vm.orderDeadline = ko.observableArray([
                new BoxModel(1, getText('KMR002_28')),
                new BoxModel(2, getText('KMR002_29')),
            ]);

            //radio button B17_2
            vm.monthlyPerformanceCalList = ko.observableArray([
                new BoxModel(1, getText('KMR002_33')),
                new BoxModel(2, getText('KMR002_34')),
            ]);

            //radio button B18_2
            vm.dailyActualCalList = ko.observableArray([
                new BoxModel(1, getText('KMR002_33')),
                new BoxModel(2, getText('KMR002_34')),
            ]);

            vm.selectedId = ko.observable(1);
            vm.enable = ko.observable(true);
        }

		created() {
			const vm = this;
			vm.$blockui('show')
				.then(() => vm.$ajax('at', API.SETTING))
				.fail((res) => {
					vm.$dialog.error({ messageId: res.messageId })
						.then(() => vm.$jump("com", "/view/ccg/008/a/index.xhtml"));
				})
				.always(() => vm.$blockui('clear'));

			_.extend(window, { vm });
		}

	}

    class BoxModel {
        id: number;
        name: string;
        constructor(id, name){
            var self = this;
            self.id = id;
            self.name = name;
        }
    }

    class ReservationChange{
        appId: number;
        appName: string;
        constructor(appId: number, appName: string){
            this.appId = appId;
            this.appName = appName;
        }
    }

    class TimePeriod{
        appId: number;
        appValue: number;
        constructor(appId: number, appValue: number){
            this.appId = appId;
            this.appValue = appValue;
        }
    }

    class ItemModel {
        code: string;
        name: string;

        constructor(code: string, name: string) {
            this.code = code;
            this.name = name;
        }
    }
}
