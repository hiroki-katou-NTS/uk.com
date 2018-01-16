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
        export function getDefaultAtdItemCondition() : model.AttendanceItemCondition {
            let conditions : Array<model.ErAlAtdItemCondition> = [getDefaultCondition(0), getDefaultCondition(1), getDefaultCondition(2)];
            return new model.AttendanceItemCondition({
                group1: getDefaultGroupCondition(conditions)
                , group2UseAtr: false
                , group2: getDefaultGroupCondition(conditions)
                , operatorBetweenGroups: 0
            });
        }

        /**
         * initial default value for ErrorAlarmCondition Object
         * @param checkItem
         */
        export function getDefaultErrorAlarmCondition(checkItem : number) : model.ErrorAlarmCondition {
            let self = this;
            let defaultAtdItemCondition = getDefaultAtdItemCondition();
            let errorAlarmCondition = new model.ErrorAlarmCondition({
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
            return errorAlarmCondition;
        }
        
        /**
         * initial default value for WorkRecordExtractingCondition Object
         * @param checkItem
         */
        export function getDefaultWorkRecordExtractingCondition(checkItem : number) : model.WorkRecordExtractingCondition {
            let workRecordExtractingCondition = new model.WorkRecordExtractingCondition({
                errorAlarmCheckID       : ''
                , checkItem             : checkItem || 0
                , sortOrderBy           : 0
                , useAtr                : false
                , nameWKRecord          : ''
                , errorAlarmCondition   : getDefaultErrorAlarmCondition(checkItem)
                , rowId                 : 0
            });
            return workRecordExtractingCondition;
        }
    }
}