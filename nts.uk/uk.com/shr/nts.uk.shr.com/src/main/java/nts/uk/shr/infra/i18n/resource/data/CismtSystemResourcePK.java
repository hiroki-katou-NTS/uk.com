package nts.uk.shr.infra.i18n.resource.data;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Embeddable
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class CismtSystemResourcePK {

	/**
	 * @see: SystemProperties ,if is used for all program it will be "SYSTEM"
	 */
	public static final String PROGRAM_ID_FOR_ALL = "SYSTEM";
	
	@Column(name = "CODE")
	public String code;
	
	@Column(name = "LANGUAGE_CODE")
	public String languageId;
	
	@Column(name = "PROGRAM_ID")
	public String programId;
	
	public static CismtSystemResourcePK createForAllPrograms(String code, String languageId) {
		return new CismtSystemResourcePK(code, languageId, PROGRAM_ID_FOR_ALL);
	}
}
