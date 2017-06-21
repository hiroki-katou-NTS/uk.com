package nts.uk.ctx.at.record.dom.standardtime;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.record.dom.standardtime.enums.ClassificationUseAtr;
import nts.uk.ctx.at.record.dom.standardtime.enums.EmploymentUseAtr;
import nts.uk.ctx.at.record.dom.standardtime.enums.WorkPlaceUseAtr;

/**
 * 
 * @author nampt
 *
 */
@Getter
public class AgreementUnitSetting extends AggregateRoot{

	private String companyId;

	private ClassificationUseAtr classificationUseAtr;

	private EmploymentUseAtr employmentUseAtr;

	private WorkPlaceUseAtr workPlaceUseAtr;

	public AgreementUnitSetting(String companyId, ClassificationUseAtr classificationUseAtr,
			EmploymentUseAtr employmentUseAtr, WorkPlaceUseAtr workPlaceUseAtr) {
		super();
		this.companyId = companyId;
		this.classificationUseAtr = classificationUseAtr;
		this.employmentUseAtr = employmentUseAtr;
		this.workPlaceUseAtr = workPlaceUseAtr;
	}
	
	public static AgreementUnitSetting createFromJavaType(String companyId, int classificationUseAtr, int employmentUseAtr, int workPlaceUseAtr){
		return new AgreementUnitSetting(companyId, EnumAdaptor.valueOf(classificationUseAtr, ClassificationUseAtr.class),
				EnumAdaptor.valueOf(employmentUseAtr, EmploymentUseAtr.class),
				EnumAdaptor.valueOf(workPlaceUseAtr, WorkPlaceUseAtr.class));
	}
}
