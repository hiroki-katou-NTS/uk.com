package nts.uk.ctx.at.function.infra.repository.employmentinfoterminal.infoterminal.nrweb;

import java.util.List;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.function.dom.employmentinfoterminal.infoterminal.ContractCode;
import nts.uk.ctx.at.function.dom.employmentinfoterminal.infoterminal.nrweb.month.repo.NRWebQueryMonthItem;
import nts.uk.ctx.at.function.dom.employmentinfoterminal.infoterminal.nrweb.month.repo.NRWebQueryMonthItemRepo;
import nts.uk.ctx.at.function.infra.entity.employmentinfoterminal.infoterminal.nrweb.KfnmtTrInquiryMonAtd;

@Stateless
public class JpaNRWebQueryMonthItemRepo extends JpaRepository implements NRWebQueryMonthItemRepo {

	private final static String FIND_WITH_CONTRACT = "SELECT a FROM KfnmtTrInquiryMonAtd a WHERE a.pk.contractCode = :contractCode ORDER BY a.no DESC";

	@Override
	public List<NRWebQueryMonthItem> findByContractCode(ContractCode contractCode) {
		return this.queryProxy().query(FIND_WITH_CONTRACT, KfnmtTrInquiryMonAtd.class)
				.setParameter("contractCode", contractCode.v()).getList(x -> toDomain(x));
	}

	private NRWebQueryMonthItem toDomain(KfnmtTrInquiryMonAtd entity) {
		return new NRWebQueryMonthItem(new ContractCode(entity.pk.contractCode), entity.no, entity.pk.attendanceId);
	}
}
