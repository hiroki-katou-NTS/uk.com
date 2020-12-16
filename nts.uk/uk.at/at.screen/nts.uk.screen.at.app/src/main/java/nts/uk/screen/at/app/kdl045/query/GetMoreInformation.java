package nts.uk.screen.at.app.kdl045.query;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.AllArgsConstructor;
import lombok.val;
import nts.uk.ctx.at.record.dom.require.RecordDomRequireService;
import nts.uk.ctx.at.shared.app.find.worktime.common.dto.WorkTimezoneCommonSetDto;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.BasicScheduleService;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.SetupType;
import nts.uk.ctx.at.shared.dom.workrule.BreakTimeZone;
import nts.uk.ctx.at.shared.dom.worktime.algorithm.getcommonset.GetCommonSet;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneCommonSet;
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
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.internal.PredetermineTimeSetForCalc;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * «ScreenQuery» 詳細情報を取得する
 * @author tutk
 *
 */
@Stateless
public class GetMoreInformation {
	@Inject
	private WorkTimeSettingRepository workTimeSettingRepository;
	
	@Inject 
	private RecordDomRequireService requireService;
	
	@Inject
	private WorkTypeRepository workTypeRepo;

	@Inject
	private WorkTimeSettingService workTimeSettingService;

	@Inject
	private BasicScheduleService basicScheduleService;
	
	@Inject
	private FixedWorkSettingRepository fixedWorkSettingRepository;
	
	@Inject
	private FlowWorkSettingRepository flowWorkSettingRepository;
	
	@Inject
	private FlexWorkSettingRepository flexWorkSettingRepository;
	
	@Inject
	private PredetemineTimeSettingRepository predetemineTimeSettingRepository;
	
	public GetMoreInformationOutput getMoreInformation(String employeeId,String workType,String workTimeCode) {
		
		String companyId = AppContexts.user().companyId();
		GetMoreInformationOutput data =new GetMoreInformationOutput();
		val require = requireService.createRequire();
		WorkInformation.Require requireWorkInfo = new WorkInformationImpl(workTypeRepo, workTimeSettingRepository,
				workTimeSettingService, basicScheduleService,fixedWorkSettingRepository,flowWorkSettingRepository,flexWorkSettingRepository,predetemineTimeSettingRepository);
		
		//1 : 共通設定の取得
		Optional<WorkTimezoneCommonSet> optWorktimezone = GetCommonSet.workTimezoneCommonSet(require, companyId, workTimeCode);
		WorkTimezoneCommonSetDto workTimezoneCommonSetDto = new WorkTimezoneCommonSetDto(); 
		if(!optWorktimezone.isPresent()) {
			data.setWorkTimezoneCommonSet(null);
		}else {
			optWorktimezone.get().saveToMemento(workTimezoneCommonSetDto);
			data.setWorkTimezoneCommonSet(workTimezoneCommonSetDto);
		}
		
		//2 :休憩時間帯を取得する (require: Require): Optional<休憩時間>
		WorkInformation wi = new WorkInformation(workType, workTimeCode);
		Optional<BreakTimeZone> optBreakTimeZone = wi.getBreakTimeZone(requireWorkInfo);
		if(optBreakTimeZone.isPresent()) {
			data.setBreakTime(BreakTimeKdl045Dto.convertToBreakTimeZone(optBreakTimeZone.get()));
		}else {
			data.setBreakTime(null);
		}

		//3:就業時間帯の設定を取得する
		//emptyの運用はあり得ないため - QA : 112354
		Optional<WorkTimeSetting> workTimeSetting = workTimeSettingRepository.findByCode(companyId, workTimeCode);
		data.setWorkTimeForm(workTimeSetting.get().getWorkTimeDivision().getWorkTimeForm().value);
		
		return data;
		
	}
	
	@AllArgsConstructor
	public static class WorkInformationImpl implements WorkInformation.Require {

		private final String companyId = AppContexts.user().companyId();

		@Inject
		private WorkTypeRepository workTypeRepo;

		@Inject
		private WorkTimeSettingRepository workTimeSettingRepository;

		@Inject
		private WorkTimeSettingService workTimeSettingService;

		@Inject
		private BasicScheduleService basicScheduleService;
		
		@Inject
		private FixedWorkSettingRepository fixedWorkSettingRepository;
		
		@Inject
		private FlowWorkSettingRepository flowWorkSettingRepository;
		
		@Inject
		private FlexWorkSettingRepository flexWorkSettingRepository;
		
		@Inject
		private PredetemineTimeSettingRepository predetemineTimeSettingRepository;

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
		public PredetermineTimeSetForCalc getPredeterminedTimezone(String workTimeCd, String workTypeCd,
				Integer workNo) {
			return workTimeSettingService.getPredeterminedTimezone(companyId, workTimeCd, workTypeCd, workNo);
		}

		@Override
		public FixedWorkSetting getWorkSettingForFixedWork(WorkTimeCode code) {
			return fixedWorkSettingRepository.findByKey(companyId, code.v()).get();
		}

		@Override
		public FlowWorkSetting getWorkSettingForFlowWork(WorkTimeCode code) {
			return flowWorkSettingRepository.find(companyId, code.v()).get();
		}

		@Override
		public FlexWorkSetting getWorkSettingForFlexWork(WorkTimeCode code) {
			return flexWorkSettingRepository.find(companyId, code.v()).get();
		}

		@Override
		public PredetemineTimeSetting getPredetermineTimeSetting(WorkTimeCode wktmCd) {
			return predetemineTimeSettingRepository.findByWorkTimeCode(companyId, wktmCd.v()).get();
		}
	}
}
