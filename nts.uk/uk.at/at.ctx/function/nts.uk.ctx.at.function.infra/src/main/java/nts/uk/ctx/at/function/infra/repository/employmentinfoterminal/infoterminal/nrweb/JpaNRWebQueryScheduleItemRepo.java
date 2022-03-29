package nts.uk.ctx.at.function.infra.repository.employmentinfoterminal.infoterminal.nrweb;

import java.util.Arrays;
import java.util.List;

import javax.ejb.Stateless;

import nts.uk.ctx.at.function.dom.employmentinfoterminal.infoterminal.ContractCode;
import nts.uk.ctx.at.function.dom.employmentinfoterminal.infoterminal.nrweb.schedule.repo.NRWebQueryScheduleItem;
import nts.uk.ctx.at.function.dom.employmentinfoterminal.infoterminal.nrweb.schedule.repo.NRWebQueryScheduleItemRepo;

@Stateless
public class JpaNRWebQueryScheduleItemRepo implements NRWebQueryScheduleItemRepo {

	@Override
	public List<NRWebQueryScheduleItem> findByContractCode(ContractCode contractCode) {
		return Arrays.asList(new NRWebQueryScheduleItem(contractCode, 1, 28),
				new NRWebQueryScheduleItem(contractCode, 1, 29), new NRWebQueryScheduleItem(contractCode, 1, 31), new NRWebQueryScheduleItem(contractCode, 1, 31), new NRWebQueryScheduleItem(contractCode, 1, 1));
	}

}
