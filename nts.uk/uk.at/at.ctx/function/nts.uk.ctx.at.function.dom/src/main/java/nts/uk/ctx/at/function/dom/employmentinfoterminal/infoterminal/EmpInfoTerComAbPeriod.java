package nts.uk.ctx.at.function.dom.employmentinfoterminal.infoterminal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.objecttype.DomainAggregate;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;

/**
 * 
 * @author dungbn
 * 就業情報端末通信異常期間
 */
@AllArgsConstructor
@Getter
public class EmpInfoTerComAbPeriod implements DomainAggregate {

	/**
	 * 	契約コード
	 */
	private final ContractCode contractCode;
	
	/**
	 * 	就業情報端末コード
	 */
	private final EmpInfoTerminalCode empInfoTerCode;
	
	/**
	 * 	前回通信成功日時	
	 */
	private final GeneralDateTime lastComSuccess;
	
	/**
	 * 最新通信成功日時
	 */
	private final GeneralDateTime lastestComSuccess;
	
	
	// [S-1] 何日以前の情報を削除するか判断する
	public static GeneralDate getDayRemoveInfo() {
		return GeneralDate.today().addMonths(-3);
	}
	
	
}
