package nts.uk.ctx.at.record.app.find.monthly.root.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.app.util.attendanceitem.ConvertHelper;
import nts.uk.ctx.at.shared.dom.attendance.util.item.AttendanceItemDataGate;
import nts.uk.ctx.at.shared.dom.attendance.util.item.AttendanceItemDataGate.PropType;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.ItemConst;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.totalcount.TotalCountByPeriod;

/**
 * 期間別の回数集計Dto
 * @author shuichu_ishida
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TotalCountByPeriodDto implements ItemConst, AttendanceItemDataGate {

	/** 回数集計 */
	@AttendanceItemLayout(jpPropertyName = AGGREGATE, layout = LAYOUT_A, listMaxLength = 30, indexField = DEFAULT_INDEX_FIELD_NAME)
	private List<TotalCountDto> totalCount;

	public TotalCountByPeriod toDomain(){
		return TotalCountByPeriod.of(
				ConvertHelper.mapTo(this.totalCount, c -> c.toDomain()));
	}
	
	public static TotalCountByPeriodDto from(TotalCountByPeriod domain){
		TotalCountByPeriodDto dto = new TotalCountByPeriodDto();
		if (domain != null){
			dto.setTotalCount(ConvertHelper.mapTo(domain.getTotalCountList(), c -> TotalCountDto.from(c.getValue())));;
		}
		return dto;
	}

	@Override
	public AttendanceItemDataGate newInstanceOf(String path) {
		if (AGGREGATE.equals(path)) {
			return new TotalCountDto();
		}
		return AttendanceItemDataGate.super.newInstanceOf(path);
	}

	@Override
	public int size(String path) {
		if (AGGREGATE.equals(path)) {
			return 30;
		}
		return AttendanceItemDataGate.super.size(path);
	}

	@Override
	public PropType typeOf(String path) {
		if (AGGREGATE.equals(path)) {
			return PropType.IDX_LIST;
		}
		return AttendanceItemDataGate.super.typeOf(path);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends AttendanceItemDataGate> List<T> gets(String path) {
		if (AGGREGATE.equals(path)) {
			return (List<T>) totalCount;
		}
		return AttendanceItemDataGate.super.gets(path);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends AttendanceItemDataGate> void set(String path, List<T> value) {
		if (AGGREGATE.equals(path)) {
			totalCount = (List<TotalCountDto>) value;
		}
	}
	
	
}
