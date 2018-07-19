package nts.uk.ctx.at.shared.dom.worktype.specialholidayframe;

import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.shared.dom.worktype.DeprecateClassification;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeName;

/**
 * 特別休暇枠
 * 
 * @author sonnh
 *
 */
@Getter
@NoArgsConstructor
public class SpecialHolidayFrame {
	
	/**
	 * 会社ID
	 */
	private String companyId;
	
	/**
	 * 特別休暇枠ID
	 */
	private int SpecialHdFrameNo;
	
	/**
	 * 枠名称
	 */
	private WorkTypeName SpecialHdFrameName;
	
	/**
	 * 特別休暇枠の廃止区分
	 */
	private DeprecateClassification deprecateSpecialHd;
	
	/**
	 * 
	 * @param companyId
	 * @param specialHdFrameNo
	 * @param specialHdFrameName
	 * @param deprecateSpecialHd
	 */
	public SpecialHolidayFrame(String companyId, int specialHdFrameNo, WorkTypeName specialHdFrameName,
			DeprecateClassification deprecateSpecialHd) {
		super();
		this.companyId = companyId;
		this.SpecialHdFrameNo = specialHdFrameNo;
		SpecialHdFrameName = specialHdFrameName;
		this.deprecateSpecialHd = deprecateSpecialHd;
	}
	
	/**
	 * 
	 * @param companyId
	 * @param specialHdFrameNo
	 * @param specialHdFrameName
	 * @param deprecateSpecialHd
	 * @return
	 */
	public static SpecialHolidayFrame createSimpleFromJavaType(String companyId, int specialHdFrameNo, String specialHdFrameName,
			int deprecateSpecialHd) {
		return new SpecialHolidayFrame(companyId, 
				specialHdFrameNo,
				new WorkTypeName(specialHdFrameName), 
				EnumAdaptor.valueOf(deprecateSpecialHd, DeprecateClassification.class));
	}

	/**
	 * Creates the from java type.
	 *
	 * @param companyId the company id
	 * @param specialHdFrameNo the special hd frame no
	 * @param specialHdFrameName the special hd frame name
	 * @param deprecateSpecialHd the deprecate special hd
	 * @return the special holiday frame
	 */
	public static SpecialHolidayFrame createFromJavaType(String companyId, int specialHdFrameNo, String specialHdFrameName,
			int deprecateSpecialHd) {
		return new SpecialHolidayFrame(companyId, specialHdFrameNo, new WorkTypeName(specialHdFrameName), EnumAdaptor.valueOf(deprecateSpecialHd, DeprecateClassification.class));
	}
}
