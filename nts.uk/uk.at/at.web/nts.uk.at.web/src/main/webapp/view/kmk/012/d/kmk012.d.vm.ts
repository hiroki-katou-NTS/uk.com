module nts.uk.at.view.kmk012.d {

    import ClosureModel = nts.uk.at.view.kmk012.a.viewmodel.ClosureModel;
    import ClosureHistoryDetailModel = nts.uk.at.view.kmk012.a.viewmodel.ClosureHistoryDetailModel;
    import DayofMonth = nts.uk.at.view.kmk012.a.service.model.DayofMonth;
    
    export module viewmodel {

        export class ScreenModel {
            closureModel : ClosureModel;
            closureHistoryModel : ClosureHistoryDetailModel;
            closureDetailModel: ClosureDetailModel;
            lstDayOfMonth: KnockoutObservableArray<DayofMonth>;
            
            constructor() {
                var self = this;
                self.closureModel = nts.uk.ui.windows.getShared("closureModel");
                self.closureHistoryModel = nts.uk.ui.windows.getShared("closureHistoryModel");
                self.closureDetailModel = new ClosureDetailModel();
                self.closureDetailModel.updateData(self.closureModel,self.closureHistoryModel);
                self.lstDayOfMonth = ko.observableArray<DayofMonth>(self.intDataMonth());
            }

            // start page
            startPage(): JQueryPromise<any> {
                var self = this;
                var dfd = $.Deferred();
                dfd.resolve();
                return dfd.promise();
            }
            
            intDataMonth(): DayofMonth[]{
                var data: DayofMonth[] = [];
                var i: number = 1 ;
                for(i=1; i<=30; i++){
                    var dayI:  DayofMonth;
                    dayI = new DayofMonth();
                    dayI.day = i;
                    dayI.name=i+"日";
                    data.push(dayI);   
                }
                var dayLast: DayofMonth;
                dayLast = new DayofMonth();
                dayLast.day = 0;
                dayLast.name = "末日";
                data.push(dayLast);  
                return data;
            }
            
            
            
        }
        
        export class ClosureDetailModel {

            closureId: KnockoutObservable<number>;

            /** The use classification. */
            useClassification: KnockoutObservable<number>;

            /** The day. */
            month: KnockoutObservable<number>;

            /** The history id. */
            historyId: KnockoutObservable<string>;

            /** The end date. */
            // 終了年月: 年月
            closureName: KnockoutObservable<string>;

            /** The start date. */
            // 開始年月: 年月
            closureDate: KnockoutObservable<number>;
            
            
            startDate: KnockoutObservable<number>;
            
            
            endDate: KnockoutObservable<number>;

            constructor() {
                this.closureId = ko.observable(0);
                this.useClassification = ko.observable(0)
                this.historyId = ko.observable('');
                this.closureName = ko.observable('');
                this.closureDate = ko.observable(0);
                this.month = ko.observable(0);
            }
            
            updateData(closureModel : ClosureModel, closureHistoryModel :ClosureHistoryDetailModel){
                this.closureId(closureModel.closureId());
                this.useClassification(closureModel.useClassification());                  
                this.month(closureModel.month());                  
                this.closureDate(closureHistoryModel.closureDate());
            }
            
            getNextClosureDate(): number{
                if(this.closureDate() == 0 || this.closureDate() == 30){
                    return 1;    
                }
                return this.closureDate()+1;
            }
            
            getStartClosureDate(): string{
                nts.uk.time.formatYearMonth(this.startDate());    
            }
        }
    }
}