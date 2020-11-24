package nts.uk.screen.at.app.query.kcp013;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AcquireWorkHours {
	/** コード */
	private String code;
	/** 表示名 */
	private String name;
	/** 開始時刻1 */
	private int tzStart1;
	/** 終了時刻1 */
	private int tzEnd1;
	/** 開始時刻2 */
	private int tzStart2;
	/** 終了時刻2 */
	private int tzEnd2;
	/** 勤務区分 */
	private String workStyleClassfication;
	/** 備考 */
	private String remark;
	/** 使用区分 */
	private int useDistintion;
	
	private String nameAb;

}
