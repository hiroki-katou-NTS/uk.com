package nts.uk.ctx.at.function.infra.repository.employmentinfoterminal.infoterminal.nrweb;

import java.util.Arrays;
import java.util.List;

import javax.ejb.Stateless;

import nts.uk.ctx.at.function.dom.employmentinfoterminal.infoterminal.ContractCode;
import nts.uk.ctx.at.function.dom.employmentinfoterminal.infoterminal.nrweb.daily.repo.NRWebQueryDailyItem;
import nts.uk.ctx.at.function.dom.employmentinfoterminal.infoterminal.nrweb.daily.repo.NRWebQueryDailyItemRepo;

@Stateless
public class JpaNRWebQueryDailyItemRepo implements NRWebQueryDailyItemRepo{

	@Override
	public List<NRWebQueryDailyItem> findByContractCode(ContractCode contractCode) {
		// TODO Auto-generated method stub
		return Arrays.asList(new NRWebQueryDailyItem(contractCode, 1, 28),
				new NRWebQueryDailyItem(contractCode, 1, 29), new NRWebQueryDailyItem(contractCode, 1, 31));
	}

}
