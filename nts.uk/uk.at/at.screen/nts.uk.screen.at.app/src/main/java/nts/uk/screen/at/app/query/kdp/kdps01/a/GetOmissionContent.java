package nts.uk.screen.at.app.query.kdp.kdps01.a;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.function.dom.adapter.standardmenu.StandardMenuAdaptor;
import nts.uk.ctx.at.record.app.command.kdp.kdp004.a.StampButtonCommand;
import nts.uk.ctx.at.record.dom.stamp.application.StampPromptAppRepository;
import nts.uk.ctx.at.record.dom.stamp.application.StampPromptApplication;
import nts.uk.ctx.at.record.dom.stamp.card.management.personalengraving.AppDispNameExp;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.EmployeeDailyPerErrorRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.ErAlApplication;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.ErAlApplicationRepository;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.StampMeans;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice.CheckAttdErrorAfterStampService;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice.DailyAttdErrorInfo;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.PortalStampSettings;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.PortalStampSettingsRepository;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.SettingsSmartphoneStamp;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.SettingsSmartphoneStampRepository;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.StampSetPerRepository;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.StampSettingPerson;
import nts.uk.ctx.at.request.app.find.setting.company.displayname.AppDispNameDto;
import nts.uk.ctx.at.request.dom.setting.company.displayname.AppDispNameRepository;
import nts.uk.ctx.at.shared.dom.adapter.employment.ShareEmploymentAdapter;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.erroralarm.EmployeeDailyPerError;
import nts.uk.ctx.at.shared.dom.workrule.closure.Closure;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmploymentRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosurePeriod;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.ClosureService;
//import nts.uk.ctx.sys.portal.pub.standardmenu.StandardMenuPub;
import nts.uk.screen.at.app.dailyperformance.correction.dto.ApplicationType;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author sonnlb
 *
 *         UKDesign.UniversalK.就業.KDP_打刻.KDPS01_打刻入力(スマホ).A:打刻入力(スマホ).メニュー別OCD.打刻入力(スマホ)の打刻漏れの内容を取得する
 */
@Stateless
public class GetOmissionContent {

	@Inject
	private StandardMenuAdaptor menuAdaptor;

	@Inject
	private AppDispNameRepository appDispRepo;

	@Inject
	private ClosureRepository closureRepo;
	@Inject
	private ClosureEmploymentRepository closureEmploymentRepo;
	@Inject
	private ShareEmploymentAdapter shareEmploymentAdapter;

	@Inject
	private StampPromptAppRepository stamPromptAppRepo;

	@Inject
	private ErAlApplicationRepository erAlApplicationRepo;

	@Inject
	private EmployeeDailyPerErrorRepository employeeDailyPerErrorRepo;

	@Inject
	private StampSetPerRepository stampSetPerRepo;
	
	@Inject
	private SettingsSmartphoneStampRepository settingSmartPhone;
	
	@Inject
	private PortalStampSettingsRepository settingPotal;

	public GetOmissionContentDto getOmission(StampButtonCommand query) {

		GetOmissionContentDto result = new GetOmissionContentDto();

		CheckAttdErrorAfterStampRequiredImpl require = new CheckAttdErrorAfterStampRequiredImpl();

		// 1.require, 社員ID, 打刻手段, 打刻ボタン
		List<DailyAttdErrorInfo> errorInfo = CheckAttdErrorAfterStampService.get(require,
				AppContexts.user().employeeId(), EnumAdaptor.valueOf(query.getStampMeans(), StampMeans.class),
				query.toDomainValue());

		result.setErrorInfo(
				errorInfo.stream().map(x -> DailyOmissionAttdErrorInfoDto.fromDomain(x)).collect(Collectors.toList()));

		// アルゴリズム「メニューの表示名を取得する」を実行する

		List<AppDispNameExp> appDispNames = new ArrayList<AppDispNameExp>();

		if (errorInfo.size() > 0) {
			List<ApplicationType> appTypes = errorInfo.get(0).getListRequired().stream().sorted()
					.map(x -> EnumAdaptor.valueOf(x, ApplicationType.class)).collect(Collectors.toList());

			String companyId = AppContexts.user().companyId();
			appTypes.forEach(x -> {

				List<AppDispNameExp> appNames = this.menuAdaptor
						.getMenuDisplayName(companyId, Arrays.asList(x.toStandardMenuNameQuery())).stream()
						.map(item -> {
							String screen = item.getProgramId().substring(0, 3).toLowerCase();
							String screenCd = item.getProgramId().substring(3, 6).toLowerCase();
							String url = "/view/" + screen + "/" + screenCd + "/" + item.getScreenId().toLowerCase()
									+ "/index.xhtml"
									+ (item.getQueryString() != null ? "?" + item.getQueryString() : "");

							return new AppDispNameExp(companyId, x.value, item.getDisplayName(), screen, screenCd,
									item.getScreenId(), item.getQueryString(), url);
						}).collect(Collectors.toList());
				appDispNames.addAll(appNames);
			});
		}

		result.setAppDispNames(appDispNames);

		// アルゴリズム「申請種類を取得する」を実行する

		List<AppDispNameDto> appNames = this.appDispRepo.getAll().stream().map(x -> AppDispNameDto.convertToDto(x))
				.collect(Collectors.toList());

		result.setAppNames(appNames);

		return result;

	}

	
	private class CheckAttdErrorAfterStampRequiredImpl implements CheckAttdErrorAfterStampService.Require {

		@Override
		public Optional<StampPromptApplication> getStampSet() {
			return stamPromptAppRepo.getStampPromptApp(AppContexts.user().companyId());
		}

		@Override
		public DatePeriod findClosurePeriod(String employeeId, GeneralDate baseDate) {
			return ClosureService.findClosurePeriod(
					ClosureService.createRequireM3(closureRepo, closureEmploymentRepo, shareEmploymentAdapter),
					new CacheCarrier(), employeeId, baseDate);
		}

		@Override
		public Optional<ClosurePeriod> getClosurePeriod(String employeeId, GeneralDate baseDate) {
			Closure closure = ClosureService.getClosureDataByEmployee(
					ClosureService.createRequireM3(closureRepo, closureEmploymentRepo, shareEmploymentAdapter),
					new CacheCarrier(), employeeId, baseDate);
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
		public Optional<SettingsSmartphoneStamp> getSettingSmartPhone() {
			return settingSmartPhone.get(AppContexts.user().companyId());
		}

		@Override
		public Optional<PortalStampSettings> getSettingPortal() {
			return settingPotal.get(AppContexts.user().companyId());
		}

	}
}
