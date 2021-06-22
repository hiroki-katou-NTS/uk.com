module nts.uk.at.kha003.c {

    const API = {
        //TODO api path
    };

    @bean()
    export class ViewModel extends ko.ViewModel {
        // c2_4
        c24Items: KnockoutObservableArray<ItemModel>;
        c24CurrentCodeList: KnockoutObservableArray<any>;
        // c3_4
        c34Items: KnockoutObservableArray<ItemModel>;
        c34CurrentCodeList: KnockoutObservableArray<any>;
        // c4_4
        c44Items: KnockoutObservableArray<ItemModel>;
        c44CurrentCodeList: KnockoutObservableArray<any>;
        // c5_4
        c54Items: KnockoutObservableArray<ItemModel>;
        c54CurrentCodeList: KnockoutObservableArray<any>;
        columns: KnockoutObservableArray<any>;
        c21Params: KnockoutObservable<any>;
        c21Text: KnockoutObservable<any>;
        c31Params: KnockoutObservable<any>;
        c31Text: KnockoutObservable<any>;
        c41Params: KnockoutObservable<any>;
        c41Text: KnockoutObservable<any>;
        c51Params: KnockoutObservable<any>;
        c51Text: KnockoutObservable<any>;
        constructor() {
            super();
            const vm = this;
            // c2_4
            vm.c24Items = ko.observableArray([]);
            var str = ['a0', 'b0', 'c0', 'd0'];
            for (var j = 0; j < 4; j++) {
                for (var i = 1; i < 51; i++) {
                    var code = i < 10 ? str[j] + '0' + i : str[j] + i;
                    vm.c24Items.push(new ItemModel(code, code));
                }
            }
            vm.columns = ko.observableArray([
                {headerText: this.$i18n('KHA003_62'), prop: 'code', width: 80}, //c2_5,c3_5,c4_5,c5_5
                {headerText: this.$i18n('KHA003_63'), prop: 'name', width: 150}  //c2_6,c3_6,c4_6,c5_6
            ]);
            vm.c24CurrentCodeList = ko.observableArray([]);

            // c3_4
            vm.c34Items = ko.observableArray([]);
            var str = ['a0', 'b0', 'c0', 'd0'];
            for (var j = 0; j < 4; j++) {
                for (var i = 1; i < 51; i++) {
                    var code = i < 10 ? str[j] + '0' + i : str[j] + i;
                    vm.c34Items.push(new ItemModel(code, code));
                }
            }
            vm.c34CurrentCodeList = ko.observableArray([]);

            // c4_4
            vm.c44Items = ko.observableArray([]);
            var str = ['a0', 'b0', 'c0', 'd0'];
            for (var j = 0; j < 4; j++) {
                for (var i = 1; i < 51; i++) {
                    var code = i < 10 ? str[j] + '0' + i : str[j] + i;
                    vm.c44Items.push(new ItemModel(code, code));
                }
            }
            vm.c44CurrentCodeList = ko.observableArray([]);

            // c5_4
            vm.c54Items = ko.observableArray([]);
            var str = ['a0', 'b0', 'c0', 'd0'];
            for (var j = 0; j < 4; j++) {
                for (var i = 1; i < 51; i++) {
                    var code = i < 10 ? str[j] + '0' + i : str[j] + i;
                    vm.c54Items.push(new ItemModel(code, code));
                }
            }
            vm.c54CurrentCodeList = ko.observableArray([]);
            vm.c21Params = ko.observable();
            vm.c21Text = ko.observable();
            vm.c31Params = ko.observable();
            vm.c31Text = ko.observable();
            vm.c41Params = ko.observable();
            vm.c41Text = ko.observable();
            vm.c51Params = ko.observable();
            vm.c51Text = ko.observable();
        }

        created() {
            const vm = this;
            _.extend(window, {vm});
            vm.c21Params('c21 test data');
            vm.c21Text(vm.$i18n('KHA003_61', [vm.c21Params()]))
            vm.c31Params('c31 test data');
            vm.c31Text(vm.$i18n('KHA003_61', [vm.c31Params()]))
            vm.c41Params('c41 test data');
            vm.c41Text(vm.$i18n('KHA003_61', [vm.c41Params()]))
            vm.c51Params('c51 test data');
            vm.c51Text(vm.$i18n('KHA003_61', [vm.c51Params()]))
        }

        mounted() {
            const vm = this;
        }

        /**
         * Event on click cancel button.
         */
        public cancel(): void {
            nts.uk.ui.windows.close();
        }

        /**
         * Event on click decide button.
         */
        public decide(): void {
            alert('TODO write business logic');
            nts.uk.ui.windows.close();
        }
    }

    class ItemModel {
        code: string; //c2_8,c3_8,c4_8,c5_8,
        name: string; //c2_9,c3_9,c4_9,c5_9
        constructor(code: string, name: string) {
            this.code = code;
            this.name = name;
        }
    }

}


