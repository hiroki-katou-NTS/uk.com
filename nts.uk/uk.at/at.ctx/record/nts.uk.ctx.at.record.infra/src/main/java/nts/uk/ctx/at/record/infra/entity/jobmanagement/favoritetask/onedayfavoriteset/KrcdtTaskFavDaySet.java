package nts.uk.ctx.at.record.infra.entity.jobmanagement.favoritetask.onedayfavoriteset;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.jobmanagement.favoritetask.favoritetaskitem.FavoriteTaskName;
import nts.uk.ctx.at.record.dom.jobmanagement.favoritetask.onedayfavoriteset.OneDayFavoriteSet;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * 1日お気に入りセット OneDayFavoriteSet
 * 
 * @author tutt
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KRCDT_TASK_FAV_DAY_SET")
public class KrcdtTaskFavDaySet extends ContractUkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "FAV_ID")
	public String favId;

	@Column(name = "CID")
	public String cId;

	@Column(name = "FAV_NAME")
	public String favName;

	@Column(name = "SID")
	public String sId;

	@Override
	protected Object getKey() {
		return this.favId;
	}

	public KrcdtTaskFavDaySet(OneDayFavoriteSet domain) {
		this.favId = domain.getFavId();
		this.cId = AppContexts.user().companyId();
		this.favName = domain.getTaskName().v();
		this.sId = domain.getSId();
	}

}
