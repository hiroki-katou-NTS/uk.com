package nts.uk.ctx.sys.env.infra.useatr.entity;

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
@Table(name = "SACMT_SYS_USA_SET")
public class SacmtSysUsageSet extends UkJpaEntity implements Serializable{
	private static final long serialVersionUID = 1L;
	@EmbeddedId
	public SacmtSysUsageSetPK sacmtSysUsageSetPK;
	/** 人事システム */
	@Column(name = "PERSONNEL_SYS")
	public int personnelSystem;
	
	/** 就業システム */
	@Column(name = "EMP_SYS")
	public int employmentSys;
	
	/** 給与システム */
	@Column(name = "PAY_SYS")
	public int payrollSys;

	@Override
	protected Object getKey() {
		return sacmtSysUsageSetPK;
	}
	
	public SacmtSysUsageSet( SacmtSysUsageSetPK sacmtSysUsageSetPK){
		super();
		this.sacmtSysUsageSetPK = sacmtSysUsageSetPK;
	}
}
