package nts.uk.ctx.at.record.dom.stamp.card.stampcard;

import java.util.List;
import java.util.Optional;

import nts.arc.task.tran.AtomTask;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.adapter.company.CompanyImport622;
import nts.uk.ctx.at.record.dom.stamp.card.stamcardedit.StampCardEditing;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.Stamp;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.StampMeans;

/**
 * @author sonnlb
 * 
 *         打刻カード番号を自動作成する
 *         UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務実績.勤務実績.打刻管理.打刻カード.打刻カード番号を自動作成する
 */
public class AutoCreateStampCardNumberService {

	/**
	 * 	[1] 作成する
	 * @param 社員ID
	 * employeeID
	 * @param 打刻手段
	 * stampMeans
	 * @return 
	 * @return 打刻カード作成結果
	 * 	Optional<打刻カード作成結果>
	 */
	
	public Optional<StampCardCreateResult> create(Require require, String employeeID, StampMeans stampMeans) {
		
		// if not 打刻手段.打刻カードを自動作成するか()
		if (!stampMeans.checkAutoCreateStamp()) {
			return Optional.empty();
		}
		
		// $打刻カード = [prv-1] 打刻カードを作成する(require, 社員ID)
		Optional<StampCard> stampCard = createStampCard(require, employeeID);
		
		//	if $打刻カード.isEmpty
		if(!stampCard.isPresent()){
			return Optional.empty();
		}
		
		// $AtomTask = AtomTask: require.打刻カードを追加する($打刻カード)
		
		Optional<AtomTask> atomTask = Optional.of(AtomTask.of(() -> {
			require.add(stampCard.get());
		}));
		
		return Optional.ofNullable(new StampCardCreateResult(stampCard.get().getStampNumber().v(), atomTask.get()));
	}
	
	/**
	 * [prv-1] 打刻カードを作成する
	 */
	private Optional<StampCard> createStampCard(Require require, String employeeID) {
		//	$社員データ管理情報 = require.社員情報を取得する(社員ID)	
		//EmployeeDataMngInfoImport empInfo =  
		
		return null;
	}

	/**
	 * [prv-2] 打刻カード番号を作成する
	 */
	private void createStampCardNumber() {

	}
	
	public static interface Require {
		// [R-1] 社員情報を取得する
		//List<EmployeeDataMngInfo> findBySidNotDel(List<String> sid);
		// [R-2] 会社情報を取得する
		Optional<CompanyImport622> getCompanyNotAbolitionByCid(String cid);
		// [R-3] 打刻カード編集を取得する
		Optional<StampCardEditing> get(String companyId);
		// [R-4] 打刻カードを取得する
		Optional<Stamp> get(String contractCode, String stampNumber);
		// [R-5] 打刻カードを追加する
		void add(StampCard domain);
	}
}
