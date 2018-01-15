package nts.uk.ctx.at.schedule.infra.entity.schedule.setting;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * 表示可能勤務種類制御
 * 
 * @author sonnh1
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KSCST_WORKTYPE_DISPLAY")
public class KscstWorkTypeDisplay extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public KscstWorkTypeDisplayPK kscstWorkTypeDisplayPk;

	@Column(name = "USE_ATR")
	public int useAtr;

	@Override
	protected Object getKey() {
		return this.kscstWorkTypeDisplayPk;
	}

}
