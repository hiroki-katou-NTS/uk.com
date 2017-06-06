module cmm044.a.viewmodel {
    import alert = nts.uk.ui.dialog.alert;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import DirtyChecker = nts.uk.ui.DirtyChecker;
    import modal = nts.uk.ui.windows.sub.modal;
    import formatym = nts.uk.time.parseYearMonthDate;

    export class ScreenModel {
        empItems: KnockoutObservableArray<PersonModel> = ko.observableArray([new PersonModel({ personId: 'A00000000000000000000000000000000001', code: 'A000000000001', name: '日通　社員1', baseDate: 20170104 })]);
        empSelectedItem: KnockoutObservable<PersonModel> = ko.observable(new PersonModel({ personId: 'A00000000000000000000000000000000001', code: 'A000000000001', name: '日通　社員1', baseDate: 20170104 }));
        tabs: KnockoutObservableArray<nts.uk.ui.NtsTabPanelModel>;
        selectedTab: KnockoutObservable<string>;
        simpleValue: KnockoutObservable<string>;
        simpleValue1: KnockoutObservable<string>;
        itemList: KnockoutObservableArray<any>;
        selectedId: KnockoutObservable<number>;
        date: KnockoutObservable<string>;
        date1: KnockoutObservable<string>;
        

        histItems: KnockoutObservableArray<ListModel> = ko.observableArray([new ListModel({ historyId: 'NEW', startDate: 20170104, endDate: 99991201 }),
            new ListModel({ historyId: 'NEW', startDate: 20160104, endDate: 20170103 })
        ]);
        histSelectedItem: KnockoutObservable<ListModel> = ko.observable(new ListModel({ historyId: 'NEW', startDate: 20170104, endDate: 99991201 }));

        constructor() {
            let self = this;
            self.simpleValue = ko.observable("2017/01/04");
            self.simpleValue1 = ko.observable("9999/12/01");
            self.date = ko.observable('20000101');
            self.date1 = ko.observable('99990112');
            self.tabs = ko.observableArray([
                { id: 'tab-1', title: '就業承認', content: '.tab-content-1', enable: ko.observable(true), visible: ko.observable(true) },
                { id: 'tab-2', title: '人事承認', content: '.tab-content-2', enable: ko.observable(true), visible: ko.observable(true) },
                { id: 'tab-3', title: '給与承認', content: '.tab-content-3', enable: ko.observable(true), visible: ko.observable(true) },
                { id: 'tab-4', title: '経理承認', content: '.tab-content-4', enable: ko.observable(true), visible: ko.observable(true) }
            ]);
            self.selectedTab = ko.observable('tab-1');
            self.empSelectedItem.subscribe(v => {

                self.histItems([
                    new ListModel({ historyId: 'NEW', startDate: 50170104, endDate: 99991201 }),
                    new ListModel({ historyId: 'NEW1', startDate: 50160104, endDate: 50170103 }),
                    new ListModel({ historyId: 'NEW2', startDate: 50150104, endDate: 50160103 }),
                    new ListModel({ historyId: 'NEW3', startDate: 50140104, endDate: 50150103 }),
                    new ListModel({ historyId: 'NEW4', startDate: 50130104, endDate: 50140103 }),
                    new ListModel({ historyId: 'NEW5', startDate: 50120104, endDate: 50130103 }),
                    new ListModel({ historyId: 'NEW6', startDate: 50110104, endDate: 50120103 }),
                    new ListModel({ historyId: 'NEW7', startDate: 50100104, endDate: 50110103 }),
                    new ListModel({ historyId: 'NEW8', startDate: 50090104, endDate: 50100103 }),

                    new ListModel({ historyId: 'NEW9', startDate: 40170104, endDate: 49991201 }),
                    new ListModel({ historyId: 'NEW10', startDate: 40160104, endDate: 40170103 }),
                    new ListModel({ historyId: 'NEW11', startDate: 40150104, endDate: 40160103 }),
                    new ListModel({ historyId: 'NEW12', startDate: 40140104, endDate: 40150103 }),
                    new ListModel({ historyId: 'NEW13', startDate: 40130104, endDate: 40140103 }),
                    new ListModel({ historyId: 'NEW14', startDate: 40120104, endDate: 40130103 }),
                    new ListModel({ historyId: 'NEW15', startDate: 40110104, endDate: 40120103 }),
                    new ListModel({ historyId: 'NEW17', startDate: 40100104, endDate: 40110103 }),
                    new ListModel({ historyId: 'NEW18', startDate: 40090104, endDate: 40100103 }),

                    new ListModel({ historyId: 'NEW25', startDate: 30120104, endDate: 30130103 }),
                    new ListModel({ historyId: 'NEW26', startDate: 30110104, endDate: 30120103 }),
                    new ListModel({ historyId: 'NEW27', startDate: 30100104, endDate: 30110103 }),
                    new ListModel({ historyId: 'NEW28', startDate: 30090104, endDate: 30100103 }),

                    new ListModel({ historyId: 'NEW35', startDate: 20120104, endDate: 20130103 }),
                    new ListModel({ historyId: 'NEW36', startDate: 20110104, endDate: 20120103 }),
                    new ListModel({ historyId: 'NEW74', startDate: 20100104, endDate: 20110103 }),
                    new ListModel({ historyId: 'NEW48', startDate: 20090104, endDate: 20100103 })
                ]);
                self.histSelectedItem(self.histItems()[0]);

            });
            self.itemList = ko.observableArray([
                new BoxModel(0, '代理'),
                new BoxModel(1, 'パス'),
                new BoxModel(2, '設定しない(待ってもらう)')
            ]);
            self.selectedId = ko.observable(0);

            self.start();
        }
        start() {
            let self = this;

            self.empItems.removeAll();

            // demo data
            _.range(20).map(i => {
                i++;
                if (i < 10) {
                    self.empItems.push(new PersonModel({
                        personId: 'A0000000000000000000000000000000000' + i,
                        code: 'A00000000' + i,
                        name: '日通　社員' + i,
                        baseDate: 20170105
                    }));
                } else {
                    self.empItems.push(new PersonModel({
                        personId: 'A0000000000000000000000000000000000' + i,
                        code: 'A0000000' + i,
                        name: '日通　社員' + i,
                        baseDate: 20170105
                    }));
                }
            });

            // select first record;
            self.empSelectedItem(self.empItems()[0]);

            return $.Deferred().resolve(true).promise();
        }
        openDDialog() {
            let self = this;
            nts.uk.ui.windows.sub.modal('/view/cmm/044/b/index.xhtml', { title: '代行リスト', height: 550, width: 1050, dialogClass: 'no-close' }).onClosed(function(): any {
            });
        }

    }



    interface IPersonModel {
        personId: string;
        code: string;
        name: string;
        baseDate?: number;
    }

    class PersonModel {
        personId: string;
        code: string;
        name: string;
        baseDate: number;

        constructor(param: IPersonModel) {
            this.personId = param.personId;
            this.code = param.code;
            this.name = param.name;
            this.baseDate = param.baseDate || 20170104;
        }
    }

    interface IListModel {
        historyId: string;
        startDate: number;
        endDate: number;
    }
    class BoxModel {
        id: number;
        name: string;
        constructor(id, name) {
            var self = this;
            self.id = id;
            self.name = name;
        }
    }
    class ListModel {
        historyId: string;
        startDate: number;
        endDate: number;
        text: string;
        isMaxDate: boolean = false;

        constructor(param: IListModel) {
            this.historyId = param.historyId;
            this.endDate = param.endDate;
            this.startDate = param.startDate;

            this.update();
        }

        update() {
            let sDate = formatym(this.startDate);
            let endDate = formatym(this.endDate);
            this.text = moment.utc([sDate.year, sDate.month - 1, sDate.date]).format("YYYY/MM/DD") + " ~ " + moment.utc([endDate.year, endDate.month - 1, endDate.date]).format("YYYY/MM/DD");
        }
    }

}