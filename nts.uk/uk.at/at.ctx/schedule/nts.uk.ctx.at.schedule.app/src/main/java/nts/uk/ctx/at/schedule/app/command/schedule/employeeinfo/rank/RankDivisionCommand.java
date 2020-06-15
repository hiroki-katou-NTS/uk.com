package nts.uk.ctx.at.schedule.app.command.schedule.employeeinfo.rank;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * 
 * @author hieult
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RankDivisionCommand {
	/** ランクコード **/
	private String rankCd;
	/** List 社員ID **/
	private List<String> listEmpId;
}
