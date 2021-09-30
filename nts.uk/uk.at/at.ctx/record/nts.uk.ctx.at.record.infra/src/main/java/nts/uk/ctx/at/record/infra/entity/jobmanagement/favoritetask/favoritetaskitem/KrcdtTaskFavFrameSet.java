package nts.uk.ctx.at.record.infra.entity.jobmanagement.favoritetask.favoritetaskitem;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.jobmanagement.favoritetask.favoritetaskitem.FavoriteTaskItem;
import nts.uk.ctx.at.record.dom.jobmanagement.favoritetask.favoritetaskitem.FavoriteTaskName;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.infra.data.entity.ContractCompanyUkJpaEntity;

/**
 * @name お気に入り作業項目 FavoriteTaskItem
 * @author tutt
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KRCDT_TASK_FAV_FRAME_SET")
public class KrcdtTaskFavFrameSet extends ContractCompanyUkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "FAV_ID")
	public String favId;

	@Column(name = "TASK_CD1")
	public String taskCd1;

	@Column(name = "TASK_CD2")
	public String taskCd2;

	@Column(name = "TASK_CD3")
	public String taskCd3;

	@Column(name = "TASK_CD4")
	public String taskCd4;

	@Column(name = "TASK_CD5")
	public String taskCd5;

	@Column(name = "FAV_NAME")
	public String favName;

	@Column(name = "SID")
	public String sId;

	@Override
	protected Object getKey() {
		return this.favId;
	}
	
	public KrcdtTaskFavFrameSet (FavoriteTaskItem domain) {
		this.sId = domain.getEmployeeId();
		this.favName = domain.getTaskName().v();
		//TODO: mapping task name
	}
	
	public FavoriteTaskItem toDomain() {
		//TODO: mapping taskCd 1 -> 5 với AR FavoriteTaskItem.
		return new FavoriteTaskItem(this.sId, this.favId, new FavoriteTaskName(this.favName), null);
	}

}
