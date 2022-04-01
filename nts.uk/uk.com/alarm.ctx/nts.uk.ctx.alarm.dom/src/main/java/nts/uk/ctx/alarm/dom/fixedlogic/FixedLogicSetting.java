package nts.uk.ctx.alarm.dom.fixedlogic;

import lombok.Value;

/**
 * 固定ロジックの設定
 * @param <L> 固定ロジックのEunm型
 */
@Value
public class FixedLogicSetting<L> {

    /** ロジック */
    L logic;

    /** メッセージ */
    String message;
}
