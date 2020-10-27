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
@Table(name="WWFDT_CONF_PHASE")
public class WwfdtConfPhase extends ContractUkJpaEntity {
	
	@EmbeddedId
	private WwfdpAppPhaseConfirmPK pk;
	
	@Column(name="APP_PHASE_ATR")
	private Integer appPhaseAtr;
	
	@ManyToOne
	@PrimaryKeyJoinColumns({
		@PrimaryKeyJoinColumn(name="ROOT_ID",referencedColumnName="ROOT_ID")
	})
	private WwfdtConfRoute wwfdtConfRoute;
	
	@OneToMany(targetEntity=WwfdtConfFrame.class, cascade = CascadeType.ALL, mappedBy = "wwfdtConfPhase", orphanRemoval = true)
	@JoinTable(name = "WWFDT_CONF_FRAME")
	public List<WwfdtConfFrame> listWwfdtConfFrame;

	@Override
	protected Object getKey() {
		return pk;
	}
}
