/**
 * 
 */
package nts.uk.ctx.bs.person.dom.person.family;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.person.dom.person.info.currentaddress.CountryId;
import nts.uk.ctx.bs.person.dom.person.info.fullnameset.FullNameSet;

/**
 * @author danpv
 *
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Family extends AggregateRoot {

	private GeneralDate birthday;

	private GeneralDate deadDay;

	private GeneralDate entryDate;

	private GeneralDate expelledDate;

	private FamilyId familyId;

	private FullNameSet name;

	private FullNameSet nameMultiLang;

	private FullNameSet nameRomaji;

	private CountryId nationality;

	private String occupationName;

	private String personId;

	private String relationship;

	private int supportCareType;

	private String notificationName;

	private int togSepDivision;

	private int workStudent;

	public static Family createFromJavaType(Date birthday, Date deadDay, Date entryDate, Date expelledDate,
			String familyId, String name, String nameKana, String nameMulti, String nameMultiKana, String nameRomaji,
			String nameRomajiKana, String nationality, String occupationName, String personId, String relationship,
			int supportCareType, String notificationName, int togSepDivision, int workStudent) {
		return new Family(GeneralDate.legacyDate(birthday), GeneralDate.legacyDate(deadDay),
				GeneralDate.legacyDate(entryDate), GeneralDate.legacyDate(expelledDate), new FamilyId(familyId),
				new FullNameSet(name, nameKana), new FullNameSet(nameMulti, nameMultiKana),
				new FullNameSet(nameRomaji, nameRomajiKana), new CountryId(nationality), occupationName, personId,
				relationship, supportCareType, notificationName, togSepDivision, workStudent);

	}
}
