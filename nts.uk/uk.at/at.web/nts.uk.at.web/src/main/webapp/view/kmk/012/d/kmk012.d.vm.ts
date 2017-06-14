module nts.uk.at.view.kmk012.d {

    import DayofMonth = nts.uk.at.view.kmk012.a.service.model.DayofMonth;
    import ClosureHistoryInDto = service.model.ClosureHistoryInDto;
    import ClosureDetailDto = service.model.ClosureDetailDto;

    export module viewmodel {

        export class ScreenModel {
            closureDetailModel: ClosureDetailModel;
            lstDayOfMonth: KnockoutObservableArray<DayofMonth>;

            constructor() {
                var self = this;
                var input: ClosureHistoryInDto;
                input = new ClosureHistoryInDto();
                input.historyId = nts.uk.ui.windows.getShared("historyId");
                input.closureId = nts.uk.ui.windows.getShared("closureId");
                self.closureDetailModel = new ClosureDetailModel();
                self.lstDayOfMonth = ko.observableArray<DayofMonth>(self.intDataMonth());
                service.detailClosureHistory(input).done(function(data) {
                    self.closureDetailModel.updateData(data);
                });
            }

            intDataMonth(): DayofMonth[] {
                var data: DayofMonth[] = [];
                var i: number = 1;
                for (i = 1; i <= 30; i++) {
                    var dayI: DayofMonth;
                    dayI = new DayofMonth();
                    dayI.day = i;
                    dayI.name = i + "日";
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

            /** The history id. */
            historyId: KnockoutObservable<string>;

            /** The closure id. */
            closureId: KnockoutObservable<number>;

            /** The closure name. */
            closureName: KnockoutObservable<string>;

            /** The closure date. */
            closureDate: KnockoutObservable<number>;

            /** The use classification. */
            useClassification: KnockoutObservable<number>;

            /** The end date. */
            // 終了年月: 年月
            endDate: KnockoutObservable<number>;

            /** The start date. */
            // 開始年月: 年月
            startDate: KnockoutObservable<number>;

            /** The month. */
            month: KnockoutObservable<number>;

            constructor() {
                this.closureId = ko.observable(0);
                this.useClassification = ko.observable(0)
                this.historyId = ko.observable('');
                this.closureName = ko.observable('');
                this.closureDate = ko.observable(0);
                this.month = ko.observable(0);
                this.endDate = ko.observable(0);
                this.startDate = ko.observable(0);
            }

            updateData(dto: ClosureDetailDto) {
                this.closureId(dto.closureId);
                this.useClassification(dto.useClassification);
                this.historyId(dto.historyId);
                this.closureName(dto.closureName);
                this.closureDate(dto.closureDate);
                this.month(dto.month);
                this.endDate(dto.endDate);
                this.startDate(dto.startDate);
            }

             getDayClosureDate(): string{
                if(this.closureDate() == 0){
                    return "末日";
                }
                return this.closureDate()+"日";
            }
            getNextClosureDate(): number {
                if (this.closureDate() == 0 || this.closureDate() == 30) {
                    return 1;
                }
                return this.closureDate() + 1;
            }

            getStartClosureDate(): string {
                var startDateStr: string =  nts.uk.time.formatYearMonth(this.startDate());
                startDateStr=startDateStr+'/'+this.getNextClosureDate();
                return startDateStr; 
            }
            
            getPreviouClosureDate(): number {
                if (this.closureDate() == 0) {
                    return 1;
                }
                return this.closureDate();
            }
            
             getEndClosureDate(): string {
                var endDateStr: string =  nts.uk.time.formatYearMonth(this.endDate());
                endDateStr=endDateStr+'/'+this.getPreviouClosureDate();
                return endDateStr; 
            }
        }
    }
}