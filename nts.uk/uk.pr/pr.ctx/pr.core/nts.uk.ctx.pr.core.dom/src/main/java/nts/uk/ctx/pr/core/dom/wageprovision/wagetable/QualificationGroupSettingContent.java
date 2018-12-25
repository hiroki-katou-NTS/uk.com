package nts.uk.ctx.pr.core.dom.wageprovision.wagetable;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.DomainObject;

/**
 * 資格グループ設定内容
 */
@AllArgsConstructor
@Getter
public class QualificationGroupSettingContent extends DomainObject {

	/**
	 * 資格グループコード
	 */
	private QualificationGroupCode qualificationGroupCode;
	
	private QualificationGroupName qualificationGroupName;

	/**
	 * 支払方法
	 */
	private QualificationPaymentMethod paymentMethod;

	/**
	 * 対象資格コード
	 */
	private List<QualificationCode> eligibleQualificationCodes;

	public QualificationGroupSettingContent(String qualificationGroupCode, String qualificationGroupName, Integer paymentMethod,
			List<String> eligibleQualificationCodes) {
		this.qualificationGroupCode = new QualificationGroupCode(qualificationGroupCode);
		this.qualificationGroupName = new QualificationGroupName(qualificationGroupName);
		this.paymentMethod = EnumAdaptor.valueOf(paymentMethod, QualificationPaymentMethod.class);
		this.eligibleQualificationCodes = eligibleQualificationCodes.stream().map(item -> new QualificationCode(item))
				.collect(Collectors.toList());
	}

}
