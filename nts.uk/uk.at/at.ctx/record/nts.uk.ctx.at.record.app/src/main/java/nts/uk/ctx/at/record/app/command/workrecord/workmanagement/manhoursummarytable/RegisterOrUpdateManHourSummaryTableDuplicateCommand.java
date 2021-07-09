package nts.uk.ctx.at.record.app.command.workrecord.workmanagement.manhoursummarytable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.val;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.record.dom.workrecord.workmanagement.manhoursummarytable.DetailFormatSetting;
import nts.uk.ctx.at.record.dom.workrecord.workmanagement.manhoursummarytable.DisplayFormat;
import nts.uk.ctx.at.record.dom.workrecord.workmanagement.manhoursummarytable.ManHourSummaryTableCode;
import nts.uk.ctx.at.record.dom.workrecord.workmanagement.manhoursummarytable.ManHourSummaryTableFormat;
import nts.uk.ctx.at.record.dom.workrecord.workmanagement.manhoursummarytable.ManHourSummaryTableName;
import nts.uk.ctx.at.record.dom.workrecord.workmanagement.manhoursummarytable.SummaryItem;
import nts.uk.ctx.at.record.dom.workrecord.workmanagement.manhoursummarytable.SummaryItemType;
import nts.uk.ctx.at.record.dom.workrecord.workmanagement.manhoursummarytable.TotalUnit;
import nts.uk.shr.com.enumcommon.NotUseAtr;

import java.util.Collections;
import java.util.stream.Collectors;

@AllArgsConstructor
@Getter
@NoArgsConstructor
public class RegisterOrUpdateManHourSummaryTableDuplicateCommand {
    /**
     * 工数集計表コード
     */
    private String sourcecode;

    /**
     * 工数集計表コード
     */
    private String destinationCode;
    /**
     * 工数集計表名称
     */
    private String name;
    /**
     * 合計単位
     */
    private boolean isCopy;

    public ManHourSummaryTableFormat toDomain(ManHourSummaryTableFormat copy) {
        if (this.isCopy()) {
            val manHourSummaryTableFormat = new ManHourSummaryTableFormat(
                    new ManHourSummaryTableCode(this.getSourcecode()),
                    new ManHourSummaryTableName(this.getName()),
                    new DetailFormatSetting(
                            DisplayFormat.of(copy.getDetailFormatSetting().getDisplayFormat().value),
                            TotalUnit.of(copy.getDetailFormatSetting().getTotalUnit().value),
                            EnumAdaptor.valueOf(copy.getDetailFormatSetting().getDisplayVerticalHorizontalTotal().value, NotUseAtr.class),
                            copy.getDetailFormatSetting().getSummaryItemList().stream().map(item -> new SummaryItem(item.getHierarchicalOrder(),
                                    SummaryItemType.of(item.getSummaryItemType().value)))
                                    .collect(Collectors.toList())
                    )
            );
            return manHourSummaryTableFormat;
        } else {
            return new ManHourSummaryTableFormat(
                    new ManHourSummaryTableCode(this.getSourcecode()),
                    new ManHourSummaryTableName(this.getName()),
                    new DetailFormatSetting(
                            DisplayFormat.of(0),
                            TotalUnit.of(0),
                            EnumAdaptor.valueOf(0, NotUseAtr.class),
                            Collections.emptyList()
                    )
            );
        }
    }
}