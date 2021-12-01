package nts.uk.screen.at.app.query.knr.knr001.a;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.pub.workplace.master.WorkplacePub;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author dungbn
 *	基準日を変えて職場名称を取得する
 * 	UKDesign.UniversalK.就業.KNR_就業情報端末.KNR001_就業情報端末の登録.メニュー別OCD.基準日を変えて職場名称を取得する.基準日を変えて職場名称を取得する
 */
@Stateless
public class GetWorkplaceNameChangingBaseDateSQ {

	@Inject
	private WorkplacePub workplacePub;
	
	public List<GetWorkplaceNameChangingBaseDateDto> getWorkplaceNameChangingBaseDate(GetWorkplaceNameChangingBaseDateInput input) {
		
		String companyId = AppContexts.user().companyId();
		
		List<GetWorkplaceNameChangingBaseDateDto> listDto = workplacePub
				.getWorkplaceInforByWkpIds(companyId, input.getListWorkPlaceId(), GeneralDate.fromString(input.getBaseDate(), "yyyy/MM/dd"))
				.stream()
				.map(e -> new GetWorkplaceNameChangingBaseDateDto(
						e.getWorkplaceId(),
						e.getHierarchyCode(),
						e.getWorkplaceCode(),
						e.getWorkplaceName(),
						e.getWorkplaceDisplayName(),
						e.getWorkplaceGenericName(),
						e.getWorkplaceExternalCode()))
				.collect(Collectors.toList());
	
		return listDto;
	}
	
}
