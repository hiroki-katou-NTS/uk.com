package nts.uk.ctx.at.schedule.infra.entity.schedule.setting.modify.description;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;
/**
 * 
 * @author phongtq
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KSCST_SCHEDULE_COMMON")
public class KscstScheduleCommon extends ContractUkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	public KscstScheduleCommonPK kscstScheduleCommonPK;
	
	/** 表示順 */
	@Column(name = "DISPLAY_ORDER_COM")
	public int displayOrderCom;
	
	/** 利用可否権限の機能名 */
	@Column(name = "DISPLAY_NAME_COM")
	public String displayNameCom;
	
	/** 説明文 */
	@Column(name = "DESCRIPTION_COM")
	public String descriptionCom;
	
	/** 初期値 */
	@Column(name = "INITIAL_VALUE_COM")
	public int initialValueCom;

	@Override
	protected Object getKey() {
		// TODO Auto-generated method stub
		return kscstScheduleCommonPK;
	}
	
	

}
