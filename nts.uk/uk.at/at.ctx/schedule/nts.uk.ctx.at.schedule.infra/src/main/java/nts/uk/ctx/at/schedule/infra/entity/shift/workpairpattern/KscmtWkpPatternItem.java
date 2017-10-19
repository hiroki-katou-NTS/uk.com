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
 * 勤務ペアパターン
 * 
 * @author sonnh1
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KSCMT_WKP_PATTERN_ITEM")
public class KscmtWkpPatternItem extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public KscmtWkpPatternItemPK kscmtWkpPatternItemPk;

	@Column(name = "PATTERN_NAME")
	public String patternName;

	@Override
	protected Object getKey() {
		return this.kscmtWkpPatternItemPk;
	}

}
