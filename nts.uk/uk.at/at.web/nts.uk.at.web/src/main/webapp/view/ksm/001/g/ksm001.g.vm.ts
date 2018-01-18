module nts.uk.at.view.ksm001.g {

    import CommonGuidelineSettingDto = service.model.CommonGuidelineSettingDto;
    import UsageSettingDto = nts.uk.at.view.ksm001.a.service.model.UsageSettingDto;
    import UsageSettingModel = nts.uk.at.view.ksm001.a.viewmodel.UsageSettingModel;
    import EstimatedConditionDto = service.model.EstimatedConditionDto;
    import EstimatedAlarmColorDto = service.model.EstimatedAlarmColorDto;
    import ReferenceConditionDto = service.model.ReferenceConditionDto;

    export module viewmodel {

        export class ScreenModel {
//            commonGuidelineSettingModel: CommonGuidelineSettingModel;
           
            constructor() {
                var self = this;
//                self.commonGuidelineSettingModel = new CommonGuidelineSettingModel();
            }

            /**
            * start page data 
            */
            public startPage(): JQueryPromise<any> {
                var self = this;
                var dfd = $.Deferred();

                return dfd.promise();
            }
            
            /**
             * function on click save CommonGuidelineSetting
             */
            public save(): void {
                var self = this;

                nts.uk.ui.block.invisible();
            }

            /**
             * Event on click cancel button.
             */
            public cancelSetting(): void {
                nts.uk.ui.windows.close();
            }

        }     
        
        
//        export class ReferenceConditionModel {
//            yearlyDisplayCondition: KnockoutObservable<number>;
//            monthlyDisplayCondition: KnockoutObservable<number>;
//            alarmCheckCondition: KnockoutObservable<number>;
//            lstEstimateCondition: KnockoutObservableArray<EstimatedConditionDto>;
//
//            constructor() {
//                this.yearlyDisplayCondition = ko.observable(1);
//                this.monthlyDisplayCondition = ko.observable(1);
//                this.alarmCheckCondition = ko.observable(1);
//                this.lstEstimateCondition = ko.observableArray([
//                    { code: 1, name: "条件1" },
//                    { code: 2, name: "条件2" },
//                    { code: 3, name: "条件3" },
//                    { code: 4, name: "条件4" },
//                    { code: 5, name: "条件5" },
//                ]);
//            }
//            
//            updateData(dto: ReferenceConditionDto) {
//                this.yearlyDisplayCondition(dto.yearlyDisplayCondition);
//                this.monthlyDisplayCondition(dto.monthlyDisplayCondition);
//                this.alarmCheckCondition(dto.alarmCheckCondition);
//            }
//            
//            toDto(): ReferenceConditionDto {
//                var dto: ReferenceConditionDto = 
//                    {
//                        yearlyDisplayCondition: this.yearlyDisplayCondition(),
//                        monthlyDisplayCondition: this.monthlyDisplayCondition(),
//                        alarmCheckCondition: this.alarmCheckCondition()
//                    };
//                return dto;
//            }
//        }
//        export class CommonGuidelineSettingModel{
//            estAlarmColors1st:   KnockoutObservable<string>;  
//            estAlarmColors2nd:   KnockoutObservable<string>;  
//            estAlarmColors3rd:   KnockoutObservable<string>;  
//            estAlarmColors4th:   KnockoutObservable<string>;  
//            estAlarmColors5th:   KnockoutObservable<string>;
//
//            /** The estimate time. */
//            estimateTime: ReferenceConditionModel;
//
//            /** The estimate price. */
//            estimatePrice: ReferenceConditionModel;
//
//            /** The estimate number of days. */
//            estimateNumberOfDays: ReferenceConditionModel;
//            
//            
//            
//            constructor() {
//                this.estAlarmColors1st = ko.observable('#0000ff');
//                this.estAlarmColors2nd = ko.observable('#ffff00');
//                this.estAlarmColors3rd = ko.observable('#ff9900');
//                this.estAlarmColors4th = ko.observable('#ff00ff');
//                this.estAlarmColors5th = ko.observable('#ff0000');
//                this.estimateTime = new ReferenceConditionModel();
//                this.estimatePrice = new ReferenceConditionModel();
//                this.estimateNumberOfDays = new ReferenceConditionModel();
//            }
//            
//            updateData(dto: CommonGuidelineSettingDto) {
//                for (var color of dto.alarmColors) {
//                    switch(color.guidelineCondition){
//                        case EstimatedCondition.CONDITION_1ST:
//                        this.estAlarmColors1st(color.color);
//                        break;    
//                        case EstimatedCondition.CONDITION_2ND:
//                        this.estAlarmColors2nd(color.color);
//                        break;    
//                        case EstimatedCondition.CONDITION_3RD:
//                        this.estAlarmColors3rd(color.color);
//                        break;    
//                        case EstimatedCondition.CONDITION_4TH:
//                        this.estAlarmColors4th(color.color);
//                        break;    
//                        case EstimatedCondition.CONDITION_5TH:
//                        this.estAlarmColors5th(color.color);
//                        break;    
//                    }
//                }
//                this.estimateTime.updateData(dto.estimateTime);
//                this.estimatePrice.updateData(dto.estimatePrice);
//                this.estimateNumberOfDays.updateData(dto.estimateNumberOfDays);
//            }
//            
//            toDto(): CommonGuidelineSettingDto {
//                var alarmColors: EstimatedAlarmColorDto[] = [];
//                alarmColors.push(
//                    { guidelineCondition: EstimatedCondition.CONDITION_1ST, color: this.estAlarmColors1st() });
//                alarmColors.push(
//                    { guidelineCondition: EstimatedCondition.CONDITION_2ND, color: this.estAlarmColors2nd() });
//                alarmColors.push(
//                    { guidelineCondition: EstimatedCondition.CONDITION_3RD, color: this.estAlarmColors3rd() });
//                alarmColors.push(
//                    { guidelineCondition: EstimatedCondition.CONDITION_4TH, color: this.estAlarmColors4th() });
//                alarmColors.push(
//                    { guidelineCondition: EstimatedCondition.CONDITION_5TH, color: this.estAlarmColors5th() });
//                var dto: CommonGuidelineSettingDto =
//                    {
//                        alarmColors: alarmColors,
//                        estimateTime: this.estimateTime.toDto(),
//                        estimatePrice: this.estimatePrice.toDto(),
//                        estimateNumberOfDays: this.estimateNumberOfDays.toDto()
//                    };
//                return dto;
//            }
//        }
        
        //  目安利用条件
        export enum EstimatedCondition {
            // 条件1
            CONDITION_1ST = 1,
            // 条件2
            CONDITION_2ND = 2,
            // 条件3
            CONDITION_3RD = 3,
            // 条件4
            CONDITION_4TH = 4,
            // 条件5
            CONDITION_5TH = 5,
        }
    }
}