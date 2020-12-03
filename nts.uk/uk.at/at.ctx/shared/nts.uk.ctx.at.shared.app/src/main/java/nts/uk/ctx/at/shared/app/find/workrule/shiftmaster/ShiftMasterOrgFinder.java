package nts.uk.ctx.at.shared.app.find.workrule.shiftmaster;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import lombok.AllArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.shared.app.find.worktime.dto.WorkTimeDto;
import nts.uk.ctx.at.shared.app.find.worktime.worktimeset.WorkTimeSettingFinder;
import nts.uk.ctx.at.shared.dom.WorkInfoAndTimeZone;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.BasicScheduleService;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.SetupType;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrganizationUnit;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.GetShiftMasterByWorkplaceService;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.GetShiftMasterByWorkplaceService.Require;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.GetUsableShiftMasterService;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.ShiftMasterOrgRepository;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.ShiftMasterOrganization;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.ShiftMasterRepository;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.dto.ShiftMasterDto;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.flexset.FlexWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingRepository;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingService;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.internal.PredetermineTimeSetForCalc;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 *
 *
 * @author anhdt
 *
 */
@Stateless
public class ShiftMasterOrgFinder {

	@Inject
	private ShiftMasterOrgRepository shiftMasterOrgRp;

	@Inject
	private ShiftMasterRepository shiftMasterRepo;

	@Inject
	private WorkTimeSettingFinder workTimeFinder;

	@Inject
	private WorkTypeRepository workTypeRepo;

	@Inject
	private WorkTimeSettingRepository workTimeSettingRepository;

	@Inject
	private WorkTimeSettingService workTimeSettingService;

	@Inject
	private BasicScheduleService basicScheduleService;

	// 使用できるシフトマスタの勤務情報と補正済み所定時間帯を取得する
	@SuppressWarnings("static-access")
	public List<ShiftMasterDto> optainShiftMastersByWorkPlace(String targetId, Integer targetUnit) {

		GetUsableShiftMasterService.Require require = new UseableRequireImpl(shiftMasterOrgRp, shiftMasterRepo);

		TargetOrgIdenInfor target = null;
		if(targetUnit != null && targetId != null) {
			TargetOrganizationUnit unit = EnumAdaptor.valueOf(targetUnit, TargetOrganizationUnit.class);
			target = new TargetOrgIdenInfor(unit, Optional.ofNullable(targetId), Optional.ofNullable(targetId));
		}
		List<ShiftMasterDto> shiftMasters = new ArrayList<>();
		if(target == null) {
			shiftMasters =  require.getAllByCid();
		} else {
			shiftMasters = GetUsableShiftMasterService.getUsableShiftMaster(require, target);
		}

		if(CollectionUtil.isEmpty(shiftMasters)) {
			return Collections.emptyList();
		}

		List<String> workTimeCodes = shiftMasters.stream().map(s -> s.getWorkTimeCd()).collect(Collectors.toList());

		List<WorkTimeDto> workTimeInfos = workTimeFinder.findByCodes(workTimeCodes);

		shiftMasters.forEach(shiftMaster -> {
			shiftMaster.setWorkTime1(shiftMaster.getWorkTime1().replace('~', '～'));
			shiftMaster.setWorkTime2(shiftMaster.getWorkTime2().replace('~', '～'));
			if(!StringUtils.isEmpty(shiftMaster.getWorkTimeCd())) {
				Optional<WorkTimeDto> oWorkTime = workTimeInfos.stream().filter(wkt -> shiftMaster.getWorkTimeCd().equalsIgnoreCase(wkt.code)).findFirst();

				if(oWorkTime.isPresent()) {
					WorkTimeDto worktime = oWorkTime.get();
					shiftMaster.setWorkTime1(!StringUtils.isEmpty(worktime.workTime1) ? worktime.workTime1.replace('~', '～') : "");
					shiftMaster.setWorkTime2(!StringUtils.isEmpty(worktime.workTime2) ? worktime.workTime2.replace('~', '～') : "");
				}
			}
		});

		return shiftMasters;

	}

	// 対象のシフト情報を習得
	public List<ShiftMasterDto> getShiftMastersByWorkPlace(String targetId, Integer targetUnit) {
		Require require = new  RequireImpl(shiftMasterOrgRp, shiftMasterRepo);

		TargetOrgIdenInfor target = null;
		if(targetUnit != null && targetId != null) {
			TargetOrganizationUnit unit = EnumAdaptor.valueOf(targetUnit, TargetOrganizationUnit.class);
			target = new TargetOrgIdenInfor(unit, Optional.ofNullable(targetId), Optional.ofNullable(targetId));
		}

		if(target == null) {
			return Collections.emptyList();
		}

		@SuppressWarnings("static-access")
		List<ShiftMasterDto> shiftMasters = GetShiftMasterByWorkplaceService.getShiftMasterByWorkplaceService(require, target);


		if(CollectionUtil.isEmpty(shiftMasters)) {
			return Collections.emptyList();
		}

		List<String> workTimeCodes = shiftMasters.stream().map(s -> s.getWorkTimeCd()).collect(Collectors.toList());

		List<WorkTimeDto> workTimeInfos = workTimeFinder.findByCodes(workTimeCodes);

		shiftMasters.forEach(shiftMaster -> {
			if(!StringUtils.isEmpty(shiftMaster.getWorkTimeCd())) {
				Optional<WorkTimeDto> oWorkTime = workTimeInfos.stream().filter(wkt -> shiftMaster.getWorkTimeCd().equalsIgnoreCase(wkt.code)).findFirst();

				if(oWorkTime.isPresent()) {
					WorkTimeDto worktime = oWorkTime.get();
					shiftMaster.setWorkTime1(!StringUtils.isEmpty(worktime.workTime1) ? worktime.workTime1.replace('~', '～') : "");
					shiftMaster.setWorkTime2(!StringUtils.isEmpty(worktime.workTime2) ? worktime.workTime2.replace('~', '～') : "");
				}
			}
		});

		return shiftMasters;

	}

	public AlreadySettingWorkplaceDto getAlreadySetting(int unit) {
		AlreadySettingWorkplaceDto result = new AlreadySettingWorkplaceDto();
		result.setWorkplaceIds(shiftMasterOrgRp.getAlreadySettingWorkplace(AppContexts.user().companyId(), unit)
				.stream().map(d -> d.getTargetOrg().getWorkplaceId().get()).distinct().collect(Collectors.toList()));
		return result;
	}

	public AlreadySettingWorkplaceDto getAlreadySettingWplGr(int unit) {
		AlreadySettingWorkplaceDto result = new AlreadySettingWorkplaceDto();
		result.setWorkplaceGrpIds(shiftMasterOrgRp.getAlreadySettingWorkplaceGrp(AppContexts.user().companyId(), unit)
					.stream().map(d -> d.getTargetOrg().getWorkplaceGroupId().get()).distinct().collect(Collectors.toList()));

		return result;
	}

	// 職場グループのシフト情報を取得する
	public OutPutShiftMasterDto getWorkgroupShiftInfo(String targetId) {
		OutPutShiftMasterDto shiftMasterDto = new OutPutShiftMasterDto();
		String companyId = AppContexts.user().companyId();
		GetShiftMasterByWorkplaceService.Require require = new RequireImpl(shiftMasterOrgRp, shiftMasterRepo);
		TargetOrgIdenInfor targetOrg = TargetOrgIdenInfor.creatIdentifiWorkplaceGroup(targetId);
		Optional<ShiftMasterOrganization> shiftMasterOrg = require.getByTargetOrg(companyId, targetOrg);

		if(!shiftMasterOrg.isPresent()) {
			shiftMasterDto =  new OutPutShiftMasterDto(new ArrayList<>(), null);
		}
		List<ShiftMasterDto> shiftMasterDtos = require.getByListShiftMaterCd(companyId, shiftMasterOrg.get().getListShiftMaterCode());
		List<ShiftMastersDto> shiftMaster = shiftMasterDtos.stream().map(x->
		new ShiftMastersDto(x.getCompanyId(), x.getShiftMasterCode(), x.getShiftMasterName(), x.getColor(), x.getRemark())).collect(Collectors.toList());
		WorkInformation information = new WorkInformation("", "");

		WorkInformation.Require require2 = new WorkInfoImpl(workTypeRepo,workTimeSettingRepository,workTimeSettingService, basicScheduleService);
		Optional<WorkInfoAndTimeZone> timeZone = information.getWorkInfoAndTimeZone(require2);
		Optional<WorkInfoTimeZoneTempo> tempo = timeZone.map(x-> WorkInfoTimeZoneTempo.toDto(x));
		shiftMasterDto =  new OutPutShiftMasterDto(shiftMaster, tempo.isPresent() ? tempo.get() : null);
		return shiftMasterDto;
	}


	@AllArgsConstructor
	private static class RequireImpl implements GetShiftMasterByWorkplaceService.Require {

		@Inject
		private ShiftMasterOrgRepository shiftMasterOrgRp;

		@Inject
		private ShiftMasterRepository shiftMasterRepo;

		@Override
		public Optional<ShiftMasterOrganization> getByTargetOrg(String companyId, TargetOrgIdenInfor targetOrg) {
			return shiftMasterOrgRp.getByTargetOrg(companyId, targetOrg);
		}

		@Override
		public List<ShiftMasterDto> getAllByCid(String companyId) {
			return shiftMasterRepo.getAllByCid(companyId);
		}

		@Override
		public List<ShiftMasterDto> getByListShiftMaterCd(String companyId, List<String> listShiftMaterCode) {
			return shiftMasterRepo.getByListShiftMaterCd(companyId, listShiftMaterCode);
		}

	}

	@AllArgsConstructor
	private static class UseableRequireImpl implements GetUsableShiftMasterService.Require {

		private final String companyId = AppContexts.user().companyId();
		@Inject
		private ShiftMasterOrgRepository shiftMasterOrgRp;

		@Inject
		private ShiftMasterRepository shiftMasterRepo;

		@Override
		public Optional<ShiftMasterOrganization> getByTargetOrg(TargetOrgIdenInfor targetOrg) {
			return shiftMasterOrgRp.getByTargetOrg(companyId, targetOrg);
		}

		@Override
		public List<ShiftMasterDto> getAllByCid() {
			return shiftMasterRepo.getAllByCid(companyId);
		}

		@Override
		public List<ShiftMasterDto> getByListShiftMaterCd(List<String> listShiftMaterCode) {
			return shiftMasterRepo.getByListShiftMaterCd(companyId, listShiftMaterCode);
		}

	}

	@AllArgsConstructor
	private static class WorkInfoImpl implements WorkInformation.Require {

		private final String companyId = AppContexts.user().companyId();

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
			 return basicScheduleService.checkNeededOfWorkTimeSetting(workTypeCode);
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
		public FixedWorkSetting getWorkSettingForFixedWork(WorkTimeCode code) {
			// TODO 自動生成されたメソッド・スタブ
			return null;
		}

		@Override
		public FlowWorkSetting getWorkSettingForFlowWork(WorkTimeCode code) {
			// TODO 自動生成されたメソッド・スタブ
			return null;
		}

		@Override
		public FlexWorkSetting getWorkSettingForFlexWork(WorkTimeCode code) {
			// TODO 自動生成されたメソッド・スタブ
			return null;
		}

		@Override
		public PredetemineTimeSetting getPredetermineTimeSetting(WorkTimeCode wktmCd) {
			// TODO 自動生成されたメソッド・スタブ
			return null;
		}

	}

}
