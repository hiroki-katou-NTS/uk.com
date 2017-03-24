module cmm013.c.viewmodel {

    export class ScreenModel {
        label_002: KnockoutObservable<Labels>;
        inp_003: KnockoutObservable<string>;
        historyId: KnockoutObservable<string>;
        startDateLast: KnockoutObservable<string>;
        itemList: KnockoutObservableArray<any>;
        selectedId: KnockoutObservable<number>;
        enable: KnockoutObservable<boolean>;
        endDateUpdate: KnockoutObservable<string>;
        length: KnockoutObservable<number>;
        startDateAddNew: KnockoutObservable<string>;
        listbox: KnockoutObservableArray<model.ListHistoryDto>;
        selectedCode: KnockoutObservable<any>;
        checkDelete: KnockoutObservable<any>;


        startDateUpdate: KnockoutObservable<string>;
        historyIdUpdate: KnockoutObservable<string>;
        startDateUpdateNew: KnockoutObservable<string>;
        startDatePre: KnockoutObservable<string>;
        jobHistory: KnockoutObservable<model.ListHistoryDto>;

        constructor() {
            var self = this;
            self.label_002 = ko.observable(new Labels());
            self.inp_003 = ko.observable(null);
            self.historyId = ko.observable(null);
            self.startDateLast = ko.observable('');
            self.endDateUpdate = ko.observable('');
            self.selectedId = ko.observable(1);
            self.enable = ko.observable(true);

            self.length = ko.observable(0);
            self.startDateAddNew = ko.observable("");

            self.startDateUpdate = ko.observable(null);
            self.endDateUpdate = ko.observable(null);
            self.historyIdUpdate = ko.observable(null);
            self.startDateUpdateNew = ko.observable(null);
            self.startDatePre = ko.observable(null);
            self.jobHistory = ko.observable(null);
            self.selectedCode = ko.observable(null);
            self.checkDelete = ko.observable(null);
            self.listbox = ko.observableArray([]);

            self.itemList = ko.observableArray([
                new BoxModel(1, '最新の履歴（' + self.startDateLast() + '）から引き継ぐ  '),
                new BoxModel(2, '全員参照不可')
            ]);

        }
        closeDialog(): any{
            nts.uk.ui.windows.close();   
        }

        

        startPage(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            self.historyId(nts.uk.ui.windows.getShared('Id_13'));
            self.startDateLast(nts.uk.ui.windows.getShared('startLast'));
            self.endDateUpdate(nts.uk.ui.windows.getShared('endUpdate'));
            self.startDateUpdate(nts.uk.ui.windows.getShared('startUpdate'));
            if (self.startDateUpdate() != null) {
                self.itemList = ko.observableArray([
                    new BoxModel(1, '最新の履歴（' + self.startDateUpdate() + '）から引き継ぐ  '),
                    new BoxModel(2, '全員参照不可')
                ]);
            }
            else {
                self.itemList = ko.observableArray([
                    new BoxModel(1, '全員参照不可')
                ]);
            }

            dfd.resolve();
            return dfd.promise();
        }


        add() {
            var self = this;
            let startDateLast = new Date(self.endDateUpdate());
            let startDate = new Date(self.inp_003());
            if (startDate.getTime() < startDateLast.getTime()) {
                alert("no no no");
                return;
            }
            else {
                if (nts.uk.text.isNullOrEmpty(self.startDateLast())) {
                    var check = self.selectedId();
                } else {
                    var check = 2;
                }
                nts.uk.ui.windows.setShared('startNew', self.inp_003());
                nts.uk.ui.windows.setShared('copy_c', check, false);
                nts.uk.ui.windows.close();
            }
        }



    }

    export class Labels {
        constraint: string = 'LayoutCode';
        inline: KnockoutObservable<boolean>;
        required: KnockoutObservable<boolean>;
        enable: KnockoutObservable<boolean>;

        constructor() {
            var self = this;
            self.inline = ko.observable(true);
            self.required = ko.observable(true);
            self.enable = ko.observable(true);
        }
    }

    export class BoxModel {
        id: number;
        name: string;
        constructor(id, name) {
            var self = this;
            self.id = id;
            self.name = name;
        }
    }


    export module model {
        export class historyDto {
            startDate: string;
            endDate: string;
            historyId: string;
            constructor(startDate: string, endDate: string, historyId: string) {
                this.startDate = startDate;
                this.endDate = endDate;
                this.historyId = historyId;
            }
        }

        export class ListHistoryDto {
            companyCode: string;
            startDate: string;
            endDate: string;
            historyId: string;
            constructor(companyCode: string, startDate: string, endDate: string, historyId: string) {
                var self = this;
                self.companyCode = companyCode;
                self.startDate = startDate;
                self.endDate = endDate;
                self.historyId = historyId;
            }
        }


    }
}
