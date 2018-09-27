package nts.uk.ctx.at.record.app.find.monthlyclosureupdate;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.record.app.command.monthlyclosureupdate.MonthlyClosureResponse;
/**
 * 
 * @author HungTT
 *
 */
@AllArgsConstructor
@Data
public class Kmw006aResultDto {
	private Boolean executable;
	private Integer selectClosureId;
	private List<ClosureInforDto> listInfor;
	private MonthlyClosureResponse screenParams;
}
