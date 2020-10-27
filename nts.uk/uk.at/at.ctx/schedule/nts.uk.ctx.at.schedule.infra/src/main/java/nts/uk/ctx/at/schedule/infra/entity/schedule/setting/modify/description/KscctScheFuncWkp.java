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
@Table(name = "KSCCT_SCHE_FUNC_WKP")
public class KscctScheFuncWkp extends ContractUkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	public KscctScheFuncWkpPK kscctScheFuncWkpPK;
	
	/** 表示順 */
	@Column(name = "DISPLAY_ORDER_WORKPLACE")
	public int displayOrderWork;
	
	/** 利用可否権限の機能名 */
	@Column(name = "DISPLAY_NAME_WORKPLACE")
	public String displayNameWork;
	
	/** 説明文 */
	@Column(name = "DESCRIPTION_WORKPLACE")
	public String descriptionWork;
	
	/** 初期値 */
	@Column(name = "INITIAL_VALUE_WORKPLACE")
	public int initialValueWork;

	@Override
	protected Object getKey() {
		// TODO Auto-generated method stub
		return kscctScheFuncWkpPK;
	}
	
	

}
