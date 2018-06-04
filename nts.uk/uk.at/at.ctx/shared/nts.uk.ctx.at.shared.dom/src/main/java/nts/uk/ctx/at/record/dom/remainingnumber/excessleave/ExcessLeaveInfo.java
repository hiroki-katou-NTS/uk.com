package nts.uk.ctx.at.record.dom.remainingnumber.excessleave;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.shared.dom.bonuspay.enums.UseAtr;

/**
 * domain 超過有休基本情報
 * 
 * @author Hop.NT
 *
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ExcessLeaveInfo extends AggregateRoot {

	private String cid;

	// 社員ID
	private String sID;

	// 使用区分
	private UseAtr useAtr;

	// 発生単位
	private OccurrenceUnit occurrenceUnit;

	// 精算方法
	private PaymentMethod paymentMethod;

	public ExcessLeaveInfo(String cid, String sid, Integer useCls, Integer occ, Integer paymentMethod) {
		this.cid = cid;
		this.sID = sid;
		if (useCls == null) {
			this.useAtr = UseAtr.NOT_USE;
		} else {
			this.useAtr = EnumAdaptor.valueOf(useCls, UseAtr.class);
		}
		if (occ == null) {
			this.occurrenceUnit = new OccurrenceUnit(0);
		} else {
			this.occurrenceUnit = new OccurrenceUnit(occ);
		}
		if (paymentMethod == null) {
			this.paymentMethod = PaymentMethod.VACATION_OCCURRED;
		} else {
			this.paymentMethod = EnumAdaptor.valueOf(paymentMethod, PaymentMethod.class);
		}
	}

	public static ExcessLeaveInfo createDomain(String cid, String sid, BigDecimal useCls, BigDecimal occ,
			BigDecimal paymentMethod) {
		return new ExcessLeaveInfo(cid, sid, toInteger(useCls), toInteger(occ), toInteger(paymentMethod));
	}

	private static Integer toInteger(BigDecimal bigNumber) {
		return bigNumber != null ? bigNumber.intValue() : null;
	}
}
