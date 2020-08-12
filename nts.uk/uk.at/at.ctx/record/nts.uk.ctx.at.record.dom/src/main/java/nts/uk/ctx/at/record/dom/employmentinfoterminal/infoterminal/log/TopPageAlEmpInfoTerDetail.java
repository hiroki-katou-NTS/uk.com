package nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.log;

import lombok.Getter;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampNumber;
import nts.uk.ctx.at.shared.dom.common.EmployeeId;

/**
 * @author ThanhNX
 *
 *         トップページアラーム詳細(就業情報端末通信)
 */
@Getter
public class TopPageAlEmpInfoTerDetail extends TopPageAlarmDetail {

	/**
	 * カード番号
	 */
	private final StampNumber stampNumber;

	public TopPageAlEmpInfoTerDetail(Integer serialNo, String errorMessage, EmployeeId targerEmployee,
			StampNumber stampNumber) {
		super(serialNo, errorMessage, targerEmployee);
		this.stampNumber = stampNumber;

	}

}
