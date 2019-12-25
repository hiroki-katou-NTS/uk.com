package nts.uk.ctx.hr.develop.infra.entity.announcement.mandatoryretirement;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@Entity
@NoArgsConstructor
@Table(name = "JSHMT_RETIRE_TERM_COURCE")
public class JshmtRetireTermCource extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public JshmtRetireTermCourcePK pkJshmtRetireTermCource;
	
	@Column(name = "CID")
	public String cId;
	
	@Override
	public Object getKey() {
		return pkJshmtRetireTermCource;
	}
	
	@ManyToOne
	@JoinColumns({ @JoinColumn(name = "HIST_ID", referencedColumnName = "HIST_ID", insertable = false, updatable = false),
			@JoinColumn(name = "COMMON_MASTER_ITEM_ID", referencedColumnName = "COMMON_MASTER_ITEM_ID", insertable = false, updatable = false) })
	public JshmtRetireTerm enableRetirePlanCourse;

	public JshmtRetireTermCource(String historyId, String empCommonMasterItemId, long retirePlanCourseId, String cId) {
		this.pkJshmtRetireTermCource = new JshmtRetireTermCourcePK(historyId, empCommonMasterItemId, retirePlanCourseId);
		this.cId = cId;
	}
}
