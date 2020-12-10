package nts.uk.ctx.at.record.pub.employmentinfoterminal.infoterminal;

import nts.arc.task.tran.AtomTask;

public interface DeleteRequestSettingTimeRecordPub {

	public AtomTask remove(String empInfoTerCode, String contractCode);
	
}
