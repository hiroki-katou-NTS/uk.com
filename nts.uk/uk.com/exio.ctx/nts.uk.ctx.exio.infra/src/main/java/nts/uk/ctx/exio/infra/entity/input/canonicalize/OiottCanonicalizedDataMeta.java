package nts.uk.ctx.exio.infra.entity.input.canonicalize;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.val;
import nts.uk.ctx.exio.dom.input.canonicalize.CanonicalizedDataMeta;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "OIOTT_CANONICALIZED_DATA_META")
public class OiottCanonicalizedDataMeta extends ContractUkJpaEntity implements Serializable{
	private static final long serialVersionUID = 1L;

	/** 会社ID */
	@Id
	@Column(name = "CID")
	public String cid;

	@OneToMany(targetEntity = OiottImportingItem.class, mappedBy = "canonicalizedDataMeta", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	public List<OiottImportingItem> items;
	
	@Override
	protected Object getKey() {
		return this.cid;
	}
	
	public CanonicalizedDataMeta toDomain() {
		val importingItemNames = this.items.stream()
				.map(item -> item.name)
				.collect(Collectors.toList());
		return new CanonicalizedDataMeta(
				this.cid,
				importingItemNames
			);
	}
}
