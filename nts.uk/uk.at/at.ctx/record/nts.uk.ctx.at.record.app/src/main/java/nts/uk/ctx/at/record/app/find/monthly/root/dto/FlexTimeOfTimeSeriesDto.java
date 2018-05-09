package nts.uk.ctx.at.record.app.find.monthly.root.dto;

import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.daily.overtimework.FlexTime;

/** 時系列のフレックス時間 */
public class FlexTimeOfTimeSeriesDto {
	
	/** 年月日 */
	private GeneralDate ymd;
	
	/** フレックス時間（日別実績） */
	@Setter
	private FlexTime flexTime;
}
