package nts.uk.ctx.at.record.app.find.monthly.root.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import lombok.Data;
import nts.uk.ctx.at.record.dom.monthly.excessoutside.ExcessOutSideWorkEachBreakdown;
import nts.uk.ctx.at.record.dom.monthly.excessoutside.ExcessOutsideWork;
import nts.uk.ctx.at.shared.dom.attendance.util.ItemConst;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ValueType;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;

@Data
/** 時間外超過 */
public class ExcessOutsideWorkDto implements ItemConst {

	/** 超過NO: int */
	private int excessNo;

	/** 超過時間: 勤怠月間時間 */
	@AttendanceItemValue(type = ValueType.INTEGER)
	@AttendanceItemLayout(jpPropertyName = TIME, layout = LAYOUT_A)
	private Integer breakdown;

	/** 内訳NO: int */
	private int breakdownNo;

	/** FAKE NO */
	private int no;

	public ExcessOutsideWorkDto(int excessNo, int breakdownNo, Integer breakdown) {
		super();
		this.excessNo = excessNo;
		this.breakdown = breakdown;
		this.breakdownNo = breakdownNo;
		this.no = (breakdownNo-1) * 10 + excessNo;
	}

	public ExcessOutsideWork toDomain() {
		//TODO: need check
		return ExcessOutsideWork.of((no / 10) + 1, (no % 10) + 1,
				breakdown == null ? null : new AttendanceTimeMonth(breakdown));
	}

	public static ExcessOutsideWorkDto from(ExcessOutsideWork domain) {
		return new ExcessOutsideWorkDto(domain.getExcessNo(), domain.getBreakdownNo(),
				domain.getExcessTime() == null ? null : domain.getExcessTime().valueAsMinutes());
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
}
