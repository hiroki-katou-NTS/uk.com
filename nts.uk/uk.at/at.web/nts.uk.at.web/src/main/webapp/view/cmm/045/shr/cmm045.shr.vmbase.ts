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
			/** 期間開始日 */
			periodStartDate: string;
			/** 期間終了日 */
			periodEndDate: string;
			/** 事後出力 */
			postOutput: boolean;
			/** 事前出力 */
			preOutput: boolean;
			/** 申請一覧区分 */
			appListAtr: number;
			/** 申請表示順 */
			appDisplayOrder: number;
			/** 表の幅登録 */
			tableWidthRegis: boolean;
			/** 社員IDリスト */
			opListEmployeeID: Array<string>;
			/** 承認状況＿差戻 */
			opRemandStatus: boolean;
			/** 承認状況＿取消 */
			opCancelStatus: boolean;
			/** 承認状況＿承認済 */
			opApprovalStatus: boolean;
			/** 承認状況＿代行承認済 */
			opAgentApprovalStatus: boolean;
			/** 承認状況＿否認 */
			opDenialStatus: boolean;
			/** 承認状況＿未承認 */
			opUnapprovalStatus: boolean;
			/** 申請種類 */
			opAppTypeLst: Array<any>;
			/** 申請種類リスト */
			opListOfAppTypes: Array<any>;

			constructor(periodStartDate: string, periodEndDate: string, postOutput: boolean, preOutput: boolean, appListAtr: number,
				appDisplayOrder: number, tableWidthRegis: boolean, opListEmployeeID: Array<string>, opRemandStatus: boolean,
				opCancelStatus: boolean, opApprovalStatus: boolean, opAgentApprovalStatus: boolean, opDenialStatus: boolean,
				opUnapprovalStatus: boolean, opAppTypeLst: Array<any>, opListOfAppTypes: Array<any>) {
					this.periodStartDate = periodStartDate;
					this.periodEndDate = periodEndDate;
					this.postOutput = postOutput;
					this.preOutput = preOutput;
					this.appListAtr = appListAtr;
					this.appDisplayOrder = appDisplayOrder;
					this.tableWidthRegis = tableWidthRegis;
					this.opListEmployeeID = opListEmployeeID;
					this.opRemandStatus = opRemandStatus;
					this.opCancelStatus = opCancelStatus;
					this.opApprovalStatus = opApprovalStatus;
					this.opAgentApprovalStatus = opAgentApprovalStatus;
					this.opDenialStatus = opDenialStatus;
					this.opUnapprovalStatus = opUnapprovalStatus;
					this.opAppTypeLst = opAppTypeLst;
					this.opListOfAppTypes = opListOfAppTypes;
			}

//            /**期間開始日付*/
//            startDate: string;
//            /**期間終了日付*/
//            endDate: string;
//            /**申請一覧区分*/
//            appListAtr: number;
//            /**申請種類*/
//            appType: number;
//            /**承認状況＿未承認*/
//            unapprovalStatus: boolean;
//            /**承認状況＿承認済*/
//            approvalStatus: boolean;
//            /**承認状況＿否認*/
//            denialStatus: boolean;
//            /**承認状況＿代行承認済*/
//            agentApprovalStatus: boolean;
//            /**承認状況＿差戻*/
//            remandStatus: boolean;
//            /**承認状況＿取消*/
//            cancelStatus: boolean;
//            /**申請表示対象*/
//            appDisplayAtr: number;
//            /**社員IDリスト*/
//            listEmployeeId: Array<string>;
//            /**社員絞込条件*/
//            empRefineCondition: string;
//            constructor(startDate: string, endDate: string, appListAtr: number,
//                appType: number, unapprovalStatus: boolean, approvalStatus: boolean,
//                denialStatus: boolean, agentApprovalStatus: boolean, remandStatus: boolean,
//                cancelStatus: boolean, appDisplayAtr: number, listEmployeeId: Array<string>,
//                empRefineCondition: string){
//                    this.startDate = startDate;
//                    this.endDate =  endDate;
//                    this.appListAtr =  appListAtr;
//                    this.appType = appType;
//                    this.unapprovalStatus = unapprovalStatus;
//                    this.approvalStatus = approvalStatus;
//                    this.denialStatus = denialStatus;
//                    this.agentApprovalStatus = agentApprovalStatus;
//                    this.remandStatus = remandStatus;
//                    this.cancelStatus = cancelStatus;
//                    this.appDisplayAtr = appDisplayAtr;
//                    this.listEmployeeId = listEmployeeId;
//                    this.empRefineCondition = empRefineCondition;

//            }
//            setAppType(appType: number){
//                  this.appType = appType;
//            }
        }
        interface AppListParamFilter{
            condition: AppListExtractConditionDto;//抽出条件
            spr: boolean;//SPRから呼ぶ？
            extractCondition: number;//選択申請種類
            device: number;
            lstAppType: Array<number>;
        }
        //data fill grid list mode application
        export class DataModeApp{
			/** 事前事後区分 */
			prePostAtr: number;
			/** 職場名 */
			workplaceName: string;
			/** 申請 */
			application: any;
			/** 申請ID */
			appID: string;
			/** 申請者CD */
			applicantCD: string;
			/** 申請者ID */
			applicantID: string;
			/** 申請者名 */
			applicantName: string;
			/** 申請種類 */
			appType: number;
			/** 申請内容 */
			appContent: string;
			/** 申請日 */
			appDate: string;
			/** 入力社名 */
			inputCompanyName: string;
			/** 入力日 */
			inputDate: string;
			/** 反映状態 */
			reflectionStatus: string;
			/** 時刻計算利用区分 */
			opTimeCalcUseAtr: number;
			/** 承認フェーズインスタンス */
			opApprovalPhaseLst: Array<any>;
			/** 承認状況照会 */
			opApprovalStatusInquiry: string;
			/** 承認枠の承認状態 */
			opApprovalFrameStatus: number;
			/** 振休振出申請 */
			opComplementLeaveApp: any;
			/** 申請開始日 */
			opAppStartDate: string;
			/** 申請種類表示 */
			opAppTypeDisplay: number;
			/** 申請終了日 */
			opAppEndDate: string;
			/** 定型理由 */
			opAppStandardReason: string;
			/** 入力者名称 */
			opEntererName: string;
			/** 背景色 */
			opBackgroundColor: number;
			/** 表示行数超 */
			opMoreThanDispLineNO: boolean;

			// param dùng cho grid ở UI
            check: boolean;
            details: string;
            appName: string;
            appAtr: string;
            checkAtr: boolean;
            version: number;
            checkTimecolor: number;
            appIdSub: string;
            appStatusNo: number;
            constructor(listOfApplicationDto: ListOfApplicationDto){
				this.prePostAtr = listOfApplicationDto.prePostAtr;
				this.workplaceName = listOfApplicationDto.workplaceName;
				this.application = listOfApplicationDto.application;
				this.appID = listOfApplicationDto.appID;
				this.applicantCD = listOfApplicationDto.applicantCD;
				this.applicantID = listOfApplicationDto.applicantID;
				this.applicantName = listOfApplicationDto.applicantName;
				this.appType = listOfApplicationDto.appType;
				this.appContent = listOfApplicationDto.appContent;
				this.appDate = listOfApplicationDto.appDate;
				this.inputCompanyName = listOfApplicationDto.inputCompanyName;
				this.inputDate = listOfApplicationDto.inputDate;
				this.reflectionStatus = listOfApplicationDto.reflectionStatus;
				this.opTimeCalcUseAtr = listOfApplicationDto.opTimeCalcUseAtr;
				this.opApprovalPhaseLst = listOfApplicationDto.opApprovalPhaseLst;
				this.opApprovalStatusInquiry = listOfApplicationDto.opApprovalStatusInquiry;
				this.opApprovalFrameStatus = listOfApplicationDto.opApprovalFrameStatus;
				this.opComplementLeaveApp = listOfApplicationDto.opComplementLeaveApp;
				this.opAppStartDate = listOfApplicationDto.opAppStartDate;
				this.opAppTypeDisplay = listOfApplicationDto.opAppTypeDisplay;
				this.opAppEndDate = listOfApplicationDto.opAppEndDate;
				this.opAppStandardReason = listOfApplicationDto.opAppStandardReason;
				this.opEntererName = listOfApplicationDto.opEntererName;
				this.opBackgroundColor = listOfApplicationDto.opBackgroundColor;
				this.opMoreThanDispLineNO = listOfApplicationDto.opMoreThanDispLineNO;
				// param dùng cho grid ở UI
		        this.check = false;
		        this.details = '';
		        this.appName = '';
		        this.appAtr = '';
		        this.checkAtr = this.reflectionStatus == 'CMM045_62' ? true : false;
		        this.version = 0;
		        this.checkTimecolor = 0;
		        this.appIdSub = '';
		        this.appStatusNo = 0;
            }
        }

		export interface ListOfApplicationDto {
			/** 事前事後区分 */
			prePostAtr: number;
			/** 職場名 */
			workplaceName: string;
			/** 申請 */
			application: any;
			/** 申請ID */
			appID: string;
			/** 申請者CD */
			applicantCD: string;
			/** 申請者ID */
			applicantID: string;
			/** 申請者名 */
			applicantName: string;
			/** 申請種類 */
			appType: number;
			/** 申請内容 */
			appContent: string;
			/** 申請日 */
			appDate: string;
			/** 入力社名 */
			inputCompanyName: string;
			/** 入力日 */
			inputDate: string;
			/** 反映状態 */
			reflectionStatus: string;
			/** 時刻計算利用区分 */
			opTimeCalcUseAtr: number;
			/** 承認フェーズインスタンス */
			opApprovalPhaseLst: Array<any>;
			/** 承認状況照会 */
			opApprovalStatusInquiry: string;
			/** 承認枠の承認状態 */
			opApprovalFrameStatus: number;
			/** 振休振出申請 */
			opComplementLeaveApp: any;
			/** 申請開始日 */
			opAppStartDate: string;
			/** 申請種類表示 */
			opAppTypeDisplay: number;
			/** 申請終了日 */
			opAppEndDate: string;
			/** 定型理由 */
			opAppStandardReason: string;
			/** 入力者名称 */
			opEntererName: string;
			/** 背景色 */
			opBackgroundColor: number;
			/** 表示行数超 */
			opMoreThanDispLineNO: boolean;
		}

        export class AppMasterInfo {
            appID: string;
            appType: number;
            dispName: string;
            empName: string;
            inpEmpName: string;
            workplaceName: string;
            statusFrameAtr: boolean;
            phaseStatus: string;
            //事前、事後の後ろに#CMM045_101(※)を追加
            checkAddNote: boolean;
            checkTimecolor: number;
            //ver14 + EA1360
            detailSet: number;
            constructor(appID: string, appType: number, dispName: string, empName: string, inpEmpName: string,
            workplaceName: string, statusFrameAtr: boolean, phaseStatus: string, checkAddNote: boolean,
            checkTimecolor: number, detailSet: number)
            {
                this.appID = appID;
                this.appType = appType;
                this.dispName = dispName;
                this.empName = empName;
                this.inpEmpName = inpEmpName;
                this.workplaceName = workplaceName;
                this.statusFrameAtr = statusFrameAtr;
                this.phaseStatus = phaseStatus;
                this.checkAddNote = checkAddNote;
                this.checkTimecolor = checkTimecolor;
                this.detailSet = detailSet;
            }
        }
        export class ApplicationDataOutput{
            // 申請ID
            applicationID: string;
            // 事前事後区分
            prePostAtr: number;
            // 入力日
            inputDate: string;
            // 入力者
            enteredPersonSID: string;
            // 申請日
            applicationDate: string;
            // 申請種類
            applicationType: number;
            // 申請者
            applicantSID: string;
            //反映状態
            reflectStatus: string;
            startDate: string;
            endDate: string;
            version: number;
            reflectPerState: number;
            constructor(applicationID: string, prePostAtr: number, inputDate: string, enteredPersonSID: string,
                applicationDate: string, applicationType: number,
                applicantSID: string, reflectPerState: number,
                startDate: string, endDate: string, version: number, reflectStatus: string)
            {
                this.applicationID = applicationID;
                this.prePostAtr = prePostAtr;
                this.inputDate = inputDate;
                this.enteredPersonSID = enteredPersonSID;
                this.applicationDate = applicationDate;
                this.applicationType = applicationType;
                this.applicantSID = applicantSID;
                this.reflectPerState = reflectPerState;
                this.startDate = startDate;
                this.endDate = endDate;
                this.version = version;
                this.reflectStatus = reflectStatus;
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
                this.unApprovalNumber = getText('CMM045_18', [unApprovalNumber]);
                this.approvalNumber = getText('CMM045_18', [approvalNumber]);
                this.approvalAgentNumber = getText('CMM045_18', [denialNumber]);
                this.cancelNumber = getText('CMM045_18', [approvalAgentNumber]);
                this.remandNumner = getText('CMM045_18', [remandNumner]);
                this.denialNumber = getText('CMM045_18', [cancelNumber]);
            }
        }
        export class ChoseApplicationList{
            appType: string;
            appName: string;
            constructor(appType: string, appName: string){
                this.appType = appType;
                this.appName = appName;
            }
        }
        export interface Date{
            startDate: string;
            endDate: string;
        }
        export class ApproveAgent{
            appID: string;
            agentId: string;
            constructor(appID: string, agentId: string){
                this.appID = appID;
                this.agentId = agentId;
            }
        }
        export class AppAbsRecSyncData {
            //0 - abs
            //1 - rec
            typeApp: number;
            appMainID: string;
            appSubID: string;
            appDateSub: string;
            constructor(typeApp: number, appMainID: string,
                appSubID: string, appDateSub: string){
                this.typeApp = typeApp;
                this.appMainID = appMainID;
                this.appSubID = appSubID;
                this.appDateSub = appDateSub;
            }
        }
        export class CellState {
            rowId: number;
            columnKey: string;
            state: Array<any>
            constructor(rowId: any, columnKey: string, state: Array<any>) {
                this.rowId = rowId;
                this.columnKey = columnKey;
                this.state = state;
            }
        }
        export class TextColor {
            rowId: number;
            columnKey: string;
            color: string;
            constructor(rowId: any, columnKey: string, color: string) {
                this.rowId = rowId;
                this.columnKey = columnKey;
                this.color = color;
            }
        }
        export interface IntefaceSPR{
            mode: number;//1=承認一覧
            startDate: string;//yyyy-mm-dd //期間（開始日）
            endDate: string;//yyyy-mm-dd //期間（終了日）
            extractCondition: number;//０＝全て、１＝早出・普通残業のみ
            agreementTime36: number;//０＝表示しない、1＝表示する
		}
		export class columnWidth {
			appLstAtr: boolean;
			width: number;
			cID: string;
			sID: string;

			constructor(appLstAtr: boolean, width: number, cID: string, sID: string) {
				this.appLstAtr = appLstAtr;
				this.width = width;
				this.cID = cID;
				this.sID = sID;
			}
		}
    }
}