package nts.uk.ctx.at.record.app.find.monthlyclosureupdate;

import java.util.List;

import lombok.Value;
import nts.arc.time.GeneralDateTime;
/**
 * 
 * @author HungTT
 *
 */

@Value
public class Kmw006aResultDto {
	private boolean executable;
	private Integer targetYm;
	private GeneralDateTime executionDate;
	private int selectClosureId;
	private List<ClosureInforDto> listInfor;
}
