module nts.uk.at.view.kal003.a.tab {
    import windows = nts.uk.ui.windows;
    import getText = nts.uk.resource.getText;
    import block = nts.uk.ui.block;
    import model = kal003.share.model;
    import shareutils = nts.uk.at.view.kal003.share.kal003utils;

    export class DailyPerformanceTab {
        listWorkRecordExtraCon: KnockoutObservableArray<model.DailyErrorAlarmCheck> = ko.observableArray([
            new model.DailyErrorAlarmCheck('S001', 'name S001', model.ERROR_CLASSIFICATION.ERROR, 'message s001'),
            new model.DailyErrorAlarmCheck('S002', 'name S002', model.ERROR_CLASSIFICATION.ALARM, 'message s002'),
            new model.DailyErrorAlarmCheck('S003', 'name S003', model.ERROR_CLASSIFICATION.OTHER, 'message s003'),
            new model.DailyErrorAlarmCheck('S004', 'name S004', model.ERROR_CLASSIFICATION.ALARM, 'message s004')
        ]);
        currentCodeList: KnockoutObservableArray<string>;
        addApplication: KnockoutObservable<boolean> = ko.observable(true);
        columns: Array<any>;

        constructor(listWorkRecordExtraCon?: Array<model.DailyErrorAlarmCheck>) {
            let self = this;

            if (listWorkRecordExtraCon) {
                self.listWorkRecordExtraCon.removeAll();
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
                        $div.hide();
                        if (record.classification.toString() === "0") {
                            $div.addClass("bg-daily-error");
                        } else if (record.classification.toString() === "1") {
                            $div.addClass("bg-daily-alarm");
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


//            self.listWorkRecordExtraCon.subscribe((data: Array<model.DailyErrorAlarmCheck>) => {
//                for (var i = 1; i <= data.length; i++) {
//                    if ($("table[id='AA3_1'] tr:nth-child(" + i + ") td:nth-child(3)").text() == "ER") {
//                        $("table[id='AA3_1'] tr:nth-child(" + i + ") td:nth-child(3)").css("background-color", "red");
//                    }
//                    if ($("table[id='AA3_1'] tr:nth-child(" + i + ") td:nth-child(3)").text() == "AL") {
//                        $("table[id='AA3_1'] tr:nth-child(" + i + ") td:nth-child(3)").css("background-color", "yellow");
//                    }
//                }
//            });
            this.currentCodeList = ko.observableArray([]);
            //self.listWorkRecordExtraCon.valueHasMutated();

        }//end constructor
    }//en            b
}//end tab



