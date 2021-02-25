package nts.uk.ctx.at.shared.dom.remainingnumber.excessleave;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.enums.UseAtr;

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

	public static ExcessLeaveInfo createDomainforcps013(String cid, String sid, BigDecimal useCls, BigDecimal occ,
			BigDecimal paymentMethod) {
		
		ExcessLeaveInfo domain = new ExcessLeaveInfo();
		Integer useCls1 = toInteger(useCls);
		Integer occ1 = toInteger(occ);
		Integer paymentMethod1 = toInteger(paymentMethod);
		
		domain.cid = cid;
		domain.sID = sid;
		
		try {
			domain.useAtr = EnumAdaptor.valueOf(useCls1, UseAtr.class);
		} catch (Exception e) {
			domain.useAtr = UseAtr.USE;
		}
		
		if (occ == null) {
			domain.occurrenceUnit = new OccurrenceUnit(0);
		} else {
			domain.occurrenceUnit = new OccurrenceUnit(occ1);
		}
		
		try {
			domain.paymentMethod = EnumAdaptor.valueOf(paymentMethod1, PaymentMethod.class);
		} catch (Exception e) {
			domain.paymentMethod = PaymentMethod.VACATION_OCCURRED;
		}
		
		return domain;
	}
}
