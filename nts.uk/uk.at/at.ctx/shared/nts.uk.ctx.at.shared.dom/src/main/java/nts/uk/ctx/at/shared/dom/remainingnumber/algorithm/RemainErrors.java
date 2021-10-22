package nts.uk.ctx.at.shared.dom.remainingnumber.algorithm;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.PauseError;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.DayOffError;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.children.service.ChildCareNurseErrors;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.service.SpecialLeaveError;
import nts.uk.ctx.at.shared.dom.remainingnumber.work.service.AnnualLeaveErrorSharedImport;
import nts.uk.ctx.at.shared.dom.remainingnumber.work.service.ReserveLeaveErrorImport;

@AllArgsConstructor
@Data
public class RemainErrors {

	// 代休エラー情報
	private List<DayOffError> dayOffErrors;

	// 振休エラー
	private List<PauseError> pErrors;

	// 特別休暇エラー情報
	private List<Pair<Integer, List<SpecialLeaveError>>> specialLeaveErrors;

	// 年休エラー情報
	private List<AnnualLeaveErrorSharedImport> annualErrors;

	// 年休積休エラー情報
	private List<ReserveLeaveErrorImport> reserveLeaveErrors;

	// 子の看護介護エラー情報
	private List<ChildCareNurseErrors> childCareErrors;

	/** エラー情報 */
	private List<ChildCareNurseErrors> nurseErrors;

	public RemainErrors() {
		this.dayOffErrors = new ArrayList<>();
		this.pErrors = new ArrayList<>();
		this.specialLeaveErrors = new ArrayList<>();
		this.annualErrors = new ArrayList<>();
		this.reserveLeaveErrors = new ArrayList<>();
		this.childCareErrors = new ArrayList<>();
		this.nurseErrors = new ArrayList<>();
	}
}
