package nts.uk.ctx.at.shared.dom.workrule.shiftmaster;

import nts.arc.time.GeneralDate;

import java.util.List;

public interface AffWorkplaceAdapter {
	public List<String> getWKPID(String CID, String WKPGRPID);
	List<String> getUpperWorkplace(String companyID, String workplaceID, GeneralDate date);

	/**
	 * [No.571]職場の上位職場を基準職場を含めて取得する
	 *
	 * @param companyId
	 * @param baseDate
	 * @param workplaceId
	 * @return
	 */
	public List<String> getWorkplaceIdAndUpper(String companyId, GeneralDate baseDate, String workplaceId);

}
