package nts.uk.ctx.exio.dom.input.canonicalize.methods;

import java.util.List;
import java.util.Optional;

import nts.uk.ctx.exio.dom.input.ExecutionContext;
import nts.uk.ctx.exio.dom.input.canonicalize.existing.AnyRecordToChange;
import nts.uk.ctx.exio.dom.input.canonicalize.existing.AnyRecordToDelete;
import nts.uk.ctx.exio.dom.input.canonicalize.methods.employee.EmployeeCodeCanonicalization;
import nts.uk.ctx.exio.dom.input.meta.ImportingDataMeta;
import nts.uk.ctx.exio.dom.input.setting.assembly.RevisedDataRecord;

/**
 * 正準化の方法
 */
public interface CanonicalizationMethodRequire extends
		ImportingDataMeta.Require,
		EmployeeCodeCanonicalization.Require {

	
	/** 受け入れた編集済みデータの行数を返す */
	int getMaxRowNumberOfRevisedData(ExecutionContext context);
	
	/** 受け入れた編集済みデータ全てから特定の項目Noの文字列値を返す */
	List<String> getStringsOfRevisedData(ExecutionContext context, int itemNo);

	/** 受け入れた編集済みデータから特定のCSV行番号のものを返す */
	Optional<RevisedDataRecord> getRevisedDataRecordByRowNo(ExecutionContext context, int rowNo);
	
	/** 受け入れた編集済みデータから特定の項目Noの値が条件に合致するものを返す */
	List<RevisedDataRecord> getRevisedDataRecordWhere(ExecutionContext context, int itemNoCondition, String conditionString);
	
	void save(ExecutionContext context, AnyRecordToChange recordToChange);
	
	void save(ExecutionContext context, AnyRecordToDelete recordToDelete);
}
