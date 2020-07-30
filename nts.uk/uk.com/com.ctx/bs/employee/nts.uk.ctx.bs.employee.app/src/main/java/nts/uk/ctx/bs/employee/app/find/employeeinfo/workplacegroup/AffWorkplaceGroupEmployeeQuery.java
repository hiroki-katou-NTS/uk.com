package nts.uk.ctx.bs.employee.app.find.employeeinfo.workplacegroup;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.dom.workplace.group.WorkplaceGroup;
import nts.uk.ctx.bs.employee.dom.workplace.group.WorkplaceGroupRespository;
import nts.uk.shr.com.context.AppContexts;

/**
 * <<Query>> 社員の所属職場グループを取得する
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.基幹.社員.職場.職場グループ.App.社員の所属職場グループを取得する
 * @author quytb
 *
 */
@Stateless
public class AffWorkplaceGroupEmployeeQuery {
	
	@Inject
	private WorkplaceGroupRespository workplaceGroupRespository;
	
	/** 社員の所属職場グループを取得する */
	public AffWorkplaceGroupDto getWorkplaceGroupOfEmployee(){
		//TODO DS get WKPGRPID - chua co DS(input employeeId va baseDate)
		String employeeId = AppContexts.user().employeeId();
		GeneralDate baseDate = GeneralDate.today();
		
		
		//return EmployeeOrganizationImport
//		EmployeeOrganizationImport employeeOrganization = new EmployeeOrganizationImport(Optional.of("AAA"), "111", Optional.of("09012"), "1d2d309a-0080-47aa-845e-dad2e436673d", Optional.of("3d9b24dc-c2c4-400e-8737-edcef04db18b"));
//		String WKPGRPID = employeeOrganization.getWorkplaceGroupId().orElse(null);
		String WKPGRPID = "3d9b24dc-c2c4-400e-8737-edcef04db18b";
		String companyId = AppContexts.user().companyId();
		Optional<WorkplaceGroup> workplaceGroup = workplaceGroupRespository.getById(companyId, WKPGRPID);
		if(workplaceGroup.isPresent()){
			WorkplaceGroup x = workplaceGroup.get();
			System.out.println(x.getWKPGRPID());
		}
		
		
		return workplaceGroupRespository.getById(companyId, WKPGRPID).map(x -> new AffWorkplaceGroupDto(x.getWKPGRPName().v(),
				x.getWKPGRPID())).orElse(null);			
	}
}
