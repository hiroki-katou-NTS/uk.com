package nts.uk.ctx.at.shared.dom;

import java.util.Optional;

import org.apache.commons.lang3.StringUtils;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.SetupType;
import nts.uk.ctx.at.shared.dom.workrule.ErrorStatusWorkInfo;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;
import nts.uk.shr.com.context.AppContexts;

/**
 * 勤務情報
 * 
 * @author ken_takasu
 *
 */
@Getter
public class WorkInformation {

	private WorkTypeCode workTypeCode;
	private WorkTimeCode siftCode;

	public WorkInformation(String workTimeCode, String workTypeCode) {
		this.siftCode = StringUtils.isEmpty(workTimeCode) ? null : new WorkTimeCode(workTimeCode);
		this.workTypeCode = workTypeCode == null ? null : new WorkTypeCode(workTypeCode);
	}

	public WorkInformation(WorkTimeCode workTimeCode, WorkTypeCode workTypeCode) {
		this.siftCode = workTimeCode;
		this.workTypeCode = workTypeCode;
	}

	public WorkTimeCode getWorkTimeCode() {
		return this.siftCode;
	}

	public void removeWorkTimeInHolydayWorkType() {
		this.siftCode = null;
	}

	public void setSiftCode(WorkTimeCode siftCode) {
		this.siftCode = siftCode;
	}

	public void setWorkTypeCode(WorkTypeCode workTypeCode) {
		this.workTypeCode = workTypeCode;
	}

	/**
	 * [2] エラー状態をチェックする
	 * 
	 * @param require
	 */
	public ErrorStatusWorkInfo checkErrorCondition(Require require) {
		try {
			// if $勤務種類.isEmpty
			if(this.workTypeCode == null ) {
				return ErrorStatusWorkInfo.WORKTYPE_WAS_DELETE;
			}
			
			// require.勤務種類を取得する(@勤務種類コード)
			SetupType setupType = require.checkNeededOfWorkTimeSetting(this.workTypeCode.v());

			// 必須
			if (setupType == SetupType.REQUIRED) {
				// @就業時間帯コード ==null
				if (this.getSiftCode() == null) {
					return ErrorStatusWorkInfo.WORKTIME_ARE_REQUIRE_NOT_SET;
				}
			}

			// 任意
			if (setupType == SetupType.OPTIONAL) {
				// @就業時間帯コード ==null
				if (this.getSiftCode() == null) {
					return ErrorStatusWorkInfo.NORMAL;
				}

			}

			// 不要
			if (setupType == SetupType.NOT_REQUIRED) {
				// @就業時間帯コード ==null
				if (this.getSiftCode() == null) {
					return ErrorStatusWorkInfo.NORMAL;
				}
				// @就業時間帯コード.isPresent
				return ErrorStatusWorkInfo.WORKTIME_ARE_SET_WHEN_UNNECESSARY;

			}

		} catch (Exception e) {
			// if $勤務種類.isEmpty
			return ErrorStatusWorkInfo.WORKTYPE_WAS_DELETE;
		}

		// require.就業時間帯を取得する(ログイン会社ID, @就業時間帯コード) - CID sẽ dc truyền trên app
		Optional<WorkType> workTypeOpt = require.findByPK(this.siftCode == null ? null : this.siftCode.v());
		// if $就業時間帯.isEmpty
		if (!workTypeOpt.isPresent()) {
			return ErrorStatusWorkInfo.WORKTIME_WAS_DELETE;
		}

		return ErrorStatusWorkInfo.NORMAL;
	}

	public static interface Require {
		SetupType checkNeededOfWorkTimeSetting(String workTypeCode);

		Optional<WorkType> findByPK(String workTypeCd);
	}
}
