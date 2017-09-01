package nts.uk.ctx.workflow.pub.approvalroot;

import java.util.Date;
import java.util.List;

import nts.uk.ctx.workflow.pub.approvalroot.export.ApprovalPhaseExport;
import nts.uk.ctx.workflow.pub.approvalroot.export.CompanyApprovalRootExport;
import nts.uk.ctx.workflow.pub.approvalroot.export.PersonApprovalRootExport;
import nts.uk.ctx.workflow.pub.approvalroot.export.WkpApprovalRootExport;

public interface ApprovalRootPub {
	/**
	 * 1.社員の対象申請の承認ルートを取得する（共通以外）
	 * 
	 * @param cid 会社ID
	 * @param sid 社員ID（申請本人の社員ID）
	 * @param baseDate 基準日
	 * @param appType 対象申請
	 * @return ApprovalRootDtos
	 */
	List<PersonApprovalRootExport> findByBaseDate(String cid, String sid, Date baseDate, int appType);
	
	/**
	 * 1.社員の対象申請の承認ルートを取得する(共通）
	 * 
	 * @param cid 会社ID
	 * @param sid 社員ID（申請本人の社員ID）
	 * @param baseDate 基準日
	 * @return ApprovalRootDtos
	 */
	List<PersonApprovalRootExport> findByBaseDateOfCommon(String cid, String sid, Date baseDate);
	
	/**
	 * ドメインモデル「職場別就業承認ルート」を取得する(就業ルート区分(申請か、確認か、任意項目か))
	 * 
	 * @param cid 会社ID
	 * @param workplaceId 職場ID
	 * @param baseDate 基準日
	 * @param appType 対象申請
	 * @return ApprovalRootDtos
	 */
	List<WkpApprovalRootExport> findWkpByBaseDate(String cid, String workplaceId, Date baseDate, int appType);
	
	/**
	 * ドメインモデル「職場別就業承認ルート」を取得する(共通)
	 * 
	 * @param cid 会社ID
	 * @param workplaceId 職場ID
	 * @param subjectRequest 対象申請
	 * @param baseDate 基準日
	 * @return ApprovalRootDtos
	 */
	List<WkpApprovalRootExport> findWkpByBaseDateOfCommon(String cid, String workplaceId, Date baseDate);
	
	/**
	 * ドメインモデル「会社別就業承認ルート」を取得する(就業ルート区分(申請か、確認か、任意項目か))
	 * 
	 * @param cid 会社ID
	 * @param baseDate 基準日
	 * @param appType 対象申請
	 * @return ApprovalRootDtos
	 */
	List<CompanyApprovalRootExport> findCompanyByBaseDate(String cid, Date baseDate, int appType);
	
	/**
	 * ドメインモデル「会社別就業承認ルート」を取得する(共通)
	 * 
	 * @param cid 会社ID
	 * @param baseDate 基準日
	 * @return ApprovalRootDtos
	 */
	List<CompanyApprovalRootExport> findCompanyByBaseDateOfCommon(String cid, Date baseDate);
	
	/**
	 * 
	 * @param cid
	 * @param branchId
	 * @return
	 */
	List<ApprovalPhaseExport> findApprovalPhaseByBranchId(String cid, String branchId);
}
