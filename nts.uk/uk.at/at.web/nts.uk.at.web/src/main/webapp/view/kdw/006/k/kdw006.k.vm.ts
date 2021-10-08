/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kmk006.k {

    const API = {
        LIST_WORK_INFO: 'at/record/kdw006/view-k/get-list-work'
    }

    @bean()
    export class ViewModel extends ko.ViewModel {

        public itemList: KnockoutObservableArray<ItemModel>;
        public selectedCode: KnockoutObservable<string>;
        public isEnable: KnockoutObservable<boolean>;
        public isEditable: KnockoutObservable<boolean>;

        public datePeriod: KnockoutObservable<string> = ko.observable('1990/01/28 ~ 9999/12/31');
        public item: KnockoutObservableArray<Model> = ko.observableArray([]);
        public currentCode: KnockoutObservable<string> = ko.observable('');
        public currentName: KnockoutObservable<string> = ko.observable('');
        public currentMemo: KnockoutObservable<string> = ko.observable('');

        public screenMode: KnockoutObservable<number> = ko.observable(SCREEN_MODE.UPDATE);

        created() {
            const vm = this;

            const inputListWork = {
                cId: vm.$user.companyId,
                items: [25, 26, 27, 28, 29]
            }

            vm.$blockui('invisible')
                .then(() => {
                    vm.$ajax('at', API.LIST_WORK_INFO, inputListWork)
                        .then((data: any) => {
                            if (data.length === 0) {
                                // vm.screenMode(SCREEN_MODE.NOT_HISTORY);
                            }
                        })
                })
                .always(() => {
                    vm.$blockui('clear');
                })

            vm.itemList = ko.observableArray([
                { code: '1', name: '基本給' },
                { code: '2', name: '役職手当' },
                { code: '3', name: '基本給ながい文字列ながい文字列ながい文字列' }
            ]);

            vm.selectedCode = ko.observable('1');
            vm.isEnable = ko.observable(true);
            vm.isEditable = ko.observable(true);

            for (var i = 1; i <= 20; i++) {
                vm.item.push({ id: '000000000' + i, code: '00' + i, name: 'ちゅん　ちゅん' + i });
            }

        }

        mounted() {
            const vm = this;

        }

        openViewL() {
            const vm = this;
            vm.$window.modal('at', '/view/kdw/006/l/index.xhtml');
        }
    }

    interface ItemModel {
        code: string;
        name: string;
    }

    export interface Model {
        id: string;
        code: string;
        name: string;
    }

    enum SCREEN_MODE {
        NEW = 1,
        UPDATE = 2,
        NOT_HISTORY = 3
    }
}
