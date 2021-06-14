package nts.uk.ctx.at.function.infra.entity.alarm.webmenu;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * Entity: アラームリストのWebメニュー
 * @author viet.tx
 */
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class KfndtAlarmWebMenuPk implements Serializable{
    private static final long serialVersionUID = 1L;

    /** 会社ID */
    @Column(name = "CID")
    public String cid;

    /** システム:
     0:共通
     1：勤次郎
     2：オフィスヘルパー
     3：Q太郎
     4：人事郎 */
    @Column(name = "SYSTEM")
    public int system;

    /** カテゴリ区分:
     0:スケジュール日次
     1:スケジュール週次
     2:スケジュール4週
     3:スケジュール月次
     4:スケジュール年間
     5:日次
     6:週次
     7:月次
     8:申請承認
     9:複数月
     10:任意期間
     11:年休
     12:３６協定
     13:工数チェック
     14:マスタチェック*/
    @Column(name = "CATEGORY")
    public int category;

    /** チェック種類：
     0：固定チェック
     １：自由チェック
     ２：36法定
     3：36超過 */
    @Column(name = "CHECK_ATR")
    public int checkAtr;

    /** メニュー分類
     0：標準
     1：任意項目申請
     2：携帯
     3：タブレット
     4：コード名称
     5：グループ会社メニュー
     6：カスタマイズ
     7：オフィスヘルパー稟議書
     8：トップページ
     9：スマートフォン */
    @Column(name = "MENU_CLS")
    public int menuCls;

    /** メニューコード */
    @Column(name = "MENU_CODE")
    public String menuCode;

    /** アラームチェック条件コード */
    @Column(name = "ALARM_CHECK_CODE")
    public String alarmCheckCode;

    /** アラームコード */
    @Column(name = "CONDITIONT_CODE")
    public String conditionCode;
}
