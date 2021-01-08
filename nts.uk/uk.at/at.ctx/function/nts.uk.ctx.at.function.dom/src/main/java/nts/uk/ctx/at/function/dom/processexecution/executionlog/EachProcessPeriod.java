package nts.uk.ctx.at.function.dom.processexecution.executionlog;

import java.util.Optional;

//import lombok.AllArgsConstructor;
import lombok.Getter;
//import lombok.Setter;
import nts.arc.layer.dom.DomainObject;
import nts.arc.time.calendar.period.DatePeriod;

/**
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.就業機能.更新処理自動実行.更新処理自動実行ログ.各処理の期間
 */
@Getter
public class EachProcessPeriod extends DomainObject {
	/* スケジュール作成の期間 */
	private Optional<DatePeriod> scheduleCreationPeriod;
	
	/* 日別作成の期間 */
	private Optional<DatePeriod> dailyCreationPeriod;
	
	/* 日別計算の期間 */
	private Optional<DatePeriod> dailyCalcPeriod;
	
	/* 承認結果反映 */
	private Optional<DatePeriod> reflectApprovalResult;
	

	public void setScheduleCreationPeriod(DatePeriod scheduleCreationPeriod) {
		this.scheduleCreationPeriod = Optional.ofNullable(scheduleCreationPeriod);
	}

	public void setDailyCreationPeriod(DatePeriod dailyCreationPeriod) {
		this.dailyCreationPeriod = Optional.ofNullable(dailyCreationPeriod);
	}

	public void setDailyCalcPeriod(DatePeriod dailyCalcPeriod) {
		this.dailyCalcPeriod = Optional.ofNullable(dailyCalcPeriod);
	}

	public void setReflectApprovalResult(DatePeriod reflectApprovalResult) {
		this.reflectApprovalResult = Optional.ofNullable(reflectApprovalResult);
	}

	public EachProcessPeriod(DatePeriod scheduleCreationPeriod, DatePeriod dailyCreationPeriod,
			DatePeriod dailyCalcPeriod, DatePeriod reflectApprovalResult) {
		super();
		this.scheduleCreationPeriod = Optional.ofNullable(scheduleCreationPeriod);
		this.dailyCreationPeriod = Optional.ofNullable(dailyCreationPeriod);
		this.dailyCalcPeriod = Optional.ofNullable(dailyCalcPeriod);
		this.reflectApprovalResult = Optional.ofNullable(reflectApprovalResult);
	}
	
}
