package nts.uk.ctx.sys.auth.dom.wkpmanager;

import java.util.List;

import lombok.val;
import nts.arc.error.BusinessException;
import nts.arc.task.tran.AtomTask;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.sys.auth.dom.adapter.employee.EmpEnrollPeriodImport;

/**
 * 職場管理者を登録する
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.システム.権限管理.職場管理者.職場管理者を登録する
 * @author lan_lt
 *
 */
public class RegisterWorkplaceManagerService {
	
	/**
	 * 追加する
	 * add
	 * @param require Require
	 * @param workplaceId 職場ID
	 * @param sid 社員ID
	 * @param historyPeriod 履歴期間
	 * @return
	 */
	public static AtomTask add(Require require, String workplaceId, String sid, DatePeriod historyPeriod) {
		
		val wpkManageRegistered = require.getWorkplaceMangagerByWorkplaceIdAndSid(workplaceId, sid);
		
		checkError(require, sid, historyPeriod, wpkManageRegistered);
		
		val workPlaceManager = WorkplaceManager.createNew(workplaceId, sid, historyPeriod);
		
		return AtomTask.of(() -> require.insert(workPlaceManager));
		
	}
	
	/**
	 * 期間を変更する
	 * changePeriod
	 * @param require Require
	 * @param workPlaceManager 職場管理者
	 * @return
	 */
	public static AtomTask changePeriod(Require require, WorkplaceManager workPlaceManager) {
		
		val wpkManageRegistered = require.getWorkplaceMangagerByWorkplaceIdAndSid(workPlaceManager.getWorkplaceId()
				,	workPlaceManager.getEmployeeId());
		
		checkError(require, workPlaceManager.getEmployeeId(), workPlaceManager.getHistoryPeriod(), wpkManageRegistered);
		
		return AtomTask.of(() -> require.update(workPlaceManager));
	}
	
	/**
	 * エラーをチェック
	 * check error
	 * @param require
	 * @param sid
	 * @param historyDatePeriod
	 * @param wpkManageRegistered
	 */
	private static void checkError(Require require, String sid, DatePeriod historyDatePeriod,
			List<WorkplaceManager> wpkManageRegistered) {
		if (wpkManageRegistered.stream()
				.anyMatch(c -> c.getHistoryPeriod().compare(historyDatePeriod).isDuplicated())) {
			throw new BusinessException("Msg_619");
		}
		
		List<EmpEnrollPeriodImport> empComHistories = require.getHistoryCompanyBySidAndDatePeriod(sid, historyDatePeriod);
		
		if(empComHistories.stream().allMatch(empHist -> !empHist.getDatePeriod().contains(historyDatePeriod))) {
			throw new BusinessException("Msg_2199");
		}
	
	}
	
	public static interface Require{
		/**
		 * 社員IDと職場IDを指定して職場管理者を取得する( 職場ID, 社員ID)
		 * get workplace manager by workplaceId and employeeId
		 * @param workplaceId 職場ID
		 * @param sid 社員ID
		 * @return
		 */
		List<WorkplaceManager> getWorkplaceMangagerByWorkplaceIdAndSid(String workplaceId, String sid);
		
		/**
		 * 職場管理者を追加する
		 * add workplace manager
		 * @param workplaceManager 職場管理者
		 * @return 
		 */
		void insert(WorkplaceManager workplaceManager);
		
		/**
		 * 職場管理者を登録
		 * update workplace manager
		 * @param workplaceManager 職場管理者
		 */
		void update(WorkplaceManager workplaceManager);
		
		/**
		 * 社員の所属会社履歴を取得する( 社員ID, 期間 )
		 * get history company by sid and datePeriod
		 * @param sid 社員ID
		 * @param datePeriod 期間
		 * @return
		 */
		List<EmpEnrollPeriodImport> getHistoryCompanyBySidAndDatePeriod(String sid, DatePeriod datePeriod);
	}
	
}
