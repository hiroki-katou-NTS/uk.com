/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.infra.repository.insurance.social;

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
	public List<SocialInsuranceOffice> findAll(int companyCode) {
		// TODO Auto-generated method stub
		return null;
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
		SocialInsuranceOffice socialInsuranceOffice = new SocialInsuranceOffice(new SocialInsuranceOfficeGetMemento() {

			@Override
			public CompanyCode getCompanyCode() {
				return new CompanyCode("0000");
			}

			@Override
			public OfficeCode getCode() {
				return new OfficeCode("officeCode");
			}

			@Override
			public OfficeName getName() {
				return new OfficeName("OfficeName");
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
				return new PotalCode("t");
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

			@Override
			public Long getVersion() {
				return 0L;
			}
		});

		return Optional.of(socialInsuranceOffice);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pr.core.dom.insurance.social.SocialInsuranceOfficeRepository#
	 * findByOfficeCode(java.lang.String)
	 */
	@Override
	public SocialInsuranceOffice findByOfficeCode(String officeCode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isDuplicateCode(OfficeCode code) {
		// TODO Auto-generated method stub
		return false;
	}

}
