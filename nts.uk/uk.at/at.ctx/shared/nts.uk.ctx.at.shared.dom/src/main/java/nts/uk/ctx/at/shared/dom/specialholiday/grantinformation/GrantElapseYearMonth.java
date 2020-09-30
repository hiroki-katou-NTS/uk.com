package nts.uk.ctx.at.shared.dom.specialholiday.grantinformation;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 経過年数に対する付与日数
 * @author masaaki_jinno
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GrantElapseYearMonth {

	/** 付与回数 */
	private int elapseNo;
	
	/** 付与日数 */
	private GrantedDays grantedDays;
}

