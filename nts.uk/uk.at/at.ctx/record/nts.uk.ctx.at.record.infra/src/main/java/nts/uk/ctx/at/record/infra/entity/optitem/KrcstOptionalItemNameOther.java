package nts.uk.ctx.at.record.infra.entity.optitem;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.scherec.optitem.OptionalItemName;
import nts.uk.ctx.at.shared.dom.scherec.optitem.OptionalItemNameOther;
import nts.uk.ctx.at.shared.dom.scherec.optitem.OptionalItemNo;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@Getter
@Setter
@Entity
@Table(name = "KRCST_OPT_ITEM_NAME_OTHER")
@AllArgsConstructor
@NoArgsConstructor
public class KrcstOptionalItemNameOther extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	protected KrcstOptionalItemNameOtherPK krcstOptItemNamePK;

	/** The optional item name. */
	@Column(name = "OPTIONAL_ITEM_NAME")
	private String optionalItemName;

	@Override
	protected Object getKey() {
		return this.krcstOptItemNamePK;
	}
	
	public static KrcstOptionalItemNameOther toEntity(OptionalItemNameOther domain) {
		return new KrcstOptionalItemNameOther(new KrcstOptionalItemNameOtherPK(domain.getCompanyId().v(),
				domain.getOptionalItemNo().v(), domain.getLangId()), domain.getOptionalItemName().v());
	}
	
	public OptionalItemNameOther toDomain() {
		return new OptionalItemNameOther(new CompanyId(this.krcstOptItemNamePK.cid),
				new OptionalItemNo(krcstOptItemNamePK.optionalItemNo), krcstOptItemNamePK.langId,
				new OptionalItemName(optionalItemName));

	}
}
