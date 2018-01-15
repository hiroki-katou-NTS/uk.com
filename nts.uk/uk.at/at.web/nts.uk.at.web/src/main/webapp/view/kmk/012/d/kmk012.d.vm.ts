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
                self.closureDetailModel = new ClosureDetailModel();
                self.lstDayOfMonth = ko.observableArray<DayofMonth>(self.intDataMonth());
                self.dayMonthModel = new DayMonthModel();
                self.dayMonthChangeModel = new DayMonthChangeModel();
               
                self.closureDetailModel.month.subscribe(function(){
                    if (self.validateClient()) {
                        return;
                    }
                    self.updateDayMonthModel();
                    self.updateDayMonthChangeModel();
                });
                
                self.closureDetailModel.closureDateChange.subscribe(function() {
                    if (self.validateClient()) {
                        return;
                    }
                    self.updateDayMonthChangeModel();
                });
            }
            /**
             * start page
             */
            public startPage(): JQueryPromise<any> {
                var self = this;
                var dfd = $.Deferred();
                var input: ClosureHistoryInDto;
                input = new ClosureHistoryInDto();
                input.startDate = nts.uk.ui.windows.getShared("startDate");
                input.closureId = nts.uk.ui.windows.getShared("closureId");
                service.findByMasterClosureHistory(input).done(function(data) {
                    self.closureDetailModel.updateData(data);
                    service.getDayMonth(self.convertDayIn()).done(function(resDay) {
                        service.getDayMonthChange(self.convertDayChangeIn()).done(function(resChangeDay) {
                            self.dayMonthModel.updateData(resDay);
                            self.dayMonthChangeModel.updateData(resChangeDay);
                            dfd.resolve(self);
                        });
                    });
                });
                return dfd.promise();
            }
        
            /**
             * update day month to call service => update view model
             */
            updateDayMonthModel(): void{
                var self = this;
                service.getDayMonth(self.convertDayIn()).done(function(res) {
                    self.dayMonthModel.updateData(res);
                });
            }
            
            /**
             * update day month change to call service => update view model
             */
            updateDayMonthChangeModel(): void{
                var self = this;
                service.getDayMonthChange(self.convertDayChangeIn()).done(function(res) {
                    self.dayMonthChangeModel.updateData(res);
                });  
            }

            /**
             * init data month
             */
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
            /**
             * collect date closure day
             */
            convertDayIn(): DayMonthInDto{
                var self = this;
                var dto: DayMonthInDto = new DayMonthInDto();
                dto.month = self.closureDetailModel.month(); 
                dto.closureDate = self.closureDetailModel.closureDate();
                return dto;
            }
            
            /**
             * collect date closure change day
             */
            convertDayChangeIn(): DayMonthChangeInDto{
                var self = this;
                var dto: DayMonthChangeInDto = new DayMonthChangeInDto();
                dto.month = self.closureDetailModel.month();
                dto.closureDate = self.closureDetailModel.closureDate();
                dto.changeClosureDate = self.closureDetailModel.closureDateChange();
                return dto;
            }
            
            /**
             * collect data closure history add 
             */
            collectClosureHistoryAddDto(): ClosureHistoryAddDto {
                var self = this;
                var dto: ClosureHistoryAddDto = new ClosureHistoryAddDto();
                dto.closureId = self.closureDetailModel.closureId();
                dto.endDate = 999912;
                dto.startDate = self.closureDetailModel.month();
                dto.closureDate = self.closureDetailModel.closureDateChange();
                dto.closureName = self.closureDetailModel.closureName();
                return dto;
            }
            
            /**
             * close dialog
             */
            closeWindowns(): void {
                nts.uk.ui.windows.close();
            }
            
            /**
             * clear validate client
             */
             clearValiate(){
                $('#inpMonth').ntsError('clear');
                 
            }
            
            /**
             * validate client
             */
            validateClient(): boolean {
                $("#valueProcessingDate").ntsEditor("validate");
                
                if($('.nts-input').ntsError('hasError')) {
                    return true;
                }
                return false;
            }

            /**
             * save closure date change call service => reload page 
             */
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
                this.closureName = ko.observable('');
                this.closureDate = ko.observable(0);
                this.closureDateChange = ko.observable(0);
                this.month = ko.observable(201705);
                this.endDate = ko.observable(0);
                this.startDate = ko.observable(0);
            }

            updateData(dto: ClosureDetailDto) {
                this.closureId(dto.closureId);
                this.useClassification(dto.useClassification);
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