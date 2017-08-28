package nts.uk.ctx.at.request.dom.application.common.approvalroot.subjectrequest.impls;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;

import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.request.dom.application.common.approvalroot.subjectrequest.services.GetApprovalRootService;

@Stateless
public class GetApprovalRootServiceImpl implements GetApprovalRootService {

	/**
	 * 1.社員の対象申請の承認ルートを取得する
	 * 
	 * @param cid 会社ID
	 * @param sid 社員ID（申請本人の社員ID）
	 * @param employmentRootAtr 就業ルート区分
	 * @param subjectRequest 対象申請
	 * @param standardDate 基準日
	 */
	@Override
	public void getApprovalRootOfSubjectRequest(
			String cid, String sid, int employmentRootAtr, 
			String subjectRequest,Date standardDate) {
		
		// get 個人別就業承認ルート from workflow
		List<String> sample = new ArrayList<String>();
		if (CollectionUtil.isEmpty(sample)) {
			// get 個人別就業承認ルート from workflow by other conditions
			List<String> sample2 = new ArrayList<String>();
			if (CollectionUtil.isEmpty(sample2)) {
				
				// 所属職場を含む上位職場を取得
				
			}else {
				// 2.承認ルートを整理する
			}
			
		}else {
			// 2.承認ルートを整理する
		}
	}

	
}
