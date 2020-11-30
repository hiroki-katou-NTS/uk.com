package nts.uk.ctx.workflow.infra.entity.approvermanagement.setting;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;
/**
 * Refactor5
 * @author hoangnd
 *
 */
@Entity
@Table(name="WWFMT_APPROVAL_HR")
@AllArgsConstructor
@NoArgsConstructor
public class JcmmtRootArpWF extends ContractUkJpaEntity implements Serializable{
	
	public  static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "CID")
    public String cid;  
	
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
