package nts.uk.screen.at.app.query.kdp.kdp002.a;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.stamp.application.StampPromptAppRepository;
import nts.uk.ctx.at.record.dom.stamp.application.StampPromptApplication;
import nts.uk.ctx.at.record.dom.stamp.card.management.personalengraving.AppDispNameExp;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.EmployeeDailyPerError;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.EmployeeDailyPerErrorRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.ErAlApplication;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.ErAlApplicationRepository;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice.CheckAttdErrorAfterStampService;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice.DailyAttdErrorInfo;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.ButtonPositionNo;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.PageNo;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.StampButton;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.StampSetPerRepository;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.StampSettingPerson;
import nts.uk.ctx.at.shared.dom.adapter.employment.BsEmploymentHistoryImport;
import nts.uk.ctx.at.shared.dom.adapter.employment.ShareEmploymentAdapter;
import nts.uk.ctx.at.shared.dom.workrule.closure.Closure;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmployment;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmploymentRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosurePeriod;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.ClosureService;
import nts.uk.ctx.sys.portal.pub.standardmenu.StandardMenuPub;
import nts.uk.screen.at.app.dailyperformance.correction.dto.ApplicationType;
import nts.uk.shr.com.context.AppContexts;

/**
 * @author anhdt 打刻漏れの内容を取得する
 */
@Stateless
public class GetOmissionContentsFinder {

	@Inject
	private StampPromptAppRepository stamPromptAppRepo;

	@Inject
	private ErAlApplicationRepository erAlApplicationRepo;

	@Inject
	private EmployeeDailyPerErrorRepository employeeDailyPerErrorRepo;

	@Inject
	private StampSetPerRepository stampSetPerRepo;
	
	@Inject
	private StandardMenuPub menuPub;
	
	@Inject
	private ClosureRepository closureRepository;
	
	@Inject
	private ClosureEmploymentRepository closureEmploymentRepository;
	
	@Inject
	private ShareEmploymentAdapter shrEmpAdapter;

	public DailyAttdErrorInfoDto getOmissionContents(int pageNo, int buttonDisNo) {
		String employeeId = AppContexts.user().employeeId();
		CheckAttdErrorAfterStampRequiredImpl required = new CheckAttdErrorAfterStampRequiredImpl();
		StampButton stampButton = new StampButton(new PageNo(pageNo), new ButtonPositionNo(buttonDisNo));

		List<DailyAttdErrorInfo> errorInfo = CheckAttdErrorAfterStampService.get(required, employeeId, stampButton);

		// アルゴリズム「メニューの表示名を取得する」を実行する

		

		List<AppDispNameExp> appDispNames = new ArrayList<AppDispNameExp>();
		
		if (errorInfo.size() > 0) {
			List<ApplicationType> appTypes = errorInfo.get(0).getListRequired().stream().sorted()
					.map(x -> EnumAdaptor.valueOf(x, ApplicationType.class)).collect(Collectors.toList());

			String companyId = AppContexts.user().companyId();
			appTypes.forEach(x -> {

				List<AppDispNameExp> appNames = this.menuPub
						.getMenuDisplayName(companyId, Arrays.asList(x.toStandardMenuNameQuery())).stream()
						.map(item -> {
							String screen = item.getProgramId().substring(0, 3).toLowerCase();
							String screenCd = item.getProgramId().substring(3, 6).toLowerCase();
							String url = "/view/" + screen + "/" + screenCd + "/" + item.getScreenId().toLowerCase()
									+ "/index.xhtml"
									+ (item.getQueryString() != null ? "?" + item.getQueryString() : "");

							return new AppDispNameExp(companyId, x.value, item.getDisplayName(), url);
						}).collect(Collectors.toList());
				appDispNames.addAll(appNames);
			});
		}

		return new DailyAttdErrorInfoDto(errorInfo, appDispNames);
	}

	@NoArgsConstructor
	private class CheckAttdErrorAfterStampRequiredImpl 
		implements CheckAttdErrorAfterStampService.Require, ClosureService.RequireM3 {
		
		CacheCarrier cacheCarrier = new CacheCarrier();

		@Override
		public Optional<StampPromptApplication> getStampSet() {
			return stamPromptAppRepo.getStampPromptApp(AppContexts.user().companyId());
		}

		@Override
		public DatePeriod findClosurePeriod(String employeeId, GeneralDate baseDate) {
			return ClosureService.findClosurePeriod(this, cacheCarrier, employeeId, baseDate);
		}

		@Override
		public Optional<ClosurePeriod> getClosurePeriod(String employeeId, GeneralDate baseDate) {
			Closure closure = ClosureService.getClosureDataByEmployee(this, cacheCarrier, employeeId, baseDate);
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

		@Override
		public Optional<ClosureEmployment> employmentClosure(String companyID, String employmentCD) {
			return closureEmploymentRepository.findByEmploymentCD(companyID, employmentCD);
		}

		@Override
		public Optional<Closure> closure(String companyId, int closureId) {
			return closureRepository.findById(companyId, closureId);
		}

		@Override
		public Optional<BsEmploymentHistoryImport> employmentHistory(CacheCarrier cacheCarrier, String companyId,
				String employeeId, GeneralDate baseDate) {
			return shrEmpAdapter.findEmploymentHistoryRequire(cacheCarrier, companyId, employeeId, baseDate);
		}

	}

}