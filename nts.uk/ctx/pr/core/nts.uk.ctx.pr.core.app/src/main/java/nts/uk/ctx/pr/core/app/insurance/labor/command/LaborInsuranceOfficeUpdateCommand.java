/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.app.insurance.labor.command;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.core.app.insurance.labor.find.LaborInsuranceOfficeDto;
import nts.uk.ctx.pr.core.dom.insurance.Address;
import nts.uk.ctx.pr.core.dom.insurance.KanaAddress;
import nts.uk.ctx.pr.core.dom.insurance.OfficeCode;
import nts.uk.ctx.pr.core.dom.insurance.OfficeName;
import nts.uk.ctx.pr.core.dom.insurance.PicName;
import nts.uk.ctx.pr.core.dom.insurance.PicPosition;
import nts.uk.ctx.pr.core.dom.insurance.PotalCode;
import nts.uk.ctx.pr.core.dom.insurance.ShortName;
import nts.uk.ctx.pr.core.dom.insurance.labor.LaborInsuranceOffice;
import nts.uk.shr.com.primitive.Memo;

/**
 * The Class UpdateLaborInsuranceOfficeCommand.
 */
// TODO: Auto-generated Javadoc

@Getter
@Setter
public class LaborInsuranceOfficeUpdateCommand {

	/** The labor insurance office. */
	private LaborInsuranceOfficeDto laborInsuranceOffice;

	/** The commany code. */
	private String companyCode;

	/**
	 * To domain.
	 *
	 * @return the labor insurance office
	 */
	public LaborInsuranceOffice toDomain() {
		LaborInsuranceOffice laborInsuranceOffice = new LaborInsuranceOffice();
		laborInsuranceOffice.setCompanyCode(new CompanyCode(this.companyCode));
		laborInsuranceOffice.setCode(new OfficeCode(this.laborInsuranceOffice.getCode()));
		laborInsuranceOffice.setName(new OfficeName(this.laborInsuranceOffice.getName()));
		laborInsuranceOffice.setShortName(new ShortName(this.laborInsuranceOffice.getShortName()));
		laborInsuranceOffice.setPicName(new PicName(this.laborInsuranceOffice.getPicName()));
		laborInsuranceOffice.setPicPosition(new PicPosition(this.laborInsuranceOffice.getPicPosition()));
		laborInsuranceOffice.setPotalCode(new PotalCode(this.laborInsuranceOffice.getPotalCode()));
		laborInsuranceOffice.setPrefecture(this.laborInsuranceOffice.getPrefecture());
		laborInsuranceOffice.setAddress1st(new Address(this.laborInsuranceOffice.getAddress1st()));
		laborInsuranceOffice.setAddress2nd(new Address(this.laborInsuranceOffice.getAddress2nd()));
		laborInsuranceOffice.setKanaAddress1st(new KanaAddress(this.laborInsuranceOffice.getKanaAddress1st()));
		laborInsuranceOffice.setKanaAddress2nd(new KanaAddress(this.laborInsuranceOffice.getKanaAddress2nd()));
		laborInsuranceOffice.setPhoneNumber(this.laborInsuranceOffice.getPhoneNumber());
		laborInsuranceOffice.setOfficeMark(this.laborInsuranceOffice.getOfficeMark());
		laborInsuranceOffice.setCitySign(this.laborInsuranceOffice.getCitySign());
		laborInsuranceOffice.setOfficeNoA(this.laborInsuranceOffice.getOfficeNoA());
		laborInsuranceOffice.setOfficeNoB(this.laborInsuranceOffice.getOfficeNoB());
		laborInsuranceOffice.setOfficeNoC(this.laborInsuranceOffice.getOfficeNoC());
		laborInsuranceOffice.setMemo(new Memo(this.laborInsuranceOffice.getMemo()));
		return laborInsuranceOffice;
	}
}
