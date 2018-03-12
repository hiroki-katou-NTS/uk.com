module nts.uk.at.view.kal003.a.tab {
    import windows = nts.uk.ui.windows;
    import getText = nts.uk.resource.getText;
    import block = nts.uk.ui.block;
    import model = kal003.share.model;
    import shareutils = nts.uk.at.view.kal003.share.kal003utils;

    export class DailyPerformanceTab {
        listWorkRecordExtraCon: KnockoutObservableArray<model.DailyErrorAlarmCheck> = ko.observableArray([]);
        currentCodeList: KnockoutObservableArray<string>;
        addApplication: KnockoutObservable<boolean> = ko.observable(true);
        columns: Array<any>;

        constructor(listWorkRecordExtraCon?: Array<model.DailyErrorAlarmCheck>) {
            let self = this;

            service.getDailyErrorAlarmCheck().done((data: Array<any>) => {
                if (data && data.length) {
                    let _list: Array<model.DailyErrorAlarmCheck> = _.map(data, acc => {
                        return new model.DailyErrorAlarmCheck(acc.code, acc.name, acc.classification, acc.message);
                    });
                    self.listWorkRecordExtraCon(_list);
                }
            });
            
            if (listWorkRecordExtraCon) {
                self.listWorkRecordExtraCon.removeAll();
                _.orderBy(listWorkRecordExtraCon, ['code'], ['asc']);  
                self.listWorkRecordExtraCon(listWorkRecordExtraCon);
            }
            self.columns = [
                { headerText: getText('KAL003_52'), key: 'code', width: 70 },
                {
                    // giair thich 1 chut dc k :D
                    
                    headerText: getText('KAL003_53'), key: 'classification', width: 50,
                    formatter: function(classification, record) {
                        let id = nts.uk.util.randomId();
                        
                        let $div = $("<div/>", {html: classification, id: id});
                        if (record.classification.toString() === "0") {
                            $div.addClass("bg-daily-error");
                            $div.html(getText('KAL003_110'));
                        } else if (record.classification.toString() === "1") {
                            $div.addClass("bg-daily-alarm");
                            $div.html(getText('KAL003_111'));
                        } else{
                            $div.html("");
                        }
                        setTimeout(function(){
                            let d = $("#" + id);
                            let className = d.attr('class');
                            if(!nts.uk.util.isNullOrEmpty(className)){
                                let $cell = d.parent();
                                $cell.addClass(className);
                                d.removeClass(className);    
                            }
                        }, 100);
                        return $div[0].outerHTML;
                    }
                },
                { headerText: getText('KAL003_54'), key: 'name', width: 200 },
                { headerText: getText('KAL003_55'), key: 'message', width: 300 }
            ];

            this.currentCodeList = ko.observableArray([]);

        }//end constructor
    }//en            b
}//end tab



