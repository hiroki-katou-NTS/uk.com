package nts.uk.ctx.at.record.pub.employmentinfoterminal.infoterminal.log;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.common.EmployeeId;

/**
 * @author ThanhNX
 *
 *         トップページアラーム詳細(就業情報端末通信)
 */
@Getter
public class TopPageAlEmpInfoTerDetailPub extends TopPageAlarmDetailPub {

	/**
	 * カード番号
	 */
	private final String stampNumber;

	public TopPageAlEmpInfoTerDetailPub(Integer serialNo, String errorMessage, EmployeeId targerEmployee,
			String stampNumber) {
		super(serialNo, errorMessage, targerEmployee);
		this.stampNumber = stampNumber;

	}

}
