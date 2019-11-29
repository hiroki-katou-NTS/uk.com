package nts.uk.ctx.pr.core.dom.wageprovision.wagetable;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;

/**
 * 資格グループ設定
 */
@Getter
public class QualificationGroupSetting extends AggregateRoot {

	/**
	 * 会社ID
	 */
	private String companyID;

	/**
	 * 資格グループコード
	 */
	private QualificationGroupCode qualificationGroupCode;

	/**
	 * 支払方法
	 */
	private QualificationPaymentMethod paymentMethod;

	/**
	 * 対象資格コード
	 */
	private List<QualificationCode> eligibleQualificationCode;

	/**
	 * 資格グループ名
	 */
	private QualificationGroupName qualificationGroupName;

	public QualificationGroupSetting(String companyID, String qualificationGroupCode, Integer paymentMethod,
			List<String> eligibleQualificationCode, String qualificationGroupName) {
		this.qualificationGroupCode = new QualificationGroupCode(qualificationGroupCode);
		this.paymentMethod = EnumAdaptor.valueOf(paymentMethod, QualificationPaymentMethod.class);
		this.eligibleQualificationCode = eligibleQualificationCode.stream().map(item -> new QualificationCode(item))
				.collect(Collectors.toList());
		this.qualificationGroupName = new QualificationGroupName(qualificationGroupName);
		this.companyID = companyID;
	}

}
