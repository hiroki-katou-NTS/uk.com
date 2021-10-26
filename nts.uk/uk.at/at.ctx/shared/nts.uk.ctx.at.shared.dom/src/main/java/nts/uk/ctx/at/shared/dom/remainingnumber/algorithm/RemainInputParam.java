package nts.uk.ctx.at.shared.dom.remainingnumber.algorithm;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.CreateAtr;

@AllArgsConstructor
@Data
public class RemainInputParam {

	/** 会社ID */
	private String cid;
	/** ・社員ID */
	private String sid;
	/** ・集計開始日・集計終了日 */
	private DatePeriod datePeriod;
	/** ・モード: True: 月次か, false: その他か */
	private boolean mode;
	/** ・基準日 */
	private GeneralDate baseDate;
	/** ・登録期間の開始日・登録期間の終了日 */
	private DatePeriod registerDate;
	/**作成元区分 */
	Optional<CreateAtr> createAtr;
}
