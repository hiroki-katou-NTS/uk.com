package nts.uk.ctx.at.record.app.find.stamp.management.personalengraving;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.AllArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.app.find.stamp.management.personalengraving.dto.DailyAttdErrorInfoDto;
import nts.uk.ctx.at.record.dom.stamp.application.StampPromptAppRepository;
import nts.uk.ctx.at.record.dom.stamp.application.StampPromptApplication;
import nts.uk.ctx.at.record.dom.stamp.management.StampSetPerRepository;
import nts.uk.ctx.at.record.dom.stamp.management.StampSettingPerson;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.EmployeeDailyPerError;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.EmployeeDailyPerErrorRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.ErAlApplication;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.ErAlApplicationRepository;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice.CheckAttdErrorAfterStampService;
import nts.uk.ctx.at.shared.dom.workrule.closure.Closure;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosurePeriod;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.ClosureService;
import nts.uk.shr.com.context.AppContexts;

/**
 * @author anhdt 打刻漏れの内容を取得する
 */
@Stateless
public class GetOmissionContentsFinder {

	@Inject
	private StampPromptAppRepository stamPromptAppRepo;

	@Inject
	private ClosureService closureService;

	@Inject
	private ErAlApplicationRepository erAlApplicationRepo;

	@Inject
	private EmployeeDailyPerErrorRepository employeeDailyPerErrorRepo;

	@Inject
	private StampSetPerRepository stampSetPerRepo;

	public DailyAttdErrorInfoDto getOmissionContents(int pageNo, int buttonDisNo) {
		String employeeId = AppContexts.user().employeeId();
		CheckAttdErrorAfterStampRequiredImpl required = new CheckAttdErrorAfterStampRequiredImpl(stamPromptAppRepo,
				closureService, erAlApplicationRepo, employeeDailyPerErrorRepo, stampSetPerRepo );
		return new DailyAttdErrorInfoDto(CheckAttdErrorAfterStampService.get(required, employeeId, pageNo, buttonDisNo));
	}

	@AllArgsConstructor
	private class CheckAttdErrorAfterStampRequiredImpl implements CheckAttdErrorAfterStampService.Require {

		@Inject
		private StampPromptAppRepository stamPromptAppRepo;

		@Inject
		private ClosureService closureService;

		@Inject
		private ErAlApplicationRepository erAlApplicationRepo;

		@Inject
		private EmployeeDailyPerErrorRepository employeeDailyPerErrorRepo;

		@Inject
		private StampSetPerRepository stampSetPerRepo;

		@Override
		public Optional<StampPromptApplication> getStampSet() {
			return stamPromptAppRepo.getStampPromptApp(AppContexts.user().companyId());
		}

		@Override
		public DatePeriod findClosurePeriod(String employeeId, GeneralDate baseDate) {
			return closureService.findClosurePeriod(employeeId, baseDate);
		}

		@Override
		public Optional<ClosurePeriod> getClosurePeriod(String employeeId, GeneralDate baseDate) {
			Closure closure = closureService.getClosureDataByEmployee(employeeId, baseDate);
			if (closure == null)
				return Optional.empty();
			Optional<ClosurePeriod> closurePeriodOpt = closure.getClosurePeriodByYmd(baseDate);
			return closurePeriodOpt;
		}

		@Override
		public Optional<ErAlApplication> getAllErAlAppByEralCode(String errorAlarmCode) {
			return erAlApplicationRepo.getAllErAlAppByEralCode(AppContexts.user().companyId(), errorAlarmCode);
		}

		@Override
		public List<EmployeeDailyPerError> findByPeriodOrderByYmd(String employeeId, DatePeriod datePeriod) {
			return employeeDailyPerErrorRepo.findByPeriodOrderByYmd(employeeId, datePeriod);
		}

		@Override
		public Optional<StampSettingPerson> getStampSetPer() {
			return stampSetPerRepo.getStampSetting(AppContexts.user().companyId());
		}

	}

}
