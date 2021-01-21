package nts.uk.ctx.at.shared.dom.dailyperformanceprocessing.repository;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.workrecord.workperfor.dailymonthlyprocessing.ErrMessageInfo;

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
