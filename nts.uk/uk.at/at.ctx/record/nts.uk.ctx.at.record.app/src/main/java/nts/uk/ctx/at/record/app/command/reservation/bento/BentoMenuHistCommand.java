package nts.uk.ctx.at.record.app.command.reservation.bento;

import lombok.Getter;
/**
 * 予約構成を追加する\
 *
 * @author Minh Chinh.
 */
@Getter
public class BentoMenuHistCommand {
    
	/**
	 * 期間
	 */
	private String startDate;
    private String endDate;
}
