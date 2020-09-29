package nts.uk.ctx.at.shared.app.find.remainingnumber.paymana;

import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;

@NoArgsConstructor
@Getter
public class CompositePayOutSubMngData {
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
	private Integer lawAtr;

	// 発生日数
	private Double occurredDays;

	// 未使用日数
	private Double unUsedDays;

	// 振休消化区分
	private Integer stateAtr;
	
	// add
	private boolean payoutTied;
	
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
	
	// add
	private boolean subTied;

//	public CompositePayOutSubMngData(PayoutManagementData domain, List<PayoutSubofHDManagement> listPayoutSub) {
//		this.payoutId = domain.getPayoutId();
//		this.cID = domain.getCID();
//		this.sID = domain.getSID();
//		this.unknownDatePayout = domain.getPayoutDate().isUnknownDate();
//		domain.getPayoutDate().getDayoffDate().ifPresent(dayoff->{
//			this.dayoffDatePyout = dayoff;
//		});
//		this.expiredDate = domain.getExpiredDate();
//		this.lawAtr = domain.getLawAtr().value;
//		this.occurredDays = domain.getOccurredDays().v();
//		this.unUsedDays = domain.getUnUsedDays().v();
//		this.stateAtr = domain.getStateAtr().value;
//		
//		if(listPayoutSub.stream().anyMatch(item -> item.getPayoutId().equals(domain.getPayoutId()))) {
//			this.payoutTied = true;
//		} else {
//			this.payoutTied = false;
//		}
//	}
//	
//	public CompositePayOutSubMngData(SubstitutionOfHDManagementData domain, List<PayoutSubofHDManagement> listPayoutSub) {
//		this.subOfHDID = domain.getSubOfHDID();
//		this.cID = domain.getCid();
//		this.sID = domain.getSID();
//		this.unknownDateSub = domain.getHolidayDate().isUnknownDate();
//		domain.getHolidayDate().getDayoffDate().ifPresent(dayoff->{
//			this.dayoffDateSub  = dayoff;
//		});
//		this.requiredDays = domain.getRequiredDays().v();	
//		this.remainDays = domain.getRemainDays().v();
//		
//		if(listPayoutSub.stream().anyMatch(item -> item.getSubOfHDID().equals(domain.getSubOfHDID()))) {
//			this.subTied = true;
//		} else {
//			this.subTied = false;
//		}
//	}
}
