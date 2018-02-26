module nts.uk.com.view.cmf001.s.viewmodel {
    import close = nts.uk.ui.windows.close;
    import model = cmf001.share.model;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    export class ScreenModel {
        dateFrom: KnockoutObservable<string>;
        dateTo: KnockoutObservable<string>;
        conditionList: KnockoutObservableArray<model.ImExConditonSetting>;
        executeResultLogList: KnockoutObservableArray<model.ImExExecuteResultLog> = ko.observableArray([]);
        currentConditionCode: KnockoutObservable<string> = ko.observable('');
        numberOfList: KnockoutObservable<number> = ko.observable(8);
        totalPage: KnockoutObservable<number> = ko.observable(2);
        currentPage: KnockoutObservable<number> = ko.observable(1);
        constructor() {
            var self = this;
            self.dateFrom = ko.observable('20000101');
            self.dateTo = ko.observable('20000101');
            self.conditionList = ko.observableArray([
                new model.ImExConditonSetting('001', 'Import Setting 1'),
                new model.ImExConditonSetting('002', 'Import Setting 2'),
                new model.ImExConditonSetting('003', 'Import Setting 3'),
                new model.ImExConditonSetting('004', 'Import Setting 4'),
                new model.ImExConditonSetting('005', 'Import Setting 5'),
                new model.ImExConditonSetting('006', 'Import Setting 5'),
                new model.ImExConditonSetting('007', 'Import Setting 5')
            ]);
            for (let i = 0; i < 100; i++) {
                self.executeResultLogList.push(
                    new model.ImExExecuteResultLog(
                        '100000001'
                        , 'AAAAAAAA'
                        , '2017/3/25 13:25'
                        , 'AAAAAAAA'
                        , '3510'
                        , '10'
                        , 'test.csv'
                        , '1'
                    )); 
            }

            $("#fixed-table").ntsFixedTable({ height: 200 });

        }

        start(): JQueryPromise<any> {
            let self = this;
            var dfd = $.Deferred();
            dfd.resolve();
            return dfd.promise();
        }
    }//end screenModel
}//end module