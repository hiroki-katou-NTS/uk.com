package nts.uk.screen.com.app.find.user.information.anniversary;

import lombok.Data;
import lombok.Builder;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.person.dom.person.personal.anniversary.AnniversaryNotice;


import java.time.MonthDay;

/**
 * Dto 個人の記念日情報
 */
@Data
@Builder
public class AnniversaryNoticeDto implements AnniversaryNotice.MementoSetter {
    /**
     * 個人ID
     */
    private String personalId;

    /**
     * 日数前の通知
     */
    private Integer noticeDay;

    /**
     * 最後見た記念日
     */
    private GeneralDate seenDate;

    /**
     * 記念日
     */
    private String anniversary;

    /**
     * 記念日のタイトル
     */
    private String anniversaryTitle;

    /**
     * 記念日の内容
     */
    private String notificationMessage;

    @Override
    public void setAnniversary(MonthDay anniversary) {
        this.anniversary = anniversary.format(AnniversaryNotice.FORMAT_MONTH_DAY);
    }
}
