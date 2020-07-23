/**
 * 
 */
package nts.uk.screen.at.app.ksu001.getshiftpalette;

import java.util.ArrayList;
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
import nts.uk.ctx.at.schedule.dom.shift.management.Combinations;
import nts.uk.ctx.at.schedule.dom.shift.management.ShiftPalletCombinations;
import nts.uk.ctx.at.schedule.dom.shift.management.ShiftPalletsCom;
import nts.uk.ctx.at.schedule.dom.shift.management.ShiftPalletsComRepository;
import nts.uk.ctx.at.schedule.dom.shift.management.ShiftPalletsOrg;
import nts.uk.ctx.at.schedule.dom.shift.management.ShiftPalletsOrgRepository;
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
import nts.uk.shr.com.context.AppContexts;

/**
 * @author laitv
 * <<ScreenQuery>> シフトパレットを取得する
 *
 */

@Stateless
public class GetShiftPalette {
	
	@Inject
	private ShiftPalletsComRepository shiftPalletsComRepository;
	@Inject
	private ShiftPalletsOrgRepository shiftPalletsOrgRepository;
	
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
	
	
	public GetShiftPaletteResult getData(GetShiftPaletteParam param) {
		
		GetShiftPaletteResult result = new GetShiftPaletteResult();
		if (param.shiftPalletUnit == ShiftPalletUnit.COMPANY.value) {
			
			result = getShiftPalletCom(param);
			
		}else if (param.shiftPalletUnit == ShiftPalletUnit.WORKPLACE.value) {
			
			result = getbyWorkPlaceId(param);
		}
		
		// Step5
		List<String> listShiftMasterCode = result.lstShiftMasterCode;
		List<Shift> listOfShift = new ArrayList<>();
		
		GetCombinationrAndWorkHolidayAtrService.Require require = new RequireImpl(shiftMasterRepo);
		WorkInformation.Require workRequired = new WorkInfoRequireImpl(basicScheduleService, workTypeRepo,workTimeSettingRepository,workTimeSettingService, basicScheduleService);
		
		Map<ShiftMaster,Optional<WorkStyle>> sMap = GetCombinationrAndWorkHolidayAtrService.getCode(require, workRequired, AppContexts.user().companyId(), listShiftMasterCode);
		for (Map.Entry<ShiftMaster, Optional<WorkStyle>> entry : sMap.entrySet()) {
			System.out.println("Item : " + entry.getKey() + " Count : " + entry.getValue());
			Shift shift = new Shift(new ShiftMasterDto(entry.getKey()), entry.getValue());
			listOfShift.add(shift);
		}
		result.setListOfShift(listOfShift);
		return result;
	}
	
	public GetShiftPaletteResult getShiftPalletCom(GetShiftPaletteParam param) {
		String companyId = AppContexts.user().companyId();
		// step 1.1
		List<ShiftPalletsCom> listShiftPalletsCom = shiftPalletsComRepository.findShiftPalletUse(companyId);
		
		List<PageInfo> listPageInfo = new ArrayList<>(); // List<ページ, 名称>
		Optional<TargetShiftPalette> targetShiftPalette = Optional.empty(); // 対象のシフトパレット： Optional<ページ, シフトパレット>
		
		if (listShiftPalletsCom.isEmpty()) {
			return new GetShiftPaletteResult(listPageInfo, targetShiftPalette, new ArrayList<>(),new ArrayList<>());
		}
		
		
		for (int i = 0; i < listShiftPalletsCom.size(); i++) {
			ShiftPalletsCom shiftPalletsCom = listShiftPalletsCom.get(i);
			listPageInfo.add(new PageInfo(shiftPalletsCom.getPage(),
					shiftPalletsCom.getShiftPallet().getDisplayInfor().getShiftPalletName().v()));
		}
		ShiftPalletsCom shiftPalletsCom = null;
		if (param.dataLocalstorageEmpty) {
			shiftPalletsCom = listShiftPalletsCom.stream().filter(i -> i.getPage() == 0).findFirst().get();
			ComPatternScreenDto shiftPallet = new ComPatternScreenDto(shiftPalletsCom.getPage(),
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
			TargetShiftPalette targetShiftPalettea = new TargetShiftPalette(0, shiftPallet, null);
			targetShiftPalette = Optional.of(targetShiftPalettea);
		}else{
			shiftPalletsCom = listShiftPalletsCom.stream().filter(i -> i.getPage() == param.getPageNumber()).findFirst().get();
			ComPatternScreenDto shiftPallet = new ComPatternScreenDto(shiftPalletsCom.getPage(),
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
			TargetShiftPalette targetShiftPalettea = new TargetShiftPalette(0, shiftPallet, null);
			targetShiftPalette = Optional.of(targetShiftPalettea);
		}
		
		// get List SHiftMasterCode
		List<ShiftPalletCombinations> combinations = shiftPalletsCom.getShiftPallet().getCombinations();
		List<String> listShiftMasterCode = getListShiftMasterCode(combinations, param).stream().collect(Collectors.toList()); 
		
		return new GetShiftPaletteResult(listPageInfo, targetShiftPalette, new ArrayList<>(), listShiftMasterCode);									
	}
	
	public GetShiftPaletteResult getbyWorkPlaceId(GetShiftPaletteParam param) {
		// 0 = work place
		// step 1.2
		List<ShiftPalletsOrg> listShiftPalletsOrg = shiftPalletsOrgRepository.findbyWorkPlaceId(0, param.getWorkplaceId());
		
		List<PageInfo> listPageInfo = new ArrayList<>(); // List<ページ, 名称>
		Optional<TargetShiftPalette> targetShiftPalette = Optional.empty(); // 対象のシフトパレット： Optional<ページ, シフトパレット>
		
		if (listShiftPalletsOrg.isEmpty()) {
			return new GetShiftPaletteResult(listPageInfo, targetShiftPalette, new ArrayList<>(),new ArrayList<>());
		}
		
		for (int i = 0; i < listShiftPalletsOrg.size(); i++) {
			ShiftPalletsOrg shiftPalletsCom = listShiftPalletsOrg.get(i);
			listPageInfo.add(new PageInfo(shiftPalletsCom.getPage(),
					shiftPalletsCom.getShiftPallet().getDisplayInfor().getShiftPalletName().v()));
		}
		
		ShiftPalletsOrg shiftPalletsOrg = null;
		if (param.dataLocalstorageEmpty) {
			shiftPalletsOrg = listShiftPalletsOrg.stream().filter(i -> i.getPage() == 0).findFirst().get();
			ShiftPalletsOrgDto shiftPalletsOrgDto = new ShiftPalletsOrgDto(shiftPalletsOrg, param.getWorkplaceId());
			TargetShiftPalette targetShiftPalettea = new TargetShiftPalette(0, null, shiftPalletsOrgDto);
			targetShiftPalette = Optional.of(targetShiftPalettea);
			
		}else{
			shiftPalletsOrg = listShiftPalletsOrg.stream().filter(i -> i.getPage() == param.getPageNumber()).findFirst().get();
			ShiftPalletsOrgDto shiftPalletsOrgDto = new ShiftPalletsOrgDto(shiftPalletsOrg, param.getWorkplaceId());
			TargetShiftPalette targetShiftPalettea = new TargetShiftPalette(0, null, shiftPalletsOrgDto);
			targetShiftPalette = Optional.of(targetShiftPalettea);
		}
		
		// get List ShiftMasterCode
		List<ShiftPalletCombinations> combinations = shiftPalletsOrg.getShiftPallet().getCombinations();
		List<String> listShiftMasterCode = getListShiftMasterCode(combinations, param).stream().collect(Collectors.toList());
		
		return new GetShiftPaletteResult(listPageInfo, targetShiftPalette, new ArrayList<>(), listShiftMasterCode);	
	}
	
	public Set<String> getListShiftMasterCode(List<ShiftPalletCombinations> shiftPalletCombinations, GetShiftPaletteParam param){
		
		Set<String> listShiftMasterCode = new HashSet<>();  // danh sach ShiftMasterCode cua shiftPallet.
		List<String> listShiftMasterCodeFromUI = param.listShiftMasterCode; // ko cần get mới
		
		if (shiftPalletCombinations.isEmpty()) {
			return new HashSet<>();
		}
		
		for (int i = 0; i < shiftPalletCombinations.size(); i++) {
			List<Combinations> combinations = shiftPalletCombinations.get(i).getCombinations();
			for (int j = 0; j < combinations.size(); j++) {
				String shiftMasterCode = combinations.get(j).getShiftCode().v();
				if (listShiftMasterCodeFromUI.isEmpty()) {
					listShiftMasterCode.add(shiftMasterCode);
				}else{
					if (!listShiftMasterCodeFromUI.contains(shiftMasterCode)) {
						listShiftMasterCode.add(shiftMasterCode);
					}
				}
			}
		}
		return listShiftMasterCode;
	};
	
	@AllArgsConstructor
	private static class RequireImpl implements GetCombinationrAndWorkHolidayAtrService.Require {
		
		private final String companyId = AppContexts.user().companyId();
		
		@Inject
		private ShiftMasterRepository shiftMasterRepo;
		
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
	}
	
	@AllArgsConstructor
	private static class WorkInfoRequireImpl implements WorkInformation.Require {
		
		private final String companyId = AppContexts.user().companyId();
		
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
		public SetupType checkNeededOfWorkTimeSetting(String workTypeCode) {
			 return service.checkNeededOfWorkTimeSetting(workTypeCode);
		}

		@Override
		public Optional<WorkType> findByPK(String workTypeCd) {
			return workTypeRepo.findByPK(companyId, workTypeCd);
		}

		@Override
		public Optional<WorkTimeSetting> findByCode(String workTimeCode) {
			return workTimeSettingRepository.findByCode(companyId, workTimeCode);
		}

		@Override
		public PredetermineTimeSetForCalc getPredeterminedTimezone(String workTimeCd, String workTypeCd, Integer workNo) {
			return workTimeSettingService .getPredeterminedTimezone(companyId, workTimeCd, workTypeCd, workNo);
		}

		@Override
		public WorkStyle checkWorkDay(String workTypeCode) {
			return basicScheduleService.checkWorkDay(workTypeCode);
		}
	}
}
