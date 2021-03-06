package nts.uk.ctx.at.record.app.find.monthly.root.dto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.attendance.util.item.AttendanceItemDataGate;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.ctx.at.shared.dom.scherec.attendanceitem.converter.util.ItemConst;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ValueType;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.excessoutside.ExcessOutSideWorkEachBreakdown;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.excessoutside.ExcessOutsideWork;

/** 時間外超過 */
@NoArgsConstructor
public class ExcessOutsideWorkDto implements ItemConst, AttendanceItemDataGate {

	public final static List<Integer> LIST_FAKE_NO = Arrays.asList(11, 12, 13, 14, 15, 16, 17, 18, 19, 110, 
																	21, 22, 23, 24, 25, 26, 27, 28, 29, 210, 
																	31, 32, 33, 34, 35, 36, 37, 38, 39, 310, 
																	41, 42, 43, 44, 45, 46, 47, 48, 49, 410, 
																	51, 52, 53, 54, 55, 56, 57, 58, 59, 510);
	
	/** 超過NO: int */
	private int excessNo;

	/** 超過時間: 勤怠月間時間 */
	@Getter
	@Setter
	@AttendanceItemValue(type = ValueType.TIME)
	@AttendanceItemLayout(jpPropertyName = TIME, layout = LAYOUT_A)
	private int breakdown;

	/** 内訳NO: int */
	private int breakdownNo;

	/** FAKE NO */
	private int no;

	public ExcessOutsideWorkDto(int excessNo, int breakdownNo, Integer breakdown) {
		super();
		this.excessNo = excessNo;
		this.breakdown = breakdown;
		this.breakdownNo = breakdownNo;
//		this.no = (breakdownNo-1) * 10 + excessNo;
		this.no = calcFakeNo(excessNo, breakdownNo);
	}

	private int calcFakeNo(int excessNo, int breakdownNo) {
		return Integer.parseInt(String.valueOf(excessNo) + String.valueOf(breakdownNo));
	}
	
	private int calcBreakDownNo(int fakeNo) {
		return Integer.parseInt(String.valueOf(fakeNo).substring(1));
	}
	
	private int calcExcessNo(int fakeNo) {
		return Integer.parseInt(String.valueOf(fakeNo).substring(0, 1));
	}

	public ExcessOutsideWork toDomain() {
		return ExcessOutsideWork.of(getBreakdownNo(), getExcessNo(), new AttendanceTimeMonth(breakdown));
	}

	public static ExcessOutsideWorkDto from(ExcessOutsideWork domain) {
		return new ExcessOutsideWorkDto(domain.getExcessNo(), domain.getBreakdownNo(),
				domain.getExcessTime() == null ? 0 : domain.getExcessTime().valueAsMinutes());
	}

	public static List<ExcessOutsideWorkDto> from(Map<Integer, ExcessOutSideWorkEachBreakdown> domain) {
		List<ExcessOutsideWorkDto> result = new ArrayList<>();
		if(domain != null) {
			domain.entrySet().stream().forEach(c -> {
				c.getValue().getBreakdown().entrySet().stream().forEach(b -> {
					result.add(ExcessOutsideWorkDto.from(b.getValue()));
				});
			});
		}
		return result;
	}

	@Override
	public Optional<ItemValue> valueOf(String path) {
		if (TIME.equals(path)) {
			return Optional.of(ItemValue.builder().value(breakdown).valueType(ValueType.TIME));
		}
		return AttendanceItemDataGate.super.valueOf(path);
	}

	@Override
	public PropType typeOf(String path) {
		if (TIME.equals(path)) {
			return PropType.VALUE;
		}
		return AttendanceItemDataGate.super.typeOf(path);
	}

	@Override
	public void set(String path, ItemValue value) {
		if (TIME.equals(path)) {
			breakdown = value.valueOrDefault(0);
		}
	}

	public int getExcessNo() {
		return calcExcessNo(this.no);
	}

	public void setExcessNo(int excessNo) {
		this.excessNo = excessNo;
		this.no = calcFakeNo(this.excessNo, this.breakdownNo);
	}

	public int getBreakdownNo() {
		return calcBreakDownNo(this.no);
	}

	public void setBreakdownNo(int breakdownNo) {
		this.breakdownNo = breakdownNo;
		this.no = calcFakeNo(this.excessNo, this.breakdownNo);
	}

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
//		this.excessNo = calcExcessNo(this.no);
//		this.breakdownNo = calcBreakDownNo(this.no);
	}
}
