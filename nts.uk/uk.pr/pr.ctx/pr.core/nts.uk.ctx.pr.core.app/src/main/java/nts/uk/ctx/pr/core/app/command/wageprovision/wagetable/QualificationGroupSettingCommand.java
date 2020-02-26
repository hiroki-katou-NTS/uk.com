package nts.uk.ctx.pr.core.app.command.wageprovision.wagetable;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.pr.core.dom.wageprovision.wagetable.QualificationGroupSetting;

/**
 * 資格グループ設定
 */
@NoArgsConstructor
@Data
public class QualificationGroupSettingCommand {

	/**
	 * 会社ID
	 */
	private String companyID;

	/**
	 * 資格グループコード
	 */
	private String qualificationGroupCode;

	/**
	 * 支払方法
	 */
	private int paymentMethod;

	/**
	 * 対象資格コード
	 */
	private List<String> eligibleQualificationCode;

	/**
	 * 資格グループ名
	 */
	private String qualificationGroupName;

	public QualificationGroupSetting fromCommandToDomain() {
		return new QualificationGroupSetting(companyID, qualificationGroupCode, paymentMethod,
				eligibleQualificationCode, qualificationGroupName);
	}

}
