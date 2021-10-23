package nts.uk.ctx.at.shared.dom.remainingnumber.algorithm;
/**
 * 暫定残数データ
 * @author do_dt
 *
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.interimdata.TempPublicHolidayManagement;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.interim.InterimAbsMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.interim.InterimRecMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.interim.TempAnnualLeaveMngs;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.interim.InterimBreakMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.interim.InterimDayOffMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.InterimRemain;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.care.interimdata.TempCareManagement;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.childcare.interimdata.TempChildCareManagement;
import nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.interim.TmpResereLeaveMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialholidaymng.interim.InterimSpecialHolidayMng;
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class DailyInterimRemainMngData {

	private GeneralDate ymd;

	/**暫定振休管理データ */
	private Optional<InterimAbsMng> interimAbsData;
	/**	暫定残数管理データ */
	private List<InterimRemain> recAbsData;
	/**
	 * 暫定振出管理データ
	 */
	private Optional<InterimRecMng> recData;
	/**
	 * 暫定代休管理データ
	 */
	private List<InterimDayOffMng> dayOffData;
	/**
	 * 暫定年休管理データ
	 */
	private List<TempAnnualLeaveMngs> annualHolidayData;
	/**
	 * 暫定積立年休管理データ
	 */
	private Optional<TmpResereLeaveMng> resereData;
	/**
	 * 暫定休出管理データ
	 */
	private Optional<InterimBreakMng> breakData;
	/**
	 * 暫定特別休暇データ
	 */
	private List<InterimSpecialHolidayMng> specialHolidayData;
	/**
	 * 暫定子の看護休暇データ
	 */
	private List<TempChildCareManagement> childCareData;
	/**
	 * 暫定介護休暇データ
	 */
	private List<TempCareManagement> careData;
	/**
	 * 暫定公休管理データ
	 */
	private List<TempPublicHolidayManagement> publicHolidayData;


	public static DailyInterimRemainMngData createEmpty(GeneralDate ymd) {
		DailyInterimRemainMngData dom = new DailyInterimRemainMngData();
		dom.ymd = ymd;
		dom.interimAbsData=Optional.empty();
		dom.recAbsData = new ArrayList<>();
		dom.recData = Optional.empty();
		dom.dayOffData = new ArrayList<>();
		dom.annualHolidayData = new ArrayList<>();
		dom.resereData = Optional.empty();
		dom.breakData = Optional.empty();
		dom.specialHolidayData = new ArrayList<>();
		dom.childCareData = new ArrayList<>();
		dom.careData = new ArrayList<>();
		dom.publicHolidayData = new ArrayList<>();

		return dom;
	}
}
