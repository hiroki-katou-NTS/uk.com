/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.kmr001.c {

    const API = {
        SETTING: 'at/record/stamp/management/personal/startPage',
        HIGHTLIGHT: 'at/record/stamp/management/personal/stamp/getHighlightSetting',
        GET_ALL : 'screen/at/record/reservation/bento_menu/getBentoMenuByHist'
    };

    const PATH = {
        REDIRECT: '/view/ccg/008/a/index.xhtml',
        KMR001_D: '/view/kmr/001/d/index.xhtml'
    }

    @bean()
    export class ViewModel extends ko.ViewModel {

        items: KnockoutObservableArray<ItemModel> = ko.observableArray([]);
        currentCode: KnockoutObservable<string> = ko.observable('');
        currentCodeList: KnockoutObservableArray<any> = ko.observableArray([]);
        workPlaceList: KnockoutObservableArray<ReservedItemDto> = ko.observableArray([]);
        bentoList: KnockoutObservableArray<any> = ko.observableArray([]);
        columnBento: KnockoutObservableArray<any> = ko.observableArray([]);
        model: KnockoutObservable<BentoMenuSetting> = ko.observable(new BentoMenuSetting("","",
            false, false,
            0, 0,
            0, 0,
            0, 0,
            "", "",
            ""));
        hidden : KnockoutObservable<boolean> = ko.observable(true);
        workPlaceCode: KnockoutObservable<string> = ko.observable();
        constructor() {
            super();
            const vm = this;
            for(let i = 1; i < 41; ++i){
                vm.items.push(new ItemModel(i.toString(), 'data ' + i));
            }
            vm.workPlaceList([
                {
                    id: '1',
                    name: 'test'
                }
            ]);
            vm.columnBento([
                { headerText: vm.$i18n('KMR001_41'), key: 'id', width: 50 },
                { headerText: vm.$i18n('KMR001_42'), key: 'name', width: 225 },
                { headerText: vm.$i18n('KMR001_39'), key: 'workLocaltionName', width: 100,hidden: vm.hidden() }
            ])
            vm.$ajax(API.GET_ALL, {histId : null}).done(dataRes => {
                console.log(dataRes)
                if(dataRes && dataRes[0].operationDistinction == 1) {
                    vm.hidden(false);
                }
                vm.model (
                    new BentoMenuSetting(
                        dataRes[0].bentoName,  dataRes[0].unitName,
                        dataRes[0].reservationAtr1,  dataRes[0].reservationAtr2,
                        Number(dataRes[0].reservationStartTime1),  Number(dataRes[0].reservationEndTime1),
                        Number(dataRes[0].reservationStartTime2),  Number(dataRes[0].reservationEndTime2),
                        Number(dataRes[0].price1),  Number(dataRes[0].price2),
                        dataRes[0].startDate,  dataRes[0].endDate,
                        dataRes[0].workLocationCode
                    )
                );
                console.log(vm)
            });
            vm.currentCode.subscribe(data => {
                console.log(data)

            })
        }

        deselectAll() {
            this.currentCode(null);
            this.currentCodeList.removeAll();
        }

        removeItem() {
            this.items.shift();
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

    class ItemModel {
        id: string;
        name: string;

        constructor(id: string, name: string) {
            this.id = id;
            this.name = name;
        }
    }

    interface ReservedItemDto {
        id: string;
        name: string;
    }

    class BentoMenuSetting{
        bentoName: KnockoutObservable<string> = ko.observable(null);
        reservationAtr1: KnockoutObservable<boolean> = ko.observable(false);
        reservationAtr2: KnockoutObservable<boolean> = ko.observable(false);
        reservationStartTime1: KnockoutObservable<number>= ko.observable(0);
        reservationEndTime1: KnockoutObservable<number>= ko.observable(0);
        reservationStartTime2: KnockoutObservable<number>= ko.observable(0);
        reservationEndTime2: KnockoutObservable<number>= ko.observable(0);
        unitName: KnockoutObservable<string>= ko.observable(null);
        price1: KnockoutObservable<number>= ko.observable(0);
        price2: KnockoutObservable<number>= ko.observable(0);

        startDate: KnockoutObservable<string>= ko.observable(null);
        endDate: KnockoutObservable<string>= ko.observable(null);
        workLocationCode: KnockoutObservable<string>= ko.observable(null);
        constructor(bentoName: string, unitName: string,
                    reservationAtr1: boolean, reservationAtr2: boolean,
                    reservationStartTime1: number, reservationEndTime1: number,
                    reservationStartTime2: number, reservationEndTime2: number,
                    price1: number, price2: number,
                    startDate: string, endDate: string,
                    workLocationCode: string){
            this.bentoName(bentoName);
            this.reservationAtr1(reservationAtr1);
            this.reservationAtr2(reservationAtr2);
            this.reservationStartTime1(reservationStartTime1 );
            this.reservationEndTime1(reservationEndTime1 );
            this.reservationStartTime2(reservationStartTime2 );
            this.reservationEndTime2( reservationEndTime2);
            this.unitName(unitName );
            this.price1(price1);
            this.price2(price2);

            this.startDate(startDate);
            this.endDate(endDate);
            this.workLocationCode(workLocationCode);
        }
    }

}

