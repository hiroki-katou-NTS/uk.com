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
	/** List<ランク> **/ 
	public List<RankDto> listRankDto;
	
	/** List<社員ランク> **/
	public List<EmployeeRankDto> listEmpRankDto;

}
