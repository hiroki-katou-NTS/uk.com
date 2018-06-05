package nts.uk.ctx.at.shared.app.find.remainingnumber.paymana;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.uk.ctx.at.shared.dom.remainingnumber.paymana.PayoutSubofHDManaRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.paymana.SubstitutionOfHDManaDataRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class SubstitutionOfHDManagementDataFinder {

	@Inject
	private SubstitutionOfHDManaDataRepository substitutionOfHDManaDataRepository;
	
	@Inject 
	private PayoutSubofHDManaRepository payoutSubofHDManaRepository;
	/**
	 * ドメイン「振出管理データ」より紐付け対象となるデータを取得する
	 * @param sid
	 * @return
	 */
	public List<SubstitutionOfHDManagementDataDto> getBySidDatePeriod(String sid, String payoutID){
		List<String> listSubID = payoutSubofHDManaRepository.getByPayoutId(payoutID).stream().map(i -> i.getSubOfHDID())
				.collect(Collectors.toList());
		List<SubstitutionOfHDManagementDataDto> lstSubstitutionOfHDManagement =  substitutionOfHDManaDataRepository.getBySidDatePeriod(sid,payoutID, 0d).stream()
				.map(item -> {
					SubstitutionOfHDManagementDataDto subDto =SubstitutionOfHDManagementDataDto.createFromDomain(item);
					if (listSubID.contains(item.getSubOfHDID())){
						subDto.setLinked(true);
					}
					return subDto;
				}).collect(Collectors.toList());
		return lstSubstitutionOfHDManagement;
	}
	
	public List<SubstitutionOfHDManagementDataDto> getBysiDRemCod(String empId) {
		
		String cid = AppContexts.user().companyId();
		return substitutionOfHDManaDataRepository.getBysiDRemCod(cid, empId).stream().map(item -> SubstitutionOfHDManagementDataDto.createFromDomain(item))
				.collect(Collectors.toList());
	}
	
	public List<SubstitutionOfHDManagementDataDto> getBySidRemainDayAndInPayout(String sid) {
		return substitutionOfHDManaDataRepository.getBySidRemainDayAndInPayout(sid).stream().map(item -> SubstitutionOfHDManagementDataDto.createFromDomain(item))
				.collect(Collectors.toList());
	}
}
