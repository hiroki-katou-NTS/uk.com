package nts.uk.ctx.at.schedule.infra.entity.schedule.setting.worktypecontrol;

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
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * 表示可能勤務種類制御
 * 
 * @author sonnh1
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KSCMT_DISP_WKTP_USE")
public class KscstWorkTypeDisplay extends ContractUkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public KscstWorkTypeDisplayPK kscstWorkTypeDisplayPk;

	/** 制御使用区分 **/
	@Column(name = "USE_ATR")
	public int useAtr;

	@Override
	protected Object getKey() {
		return this.kscstWorkTypeDisplayPk;
	}
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy="workTypeDisplay", orphanRemoval = true)
	public List<KscstWorkTypeDispSet> typeDispSets;

	public KscstWorkTypeDisplay(KscstWorkTypeDisplayPK kscstWorkTypeDisplayPk, int useAtr) {
		super();
		this.kscstWorkTypeDisplayPk = kscstWorkTypeDisplayPk;
		this.useAtr = useAtr;
	}

}
