package nts.uk.screen.at.app.query.knr.knr001.a;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminal;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.repo.EmpInfoTerminalRepository;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.ContractCode;
import nts.uk.ctx.at.shared.dom.supportmanagement.supportoperationsetting.SupportOperationSetting;
import nts.uk.ctx.at.shared.dom.supportmanagement.supportoperationsetting.SupportOperationSettingRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * UKDesign.UniversalK.就業.KNR_就業情報端末.KNR001_就業情報端末の登録.メニュー別OCD.就業情報端末の一覧表示を取得する
 * 
 * @author xuannt
 *
 */
@Stateless
public class GetEmpInfoTerminalList {

	@Inject
	private EmpInfoTerminalRepository empInfoTerRepo;

	@Inject
	private SupportOperationSettingRepository supportOperationSettingRepo;

	public GetEmpInfoTerminalListOutputDto getAll() {
		ContractCode contractCode = new ContractCode(AppContexts.user().contractCode());
		String cid = AppContexts.user().companyId();

		// １．get(会社ID)：応援の運用設定
		SupportOperationSetting supportOperationSetting = this.supportOperationSettingRepo.get(cid);
		// ２．取得する（ログイン者の契約コード）： List<就業情報端末>
		List<EmpInfoTerminal> empInfoTerList = this.empInfoTerRepo.get(contractCode);
		
		List<EmpInfoTerminalListDto> empInfoTerminalListDto = empInfoTerList.stream().map(e -> {
																				EmpInfoTerminalListDto dto = new EmpInfoTerminalListDto();
																				dto.setEmpInfoTerCode(e.getEmpInfoTerCode().v());
																				dto.setEmpInfoTerName(e.getEmpInfoTerName().v());
																				return dto;
																			}).collect(Collectors.toList());
		
		GetEmpInfoTerminalListOutputDto empInfoTerminalListOutputDto = new GetEmpInfoTerminalListOutputDto();
		empInfoTerminalListOutputDto.setUsedSupportOperationSetting(supportOperationSetting.isUsed());
		empInfoTerminalListOutputDto.setEmpInfoTerminalListDto(empInfoTerminalListDto.isEmpty()? Collections.emptyList() : empInfoTerminalListDto);
		return empInfoTerminalListOutputDto;
	}

}
