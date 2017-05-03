package nts.uk.shr.infra.i18n.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;
import nts.arc.i18n.custom.ResourceType;

@Entity
@Table(name = "CISMT_SYSTEM_RESOURCE")
@Getter
@Setter
public class SystemResource {
	@Id
	private String code;

	@Column(name = "ITEM_CONTENT")
	private String content;

	@Enumerated(EnumType.ORDINAL)
	@Column(name = "RESOURCE_TYPE")
	private ResourceType type;

	@Column(name = "LANGUAGE_CODE")
	private String languageCode;
	/**
	 * @see: SystemProperties ,if is used for all program it will be "SYSTEM"
	 */
	@NotNull
	@Column(name = "PROGRAM_ID")
	private String programId;

	// use for 予約
	@Column
	private boolean customizable;

}
