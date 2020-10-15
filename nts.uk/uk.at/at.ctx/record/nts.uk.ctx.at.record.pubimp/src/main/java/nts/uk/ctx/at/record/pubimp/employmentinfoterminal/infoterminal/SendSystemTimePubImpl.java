package nts.uk.ctx.at.record.pubimp.employmentinfoterminal.infoterminal;

import javax.ejb.Stateless;

import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.send.SendTimeInfomation;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.service.SendTimeInfomationService;
import nts.uk.ctx.at.record.pub.employmentinfoterminal.infoterminal.SendSystemTimePub;
import nts.uk.ctx.at.record.pub.employmentinfoterminal.infoterminal.SendTimeInfomationExport;

@Stateless
public class SendSystemTimePubImpl implements SendSystemTimePub {

	@Override
	public SendTimeInfomationExport send() {
		SendTimeInfomation info = SendTimeInfomationService.send();
		return new SendTimeInfomationExport(info.getYear(), info.getMonth(), info.getDay(), info.getHour(),
				info.getMinute(), info.getSecond(), info.getWeek());
	}

}
