/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.kmr001.c {
    import parseTime = nts.uk.time.parseTime;

    const API = {
        GET_LIST_WORK_LOCATION: 'screen/at/record/reservation/bento-menu/getworklocation',
        GET_ALL : 'screen/at/record/reservation/bento-menu/getbentomenubyhist',
        CREATE_BENTO: 'bento/updateitemsetting/add',
        UPDATE_BENTO: 'bento/updateitemsetting/update',
        DELETE_BENTO: 'bento/updateitemsetting/delete',
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


        selectedWorkLocationCode: KnockoutObservable<string> = ko.observable('');
        // columnBentoByLocation: KnockoutObservableArray<any> = ko.observableArray([]);
        // itemsBentoByLocation: KnockoutObservableArray<ItemBentoByLocation> = ko.observableArray();

        //bento data
        model: KnockoutObservable<BentoMenuSetting> = ko.observable(new BentoMenuSetting(
            "","",
            false, false,
            null, null,
            ""));

        workLocationList: KnockoutObservableArray<WorkLocation> = ko.observableArray([]);

        listData: Array<any> = [];
        listIdBentoMenu: Array<any> = [];

        history: any = null;

        reservationEndTime1: KnockoutObservable<string> = ko.observable(null);
        reservationEndTime2: KnockoutObservable<string> = ko.observable(null);
        reservationFrameName1: KnockoutObservable<string> = ko.observable(null);
        reservationFrameName2: KnockoutObservable<string> = ko.observable(null);
        reservationStartTime1: KnockoutObservable<string> = ko.observable(null);
        reservationStartTime2: KnockoutObservable<string> = ko.observable(null);

        operationDistinction: KnockoutObservable<number> = ko.observable(null);
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
            vm.getBentoMenu(null);

        }

        created() {
            const vm = this;
            _.extend(window, { vm });
        }

        reloadPage() {
            const vm = this;
            vm.$ajax(API.GET_ALL, {histId : vm.history && vm.history.params.historyId ? vm.history.params.historyId : null}).done(dataRes => {
                let bentoDtos = dataRes.bentoDtos;
                if(vm.operationDistinction() == 1) {
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
                } else {
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
                    vm.itemsBento(array);
                }
                vm.listData = [...bentoDtos];
            })

        }

        deleteBento() {
            const vm = this;
            vm.$dialog.confirm({ messageId: 'Msg_18' }).then(res => {
                if (res == "yes"){
                    vm.$blockui("invisible");
                    let commandDelete ={
                        histId: vm.history && vm.history.params.historyId ? vm.history.params.historyId : null,
                        frameNo: vm.selectedBentoSetting()
                    };

                    vm.$ajax(API.DELETE_BENTO, commandDelete).done(() => {
                        vm.$dialog.info({ messageId: "Msg_16" }).then(function () {
                            vm.reloadPage();
                            vm.model( new BentoMenuSetting(
                                '',  null,
                                false,  false,
                                null,  null,
                                vm.workLocationList()[0].id
                            ));
                            vm.selectedWorkLocationCode(vm.workLocationList()[0].id);
                            vm.$blockui("clear");
                        });
                    }).always(() => vm.$blockui("clear"));
                }
            });
        }

        registerBentoMenu() {
            const vm = this,
            model = this.model();
            $(".nts-input").trigger("validate");
            if (nts.uk.ui.errors.hasError()){
                return;
            }
            vm.$blockui("invisible");
            if(vm.listIdBentoMenu.indexOf(Number(vm.selectedBentoSetting())) >= 0 ) {
                const param = {
                    histId: vm.history && vm.history.params.historyId ? vm.history.params.historyId : null,
                    frameNo: vm.selectedBentoSetting(),
                    benToName: model.bentoName(),
                    workLocationCode: vm.operationDistinction() == 1 ? vm.selectedWorkLocationCode() : null,
                    amount1: model.price1(),
                    amount2: model.price2(),
                    unit: model.unitName(),
                    canBookClosesingTime1: model.reservationAtr1(),
                    canBookClosesingTime2: model.reservationAtr2()
                };
                vm.$ajax(API.CREATE_BENTO, param).done(() => {
                    vm.$dialog.info({ messageId: "Msg_15" }).then(function () {
                        vm.$blockui("clear");
                    });
                }).always(() => this.$blockui("clear"));
            } else {
                const param = {
                    histId: vm.history && vm.history.params.historyId ? vm.history.params.historyId : null,
                    frameNo: vm.selectedBentoSetting(),
                    benToName: model.bentoName(),
                    workLocationCode: vm.operationDistinction() == 1 ?  vm.selectedWorkLocationCode() : null,
                    amount1: model.price1(),
                    amount2: model.price2(),
                    unit: model.unitName(),
                    canBookClosesingTime1: model.reservationAtr1(),
                    canBookClosesingTime2: model.reservationAtr2()
                };
                vm.$ajax(API.CREATE_BENTO, param).done(() => {
                    vm.$dialog.info({ messageId: "Msg_15" }).then(function () {
                        vm.$blockui("clear");
                    });
                }).always(() => this.$blockui("clear"));
            }
            vm.reloadPage();
        }

        openConfigHisDialog() {
            let vm = this;
            vm.$blockui('invisible');
            vm.$window.modal('at', PATH.KMR001_D, vm.history && vm.history.params ? vm.history.params : null)
                .then((result: any) => {
                    console.log(result);
                    if(vm.history && vm.history.params.historyId == result.params.historyId) {
                        return
                    }
                    vm.getBentoMenu(result.params.historyId );
                    vm.history = result;
                });
            vm.$blockui('clear');
        }

        getBentoMenu(historyId: string) {
            const vm = this;
            vm.$ajax(API.GET_ALL, {histId : historyId ? historyId : null}).done(dataRes => {
                vm.$blockui('invisible');
                vm.reservationFrameName1(dataRes.reservationFrameName1);
                vm.reservationFrameName2(dataRes.reservationFrameName2);
                vm.reservationEndTime1(dataRes.reservationEndTime1);
                vm.reservationEndTime2(dataRes.reservationEndTime2);
                vm.reservationStartTime1(dataRes.reservationStartTime1);
                vm.reservationStartTime2(dataRes.reservationStartTime2);
                vm.start(dataRes.startDate);
                vm.end(dataRes.endDate);
                vm.operationDistinction(dataRes.operationDistinction);
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
                        vm.itemsBento(array);
                        vm.selectedBentoSetting(bentoDtos[0].frameNo);
                    }

                    vm.model( new BentoMenuSetting(
                        bentoDtos[0].bentoName,  bentoDtos[0].unitName,
                        bentoDtos[0].reservationAtr1,  bentoDtos[0].reservationAtr2,
                        Number(bentoDtos[0].price1), Number(bentoDtos[0].price2),
                        bentoDtos[0].workLocationCode
                    ));
                    vm.reservationFrameName1(dataRes.reservationFrameName1);
                    vm.reservationFrameName2(dataRes.reservationFrameName2);
                    vm.reservationStartTime1(parseTime(dataRes.reservationStartTime1, true).format());
                    vm.reservationStartTime2(parseTime(dataRes.reservationStartTime2, true).format());
                    vm.reservationEndTime1(parseTime(dataRes.reservationEndTime1,true).format());
                    vm.reservationEndTime2(parseTime(dataRes.reservationEndTime2, true).format());
                    vm.listData = [...bentoDtos];
                }
                vm.$blockui('clear');
            }).then(() => {
                vm.selectedBentoSetting.subscribe(data => {
                    vm.$blockui('invisible');
                    const bento = vm.listData.filter(item => data == item.frameNo);
                    if(bento.length > 0) {
                        vm.model( new BentoMenuSetting(
                            bento[0].bentoName,  bento[0].unitName,
                            bento[0].reservationAtr1,  bento[0].reservationAtr2,
                            Number(bento[0].price1),  Number(bento[0].price2),
                            bento[0].workLocationCode
                        ));
                        vm.selectedWorkLocationCode(bento[0].workLocationCode);
                        vm.$blockui('clear');
                    } else {
                        vm.model( new BentoMenuSetting(
                            '',  null,
                            false,  false,
                            null,  null,
                            vm.workLocationList()[0].id
                        ));
                        vm.selectedWorkLocationCode(vm.workLocationList()[0].id);
                        vm.$blockui('clear');
                    }
                });
                vm.selectedWorkLocationCode.subscribe((data) => {
                    vm.model().workLocationCode(data);
                });
                vm.end.subscribe(data => {
                    console.log(data)
                })
            }).always(() => this.$blockui("clear"));
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
        bentoName: KnockoutObservable<string> = ko.observable(null);
        reservationAtr1: KnockoutObservable<boolean> = ko.observable(false);
        reservationAtr2: KnockoutObservable<boolean> = ko.observable(false);
        unitName: KnockoutObservable<string>= ko.observable(null);
        price1: KnockoutObservable<number>= ko.observable(0);
        price2: KnockoutObservable<number>= ko.observable(0);
        workLocationCode: KnockoutObservable<string>= ko.observable(null);
        constructor( bentoName: string, unitName: string,
                    reservationAtr1: boolean, reservationAtr2: boolean,
                    price1: number, price2: number,
                    workLocationCode: string){
            this.bentoName(bentoName);
            this.reservationAtr1(reservationAtr1);
            this.reservationAtr2(reservationAtr2);
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

