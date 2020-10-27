package nts.uk.ctx.at.schedule.infra.entity.schedule.setting.modify.description;

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
@Table(name = "KSCST_SCHEDULE_SHIFT")
public class KscstScheduleShift extends ContractUkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	public KscstScheduleShiftPK kscstScheduleShiftPK;
	
	/** 表示順 */
	@Column(name = "DISPLAY_ORDER_SHIFT")
	public int displayOrderShift;
	
	/** 利用可否権限の機能名 */
	@Column(name = "DISPLAY_NAME_SHIFT")
	public String displayNameShift;
	
	/** 説明文 */
	@Column(name = "DESCRIPTION_SHIFT")
	public String descriptionShift;
	
	/** 初期値 */
	@Column(name = "INITIAL_VALUE_SHIFT")
	public int initialValueShift;

	@Override
	protected Object getKey() {
		// TODO Auto-generated method stub
		return kscstScheduleShiftPK;
	}
	
	

}
