package nts.uk.ctx.at.request.ac.record.infoterminal.log;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.record.pub.employmentinfoterminal.infoterminal.log.RogerFlagPub;
import nts.uk.ctx.at.record.pub.employmentinfoterminal.infoterminal.log.TopPageAlEmpInfoTerDetailPub;
import nts.uk.ctx.at.record.pub.employmentinfoterminal.infoterminal.log.TopPageAlarmEmpInfoTerPub;
import nts.uk.ctx.at.record.pub.employmentinfoterminal.infoterminal.log.TopPageAlarmManagerTrPub;
import nts.uk.ctx.at.record.pub.employmentinfoterminal.infoterminal.log.TopPgAlTrPub;
import nts.uk.ctx.at.request.dom.application.employmentinfoterminal.infoterminal.log.TopPageAlarmEmpInfoTerRQ;
import nts.uk.ctx.at.request.dom.application.employmentinfoterminal.infoterminal.log.TopPgAlTrAdapter;

@Stateless
public class TopPgAlTrAdapterImpl implements TopPgAlTrAdapter {

	@Inject
	private TopPgAlTrPub pub;

	@Override
	public void insertLogAll(TopPageAlarmEmpInfoTerRQ alEmpInfo) {

		pub.insertLogAll(convertTo(alEmpInfo));

	}

	private TopPageAlarmEmpInfoTerPub convertTo(TopPageAlarmEmpInfoTerRQ domainRQ) {

		List<TopPageAlarmManagerTrPub> lstManagerTr = domainRQ.getLstManagerTr().stream()
				.map(x -> new TopPageAlarmManagerTrPub(x.getManagerId(),
						EnumAdaptor.valueOf(x.getRogerFlag().value, RogerFlagPub.class)))
				.collect(Collectors.toList());

		List<TopPageAlEmpInfoTerDetailPub> lstEmpInfoTerDetail = domainRQ.getLstEmpInfoTerDetail().stream()
				.map(x -> new TopPageAlEmpInfoTerDetailPub(x.getSerialNo(), x.getErrorMessage(), x.getTargerEmployee(),
						x.getStampNumber()))
				.collect(Collectors.toList());

		return new TopPageAlarmEmpInfoTerPub(domainRQ.getCompanyId(), lstManagerTr, domainRQ.getEmpInfoTerCode(),
				lstEmpInfoTerDetail);
	}

}
