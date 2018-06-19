package nts.uk.ctx.at.shared.dom.remainingnumber.paymana;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.CompensatoryDayoffDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.ManagementDataDaysAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.ManagementDataRemainUnit;

/**
 * 振休管理データ
 * @author HopNT
 *
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SubstitutionOfHDManagementData extends AggregateRoot {

	// 振休データID
	private String subOfHDID;
	
	private String cid;
	
	// 社員ID	
	private String sID;
	
	// 振休日
	private CompensatoryDayoffDate holidayDate;
	
	// 必要日数
	private ManagementDataDaysAtr requiredDays;	
	
	// 未相殺日数
	private ManagementDataRemainUnit remainDays;
	
	public SubstitutionOfHDManagementData(String id, String cid, String sid, boolean unknowDate, GeneralDate dayoffDate,
			Double requiredDays, Double remainDays) {
		this.subOfHDID = id;
		this.cid = cid;
		this.sID = sid;
		this.holidayDate = new CompensatoryDayoffDate(unknowDate, Optional.ofNullable(dayoffDate));
		this.requiredDays = new ManagementDataDaysAtr(requiredDays);
		this.remainDays = new ManagementDataRemainUnit(remainDays);
	}
	
	public void setRemainsDay(Double remainNumber){
		this.remainDays = new ManagementDataRemainUnit(remainNumber);
	}
	
	
}
