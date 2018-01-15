package nts.uk.ctx.at.record.dom.standardtime;

import java.math.BigDecimal;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.record.dom.standardtime.enums.LaborSystemtAtr;

/**
 * 
 * @author nampt
 *
 */
@Getter
public class AgreementTimeOfEmployment extends AggregateRoot {

	private String companyId;

	private String basicSettingId;

	private LaborSystemtAtr laborSystemAtr;

	private String employmentCategoryCode;

	public AgreementTimeOfEmployment(String companyId, String basicSettingId, LaborSystemtAtr laborSystemAtr,
			String employmentCategoryCode) {
		super();
		this.companyId = companyId;
		this.basicSettingId = basicSettingId;
		this.laborSystemAtr = laborSystemAtr;
		this.employmentCategoryCode = employmentCategoryCode;
	}

	public static AgreementTimeOfEmployment createJavaType(String companyId, String basicSettingId, BigDecimal laborSystemAtr,
			String employmentCategoryCode) {
		return new AgreementTimeOfEmployment(companyId, basicSettingId,
				EnumAdaptor.valueOf(laborSystemAtr.intValue(), LaborSystemtAtr.class), employmentCategoryCode);
	}
}
