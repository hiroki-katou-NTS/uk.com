module nts.uk.at.view.kmk012.d {

    import DayofMonth = nts.uk.at.view.kmk012.a.service.model.DayofMonth;
    import ClosureHistoryInDto = service.model.ClosureHistoryInDto;
    import ClosureDetailDto = service.model.ClosureDetailDto;
    import DayMonthInDto = service.model.DayMonthInDto;
    import DayMonthDto = service.model.DayMonthDto;
    import DayMonthChangeInDto = service.model.DayMonthChangeInDto;
    import DayMonthChangeDto = service.model.DayMonthChangeDto;
    import ClosureHistoryAddDto = service.model.ClosureHistoryAddDto;

    export module viewmodel {

        export class ScreenModel {
            closureDetailModel: ClosureDetailModel;
            lstDayOfMonth: KnockoutObservableArray<DayofMonth>;
            dayMonthModel: DayMonthModel;
            dayMonthChangeModel: DayMonthChangeModel;
            

            constructor() {
                var self = this;
                var input: ClosureHistoryInDto;
                input = new ClosureHistoryInDto();
                input.historyId = nts.uk.ui.windows.getShared("historyId");
                input.closureId = nts.uk.ui.windows.getShared("closureId");
                self.closureDetailModel = new ClosureDetailModel();
                self.lstDayOfMonth = ko.observableArray<DayofMonth>(self.intDataMonth());
                self.dayMonthModel = new DayMonthModel();
                self.dayMonthChangeModel = new DayMonthChangeModel();
                service.detailClosureHistory(input).done(function(data) {
                    self.closureDetailModel.updateData(data);
                    self.updateDayMonthModel();
                    self.updateDayMonthChangeModel();
                });
                self.closureDetailModel.month.subscribe(function(){
                    self.updateDayMonthModel();
                    self.updateDayMonthChangeModel();
                });
                
                self.closureDetailModel.closureDateChange.subscribe(function() {
                    self.updateDayMonthChangeModel();
                });
            }
            
            updateDayMonthModel(): void{
                var self = this;
                service.getDayMonth(self.convertDayIn()).done(function(res) {
                    self.dayMonthModel.updateData(res);
                });
            }
            
            updateDayMonthChangeModel(): void{
                var self = this;
                service.getDayMonthChange(self.convertDayChangeIn()).done(function(res) {
                    self.dayMonthChangeModel.updateData(res);
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
            
            convertDayIn(): DayMonthInDto{
                var self = this;
                var dto: DayMonthInDto = new DayMonthInDto();
                dto.month = self.closureDetailModel.month(); 
                dto.closureDate = self.closureDetailModel.closureDate();
                return dto;
            }
            
            convertDayChangeIn(): DayMonthChangeInDto{
                var self = this;
                var dto: DayMonthChangeInDto = new DayMonthChangeInDto();
                dto.month = self.closureDetailModel.month();
                dto.closureDate = self.closureDetailModel.closureDate();
                dto.changeClosureDate = self.closureDetailModel.closureDateChange();
                return dto;
            }
            
            collectClosureHistoryAddDto(): ClosureHistoryAddDto {
                var self = this;
                var dto: ClosureHistoryAddDto = new ClosureHistoryAddDto();
                dto.closureId = self.closureDetailModel.closureId();
                dto.endDate = 999912;
                dto.startDate = self.closureDetailModel.month();
                dto.closureDate = self.closureDetailModel.closureDateChange();
                return dto;
            }
            
            closeWindowns(): void {
                nts.uk.ui.windows.close();
            }
            
             clearValiate(){
                $('#inpMonth').ntsError('clear');
                 
            }
            
            validateClient(): boolean {
                $("#valueProcessingDate").ntsEditor("validate");
                
                if($('.nts-input').ntsError('hasError')) {
                    return true;
                }
                return false;
            }

            saveChangeClosureDate(): void {
                var self = this;
                if(self.validateClient()){
                    return;    
                }
                service.addClosureHistory(self.collectClosureHistoryAddDto()).done(function(){
                    nts.uk.ui.dialog.info({ messageId: "Msg_15" }).then(function() {
                        self.closeWindowns();
                    });
                    
                }).fail(function(error){
                   nts.uk.ui.dialog.alertError(error);
                });
                  
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
            
            closureDateChange: KnockoutObservable<number>;

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
                this.closureDateChange = ko.observable(0);
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
                this.closureDateChange(dto.closureDate);
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
        }
        
        export class DayMonthModel {
            
            /** The begin day. */
            beginDay: KnockoutObservable<string>;

            /** The end day. */
            endDay: KnockoutObservable<string>;
            
            constructor(){
                this.beginDay = ko.observable('');    
                this.endDay = ko.observable('');    
            }
            
            updateData(dto: DayMonthDto){
                this.beginDay(dto.beginDay);
                this.endDay(dto.endDay);
            }
            
        }
        
        export class DayMonthChangeModel{
            
            beforeClosureDate: DayMonthModel;
            
            afterClosureDate: DayMonthModel;
            
            constructor() {
                this.beforeClosureDate = new DayMonthModel();
                this.afterClosureDate = new DayMonthModel();
            }
            
            updateData(dto: DayMonthChangeDto){
                this.beforeClosureDate.updateData(dto.beforeClosureDate);    
                this.afterClosureDate.updateData(dto.afterClosureDate);    
            }
        }
    }
        
}