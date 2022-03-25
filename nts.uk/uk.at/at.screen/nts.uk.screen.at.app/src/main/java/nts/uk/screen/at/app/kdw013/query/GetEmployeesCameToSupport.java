package nts.uk.screen.at.app.kdw013.query;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.ejb.Stateless;
import javax.inject.Inject;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.supportmanagement.supportoperationsetting.SupportOperationSetting.Require;
import nts.uk.ctx.at.record.dom.daily.ouen.OuenWorkTimeSheetOfDailyRepo;
import nts.uk.ctx.at.shared.dom.supportmanagement.supportoperationsetting.SupportOperationSetting;
import nts.uk.ctx.at.shared.dom.supportmanagement.supportoperationsetting.SupportOperationSettingRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.license.option.OptionLicense;

/**
 * 
 * @author PhongTQ
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務実績.勤務実績.日別時間帯別実績.App.応援勤務に来た社員を取得する.応援勤務に来た社員を取得する
 */
@Stateless
public class GetEmployeesCameToSupport {
	
	@Inject
	private SupportOperationSettingRepository repo;
	
	@Inject
	private OuenWorkTimeSheetOfDailyRepo dailyRepo;
	
	public List<String> getEmployeesCameToSupport(DatePeriod period, List<String> lstWplId) {
		List<String> lstEmps = new ArrayList<>();
		
		// 1: Get(ログイン会社ID)
		Optional<SupportOperationSetting> support = repo.getSupportOperationSetting(AppContexts.user().companyId());
		
		// 2: 応援の運用設定.isPresent:応援を利用できるか(@Require)
		Require require = new SupportOperationSettingImpl();
		boolean isSupport = false;
		if (support.isPresent())
			isSupport = support.get().canUsedSupport(require);
		
		// 3:
		if (isSupport)
			lstEmps = dailyRepo.getListEmp(AppContexts.user().companyId(), lstWplId, period).stream().distinct().collect(Collectors.toList());
		
		return lstEmps;
	}
	
	public class SupportOperationSettingImpl implements SupportOperationSetting.Require {
		@Override
		public OptionLicense getOptionLicense() {
			return AppContexts.optionLicense();
		}
	}
}
