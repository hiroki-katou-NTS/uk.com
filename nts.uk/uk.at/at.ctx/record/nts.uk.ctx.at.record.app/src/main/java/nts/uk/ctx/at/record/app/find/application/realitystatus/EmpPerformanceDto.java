package nts.uk.ctx.at.record.app.find.application.realitystatus;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.arc.time.GeneralDate;

/**
 * @author dat.lh
 *
 */
@Value
@AllArgsConstructor
public class EmpPerformanceDto {
	/**
	 * 社員ID
	 */
	private String sId;
	/**
	 * 社員名
	 */
	private String sName;
	/**
	 * 社員ID.期間
	 */
	private GeneralDate startDate;
	/**
	 * 社員ID.期間
	 */
	private GeneralDate endDate;	
	/**
	 * 承認状況
	 */
	private Integer approvalStatus;
	/**
	 * 日別確認
	 */
	List<DailyConfirmDto> listDailyConfirm;
	/**
	 * エラー状況
	 */
	List<GeneralDate> listErrorStatus;
}
