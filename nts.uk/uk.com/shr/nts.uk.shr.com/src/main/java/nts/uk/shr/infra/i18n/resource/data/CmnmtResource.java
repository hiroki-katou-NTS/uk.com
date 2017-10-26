package nts.uk.shr.infra.i18n.resource.data;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "CMNMT_RESOURCE")
public class CmnmtResource {
	@EmbeddedId
	private CmnmtResourcePK primaryKey;
	@Column(name = "ITEM_CONTENT")
	private String content;
	
}
