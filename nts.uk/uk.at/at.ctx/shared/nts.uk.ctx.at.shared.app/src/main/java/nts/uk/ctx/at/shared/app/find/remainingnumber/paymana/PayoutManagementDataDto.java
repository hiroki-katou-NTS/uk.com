package nts.uk.ctx.at.shared.app.find.remainingnumber.paymana;

import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.paymana.PayoutManagementData;

@Getter
@NoArgsConstructor
public class PayoutManagementDataDto {
	// 振出データID
	private String payoutId;

	// 社員ID
	private String sID;

	// 年月日
	private GeneralDate dayoffDate;

	// 発生日数
	private Double occurredDays;

	private Double unUsedDays;

	// 振休消化区分
	private int stateAtr;
	
	private boolean Linked;

	private PayoutManagementDataDto(String payoutId, String sID, GeneralDate dayoffDate,
			 Double occurredDays, Double unUsedDays) {
		super();
		this.payoutId = payoutId;
		this.sID = sID;
		this.dayoffDate = dayoffDate;
		this.occurredDays = occurredDays;
		this.unUsedDays = unUsedDays;
	}
	
	public static PayoutManagementDataDto createFromDomain(PayoutManagementData domain){
		return new PayoutManagementDataDto(domain.getPayoutId(), domain.getSID(),
				domain.getPayoutDate().getDayoffDate().isPresent() ? domain.getPayoutDate().getDayoffDate().get()
						: null, domain.getOccurredDays().v(), domain.getUnUsedDays().v());
	}
	
	public void setLinked(boolean isLinked){
		this.Linked = isLinked;
	}
	
}
