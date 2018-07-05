package nts.uk.ctx.at.shared.infra.entity.specialholiday.specialholidayevent;

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
@Table(name = "KSHST_RELATIONSHIP")
// 続柄
public class KshstRelationship extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public KshstRelationshipPK pk;

	/* 名称 */
	@Column(name = "NAME")
	public String name;

	/* 3親等以内とする */
	@Column(name = "THREE_PARENT_OR_LESS")
	public int threeParentOrLess;

	@Override
	protected Object getKey() {
		return this.pk;
	}

}
