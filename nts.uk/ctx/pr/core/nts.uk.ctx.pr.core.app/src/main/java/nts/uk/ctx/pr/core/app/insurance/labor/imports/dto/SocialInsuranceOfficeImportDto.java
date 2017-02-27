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
	public String code;

	/** The name. */
	public String name;

	/** The short name. */
	public String shortName;

	/** The pic name. */
	public String picName;

	/** The pic position. */
	public String picPosition;

	/** The potal code. */
	public String potalCode;

	/** The prefecture. */
	public String prefecture;

	/** The address 1 st. */
	public String address1st;

	/** The address 2 nd. */
	public String address2nd;

	/** The kana address 1 st. */
	public String kanaAddress1st;

	/** The kana address 2 nd. */
	public String kanaAddress2nd;

	/** The phone number. */
	public String phoneNumber;

	/** The health insu office ref code 1 st. */
	public String healthInsuOfficeRefCode1st;

	/** The health insu office ref code 2 nd. */
	public String healthInsuOfficeRefCode2nd;

	/** The pension office ref code 1 st. */
	public String pensionOfficeRefCode1st;

	/** The pension office ref code 2 nd. */
	public String pensionOfficeRefCode2nd;

	/** The welfare pension fund code. */
	public String welfarePensionFundCode;

	/** The office pension fund code. */
	public String officePensionFundCode;

	/** The health insu city code. */
	public String healthInsuCityCode;

	/** The health insu office sign. */
	public String healthInsuOfficeSign;

	/** The pension city code. */
	public String pensionCityCode;

	/** The pension office sign. */
	public String pensionOfficeSign;

	/** The health insu office code. */
	public String healthInsuOfficeCode;

	/** The health insu asso code. */
	public String healthInsuAssoCode;

	/** The memo. */
	public String memo;
	
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
				return dto.healthInsuCityCode;
			}

			@Override
			public String getOfficeNoB() {
				return dto.healthInsuOfficeRefCode2nd;
			}

			@Override
			public String getOfficeNoA() {
				return dto.healthInsuOfficeRefCode1st;
			}

			@Override
			public String getOfficeMark() {
				return dto.healthInsuAssoCode;
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
