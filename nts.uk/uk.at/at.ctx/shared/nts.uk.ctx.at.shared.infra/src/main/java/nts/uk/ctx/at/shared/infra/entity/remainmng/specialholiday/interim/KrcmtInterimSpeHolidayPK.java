package nts.uk.ctx.at.shared.infra.entity.remainmng.specialholiday.interim;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class KrcmtInterimSpeHolidayPK implements Serializable{
	/**	社員ID */
	@Column(name = "SID")	
	public String sid;
	/** 年月日 */
	@Column(name = "YMD")
	public GeneralDate ymd;
	/** 特別休暇コード */
	@Column(name = "SPECIAL_HOLIDAY_CODE")
	public int specialHolidayCode;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
