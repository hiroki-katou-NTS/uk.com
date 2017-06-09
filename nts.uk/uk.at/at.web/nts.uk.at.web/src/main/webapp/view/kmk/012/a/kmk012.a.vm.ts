module nts.uk.at.view.kmk012.a {

    import ClosureHistoryFindDto = service.model.ClosureHistoryFindDto;
    import DayofMonth = service.model.DayofMonth;
    import ClosureDto = service.model.ClosureDto;
    import ClosureHistoryMDto = service.model.ClosureHistoryMDto;
    export module viewmodel {

        export class ScreenModel {
            lstClosureHistory: KnockoutObservableArray<ClosureHistoryFindDto>;
            closureModel: ClosureModel;
            useClassification: KnockoutObservableArray<any>;
            lstDayOfMonth: KnockoutObservableArray<DayofMonth>;
            columnsLstClosureHistory: KnockoutObservableArray<any>;
            selectCodeLstClosure: KnockoutObservable<number>;
            selectCodeLstClosureHistory: KnockoutObservable<string>;
            name: KnockoutObservable<string>;
            day: KnockoutObservable<number>;
            
            
            constructor() {
                var self = this;
                self.lstClosureHistory = ko.observableArray<ClosureHistoryFindDto>([]);
                self.closureModel = new ClosureModel();
                 self.columnsLstClosureHistory = ko.observableArray([
                    { headerText: 'コード', prop: 'id', width: 120 },
                    { headerText: '名称', prop: 'name', width: 120 }
                ]);
                
                self.useClassification = ko.observableArray([
                    { code: '1', name: nts.uk.resource.getText("KMK012_3")},
                    { code: '0', name: nts.uk.resource.getText("KMK012_4") }
                ]);
                self.selectCodeLstClosure = ko.observable(0);
                self.selectCodeLstClosureHistory = ko.observable('');
                self.name = ko.observable('MINH ANH');
                self.lstDayOfMonth = ko.observableArray<DayofMonth>(self.intDataMonth());
                self.day = ko.observable(0);
            }

            // start page
            startPage(): JQueryPromise<any> {
                var self = this;
                var dfd = $.Deferred();
                service.getAllClosureHistory().done(function(data) {
                    var dataRes: ClosureHistoryFindDto[] = [];
                    for (var item: ClosureHistoryFindDto of data) {
                        var dataI: ClosureHistoryFindDto = new ClosureHistoryFindDto();
                        dataI.id = item.id; 
                        dataI.name = item.name;
                        dataI.updateData();
                        dataRes.push(dataI);
                    }
                   self.lstClosureHistory(dataRes);
                   self.selectCodeLstClosure(data[0].id);
                   self.detailClosure(data[0].id);
                });
                dfd.resolve();
                return dfd.promise();
            }
            
            
            detailClosure(closureId: number){
                var self = this;
                service.detailClosure(closureId).done(function(data){
                    self.closureModel.updateData(data);
                });
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
            
            saveClosureHistory(): void {
                var self = this;
            }
            
        }
        
            
        export class ClosureHistoryModel {

            id: KnockoutObservable<number>;
            name: KnockoutObservable<string>;

            constructor() {
                this.id = ko.observable(0);
                this.name = ko.observable('');
            }

            updateDate(dto: ClosureHistoryFindDto) {
                this.id(dto.id);
                this.name(dto.name);
            }
        }

        export class ClosureModel {
            /** The closure id. */
            closureId: KnockoutObservable<number>;

            /** The use classification. */
            useClassification: KnockoutObservable<number>;

            /** The day. */
            month: KnockoutObservable<number>;
            
            
            closureHistories: KnockoutObservableArray<ClosureHistoryMDto>;

            constructor() {
                this.closureId = ko.observable(0);
                this.useClassification = ko.observable(0)
                this.month = ko.observable(0);
                this.closureHistories = ko.observableArray<ClosureHistoryMDto>([]);
            }

            updateData(dto: ClosureDto) {
                this.closureId(dto.closureId);
                this.useClassification(dto.useClassification);
                this.month(dto.month);
                this.closureHistories([]);
                var dataRes : ClosureHistoryMDto[] = [];
                for(var history :ClosureHistoryMDto of dto.closureHistories){
                     var dataI: ClosureHistoryMDto = new ClosureHistoryMDto();
                    dataI.historyId = history.historyId; 
                    dataI.closureId = history.closureId;
                    dataI.endDate = history.endDate;
                    dataI.startDate = history.startDate;
                    dataI.updateData();
                    dataRes.push(dataI);
                }
                console.log(dto);
                this.closureHistories(dataRes);
            }
        }


    }
}