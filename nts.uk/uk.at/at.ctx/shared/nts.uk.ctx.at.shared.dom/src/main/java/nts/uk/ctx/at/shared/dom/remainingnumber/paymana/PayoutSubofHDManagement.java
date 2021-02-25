package nts.uk.ctx.at.shared.dom.remainingnumber.paymana;

import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.TargetSelectionAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.SeqVacationAssociationInfo;
import nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.empinfo.grantremainingdata.daynumber.ReserveLeaveRemainingDayNumber;

/**
 * 振出振休紐付け管理
 * @author HopNT	
 *
 */

@NoArgsConstructor
@Getter
public class PayoutSubofHDManagement extends AggregateRoot{

	// 社員ID
	private String sid;
	
	//逐次休暇の紐付け情報
	private SeqVacationAssociationInfo associalInfo;

	public PayoutSubofHDManagement(String sid, GeneralDate occDate, GeneralDate digestDate, Double usedDays, 
			int targetSelectionAtr) {
		super();
		this.sid = sid;
		this.associalInfo = new SeqVacationAssociationInfo(occDate, digestDate,
				new ReserveLeaveRemainingDayNumber(usedDays),
				EnumAdaptor.valueOf(targetSelectionAtr, TargetSelectionAtr.class));
	}
	
}
