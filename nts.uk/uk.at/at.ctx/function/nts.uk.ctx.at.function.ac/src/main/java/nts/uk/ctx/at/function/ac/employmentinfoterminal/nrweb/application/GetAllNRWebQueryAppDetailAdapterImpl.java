package nts.uk.ctx.at.function.ac.employmentinfoterminal.nrweb.application;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.nrweb.application.GetAllNRWebQueryAppDetailAdapter;
import nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.nrweb.application.NRQueryAppImport;
import nts.uk.ctx.at.function.dom.employmentinfoterminal.infoterminal.nrweb.common.NRWebQuerySidDateParameter;
import nts.uk.ctx.at.request.pub.application.infoterminal.nrwebquery.GetAllNRWebQueryAppDetailPub;

@Stateless
public class GetAllNRWebQueryAppDetailAdapterImpl implements GetAllNRWebQueryAppDetailAdapter {

	@Inject
	private GetAllNRWebQueryAppDetailPub pub;

	@Override
	public List<? extends NRQueryAppImport> getAll(NRWebQuerySidDateParameter param, DatePeriod period) {

		val export = pub.getAll(NRQueryAppConvertFunc.toDomain(param), period);
		return export.stream().map(x -> NRQueryAppConvertFunc.fromDomain(x)).collect(Collectors.toList());
	}

}
