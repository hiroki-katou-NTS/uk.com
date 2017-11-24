/**
 * 
 */
package nts.uk.ctx.bs.person.dom.person.family;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.person.dom.person.info.CountryId;

/**
 * @author danpv
 *
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
// 家族
public class FamilyMember extends AggregateRoot {
	// 生年月日
	private GeneralDate birthday;
	// 死亡年月日
	private GeneralDate deadDay;
	// 入籍年月日
	private GeneralDate entryDate;
	// 除籍年月日
	private GeneralDate expelledDate;
	// 家族ID
	private String familyMemberId;
	// 氏名
	private FullName fullName;
	// 氏名カナ
	private FullNameKana fullNameKana;
	// 氏名他言語
	private NameMultiLangFull nameMultiLangFull;
	// 氏名他言語カナ
	private NameMultiLangFullKana nameMultiLangFullKana;
	// 氏名ローマ字
	private NameRomajiFull nameRomajiFull;
	// 氏名ローマ字カナ
	private NameRomajiFullKana nameRomajiFullKana;
	// 国籍
	private CountryId nationalityId;
	// 職業
	private OccupationName occupationName;
	// 個人ID
	private String personId;
	// 続柄
	private RelationShip relationship;
	// 支援介護区分
	private SupportCareType supportCareType;

	private TokodekeName tokodekeName;

	// 同居別居区分
	private TogSepDivisionType togSepDivisionType;
	// 勤労学生
	private WorkStudentType workStudentType;

	public static FamilyMember createFromJavaType(GeneralDate birthday, GeneralDate deadDay, GeneralDate entryDate,
			GeneralDate expelledDate, String familyId, String fullName, String fullNameKana, String nameMultiLangFull,
			String nameMultiLangFullKana, String nameRomajiFull, String nameRomajiFullKana, String nationalityId,
			String occupationName, String personId, String relationship, int supportCareType, String tokodekeName,
			int togSepDivisionType, int workStudentType) {
		return new FamilyMember(birthday, deadDay, entryDate, expelledDate, familyId, new FullName(fullName),
				new FullNameKana(fullNameKana), new NameMultiLangFull(nameMultiLangFull),
				new NameMultiLangFullKana(nameMultiLangFullKana), new NameRomajiFull(nameRomajiFull),
				new NameRomajiFullKana(nameRomajiFullKana), new CountryId(nationalityId),
				new OccupationName(occupationName), personId, new RelationShip(relationship),
				EnumAdaptor.valueOf(supportCareType, SupportCareType.class), new TokodekeName(tokodekeName),
				EnumAdaptor.valueOf(togSepDivisionType, TogSepDivisionType.class),
				EnumAdaptor.valueOf(workStudentType, WorkStudentType.class));

	}
}
