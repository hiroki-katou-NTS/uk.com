package nts.uk.ctx.at.shared.app.command.employeeworkway.medicalworkstyle;

import java.math.BigDecimal;

import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.uk.shr.pereg.app.PeregEmployeeId;
import nts.uk.shr.pereg.app.PeregItem;
import nts.uk.shr.pereg.app.PeregRecordId;

@Getter
public class UpdateEmpMedicalWorkCommand {
	
	@PeregRecordId
	private String histId;

	/**
	 * 社員ID
	 */
	@PeregEmployeeId
	private String sId;

	/**
	 * 期間
	 */
	@PeregItem("IS01090")
	private String period;

	/**
	 * 開始日
	 */
	@Getter
	@PeregItem("IS01091")
	private GeneralDate startDate;

	/**
	 * 終了日
	 */
	@PeregItem("IS01092")
	private GeneralDate endDate;
	
	/**
	 * 夜勤専従者として扱う
	 */
	@PeregItem("IS01093")
	private BigDecimal isOnlyNightShift;
	
	/**
	 * 医療勤務形態
	 */
	@PeregItem("IS01094")
	private BigDecimal medicalWorkStyle;
	
	/**
	 * 他部署の兼務
	 */
	@PeregItem("IS01095")
	private BigDecimal isConcurrently;
	
	/**
	 * 看護区分
	 */
	@PeregItem("IS01096")
	private String nurseClassifiCode;
}
