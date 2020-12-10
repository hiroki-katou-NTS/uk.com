package nts.uk.screen.at.app.dailyperformance.correction.mobile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.function.dom.dailyperformanceformat.AuthoritySFomatMonthly;
import nts.uk.ctx.at.function.dom.dailyperformanceformat.BusinessTypeSFormatMonthly;
import nts.uk.ctx.at.function.dom.dailyperformanceformat.repository.AuthorityFormatMonthlySRepository;
import nts.uk.ctx.at.function.dom.dailyperformanceformat.repository.BusinessTypeSFormatMonthlyRepository;
import nts.uk.ctx.at.record.app.find.monthly.root.MonthlyRecordWorkDto;
import nts.uk.ctx.at.record.dom.workrecord.operationsetting.SettingUnitType;
import nts.uk.ctx.at.shared.app.find.scherec.monthlyattditem.MonthlyItemControlByAuthDto;
import nts.uk.ctx.at.shared.app.find.scherec.monthlyattditem.MonthlyItemControlByAuthFinder;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.AttendanceItemUtil;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.adapter.attendanceitemname.AttItemName;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattendanceitem.service.CompanyMonthlyItemService;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;
import nts.uk.screen.at.app.dailyperformance.correction.DailyPerformanceScreenRepo;
import nts.uk.screen.at.app.dailyperformance.correction.dto.OperationOfDailyPerformanceDto;
import nts.uk.screen.at.app.monthlyperformance.correction.dto.FormatDailyDto;
import nts.uk.screen.at.app.monthlyperformance.correction.query.MonthlyModifyQueryProcessor;
import nts.uk.screen.at.app.monthlyperformance.correction.query.MonthlyModifyResult;
import nts.uk.screen.at.app.monthlyperformance.correction.query.MonthlyMultiQuery;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.time.calendar.date.ClosureDate;

@Stateless
public class MonthlyPerfomanceMob {

	@Inject
	private MonthlyModifyQueryProcessor monModifyQueryPro;
    @Inject
    private MonthlyItemControlByAuthFinder monItemControlByAuth;
    @Inject
	private DailyPerformanceScreenRepo repo;
	@Inject
	private CompanyMonthlyItemService companyMonthlyItemService;
	@Inject
	private AuthorityFormatMonthlySRepository authMFormatMonthlyRepo;
	@Inject
	private BusinessTypeSFormatMonthlyRepository bussSFormatMonthlyRepo;
    /**
     * F：月別実績の参照を起動する
     * @param param
     * @return 月別実績
     */
	public List<MonthlyPerData> getMonthlyPerData(MonthlyPerParamMob param){
		String companyId = AppContexts.user().companyId();
		//get フォーマット種類
		OperationOfDailyPerformanceDto dailyPerDto = repo.findOperationOfDailyPerformance();
		//get フォーマット
		List<FormatDailyDto> formatDaily = this.getFormatCode(param.getFormatCode(),
				dailyPerDto.getSettingUnit(), companyId);
		//get ItemId
		List<Integer> itemIds = this.getItemIds(companyId, formatDaily);
		GeneralDate date = param.getClosureDate();
		Boolean lastDayOfMonth = false;
		if(date.addDays(1).day() == 1) {
			lastDayOfMonth = true;
		}
		Integer a = Integer.valueOf(date.day());
		//get 月別実績
		Optional<MonthlyRecordWorkDto> monthOpt =  monModifyQueryPro.getDataMonthAll(
				new MonthlyMultiQuery(Arrays.asList(param.getEmployeeId())), 
				itemIds.stream().collect(Collectors.toList()),
				new YearMonth(param.getYearMonth()),
				ClosureId.valueOf(param.getClosureId()),
				new ClosureDate(a, lastDayOfMonth));
		//convert data
		List<MonthlyModifyResult> mResult = monthOpt.isPresent() ? AttendanceItemUtil.toItemValues(Arrays.asList(monthOpt.get()),
				itemIds.stream().collect(Collectors.toList()),
				AttendanceItemUtil.AttendanceItemType.MONTHLY_ITEM).entrySet().stream().map(record -> {
					return MonthlyModifyResult.builder().items(record.getValue())
							.employeeId(record.getKey().getEmployeeId()).yearMonth(record.getKey().getYearMonth().v())
							.closureId(record.getKey().getClosureID()).closureDate(record.getKey().getClosureDate())
							.workDatePeriod(record.getKey().getAttendanceTime().getDatePeriod().toDomain())
							.version(record.getKey().getAffiliation().getVersion()).completed();
				}).collect(Collectors.toList()) : new ArrayList<>();
		if(mResult.isEmpty()) return null;
		List<ItemValue> items = mResult.get(0).getItems();
		List<Integer> lstId = items.stream().map(c -> c.getItemId()).collect(Collectors.toList());
		//勤怠項目に対応する名称を生成する
		List<AttItemName> lstName = companyMonthlyItemService.getMonthlyItems(companyId, Optional.empty(), lstId, null);
		List<MonthlyPerData> data = new ArrayList<>();
		for(ItemValue item : items){
			data.add(new MonthlyPerData(item.getItemId(), this.findName(lstName, item.getItemId()),
					item.getValue(), item.getValueType() != null ? item.getValueType().value : null,
							this.findOrder(formatDaily, item.getItemId())));
		}
		//【ソート】・パラメータ「日別実績の修正の状態．表示する項目．月次の勤怠項目一覧．並び順」（ASC）
		Collections.sort(data, Comparator.comparing(MonthlyPerData::getOrder));
		return data;
	}
	/**
	 * 月別実績のフォーマットの設定を取得する
	 * @param formatCode
	 * @param settingUnit
	 * @param companyId
	 * @return
	 */
	public List<FormatDailyDto> getFormatCode(Collection<String> formatCode, SettingUnitType settingUnit, String companyId) {
		
		if (formatCode.isEmpty()) {
			return new ArrayList<>();
		}
		
		if (settingUnit == SettingUnitType.AUTHORITY) {//権限の場合
			List<AuthoritySFomatMonthly> authMonthly = authMFormatMonthlyRepo.getListAuthorityFormatDaily(companyId, formatCode);
			return authMonthly.stream().map(c -> new FormatDailyDto(null, 
					c.getAttendanceItemId(), c.getColumnWidthTable(), c.getDisplayOrder())).collect(Collectors.toList());
		}
		//勤務種別の場合
		List<BusinessTypeSFormatMonthly> bussMonthly = bussSFormatMonthlyRepo.getListBusinessTypeFormat(companyId, formatCode);
		return bussMonthly.stream().map(c -> new FormatDailyDto(c.getBusinessTypeCode().v(),
				c.getAttendanceItemId(), null, c.getOrder())).collect(Collectors.toList());
	}
	/**
	 * get Item ID
	 * @param companyId
	 * @param formatDaily
	 * @return
	 */
	private List<Integer> getItemIds(String companyId, List<FormatDailyDto> formatDaily) {
		if (!formatDaily.isEmpty()) {
			// 対応するドメインモデル「権限別月次項目制御」を取得する
			MonthlyItemControlByAuthDto mItemAuthDto = monItemControlByAuth
					.getMonthlyItemControlByToUse(companyId, AppContexts.user().roles().forAttendance(), 
					formatDaily.stream().map(x-> x.getAttendanceItemId()).collect(Collectors.toList()), 1);
			
			// 取得したドメインモデル「権限別月次項目制御」の件数をチェックする
			if (mItemAuthDto != null) {
				return mItemAuthDto.getListDisplayAndInputMonthly().stream()
									.map(x -> x.getItemMonthlyId())
									.collect(Collectors.toList());
			}
		}
		return new ArrayList<>();
	}
	//find 勤怠項目の名称
	private String findName(List<AttItemName> lstName, Integer itemId){
		for(AttItemName name : lstName){
			if(name.getAttendanceItemId() == itemId) return name.getAttendanceItemName();
		}
		return null;
	}
	//find order of 勤怠項目
	private Integer findOrder(List<FormatDailyDto> formatDaily, Integer itemId){
		for(FormatDailyDto format : formatDaily){
			if(format.getAttendanceItemId().equals(itemId)) return format.getOrder();
		}
		return null;
	}
}
