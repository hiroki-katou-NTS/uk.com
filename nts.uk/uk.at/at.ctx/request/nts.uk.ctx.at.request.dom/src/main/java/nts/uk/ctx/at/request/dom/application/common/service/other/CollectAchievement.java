package nts.uk.ctx.at.request.dom.application.common.service.other;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.AchievementOutput;

/**
 * 13.実績を取得する
 * @author Doan Duy Hung
 *
 */
public interface CollectAchievement {
	
	/**
	 * 13.実績を取得する
	 * 実績の取得
	 * @param companyID
	 * @param applicantID
	 * @param appDate
	 * @return
	 */
	public AchievementOutput getAchievement(String companyID, String applicantID, GeneralDate appDate);
	
}
