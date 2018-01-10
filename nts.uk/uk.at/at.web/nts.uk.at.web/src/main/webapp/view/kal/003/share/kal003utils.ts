module nts.uk.at.view.kal003.share {
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import model = nts.uk.at.view.kal003.share.model;
    
    export module kal003utils{
        /**
         * initial default value for Condition Object
         * @param itemcheck
         */
        export function getDefaultCondition(itemcheck : number) : model.Condition {
            return new model.Condition({
            itemCheck:              itemcheck || 0,
            target:                 0,
            operatorCd:             '',
            comparisonOperatorId:   0,
            itemConditionId:        ''
            });
        }
        
        /**
         * initial default value for GroupConditio Object
         * @param conditions
         */
        export function getDefaultGroupCondition(conditions : Array<model.Condition>) : model.GroupCondition {
            return new model.GroupCondition({
                groupOperator: 0
                , groupListCondition: conditions || ([])
            });
        }
    
        /**
         * initial default value for CompoundConditio Object
         * @param itemcheck
         */
        export function getDefaultCompoundCondition(itemcheck : number) : model.CompoundCondition {
            let conditions : Array<model.Condition> = [getDefaultCondition(itemcheck), getDefaultCondition(itemcheck), getDefaultCondition(itemcheck)];
            return new model.CompoundCondition({
                group1Condition: getDefaultGroupCondition(conditions)
                , hasGroup2: false
                , group2Condition: getDefaultGroupCondition(conditions)
                , operatorBetweenG1AndG2: 0
            });
        }

        /**
         * initial default value for ErrorAlarmCondition Object
         * @param checkItem
         */
        export function getDefaultErrorAlarmCondition(checkItem : number) : model.ErrorAlarmCondition {
            let self = this;
            let defaultCompoundCondition = getDefaultCompoundCondition(checkItem);
            let errorAlarmCondition = new model.ErrorAlarmCondition({
                category:                   0
                , erAlCheckId:              ''
                , checkItem:                checkItem || 0
                , workTypeRange:            ''
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
                , compoundCondition:        defaultCompoundCondition
            });
            return errorAlarmCondition;
        }
        
        /**
         * initial default value for WorkRecordExtractingCondition Object
         * @param checkItem
         */
        export function getDefaultWorkRecordExtractingCondition(checkItem : number) : model.WorkRecordExtractingCondition {
            let workRecordExtractingCondition = new model.WorkRecordExtractingCondition({
                errorAlarmCheckID   : ''
                , checkItem           : checkItem || 0
                , sortOrderBy         : 0
                , useAtr              : false
                , nameWKRecord        : ''
                , errorAlarmCondition  : getDefaultErrorAlarmCondition(checkItem)
                , rowId               : 0
            });
            return workRecordExtractingCondition;
        }
    }
}