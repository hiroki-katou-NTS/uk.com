package nts.uk.ctx.at.function.ac.employmentinfoterminal.infoterminal;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.infoterminal.DateTimeSwitchUKModeImport;
import nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.infoterminal.SendDateTimeSwitchUKModeAdapter;
import nts.uk.ctx.at.record.pub.employmentinfoterminal.infoterminal.SendDateTimeSwitchUKModePub;

@Stateless
public class SendDateTimeSwitchUKModeAC implements SendDateTimeSwitchUKModeAdapter {

	@Inject
	private SendDateTimeSwitchUKModePub pub;

	@Override
	public Optional<DateTimeSwitchUKModeImport> process(String empInfoTerCode, String contractCode) {
		return pub.process(empInfoTerCode, contractCode).map(x -> new DateTimeSwitchUKModeImport(x.getYear(),
				x.getMonth(), x.getDay(), x.getHour(), x.getMinute(), x.getSecond(), x.getWeek()));
	}

}
