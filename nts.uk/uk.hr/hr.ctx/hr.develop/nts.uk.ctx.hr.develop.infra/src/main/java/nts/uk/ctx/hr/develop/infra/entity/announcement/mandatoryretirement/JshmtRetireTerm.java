package nts.uk.ctx.hr.develop.infra.entity.announcement.mandatoryretirement;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.NoArgsConstructor;
import nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement.EnableRetirePlanCourse;
import nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement.MandatoryRetireTerm;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@Entity
@NoArgsConstructor
@Table(name = "JSHMT_RETIRE_TERM")
public class JshmtRetireTerm extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public JshmtRetireTermPK pkJshmtRetireEvalItem;
	
	@Column(name = "CID")
	public String cId;
	
	@Column(name = "USAGE_FLG")
	public Integer usageFlg;
	
	@OneToMany(mappedBy = "enableRetirePlanCourse", cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinTable(name = "JSHMT_RETIRE_TERM")
	public List<JshmtRetireTermCource> enableRetirePlanCourse;
	
	@ManyToOne
	@JoinColumns({ @JoinColumn(name = "HIST_ID", referencedColumnName = "HIST_ID", insertable = false, updatable = false),
			@JoinColumn(name = "CID", referencedColumnName = "CID", insertable = false, updatable = false) })
	public JshmtMandatRetireReg mandatoryRetireTerm;
	
	@Override
	public Object getKey() {
		return pkJshmtRetireEvalItem;
	}
	
	public JshmtRetireTerm(String companyId, String historyId, MandatoryRetireTerm mandatoryRetireTerm) {
		this.pkJshmtRetireEvalItem = new JshmtRetireTermPK(historyId, mandatoryRetireTerm.getEmpCommonMasterItemId());
		this.cId = companyId;
		this.usageFlg = mandatoryRetireTerm.isUsageFlg()?1:0;
		this.enableRetirePlanCourse = mandatoryRetireTerm.getEnableRetirePlanCourse().stream().map(
				c-> new JshmtRetireTermCource(historyId, mandatoryRetireTerm.getEmpCommonMasterItemId(), c.getRetirePlanCourseId(), companyId))
				.collect(Collectors.toList());
	}
	
	public MandatoryRetireTerm toDomain() {
		return new MandatoryRetireTerm(this.pkJshmtRetireEvalItem.empCommonMasterItemId, this.usageFlg == 1, enableRetirePlanCourse.stream().map(c -> new EnableRetirePlanCourse(c.pkJshmtRetireTermCource.retirePlanCourseId)).collect(Collectors.toList()));
	}
}
