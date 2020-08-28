package nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.service;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.apache.logging.log4j.util.Strings;

import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.common.adapter.closure.PresentClosingPeriodImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.closure.RqClosureAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.schedule.shift.businesscalendar.daycalendar.ObtainDeadlineDateAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.workplace.WkpHistImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.workplace.WorkplaceAdapter;
import nts.uk.ctx.at.request.dom.application.common.service.smartphone.output.DeadlineLimitCurrentMonth;
import nts.uk.ctx.at.request.dom.setting.UseDivision;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.ApplicationSetting;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.ApplicationSettingRepository;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.appdeadlineset.AppDeadlineSetting;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.appdeadlineset.DeadlineCriteria;

/**
 * refactor 4
 * @author Doan Duy Hung
 *
 */
@Stateless
public class AppDeadlineSettingGetImpl implements AppDeadlineSettingGet {
	
	@Inject
	private ApplicationSettingRepository applicationSettingRepository;
	
	@Inject
	private RqClosureAdapter rqClosureAdapter;
	
	@Inject
	private WorkplaceAdapter workplaceAdapter;
	
	@Inject
	private ObtainDeadlineDateAdapter obtainDeadlineDateAdapter;

	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public DeadlineLimitCurrentMonth getApplicationDeadline(String companyID, String employeeID, int closureID) {
		// ドメインモデル「申請設定」を取得する
		ApplicationSetting applicationSetting = applicationSettingRepository.findByCompanyId(companyID).orElse(null);
		AppDeadlineSetting appDeadlineSetting = applicationSetting.getAppDeadlineSetLst().stream().filter(x -> x.getClosureId() == closureID).findAny().get();
		// ドメインモデル「申請締切設定」．利用区分をチェックする(check利用区分)
		if (appDeadlineSetting.getUseAtr() == UseDivision.NOT_USE) {
			DeadlineLimitCurrentMonth deadlineLimitCurrentMonth = new DeadlineLimitCurrentMonth(false);
			return deadlineLimitCurrentMonth;
		}
		// アルゴリズム「処理年月と締め期間を取得する」を実行する
		PresentClosingPeriodImport presentClosingPeriodImport = rqClosureAdapter.getClosureById(companyID, closureID).get();
		// ドメインモデル「申請締切設定」．締切基準をチェックする
		Optional<GeneralDate> deadline = Optional.empty();
		if(appDeadlineSetting.getDeadlineCriteria() == DeadlineCriteria.WORKING_DAY) {
			// アルゴリズム「社員所属職場履歴を取得」を実行する
			WkpHistImport wkpHistImport = workplaceAdapter.findWkpBySid(employeeID, GeneralDate.today());
			// アルゴリズム「締切日を取得する」を実行する
			// nếu wkpHistImport = null thì xem QA http://192.168.50.4:3000/issues/97192
			if(wkpHistImport==null || Strings.isBlank(wkpHistImport.getWorkplaceId())){
				throw new BusinessException("EA No.2110: 終了状態：申請締切日取得エラー");
			}
			deadline = Optional.of(obtainDeadlineDateAdapter.obtainDeadlineDate(
					presentClosingPeriodImport.getClosureEndDate(), 
					appDeadlineSetting.getDeadline().v(), 
					wkpHistImport.getWorkplaceId(), 
					companyID));
		} else {
			// 申請締め切り日 = 締め終了日.AddDays(ドメインモデル「申請締切設定」．締切日数)
			deadline = Optional.of(presentClosingPeriodImport.getClosureEndDate().addDays(appDeadlineSetting.getDeadline().v()));
		}
		DeadlineLimitCurrentMonth deadlineLimitCurrentMonth = new DeadlineLimitCurrentMonth(true);
		deadlineLimitCurrentMonth.setOpAppDeadline(deadline);
		return deadlineLimitCurrentMonth;
	}
	
}
