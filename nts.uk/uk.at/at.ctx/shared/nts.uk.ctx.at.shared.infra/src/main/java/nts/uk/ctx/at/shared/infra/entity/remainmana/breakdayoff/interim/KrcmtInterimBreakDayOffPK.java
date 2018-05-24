package nts.uk.ctx.at.shared.infra.entity.remainmana.breakdayoff.interim;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class KrcmtInterimBreakDayOffPK implements Serializable{
	/** 休出管理データ*/
	@Column(name = "BREAK_MANA_ID")
	public String breakManaId;
	/**	代休管理データ */
	@Column(name = "DAYOFF_MANA_ID")
	public String dayOffManaId;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

}
