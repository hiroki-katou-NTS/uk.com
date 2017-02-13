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
		LaborInsuranceOfficeUpdateCommand command = this;
		LaborInsuranceOffice laborInsuranceOffice = new LaborInsuranceOffice(new LaborInsuranceOfficeGetMemento() {

			@Override
			public Long getVersion() {
				// TODO Auto-generated method stub
				return 11L;
			}

			@Override
			public ShortName getShortName() {
				// TODO Auto-generated method stub
				return new ShortName(command.laborInsuranceOffice.getShortName());
			}

			@Override
			public String getPrefecture() {
				// TODO Auto-generated method stub
				return command.laborInsuranceOffice.getPrefecture();
			}

			@Override
			public PotalCode getPotalCode() {
				// TODO Auto-generated method stub
				return new PotalCode(command.laborInsuranceOffice.getPotalCode());
			}

			@Override
			public PicPosition getPicPosition() {
				// TODO Auto-generated method stub
				return new PicPosition(command.laborInsuranceOffice.getPicPosition());
			}

			@Override
			public PicName getPicName() {
				// TODO Auto-generated method stub
				return new PicName(command.laborInsuranceOffice.getPicName());
			}

			@Override
			public String getPhoneNumber() {
				// TODO Auto-generated method stub
				return command.laborInsuranceOffice.getPhoneNumber();
			}

			@Override
			public String getOfficeNoC() {
				// TODO Auto-generated method stub
				return command.laborInsuranceOffice.getOfficeNoC();
			}

			@Override
			public String getOfficeNoB() {
				// TODO Auto-generated method stub
				return command.laborInsuranceOffice.getOfficeNoB();
			}

			@Override
			public String getOfficeNoA() {
				// TODO Auto-generated method stub
				return command.laborInsuranceOffice.getOfficeNoA();
			}

			@Override
			public String getOfficeMark() {
				// TODO Auto-generated method stub
				return command.laborInsuranceOffice.getOfficeMark();
			}

			@Override
			public OfficeName getName() {
				// TODO Auto-generated method stub
				return new OfficeName(command.laborInsuranceOffice.getName());
			}

			@Override
			public Memo getMemo() {
				// TODO Auto-generated method stub
				return new Memo(command.laborInsuranceOffice.getMemo());
			}

			@Override
			public KanaAddress getKanaAddress2nd() {
				// TODO Auto-generated method stub
				return new KanaAddress(command.laborInsuranceOffice.getKanaAddress2nd());
			}

			@Override
			public KanaAddress getKanaAddress1st() {
				// TODO Auto-generated method stub
				return new KanaAddress(command.laborInsuranceOffice.getKanaAddress1st());
			}

			@Override
			public CompanyCode getCompanyCode() {
				// TODO Auto-generated method stub
				return new CompanyCode(command.companyCode);
			}

			@Override
			public OfficeCode getCode() {
				// TODO Auto-generated method stub
				return new OfficeCode(command.laborInsuranceOffice.getCode());
			}

			@Override
			public String getCitySign() {
				// TODO Auto-generated method stub
				return command.laborInsuranceOffice.getCitySign();
			}

			@Override
			public Address getAddress2nd() {
				// TODO Auto-generated method stub
				return new Address(command.laborInsuranceOffice.getAddress2nd());
			}

			@Override
			public Address getAddress1st() {
				// TODO Auto-generated method stub
				return new Address(command.laborInsuranceOffice.getAddress1st());
			}
		});
		return laborInsuranceOffice;
	}
}
