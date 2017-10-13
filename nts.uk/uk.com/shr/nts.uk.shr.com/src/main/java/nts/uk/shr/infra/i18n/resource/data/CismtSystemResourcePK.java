package nts.uk.shr.infra.i18n.resource.data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Embeddable
@Setter
@Getter
public class CismtSystemResourcePK {
	@Column(name = "CODE")
	private String code;
	@Column(name = "LANGUAGE_CODE")
	private String languageCode;
	/**
	 * @see: SystemProperties ,if is used for all program it will be "SYSTEM"
	 */
	@NotNull
	@Column(name = "PROGRAM_ID")
	private String programId;
}
