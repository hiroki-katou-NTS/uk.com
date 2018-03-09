package nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.fixedcheckitem.checkprincipalunconfirm.checkconfirm;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDate;
@Getter
@Setter
@NoArgsConstructor
public class StateConfirm {
	GeneralDate date;
	boolean state;
	public StateConfirm(GeneralDate date, boolean state) {
		super();
		this.date = date;
		this.state = state;
	}

}
