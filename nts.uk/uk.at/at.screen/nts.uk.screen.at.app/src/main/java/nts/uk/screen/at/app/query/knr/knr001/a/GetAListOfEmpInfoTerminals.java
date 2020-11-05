package nts.uk.screen.at.app.query.knr.knr001.a;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminal;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.repo.EmpInfoTerminalRepository;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.ContractCode;
import nts.uk.shr.com.context.AppContexts;

/**
 * UKDesign.UniversalK.就業.KNR_就業情報端末.KNR001_就業情報端末の登録.メニュー別OCD.就業情報端末の一覧表示を取得する
 * 
 * @author xuannt
 *
 */
@Stateless
public class GetAListOfEmpInfoTerminals {
	@Inject
	private EmpInfoTerminalRepository empInfoTerRepo;

	public List<GetAListOfEmpInfoTerminalsDto> getAll() {
		ContractCode contractCode = new ContractCode(AppContexts.user().contractCode());
		List<EmpInfoTerminal> empInfoTerList = this.empInfoTerRepo.get(contractCode);

		return empInfoTerList.stream().map(e -> {
			GetAListOfEmpInfoTerminalsDto dto = new GetAListOfEmpInfoTerminalsDto();
			dto.setEmpInfoTerCode(e.getEmpInfoTerCode().v());
			dto.setEmpInfoTerName(e.getEmpInfoTerName().v());
			return dto;
		}).collect(Collectors.toList());
	}

}
