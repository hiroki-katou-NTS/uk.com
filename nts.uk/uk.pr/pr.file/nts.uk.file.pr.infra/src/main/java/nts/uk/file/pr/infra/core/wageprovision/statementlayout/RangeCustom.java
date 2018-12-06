package nts.uk.file.pr.infra.core.wageprovision.statementlayout;

import com.aspose.cells.Cell;
import com.aspose.cells.Range;
import com.aspose.cells.Worksheet;
import com.aspose.cells.WorksheetCollection;

public class RangeCustom {
    int index;
    final int offset;
    Range range;
    Worksheet worksheet;
    WorksheetCollection worksheets;

    public RangeCustom(Range range, int offset) {
        this.offset = offset;
        this.range = range;
        this.worksheet = range.getWorksheet();
        this.worksheets = this.worksheet.getWorkbook().getWorksheets();
    }

    public RangeCustom(Range range, int offset, int index) {
        this.index = index;
        this.offset = offset;
        this.range = range;
        this.worksheet = range.getWorksheet();
        this.worksheets = this.worksheet.getWorkbook().getWorksheets();
    }

    public Cell cell(String name) {
        Cell orginCell = worksheets.getRangeByName(name).getCellOrNull(0, 0);
        return worksheet.getCells().get(orginCell.getRow() + offset, orginCell.getColumn());
    }

    public Cell cell(String name, int rowOffset, int columnOffset) {
        Cell orginCell = worksheets.getRangeByName(name).getCellOrNull(0, 0);
        return worksheet.getCells().get(orginCell.getRow() + offset + rowOffset, orginCell.getColumn() + columnOffset);
    }

    public Cell firstCell() {
        return range.getCellOrNull(0, 0);
    }
}
