package nts.uk.ctx.at.record.app.find.monthlyclosureupdate;

import java.util.List;

import lombok.Value;
import nts.uk.ctx.at.record.app.command.monthlyclosureupdate.MonthlyClosureResponse;
/**
 * 
 * @author HungTT
 *
 */

@Value
public class Kmw006aResultDto {
	private Boolean executable;
	private Integer selectClosureId;
	private List<ClosureInforDto> listInfor;
	private MonthlyClosureResponse screenParams;
}
