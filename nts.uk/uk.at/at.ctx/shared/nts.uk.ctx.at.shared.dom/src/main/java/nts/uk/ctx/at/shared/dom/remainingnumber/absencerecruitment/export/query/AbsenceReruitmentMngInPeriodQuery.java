package nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query;

import java.util.List;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.paymana.SubstitutionOfHDManagementData;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

public interface AbsenceReruitmentMngInPeriodQuery {
	/**
	 * 期間内の振出振休残数を取得する
	 * @param cid
	 * @param sid
	 * @param dateData
	 * @param baseDate
	 * @param mode: True: 月次か, false: その他か
	 * @return
	 */
	AbsRecRemainMngOfInPeriod getAbsRecMngInPeriod(String cid, String sid, DatePeriod dateData, GeneralDate baseDate, boolean mode);
	/**
	 * 1.未相殺の振休(確定)を取得する
	 * @param sid
	 * @return
	 */
	List<AbsRecDetailPara> getAbsOfUnOffset(String sid);
	/**
	 * 1-1.確定振休から未相殺の振休を取得する
	 * @param sid
	 * @return
	 */
	List<SubstitutionOfHDManagementData> getAbsOfUnOffsetFromConfirm(String sid);
	/**
	 * 1-3.暫定振出と紐付けをしない確定振休を取得する
	 * @param confirmAbsData
	 * @return
	 */
	AbsRecDetailPara getInterimAndConfirmAbs(String sid, SubstitutionOfHDManagementData confirmAbsData);
}
