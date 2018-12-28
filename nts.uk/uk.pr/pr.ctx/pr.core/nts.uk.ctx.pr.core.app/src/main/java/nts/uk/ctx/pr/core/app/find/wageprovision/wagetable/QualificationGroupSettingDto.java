package nts.uk.ctx.pr.core.app.find.wageprovision.wagetable;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.primitive.PrimitiveValueBase;
import nts.uk.ctx.pr.core.dom.wageprovision.wagetable.QualificationGroupSetting;

/**
 * 資格グループ設定
 */
@NoArgsConstructor
@Data
public class QualificationGroupSettingDto {

	/**
	 * 資格グループコード
	 */
	private String qualificationGroupCode;

	/**
	 * 資格グループ名
	 */
	private String qualificationGroupName;
	
	/**
	 * 支払方法
	 */
	private int paymentMethod;

	/**
	 * 対象資格コード
	 */
	private List<String> eligibleQualificationCode;

	public static QualificationGroupSettingDto fromDomainToDto(QualificationGroupSetting domain) {
		QualificationGroupSettingDto dto = new QualificationGroupSettingDto();
		dto.qualificationGroupCode = domain.getQualificationGroupCode().v();
		dto.qualificationGroupName = domain.getQualificationGroupName().v();
		dto.paymentMethod = domain.getPaymentMethod().value;
		dto.eligibleQualificationCode = domain.getEligibleQualificationCode().stream().map(PrimitiveValueBase::v)
				.collect(Collectors.toList());
		return dto;
	}

}
