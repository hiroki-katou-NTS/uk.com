package nts.uk.ctx.at.schedule.infra.entity.schedule.workschedule;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;

/**
 *
 * @author HieuLt
 *
 */
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
@EqualsAndHashCode
public class KscdtSchShortTimePK implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	/** 社員ID */
	@Basic(optional = false)
	@NotNull
	@Column(name = "SID")
	public String sid;

	/** 年月日 */
	@Basic(optional = false)
	@NotNull
	@Column(name = "YMD")
	public GeneralDate ymd;
	
	/**"育児介護区分---0:育児---1:介護"*/
	@Getter
	@Basic(optional = false)
	@NotNull
	@Column(name = "CHILD_CARE_ATR")
	public int childCareAtr;
	
}
