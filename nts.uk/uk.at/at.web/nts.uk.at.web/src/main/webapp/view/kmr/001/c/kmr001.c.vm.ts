/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.kmr001.c {
    import parseTime = nts.uk.time.parseTime;
    import confirm = nts.uk.ui.dialog.confirm;

    const API = {
        GET_LIST_WORK_LOCATION: 'at/record/reservation/bento-menu/getworklocation',
        GET_ALL: 'at/record/reservation/bento-menu/getbentomenubyhist',
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

        //Work location
        selectedWorkLocationCode: KnockoutObservable<string> = ko.observable('');
        workLocationList: KnockoutObservableArray<WorkLocation> = ko.observableArray([]);

        //bento data
        model: KnockoutObservable<BentoMenuSetting> = ko.observable(new BentoMenuSetting());

        //list bento
        listData: Array<any> = [];
        listIdBentoMenu: Array<any> = [];

        history: any = null;
		date: any = "9999/12/31";
		historyID: string = null;

        //reservation data
        reservationEndTime1: KnockoutObservable<string> = ko.observable(null);
        reservationEndTime2: KnockoutObservable<string> = ko.observable(null);
        reservationFrameName1: KnockoutObservable<string> = ko.observable(null);
        reservationFrameName2: KnockoutObservable<string> = ko.observable(null);
        reservationStartTime1: KnockoutObservable<string> = ko.observable(null);
        reservationStartTime2: KnockoutObservable<string> = ko.observable(null);

        //operation data
        operationDistinction: KnockoutObservable<number> = ko.observable(null);

        // is lasted
        isLasted: KnockoutObservable<boolean> = ko.observable(true);

        //visible Closing2
        visibleClosing2: KnockoutObservable<boolean> = ko.observable(false);

        constructor() {
            super();
            const vm = this;
            //get list work location
            vm.$ajax(API.GET_LIST_WORK_LOCATION).done(data => {
                vm.$blockui('invisible');
                if (data) {
                    let listData = []
                    data.forEach(item => {
                        listData.push(new WorkLocation(item.workLocationCD, item.workLocationName));
                    });
                    vm.workLocationList(listData);
                    vm.$blockui('clear');
                }
            });
            vm.getBentoMenu();
        }

        created() {
            const vm = this;

            vm.model().receptionTimezoneNo.subscribe(data => {
                if (data) {
                    vm.$errors("clear", ".reservationAtr");
                }
            });

            _.extend(window, { vm });
        }

        deleteBento() {
            const vm = this;
            confirm({ messageId: "Msg_18" }).ifYes(() => {
                vm.$blockui("invisible");
                let commandDelete = {
                    histId: vm.historyID,
                    frameNo: vm.selectedBentoSetting()
                };
                // delete bento
                vm.$ajax(API.DELETE_BENTO, commandDelete).done(() => {
                    vm.$dialog.info({ messageId: "Msg_16" }).then(() => vm.selectedBentoSetting('')).then(() => vm.getBentoMenu());
                }).fail(function (error) {
                    vm.$dialog.error({ messageId: error.messageId });
                }).always(() => {
                    vm.$blockui("clear");
                    vm.$errors("clear");
                });
            }).ifNo(() => {
            });
        }

        registerBentoMenu() {
            const vm = this,
                model = this.model();
            vm.$validate(".nts-input", ".ntsControl").then((valid: boolean) => {
                if (!valid) {
                    return;
                }
                if (!_.includes([1,2], vm.model().receptionTimezoneNo())) {
                    vm.$errors(".reservationAtr", { messageId: 'MsgB_1', messageParams: [vm.$i18n('KMR001_47')] });
                    return;
                }
                vm.$blockui("invisible");
                if (vm.listIdBentoMenu.indexOf(Number(vm.selectedBentoSetting())) < 0 || 
                        !vm.itemsBento()[Number(vm.selectedBentoSetting()) - 1].name) {
                    const param = {
                        histId: vm.historyID,
                        frameNo: vm.selectedBentoSetting(),
                        benToName: model.bentoName(),
                        workLocationCode: vm.operationDistinction() == 1 ? vm.selectedWorkLocationCode() : null,
                        amount1: model.price1(),
                        amount2: model.price2(),
                        unit: model.unitName(),
                        receptionTimezoneNo: model.receptionTimezoneNo()
                    };
                    //create bento
                    vm.$ajax(API.CREATE_BENTO, param).done(() => {
                        vm.$dialog.info({ messageId: "Msg_15" }).then(function () {
                        }).then(() => vm.getBentoMenu())
                    }).always(() => vm.$blockui("clear"));
                } else {
                    const param = {
                        histId: vm.historyID,
                        frameNo: vm.selectedBentoSetting(),
                        benToName: model.bentoName(),
                        workLocationCode: vm.operationDistinction() == 1 ? vm.selectedWorkLocationCode() : null,
                        amount1: model.price1(),
                        amount2: model.price2(),
                        unit: model.unitName(),
                        receptionTimezoneNo: model.receptionTimezoneNo()
                    };
                    //update bento
                    vm.$ajax(API.UPDATE_BENTO, param).done(() => {
                        vm.$dialog.info({ messageId: "Msg_15" }).then(() => vm.getBentoMenu())
                    }).always(() => vm.$blockui("clear"));
                }
            })
        }

        openConfigHisDialog() {
            const vm = this;
            vm.$blockui('invisible');
            vm.$window.modal('at', PATH.KMR001_D)
                .then((result: any) => {
					if(result) {
						vm.date = result.params.date;
						vm.isLasted(result.params.isLasted);
						vm.getBentoMenu();	
					}
                }).then(() => {
                    vm.$blockui('clear');
                    vm.$errors("clear");
                });
        }

        getBentoMenu() {
            const vm = this;
            //get list bento
            vm.$ajax(API.GET_ALL, {date: vm.date}).done(dataRes => {
                vm.$blockui('invisible');
                let bentoDtos = dataRes.bentoDtos;
				vm.historyID = dataRes.historyID;
                vm.reservationFrameName1(dataRes.reservationFrameName1);
                vm.reservationStartTime1(parseTime(dataRes.reservationStartTime1, true).format());
                vm.reservationEndTime1(parseTime(dataRes.reservationEndTime1, true).format());
                vm.start(dataRes.startDate);
                vm.end(dataRes.endDate);
                vm.operationDistinction(dataRes.operationDistinction);
                vm.listData = [...bentoDtos];
                if (dataRes.reservationEndTime2 && dataRes.reservationStartTime2 && dataRes.reservationFrameName2) {
                    vm.visibleClosing2(true);
                    vm.reservationEndTime2(parseTime(dataRes.reservationEndTime2, true).format());
                    vm.reservationStartTime2(parseTime(dataRes.reservationStartTime2, true).format());
                    vm.reservationFrameName2(dataRes.reservationFrameName2);
                }
                bentoDtos = _.orderBy(bentoDtos, ['frameNo', 'asc']);
                vm.columnBento([
                    { headerText: vm.$i18n('KMR001_41'), key: 'id', width: 50, formatter: _.escape },
                    { headerText: vm.$i18n('KMR001_42'), key: 'name', width: 325, formatter: _.escape },
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
                        if (item.frameNo == rc.id) {
                            array[index].name = item.bentoName;
                        }
                    })
                }
                );
                vm.itemsBento(array);
				if(!_.isEmpty(bentoDtos)) {
                    if(vm.selectedBentoSetting() === '')
					    vm.selectedBentoSetting(bentoDtos[0].frameNo);	
				} else {
					vm.selectedBentoSetting(vm.itemsBento()[0].id);
				}
				if(!_.isEmpty(bentoDtos)) {
					vm.model().updateData(
	                    bentoDtos[Number(vm.selectedBentoSetting()) - 1].bentoName, bentoDtos[Number(vm.selectedBentoSetting()) - 1].unitName,
	                    bentoDtos[Number(vm.selectedBentoSetting()) - 1].receptionTimezoneNo,
	                    Number(bentoDtos[Number(vm.selectedBentoSetting()) - 1].price1), Number(bentoDtos[Number(vm.selectedBentoSetting()) - 1].price2),
	                    bentoDtos[Number(vm.selectedBentoSetting()) - 1].workLocationCode
	                );
	                vm.model.valueHasMutated();	
				} else {
					vm.model().updateData(
                        '', null,
                        1,
                        null, null,
                        vm.workLocationList().length > 0 ? vm.workLocationList()[0].id : ''
                    );
                    vm.model.valueHasMutated();
				}
            }).then(() => {
                vm.selectedBentoSetting.subscribe(data => {
                    vm.$blockui('invisible');
                    const bento = vm.listData.filter(item => data == item.frameNo);
                    if (bento.length > 0) {
                        vm.model().updateData(
                            bento[0].bentoName, bento[0].unitName,
                            bento[0].receptionTimezoneNo,
                            Number(bento[0].price1), Number(bento[0].price2),
                            bento[0].workLocationCode
                        );
                        vm.model.valueHasMutated();
                        vm.selectedWorkLocationCode(bento[0].workLocationCode);
                        vm.$blockui('clear');
                    } else {
                        vm.model().updateData(
                            '', null,
                            1,
                            null, null,
                            vm.workLocationList().length > 0 ? vm.workLocationList()[0].id : ''
                        );
                        vm.model.valueHasMutated();
                        vm.selectedWorkLocationCode(vm.workLocationList().length > 0 ? vm.workLocationList()[0].id : '');
                        vm.$blockui('clear');
                    }
                    vm.$errors("clear");
                });
                vm.selectedWorkLocationCode.subscribe((data) => {
                    vm.model().workLocationCode(data);
                });
            }).fail(function (error) {
                vm.isLasted(false);
				if(error.messageId=="Msg_3259") {
					vm.$dialog.error({ messageId: error.messageId }).then(function () {
	                    vm.$jump("at", "/view/kmr/001/a/index.xhtml");
	                });
					return;
				}
				if(error.messageId=="Msg_3260") {
					vm.$dialog.error({ messageId: error.messageId }).then(function () {
	                	vm.openConfigHisDialog();
	                });
					return;
				}
                vm.$dialog.error({ messageId: error.messageId }).then(function () {
                    vm.$jump("at", "/view/kmr/001/a/index.xhtml");
                });
            }).always(() => vm.$blockui("clear"));
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

    class BentoMenuSetting {
        bentoName: KnockoutObservable<string> = ko.observable("");
        receptionTimezoneNo: KnockoutObservable<number> = ko.observable(1);
        unitName: KnockoutObservable<string> = ko.observable("");
        price1: KnockoutObservable<number> = ko.observable(null);
        price2: KnockoutObservable<number> = ko.observable(null);
        workLocationCode: KnockoutObservable<string> = ko.observable("");

        constructor() { }

        updateData(bentoName: string, unitName: string,
            receptionTimezoneNo: number,
            price1: number, price2: number,
            workLocationCode: string) {
            this.bentoName(bentoName);
            this.receptionTimezoneNo(receptionTimezoneNo);
            this.unitName(unitName);
            this.price1(price1);
            this.price2(price2);
            this.workLocationCode(workLocationCode);
        }
    }

    class WorkLocation {
        id: string;
        name: string;

        constructor(id: string, name: string) {
            this.id = id;
            this.name = name;
        }
    }

}

