/**
 *
 */
package nts.uk.screen.at.app.ksu001.changepage;

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
import nts.uk.ctx.at.schedule.dom.shift.management.shiftPalette.Combinations;
import nts.uk.ctx.at.schedule.dom.shift.management.shiftPalette.ShiftPaletteCom;
import nts.uk.ctx.at.schedule.dom.shift.management.shiftPalette.ShiftPaletteComRepository;
import nts.uk.ctx.at.schedule.dom.shift.management.shiftPalette.ShiftPaletteCombinations;
import nts.uk.ctx.at.schedule.dom.shift.management.shiftPalette.ShiftPaletteOrg;
import nts.uk.ctx.at.schedule.dom.shift.management.shiftPalette.ShiftPaletteOrgRepository;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.BasicScheduleService;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.SetupType;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.WorkStyle;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.GetCombinationrAndWorkHolidayAtrService;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.ShiftMaster;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.ShiftMasterRepository;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkSettingRepository;
import nts.uk.ctx.at.shared.dom.worktime.flexset.FlexWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.flexset.FlexWorkSettingRepository;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkSettingRepository;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSettingRepository;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingRepository;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingService;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
import nts.uk.screen.at.app.ksu001.displayinshift.ShiftMasterMapWithWorkStyle;
import nts.uk.screen.at.app.ksu001.getshiftpalette.PageInfo;
import nts.uk.screen.at.app.ksu001.getshiftpalette.ShiftPalletUnit;
import nts.uk.screen.at.app.ksu001.getshiftpalette.TargetShiftPalette;
import nts.uk.shr.com.context.AppContexts;

/**
 * @author laitv
 *
 */
@Stateless
public class GetDataWhenChangePage {

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


	@Inject
	private ShiftPaletteComRepository shiftPalletsComRepository;
	@Inject
	private ShiftPaletteOrgRepository shiftPalletsOrgRepository;
	
	@Inject
	private FixedWorkSettingRepository fixedWorkSet; 
	@Inject
	private FlowWorkSettingRepository flowWorkSet;
	@Inject
	private FlexWorkSettingRepository flexWorkSet;
	@Inject
	private PredetemineTimeSettingRepository predetemineTimeSet;

	public GetShiftPalChangePageResult gatData(ChangePageParam param) {

		GetShiftPalChangePageResult result = new GetShiftPalChangePageResult();
		if (param.shiftPalletUnit == ShiftPalletUnit.COMPANY.value) {

			result = getShiftPalletCom(param);

		} else if (param.shiftPalletUnit == ShiftPalletUnit.WORKPLACE.value) {

			result = getShiftPalletWkp(param);
		}

		List<String> listShiftMasterCodeOfPageSelectd     = result.listShiftMasterCodeOfPageSelectd;

		List<ShiftMasterMapWithWorkStyle> listShiftMaster = param.listShiftMasterNotNeedGetNew;

		GetCombinationrAndWorkHolidayAtrService.Require require = new RequireImpl(shiftMasterRepo, basicScheduleService, workTypeRepo,workTimeSettingRepository,workTimeSettingService, fixedWorkSet , flowWorkSet, flexWorkSet, predetemineTimeSet);

		Map<ShiftMaster,Optional<WorkStyle>> sMap = GetCombinationrAndWorkHolidayAtrService.getCode(require,AppContexts.user().companyId(), listShiftMasterCodeOfPageSelectd);
		List<String> listShiftMasterCodeFromUI = param.listShiftMasterNotNeedGetNew.stream().map(mapper -> mapper.getShiftMasterCode()).collect(Collectors.toList()); // ko cần get mới

		for (Map.Entry<ShiftMaster, Optional<WorkStyle>> entry : sMap.entrySet()) {
			String shiftMasterCd = entry.getKey().getShiftMasterCode().toString();
			if(listShiftMasterCodeFromUI.contains(shiftMasterCd)){
				// remove di, roi add lai
				ShiftMasterMapWithWorkStyle obj = listShiftMaster.stream().filter(shiftLocal -> shiftLocal.shiftMasterCode.equals(shiftMasterCd)).findFirst().get();
				listShiftMaster.remove(obj);
			}
			ShiftMasterMapWithWorkStyle shift = new ShiftMasterMapWithWorkStyle(entry.getKey(), entry.getValue().isPresent() ? entry.getValue().get().value + "" : null);
			listShiftMaster.add(shift);
		}
		result.setListShiftMaster(listShiftMaster);
		return result;
	}

	public GetShiftPalChangePageResult getShiftPalletCom(ChangePageParam param) {

		//ShiftPalletsCom shiftPalletsCom = getShiftPalettebyComAndSpePage.getShiftPalletCom(param.pageNumberCom); dung phai goi ham nay, nhung ham nay dang tra ra data sai

		// cho a Hieu fix ham tren roi dung lai
		List<ShiftPaletteCom> listShiftPalletsCom = shiftPalletsComRepository.findShiftPalletUse(AppContexts.user().companyId());
		ShiftPaletteCom shiftPalletsCom = listShiftPalletsCom.stream().filter(item -> item.getPage() == param.pageNumberCom).findFirst().get();
		//

		PageInfo pageInfo = null;
		TargetShiftPalette targetShiftPalette = null;

		if (shiftPalletsCom == null) {
			return new GetShiftPalChangePageResult(pageInfo, targetShiftPalette, new ArrayList<>(), new ArrayList<>());
		}

		List<ComPatternScreenDto> listShiftPalletComDto = new ArrayList<>();

		pageInfo = new PageInfo(shiftPalletsCom.getPage(),shiftPalletsCom.getShiftPallet().getDisplayInfor().getShiftPalletName().v());

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
		targetShiftPalette = new TargetShiftPalette(param.pageNumberCom, listShiftPalletComDto, null);

		// get List SHiftMasterCode
		List<ShiftPaletteCombinations> combinations = shiftPalletsCom.getShiftPallet().getCombinations();
		List<String> listShiftMasterCodeOfPageSelectd = getListShiftMasterCode(combinations).stream().collect(Collectors.toList());

		return new GetShiftPalChangePageResult(pageInfo, targetShiftPalette, new ArrayList<>(), listShiftMasterCodeOfPageSelectd);
	}

	public GetShiftPalChangePageResult getShiftPalletWkp(ChangePageParam param) {

		//ShiftPalletsOrg shiftPalletsOrg = getShiftPalettebyOrgAndSpePage.getShiftPalletOrg(param.pageNumberOrg, param.workplaceId);
		List<ShiftPaletteOrg> listShiftPalletsOrg = new ArrayList<>();
		if (param.unit == 0) {
			listShiftPalletsOrg = shiftPalletsOrgRepository.findbyWorkPlaceIdUse(0, param.workplaceId );
		}else{
			listShiftPalletsOrg = shiftPalletsOrgRepository.findbyWorkPlaceIdUse(1, param.workplaceGroupId );
		}

		Optional<ShiftPaletteOrg> shiftPalletsOrg = listShiftPalletsOrg.stream().filter(item -> item.getPage() == param.pageNumberCom).findFirst();


		PageInfo pageInfo = null;
		TargetShiftPalette targetShiftPalette = null;

		if (!shiftPalletsOrg.isPresent()) {
			return new GetShiftPalChangePageResult(pageInfo, targetShiftPalette, new ArrayList<>(), new ArrayList<>());
		}

		List<ShiftPalletsOrgDto> listShiftPalletOrgDto = new ArrayList<>();
		pageInfo = new PageInfo(shiftPalletsOrg.get().getPage(), shiftPalletsOrg.get().getShiftPallet().getDisplayInfor().getShiftPalletName().v());
		ShiftPalletsOrgDto shiftPalletsOrgDto = new ShiftPalletsOrgDto(shiftPalletsOrg.get(), param.getWorkplaceId());
		listShiftPalletOrgDto.add(shiftPalletsOrgDto);


		targetShiftPalette = new TargetShiftPalette(param.pageNumberOrg, null, listShiftPalletOrgDto);

		// get List ShiftMasterCode
		List<ShiftPaletteCombinations> combinations = shiftPalletsOrg.get().getShiftPallet().getCombinations();
		List<String> listShiftMasterCodeOfPageSelectd = getListShiftMasterCode(combinations).stream().collect(Collectors.toList());

		return new GetShiftPalChangePageResult(pageInfo, targetShiftPalette, new ArrayList<>(), listShiftMasterCodeOfPageSelectd);
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
		private FixedWorkSettingRepository fixedWorkSet;
		@Inject
		private FlowWorkSettingRepository flowWorkSet;
		@Inject
		private FlexWorkSettingRepository flexWorkSet;
		@Inject
		private PredetemineTimeSettingRepository predetemineTimeSet;

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

//		@Override
//		public PredetermineTimeSetForCalc getPredeterminedTimezone(String workTypeCd, String workTimeCd, Integer workNo) {
//			return workTimeSettingService .getPredeterminedTimezone(companyId, workTimeCd, workTypeCd, workNo);
//		}

		@Override
		public FixedWorkSetting getWorkSettingForFixedWork(WorkTimeCode code) {
			Optional<FixedWorkSetting> workSetting = fixedWorkSet.findByKey(companyId, code.v());
			return workSetting.isPresent() ? workSetting.get() : null;
		}
		@Override
		public FlowWorkSetting getWorkSettingForFlowWork(WorkTimeCode code) {
			Optional<FlowWorkSetting> workSetting = flowWorkSet.find(companyId, code.v());
			return workSetting.isPresent() ? workSetting.get() : null;
		}
		@Override
		public FlexWorkSetting getWorkSettingForFlexWork(WorkTimeCode code) {
			Optional<FlexWorkSetting> workSetting = flexWorkSet.find(companyId, code.v());
			return workSetting.isPresent() ? workSetting.get() : null;
		}
		@Override
		public PredetemineTimeSetting getPredetermineTimeSetting(WorkTimeCode wktmCd) {
			Optional<PredetemineTimeSetting> workSetting = predetemineTimeSet.findByWorkTimeCode(companyId, wktmCd.v());
			return workSetting.isPresent() ? workSetting.get() : null;
		}
	}
}
