package nts.uk.ctx.at.request.dom.application.common.service.other;

import java.util.List;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
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
	
	/**
	 * 実績内容の取得
	 * @param companyID 会社ID
	 * @param employeeID 社員ID
	 * @param dateLst 申請対象日リスト<Optional>
	 * @param appType 申請種類
	 * @return
	 */
	public List<AchievementOutput> getAchievementContents(String companyID, String employeeID, List<GeneralDate> dateLst, ApplicationType appType);
	
	/**
	 * 事前内容の取得
	 * @param companyID 会社ID
	 * @param employeeID 社員ID
	 * @param dateLst 申請対象日リスト<Optional>
	 * @param appType 申請種類
	 * @return
	 */
	public List<AppDetailContent> getPreAppContents(String companyID, String employeeID, List<GeneralDate> dateLst, ApplicationType appType);
	
}
