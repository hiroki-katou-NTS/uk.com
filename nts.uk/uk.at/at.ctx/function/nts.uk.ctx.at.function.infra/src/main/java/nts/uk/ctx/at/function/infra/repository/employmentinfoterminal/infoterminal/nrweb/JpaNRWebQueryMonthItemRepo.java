package nts.uk.ctx.at.function.infra.repository.employmentinfoterminal.infoterminal.nrweb;

import java.util.Arrays;
import java.util.List;

import javax.ejb.Stateless;

import nts.uk.ctx.at.function.dom.employmentinfoterminal.infoterminal.ContractCode;
import nts.uk.ctx.at.function.dom.employmentinfoterminal.infoterminal.nrweb.month.repo.NRWebQueryMonthItem;
import nts.uk.ctx.at.function.dom.employmentinfoterminal.infoterminal.nrweb.month.repo.NRWebQueryMonthItemRepo;

@Stateless
public class JpaNRWebQueryMonthItemRepo implements NRWebQueryMonthItemRepo {

	@Override
	public List<NRWebQueryMonthItem> findByContractCode(ContractCode contractCode) {
		return Arrays.asList(new NRWebQueryMonthItem(contractCode,1, 31));
	}

}
