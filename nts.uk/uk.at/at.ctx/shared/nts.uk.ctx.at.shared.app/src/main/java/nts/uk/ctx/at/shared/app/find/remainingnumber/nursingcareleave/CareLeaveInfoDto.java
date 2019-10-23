package nts.uk.ctx.at.shared.app.find.remainingnumber.nursingcareleave;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.data.ChildCareLeaveRemainingData;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.data.LeaveForCareData;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.info.ChildCareLeaveRemainingInfo;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.info.LeaveForCareInfo;
import nts.uk.shr.pereg.app.PeregEmployeeId;
import nts.uk.shr.pereg.app.PeregItem;
import nts.uk.shr.pereg.app.find.dto.PeregDomainDto;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CareLeaveInfoDto extends PeregDomainDto {

	// 社員ID
	@PeregEmployeeId
	private String sId;

	// 子の看護休暇管理
	@PeregItem("IS00375")
	private int childCareUseArt;

	@PeregItem("IS00376")
	// 子の看護上限設定
	private Integer childCareUpLimSet;

	@PeregItem("IS00377")
	// 本年度の子の看護上限日数
	private Double childCareThisFiscal;

	@PeregItem("IS00378")
	// 次年度の子の看護上限日数
	private Double childCareNextFiscal;

	// 子の看護休暇管理
	@PeregItem("IS00379")
	private Double childCareUsedDays;

	// 介護休暇管理
	@PeregItem("IS00380")
	private int careUseArt;

	@PeregItem("IS00381")
	// 介護上限設定
	private Integer careUpLimSet;

	@PeregItem("IS00382")
	// 本年度の介護上限日数
	private Double careThisFiscal;

	@PeregItem("IS00383")
	// 次年度の介護上限日数
	private Double careNextFiscal;

	// 介護使用日数
	@PeregItem("IS00384")
	private Double careUsedDays;

	public static CareLeaveInfoDto createFromDomain(String employeeId,
			Optional<ChildCareLeaveRemainingInfo> childCareInfoDomainOpt,
			Optional<ChildCareLeaveRemainingData> childCareDataDomainOpt,
			Optional<LeaveForCareInfo> careInfoDomainOpt, Optional<LeaveForCareData> careDataDomainOpt) {
		CareLeaveInfoDto result = new CareLeaveInfoDto();
		result.setSId(employeeId);
		result.setRecordId(employeeId);

		// child-care-info
		if (childCareInfoDomainOpt.isPresent()) {
			ChildCareLeaveRemainingInfo childCareInfoDomain = childCareInfoDomainOpt.get();
			result.setChildCareUseArt(childCareInfoDomain.isUseClassification() ? 1 : 0);
			result.setChildCareUpLimSet(childCareInfoDomain.getUpperlimitSetting().value);
			result.setChildCareThisFiscal(childCareInfoDomain.getMaxDayForThisFiscalYear().isPresent()
					? childCareInfoDomain.getMaxDayForThisFiscalYear().get().v() : null);
			result.setChildCareNextFiscal(childCareInfoDomain.getMaxDayForNextFiscalYear().isPresent()
					? childCareInfoDomain.getMaxDayForNextFiscalYear().get().v() : null);
		}

		// child-care-data
		if (childCareDataDomainOpt.isPresent()) {
			ChildCareLeaveRemainingData childCareDataDomain = childCareDataDomainOpt.get();
			result.setChildCareUsedDays(childCareDataDomain.getNumOfUsedDay().v());
		} else {
			result.setChildCareUsedDays(null);
		}

		// care-info
		if (careInfoDomainOpt.isPresent()) {
			LeaveForCareInfo careInfoDomain = careInfoDomainOpt.get();
			result.setCareUseArt(careInfoDomain.isUseClassification() ? 1 : 0);
			result.setCareUpLimSet(careInfoDomain.getUpperlimitSetting().value);
			result.setCareThisFiscal(careInfoDomain.getMaxDayForThisFiscalYear().isPresent()
					? careInfoDomain.getMaxDayForThisFiscalYear().get().v() : null);
			result.setCareNextFiscal(careInfoDomain.getMaxDayForNextFiscalYear().isPresent()
					? careInfoDomain.getMaxDayForNextFiscalYear().get().v() : null);
		}

		//care-data
		if (careDataDomainOpt.isPresent()) {
			LeaveForCareData careDataDomain = careDataDomainOpt.get();
			result.setCareUsedDays(careDataDomain.getNumOfUsedDay().v());
		} else {
			result.setCareUsedDays(null);
		}

		return result;
	}

}
