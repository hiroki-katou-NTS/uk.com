package nts.uk.ctx.at.record.dom.remainingnumber.childcarenurse.childcare;

import java.util.List;
import java.util.Optional;

import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.CreateAtr;


/**
 * 上書き用の暫定管理データWORK
 * @author yuri_tamakoshi
 */
@Getter
@Setter
public class TmpChildCareNurseMngWork {

	/** 社員ID */
	private String employeeId;
	/** 期間 */
	private DatePeriod period;
	/** 上書きフラグ */
	private Boolean isOverWrite;
	/** 上書き用暫定管理データ */
	private List<TmpChildCareNurseMngWork> tempChildCareDataforOverWriteList;
	/** 作成元区分<Optional> */
	private Optional<CreateAtr> creatorAtr;
	/** 上書き対象期間<Optional> */
	private Optional<GeneralDate> periodOverWrite;

	/**
	 * ファクトリー
	 * @param employeeId 社員ID
	 * @param period 期間
	 * @param isOverWrite 上書きフラグ
	 * @param tempChildCareDataforOverWriteList 上書き用暫定管理データ
	 * @param creatorAtr 作成元区分
	 * @param periodOverWrite 残数分類
	 * @return 暫定管理データWORK
	 */
	public static TmpChildCareNurseMngWork of (

			String employeeId,
			DatePeriod period,
			Boolean isOverWrite,
			List<TmpChildCareNurseMngWork> tempChildCareDataforOverWriteList,
			Optional<CreateAtr> creatorAtr,
			Optional<GeneralDate> periodOverWrite) {

		TmpChildCareNurseMngWork domain = new TmpChildCareNurseMngWork ();
		domain.employeeId = employeeId;
		domain.period = period;
		domain.isOverWrite = isOverWrite;
		domain.tempChildCareDataforOverWriteList = tempChildCareDataforOverWriteList;
		domain.creatorAtr = creatorAtr;
		domain.periodOverWrite = periodOverWrite;
		return domain;
	}
}
