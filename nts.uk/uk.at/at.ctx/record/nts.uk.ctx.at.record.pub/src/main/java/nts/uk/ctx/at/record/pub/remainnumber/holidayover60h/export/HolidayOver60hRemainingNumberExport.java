package nts.uk.ctx.at.record.pub.remainnumber.holidayover60h.export;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.record.dom.monthly.vacation.holidayover60h.HolidayOver60h;
import nts.uk.ctx.at.record.dom.remainingnumber.holidayover60h.export.param.HolidayOver60hRemainingNumber;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata.daynumber.AnnualLeaveRemainingTime;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata.daynumber.AnnualLeaveUsedTime;

/**
 * 60H超休情報残数
 * @author masaaki_jinno
 *
 */
@Getter
@Setter
public class HolidayOver60hRemainingNumberExport {

	/**
	 * 60H超休（マイナスあり）
	 */
	private HolidayOver60hExport holidayOver60hWithMinus;

	/**
	 * 60H超休（マイナスなし）
	 */
	private HolidayOver60hExport holidayOver60hNoMinus;

	/**
	 * 繰越時間
	*/
	private Integer carryForwardTimes;

	/**
	 * 未消化数
	*/
	private Integer holidayOver60hUndigestNumber;

	/**
	   * ドメインから変換
	 * @param HolidayOver60hRemainingNumber
	 * @return
	 */
	static public HolidayOver60hRemainingNumberExport of(
			HolidayOver60hRemainingNumber holidayOver60hRemainingNumber) {

		HolidayOver60hRemainingNumberExport export
			= new HolidayOver60hRemainingNumberExport();
		export.holidayOver60hWithMinus = HolidayOver60hExport.of(holidayOver60hRemainingNumber.getHolidayOver60hWithMinus());
		export.holidayOver60hNoMinus = HolidayOver60hExport.of(holidayOver60hRemainingNumber.getHolidayOver60hNoMinus());
		export.carryForwardTimes = holidayOver60hRemainingNumber.getCarryForwardTimes().v();
		export.holidayOver60hUndigestNumber = holidayOver60hRemainingNumber.getHolidayOver60hUndigestNumber().v();
		return export;
	}

	/**
	   * ドメインへ変換
	 * @param holidayOver60h
	 * @return
	 */
	static public HolidayOver60hRemainingNumber toDomain(
			HolidayOver60hRemainingNumberExport holidayOver60hRemainingNumberExport) {

		HolidayOver60hRemainingNumber domain = new HolidayOver60hRemainingNumber();
		domain.setHolidayOver60hWithMinus(
				HolidayOver60hExport.toDomain(holidayOver60hRemainingNumberExport.getHolidayOver60hWithMinus()));
		domain.setHolidayOver60hNoMinus(
				HolidayOver60hExport.toDomain(holidayOver60hRemainingNumberExport.getHolidayOver60hNoMinus()));
		domain.setCarryForwardTimes(
				new AnnualLeaveRemainingTime(holidayOver60hRemainingNumberExport.getCarryForwardTimes()));
		domain.setHolidayOver60hUndigestNumber(
				new AnnualLeaveRemainingTime(holidayOver60hRemainingNumberExport.getHolidayOver60hUndigestNumber()));
		return domain;
	}

}
