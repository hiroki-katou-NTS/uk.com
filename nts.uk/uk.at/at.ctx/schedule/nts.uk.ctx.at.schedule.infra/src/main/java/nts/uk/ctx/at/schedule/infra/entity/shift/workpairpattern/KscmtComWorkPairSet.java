package nts.uk.ctx.at.schedule.infra.entity.shift.workpairpattern;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * 勤務ペア設定
 * 
 * @author sonnh1
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KSCMT_COM_WORK_PAIR_SET")
public class KscmtComWorkPairSet extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public KscmtComWorkPairSetPK kscmtComWorkPairSetPk;

	@Column(name = "WORKTYPE_CD")
	public String workTypeCode;

	@Column(name = "WORKTIME_CD")
	public String workTimeCode;

	@Override
	protected Object getKey() {
		return this.kscmtComWorkPairSetPk;
	}

}
