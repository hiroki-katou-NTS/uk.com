package nts.uk.screen.at.app.kmk004.h;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 
 * @author sonnlb
 *
 */
@AllArgsConstructor
@Data
public class FlexMonthWorkTimeAggrSetDto {
	/** 会社ID */
	private String comId;

	/** 集計方法 */
	private int aggrMethod;

	/** 不足設定 */
	private ShortageFlexSettingDto insufficSet;

	/** 法定内集計設定 */
	private AggregateTimeSettingDto legalAggrSet;

	/** フレックス時間の扱い */
	private FlexTimeHandleDto flexTimeHandle;
}
