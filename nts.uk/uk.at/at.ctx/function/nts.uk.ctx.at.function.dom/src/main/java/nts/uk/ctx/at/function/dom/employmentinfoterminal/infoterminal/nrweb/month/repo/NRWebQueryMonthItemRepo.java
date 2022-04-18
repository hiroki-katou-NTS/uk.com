package nts.uk.ctx.at.function.dom.employmentinfoterminal.infoterminal.nrweb.month.repo;

import java.util.List;

import nts.uk.ctx.at.function.dom.employmentinfoterminal.infoterminal.ContractCode;

public interface NRWebQueryMonthItemRepo {
	public List<NRWebQueryMonthItem> findByContractCode(ContractCode contractCode);
}
