package nts.uk.ctx.at.record.infra.entity.monthly.vacation.absenceleave;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Embeddable
@EqualsAndHashCode
public class KrcdtMonSubOfHdRemainPK implements Serializable{
	/**	社員ID */
	@Column(name = "SID")
	public String sId;
	/**	年月 */
	@Column(name = "YM")
	public int ym;
	/**	締めID */
	@Column(name = "CLOSURE_ID")
	public int closureId;
	/**	締め日 */
	@Column(name = "CLOSURE_DAY")
	public int closureDay;
	/**	末日とする */
	@Column(name = "IS_LAST_DAY")
	public int isLastDay;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
}
