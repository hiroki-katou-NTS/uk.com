package nts.uk.ctx.workflow.infra.entity.resultrecord;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.PrimaryKeyJoinColumns;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;
/**
 * 
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="WWFDT_INST_APPROVE")
public class WwfdtInstApprove extends ContractUkJpaEntity {
	
	@EmbeddedId
	private WwfdpAppApproveInstancePK pk;
	
	@ManyToOne
	@PrimaryKeyJoinColumns({
		@PrimaryKeyJoinColumn(name="ROOT_ID",referencedColumnName="ROOT_ID"),
		@PrimaryKeyJoinColumn(name="PHASE_ORDER",referencedColumnName="PHASE_ORDER"),
		@PrimaryKeyJoinColumn(name="FRAME_ORDER",referencedColumnName="FRAME_ORDER")
	})
	private WwfdtInstFrame wwfdtInstFrame;

	@Override
	protected Object getKey() {
		return pk;
	}
}
