package nts.uk.ctx.at.record.infra.entity.jobmanagement.favoritetask.favoritetaskitem;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.jobmanagement.favoritetask.favoritetaskitem.FavoriteDisplayOrder;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.infra.data.entity.ContractCompanyUkJpaEntity;

/**
 * お気に入り作業の表示順 FavoriteTaskDisplayOrder
 * 
 * @author tutt
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KRCDT_TASK_FAV_FRAME_SET_DISPORDER")
public class KrcdtTaskFavFrameSetDisporder extends ContractCompanyUkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public KrcdtTaskFavFrameSetDisporderPk pk;

	@Column(name = "SID")
	public String sId;

	@Override
	protected Object getKey() {
		return this.pk;
	}

	public FavoriteDisplayOrder toDomain() {
		return new FavoriteDisplayOrder(this.pk.favId, this.pk.disporder);
	}

	public KrcdtTaskFavFrameSetDisporder(String sId, FavoriteDisplayOrder order) {
		this.sId = sId;
		this.pk = new KrcdtTaskFavFrameSetDisporderPk(order.getFavId(), order.getOrder());
	}

}
