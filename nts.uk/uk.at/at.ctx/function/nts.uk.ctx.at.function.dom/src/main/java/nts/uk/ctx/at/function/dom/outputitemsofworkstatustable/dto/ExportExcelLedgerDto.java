package nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.function.dom.workledgeroutputitem.WorkLedgerDisplayContent;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ExportExcelLedgerDto {
    private String workPlaceCode;
    // 職場名
    private String workPlaceName;
    // 日付
    private List<WorkLedgerDisplayContent> data;
}
