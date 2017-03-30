/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.app.insurance.social.office.command;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.pr.core.dom.insurance.Address1;
import nts.uk.ctx.pr.core.dom.insurance.Address2;
import nts.uk.ctx.pr.core.dom.insurance.AddressKana1;
import nts.uk.ctx.pr.core.dom.insurance.AddressKana2;
import nts.uk.ctx.pr.core.dom.insurance.OfficeCode;
import nts.uk.ctx.pr.core.dom.insurance.OfficeName;
import nts.uk.ctx.pr.core.dom.insurance.PhoneNumber;
import nts.uk.ctx.pr.core.dom.insurance.PicName;
import nts.uk.ctx.pr.core.dom.insurance.PicPosition;
import nts.uk.ctx.pr.core.dom.insurance.PotalCode;
import nts.uk.ctx.pr.core.dom.insurance.ShortName;
import nts.uk.ctx.pr.core.dom.insurance.social.SocialInsuranceOffice;
import nts.uk.ctx.pr.core.dom.insurance.social.SocialInsuranceOfficeGetMemento;
import nts.uk.shr.com.primitive.Memo;

/**
 * The Class RegisterSocialOfficeCommand.
 */
@Getter
@Setter
public class RegisterSocialOfficeCommand extends SocialOfficeBaseCommand {

	/**
	 * To domain.
	 *
	 * @param companyCode
	 *            the company code
	 * @return the social insurance office
	 */
	public SocialInsuranceOffice toDomain(String companyCode) {
		RegisterSocialOfficeCommand command = this;
		SocialInsuranceOffice socialInsuranceOffice = new SocialInsuranceOffice(
				new SocialInsuranceOfficeGetMemento() {

					@Override
					public String getCompanyCode() {
						return companyCode;
					}

					@Override
					public OfficeCode getCode() {
						return new OfficeCode(command.getCode());
					}

					@Override
					public OfficeName getName() {
						return new OfficeName(command.getName());
					}

					@Override
					public ShortName getShortName() {
						return new ShortName(command.getShortName());
					}

					@Override
					public PicName getPicName() {
						return new PicName(command.getPicName());
					}

					@Override
					public PicPosition getPicPosition() {
						return new PicPosition(command.getPicPosition());
					}

					@Override
					public PotalCode getPotalCode() {
						return new PotalCode(command.getPotalCode());
					}

					@Override
					public Address1 getAddress1st() {
						return new Address1(command.getAddress1st());
					}

					@Override
					public Address2 getAddress2nd() {
						return new Address2(command.getAddress2nd());
					}

					@Override
					public AddressKana1 getKanaAddress1st() {
						return new AddressKana1(command.getKanaAddress1st());
					}

					@Override
					public AddressKana2 getKanaAddress2nd() {
						return new AddressKana2(command.getKanaAddress2nd());
					}

					@Override
					public PhoneNumber getPhoneNumber() {
						return new PhoneNumber(command.getPhoneNumber());
					}

					@Override
					public String getHealthInsuOfficeRefCode1st() {
						return command.getHealthInsuOfficeRefCode1st();
					}

					@Override
					public String getHealthInsuOfficeRefCode2nd() {
						return command.getHealthInsuOfficeRefCode2nd();
					}

					@Override
					public String getPensionOfficeRefCode1st() {
						return command.getPensionOfficeRefCode1st();
					}

					@Override
					public String getPensionOfficeRefCode2nd() {
						return command.getPensionOfficeRefCode2nd();
					}

					@Override
					public String getWelfarePensionFundCode() {
						return command.getWelfarePensionFundCode();
					}

					@Override
					public String getOfficePensionFundCode() {
						return command.getOfficePensionFundCode();
					}

					@Override
					public String getHealthInsuCityCode() {
						return command.getHealthInsuCityCode();
					}

					@Override
					public String getHealthInsuOfficeSign() {
						return command.getHealthInsuOfficeSign();
					}

					@Override
					public String getPensionCityCode() {
						return command.getPensionCityCode();
					}

					@Override
					public String getPensionOfficeSign() {
						return command.getPensionOfficeSign();
					}

					@Override
					public String getHealthInsuOfficeCode() {
						return command.getHealthInsuOfficeCode();
					}

					@Override
					public String getHealthInsuAssoCode() {
						return command.getHealthInsuAssoCode();
					}

					@Override
					public Memo getMemo() {
						return new Memo(command.getMemo());
					}
				});

		return socialInsuranceOffice;
	}
}
