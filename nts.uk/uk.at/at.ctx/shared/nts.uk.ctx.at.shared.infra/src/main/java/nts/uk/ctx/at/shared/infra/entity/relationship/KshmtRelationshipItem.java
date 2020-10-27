package nts.uk.ctx.at.shared.infra.entity.relationship;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KSHMT_RELATIONSHIP")
/**
 * @author sonnlb
 */
// 続柄
public class KshmtRelationshipItem extends ContractUkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@EmbeddedId
	public KshmtRelationshipPK kshmtRelationshipPK;
	/* 名称 */
	@Column(name = "RELATIONSHIP_NAME")
	public String relationshipName;

	/* 3親等以内とする */
	@Column(name = "THREE_PARENT_OR_LESS")
	public boolean threeParentOrLess;

	@Override
	protected Object getKey() {
		return kshmtRelationshipPK;
	}

}
