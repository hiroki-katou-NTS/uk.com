package nts.uk.ctx.at.schedule.dom.schedule.workschedule;

import java.util.List;
import java.util.Optional;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.adapter.employee.AffCompanyHistSharedImport;
import nts.uk.ctx.at.shared.dom.adapter.employee.EmpEmployeeAdapter;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.EmploymentCode;
import nts.uk.ctx.at.shared.dom.workingcondition.ManageAtr;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingCondition;



/**
 * 社員の予定管理状態	
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.勤務予定.勤務予定.勤務予定
 * @author HieuLt
 *
 */
@Getter
@RequiredArgsConstructor
public class ScheManaStatuTempo {
 
	/**	社員ID **/ 
	private final String employeeID;
	
	/** 年月日 **/ 
	private final GeneralDate date;
	
	/**	予定管理状態 **/
	private final ScheManaStatus scheManaStatus;
	
	/** 休業枠NO**/
	private final Optional<TempAbsenceFrameNo> optTempAbsenceFrameNo;
	/** 雇用コード **/
	private final Optional<EmploymentCode> optEmploymentCd;
	
	
	/**
	 * [C-1] 作成する	
	 * @param require
	 * @param employeeID
	 * @param date
	 * @return
	 */
	public ScheManaStatuTempo create(Require require, String employeeID, GeneralDate date) {
		return null;
	}
	/**
	 * [S-1] 在籍中か		
	 * @param require
	 * @param employeeID
	 * @param date
	 * @return
	 */
	private static boolean enrolled(Require require, String employeeID, GeneralDate date)
			 {
		return false;
	}
	/**
	 * [S-2] 雇用コードを取得する			
	 * @param require
	 * @param employeeID
	 * @param date
	 * @return
	 */
	private static Optional<EmploymentCode> getEmplomentCd (Require require, String employeeID, GeneralDate date)
	 {
		return Optional.empty();
	 }
	
	/**
	 * [S-3] 予定管理区分を取得する	
	 * @param require
	 * @param employeeID
	 * @param date
	 * @return
	 */
	private static Optional<ManageAtr> getManageAtr(Require require, String employeeID, GeneralDate date){
		return Optional.empty();
	}
	/**
	 * [S-4] 休職中か		
	 * @param require
	 * @param employeeID
	 * @param date
	 * @return 
	 */
	private static boolean onLeave(Require require, String employeeID, GeneralDate date){
		return false;
	}
	/**
	 * [S-5] 休業枠NOを取得する	
	 * @param require
	 * @param employeeID
	 * @param date
	 * @return Optional<休職休業枠NO>
	 */
	private static Optional<TempAbsenceFrameNo> getTempAbsenceFrameNo(Require require, String employeeID, GeneralDate date){
		return Optional.empty();
	}
	public static interface Require extends EmpEmployeeAdapter  {
		
		//@Inject
		//private WorkingConditionRepository repo;
		
		/**
		 * 	[R-1] 在籍期間を取得する( 社員ID, 年月日 ) : Optional
		 * 	社員の所属会社履歴Adapter.期間を指定して在籍期間を取得する( list: 社員ID, 期間: 年月日 )		
		 */ 
		List<AffCompanyHistSharedImport> getAffCompanyHistByEmployee(List<String> sids, DatePeriod datePeriod);
		
		/** 																																					
		 *[R-2] 労働条件履歴を取得する	
		 * 	労働条件Repository.社員を指定して年月日時点の履歴項目を取得する( 会社ID, 社員ID, 年月日 )
		 */
			
		//Optional<WorkingCondition>   repo.getBy getBySidAndStandardDate(String companyId, String employeeId, GeneralDate baseDate);
		Optional<WorkingCondition> getBySidAndStandardDate(String companyId, String employeeId, GeneralDate baseDate);
		
		/**
		 *  [R-3] 休職期間を取得する( 社員ID, 年月日 ) : Optional	
		 * 	社員の休職履歴Adapter.期間を指定して休職期間を取得する( list: 社員ID, 期間: 年月日 )
		 * GetAffCompanyHistByEmployee			
		 */

		
	}
	
}
