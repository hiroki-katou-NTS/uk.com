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
public class AgreementTimeOfClassification extends AggregateRoot {

	private String companyId;

	private String basicSettingId;

	private LaborSystemtAtr laborSystemAtr;

	private String classificationCode;

	public AgreementTimeOfClassification(String companyId, String basicSettingId, LaborSystemtAtr laborSystemAtr,
			String classificationCode) {
		super();
		this.companyId = companyId;
		this.basicSettingId = basicSettingId;
		this.laborSystemAtr = laborSystemAtr;
		this.classificationCode = classificationCode;
	}

	public static AgreementTimeOfClassification createJavaType(String companyId, String basicSettingId,
			BigDecimal laborSystemAtr, String classificationCode) {
		return new AgreementTimeOfClassification(companyId, basicSettingId,
				EnumAdaptor.valueOf(laborSystemAtr.intValue(), LaborSystemtAtr.class), classificationCode);
	}
}
