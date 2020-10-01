package nts.uk.ctx.at.request.pub.setting.applicationreason;

import java.util.List;

public interface ApplicationReasonPub {

	/**
	 * 申請定型理由を取得する
	 * 
	 * @param companyId
	 * @param lstAppType
	 * @return
	 */
	List<ApplicationReasonExport> getReasonByAppType(String companyId, List<Integer> lstAppType);
}
