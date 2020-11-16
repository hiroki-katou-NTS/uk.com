/**
 * 
 */
package nts.uk.screen.at.app.ksu001.getshiftpalette;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.AllArgsConstructor;
import nts.uk.ctx.at.schedule.app.find.shift.shiftpalletsorg.ShiftPalletsOrgDto;
import nts.uk.ctx.at.schedule.app.find.shift.shijtpalletcom.ComPatternScreenDto;
import nts.uk.ctx.at.schedule.app.find.shift.shijtpalletcom.PatternItemScreenDto;
import nts.uk.ctx.at.schedule.app.find.shift.shijtpalletcom.WorkPairSetScreenDto;
import nts.uk.ctx.at.schedule.dom.shift.management.shiftPalette.Combinations;
import nts.uk.ctx.at.schedule.dom.shift.management.shiftPalette.ShiftPaletteCombinations;
import nts.uk.ctx.at.schedule.dom.shift.management.shiftPalette.ShiftPaletteCom;
import nts.uk.ctx.at.schedule.dom.shift.management.shiftPalette.ShiftPaletteComRepository;
import nts.uk.ctx.at.schedule.dom.shift.management.shiftPalette.ShiftPaletteOrg;
import nts.uk.ctx.at.schedule.dom.shift.management.shiftPalette.ShiftPaletteOrgRepository;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.BasicScheduleService;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.SetupType;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.WorkStyle;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.GetCombinationrAndWorkHolidayAtrService;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.ShiftMaster;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.ShiftMasterRepository;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingRepository;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingService;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.internal.PredetermineTimeSetForCalc;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
import nts.uk.screen.at.app.ksu001.displayinshift.ShiftMasterMapWithWorkStyle;
import nts.uk.shr.com.context.AppContexts;

/**
 * @author laitv
 * <<ScreenQuery>> シフトパレットを取得する
 *
 */

@Stateless
public class GetShiftPalette {
	
	@Inject
	private ShiftPaletteComRepository shiftPalletsComRepository;
	@Inject
	private ShiftPaletteOrgRepository shiftPalletsOrgRepository;
	@Inject
	private BasicScheduleService basicScheduleService;
	@Inject
	private WorkTypeRepository workTypeRepo;
	@Inject
	private WorkTimeSettingRepository workTimeSettingRepository;
	@Inject
	private WorkTimeSettingService workTimeSettingService;
	@Inject
	private ShiftMasterRepository shiftMasterRepo;
	
	public GetShiftPaletteResult getDataShiftPallet(GetShiftPaletteParam param) {
		
		GetShiftPaletteResult result = new GetShiftPaletteResult();
		if (param.shiftPaletteWantGet.shiftPalletUnit == ShiftPalletUnit.COMPANY.value) {
			
			result = getShiftPalletCom(param);
			
		} else if (param.shiftPaletteWantGet.shiftPalletUnit == ShiftPalletUnit.WORKPLACE.value) {
			
			result = getShiftPalletWkp(param);
		}
		
		// Step5
		List<String> listShiftMasterCodeOfPageSelectd = result.listShiftMasterCodeOfPageSelectd;
		
		List<ShiftMasterMapWithWorkStyle> listShiftMaster = param.listShiftMasterNotNeedGetNew;
		
		GetCombinationrAndWorkHolidayAtrService.Require require = new RequireImpl(shiftMasterRepo, basicScheduleService, workTypeRepo,workTimeSettingRepository,workTimeSettingService, basicScheduleService);
		
		// listShiftMasterCode này chỉ bao gồm những Code chưa được lưu ở localStorage.
		Map<ShiftMaster,Optional<WorkStyle>> sMap = new HashMap<>();
		if(!listShiftMasterCodeOfPageSelectd.isEmpty()){
			sMap = GetCombinationrAndWorkHolidayAtrService.getCode(require,AppContexts.user().companyId(), listShiftMasterCodeOfPageSelectd);
		}
		
		List<String> listShiftMasterCodeFromUI = param.listShiftMasterNotNeedGetNew.stream().map(mapper -> mapper.getShiftMasterCode()).collect(Collectors.toList()); // ko cần get mới

		for (Map.Entry<ShiftMaster, Optional<WorkStyle>> entry : sMap.entrySet()) {
			String shiftMasterCd = entry.getKey().getShiftMasterCode().toString();
			if(!listShiftMasterCodeFromUI.contains(shiftMasterCd)){
				ShiftMasterMapWithWorkStyle shift = new ShiftMasterMapWithWorkStyle(entry.getKey(), entry.getValue().isPresent() ? entry.getValue().get().value + "" : null);
				listShiftMaster.add(shift);
			}
		}
		result.setListShiftMaster(listShiftMaster);
		return result;
	}
	
	
	public GetShiftPaletteResult getShiftPalletCom(GetShiftPaletteParam param) {
		String companyId = AppContexts.user().companyId();
		// step 1.1
		List<ShiftPaletteCom> listShiftPalletsCom = shiftPalletsComRepository.findShiftPalletUse(companyId);
		
		List<PageInfo> listPageInfo = new ArrayList<>(); // List<ページ, 名称>
		TargetShiftPalette targetShiftPalette = new TargetShiftPalette(param.shiftPaletteWantGet.getPageNumberCom(), new ArrayList<>(), new ArrayList<>()); // 対象のシフトパレット： Optional<ページ, シフトパレット>
		
		if (listShiftPalletsCom.isEmpty()) {
			return new GetShiftPaletteResult(listPageInfo, targetShiftPalette, new ArrayList<>(), new ArrayList<>());
		}
		
		List<ComPatternScreenDto> listShiftPalletComDto = new ArrayList<>();
		
		for (int i = 0; i < listShiftPalletsCom.size(); i++) {
			ShiftPaletteCom shiftPalletsCom = listShiftPalletsCom.get(i);
			listPageInfo.add(new PageInfo(shiftPalletsCom.getPage(),shiftPalletsCom.getShiftPallet().getDisplayInfor().getShiftPalletName().v()));
			
			ComPatternScreenDto shiftPalletCom = new ComPatternScreenDto(shiftPalletsCom.getPage(),
					shiftPalletsCom.getShiftPallet().getDisplayInfor().getShiftPalletName().v(),
					shiftPalletsCom.getShiftPallet().getDisplayInfor().getShiftPalletAtr().value,
					shiftPalletsCom.getShiftPallet().getDisplayInfor().getRemarks().v(),
					shiftPalletsCom.getShiftPallet().getCombinations().stream()
														.map(d -> new PatternItemScreenDto(d.getPositionNumber(),
																						   d.getCombinationName().v(),
																						   d.getCombinations().stream()
														.map(e -> new WorkPairSetScreenDto(e.getOrder(),
																 						   e.getShiftCode().v()))
																							.collect(Collectors.toList())))			   
																							.collect(Collectors.toList()));
			listShiftPalletComDto.add(shiftPalletCom);
		}
		
		targetShiftPalette = new TargetShiftPalette(param.shiftPaletteWantGet.getPageNumberCom(), listShiftPalletComDto, null);
		
		// get List SHiftMasterCode
		Optional<ShiftPaletteCom> shiftPalletsComWantGet = listShiftPalletsCom.stream().filter(i -> i.getPage() == param.shiftPaletteWantGet.getPageNumberCom()).findFirst();
		List<ShiftPaletteCombinations> combinations = shiftPalletsComWantGet.isPresent() ? shiftPalletsComWantGet.get().getShiftPallet().getCombinations() : listShiftPalletsCom.get(0).getShiftPallet().getCombinations();
		List<String> listShiftMasterCodeOfPageSelectd = getListShiftMasterCode(combinations).stream().collect(Collectors.toList()); 
		
		return new GetShiftPaletteResult(listPageInfo, targetShiftPalette, new ArrayList<>(), listShiftMasterCodeOfPageSelectd);									
	}
	
	public GetShiftPaletteResult getShiftPalletWkp(GetShiftPaletteParam param) {
		// 0 = work place
		// step 1.2
		List<ShiftPaletteOrg> listShiftPalletsOrg = new ArrayList<>();
		if (param.unit == 0) {
			listShiftPalletsOrg = shiftPalletsOrgRepository.findbyWorkPlaceIdUse(0, param.workplaceId );
		}else{
			listShiftPalletsOrg = shiftPalletsOrgRepository.findbyWorkPlaceIdUse(1, param.workplaceGroupId );
		}
		
		List<PageInfo> listPageInfo = new ArrayList<>(); // List<ページ, 名称>
		TargetShiftPalette targetShiftPalette = new TargetShiftPalette(param.shiftPaletteWantGet.getPageNumberOrg(), new ArrayList<>(), new ArrayList<>());; // 対象のシフトパレット： Optional<ページ, シフトパレット>
		
		if (listShiftPalletsOrg.isEmpty()) {
			return new GetShiftPaletteResult(listPageInfo, targetShiftPalette, new ArrayList<>(), new ArrayList<>());
		}
		
		List<ShiftPalletsOrgDto> listShiftPalletOrgDto = new ArrayList<>();
		for (int i = 0; i < listShiftPalletsOrg.size(); i++) {
			ShiftPaletteOrg shiftPalletsOrg = listShiftPalletsOrg.get(i);
			listPageInfo.add(new PageInfo(shiftPalletsOrg.getPage(), shiftPalletsOrg.getShiftPallet().getDisplayInfor().getShiftPalletName().v()));
			ShiftPalletsOrgDto shiftPalletsOrgDto = new ShiftPalletsOrgDto(shiftPalletsOrg, param.getWorkplaceId());
			listShiftPalletOrgDto.add(shiftPalletsOrgDto);
		}
		targetShiftPalette = new TargetShiftPalette(param.shiftPaletteWantGet.getPageNumberOrg(), null, listShiftPalletOrgDto);
		
		// get List ShiftMasterCode
		Optional<ShiftPaletteOrg> shiftPalletsOrg = listShiftPalletsOrg.stream().filter(i -> i.getPage() == param.shiftPaletteWantGet.getPageNumberOrg()).findFirst();
		List<ShiftPaletteCombinations> combinations = shiftPalletsOrg.isPresent() ? shiftPalletsOrg.get().getShiftPallet().getCombinations() : listShiftPalletsOrg.get(0).getShiftPallet().getCombinations();
		List<String> listShiftMasterCodeOfPageSelectd = getListShiftMasterCode(combinations).stream().collect(Collectors.toList());
		
		return new GetShiftPaletteResult(listPageInfo, targetShiftPalette, new ArrayList<>(), listShiftMasterCodeOfPageSelectd);	
	}
	
	public List<String> getListShiftMasterCode(List<ShiftPaletteCombinations> shiftPalletCombinations){
		
		Set<String>  listShiftMasterCodeOfPage = new HashSet<>();  // danh sach nay chỉ bao gôm những shiftMasterCode mới lấy.
		
		if (shiftPalletCombinations.isEmpty()) {
			return new ArrayList<>();
		}
		
		for (int i = 0; i < shiftPalletCombinations.size(); i++) {
			List<Combinations> combinations = shiftPalletCombinations.get(i).getCombinations();
			for (int j = 0; j < combinations.size(); j++) {
				String shiftMasterCode = combinations.get(j).getShiftCode().v();
				listShiftMasterCodeOfPage.add(shiftMasterCode);
			}
		}
		return listShiftMasterCodeOfPage.stream().collect(Collectors.toList());
	};
	
	@AllArgsConstructor
	private static class RequireImpl implements GetCombinationrAndWorkHolidayAtrService.Require {
		
		private final String companyId = AppContexts.user().companyId();
		
		@Inject
		private ShiftMasterRepository shiftMasterRepo;
		@Inject
		private BasicScheduleService service;
		@Inject
		private WorkTypeRepository workTypeRepo;
		@Inject
		private WorkTimeSettingRepository workTimeSettingRepository;
		@Inject
		private WorkTimeSettingService workTimeSettingService;
		@Inject
		private BasicScheduleService basicScheduleService;
		
		@Override
		public List<ShiftMaster> getByListEmp(String companyID, List<String> lstShiftMasterCd) {
			List<ShiftMaster> data = shiftMasterRepo.getByListShiftMaterCd2(companyId, lstShiftMasterCd);
			return data;
		}

		@Override
		public List<ShiftMaster> getByListWorkInfo(String companyId, List<WorkInformation> lstWorkInformation) {
			List<ShiftMaster> data = shiftMasterRepo.get(companyId, lstWorkInformation);
			return data;
		}

		@Override
		public SetupType checkNeededOfWorkTimeSetting(String workTypeCode) {
			 return service.checkNeededOfWorkTimeSetting(workTypeCode);
		}

		@Override
		public Optional<WorkType> getWorkType(String workTypeCd) {
			return workTypeRepo.findByPK(companyId, workTypeCd);
		}

		@Override
		public Optional<WorkTimeSetting> getWorkTime(String workTimeCode) {
			return workTimeSettingRepository.findByCode(companyId, workTimeCode);
		}

		@Override
		public PredetermineTimeSetForCalc getPredeterminedTimezone(String workTypeCd, String workTimeCd, Integer workNo) {
			return workTimeSettingService .getPredeterminedTimezone(companyId, workTimeCd, workTypeCd, workNo);
		}

		@Override
		public WorkStyle checkWorkDay(String workTypeCode) {
			return basicScheduleService.checkWorkDay(workTypeCode);
		}
	}
}
