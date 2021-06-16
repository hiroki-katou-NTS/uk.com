module nts.uk.at.kha003.a {

    const API = {};

    @bean()
    export class ViewModel extends ko.ViewModel {
        items: KnockoutObservableArray<ItemModel>;
        currentCode: KnockoutObservable<any>;
        currentCodeList: KnockoutObservableArray<any>;
        manHour: CodeName = new CodeName('', '');
        constructor() {
            super();
            const vm = this;
            vm.currentCode = ko.observable();
            vm.currentCodeList = ko.observableArray([]);
            vm.items = ko.observableArray([]);
            for (var i = 1; i < 51; i++) {
                vm.items.push(new ItemModel('code' + i, 'name' + i));
            }
            vm.manHour.code('111');
            vm.manHour.name('name');
        }

        created() {
            const vm = this;
            _.extend(window, {vm});
        }

        mounted() {
            const vm = this;
        }

        /**
         * create new button
         *
         * @author rafiqul.islam
         * */
        clickNewButton() {
            const vm = this;
            alert("new button is cliced")
        }

        /**
         * registration
         *
         * @author rafiqul.islam
         * */
        clickRegistrationButton() {
            const vm = this;
            alert("Registration button is cliced")
        }

        /**
         * duplicate
         *
         * @author rafiqul.islam
         * */
        clickDuplicateButton() {
            const vm = this;
            alert("duplicate button is cliced")
        }

        /**
         * click delete
         *
         * @author rafiqul.islam
         * */
        clickDeleteButton() {
            const vm = this;
            alert("delete button is cliced")
        }

        /**
         * output all
         *
         * @author rafiqul.islam
         * */
        clickOutputAllButton() {
            const vm = this;
            alert("output all button is cliced")
        }

    }

    class CodeName {
        /** コード */
        code: KnockoutObservable<string>;
        /** 名称 */
        name: KnockoutObservable<string>;

        constructor(code: string, name: string) {
            this.code = ko.observable(code);
            this.name = ko.observable(name);
        };
    }

    class ItemModel {
        code: string;
        name: string;

        constructor(code: string, name: string) {
            this.code = code;
            this.name = name;
        }
    }
}

