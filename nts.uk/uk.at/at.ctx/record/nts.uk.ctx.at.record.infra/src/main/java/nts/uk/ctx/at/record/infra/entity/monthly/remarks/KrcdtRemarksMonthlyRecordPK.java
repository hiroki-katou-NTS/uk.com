package nts.uk.ctx.at.record.infra.entity.monthly.remarks;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
/**
 * 
 * @author phongtq
 *
 */
@Embeddable
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class KrcdtRemarksMonthlyRecordPK implements Serializable{
	private static final long serialVersionUID = 1L;
	
	/** 社員ID */
	@Column(name = "SID")
	public String employeeId;
	
	/** 締めID */
	@Column(name = "CLOSURE_ID")
	public int closureId;
	
	/** 備考欄NO */
	@Column(name = "REMARKS_NO")
	public int remarksNo;

	/** 年月 */
	@Column(name = "REMARKS_YM")
	public int yearMonth;
	
	/** 締め日 */
	@Column(name = "CLOSURE_DAY")
	public Integer closureDay;
	
	/** 末日とする */
	@Column(name = "IS_LAST_DAY")
	public Integer isLastDay;
	
}
