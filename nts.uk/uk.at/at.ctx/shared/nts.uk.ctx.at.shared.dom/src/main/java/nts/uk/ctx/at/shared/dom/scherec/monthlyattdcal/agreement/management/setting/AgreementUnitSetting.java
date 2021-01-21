package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.setting;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.enums.UseClassificationAtr;

/**
 * ３６協定単位設定
 * @author nampt
 *
 */
@Getter
public class AgreementUnitSetting extends AggregateRoot{
	/** 会社ID */
	private String companyId;
	/** 分類使用区分 */
	private UseClassificationAtr classificationUseAtr;
	/** 雇用使用区分 */
	private UseClassificationAtr employmentUseAtr;
	/** 職場使用区分 */
	private UseClassificationAtr workPlaceUseAtr;

	public AgreementUnitSetting(String companyId, UseClassificationAtr classificationUseAtr,
			UseClassificationAtr employmentUseAtr, UseClassificationAtr workPlaceUseAtr) {
		super();
		this.companyId = companyId;
		this.classificationUseAtr = classificationUseAtr;
		this.employmentUseAtr = employmentUseAtr;
		this.workPlaceUseAtr = workPlaceUseAtr;
	}
	
	public static AgreementUnitSetting createFromJavaType(String companyId, int classificationUseAtr, int employmentUseAtr, int workPlaceUseAtr){
		return new AgreementUnitSetting(companyId, EnumAdaptor.valueOf(classificationUseAtr, UseClassificationAtr.class),
				EnumAdaptor.valueOf(employmentUseAtr, UseClassificationAtr.class),
				EnumAdaptor.valueOf(workPlaceUseAtr, UseClassificationAtr.class));
	}
}
