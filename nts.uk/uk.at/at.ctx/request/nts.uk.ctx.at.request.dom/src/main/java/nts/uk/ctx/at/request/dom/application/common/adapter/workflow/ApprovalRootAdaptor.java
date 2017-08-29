package nts.uk.ctx.at.request.dom.application.common.adapter.workflow;

import java.util.Date;
import java.util.List;

import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.CompanyAppRootAdaptorDto;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.PersonAppRootAdaptorDto;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.WkpAppRootAdaptorDto;

public interface ApprovalRootAdaptor {
	/**
	 * 1.社員の対象申請の承認ルートを取得する（共通以外）
	 * 
	 * @param cid 会社ID
	 * @param sid 社員ID（申請本人の社員ID）
	 * @param employmentRootAtr 就業ルート区分
	 * @param subjectRequest 対象申請
	 * @param standardDate 基準日
	 * @param appType 対象種類
	 * @return ApprovalRootDtos
	 */
	List<PersonAppRootAdaptorDto> findByBaseDate(String cid, String sid, Date standardDate, int appType);
	
	/**
	 * 1.社員の対象申請の承認ルートを取得する（共通）
	 * 
	 * @param cid 会社ID
	 * @param sid 社員ID（申請本人の社員ID）
	 * @param employmentRootAtr 就業ルート区分
	 * @param subjectRequest 対象申請
	 * @param standardDate 基準日
	 * @return ApprovalRootDtos
	 */
	List<PersonAppRootAdaptorDto> findByBaseDateOfCommon(String cid, String sid, Date standardDate);
	
	/**
	 * ドメインモデル「職場別就業承認ルート」を取得する(就業ルート区分(申請か、確認か、任意項目か))
	 * 
	 * @param cid 会社ID
	 * @param workPlaceId 職場ID
	 * @param baseDate 基準日
	 * @param appType 対象申請
	 * @return ApprovalRootDtos
	 */
	List<WkpAppRootAdaptorDto> findWkpByBaseDate(String cid, String workPlaceId, Date baseDate, int appType);
	
	/**
	 * ドメインモデル「職場別就業承認ルート」を取得する(共通)
	 * 
	 * @param cid 会社ID
	 * @param workPlaceId 職場ID
	 * @param baseDate 基準日
	 * @return ApprovalRootDtos
	 */
	List<WkpAppRootAdaptorDto> findWkpByBaseDateOfCommon(String cid, String workPlaceId, Date baseDate);
	
	/**
	 * ドメインモデル「会社別就業承認ルート」を取得する(就業ルート区分(申請か、確認か、任意項目か))
	 * 
	 * @param cid 会社ID
	 * @param baseDate 基準日
	 * @param appType 対象申請
	 * @return ApprovalRootDtos
	 */
	List<CompanyAppRootAdaptorDto> findCompanyByBaseDate(String cid, Date baseDate, int appType);
	
	/**
	 * ドメインモデル「会社別就業承認ルート」を取得する(共通)
	 * 
	 * @param cid 会社ID
	 * @param baseDate 基準日
	 * @return ApprovalRootDtos
	 */
	List<CompanyAppRootAdaptorDto> findCompanyByBaseDateOfCommon(String cid, Date baseDate);
}
