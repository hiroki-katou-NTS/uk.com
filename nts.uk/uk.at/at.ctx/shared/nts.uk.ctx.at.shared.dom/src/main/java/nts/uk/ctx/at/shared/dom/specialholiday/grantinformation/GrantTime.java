package nts.uk.ctx.at.shared.dom.specialholiday.grantinformation;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 付与するタイミングの種類
 * 
 * @author tanlv
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class GrantTime {
	/** 固定付与日 */
	private FixGrantDate fixGrantDate;
	
	/** 特別休暇付与テーブル */
	private List<GrantDateTbl> grantDateTbl;

	public static GrantTime createFromJavaType(FixGrantDate fixGrantDate, List<GrantDateTbl> grantDateTbl) {
		return new GrantTime(fixGrantDate, grantDateTbl);
	}
}
