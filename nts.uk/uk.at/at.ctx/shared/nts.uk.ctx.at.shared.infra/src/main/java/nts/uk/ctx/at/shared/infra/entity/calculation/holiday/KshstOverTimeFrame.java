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
@Table(name = "KSHST_OVER_DAY_HD_SET ")
public class KshstOverTimeFrame extends UkJpaEntity implements Serializable{
	private static final long serialVersionUID = 1L;
	/** 主キー */
	@EmbeddedId
	public KshstOverTimeFramePK kshstOverTimeFramePK;
	
	/** 使用区分*/
	@Column(name = "USE_ATR")
	public int useAtr;
	
	/** 振替枠名称 */
	@Column(name = "TRANSFER_FRAME_NAME")
	public String transferFrameName;
	
	/** 残業枠名称 */
	@Column(name = "OVERTIME_FRAME_NAME")
	public String overtimeFrameName;
	
	@Override
	protected Object getKey() {
		return kshstOverTimeFramePK;
	}
}
