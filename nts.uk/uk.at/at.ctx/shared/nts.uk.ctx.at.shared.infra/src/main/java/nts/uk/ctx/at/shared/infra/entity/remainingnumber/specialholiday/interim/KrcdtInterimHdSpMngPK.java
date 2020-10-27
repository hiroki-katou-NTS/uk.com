package nts.uk.ctx.at.shared.infra.entity.remainingnumber.specialholiday.interim;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class KrcdtInterimHdSpMngPK implements Serializable{
	/**
	 * 特別休暇ID
	 */
	@Column(name = "SPE_HOLIDAY_ID")
	public String specialHolidayId;
	/** 特別休暇コード */
	@Column(name = "SPECIAL_HOLIDAY_CODE")
	public int specialHolidayCode;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
