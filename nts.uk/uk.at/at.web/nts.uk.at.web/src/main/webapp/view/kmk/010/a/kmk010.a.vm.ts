module nts.uk.at.view.kmk010.a {

    import EnumConstantDto = service.model.EnumConstantDto;
    import OvertimeDto = service.model.OvertimeDto;
    import OvertimeSettingDto = service.model.OvertimeSettingDto;
    
    export module viewmodel {

        export class ScreenModel {
            calculationMethods: KnockoutObservableArray<EnumConstantDto>;
            overtimeSettingModel: OvertimeSettingModel;
            useClassification: KnockoutObservableArray<any>;

            constructor() {
                var self = this;
                self.calculationMethods = ko.observableArray<EnumConstantDto>([]);
                self.overtimeSettingModel = new OvertimeSettingModel();
            }

            /**
             * start page data 
             */
            startPage(): JQueryPromise<any> {
                var self = this;
                var dfd = $.Deferred();
                service.findByIdOvertimeSetting().done(function(dataOvertimeSetting) {
                    self.overtimeSettingModel.updateData(dataOvertimeSetting);
                    service.findAllOvertimeCalculationMethod().done(function(dataMethod) {
                        self.calculationMethods(dataMethod);
                        dfd.resolve(self);
                    });
                });
                return dfd.promise();
            }

        }
        export class OvertimeModel {
            name: KnockoutObservable<string>;
            overtime: KnockoutObservable<number>;
            overtimeNo: KnockoutObservable<number>;
            useClassification: KnockoutObservable<number>;
            constructor(){
                this.name = ko.observable('');
                this.overtime = ko.observable(0);
                this.overtimeNo = ko.observable(0);
                this.useClassification = ko.observable(0);    
            }
            
            updateData(dto: OvertimeDto){
                this.name(dto.name);
                this.overtime(dto.overtime);
                this.overtimeNo(dto.overtimeNo);
                this.useClassification(dto.useClassification);
            }
        }
        export class OvertimeSettingModel {
            note: KnockoutObservable<string>;
            calculationMethod: KnockoutObservable<number>;
            overtimes: OvertimeModel[];
            constructor() {
                this.note = ko.observable('');
                this.calculationMethod = ko.observable(0);
                this.overtimes = [];
            }
            
            updateData(dto: OvertimeSettingDto){
                this.note(dto.note);
                this.calculationMethod(dto.calculationMethod);
                this.overtimes = [];
                for(var overtime of dto.overtimes){
                    var model: OvertimeModel = new OvertimeModel();
                    model.updateData(overtime);
                    this.overtimes.push(model); 
                }
            }
        }
        
            
    }
}