package nts.uk.screen.at.app.query.kdp.kdps01.c;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.confirmationstatus.ConfirmStatusActualResult;

/**
 * 
 * @author sonnlb
 *
 *
 *         日の実績の確認状況
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ConfirmStatusActualResultDto {

	/**
	 * 対象社員
	 */
	private String employeeId;

	/**
	 * 対象年月日
	 */
	private GeneralDate date;

	/**
	 * 確認状態 or 承認状態
	 */
	@Setter
	private boolean status;

	/**
	 * 実施可否
	 */
	protected Integer permissionCheck;

	/**
	 * 解除可否
	 */
	protected Integer permissionRelease;

	public static ConfirmStatusActualResultDto fromDomain(ConfirmStatusActualResult domain) {

		return new ConfirmStatusActualResultDto(domain.getEmployeeId(), domain.getDate(), domain.isStatus(),
				domain.getPermissionCheck() != null ? domain.getPermissionCheck().value : null,
				domain.getPermissionRelease() != null ? domain.getPermissionRelease().value : null);

	}
}
