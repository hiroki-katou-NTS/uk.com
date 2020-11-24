package nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.infoterminal;

import nts.arc.task.tran.AtomTask;

public interface DeleteRequestSettingTRAdapter {

	public AtomTask remove(String empInfoTerCode, String contractCode);
}
