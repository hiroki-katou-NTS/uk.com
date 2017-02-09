/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.insurance.social;

import lombok.Data;
import nts.arc.error.BusinessException;
import nts.arc.layer.dom.AggregateRoot;
import nts.gul.text.StringUtil;
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
 * The Class SocialInsuranceOffice.
 */
// TODO: @Data -> @Getter
@Data
public class SocialInsuranceOffice extends AggregateRoot {

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

	/** The health insu office ref code 1 st. */
	private String healthInsuOfficeRefCode1st;

	/** The health insu office ref code 2 nd. */
	private String healthInsuOfficeRefCode2nd;

	/** The pension office ref code 1 st. */
	private String pensionOfficeRefCode1st;

	/** The pension office ref code 2 nd. */
	private String pensionOfficeRefCode2nd;

	/** The welfare pension fund code. */
	private String welfarePensionFundCode;

	/** The office pension fund code. */
	private String officePensionFundCode;

	/** The health insu city code. */
	private String healthInsuCityCode;

	/** The health insu office sign. */
	private String healthInsuOfficeSign;

	/** The pension city code. */
	private String pensionCityCode;

	/** The pension office sign. */
	private String pensionOfficeSign;

	/** The health insu office code. */
	private String healthInsuOfficeCode;

	/** The health insu asso code. */
	private String healthInsuAssoCode;

	/** The memo. */
	private Memo memo;

	/**
	 * Instantiates a new social insurance office.
	 */
	public SocialInsuranceOffice() {
		super();
	}

	/**
	 * @param companyCode
	 * @param code
	 * @param name
	 * @param shortName
	 * @param picName
	 * @param picPosition
	 * @param potalCode
	 * @param prefecture
	 * @param address1st
	 * @param address2nd
	 * @param kanaAddress1st
	 * @param kanaAddress2nd
	 * @param phoneNumber
	 * @param healthInsuOfficeRefCode1st
	 * @param healthInsuOfficeRefCode2nd
	 * @param pensionOfficeRefCode1st
	 * @param pensionOfficeRefCode2nd
	 * @param welfarePensionFundCode
	 * @param officePensionFundCode
	 * @param healthInsuCityCode
	 * @param healthInsuOfficeSign
	 * @param pensionCityCode
	 * @param pensionOfficeSign
	 * @param healthInsuOfficeCode
	 * @param healthInsuAssoCode
	 * @param memo
	 */
	public SocialInsuranceOffice(CompanyCode companyCode, OfficeCode code, OfficeName name, ShortName shortName,
			PicName picName, PicPosition picPosition, PotalCode potalCode, String prefecture, Address address1st,
			Address address2nd, KanaAddress kanaAddress1st, KanaAddress kanaAddress2nd, String phoneNumber,
			String healthInsuOfficeRefCode1st, String healthInsuOfficeRefCode2nd, String pensionOfficeRefCode1st,
			String pensionOfficeRefCode2nd, String welfarePensionFundCode, String officePensionFundCode,
			String healthInsuCityCode, String healthInsuOfficeSign, String pensionCityCode, String pensionOfficeSign,
			String healthInsuOfficeCode, String healthInsuAssoCode, Memo memo) {
		super();

		// Validate required item
		if (StringUtil.isNullOrEmpty(code.v(), true) || StringUtil.isNullOrEmpty(name.v(), true)
				|| StringUtil.isNullOrEmpty(picPosition.v(), true)) {
			throw new BusinessException("ER001");
		}

		// TODO: Office code duplication check
		// throw new BusinessException("ER005");

		this.companyCode = companyCode;
		this.code = code;
		this.name = name;
		this.shortName = shortName;
		this.picName = picName;
		this.picPosition = picPosition;
		this.potalCode = potalCode;
		this.prefecture = prefecture;
		this.address1st = address1st;
		this.address2nd = address2nd;
		this.kanaAddress1st = kanaAddress1st;
		this.kanaAddress2nd = kanaAddress2nd;
		this.phoneNumber = phoneNumber;
		this.healthInsuOfficeRefCode1st = healthInsuOfficeRefCode1st;
		this.healthInsuOfficeRefCode2nd = healthInsuOfficeRefCode2nd;
		this.pensionOfficeRefCode1st = pensionOfficeRefCode1st;
		this.pensionOfficeRefCode2nd = pensionOfficeRefCode2nd;
		this.welfarePensionFundCode = welfarePensionFundCode;
		this.officePensionFundCode = officePensionFundCode;
		this.healthInsuCityCode = healthInsuCityCode;
		this.healthInsuOfficeSign = healthInsuOfficeSign;
		this.pensionCityCode = pensionCityCode;
		this.pensionOfficeSign = pensionOfficeSign;
		this.healthInsuOfficeCode = healthInsuOfficeCode;
		this.healthInsuAssoCode = healthInsuAssoCode;
		this.memo = memo;
	}

	// =================== Memento State Support Method ===================

	/**
	 * Instantiates a new social insurance office.
	 *
	 * @param memento
	 *            the memento
	 */
	public SocialInsuranceOffice(SocialInsuranceOfficeGetMemento memento) {
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
		this.healthInsuOfficeRefCode1st = memento.getHealthInsuOfficeRefCode1st();
		this.healthInsuOfficeRefCode2nd = memento.getHealthInsuOfficeRefCode2nd();
		this.pensionOfficeRefCode1st = memento.getPensionOfficeRefCode1st();
		this.pensionOfficeRefCode2nd = memento.getPensionOfficeRefCode2nd();
		this.welfarePensionFundCode = memento.getWelfarePensionFundCode();
		this.officePensionFundCode = memento.getOfficePensionFundCode();
		this.healthInsuCityCode = memento.getHealthInsuCityCode();
		this.healthInsuOfficeSign = memento.getHealthInsuOfficeSign();
		this.pensionCityCode = memento.getPensionCityCode();
		this.pensionOfficeSign = memento.getPensionOfficeSign();
		this.healthInsuOfficeCode = memento.getHealthInsuOfficeCode();
		this.healthInsuAssoCode = memento.getHealthInsuAssoCode();
		this.memo = memento.getMemo();
		this.setVersion(memento.getVersion());
	}

	/**
	 * Save to memento.
	 *
	 * @param memento
	 *            the memento
	 */
	public void saveToMemento(SocialInsuranceOfficeSetMemento memento) {
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
		memento.setHealthInsuOfficeRefCode1st(this.healthInsuOfficeRefCode1st);
		memento.setHealthInsuOfficeRefCode2nd(this.healthInsuOfficeRefCode2nd);
		memento.setPensionOfficeRefCode1st(this.pensionOfficeRefCode1st);
		memento.setPensionOfficeRefCode2nd(this.pensionOfficeRefCode2nd);
		memento.setWelfarePensionFundCode(this.welfarePensionFundCode);
		memento.setOfficePensionFundCode(this.officePensionFundCode);
		memento.setHealthInsuCityCode(this.healthInsuCityCode);
		memento.setHealthInsuOfficeSign(this.healthInsuOfficeSign);
		memento.setPensionCityCode(this.pensionCityCode);
		memento.setPensionOfficeSign(this.pensionOfficeSign);
		memento.setHealthInsuOfficeCode(this.healthInsuOfficeCode);
		memento.setHealthInsuAssoCode(this.healthInsuAssoCode);
		memento.setMemo(this.memo);
		memento.setVersion(this.getVersion());
	}

}
