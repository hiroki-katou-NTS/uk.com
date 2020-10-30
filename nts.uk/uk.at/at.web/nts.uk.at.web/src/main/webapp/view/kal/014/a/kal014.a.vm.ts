module nts.uk.at.ksm008.a {
    import getText = nts.uk.resource.getText;
    const PATH_API = {}

    @bean()
    export class KSM008AViewModel extends ko.ViewModel {

        backButon: string = "#";
        gridItems: KnockoutObservableArray<GridItem> = ko.observableArray([]);
        currentCode: KnockoutObservable<string> = ko.observable(null);
        alarmPattern: AlarmPattern = new AlarmPattern('', '');
        tabs: KnockoutObservableArray<nts.uk.ui.NtsTabPanelModel>;
        selectedTab: KnockoutObservable<string>;

        constructor(props: any) {
            super();
            const vm  = this;
            vm.tabs = ko.observableArray([
                {id: 'tab-1', title: getText('KAL014_17'), content: '.tab-content-1', enable: ko.observable(true), visible: ko.observable(true)},
                {id: 'tab-2', title: getText('KAL014_18'), content: '.tab-content-2', enable: ko.observable(true), visible: ko.observable(true)},
                {id: 'tab-3', title: getText('KAL014_19'), content: '.tab-content-3', enable: ko.observable(true), visible: ko.observable(true)}
            ]);
            vm.selectedTab = ko.observable('tab-1');
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

    class AlarmPattern{
        /** コード */
        code: KnockoutObservable<string>;
        /** 名称 */
        name: KnockoutObservable<string>;

        constructor(code: string, name: string) {
            this.code = ko.observable(code);
            this.name = ko.observable(name);
        };
    }
}