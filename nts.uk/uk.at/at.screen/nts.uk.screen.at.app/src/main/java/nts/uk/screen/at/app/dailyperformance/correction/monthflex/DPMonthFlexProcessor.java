package nts.uk.screen.at.app.dailyperformance.correction.monthflex;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.app.find.monthly.root.common.ClosureDateDto;
import nts.uk.ctx.at.record.dom.workrecord.identificationstatus.month.ConfirmationMonth;
import nts.uk.ctx.at.record.dom.workrecord.identificationstatus.repository.ConfirmationMonthRepository;
import nts.uk.ctx.at.record.dom.workrecord.operationsetting.SettingUnitType;
import nts.uk.ctx.at.shared.dom.common.Day;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureDate;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmployment;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmploymentRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;
import nts.uk.ctx.at.shared.pub.workrule.closure.PresentClosingPeriodExport;
import nts.uk.ctx.at.shared.pub.workrule.closure.ShClosurePub;
import nts.uk.screen.at.app.dailyperformance.correction.DailyPerformanceScreenRepo;
import nts.uk.screen.at.app.dailyperformance.correction.dto.month.DPMonthParent;
import nts.uk.screen.at.app.dailyperformance.correction.flex.change.FlexInfoDisplayChange;
import nts.uk.screen.at.app.dailyperformance.correction.flex.change.FlexShortageDto;
import nts.uk.screen.at.app.monthlyperformance.correction.query.MonthlyModifyQueryProcessor;
import nts.uk.screen.at.app.monthlyperformance.correction.query.MonthlyModifyResult;
import nts.uk.screen.at.app.monthlyperformance.correction.query.MonthlyMultiQuery;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class DPMonthFlexProcessor {

	@Inject
	private ClosureEmploymentRepository closureEmploymentRepository;

	@Inject
	private ShClosurePub shClosurePub;

	@Inject
	private DailyPerformanceScreenRepo repo;

	@Inject
	private MonthlyModifyQueryProcessor monthlyModifyQueryProcessor;

	@Inject
	private ConfirmationMonthRepository confirmationMonthRepository;
	
	@Inject
	private FlexInfoDisplayChange flexInfoDisplayChange;

	private static final List<Integer> DAFAULT_ITEM = Arrays.asList(18, 19, 21, 189, 190, 191);

	public DPMonthResult getDPMonthFlex(DPMonthFlexParam param) {
		String companyId = AppContexts.user().companyId();
		List<Integer> itemIds = new ArrayList<>();
		boolean hasItem = false;
		List<MonthlyModifyResult> itemMonthResults = new ArrayList<>();
		List<MonthlyModifyResult> itemMonthFlexResults = new ArrayList<>();
		// 社員に対応する処理締めを取得する
		Optional<ClosureEmployment> closureEmploymentOptional = this.closureEmploymentRepository
				.findByEmploymentCD(companyId, param.getEmploymentCode());
		if (!closureEmploymentOptional.isPresent())
			return null;
		// 指定した年月日時点の締め期間を取得する
		Optional<PresentClosingPeriodExport> closingPeriod = shClosurePub.find(companyId,
				closureEmploymentOptional.get().getClosureId(), param.getDate());
		if (!closingPeriod.isPresent())
			return null;

		if (param.dailyPerformanceDto.getSettingUnit() == SettingUnitType.AUTHORITY) {
			itemIds = repo.getItemIdsMonthByAuthority(companyId, param.getFormatCode());
		} else {
			itemIds = repo.getItemIdsMonthByBussiness(companyId, param.getFormatCode());
		}
		if (!itemIds.isEmpty()) {
			hasItem = true;
			// itemIds.addAll(DAFAULT_ITEM);

			// 対応する「月別実績」をすべて取得する

			itemMonthResults = monthlyModifyQueryProcessor.initScreen(
					new MonthlyMultiQuery(Arrays.asList(param.getEmployeeId())), itemIds,
					closingPeriod.get().getProcessingYm(),
					ClosureId.valueOf(closureEmploymentOptional.get().getClosureId()),
					new ClosureDate(closingPeriod.get().getClosureDate().getClosureDay(),
							closingPeriod.get().getClosureDate().getLastDayOfMonth()));
		}
		// ドメインモデル「月の本人確認」を取得する
		Optional<ConfirmationMonth> confirmMonth = confirmationMonthRepository.findByKey(companyId,
				param.getEmployeeId(), ClosureId.valueOf(closureEmploymentOptional.get().getClosureId()),
				new Day(closingPeriod.get().getClosureDate().getClosureDay()), closingPeriod.get().getProcessingYm());

		// TODO ドメインモデル「社員の月別実績エラー一覧」を取得する
		
		//フレックス情報を表示する
		itemMonthFlexResults = monthlyModifyQueryProcessor.initScreen(
				new MonthlyMultiQuery(Arrays.asList(param.getEmployeeId())), DAFAULT_ITEM,
				closingPeriod.get().getProcessingYm(),
				ClosureId.valueOf(closureEmploymentOptional.get().getClosureId()),
				new ClosureDate(closingPeriod.get().getClosureDate().getClosureDay(),
						closingPeriod.get().getClosureDate().getLastDayOfMonth()));
		FlexShortageDto flexShortageDto = flexInfoDisplayChange.flexInfo(param.getEmployeeId(), param.getDate(), null, closingPeriod, itemMonthFlexResults);
		flexShortageDto.createMonthParent(new DPMonthParent(param.getEmployeeId(), closingPeriod.get().getProcessingYm().v(),
				closureEmploymentOptional.get().getClosureId(),
				new ClosureDateDto(closingPeriod.get().getClosureDate().getClosureDay(),
						closingPeriod.get().getClosureDate().getLastDayOfMonth())));
		return new DPMonthResult(flexShortageDto, itemMonthResults, false, hasItem);
	}

}
