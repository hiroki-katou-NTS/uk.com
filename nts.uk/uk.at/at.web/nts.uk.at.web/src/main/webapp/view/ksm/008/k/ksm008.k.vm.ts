module nts.uk.at.ksm008.i {

    import modal = nts.uk.ui.windows.sub.modal;
    import setShared = nts.uk.ui.windows.setShared;
    const API = {
        //TODO
    };

    @bean()
    export class ViewModel extends ko.ViewModel {

        scheduleAlarmCheckCond: KnockoutObservable<ScheduleAlarmCheckCond> = ko.observable(new ScheduleAlarmCheckCond());
        workPlace: CodeName = new CodeName('S0000000', 'test data');
        // mock data for J3_2,J3_3
        jScreenWorkingHour: JscreenWorkHour = new JscreenWorkHour('0000', '数字3文字', '数字3文字 mock','100');
        // I5_1 mock data
        items: KnockoutObservableArray<ItemModel>;
        // J2_1 mock data
        jItems: KnockoutObservableArray<ItemModel>;
        J4_1MOckContrain: string = "";
        backButon: string = "/view/ksm/008/a/index.xhtml";
        item: KnockoutObservable<ItemModel>;
        currentCode: KnockoutObservable<any>;
        currentCodeList: KnockoutObservableArray<any>;
        count: number = 100;
        switchOptions: KnockoutObservableArray<any>;
        simpleValue: KnockoutObservable<string>;

        constructor() {
            super();
            const self = this;
            // I5_1 mock data creation
            this.items = ko.observableArray([]);
            this.item = ko.observable(new ItemModel('0001', '基本給', 1));
            for (let i = 1; i < 100; i++) {
                this.items.push(new ItemModel('00' + i, '基本給' + i, i));
            }
            // I2_1 mock data creation
            this.jItems = ko.observableArray([]);
            for (let i = 1; i < 100; i++) {
                this.jItems.push(new ItemModel('00J' + i, '基本給 J' + i, i));
            }
            this.currentCode = ko.observable();
            this.currentCodeList = ko.observableArray([]);
            // TODO
            self.simpleValue = ko.observable("123");
        }

        created() {
            const vm = this;
            // TODO
            _.extend(window, {vm});
        }

        openModal() {
            setShared("kml001multiSelectMode", true);
            setShared("kml001selectedCodeList", [this.currentCode]);
            setShared("kml001isSelection", false);
            modal('at', '/view/kdl/001/a/index.xhtml').onClosed(() => {
            });
        }
    }

    class CommonCode {
        code: KnockoutObservable<string>;//I3_1
    }

    class ScheduleAlarmCheckCond extends CommonCode {
        conditionName: KnockoutObservable<string>;
        descriptions: KnockoutObservable<string>;// TODO
        constructor() {
            super()
            this.code = ko.observable("Dummy  coding is used when");//I3_1
            this.conditionName = ko.observable("Dummy coding is used when categorical variables ");//I3_1
            this.descriptions = ko.observable("Dummy coding is used when categorical variables ");//I4_2
        };
    }

    class CodeName {
        code: KnockoutObservable<string>;
        name: KnockoutObservable<string>;
        constructor(code: string, name: string) {
            this.code = ko.observable(code);
            this.name = ko.observable(name);
        };
    }

    class JscreenWorkHour extends CodeName {
        workHour: KnockoutObservable<string>;
        numberOfConDays: KnockoutObservable<string>;
        constructor(code: string, name: string, workHour: string ,numberOfConDays: string) {
            super(code, name)
            this.workHour = ko.observable(workHour);
            this.numberOfConDays = ko.observable(numberOfConDays);
        };
    }

    class ItemModel {
        maxNumberOfDay: Number;
        code: string;
        name: string;
        constructor(code: string, name: string, maxNumberOfDay: Number) {
            this.code = code;//I3_1
            this.name = name;//I3_1
            this.maxNumberOfDay = maxNumberOfDay;
        }
    }
}
