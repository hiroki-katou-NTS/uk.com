package nts.uk.ctx.at.shared.infra.entity.alarmList;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDate;
@Setter
@Getter
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class KfndtAlarmExtracResultPK implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**	会社ID */
	@Column(name = "CID")
	public String cid;
	/**	自動実行コード */
	@Column(name = "AUTORUN_CODE")
	public String autoRunCode;
	/**	アラームリストパターンコード */
	@Column(name = "PATTERN_CODE")
	public String patternCode;
	/**カテゴリ	 */
	@Column (name = "CATEGORY")
	public int category;
	/**	アラームチェック条件コード */
	@Column(name = "ALARM_CHECK_CODE")
	public String alarmCheckCode;
	/**	チェック種類 */
	@Column(name = "CHECK_ATR")
	public int checkAtr;
	/**	コード: 条件のコード又はNo */
	@Column(name = "CONDITION_NO")
	public String conditionNo;
	/**	社員ID */
	@Column(name = "SID")
	public String sid;
	/**	アラーム値日付：開始日 */
	@Column(name = "START_DATE")
	public GeneralDate startDate;

}
