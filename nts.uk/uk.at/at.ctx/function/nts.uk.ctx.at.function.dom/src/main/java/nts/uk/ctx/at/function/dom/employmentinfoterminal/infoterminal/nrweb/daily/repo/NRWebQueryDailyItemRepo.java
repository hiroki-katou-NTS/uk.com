package nts.uk.ctx.at.function.dom.employmentinfoterminal.infoterminal.nrweb.daily.repo;

import java.util.List;

import nts.uk.ctx.at.function.dom.employmentinfoterminal.infoterminal.ContractCode;

public interface NRWebQueryDailyItemRepo {

	public List<NRWebQueryDailyItem> findByContractCode(ContractCode contractCode);
	
}
