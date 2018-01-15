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
public class AgreementTimeOfCompany extends AggregateRoot{
	
	private String companyId;

	private String basicSettingId;
	
	private LaborSystemtAtr laborSystemAtr;
	
	public AgreementTimeOfCompany(String companyId, String basicSettingId, LaborSystemtAtr laborSystemAtr) {
		super();
		this.companyId = companyId;
		this.basicSettingId = basicSettingId;
		this.laborSystemAtr = laborSystemAtr;
	}
	
	public static AgreementTimeOfCompany createFromJavaType(String companyId, String basicSettingId, BigDecimal laborSystemAtr){
		return new AgreementTimeOfCompany(companyId, basicSettingId, EnumAdaptor.valueOf(laborSystemAtr.intValue(), LaborSystemtAtr.class));
	}
	
}
