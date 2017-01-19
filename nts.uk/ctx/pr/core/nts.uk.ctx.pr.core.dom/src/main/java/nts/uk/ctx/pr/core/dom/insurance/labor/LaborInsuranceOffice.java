/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.insurance.labor;

import lombok.Data;
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
@Data
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

	/**
	 * Instantiates a new labor insurance office.
	 */
	public LaborInsuranceOffice() {
		super();
	}

	// =================== Memento State Support Method ===================

	/**
	 * Instantiates a new labor insurance office.
	 *
	 * @param memento
	 *            the memento
	 */
	public LaborInsuranceOffice(LaborInsuranceOfficeMemento memento) {
		this.companyCode = memento.getCompanyCode();
		this.code = memento.getCode();
		this.name = memento.getName();
		this.shortName = memento.getShortName();
		this.picName = memento.getPicName();
		this.picPosition = memento.getPicPosition();
		this.potalCode = memento.getPotalCode();
		this.prefecture = memento.getPrefecture();
		this.address1st = memento.getAddress1st();
		this.address2nd = memento.getAddress2nd();
		this.kanaAddress1st = memento.getKanaAddress1st();
		this.kanaAddress2nd = memento.getKanaAddress2nd();
		this.phoneNumber = memento.getPhoneNumber();
		this.citySign = memento.getCitySign();
		this.officeMark = memento.getOfficeMark();
		this.officeNoA = memento.getOfficeNoA();
		this.officeNoB = memento.getOfficeNoB();
		this.officeNoC = memento.getOfficeNoC();
		this.memo = memento.getMemo();
		this.setVersion(memento.getVersion());
	}

	/**
	 * Save to memento.
	 *
	 * @param memento
	 *            the memento
	 */
	public void saveToMemento(LaborInsuranceOfficeMemento memento) {
		memento.setCompanyCode(this.companyCode);
		memento.setCode(this.code);
		memento.setName(this.name);
		memento.setShortName(this.shortName);
		memento.setPicName(this.picName);
		memento.setPicPosition(this.picPosition);
		memento.setPotalCode(this.potalCode);
		memento.setPrefecture(this.prefecture);
		memento.setAddress1st(this.address1st);
		memento.setAddress2nd(this.address2nd);
		memento.setKanaAddress1st(this.kanaAddress1st);
		memento.setKanaAddress2nd(this.kanaAddress2nd);
		memento.setPhoneNumber(this.phoneNumber);
		memento.setCitySign(this.citySign);
		memento.setOfficeMark(this.officeMark);
		memento.setOfficeNoA(this.officeNoA);
		memento.setOfficeNoB(this.officeNoB);
		memento.setOfficeNoC(this.officeNoC);
		memento.setMemo(this.memo);
		memento.setVersion(this.getVersion());
	}

}
