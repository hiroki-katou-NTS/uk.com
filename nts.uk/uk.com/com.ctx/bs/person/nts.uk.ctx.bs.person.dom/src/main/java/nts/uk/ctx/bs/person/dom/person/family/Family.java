/**
 * 
 */
package nts.uk.ctx.bs.person.dom.person.family;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.person.dom.person.info.currentaddress.CountryId;
import nts.uk.ctx.bs.person.dom.person.info.fullnameset.FullNameSet;

/**
 * @author danpv
 *
 */
@Getter
public class Family extends AggregateRoot{
	
	private GeneralDate Birthday;
	
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
	
	private int SupportCareType;
	
	private String notificationName;
	
	private int togSepDivision;
	
	private int workStudent;
}
