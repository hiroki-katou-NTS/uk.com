module nts.uk.hr.view.jhc002.a.viewmodel {
    import modal = nts.uk.ui.windows.sub.modal;
    import block = nts.uk.ui.block;
    import NtsGridListColumn = nts.uk.ui.NtsGridListColumn;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    export class ScreenModel {

        masterId: KnockoutObservable<string>;
        histList: KnockoutObservableArray<any>;
        selectedHistId: KnockoutObservable<any>;

        itemList: KnockoutObservableArray<ScreenItem>;

        constructor() {
            var self = this;

            // history component            
            self.height = ko.observable("200px");
            self.labelDistance = ko.observable("30px");
            self.screenMode = ko.observable(1);
            self.masterId = ko.observable("a2316878-a3a5-4362-917e-ad71d956e6c2");
            self.histList = ko.observableArray([]);
            self.selectedHistId = ko.observable();
            self.pathGet = ko.observable(`careermgmt/careerpath/getDateHistoryItem`);
            self.pathAdd = ko.observable(`careermgmt/careerpath/saveDateHistoryItem`);
            self.pathUpdate = ko.observable(`careermgmt/careerpath/updateDateHistoryItem`);
            self.pathDelete = ko.observable(`careermgmt/careerpath/removeDateHistoryItem`);
            self.getQueryResult = (res) => {
                return _.map(res, h => {
                    return { histId: h.historyId, startDate: h.startDate, endDate: h.endDate, displayText: `${h.startDate} ～ ${h.endDate}` };
                });
            };
            self.getSelectedStartDate = () => {
                let selectedHist = _.find(self.histList(), h => h.histId === self.selectedHistId());
                if (selectedHist) return selectedHist.startDate;
            };
            self.commandAdd = (masterId, histId, startDate, endDate) => {
                return {startDate: moment(startDate).format("YYYY/MM/DD")}
            };
            self.commandUpdate = (masterId, histId, startDate, endDate) => {
                return {
                    historyId: histId,
                    startDate: moment(startDate).format("YYYY/MM/DD")
                }
            };
            self.commandDelete = (masterId, histId) => {
                return {
                    historyId: histId
                };
            };
            self.delVisible = ko.observable(true);
            self.delChecked = ko.observable();
            self.afterRender = () => {
                alert("Load!");
            };
            self.afterAdd = () => {
                alert("Added");
            };
            self.afterUpdate = () => {
                alert("Updated");
            };
            self.afterDelete = () => {
                alert("Deleted");
            };


            //table 
            self.itemList = ko.observableArray([]);
            $("#fixed-table").ntsFixedTable({ height: 246, width: 600 });

        }
        startPage(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            for (let i = 1; i <= 6; i++) {
                self.itemList.push(new ScreenItem(i, '1', '1', '1', '1', '1', '1', '3', '1', '1', '1'));
            }
            dfd.resolve();
            return dfd.promise();
        }
    }

    class ScreenItem {
        val1: KnockoutObservable<String>;
        val2: KnockoutObservable<String>;
        val3: KnockoutObservable<String>;
        val4: KnockoutObservable<String>;
        val5: KnockoutObservable<String>;
        val6: KnockoutObservable<String>;
        val7: KnockoutObservable<String>;
        val8: KnockoutObservable<String>;
        val9: KnockoutObservable<String>;
        val10: KnockoutObservable<String>;
        code: KnockoutObservable<String>;
        comBoList: KnockoutObservableArray<ItemModel>;
        constructor(code: String, val1: String, val2: String, val3: String, val4: String, val5: String, val6: String, val7: String, val8: String, val9: String, val10: String) {
            var self = this;
            self.code = ko.observable(code);
            self.val1 = ko.observable(val1);
            self.val2 = ko.observable(val2);
            self.val3 = ko.observable(val3);
            self.val4 = ko.observable(val4);
            self.val5 = ko.observable(val5);
            self.val6 = ko.observable(val6);
            self.val7 = ko.observable(val7);
            self.val8 = ko.observable(val8);
            self.val9 = ko.observable(val9);
            self.val10 = ko.observable(val10);
            
            self.comBoList = ko.observableArray([
                new ItemModel('1', 'chon 1'),
                new ItemModel('2', 'chon 2'),
                new ItemModel('3', 'chon 3'),
                new ItemModel('4', 'chon 4'),
                new ItemModel('5', 'chon 5'),
                new ItemModel('6', 'chon 6')
            ]);
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
}
