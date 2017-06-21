package nts.uk.ctx.at.record.dom.standardtime;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.record.dom.standardtime.enums.UseClassificationAtr;

/**
 * 
 * @author nampt
 *
 */
@Getter
public class AgreementUnitSetting extends AggregateRoot{

	private String companyId;

	private UseClassificationAtr classificationUseAtr;

	private UseClassificationAtr employmentUseAtr;

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
