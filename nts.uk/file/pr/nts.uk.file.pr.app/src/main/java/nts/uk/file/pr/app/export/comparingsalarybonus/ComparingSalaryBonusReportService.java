package nts.uk.file.pr.app.export.comparingsalarybonus;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.error.RawErrorMessage;
import nts.arc.layer.app.file.export.ExportService;
import nts.arc.layer.app.file.export.ExportServiceContext;
import nts.uk.ctx.pr.report.app.payment.comparing.confirm.find.DetailDifferentialDto;
import nts.uk.ctx.pr.report.app.payment.comparing.confirm.find.DetailDifferentialFinder;
import nts.uk.ctx.pr.report.dom.payment.comparing.settingoutputitem.ComparingFormDetail;
import nts.uk.file.pr.app.export.comparingsalarybonus.data.ComparingSalaryBonusHeaderReportData;
import nts.uk.file.pr.app.export.comparingsalarybonus.data.ComparingSalaryBonusReportData;
import nts.uk.file.pr.app.export.comparingsalarybonus.data.DataRowComparingSalaryBonus;
import nts.uk.file.pr.app.export.comparingsalarybonus.data.DeparmentInf;
import nts.uk.file.pr.app.export.comparingsalarybonus.data.DetailEmployee;
import nts.uk.file.pr.app.export.comparingsalarybonus.data.HeaderTable;
import nts.uk.file.pr.app.export.comparingsalarybonus.data.TotalValue;
import nts.uk.file.pr.app.export.comparingsalarybonus.query.ComparingSalaryBonusQuery;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

@Stateless
public class ComparingSalaryBonusReportService extends ExportService<ComparingSalaryBonusQuery> {

	@Inject
	private ComparingSalaryBonusGenerator compareGenertor;

	@Inject
	private ComparingSalaryBonusQueryRepository compareSalaryBonusQueryRepo;

	@Inject
	private DetailDifferentialFinder detailDifferentialFinder;

	@Override
	protected void handle(ExportServiceContext<ComparingSalaryBonusQuery> context) {
		ComparingSalaryBonusQuery comparingQuery = context.getQuery();
		LoginUserContext loginUserContext = AppContexts.user();
		String companyCode = loginUserContext.companyCode();
		// error1
		if (comparingQuery.getMonth1() == 0 || comparingQuery.getMonth2() == 0) {
			throw new BusinessException(new RawErrorMessage("比較年月1、比較年月2 が入力されていません。"));
		}
		// error 31
		if (comparingQuery.getMonth1() == comparingQuery.getMonth2()) {
			throw new BusinessException(new RawErrorMessage("設定が正しくありません。"));
		}

		if (comparingQuery.getEmployeeCodeList().isEmpty()) {
			throw new BusinessException(new RawErrorMessage("List Employee is not selected"));
		}

		List<ComparingFormDetail> lstComparingFormDetail = this.compareSalaryBonusQueryRepo
				.getPayComDetailByFormCode(companyCode, comparingQuery.getFormCode());
		// error1

		/****************** EA2 ******************/
		List<DetailDifferentialDto> lstDetailDto = this.detailDifferentialFinder.getDetailDifferential(
				comparingQuery.getMonth1(), comparingQuery.getMonth2(), comparingQuery.getEmployeeCodeList());
		List<DetailDifferentialDto> lstDetailFinal = new ArrayList<>();
		lstComparingFormDetail.stream().forEach(c -> {
			List<DetailDifferentialDto> lstDetail = lstDetailDto.stream()
					.filter(s -> (c.getCategoryAtr().value == s.getCategoryAtr()
							&& s.getItemCode().equals(c.getItemCode().toString())))
					.collect(Collectors.toList());
			lstDetailFinal.addAll(lstDetail);
		});
		ComparingSalaryBonusReportData reportData = fakeData(companyCode, comparingQuery, lstDetailFinal);
		this.compareGenertor.generate(context.getGeneratorContext(), reportData);

	}

	private ComparingSalaryBonusReportData fakeData(String companyCode, ComparingSalaryBonusQuery comparingQuery,
			List<DetailDifferentialDto> lstDetailComparing) {
		ComparingSalaryBonusReportData reportData = new ComparingSalaryBonusReportData();
		// TODO-LanLT: doan nay dang chua hieu tai lieu
		// List<ComparingSalaryBonusHeaderReportData> lstheaderData;
		// lstheaderData =
		// this.compareSalaryBonusQueryRepo.getReportHeader(companyCode);
		// if (lstheaderData.isEmpty()) {
		// throw new BusinessException(new RawErrorMessage("Data is not
		// found"));
		// }
		// int max = lstheaderData.size();
		ComparingSalaryBonusHeaderReportData headerData = new ComparingSalaryBonusHeaderReportData();
		headerData.setTitleReport("明細金額比較表");
		headerData.setNameCompany("A");
		headerData.setNameDeparment("【部門：役員　販売促進1課　役員～製造部　製造課　製造（31部門）】");
		headerData.setTypeDeparment("【分類：正社員～アルバイト（５分類）】");
		headerData.setPostion("【職位：参事～主任（10職位）】");
		headerData.setTargetYearMonth("【処理年月：平成12年2月度】");

		HeaderTable headerTable = new HeaderTable();
		headerTable.setItemName("項目名称");
		headerTable.setMonth1(comparingQuery.getMonthJapan1() + "(A)");
		headerTable.setMonth2(comparingQuery.getMonthJapan2() + "(B)");
		headerTable.setDifferentSalary("差額(C)");
		headerTable.setRegistrationStatus1("登録状況1" + "(" + comparingQuery.getMonthJapan1() + ")");
		headerTable.setRegistrationStatus2("登録状況2" + "(" + comparingQuery.getMonthJapan2() + ")");
		headerTable.setReason("差異理由");
		headerTable.setConfirmed("確認済");

		reportData.setHeaderData(headerData);
		reportData.setHeaderTable(headerTable);

		// nhung item code co employee ID giong nhau thi sap xep nhan vien do co
		// nhung item code day.
		List<DetailEmployee> lstDetailEmployee = new ArrayList<>();
		comparingQuery.getEmployeeCodeList().stream().forEach(c -> {
			List<DetailDifferentialDto> comparingDto = null;
			comparingDto = lstDetailComparing.stream().filter(s -> s.getEmployeeCode().equals(c))
					.collect(Collectors.toList());
			if (comparingDto.isEmpty()) {
				// throw new BusinessException(new RawErrorMessage(c));
				System.out.println(c);
			} else {
				DetailEmployee detailEmployee = new DetailEmployee();
				detailEmployee.setPersonID(c);
				detailEmployee.setPersonName(comparingDto.get(0).getEmployeeName());
				List<DataRowComparingSalaryBonus> lstDataRowComparingSalaryBonus = comparingDto.stream()
						.map(comparing -> {
							DataRowComparingSalaryBonus dataRow = new DataRowComparingSalaryBonus();
							dataRow.setItemName(comparing.getItemName());
//                            BigDecimal value = new BigDecimal(-1);
//                            System.out.println(value);
//							// data don't exist database
//							if (comparing.getComparisonValue1().compareTo(value) == 0) {
//								dataRow.setMonth1(new BigDecimal(""));
//							} else {
//								dataRow.setMonth1(comparing.getComparisonValue1());
//							}
//
//							// data don't exist database
//							if (comparing.getComparisonValue2().compareTo(value) == 0) {
//								dataRow.setMonth2(new BigDecimal(""));
//							} else {
//								dataRow.setMonth2(comparing.getComparisonValue1());
//							}
							dataRow.setMonth1(comparing.getComparisonValue1());
							dataRow.setMonth2(comparing.getComparisonValue1());
							dataRow.setDifferentSalary(comparing.getValueDifference());
							dataRow.setRegistrationStatus1(comparing.getRegistrationStatus1());
							dataRow.setRegistrationStatus2(comparing.getRegistrationStatus2());
							dataRow.setReason(comparing.getReasonDifference());
							if (comparing.getConfirmedStatus() == 1) {
								dataRow.setConfirmed("O");
							} else {
								dataRow.setConfirmed("");
							}
							return dataRow;

						}).collect(Collectors.toList());

				detailEmployee.setLstData(lstDataRowComparingSalaryBonus);
				lstDetailEmployee.add(detailEmployee);
			}
		});

		List<DeparmentInf> lstDepartmentData = new ArrayList<>();
		DeparmentInf deparmentInf = new DeparmentInf("0000000101", "部門1", lstDetailEmployee, 0.0, 0.0, 0.0);
		lstDepartmentData.add(deparmentInf);
		lstDepartmentData.add(new DeparmentInf("0000000102", "部門2", lstDetailEmployee, 0.0, 0.0, 0.0));
		reportData.setDeparmentInf(lstDepartmentData);

		DataRowComparingSalaryBonus divionsTotal = new DataRowComparingSalaryBonus();
		divionsTotal.setItemName("部門計");
		divionsTotal.setMonth1(new BigDecimal(deparmentInf.total().getTotalMonth1()));
		divionsTotal.setMonth2(new BigDecimal(deparmentInf.total().getTotalMonth2()));
		divionsTotal.setDifferentSalary(new BigDecimal(deparmentInf.total().getDifferentSalary()));
		reportData.setDivisionTotal(divionsTotal);

		DataRowComparingSalaryBonus totalA = new DataRowComparingSalaryBonus();
		totalA.setItemName("A　部門階層累計");
		totalA.setMonth1(new BigDecimal(deparmentInf.total().getTotalMonth1()));
		reportData.setTotalA(totalA);

		DataRowComparingSalaryBonus totalC = new DataRowComparingSalaryBonus();
		totalC.setItemName("C 部門階層累計");
		totalC.setDifferentSalary(new BigDecimal(deparmentInf.total().getDifferentSalary()));
		reportData.setTotalC(totalC);

		// TotalValue totalValue = new TotalValue();
		// lstDepartmentData.stream().forEach(c ->{
		// totalValue.setTotalMonth1(totalValue.getTotalMonth1() +
		// c.total().getTotalMonth1());
		// totalValue.setTotalMonth2(totalValue.getTotalMonth2()
		// +c.total().getTotalMonth2());
		// totalValue.setDifferentSalary(totalValue.getDifferentSalary()
		// +c.total().getDifferentSalary());
		// });
		DataRowComparingSalaryBonus grandTotal = new DataRowComparingSalaryBonus();
		grandTotal.setItemName("総合計");
		// grandTotal.setMonth1(new BigDecimal(totalValue.getTotalMonth1()));
		// grandTotal.setMonth2(new BigDecimal(totalValue.getTotalMonth2()));
		// grandTotal.setDifferentSalary(new
		// BigDecimal(totalValue.getDifferentSalary()));
		reportData.setGrandTotal(grandTotal);

		return reportData;
	}

}
