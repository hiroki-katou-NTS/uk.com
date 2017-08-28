module nts.uk.at.view.ksm001.f {

    import CommonGuidelineSettingDto = service.model.CommonGuidelineSettingDto;
    import UsageSettingDto = nts.uk.at.view.ksm001.a.service.model.UsageSettingDto;
    import UsageSettingModel = nts.uk.at.view.ksm001.a.viewmodel.UsageSettingModel;
    import EstimatedConditionDto = service.model.EstimatedConditionDto;
    import EstimatedAlarmColorDto = service.model.EstimatedAlarmColorDto;
    import ReferenceConditionDto = service.model.ReferenceConditionDto;

    export module viewmodel {

        export class ScreenModel {
            commonGuidelineSettingModel: CommonGuidelineSettingModel;
           
            constructor() {
                var self = this;
                self.commonGuidelineSettingModel = new CommonGuidelineSettingModel();
            }

            /**
            * start page data 
            */
            public startPage(): JQueryPromise<any> {
                var self = this;
                var dfd = $.Deferred();
                service.findCommonGuidelineSetting().done(function(data){
                    if(data){
                        self.commonGuidelineSettingModel.updateData(data);    
                    }
                    dfd.resolve();
                });
                return dfd.promise();
            }
            
            /**
             * function on click save CommonGuidelineSetting
             */
            public saveCommonGuidelineSetting(): void {
                var self = this;

                nts.uk.ui.block.invisible();

                service.saveCommonGuidelineSetting(self.commonGuidelineSettingModel.toDto()).done(function() {
                    // show message 15
                    nts.uk.ui.dialog.info({ messageId: "Msg_15" }).then(function() {
                        // close windows
                        nts.uk.ui.windows.close();
                    });
                }).fail(function(error) {
                    nts.uk.ui.dialog.alertError(error);
                }).always(function() {
                    nts.uk.ui.block.clear();
                });
            }

            /**
             * Event on click cancel button.
             */
            public cancelCommonGuidelineSetting(): void {
                nts.uk.ui.windows.close();
            }

        }     
        
        
        export class ReferenceConditionModel {
            yearlyDisplayCondition: KnockoutObservable<number>;
            monthlyDisplayCondition: KnockoutObservable<number>;
            alarmCheckCondition: KnockoutObservable<number>;
            lstEstimateCondition: KnockoutObservableArray<EstimatedConditionDto>;

            constructor() {
                this.yearlyDisplayCondition = ko.observable(1);
                this.monthlyDisplayCondition = ko.observable(1);
                this.alarmCheckCondition = ko.observable(1);
                this.lstEstimateCondition = ko.observableArray([
                    { code: 1, name: "条件1" },
                    { code: 2, name: "条件2" },
                    { code: 3, name: "条件3" },
                    { code: 4, name: "条件4" },
                    { code: 5, name: "条件5" },
                ]);
            }
            
            updateData(dto: ReferenceConditionDto) {
                this.yearlyDisplayCondition(dto.yearlyDisplayCondition);
                this.monthlyDisplayCondition(dto.monthlyDisplayCondition);
                this.alarmCheckCondition(dto.alarmCheckCondition);
            }
            
            toDto(): ReferenceConditionDto {
                var dto: ReferenceConditionDto = 
                    {
                        yearlyDisplayCondition: this.yearlyDisplayCondition(),
                        monthlyDisplayCondition: this.monthlyDisplayCondition(),
                        alarmCheckCondition: this.alarmCheckCondition()
                    };
                return dto;
            }
        }
        export class CommonGuidelineSettingModel{
            estAlarmColors1st:   KnockoutObservable<string>;  
            estAlarmColors2nd:   KnockoutObservable<string>;  
            estAlarmColors3rd:   KnockoutObservable<string>;  
            estAlarmColors4th:   KnockoutObservable<string>;  
            estAlarmColors5th:   KnockoutObservable<string>;

            /** The estimate time. */
            estimateTime: ReferenceConditionModel;

            /** The estimate price. */
            estimatePrice: ReferenceConditionModel;

            /** The estimate number of days. */
            estimateNumberOfDays: ReferenceConditionModel;
            
            
            
            constructor() {
                this.estAlarmColors1st = ko.observable('#ff0000');
                this.estAlarmColors2nd = ko.observable('#ff0000');
                this.estAlarmColors3rd = ko.observable('#ff0000');
                this.estAlarmColors4th = ko.observable('#ff0000');
                this.estAlarmColors5th = ko.observable('#ff0000');
                this.estimateTime = new ReferenceConditionModel();
                this.estimatePrice = new ReferenceConditionModel();
                this.estimateNumberOfDays = new ReferenceConditionModel();
            }
            
            updateData(dto: CommonGuidelineSettingDto) {
                for (var color of dto.alarmColors) {
                    switch(color.guidelineCondition){
                        case 1:
                        this.estAlarmColors1st(color.color);
                        break;    
                        case 2:
                        this.estAlarmColors2nd(color.color);
                        break;    
                        case 3:
                        this.estAlarmColors3rd(color.color);
                        break;    
                        case 4:
                        this.estAlarmColors4th(color.color);
                        break;    
                        case 5:
                        this.estAlarmColors5th(color.color);
                        break;    
                    }
                }
                this.estimateTime.updateData(dto.estimateTime);
                this.estimatePrice.updateData(dto.estimatePrice);
                this.estimateNumberOfDays.updateData(dto.estimateNumberOfDays);
            }
            
            toDto(): CommonGuidelineSettingDto {
                var alarmColors: EstimatedAlarmColorDto[] = [];
                alarmColors.push(
                    { guidelineCondition: 1, color: this.estAlarmColors1st() });
                alarmColors.push(
                    { guidelineCondition: 2, color: this.estAlarmColors2nd() });
                alarmColors.push(
                    { guidelineCondition: 3, color: this.estAlarmColors3rd() });
                alarmColors.push(
                    { guidelineCondition: 4, color: this.estAlarmColors4th() });
                alarmColors.push(
                    { guidelineCondition: 5, color: this.estAlarmColors5th() });
                var dto: CommonGuidelineSettingDto =
                    {
                        alarmColors: alarmColors,
                        estimateTime: this.estimateTime.toDto(),
                        estimatePrice: this.estimatePrice.toDto(),
                        estimateNumberOfDays: this.estimateNumberOfDays.toDto()
                    };
                return dto;
            }
        }
    }
}