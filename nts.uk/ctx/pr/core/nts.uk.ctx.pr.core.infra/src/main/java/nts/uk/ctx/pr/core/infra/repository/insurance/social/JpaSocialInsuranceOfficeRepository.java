/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.infra.repository.insurance.social;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
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
import nts.uk.ctx.pr.core.dom.insurance.social.SocialInsuranceOfficeRepository;
import nts.uk.shr.com.primitive.Memo;

/**
 * The Class JpaSocialInsuranceOfficeRepository.
 */
@Stateless
public class JpaSocialInsuranceOfficeRepository extends JpaRepository implements SocialInsuranceOfficeRepository {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pr.core.dom.insurance.social.SocialInsuranceOfficeRepository#
	 * add(nts.uk.ctx.pr.core.dom.insurance.social.SocialInsuranceOffice)
	 */
	@Override
	public void add(SocialInsuranceOffice office) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pr.core.dom.insurance.social.SocialInsuranceOfficeRepository#
	 * update(nts.uk.ctx.pr.core.dom.insurance.social.SocialInsuranceOffice)
	 */
	@Override
	public void update(SocialInsuranceOffice office) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pr.core.dom.insurance.social.SocialInsuranceOfficeRepository#
	 * remove(java.lang.String, java.lang.Long)
	 */
	@Override
	public void remove(String id, Long version) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pr.core.dom.insurance.social.SocialInsuranceOfficeRepository#
	 * findAll(int)
	 */
	@Override
	public List<SocialInsuranceOffice> findAll(String companyCode) {
		// Mock
		List<SocialInsuranceOffice> listSocialInsuranceOffice = new ArrayList<SocialInsuranceOffice>();

		SocialInsuranceOffice socialInsuranceOffice1 = new SocialInsuranceOffice(new SocialInsuranceOfficeGetMemento() {

			@Override
			public CompanyCode getCompanyCode() {
				return new CompanyCode("00000");
			}

			@Override
			public OfficeCode getCode() {
				return new OfficeCode("officeCode1");
			}

			@Override
			public OfficeName getName() {
				return new OfficeName("OfficeName1");
			}

			@Override
			public ShortName getShortName() {
				return new ShortName("sn");
			}

			@Override
			public PicName getPicName() {
				return new PicName("s");
			}

			@Override
			public PicPosition getPicPosition() {
				return new PicPosition("e");
			}

			@Override
			public PotalCode getPotalCode() {
				return new PotalCode("123124");
			}

			@Override
			public String getPrefecture() {
				return "f";
			}

			@Override
			public Address getAddress1st() {
				return new Address("add");
			}

			@Override
			public Address getAddress2nd() {
				return new Address("add");
			}

			@Override
			public KanaAddress getKanaAddress1st() {
				return new KanaAddress("addd");
			}

			@Override
			public KanaAddress getKanaAddress2nd() {
				return new KanaAddress("dd");
			}

			@Override
			public String getPhoneNumber() {
				return "0123445";
			}

			@Override
			public String getHealthInsuOfficeRefCode1st() {
				return "HealthInsuOfficeRefCode1st";
			}

			@Override
			public String getHealthInsuOfficeRefCode2nd() {
				return "HealthInsuOfficeRefCode2nd";
			}

			@Override
			public String getPensionOfficeRefCode1st() {
				return "getPensionOfficeRefCode1st";
			}

			@Override
			public String getPensionOfficeRefCode2nd() {
				return "getPensionOfficeRefCode2nd";
			}

			@Override
			public String getWelfarePensionFundCode() {
				return "getWelfarePensionFundCode";
			}

			@Override
			public String getOfficePensionFundCode() {
				return "getOfficePensionFundCode";
			}

			@Override
			public String getHealthInsuCityCode() {
				return "getHealthInsuCityCod";
			}

			@Override
			public String getHealthInsuOfficeSign() {
				return "getHealthInsuOfficeSign";
			}

			@Override
			public String getPensionCityCode() {
				return "getPensionCityCode";
			}

			@Override
			public String getPensionOfficeSign() {
				return "getPensionOfficeSign";
			}

			@Override
			public String getHealthInsuOfficeCode() {
				return "getHealthInsuOfficeCode";
			}

			@Override
			public String getHealthInsuAssoCode() {
				return "getHealthInsuAssoCode";
			}

			@Override
			public Memo getMemo() {
				return new Memo("getMemo");
			}

		});
		
		SocialInsuranceOffice socialInsuranceOffice2 = new SocialInsuranceOffice(new SocialInsuranceOfficeGetMemento() {

			@Override
			public CompanyCode getCompanyCode() {
				return new CompanyCode("00001");
			}

			@Override
			public OfficeCode getCode() {
				return new OfficeCode("officeCode2");
			}

			@Override
			public OfficeName getName() {
				return new OfficeName("OfficeName2");
			}

			@Override
			public ShortName getShortName() {
				return new ShortName("sn");
			}

			@Override
			public PicName getPicName() {
				return new PicName("s");
			}

			@Override
			public PicPosition getPicPosition() {
				return new PicPosition("e");
			}

			@Override
			public PotalCode getPotalCode() {
				return new PotalCode("12");
			}

			@Override
			public String getPrefecture() {
				return "f";
			}

			@Override
			public Address getAddress1st() {
				return new Address("");
			}

			@Override
			public Address getAddress2nd() {
				return new Address("");
			}

			@Override
			public KanaAddress getKanaAddress1st() {
				return new KanaAddress("");
			}

			@Override
			public KanaAddress getKanaAddress2nd() {
				return new KanaAddress("");
			}

			@Override
			public String getPhoneNumber() {
				return "";
			}

			@Override
			public String getHealthInsuOfficeRefCode1st() {
				return "";
			}

			@Override
			public String getHealthInsuOfficeRefCode2nd() {
				return "";
			}

			@Override
			public String getPensionOfficeRefCode1st() {
				return "";
			}

			@Override
			public String getPensionOfficeRefCode2nd() {
				return "";
			}

			@Override
			public String getWelfarePensionFundCode() {
				return "";
			}

			@Override
			public String getOfficePensionFundCode() {
				return "";
			}

			@Override
			public String getHealthInsuCityCode() {
				return "";
			}

			@Override
			public String getHealthInsuOfficeSign() {
				return "";
			}

			@Override
			public String getPensionCityCode() {
				return "";
			}

			@Override
			public String getPensionOfficeSign() {
				return "";
			}

			@Override
			public String getHealthInsuOfficeCode() {
				return "";
			}

			@Override
			public String getHealthInsuAssoCode() {
				return "";
			}

			@Override
			public Memo getMemo() {
				return new Memo("");
			}

		});
		
		SocialInsuranceOffice socialInsuranceOffice3 = new SocialInsuranceOffice(new SocialInsuranceOfficeGetMemento() {

			@Override
			public CompanyCode getCompanyCode() {
				return new CompanyCode("00002");
			}

			@Override
			public OfficeCode getCode() {
				return new OfficeCode("officeCode3");
			}

			@Override
			public OfficeName getName() {
				return new OfficeName("OfficeName3");
			}

			@Override
			public ShortName getShortName() {
				return new ShortName("sn");
			}

			@Override
			public PicName getPicName() {
				return new PicName("s");
			}

			@Override
			public PicPosition getPicPosition() {
				return new PicPosition("e");
			}

			@Override
			public PotalCode getPotalCode() {
				return new PotalCode("4363");
			}

			@Override
			public String getPrefecture() {
				return "f";
			}

			@Override
			public Address getAddress1st() {
				return new Address("");
			}

			@Override
			public Address getAddress2nd() {
				return new Address("");
			}

			@Override
			public KanaAddress getKanaAddress1st() {
				return new KanaAddress("");
			}

			@Override
			public KanaAddress getKanaAddress2nd() {
				return new KanaAddress("");
			}

			@Override
			public String getPhoneNumber() {
				return "";
			}

			@Override
			public String getHealthInsuOfficeRefCode1st() {
				return "";
			}

			@Override
			public String getHealthInsuOfficeRefCode2nd() {
				return "";
			}

			@Override
			public String getPensionOfficeRefCode1st() {
				return "";
			}

			@Override
			public String getPensionOfficeRefCode2nd() {
				return "";
			}

			@Override
			public String getWelfarePensionFundCode() {
				return "";
			}

			@Override
			public String getOfficePensionFundCode() {
				return "";
			}

			@Override
			public String getHealthInsuCityCode() {
				return "";
			}

			@Override
			public String getHealthInsuOfficeSign() {
				return "";
			}

			@Override
			public String getPensionCityCode() {
				return "";
			}

			@Override
			public String getPensionOfficeSign() {
				return "";
			}

			@Override
			public String getHealthInsuOfficeCode() {
				return "";
			}

			@Override
			public String getHealthInsuAssoCode() {
				return "";
			}

			@Override
			public Memo getMemo() {
				return new Memo("");
			}

		});
		
		listSocialInsuranceOffice.add(socialInsuranceOffice1);
		listSocialInsuranceOffice.add(socialInsuranceOffice2);
		listSocialInsuranceOffice.add(socialInsuranceOffice3);
		return listSocialInsuranceOffice;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pr.core.dom.insurance.social.SocialInsuranceOfficeRepository#
	 * findById(java.lang.String)
	 */
	@Override
	public Optional<SocialInsuranceOffice> findById(String id) {
		// TODO Mock data to send service
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pr.core.dom.insurance.social.SocialInsuranceOfficeRepository#
	 * findByOfficeCode(java.lang.String)
	 */
	@Override
	public Optional<SocialInsuranceOffice> findByOfficeCode(String officeCode) {
		// TODO Auto-generated method stub
		SocialInsuranceOffice socialInsuranceOffice = new SocialInsuranceOffice(new SocialInsuranceOfficeGetMemento() {

			@Override
			public CompanyCode getCompanyCode() {
				return new CompanyCode("0000");
			}

			@Override
			public OfficeCode getCode() {
				if (officeCode.equals("officeCode1"))
					return new OfficeCode("officeCode");
				else
					return new OfficeCode("officeCode#");
			}

			@Override
			public OfficeName getName() {
				if (officeCode.equals("officeCode1"))
					return new OfficeName("OfficeName");
				else
					return new OfficeName("OfficeName#");
			}

			@Override
			public ShortName getShortName() {
				return new ShortName("sn");
			}

			@Override
			public PicName getPicName() {
				return new PicName("s");
			}

			@Override
			public PicPosition getPicPosition() {
				return new PicPosition("e");
			}

			@Override
			public PotalCode getPotalCode() {
				return new PotalCode("123412");
			}

			@Override
			public String getPrefecture() {
				return "f";
			}

			@Override
			public Address getAddress1st() {
				return new Address("");
			}

			@Override
			public Address getAddress2nd() {
				return new Address("");
			}

			@Override
			public KanaAddress getKanaAddress1st() {
				return new KanaAddress("");
			}

			@Override
			public KanaAddress getKanaAddress2nd() {
				return new KanaAddress("");
			}

			@Override
			public String getPhoneNumber() {
				return "";
			}

			@Override
			public String getHealthInsuOfficeRefCode1st() {
				return "";
			}

			@Override
			public String getHealthInsuOfficeRefCode2nd() {
				return "";
			}

			@Override
			public String getPensionOfficeRefCode1st() {
				return "";
			}

			@Override
			public String getPensionOfficeRefCode2nd() {
				return "";
			}

			@Override
			public String getWelfarePensionFundCode() {
				return "";
			}

			@Override
			public String getOfficePensionFundCode() {
				return "";
			}

			@Override
			public String getHealthInsuCityCode() {
				return "";
			}

			@Override
			public String getHealthInsuOfficeSign() {
				return "";
			}

			@Override
			public String getPensionCityCode() {
				return "";
			}

			@Override
			public String getPensionOfficeSign() {
				return "";
			}

			@Override
			public String getHealthInsuOfficeCode() {
				return "";
			}

			@Override
			public String getHealthInsuAssoCode() {
				return "";
			}

			@Override
			public Memo getMemo() {
				return new Memo("");
			}

		});

		return Optional.of(socialInsuranceOffice);
	}

	@Override
	public boolean isDuplicateCode(OfficeCode code) {
		// TODO Auto-generated method stub
		return false;
	}

}
