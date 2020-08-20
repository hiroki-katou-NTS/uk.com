/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.kmr001.c {

    const API = {
        SETTING: 'at/record/stamp/management/personal/startPage',
        HIGHTLIGHT: 'at/record/stamp/management/personal/stamp/getHighlightSetting',
        GET_ALL : 'screen/at/record/reservation/bento_menu/getBentoMenuByHist/00000000-0000-0000-0001-000000000002'
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
        model: BentoMenuSetting = new BentoMenuSetting(ko.observable(""),ko.observable(""),
            ko.observable(false), ko.observable(false),
            ko.observable(0), ko.observable(0),
            ko.observable(0), ko.observable(0),
            ko.observable(0), ko.observable(0),
            ko.observable(""), ko.observable(""),
            ko.observable(""));
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
            vm.$ajax(API.GET_ALL).done(dataRes => {
                console.log(dataRes)
            });
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
        bentoName: KnockoutObservable<string>;
        reservationAtr1: KnockoutObservable<boolean>;
        reservationAtr2: KnockoutObservable<boolean>;
        reservationStartTime1: KnockoutObservable<number>;
        reservationEndTime1: KnockoutObservable<number>;
        reservationStartTime2: KnockoutObservable<number>;
        reservationEndTime2: KnockoutObservable<number>;
        unitName: KnockoutObservable<string>;
        price1: KnockoutObservable<number>;
        price2: KnockoutObservable<number>;

        startDate: KnockoutObservable<string>;
        endDate: KnockoutObservable<string>;
        workLocationCode: KnockoutObservable<string>;
        constructor(bentoName: KnockoutObservable<string>, unitName: KnockoutObservable<string>,
                    reservationAtr1: KnockoutObservable<boolean>, reservationAtr2: KnockoutObservable<boolean>,
                    reservationStartTime1: KnockoutObservable<number>, reservationEndTime1: KnockoutObservable<number>,
                    reservationStartTime2: KnockoutObservable<number>, reservationEndTime2: KnockoutObservable<number>,
                    price1: KnockoutObservable<number>, price2: KnockoutObservable<number>,
                    startDate: KnockoutObservable<string>, endDate: KnockoutObservable<string>,
                    workLocationCode: KnockoutObservable<string>){
            this.bentoName = bentoName;
            this.reservationAtr1 = reservationAtr1;
            this.reservationAtr2 = reservationAtr2;
            this.reservationStartTime1 = reservationStartTime1 ;
            this.reservationEndTime1 = reservationEndTime1 ;
            this.reservationStartTime2 = reservationStartTime2 ;
            this.reservationEndTime2 =  reservationEndTime2;
            this.unitName = unitName ;
            this.price1 = price1;
            this.price2 = price2;

            this.startDate = startDate;
            this.endDate = endDate;
            this.workLocationCode = workLocationCode;
        }
    }

}

