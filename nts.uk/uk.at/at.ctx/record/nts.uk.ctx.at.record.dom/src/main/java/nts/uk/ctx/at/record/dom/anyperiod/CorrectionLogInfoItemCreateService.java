package nts.uk.ctx.at.record.dom.anyperiod;

import nts.uk.ctx.at.shared.dom.monthlyattditem.MonthlyAttendanceItem;
import nts.uk.ctx.at.shared.dom.monthlyattditem.MonthlyAttendanceItemAtr;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ItemValue;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.security.audittrail.correction.content.DataValueAttribute;
import nts.uk.shr.com.security.audittrail.correction.content.ItemInfo;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 修正ログの項目情報を作成する
 */
public class CorrectionLogInfoItemCreateService {

    /**
     * [1] 作成する
     * @param require
     * @param companyId
     * @param targetItems 基準項目リスト
     * @param comparingItems 比較項目リスト
     * @return 修正リスト
     */
    public static List<ItemInfo> create(Require require, String companyId, List<ItemValue> targetItems, List<ItemValue> comparingItems) {
        List<ItemInfo> result = new ArrayList<>();
        List<Integer> itemIds = targetItems.stream().map(ItemValue::getItemId).collect(Collectors.toList());
        List<MonthlyAttendanceItem> attItems = require.findByAttendanceItemId(companyId, itemIds);
        attItems.forEach(attItem -> {
            ItemValue target = targetItems.stream().filter(i -> i.getItemId() == attItem.getAttendanceItemId()).findFirst().get();
            ItemValue compare = comparingItems.stream().filter(i -> i.getItemId() == attItem.getAttendanceItemId()).findFirst().get();
            if (StringUtils.compare(target.getValue(), compare.getValue()) != 0) {
                ItemInfo info = createTargetInfo(
                        attItem,
                        Optional.ofNullable(target.value()),
                        Optional.ofNullable(compare.value())
                );
                result.add(info);
            }
        });
        return result;
    }

    /**
     * [prv-1] 対象情報を作成する
     * @param attItem 勤怠項目
     * @param valueBefore 修正前の項目値
     * @param valueAfter 修正後の項目値
     * @return 項目情報
     */
    private static ItemInfo createTargetInfo(MonthlyAttendanceItem attItem, Optional<Object> valueBefore, Optional<Object> valueAfter) {
        DataValueAttribute dataAtr;
        if (attItem.getMonthlyAttendanceAtr() == MonthlyAttendanceItemAtr.TIME) {
            dataAtr = DataValueAttribute.TIME;
        } else if (attItem.getMonthlyAttendanceAtr() == MonthlyAttendanceItemAtr.NUMBER) {
            dataAtr = DataValueAttribute.COUNT;
        } else if (attItem.getMonthlyAttendanceAtr() == MonthlyAttendanceItemAtr.DAYS) {
            dataAtr = DataValueAttribute.COUNT;
        } else if (attItem.getMonthlyAttendanceAtr() == MonthlyAttendanceItemAtr.AMOUNT) {
            dataAtr = DataValueAttribute.MONEY;
        } else {
            dataAtr = DataValueAttribute.STRING;
        }
        return ItemInfo.create(
                String.valueOf(attItem.getAttendanceItemId()),
                attItem.getAttendanceName().v(),
                dataAtr,
                valueBefore.orElse(null),
                valueAfter.orElse(null)
        );
    }

    public interface Require {
        List<MonthlyAttendanceItem> findByAttendanceItemId(String companyId, List<Integer> attendanceItemIds);
    }
}
