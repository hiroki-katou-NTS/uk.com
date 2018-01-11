package nts.uk.ctx.at.shared.dom.specialholiday;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.layer.dom.DomainObject;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Classfication extends DomainObject{
	/** 会社ID */
	private String companyId;

	/** 特別休暇コード */
	private SpecialHolidayCode specialHolidayCode;
	
	/** 勤務種類コード */
	private String classficationCode;
	
	public static Classfication createFromJavaType(String companyId, String specialHolidayCode, String classficationCode){
		return new Classfication(companyId, new SpecialHolidayCode(specialHolidayCode), classficationCode);
	}
}
