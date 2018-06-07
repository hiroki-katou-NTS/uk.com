package nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class AbsRecGenerationDigestionHis {
	/**
	 * 社員ID
	 */
	private String sid;
	/**
	 * 残数集計情報
	 */
	private AsbRemainTotalInfor absRemainInfor;
	/**
	 * 履歴対照情報
	 */
	private List<RecAbsHistoryOutputPara> greneraGigesHis; 
}
