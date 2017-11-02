package nts.uk.ctx.bs.employee.app.find.person.item;

import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.app.find.person.category.CtgItemFixDto;

@Getter
public class ItemFamilyDto extends CtgItemFixDto{
	private GeneralDate birthday;
	private GeneralDate deadDay;
	private GeneralDate entryDate;
	private GeneralDate expelledDate;
	private String familyId;
	private String name;
	private String nameKana;
	private String nameMulti;
	private String nameMultiKana;
	private String nameRomaji;
	private String nameRomajiKana;
	private String nationality;
	private String occupationName;
	private String personId;
	private String relationship;
	private int supportCareType;
	private String notificationName;
	private int togSepDivision;
	private int workStudent;
	private ItemFamilyDto(GeneralDate birthday, GeneralDate deadDay, GeneralDate entryDate, GeneralDate expelledDate,
			String familyId, String name, String nameKana, String nameMulti, String nameMultiKana, String nameRomaji,
			String nameRomajiKana, String nationality, String occupationName, String personId, String relationship,
			int supportCareType, String notificationName, int togSepDivision, int workStudent){
		super();
		this.ctgItemType = CtgItemType.FAMILY;
		this.birthday = birthday;
		this.deadDay = deadDay;
		this.entryDate = entryDate;
		this.expelledDate = expelledDate;
		this.familyId = familyId;
		this.name = name;
		this.nameKana = nameKana;
		this.nameMulti = nameMulti;
		this.nameMultiKana = nameMultiKana;
		this.nameRomaji = nameRomaji;
		this.nameRomajiKana = nameRomajiKana;
		this.nationality = nationality;
		this.occupationName = occupationName;
		this.personId = personId;
		this.relationship = relationship;
		this.supportCareType = supportCareType;
		this.notificationName = notificationName;
		this.togSepDivision = togSepDivision;
		this.workStudent = workStudent;
	}
	
	public static ItemFamilyDto createFromJavaType(GeneralDate birthday, GeneralDate deadDay, GeneralDate entryDate, GeneralDate expelledDate,
			String familyId, String name, String nameKana, String nameMulti, String nameMultiKana, String nameRomaji,
			String nameRomajiKana, String nationality, String occupationName, String personId, String relationship,
			int supportCareType, String notificationName, int togSepDivision, int workStudent){
		return new ItemFamilyDto(birthday, deadDay, entryDate, expelledDate, familyId, name, nameKana, nameMulti, 
				nameMultiKana, nameRomaji, nameRomajiKana, nationality, occupationName, personId, relationship, 
				supportCareType, notificationName, togSepDivision, workStudent);
	}
}
