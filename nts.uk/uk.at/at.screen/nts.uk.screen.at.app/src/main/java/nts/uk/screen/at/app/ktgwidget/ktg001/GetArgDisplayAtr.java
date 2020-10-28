package nts.uk.screen.at.app.ktgwidget.ktg001;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.schedule.dom.plannedyearholiday.frame.NotUseAtr;
import nts.uk.ctx.at.shared.app.query.workrule.closure.ClosureIdPresentClosingPeriod;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.GetYearProcessAndPeriodDto;
import nts.uk.ctx.sys.portal.dom.toppagepart.standardwidget.ApprovedAppStatusDetailedSetting;
import nts.uk.ctx.sys.portal.dom.toppagepart.standardwidget.ApprovedApplicationStatusItem;

/**
 * UKDesign.UniversalK.就業.KTG_ウィジェット.KTG001_承認すべきデータ.アルゴリズム.4.36協定申請の承認すべきデータの取得
 * 
 * @author tutt
 *
 */
@Stateless
public class GetArgDisplayAtr {

	@Inject
	private PresentClosingPeriodFinder presentClosingPeriodFinder;
	
	@Inject
	private Check36AgreementApproved check36AgreementApproved;

	// 4.36協定申請の承認すべきデータの取得
	public Boolean get(List<ApprovedAppStatusDetailedSetting> approvedAppStatusDetailedSettingList,
			List<ClosureIdPresentClosingPeriod> closingPeriods, String employeeId, String companyId, Integer yearMonth,
			int closureId) {
		
		ApprovedAppStatusDetailedSetting argPerformanceDataSetting = approvedAppStatusDetailedSettingList.stream()
				.filter(a -> a.getItem().value == ApprovedApplicationStatusItem.AGREEMENT_APPLICATION_DATA.value)
				.collect(Collectors.toList()).get(0);

		if (argPerformanceDataSetting.getDisplayType().value == NotUseAtr.NOT_USE.value) {
			return false;

		} else {
			// 承認すべき申請の対象期間を取得する
			GetYearProcessAndPeriodDto periodImport = presentClosingPeriodFinder.getPeriod(closingPeriods);

			// 承認すべき36協定があるかチェックする
			return check36AgreementApproved.check36AgreementApproved(companyId, employeeId, periodImport);
		}
	}
}
