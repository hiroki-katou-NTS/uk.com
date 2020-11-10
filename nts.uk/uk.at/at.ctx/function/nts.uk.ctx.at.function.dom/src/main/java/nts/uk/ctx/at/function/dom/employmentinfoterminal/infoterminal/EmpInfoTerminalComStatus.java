package nts.uk.ctx.at.function.dom.employmentinfoterminal.infoterminal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.objecttype.DomainAggregate;
import nts.arc.time.GeneralDateTime;

/**
 * 
 * @author huylq
 * 
 * 	就業情報端末通信状況
 *
 */

@AllArgsConstructor
@Getter
@Setter
public class EmpInfoTerminalComStatus implements DomainAggregate{
	
	/**
	 * 	契約コード
	 */
	private final ContractCode contractCode;
	
	/**
	 * 	就業情報端末コード
	 */
	private final EmpInfoTerminalCode empInfoTerCode;
	
	/**
	 * 	最終通信日時	
	 */
	private GeneralDateTime signalLastTime;
	
	//	[1] 通信異常があったか判断する
	public boolean isCommunicationError(MonitorIntervalTime intervalTime) {
		if(this.signalLastTime.addMinutes(intervalTime.v()).compareTo(GeneralDateTime.now())<0) {
			return true;
		}
		return false;
	}
}
