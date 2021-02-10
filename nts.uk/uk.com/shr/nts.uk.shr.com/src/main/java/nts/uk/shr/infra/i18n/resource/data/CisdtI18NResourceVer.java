package nts.uk.shr.infra.i18n.resource.data;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDateTime;

@Getter
@Setter
@Entity
@Table(name = "CISMT_I18N_RESOURCE_VER")
public class CisdtI18NResourceVer {
	
	@EmbeddedId
	public CisdtI18NResourceVerPK pk;

	@Column(name = "LAST_UPDATED_AT")
	public GeneralDateTime lastUpdatedAt;
	
}
