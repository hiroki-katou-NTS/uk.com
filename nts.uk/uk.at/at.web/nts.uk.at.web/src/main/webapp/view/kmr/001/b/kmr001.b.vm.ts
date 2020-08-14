/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.kmr001.b {

	const API = {
        GET_BENTO_RESERVATION: 'screen/at/record/reservation/bento_menu/getBentoMenu'

    };

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
        model : Reservation;
        // model : Reservation = new Reservation(ko.observable(-1),ko.observable(),ko.observable(),
        //     ko.observable(""),ko.observable(-1));

        constructor() {
        	super();
            const vm = this;
            // vm.itemList2 ([
            //     {code: "1", name: "基本給"},
            //     {code: "2", name: "役職手当"},
            //     {code: "3", name: "基本給ながい文字列ながい文字列ながい文字列"}
            // ]);

            vm.selectedCode = ko.observable('0');
            vm.isEnable = ko.observable(true);
            vm.isEditable = ko.observable(true);

            //combo box B10_2
            vm.itemsReservationChange([
                {appId: 0, appName: '常に修正可能'},
                {appId: 1, appName: '注文からの期間'},
                {appId: 2, appName: '受付時間内のみ修正可能'}
            ]);

            //combo box B10_3
            _.range(0, 32).map(item => vm.itemsTimePeriod.push({appId: item, appValue: (item+1).toString()}));
            vm.enable = ko.observable(true);
        }

		created() {
			const vm = this;
            vm.$ajax(API.GET_BENTO_RESERVATION).done((data) => {
                const vm = this;
                console.log(data)
                // let reservationChange: ReservationChange = null;
                // reservationChange.appId = data.reservationEndTime1;
                // reservationChange.appName = data.reservationFrameName1;
                // vm.model = new BentoReservation(
                //     ko.observable(data.operationDistinction),
                //     ko.observable(data.referenceTime),
                //     ko.observable(data.changeDeadlineContents),
                //     ko.observable(data.changeDeadlineDays),
                //     ko.observable(data.orderDeadline),
                //     ko.observable(data.monthlyResults),
                //     ko.observable(data.dailyResults),
                //     ko.observable(data.orderedData),
                //     ko.observable(data.reservationFrameName1),
                //     ko.observable(data.reservationStartTime1),
                //     ko.observable(data.reservationEndTime1),
                //     ko.observable(data.reservationFrameName2),
                //     ko.observable(data.reservationStartTime2),
                //     ko.observable(data.reservationEndTime2)
                // );
            });
			 _.extend(window, { vm });
		}

	}


    interface ReservationChange{
        appId: number;
        appName: string;
    }

    interface TimePeriod{
        appId: number;
        appValue: string;
    }

    // class for kmr001 b
    interface Reservation{
	    //予約の運用区分
        operationDistinction: KnockoutObservable<number>;
        //
        referenceTime: KnockoutObservable<number>;
        contentChangeDeadline: KnockoutObservable<ReservationChange>;
        timePeriod:  KnockoutObservable<TimePeriod>;
        name1Textbox: KnockoutObservable<string>;
        selectedId: KnockoutObservable<number>;
        // constructor(operationDistinction: KnockoutObservable<number>, reservationChange: KnockoutObservable<ReservationChange>,
        //             timePeriod:  KnockoutObservable<TimePeriod>, name1Textbox: KnockoutObservable<string>, selectedId: KnockoutObservable<number>){
        //     this.operationDistinction = operationDistinction;
        //     this.reservationChange = reservationChange;
        //     this.timePeriod = timePeriod;
        //     this.name1Textbox = name1Textbox;
        //     this.selectedId = selectedId;
        // }
    }

    class BentoReservation{

	    //予約の運用区別
        operationClassification: KnockoutObservable<number>;

        //基準時間
        referenceTime: KnockoutObservable<number> = ko.observable(-1);

        //予約済みの内容変更期限内容
        changeDeadlineContents: KnockoutObservable<number> = ko.observable(-1);

        //予約済みの内容変更期限日数
        changeDeadlineDays: KnockoutObservable<number> = ko.observable(-1);

        //注文済み期限方法
        orderDeadline: KnockoutObservable<number> = ko.observable(-1);

        //月別実績の集計
        monthlyResults: KnockoutObservable<number> = ko.observable(-1);

        //日別実績の集計
        dailyResults: KnockoutObservable<number> = ko.observable(-1);

        //注文済みデータ
        orderedData: KnockoutObservable<number> = ko.observable(-1);

        reservationFrameName1: KnockoutObservable<string> = ko.observable("");

        reservationStartTime1: KnockoutObservable<number> = ko.observable(-1);

        reservationEndTime1: KnockoutObservable<number> = ko.observable(-1);

        reservationFrameName2: KnockoutObservable<string> = ko.observable("");

        reservationStartTime2: KnockoutObservable<number> = ko.observable(-1);

        reservationEndTime2: KnockoutObservable<number> = ko.observable(-1);

        constructor(operationClassification: KnockoutObservable<number>,referenceTime: KnockoutObservable<number>,
                    changeDeadlineContents: KnockoutObservable<number>,changeDeadlineDays: KnockoutObservable<number>,
                    orderDeadline: KnockoutObservable<number>,monthlyResults: KnockoutObservable<number>,
                    dailyResults: KnockoutObservable<number>,orderedData: KnockoutObservable<number>,
                    reservationFrameName1: KnockoutObservable<string>,reservationStartTime1: KnockoutObservable<number>,
                    reservationEndTime1: KnockoutObservable<number>,reservationFrameName2: KnockoutObservable<string>,
                    reservationStartTime2: KnockoutObservable<number>,reservationEndTime2: KnockoutObservable<number>,){
            this.operationClassification = operationClassification;
            this.referenceTime = referenceTime;
            this.changeDeadlineContents = changeDeadlineContents;
            this.changeDeadlineDays = changeDeadlineDays;
            this.orderDeadline = orderDeadline;
            this.monthlyResults = monthlyResults;
            this.dailyResults = dailyResults;
            this.orderedData = orderedData;
            this.reservationFrameName1 = reservationFrameName1;
            this.reservationStartTime1 = reservationStartTime1;
            this.reservationEndTime1 = reservationEndTime1;
            this.reservationFrameName2 = reservationFrameName2;
            this.reservationStartTime2 = reservationStartTime2;
            this.reservationEndTime2 = reservationEndTime2;

        }
    }

    export class classmap{
        contentChangeDeadline: number;
        contentChangeDeadlineDay: number;
        dailyResults: number;
        monthlyResults: number;
        operationDistinction: number;
        orderDeadline: number;
        orderedData: number;
        referenceTime: number;
        reservationEndTime1: number;
        reservationEndTime2:number;
        reservationFrameName1: string;
        reservationFrameName2: string;
        reservationStartTime1: number;
        reservationStartTime2: number;

        constructor(contentChangeDeadline: number, contentChangeDeadlineDay: number, dailyResults: number,
                    monthlyResults: number,operationDistinction: number,orderDeadline: number,orderedData: number,
                    referenceTime: number,reservationEndTime1: number,reservationEndTime2:number, reservationFrameName1: string,
                    reservationFrameName2: string,reservationStartTime1: number,reservationStartTime2: number) {
            this.contentChangeDeadline = contentChangeDeadline;
            this.contentChangeDeadlineDay = contentChangeDeadlineDay;
            this.dailyResults = dailyResults;
            this.monthlyResults = monthlyResults;
            this.operationDistinction = operationDistinction;
            this.orderDeadline = orderDeadline;
            this.orderedData = orderedData;
            this.referenceTime = referenceTime;
            this.reservationEndTime1 = reservationEndTime1;
            this.reservationEndTime2 = reservationEndTime2;
            this.reservationFrameName1 = reservationFrameName1;
            this.reservationFrameName2 = reservationFrameName2;
            this.reservationStartTime1 = reservationStartTime1;
            this.reservationStartTime2 = reservationStartTime2;

        }
    }

}
