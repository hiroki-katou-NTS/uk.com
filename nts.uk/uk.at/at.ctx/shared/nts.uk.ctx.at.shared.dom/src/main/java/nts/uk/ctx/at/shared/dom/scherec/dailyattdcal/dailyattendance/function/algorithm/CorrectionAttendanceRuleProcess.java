package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.function.algorithm;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.scherec.attendanceitem.converter.service.AttendanceItemConvertFactory;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.CommonCompanySettingForCalc;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.function.algorithm.aftercorrectatt.CorrectionAfterTimeChange;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.function.algorithm.aftercorrectwork.CorrectionAfterChangeWorkInfo;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.function.algorithm.aftercorrectwork.CorrectionShortWorkingHour;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.function.algorithm.breaktime.CreateOneDayRangeCalc;
import nts.uk.ctx.at.shared.dom.scherec.dailyprocess.calc.FactoryManagePerPersonDailySet;
import nts.uk.ctx.at.shared.dom.scherec.optitem.OptionalItemRepository;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItemRepository;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionRepository;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkSettingRepository;
import nts.uk.ctx.at.shared.dom.worktime.flexset.FlexWorkSettingRepository;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkSettingRepository;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSettingRepository;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingRepository;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;

@Stateless
public class CorrectionAttendanceRuleProcess implements ICorrectionAttendanceRule{

	@Inject
	private AttendanceItemConvertFactory attendanceItemConvertFactory;

	@Inject
	private OptionalItemRepository optionalItem;

	@Inject
	private CorrectionAfterTimeChange correctionAfterTimeChange;

	@Inject
	private CorrectionAfterChangeWorkInfo correctionAfterChangeWorkInfo;

	@Inject
	private CommonCompanySettingForCalc companyCommonSettingRepo;

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

	@Inject
	private ICorrectSupportDataWork iCorrectSupportDataWork;

	@Inject
	private WorkingConditionItemRepository workingConditionItemRepo;

	@Inject
	private WorkingConditionRepository workingConditionRepo;

	@Inject
	private CorrectionShortWorkingHour correctShortWorkingHour;
	
	
	@Override
	public IntegrationOfDaily process(IntegrationOfDaily domainDaily, ChangeDailyAttendance changeAtt) {
		CorrectionAttendanceRuleRequireImpl impl = new CorrectionAttendanceRuleRequireImpl(attendanceItemConvertFactory,
				optionalItem, correctionAfterTimeChange, correctionAfterChangeWorkInfo, companyCommonSettingRepo,
				personDailySetFactory, fixWorkSetRepo, predetemineTimeSetRepo, createOneDayRangeCalc, workTypeRepo,
				workTimeSettingRepo, flowWorkSettingRepo, flexWorkSettingRepo, iCorrectSupportDataWork,
				workingConditionItemRepo, workingConditionRepo, correctShortWorkingHour);
		return CorrectionAttendanceRule.process(impl, domainDaily, changeAtt);
	}

}
