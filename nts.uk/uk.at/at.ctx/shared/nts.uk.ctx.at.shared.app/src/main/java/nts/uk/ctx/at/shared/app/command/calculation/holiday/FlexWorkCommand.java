package nts.uk.ctx.at.shared.app.command.calculation.holiday;

import lombok.Data;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * 
 * @author phongtq
 *
 */
@Data
public class FlexWorkCommand {
	/** 会社ID */
	private String companyId;

	/** 実働のみで計算する */
	private int calcActualOperationPre;

	/** インターバル免除時間を含めて計算する */
	private int exemptTaxTimePre;

	/** 育児・介護時間を含めて計算する */
	private int incChildNursingCarePre;

	/** フレックスの所定超過時 */
	private int predeterminedOvertimePre;

	/** 加算する */
	private int additionTimePre;

	/** 遅刻・早退を控除しない */
	private int notDeductLateleavePre;

	/** インターバル免除時間を含めて計算する */
	private int exemptTaxTimeWork;

	/** 欠勤時間をマイナスする */
	private int minusAbsenceTimeWork;

	/** 実働のみで計算する */
	private int calcActualOperationWork;

	/** 育児・介護時間を含めて計算する */
	private int incChildNursingCareWork;

	/** 遅刻・早退を控除しない */
	private int notDeductLateleaveWork;

	/** フレックスの所定不足時 */
	private int predeterminDeficiencyWork;

	/** 加算する */
	private int additionTimeWork;
	
	/*就業時間帯毎の設定を可能とする*/
	private int enableSetPerWorkHour1;
	
	/*就業時間帯毎の設定を可能とする*/
	private int enableSetPerWorkHour2;
	
	/*月次法定内のみ加算*/
	private int additionWithinMonthlyStatutory;
}
