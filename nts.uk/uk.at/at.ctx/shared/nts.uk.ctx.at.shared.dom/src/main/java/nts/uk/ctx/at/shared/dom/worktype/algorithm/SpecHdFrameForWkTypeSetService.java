package nts.uk.ctx.at.shared.dom.worktype.algorithm;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.worktype.DailyWork;
import nts.uk.ctx.at.shared.dom.worktype.WorkAtr;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeClassification;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeSet;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeUnit;
@Stateless
public class SpecHdFrameForWkTypeSetService {
	@Inject
	private WorkTypeRepository wkTypeRepo;
	/**
	 * 指定する勤務種類の特休枠を取得する
	 * @param companyId - 会社ID
	 * @param workTypeCD - 勤務種類コード
	 * @return 特休枠NO
	 */
	public Optional<Integer> getSpecHdFrameForWkType(String companyID, String workTypeCD){
		//ドメインモデル「勤務種類」を取得する
		Optional<WorkType> wkTypeOpt = wkTypeRepo.findByPK(companyID, workTypeCD);
		if(!wkTypeOpt.isPresent()){//0件
			//終了状態：取得失敗
			return Optional.empty();
		}
		//ドメインモデル「特別休暇の設定」．集計枠を取得する
		WorkType workType = wkTypeOpt.get();
		List<WorkTypeSet> lstWkTset = workType.getWorkTypeSetList();
		Integer frameNo = null;
		//1日⇒午前⇒午後の優先順位で、集計枠があれば、その集計枠を取得する
		//1日
		frameNo = this.findByWorkAtr(lstWkTset, WorkAtr.OneDay);
		if(frameNo != null){
			return Optional.of(frameNo);
		}
		//午前
		frameNo = this.findByWorkAtr(lstWkTset, WorkAtr.Monring);
		if(frameNo != null){
			return Optional.of(frameNo);
		}
		//午後
		frameNo = this.findByWorkAtr(lstWkTset, WorkAtr.Afternoon);
		if(frameNo != null){
			return Optional.of(frameNo);
		}
		return Optional.empty();
	};
	/**
	 * fin frame No by workAtr
	 * @param lstWkTset
	 * @param workAtr
	 * @return
	 */
	private Integer findByWorkAtr(List<WorkTypeSet> lstWkTset, WorkAtr workAtr){
		for (WorkTypeSet workTypeSet : lstWkTset) {
			if(workTypeSet.getWorkAtr().equals(workAtr)){
				return workTypeSet.getSumSpHodidayNo();
			}
		}
		return null;
	}
	/**
	 * @author hoatt
	 * 1日休日の判定
	 * @return
	 */
	public boolean jubgeHdOneDay(String companyId, String workTypeCD){
		//ドメイン「勤務種類」を取得する
		Optional<WorkType> wkTypeOpt = wkTypeRepo.findByPK(companyId, workTypeCD);
		if(!wkTypeOpt.isPresent()){
			return false;
		}
		//勤務区分チェック
		//1日の勤務
		DailyWork dailyWork = wkTypeOpt.get().getDailyWork();
		if(!dailyWork.getWorkTypeUnit().equals(WorkTypeUnit.OneDay)){//午前と午後
			return false;
		}
		//1日をチェックする
		if(dailyWork.getOneDay().equals(WorkTypeClassification.Holiday)){//1日 == 「勤務種類の分類」．休日
			return true;
		}
		return false;
	}
}
