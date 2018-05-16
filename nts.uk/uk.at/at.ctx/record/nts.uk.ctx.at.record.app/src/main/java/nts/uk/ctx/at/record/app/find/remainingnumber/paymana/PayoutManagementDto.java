package nts.uk.ctx.at.record.app.find.remainingnumber.paymana;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.remainingnumber.paymana.PayoutManagementData;

/**
 * 
 * @author TrungBV
 *
 */

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class PayoutManagementDto {
	// 振出データID
	private String payoutId;

	private String cID;

	// 社員ID
	private String sID;

	// 振出日
	private boolean unknownDate;
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

	public static PayoutManagementDto fromDomain(PayoutManagementData domain) {
		return new PayoutManagementDto(domain.getPayoutId(), domain.getCID(), domain.getSID(),
				domain.getPayoutDate().isUnknownDate(), domain.getPayoutDate().getDayoffDate().get(),
				domain.getExpiredDate(), domain.getLawAtr().value, domain.getOccurredDays().v(),
				domain.getUnUsedDays().v(), domain.getStateAtr().value);
	}
}
