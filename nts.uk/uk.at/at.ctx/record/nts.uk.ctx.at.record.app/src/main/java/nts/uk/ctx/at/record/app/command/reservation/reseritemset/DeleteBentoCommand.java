package nts.uk.ctx.at.record.app.command.reservation.reseritemset;
import lombok.Getter;

@Getter
public class DeleteBentoCommand {

    private String histId;

    // 枠番
    private int frameNo;

}
