package nts.uk.ctx.bs.employee.infra.entity.temporaryabsence;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.shr.infra.data.entity.UkJpaEntity;


@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "BSYMT_TEMP_ABS_HIS_ITEM")
public class BsymtTempAbsHisItem extends UkJpaEntity implements Serializable{
	
	private static final long serialVersionUID = 1L;

	/** The BsymtTempAbsHistoryPk. */
	@EmbeddedId
	public BsymtTempAbsHistoryPk key;
	
	@Basic(optional = false)
	@Column(name = "LEAVE_HOLIDAY_ATR")
	public int leaveHolidayAtr;

	@Basic(optional = true)
	@Column(name = "REMARKS")
	public String remarks;
	
	@Basic(optional = true)
	@Column(name = "SO_INS_PAY_CATEGORY")
	public int soInsPayCategory;
	
	// -------------- extend object ----------------------
	
	@Basic(optional = true)
	@Column(name = "MULTIPLE")
	public int multiple;

	@Basic(optional = true)
	@Column(name = "FAMILY_MEMBER_ID")
	public String familyMemberId;
	
	@Basic(optional = true)
	@Column(name = "SAME_FAMILY")
	public int sameFamily;
	
	@Basic(optional = true)
	@Column(name = "CHILD_TYPE")
	public int childType;

	@Basic(optional = true)
	@Column(name = "CREATE_DATE")
	public GeneralDate createDate;

	@Basic(optional = true)
	@Column(name = "SPOUSE_IS_LEAVE")
	public int spouseIsLeave;
	
	@Basic(optional = true)
	@Column(name = "SAME_FAMILY_DAYS")
	public int sameFamilyDays;
	
	@Override
	protected Object getKey() {
		return key;
	}
}
