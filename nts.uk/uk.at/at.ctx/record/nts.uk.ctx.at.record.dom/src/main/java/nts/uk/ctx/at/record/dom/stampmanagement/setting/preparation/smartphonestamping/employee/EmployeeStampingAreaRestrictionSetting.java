package nts.uk.ctx.at.record.dom.stampmanagement.setting.preparation.smartphonestamping.employee;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import nts.arc.layer.dom.AggregateRoot;
import nts.gul.location.GeoCoordinate;
import nts.uk.ctx.at.record.dom.stampmanagement.setting.preparation.smartphonestamping.employee.StampingAreaRestriction.Require;
import nts.uk.ctx.at.record.dom.stampmanagement.workplace.WorkLocation;

/**
 * 社員別の打刻エリア制限設定
 * @author NWS_vandv
 *
 */
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public class EmployeeStampingAreaRestrictionSetting extends AggregateRoot {
	
	// 社員ID
	private final String employeeId;
	
	// 打刻エリア制限
	private StampingAreaRestriction stampingAreaRestriction;

	/**
	 *打刻してもいいエリアかチェックする
	 */
	public Optional<WorkLocation> checkAreaStamp(Require require, String contractCd, String companyId, String employeeId, Optional<GeoCoordinate> positionInfor) {
		
		return stampingAreaRestriction.checkAreaStamp(require, contractCd, companyId, employeeId, positionInfor);	
	}
	
}

