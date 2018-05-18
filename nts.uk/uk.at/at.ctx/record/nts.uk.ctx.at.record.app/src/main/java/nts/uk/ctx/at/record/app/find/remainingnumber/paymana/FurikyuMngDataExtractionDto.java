package nts.uk.ctx.at.record.app.find.remainingnumber.paymana;

import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.remainingnumber.paymana.FurikyuMngDataExtractionData;

@NoArgsConstructor
@Getter
public class FurikyuMngDataExtractionDto {
	// 振出データID
	private String payoutId;

	private String cID;

	// 社員ID
	private String sID;

	// 日付不明
	private boolean unknownDatePayout;

	// 年月日
	private GeneralDate dayoffDatePyout;

	// 使用期限日
	private GeneralDate expiredDate;

	// 法定内外区分
	private int lawAtr;

	// 発生日数
	private Double occurredDays;

	// 未使用日数
	private Double unUsedDays;

	// 振休消化区分
	private int stateAtr;
	
	// 振休データID
	private String subOfHDID;
	
	// 日付不明
	private boolean unknownDateSub;
	
	// 年月日
	private GeneralDate dayoffDateSub;
	
	// 必要日数
	private Double requiredDays;	
	
	// 未相殺日数
	private Double remainDays;
	
	public FurikyuMngDataExtractionDto(FurikyuMngDataExtractionData domain){
		if (domain.getPayoutManagementData() != null) {
			this.payoutId = domain.getPayoutManagementData().getPayoutId();
			this.cID = domain.getPayoutManagementData().getCID();
			this.sID = domain.getPayoutManagementData().getSID();
			this.unknownDatePayout = domain.getPayoutManagementData().getPayoutDate().isUnknownDate();
			this.dayoffDatePyout = domain.getPayoutManagementData().getPayoutDate().getDayoffDate().get();
			this.expiredDate = domain.getPayoutManagementData().getExpiredDate();
			this.lawAtr = domain.getPayoutManagementData().getLawAtr().value;
			this.occurredDays = domain.getPayoutManagementData().getOccurredDays().v();
			this.unUsedDays = domain.getPayoutManagementData().getUnUsedDays().v();
			this.stateAtr = domain.getPayoutManagementData().getStateAtr().value;
		} else if (domain.getSubstitutionOfHDManagementData() != null) {
			this.subOfHDID = domain.getSubstitutionOfHDManagementData().getSubOfHDID();
			this.cID = domain.getSubstitutionOfHDManagementData().getCid();
			this.sID = domain.getSubstitutionOfHDManagementData().getSID();
			this.unknownDateSub = domain.getSubstitutionOfHDManagementData().getHolidayDate().isUnknownDate();
			this.dayoffDateSub = domain.getSubstitutionOfHDManagementData().getHolidayDate().getDayoffDate().get();
			this.requiredDays = domain.getSubstitutionOfHDManagementData().getRequiredDays().v();	
			this.remainDays = domain.getSubstitutionOfHDManagementData().getRemainDays().v();
		}
	}
}
