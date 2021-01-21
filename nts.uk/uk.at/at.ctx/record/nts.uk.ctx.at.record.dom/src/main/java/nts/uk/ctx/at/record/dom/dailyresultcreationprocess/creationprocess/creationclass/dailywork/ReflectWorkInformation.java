package nts.uk.ctx.at.record.dom.dailyresultcreationprocess.creationprocess.creationclass.dailywork;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.createdailyoneday.imprint.changeworktohalfdayleave.ChangeWorkToHalfdayLeave;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.createdailyoneday.reflectcalcategory.ReflectCalCategory;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.reflectworkinfor.reflectworktimestamp.ReflectWorkTimeStamp;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.reflectworkinfor.updateifnotmanaged.UpdateIfNotManaged;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.Stamp;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.editstate.EditStateOfDailyAttd;
import nts.uk.ctx.at.shared.dom.workrecord.workperfor.dailymonthlyprocessing.ErrorMessageInfo;

/**
 * 勤務情報を反映する (new_2020)
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務実績.日別実績作成処理.作成処理.作成用クラス.日別作成WORK.勤務情報を反映する
 * @author tutk
 *
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class ReflectWorkInformation {
	@Inject
	private UpdateIfNotManaged updateIfNotManaged;
	
	@Inject
	private ChangeWorkToHalfdayLeave changeWorkToHalfdayLeave;
	
	@Inject
	private ReflectWorkTimeStamp reflectWorkTimeStamp;
	
	@Inject
	private ReflectCalCategory reflectCalCategory;
	/**
	 * 勤務情報を反映する
	 */
	public List<ErrorMessageInfo> reflect(boolean isReflectStampGoingToWork,boolean isReflectStampLeavingWork,String companyId,
			String employeeId, GeneralDate ymd, Stamp stamp, IntegrationOfDaily integrationOfDaily) {
		List<ErrorMessageInfo> listErrorMessageInfo = new ArrayList<>();
		if (isReflectStampGoingToWork) {
			// スケジュール管理しない場合勤務情報を更新
			updateIfNotManaged.update(companyId, employeeId, ymd, integrationOfDaily);
		}
		if (isReflectStampGoingToWork || isReflectStampLeavingWork) {
			// 半休により勤務変更する
			listErrorMessageInfo
					.addAll(changeWorkToHalfdayLeave.changeWork(stamp, integrationOfDaily, new ArrayList<>()));
		}
		if (stamp.getRefActualResults().getWorkTimeCode().isPresent()) {
			// 打刻の就業時間帯を反映
			reflectWorkTimeStamp.reflectStamp(stamp.getRefActualResults().getWorkTimeCode().get(), integrationOfDaily);
		}
		// 計算区分に反映する
		List<EditStateOfDailyAttd> reflects = reflectCalCategory.reflect(stamp.getType().getChangeCalArt(), integrationOfDaily.getCalAttr(),
				integrationOfDaily.getEditState());
		reflects.addAll(integrationOfDaily.getEditState());
		integrationOfDaily.setEditState(reflects);
		return listErrorMessageInfo;
	}

}
