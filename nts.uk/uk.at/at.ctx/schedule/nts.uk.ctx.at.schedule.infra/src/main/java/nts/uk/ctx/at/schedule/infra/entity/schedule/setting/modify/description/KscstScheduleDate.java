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
@Table(name = "KSCST_SCHEDULE_DATE")
public class KscstScheduleDate extends ContractUkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	public KscstScheduleDatePK kscstScheduleDatePK;
	
	/** 表示順 */
	@Column(name = "DISPLAY_ORDER_DATE")
	public int displayOrderDate;
	
	/** 利用可否権限の機能名 */
	@Column(name = "DISPLAY_NAME_DATE")
	public String displayNameDate;
	
	/** 説明文 */
	@Column(name = "DESCRIPTION_DATE")
	public String descriptionDate;
	
	/** 初期値 */
	@Column(name = "INITIAL_VALUE_DATE")
	public int initialValueDate;

	@Override
	protected Object getKey() {
		// TODO Auto-generated method stub
		return kscstScheduleDatePK;
	}
	
	

}
