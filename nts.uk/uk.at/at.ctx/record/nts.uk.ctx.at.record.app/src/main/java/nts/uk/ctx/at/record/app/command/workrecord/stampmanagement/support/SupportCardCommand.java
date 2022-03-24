package nts.uk.ctx.at.record.app.command.workrecord.stampmanagement.support;

import lombok.Data;
import lombok.NoArgsConstructor;

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
	private String supportCardNumber;

	// 	職場ID
	private String workplaceId;
}
