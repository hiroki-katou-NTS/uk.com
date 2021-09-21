package nts.uk.ctx.exio.dom.input.setting.assembly;

import java.util.List;
import java.util.Optional;

import nts.uk.ctx.exio.dom.input.ExecutionContext;
import nts.uk.ctx.exio.dom.input.workspace.ExternalImportWorkspaceRepository.Require;

public interface RevisedDataRecordRepository {

	void save(Require require, ExecutionContext context, RevisedDataRecord record);
	
	/**
	 * 受け入れた編集済みデータの内、最も大きいCSV行番号を返す（１行ずつループしたいときとか）
	 * @param require
	 * @param context
	 * @return
	 */
	int getMaxRowNumber(Require require, ExecutionContext context);
	
	/**
	 * 指定した項目NOの文字列値を全て返す（社員コードのリストを入手したいときとか）
	 * @param require
	 * @param context
	 * @param itemNo
	 * @return
	 */
	List<String> getStrings(Require require, ExecutionContext context, int itemNo);
	
	Optional<RevisedDataRecord> findByRowNo(Require require, ExecutionContext context, int rowNo);
	
	/**
	 * 指定した項目NOの文字列値を条件として取得する
	 * @param require
	 * @param context
	 * @param criteriaItemNo
	 * @param criteriaValue
	 * @return
	 */
	List<RevisedDataRecord> findByCriteria(
			Require require, ExecutionContext context, int criteriaItemNo, String criteriaValue);
}
