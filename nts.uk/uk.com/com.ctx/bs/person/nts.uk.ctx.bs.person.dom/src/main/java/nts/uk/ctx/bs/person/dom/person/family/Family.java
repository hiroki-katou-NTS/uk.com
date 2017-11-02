/**
 * 
 */
package nts.uk.ctx.bs.person.dom.person.family;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.person.dom.person.info.CountryId;

/**
 * @author danpv
 *
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
// 家族
public class Family extends AggregateRoot {
	// 生年月日
	private GeneralDate birthday;
	// 死亡年月日
	private GeneralDate deadDay;
	// 入籍年月日
	private GeneralDate entryDate;
	// 除籍年月日
	private GeneralDate expelledDate;
	// 家族ID
	private String familyId;
	// 氏名
	private NameSetType nameType;
	// 氏名他言語
	private NameSetType nameMultiLangType;
	// 氏名ローマ字
	private NameSetType nameRomajiType;
	// 国籍
	private CountryId nationalityId;
	// 職業
	private String occupationName;
	// 個人ID
	private String personId;
	// 続柄
	private String relationshipId;
	// 支援介護区分
	private SupportCareType supportCareType;
	// 同居別居区分
	private TogSepDivisionType togSepDivisionType;
	// 勤労学生
	private WorkStudentType workStudentType;

	public static Family createFromJavaType(GeneralDate birthday, GeneralDate deadDay, GeneralDate entryDate,
			GeneralDate expelledDate, String familyId, int nameType, int nameMultiLangType, int nameRomajiType,
			String nationalityId, String occupationName, String personId, String relationship, int supportCareType,
			int togSepDivisionType, int workStudentType) {
		return new Family(birthday, deadDay, entryDate, expelledDate, familyId,
				EnumAdaptor.valueOf(nameType, NameSetType.class),
				EnumAdaptor.valueOf(nameMultiLangType, NameSetType.class),
				EnumAdaptor.valueOf(nameRomajiType, NameSetType.class), new CountryId(nationalityId), occupationName,
				personId, relationship, EnumAdaptor.valueOf(supportCareType, SupportCareType.class),
				EnumAdaptor.valueOf(togSepDivisionType, TogSepDivisionType.class),
				EnumAdaptor.valueOf(workStudentType, WorkStudentType.class));

	}
}
