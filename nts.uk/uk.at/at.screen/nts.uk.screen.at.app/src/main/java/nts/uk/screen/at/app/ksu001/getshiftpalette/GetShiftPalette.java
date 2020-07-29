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
import nts.uk.screen.at.app.ksu001.displayinshift.PageShift;
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
		if (param.shiftPaletteWantGet.shiftPalletUnit == ShiftPalletUnit.COMPANY.value) {
			
			result = getShiftPalletCom(param);
			
		}else if (param.shiftPaletteWantGet.shiftPalletUnit == ShiftPalletUnit.WORKPLACE.value) {
			
			result = getbyWorkPlaceId(param);
		}
		
		// Step5
		List<String> listShiftMasterCodeGetNew = result.listShiftMasterCodeGetNew;
		List<PageShift> listOfShift = new ArrayList<>();
		
		GetCombinationrAndWorkHolidayAtrService.Require require = new RequireImpl(shiftMasterRepo, basicScheduleService, workTypeRepo,workTimeSettingRepository,workTimeSettingService, basicScheduleService);
		
		// listShiftMasterCode này chỉ bao gồm những Code chưa được lưu ở localStorage.
		Map<ShiftMaster,Optional<WorkStyle>> sMap = GetCombinationrAndWorkHolidayAtrService.getCode(require,AppContexts.user().companyId(), listShiftMasterCodeGetNew);
		for (Map.Entry<ShiftMaster, Optional<WorkStyle>> entry : sMap.entrySet()) {
			System.out.println("ShiftMaster : " + entry.getKey() + " WorkStyle : " + entry.getValue());
			PageShift shift = new PageShift(new ShiftMasterDto(entry.getKey()), entry.getValue().isPresent() ? entry.getValue().get().value : null);
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
		
		ShiftPalletsCom shiftPalletsCom = listShiftPalletsCom.stream().filter(i -> i.getPage() == param.shiftPaletteWantGet.getPageNumber()).findFirst().get();
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
		TargetShiftPalette targetShiftPalettea = new TargetShiftPalette(param.shiftPaletteWantGet.getPageNumber(), shiftPallet, null);
		targetShiftPalette = Optional.of(targetShiftPalettea);
		
		// get List SHiftMasterCode
		List<ShiftPalletCombinations> combinations = shiftPalletsCom.getShiftPallet().getCombinations();
		List<String> listShiftMasterCodeGetNew = getListShiftMasterCode(combinations, param).stream().collect(Collectors.toList()); 
		
		return new GetShiftPaletteResult(listPageInfo, targetShiftPalette, new ArrayList<>(), listShiftMasterCodeGetNew);									
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
		
		ShiftPalletsOrg shiftPalletsOrg = listShiftPalletsOrg.stream().filter(i -> i.getPage() == param.shiftPaletteWantGet.getPageNumber()).findFirst().get();
		ShiftPalletsOrgDto shiftPalletsOrgDto = new ShiftPalletsOrgDto(shiftPalletsOrg, param.getWorkplaceId());
		TargetShiftPalette targetShiftPalettea = new TargetShiftPalette(param.shiftPaletteWantGet.getPageNumber(), null, shiftPalletsOrgDto);
		targetShiftPalette = Optional.of(targetShiftPalettea);
		
		// get List ShiftMasterCode
		List<ShiftPalletCombinations> combinations = shiftPalletsOrg.getShiftPallet().getCombinations();
		List<String> listShiftMasterCodeGetNew = getListShiftMasterCode(combinations, param).stream().collect(Collectors.toList());
		
		return new GetShiftPaletteResult(listPageInfo, targetShiftPalette, new ArrayList<>(), listShiftMasterCodeGetNew);	
	}
	
	public Set<String> getListShiftMasterCode(List<ShiftPalletCombinations> shiftPalletCombinations, GetShiftPaletteParam param){
		
		Set<String>  listShiftMasterCodeGetNew = new HashSet<>();  // danh sach nay chỉ bao gôm những shiftMasterCode mới lấy.
		List<String> listShiftMasterCodeFromUI = param.listShiftMasterNotNeedGetNew.stream().map(mapper -> mapper.getShiftMasterCode()).collect(Collectors.toList()); // ko cần get mới
		
		if (shiftPalletCombinations.isEmpty()) {
			return new HashSet<>();
		}
		
		for (int i = 0; i < shiftPalletCombinations.size(); i++) {
			List<Combinations> combinations = shiftPalletCombinations.get(i).getCombinations();
			for (int j = 0; j < combinations.size(); j++) {
				String shiftMasterCode = combinations.get(j).getShiftCode().v();
				if (listShiftMasterCodeFromUI.isEmpty()) {
					listShiftMasterCodeGetNew.add(shiftMasterCode);
				}else{
					if (!listShiftMasterCodeFromUI.contains(shiftMasterCode)) {
						listShiftMasterCodeGetNew.add(shiftMasterCode);
					}
				}
			}
		}
		return listShiftMasterCodeGetNew;
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
