package nts.uk.screen.at.app.ktgwidget.ktg001;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.app.query.workrule.closure.ClosureIdPresentClosingPeriod;
import nts.uk.ctx.at.shared.app.query.workrule.closure.GetClosureIdPresentClosingPeriods;
import nts.uk.ctx.sys.auth.pub.role.RoleExportRepo;
import nts.uk.ctx.sys.portal.dom.toppagepart.standardwidget.ApproveWidgetRepository;
import nts.uk.ctx.sys.portal.dom.toppagepart.standardwidget.StandardWidget;
import nts.uk.ctx.sys.portal.dom.toppagepart.standardwidget.StandardWidgetType;
import nts.uk.screen.at.app.ktgwidget.find.dto.ApprovedAppStatusDetailedSettingDto;
import nts.uk.screen.at.app.ktgwidget.find.dto.ClosureIdPresentClosingPeriodDto;
import nts.uk.screen.at.app.ktgwidget.find.dto.PresentClosingPeriodImportDto;
import nts.uk.shr.com.context.AppContexts;

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
	public ApprovedDataExecutionResultDto getApprovedDataExecutionResult(Integer yearMonth, int closureId) {

		String companyId = AppContexts.user().companyId();
		String employeeId = AppContexts.user().employeeId();

		ApprovedDataExecutionResultDto approvedDataExecutionResultDto = new ApprovedDataExecutionResultDto();
		List<ClosureIdPresentClosingPeriod> closingPeriods = new ArrayList<>();

		// 1. 指定するウィジェットの設定を取得する
		Optional<StandardWidget> standardWidgetOpt = approveWidgetRepository
				.findByWidgetTypeAndCompanyId(StandardWidgetType.APPROVE_STATUS, companyId);
		
		// 4. ログイン者が担当者か判断する
				Boolean haveParticipant = roleExportRepo.getWhetherLoginerCharge(AppContexts.user().roles()).isEmployeeCharge();
				
		if (!standardWidgetOpt.isPresent()) {
			approvedDataExecutionResultDto.setHaveParticipant(haveParticipant);
			return approvedDataExecutionResultDto;
		}
		
		StandardWidget standardWidget = standardWidgetOpt.get();
		
		// 2. 全ての締めの処理年月と締め期間を取得する
		closingPeriods = getClosureIdPresentClosingPeriods.get(companyId);

		// 3. 全ての承認すべき情報を取得する
		approvedDataExecutionResultDto = approvedInfoFinder.get(approvedDataExecutionResultDto, standardWidget,
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
		approvedDataExecutionResultDto.setTopPagePartName(standardWidget.getName().v());
		approvedDataExecutionResultDto.setClosingPeriods(closingPeriodDtos);

		approvedDataExecutionResultDto
				.setApprovedAppStatusDetailedSettings(standardWidget.getApprovedAppStatusDetailedSettingList().stream()
						.map(a -> new ApprovedAppStatusDetailedSettingDto(a.getDisplayType().value, a.getItem().value))
						.collect(Collectors.toList()));

		return approvedDataExecutionResultDto;
	}
}
