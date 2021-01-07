package nts.uk.ctx.office.app.command.reference.auth;

import java.util.List;
import lombok.Getter;

/*
 * 在席照会で参照できる権限の指定 command
 */
@Getter
public class SpecifyAuthInquiryCommand {
	// 会社ID
	private String cid;

	// 就業ロールID
	private String employmentRoleId;

	// 見られる職位ID
	private List<String> positionIdSeen;
}
