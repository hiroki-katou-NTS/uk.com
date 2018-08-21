package nts.uk.ctx.at.function.app.command.alarm.checkcondition;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.multimonth.doevent.MulMonCheckCondDomainEventDto;

/**
 * 
 * @author Hiep.TH
 *
 */
@Getter
@Setter
@NoArgsConstructor
public class MulMonCheckCondCommand {

	/** 複数月のﾁｪｯｸ条件 */
	List<MulMonCheckCondDomainEventDto> listMulMonCheckConds = new ArrayList<>();

	public MulMonCheckCondCommand(List<MulMonCheckCondDomainEventDto> listMulMonCheckConds) {
		super();
		this.listMulMonCheckConds = listMulMonCheckConds;
	}
}
