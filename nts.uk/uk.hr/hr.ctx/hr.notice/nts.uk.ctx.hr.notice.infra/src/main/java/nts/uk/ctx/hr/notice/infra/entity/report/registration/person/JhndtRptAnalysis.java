package nts.uk.ctx.hr.notice.infra.entity.report.registration.person;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;
/**
 * 届出分析
 * @author lanlt
 *
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "JHNDT_RPT_ANALYSIS")
public class JhndtRptAnalysis extends UkJpaEntity implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	public JhndtRptAnalysisPk pk;
	
	@NotNull
	@Column(name = "RPT_COUNT")
	public int reportCount;//カウント数量

	@Override
	protected Object getKey() {
		return this.pk;
	}

}
