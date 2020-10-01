package nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.log;

import java.util.List;

import lombok.Getter;
import nts.arc.layer.dom.objecttype.DomainAggregate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminalCode;

/**
 * @author ThanhNX
 *
 *         就業情報端末通信用トップページアラーム
 */
@Getter
public class TopPageAlarmEmpInfoTer extends TopPageAlarm implements DomainAggregate {

	/**
	 * 端末コード
	 */
	private final EmpInfoTerminalCode empInfoTerCode;

	/**
	 * 詳細
	 */
	private final List<TopPageAlEmpInfoTerDetail> lstEmpInfoTerDetail;

	public TopPageAlarmEmpInfoTer(String companyId, List<TopPageAlarmManagerTr> lstManagerTr,
			EmpInfoTerminalCode empInfoTerCode, List<TopPageAlEmpInfoTerDetail> lstEmpInfoTerDetail) {
		super(companyId, GeneralDateTime.now(), ExistenceError.HAVE_ERROR, IsCancelled.NOT_CANCELLED, lstManagerTr);
		this.empInfoTerCode = empInfoTerCode;
		this.lstEmpInfoTerDetail = lstEmpInfoTerDetail;
	}

}
