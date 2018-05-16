package nts.uk.ctx.at.record.app.find.remainingnumber.paymana;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.remainingnumber.paymana.SubstitutionOfHDManaDataRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class SubstitutionOfHDManagementDataFinder {

	@Inject
	private SubstitutionOfHDManaDataRepository substitutionOfHDManaDataRepository;
	
	/**
	 * ドメイン「振出管理データ」より紐付け対象となるデータを取得する
	 * @param sid
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List<SubstitutionOfHDManagementDataDto> getBySidDatePeriod(String sid, GeneralDate startDate, GeneralDate endDate){
		return substitutionOfHDManaDataRepository.getBySidDatePeriod(sid, startDate, endDate, 0d).stream()
				.map(item -> SubstitutionOfHDManagementDataDto.createFromDomain(item)).collect(Collectors.toList());
	}
	
	public List<SubstitutionOfHDManagementDataDto> getBysiDRemCod(String empId) {
		
		String cid = AppContexts.user().companyId();
		return substitutionOfHDManaDataRepository.getBysiDRemCod(cid, empId).stream().map(item -> SubstitutionOfHDManagementDataDto.createFromDomain(item))
				.collect(Collectors.toList());
	}
	
	public List<SubstitutionOfHDManagementDataDto> getBySidDatePeriodNoRemainDay(String sid, GeneralDate startDate, GeneralDate endDate) {
		return substitutionOfHDManaDataRepository.getBySidDatePeriodNoRemainDay(sid, startDate, endDate).stream().map(item -> SubstitutionOfHDManagementDataDto.createFromDomain(item))
				.collect(Collectors.toList());
	}
	
	public List<SubstitutionOfHDManagementDataDto> getBySidRemainDayAndInPayout(String sid) {
		return substitutionOfHDManaDataRepository.getBySidRemainDayAndInPayout(sid).stream().map(item -> SubstitutionOfHDManagementDataDto.createFromDomain(item))
				.collect(Collectors.toList());
	}
}
