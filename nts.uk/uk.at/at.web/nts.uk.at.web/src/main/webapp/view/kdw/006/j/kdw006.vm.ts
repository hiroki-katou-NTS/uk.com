/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kmk006.j {

    @bean()
    export class ViewModel extends ko.ViewModel {

        public tabs: KnockoutObservableArray<any> = ko.observableArray([
            { id: 'tab-1', title: this.$i18n('KDW006_308'), content: '.tab-content-1', enable: ko.observable(true), visible: ko.observable(true) },
            { id: 'tab-2', title: this.$i18n('KDW006_309'), content: '.tab-content-2', enable: ko.observable(true), visible: ko.observable(true) },
            { id: 'tab-3', title: this.$i18n('KDW006_310'), content: '.tab-content-3', enable: ko.observable(true), visible: ko.observable(true) }
        ]);
        public selectedTab: KnockoutObservable<string> = ko.observable('tab-1');
        public columns1: KnockoutObservableArray<any> = ko.observableArray([
            { headerText: this.$i18n('KDW006_44'), key: 'code', width: 70 },
            { headerText: 'number', key: 'id', hidden: true, width: 100 },
            { headerText: this.$i18n('KDW006_45'), key: 'name', width: 200, formatter: _.escape }
        ]);

        // Value Tab1
        public textInput1: KnockoutObservable<string> = ko.observable(this.$i18n('復路'));
        public textInput2: KnockoutObservable<string> = ko.observable(this.$i18n('往路'));
        public textInput3: KnockoutObservable<string> = ko.observable(this.$i18n('総働'));

        // Value Tab2
        public currentCodeListLeft: KnockoutObservableArray<any> = ko.observableArray([]);
        public itemsLeft: KnockoutObservableArray<ModelList> = ko.observableArray([]);

        // Value Tab3
        public currentCodeListRight: KnockoutObservableArray<any> = ko.observableArray([]);
        public itemsRight: KnockoutObservableArray<ModelList> = ko.observableArray([]);

        created() {
            const vm = this;

            for (var i = 1; i <= 20; i++) {
                vm.itemsLeft.push({ id: i + '', code: '00' + i, name: 'Employee' + i });
            }

            for (var i = 1; i <= 20; i++) {
                vm.itemsRight.push({ id: i + '', code: '00' + i, name: 'Employee' + i });
            }

            console.log(ko.unwrap(vm.itemsLeft));
            

        }

        mounted() {
            const vm = this;

            
        }
    }

    export interface ModelList {
        id: string;
        code: string;
        name: string;
    }
}
