package nts.uk.ctx.bs.employee.infra.entity.temporaryAbsence;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@Getter
@Setter
@Entity
@Table(name = "BSYMT_TEMPORARY_ABSENCE")
public class BsymtTemporaryAbsence extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	/** The BsymtTemporaryAbsencePK. */
	@EmbeddedId
	protected BsymtTemporaryAbsencePK bsymtTemporaryAbsencePK;

	@Basic(optional = false)
	@Column(name = "LEAVE_HOLIDAY_ID")
	private String leaveHolidayId;

	@Basic(optional = false)
	@Column(name = "START_DATE")
	private GeneralDate startDate;

	@Basic(optional = false)
	@Column(name = "END_DATE")
	private GeneralDate endDate;

	@Basic(optional = false)
	@Column(name = "LEAVE_HOLIDAY_ATR")
	private int leaveHolidayAtr;

	@Basic(optional = false)
	@Column(name = "REASON")
	private String reason;

	@Basic(optional = false)
	@Column(name = "FAMILY_MEMBER_ID")
	private String familyMemberId;

	@Basic(optional = false)
	@Column(name = "BIRTHDAY")
	private GeneralDate birthday;

	@Basic(optional = false)
	@Column(name = "MULTIPLE")
	private int multiple;

	@Override
	protected Object getKey() {
		// TODO Auto-generated method stub
		return null;
	}

	public BsymtTemporaryAbsence() {
		super();
	}

}
