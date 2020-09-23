package nts.uk.ctx.at.record.app.command.reservation.bento;

import lombok.Getter;
import nts.arc.time.GeneralDate;
/**
 * 予約構成を追加する\
 *
 * @author Minh Chinh.
 */
public class BentoMenuHistCommand {
    @Getter
    private GeneralDate date;
}
