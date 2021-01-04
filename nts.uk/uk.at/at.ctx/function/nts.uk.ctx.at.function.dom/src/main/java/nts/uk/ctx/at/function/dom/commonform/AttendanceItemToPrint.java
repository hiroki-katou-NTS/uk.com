package nts.uk.ctx.at.function.dom.commonform;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.gul.collection.CollectionUtil;
import org.eclipse.persistence.internal.xr.ValueObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 印刷する勤怠項目
 *
 * @author khai.dh
 */
@Getter
@AllArgsConstructor
public class AttendanceItemToPrint extends ValueObject {

	// 勤怠項目ID
	private int attendanceId;

	// 順位
	private int ranking;

}
