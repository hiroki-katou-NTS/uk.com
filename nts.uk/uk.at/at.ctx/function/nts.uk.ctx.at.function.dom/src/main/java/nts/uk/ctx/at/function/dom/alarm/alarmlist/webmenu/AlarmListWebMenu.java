package nts.uk.ctx.at.function.dom.alarm.alarmlist.webmenu;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.AlarmCheckConditionCode;
import nts.uk.ctx.at.shared.dom.alarmList.AlarmCategory;
import nts.uk.ctx.at.shared.dom.alarmList.extractionResult.AlarmListCheckType;

import java.util.List;

/**
 * アラームリストのWebメニュー
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.就業機能.アラーム.永続化のアラームリスト.アラームリストのWebメニュー.アラームリストのWebメニュー
 * @author viet.tx
 */
@AllArgsConstructor
@Getter
public class AlarmListWebMenu extends AggregateRoot {
    /** 会社ID */
    private String companyID;

    /** アラームコード */
    private String alarmCode;

    /** アラームチェック条件コード */
    private AlarmCheckConditionCode alarmCheckConditionCode;

    /** カテゴリ区分 */
    private AlarmCategory alarmCategory;

    /** チェック種類 */
    private AlarmListCheckType checkType;

    /** Webメニュー */
    private List<WebMenuInfo> webMenuInfoList;

}
