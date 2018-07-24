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
@Table(name="WWFDT_APP_PHASE_DYNAMIC")
public class WwfdtAppPhaseDynamic extends UkJpaEntity {
	
	@EmbeddedId
	private WwfdpAppPhaseDynamicPK pk;
	
	@Column(name="APPROVAL_FORM")
	private Integer approvalForm;
	
	@ManyToOne
	@PrimaryKeyJoinColumns({
		@PrimaryKeyJoinColumn(name="ROOT_ID",referencedColumnName="ROOT_ID")
	})
	private WwfdtAppRootDynamic wwfdtAppRootDynamic;
	
	@OneToMany(targetEntity=WwfdtAppFrameDynamic.class, cascade = CascadeType.ALL, mappedBy = "wwfdtAppPhaseDynamic", orphanRemoval = true)
	@JoinTable(name = "WWFDT_APP_FRAME_DYNAMIC")
	public List<WwfdtAppFrameDynamic> listWwfdtAppFrameDynamic;

	@Override
	protected Object getKey() {
		return pk;
	}
	
}
