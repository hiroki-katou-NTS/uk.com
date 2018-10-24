package nts.uk.ctx.at.record.dom.service;

import java.util.List;
import java.util.Map;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.monthly.erroralarm.EmployeeMonthlyPerError;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.interim.InterimAbsMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.interim.InterimRecMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.DailyInterimRemainMngData;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.interim.TmpAnnualHolidayMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.interim.InterimBreakMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.interim.InterimDayOffMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.InterimRemain;
import nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.interim.TmpResereLeaveMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialholidaymng.interim.InterimSpecialHolidayMng;

public interface TimeOffRemainErrorInfor {
	/**
	 * 休暇残数エラーの取得
	 * @param param
	 * @return 社員の月別実績エラー一覧の情報
	 */
	List<EmployeeMonthlyPerError> getErrorInfor(TimeOffRemainErrorInputParam param);
	/**
	 * 年休残数のチェック
	 * @param interimRemainData
	 * @return
	 */
	List<EmployeeMonthlyPerError> annualData(TimeOffRemainErrorInputParam param, List<InterimRemain> annualMng, 
			List<TmpAnnualHolidayMng> annualHolidayData,
			List<InterimRemain> resereMng,List<TmpResereLeaveMng> resereLeaveData);
	/**
	 * 特休残数のチェック
	 * @param param
	 * @param interimSpecial
	 * @param specialHolidayData
	 * @return
	 */
	List<EmployeeMonthlyPerError> specialData(TimeOffRemainErrorInputParam param, List<InterimRemain> interimSpecial,
			List<InterimSpecialHolidayMng> specialHolidayData);
	/**
	 * 振休残数のチェック
	 * @param param
	 * @param interimMngAbsRec
	 * @param useAbsMng
	 * @param useRecMng
	 * @return
	 */
	List<EmployeeMonthlyPerError> absRecData(TimeOffRemainErrorInputParam param, List<InterimRemain> interimMngAbsRec,
			List<InterimAbsMng> useAbsMng, List<InterimRecMng> useRecMng);
	/**
	 * 代休残数のチェック
	 * @param param
	 * @param interimMngBreakDayOff
	 * @param breakMng
	 * @param dayOffMng
	 * @return
	 */
	List<EmployeeMonthlyPerError> dayoffData(TimeOffRemainErrorInputParam param, List<InterimRemain> interimMngBreakDayOff,
			List<InterimBreakMng> breakMng, List<InterimDayOffMng> dayOffMng);
}
