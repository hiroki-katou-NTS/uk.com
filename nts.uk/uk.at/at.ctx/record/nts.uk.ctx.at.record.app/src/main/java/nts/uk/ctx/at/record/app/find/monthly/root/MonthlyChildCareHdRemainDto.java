package nts.uk.ctx.at.record.app.find.monthly.root;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.app.find.monthly.root.common.ClosureDateDto;
import nts.uk.ctx.at.record.app.find.monthly.root.common.DatePeriodDto;
import nts.uk.ctx.at.record.app.find.monthly.root.common.MonthlyItemCommon;
import nts.uk.ctx.at.shared.app.util.attendanceitem.ConvertHelper;
import nts.uk.ctx.at.shared.dom.attendance.util.item.AttendanceItemDataGate;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.maxdata.UsedTimes;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.childcare.ChildCareNurseUsedNumber;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.grantremainingdata.remainingnumber.DayNumberOfRemain;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.grantremainingdata.remainingnumber.TimeOfRemain;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.grantremainingdata.usenumber.DayNumberOfUse;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.grantremainingdata.usenumber.TimeOfUse;
import nts.uk.ctx.at.shared.dom.scherec.attendanceitem.converter.util.ItemConst;
import nts.uk.ctx.at.shared.dom.scherec.attendanceitem.converter.util.AttendanceItemUtil.AttendanceItemType;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemRoot;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ValueType;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.ClosureStatus;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.childcare.ChildcareRemNumEachMonth;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.childcarenurse.ChildCareNurseRemainingNumber;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.childcarenurse.ChildCareNurseUsedInfo;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.childcarenurse.ChildcareNurseRemNumEachMonth;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;
import nts.uk.shr.com.time.calendar.date.ClosureDate;

/** 子の看護月別残数データ */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@AttendanceItemRoot(rootName = ItemConst.MONTHLY_CHILD_CARE_HD_REMAIN_NAME, itemType = AttendanceItemType.MONTHLY_ITEM)
public class MonthlyChildCareHdRemainDto extends MonthlyItemCommon {

	/***/
	private static final long serialVersionUID = 1L;

	/** 社員ID: 社員ID */
	private String employeeId;

	/** 年月: 年月 */
	private YearMonth ym;

	/** 締めID: 締めID */
	// @AttendanceItemValue
	// @AttendanceItemLayout(jpPropertyName = "締めID", layout = "A")
	private int closureID = 1;

	/** 締め日: 日付 */
	// @AttendanceItemLayout(jpPropertyName = "締め日", layout = "B")
	private ClosureDateDto closureDate;

	private ClosureStatus closureStatus = ClosureStatus.UNTREATED;

	/** 締め期間: 期間 */
	@AttendanceItemLayout(jpPropertyName = PERIOD, layout = LAYOUT_A)
	private DatePeriodDto datePeriod;

	/** 使用日数 */
	@AttendanceItemLayout(jpPropertyName = USAGE + DAYS, layout = LAYOUT_B)
	@AttendanceItemValue(type = ValueType.COUNT_WITH_DECIMAL)
	private Double usedDays;

	/** 使用日数付与前 */
	@AttendanceItemLayout(jpPropertyName = USAGE + DAYS + BEFORE, layout = LAYOUT_C)
	@AttendanceItemValue(type = ValueType.COUNT_WITH_DECIMAL)
	private Double usedDaysBefore;

	/** 使用日数付与後 */
	@AttendanceItemLayout(jpPropertyName = USAGE + DAYS + AFTER, layout = LAYOUT_D)
	@AttendanceItemValue(type = ValueType.COUNT_WITH_DECIMAL)
	private Double usedDaysAfter;

	/** 使用時間 */
	@AttendanceItemLayout(jpPropertyName = USAGE + TIME, layout = LAYOUT_E)
	@AttendanceItemValue(type = ValueType.TIME)
	private Integer usedMinutes;

	/** 使用時間付与前 */
	@AttendanceItemLayout(jpPropertyName = USAGE + TIME + BEFORE, layout = LAYOUT_F)
	@AttendanceItemValue(type = ValueType.TIME)
	private Integer usedMinutesBefore;

	/** 使用時間付与後 */
	@AttendanceItemLayout(jpPropertyName = USAGE + TIME + AFTER, layout = LAYOUT_G)
	@AttendanceItemValue(type = ValueType.TIME)
	private Integer usedMinutesAfter;

	@Override
	public String employeeId() {
		return employeeId;
	}

	/**
	 * ドメインへ変換
	 */
	@Override
	public ChildcareRemNumEachMonth toDomain(String employeeId, YearMonth ym, int closureID, ClosureDateDto closureDate) {

		/** 本年使用数 */
		ChildCareNurseUsedInfo thisYearUsedInfo
			= ChildCareNurseUsedInfo.of(
					/** 使用数 */
					ChildCareNurseUsedNumber.of(
							/** 日数 */
							toDayNumberOfUse(usedDaysBefore),
							/** 時間 */
							toUsedMinutes(usedMinutesBefore)),
					/** 時間休暇使用回数 */
					new UsedTimes(0), // 未実装
					/** 時間休暇使用日数 */
					new UsedTimes(0) ); // 未実装

		/** 合計使用数 */
		ChildCareNurseUsedInfo usedInfo
			= ChildCareNurseUsedInfo.of(
					/** 使用数 */
					ChildCareNurseUsedNumber.of(
							/** 日数 */
							toDayNumberOfUse(usedDaysAfter),
							/** 時間 */
							toUsedMinutes(usedMinutesAfter)),
					/** 時間休暇使用回数 */
					new UsedTimes(0), // 未実装
					/** 時間休暇使用日数 */
					new UsedTimes(0) ); // 未実装

		/** 本年残数 */
		ChildCareNurseRemainingNumber thisYearRemainNumber
			/** 残数 */
			= ChildCareNurseRemainingNumber.of(
					/** 日数 */
					toDayNumberOfRemain(0.0), // 未実装
					/** 時間 */
					toTimeOfRemain(0)); // 未実装

		/** 翌年使用数 */
		Optional<ChildCareNurseUsedInfo> nextYearUsedInfo
			= Optional.of(ChildCareNurseUsedInfo.of(
					/** 使用数 */
					ChildCareNurseUsedNumber.of(
							/** 日数 */
							toDayNumberOfUse(usedDaysAfter),
							/** 時間 */
							toUsedMinutes(usedMinutesAfter)),
					/** 時間休暇使用回数 */
					new UsedTimes(0), // 未実装
					/** 時間休暇使用日数 */
					new UsedTimes(0) )); // 未実装

		/** 翌年残数 */
		Optional<ChildCareNurseRemainingNumber> nextYearRemainNumber
			/** 残数 */
			= Optional.of(ChildCareNurseRemainingNumber.of(
					/** 日数 */
					toDayNumberOfRemain(0.0), // 未実装
					/** 時間 */
					toTimeOfRemain(0))); // 未実装

		return new ChildcareRemNumEachMonth(
				employeeId, /** 社員ID */
				ym, /** 年月 */
				ConvertHelper.getEnum(closureID, ClosureId.class), /** 締めID */
				new ClosureDate(closureDate.getClosureDay(), closureDate.getLastDayOfMonth()), /** 締め日付 */
				ClosureStatus.UNTREATED, // 締め処理状態←未締め
				/** 子の看護休暇月別残数データ */
				ChildcareNurseRemNumEachMonth.of(
						thisYearUsedInfo, /** 本年使用数 */
						usedInfo, /** 合計使用数 */
						thisYearRemainNumber, /** 本年残数 */
						nextYearUsedInfo, /** 翌年使用数 */
						nextYearRemainNumber) /** 翌年残数 */
				);
	}

	private DayNumberOfUse toDayNumberOfUse(Double number){
		return new DayNumberOfUse(number == null ? 0.0 : number);
	}

	private Optional<TimeOfUse> toUsedMinutes(Integer minutes){
		return minutes == null ? Optional.empty() : Optional.of(new TimeOfUse(minutes));
	}

	private DayNumberOfRemain toDayNumberOfRemain(Double number){
		return new DayNumberOfRemain(number == null ? 0.0 : number);
	}

	private Optional<TimeOfRemain> toTimeOfRemain(Integer minutes){
		return minutes == null ? Optional.empty() : Optional.of(new TimeOfRemain(minutes));
	}

	@Override
	public YearMonth yearMonth() {
		return ym;
	}

	public static MonthlyChildCareHdRemainDto from(ChildcareRemNumEachMonth domain){
		MonthlyChildCareHdRemainDto dto = new MonthlyChildCareHdRemainDto();
		if (domain != null) {
			dto.setEmployeeId(domain.getEmployeeId());
			dto.setYm(domain.getYearMonth());
			dto.setClosureID(domain.getClosureId().value);
			dto.setClosureStatus(domain.getClosureStatus());
			dto.setClosureDate(new ClosureDateDto(domain.getClosureDate().getClosureDay().v(), domain.getClosureDate().getLastDayOfMonth()));
			dto.setUsedDays(domain.getRemNumEachMonth().getUsedInfo().getUsedNumber().getUsedDay().v());
			dto.setUsedDaysAfter(domain.getRemNumEachMonth().getNextYearUsedInfo().map(mapper->mapper.getUsedNumber().getUsedDay().v()).orElse(0.0));
			dto.setUsedDaysBefore(domain.getRemNumEachMonth().getThisYearUsedInfo().getUsedNumber().getUsedDay().v());
			dto.setUsedMinutes(domain.getRemNumEachMonth().getUsedInfo().getUsedNumber().getUsedTimes().map(mapper->mapper.v()).orElse(0));
			dto.setUsedMinutesAfter(domain.getRemNumEachMonth().getNextYearUsedInfo().map(mapper->mapper.getUsedNumber().getUsedTimes().map(mapper2->mapper2.v()).orElse(0)).orElse(0));
			dto.setUsedMinutesBefore(domain.getRemNumEachMonth().getThisYearUsedInfo().getUsedNumber().getUsedTimes().map(mapper->mapper.v()).orElse(0));
			dto.exsistData();
		}
		return dto;
	}@Override
	public Optional<ItemValue> valueOf(String path) {
		switch (path) {
		case (USAGE + DAYS):
			return Optional.of(ItemValue.builder().value(usedDays).valueType(ValueType.COUNT_WITH_DECIMAL));
		case (USAGE + DAYS + BEFORE):
			return Optional.of(ItemValue.builder().value(usedDaysBefore).valueType(ValueType.COUNT_WITH_DECIMAL));
		case (USAGE + DAYS + AFTER):
			return Optional.of(ItemValue.builder().value(usedDaysAfter).valueType(ValueType.COUNT_WITH_DECIMAL));
		case (USAGE + TIME):
			return Optional.of(ItemValue.builder().value(usedMinutes).valueType(ValueType.TIME));
		case (USAGE + TIME + BEFORE):
			return Optional.of(ItemValue.builder().value(usedMinutesBefore).valueType(ValueType.TIME));
		case (USAGE + TIME + AFTER):
			return Optional.of(ItemValue.builder().value(usedMinutesAfter).valueType(ValueType.TIME));
		default:
			break;
		}
		return super.valueOf(path);
	}
	@Override
	public AttendanceItemDataGate newInstanceOf(String path) {
		if (PERIOD.equals(path)) {
			return new DatePeriodDto();
		}
		return super.newInstanceOf(path);
	}
	@Override
	public Optional<AttendanceItemDataGate> get(String path) {
		if (PERIOD.equals(path)) {
			return Optional.ofNullable(datePeriod);
		}
		return super.get(path);
	}
	@Override
	public PropType typeOf(String path) {
		switch (path) {
		case (USAGE + DAYS):
		case (USAGE + DAYS + BEFORE):
		case (USAGE + DAYS + AFTER):
		case (USAGE + TIME):
		case (USAGE + TIME + BEFORE):
		case (USAGE + TIME + AFTER):
			return PropType.VALUE;
		default:
			break;
		}
		return super.typeOf(path);
	}
	@Override
	public void set(String path, ItemValue value) {
		switch (path) {
		case (USAGE + DAYS):
			usedDays = value.valueOrDefault(null); break;
		case (USAGE + DAYS + BEFORE):
			usedDaysBefore = value.valueOrDefault(null); break;
		case (USAGE + DAYS + AFTER):
			usedDaysAfter = value.valueOrDefault(null); break;
		case (USAGE + TIME):
			usedMinutes = value.valueOrDefault(null); break;
		case (USAGE + TIME + BEFORE):
			usedMinutesBefore = value.valueOrDefault(null); break;
		case (USAGE + TIME + AFTER):
			usedMinutesAfter = value.valueOrDefault(null); break;
		default:
			break;
		}
	}
	@Override
	public void set(String path, AttendanceItemDataGate value) {
		if (PERIOD.equals(path)) {
			datePeriod = (DatePeriodDto) value;
		}
	}
	@Override
	public boolean isRoot() {
		return true;
	}
	@Override
	public String rootName() {
		return MONTHLY_CHILD_CARE_HD_REMAIN_NAME;
	}
}
