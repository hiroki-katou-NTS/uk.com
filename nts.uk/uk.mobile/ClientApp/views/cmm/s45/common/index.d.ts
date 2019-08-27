import { IOvertime } from "views/cmm/s45/components/app1";

export interface IApprovalPhase {
    approvalAtrName: string;
    approvalAtrValue: number;
    listApprovalFrame: Array<IApprovalFrame>;
    phaseOrder: number;
}

export interface IApprovalFrame {
    approvalAtrName: string;
    approvalAtrValue: number;
    approvalReason: string;
    approverID: string;
    approverMail: string;
    approverName: string;
    frameOrder: number;
    listApprover: Array<IApprover>;
    phaseOrder: number;
    representerID: string;
    representerMail: string;
    representerName: string;
}

export interface IApprover {
    approverID: string;
    approverMail: string;
    approverName: string;
    representerID: string;
    representerMail: string;
    representerName: string;
}

export interface IAppInfo {
    id: string;
    appDate: Date;
    appType: number;
    appName: string;
    prePostAtr: number;
    reflectStatus: string;
    appStatusNo: number;
    frameStatus?: boolean;
    version?: number;
}

export interface AppListExtractConditionDto {
    //期間開始日付
    startDate: string;
    //期間終了日付
    endDate: string;
    //申請一覧区分
    appListAtr: number;
    //申請種類
    appType: number;
    //承認状況＿未承認
    unapprovalStatus: boolean;
    //承認状況＿承認済
    approvalStatus: boolean;
    //承認状況＿否認
    denialStatus: boolean;
    //承認状況＿代行承認済
    agentApprovalStatus: boolean;
    //承認状況＿差戻
    remandStatus: boolean;
    //承認状況＿取消
    cancelStatus: boolean;
    //申請表示対象
    appDisplayAtr: number;
    //社員IDリスト
    listEmployeeId: Array<string>;
    //社員絞込条件
    empRefineCondition: string;
}

export interface IApplication {
    listApprovalPhaseStateDto: Array<IApprovalPhase>;
    appStatus: number;
    reflectStatus: number;
    reversionReason: string;
    version: number;
    authorizableFlags: boolean;
	approvalATR: number;
    alternateExpiration: boolean;
    authorComment: string;
    appOvertime: IOvertime;
}

