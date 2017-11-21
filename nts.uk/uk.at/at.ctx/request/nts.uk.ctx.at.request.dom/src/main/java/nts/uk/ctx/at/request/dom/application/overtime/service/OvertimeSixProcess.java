package nts.uk.ctx.at.request.dom.application.overtime.service;

import java.util.List;

import nts.uk.ctx.at.request.dom.application.common.adapter.frame.OvertimeInputCaculation;
import nts.uk.ctx.at.request.dom.application.overtime.OverTimeInput;

public interface OvertimeSixProcess {
	
	/**
	 * 06-01_色表示チェック
	 * @param overTimeInputs
	 * @param overtimeInputCaculations
	 */
	public void checkDisplayColor(List<OverTimeInput> overTimeInputs,List<OvertimeInputCaculation> overtimeInputCaculations);

}
