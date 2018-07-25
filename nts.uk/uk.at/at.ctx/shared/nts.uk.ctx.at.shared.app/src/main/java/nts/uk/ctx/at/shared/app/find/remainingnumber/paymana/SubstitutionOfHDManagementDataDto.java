package nts.uk.ctx.at.shared.app.find.remainingnumber.paymana;

import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.paymana.SubstitutionOfHDManagementData;

@NoArgsConstructor
@Getter
public class SubstitutionOfHDManagementDataDto{

	// 振休データID
	private String subOfHDID;
	
	private String cid;
	
	// 社員ID	
	private String sID;
	
	// 年月日
	private GeneralDate dayoffDate;
	
	// 必要日数
	private Double requiredDays;
	
	private boolean isLinked;
	
	// 未相殺日数
	private Double remainDays;
		
	private SubstitutionOfHDManagementDataDto(String subOfHDID, String cid, String sID,	GeneralDate dayoffDate, Double required, Double remainDays) {
		this.subOfHDID = subOfHDID;
		this.cid = cid;
		this.sID = sID;
		this.dayoffDate = dayoffDate;
		this.requiredDays = required;
		this.remainDays = remainDays;
	}
	
	public static SubstitutionOfHDManagementDataDto createFromDomain(SubstitutionOfHDManagementData domain){
		return new SubstitutionOfHDManagementDataDto(domain.getSubOfHDID(), domain.getCid(), domain.getSID(), domain.getHolidayDate().getDayoffDate().isPresent()
						? domain.getHolidayDate().getDayoffDate().get() : null, domain.getRequiredDays().v(), domain.getRemainDays().v());
	}
	
	public void setLinked(boolean isLinked){
		this.isLinked = isLinked;
	}
	
	public void setremainDays(Double remainDays){
		this.remainDays = remainDays;
	}
}
