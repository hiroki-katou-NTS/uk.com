package nts.uk.ctx.exio.infra.entity.input.validation;

import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.val;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.infra.data.jdbc.map.JpaEntityMapper;
import nts.uk.ctx.exio.dom.input.importableitem.CheckMethod;
import nts.uk.ctx.exio.dom.input.importableitem.DomainConstraint;
import nts.uk.ctx.exio.dom.input.importableitem.ImportableItem;
import nts.uk.ctx.exio.dom.input.importableitem.ItemType;
import nts.uk.ctx.exio.dom.input.importableitem.group.ImportingGroupId;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

import java.io.Serializable;
import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

/**
 * 受入可能項目の定義 
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Table(name = "OIOCT_IMPORTABLE_ITEM")
public class OioctImportableItem extends ContractUkJpaEntity 		implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private OioctImportableItemPK pk;
	
	@Column(name = "ITEM_TYPE")
	private int itemType;
	
	@Column(name = "REQUIRED")
	private boolean required;
	
	@Column(name = "CHECK_METHOD")
	private Integer checkMethod;
	
	@Column(name = "FQN")
	private String fqn;
	
	@Override
	protected Object getKey() {
		return pk;
	}
	
	public static final JpaEntityMapper<OioctImportableItem> MAPPER = new JpaEntityMapper<>(OioctImportableItem.class);
	
	public ImportableItem toDomain() {
		Optional<DomainConstraint> constraint = Optional.empty();
		if(this.checkMethod != null && this.fqn != null) {
			constraint = Optional.of(
						new DomainConstraint(EnumAdaptor.valueOf(this.checkMethod, CheckMethod.class),
															fqn)
					);
		}
		
		return new ImportableItem(
				ImportingGroupId.valueOf(pk.getGroupdId()),
				pk.getItemNo(),
				EnumAdaptor.valueOf(itemType, ItemType.class),
				required,
				constraint);
	}
	
	public static OioctImportableItem fromDomain(String cid, ImportableItem target) {
		val pk = new OioctImportableItemPK(
					cid,
					target.getGroupId().value,
					target.getItemNo()
				);
		
		Integer checkMethod = null;
		String fqn = null;
		if(target.getDomainConstraint().isPresent()) {
			checkMethod = target.getDomainConstraint().get().getCheckMethod().value;
			fqn = target.getDomainConstraint().get().getFqn();
		}
		
		return new OioctImportableItem(
				pk, 
				target.getItemType().value, 
				target.isRequired(),
				checkMethod, 
				fqn);
	}
}
