package nts.uk.screen.at.app.dailyperformance.correction.monthflex;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.function.dom.dailyperformanceformat.repository.AuthorityFormatMonthlyRepository;
import nts.uk.ctx.at.record.app.find.monthly.root.common.ClosureDateDto;
import nts.uk.ctx.at.record.dom.dailyperformanceformat.repository.BusinessTypeFormatMonthlyRepository;
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
import nts.uk.screen.at.app.dailyperformance.correction.dto.month.DPMonthParent;
import nts.uk.screen.at.app.dailyperformance.correction.flex.change.ErrorFlexMonthDto;
import nts.uk.screen.at.app.dailyperformance.correction.flex.change.FlexInfoDisplayChange;
import nts.uk.screen.at.app.dailyperformance.correction.flex.change.FlexShortageDto;
import nts.uk.screen.at.app.monthlyperformance.correction.dto.FormatDailyDto;
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
	private MonthlyModifyQueryProcessor monthlyModifyQueryProcessor;

	@Inject
	private ConfirmationMonthRepository confirmationMonthRepository;
	
	@Inject
	private FlexInfoDisplayChange flexInfoDisplayChange;
	
	@Inject
	private BusinessTypeFormatMonthlyRepository businessTypeFormatMonthlyRepository;
	
	@Inject
	private AuthorityFormatMonthlyRepository authorityFormatMonthlyRepository;

	private static final List<Integer> DAFAULT_ITEM = Arrays.asList(18, 19, 21, 189, 190, 191);

	public DPMonthResult getDPMonthFlex(DPMonthFlexParam param) {
		String companyId = AppContexts.user().companyId();
		List<Integer> itemIds = new ArrayList<>();
		boolean hasItem = false;
		List<MonthlyModifyResult> itemMonthResults = new ArrayList<>();
		List<MonthlyModifyResult> itemMonthFlexResults = new ArrayList<>();
		List<FormatDailyDto> formatDaily = new ArrayList<>();
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
			List<String> listDailyPerformanceFormatCode = new ArrayList<>(param.getFormatCode());
			if (listDailyPerformanceFormatCode.size() > 0) {
				formatDaily = authorityFormatMonthlyRepository
						.getListAuthorityFormatDaily(companyId, listDailyPerformanceFormatCode).stream()
						.map(x -> new FormatDailyDto(x.getDailyPerformanceFormatCode().v(), new Integer(x.getAttendanceItemId()),
								x.getColumnWidth(), x.getDisplayOrder()))
						.collect(Collectors.toList());
			}
		} else {
			List<String> listBusinessTypeCode = new ArrayList<>(param.getFormatCode());
			if (listBusinessTypeCode.size() > 0) {
				formatDaily = businessTypeFormatMonthlyRepository
						.getListBusinessTypeFormat(companyId, listBusinessTypeCode).stream()
						.map(x -> new FormatDailyDto(x.getBusinessTypeCode().v(), new Integer(x.getAttendanceItemId()),
								x.getColumnWidth(), x.getOrder()))
						.collect(Collectors.toList());
			}
		}
		
		itemIds = formatDaily.stream().map(x-> x.getAttendanceItemId()).collect(Collectors.toList());
		
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
//		Optional<ErrorFlexMonthDto> errorMonth = repo.getErrorFlexMonth(0, closingPeriod.get().getProcessingYm().v(), param.getEmployeeId(),
//				closureEmploymentOptional.get().getClosureId(), closingPeriod.get().getClosureDate().getClosureDay().intValue(),
//				closingPeriod.get().getClosureDate().getLastDayOfMonth().booleanValue() ? 1 : 0);
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
		
		return new DPMonthResult(flexShortageDto, itemMonthResults, false, hasItem, closingPeriod.get().getProcessingYm().v(), formatDaily);
	}

}
