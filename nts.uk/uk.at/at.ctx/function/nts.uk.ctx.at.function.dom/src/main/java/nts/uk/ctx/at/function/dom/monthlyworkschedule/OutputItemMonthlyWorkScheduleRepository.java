package nts.uk.ctx.at.function.dom.monthlyworkschedule;

import java.util.List;
import java.util.Optional;

/**
 * The Interface OutputItemMonthlyWorkScheduleRepository.
 */
public interface OutputItemMonthlyWorkScheduleRepository {

	/**
	 * Find by cid and code.
	 *
	 * @param companyId the company id
	 * @param code the code
	 * @return the optional
	 */
	public Optional<OutputItemMonthlyWorkSchedule> findByCidAndCode(String companyId, String code);
	
	/**
	 * Find by cid.
	 *
	 * @param companyId the company id
	 * @return the list
	 */
	public List<OutputItemMonthlyWorkSchedule> findByCid(String companyId);
	
	/**
	 * Adds the.
	 *
	 * @param domain the domain
	 */
	void add(OutputItemMonthlyWorkSchedule domain);
	
	/**
	 * Update.
	 *
	 * @param domain the domain
	 */
	void update(OutputItemMonthlyWorkSchedule domain);
	
	/**
	 * Delete.
	 *
	 * @param domain the domain
	 */
	void delete(OutputItemMonthlyWorkSchedule domain);
	
	/**
	 * Delete by cid and code.
	 *
	 * @param companyId the company id
	 * @param code the code
	 */
	void deleteByCidAndCode(String companyId, String code);
	
	/**
	 * ドメインモデル「月別勤務表の出力項目」をすべて取得する (Acquire all domain model "output items of monthly work schedule")
	 * @param itemSelectionEnum 定型選択の場合 or 自由設定の場合
	 * @param companyId 会社ID 
	 * @param employeeId 社員ID
	 * @return the list
	 */
	public List<OutputItemMonthlyWorkSchedule> findBySelectionAndCidAndSid(ItemSelectionEnum itemSelectionEnum
			, String companyId
			, Optional<String> employeeId); 
	
	/**
	 * ドメインモデル「月別勤務表の出力項目」を削除する (Xóa domain model "Output item of monthly work schedule")
	 * @param itemSelectionEnum 定型選択の場合 or 自由設定の場合
	 * @param companyId 会社ID 
	 * @param code コード
	 * @param employeeId 社員ID
	 */
	void deleteBySelectionAndCidAndSidAndCode (ItemSelectionEnum itemSelectionEnum
					,String companyId, String code, Optional<String> employeeId); 
	
	/**
	   * ドメインモデル「月別勤務表の出力項目」を取得する (Acquire domain model "Output items of monthly work schedule")
	 * @param itemSelectionEnum 定型選択の場合 or 自由設定の場合
	 * @param companyId 会社ID 
	 * @param code コード
	 * @param employeeId 社員ID
	 * @return
	 */
	public Optional<OutputItemMonthlyWorkSchedule> findBySelectionAndCidAndSidAndCode (ItemSelectionEnum itemSelectionEnum
			,String companyId, String code, Optional<String> employeeId); 

	/**
	 *  パラメータ.出力項目一覧の件数をチェックする
	 * @param itemSelectionEnum 定型選択の場合 or 自由設定の場合
	 * @param code コード
	 * * @param name 名称
	 * @param employeeId 社員ID
	 * @return the list
	 */
	public List<OutputItemMonthlyWorkSchedule> findBySelectionAndSidAndNameAndCode (ItemSelectionEnum itemSelectionEnum
			,String name, String code, Optional<String> employeeId);
}
