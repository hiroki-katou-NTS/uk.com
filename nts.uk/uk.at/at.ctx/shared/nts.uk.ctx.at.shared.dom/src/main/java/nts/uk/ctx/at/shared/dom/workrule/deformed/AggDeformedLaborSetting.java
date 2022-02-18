package nts.uk.ctx.at.shared.dom.workrule.deformed;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.personallaborcondition.UseAtr;

/**
 * Domain aggregate deformed labor setting
 * @author HoangNDH
 *
 */
@Data
@AllArgsConstructor
// 変形労働の集計設定
public class AggDeformedLaborSetting {
	
	/** 会社ID */
	private CompanyId companyId;
	/** 変形労働を使用する */
	private UseAtr useDeformedLabor;
	
	/**
	 * Create domain aggregate deformed labor setting
	 * @param companyId
	 * @param useDeformedLabor
	 * @return
	 */
	public static AggDeformedLaborSetting createFromJavaType(String companyId, int useDeformedLabor) {
		return new AggDeformedLaborSetting(new CompanyId(companyId), EnumAdaptor.valueOf(useDeformedLabor, UseAtr.class));
	}
	
	/**
	 * 	[1] 変形労働に対応する日次の勤怠項目を取得する
	 * @return
	 */
	public List<Integer> getDaiLyAttendanceId() {
		return Arrays.asList(551,1122);
	}
	
	/**
	 * 	[2] 変形労働に対応する月次の勤怠項目を取得する
	 * @return
	 */
	public List<Integer> getMonthlyAttendanceId() {
		return Arrays.asList(588,1351,1352,1353,1354,1355);
	}
	
	/**
	 * 	[3] 利用できない日次の勤怠項目を取得する
	 * @return
	 */
	public List<Integer> getDaiLyAttendanceIdNotAvailable() {
		if(this.useDeformedLabor == UseAtr.NOTUSE ) {
			return this.getDaiLyAttendanceId();
		}
		return new ArrayList<>();
	}
	
	/**
	 * 	[4] 利用できない月次の勤怠項目を取得する
	 * @return
	 */
	public List<Integer> getMonthlyAttendanceIdNotAvailable() {
		if(this.useDeformedLabor == UseAtr.NOTUSE ) {
			return this.getMonthlyAttendanceId();
		}
		return new ArrayList<>();
	}
}
