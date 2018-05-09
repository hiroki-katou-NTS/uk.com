package nts.uk.ctx.at.record.dom.monthlyclosureupdatelog;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;

/**
 * 
 * @author HungTT - 月締め更新エラー情報
 *
 */

@Getter
public class MonthlyClosureUpdateErrorInfor extends AggregateRoot {

	// 社員ID
	private String employeeId;

	// 月締め更新ログID
	private String monthlyClosureUpdateLogId;

	// 実締め終了日
	private GeneralDate actualClosureEndDate;

	// リソースID
	private String resourceId;

	// エラーメッセージ
	private String errorMessage;

	// 区分
	private MonthlyClosureUpdateErrorAlarmAtr atr;

	public MonthlyClosureUpdateErrorInfor(String employeeId, String monthlyClosureUpdateLogId,
			GeneralDate actualClosureEndDate, String resourceId, String errorMessage, int atr) {
		super();
		this.employeeId = employeeId;
		this.monthlyClosureUpdateLogId = monthlyClosureUpdateLogId;
		this.resourceId = resourceId;
		this.errorMessage = errorMessage;
		this.atr = EnumAdaptor.valueOf(atr, MonthlyClosureUpdateErrorAlarmAtr.class);
		this.actualClosureEndDate = actualClosureEndDate;
	}

}
