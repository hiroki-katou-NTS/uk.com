package nts.uk.ctx.at.record.dom.remainingnumber.excessleave;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.shared.dom.bonuspay.enums.UseAtr;

/**
 * domain 超過有休基本情報
 * @author Hop.NT
 *
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ExcessLeaveInfo extends AggregateRoot{
	
	private String cid;
	// 社員ID
	private String sID;
	
	// 使用区分
	private UseAtr useAtr;
	
	// 発生単位
	private OccurrenceUnit occurrenceUnit;
	
	
	// 精算方法
	private PaymentMethod paymentMethod;
	
	public ExcessLeaveInfo(String cid, String sid, int useCls, int occ, int paymentMethod){
		this.cid = cid;
		this.sID = sid;
		this.useAtr = EnumAdaptor.valueOf(useCls, UseAtr.class);
		this.occurrenceUnit = new OccurrenceUnit(occ);
		this.paymentMethod = EnumAdaptor.valueOf(paymentMethod, PaymentMethod.class);
	}
}
