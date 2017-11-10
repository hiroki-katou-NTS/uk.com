package nts.uk.ctx.at.record.infra.entity.log;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.workrecord.log.TargetPerson;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * 
 * @author nampt
 * 対象者
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KRCDT_EMP_EXE_TARGET")
public class KrcdtEmpExeTarget extends UkJpaEntity implements Serializable  {
	
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	public KrcdtEmpExeTargetPK krcdtEmpExeTargetPK;
	
	@Column(name = "EXECUTION_CONTENT")
	public int executionContent;

	@Column(name = "EXECUTION_STATE")
	public int executionState;

	@Override
	protected Object getKey() {
		return this.krcdtEmpExeTargetPK;
	}
	
	public static KrcdtEmpExeTarget toEntity(TargetPerson domain) {
		KrcdtEmpExeTarget entity = new KrcdtEmpExeTarget(
				new KrcdtEmpExeTargetPK(domain.getEmployeeId(), domain.getEmpCalAndSumExecLogId()), 
				domain.getState().getExecutionContent().value, 
				domain.getState().getStatus().value);
		return entity;
	}
	
}
