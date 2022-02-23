package nts.uk.ctx.at.record.dom.anyperiod;

import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.adapter.attendanceitemname.AttItemName;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.adapter.attendanceitemname.TypeOfItemImport;
import nts.uk.shr.com.security.audittrail.correction.content.CorrectionAttr;
import nts.uk.shr.com.security.audittrail.correction.content.DataValueAttribute;
import nts.uk.shr.com.security.audittrail.correction.content.ItemInfo;
import org.apache.commons.lang3.StringUtils;

import javax.ejb.Stateless;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 修正ログの項目情報を作成する
 */
@Stateless
public class CorrectionLogInfoItemCreateService {

    /**
     * [1] 作成する
     * @param require
     * @param type 勤怠項目の種類
     * @param targetItems 基準項目リスト
     * @param comparingItems 比較項目リスト
     * @param correctionAttr 修正区分
     * @return 修正リスト
     */
    public List<ItemInfo> create(Require require, TypeOfItemImport type, List<ItemValue> targetItems, List<ItemValue> comparingItems, CorrectionAttr correctionAttr) {
        List<ItemInfo> result = new ArrayList<>();
        List<Integer> itemIds = targetItems.stream().map(ItemValue::getItemId).collect(Collectors.toList());
        List<AttItemName> attItemNames = require.getNameOfAttendanceItem(itemIds, TypeOfItemImport.AnyPeriod);
        attItemNames.forEach(attItem -> {
            ItemValue target = targetItems.stream().filter(i -> i.getItemId() == attItem.getAttendanceItemId()).findFirst().get();
            ItemValue compare = comparingItems.stream().filter(i -> i.getItemId() == attItem.getAttendanceItemId()).findFirst().get();
            if (StringUtils.compare(target.getValue(), compare.getValue()) != 0) {
                ItemInfo info = this.createTargetInfo(
                        correctionAttr,
                        attItem,
                        Optional.ofNullable(target.getValue()),
                        Optional.ofNullable(compare.getValue())
                );
                result.add(info);
            }
        });
        return result;
    }

    /**
     * [prv-1] 対象情報を作成する
     * @param correctionAttr 修正区分
     * @param attItemName 勤怠項目
     * @param valueBefore 修正前の項目値
     * @param valueAfter 修正後の項目値
     * @return 項目情報
     */
    private ItemInfo createTargetInfo(CorrectionAttr correctionAttr, AttItemName attItemName, Optional<String> valueBefore, Optional<String> valueAfter) {
        return ItemInfo.create(
                String.valueOf(attItemName.getAttendanceItemId()),
                attItemName.getAttendanceItemName(),
                DataValueAttribute.STRING,
                Integer.valueOf(1).equals(attItemName.getAttendanceAtr()) && valueBefore.isPresent() ? convertTime_Short_HM(Integer.parseInt(valueBefore.get())) : valueBefore.orElse(null),
                Integer.valueOf(1).equals(attItemName.getAttendanceAtr()) && valueAfter.isPresent() ? convertTime_Short_HM(Integer.parseInt(valueAfter.get())) : valueAfter.orElse(null)
        );
    }

    private String convertTime_Short_HM(int time) {
        return (time / 60 + ":" + (time % 60 < 10 ? "0" + time % 60 : time % 60));
    }

    public interface Require {
        List<AttItemName> getNameOfAttendanceItem(List<Integer> attendanceItemIds, TypeOfItemImport type);
    }
}
