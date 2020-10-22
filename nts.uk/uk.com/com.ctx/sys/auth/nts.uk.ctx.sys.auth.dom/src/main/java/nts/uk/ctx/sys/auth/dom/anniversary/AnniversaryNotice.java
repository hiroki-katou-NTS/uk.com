package nts.uk.ctx.sys.auth.dom.anniversary;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;

import java.time.MonthDay;
import java.time.format.DateTimeFormatter;

/**
 * UKDesign.データベース.ER図.基幹.個人.個人のインフォメーション.個人のインフォメーション.個人の記念日情報
 */
@Getter
public class AnniversaryNotice extends AggregateRoot {
    /**
     * 個人ID
     */
    private String personalId;

    /**
     * 日数前の通知
     */
    private NoticeDay noticeDay;

    /**
     * 最後見た記念日
     */
    private GeneralDate seenDate;

    /**
     * 記念日
     */
    private MonthDay anniversary;

    /**
     * 記念日のタイトル
     */
    private AnniversaryTitle anniversaryTitle;

    /**
     * 記念日の内容
     */
    private NotificationMessage notificationMessage;

    public static DateTimeFormatter FORMAT_MONTH_DAY = DateTimeFormatter.ofPattern("MMdd");

    public static AnniversaryNotice createFromMemento(MementoGetter memento) {
        AnniversaryNotice domain = new AnniversaryNotice();
        domain.getMemento(memento);
        MonthDay anniversary = memento.getAnniversary();
        int noticeDay = memento.getNoticeDay();
        GeneralDate todayAnniversary = GeneralDate.ymd(GeneralDate.today().year(), anniversary.getMonth().getValue(), anniversary.getDayOfMonth());
        if (todayAnniversary.addDays(-noticeDay).compareTo(GeneralDate.today()) <= 0) {
            domain.seenDate = todayAnniversary;
        } else {
            domain.seenDate = todayAnniversary.addYears(-1);
        }
        return domain;
    }

    //新記念日の判断
    public boolean isNewAnniversary(GeneralDate date) {
        GeneralDate checkDate = this.seenDate.addYears(1).addDays(-this.noticeDay.value);
        return checkDate.compareTo(date) <= 0;
    }

    // 最後見た記念日をUpdateする
    public void updateSeenDate(GeneralDate date) {
        GeneralDate todayAnniversary = GeneralDate.ymd(date.year(), this.anniversary.getMonth().getValue(), this.anniversary.getDayOfMonth());
        GeneralDate checkDate = todayAnniversary.addDays(-this.noticeDay.value);
        if (checkDate.compareTo(date) <= 0) {
            this.seenDate = todayAnniversary;
        } else {
            this.seenDate = todayAnniversary.addYears(-1);
        }
    }

    public void getMemento(MementoGetter memento) {
        this.personalId = memento.getPersonalId();
        this.noticeDay = EnumAdaptor.valueOf(memento.getNoticeDay(), NoticeDay.class);
        this.seenDate = memento.getSeenDate();
        this.anniversary = memento.getAnniversary();
        this.anniversaryTitle = new AnniversaryTitle(memento.getAnniversaryTitle());
        this.notificationMessage = new NotificationMessage(memento.getNotificationMessage());
    }

    public void setMemento(MementoSetter memento) {
        memento.setPersonalId(this.personalId);
        memento.setNoticeDay(this.noticeDay.value);
        memento.setSeenDate(this.seenDate);
        memento.setAnniversary(this.anniversary);
        memento.setAnniversaryTitle(this.anniversaryTitle.v());
        memento.setNotificationMessage(this.notificationMessage.v());
    }

    public interface MementoSetter {
        void setPersonalId(String personalId);

        void setNoticeDay(Integer noticeDay);

        void setSeenDate(GeneralDate seenDate);

        void setAnniversary(MonthDay anniversary);

        void setAnniversaryTitle(String anniversaryTitle);

        void setNotificationMessage(String notificationMessage);
    }

    public interface MementoGetter {
        String getPersonalId();

        Integer getNoticeDay();

        GeneralDate getSeenDate();

        MonthDay getAnniversary();

        String getAnniversaryTitle();

        String getNotificationMessage();
    }
}
