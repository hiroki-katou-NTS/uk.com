package nts.uk.ctx.exio.dom.input.revise;

import java.util.ArrayList;
import java.util.List;

import lombok.val;
import nts.uk.ctx.exio.dom.input.ExecutionContext;
import nts.uk.ctx.exio.dom.input.csvimport.CsvRecord;
import nts.uk.ctx.exio.dom.input.setting.mapping.ImportItemMapping;

/**
 * 1行分のCSV受入データを編集する
 */
public class ReviseCsvRecord {
	
	public List<RevisedValueResult> revise(Require require, ExecutionContext context, 
			CsvRecord csvRecord, 
			List<ImportItemMapping> mapping) {
		
		val results = new ArrayList<RevisedValueResult>();
		for(int i = 1; i <= mapping.size(); i++) {
			val itemNo = mapping.get(i).getImportItemNumber();
			val csvValue = csvRecord.getRawItems().get(mapping.get(i).getCsvLineNumber());
			// 編集
			results.add(ReviseValue.revise(require, context, itemNo, csvValue));
		}
		return results;
	}
	
	public interface Require extends ReviseValue.Require{
	}
}
