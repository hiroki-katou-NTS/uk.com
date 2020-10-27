package nts.uk.ctx.at.schedule.infra.entity.schedule.setting.worktypecontrol;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

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
public class KscstWorkTypeDispSet extends ContractUkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public KscstWorkTypeDispSetPK kscstWorkTypeDispSetPk;

	@ManyToOne
    @JoinColumns({
    	@JoinColumn(name="CID", referencedColumnName="CID", insertable = false, updatable = false),
    })
	public KscstWorkTypeDisplay workTypeDisplay;
	
	@Override
	protected Object getKey() {
		return this.kscstWorkTypeDispSetPk;
	}

	public KscstWorkTypeDispSet(KscstWorkTypeDispSetPK kscstWorkTypeDispSetPk) {
		super();
		this.kscstWorkTypeDispSetPk = kscstWorkTypeDispSetPk;
	}

}
