package nts.uk.screen.at.app.query.knr.knr001.a;

import java.util.Optional;

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
 *
 * UKDesign.UniversalK.就業.KNR_就業情報端末.KNR001_就業情報端末の登録.メニュー別OCD.選択した端末の情報を取得する.選択した端末の情報を取得する
 *
 * @author xuannt
 *
 */
@Stateless
public class GetSelectedDeviceInfo {

	@Inject
	private EmpInfoTerminalRepository empInfoTerRepo;
	@Inject
	private WorkLocationRepository workPlaceRepository;

	public GetSelectedDeviceInfoDto getDetails(int empInforTerCode) {
		ContractCode contractCode = new ContractCode(AppContexts.user().contractCode());
		String companyID = AppContexts.user().companyId();
		GetSelectedDeviceInfoDto dto = new GetSelectedDeviceInfoDto();
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
		Optional<WorkLocation> workLocation = this.workPlaceRepository.findByCode(companyID, workLocationCD);
		dto.setWorkLocationCode(workLocationCD);
		dto.setEmpInfoTerCode(empInfoTerValue.getEmpInfoTerCode().v());
		dto.setEmpInfoTerName(empInfoTerValue.getEmpInfoTerName().v());
		dto.setModelEmpInfoTer(empInfoTerValue.getModelEmpInfoTer().value);
		dto.setMacAddress(empInfoTerValue.getMacAddress().v());
		dto.setIpAddress(empInfoTerValue.getIpAddress().isPresent() ? empInfoTerValue.getIpAddress().get().v() : "");
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
		String companyID = AppContexts.user().companyId();
		Optional<WorkLocation> workLocation = this.workPlaceRepository.findByCode(companyID, workLocationCD);
		return new GetWorkLocationNameDto(workLocation.isPresent() ? workLocation.get().getWorkLocationName().v() : "");
	}
}
