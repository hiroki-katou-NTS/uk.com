package nts.uk.ctx.at.schedule.infra.entity.budget.schedulevertical.verticalsetting;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KSCST_VERTICAL_CAL_SET")
public class KscstVerticalCalSet extends UkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/* 主キー */
	@EmbeddedId
	public KscstVerticalCalSetPK kscstVerticalCalSetPK;
	
	/* 名称 */
	@Column(name = "VERTICAL_CAL_NAME")
	public String verticalCalName;
	
	/* 単位 */
	@Column(name = "UNIT")
	public int unit;
	
	/* 利用区分 */
	@Column(name = "USE_ATR")
	public int useAtr;
	
	/* 応援集計区分 */
	@Column(name = "ASSISTANCE_TABULATION_ATR")
	public int assistanceTabulationAtr;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy="verticalCalSet", orphanRemoval = true)
	public List<KscstVerticalCalItem> verticalCalItems;
	
	@Override
	protected Object getKey() {
		// TODO Auto-generated method stub
		return kscstVerticalCalSetPK;
	}
}
