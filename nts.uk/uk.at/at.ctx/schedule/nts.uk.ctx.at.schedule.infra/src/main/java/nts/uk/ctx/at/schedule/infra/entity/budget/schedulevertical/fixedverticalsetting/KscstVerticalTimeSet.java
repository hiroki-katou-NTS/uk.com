package nts.uk.ctx.at.schedule.infra.entity.budget.schedulevertical.fixedverticalsetting;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KSCST_VERTICAL_TIME_SET")

public class KscstVerticalTimeSet extends UkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/* 主キー */
	@EmbeddedId
	public KscstVerticalTimeSetPK kscstVerticalTimeSetPK;
	
	/* 表示区分 */
	@Column(name = "DISPLAY_ATR")
	public int displayAtr;
	
	/* 時刻 */
	@Column(name = "START_CLOCK")
	public int startClock;
	
	@Override
	protected Object getKey() {
		// TODO Auto-generated method stub
		return kscstVerticalTimeSetPK;
	}
}

