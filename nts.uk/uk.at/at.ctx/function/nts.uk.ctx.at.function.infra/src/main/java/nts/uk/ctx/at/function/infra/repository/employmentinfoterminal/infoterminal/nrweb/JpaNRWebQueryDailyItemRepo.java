package nts.uk.ctx.at.function.infra.repository.employmentinfoterminal.infoterminal.nrweb;

import java.util.List;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.function.dom.employmentinfoterminal.infoterminal.ContractCode;
import nts.uk.ctx.at.function.dom.employmentinfoterminal.infoterminal.nrweb.daily.repo.NRWebQueryDailyItem;
import nts.uk.ctx.at.function.dom.employmentinfoterminal.infoterminal.nrweb.daily.repo.NRWebQueryDailyItemRepo;
import nts.uk.ctx.at.function.infra.entity.employmentinfoterminal.infoterminal.nrweb.KfnmtTrInquiryRecordAtd;

@Stateless
public class JpaNRWebQueryDailyItemRepo extends JpaRepository implements NRWebQueryDailyItemRepo {

	private final static String FIND_WITH_CONTRACT = "SELECT a FROM KfnmtTrInquiryRecordAtd a WHERE a.pk.contractCode = :contractCode ORDER BY a.no DESC";

	@Override
	public List<NRWebQueryDailyItem> findByContractCode(ContractCode contractCode) {
		return this.queryProxy().query(FIND_WITH_CONTRACT, KfnmtTrInquiryRecordAtd.class)
				.setParameter("contractCode", contractCode.v()).getList(x -> toDomain(x));
	}

	private NRWebQueryDailyItem toDomain(KfnmtTrInquiryRecordAtd entity) {
		return new NRWebQueryDailyItem(new ContractCode(entity.pk.contractCode), entity.no, entity.pk.attendanceId);
	}
}
