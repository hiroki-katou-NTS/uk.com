package nts.uk.ctx.bs.employee.pub.person;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PersonInfoDto {
	/** The 社員ID sID */
	private String sID;

	/** The 社員コード id. */
	private String positionId;

	/** The position code. */
	private String positionCode;

	/** The position name. */
	private String positionName;
}
