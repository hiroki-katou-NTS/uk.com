module cmm045.a.viewmodel {
    export class ScreenModel {
        roundingRules: KnockoutObservableArray<ApplicationDisplayAtr> = ko.observableArray([]);
        selectedRuleCode: KnockoutObservable<any> = ko.observable(1);
        constructor(){
            
        }
   
        start(){
            let self = this;
            let param: AppListExtractConditionDto = new AppListExtractConditionDto('2017-12-01', '2018-06-01', 0,
                    1, true, true, true, true, true, true, 1, [], '');
            service.getApplicationDisplayAtr().done(function(data){
                _.each(data, function(obj){
                    self.roundingRules.push(new ApplicationDisplayAtr(obj.value, obj.localizedName));
                });
                service.getApplicationList(param).done(function(data){
                    console.log(data);
            });
            });
        }
    } 
    export class ApplicationDisplayAtr{
        code: number;
        name: string;
        constructor(code: number, name: string){
            this.code = code;
            this.name = name;
            
        } 
    }
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
}
