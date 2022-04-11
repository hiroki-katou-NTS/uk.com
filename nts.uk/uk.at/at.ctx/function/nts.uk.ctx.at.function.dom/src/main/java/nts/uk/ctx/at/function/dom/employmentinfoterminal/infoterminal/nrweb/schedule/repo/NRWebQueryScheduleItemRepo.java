package nts.uk.ctx.at.function.dom.employmentinfoterminal.infoterminal.nrweb.schedule.repo;

import java.util.List;

import nts.uk.ctx.at.function.dom.employmentinfoterminal.infoterminal.ContractCode;

public interface NRWebQueryScheduleItemRepo {

	public List<NRWebQueryScheduleItem> findByContractCode(ContractCode contractCode);
	
}
