package nts.uk.ctx.at.function.dom.adapter.outputitemsofworkstatustable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ItemValue;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Getter
public class AnyPeriodRecordValuesExportDto {
    private String anyAggrFrameCode;

    /** 項目値リスト */
    private List<ItemValue> itemValues = new ArrayList<>();
}
