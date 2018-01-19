module nts.uk.at.view.kal003.share {
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import model = nts.uk.at.view.kal003.share.model;
    
    export module kal003utils{
        /**
         * initial default value for Condition Object
         * @param itemcheck
         */
        export function getDefaultCondition(No) : model.ErAlAtdItemCondition {
            return new model.ErAlAtdItemCondition(No, null);
        }
        
        /**
         * initial default value for GroupConditio Object
         * @param conditions
         */
        export function getDefaultGroupCondition(conditions : Array<model.ErAlAtdItemCondition>) : model.ErAlConditionsAttendanceItem {
            return new model.ErAlConditionsAttendanceItem({
                atdItemConGroupId: ''
                ,conditionOperator: 0
                , lstErAlAtdItemCon: conditions || ([])
            });
        }
    
        /**
         * initial default value for CompoundConditio Object
         * @param itemcheck
         */
        export function getDefaultAttendanceItemCondition() : model.AttendanceItemCondition {
            let conditions : Array<model.ErAlAtdItemCondition> = [getDefaultCondition(0), getDefaultCondition(1), getDefaultCondition(2)];
            return new model.AttendanceItemCondition({
                group1: getDefaultGroupCondition(conditions)
                , group2UseAtr: false
                , group2: getDefaultGroupCondition(conditions)
                , operatorBetweenGroups: 0
            });
        }
        export function getDefaultWorkTypeCondition() : model.WorkTypeCondition {
            return new model.WorkTypeCondition({
                useAtr : false,
                comparePlanAndActual: 1,
                planFilterAtr : false,
                planLstWorkType : [],
                actualFilterAtr: false,
                actualLstWorkType: []
            });
        }
        export function getDefaultWorkTimeCondition() : model.WorkTimeCondition {
            return new model.WorkTimeCondition({
                useAtr                  : false,
                comparePlanAndActual    : 0,
                planFilterAtr           : false,
                planLstWorkTime         : [],
                actualFilterAtr         : false,
                actualLstWorkTime       : []
                
            });
        }
        
        export function getDefaultErrorAlarmCondition() : model.ErrorAlarmCondition {
            return new model.ErrorAlarmCondition({
                errorAlarmCheckID       : '',
                displayMessage          : '',
                alCheckTargetCondition  : null,
                workTypeCondition       : getDefaultWorkTypeCondition(),
                workTimeCondition       : getDefaultWorkTimeCondition(),
                atdItemCondition        : getDefaultAttendanceItemCondition(),
                continuousPeriod        : 0
            });
        }
            
        /**
         * initial default value for IKAL003BModel Object
         * @param checkItem
         */
        /*
        export function getDefaultIKAL003BModel(checkItem : number) : model.IKAL003BModel {
            let self = this;
            let defaultAtdItemCondition = getDefaultAtdItemCondition();
            let kal003BModel = new model.KAL003BModel({
                category:                   0
                , erAlCheckId:              ''
                , checkItem:                checkItem || 0
                , workTypeSelections:       []
                , workTimeItemSelections:   []
                , comparisonOperator:       0
                , minimumValue:             ''
                , maximumValue:             ''
                , continuousPeriodInput:    ''
                , workingTimeZoneSelections: []
                , color:                    ''
                , message:                  ''
                , isBold:                   false
                , workTypeCondition:        1
                , workTimeCondition:        0
                , atdItemCondition:         defaultAtdItemCondition
            });
            return kal003BModel;
        }
        */
        /**
         * initial default value for WorkRecordExtractingCondition Object
         * @param checkItem
         */
        export function getDefaultWorkRecordExtractingCondition(checkItem : number) : model.WorkRecordExtractingCondition {
            let workRecordExtractingCondition = new model.WorkRecordExtractingCondition({
                errorAlarmCheckID       : '',
                checkItem             : checkItem || 0,
                messageBold           : false,
                messageColor          : '',
                sortOrderBy           : 0,
                useAtr                : false,
                nameWKRecord          : '',
                errorAlarmCondition   : getDefaultErrorAlarmCondition(),
                rowId                 : 0
            });
            return workRecordExtractingCondition;
        }
        /*
        export function transformDataFromA2B(category: number, 
            workRecordExtractingCondition : WorkRecordExtractingCondition,  ) : model.IKAL003BModel {
            errorAlarmCondition : model.ErrorAlarmCondition = workRecordExtractingCondition.errorAlarmCondition;
            return model.IErrorAlarmWorkRecordDto ({
                category            : category,
                erAlCheckId         : workRecordExtractingCondition.errorAlarmCheckID,
                checkItem           : workRecordExtractingCondition.checkItem,
                workTypeSelections  : errorAlarmCondition.workTypeCondition.planLstWorkType,
                workTimeItemSelections:errorAlarmCondition.workTimeCondition.planLstWorkType,
                comparisonOperator  : errorAlarmCondition.workTimeCondition.comparisonOperator,
                minimumValue        : errorAlarmCondition.workTimeCondition.minimumValue,
                maximumValue        : errorAlarmCondition.workTimeCondition.maximumValue,
                continuousPeriod    : errorAlarmCondition.continuousPeriod,
                workingTimeZoneSelections: errorAlarmCondition.workTimeZoneCondition.workTimeZoneCode,
                color               : workRecordExtractingCondition.messageColor,
                message             : errorAlarmCondition.displayMessage,
                isBold              : workRecordExtractingCondition.messageBoldboolean,
                workTypeCondition: errorAlarmCondition.workTypeCondition.comparePlanAndActual,
                workTimeZoneCondition: errorAlarmCondition.workZoneCondition.comparePlanAndActual,
                atdItemCondition: errorAlarmCondition.atdItemCondition,
            });
        }
        
        export function transformDataFromB2A() : model.IErrorAlarmCondition {
        
            return model.IErrorAlarmCondition ({
                
            });
        }
        */
    }
}