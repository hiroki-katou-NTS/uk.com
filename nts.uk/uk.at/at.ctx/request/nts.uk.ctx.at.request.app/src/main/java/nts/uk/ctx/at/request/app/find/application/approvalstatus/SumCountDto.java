package nts.uk.ctx.at.request.app.find.application.approvalstatus;

/**
 * @author dat.lh
 */

public class SumCountDto {
	/**
	 * 月別確認件数
	 */
	int monthConfirm;
	/**
	 * 月別未確認件数
	 */
    int monthUnconfirm;
    /**
     * 本人確認件数
     */
	int personConfirm;
	/**
	 * 本人未確認件数
	 */
	int personUnconfirm;
	/**
	 * 上司確認件数
	 */
	int bossConfirm;
	/**
	 * 上司未確認件数
	 */
	int bossUnconfirm;
}
