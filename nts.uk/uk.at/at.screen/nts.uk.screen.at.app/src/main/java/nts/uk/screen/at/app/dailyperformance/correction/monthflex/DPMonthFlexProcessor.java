package nts.uk.screen.at.app.dailyperformance.correction.monthflex;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.arc.layer.app.cache.CacheCarrier;
import nts.uk.ctx.at.function.dom.dailyperformanceformat.repository.AuthorityFormatMonthlyRepository;
import nts.uk.ctx.at.record.app.find.monthly.root.MonthlyRecordWorkDto;
import nts.uk.ctx.at.record.app.find.monthly.root.common.ClosureDateDto;
import nts.uk.ctx.at.record.dom.dailyperformanceformat.repository.BusinessTypeFormatMonthlyRepository;
import nts.uk.ctx.at.record.dom.require.RecordDomRequireService;
import nts.uk.ctx.at.record.dom.workrecord.operationsetting.SettingUnitType;
import nts.uk.ctx.at.shared.app.find.scherec.monthlyattditem.MonthlyItemControlByAuthDto;
import nts.uk.ctx.at.shared.app.find.scherec.monthlyattditem.MonthlyItemControlByAuthFinder;
import nts.uk.ctx.at.shared.dom.scherec.attendanceitem.converter.util.AttendanceItemUtil;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.adapter.attendanceitemname.AttItemName;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattendanceitem.service.CompanyMonthlyItemService;
import nts.uk.ctx.at.shared.dom.workrule.closure.Closure;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosurePeriod;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.ClosureService;
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

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class DPMonthFlexProcessor {

//	@Inject
//	private ClosureEmploymentRepository closureEmploymentRepository;
//
//	@Inject
//	private ShClosurePub shClosurePub;

	@Inject
	private MonthlyModifyQueryProcessor monthlyModifyQueryProcessor;

//	@Inject
//	private ConfirmationMonthRepository confirmationMonthRepository;
	
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
    
    @Inject
    private RecordDomRequireService requireService;
    
    @Inject
    private CompanyMonthlyItemService companyMonthlyItemService;
    

	private static final List<Integer> DAFAULT_ITEM = Arrays.asList(18, 19, 21, 189, 190, 191, 202, 204);
	
    //月次項目の取得
	public DPMonthResult getDPMonthFlex(DPMonthFlexParam param) {
		String companyId = param.getCompanyId();
		List<MonthlyModifyResult> itemMonthResults = new ArrayList<>();
		List<MonthlyModifyResult> itemMonthFlexResults = new ArrayList<>();
		//nts.uk.ctx.at.shared.dom.workrule.closure.service.ClosureService.getClosureDataByEmployee
		// 社員に対応する処理締めを取得する
		Closure closure = ClosureService.getClosureDataByEmployee(
				requireService.createRequire(), new CacheCarrier(),
				param.getEmployeeId(), param.getDate());
		if(closure == null) return null;
		Optional<ClosurePeriod> closurePeriodOpt = closure.getClosurePeriodByYmd(param.getDate());
		
//		Optional<ClosureEmployment> closureEmploymentOptional = this.closureEmploymentRepository
//				.findByEmploymentCD(companyId, param.getEmploymentCode());
//		if (!closureEmploymentOptional.isPresent())
//			return null;
		// 指定した年月日時点の締め期間を取得する
//		Optional<PresentClosingPeriodExport> closingPeriod = shClosurePub.find(companyId,
//				closureEmploymentOptional.get().getClosureId(), param.getDate());
		if (!closurePeriodOpt.isPresent())
			return null;

		List<FormatDailyDto> formatDaily = getFormatCode(param, companyId);
		
		// フォーマット．月次の勤怠項目一覧が存在するかチェックする
		List<Integer> itemIdsOld = getItemIds(companyId, formatDaily);
		
		List<AttItemName> listAttItemName = new ArrayList<>();
		if(!itemIdsOld.isEmpty()) {
			//EA 4248
			//会社の月次項目を取得する
			listAttItemName = companyMonthlyItemService.getMonthlyItems(companyId, Optional.empty(), itemIdsOld, new ArrayList<>());
		}
		List<Integer> itemIds = listAttItemName.stream().map(c->c.getAttendanceItemId()).collect(Collectors.toList());
		
		// ドメインモデル「月の本人確認」を取得する
//		Optional<ConfirmationMonth> confirmMonth = confirmationMonthRepository.findByKey(companyId,
//				param.getEmployeeId(), ClosureId.valueOf(closureEmploymentOptional.get().getClosureId()),
//				new Day(closingPeriod.get().getClosureDate().getClosureDay()), closingPeriod.get().getProcessingYm());
		// TODO ドメインモデル「社員の月別実績エラー一覧」を取得する
		List<ErrorFlexMonthDto> errorMonth = repo.getErrorFlexMonth(0, closurePeriodOpt.get().getYearMonth().v(), param.getEmployeeId(),
				closurePeriodOpt.get().getClosureId().value, closurePeriodOpt.get().getClosureDate().getClosureDay().v(),
				closurePeriodOpt.get().getClosureDate().getLastDayOfMonth().booleanValue() ? 1 : 0);
		
		Optional<MonthlyRecordWorkDto> monthOpt =  !param.getMonthOpt().isPresent() ? monthlyModifyQueryProcessor.getDataMonthAll(new MonthlyMultiQuery(Arrays.asList(param.getEmployeeId())), 
				Stream.concat(itemIds.stream(), DAFAULT_ITEM.stream()).collect(Collectors.toList()),
				closurePeriodOpt.get().getYearMonth(),
				closurePeriodOpt.get().getClosureId(),
				closurePeriodOpt.get().getClosureDate()) : param.getMonthOpt();
		
		List<MonthlyModifyResult> mResult = monthOpt.isPresent() ? AttendanceItemUtil.toItemValues(Arrays.asList(monthOpt.get()),
				Stream.concat(itemIds.stream(), DAFAULT_ITEM.stream()).collect(Collectors.toList()),
				AttendanceItemUtil.AttendanceItemType.MONTHLY_ITEM).entrySet().stream().map(record -> {
					return MonthlyModifyResult.builder().items(record.getValue())
							.employeeId(record.getKey().getEmployeeId()).yearMonth(record.getKey().getYearMonth().v())
							.closureId(record.getKey().getClosureID()).closureDate(record.getKey().getClosureDate())
							.workDatePeriod(record.getKey().getAttendanceTime().getDatePeriod().toDomain())
							.version(record.getKey().getAffiliation().getVersion()).completed();
				}).collect(Collectors.toList()) : new ArrayList<>();
		
		
		if(!mResult.isEmpty()){
			MonthlyModifyResult firstDT = mResult.get(0);
			if(!itemIds.isEmpty()){
				itemMonthResults.add(MonthlyModifyResult.builder()
						.closureDate(firstDT.getClosureDate())
						.closureId(firstDT.getClosureId())
						.yearMonth(firstDT.getYearMonth())
						.employeeId(firstDT.getEmployeeId())
						.items(new ArrayList<>(firstDT.getItems().stream().filter(c -> itemIds.contains(c.itemId())).collect(Collectors.toSet())))
						.version(firstDT.getVersion())
						.completed());
			}

			//フレックス情報を表示する
			itemMonthFlexResults.add(MonthlyModifyResult.builder()
					.closureDate(firstDT.getClosureDate())
					.closureId(firstDT.getClosureId())
					.yearMonth(firstDT.getYearMonth())
					.employeeId(firstDT.getEmployeeId())
					.items(firstDT.getItems().stream().filter(c -> DAFAULT_ITEM.contains(c.itemId())).collect(Collectors.toList()))
					.version(firstDT.getVersion())
					.completed());
		}
        Optional<ClosurePeriod> closingPeriodNew = Optional.of(ClosurePeriod.of(closurePeriodOpt.get().getClosureId(),
                closurePeriodOpt.get().getClosureDate(), closurePeriodOpt.get().getYearMonth(), param.getDatePeriod()));
        //フレックス情報を取得する		
		FlexShortageDto flexShortageDto = flexInfoDisplayChange.flexInfo(companyId, 
				param.getEmployeeId(), param.getDate(), null, 
				closingPeriodNew, itemMonthFlexResults, null);
		flexShortageDto.createError(errorMonth);
		flexShortageDto.createMonthParent(new DPMonthParent(param.getEmployeeId(), closurePeriodOpt.get().getYearMonth().v(),
				closurePeriodOpt.get().getClosureId().value,
				new ClosureDateDto(closurePeriodOpt.get().getClosureDate().getClosureDay().v(),
						closurePeriodOpt.get().getClosureDate().getLastDayOfMonth())));
		if(!mResult.isEmpty()){
			flexShortageDto.getMonthParent().setVersion(mResult.get(0).getVersion());
			flexShortageDto.setVersion(mResult.get(0).getVersion());
		}
		AgreementInfomationDto agreeDto = displayAgreementInfo.displayAgreementInfo(companyId, param.getEmployeeId(),
				closurePeriodOpt.get().getPeriod().end().year(), closurePeriodOpt.get().getPeriod().end().month());
		//setAgreeItem(itemMonthFlexResults, agreeDto);
		
		return new DPMonthResult(flexShortageDto, itemMonthResults, !itemIds.isEmpty() && !itemMonthResults.isEmpty(),
				closurePeriodOpt.get().getYearMonth().v(), formatDaily, agreeDto, monthOpt);
	}

	private List<FormatDailyDto> getFormatCode(DPMonthFlexParam param, String companyId) {
		
		if (param.getFormatCode().isEmpty()) {
			return new ArrayList<>();
		}
		
		if (param.getDailyPerformanceDto().getSettingUnit() == SettingUnitType.AUTHORITY) {
			return authorityFormatMonthlyRepository
					.getListAuthorityFormatDailyByCollection(companyId, param.getFormatCode()).stream()
					.map(x -> new FormatDailyDto(x.getDailyPerformanceFormatCode().v(), new Integer(x.getAttendanceItemId()),
							x.getColumnWidth() == null ? BigDecimal.valueOf(100l): x.getColumnWidth(), x.getDisplayOrder()))
					.collect(Collectors.toList());
		}
		
		return businessTypeFormatMonthlyRepository
				.getListBusinessTypeFormat(companyId, param.getFormatCode()).stream()
				.map(x -> new FormatDailyDto(x.getBusinessTypeCode().v(), new Integer(x.getAttendanceItemId()),
						x.getColumnWidth() == null ? BigDecimal.valueOf(100l): x.getColumnWidth(), x.getOrder()))
				.collect(Collectors.toList());
	}

	private List<Integer> getItemIds(String companyId, List<FormatDailyDto> formatDaily) {
		if (!formatDaily.isEmpty()) {
			// 対応するドメインモデル「権限別月次項目制御」を取得する
			MonthlyItemControlByAuthDto mItemAuthDto = monthlyItemControlByAuthFinder
					.getMonthlyItemControlByToUse(companyId, AppContexts.user().roles().forAttendance(), 
					formatDaily.stream().map(x-> x.getAttendanceItemId()).collect(Collectors.toList()), 1);
			
			// 取得したドメインモデル「権限別月次項目制御」の件数をチェックする
			if (mItemAuthDto != null) {
//				hasItem = true;
				// itemIds.addAll(DAFAULT_ITEM);
				return mItemAuthDto.getListDisplayAndInputMonthly().stream()
									.map(x -> x.getItemMonthlyId())
									.collect(Collectors.toList());
				// 対応する「月別実績」をすべて取得する

//				itemMonthResults = monthlyModifyQueryProcessor.initScreen(
//						new MonthlyMultiQuery(Arrays.asList(param.getEmployeeId())), itemIds,
//						closingPeriod.get().getProcessingYm(),
//						ClosureId.valueOf(closureEmploymentOptional.get().getClosureId()),
//						new ClosureDate(closingPeriod.get().getClosureDate().getClosureDay(),
//								closingPeriod.get().getClosureDate().getLastDayOfMonth()));
			}
		}
		return new ArrayList<>();
	}

//	private void setAgreeItem(List<MonthlyModifyResult> itemMonthFlexResults, AgreementInfomationDto agreeDto){
//		if(itemMonthFlexResults.isEmpty()) return;
//		else{
//			List<ItemValue> values = itemMonthFlexResults.get(0).getItems().stream()
//					.filter(x -> (x.getItemId() == -1 || x.getItemId() == -3))
//					.sorted((x, y) -> x.getItemId() - y.getItemId()).collect(Collectors.toList());
//			agreeDto.setAgreementTime36(values.get(0).getValue() != null ? convertTime(Integer.parseInt(values.get(0).getValue())) : "0:00");
//			agreeDto.setMaxTime(values.get(1).getValue() != null ? convertTime(Integer.parseInt(values.get(1).getValue())) : "0:00");
//		}
//	}
}
