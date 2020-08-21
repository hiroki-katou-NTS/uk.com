/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.kmr001.c {
    import parseTime = nts.uk.time.parseTime;

    const API = {
        GET_LIST_WORK_LOCATION: 'screen/at/record/reservation/bento_menu/getWorkLocation',
        GET_ALL : 'screen/at/record/reservation/bento_menu/getBentoMenuByHist'
    };

    const PATH = {
        REDIRECT: '/view/ccg/008/a/index.xhtml',
        KMR001_D: '/view/kmr/001/d/index.xhtml'
    };

    @bean()
    export class ViewModel extends ko.ViewModel {
        //history
        start: KnockoutObservable<string> = ko.observable('');
        end: KnockoutObservable<string> = ko.observable('');

        //menu list
        selectedBentoSetting: KnockoutObservable<string> = ko.observable('');

        columnBento: KnockoutObservableArray<any> = ko.observableArray([]);
        itemsBento: KnockoutObservableArray<any> = ko.observableArray([]);

        // columnBentoByLocation: KnockoutObservableArray<any> = ko.observableArray([]);
        // itemsBentoByLocation: KnockoutObservableArray<ItemBentoByLocation> = ko.observableArray();

        //bento data
        model: KnockoutObservable<BentoMenuSetting> = ko.observable(new BentoMenuSetting(
            "", "",
            "","",
            false, false,
            "", "",
            "", "",
            0, 0,
            ""));

        workLocationList: KnockoutObservableArray<WorkLocation> = ko.observableArray([]);

        listData = [];
        constructor() {
            super();
            const vm = this;

            // vm.columnBento([
            //     { headerText: vm.$i18n('KMR001_41'), key: 'id', width: 50 },
            //     { headerText: vm.$i18n('KMR001_42'), key: 'name', width: 225 },
            //     { headerText: vm.$i18n('KMR001_42'), key: 'frameNo', width: 0, hidden: true },
            // ]);
            // vm.columnBento([
            //     { headerText: vm.$i18n('KMR001_41'), key: 'id', width: 50 },
            //     { headerText: vm.$i18n('KMR001_42'), key: 'name', width: 225 },
            //     { headerText: vm.$i18n('KMR001_50'), key: 'locationName', width: 100 },
            //     { headerText: vm.$i18n('KMR001_42'), key: 'frameNo', width: 0, hidden: true },
            // ]);

            vm.$ajax(API.GET_LIST_WORK_LOCATION).done(data => {
                data.forEach(item =>{
                    vm.workLocationList.push(
                        new WorkLocation(item.workLocationCD, item.workLocationName)
                    )}

                );
            });


            vm.$ajax(API.GET_ALL, {histId : null}).done(dataRes => {
                if(dataRes.length > 0) {
                    dataRes = _.orderBy(dataRes, ['frameNo', 'asc']);
                    if(dataRes[0].operationDistinction == 1) {

                        vm.columnBento([
                            { headerText: vm.$i18n('KMR001_41'), key: 'id', width: 50 },
                            { headerText: vm.$i18n('KMR001_42'), key: 'name', width: 225 },
                            { headerText: vm.$i18n('KMR001_50'), key: 'locationName', width: 100 },
                        ]);

                        let array: Array<any> = [];
                        _.range(1, 41).forEach(item =>
                            array.push(new ItemBentoByLocation(
                                item.toString(),
                                "",
                                "",
                            ))
                        );
                        dataRes.forEach(item => {
                                array.forEach((rc, index) => {
                                    if(item.frameNo == rc.id) {
                                        array[index].locationName = item.workLocationName;
                                        array[index].name = item.bentoName;
                                    }
                                })
                            }
                        );
                        vm.itemsBento(array);
                    } else {
                        dataRes.forEach(item => {
                            vm.itemsBento().map(rc => {
                                if(rc.id == item.frameNo) {
                                    return new ItemBentoByCompany(
                                        item.frameNo,
                                        item.bentoName,

                                    )
                                }
                            });
                        });
                        vm.columnBento([
                            { headerText: vm.$i18n('KMR001_41'), key: 'id', width: 50 },
                            { headerText: vm.$i18n('KMR001_42'), key: 'name', width: 325 },
                        ]);
                    }
                    vm.start(dataRes[0].startDate);
                    vm.end(dataRes[0].endDate);

                    vm.selectedBentoSetting(dataRes[0].frameNo);
                    
                    vm.model( new BentoMenuSetting(
                        dataRes[0].reservationFrameName1, dataRes[0].reservationFrameName2,
                        dataRes[0].bentoName,  dataRes[0].unitName,
                        dataRes[0].reservationAtr1,  dataRes[0].reservationAtr2,
                        parseTime(dataRes[0].reservationStartTime1, true).format(),  parseTime(dataRes[0].reservationEndTime1,true).format(),
                        parseTime(dataRes[0].reservationStartTime2, true).format(),  parseTime(dataRes[0].reservationEndTime2, true).format(),
                        Number(dataRes[0].price1),  Number(dataRes[0].price2),
                        dataRes[0].workLocationCode
                    ));
                    vm.listData = [...dataRes];

                }
            }).then(() => {
                vm.selectedBentoSetting.subscribe(data => {
                    console.log(data);
                    console.log(vm.listData.filter(item => data == item.frameNo));
                    const bento = vm.listData.filter(item => data == item.frameNo);
                    if(bento.length) {
                        vm.model( new BentoMenuSetting(
                            bento[0].reservationFrameName1, bento[0].reservationFrameName2,
                            bento[0].bentoName,  bento[0].unitName,
                            bento[0].reservationAtr1,  bento[0].reservationAtr2,
                            parseTime(bento[0].reservationStartTime1, true).format(),  parseTime(bento[0].reservationEndTime1,true).format(),
                            parseTime(bento[0].reservationStartTime2, true).format(),  parseTime(bento[0].reservationEndTime2, true).format(),
                            Number(bento[0].price1),  Number(bento[0].price2),
                            bento[0].workLocationCode
                        ))
                    }else {
                        vm.model( new BentoMenuSetting(
                            '', '',
                            '',  '',
                            false,  false,
                            '0',  '0',
                            '0',  '0',
                            0,  0,
                            vm.workLocationList()[0].id
                        ));
                    }

                })
            } );

        }

        created() {
            const vm = this;
            _.extend(window, { vm });
        }

        openConfigHisDialog() {
            let vm = this;
            vm.$blockui('invisible');
            vm.$window.modal('at', PATH.KMR001_D, {});
            vm.$blockui('clear');
            //block.invisible();
            //block.invisible();
            //setShared('KMR001_C_PARAMS', { });
            // modal(PATH.KMR001_D).onClosed(function() {
            //     let params = getShared('KMR001_C_PARAMS');
            // });
            //block.clear();
        }

    }

    class ItemBentoByCompany {
        id: string;
        name: string;

        constructor(id: string, name: string) {
            this.id = id;
            this.name = name;
        }
    }

    class ItemBentoByLocation {
        id: string;
        name: string;
        locationName: string;

        constructor(id: string, name: string, locationName: string) {
            this.id = id;
            this.name = name;
            this.locationName = locationName;
        }
    }

    class BentoMenuSetting{
        reservationFrameName1: KnockoutObservable<string> = ko.observable(null);
        reservationFrameName2: KnockoutObservable<string> = ko.observable(null);
        bentoName: KnockoutObservable<string> = ko.observable(null);
        reservationAtr1: KnockoutObservable<boolean> = ko.observable(false);
        reservationAtr2: KnockoutObservable<boolean> = ko.observable(false);
        reservationStartTime1: KnockoutObservable<string>= ko.observable('');
        reservationEndTime1: KnockoutObservable<string>= ko.observable('');
        reservationStartTime2: KnockoutObservable<string>= ko.observable('');
        reservationEndTime2: KnockoutObservable<string>= ko.observable('');
        unitName: KnockoutObservable<string>= ko.observable(null);
        price1: KnockoutObservable<number>= ko.observable(0);
        price2: KnockoutObservable<number>= ko.observable(0);
        workLocationCode: KnockoutObservable<string>= ko.observable(null);
        constructor(reservationFrameName1: string, reservationFrameName2: string, bentoName: string, unitName: string,
                    reservationAtr1: boolean, reservationAtr2: boolean,
                    reservationStartTime1: string, reservationEndTime1: string,
                    reservationStartTime2: string, reservationEndTime2: string,
                    price1: number, price2: number,
                    workLocationCode: string){
            this.reservationFrameName1(reservationFrameName1);
            this.reservationFrameName2(reservationFrameName2);
            this.bentoName(bentoName);
            this.reservationAtr1(reservationAtr1);
            this.reservationAtr2(reservationAtr2);
            this.reservationStartTime1(reservationStartTime1);
            this.reservationEndTime1(reservationEndTime1);
            this.reservationStartTime2(reservationStartTime2);
            this.reservationEndTime2( reservationEndTime2);
            this.unitName(unitName );
            this.price1(price1);
            this.price2(price2);
            this.workLocationCode(workLocationCode);
        }
    }

    class WorkLocation{
        id: string ;
        name: string ;
        constructor(id: string, name: string) {
            this.id =id;
            this.name = name;
        }
    }

}

