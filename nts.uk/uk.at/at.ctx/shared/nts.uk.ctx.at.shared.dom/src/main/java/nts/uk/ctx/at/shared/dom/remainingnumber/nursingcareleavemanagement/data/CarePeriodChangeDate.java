package nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.data;

import java.util.List;
import java.util.Optional;

import lombok.Getter;
import lombok.Setter;
import lombok.val;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.CareTargetPeriod;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.ChildCareTargetChanged;

/**
 *　介護期間変更日
  * @author yuri_tamakoshi
 */
@Getter
@Setter
public class CarePeriodChangeDate {
	/** 介護開始日 */
	private Optional<GeneralDate> careStartYmd;
	/** 介護終了日 */
	private Optional<GeneralDate> careEndYmd;
	/** 死亡年月日 */
	private Optional<GeneralDate> deadDay;

	/**
	 * コンストラクタ
	 */
	public CarePeriodChangeDate(){
		this.careStartYmd = Optional.empty();
		this.careEndYmd =  Optional.empty();
		this.deadDay = Optional.empty();;
	}
	/**
	 * ファクトリー
	 * @param careStartYmd 介護開始日
	 * @param careEndYmd 介護終了日
	 * @param deadDay 死亡年月日
	 * @return 介護期間変更日
	 */
	public static CarePeriodChangeDate of(
			Optional<GeneralDate> careStartYmd,
			Optional<GeneralDate> careEndYmd,
			Optional<GeneralDate> deadDay) {

		CarePeriodChangeDate domain = new CarePeriodChangeDate();
		domain.careStartYmd = careStartYmd;
		domain.careEndYmd = careEndYmd;
		domain.deadDay = deadDay;
		return domain;
	}

	/**
	 * 介護期間終了日を判断
	 * @return 介護期間終了日
	 */
	public Optional<GeneralDate> carePeriodEndYmd() {

		// 介護終了日が入っているか
		if(careEndYmd.isPresent()) {
			// 死亡年月日に値が入っているか
			if(deadDay.isPresent()) {
				// 介護終了日と死亡年月日の年月日の早いのはどちらか
				// ===死亡年月日　＜　介護終了日
				if(deadDay.get().before(careEndYmd.get())) {
					// 介護期間終了日を死亡年月日で返す
					return deadDay;
				}
			}
			//介護期間終了日を介護終了日で返す
			return careEndYmd;
		}

		// 死亡年月日に値が入っているか
		if(deadDay.isPresent()) {
			// 介護期間終了日を死亡年月日で返す
			return deadDay;
		}
		// 介護期間終了日をNullで返す
		return Optional.empty();
	}

	/**
	 * 介護人数変更日リストに追加
	 * @param childCareTargetChanged 看護介護対象人数変更日（List）
	 * @return 看護介護対象人数変更日（List）
	 */
	public List<ChildCareTargetChanged> childCareTargetChanged(List<ChildCareTargetChanged> childCareTargetChanged){

		// 介護開始日に値が入っているか
		if(careStartYmd.isPresent()) {
			// 介護開始日の看護介護対象人数変更日を作成
			// ===変更日　←　介護開始日
			// ===人数　←　看護開始日より前の変更日を持つ1件目の看護介護対象人数変更日の人数
			// === 　　　　　　看護介護対象人数変更日に介護開始日と同じ日のデータがある場合追加しない
			val sameStartYmd = childCareTargetChanged.stream().filter(c -> c.getYmd().equals(careStartYmd.get())).findFirst();
			if (!sameStartYmd.isPresent()) {
					val prevStartYmd = childCareTargetChanged.stream().sorted((c1, c2) -> c2.getYmd().compareTo(c1.getYmd()))
							.filter(c -> c.getYmd().before(careStartYmd.get())).findFirst();

					prevStartYmd.ifPresent(c -> {
						childCareTargetChanged.add(ChildCareTargetChanged.of(c.getNumPerson(), careStartYmd.get()));
					});
			}
		}

		// 介護期間終了日を判断
		Optional<GeneralDate> carePeriodEndYmd = carePeriodEndYmd();
		if(carePeriodEndYmd.isPresent()) {
			val endDate = carePeriodEndYmd.get().addDays(1);
			// 介護期間終了日　!= Null
			// 受け取った介護期間終了日の看護介護対象人数変更日を作成
			// ===変更日　←　介護期間終了日+1日
			// ===人数　←　介護期間終了日+1日より前の変更日を持つ1件目の看護介護対象人数変更日の人数
			// ===　　　　　　 ※看護介護対象人数変更日に介護期間終了日+1日と同じ日のデータがある場合追加しない
			val sameEndYmd = childCareTargetChanged.stream().filter(c -> c.getYmd().equals(endDate)).findFirst();
			if (!sameEndYmd.isPresent()) {
					val prevEndYmd = childCareTargetChanged.stream().sorted((c1, c2) -> c2.getYmd().compareTo(c1.getYmd()))
							.filter(c -> c.getYmd().before(endDate)).findFirst();

					prevEndYmd.ifPresent(c -> {
						childCareTargetChanged.add(ChildCareTargetChanged.of(c.getNumPerson(), endDate));
					});
			}
		}
		// 「看護介護対象人数変更日（List）」を返す
		return childCareTargetChanged;
	}

	/**
	 * 介護対象期間を求める
	 * @param period 期間
	 * @return 介護対象期間
	 */
	public CareTargetPeriod careTargetPeriod(DatePeriod period) {

		// 介護対象期間
		CareTargetPeriod careTargetPeriod;

		// 介護開始日に値が入っているか
		// 期間．開始日に介護開始日を設定
		// 介護開始日がない場合パラメータ「期間．開始日」を設定
		GeneralDate start = careStartYmd.orElse(period.start());

		// 介護期間終了日を判断
		Optional<GeneralDate> carePeriodEndYmd = carePeriodEndYmd();

		// 期間．終了日に受け取った「介護期間終了日」を設定
		// 介護終了日がない場合パラメータ「期間．終了日」を設定
		GeneralDate end = carePeriodEndYmd.orElse(period.end());

		careTargetPeriod = CareTargetPeriod.of(new DatePeriod(start, end));

		// 「介護対象期間」を返す
		return careTargetPeriod;
	}
}
