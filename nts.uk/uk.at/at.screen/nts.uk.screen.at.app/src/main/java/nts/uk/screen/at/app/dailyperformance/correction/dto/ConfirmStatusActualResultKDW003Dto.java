package nts.uk.screen.at.app.dailyperformance.correction.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.app.find.dailyperform.customjson.CustomGeneralDateSerializer;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.confirmationstatus.ConfirmStatusActualResult;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.confirmationstatus.ReleasedAtr;

/**
 * 
 * @author tutk
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ConfirmStatusActualResultKDW003Dto {

	/**
	 * 対象社員
	 */
	private String employeeId;

	/**
	 * 対象年月日
	 */
	@JsonDeserialize(using = CustomGeneralDateSerializer.class)
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

	public static ConfirmStatusActualResultKDW003Dto fromDomain(ConfirmStatusActualResult domain) {

		return new ConfirmStatusActualResultKDW003Dto(domain.getEmployeeId(), domain.getDate(), domain.isStatus(),
				domain.getPermissionCheck() != null ? domain.getPermissionCheck().value : null,
				domain.getPermissionRelease() != null ? domain.getPermissionRelease().value : null);

	}
	
	public ConfirmStatusActualResult toDomain() {

		return new ConfirmStatusActualResult(this.employeeId, this.date, this.status,
				ReleasedAtr.valueOf(this.permissionCheck),
				ReleasedAtr.valueOf(this.permissionRelease));

	}
}
