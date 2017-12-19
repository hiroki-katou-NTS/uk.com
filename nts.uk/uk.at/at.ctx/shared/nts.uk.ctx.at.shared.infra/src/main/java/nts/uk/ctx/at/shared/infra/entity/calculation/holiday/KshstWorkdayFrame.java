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
public class KshstWorkdayFrame extends UkJpaEntity implements Serializable{
	private static final long serialVersionUID = 1L;
	/** 主キー */
	@EmbeddedId
	public KshstWorkdayFramePK kshstWorkdayFramePK;
	
	/** 休出枠名称*/
	@Column(name = "WORKDAY_FRAME_NAME")
	public String workdayFrameName;
	
	/** 使用区分*/
	@Column(name = "USE_ATR")
	public int useAtr;
	
	/** 振替枠名称*/
	@Column(name = "TRANSFER_FRAME_NAME")
	public String transferFrameName;
	
	@Override
	protected Object getKey() {
		return kshstWorkdayFramePK;
	}
}
