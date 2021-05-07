package nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.val;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;

/**
 *　介護対象期間
  * @author yuri_tamakoshi
 */
@Getter
@Setter
public class CareTargetPeriod {
	/** 期間 */
	private DatePeriod period;

	/**
	 * コンストラクタ
	 */
	public CareTargetPeriod(){
		this.period = new DatePeriod(GeneralDate.today(), GeneralDate.today());
	}
	/**
	 * ファクトリー
	 * @param period 期間
	 * @return 介護対象期間
	 */
	public static CareTargetPeriod of(
			DatePeriod period) {

		CareTargetPeriod domain = new CareTargetPeriod();
		domain.period = period;
		return domain;
	}


	/**
	 * 介護期間に含まれる介護人数変更日リストを求める
	 * @param childCareTargetChanged 看護介護対象人数変更日
	 * @return 看護介護対象人数変更日
	 */
	public List<ChildCareTargetChanged> childCareTargetChanged(List<ChildCareTargetChanged> childCareTargetChanged) {

		// 看護介護対象人数変更日の件数ループ
		for (int idx = 0; idx < childCareTargetChanged.size(); idx++) {

			val currentDayProcess = childCareTargetChanged.get(idx);
			// 処理中看護介護対象人数変更日が期間内か
			// ===期間．開始日  <= 看護介護対象人数変更日．変更日 <= 期間．終了日
			if(period.contains(currentDayProcess.getYmd()) ) {
				// 看護介護対象人数変更日の人数1人追加
				currentDayProcess.addOnePerson();
			}
		}
		return childCareTargetChanged;
	}
}
