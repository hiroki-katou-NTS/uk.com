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
    
        export function getDefaultAlCheckTargetCondition() : model.AlCheckTargetCondition {
            return new model.AlCheckTargetCondition ({
                filterByBusinessType    : false,
                filterByJobTitle        : false,
                filterByEmployment      : false,
                filterByClassification  : false,
                lstBusinessTypeCode     : [],
                lstJobTitleId           : [],
                lstEmploymentCode       : [],
                lstClassificationCode   : []
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
                alCheckTargetCondition  : getDefaultAlCheckTargetCondition(),
                workTypeCondition       : getDefaultWorkTypeCondition(),
                workTimeCondition       : getDefaultWorkTimeCondition(),
                atdItemCondition        : getDefaultAttendanceItemCondition(),
                continuousPeriod        : 0
            });
        }
            
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
        
        export function convertTransferDataToWorkRecordExtractingCondition(
            workRecordExtractingCondition : workRecordExtractingCondition) : model.WorkRecordExtractingCondition {
            let convertWorkRecordExtractingCondition = new model.WorkRecordExtractingCondition(workRecordExtractingCondition);
            
            convertWorkRecordExtractingCondition.errorAlarmCondition(new model.ErrorAlarmCondition(workRecordExtractingCondition.errorAlarmCondition));
            convertWorkRecordExtractingCondition.errorAlarmCondition()
                .alCheckTargetCondition(new model.AlCheckTargetCondition(workRecordExtractingCondition.errorAlarmCondition.alCheckTargetCondition));
            
            convertWorkRecordExtractingCondition.errorAlarmCondition()
                .workTypeCondition(new model.WorkTypeCondition(workRecordExtractingCondition.errorAlarmCondition.workTypeCondition));
            convertWorkRecordExtractingCondition.errorAlarmCondition()
                .workTimeCondition(new model.WorkTimeCondition(workRecordExtractingCondition.errorAlarmCondition.workTimeCondition));
            convertWorkRecordExtractingCondition.errorAlarmCondition()
                .atdItemCondition(new model.AttendanceItemCondition(workRecordExtractingCondition.errorAlarmCondition.atdItemCondition));
            //group 1
            convertWorkRecordExtractingCondition.errorAlarmCondition().atdItemCondition()
                .group1(new model.ErAlConditionsAttendanceItem(workRecordExtractingCondition.errorAlarmCondition.atdItemCondition.group1));
            //ErAlAtdItemCondition
            let lstErAlAtdItemCon1 = workRecordExtractingCondition.errorAlarmCondition.atdItemCondition.group1.lstErAlAtdItemCon;
            convertWorkRecordExtractingCondition.errorAlarmCondition().atdItemCondition().group1().lstErAlAtdItemCon = ko.observableArray([]);
            if (lstErAlAtdItemCon1) {
                for(var i=0; i< lstErAlAtdItemCon1.length; i++) {
                    var erAlAtdItemCondition1 = new model.ErAlAtdItemCondition(lstErAlAtdItemCon1[i].targetNO, lstErAlAtdItemCon1[i]);
                    convertWorkRecordExtractingCondition.errorAlarmCondition().atdItemCondition().group1().lstErAlAtdItemCon().push(erAlAtdItemCondition1);
                }
                

            }
            
            //group 2
            convertWorkRecordExtractingCondition.errorAlarmCondition().atdItemCondition()
                .group2(new model.ErAlConditionsAttendanceItem(workRecordExtractingCondition.errorAlarmCondition.atdItemCondition.group2));
            //ErAlAtdItemCondition
            let lstErAlAtdItemCon2 = workRecordExtractingCondition.errorAlarmCondition.atdItemCondition.group2.lstErAlAtdItemCon;
            convertWorkRecordExtractingCondition.errorAlarmCondition().atdItemCondition().group2().lstErAlAtdItemCon = ko.observableArray([]);
            
            if (lstErAlAtdItemCon2) {
                for(var i=0; i< lstErAlAtdItemCon2.length; i++) {
                   var  erAlAtdItemCondition2 = new model.ErAlAtdItemCondition(lstErAlAtdItemCon2[i].targetNO, lstErAlAtdItemCon2[i]);
                    convertWorkRecordExtractingCondition.errorAlarmCondition().atdItemCondition().group2().lstErAlAtdItemCon().push(erAlAtdItemCondition2);
                }
            }
            return convertWorkRecordExtractingCondition;
        }
    }
}