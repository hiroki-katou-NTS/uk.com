package nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.childcare;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.val;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata.daynumber.LeaveRemainingNumber;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.grantremainingdata.usenumber.DayNumberOfUse;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.grantremainingdata.usenumber.TimeOfUse;
import nts.uk.ctx.at.shared.dom.workingcondition.LaborContractTime;

/**
 * 子の看護介護使用数
 * @author yuri_tamakoshi
 */
@Getter
@Setter
@AllArgsConstructor
public class ChildCareNurseUsedNumber implements Cloneable{

	/** 日数 */
	private DayNumberOfUse usedDay;
	/** 時間 */
	private Optional<TimeOfUse>usedTimes;

	/**
	 * コンストラクタ　ChildCareNurseUsedNumber
	 */
	public ChildCareNurseUsedNumber(){
		this.usedDay = new DayNumberOfUse(0.0);
		this.usedTimes = Optional.empty();
	}

	/**
	 * コンストラクタ
	 */
	public ChildCareNurseUsedNumber(ChildCareNurseUsedNumber c) {
		this.usedDay = new DayNumberOfUse(c.usedDay.v());
		this.usedTimes = c.usedTimes.map(mapper->new TimeOfUse(mapper.v()));
	}

	/**
	 * クローン
	 */
	public ChildCareNurseUsedNumber clone() {
		return new ChildCareNurseUsedNumber(this);
	}

	/**
	 * ファクトリー
	 * @param usedDay 日数
	 * @param usedTimes 時間
	 * @return 子の看護介護使用数
	*/
	public static ChildCareNurseUsedNumber of(
			DayNumberOfUse usedDay,
			Optional<TimeOfUse> usedTimes){

		ChildCareNurseUsedNumber domain = new ChildCareNurseUsedNumber();
		domain.usedDay = usedDay;
		domain.usedTimes = usedTimes;
		return domain;
	}

	/** 使用数に暫定管理データ使用数を加算 */
	public void add(ChildCareNurseUsedNumber usedNumber) {
		usedDay = new DayNumberOfUse(usedDay.v() + usedNumber.getUsedDay().v());
		if (usedTimes.isPresent()) {
			usedTimes = usedTimes.map(c -> c.addMinutes(usedNumber.getUsedTimes().map(x -> x.v()).orElse(0)));
		} else {
			usedTimes = usedNumber.getUsedTimes();
		}
	}

	/** 使用数に暫定管理データ使用数を減算 */
	public void subtract(ChildCareNurseUsedNumber usedNumber) {
		usedDay = new DayNumberOfUse(usedDay.v() - usedNumber.getUsedDay().v());
		val toSubTime = usedNumber.getUsedTimes().map(x -> x.v()).orElse(0);
		if (usedTimes.isPresent()) {
			usedTimes = usedTimes.map(c -> c.minusMinutes(toSubTime));
		} else {;
			usedTimes = Optional.of(new TimeOfUse(0));
		}
	}

	/**
	 * 年休の契約時間を取得する
	 * @param companyId 会社ID
	 * @param employeeId 社員ID
	 * @param criteriaDate 基準日
	 * @return 契約時間
	 */
	public ChildCareNurseUsedNumber contractTime(RequireM3 require, String companyId, String employeeId, GeneralDate criteriaDate) {

//		// INPUT．Require．年休の契約時間を取得する
//		LaborContractTime contractTime = require.contractTime(companyId, employeeId, criteriaDate);

		// 「年休１日に相当する時間年休時間を取得する」を取得する
		 Optional<LaborContractTime> contractTime = LeaveRemainingNumber.getContractTime(require, companyId, employeeId, criteriaDate);

		return usedDayfromUsedTime(contractTime);
	}

	/**
	 * 時間使用数を日数に積み上げ
	 * @param contractTime 契約時間
	 * @return 子の看護介護使用数
	 */
	public ChildCareNurseUsedNumber usedDayfromUsedTime(Optional<LaborContractTime> contractTime) {

		if(!contractTime.isPresent() || contractTime.get().v() == 0) {
			return this;
		} else {
			// 使用時間を契約時間分だけ日数に変換する
			int timeOfUse = this.usedTimes.map(c -> c.valueAsMinutes()).orElse(0);
			int days = timeOfUse / contractTime.get().valueAsMinutes();
			this.usedDay = new DayNumberOfUse(this.usedDay.v() + days);
			this.usedTimes = Optional.of(new TimeOfUse(timeOfUse % contractTime.get().valueAsMinutes()));
		}

		// 「子の看護介護使用数」を返す
		return this;
	}

	/** Require	 */
	public static interface RequireM3 extends LeaveRemainingNumber.RequireM3{

	}
}