package nts.uk.ctx.at.schedule.infra.entity.schedule.setting;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * 表示可能勤務種類制御設定
 * 
 * @author sonnh1
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KSCST_WORKTYPE_DISP_SET")
public class KscstWorkTypeDispSet extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public KscstWorkTypeDispSetPK kscstWorkTypeDispSetPk;

	@Override
	protected Object getKey() {
		return this.kscstWorkTypeDispSetPk;
	}

}
