package nts.uk.ctx.at.record.app.find.monthly.root.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.monthly.totalcount.TotalCountByPeriod;
import nts.uk.ctx.at.shared.app.util.attendanceitem.ConvertHelper;
import nts.uk.ctx.at.shared.dom.attendance.util.ItemConst;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemLayout;

/**
 * 期間別の回数集計Dto
 * @author shuichu_ishida
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TotalCountByPeriodDto implements ItemConst {

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
}
