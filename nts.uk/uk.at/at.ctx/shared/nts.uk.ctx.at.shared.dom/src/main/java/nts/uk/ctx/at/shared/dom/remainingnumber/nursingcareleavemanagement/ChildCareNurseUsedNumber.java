package nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement;

import java.util.Optional;

import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.grantremainingdata.usenumber.DayNumberOfUse;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.grantremainingdata.usenumber.TimeOfUse;
import nts.uk.ctx.at.shared.dom.workingcondition.LaborContractTime;

/**
 * 子の看護介護使用数
 * @author yuri_tamakoshi
 */
@Getter
@Setter
public class ChildCareNurseUsedNumber implements Cloneable{

	/** 子の看護介護休暇（使用日数） */
	private DayNumberOfUse usedDay;
	/** 子の看護介護休暇（使用時間） */
	private Optional<TimeOfUse>usedTimes;

	/**
	 * コンストラクタ　ChildCareNurseUsedNumber
	 */
	public ChildCareNurseUsedNumber(){
		this.usedDay = new DayNumberOfUse(0.0);
		this.usedTimes = Optional.empty();
	}

	/**
	 * ファクトリー
	 * @param usedDay　子の看護介護休暇（使用日数）
	 * @param usedTimes　子の看護介護休暇（使用時間）
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

	/**
	 * 時間使用数を日数に積み上げ
	 * @param companyId 会社ID
	 * @param employeeId 社員ID
	 * @param criteriaDate 基準日
	 * @param 年休の契約時間（社員ID、基準日）
	 * @return 子の看護介護使用数
	 */
	public ChildCareNurseUsedNumber usedDayfromUsedTime(String companyId, String employeeId, GeneralDate criteriaDate, Require require) {

		// INPUT．Require．年休の契約時間を取得する
		LaborContractTime contractTime = require.contractTime(employeeId, criteriaDate); // 一時対応　神野さんに確認

		// === UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務実績.残数管理.付与せず上限で管理する休暇.子の看護・介護休暇管理.アルゴリズム.起算日からの使用数に加算.起算日からの使用数に加算
		// === LaborContractTime の労働契約時間ではない※
		// ＝＝＝時間年休一日の時間.全社一律の時間（年休1日に相当する時間年休時間のこと）

		// 使用時間を契約時間分だけ日数に変換する
		int timeOfUse = this.usedTimes.map(c -> c.valueAsMinutes()).orElse(0);
		DayNumberOfUse usedDay = new DayNumberOfUse((double) timeOfUse / contractTime.valueAsMinutes());
		Optional<TimeOfUse> usedTimes = Optional.of(new TimeOfUse(timeOfUse % contractTime.valueAsMinutes()));

		// return 子の看護介護使用数を返す
		return ChildCareNurseUsedNumber.of(usedDay,usedTimes);
	}

	// Require
	public static interface Require {
		// 年休の契約時間を取得する（社員ID、基準日）
		LaborContractTime contractTime(String employeeId, GeneralDate criteriaDate);
	}
}