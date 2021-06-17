package nts.uk.ctx.at.record.dom.adapter.employmentinfoterminal.infoterminal;

import lombok.Value;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminalCode;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.MonitorIntervalTime;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.ContractCode;
/**
 * 
 * @author dungbn
 *
 */
@Value
public class EmpInfoTerminalComStatusImport {

	private ContractCode contractCode;
	
 	private EmpInfoTerminalCode empInfoTerCode;
	
 	private GeneralDateTime signalLastTime;
 	
 	public boolean isCommunicationError(MonitorIntervalTime intervalTime) {
		if(this.signalLastTime.addMinutes(intervalTime.v()).compareTo(GeneralDateTime.now())<0) {
			return true;
		}
		return false;
	}
	
}
