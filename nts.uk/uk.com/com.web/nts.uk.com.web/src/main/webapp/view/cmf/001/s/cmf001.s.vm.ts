let moment: any = window['moment'];

module nts.uk.com.view.cmf001.s.viewmodel {
    import close = nts.uk.ui.windows.close;
    import model = cmf001.share.model;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import alertError = nts.uk.ui.dialog.alertError;   
    import block = nts.uk.ui.block; 
    
    export class ScreenModel {
        dateFrom: KnockoutObservable<Date>;
        dateTo: KnockoutObservable<Date>;
        conditionList: KnockoutObservableArray<model.ImExConditonSetting>;
        executeResultLogList: KnockoutObservableArray<model.ImExExecuteResultLog> = ko.observableArray([]);
        currentConditionCode: KnockoutObservable<string> = ko.observable('');
        numberOfList: KnockoutObservable<number> = ko.observable(8);
        totalPage: KnockoutObservable<number> = ko.observable(2);
        currentPage: KnockoutObservable<number> = ko.observable(1);
        constructor() {
            var self = this;
            self.dateFrom = ko.observable(moment().day(moment().day()-2).toDate());
            self.dateTo = ko.observable(moment().day(moment().day()+1).toDate());
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

            let time = {
                start:  moment.utc(self.dateFrom()).format(),
                end: moment.utc(self.dateTo()).format()
            }
            
            service.getLogResultsList(time).done(function(data: Array<any>) {
                if (data && data.length) {
                    console.log(data);
                } else {
                }
                dfd.resolve();
            }).fail(function(error) {
                alertError(error);
                dfd.reject();
            }).always(() => {
                block.clear();
            });
            return dfd.promise();
        }
        
    }//end screenModel
}//end module