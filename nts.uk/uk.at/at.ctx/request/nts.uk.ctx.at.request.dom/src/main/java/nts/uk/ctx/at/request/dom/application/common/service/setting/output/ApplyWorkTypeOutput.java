package nts.uk.ctx.at.request.dom.application.common.service.setting.output;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;

@AllArgsConstructor
@Getter
public class ApplyWorkTypeOutput {
	
	/**
	 * 勤務種類(List)//workType(List)
	 */
	private List<WorkType> wkTypes;
	
	/**
	 * マスタ未登録 //master Unregistered
	 */
	private boolean masterUnregister;
	
}
