package nts.uk.ctx.at.shared.infra.entity.specialholiday.grantrelationship;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.infra.entity.specialholiday.KshstGrantSingle;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * @author yennth
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KSHST_GRANT_RELATIONSHIP")
public class KshstGrantRelationshipItem extends UkJpaEntity implements Serializable{
	private static final long serialVersionUID = 1L;
	@EmbeddedId
	public KshstGrantRelationshipPK kshstGrantRelationshipPK;
	/* 付与日数 */
	@Column(name = "GRANT_RELATIONSHIP_DAYS")
	public int grantRelationshipDay;
	/* 喪主時加算日数 */
	@Column(name = "MORNING_HOUR")
	public Integer morningHour;
	@ManyToOne
	@JoinColumns({
		@JoinColumn(name = "CID", referencedColumnName="CID", insertable = false, updatable = false),
		@JoinColumn(name = "SPHD_CD", referencedColumnName="SPHD_CD", insertable = false, updatable = false)
	})
public KshstGrantSingle grantSingle;
	@Override
	protected Object getKey() {
		return kshstGrantRelationshipPK;
	}
}
