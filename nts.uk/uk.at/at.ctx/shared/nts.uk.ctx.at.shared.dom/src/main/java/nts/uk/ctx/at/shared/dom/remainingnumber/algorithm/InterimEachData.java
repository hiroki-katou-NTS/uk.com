package nts.uk.ctx.at.shared.dom.remainingnumber.algorithm;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.interim.InterimAbsMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.interim.InterimRecMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.interim.TmpAnnualHolidayMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.interim.InterimBreakMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.interim.InterimDayOffMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.InterimRemain;
import nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.interim.TmpResereLeaveMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialholidaymng.interim.InterimSpecialHolidayMng;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class InterimEachData {
	/**
	 * 振休か振出の暫定残数管理
	 */
	List<InterimRemain> interimMngAbsRec = new ArrayList<>();
	List<InterimAbsMng> useAbsMng = new ArrayList<>();
	List<InterimRecMng> useRecMng = new ArrayList<>();
	/**
	 * 休出か代休の暫定残数管理
	 */
	List<InterimRemain> interimMngBreakDayOff = new ArrayList<>();
	List<InterimBreakMng> breakMng = new ArrayList<>();
	List<InterimDayOffMng> dayOffMng = new ArrayList<>();
	/**
	 * 特別休暇の暫定残数管理
	 */
	List<InterimRemain> interimSpecial = new ArrayList<>();
	List<InterimSpecialHolidayMng> specialHolidayData = new ArrayList<>();
	/**
	 * 年休の暫定残数管理
	 */
	List<InterimRemain> annualMng = new ArrayList<>();
	List<TmpAnnualHolidayMng> annualHolidayData = new ArrayList<>();
	/**
	 * 積立年休の暫定残数管理
	 */
	List<InterimRemain> resereMng = new ArrayList<>();
	List<TmpResereLeaveMng> resereLeaveData = new ArrayList<>();
	
}
