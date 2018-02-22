module cmm045.shr {
    export module vmbase {
        import getText = nts.uk.resource.getText;
        export class ApplicationDisplayAtr{
            code: number;
            name: string;
            constructor(code: number, name: string){
                this.code = code;
                this.name = name;
                
            } 
        }
        //parameter filter
        export class AppListExtractConditionDto{
            /**期間開始日付*/
            startDate: string;
            /**期間終了日付*/
            endDate: string;
            /**申請一覧区分*/
            appListAtr: number;
            /**申請種類*/
            appType: number;
            /**承認状況＿未承認*/
            unapprovalStatus: boolean;
            /**承認状況＿承認済*/
            approvalStatus: boolean;
            /**承認状況＿否認*/
            denialStatus: boolean;
            /**承認状況＿代行承認済*/
            agentApprovalStatus: boolean;
            /**承認状況＿差戻*/
            remandStatus: boolean;
            /**承認状況＿取消*/
            cancelStatus: boolean;
            /**申請表示対象*/
            appDisplayAtr: number;
            /**社員IDリスト*/
            listEmployeeId: Array<string>;
            /**社員絞込条件*/
            empRefineCondition: string;
            constructor(startDate: string, endDate: string, appListAtr: number,
                appType: number, unapprovalStatus: boolean, approvalStatus: boolean,
                denialStatus: boolean, agentApprovalStatus: boolean, remandStatus: boolean,
                cancelStatus: boolean, appDisplayAtr: number, listEmployeeId: Array<string>,
                empRefineCondition: string){
                    this.startDate = startDate;
                    this.endDate =  endDate;
                    this.appListAtr =  appListAtr;
                    this.appType = appType;
                    this.unapprovalStatus = unapprovalStatus;
                    this.approvalStatus = approvalStatus;
                    this.denialStatus = denialStatus;
                    this.agentApprovalStatus = agentApprovalStatus;
                    this.remandStatus = remandStatus;
                    this.cancelStatus = cancelStatus;
                    this.appDisplayAtr = appDisplayAtr;
                    this.listEmployeeId = listEmployeeId;
                    this.empRefineCondition = empRefineCondition;
            }
        }
        export class GridItem {
            id: number;
            flag: boolean;
            ruleCode: string;
            combo: string;
            text1: string;
            constructor(index: number) {
                this.id = index;
                this.flag = index % 2 == 0;
                this.ruleCode = String(index % 3 + 1);
                this.combo = String(index % 3 + 1);
                this.text1 = "TEXT";
            }
        }
        
        export class ItemModel {
            code: string;
            name: string;
    
            constructor(code: string, name: string) {
                this.code = code;
                this.name = name;
            }
        }
        //data fill grid list mode application
        export class DataModeApp{
            appId: string;
            appType: number;
            check: boolean;
            details: string;
            applicant: string;
            appName: string;
            appAtr: string;
            appDate: string;
            appContent: string;
            inputDate: string;
            appStatus: string;
            displayAppStatus: string;
            constructor(appId: string,appType: number,  details: string, applicant: string,
                appName: string, appAtr: string, appDate: string,
                appContent: string, inputDate: string, appStatus: string,
                displayAppStatus: string){
                this.appId = appId;
                this.appType = appType;
                this.check = appType == 0 ? true : false;
                this.details = details;
                this.applicant = applicant;
                this.appName = appName;
                this.appAtr = appAtr;
                this.appDate = appDate;
                this.appContent = appContent;
                this.inputDate = inputDate;
                this.appStatus = appStatus;
                this.displayAppStatus = displayAppStatus;
            }
        }  
        
        export class AppMasterInfo {
            appID: string;
            appType: number;
            dispName: string;
            empName: string;
            workplaceName: string;
            constructor(appID: string, appType: number, dispName: string, empName: string, workplaceName: string)
            {
                this.appID = appID;
                this.appType = appType;
                this.dispName = dispName;
                this.empName = empName;
                this.workplaceName = workplaceName;
            }
        }
        export class ApplicationDto_New{
            // 申請ID
            applicationID: string;
            // 事前事後区分
            prePostAtr: number; 
            // 入力日
            inputDate: string; 
            // 入力者
            enteredPersonSID: string;
            // 差戻し理由
            reversionReason: string; 
            // 申請日
            applicationDate: string; 
            // 申請理由
            applicationReason: string;
            // 申請種類
            applicationType: number;
            // 申請者
            applicantSID: string;
            // 予定反映不可理由
            reflectPlanScheReason: number;
            // 予定反映日時
            reflectPlanTime: string;
            // 予定反映状態
            reflectPlanState: number;
            // 予定強制反映
            reflectPlanEnforce: number;
            // 実績反映不可理由
            reflectPerScheReason: number;
            // 実績反映日時
            reflectPerTime: string;
            // 予定反映状態=comment line71???
            reflectPerState: number;
            // 実績強制反映
            reflectPerEnforce: number;
            startDate: string;
            endDate: string;
            constructor(applicationID: string,prePostAtr: number, inputDate: string, enteredPersonSID: string,
                reversionReason: string, applicationDate: string, applicationReason: string, applicationType: number,
                applicantSID: string, reflectPlanScheReason: number, reflectPlanTime: string, reflectPlanState: number,
                reflectPlanEnforce: number, reflectPerScheReason: number, reflectPerTime: string, reflectPerState: number,
                reflectPerEnforce: number, startDate: string, endDate: string)
            {
                this.applicationID = applicationID;
                this.prePostAtr = prePostAtr; 
                this.inputDate = inputDate; 
                this.enteredPersonSID = enteredPersonSID;
                this.reversionReason = reversionReason; 
                this.applicationDate = applicationDate; 
                this.applicationReason = applicationReason;
                this.applicationType = applicationType;
                this.applicantSID = applicantSID;
                this.reflectPlanScheReason = reflectPlanScheReason;
                this.reflectPlanTime = reflectPlanTime;
                this.reflectPlanState = reflectPlanState;
                this.reflectPlanEnforce = reflectPlanEnforce;
                this.reflectPerScheReason = reflectPerScheReason;
                this.reflectPerTime = reflectPerTime;
                this.reflectPerState = reflectPerState;
                this.reflectPerEnforce = reflectPerEnforce;
                this.startDate = startDate;
                this.endDate = endDate;
            }
    }
        export class AppOverTimeInfoFull{
            appID: string;
            /**勤務時間From1*/
            workClockFrom1: string;
            /**勤務時間To1*/
            workClockTo1: string;
            /**勤務時間From2*/
            workClockFrom2: string;
            /**勤務時間To2*/
            workClockTo2: string;
            /**残業時間合計 - wait loivt*/
            total: number;
            lstFrame: Array<OverTimeFrame>;
            /**就業時間外深夜時間*/
            overTimeShiftNight: number;
            /**フレックス超過時間*/
            flexExessTime: number;
            constructor(appID: string, workClockFrom1: string, workClockTo1: string, workClockFrom2: string,
                workClockTo2: string, total: number, lstFrame: Array<OverTimeFrame>,
                overTimeShiftNight: number, flexExessTime: number)
            {
                this.appID = appID;
                this.workClockFrom1 = workClockFrom1;
                this.workClockTo1 = workClockTo1;
                this.workClockFrom2 = workClockFrom2;
                this.workClockTo2 = workClockTo2;
                this.total = total;
                this.lstFrame = lstFrame;
                this.overTimeShiftNight = overTimeShiftNight;
                this.flexExessTime = flexExessTime;    
            }
        }
        
        export class OverTimeFrame {
            /** 勤怠種類 */
            attendanceType: number;
            /**勤怠項目NO ???*/
            frameNo: number;
            /**枠名称*/
            name: string;
            //加給申請時間設定.特定日加給時間 - loai 3 bonus moi co
            timeItemTypeAtr: number;
            /**申請時間  - phut*/
            applicationTime: number;
            constructor(attendanceType: number, frameNo: number, name: string,
                timeItemTypeAtr: number, applicationTime: number)
            {
                this.attendanceType = attendanceType;
                this.frameNo = frameNo;
                this.name = name;
                this.timeItemTypeAtr = timeItemTypeAtr;
                this.applicationTime = applicationTime;    
            }
        }
        export class AppGoBackInfoFull {
            appID: string;
            /**勤務直行1*/
            goWorkAtr1: number;
            /**勤務時間開始1*/
            workTimeStart1: string;
            /**勤務直帰1*/
            backHomeAtr1: number;
            /**勤務時間終了1*/
            workTimeEnd1: string;
            /**勤務直行2*/
            goWorkAtr2: number;
            /**勤務時間開始2*/
            workTimeStart2: string;
            /**勤務直帰2*/
            backHomeAtr2: number;
            /**勤務時間終了2*/
            workTimeEnd2: string;
            constructor(appID: string, goWorkAtr1: number, workTimeStart1: string, backHomeAtr1: number,
                workTimeEnd1: string, goWorkAtr2: number, workTimeStart2: string,
                backHomeAtr2: number, workTimeEnd2: string)
            {
                this.appID = appID;
                this.goWorkAtr1 = goWorkAtr1;
                this.workTimeStart1 = workTimeStart1;
                this.backHomeAtr1 = backHomeAtr1;
                this.workTimeEnd1 = workTimeEnd1;
                this.goWorkAtr2 = goWorkAtr2;
                this.workTimeStart2 = workTimeStart2;
                this.backHomeAtr2 = backHomeAtr2;
                this.workTimeEnd2 = workTimeEnd2;
            }
        }
        //display setting
        export class ApprovalListDisplaySetDto {
            /**事前申請の超過メッセージ*/
            advanceExcessMessDisAtr: number;
            /**休出の事前申請*/
            hwAdvanceDisAtr: number;
            /**休出の実績*/
            hwActualDisAtr: number;
            /**実績超過メッセージ*/
            actualExcessMessDisAtr: number;
            /**残業の事前申請*/
            otAdvanceDisAtr: number;
            /**残業の実績*/
            otActualDisAtr: number;
            /**申請対象日に対して警告表示*/
            warningDateDisAtr: number;
            /**申請理由*/
            appReasonDisAtr: number;
            constructor(advanceExcessMessDisAtr: number, hwAdvanceDisAtr: number,
                hwActualDisAtr: number, actualExcessMessDisAtr: number,
                otAdvanceDisAtr: number, otActualDisAtr: number,
                warningDateDisAtr: number, appReasonDisAtr: number)
            {
                this.advanceExcessMessDisAtr = advanceExcessMessDisAtr;
                this.hwAdvanceDisAtr = hwAdvanceDisAtr;
                this.hwActualDisAtr = hwActualDisAtr;
                this.actualExcessMessDisAtr = actualExcessMessDisAtr;
                this.otAdvanceDisAtr = otAdvanceDisAtr;
                this.otActualDisAtr = otActualDisAtr;
                this.warningDateDisAtr = warningDateDisAtr;
                this.appReasonDisAtr = appReasonDisAtr;
            }
        }
        export class ApplicationStatus {
            unApprovalNumber: string;
            approvalNumber: string;
            approvalAgentNumber: string;
            cancelNumber: string;
            remandNumner: string;
            denialNumber: string;
            constructor(unApprovalNumber: number, approvalNumber: number,
                approvalAgentNumber: number, cancelNumber: number,
                remandNumner: number,denialNumber: number)
            {
                this.unApprovalNumber = getText('CMM045_12') + ' ' + unApprovalNumber + getText('CMM045_18');
                this.approvalNumber = getText('CMM045_13') + ' ' + approvalNumber + getText('CMM045_18');
                this.approvalAgentNumber = getText('CMM045_14') + ' ' + approvalAgentNumber + getText('CMM045_18');
                this.cancelNumber = getText('CMM045_15') + ' ' + cancelNumber + getText('CMM045_18');
                this.remandNumner = getText('CMM045_16') + ' ' + remandNumner + getText('CMM045_18');
                this.denialNumber = getText('CMM045_17') + ' ' + denialNumber + getText('CMM045_18');        
            }
        }
    }
}