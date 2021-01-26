package nts.uk.ctx.at.function.app.find.processexecution.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.function.dom.processexecution.personalschedule.TargetSetting;

/**
 * The class Target setting dto.<br>
 * Dto 作成対象詳細設定
 *
 * @author nws-minhnb
 */
@Data
@AllArgsConstructor
public class TargetSettingDto {

	/**
	 * 勤務種別変更者を再作成
	 */
	private boolean recreateWorkType;

//	/**
//	 * 手修正を保護する
//	 */
//	private boolean manualCorrection;

	/**
	 * 新入社員を作成する
	 */
	private boolean createEmployee;

	/**
	 * 異動者を再作成する
	 */
	private boolean recreateTransfer;

	/**
	 * No args constructor.
	 */
	private TargetSettingDto() {
	}

	/**
	 * Create from domain.
	 *
	 * @param domain the domain
	 * @return the Target setting dto
	 */
	public static TargetSettingDto createFromDomain(TargetSetting domain) {
		if (domain == null) {
			return null;
		}
		TargetSettingDto dto = new TargetSettingDto();
		dto.recreateWorkType = domain.isRecreateWorkType();
		dto.createEmployee = domain.isCreateEmployee();
		dto.recreateTransfer = domain.isRecreateTransfer();
		return dto;
	}

}
