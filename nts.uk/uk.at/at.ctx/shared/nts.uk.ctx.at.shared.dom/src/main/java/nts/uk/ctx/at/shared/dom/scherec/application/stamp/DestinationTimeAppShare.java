package nts.uk.ctx.at.shared.dom.scherec.application.stamp;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author thanh_nx
 *
 *         時刻申請の反映先情報(反映用)
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class DestinationTimeAppShare {
	// 打刻分類
	private TimeStampAppEnumShare timeStampAppEnum;
	// 打刻枠No
	private Integer stampNo;
	// 開始終了区分
	private StartEndClassificationShare startEndClassification;
	// 応援勤務NO
	private Optional<Integer> workNo;
}
