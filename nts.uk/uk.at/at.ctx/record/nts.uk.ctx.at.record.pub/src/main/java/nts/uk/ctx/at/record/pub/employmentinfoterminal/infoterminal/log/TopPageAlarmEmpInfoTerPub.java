package nts.uk.ctx.at.record.pub.employmentinfoterminal.infoterminal.log;

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
public class TopPageAlarmEmpInfoTerPub extends TopPageAlarmPub implements DomainAggregate {

	/**
	 * 端末コード
	 */
	private final String empInfoTerCode;

	/**
	 * 詳細
	 */
	private final List<TopPageAlEmpInfoTerDetailPub> lstEmpInfoTerDetail;

	public TopPageAlarmEmpInfoTerPub(String companyId, List<TopPageAlarmManagerTrPub> lstManagerTr,
			String empInfoTerCode, List<TopPageAlEmpInfoTerDetailPub> lstEmpInfoTerDetail) {
		super(companyId, GeneralDateTime.now(), ExistenceErrorPub.HAVE_ERROR, IsCancelledPub.NOT_CANCELLED, lstManagerTr);
		this.empInfoTerCode = empInfoTerCode;
		this.lstEmpInfoTerDetail = lstEmpInfoTerDetail;
	}

}
