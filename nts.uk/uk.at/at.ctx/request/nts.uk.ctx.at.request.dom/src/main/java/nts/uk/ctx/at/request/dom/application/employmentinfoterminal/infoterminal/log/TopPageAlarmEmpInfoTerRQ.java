package nts.uk.ctx.at.request.dom.application.employmentinfoterminal.infoterminal.log;

import java.util.List;

import lombok.Getter;
import nts.arc.layer.dom.objecttype.DomainAggregate;
import nts.arc.time.GeneralDateTime;

/**
 * @author ThanhNX
 *
 *         就業情報端末通信用トップページアラーム
 */
@Getter
public class TopPageAlarmEmpInfoTerRQ extends TopPageAlarmRQ implements DomainAggregate {

	/**
	 * 端末コード
	 */
	private final String empInfoTerCode;

	/**
	 * 詳細
	 */
	private final List<TopPageAlEmpInfoTerDetailRQ> lstEmpInfoTerDetail;

	public TopPageAlarmEmpInfoTerRQ(String companyId, List<TopPageAlarmManagerTrRQ> lstManagerTr,
			String empInfoTerCode, List<TopPageAlEmpInfoTerDetailRQ> lstEmpInfoTerDetail) {
		super(companyId, GeneralDateTime.now(), ExistenceErrorRQ.HAVE_ERROR, IsCancelledRQ.NOT_CANCELLED, lstManagerTr);
		this.empInfoTerCode = empInfoTerCode;
		this.lstEmpInfoTerDetail = lstEmpInfoTerDetail;
	}

}
