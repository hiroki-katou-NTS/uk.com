package nts.uk.ctx.at.record.pub.anyperiod;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ItemValue;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AnyPeriodRecordValuesExport {

    private String anyAggrFrameCode;

    /** 項目値リスト */
    private List<ItemValue> itemValues = new ArrayList<>();

    public static AnyPeriodRecordValuesExport of(String frameCode, List<ItemValue> itemValues){
        AnyPeriodRecordValuesExport domain = new AnyPeriodRecordValuesExport(frameCode, itemValues);
        return domain;
    }
}
