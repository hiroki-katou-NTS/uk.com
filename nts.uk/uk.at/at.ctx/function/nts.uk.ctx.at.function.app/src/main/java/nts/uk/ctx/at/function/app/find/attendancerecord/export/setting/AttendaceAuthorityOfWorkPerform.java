package nts.uk.ctx.at.function.app.find.attendancerecord.export.setting;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AttendaceAuthorityOfWorkPerform {
	/**
	 * 日別実績の機能NO
	 */
	private int functionNo;

	/**
	 * 日別実績の機能名
	 */
	private String companyId;

	/**
	 * 利用区分
	 */
	private boolean availability;

	/**
	 * 日別実績の機能説明文
	 */
	private String roleId;

	public AttendaceAuthorityOfWorkPerform(int functionNo, String companyId,
			String roleId) {
		this.functionNo = functionNo;
		this.companyId = companyId;
		this.roleId = roleId;
	}
}
