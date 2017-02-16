/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.infra.repository.insurance.labor;

import java.util.ArrayList;
import java.util.List;

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
import nts.uk.ctx.pr.core.dom.insurance.labor.LaborInsuranceOffice;
import nts.uk.ctx.pr.core.dom.insurance.labor.LaborInsuranceOfficeGetMemento;
import nts.uk.ctx.pr.core.dom.insurance.labor.LaborInsuranceOfficeRepository;
import nts.uk.shr.com.primitive.Memo;

/**
 * The Class JpaLaborInsuranceOfficeRepository.
 */
@Stateless
public class JpaLaborInsuranceOfficeRepository extends JpaRepository implements LaborInsuranceOfficeRepository {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pr.core.dom.insurance.labor.LaborInsuranceOfficeRepository#add
	 * (nts.uk.ctx.pr.core.dom.insurance.labor.LaborInsuranceOffice)
	 */
	@Override
	public void add(LaborInsuranceOffice office) {
		// TODO Auto-generated method stub
		

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pr.core.dom.insurance.labor.LaborInsuranceOfficeRepository#
	 * update(nts.uk.ctx.pr.core.dom.insurance.labor.LaborInsuranceOffice)
	 */
	@Override
	public void update(LaborInsuranceOffice office) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pr.core.dom.insurance.labor.LaborInsuranceOfficeRepository#
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
	 * nts.uk.ctx.pr.core.dom.insurance.labor.LaborInsuranceOfficeRepository#
	 * findAll(java.lang.String)
	 */
	@Override
	public List<LaborInsuranceOffice> findAll(CompanyCode companyCode) {
		return findAllDataDemo(companyCode);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pr.core.dom.insurance.labor.LaborInsuranceOfficeRepository#
	 * findById(java.lang.String, java.lang.String)
	 */
	@Override
	public LaborInsuranceOffice findById(CompanyCode companyCode, OfficeCode officeCode) {
		// TODO Auto-generated method stub
		List<LaborInsuranceOffice> lstLaborInsuranceOffice = findAllDataDemo(companyCode);
		for (LaborInsuranceOffice laborInsuranceOffice : lstLaborInsuranceOffice) {
			if (officeCode.equals(laborInsuranceOffice.getCode())) {
				return laborInsuranceOffice;
			}
		}
		return null;
	}

	/*
	 * DEMO SEARCH DATA
	 */
	public List<LaborInsuranceOffice> findAllDataDemo(CompanyCode companyCode) {
		List<LaborInsuranceOffice> lstLaborInsuranceOffice = new ArrayList<>();
		lstLaborInsuranceOffice.add(fromDomain(companyCode, "XA事業所", "000000000001"));
		lstLaborInsuranceOffice.add(fromDomain(companyCode, "B事業所", "000000000002"));
		lstLaborInsuranceOffice.add(fromDomain(companyCode, "C事業所", "000000000003"));
		return lstLaborInsuranceOffice;

	}

	public LaborInsuranceOffice fromDomain(CompanyCode companyCode, String officeName, String officeCode) {
		return new LaborInsuranceOffice(new LaborInsuranceOfficeGetMemento() {

			@Override
			public ShortName getShortName() {
				// TODO Auto-generated method stub
				return new ShortName("ShortName");
			}

			@Override
			public String getPrefecture() {
				// TODO Auto-generated method stub
				return "Prefecture";
			}

			@Override
			public PotalCode getPotalCode() {
				// TODO Auto-generated method stub
				return new PotalCode("PotalCode");
			}

			@Override
			public PicPosition getPicPosition() {
				// TODO Auto-generated method stub
				return new PicPosition("PicPosition");
			}

			@Override
			public PicName getPicName() {
				// TODO Auto-generated method stub
				return new PicName("PicName");
			}

			@Override
			public String getPhoneNumber() {
				// TODO Auto-generated method stub
				return "PhoneNumber";
			}

			@Override
			public String getOfficeNoC() {
				// TODO Auto-generated method stub
				return "1";
			}

			@Override
			public String getOfficeNoB() {
				// TODO Auto-generated method stub
				return "567890";
			}

			@Override
			public String getOfficeNoA() {
				// TODO Auto-generated method stub
				return "01234";
			}

			@Override
			public String getOfficeMark() {
				// TODO Auto-generated method stub
				return "OfficeMark";
			}

			@Override
			public OfficeName getName() {
				// TODO Auto-generated method stub
				return new OfficeName(officeName);
			}

			@Override
			public Memo getMemo() {
				// TODO Auto-generated method stub
				return new Memo("Memo");
			}

			@Override
			public KanaAddress getKanaAddress2nd() {
				// TODO Auto-generated method stub
				return new KanaAddress("KanaAddress2nd");
			}

			@Override
			public KanaAddress getKanaAddress1st() {
				// TODO Auto-generated method stub
				return new KanaAddress("KanaAddress1st");
			}

			@Override
			public CompanyCode getCompanyCode() {
				// TODO Auto-generated method stub
				return companyCode;
			}

			@Override
			public OfficeCode getCode() {
				// TODO Auto-generated method stub
				return new OfficeCode(officeCode);
			}

			@Override
			public String getCitySign() {
				// TODO Auto-generated method stub
				return "01";
			}

			@Override
			public Address getAddress2nd() {
				// TODO Auto-generated method stub
				return new Address("Address2nd");
			}

			@Override
			public Address getAddress1st() {
				// TODO Auto-generated method stub
				return new Address("Address1st");
			}
		});
	}

	@Override
	public boolean isDuplicateCode(CompanyCode companyCode, OfficeCode code) {
		// TODO Auto-generated method stub
		return false;
	}

}
