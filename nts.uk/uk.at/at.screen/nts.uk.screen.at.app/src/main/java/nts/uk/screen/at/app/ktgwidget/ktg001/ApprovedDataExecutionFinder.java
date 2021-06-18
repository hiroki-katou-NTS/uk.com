package nts.uk.screen.at.app.ktgwidget.ktg001;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.i18n.I18NText;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.ClosureIdPresentClosingPeriod;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.GetClosureIdPresentClosingPeriods;
import nts.uk.ctx.sys.auth.pub.role.RoleExportRepo;
import nts.uk.ctx.sys.portal.dom.toppagepart.standardwidget.ApproveWidgetRepository;
import nts.uk.ctx.sys.portal.dom.toppagepart.standardwidget.ApprovedAppStatusDetailedSetting;
import nts.uk.ctx.sys.portal.dom.toppagepart.standardwidget.ApprovedApplicationStatusItem;
import nts.uk.ctx.sys.portal.dom.toppagepart.standardwidget.StandardWidget;
import nts.uk.ctx.sys.portal.dom.toppagepart.standardwidget.StandardWidgetType;
import nts.uk.screen.at.app.ktgwidget.find.dto.ClosureIdPresentClosingPeriodDto;
import nts.uk.screen.at.app.ktgwidget.find.dto.PresentClosingPeriodImportDto;
import nts.uk.screen.at.app.ktgwidget.find.dto.StatusDetailedSettingDto;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * UKDesign.UniversalK.就業.KTG_ウィジェット.KTG001_承認すべきデータ.アルゴリズム.承認すべきデータのウィジェットを起動する.承認すべきデータのウィジェットを起動する
 * 
 * @author tutt
 *
 */
@Stateless
public class ApprovedDataExecutionFinder {

	@Inject
	private ApproveWidgetRepository approveWidgetRepository;

	@Inject
	private RoleExportRepo roleExportRepo;

	@Inject
	private GetClosureIdPresentClosingPeriods getClosureIdPresentClosingPeriods;

	@Inject
	private ApprovedInfoFinder approvedInfoFinder;

	/**
	 * 承認すべきデータのウィジェットを起動する
	 * 
	 * @param companyId
	 * @param employeeId
	 * @param yearMonth
	 * @param closureId
	 * @return
	 */
	public ApprovedDataExecutionResultDto getApprovedDataExecutionResult(Integer yearMonth, Integer closureId) {

		String companyId = AppContexts.user().companyId();
		String employeeId = AppContexts.user().employeeId();

		ApprovedDataExecutionResultDto approvedDataExecutionResultDto = new ApprovedDataExecutionResultDto();
		List<ClosureIdPresentClosingPeriod> closingPeriods = new ArrayList<>();

		// 1. 指定するウィジェットの設定を取得する
		Optional<StandardWidget> standardWidgetOpt = approveWidgetRepository
				.findByWidgetTypeAndCompanyId(StandardWidgetType.APPROVE_STATUS, companyId);
		
		// 4. ログイン者が担当者か判断する
		Boolean haveParticipant = roleExportRepo.getWhetherLoginerCharge(AppContexts.user().roles()).isEmployeeCharge();
		
		List<ApprovedAppStatusDetailedSetting> approvedAppStatusDetailedSettingList = new ArrayList<>();
		
		approvedAppStatusDetailedSettingList.add(new ApprovedAppStatusDetailedSetting(NotUseAtr.USE, ApprovedApplicationStatusItem.APPLICATION_DATA));
		approvedAppStatusDetailedSettingList.add(new ApprovedAppStatusDetailedSetting(NotUseAtr.USE, ApprovedApplicationStatusItem.DAILY_PERFORMANCE_DATA));
		approvedAppStatusDetailedSettingList.add(new ApprovedAppStatusDetailedSetting(NotUseAtr.USE, ApprovedApplicationStatusItem.MONTHLY_RESULT_DATA));
		approvedAppStatusDetailedSettingList.add(new ApprovedAppStatusDetailedSetting(NotUseAtr.NOT_USE, ApprovedApplicationStatusItem.AGREEMENT_APPLICATION_DATA));
		
		if (standardWidgetOpt.isPresent()) {
			approvedAppStatusDetailedSettingList = standardWidgetOpt.get().getApprovedAppStatusDetailedSettingList();
		}
		
		// 2. 全ての締めの処理年月と締め期間を取得する
		closingPeriods = getClosureIdPresentClosingPeriods.get(companyId);

		// 3. 全ての承認すべき情報を取得する
		approvedDataExecutionResultDto = approvedInfoFinder.get(approvedDataExecutionResultDto, approvedAppStatusDetailedSettingList,
				closingPeriods, employeeId, companyId, yearMonth, closureId);

		// set response
		List<ClosureIdPresentClosingPeriodDto> closingPeriodDtos = new ArrayList<>();

		closingPeriodDtos = closingPeriods.stream()
				.map(c -> new ClosureIdPresentClosingPeriodDto(c.getClosureId(),
						new PresentClosingPeriodImportDto(c.getCurrentClosingPeriod().getProcessingYm().v(),
								c.getCurrentClosingPeriod().getClosureStartDate().toString(),
								c.getCurrentClosingPeriod().getClosureEndDate().toString())))
				.collect(Collectors.toList());

		approvedDataExecutionResultDto.setHaveParticipant(haveParticipant);
		approvedDataExecutionResultDto.setTopPagePartName(standardWidgetOpt.isPresent() ? standardWidgetOpt.get().getName().v() : I18NText.getText("KTG001_12") );
		approvedDataExecutionResultDto.setClosingPeriods(closingPeriodDtos);

		approvedDataExecutionResultDto
				.setApprovedAppStatusDetailedSettings(approvedAppStatusDetailedSettingList.stream()
						.map(a -> new StatusDetailedSettingDto(a.getDisplayType().value, a.getItem().value))
						.collect(Collectors.toList()));

		return approvedDataExecutionResultDto;
	}
}
