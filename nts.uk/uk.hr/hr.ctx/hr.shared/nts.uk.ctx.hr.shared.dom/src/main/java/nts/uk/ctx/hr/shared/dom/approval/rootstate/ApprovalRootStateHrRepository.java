package nts.uk.ctx.hr.shared.dom.approval.rootstate;

import java.util.Optional;

import nts.arc.time.GeneralDate;

public interface ApprovalRootStateHrRepository {
	
    /**
     * Imp [No.309]承認ルートを取得する cua hungnv
     * Phần đối ứng cho bên Jinji (人事)
     * 1.社員の対象申請の承認ルートを取得する
     * @param 会社ID companyID
     * @param 社員ID employeeID
     * @param ・対象申請 targetType
     * @param 基準日 date
     * @param Optional<下位序列承認無＞ lowerApprove
     * @return
     */
	public ApprovalRootContentHrExport getApprovalRootHr(String companyID, String employeeID, String targetType, GeneralDate date, Optional<Boolean> lowerApprove);


	/**
	 * Imp [RQ637]承認ルートインスタンスを新規作成する của hoatt
	 * @author laitv
	 * @param 人事承認状態 apprSttHr
	 * @return エラーフラグ　＝　True 失敗した場合 
	 * 		        エラーフラグ　＝　False OK場合 
	 */
	public boolean createApprStateHr(ApprovalRootContentHrExport apprSttHr, String rootStateID, String employeeID, GeneralDate appDate);

}
