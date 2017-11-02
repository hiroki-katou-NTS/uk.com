package nts.uk.ctx.bs.employee.infra.entity.jobtitle;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.infra.data.entity.type.GeneralDateToDBConverter;
import nts.arc.time.GeneralDate;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * The Class BsymtJobInfo.
 */
@Getter
@Setter
@Entity
@Table(name = "BSYMT_JOB_POST_MAIN_HIST")
public class BsymtJobPosMainHist extends UkJpaEntity implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	@EmbeddedId
	private BsymtJobPosMainHistPK bsymtJobPosMainHistPK;
	  /** The start date. */
    @Column(name = "START_DATE")
	@Convert(converter = GeneralDateToDBConverter.class)
    private GeneralDate startDate;
    
    /** The end date. */
    @Column(name = "END_DATE")
	@Convert(converter = GeneralDateToDBConverter.class)
    private GeneralDate endDate;

	@Override
	protected Object getKey() {
		return this.bsymtJobPosMainHistPK;
	}

}
