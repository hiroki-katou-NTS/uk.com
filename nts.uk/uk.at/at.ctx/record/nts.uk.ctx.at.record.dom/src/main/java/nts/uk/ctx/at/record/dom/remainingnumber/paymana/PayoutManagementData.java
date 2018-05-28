package nts.uk.ctx.at.record.dom.remainingnumber.paymana;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.remainingnumber.base.CompensatoryDayoffDate;
import nts.uk.ctx.at.record.dom.remainingnumber.base.DigestionAtr;
import nts.uk.ctx.at.record.dom.remainingnumber.base.HolidayAtr;
import nts.uk.ctx.at.record.dom.remainingnumber.base.ManagementDataDaysAtr;
import nts.uk.ctx.at.record.dom.remainingnumber.base.ManagementDataRemainUnit;

/**
 * 振出管理データ
 * @author HopNT
 *
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class PayoutManagementData extends AggregateRoot {
	
	// 振出データID
	private String payoutId;
	
	private String cID;
	
	// 社員ID
	private String sID;
	
	// 振出日
	private CompensatoryDayoffDate payoutDate;
	
	// 使用期限日
	private GeneralDate expiredDate;
	
	// 法定内外区分
	private HolidayAtr lawAtr;
	
	// 発生日数
	private ManagementDataDaysAtr occurredDays;
	
	// 未使用日数	
	private ManagementDataRemainUnit unUsedDays;
	
	// 振休消化区分
	private DigestionAtr stateAtr;
	
	// 消滅日
	public GeneralDate disapearDate;
	
	private GeneralDate dayoffDate;
	
	public PayoutManagementData(String payoutId,String cid, String sid, boolean unknowDate, GeneralDate dayoffDate, GeneralDate expiredDate, int lawId,
			Double occurredDays, Double unUsedDays, int stateAtr){
		this.payoutId = payoutId;
		this.cID = cid;
		this.sID = sid;
		this.payoutDate = new CompensatoryDayoffDate(unknowDate, Optional.ofNullable(dayoffDate));
		this.expiredDate = expiredDate;
		this.lawAtr = EnumAdaptor.valueOf(lawId, HolidayAtr.class);
		this.occurredDays = new ManagementDataDaysAtr(occurredDays);
		this.unUsedDays = new ManagementDataRemainUnit(unUsedDays);
		this.stateAtr = EnumAdaptor.valueOf(stateAtr, DigestionAtr.class);
		this.dayoffDate = dayoffDate;
	}
	
	public PayoutManagementData(String payoutId,String cid, String sid, boolean unknowDate, GeneralDate dayoffDate, GeneralDate expiredDate, int lawId,
			Double occurredDays, Double unUsedDays, int stateAtr, GeneralDate disapearDate){
		this.payoutId = payoutId;
		this.cID = cid;
		this.sID = sid;
		this.payoutDate = new CompensatoryDayoffDate(unknowDate, Optional.ofNullable(dayoffDate));
		this.expiredDate = expiredDate;
		this.lawAtr = EnumAdaptor.valueOf(lawId, HolidayAtr.class);
		this.occurredDays = new ManagementDataDaysAtr(occurredDays);
		this.unUsedDays = new ManagementDataRemainUnit(unUsedDays);
		this.stateAtr = EnumAdaptor.valueOf(stateAtr, DigestionAtr.class);
		this.disapearDate = disapearDate;
	}
	
	public void setRemainNumber(Double remain){
		this.unUsedDays = new ManagementDataRemainUnit(remain);
	}
	
	public void setStateAtr(int stateAtr){
		this.stateAtr = EnumAdaptor.valueOf(stateAtr, DigestionAtr.class);
	}
}
