package nts.uk.ctx.at.shared.dom.person;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import nts.arc.time.GeneralDate;

@Builder
@Data
@AllArgsConstructor
public class PersonImport {
	/** The Birthday */
	// 生年月日
	private GeneralDate birthDate;
	
	/** The Gender - 性別 */
	private int gender;
	
	/** The person id - 個人ID */
	private String personId;
	
	/** The PersonNameGroup - 個人名グループ*/
	private PersonNameGroupImport personNameGroup;
}
