package nts.uk.ctx.at.shared.infra.entity.calculation.holiday;

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
@Table(name = "KSHST_OVERDAY_HD_ATT_SET ")
public class KshstOverdayHdAttSet extends UkJpaEntity implements Serializable{
	private static final long serialVersionUID = 1L;
	/** 主キー */
	@EmbeddedId
	public KshstOverdayHdAttSetPK kshstOverdayHdAttSetPK;
	
	/** 変更後の残業枠NO*/
	@Column(name = "OVER_WORK_NO")
	public int overWorkNo;
	
	@Override
	protected Object getKey() {
		return kshstOverdayHdAttSetPK;
	}
}
