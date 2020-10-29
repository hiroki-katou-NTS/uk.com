module nts.uk.at.ksm008.a {

    const PATH_API = {}

    @bean()
    export class KSM008AViewModel extends ko.ViewModel {

        backButon: string = "#";
        gridItems: KnockoutObservableArray<GridItem> = ko.observableArray([]);
        currentCode: KnockoutObservable<string> = ko.observable(null);

        constructor(props: any) {
            super();

        }

        created() {
            const vm = this;
            for (let i = 0; i < 100; i++) {
                vm.gridItems.push(new GridItem("code " + i + "", "name " + i));
            }
        }

        mounted() {

        }

        clickNewButton() {
        }

        clickRegister() {
        }

        clickDeleteButton() {
        }
    }

    class GridItem {
        code: string
        name: string;

        constructor(code: string, name: string) {
            this.code = code;
            this.name = name;
        }
    }
}