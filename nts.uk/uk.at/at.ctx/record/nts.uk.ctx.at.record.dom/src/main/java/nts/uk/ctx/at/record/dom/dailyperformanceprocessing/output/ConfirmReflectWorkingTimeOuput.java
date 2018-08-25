package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.output;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.record.dom.workrecord.workperfor.dailymonthlyprocessing.ErrMessageInfo;

@Getter
@Setter
@NoArgsConstructor
public class ConfirmReflectWorkingTimeOuput {
	
	/**
	 * true : reflect 反映する
	 * false : not reflect 反映しない
	 */
	private boolean isReflect;
	
	private List<ErrMessageInfo> errMesInfos;

}
