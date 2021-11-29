package nts.uk.ctx.at.record.app.command.workrecord.stampmanagement.support;

import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.support.SupportCard;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.support.SupportCardNumber;

/**
 * 
 * @author NWS_namnv
 *
 */
@Data
@NoArgsConstructor
public class SupportCardCommand {
	
	// 会社ID
	private String companyId;

	// 応援カード番号
	private int supportCardNumber;

	// 	職場ID
	private String workplaceId;
	
	public SupportCard toDomain() {
		return new SupportCard(companyId, new SupportCardNumber(supportCardNumber), workplaceId);
	}

}
