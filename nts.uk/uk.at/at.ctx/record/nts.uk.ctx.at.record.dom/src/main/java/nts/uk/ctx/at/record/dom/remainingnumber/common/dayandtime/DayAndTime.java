package nts.uk.ctx.at.record.dom.remainingnumber.common.dayandtime;

import java.util.Optional;

import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.grantremainingdata.usenumber.DayNumberOfUse;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.grantremainingdata.usenumber.TimeOfUse;
import nts.uk.ctx.at.shared.dom.workingcondition.LaborContractTime;
import nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata.daynumber.LeaveRemainingNumber;

/**
  * 日と時間
  * @author yuri_tamakoshi
 */
@Getter
@Setter
public class DayAndTime {

	/** 時間 */
	private TimeOfUse time;
	/** 日数 */
	private DayNumberOfUse day;

	/**
	 * コンストラクタ
	 */
	public DayAndTime(){

		this.time = new TimeOfUse(0);
		this.day = new DayNumberOfUse(0.0);

	}

	/**
	 * ファクトリー
	 * @param time 時間
	 * @param day 日
	 * @return 日と時間
	 */
	public static DayAndTime of(
			TimeOfUse time,
			DayNumberOfUse day){

		DayAndTime domain = new DayAndTime();
		domain.time = time;
		domain.day = day;
		return domain;
	}


	/**
	 * 日と時間の加算
	 * @param 日と時間（加算される方）
	 * @param 日と時間（加算する方）
	 * @return dayAndTime 日と時間（加算後）
	 *
	 */
	public DayAndTime addDayAndTime(DayAndTime beAdded, DayAndTime toAdd) {

		// 日を加算する
		// ===日と時間（加算後）．日＝日と時間（加算される方）．日＋日と時間（加算する方）．日
		DayNumberOfUse resultDay = new DayNumberOfUse(beAdded.day.v() + toAdd.day.v());

		// 時間を加算する
		// ===日と時間（加算後）．時間＝日と時間（加算される方）．時間＋日と時間（加算する方）．時間
		 TimeOfUse resultTime = new TimeOfUse(beAdded.time.v() + toAdd.time.v());

		DayAndTime addDayAndTime = DayAndTime.of(resultTime, resultDay);

		// 「日と時間（加算後）」を返す
		return addDayAndTime;

	}

	/**
	 * 日と時間の減算
	 * @param beSubtracted 減算される日と時間
	 * @param subtract 減算する日と時間
	 * @param companyId 会社ID
	 * @param employeeId 社員ID
	 * @param criteriaDate 基準日
	 * @return dayAndTime 計算結果の日と時間
	 *
	 */
	public static DayAndTime subDayAndTime(DayAndTime beSubtracted, DayAndTime subtract,  String companyId, String employeeId, GeneralDate criteriaDate, RequireM3 require){
		// 日数の減算を行う
		// ===計算結果の日と時間．日　=　減算される日と時間．日 ー 減算する日と時間．日
		// ===計算結果の日と時間．時間 = 　減算される日と時間．時間
		DayNumberOfUse resultDay = new DayNumberOfUse(beSubtracted.day.v() - subtract.day.v());
		TimeOfUse resultTime = beSubtracted.time;

		// 積み崩しが必要か
		// １日に相当する契約時間を取得する
		Optional<LaborContractTime> contractTime = LeaveRemainingNumber.getContractTime(require, companyId, employeeId, criteriaDate);

		if(!contractTime.isPresent()){
			contractTime = Optional.of(new LaborContractTime(0));
		}
		
		
		// ===積み崩し発生条件
		// ===「 1<=計算結果の日と時間.日 and 1<=減算する日と時間.時間 and 計算結果の日と時間.時間 < 減算する日.時間」
		while (1 <= resultDay.v() && 1 <= subtract.time.v() &&  resultTime.v() <= subtract.time.v()) {
			// 必要の場合
			// 減算される日と時間を積み崩し
			// ===計算結果の日と時間.日数から１をマイナス。
			// ===計算結果の日と時間.時間に、「契約時間」を加算。
			resultDay = new DayNumberOfUse(resultDay.v() - 1);
			resultTime = resultTime.addMinutes(contractTime.get().v());
		}
		
		
		
		// 時間の減算を行う
		// ===計算結果の日と時間．時間　＝　減算される日と時間．時間　ー　減算する日と時間．時間
		resultTime = resultTime.minusMinutes(subtract.time.v());
		

		DayAndTime subDayAndTime = DayAndTime.of(resultTime, resultDay);

		// 「計算結果の日と時間」を返す
		return subDayAndTime;
	}

	public static interface RequireM3 extends LeaveRemainingNumber.RequireM3{

	}

}
