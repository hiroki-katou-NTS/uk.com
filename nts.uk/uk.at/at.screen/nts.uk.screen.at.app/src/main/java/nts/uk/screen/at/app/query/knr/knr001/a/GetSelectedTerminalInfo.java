package nts.uk.screen.at.app.query.knr.knr001.a;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminal;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminalCode;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.repo.EmpInfoTerminalRepository;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.ContractCode;
import nts.uk.ctx.at.record.dom.stampmanagement.workplace.WorkLocation;
import nts.uk.ctx.at.record.dom.stampmanagement.workplace.WorkLocationRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 *
 * UKDesign.UniversalK.就業.KNR_就業情報端末.KNR001_就業情報端末の登録.メニュー別OCD.選択した端末の情報を取得する.選択した端末の情報を取得する
 *
 * @author xuannt
 *
 */
@Stateless
public class GetSelectedTerminalInfo {

	@Inject
	private EmpInfoTerminalRepository empInfoTerRepo;
	
	@Inject
	private WorkLocationRepository workPlaceRepository;

	public GetSelectedTerminalInfoDto getDetails(String empInforTerCode) {
		
		ContractCode contractCode = new ContractCode(AppContexts.user().contractCode());
		
		//	String companyID = AppContexts.user().companyId();
		
		GetSelectedTerminalInfoDto dto = new GetSelectedTerminalInfoDto();
		
		Optional<EmpInfoTerminal> empInfoTer = this.empInfoTerRepo
				.getEmpInfoTerminal(new EmpInfoTerminalCode(empInforTerCode), contractCode);
		
		// check existed
		if (!empInfoTer.isPresent()) {
			return dto;
		}
		
		EmpInfoTerminal empInfoTerValue = empInfoTer.get();
		
		String workLocationCD = empInfoTerValue.getCreateStampInfo().getWorkLocationCd().isPresent()
				? empInfoTerValue.getCreateStampInfo().getWorkLocationCd().get().v()
				: "";
		
		Optional<WorkLocation> workLocation = this.workPlaceRepository.findByCode(contractCode.toString(), workLocationCD);
		
		dto.setWorkLocationCode(workLocationCD);
		dto.setEmpInfoTerCode(empInfoTerValue.getEmpInfoTerCode().v());
		dto.setEmpInfoTerName(empInfoTerValue.getEmpInfoTerName().v());
		dto.setModelEmpInfoTer(empInfoTerValue.getModelEmpInfoTer().value);
		dto.setMacAddress(empInfoTerValue.getMacAddress().v());
//		dto.setIpAddress(empInfoTerValue.getIpAddress().isPresent() ? empInfoTerValue.getIpAddress().get().v() : "");
		dto.setIpAddress1(empInfoTerValue.getIpAddress().isPresent() ? String.valueOf(empInfoTerValue.getIpAddress().get().getNet1()) : "");
		dto.setIpAddress2(empInfoTerValue.getIpAddress().isPresent() ? String.valueOf(empInfoTerValue.getIpAddress().get().getNet2()) : "");
		dto.setIpAddress3(empInfoTerValue.getIpAddress().isPresent() ? String.valueOf(empInfoTerValue.getIpAddress().get().getHost1()) : "");
		dto.setIpAddress4(empInfoTerValue.getIpAddress().isPresent() ? String.valueOf(empInfoTerValue.getIpAddress().get().getHost2()) : "");
		dto.setTerSerialNo(
				empInfoTerValue.getTerSerialNo().isPresent() ? empInfoTerValue.getTerSerialNo().get().v() : "");
		dto.setWorkLocationName(workLocation.isPresent() ? workLocation.get().getWorkLocationName().v() : "");
		dto.setIntervalTime(empInfoTerValue.getIntervalTime().v());
		dto.setOutSupport(empInfoTerValue.getCreateStampInfo().getConvertEmbCate().getOutSupport().value);
		dto.setReplace(empInfoTerValue.getCreateStampInfo().getOutPlaceConvert().getReplace().value);
		dto.setGoOutReason(empInfoTerValue.getCreateStampInfo().getOutPlaceConvert().getGoOutReason().isPresent()
				? empInfoTerValue.getCreateStampInfo().getOutPlaceConvert().getGoOutReason().get().value
				: null);
		dto.setEntranceExit(empInfoTerValue.getCreateStampInfo().getConvertEmbCate().getEntranceExit().value);
		dto.setMemo(
				empInfoTerValue.getEmpInfoTerMemo().isPresent() ? empInfoTerValue.getEmpInfoTerMemo().get().v() : "");
		
		return dto;
	}

	public GetWorkLocationNameDto getWorkLocationName(String workLocationCD) {
		//	String companyID = AppContexts.user().companyId();
		if(workLocationCD.isEmpty())
			return new GetWorkLocationNameDto("");
		
		String contractCode = AppContexts.user().contractCode();
		Optional<WorkLocation> workLocation = this.workPlaceRepository.findByCode(contractCode, workLocationCD);	
		return new GetWorkLocationNameDto(workLocation.isPresent() ? workLocation.get().getWorkLocationName().v() : "");
	}
}
