package nts.uk.ctx.at.record.app.query.reservation;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.apache.logging.log4j.util.Strings;

import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.record.dom.adapter.person.EmpBasicInfoImport;
import nts.uk.ctx.at.record.dom.adapter.person.PersonInfoAdapter;
import nts.uk.ctx.at.record.dom.adapter.workplace.SWkpHistRcImported;
import nts.uk.ctx.at.record.dom.adapter.workplace.SyWorkplaceAdapter;
import nts.uk.ctx.at.shared.dom.adapter.employment.ShareEmploymentAdapter;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmploymentRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.ClosureService;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class ReservationExportQuery {
	
	@Inject
	private ClosureRepository closureRepo;
	
	@Inject
	private ClosureEmploymentRepository closureEmploymentRepo;
	
	@Inject
	private ShareEmploymentAdapter shareEmploymentAdapter;
	
	@Inject
	private SyWorkplaceAdapter syWorkplaceAdapter;
	
	@Inject
	private PersonInfoAdapter personInfoAdapter;
	
	public ReservationExportDto startup(ReservationExportParam param) {
		String loginID = AppContexts.user().employeeId();
		GeneralDate systemDate = GeneralDate.today();
		ReservationExportDto reservationExportDto = new ReservationExportDto();
		
		// 社員情報を取得
		if(Strings.isNotBlank(param.getEmployeeID())) {
		 reservationExportDto = this.getEmpInfo(param.getEmployeeID(), GeneralDate.fromString(param.getDate(), "yyyy/MM/dd"));
		}
		
		
		// 社員に対応する締め期間を取得する
		DatePeriod period = ClosureService.findClosurePeriod(
				ClosureService.createRequireM3(closureRepo, closureEmploymentRepo, shareEmploymentAdapter),
				new CacheCarrier(), loginID, systemDate);
		
		reservationExportDto.setStartDate(period.start().toString());
		reservationExportDto.setEndDate(period.end().toString());
		
		return reservationExportDto;
	}
	
	/**
	 * UKDesign.UniversalK.オフィス.KMR_予約.KMR005_月間予約台帳.A:月間予約台帳.メニュー別OCD.<<ScreenQuery>> 社員情報を取得
	 * @param employeeID
	 * @param date
	 * @return
	 */
	private ReservationExportDto getEmpInfo(String employeeID, GeneralDate date) {
		// 社員指定期間所属職場履歴を取得 
		List<SWkpHistRcImported> sWkpHistRcImportedLst = syWorkplaceAdapter.findWpkBySIDandPeriod(employeeID, new DatePeriod(date, date));
		// 社員IDから個人社員基本情報を取得
		EmpBasicInfoImport empBasicInfoImport = personInfoAdapter.getEmpBasicInfoImport(employeeID);
		return new ReservationExportDto(
				employeeID, 
				empBasicInfoImport.getEmployeeCode(), 
				empBasicInfoImport.getPName(), 
				CollectionUtil.isEmpty(sWkpHistRcImportedLst) ? "" : sWkpHistRcImportedLst.get(0).getWorkplaceName(), 
				null, 
				null);
	}
}
