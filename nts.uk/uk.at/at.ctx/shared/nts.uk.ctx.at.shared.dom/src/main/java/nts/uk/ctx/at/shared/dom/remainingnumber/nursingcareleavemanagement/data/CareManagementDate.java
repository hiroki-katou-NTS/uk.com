package nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.data;

import java.util.List;
import java.util.Optional;

import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.CareTargetPeriod;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.CareTargetPeriodWork;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.ChildCareTargetChanged;
import nts.uk.shr.com.history.DateHistoryItem;

/**
 *　介護対象管理データ
  * @author yuri_tamakoshi
 */
public class CareManagementDate extends AggregateRoot{

	/** 家族ID */
	private String familyID;
	/** 介護対象 */
	private boolean careManagement;
	/** 履歴 */
	private DateHistoryItem history;

	/**
	 * コンストラクタ
	 */
	public CareManagementDate(){
		this.familyID = "";
		this.careManagement =  false;
		this.history = new DateHistoryItem(familyID, new DatePeriod(GeneralDate.min(), GeneralDate.min()));
	}
	/**
	 * ファクトリー
	 * @param familyID 家族ID
	 * @param careManagement 介護対象
	 * @param history 履歴
	 * @return 介護対象管理データ
	 */
	public static CareManagementDate of(
			String familyID,
			boolean careManagement,
			DateHistoryItem history) {

		CareManagementDate domain = new CareManagementDate();
		domain.familyID = familyID;
		domain.careManagement = careManagement;
		domain.history = history;
		return domain;
	}

	/**
	 * 介護対象期間を確認
	 * @param period 期間
	 * @param deadYmd 死亡年月日
	 * @param childCareTargetChanged 看護介護対象人数変更日（List）
	 * @return careTargetPeriodWork 看護介護対象人数変更日（List）、介護対象期間
	 *
	 */
	public CareTargetPeriodWork careTargetPeriodWork(DatePeriod period, Optional<GeneralDate> deadYmd, List<ChildCareTargetChanged> childCareTargetChanged){

		// 介護期間変更日
		CarePeriodChangeDate carePeriodChangeDate;

		// 介護対象か
		if(!careManagement) {
			// 看護介護対象人数変更日（List）をそのまま返す
			return CareTargetPeriodWork.of(childCareTargetChanged,Optional.empty());
		}

		// 介護期間変更日を求める
		carePeriodChangeDate = carePeriodChangeDate(period, deadYmd);

		// 介護人数変更日リストに追加
		childCareTargetChanged = carePeriodChangeDate.childCareTargetChanged(childCareTargetChanged);

		// 介護対象期間を求める
		Optional<CareTargetPeriod> careTargetPeriod = Optional.of(carePeriodChangeDate.careTargetPeriod(period));

		// 「介護対象期間」を返す
		return CareTargetPeriodWork.of(childCareTargetChanged, careTargetPeriod);
	}

	/**
	 * 介護期間変更日を求める
	 * @param period 期間
	 * @param deadYmd 死亡年月日
	 * @return 介護期間変更日
	 */
	public CarePeriodChangeDate carePeriodChangeDate(DatePeriod period, Optional<GeneralDate> deadYmd) {

		CarePeriodChangeDate carePeriodChangeDate = new CarePeriodChangeDate();

		// 期間中に介護対象管理データ．履歴．開始日が含まれるか
		if(period.contains(history.start())) {
			// 介護期間変更日に介護開始日を設定
			carePeriodChangeDate.setCareStartYmd(Optional.of(history.start()));
		}
		// 期間中に介護対象管理データ．履歴．終了日が含まれるか
		if(period.contains(history.end())) {
			// 介護期間変更日に介護終了日を設定
			carePeriodChangeDate.setCareEndYmd(Optional.of(history.end()));
		}
		// 期間中に死亡年月日が含まれるか
		if(deadYmd.isPresent()) {
			if(period.contains(deadYmd.get())) {
				// 介護期間変更日に死亡年月日を設定
				carePeriodChangeDate.setDeadDay(deadYmd);
			}
		}
		// 「介護期間変更日」を返す
		return carePeriodChangeDate;
	 }
}
