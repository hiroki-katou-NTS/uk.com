package nts.uk.ctx.pr.core.app.insurance.labor.command.dto;

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

@Data
public class LaborInsuranceOfficeDto {
	/** The code. officeCode */
	private String code;

	/** The name. officeName */
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
	private String memo;

	/** The version. */
	private long version;

	public LaborInsuranceOffice toDomain(String companyCode) {
		LaborInsuranceOfficeDto dto = this;
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
				return dto.officeNoC;
			}

			@Override
			public String getOfficeNoB() {
				return dto.officeNoB;
			}

			@Override
			public String getOfficeNoA() {
				return dto.officeNoA;
			}

			@Override
			public String getOfficeMark() {
				return dto.officeMark;
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
				return dto.citySign;
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
