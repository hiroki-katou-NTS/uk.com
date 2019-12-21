package nts.uk.ctx.hr.develop.infra.entity.sysoperationset.businessrecognition;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;
/**
 * 
 * @author laitv
 *
 */
@Entity
@Table(name="JCMMT_ROOT_APR")
@AllArgsConstructor
@NoArgsConstructor
public class JcmmtRootArp extends UkJpaEntity {
	
	@Id
	@Column(name = "CID")
    public String cid;  //
	
	@Column(name = "COM_MODE")
	public int comMode;
	
	@Column(name = "DEV_MODE")
	public int devMode;
	
	@Column(name = "EMP_MODE")
	public int empMode;
	
	@Override
	protected Object getKey() {
		return cid;
	}
}
