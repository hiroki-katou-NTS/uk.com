package nts.uk.screen.at.app.query.knr.knr002.d;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminal;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminalCode;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.repo.EmpInfoTerminalRepository;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.nrlremote.TimeRecordSetFormatList;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.nrlremote.repo.TimeRecordSetFormatListRepository;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.ContractCode;
import nts.uk.ctx.at.record.dom.stampmanagement.workplace.WorkLocation;
import nts.uk.ctx.at.record.dom.stampmanagement.workplace.WorkLocationRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * UKDesign.UniversalK.就業.KNR_就業情報端末.KNR002_就業情報端末の監視.Ｄ：リモート設定複写ダイアログ.メニュー別OCD.Ｄ：リモート設定の複写先を取得する
 * 
 * @author xuannt
 *
 */
@Stateless
public class GetDestinationCopy {
	//	就業情報端末Repository.[5] 端末コードの含まないリスト取得する 
	@Inject
	private EmpInfoTerminalRepository empInfoTerRepo;
	//	打刻情報の作成
	@Inject
	private WorkLocationRepository workPlaceRepository;
	//	タイムレコード設定フォーマットリストRepository.get
	@Inject
	private TimeRecordSetFormatListRepository timeRecordSetFormatListRepository;


	
	/**
	 * 端末コードの含まないリスト取得する 
	 * @param empInforTerCode
	 * @return
	 */
	public List<GetDestinationCopyDto> getEmpInfoTerDestinalList(String empInforTerCode) {
		ContractCode contractCode = new ContractCode(AppContexts.user().contractCode());
		String companyID = AppContexts.user().companyId();
		List<EmpInfoTerminal> empInfoTerList = this.empInfoTerRepo.getEmpInfoTerminalNotIncludeCode(contractCode, new EmpInfoTerminalCode(empInforTerCode));
		if (empInfoTerList.isEmpty())
			return null;
		
		List<TimeRecordSetFormatList> machineInfoLst = this.timeRecordSetFormatListRepository
														   .get(contractCode, empInfoTerList.stream().map(e -> e.getEmpInfoTerCode()).collect(Collectors.toList()));
		return empInfoTerList.stream().map(e -> {
			GetDestinationCopyDto dto = new GetDestinationCopyDto();
			Optional<TimeRecordSetFormatList> machineInfo = machineInfoLst.stream().filter(x -> Integer.parseInt(x.getEmpInfoTerCode().v()) == Integer.parseInt(e.getEmpInfoTerCode().v())).findAny();
			if(!machineInfo.isPresent()) {
				return null;
			}
			TimeRecordSetFormatList machineInfoVal = machineInfo.get();
			String workLocationCD = e.getCreateStampInfo().getWorkLocationCd().isPresent()?
									e.getCreateStampInfo().getWorkLocationCd().get().v() : "";
			Optional<WorkLocation> workLocation = this.workPlaceRepository.findByCode(companyID, workLocationCD);
			dto.setWorkLocationCode(workLocationCD);
			dto.setEmpInfoTerCode(e.getEmpInfoTerCode().v());
			dto.setEmpInfoTerName(e.getEmpInfoTerName().v());
			dto.setModelEmpInfoTer(e.getModelEmpInfoTer().value);
			dto.setMacAddress(e.getMacAddress().v());
			dto.setIpAddress(e.getIpAddress().isPresent()?
							 e.getIpAddress().get().toString() : "");
			dto.setTerSerialNo(e.getTerSerialNo().isPresent()?
							   e.getTerSerialNo().get().v() : "");
			dto.setWorkLocationName(workLocation.isPresent()?
									workLocation.get().getWorkLocationName().v() : "");
			dto.setIntervalTime(e.getIntervalTime().v());
			//TODO: set value to dto (temporary fixed) #20210520
			dto.setOutSupport(0);
			dto.setReplace(0);
			dto.setGoOutReason(0);
			dto.setEntranceExit(0);
			dto.setMemo(e.getEmpInfoTerMemo().isPresent()?
						e.getEmpInfoTerMemo().get().v() : "");		
			dto.setTrName(machineInfoVal.getEmpInfoTerName().v());
			dto.setRomVersion(machineInfoVal.getRomVersion().v());
			return dto;
		}).filter(t -> null != t).collect(Collectors.toList());
	}
}
