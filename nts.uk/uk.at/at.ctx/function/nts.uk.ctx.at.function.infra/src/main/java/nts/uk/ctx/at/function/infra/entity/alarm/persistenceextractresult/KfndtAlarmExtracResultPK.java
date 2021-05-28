package nts.uk.ctx.at.function.infra.entity.alarm.persistenceextractresult;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class KfndtAlarmExtracResultPK implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 会社ID */
    @Column(name = "CID")
    public String cid;

    /** 自動実行コード*/
    @Column(name = "PROCESS_ID")
    public String processId;

    /** 社員ID */
    @Column(name = "SID")
    public String sid;

    /** "カテゴリ：
     0：スケジュール日次
     1：スケジュール週次
     2：スケジュール4週
     3：スケジュール月次
     4：スケジュール年間
     5：日次
     6：週次
     7：月次
     8：申請承認
     9：複数月
     10：任意期間
     11：年休
     12：３６協定
     13：工数チェック
     14：マスタチェック"
     */
    @Column(name = "CATEGORY")
    public int category ;

    /** アラームチェック条件コード */
    @Column(name = "ALARM_CHECK_CODE")
    public String alarmCheckCode;

    /** チェック種類：
     0：固定チェック
     1：自由チェック
     2：36法定
     3：36超過"
     */
    @Column(name = "CHECK_ATR")
    public int checkAtr;

    /** コード: 条件のコード又はNo */
    @Column(name = "CONDITION_NO")
    public String conditionNo;

    /** "アラーム値日付：開始日
     年月日　OR　年月　OR　年"
     */
    @Column(name = "START_DATE")
    public String startDate;
}
