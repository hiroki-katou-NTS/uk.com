package nts.uk.ctx.at.shared.dom.workrule.shiftmaster;

import lombok.Getter;
import nts.arc.error.BusinessException;
import nts.arc.layer.dom.objecttype.DomainAggregate;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.workrule.ErrorStatusWorkInfo;

/**
 * シフトマスタ
 * 
 * @author tutk
 *
 */
public class ShiftMaster extends WorkInformation implements DomainAggregate {
	/**
	 * 会社ID
	 */
	@Getter
	private final String companyId;

	/**
	 * コード
	 */
	@Getter
	private final ShiftMasterCode shiftMasterCode;

	/**
	 * 表示情報
	 */
	@Getter
	private ShiftMasterDisInfor displayInfor;

	/**
	 * 作る
	 * 
	 * @param companyId
	 * @param shiftMaterCode
	 * @param displayInfor
	 */

	public ShiftMaster(String companyId, ShiftMasterCode shiftMaterCode, ShiftMasterDisInfor displayInfor,
			String workTypeCode, String workTimeCode) {
		super(workTypeCode,workTimeCode);
		this.companyId = companyId;
		this.shiftMasterCode = shiftMaterCode;
		this.displayInfor = displayInfor;

	}

	/**
	 * エラーチェックする
	 */
	public void checkError(WorkInformation.Require require) {
		ErrorStatusWorkInfo errorStatusWorkInfo = super.checkErrorCondition(require);
		if (errorStatusWorkInfo == ErrorStatusWorkInfo.WORKTYPE_WAS_DELETE) {
			throw new BusinessException("Msg_1608");
		} else if (errorStatusWorkInfo == ErrorStatusWorkInfo.WORKTIME_WAS_DELETE) {
			throw new BusinessException("Msg_1609");
		} else if (errorStatusWorkInfo == ErrorStatusWorkInfo.WORKTIME_ARE_REQUIRE_NOT_SET) {
			throw new BusinessException("Msg_435");
		} else if (errorStatusWorkInfo == ErrorStatusWorkInfo.WORKTIME_ARE_SET_WHEN_UNNECESSARY) {
			throw new BusinessException("Msg_434");
		}
	}

	/**
	 * 変更する
	 * 
	 * @param displayInfor
	 * @param workInformation
	 */
	public void change(ShiftMasterDisInfor displayInfor, WorkInformation workInformation) {
		this.displayInfor = displayInfor;
		super.setWorkTimeCode(workInformation.getWorkTimeCode());
		super.setWorkTypeCode(workInformation.getWorkTypeCode());
	}

}
