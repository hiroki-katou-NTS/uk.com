package nts.uk.ctx.at.record.app.find.remainingnumber.paymana;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.remainingnumber.paymana.SubstitutionOfHDManagementData;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SubstitutionOfHDManagementDto {
	// 振休データID
	private String subOfHDID;
	
	private String cid;
	
	// 社員ID	
	private String sID;
	
	// 振休日
	private boolean unknowDate;
	private GeneralDate dayoffDate;
	
	// 必要日数
	private Double requiredDays;	
	
	// 未相殺日数
	private Double remainDays;
	
	public static SubstitutionOfHDManagementDto fromDomain(SubstitutionOfHDManagementData domain) {
		return new SubstitutionOfHDManagementDto(domain.getSubOfHDID(), domain.getCid(), domain.getSID(), domain.getHolidayDate().isUnknownDate(), domain.getHolidayDate().getDayoffDate().get(), domain.getRequiredDays().v(), domain.getRemainDays().v());
	}
}
