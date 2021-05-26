package nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.applicationapproval;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.primitivevalue.DisplayMessage;

/**
 * AggregateRoot: アラームリスト（職場）申請承認の固定抽出条件
 *
 * @author Thanh.LNP
 */
@Getter
@AllArgsConstructor
public class FixedExtractionAppapvCon extends AggregateRoot {
    /**
     * 職場のエラーアラームチェックID
     */
    private String errorAlarmWorkplaceId;

    /**
     * No
     */
    private CheckItemAppapv checkItemAppapv;

    /**
     * 表示するメッセージ
     */
    private DisplayMessage messageDisp;

    /**
     * 使用区分
     */
    private boolean useAtr;

    /**
     * 作成する
     *
     * @param errorAlarmWorkplaceId 職場のエラーアラームチェックID
     * @param checkItemAppapv       No
     * @param messageDisp           表示するメッセージ
     * @param useAtr                使用区分
     */
    public static FixedExtractionAppapvCon create(String errorAlarmWorkplaceId,
                                                  int checkItemAppapv,
                                                  String messageDisp,
                                                  boolean useAtr) {

        return new FixedExtractionAppapvCon(errorAlarmWorkplaceId,
                EnumAdaptor.valueOf(checkItemAppapv, CheckItemAppapv.class),
                new DisplayMessage(messageDisp),
                useAtr);
    }
}
