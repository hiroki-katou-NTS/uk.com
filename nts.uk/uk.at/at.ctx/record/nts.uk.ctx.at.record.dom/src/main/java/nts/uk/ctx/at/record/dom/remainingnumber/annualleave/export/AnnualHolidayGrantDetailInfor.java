package nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export;

import java.util.List;

import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.export.param.AnnualHolidayGrantDetail;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.export.param.ReferenceAtr;

public interface AnnualHolidayGrantDetailInfor {
	/**
	 * 年休明細情報を取得
	 * @param sid 社員ID
	 * @param referenceAtr 参照先区分
	 * @param ym 指定年月
	 * @param ymd 基準日
	 * @return
	 */
	List<AnnualHolidayGrantDetail> getAnnHolidayDetail(String cid, String sid, ReferenceAtr referenceAtr,
			YearMonth ym, GeneralDate ymd);
}
