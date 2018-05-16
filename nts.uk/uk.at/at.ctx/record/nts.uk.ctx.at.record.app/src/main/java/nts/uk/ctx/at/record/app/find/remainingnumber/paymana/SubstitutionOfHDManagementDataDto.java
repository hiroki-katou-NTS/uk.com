package nts.uk.ctx.at.record.app.find.remainingnumber.paymana;

import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.remainingnumber.paymana.SubstitutionOfHDManagementData;

@NoArgsConstructor
@Getter
public class SubstitutionOfHDManagementDataDto{

	// 振休データID
	private String subOfHDID;
	
	private String cid;
	
	// 社員ID	
	private String sID;
	
	// 日付不明
	private boolean unknownDate;
	
	// 年月日
	private GeneralDate dayoffDate;
	
	// 必要日数
	private Double requiredDays;	
	
	// 未相殺日数
	private Double remainDays;

	private SubstitutionOfHDManagementDataDto(String subOfHDID, String cid, String sID, boolean unknownDate,
			GeneralDate dayoffDate, Double requiredDays, Double remainDays) {
		this.subOfHDID = subOfHDID;
		this.cid = cid;
		this.sID = sID;
		this.unknownDate = unknownDate;
		this.dayoffDate = dayoffDate;
		this.requiredDays = requiredDays;
		this.remainDays = remainDays;
	}
	
	public static SubstitutionOfHDManagementDataDto createFromDomain(SubstitutionOfHDManagementData domain){
		return new SubstitutionOfHDManagementDataDto(domain.getSubOfHDID(), domain.getCid(), domain.getSID(),
				domain.getHolidayDate().isUnknownDate(), domain.getHolidayDate().getDayoffDate().isPresent()
						? domain.getHolidayDate().getDayoffDate().get() : null,
				domain.getRequiredDays().v(), domain.getRemainDays().v());
	}
	

}
