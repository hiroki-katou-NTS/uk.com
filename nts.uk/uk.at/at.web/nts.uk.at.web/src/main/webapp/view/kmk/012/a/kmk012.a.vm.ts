module nts.uk.at.view.kmk012.a {

    import ClosureHistoryFindDto = service.model.ClosureHistoryFindDto;
    export module viewmodel {

        export class ScreenModel {
            lstClosureHistory: KnockoutObservableArray<ClosureHistoryFindDto>;
            columnsLstClosureHistory: KnockoutObservableArray<any>;
            selectCodeLstClosureHistory: KnockoutObservable<number>;
            
            constructor() {
                var self = this;
                self.lstClosureHistory = ko.observableArray<ClosureHistoryFindDto>([]);
                self.selectCodeLstClosureHistory = ko.observable(0);
                 self.columnsLstClosureHistory = ko.observableArray([
                    { headerText: 'コード', prop: 'id', width: 120 },
                    { headerText: '名称', prop: 'name', width: 120 }
                ]);
            }

            // start page
            startPage(): JQueryPromise<any> {
                var self = this;
                var dfd = $.Deferred();
                service.getAllClosureHistory().done(function(data) {
                   self.lstClosureHistory(data);
                });
                dfd.resolve();
                return dfd.promise();
            }
            
            saveClosureHistory(): void {
                var self = this;
            }
            
        }
        
        export module model {
            
            export class ClosureHistoryModel{
                
                id: KnockoutObservable<number>;
                name: KnockoutObservable<string>; 
                
                constructor(){
                    this.id = ko.observable(0);
                    this.name = ko.observable('');
                }
                
                updateDate(dto: ClosureHistoryFindDto){
                    this.id(dto.id);
                    this.name(dto.name);    
                } 
            }
        }
    }
}