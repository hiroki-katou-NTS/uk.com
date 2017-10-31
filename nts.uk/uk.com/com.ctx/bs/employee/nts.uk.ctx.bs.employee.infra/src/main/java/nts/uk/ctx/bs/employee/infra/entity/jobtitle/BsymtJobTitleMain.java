package nts.uk.ctx.bs.employee.infra.entity.jobtitle;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.PrimaryKeyJoinColumns;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
/**
 * 職務職位
 * @author xuan vinh
 *
 */

@Getter
@Setter
@Entity
@Table(name="BSYMT_JOB_POSITION_MAIN")
public class BsymtJobTitleMain {

	@EmbeddedId
	private BsymtJobTitleMainPK bsymtJobTitleMainPK;
	
	/**社員ID*/
	@Column(name = "SID")
	private String sId;
	
	/**社員ID*/
	@Column(name = "HIST_ID")
	private String histId;
	
	/** The bsymt job hist. */
	@OneToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.LAZY)
	@PrimaryKeyJoinColumns({
			@PrimaryKeyJoinColumn(name = "HIST_ID", referencedColumnName = "HIST_ID") })
	public BsymtJobPosMainHist bsymtJobPosMainHist;
	
	
}
