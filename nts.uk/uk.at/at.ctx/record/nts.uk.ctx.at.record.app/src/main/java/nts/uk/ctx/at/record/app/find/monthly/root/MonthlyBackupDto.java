package nts.uk.ctx.at.record.app.find.monthly.root;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.AttendanceItemUtil.AttendanceItemType;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemRoot;

@Data
@NoArgsConstructor
@AllArgsConstructor
/** 月別実績バックアップ */
@AttendanceItemRoot(rootName = "月別実績バックアップ", itemType = AttendanceItemType.MONTHLY_ITEM)
public class MonthlyBackupDto {

	/** ログ作成日: 日時 */
	private GeneralDateTime logCreateDate;
}
