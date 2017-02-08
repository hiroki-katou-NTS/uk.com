package nts.uk.ctx.pr.core.app.insurance.labor.find;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
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
}
