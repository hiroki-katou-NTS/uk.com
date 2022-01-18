package nts.uk.ctx.at.record.app.query.workrecord.stampmanagement.support;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.support.SupportCard;

/**
 * 
 * @author NWS_namnv
 *
 */
@AllArgsConstructor
@Getter
public class SupportCardDto {
	
	// 会社ID
	private String companyId;
	
	// カード番号
	private String supportCardNumber;
	
	// 	職場ID
	private String workplaceId;
	
	public static SupportCardDto toDto(SupportCard supportCard) {
		return new SupportCardDto(supportCard.getCid(), supportCard.getSupportCardNumber().v(), supportCard.getWorkplaceId());
	}

}
