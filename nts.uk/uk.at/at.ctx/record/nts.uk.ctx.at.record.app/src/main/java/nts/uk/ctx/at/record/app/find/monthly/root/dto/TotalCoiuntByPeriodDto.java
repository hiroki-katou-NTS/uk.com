package nts.uk.ctx.at.record.app.find.monthly.root.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.monthly.totalcount.TotalCountByPeriod;
import nts.uk.ctx.at.shared.app.util.attendanceitem.ConvertHelper;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemLayout;

/**
 * 期間別の回数集計Dto
 * @author shuichu_ishida
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TotalCoiuntByPeriodDto {

	/** 回数集計 */
	@AttendanceItemLayout(jpPropertyName = "回数集計", layout = "A", listMaxLength = 30, indexField = "totalCountNo")
	private List<TotalCountDto> totalCount;

	public TotalCountByPeriod toDomain(){
		return TotalCountByPeriod.of(
				ConvertHelper.mapTo(this.totalCount, c -> c.toDomain()));
	}
	
	public static TotalCoiuntByPeriodDto from(TotalCountByPeriod domain){
		TotalCoiuntByPeriodDto dto = new TotalCoiuntByPeriodDto();
		if (domain != null){
			ConvertHelper.mapTo(domain.getTotalCountList(), c -> TotalCountDto.from(c.getValue()));
		}
		return dto;
	}
}
