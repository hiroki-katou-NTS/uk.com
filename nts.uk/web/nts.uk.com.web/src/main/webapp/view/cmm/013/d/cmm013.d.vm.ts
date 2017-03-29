module cmm013.d.viewmodel {

    export class ScreenModel {
        inp_003: KnockoutObservable<string>;
        inp_003_enable: KnockoutObservable<boolean>;
        startDateUpdate: KnockoutObservable<string>;
        endDateUpdate: KnockoutObservable<string>;
        startDateNew: KnockoutObservable<string>;
        itemList: KnockoutObservableArray<any>;
        selectedId: KnockoutObservable<number>;
        enable: KnockoutObservable<boolean>;
        length: KnockoutObservable<number>;
        listbox: KnockoutObservableArray<model.ListHistoryDto>;
        selectedCode: KnockoutObservable<any>;
        checkDelete: KnockoutObservable<any>;
        jobHistory: KnockoutObservable<model.ListHistoryDto>;
        startDateLast: KnockoutObservable<string>;
        histIdUpdate: KnockoutObservable<string>;
        constructor() {
            var self = this;
            self.inp_003 = ko.observable(null);
            self.inp_003_enable = ko.observable(true);
            self.startDateNew = ko.observable('');
            self.startDateUpdate = ko.observable('');
            self.endDateUpdate = ko.observable('');
            self.length = ko.observable(0);
            self.startDateUpdate = ko.observable(null);
            self.endDateUpdate = ko.observable(null);
            self.jobHistory = ko.observable(null);
            self.selectedCode = ko.observable(null);
            self.checkDelete = ko.observable(null);
            self.listbox = ko.observableArray([]);
            self.startDateLast = ko.observable('');
            self.histIdUpdate = ko.observable('');
            self.itemList = ko.observableArray([
                new BoxModel(1, '履歴を削除する '),
                new BoxModel(2, '履歴を修正する')
            ]);
            self.selectedId = ko.observable(2);
            self.enable = ko.observable(true);

            self.selectedId.subscribe((function(codeChanged) {
                if (codeChanged == 1) {
                    self.inp_003_enable(false);
                }
                else {
                    self.inp_003_enable(true);
                }
            }));
        }



        closeDialog(): any {
            nts.uk.ui.windows.close();
        }

        startPage(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            self.checkDelete(nts.uk.ui.windows.getShared('delete'));
            self.startDateUpdate(nts.uk.ui.windows.getShared('startUpdate'));
            self.endDateUpdate(nts.uk.ui.windows.getShared('endUpdate'));
            self.startDateLast(nts.uk.ui.windows.getShared('startDateLast'));
            self.histIdUpdate(nts.uk.ui.windows.getShared('historyIdUpdate'));
            if (self.checkDelete() == 1) {//option delete
                self.itemList = ko.observableArray([
                    new BoxModel(1, '履歴を削除する '),
                    new BoxModel(2, '履歴を修正する')
                ]);
                self.selectedId = ko.observable(2);
                self.selectedId.subscribe((function(codeChanged) {
                    if (codeChanged == 1) {
                        self.inp_003_enable(false);
                    }
                    else {
                        self.inp_003_enable(true);
                    }
                }));
            }
            if (self.checkDelete() == 2) {
                self.itemList = ko.observableArray([
                    new BoxModel(1, '履歴を修正する')
                ]);
                self.selectedId = ko.observable(1);
            }
            self.inp_003(self.startDateUpdate());
            dfd.resolve();
            return dfd.promise();
        }

        positionHis() {
            var self = this;
            if (self.selectedId() == 2 && self.checkDelete() == 1) {
                if (self.inp_003() >= self.endDateUpdate() || self.inp_003() <= self.startDateUpdate()) {
                    alert("Start Date sai");
                    return;
                }
            }
            var dfd = $.Deferred<any>();

            if (self.selectedId() == 1 && self.checkDelete() == 1) {
//                nts.uk.ui.windows.setShared('startUpdateNew', '', true);
//                nts.uk.ui.windows.setShared('check_d', '1', true);
                var listHist = new service.model.ListHistoryDto(self.startDateUpdate(), '', self.endDateUpdate(), self.histIdUpdate());

                var checkDelete = '1';
                var checkUpdate = '0';

            } else {
//                nts.uk.ui.windows.setShared('startUpdateNew', self.inp_003(), true);
//                nts.uk.ui.windows.setShared('check_d', '2', true);
                checkDelete = '0';
                var listHist = new service.model.ListHistoryDto(self.startDateUpdate(), self.inp_003(), self.endDateUpdate(), self.histIdUpdate());
                if (self.startDateUpdate() == self.startDateLast()) {

                    checkUpdate = '2';
                } else {
                    checkUpdate = '1';
                }

            }
            var updateHandler = new service.model.UpdateHandler(listHist, checkUpdate, checkDelete);
            service.updateHist(updateHandler).done(function() {
                alert('update thanh cong');
                nts.uk.ui.windows.setShared('updateFinish', true, true);
                nts.uk.ui.windows.close();
            }).fail(function(res) {
                dfd.reject(res);
            })
        }
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


}
