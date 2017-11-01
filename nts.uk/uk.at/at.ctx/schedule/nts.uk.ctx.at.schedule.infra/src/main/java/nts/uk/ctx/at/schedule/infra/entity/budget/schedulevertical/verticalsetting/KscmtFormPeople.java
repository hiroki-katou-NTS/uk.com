package nts.uk.ctx.at.schedule.infra.entity.budget.schedulevertical.verticalsetting;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KSCMT_FORM_PEOPLE")
public class KscmtFormPeople extends UkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/* 主キー */
	@EmbeddedId
	public KscmtFormPeoplePK kscmtFormPeoplePK;
	
	/* 実績表示区分 */
	@Column(name = "ACTUAL_DISPLAY_ATR")
	public int actualDisplayAtr;
	
	@OneToOne
	@JoinColumns({ @JoinColumn(name = "CID", referencedColumnName = "KSCMT_GEN_VERT_ITEM.CID", insertable = false, updatable = false),
		@JoinColumn(name = "VERTICAL_CAL_CD", referencedColumnName = "KSCMT_GEN_VERT_ITEM.VERTICAL_CAL_CD", insertable = false, updatable = false),
		@JoinColumn(name = "ITEM_ID", referencedColumnName = "KSCMT_GEN_VERT_ITEM.ITEM_ID", insertable = false, updatable = false)
	})
	public KscmtGenVertItem kscmtGenVertItemPeople;
	
	
	@Override
	protected Object getKey() {
		// TODO Auto-generated method stub
		return kscmtFormPeoplePK;
	}
}
