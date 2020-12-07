package nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.objecttype.DomainAggregate;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.ContractCode;

/**
 * 
 * @author dungbn
 * リクエスト通信の状態監視
 */
@AllArgsConstructor
@Getter
public class ReqComStatusMonitoring implements DomainAggregate {

	/**
	 * 契約コード
	 */
	private ContractCode contractCode; 
	
	/**
	 * 就業情報端末コード
	 */
	private EmpInfoTerminalCode terminalCode;
	
	/**
	 * 通信中
	 */
	private boolean connecting;
}
