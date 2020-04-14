package nts.uk.ctx.at.schedule.app.find.schedule.employeeinfo.rank;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.schedule.app.query.RankDto;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RankDivisionDto {
	public List<RankDto> listRankDto;
	
	public List<EmployeeRankDto> listEmpRankDto;

}
