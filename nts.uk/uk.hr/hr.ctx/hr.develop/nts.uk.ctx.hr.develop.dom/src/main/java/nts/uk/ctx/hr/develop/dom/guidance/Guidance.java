package nts.uk.ctx.hr.develop.dom.guidance;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.hr.shared.dom.primitiveValue.Integer_1_5;
import nts.uk.ctx.hr.shared.dom.primitiveValue.Integer_1_500;

/**操作ガイド*/
@AllArgsConstructor
@Getter
public class Guidance extends AggregateRoot{

	/**会社ID*/
	private String companyId;
	
	/**共通使用設定*/
	private boolean usageFlgCommon;

	/**メッセージエリアの行数*/
	private Integer_1_5 guideMsgAreaRow;

	/**メッセージの最大文字数*/
	private Integer_1_500 guideMsgMaxNum;

	/**メッセージ内容*/
	private List<GuideMsg> guideMsg;
	
	public static Guidance createFromJavaType(String companyId, boolean usageFlgCommon, int guideMsgAreaRow, int guideMsgMaxNum, List<GuideMsg> guideMsg) {
		return new Guidance(
				companyId,
				usageFlgCommon,
				new Integer_1_5(guideMsgAreaRow),
				new Integer_1_500(guideMsgMaxNum),
				guideMsg);
	}
}
