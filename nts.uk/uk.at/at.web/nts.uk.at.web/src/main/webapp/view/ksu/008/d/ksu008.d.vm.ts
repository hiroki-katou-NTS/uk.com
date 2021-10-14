module nts.uk.at.ksu008.d {
    const API = {};

    @bean()
    export class ViewModel extends ko.ViewModel {

        model: Model = new Model();
        comBOItemList: KnockoutObservableArray<ItemModel>;
        itemList: KnockoutObservableArray<any>;

        constructor() {
            super();
            const self = this;
            self.itemList = ko.observableArray([
                new BoxModel(1, self.$i18n('Enum_RoundingMethod_TRUNCATION')),//D1_4_1
                new BoxModel(2, self.$i18n('Enum_RoundingMethod_ROUND_UP')),//D1_4_2
                new BoxModel(3, self.$i18n('Enum_RoundingMethod_DOWN_4_UP_5'))//D1_4_3
            ]);
            self.comBOItemList = ko.observableArray([
                new ItemModel('1', 'test on'),
                new ItemModel('2', 'test 2'),
                new ItemModel('3', 'test 3')
            ]);

        }

        created() {
            const vm = this;
            _.extend(window, {vm});

        }

        mounted() {
            const vm = this;
        }

        closeDialog(): void {
            nts.uk.ui.windows.close();
        }

    }

    class ItemModel {
        code: string;
        name: string;

        constructor(code: string, name: string) {
            this.code = code;
            this.name = name;
        }
    }

    class BoxModel {
        id: number;
        name: string;
        constructor(id, name){
            var self = this;
            self.id = id;
            self.name = name;
        }
    }

    class Model {
        d22Checked:KnockoutObservable<boolean>;
        d12:any;
        selectedCode:KnockoutObservable<number>;
        selectedId:KnockoutObservable<number>;

        constructor() {
            var self = this
            self.d12 = ko.observable("Sample001");
            self.selectedCode = ko.observable(1);
            self.selectedId = ko.observable(1);
            self.d22Checked=ko.observable(true);
        }
    }
}


