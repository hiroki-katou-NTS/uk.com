package nts.uk.screen.at.app.dailyperformance.correction.monthflex;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.uk.ctx.at.function.dom.dailyperformanceformat.repository.AuthorityFormatMonthlyRepository;
import nts.uk.ctx.at.record.app.find.monthly.root.common.ClosureDateDto;
import nts.uk.ctx.at.record.dom.dailyperformanceformat.repository.BusinessTypeFormatMonthlyRepository;
import nts.uk.ctx.at.record.dom.workrecord.identificationstatus.repository.ConfirmationMonthRepository;
import nts.uk.ctx.at.record.dom.workrecord.operationsetting.SettingUnitType;
import nts.uk.ctx.at.shared.app.find.scherec.monthlyattditem.DisplayAndInputMonthlyDto;
import nts.uk.ctx.at.shared.app.find.scherec.monthlyattditem.MonthlyItemControlByAuthDto;
import nts.uk.ctx.at.shared.app.find.scherec.monthlyattditem.MonthlyItemControlByAuthFinder;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ItemValue;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmployment;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmploymentRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;
import nts.uk.ctx.at.shared.pub.workrule.closure.PresentClosingPeriodExport;
import nts.uk.ctx.at.shared.pub.workrule.closure.ShClosurePub;
import nts.uk.screen.at.app.dailyperformance.correction.DailyPerformanceScreenRepo;
import nts.uk.screen.at.app.dailyperformance.correction.agreement.AgreementInfomationDto;
import nts.uk.screen.at.app.dailyperformance.correction.agreement.DisplayAgreementInfo;
import nts.uk.screen.at.app.dailyperformance.correction.dto.month.DPMonthParent;
import nts.uk.screen.at.app.dailyperformance.correction.flex.change.ErrorFlexMonthDto;
import nts.uk.screen.at.app.dailyperformance.correction.flex.change.FlexInfoDisplayChange;
import nts.uk.screen.at.app.dailyperformance.correction.flex.change.FlexShortageDto;
import nts.uk.screen.at.app.monthlyperformance.correction.dto.FormatDailyDto;
import nts.uk.screen.at.app.monthlyperformance.correction.query.MonthlyModifyQueryProcessor;
import nts.uk.screen.at.app.monthlyperformance.correction.query.MonthlyModifyResult;
import nts.uk.screen.at.app.monthlyperformance.correction.query.MonthlyMultiQuery;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.time.calendar.date.ClosureDate;

@Stateless
@Transactional
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
	
    @Inject
    private DisplayAgreementInfo displayAgreementInfo;
    
    @Inject
    private DailyPerformanceScreenRepo repo;
    
    @Inject
    private MonthlyItemControlByAuthFinder monthlyItemControlByAuthFinder;

	private static final List<Integer> DAFAULT_ITEM = Arrays.asList(18, 19, 21, 189, 190, 191, 202, 204);
	
	private static final String FORMAT_HH_MM = "%d:%02d";

	public DPMonthResult getDPMonthFlex(DPMonthFlexParam param) {
		String companyId = param.getCompanyId();
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
		// フォーマット．月次の勤怠項目一覧が存在するかチェックする
		itemIds = formatDaily.stream().map(x-> x.getAttendanceItemId()).collect(Collectors.toList());
		
		if (!itemIds.isEmpty()) {
			// 対応するドメインモデル「権限別月次項目制御」を取得する
			MonthlyItemControlByAuthDto monthlyItemAuthDto = monthlyItemControlByAuthFinder
					.getMonthlyItemControlByToUse(companyId, AppContexts.user().roles().forAttendance(), itemIds, 1);
			// 取得したドメインモデル「権限別月次項目制御」の件数をチェックする
			if (monthlyItemAuthDto != null) {
				List<DisplayAndInputMonthlyDto> listDisplayAndInputMonthly = monthlyItemAuthDto
						.getListDisplayAndInputMonthly();
				if (!listDisplayAndInputMonthly.isEmpty()) {
					hasItem = true;
					// itemIds.addAll(DAFAULT_ITEM);
					itemIds = listDisplayAndInputMonthly.stream().map(x -> x.getItemMonthlyId())
							.collect(Collectors.toList());
					// 対応する「月別実績」をすべて取得する

					itemMonthResults = monthlyModifyQueryProcessor.initScreen(
							new MonthlyMultiQuery(Arrays.asList(param.getEmployeeId())), itemIds,
							closingPeriod.get().getProcessingYm(),
							ClosureId.valueOf(closureEmploymentOptional.get().getClosureId()),
							new ClosureDate(closingPeriod.get().getClosureDate().getClosureDay(),
									closingPeriod.get().getClosureDate().getLastDayOfMonth()));

				}
			}
		}
		// ドメインモデル「月の本人確認」を取得する
//		Optional<ConfirmationMonth> confirmMonth = confirmationMonthRepository.findByKey(companyId,
//				param.getEmployeeId(), ClosureId.valueOf(closureEmploymentOptional.get().getClosureId()),
//				new Day(closingPeriod.get().getClosureDate().getClosureDay()), closingPeriod.get().getProcessingYm());
		// TODO ドメインモデル「社員の月別実績エラー一覧」を取得する
		List<ErrorFlexMonthDto> errorMonth = repo.getErrorFlexMonth(0, closingPeriod.get().getProcessingYm().v(), param.getEmployeeId(),
				closureEmploymentOptional.get().getClosureId(), closingPeriod.get().getClosureDate().getClosureDay().intValue(),
				closingPeriod.get().getClosureDate().getLastDayOfMonth().booleanValue() ? 1 : 0);
		//フレックス情報を表示する
		itemMonthFlexResults = monthlyModifyQueryProcessor.initScreen(
				new MonthlyMultiQuery(Arrays.asList(param.getEmployeeId())), DAFAULT_ITEM,
				closingPeriod.get().getProcessingYm(),
				ClosureId.valueOf(closureEmploymentOptional.get().getClosureId()),
				new ClosureDate(closingPeriod.get().getClosureDate().getClosureDay(),
						closingPeriod.get().getClosureDate().getLastDayOfMonth()));
		
		FlexShortageDto flexShortageDto = flexInfoDisplayChange.flexInfo(companyId, param.getEmployeeId(), param.getDate(), null, closingPeriod, itemMonthFlexResults);
		flexShortageDto.createError(errorMonth);
		flexShortageDto.createMonthParent(new DPMonthParent(param.getEmployeeId(), closingPeriod.get().getProcessingYm().v(),
				closureEmploymentOptional.get().getClosureId(),
				new ClosureDateDto(closingPeriod.get().getClosureDate().getClosureDay(),
						closingPeriod.get().getClosureDate().getLastDayOfMonth())));
		
		AgreementInfomationDto agreeDto = displayAgreementInfo.displayAgreementInfo(companyId, param.getEmployeeId(),
				closingPeriod.get().getClosureEndDate().year(), closingPeriod.get().getClosureEndDate().month());
		setAgreeItem(itemMonthFlexResults, agreeDto);
		
		return new DPMonthResult(flexShortageDto, itemMonthResults, hasItem,
				closingPeriod.get().getProcessingYm().v(), formatDaily, agreeDto);
	}

	private void setAgreeItem(List<MonthlyModifyResult> itemMonthFlexResults, AgreementInfomationDto agreeDto){
		if(itemMonthFlexResults.isEmpty()) return;
		else{
			List<ItemValue> values = itemMonthFlexResults.get(0).getItems().stream()
					.filter(x -> (x.getItemId() == 202 || x.getItemId() == 204))
					.sorted((x, y) -> x.getItemId() - y.getItemId()).collect(Collectors.toList());
			agreeDto.setAgreementTime36(values.get(0).getValue() != null ? convertTime(Integer.parseInt(values.get(0).getValue())) : "0:00");
			agreeDto.setMaxTime(values.get(1).getValue() != null ? convertTime(Integer.parseInt(values.get(1).getValue())) : "0:00");
		}
	}
	
	private String convertTime(int minute){
		int hours = minute / 60;
		int minutes = Math.abs(minute) % 60;
		return (minute < 0 && hours == 0) ?  "-"+String.format(FORMAT_HH_MM, hours, minutes) : String.format(FORMAT_HH_MM, hours, minutes);
	}
}
