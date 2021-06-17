module nts.uk.at.kha003.a {

    const API = {
        //TODO api path
    };

    @bean()
    export class ViewModel extends ko.ViewModel {
        items: KnockoutObservableArray<ItemModel>;
        currentCode: KnockoutObservable<any>;
        currentCodeList: KnockoutObservableArray<any>;
        manHour: CodeName = new CodeName('', '');
        // A6_1_1 radio button
        itemList: KnockoutObservableArray<any>;
        selectedId: KnockoutObservable<number>;
        enable: KnockoutObservable<boolean>;
        // A6_2_2 radio button
        itemListA622: KnockoutObservableArray<any>;
        selectedIdA622: KnockoutObservable<number>;
        enableA622: KnockoutObservable<boolean>;
        //A7_1
        isA71Checked: KnockoutObservable<boolean>;
        isA71Enable: KnockoutObservable<boolean>;
        //A7_2
        isA72Enable:KnockoutObservable<boolean>;
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
            vm.itemList = ko.observableArray([
                new BoxModel(1, this.$i18n('KHA003_28')),
                new BoxModel(2, this.$i18n('KHA003_29')),
            ]);
            vm.selectedId = ko.observable(1);
            vm.enable = ko.observable(true);
            vm.itemListA622 = ko.observableArray([
                new BoxModel(1, this.$i18n('KHA003_32')+this.$i18n('KHA003_35')),
                new BoxModel(2, this.$i18n('KHA003_33')+this.$i18n('KHA003_36')),
                new BoxModel(3, this.$i18n('KHA003_34')+this.$i18n('KHA003_37')),
            ]);
            vm.selectedIdA622 = ko.observable(1);
            vm.enableA622 = ko.observable(true);
            vm.isA71Checked =ko.observable(true);
            vm.isA71Enable =ko.observable(true);
            vm.isA72Enable =ko.observable(true);
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
            alert("new button is clicked")
        }

        /**
         * registration
         *
         * @author rafiqul.islam
         * */
        clickRegistrationButton() {
            const vm = this;
            alert("Registration button is clicked")
        }

        /**
         * duplicate
         *
         * @author rafiqul.islam
         * */
        clickDuplicateButton() {
            const vm = this;
            alert("duplicate button is clicked")
        }

        /**
         * click delete
         *
         * @author rafiqul.islam
         * */
        clickDeleteButton() {
            const vm = this;
            alert("delete button is clicked")
        }

        /**
         * output all
         *
         * @author rafiqul.islam
         * */
        clickOutputAllButton() {
            const vm = this;
            alert("output all button is clicked")
        }

        /**
         * click run button
         *
         * @author rafiqul.islam
         * */
        clickRunButton() {
            const vm = this;
            alert("run button is clicked")
        }

    }
    class BoxModel {
        id: number;
        name: string;
        constructor(id:any, name:any){
            var self = this;
            self.id = id;
            self.name = name;
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

