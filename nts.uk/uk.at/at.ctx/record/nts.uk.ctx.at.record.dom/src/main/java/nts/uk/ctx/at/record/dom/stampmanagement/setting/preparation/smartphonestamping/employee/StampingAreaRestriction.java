package nts.uk.ctx.at.record.dom.stampmanagement.setting.preparation.smartphonestamping.employee;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.eclipse.persistence.internal.xr.ValueObject;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.val;
import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;
import nts.gul.location.GeoCoordinate;
import nts.uk.ctx.at.record.dom.stampmanagement.workplace.WorkLocation;
import nts.uk.ctx.at.shared.dom.ot.frame.NotUseAtr;

/**
 * 打刻エリア制限
 * 
 * @author NWS_vandv
 *
 */
@AllArgsConstructor
@Getter
@Setter
public class StampingAreaRestriction extends ValueObject {

	private final NotUseAtr useLocationInformation;

	/** 制限方法 */
	private final StampingAreaLimit stampingAreaLimit;

	/**
	 * Check area stamp. 打刻してもいいエリアかチェックする
	 *
	 * @return the work location
	 */
	public Optional<WorkLocation> checkAreaStamp(Require require, String contractCd, String companyId,
												 String employeeId, Optional<GeoCoordinate> positionInfor) {
		Optional<WorkLocation> workLocation = null;
		List<WorkLocation> listWorkLocation = new ArrayList<>();
		if (useLocationInformation.equals(NotUseAtr.NOT_USE)) {
			return Optional.empty();
		}
		
		if (!positionInfor.isPresent()) {
			throw new BusinessException("Msg_2096");
		}
		
		if (stampingAreaLimit.equals(StampingAreaLimit.ONLY_THE_WORKPLACE_BELONG_ALLOWED)) {
			// $場所一覧 = [prv-1] 所属職場に紐づける勤務場所を特定する(require,契約コード,会社ID,社員ID,打刻位置)
			listWorkLocation = identifyWorkLocation(require, contractCd, companyId, employeeId, positionInfor);
		} else {
			// $場所一覧 = require.全ての勤務場所を取得する(契約コード)
			listWorkLocation = require.findAll(contractCd);
		}
		workLocation = listWorkLocation.stream()
				.sorted(Comparator.comparing(WorkLocation::getWorkLocationCD))
				.filter(c -> c.canStamptedByMobile(positionInfor.get()))
				.findFirst();
		
		if (!stampingAreaLimit.equals(StampingAreaLimit.NO_AREA_RESTRICTION) && !workLocation.isPresent()) {
			throw new BusinessException("Msg_2095");
		}
		
		return workLocation;
	}

	private List<WorkLocation> identifyWorkLocation(Require require, String contractCd, String companyId,
			String employeeId, Optional<GeoCoordinate> positionInfor) {
		// [R-1]
		val baseDate = GeneralDate.today();
		Optional<String> workplaceId = require.getWorkPlaceOfEmpl(employeeId, baseDate);
		if (!workplaceId.isPresent()) {
			throw new BusinessException("Msg_427");
		}
		return require.findByWorkPlace(contractCd, companyId, workplaceId.get());
	}

	public static interface Require {
		// [R-1] 社員が所属している職場を取得する
		Optional<String> getWorkPlaceOfEmpl(String employeeID, GeneralDate date);

		// [R-2] 職場グループ所属情報を取得する
		List<WorkLocation> findByWorkPlace(String contractCode, String cid, String workPlaceId);

		// 勤務場所Repository.契約コード条件として勤務場所を取得する(ログインしている契約コード)
		List<WorkLocation> findAll(String contractCode);

	}

}
