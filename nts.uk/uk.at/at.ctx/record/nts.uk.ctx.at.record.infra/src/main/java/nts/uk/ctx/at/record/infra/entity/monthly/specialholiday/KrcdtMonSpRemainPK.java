package nts.uk.ctx.at.record.infra.entity.monthly.specialholiday;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Embeddable
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class KrcdtMonSpRemainPK implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**	社員ID */
	@Column(name = "SID")
	public String sid;
	/**	年月 */
	@Column(name = "YM")
	public int ym;
	/**	締めID */
	@Column(name = "CLOSURE_ID")
	public int closureId;
	/**	日 */
	@Column(name = "CLOSURE_DAY")
	public int closureDay;
	/**	末日とする */
	@Column(name = "IS_LAST_DAY")
	public int chkLastDay;
	/**	特別休暇コード */
	@Column(name = "SPECIAL_LEAVE_CD")
	public int specialHolidayCd;
}
