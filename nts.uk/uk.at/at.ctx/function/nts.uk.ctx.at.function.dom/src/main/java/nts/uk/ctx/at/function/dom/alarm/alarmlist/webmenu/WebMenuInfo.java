package nts.uk.ctx.at.function.dom.alarm.alarmlist.webmenu;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Webメニュー情報
 * @author viet.tx
 */
@AllArgsConstructor
@Getter
public class WebMenuInfo {
    /** システム/ System */
    private System system;

    /** メニューコード */
    private WebMenuCode webMenuCode;

    /** メニュー分類 */
    private MenuClassification menuClassification;
}
