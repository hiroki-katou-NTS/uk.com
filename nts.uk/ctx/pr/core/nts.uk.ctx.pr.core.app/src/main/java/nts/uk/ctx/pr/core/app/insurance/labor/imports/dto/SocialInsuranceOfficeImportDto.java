/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.app.insurance.labor.imports.dto;

import lombok.Data;
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
import nts.uk.shr.com.primitive.Memo;

/**
 * Instantiates a new social insurance office import dto.
 */
@Data
public class SocialInsuranceOfficeImportDto {

	/** The code. */
	private String code;

	/** The name. */
	private String name;

	/** The short name. */
	private String shortName;

	/** The pic name. */
	private String picName;

	/** The pic position. */
	private String picPosition;

	/** The potal code. */
	private String potalCode;

	/** The prefecture. */
	private String prefecture;

	/** The address 1 st. */
	private String address1st;

	/** The address 2 nd. */
	private String address2nd;

	/** The kana address 1 st. */
	private String kanaAddress1st;

	/** The kana address 2 nd. */
	private String kanaAddress2nd;

	/** The phone number. */
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
	private String memo;

	/**
	 * To domain.
	 *
	 * @param companyCode
	 *            the company code
	 * @return the labor insurance office
	 */
	public LaborInsuranceOffice toDomain(String companyCode) {
		SocialInsuranceOfficeImportDto dto = this;
		return new LaborInsuranceOffice(new LaborInsuranceOfficeGetMemento() {

			@Override
			public ShortName getShortName() {
				return new ShortName(dto.shortName);
			}

			@Override
			public String getPrefecture() {
				return dto.prefecture;
			}

			@Override
			public PotalCode getPotalCode() {
				return new PotalCode(dto.potalCode);
			}

			@Override
			public PicPosition getPicPosition() {
				return new PicPosition(dto.picPosition);
			}

			@Override
			public PicName getPicName() {
				return new PicName(dto.picName);
			}

			@Override
			public String getPhoneNumber() {
				return dto.phoneNumber;
			}

			@Override
			public String getOfficeNoC() {
				return null;
				//return dto.healthInsuCityCode;
			}

			@Override
			public String getOfficeNoB() {
				return null;
				//return dto.healthInsuOfficeRefCode2nd;
			}

			@Override
			public String getOfficeNoA() {
				return null;
				//return dto.healthInsuOfficeRefCode1st;
			}

			@Override
			public String getOfficeMark() {
				return null;
				//return dto.healthInsuAssoCode;
			}

			@Override
			public OfficeName getName() {
				return new OfficeName(dto.name);
			}

			@Override
			public Memo getMemo() {
				return new Memo(dto.memo);
			}

			@Override
			public KanaAddress getKanaAddress2nd() {
				return new KanaAddress(dto.kanaAddress2nd);
			}

			@Override
			public KanaAddress getKanaAddress1st() {
				return new KanaAddress(dto.kanaAddress1st);
			}

			@Override
			public CompanyCode getCompanyCode() {
				return new CompanyCode(companyCode);
			}

			@Override
			public OfficeCode getCode() {
				return new OfficeCode(dto.code);
			}

			@Override
			public String getCitySign() {
				return dto.healthInsuCityCode;
			}

			@Override
			public Address getAddress2nd() {
				return new Address(dto.address2nd);
			}

			@Override
			public Address getAddress1st() {
				return new Address(dto.address1st);
			}
		});
	}
}
