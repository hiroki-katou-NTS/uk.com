package nts.uk.pr.file.infra.wageledger;

import java.util.ArrayList;
import java.util.List;

import com.aspose.cells.Cell;
import com.aspose.cells.FindOptions;
import com.aspose.cells.LookInType;
import com.aspose.cells.Worksheet;

import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportGenerator;

public class WageLedgerBaseGenerator extends AsposeCellsReportGenerator{
	
	/**
	 * Safe sub list.
	 *
	 * @param <T> the generic type
	 * @param list the list
	 * @param fromIndex the from index
	 * @param toIndex the to index
	 * @return the list
	 */
	protected <T> List<T> safeSubList(List<T> list, int fromIndex, int toIndex) {
		int size = list.size();
		if (fromIndex >= size || toIndex <= 0 || fromIndex >= toIndex) {
			return new ArrayList<>();
		}

		fromIndex = Math.max(0, fromIndex);
		toIndex = Math.min(size, toIndex);
		return list.subList(fromIndex, toIndex);
	}
	
	/**
	 * Find cell with content.
	 *
	 * @param ws the ws
	 * @param content the content
	 * @return the cell
	 */
	protected Cell findCellWithContent(Worksheet ws, String content) {
		// Cell find option.
		FindOptions findOptions = new FindOptions();
		findOptions.setLookInType(LookInType.VALUES);
		findOptions.setCaseSensitive(false);
		
		Cell cell = ws.getCells().find(content, null, findOptions);
		
		return cell;
	}
}
