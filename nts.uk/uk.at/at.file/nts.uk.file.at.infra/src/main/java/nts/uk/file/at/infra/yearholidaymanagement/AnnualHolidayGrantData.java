package nts.uk.file.at.infra.yearholidaymanagement;

import java.util.List;
import java.util.Optional;

import lombok.Data;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.export.param.AnnualHolidayGrantDetail;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.export.param.AnnualHolidayGrantInfor;

@Data
public class AnnualHolidayGrantData {
	// 年休付与情報
	private Optional<AnnualHolidayGrantInfor> annualHolidayGrantInfor;
	// 年休使用詳細
	private List<AnnualHolidayGrantDetail> holidayDetails;
}
