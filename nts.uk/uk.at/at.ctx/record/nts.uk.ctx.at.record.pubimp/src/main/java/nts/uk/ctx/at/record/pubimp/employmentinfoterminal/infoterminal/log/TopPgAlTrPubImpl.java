package nts.uk.ctx.at.record.pubimp.employmentinfoterminal.infoterminal.log;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminalCode;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.log.RogerFlag;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.log.TopPageAlEmpInfoTerDetail;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.log.TopPageAlarmEmpInfoTer;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.log.TopPageAlarmManagerTr;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.log.TopPgAlTrRepository;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampNumber;
import nts.uk.ctx.at.record.pub.employmentinfoterminal.infoterminal.log.TopPageAlarmEmpInfoTerPub;
import nts.uk.ctx.at.record.pub.employmentinfoterminal.infoterminal.log.TopPgAlTrPub;

@Stateless
public class TopPgAlTrPubImpl implements TopPgAlTrPub {

	@Inject
	private TopPgAlTrRepository repo;

	@Override
	public void insertLogAll(TopPageAlarmEmpInfoTerPub alEmpInfo) {

		repo.insertLogAll(convertTo(alEmpInfo));

	}

	private TopPageAlarmEmpInfoTer convertTo(TopPageAlarmEmpInfoTerPub domainPub) {

		List<TopPageAlarmManagerTr> lstManagerTr = domainPub.getLstManagerTr().stream()
				.map(x -> new TopPageAlarmManagerTr(x.getManagerId(),
						EnumAdaptor.valueOf(x.getRogerFlag().value, RogerFlag.class)))
				.collect(Collectors.toList());

		List<TopPageAlEmpInfoTerDetail> lstEmpInfoTerDetail = domainPub
				.getLstEmpInfoTerDetail().stream().map(x -> new TopPageAlEmpInfoTerDetail(x.getSerialNo(),
						x.getErrorMessage(), x.getTargerEmployee(), new StampNumber(x.getStampNumber())))
				.collect(Collectors.toList());

		return new TopPageAlarmEmpInfoTer(domainPub.getCompanyId(), lstManagerTr,
				new EmpInfoTerminalCode(domainPub.getEmpInfoTerCode()), lstEmpInfoTerDetail);
	}
}
