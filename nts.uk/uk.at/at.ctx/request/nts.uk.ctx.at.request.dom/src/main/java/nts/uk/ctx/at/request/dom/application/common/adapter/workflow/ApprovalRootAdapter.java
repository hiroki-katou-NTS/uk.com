package nts.uk.ctx.at.request.dom.application.common.adapter.workflow;

import java.util.List;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApprovalPhaseImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ComApprovalRootImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.PersonApprovalRootImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.WkpApprovalRootImport;

/**
 * 
 * @author vunv
 *
 */
public interface ApprovalRootAdapter {
	/**
	 * 1.社員の対象申請の承認ルートを取得する（共通以外）
	 * 
	 * @param cid 会社ID
	 * @param sid 社員ID（申請本人の社員ID）
	 * @param employmentRootAtr 就業ルート区分
	 * @param subjectRequest 対象申請
	 * @param standardGeneralDate 基準日
	 * @param appType 対象種類
	 * @return ApprovalRootDtos
	 */
	List<PersonApprovalRootImport> findByBaseDate(String cid, String sid, GeneralDate standardDate, int appType);
	
	/**
	 * 1.社員の対象申請の承認ルートを取得する（共通）
	 * 
	 * @param cid 会社ID
	 * @param sid 社員ID（申請本人の社員ID）
	 * @param employmentRootAtr 就業ルート区分
	 * @param subjectRequest 対象申請
	 * @param standardGeneralDate 基準日
	 * @return ApprovalRootDtos
	 */
	List<PersonApprovalRootImport> findByBaseDateOfCommon(String cid, String sid, GeneralDate standardDate);
	
	/**
	 * ドメインモデル「職場別就業承認ルート」を取得する(就業ルート区分(申請か、確認か、任意項目か))
	 * 
	 * @param cid 会社ID
	 * @param workPlaceId 職場ID
	 * @param baseGeneralDate 基準日
	 * @param appType 対象申請
	 * @return ApprovalRootDtos
	 */
	List<WkpApprovalRootImport> findWkpByBaseDate(String cid, String workPlaceId, GeneralDate baseDate, int appType);
	
	/**
	 * ドメインモデル「職場別就業承認ルート」を取得する(共通)
	 * 
	 * @param cid 会社ID
	 * @param workPlaceId 職場ID
	 * @param baseGeneralDate 基準日
	 * @return ApprovalRootDtos
	 */
	List<WkpApprovalRootImport> findWkpByBaseDateOfCommon(String cid, String workPlaceId, GeneralDate baseDate);
	
	/**
	 * ドメインモデル「会社別就業承認ルート」を取得する(就業ルート区分(申請か、確認か、任意項目か))
	 * 
	 * @param cid 会社ID
	 * @param baseGeneralDate 基準日
	 * @param appType 対象申請
	 * @return ApprovalRootDtos
	 */
	List<ComApprovalRootImport> findCompanyByBaseDate(String cid, GeneralDate baseDate, int appType);
	
	/**
	 * ドメインモデル「会社別就業承認ルート」を取得する(共通)
	 * 
	 * @param cid 会社ID
	 * @param baseGeneralDate 基準日
	 * @return ApprovalRootDtos
	 */
	List<ComApprovalRootImport> findCompanyByBaseDateOfCommon(String cid, GeneralDate baseDate);
	
	/**
	 * 
	 * @param cid
	 * @param branchId
	 * @return
	 */
	List<ApprovalPhaseImport> findApprovalPhaseByBranchId(String cid, String branchId);
}
