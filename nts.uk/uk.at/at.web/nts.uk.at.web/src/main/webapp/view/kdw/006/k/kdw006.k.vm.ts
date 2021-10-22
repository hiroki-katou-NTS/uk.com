/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kmk006.k {

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

        created() {
            const vm = this;

            vm.itemList = ko.observableArray([
                { code: '1', name: '基本給' },
                { code: '2', name: '役職手当' },
                { code: '3', name: '基本給ながい文字列ながい文字列ながい文字列' }
            ]);

            vm.selectedCode = ko.observable('1');
            vm.isEnable = ko.observable(true);
            vm.isEditable = ko.observable(true);

            for (var i = 1; i <= 20; i++) {
                vm.item.push({ id: '000000000' + i , code: '00' + i, name: 'Employee' + i });
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
}
