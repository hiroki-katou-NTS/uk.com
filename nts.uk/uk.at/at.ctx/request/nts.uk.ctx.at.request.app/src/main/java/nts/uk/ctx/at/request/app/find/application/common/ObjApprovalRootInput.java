package nts.uk.ctx.at.request.app.find.application.common;

import lombok.Value;
import nts.arc.time.GeneralDate;

@Value
public class ObjApprovalRootInput {
	/**
	 * 
	 * 
	 * @param cid 会社ID
	 * @param sid 社員ID（申請本人の社員ID）
	 * @param employmentRootAtr 就業ルート区分
	 * @param subjectRequest 対象申請
	 * @param baseDate 基準日
	 */
	 
	String sid; 
	int employmentRootAtr; 
	int appType;
	String standardDate;
}
