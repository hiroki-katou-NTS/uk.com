/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.kmr001.c {
    import parseTime = nts.uk.time.parseTime;

    const API = {
        GET_LIST_WORK_LOCATION: 'screen/at/record/reservation/bento-menu/getworklocation',
        GET_ALL : 'screen/at/record/reservation/bento-menu/getbentomenubyhist',
        CREATE_BENTO_MENU: 'bento/updateitemietting/add',
        UPDATE_BENTO_MENU: 'bento/updateitemietting/update',
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


        selectedWorkLocationCode: KnockoutObservable<string> = ko.observable('')
        // columnBentoByLocation: KnockoutObservableArray<any> = ko.observableArray([]);
        // itemsBentoByLocation: KnockoutObservableArray<ItemBentoByLocation> = ko.observableArray();

        //bento data
        model: KnockoutObservable<BentoMenuSetting> = ko.observable(new BentoMenuSetting(
            "", "",
            "","",
            false, false,
            "", "",
            "", "",
            null, null,
            ""));

        workLocationList: KnockoutObservableArray<WorkLocation> = ko.observableArray([]);

        listData: Array<any> = [];
        listIdBentoMenu: Array<any> = [];

        history: any = null;
        constructor() {
            super();
            const vm = this;
            vm.$ajax(API.GET_LIST_WORK_LOCATION).done(data => {
                vm.$blockui('invisible');
                if(data) {
                    data.forEach(item =>{
                        vm.workLocationList.push(
                            new WorkLocation(item.workLocationCD, item.workLocationName)
                        )});
                    vm.$blockui('clear');
                }
            });


            vm.$ajax(API.GET_ALL, {histId : null}).done(dataRes => {
                vm.$blockui('invisible');
                if(dataRes.bentoDtos.length > 0) {
                    let bentoDtos = dataRes.bentoDtos;
                    bentoDtos = _.orderBy(bentoDtos, ['frameNo', 'asc']);

                    if(dataRes.operationDistinction == 1) {

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
                        bentoDtos.forEach(item => {
                                vm.listIdBentoMenu.push(item.frameNo);
                                array.forEach((rc, index) => {
                                    if(item.frameNo == rc.id) {
                                        array[index].locationName = item.workLocationName;
                                        array[index].name = item.bentoName;
                                    }
                                })
                            }
                        );
                        vm.itemsBento(array);
                        vm.selectedBentoSetting(bentoDtos[0].frameNo);
                        vm.selectedWorkLocationCode(bentoDtos[0].workLocationCode);

                    } else {
                        vm.columnBento([
                            { headerText: vm.$i18n('KMR001_41'), key: 'id', width: 50 },
                            { headerText: vm.$i18n('KMR001_42'), key: 'name', width: 325 },
                        ]);
                        let array: Array<any> = [];
                        _.range(1, 41).forEach(item =>
                            array.push(new ItemBentoByCompany(
                                item.toString(),
                                "",
                            ))
                        );
                        bentoDtos.forEach(item => {
                                vm.listIdBentoMenu.push(item.frameNo);
                                array.forEach((rc, index) => {
                                    if(item.frameNo == rc.id) {
                                        array[index].name = item.bentoName;
                                    }
                                })
                            }
                        );

                        vm.selectedWorkLocationCode(vm.workLocationList()[0].id);
                    }

                    vm.model( new BentoMenuSetting(
                        bentoDtos[0].reservationFrameName1, bentoDtos[0].reservationFrameName2,
                        bentoDtos[0].bentoName,  bentoDtos[0].unitName,
                        bentoDtos[0].reservationAtr1,  bentoDtos[0].reservationAtr2,
                        parseTime(bentoDtos[0].reservationStartTime1, true).format(),  parseTime(bentoDtos[0].reservationEndTime1,true).format(),
                        parseTime(bentoDtos[0].reservationStartTime2, true).format(),  parseTime(bentoDtos[0].reservationEndTime2, true).format(),
                        Number(bentoDtos[0].price1),  Number(bentoDtos[0].price2),
                        bentoDtos[0].workLocationCode
                    ));
                    vm.start(dataRes.startDate);
                    vm.end(dataRes.endDate);

                    vm.listData = [...bentoDtos];
                }
                vm.$blockui('clear');
            }).then(() => {
                vm.selectedBentoSetting.subscribe(data => {
                    vm.$blockui('invisible');
                    const bento = vm.listData.filter(item => data == item.frameNo);
                    if(bento.length > 0) {
                        vm.model( new BentoMenuSetting(
                            bento[0].reservationFrameName1, bento[0].reservationFrameName2,
                            bento[0].bentoName,  bento[0].unitName,
                            bento[0].reservationAtr1,  bento[0].reservationAtr2,
                            parseTime(bento[0].reservationStartTime1, true).format(),  parseTime(bento[0].reservationEndTime1,true).format(),
                            parseTime(bento[0].reservationStartTime2, true).format(),  parseTime(bento[0].reservationEndTime2, true).format(),
                            Number(bento[0].price1),  Number(bento[0].price2),
                            bento[0].workLocationCode
                        ));
                        vm.selectedWorkLocationCode(bento[0].workLocationCode);
                        vm.$blockui('clear');
                    } else {
                        vm.model( new BentoMenuSetting(
                            '', '',
                            '',  null,
                            false,  false,
                            parseTime(360, true).format(),  parseTime(600, true).format(),
                            parseTime(720, true).format(),  parseTime(900, true).format(),
                            null,  null,
                            vm.workLocationList()[0].id
                        ));
                        vm.selectedWorkLocationCode(vm.workLocationList()[0].id);
                        vm.$blockui('clear');
                    }
                });
                vm.selectedWorkLocationCode.subscribe((data) => {
                    vm.model().workLocationCode(data);
                })
            } );

        }

        created() {
            const vm = this;
            _.extend(window, { vm });
        }

        registerBentoMenu() {
            const vm = this,
            model = this.model();
            if(vm.listIdBentoMenu.indexOf(Number(vm.selectedBentoSetting())) >= 0 ) {
                // console.log(new paramsRegister(vm.model().))
                const param = {
                    histId: vm.history && vm.history.params.historyId ? vm.history.params.historyId : null,
                    frameNo: vm.selectedBentoSetting(),
                    benToName: model.bentoName(),
                    workLocationCode: model.workLocationCode(),
                    amount1: model.price1(),
                    amount2: model.price2(),
                    Unit: model.unitName(),
                    canBookClosesingTime1: model.reservationAtr1(),
                    canBookClosesingTime2: model.reservationAtr2()
                };
                vm.$ajax(API.UPDATE_BENTO_MENU, param).done(() => {
                    console.log('done!!!')
                })
            } else {
                const param = {
                    histId: vm.history && vm.history.params.historyId ? vm.history.params.historyId : null,
                    frameNo: vm.selectedBentoSetting(),
                    benToName: model.bentoName(),
                    workLocationCode: model.workLocationCode(),
                    amount1: model.price1(),
                    amount2: model.price2(),
                    Unit: model.unitName(),
                    canBookClosesingTime1: model.reservationAtr1(),
                    canBookClosesingTime2: model.reservationAtr2()
                };
                vm.$ajax(API.CREATE_BENTO_MENU, param).done(() => {
                    console.log('done!!!')
                })
            }
        }

        openConfigHisDialog() {
            let vm = this;
            vm.$blockui('invisible');
            vm.$window.modal('at', PATH.KMR001_D, vm.history && vm.history.params.historyId ? vm.history.params.historyId : null)
                .then((result: any) => {
                    console.log(result);
                    vm.history = result;
                });
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

    class paramsRegister{
        histId: any;
        frameNo: any;
        benToName: any;
        workLocationCode: any;
        amount1: any;
        amount2: any;
        Unit: any;
        canBookClosesingTime1: any;
        canBookClosesingTime2: any;
        constructor(histId, frameNo, benToName, workLocationCode, amount1, amount2, Unit,
                    canBookClosesingTime1, canBookClosesingTime2) {
            this.histId = histId;
            this.frameNo = frameNo;
            this.benToName = benToName;
            this.workLocationCode = workLocationCode;
            this.amount1 = amount1;
            this.amount2 = amount2;
            this.Unit = Unit;
            this.canBookClosesingTime1 = canBookClosesingTime1;
            this.canBookClosesingTime2 = canBookClosesingTime2;
        }
    }

}

