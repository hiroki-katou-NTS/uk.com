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
@Table(name = "KSCCT_SCHE_FUNC_SYA")
public class KscctScheFuncSya extends ContractUkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	public KscctScheFuncSyaPK kscctScheFuncSyaPK;
	
	/** 表示順 */
	@Column(name = "DISPLAY_ORDER_AUTH")
	public int displayOrderAuth;
	
	/** 利用可否権限の機能名 */
	@Column(name = "DISPLAY_NAME_AUTH")
	public String displayNameAuth;
	
	/** 説明文 */
	@Column(name = "DESCRIPTION_AUTH")
	public String descriptionAuth;
	
	/** 初期値 */
	@Column(name = "INITIAL_VALUE_AUTH")
	public int initialValueAuth;

	@Override
	protected Object getKey() {
		// TODO Auto-generated method stub
		return kscctScheFuncSyaPK;
	}
	
	

}
