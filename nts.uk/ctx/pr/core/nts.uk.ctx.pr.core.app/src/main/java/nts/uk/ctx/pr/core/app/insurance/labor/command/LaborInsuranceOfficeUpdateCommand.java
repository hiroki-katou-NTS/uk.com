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
import nts.uk.ctx.pr.core.dom.insurance.labor.LaborInsuranceOfficeGetMemento;
import nts.uk.shr.com.primitive.Memo;

/**
 * The Class UpdateLaborInsuranceOfficeCommand.
 */
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
		LaborInsuranceOfficeUpdateCommand command = this;
		LaborInsuranceOffice laborInsuranceOffice = new LaborInsuranceOffice(new LaborInsuranceOfficeGetMemento() {

			@Override
			public ShortName getShortName() {
				return new ShortName(command.laborInsuranceOffice.getShortName());
			}

			@Override
			public String getPrefecture() {
				return command.laborInsuranceOffice.getPrefecture();
			}

			@Override
			public PotalCode getPotalCode() {
				return new PotalCode(command.laborInsuranceOffice.getPotalCode());
			}

			@Override
			public PicPosition getPicPosition() {
				return new PicPosition(command.laborInsuranceOffice.getPicPosition());
			}

			@Override
			public PicName getPicName() {
				return new PicName(command.laborInsuranceOffice.getPicName());
			}

			@Override
			public String getPhoneNumber() {
				return command.laborInsuranceOffice.getPhoneNumber();
			}

			@Override
			public String getOfficeNoC() {
				return command.laborInsuranceOffice.getOfficeNoC();
			}

			@Override
			public String getOfficeNoB() {
				return command.laborInsuranceOffice.getOfficeNoB();
			}

			@Override
			public String getOfficeNoA() {
				return command.laborInsuranceOffice.getOfficeNoA();
			}

			@Override
			public String getOfficeMark() {
				return command.laborInsuranceOffice.getOfficeMark();
			}

			@Override
			public OfficeName getName() {
				return new OfficeName(command.laborInsuranceOffice.getName());
			}

			@Override
			public Memo getMemo() {
				return new Memo(command.laborInsuranceOffice.getMemo());
			}

			@Override
			public KanaAddress getKanaAddress2nd() {
				return new KanaAddress(command.laborInsuranceOffice.getKanaAddress2nd());
			}

			@Override
			public KanaAddress getKanaAddress1st() {
				return new KanaAddress(command.laborInsuranceOffice.getKanaAddress1st());
			}

			@Override
			public CompanyCode getCompanyCode() {
				return new CompanyCode(command.companyCode);
			}

			@Override
			public OfficeCode getCode() {
				return new OfficeCode(command.laborInsuranceOffice.getCode());
			}

			@Override
			public String getCitySign() {
				return command.laborInsuranceOffice.getCitySign();
			}

			@Override
			public Address getAddress2nd() {
				return new Address(command.laborInsuranceOffice.getAddress2nd());
			}

			@Override
			public Address getAddress1st() {
				return new Address(command.laborInsuranceOffice.getAddress1st());
			}
		});
		return laborInsuranceOffice;
	}
}
