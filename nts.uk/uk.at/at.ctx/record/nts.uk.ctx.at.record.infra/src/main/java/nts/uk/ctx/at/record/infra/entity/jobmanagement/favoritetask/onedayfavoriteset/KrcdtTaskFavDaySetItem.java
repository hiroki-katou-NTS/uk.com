package nts.uk.ctx.at.record.infra.entity.jobmanagement.favoritetask.onedayfavoriteset;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.ContractCompanyUkJpaEntity;

/**
 * 
 * @author tutt
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KRCDT_TASK_FAV_DAY_SET_ITEM")
public class KrcdtTaskFavDaySetItem extends ContractCompanyUkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public KrcdtTaskFavDaySetItemPk pk;
	
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

	@Override
	protected Object getKey() {
		return this.pk;
	}
	
}
