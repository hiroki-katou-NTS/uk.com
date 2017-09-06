module nts.uk.com.view.cmm018.shr {
    export module vmbase {
        //screenA
        export class ListHistory {
            approvalId: string;
            dateRange: string;  
            constructor(approvalId: string, dateRange: string) {
                var self = this;
                this.approvalId = approvalId;
                self.dateRange = dateRange;
            }  
        }
        //screenA
        export class ListApproval {
            approvalId: string;
            name: string;
            lstApprover: Array<Approver>;
            constructor(approvalId: string, name: string, lstApprover: Array<Approver>) {
                var self = this;
                this.approvalId = approvalId;
                self.name = name;
                self.lstApprover = lstApprover;
            }  
        }
        //Screen I
        export interface IData_Param{
            /** name */
            name?: string
            /**開始日*/
            startDate: string;
            /**開始日 Old*/
            startDateOld?: string;
            /**check 申請承認の種類区分: 会社(1)　－　職場(2)　－　社員(3)*/
            check: number;
            /** まとめて設定モード(0) - 申請個別設定モード(1)*/
            mode: number;
        }
        //ScreenI
        export class IData{
            /**開始日*/
            startDate: string;
            /**開始日 Old*/
            startDateOld: string;
            /**check 申請承認の種類区分: 会社(1)　－　職場(2)　－　社員(3)*/
            check: number;
            /** まとめて設定モード(0) - 申請個別設定モード(1)*/
            mode: number;
            /** 履歴から引き継ぐか、初めから作成するかを選択する*/
            copyDataFlag: boolean;
            constructor(startDate: string,
                startDateOld: string,
                check: number,
                mode: number,
                copyDataFlag: boolean){
                    this.startDate = startDate;
                    this.startDateOld = startDateOld;
                    this.check = check;
                    this.mode = mode;
                    this.copyDataFlag = copyDataFlag;
            }
        }
        //ScreenJ
        export class JData{
            /**開始日*/
            startDate: string;
            /**終了日*/
            endDate: string;
            /**履歴ID*/
            workplaceId: string;
            /**社員ID*/
            employeeId: string;
            /**check 申請承認の種類区分: 会社(1)　－　職場(2)　－　社員(3)*/
            check: number;
            /**「履歴を削除する」を選択する か(0)、「履歴を修正する」を選択する か(1)。*/
            editOrDelete: number;
            /**開始日 previous*/
            startDatePrevious: string;
            /** list history and approvalId */
            lstUpdate: Array<UpdateHistoryDto>;
            constructor(startDate: string,
            endDate: string,
            workplaceId: string,
            employeeId: string,
            check: number,
            editOrDelete: number,
            startDatePrevious: string,
            lstUpdate: Array<UpdateHistoryDto>){
                this.startDate = startDate;
                this.endDate = endDate;
                this.workplaceId = workplaceId;
                this.employeeId = employeeId;
                this.check = check;
                this.editOrDelete = editOrDelete;
                this.startDatePrevious = startDatePrevious;
                this.lstUpdate = lstUpdate;
            }
        }
        //ScrenJ
        export interface JData_Param{
            /** name */
            name: string
            /**開始日*/
            startDate: string;
            /**終了日*/
            endDate: string;
            /**履歴ID*/
            workplaceId?: string;
            /**社員ID*/
            employeeId?: string;
            /**check 申請承認の種類区分: 会社(1)　－　職場(2)　－　社員(3)*/
            check: number;
            /** まとめて設定モード(0) - 申請個別設定モード(1)*/
            mode: number;
            /** 編集対象期間履歴が重なっているかチェックする*/
            overlapFlag: boolean;
            /**開始日 previous*/
            startDatePrevious: string;
            /** list history and approvalId */
            lstUpdate: Array<UpdateHistoryDto>;
        }
        //ScreenJ
        export class UpdateHistoryDto{
            /**承認ID*/
            approvalId: string;
            /**履歴ID*/
            historyId: string;
            constructor(approvalId: string, historyId: string){
                this.approvalId = approvalId;
                this.historyId = historyId; 
            }
        }
        //Param screen A,C,E
        export class ParamDto {
            /**就業ルート区分: 会社(0)　－　職場(1)　－　社員(2)*/
            rootType: number;
            /**履歴ID*/
            workplaceId: string;
            /**社員ID*/
            employeeId: string;
            constructor(rootType: number, workplaceId: string, employeeId: string){
                this.rootType = rootType;
                this.workplaceId = workplaceId;
                this.employeeId =  employeeId;
            }
        }
        //data screen A,C,E
        export class CommonApprovalRootDto{
            /**会社名*/
            companyName: string;
            lstCompanyRoot: Array<CompanyAppRootDto>;
            lstWorkplaceRoot: Array<WorkPlaceAppRootDto>;
            lstPersonRoot: Array<PersonAppRootDto>;
        }
        //data screen A
        export class CompanyAppRootDto{
            company: ComApprovalRootDto;
            lstAppPhase: Array<ApprovalPhaseDto>;
        }
        export class CompanyAppRootADto{
            approvalId: string;
            appPhase1: ApprovalPhaseDto;
            appPhase2: ApprovalPhaseDto;
            appPhase3: ApprovalPhaseDto;
            appPhase4: ApprovalPhaseDto;
            appPhase5: ApprovalPhaseDto;
            constructor( approvalId: string,
            appPhase1: ApprovalPhaseDto, 
            appPhase2: ApprovalPhaseDto, 
            appPhase3: ApprovalPhaseDto,
            appPhase4: ApprovalPhaseDto, 
            appPhase5: ApprovalPhaseDto){
                this.approvalId =  approvalId;
                this.appPhase1 = appPhase1;
                this.appPhase2 = appPhase2;
                this.appPhase3 = appPhase3;
                this.appPhase4 = appPhase4;
                this.appPhase5 = appPhase5;
            }
        }
        //data screen C
        export class WorkPlaceAppRootDto{
            workplace: WpApprovalRootDto;
            lstAppPhase: Array<ApprovalPhaseDto>;
        }
        //data screen E
        export class PersonAppRootDto{
            person: PsApprovalRootDto;
            lstAppPhase: Array<ApprovalPhaseDto>;
        }
        export class ComApprovalRootDto{
            /**会社ID*/
            companyId: string;
            /**承認ID*/
            approvalId: string;
            /**履歴ID*/
            historyId: string;
            /**申請種類*/
            applicationType: number;
            /**開始日*/
            startDate: string;
            /**終了日*/
            endDate: string;
            /**分岐ID*/
            branchId: string;
            /**任意項目申請ID*/
            anyItemApplicationId: string;
            /**確認ルート種類*/
            confirmationRootType: number;
            /**就業ルート区分*/
            employmentRootAtr: number;
        }
        export class WpApprovalRootDto{
            /**会社ID*/
            companyId: string;
            /**承認ID*/
            approvalId: string;
            /**履歴ID*/
            workplaceId: string;
            /**履歴ID*/
            historyId: string;
            /**申請種類*/
            applicationType: number;
            /**開始日*/
            startDate: string;
            /**終了日*/
            endDate: string;
            /**分岐ID*/
            branchId: string;
            /**任意項目申請ID*/
            anyItemApplicationId: string;
            /**確認ルート種類*/
            confirmationRootType: number;
            /**就業ルート区分*/
            employmentRootAtr: number;
        }
        export class PsApprovalRootDto{
            /**会社ID*/
            companyId: string;
            /**承認ID*/
            approvalId: string;
            /**社員ID*/
            employeeId: string;
            /**履歴ID*/
            historyId: string;
            /**申請種類*/
            applicationType: number;
            /**開始日*/
            startDate: string;
            /**終了日*/
            endDate: string;
            /**分岐ID*/
            branchId: string;
            /**任意項目申請ID*/
            anyItemApplicationId: string;
            /**確認ルート種類*/
            confirmationRootType: number;
            /**就業ルート区分*/
            employmentRootAtr: number;
        }
        export class ApprovalPhaseDto{
            approver: Array<ApproverDto>;
            /**分岐ID*/
            branchId: string;
            /**承認フェーズID*/
            approvalPhaseId: string;
            /**承認形態*/
            approvalForm: number;
            /**閲覧フェーズ*/
            browsingPhase: number;
            /**順序*/
            orderNumber: number;
            constructor(approver: Array<ApproverDto>, branchId: string, approvalPhaseId: string, approvalForm: number, browsingPhase: number, orderNumber: number){
                this.approver = approver;
                this.branchId = branchId;
                this.approvalPhaseId = approvalPhaseId;
                this.approvalForm = approvalForm;
                this.browsingPhase = browsingPhase;
                this.orderNumber = orderNumber;
            }
        }
        export class ApproverDto{
            /**承認者ID*/
            approverId: string;
            /**職位ID*/
            jobTitleId: string;
            /**社員ID*/
            employeeId: string;
            /**順序*/
            orderNumber: number;
            /**区分*/
            approvalAtr: number;
            /**確定者*/
            confirmPerson: number;
        }
        //
        export enum ApprovalFormEnum{
            
        }
        export class ProcessHandler {
            
            /**
             * get one day before input date as string format
             */
            static getOneDayBefore(date: string) {
                return moment(date).add(-1,'days').format("YYYY/MM/DD");
            }
            
            /**
             * get one day after input date as string format
             */
            static getOneDayAfter(date: string) {
                return moment(date).add(1,'days').format("YYYY/MM/DD");
            }
            
            /**
             * check input date in range, if date in range return true
             */
            static validateDateRange(inputDate: string, startDate: string, endDate: string){
                return moment(inputDate).isBetween(moment(this.getOneDayBefore(startDate)), moment(this.getOneDayAfter(endDate)));
            }
            
            /**
             * check input date before or equal date
             */
            static validateDateInput(inputDate: string, date: string){
                return moment(inputDate).isSameOrAfter(moment(date));
            }
        }
        
        export class Approver{
            id: string;
            code: string;
            name: string;
            constructor(id: string, code: string, name: string){
                this.id = id;
                this.code = code;
                this.name = name;    
            }
        } 
    }
}