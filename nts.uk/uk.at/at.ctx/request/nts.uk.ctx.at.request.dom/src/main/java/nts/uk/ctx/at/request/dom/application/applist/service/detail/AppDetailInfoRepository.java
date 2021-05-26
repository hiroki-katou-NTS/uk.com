package nts.uk.ctx.at.request.dom.application.applist.service.detail;

import java.util.List;

import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;

public interface AppDetailInfoRepository {
	/**
	 * convert time from integer to Time_Short_HM
	 * @param time
	 * @return
	 */
	public String convertTime(Integer time);
	/**
	 * 勤務就業名称を作成 - WorkType
	 * @param lstWkType
	 * @param wkTypeCd
	 * @return
	 */
	public String findWorkTypeName(List<WorkType> lstWkType, String wkTypeCd);
	/**
	 * 勤務就業名称を作成 - WorkTime
	 * @param lstWkTime
	 * @param wkTimeCd
	 * @return
	 */
	public String findWorkTimeName(List<WorkTimeSetting> lstWkTime, String wkTimeCd);
}
