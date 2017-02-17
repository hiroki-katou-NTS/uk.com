/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.app.wagetable.find;

import java.util.Set;

import lombok.Data;
import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.core.dom.wagetable.Certification;
import nts.uk.ctx.pr.core.dom.wagetable.CertificationSetMemento;
import nts.uk.ctx.pr.core.dom.wagetable.CertifyGroupSetMemento;
import nts.uk.ctx.pr.core.dom.wagetable.MultipleTargetSetting;
import nts.uk.ctx.pr.core.dom.wagetable.WageTableCode;

@Data
public class CertifyGroupFindInDto {

	/** The code. */
	private String code;

	/** The company code. */
	private String companyCode;

}
