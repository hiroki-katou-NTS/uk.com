module nts.uk.at.view.kal003.share {
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import model = nts.uk.at.view.kal003.share.model;

    export module kal003utils {
        /**
         * initial default value for Condition Object
         * @param itemcheck
         */
        export function getDefaultCondition(No): model.ErAlAtdItemCondition {
            return new model.ErAlAtdItemCondition(No, null);
        }

        /**
         * initial default value for GroupConditio Object
         * @param conditions
         */
        export function getDefaultGroupCondition(conditions: Array<model.ErAlAtdItemCondition>): model.ErAlConditionsAttendanceItem {
            return new model.ErAlConditionsAttendanceItem({
                atdItemConGroupId: ''
                , conditionOperator: 0
                , lstErAlAtdItemCon: conditions || ([])
            });
        }

        export function getDefaultAlCheckTargetCondition(): model.AlCheckTargetCondition {
            return new model.AlCheckTargetCondition({
                filterByBusinessType: false,
                filterByJobTitle: false,
                filterByEmployment: false,
                filterByClassification: false,
                lstBusinessTypeCode: [],
                lstJobTitleId: [],
                lstEmploymentCode: [],
                lstClassificationCode: []
            });
        }
        /**
         * initial default value for CompoundConditio Object
         * @param itemcheck
         */
        export function getDefaultAttendanceItemCondition(): model.AttendanceItemCondition {
            let conditions1: Array<model.ErAlAtdItemCondition> = [getDefaultCondition(0), getDefaultCondition(1), getDefaultCondition(2)];
            let conditions2: Array<model.ErAlAtdItemCondition> = [getDefaultCondition(0), getDefaultCondition(1), getDefaultCondition(2)];
            return new model.AttendanceItemCondition({
                group1: getDefaultGroupCondition(conditions1)
                , group2UseAtr: false
                , group2: getDefaultGroupCondition(conditions2)
                , operatorBetweenGroups: 0
            });
        }
        export function getDefaultWorkTypeCondition(): model.WorkTypeCondition {
            return new model.WorkTypeCondition({
                useAtr: false,
                comparePlanAndActual: 1,
                planFilterAtr: false,
                planLstWorkType: [],
                actualFilterAtr: false,
                actualLstWorkType: []
            });
        }
        export function getDefaultWorkTimeCondition(): model.WorkTimeCondition {
            return new model.WorkTimeCondition({
                useAtr: false,
                comparePlanAndActual: 0,
                planFilterAtr: false,
                planLstWorkTime: [],
                actualFilterAtr: false,
                actualLstWorkTime: []

            });
        }

        export function getDefaultErrorAlarmCondition(): model.ErrorAlarmCondition {
            return new model.ErrorAlarmCondition({
                errorAlarmCheckID: '',
                displayMessage: '',
                alCheckTargetCondition: getDefaultAlCheckTargetCondition(),
                workTypeCondition: getDefaultWorkTypeCondition(),
                workTimeCondition: getDefaultWorkTimeCondition(),
                atdItemCondition: getDefaultAttendanceItemCondition(),
                continuousPeriod: 0
            });
        }
        
        export function getDefaultExtraResultMonthly(typeCheckItem : number):model.ExtraResultMonthly{
            
            
            let extraResultMonthly = new model.ExtraResultMonthly({
                errorAlarmCheckID: '',
                sortBy :0,
                nameAlarmExtraCon : '',
                useAtr : false,
                typeCheckItem : typeCheckItem ||0,
                messageBold: false,
                messageColor: '',
                displayMessage : '',
                checkConMonthly :getDefaultAttendanceItemCondition(),
                rowId: 0,
                specHolidayCheckCon : getDefaultSpecHolidayCheckCon(),
                checkRemainNumberMon : getDefaultCheckRemainNumberMon() ,
                agreementCheckCon36 : getDefaultAgreementCheckCon36()
                
            });
            return extraResultMonthly;
        }
        
        export function getDefaultSpecHolidayCheckCon(): model.SpecHolidayCheckCon {
            return new model.SpecHolidayCheckCon({
                errorAlarmCheckID: '',
                compareOperator: 0,
                numberDayDiffHoliday1: 0,
                numberDayDiffHoliday2: null
            });
        }
        
        export function getDefaultAgreementCheckCon36(): model.AgreementCheckCon36 {
            return new model.AgreementCheckCon36({
                errorAlarmCheckID: '',
                classification: 0,
                compareOperator: 0,
                eralBeforeTime: 0
            });
        }
        
        export function getDefaultCheckRemainNumberMon(): model.CheckRemainNumberMon {
            return new model.CheckRemainNumberMon({
                errorAlarmCheckID: '',
                checkVacation: 0,
                checkOperatorType: 0,
                compareRangeEx: getDefaultCompareRangeImport(),
                compareSingleValueEx : getDefaultCompareSingleValueImport()
            });
        }
        
        
        export function getDefaultCompareRangeImport(): model.CompareRangeImport {
            return new model.CompareRangeImport({
                compareOperator : 0,
                startValue: getDefaultCheckConValueRemainNumberImport(),
                endValue: getDefaultCheckConValueRemainNumberImport()
            });
        }
        
        export function getDefaultCompareSingleValueImport(): model.CompareSingleValueImport {
            return new model.CompareSingleValueImport({
                compareOperator : 0,
                value: getDefaultCheckConValueRemainNumberImport()
            });
        }
        
         export function getDefaultCheckConValueRemainNumberImport(): model.CheckConValueRemainNumberImport {
            return new model.CheckConValueRemainNumberImport({
                daysValue : 0,
                timeValue: 0
            });
        }


        /**
        * initial default value for WorkRecordExtractingCondition Object
        * @param checkItem
        */
        export function getDefaultWorkRecordExtractingCondition(checkItem: number): model.WorkRecordExtractingCondition {
            let workRecordExtractingCondition = new model.WorkRecordExtractingCondition({
                errorAlarmCheckID: '',
                checkItem: checkItem || 0,
                messageBold: false,
                messageColor: '',
                sortOrderBy: 0,
                useAtr: false,
                nameWKRecord: '',
                errorAlarmCondition: getDefaultErrorAlarmCondition(),
                rowId: 0
            });
            return workRecordExtractingCondition;
        }
        //monthly
        export function convertTransferDataToExtraResultMonthly(
            extraResultMonthly): model.ExtraResultMonthly {
            let convertExtraResultMonthly = new model.ExtraResultMonthly(extraResultMonthly);
            convertExtraResultMonthly
            .checkConMonthly(new model.AttendanceItemCondition(extraResultMonthly.checkConMonthly));
            
             //group 1
            convertExtraResultMonthly.checkConMonthly()
                .group1(new model.ErAlConditionsAttendanceItem(extraResultMonthly.checkConMonthly.group1));
            //ErAlAtdItemCondition
            let lstErAlAtdItemCon1 = extraResultMonthly.checkConMonthly.group1.lstErAlAtdItemCon;
            convertExtraResultMonthly.checkConMonthly().group1().lstErAlAtdItemCon = ko.observableArray([]);
            if (lstErAlAtdItemCon1) {
                for (var i = 0; i < lstErAlAtdItemCon1.length; i++) {
                    var erAlAtdItemCondition1 = new model.ErAlAtdItemCondition(lstErAlAtdItemCon1[i].targetNO, lstErAlAtdItemCon1[i]);
                    convertExtraResultMonthly.checkConMonthly().group1().lstErAlAtdItemCon().push(erAlAtdItemCondition1);
                }
            }

            //group 2
            convertExtraResultMonthly.checkConMonthly()
                .group2(new model.ErAlConditionsAttendanceItem(extraResultMonthly.checkConMonthly.group2));
            //ErAlAtdItemCondition
            let lstErAlAtdItemCon2 = extraResultMonthly.checkConMonthly.group2.lstErAlAtdItemCon;
            convertExtraResultMonthly.checkConMonthly().group2().lstErAlAtdItemCon = ko.observableArray([]);

            if (lstErAlAtdItemCon2) {
                for (var i = 0; i < lstErAlAtdItemCon2.length; i++) {
                    var erAlAtdItemCondition2 = new model.ErAlAtdItemCondition(lstErAlAtdItemCon2[i].targetNO, lstErAlAtdItemCon2[i]);
                    convertExtraResultMonthly.checkConMonthly().group2().lstErAlAtdItemCon().push(erAlAtdItemCondition2);
                }
            }
            
            return convertExtraResultMonthly;
        }

        export function convertTransferDataToWorkRecordExtractingCondition(
            workRecordExtractingCondition): model.WorkRecordExtractingCondition {
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
                for (var i = 0; i < lstErAlAtdItemCon1.length; i++) {
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
                for (var i = 0; i < lstErAlAtdItemCon2.length; i++) {
                    var erAlAtdItemCondition2 = new model.ErAlAtdItemCondition(lstErAlAtdItemCon2[i].targetNO, lstErAlAtdItemCon2[i]);
                    convertWorkRecordExtractingCondition.errorAlarmCondition().atdItemCondition().group2().lstErAlAtdItemCon().push(erAlAtdItemCondition2);
                }
            }
            return convertWorkRecordExtractingCondition;
        }
        //monthly
         export function convertArrayOfExtraResultMonthlyToJS(dataJS: any,
            extraResultMonthly :model.ExtraResultMonthly):any {
             dataJS.checkConMonthly = convertArrayOfAttendanceItemCondition(dataJS.checkConMonthly, extraResultMonthly.checkConMonthly());
             return dataJS;
         }

        export function convertArrayOfWorkRecordExtractingConditionToJS(dataJS: any,
            workRecordExtractingCondition: model.WorkRecordExtractingCondition): any {
            let errorAlarmCondition = workRecordExtractingCondition.errorAlarmCondition();
            dataJS.errorAlarmCondition.workTypeCondition.planLstWorkType = _.values(errorAlarmCondition.workTypeCondition().planLstWorkType() || []);
            dataJS.errorAlarmCondition.workTypeCondition.actualLstWorkType = _.values(errorAlarmCondition.workTypeCondition().actualLstWorkType || []);

            dataJS.errorAlarmCondition.workTimeCondition.planLstWorkTime = _.values(errorAlarmCondition.workTimeCondition().planLstWorkTime() || []);
            dataJS.errorAlarmCondition.workTimeCondition.actualLstWorkTime = _.values(errorAlarmCondition.workTimeCondition().actualLstWorkTime || []);

            dataJS.errorAlarmCondition.atdItemCondition =
                convertArrayOfAttendanceItemCondition(dataJS.errorAlarmCondition.atdItemCondition, errorAlarmCondition.atdItemCondition());
            return dataJS;
        }
        /**
         * Covert all data array to array data of JSon
         */
        export function convertArrayOfAttendanceItemCondition(dataAttItemJS, attItemCondition: model.AttendanceItemCondition): any {
            let lstErAlAtdItemCon1 = attItemCondition.group1().lstErAlAtdItemCon();
            dataAttItemJS.group1.lstErAlAtdItemCon = _.values(lstErAlAtdItemCon1);
            if (lstErAlAtdItemCon1) {
                for (var i = 0; i < lstErAlAtdItemCon1.length; i++) {
                    dataAttItemJS.group1.lstErAlAtdItemCon[i] = ko.toJS(lstErAlAtdItemCon1[i])
                    dataAttItemJS.group1.lstErAlAtdItemCon[i].countableAddAtdItems = _.values(lstErAlAtdItemCon1[i].countableAddAtdItems());
                    dataAttItemJS.group1.lstErAlAtdItemCon[i].countableSubAtdItems = _.values(lstErAlAtdItemCon1[i].countableSubAtdItems());
                }
            }

            let lstErAlAtdItemCon2 = attItemCondition.group2().lstErAlAtdItemCon();
            dataAttItemJS.group2.lstErAlAtdItemCon = _.values(lstErAlAtdItemCon2);

            if (lstErAlAtdItemCon2) {
                for (var i = 0; i < lstErAlAtdItemCon2.length; i++) {
                    dataAttItemJS.group2.lstErAlAtdItemCon[i] = ko.toJS(lstErAlAtdItemCon2[i])
                    dataAttItemJS.group2.lstErAlAtdItemCon[i].countableAddAtdItems = _.values(lstErAlAtdItemCon2[i].countableAddAtdItems());
                    dataAttItemJS.group2.lstErAlAtdItemCon[i].countableSubAtdItems = _.values(lstErAlAtdItemCon2[i].countableSubAtdItems());
                }
            }
            return dataAttItemJS;

        }

        export function getDefaultAgreementHour(): model.AgreeCondOt {
            let agreeCondOt = new model.AgreeCondOt({
                category: 0,
                code: '',
                id: '',
                no: 0,
                ot36: 0,
                excessNum: 0,
                messageDisp: '',
            });
            return agreeCondOt;
        }
    }
}