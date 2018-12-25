package nts.uk.ctx.pr.core.app.command.wageprovision.wagetable;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.pr.core.dom.wageprovision.wagetable.QualificationGroupSettingContent;

/**
 * 資格グループ設定内容
 */
@Data
@NoArgsConstructor
public class QualificationGroupSettingContentCommand {

	/**
	 * 資格グループコード
	 */
	private String qualificationGroupCode;
	
	private String qualificationGroupName;

	/**
	 * 支払方法
	 */
	private int paymentMethod;

	/**
	 * 対象資格コード
	 */
	private List<String> eligibleQualificationCode;

	public QualificationGroupSettingContent fromCommandToDomain() {
		return new QualificationGroupSettingContent(qualificationGroupCode, qualificationGroupName, paymentMethod, eligibleQualificationCode);
	}

}
