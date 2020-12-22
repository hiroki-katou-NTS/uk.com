package nts.uk.screen.at.app.command.kmk.kmk004.k;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author sonnlb
 *
 */
@NoArgsConstructor
@Data
@AllArgsConstructor
public class FlexMonthWorkTimeAggrSetCommand {

	/** 集計方法 */
	private int aggrMethod;

	/** 不足設定 */
	private ShortageFlexSettingCommand insufficSet;

	/** 法定内集計設定 */
	private AggregateTimeSettingCommand legalAggrSet;

	/** フレックス時間の扱い */
	private FlexTimeHandleCommand flexTimeHandle;
}
