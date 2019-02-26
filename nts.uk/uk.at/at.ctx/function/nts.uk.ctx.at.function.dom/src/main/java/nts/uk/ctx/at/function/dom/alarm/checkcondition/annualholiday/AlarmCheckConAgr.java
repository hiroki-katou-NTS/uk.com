package nts.uk.ctx.at.function.dom.alarm.checkcondition.annualholiday;

import java.util.Optional;

import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.agree36.MessageDisp;

/**
 * @author thanhnx
 * 年休使用義務チェック条件
 */
@Getter
@NoArgsConstructor
public class AlarmCheckConAgr{
	
	/**
	 * 前回付与からの経過期間が1年未満の場合、期間按分する
	 */
	private boolean distByPeriod;
	
	/**
	 * 表示メッセージ
	 */
	private Optional<MessageDisp> displayMessage;
	
	/**
	 * 年休使用義務日数
	 */
	private YearlyUsageObDay usageObliDay;
	
	
	public AlarmCheckConAgr(boolean distByPeriod, String displayMessage, int usageObliDay) {
		super();
		this.distByPeriod = distByPeriod;
		this.displayMessage = displayMessage == null ? Optional.empty() : Optional.of(new MessageDisp(displayMessage));
		this.usageObliDay = new YearlyUsageObDay(usageObliDay);
	}
}
