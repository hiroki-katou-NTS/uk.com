package nts.uk.ctx.pr.core.app.insurance.social.command;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.core.dom.insurance.Address;
import nts.uk.ctx.pr.core.dom.insurance.KanaAddress;
import nts.uk.ctx.pr.core.dom.insurance.OfficeCode;
import nts.uk.ctx.pr.core.dom.insurance.OfficeName;
import nts.uk.ctx.pr.core.dom.insurance.PicName;
import nts.uk.ctx.pr.core.dom.insurance.PicPosition;
import nts.uk.ctx.pr.core.dom.insurance.PotalCode;
import nts.uk.ctx.pr.core.dom.insurance.ShortName;
import nts.uk.ctx.pr.core.dom.insurance.social.SocialInsuranceOffice;
import nts.uk.ctx.pr.core.dom.insurance.social.SocialInsuranceOfficeGetMemento;
import nts.uk.shr.com.primitive.Memo;

@Getter
@Setter
public class RegisterSocialOfficeCommand extends SocialOfficeBaseCommand {

	public SocialInsuranceOffice toDomain(CompanyCode companyCode)
	{
		RegisterSocialOfficeCommand command = this;
		SocialInsuranceOffice socialInsuranceOffice = new SocialInsuranceOffice(
				new SocialInsuranceOfficeGetMemento(){

					@Override
					public CompanyCode getCompanyCode() {
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
						// TODO Auto-generated method stub
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
					public String getPrefecture() {
						return command.getPrefecture();
					}

					@Override
					public Address getAddress1st() {
						return new Address(command.getAddress1st());
					}

					@Override
					public Address getAddress2nd() {
						return new Address(command.getAddress2nd());
					}

					@Override
					public KanaAddress getKanaAddress1st() {
						return new KanaAddress(command.getKanaAddress1st());
					}

					@Override
					public KanaAddress getKanaAddress2nd() {
						return new KanaAddress(command.getKanaAddress2nd());
					}

					@Override
					public String getPhoneNumber() {
						return command.getPhoneNumber();
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

					@Override
					public Long getVersion() {
						return 0L;
					}}
				);
		return socialInsuranceOffice;
	}
}
