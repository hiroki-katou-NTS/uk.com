package nts.uk.ctx.at.schedule.app.query;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author sonnh1
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RankExport {
	public List<RankDto> lstRankDto;
	public List<String> listRankCodeSorted;
}
