package nts.uk.ctx.workflow.infra.entity.resultrecord;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
@Table(name="WWFDT_INST_PHASE")
public class WwfdtInstPhase extends ContractUkJpaEntity {
	
	@EmbeddedId
	private WwfdpAppPhaseInstancePK pk;
	
	@Column(name="APPROVAL_FORM")
	private Integer approvalForm;
	
	@ManyToOne
	@PrimaryKeyJoinColumns({
		@PrimaryKeyJoinColumn(name="ROOT_ID",referencedColumnName="ROOT_ID")
	})
	private WwfdtInstRoute wwfdtInstRoute;
	
	@OneToMany(targetEntity=WwfdtInstFrame.class, cascade = CascadeType.ALL, mappedBy = "wwfdtInstPhase", orphanRemoval = true)
	@JoinTable(name = "WWFDT_INST_FRAME")
	public List<WwfdtInstFrame> listWwfdtInstFrame;

	@Override
	protected Object getKey() {
		return pk;
	}
	
}
