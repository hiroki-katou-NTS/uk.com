package nts.uk.ctx.at.shared.dom.workrule.workform;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.personallaborcondition.UseAtr;

/**
 * フレックス勤務の設定
 * @author HoangNDH
 *
 */
@Data
@AllArgsConstructor
public class FlexWorkSet {
	/** 会社ID */
	private CompanyId companyId;
	/** フレックス勤務を管理する */
	private UseAtr useFlexWorkSetting;
	
	/**
	 * Create domain flex work setting
	 * @param companyId
	 * @param useDeformedLabor
	 * @return
	 */
	public static FlexWorkSet createFromJavaType(String companyId, int useFlexWorkSetting) {
		return new FlexWorkSet(new CompanyId(companyId), EnumAdaptor.valueOf(useFlexWorkSetting, UseAtr.class));
	}
	

	/**
	 * 	[1] フレックス勤務に対応する日次の勤怠項目を取得する
	 * @return
	 */
	public List<Integer> getDaiLyAttendanceId() {
		return Arrays.asList(466,470,474,493,497,501,555,556,557,635,788,832);
	}
	
	/**
	 * 	[2] フレックス勤務に対応する月次の勤怠項目を取得する	
	 * @return
	 */
	public List<Integer> getMonthlyAttendanceId() {
		return Arrays.asList(12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 191, 1356, 1825, 2065, 2137, 2138, 2139, 2140,
				2141, 2142, 2143, 2144, 2199, 2200, 2201, 2202, 2203, 2204, 2205, 2206, 2207, 2208, 2209, 2210, 2211,
				2212, 2213, 2214, 2215, 2216, 2217, 2218, 2219, 2220, 2221, 2222, 2223, 2224, 2225, 2226, 2227, 2228,
				2229, 2230, 2231, 2232, 2233, 2234, 2235, 2236, 2237, 2238, 2239, 2240, 2241, 2242, 2243, 2244);
	}
	
	/**
	 * 	[3] 利用できない日次の勤怠項目を取得する
	 * @return
	 */
	public List<Integer> getDaiLyAttendanceIdNotAvailable() {
		if(this.useFlexWorkSetting == UseAtr.NOTUSE ) {
			return this.getDaiLyAttendanceId();
		}
		return new ArrayList<>();
	}
	
	/**
	 * 	[4] 利用できない月次の勤怠項目を取得する
	 * @return
	 */
	public List<Integer> getMonthlyAttendanceIdNotAvailable() {
		if(this.useFlexWorkSetting == UseAtr.NOTUSE ) {
			return this.getMonthlyAttendanceId();
		}
		return new ArrayList<>();
	}
}
