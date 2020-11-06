package nts.uk.ctx.at.function.ac.employmentinfoterminal.infoterminal;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.task.tran.AtomTask;
import nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.infoterminal.DeleteRequestSettingTRAdapter;
import nts.uk.ctx.at.record.pub.employmentinfoterminal.infoterminal.DeleteRequestSettingTimeRecordPub;

@Stateless
public class DeleteRequestSettingTRAdapterImpl implements DeleteRequestSettingTRAdapter {

	@Inject
	private DeleteRequestSettingTimeRecordPub pub;

	@Override
	public AtomTask remove(Integer empInfoTerCode, String contractCode) {
		return pub.remove(empInfoTerCode, contractCode);
	}

}
