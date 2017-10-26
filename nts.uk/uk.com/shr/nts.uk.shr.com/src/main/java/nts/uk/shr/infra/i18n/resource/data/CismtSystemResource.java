package nts.uk.shr.infra.i18n.resource.data;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import nts.uk.shr.infra.i18n.resource.I18NResourceType;

@Entity
@Table(name = "CISMT_SYSTEM_RESOURCE")
public class CismtSystemResource {
	
	@EmbeddedId
	public CismtSystemResourcePK pk;

	@Column(name = "ITEM_CONTENT")
	public String content;

	@Column(name = "RESOURCE_TYPE")
	public int resourceType;

	// use for 予約
	@Column
	public boolean customizable;
}
