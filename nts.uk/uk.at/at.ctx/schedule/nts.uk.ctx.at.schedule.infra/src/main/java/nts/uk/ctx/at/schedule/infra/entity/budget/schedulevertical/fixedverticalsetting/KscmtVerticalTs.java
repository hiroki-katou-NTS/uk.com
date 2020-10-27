package nts.uk.ctx.at.schedule.infra.entity.budget.schedulevertical.fixedverticalsetting;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KSCMT_VERTICAL_TS")

public class KscmtVerticalTs extends ContractUkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/* 主キー */
	@EmbeddedId
	public KscmtVerticalTsPK kscstVerticalTimeSetPK;
	
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

