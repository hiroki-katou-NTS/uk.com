package nts.uk.ctx.at.schedule.app.query;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author sonnh1
 *
 */
@Data
@NoArgsConstructor
public class RankDto {
	/** ランクコード **/
	public String rankCd;
	/** 記号 **/
	public String rankSymbol;
	/** priority **/
	public int priority;
	
	public RankDto(String rankCd, String rankSymbol) {
		super();
		this.rankCd = rankCd;
		this.rankSymbol = rankSymbol;
	}
	
	public RankDto(String rankCd, String rankSymbol, int priority) {
		super();
		this.rankCd = rankCd;
		this.rankSymbol = rankSymbol;
		this.priority = priority;
	}

}
