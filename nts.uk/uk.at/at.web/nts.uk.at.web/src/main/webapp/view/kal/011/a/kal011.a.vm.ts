module nts.uk.at.kal014.a {
    const PATH_API = {}

    @bean()
    export class Kal011AViewModel extends ko.ViewModel {
        alarmList: KnockoutObservableArray<AlarmPattern>;
        selectedCode: KnockoutObservable<string>;
        isEnable: KnockoutObservable<boolean>;
        isEditable: KnockoutObservable<boolean>;
        gridItems: KnockoutObservableArray<GridItem> = ko.observableArray([]);
        currentCodeList: KnockoutObservableArray<any>;

        constructor(props: any) {
            super();
            const vm = this;
            vm.alarmList = ko.observableArray([]);
            for (let i = 0; i < 11; i++) {
                vm.alarmList.push(new AlarmPattern("" + i, "基本給" + i));
            }
            vm.selectedCode = ko.observable('1');
            vm.currentCodeList = ko.observableArray([]);
        }

        created() {
            const vm = this;
            _.extend(window, {vm});

            for (let i = 0; i < 100; i++) {
                vm.gridItems.push(new GridItem("code " + i + "", "name " + i));
            }

        }

    }

    class AlarmPattern {
        code: string;
        name: string;

        constructor(code: string, name: string) {
            this.code = code;
            this.name = name;
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

    class TableItem{
        isChecked:boolean;
        categoryId:any
        categoryName:any;
        startDate:any;
        endDate:any;

        constructor(isChecked: boolean, categoryId: any,categoryName: any,startDate: any,endDate: any,) {
            this.isChecked = isChecked;
            this.name = name;
            this.name = name;
            this.name = name;
        }
    }
}