package nts.uk.ctx.at.shared.infra.entity.grantrelationship;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KSHST_GRANT_RELATIONSHIP")
/**
 * @author yennth
 */
public class KshstGrantRelationshipItem extends UkJpaEntity implements Serializable{
	private static final long serialVersionUID = 1L;
	@EmbeddedId
	public KshstGrantRelationshipPK kshstGrantRelationshipPK;
	/* 付与日数 */
	@Column(name = "GRANT_RELATIONSHIP_DAYS")
	public int grantRelationshipDay;
	/* 喪主時加算日数 */
	@Column(name = "MORNING_HOUR")
	public int morningHour;
	@Override
	protected Object getKey() {
		return kshstGrantRelationshipPK;
	}
}
