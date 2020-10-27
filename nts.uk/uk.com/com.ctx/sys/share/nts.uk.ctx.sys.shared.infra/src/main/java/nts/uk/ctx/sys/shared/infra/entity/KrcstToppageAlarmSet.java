package nts.uk.ctx.sys.shared.infra.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;
/**
 * 
 * @author yennth
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KRCST_TOPPAGE_ALARM_SET")
public class KrcstToppageAlarmSet extends ContractUkJpaEntity implements Serializable {
	
	@Id
	@Column(name = "CID")
	public String companyId;
	
	@Id
	@Column(name = "ALARM_CATEGORY")
	public int alarmCategory;
	
	@Column(name = "USE_ATR")
	public int useAtr;
	
	private static final long serialVersionUID = 1L;
	
	@Override
	protected Object getKey() {
		// TODO Auto-generated method stub
		return this.companyId;
	}

}
