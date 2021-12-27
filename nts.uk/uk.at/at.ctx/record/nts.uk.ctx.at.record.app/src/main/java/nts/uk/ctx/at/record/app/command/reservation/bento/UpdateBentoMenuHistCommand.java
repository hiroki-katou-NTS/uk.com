package nts.uk.ctx.at.record.app.command.reservation.bento;

import lombok.Getter;

@Getter
public class UpdateBentoMenuHistCommand {
	/**
	 * 期間
	 */
    public String startDatePerio;
    public String endDatePerio;
    /**
     * 元の開始日
     */
    public String originalStartDate;

}

