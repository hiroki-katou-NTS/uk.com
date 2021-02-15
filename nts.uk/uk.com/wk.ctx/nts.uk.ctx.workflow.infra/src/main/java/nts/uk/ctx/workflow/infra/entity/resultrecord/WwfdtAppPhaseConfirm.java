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
import nts.uk.shr.infra.data.entity.UkJpaEntity;
/**
 * 
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="WWFDT_APP_PHASE_CONFIRM")
public class WwfdtAppPhaseConfirm extends UkJpaEntity {
	
	@EmbeddedId
	private WwfdpAppPhaseConfirmPK pk;
	
	@Column(name="APP_PHASE_ATR")
	private Integer appPhaseAtr;
	
	@ManyToOne
	@PrimaryKeyJoinColumns({
		@PrimaryKeyJoinColumn(name="ROOT_ID",referencedColumnName="ROOT_ID")
	})
	private WwfdtAppRootConfirm wwfdtAppRootConfirm;
	
	@OneToMany(targetEntity=WwfdtAppFrameConfirm.class, cascade = CascadeType.ALL, mappedBy = "wwfdtAppPhaseConfirm", orphanRemoval = true)
	@JoinTable(name = "WWFDT_APP_FRAME_CONFIRM")
	public List<WwfdtAppFrameConfirm> listWwfdtAppFrameConfirm;

	@Override
	protected Object getKey() {
		return pk;
	}
}
