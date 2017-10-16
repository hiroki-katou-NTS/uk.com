module nts.uk.com.view.cmm018.shr {
    export module vmbase {
        //data register
        export class DataResigterDto{
            /**就業ルート区分: 会社(0)　－　職場(1)　－　社員(2)*/
            rootType: number;
            checkAddHist: boolean;
            workpplaceId: string;
            employeeId: string;
            startDate: string;
            endDate: string;
            addHist: IData;
            lstAppType: Array<number>;
            root: Array<CompanyAppRootADto>;
            constructor(rootType: number, checkAddHist: boolean,
                workpplaceId: string,
                employeeId: string, startDate: string, endDate: string,
                addHist: IData,lstAppType: Array<number>,
                root: Array<CompanyAppRootADto>){
                    this.rootType = rootType;
                    this.checkAddHist = checkAddHist;
                    this.workpplaceId = workpplaceId;
                    this.employeeId = employeeId;
                    this.startDate = startDate; 
                    this.endDate = endDate;
                    this.addHist = addHist;
                    this.lstAppType = lstAppType;
                    this.root = root;
            }
        }
        //data root delete
        export class DataDeleteDto{
            approvalId: string;
            historyId: string;  
            constructor(approvalId: string, historyId: string){
                this.approvalId = approvalId;
                this.historyId = historyId;
            }  
        }
        //data dialog K
        export interface KData{
            appType: string; //設定する対象申請名 
            formSetting: number;//承認形態
            approverInfor: Array<ApproverDtoK>;//承認者一覧
            confirmedPerson: string; //確定者
            selectTypeSet: number;
            approvalFormName: string;
        }
        //data after grouping history (get from db)
        export class DataFullDto{
            workplaceId: string;
            lstCompany: Array<DataDisplayComDto> ;
            lstWorkplace: Array<DataDisplayWpDto> ;
            lstPerson: Array<DataDisplayPsDto> ;
        }
        //data after grouping history of company
        export class DataDisplayComDto{
            id: number;
            overLap: boolean;
            companyName: string;
            lstCompanyRoot: Array<CompanyAppRootDto> ;
        }
        //data after grouping history of work place
        export class DataDisplayWpDto{
            id: number;
            overLap: boolean;
            lstWorkplaceRoot: Array<WorkPlaceAppRootDto> ;
        }
        //data after grouping history of person
        export class DataDisplayPsDto{
            id: number;
            overLap: boolean;
            lstPersonRoot: Array<PersonAppRootDto>;
        }
        //app type
        export class ApplicationType{
            value: number;
            localizedName: string;
            constructor(value: number, localizedName: string){
                this.value = value;
                this.localizedName = localizedName;
            }
        }
        //screenA
        export class ListHistory {
            id: number;
            dateRange: string;
            startDate: string;
            endDate: string;
            overLap: any;
            constructor(id: number, dateRange: string, startDate: string, endDate: string, overLap: any) {
                this.id = id;
                this.dateRange = dateRange;
                this.startDate = startDate;
                this.endDate = endDate;
                this.overLap = overLap;
            }  
        }
        //screenA
        export class ListApproval {
            approvalId: string;
            name: string;
            lstApprover: Array<ApproverDto>;
            constructor(approvalId: string, name: string, lstApprover: Array<ApproverDto>) {
                var self = this;
                this.approvalId = approvalId;
                self.name = name;
                self.lstApprover = lstApprover;
            }  
        }
        //Screen I
        export interface IData_Param{
            /** name */
            name?: string;
            /**開始日*/
            startDate: string;
            /**check 申請承認の種類区分: 会社(1)　－　職場(2)　－　社員(3)*/
            check: number;
            /** まとめて設定モード(0) - 申請個別設定モード(1)*/
            mode: number;
            lstAppType: Array<number>;
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
            lstAppType: Array<number>;
            constructor(startDate: string,
                startDateOld: string,
                check: number,
                mode: number,
                copyDataFlag: boolean,
                lstAppType: Array<number>){
                    this.startDate = startDate;
                    this.startDateOld = startDateOld;
                    this.check = check;
                    this.mode = mode;
                    this.copyDataFlag = copyDataFlag;
                    this.lstAppType = lstAppType;
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
            /**check 申請承認の種類区分: 会社(0)　－　職場(1)　－　社員(2)*/
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
            name?: string
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
            overlapFlag?: boolean;
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
            workplaceId: string;
            lstCompanyRoot: Array<CompanyAppRootDto>;
            lstWorkplaceRoot: Array<WorkPlaceAppRootDto>;
            lstPersonRoot: Array<PersonAppRootDto>;
        }
        //data screen A
        export class CompanyAppRootDto{
            company: ComApprovalRootDto;
            lstAppPhase: Array<ApprovalPhaseDto>;
        }
        export class DataTreeB{
            approvalId: string;
            nameAppType: string;
            lstbyApp: Array<DataTree>;
            constructor(approvalId: string, nameAppType: string,lstbyApp: Array<DataTree>){
                this.approvalId = approvalId;
                this.nameAppType = nameAppType;
                this.lstbyApp = lstbyApp;
            }
        }
        //data check list left view model B
        export class DataCheckModeB{
            approvalId: string;
            startDate: string;
            endDate: string;
            applicationType: number;
            employmentRootAtr: number;
            constructor(approvalId: string, startDate: string, endDate: string, 
                applicationType: number, employmentRootAtr: number){
                    this.approvalId = approvalId;
                    this.startDate = startDate;
                    this.endDate = endDate;
                    this.applicationType = applicationType;
                    this.employmentRootAtr = employmentRootAtr;
            }
        }
        export class DataTree{
            approvalId: string;
            nameAppType: string;
            lstbyApp: Array<Com>;
            constructor(approvalId: string, nameAppType: string,lstbyApp: Array<Com>){
                this.approvalId = approvalId;
                this.nameAppType = nameAppType;
                this.lstbyApp = lstbyApp;
            }
        }
        export class Com{
            approvalId: string;
            nameAppType: string;
            constructor(approvalId: string, nameAppType: string){
                this.nameAppType = nameAppType;
                this.approvalId = approvalId;
            }
        }
        //data display
        export class ComRootDto{
            name: string;
            company: ComApprovalRootDto;
            lstAppPhase: Array<ApprovalPhaseDto>;
            constructor(name: string, company: ComApprovalRootDto, lstAppPhase: Array<ApprovalPhaseDto>){
                this.name = name;
                this.company = company;
                this.lstAppPhase = lstAppPhase;
            }
        }
        //list display right
        export class CompanyAppRootADto{
            color: boolean;
            common: boolean;
            appTypeValue: number;
            appTypeName: string;
            approvalId: string;
            historyId: string;
            branchId: string;
            appPhase1: ApprovalPhaseDto;
            appPhase2: ApprovalPhaseDto;
            appPhase3: ApprovalPhaseDto;
            appPhase4: ApprovalPhaseDto;
            appPhase5: ApprovalPhaseDto;
            constructor(color: boolean, 
            common: boolean,
            appTypeValue: number,
            appTypeName: string,
            approvalId: string,
            historyId: string,branchId: string,
            appPhase1: ApprovalPhaseDto, 
            appPhase2: ApprovalPhaseDto, 
            appPhase3: ApprovalPhaseDto,
            appPhase4: ApprovalPhaseDto, 
            appPhase5: ApprovalPhaseDto){
                this.color = color;
                this.common = common;
                this.appTypeValue = appTypeValue;
                this.appTypeName = appTypeName;
                this.approvalId =  approvalId;
                this.historyId = historyId;
                this.branchId = branchId;
                this.appPhase1 = appPhase1;
                this.appPhase2 = appPhase2;
                this.appPhase3 = appPhase3;
                this.appPhase4 = appPhase4;
                this.appPhase5 = appPhase5;
            }
        }
        //list check root < 14
        export class DataRootCheck{
            /**承認ID*/
            approvalId: string;
            /**履歴ID*/
            historyId: string;
            /**申請種類*/
            applicationType: number;
            /**就業ルート区分*/
            employmentRootAtr: number;
            branchId: string;
            lstAppPhase: Array<ApprovalPhaseDto>;
            constructor(approvalId: string, historyId: string,
                        applicationType: number, employmentRootAtr: number, branchId: string,
                        lstAppPhase: Array<ApprovalPhaseDto>){
                this.approvalId = approvalId;
                this.historyId = historyId;
                this.applicationType = applicationType;
                this.employmentRootAtr = employmentRootAtr;
                this.branchId =  branchId;
                this.lstAppPhase = lstAppPhase;
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
            /**承認形態 Name*/
            appFormName: string;
            /**閲覧フェーズ*/
            browsingPhase: number;
            /**順序*/
            orderNumber: number;
            constructor(approver: Array<ApproverDto>, branchId: string, approvalPhaseId: string, approvalForm: number, appFormName: string, browsingPhase: number, orderNumber: number){
                this.approver = approver;
                this.branchId = branchId;
                this.approvalPhaseId = approvalPhaseId;
                this.approvalForm = approvalForm;
                this.appFormName = appFormName;
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
            /**社員Name*/
            name: string;
            /**順序*/
            orderNumber: number;
            /**区分*/
            approvalAtr: number;
            /**確定者*/
            confirmPerson: number;
            constructor(approverId: string, jobTitleId: string,
                employeeId: string, name: string,orderNumber: number,
                approvalAtr: number, confirmPerson: number)
            {
                this.approverId = approverId;
                this.jobTitleId = jobTitleId;
                this.employeeId = employeeId;
                this.orderNumber = orderNumber;
                this.name = name;
                this.approvalAtr = approvalAtr;
                this.confirmPerson = confirmPerson;
            }
            
        }
        export class EmployeeKcp009{
                id: string;
                code: string;
                businessName: string;
                workplaceName: string;
                depName: string;
            constructor(id: string, code: string,
                businessName: string, workplaceName: string,depName: string)
            {
                this.id = id;
                this.code = code;
                this.businessName = businessName;
                this.workplaceName = workplaceName;
                this.depName = depName;
            }
            
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
        
        export class ApproverDtoK{
            id: string;
            code: string;
            name: string;
            constructor(id: string, code: string, name: string){
                this.id = id;
                this.code = code;
                this.name = name;    
            }
        } 
        //__________KCP009_________
        export interface ComponentOption {
            systemReference: SystemType;
            isDisplayOrganizationName: boolean;
            employeeInputList: KnockoutObservableArray<EmployeeKcp009>;
            targetBtnText: string;
            selectedItem: KnockoutObservable<string>;
            tabIndex: number;
        }
        export interface EmployeeModel {
            id: string;
            code: string;
            businessName: string;
            depName?: string;
            workplaceName?: string;
        }
        export class SystemType {
            static EMPLOYMENT = 1;
            static SALARY = 2;
            static PERSONNEL = 3;
            static ACCOUNTING = 4;
            static OH = 6;
        }
        export interface GroupOption {
            baseDate?: KnockoutObservable<Date>;
            // クイック検索タブ
            isQuickSearchTab: boolean;
            // 参照可能な社員すべて
            isAllReferableEmployee: boolean;
            //自分だけ
            isOnlyMe: boolean;
            //おなじ部門の社員
            isEmployeeOfWorkplace: boolean;
            //おなじ＋配下部門の社員
            isEmployeeWorkplaceFollow: boolean;
            // 詳細検索タブ
            isAdvancedSearchTab: boolean;
            //複数選択 
            isMutipleCheck: boolean;
            
            //社員指定タイプ or 全社員タイプ
            isSelectAllEmployee: boolean;
        
            onSearchAllClicked: (data: EmployeeSearchDto[]) => void;
        
            onSearchOnlyClicked: (data: EmployeeSearchDto) => void;
            
            onSearchOfWorkplaceClicked: (data: EmployeeSearchDto[]) => void;
            
            onSearchWorkplaceChildClicked: (data: EmployeeSearchDto[]) => void;
            
            onApplyEmployee: (data: EmployeeSearchDto[]) => void;
        }
        export interface EmployeeSearchDto {
            employeeId: string;
            
            employeeCode: string;
            
            employeeName: string;
            
            workplaceCode: string;
            
            workplaceId: string;
            
            workplaceName: string;
        }
        export enum RootType {
            COMPANY = 0,
            WORKPLACE = 1,
            PERSON = 2
        }
    }
}