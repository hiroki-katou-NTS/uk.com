package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.function.algorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.dailyprocess.calc.FactoryManagePerPersonDailySet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.CommonCompanySettingForCalc;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.DailyRecordToAttendanceItemConverter;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.service.AttendanceItemConvertFactory;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.function.algorithm.aftercorrectatt.CorrectionAfterTimeChange;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.function.algorithm.aftercorrectwork.CorrectionAfterChangeWorkInfo;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.function.algorithm.breaktime.BreakTimeSheetCorrector;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.function.algorithm.breaktime.CreateOneDayRangeCalc;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.WorkInfoOfDailyAttendance;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.ManagePerCompanySet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.ManagePerPersonDailySet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.CalculationRangeOfOneDay;
import nts.uk.ctx.at.shared.dom.scherec.optitem.OptionalItem;
import nts.uk.ctx.at.shared.dom.scherec.optitem.OptionalItemRepository;
import nts.uk.ctx.at.shared.dom.worktime.common.JustCorrectionAtr;
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
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * @author ThanhNX
 *
 *         勤怠ルールの補正処理
 */
@Stateless
public class CorrectionAttendanceRule implements ICorrectionAttendanceRule {

	@Inject
	private AttendanceItemConvertFactory attendanceItemConvertFactory;
	
	@Inject
	private OptionalItemRepository optionalItem;
	
	@Inject
	private CorrectionAfterTimeChange correctionAfterTimeChange;

	@Inject
	private CorrectionAfterChangeWorkInfo correctionAfterChangeWorkInfo;

	@Inject
	private FactoryManagePerPersonDailySet personDailySetFactory;
	
	@Inject
	private FixedWorkSettingRepository fixWorkSetRepo;
	
	@Inject
	private PredetemineTimeSettingRepository predetemineTimeSetRepo;
	
	@Inject
	private CreateOneDayRangeCalc createOneDayRangeCalc;
	
	@Inject 
	private WorkTypeRepository workTypeRepo;
	
	@Inject
	private WorkTimeSettingRepository workTimeSettingRepo;
	
	@Inject
	private FlowWorkSettingRepository flowWorkSettingRepo;
	
	@Inject
	private FlexWorkSettingRepository flexWorkSettingRepo;
	
	// 勤怠ルールの補正処理
	@Override
	public IntegrationOfDaily process(IntegrationOfDaily domainDaily, ChangeDailyAttendance changeAtt) {

		String companyId = AppContexts.user().companyId();
		
		val optionalItems = 
				optionalItem.findAll(companyId).stream()
				.collect(Collectors.toMap(c -> c.getOptionalItemNo().v(), c -> c));

		DailyRecordToAttendanceItemConverter converter = attendanceItemConvertFactory.createDailyConverter(optionalItems)
																					.setData(domainDaily).completed();
		List<Integer> atendanceId = converter.editStates().stream().filter(x -> x.isHandCorrect())
				.map(x -> x.getAttendanceItemId()).distinct().collect(Collectors.toList());

		// 補正前の状態を保持
		// IntegrationOfDaily beforeDomain = converter.toDomain();
		List<ItemValue> beforeItems = atendanceId.isEmpty() ? new ArrayList<>() : converter.convert(atendanceId);

		// 勤怠変更後の補正
		/// TODO: 設計中 waiting design map
		IntegrationOfDaily afterDomain = correctionAfterTimeChange.corection(companyId, domainDaily);

		// TODO: 設計中 waiting design map case 出退勤 .....
		if (changeAtt.workInfo) {
			// 変更する勤怠項目を確認
			/// TODO: processing mock new domain
			afterDomain = correctionAfterChangeWorkInfo.correction(companyId, afterDomain);

		}
		
		/** 休憩時間帯の補正 */
		BreakTimeSheetCorrector.correct(createBreakRequire(optionalItems), afterDomain, changeAtt.fixBreakCorrect);

		// 手修正を基に戻す
		DailyRecordToAttendanceItemConverter afterConverter = attendanceItemConvertFactory.createDailyConverter().setData(afterDomain)
				.completed();
		if(!beforeItems.isEmpty()) afterConverter.merge(beforeItems);

		return afterConverter.toDomain();
	}

	private BreakTimeSheetCorrector.RequireM1 createBreakRequire(Map<Integer, OptionalItem> optionalItems) {
		
		return new BreakTimeSheetCorrector.RequireM1() {
			
			@Override
			public Optional<FixedWorkSetting> fixedWorkSetting(String companyId, String workTimeCode) {

				return fixWorkSetRepo.findByKey(companyId, workTimeCode);
			}
			
			@Override
			public Optional<PredetemineTimeSetting> predetemineTimeSetting(String cid, String workTimeCode) {
				
				return predetemineTimeSetRepo.findByWorkTimeCode(cid, workTimeCode);
			}
			
			@Override
			public CalculationRangeOfOneDay createOneDayRange(Optional<PredetemineTimeSetting> predetemineTimeSet,
					IntegrationOfDaily integrationOfDaily, Optional<WorkTimezoneCommonSet> commonSet, WorkType workType,
					JustCorrectionAtr justCorrectionAtr, Optional<WorkTimeCode> workTimeCode) {
				
				return createOneDayRangeCalc.createOneDayRange(predetemineTimeSet, integrationOfDaily, commonSet, workType, justCorrectionAtr, workTimeCode);
			}
			
			@Override
			public Optional<WorkType> workType(String companyId, String workTypeCd) {

				return workTypeRepo.findByDeprecated(companyId, workTypeCd);
			}
			
			@Override
			public Optional<WorkTimeSetting> workTimeSetting(String companyId, String workTimeCode) {

				return workTimeSettingRepo.findByCode(companyId, workTimeCode);
			}
			
			@Override
			public Optional<ManagePerPersonDailySet> managePerPersonDailySet(String sid, GeneralDate ymd, IntegrationOfDaily dailyRecord) {
				
				return personDailySetFactory.create(AppContexts.user().companyId(), sid, ymd, dailyRecord);
			}
			
			@Override
			public Optional<FlowWorkSetting> flowWorkSetting(String companyId, String workTimeCode) {

				return flowWorkSettingRepo.find(companyId, workTimeCode);
			}
			
			@Override
			public Optional<FlexWorkSetting> flexWorkSetting(String companyId, String workTimeCode) {

				return flexWorkSettingRepo.find(companyId, workTimeCode);
			}
			
			@Override
			public DailyRecordToAttendanceItemConverter createDailyConverter() {
				
				return attendanceItemConvertFactory.createDailyConverter(optionalItems);
			}
		};
	}
}