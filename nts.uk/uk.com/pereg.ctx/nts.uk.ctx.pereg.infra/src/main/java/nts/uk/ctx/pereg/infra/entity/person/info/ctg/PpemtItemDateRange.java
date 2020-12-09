package nts.uk.ctx.pereg.infra.entity.person.info.ctg;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;
import nts.arc.layer.infra.data.jdbc.map.JpaEntityMapper;
import nts.uk.ctx.pereg.infra.repository.mastercopy.helper.IdContainer;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "PPEMT_ITEM_DATE_RANGE")
public class PpemtItemDateRange extends ContractUkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public static final JpaEntityMapper<PpemtItemDateRange> MAPPER = new JpaEntityMapper<>(PpemtItemDateRange.class);

	@EmbeddedId
	public PpemtPerInfoCtgPK ppemtPerInfoCtgPK;

	@Basic(optional = false)
	@Column(name = "START_DATE_ITEM_ID")
	public String startDateItemId;

	@Basic(optional = false)
	@Column(name = "END_DATE_ITEM_ID")
	public String endDateItemId;

	@Basic(optional = false)
	@Column(name = "DATE_RANGE_ITEM_ID")
	public String dateRangeItemId;

	@Override
	protected Object getKey() {
		return ppemtPerInfoCtgPK;
	}
	
	public PpemtItemDateRange copy(IdContainer idContainer) {
		
		String copiedCategoryId = idContainer.getCategoryIds().getFor(ppemtPerInfoCtgPK.perInfoCtgId);
		
		return new PpemtItemDateRange(
				new PpemtPerInfoCtgPK(copiedCategoryId),
				idContainer.getItemIds().getFor(startDateItemId),
				idContainer.getItemIds().getFor(endDateItemId),
				idContainer.getItemIds().getFor(dateRangeItemId));
	}
}
