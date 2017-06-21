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
public class AgreementTimeOfWorkPlace extends AggregateRoot {

	private String workplaceId;

	private String basicSettingId;

	private LaborSystemtAtr laborSystemAtr;

	public AgreementTimeOfWorkPlace(String workplaceId, String basicSettingId, LaborSystemtAtr laborSystemAtr) {
		super();
		this.workplaceId = workplaceId;
		this.basicSettingId = basicSettingId;
		this.laborSystemAtr = laborSystemAtr;
	}

	public static AgreementTimeOfWorkPlace createJavaType(String workplaceId, String basicSettingId,
			BigDecimal laborSystemAtr) {
		return new AgreementTimeOfWorkPlace(workplaceId, basicSettingId,
				EnumAdaptor.valueOf(laborSystemAtr.intValue(), LaborSystemtAtr.class));
	}

}
