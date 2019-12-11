package nts.uk.file.at.app.export.bento;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.file.export.ExportService;
import nts.arc.layer.app.file.export.ExportServiceContext;
import nts.arc.time.GeneralDate;
import nts.arc.time.period.DatePeriod;
import nts.uk.shr.com.company.CompanyAdapter;
import nts.uk.shr.com.company.CompanyInfor;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class ReservationMonthExportService extends ExportService<ReservationMonthQuery>{
	
	@Inject
	private ReservationMonthGenerator reservationMonthGenerator;
	
	@Inject
	private CompanyAdapter company;

	@Override
	protected void handle(ExportServiceContext<ReservationMonthQuery> context) {
		ReservationMonthQuery query = context.getQuery();
		String companyID = AppContexts.user().companyId();
		Optional<CompanyInfor> companyInfo = company.getCurrentCompany();
		
		Map<GeneralDate, Integer> quantityDateMap =  new HashMap<>();
		quantityDateMap.put(GeneralDate.fromString("2019/12/10", "yyyy/MM/dd"), 1);
		
		List<ReservationBentoLedger> reservationBentoLedgerLst = new ArrayList<>();
		for(int i = 1; i<=40; i++) {
			ReservationBentoLedger reservationBentoLedger = new ReservationBentoLedger(
					i,
					"bento" + i, 
					i, 
					i, 
					i, 
					quantityDateMap);
			reservationBentoLedgerLst.add(reservationBentoLedger);
		}
		
		ReservationEmpLedger reservationEmpLedger1 = new ReservationEmpLedger(
				"emp1", 
				"employee1", 
				1, 
				1, 
				1, 
				reservationBentoLedgerLst);
		
		ReservationEmpLedger reservationEmpLedger2 = new ReservationEmpLedger(
				"emp2", 
				"employee2", 
				1, 
				1, 
				1, 
				reservationBentoLedgerLst);
		
		ReservationWkpLedger reservationWkpLedger1 = new ReservationWkpLedger(
				"wkp1", 
				"workplace1", 
				Arrays.asList(reservationEmpLedger1, reservationEmpLedger2));
		
		ReservationWkpLedger reservationWkpLedger2 = new ReservationWkpLedger(
				"wkp2", 
				"workplace2", 
				Arrays.asList(reservationEmpLedger1, reservationEmpLedger2));
		
		ReservationMonthDataSource dataSource = new ReservationMonthDataSource(
				companyInfo.map(x -> x.getCompanyName()).orElse(""), 
				new DatePeriod(GeneralDate.fromString("2019/12/01", "yyyy/MM/dd"), GeneralDate.fromString("2019/12/31", "yyyy/MM/dd")), 
				Arrays.asList(reservationWkpLedger1, reservationWkpLedger2));

		// generate file
		reservationMonthGenerator.generate(context.getGeneratorContext(), dataSource);
		
	}

}
