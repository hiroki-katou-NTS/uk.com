package nts.uk.screen.at.app.query.knr.knr002.d;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminal;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminalCode;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.repo.EmpInfoTerminalRepository;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.ContractCode;
import nts.uk.ctx.at.record.dom.worklocation.WorkLocation;
import nts.uk.ctx.at.record.dom.worklocation.WorkLocationRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * UKDesign.UniversalK.就業.KNR_就業情報端末.KNR002_就業情報端末の監視.Ｄ：リモート設定複写ダイアログ.メニュー別OCD.Ｄ：リモート設定の複写先を取得する
 * 
 * @author xuannt
 *
 */
@Stateless
public class GetDestinationCopy {
	// 就業情報端末Repository.[5] 端末コードの含まないリスト取得する 
	@Inject
	private EmpInfoTerminalRepository empInfoTerRepo;
	@Inject
	private WorkLocationRepository workPlaceRepository;


	public List<GetDestinationCopyDto> getEmpInfoTerDestinalList(String empInforTerCode) {
		ContractCode contractCode = new ContractCode(AppContexts.user().contractCode());
		String companyID = AppContexts.user().companyId();
		// get the Selected Employment Information Terminal
		Optional<EmpInfoTerminal> empInfoTer = this.empInfoTerRepo
				.getEmpInfoTerminal(new EmpInfoTerminalCode(empInforTerCode), contractCode);
		if (!empInfoTer.isPresent())
			return null;
		List<EmpInfoTerminal> empInfoTerList = this.empInfoTerRepo.getEmpInfoTerminalNotIncludeCode(contractCode, new EmpInfoTerminalCode(empInforTerCode));
		if(null == empInfoTerList)
			return null;
		return empInfoTerList.stream().map(e -> {
			GetDestinationCopyDto dto = new GetDestinationCopyDto();
			String workLocationCD = e.getCreateStampInfo().getWorkLocationCd().isPresent()?
									e.getCreateStampInfo().getWorkLocationCd().get().v() : "";
			Optional<WorkLocation> workLocation = this.workPlaceRepository.findByCode(companyID, workLocationCD);
			dto.setWorkLocationCode(workLocationCD);
			dto.setEmpInfoTerCode(e.getEmpInfoTerCode().v());
			dto.setEmpInfoTerName(e.getEmpInfoTerName().v());
			dto.setModelEmpInfoTer(e.getModelEmpInfoTer().value);
			dto.setMacAddress(e.getMacAddress().v());
			dto.setIpAddress(e.getIpAddress().isPresent()?
							 e.getIpAddress().get().getFullIpAddress() : "");
			dto.setTerSerialNo(e.getTerSerialNo().isPresent()?
							   e.getTerSerialNo().get().v() : "");
			dto.setWorkLocationName(workLocation.isPresent()?
									workLocation.get().getWorkLocationName().v() : "");
			dto.setIntervalTime(e.getIntervalTime().v());
			dto.setOutSupport(e.getCreateStampInfo().getConvertEmbCate().getOutSupport().value);
			dto.setReplace(e.getCreateStampInfo().getOutPlaceConvert().getReplace().value);
			dto.setGoOutReason(e.getCreateStampInfo().getOutPlaceConvert().getGoOutReason().isPresent()?
							   e.getCreateStampInfo().getOutPlaceConvert().getGoOutReason().get().value : null);
			dto.setEntranceExit(e.getCreateStampInfo().getConvertEmbCate().getEntranceExit().value);
			dto.setMemo(e.getEmpInfoTerMemo().isPresent()?
						e.getEmpInfoTerMemo().get().v() : "");
			return dto;
		}).collect(Collectors.toList());
	}
}
