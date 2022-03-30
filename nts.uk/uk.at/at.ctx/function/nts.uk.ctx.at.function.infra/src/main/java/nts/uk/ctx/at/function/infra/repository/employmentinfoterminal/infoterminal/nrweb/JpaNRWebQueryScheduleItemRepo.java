package nts.uk.ctx.at.function.infra.repository.employmentinfoterminal.infoterminal.nrweb;

import java.util.List;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.function.dom.employmentinfoterminal.infoterminal.ContractCode;
import nts.uk.ctx.at.function.dom.employmentinfoterminal.infoterminal.nrweb.schedule.repo.NRWebQueryScheduleItem;
import nts.uk.ctx.at.function.dom.employmentinfoterminal.infoterminal.nrweb.schedule.repo.NRWebQueryScheduleItemRepo;
import nts.uk.ctx.at.function.infra.entity.employmentinfoterminal.infoterminal.nrweb.KfnmtTrInquiryScheAtd;

@Stateless
public class JpaNRWebQueryScheduleItemRepo extends JpaRepository implements NRWebQueryScheduleItemRepo {

	private final static String FIND_WITH_CONTRACT = "SELECT a FROM KfnmtTrInquiryScheAtd a WHERE a.pk.contractCode = :contractCode ORDER BY a.no DESC";

	@Override
	public List<NRWebQueryScheduleItem> findByContractCode(ContractCode contractCode) {
		return this.queryProxy().query(FIND_WITH_CONTRACT, KfnmtTrInquiryScheAtd.class)
				.setParameter("contractCode", contractCode.v()).getList(x -> toDomain(x));
	}

	private NRWebQueryScheduleItem toDomain(KfnmtTrInquiryScheAtd entity) {
		return new NRWebQueryScheduleItem(new ContractCode(entity.pk.contractCode), entity.no, entity.pk.attendanceId);
	}

}
