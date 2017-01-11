/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.insurance.labor;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.core.dom.insurance.Address;
import nts.uk.ctx.pr.core.dom.insurance.KanaAddress;
import nts.uk.ctx.pr.core.dom.insurance.OfficeCode;
import nts.uk.ctx.pr.core.dom.insurance.OfficeName;
import nts.uk.ctx.pr.core.dom.insurance.PicName;
import nts.uk.ctx.pr.core.dom.insurance.PicPosition;
import nts.uk.ctx.pr.core.dom.insurance.PotalCode;
import nts.uk.ctx.pr.core.dom.insurance.ShortName;
import nts.uk.shr.com.primitive.Memo;

/**
 * The Class LaborInsuranceOffice.
 */
@Getter
public class LaborInsuranceOffice extends AggregateRoot {

	/** The company code. */
	private CompanyCode companyCode;

	/** The code. */
	private OfficeCode code;

	/** The name. */
	private OfficeName name;

	/** The short name. */
	private ShortName shortName;

	/** The pic name. */
	private PicName picName;

	/** The pic position. */
	private PicPosition picPosition;

	/** The potal code. */
	private PotalCode potalCode;

	/** The prefecture. */
	private String prefecture;

	/** The address 1 st. */
	private Address address1st;

	/** The address 2 nd. */
	private Address address2nd;

	/** The kana address 1 st. */
	private KanaAddress kanaAddress1st;

	/** The kana address 2 nd. */
	private KanaAddress kanaAddress2nd;

	/** The phone number. */
	// TODO: TelephoneNo
	private String phoneNumber;

	/** The city sign. */
	private String citySign;

	/** The office mark. */
	private String officeMark;

	/** The office no A. */
	private String officeNoA;

	/** The office no B. */
	private String officeNoB;

	/** The office no C. */
	private String officeNoC;

	/** The memo. */
	private Memo memo;
}
