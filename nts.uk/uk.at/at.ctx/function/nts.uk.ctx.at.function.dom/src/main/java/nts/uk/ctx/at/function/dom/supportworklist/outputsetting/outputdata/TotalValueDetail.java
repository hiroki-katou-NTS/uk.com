package nts.uk.ctx.at.function.dom.supportworklist.outputsetting.outputdata;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.function.dom.adapter.DailyAttendanceItemAdapterDto;
import nts.uk.ctx.at.function.dom.supportworklist.aggregationsetting.SupportWorkDetails;
import nts.uk.ctx.at.function.dom.supportworklist.outputsetting.SupportWorkOutputDataRequire;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ValueType;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 合計値詳細
 */
@AllArgsConstructor
@Getter
public class TotalValueDetail {
    /**
     * 項目値リスト
     */
    private List<ItemValue> itemValues;

    /**
     * 人数
     */
    private int peopleCount;

    /**
     * [C-1] 合計する
     * @param require
     * @param companyId 会社ID
     * @param supportWorkDetails List<応援勤務明細>
     * @return
     */
    public static TotalValueDetail create(SupportWorkOutputDataRequire require, String companyId, List<SupportWorkDetails> supportWorkDetails) {
        List<ItemValue> totalValue = sumUpSupportDetails(require, companyId, supportWorkDetails);
        int peopleCount = countPeople(supportWorkDetails);
        return new TotalValueDetail(totalValue, peopleCount);
    }

    /**
     * [prv-1] 応援明細一覧を合計する
     * @param require
     * @param companyId 会社ID
     * @param supportWorkDetails List<応援勤務明細>
     * @return List<項目値>
     */
    private static List<ItemValue> sumUpSupportDetails(SupportWorkOutputDataRequire require, String companyId, List<SupportWorkDetails> supportWorkDetails) {
        if (supportWorkDetails.isEmpty()) return new ArrayList<>();
        SupportWorkDetails first = supportWorkDetails.get(0);
        List<Integer> attendanceItemIds = first.getItemList().stream()
                .map(i -> i.getItemId()).collect(Collectors.toList());
        List<DailyAttendanceItemAdapterDto> itemValues = require.getDailyAttendanceItems(companyId, attendanceItemIds);
        List<Integer> totalItems = itemValues.stream()
                .filter(i -> i.getDailyAttendanceAtr() == 3 || i.getDailyAttendanceAtr() == 5)
                .map(i -> i.getAttendanceItemId()).collect(Collectors.toList());
        if (totalItems.isEmpty()) return new ArrayList<>();
        List<ItemValue> result = new ArrayList<>();
        List<ItemValue> details = supportWorkDetails.stream()
                .map(i -> i.getItemList())
                .flatMap(i -> i.stream()).collect(Collectors.toList());
        Map<Integer, List<ItemValue>> groupedDetails = details.stream().collect(Collectors.groupingBy(ItemValue::getItemId));
        totalItems.forEach(i -> {
            List<ItemValue> d = groupedDetails.get(i);
            ValueType valueType = d.isEmpty() ? ValueType.TIME : d.get(0).getValueType();
            int totalValue = d.stream().filter(ii -> ii.getValue() != null).map(ii -> Integer.valueOf(ii.getValue())).collect(Collectors.summingInt(Integer::intValue));
            result.add(ItemValue.builder().itemId(i).value(totalValue).valueType(valueType));
        });
        return result;
    }

    /**
     * [prv-2] 人数をカウントする
     * @param supportWorkDetails List<応援勤務明細>
     * @return int
     */
    private static int countPeople(List<SupportWorkDetails> supportWorkDetails) {
        return supportWorkDetails.stream().collect(Collectors.groupingBy(i -> Pair.of(i.getEmployeeId(), i.getDate()))).size();
    }

}
