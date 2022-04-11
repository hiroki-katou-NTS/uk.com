package nts.uk.ctx.at.function.dom.employmentinfoterminal.infoterminal.nrweb.daily.repo;

import lombok.Getter;
import nts.arc.layer.dom.objecttype.DomainAggregate;
import nts.uk.ctx.at.function.dom.employmentinfoterminal.infoterminal.ContractCode;

/**
 * @author thanh_nx
 *
 *         NRWeb照会の日別実績の勤怠項目
 */
@Getter
public class NRWebQueryDailyItem implements DomainAggregate {

	// 契約コード
	private ContractCode contractCode;

	// NO
	private int no;

	// 勤怠項目ID
	private int attendanceItemId;

	public NRWebQueryDailyItem(ContractCode contractCode, int no, int attendanceItemId) {
		super();
		this.contractCode = contractCode;
		this.no = no;
		this.attendanceItemId = attendanceItemId;
	}

}
