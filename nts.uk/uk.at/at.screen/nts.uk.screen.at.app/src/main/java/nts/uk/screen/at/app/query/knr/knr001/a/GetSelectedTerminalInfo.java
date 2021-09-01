package nts.uk.screen.at.app.query.knr.knr001.a;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminal;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminalCode;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.MSConversionInfo;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.NRConvertInfo;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.repo.EmpInfoTerminalRepository;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.ContractCode;
import nts.uk.ctx.at.record.dom.stampmanagement.workplace.WorkLocation;
import nts.uk.ctx.at.record.dom.stampmanagement.workplace.WorkLocationRepository;
import nts.uk.ctx.bs.employee.pub.workplace.master.WorkplacePub;
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
	
	@Inject
	private WorkplacePub workplacePub;

	public GetSelectedTerminalInfoDto getDetails(String empInforTerCode) {
		
		ContractCode contractCode = new ContractCode(AppContexts.user().contractCode());
		String companyId = AppContexts.user().companyId();
		GeneralDate systemDate = GeneralDate.today();
		
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
		
		if (empInfoTerValue.getCreateStampInfo().getWorkPlaceId().isPresent()) {
			List<String> listWorkPlaceId = new ArrayList<String>();
			listWorkPlaceId.add(empInfoTerValue.getCreateStampInfo().getWorkPlaceId().get().v());
			String workplaceName = workplacePub.getWorkplaceInforByWkpIds(companyId, listWorkPlaceId, systemDate)
											.get(0)
											.getWorkplaceName();
			
			dto.setWorkplaceId(empInfoTerValue.getCreateStampInfo().getWorkPlaceId().get().v());
			dto.setWorkplaceName(workplaceName);
		}
		
		if (empInfoTerValue.getCreateStampInfo().getStampInfoConver() instanceof MSConversionInfo) {
			List<MSConversionDto> lstMSConversion = ((MSConversionInfo) empInfoTerValue.getCreateStampInfo().getStampInfoConver()).getLstMSConversion()
														.stream()
														.map(e -> MSConversionDto.toDto(e))
														.collect(Collectors.toList());
			
			dto.setLstMSConversion(lstMSConversion);
		}
		
		if (empInfoTerValue.getCreateStampInfo().getStampInfoConver() instanceof NRConvertInfo) {
			NRConvertInfoDto nRConvertInfo = NRConvertInfoDto.toDto(((NRConvertInfo) empInfoTerValue.getCreateStampInfo().getStampInfoConver()));
			dto.setNRConvertInfo(nRConvertInfo);
			dto.setLstMSConversion(new ArrayList<MSConversionDto>());
		}
		
		
		dto.setWorkLocationCode(workLocationCD);
		dto.setEmpInfoTerCode(empInfoTerValue.getEmpInfoTerCode().v());
		dto.setEmpInfoTerName(empInfoTerValue.getEmpInfoTerName().v());
		dto.setModelEmpInfoTer(empInfoTerValue.getModelEmpInfoTer().value);
		dto.setMacAddress(empInfoTerValue.getMacAddress().v());
		dto.setIpAddress1(empInfoTerValue.getIpAddress().isPresent() ? String.valueOf(empInfoTerValue.getIpAddress().get().getNet1()) : "");
		dto.setIpAddress2(empInfoTerValue.getIpAddress().isPresent() ? String.valueOf(empInfoTerValue.getIpAddress().get().getNet2()) : "");
		dto.setIpAddress3(empInfoTerValue.getIpAddress().isPresent() ? String.valueOf(empInfoTerValue.getIpAddress().get().getHost1()) : "");
		dto.setIpAddress4(empInfoTerValue.getIpAddress().isPresent() ? String.valueOf(empInfoTerValue.getIpAddress().get().getHost2()) : "");
		dto.setTerSerialNo(
				empInfoTerValue.getTerSerialNo().isPresent() ? empInfoTerValue.getTerSerialNo().get().v() : "");
		
		dto.setWorkLocationName(workLocation.isPresent() ? workLocation.get().getWorkLocationName().v() : "");
		dto.setIntervalTime(empInfoTerValue.getIntervalTime().v());
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
