package nts.uk.ctx.at.record.infra.entity.jobmanagement.favoritetask.favoritetaskitem;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.jobmanagement.favoritetask.favoritetaskitem.FavoriteTaskDisplayOrder;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * お気に入り作業の表示順 FavoriteTaskDisplayOrder
 * @author tutt
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KRCDT_TASK_FAV_FRAME_SET_DISPORDER")
public class KrcdtTaskFavFrameSetDisporder extends ContractUkJpaEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	public KrcdtTaskFavFrameSetDisporderPk pk;
	
	@Column(name = "SID")
	public String sId;
	
	@Column(name = "CID")
	public String cid;
	
	@Override
	protected Object getKey() {
		return this.pk;
	}
	
	public FavoriteTaskDisplayOrder toDomain() {
		return null;
	}

}
