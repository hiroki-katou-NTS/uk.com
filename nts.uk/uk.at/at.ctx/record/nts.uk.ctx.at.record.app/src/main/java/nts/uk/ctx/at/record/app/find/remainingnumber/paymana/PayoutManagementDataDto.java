package nts.uk.ctx.at.record.app.find.remainingnumber.paymana;

import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.remainingnumber.paymana.PayoutManagementData;

@Getter
@NoArgsConstructor
public class PayoutManagementDataDto {
	// 振出データID
	private String payoutId;

	private String cID;

	// 社員ID
	private String sID;

	// 日付不明
	private boolean unknownDate;

	// 年月日
	private GeneralDate dayoffDate;

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

	private PayoutManagementDataDto(String payoutId, String cID, String sID, boolean unknownDate, GeneralDate dayoffDate,
			GeneralDate expiredDate, int lawAtr, Double occurredDays, Double unUsedDays, int stateAtr) {
		super();
		this.payoutId = payoutId;
		this.cID = cID;
		this.sID = sID;
		this.unknownDate = unknownDate;
		this.dayoffDate = dayoffDate;
		this.expiredDate = expiredDate;
		this.lawAtr = lawAtr;
		this.occurredDays = occurredDays;
		this.unUsedDays = unUsedDays;
		this.stateAtr = stateAtr;
	}
	
	public static PayoutManagementDataDto createFromDomain(PayoutManagementData domain){
		return new PayoutManagementDataDto(domain.getPayoutId(), domain.getCID(), domain.getSID(),
				domain.getPayoutDate().isUnknownDate(),
				domain.getPayoutDate().getDayoffDate().isPresent() ? domain.getPayoutDate().getDayoffDate().get()
						: null,
				domain.getExpiredDate(), domain.getLawAtr().value, domain.getOccurredDays().v(),
				domain.getUnUsedDays().v(), domain.getStateAtr().value);
	}
	
	
}
