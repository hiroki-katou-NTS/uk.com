package nts.uk.ctx.at.shared.app.find.remainingnumber.nursingcareleave;

import java.util.Map;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.care.CareUsedNumberData;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.childcare.ChildCareNurseUsedNumber;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.childcare.ChildCareUsedNumberData;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.info.ChildCareLeaveRemainingInfo;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.info.CareLeaveRemainingInfo;
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
	private Integer childCareThisFiscal;

	@PeregItem("IS00378")
	// 次年度の子の看護上限日数
	private Integer childCareNextFiscal;

	// 子の看護休暇管理
	@PeregItem("IS00379")
	private Double childCareUsedDays;
	
	// 子の看護使用時間
	@PeregItem("IS01101")
	private Integer childCareUsedTimes;

	// 介護休暇管理
	@PeregItem("IS00380")
	private int careUseArt;

	@PeregItem("IS00381")
	// 介護上限設定
	private Integer careUpLimSet;

	@PeregItem("IS00382")
	// 本年度の介護上限日数
	private Integer careThisFiscal;

	@PeregItem("IS00383")
	// 次年度の介護上限日数
	private Integer careNextFiscal;

	// 介護使用日数
	@PeregItem("IS00384")
	private Double careUsedDays;
	
	// 介護使用時間
	@PeregItem("IS01102")
	private Integer careUsedTimes;

	public static CareLeaveInfoDto createFromDomain(String employeeId,
			Optional<ChildCareLeaveRemainingInfo> childCareInfoDomainOpt,
			Optional<ChildCareUsedNumberData>childCareUsedNumberDataOpt,
			Optional<CareLeaveRemainingInfo> careInfoDomainOpt,
			Optional<CareUsedNumberData> careUsedNumberDataOpt) {
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
		if (childCareUsedNumberDataOpt.isPresent()) {
			ChildCareUsedNumberData childCareDataDomain = childCareUsedNumberDataOpt.get();
			result.setChildCareUsedDays(childCareDataDomain.getUsedDay().v());
			if (childCareDataDomain.getUsedTimes().isPresent()) {
				result.setChildCareUsedTimes(childCareDataDomain.getUsedTimes().get().v());
			} else {
				result.setChildCareUsedTimes(null);
			}
		} else {
			result.setChildCareUsedDays(null);
			result.setChildCareUsedTimes(null);
		}

		// care-info
		if (careInfoDomainOpt.isPresent()) {
			CareLeaveRemainingInfo careInfoDomain = careInfoDomainOpt.get();
			result.setCareUseArt(careInfoDomain.isUseClassification() ? 1 : 0);
			result.setCareUpLimSet(careInfoDomain.getUpperlimitSetting().value);
			result.setCareThisFiscal(careInfoDomain.getMaxDayForThisFiscalYear().isPresent()
					? careInfoDomain.getMaxDayForThisFiscalYear().get().v() : null);
			result.setCareNextFiscal(careInfoDomain.getMaxDayForNextFiscalYear().isPresent()
					? careInfoDomain.getMaxDayForNextFiscalYear().get().v() : null);
		}

		//care-data
		if (careUsedNumberDataOpt.isPresent()) {
			CareUsedNumberData careDataDomain = careUsedNumberDataOpt.get();
			result.setCareUsedDays(careDataDomain.getUsedDay().v());
			if (careDataDomain.getUsedTimes().isPresent()) {
				result.setCareUsedTimes(careDataDomain.getUsedTimes().get().v());
			} else {
				result.setCareUsedTimes(null);
			}
		} else {
			result.setCareUsedDays(null);
			result.setCareUsedTimes(null);
		}

		return result;
	}

	public static CareLeaveInfoDto createFromDomainCps013(String employeeId,
			Optional<ChildCareLeaveRemainingInfo> childCareLeaveRemainingInfoOpt,
			Optional<ChildCareUsedNumberData> childCareUsedNumberDataOpt,
			Optional<CareLeaveRemainingInfo> careLeaveRemainingInfoOpt,
			Optional<CareUsedNumberData> careUsedNumberDataOpt, Map<String, Object> enums) {
		CareLeaveInfoDto result = new CareLeaveInfoDto();
		result.setSId(employeeId);
		result.setRecordId(employeeId);

		// child-care-info
		if (childCareLeaveRemainingInfoOpt.isPresent()) {

			Integer childCareUseArt = (Integer)enums.get("IS00375");
			Integer childCareUpLimSet = (Integer)enums.get("IS00376");

			ChildCareLeaveRemainingInfo childCareInfoDomain = childCareLeaveRemainingInfoOpt.get();
			result.setChildCareUseArt(childCareUseArt);
			result.setChildCareUpLimSet(childCareUpLimSet);
			result.setChildCareThisFiscal(childCareInfoDomain.getMaxDayForThisFiscalYear().isPresent()
					? childCareInfoDomain.getMaxDayForThisFiscalYear().get().v() : null);
			result.setChildCareNextFiscal(childCareInfoDomain.getMaxDayForNextFiscalYear().isPresent()
					? childCareInfoDomain.getMaxDayForNextFiscalYear().get().v() : null);
		}

		// child-care-data
		if (childCareUsedNumberDataOpt.isPresent()) {
			ChildCareUsedNumberData childCareDataDomain = childCareUsedNumberDataOpt.get();
			result.setChildCareUsedDays(childCareDataDomain.getUsedDay().v());
			if (childCareDataDomain.getUsedTimes().isPresent()) {
				result.setChildCareUsedTimes(childCareDataDomain.getUsedTimes().get().v());
			} else {
				result.setChildCareUsedTimes(null);
			}
		} else {
			result.setChildCareUsedDays(null);
			result.setChildCareUsedTimes(null);
		}

		// care-info
		if (careLeaveRemainingInfoOpt.isPresent()) {
			CareLeaveRemainingInfo careInfoDomain = careLeaveRemainingInfoOpt.get();

			Integer careUseArt = (Integer)enums.get("IS00380");
			Integer careUpLimSet = (Integer)enums.get("IS00381");

			result.setCareUseArt(careUseArt);
			result.setCareUpLimSet(careUpLimSet);
			result.setCareThisFiscal(careInfoDomain.getMaxDayForThisFiscalYear().isPresent()
					? careInfoDomain.getMaxDayForThisFiscalYear().get().v() : null);
			result.setCareNextFiscal(careInfoDomain.getMaxDayForNextFiscalYear().isPresent()
					? careInfoDomain.getMaxDayForNextFiscalYear().get().v() : null);
		}

		//care-data
		if (careUsedNumberDataOpt.isPresent()) {
			CareUsedNumberData careDataDomain = careUsedNumberDataOpt.get();
			result.setCareUsedDays(careDataDomain.getUsedDay().v());
			if (careDataDomain.getUsedTimes().isPresent()) {
				result.setCareUsedTimes(careDataDomain.getUsedTimes().get().v());
			} else {
				result.setCareUsedTimes(null);
			}
		} else {
			result.setCareUsedDays(null);
			result.setCareUsedTimes(null);
		}

		return result;
	}

}
