package nts.uk.ctx.at.record.pub.remainnumber.holidayover60h.export;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.record.dom.monthly.vacation.holidayover60h.HolidayOver60h;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata.daynumber.AnnualLeaveRemainingTime;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata.daynumber.AnnualLeaveUsedTime;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.maxdata.UsedTimes;

/**
 * 60H超休
 * @author masaaki_jinno
 */
@Getter
@Setter
@AllArgsConstructor
public class HolidayOver60hExport {

	/**
	 * 年休使用数
	*/
	private Integer usedTime;

	/**
	 * 残時間
	*/
	private Integer remainingTime;

	/**
	 * コンストラクタ
	*/
	public HolidayOver60hExport(){
		this.usedTime = 0;
		this.remainingTime = 0;
	}

	/**
	   * ドメインから変換
	 * @param holidayOver60h
	 * @return
	 */
	static public HolidayOver60hExport of(HolidayOver60h holidayOver60h) {
		HolidayOver60hExport export = new HolidayOver60hExport();
		export.usedTime = holidayOver60h.getUsedTime().v();
		export.remainingTime = holidayOver60h.getRemainingTime().v();
		return export;
	}

	/**
	   * ドメインへ変換
	 * @param holidayOver60h
	 * @return
	 */
	static public HolidayOver60h toDomain(HolidayOver60hExport holidayOver60hExport){
		HolidayOver60h domain = new HolidayOver60h();
		domain.setUsedTime(new AnnualLeaveUsedTime(holidayOver60hExport.usedTime));
		domain.setRemainingTime(new AnnualLeaveRemainingTime(holidayOver60hExport.remainingTime));

		return domain;
	}
}
