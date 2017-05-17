package nts.uk.file.pr.app.export.comparingsalarybonus;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.error.RawErrorMessage;
import nts.arc.layer.app.file.export.ExportService;
import nts.arc.layer.app.file.export.ExportServiceContext;
import nts.uk.ctx.pr.report.app.payment.comparing.confirm.find.DetailDifferentialDto;
import nts.uk.ctx.pr.report.dom.payment.comparing.confirm.ComfirmDifferentRepository;
import nts.uk.ctx.pr.report.dom.payment.comparing.confirm.PaycompConfirm;
import nts.uk.file.pr.app.export.comparingsalarybonus.data.ComparingSalaryBonusHeaderReportData;
import nts.uk.file.pr.app.export.comparingsalarybonus.data.ComparingSalaryBonusReportData;
import nts.uk.file.pr.app.export.comparingsalarybonus.data.DataRowComparingSalaryBonus;
import nts.uk.file.pr.app.export.comparingsalarybonus.data.DataRowComparingSalaryBonusDto;
import nts.uk.file.pr.app.export.comparingsalarybonus.data.DeparmentInf;
import nts.uk.file.pr.app.export.comparingsalarybonus.data.DetailEmployee;
import nts.uk.file.pr.app.export.comparingsalarybonus.data.HeaderTable;
import nts.uk.file.pr.app.export.comparingsalarybonus.data.SalaryBonusDetail;
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
	private ComfirmDifferentRepository comfirmDiffRepository;

	private double grandTotalMonth1 = 0.0;
	private double grandTotalMonth2 = 0.0;
	private double grandTotalDifferent = 0.0;

	@Override
	protected void handle(ExportServiceContext<ComparingSalaryBonusQuery> context) {
		ComparingSalaryBonusQuery comparingQuery = context.getQuery();
		LoginUserContext loginUserContext = AppContexts.user();
		String companyCode = loginUserContext.companyCode();
		List<SalaryBonusDetail> lstEarlyMonth = this.compareSalaryBonusQueryRepo.getContentReportEarlyMonth(companyCode,
				comparingQuery.getEmployeeCodeList(), comparingQuery.getMonth1(), comparingQuery.getFormCode());
		List<SalaryBonusDetail> lstLaterMonth = this.compareSalaryBonusQueryRepo.getContentReportEarlyMonth(companyCode,
				comparingQuery.getEmployeeCodeList(), comparingQuery.getMonth2(), comparingQuery.getFormCode());
		List<PaycompConfirm> payCompComfirm = this.comfirmDiffRepository.getPayCompComfirm(companyCode,
				comparingQuery.getEmployeeCodeList(), comparingQuery.getMonth1(), comparingQuery.getMonth2());
		List<?> lstDepcodeMonth1 = this.compareSalaryBonusQueryRepo.getDepartmentCodeList(companyCode,
				comparingQuery.getEmployeeCodeList(), comparingQuery.getMonth1(), comparingQuery.getFormCode());
		List<?> lstDepcodeMonth2 = this.compareSalaryBonusQueryRepo.getDepartmentCodeList(companyCode,
				comparingQuery.getEmployeeCodeList(), comparingQuery.getMonth2(), comparingQuery.getFormCode());
		List<String> lstDepcode1 = lstDepcodeMonth1.stream().map(c -> {
			return c.toString();
		}).collect(Collectors.toList());
		List<String> lstDepcode2 = lstDepcodeMonth2.stream().map(c -> {
			return c.toString();
		}).collect(Collectors.toList());

		List<DeparmentInf> lstDepartment = lstEarlyMonth.stream().map(c -> {
			List<DetailEmployee> lstEmployee = new ArrayList<>();
			List<DataRowComparingSalaryBonus> lstDataRow = new ArrayList<>();
			SalaryBonusDetail salary = lstLaterMonth.stream().filter(s -> s.getItemCode().equals(c.getItemCode())
					&& s.getPersonId().equals(c.getPersonId()) && s.getDepartmentCode().equals(c.getDepartmentCode())
					&& s.getCategoryATR().equals(c.getCategoryATR()) && s.getHierarchyId().equals(c.getHierarchyId())
					&& s.getMakeMethodFlag().equals(c.getMakeMethodFlag()) && s.getScd().equals(c.getScd())
					&& s.getSpecificationCode().equals(c.getSpecificationCode())).findFirst().orElse(null);
			DataRowComparingSalaryBonus dataRow = new DataRowComparingSalaryBonus();
			dataRow.setItemName(c.getItemAbName());
			dataRow.setMonth1(c.getValue().doubleValue());
			dataRow.setMonth2(-1);
			dataRow.setDifferentSalary(0 - c.getValue().doubleValue());
			if (c.getMakeMethodFlag().equals("1")) {
				dataRow.setRegistrationStatus1("初期データ作成");
			} else if (c.getMakeMethodFlag().equals("0")) {
				dataRow.setRegistrationStatus1("就業システム連動");
			} else {
				dataRow.setRegistrationStatus1("");
			}
			dataRow.setRegistrationStatus2("");
			if (salary != null) {
				dataRow.setMonth2(salary.getValue().doubleValue());
				if (salary.getMakeMethodFlag().equals("1")) {
					dataRow.setRegistrationStatus2("初期データ作成");
				} else if (salary.getMakeMethodFlag().equals("0")) {
					dataRow.setRegistrationStatus2("就業システム連動");
				}
				dataRow.setDifferentSalary(salary.getValue().doubleValue() - c.getValue().doubleValue());
				lstLaterMonth.remove(salary);
			}
			PaycompConfirm payConfirm = payCompComfirm.stream()
					.filter(p -> p.getPersonID().equals(c.getPersonId())
							&& p.getItemCode().equals(c.getItemCode())
							&& p.getProcessingYMEarlier().equals(c.getProcessingYM())
							&& p.getProcessingYMLater().equals(c.getProcessingYM())
							&& p.getCategoryAtr().equals(c.getCategoryATR()))
					.findFirst().orElse(null);
			if (payConfirm != null) {
				dataRow.setReason(payConfirm.getReasonDifference().v());
				if (payConfirm.getConfirmedStatus().value == 1) {
					dataRow.setConfirmed("O");
				} else {
					dataRow.setConfirmed("");
				}
			} else {
				dataRow.setConfirmed("");
				dataRow.setReason("");
			}
			lstDataRow.add(dataRow);
			DetailEmployee detailEmployee = new DetailEmployee();
			detailEmployee.setPersonID(c.getScd());
			detailEmployee.setPersonName(c.getEmployeeName());
			detailEmployee.setLstData(lstDataRow);
			lstEmployee.add(detailEmployee);
			DeparmentInf deparmentInf = new DeparmentInf(c.getDepartmentCode(), c.getDepartmentName(),
					c.getHierarchyId(), lstEmployee);
			return deparmentInf;

		}).collect(Collectors.toList());
		
		List<DeparmentInf> lstDepartmentLasterYM =  lstLaterMonth.stream().map(c -> {
			List<DetailEmployee> lstEmployee = new ArrayList<>();
			List<DataRowComparingSalaryBonus> lstDataRow = new ArrayList<>();
			DataRowComparingSalaryBonus dataRow = new DataRowComparingSalaryBonus();
			dataRow.setItemName(c.getItemAbName());
			dataRow.setMonth1(-1);
			dataRow.setMonth2(c.getValue().doubleValue());
			dataRow.setDifferentSalary(c.getValue().doubleValue());
			if (c.getMakeMethodFlag().equals("1")) {
				dataRow.setRegistrationStatus2("初期データ作成");
			} else if (c.getMakeMethodFlag().equals("0")) {
				dataRow.setRegistrationStatus2("就業システム連動");
			} else {
				dataRow.setRegistrationStatus2("");
			}
			dataRow.setRegistrationStatus1("");
			PaycompConfirm payConfirm = payCompComfirm.stream()
					.filter(p -> p.getPersonID().equals(c.getPersonId())
							&& p.getItemCode().equals(c.getItemCode())
							&& p.getProcessingYMEarlier().equals(c.getProcessingYM())
							&& p.getProcessingYMLater().equals(c.getProcessingYM())
							&& p.getCategoryAtr().equals(c.getCategoryATR()))
					.findFirst().orElse(null);
			if (payConfirm != null) {
				dataRow.setReason(payConfirm.getReasonDifference().v());
				if (payConfirm.getConfirmedStatus().value == 1) {
					dataRow.setConfirmed("O");
				} else {
					dataRow.setConfirmed("");
				}
			} else {
				dataRow.setConfirmed("");
				dataRow.setReason("");
			}
			lstDataRow.add(dataRow);
			DetailEmployee detailEmployee = new DetailEmployee();
			detailEmployee.setPersonID(c.getScd());
			detailEmployee.setPersonName(c.getEmployeeName());
			detailEmployee.setLstData(lstDataRow);
			lstEmployee.add(detailEmployee);
			DeparmentInf deparmentInf = new DeparmentInf(c.getDepartmentCode(), c.getDepartmentName(),
					c.getHierarchyId(), lstEmployee);
			return deparmentInf;
		}).collect(Collectors.toList());
		lstDepartment.addAll(lstDepartmentLasterYM);
		
		
		List<DeparmentInf> lstDepartmentFinal = new ArrayList<>();
		lstDepcode1.stream().forEach(c -> {
			List<DeparmentInf> lstDepartment2 = lstDepartment.stream().filter(dep -> dep.getDepcode().equals(c))
					.collect(Collectors.toList());

			List<DetailEmployee> lstEmployee = new ArrayList<>();
			
			lstDepartment2.stream().forEach(s -> {
				lstEmployee.addAll(s.getLstEmployee());
			});
			DeparmentInf dep = new DeparmentInf(c, lstDepartment2.get(0).getDepname(),
					lstDepartment2.get(0).getHyrachi(), lstEmployee);
			lstDepartmentFinal.add(dep);
		});
		System.out.println(lstDepartmentFinal.size());

		// error1
		if (comparingQuery.getMonth1() == 0 || comparingQuery.getMonth2() == 0) {
			throw new BusinessException(new RawErrorMessage("比較年月1、比較年月2 が入力されていません。"));
		}
		// error 31
		if (comparingQuery.getMonth1() == comparingQuery.getMonth2()) {
			throw new BusinessException(new RawErrorMessage("設定が正しくありません。"));
		}
		// error 07
		if (comparingQuery.getEmployeeCodeList().isEmpty()) {
			throw new BusinessException(new RawErrorMessage("雇員リストのが選択されていません。"));
		}
		if (comparingQuery.getFormCode().isEmpty()) {
			throw new BusinessException(new RawErrorMessage("設定が正しくありません。"));
		}

		List<DetailDifferentialDto> lstDetailFinal = new ArrayList<>();

		// error 10
		if (lstDetailFinal.isEmpty()) {
			throw new BusinessException(new RawErrorMessage("対象データがありません。"));
		} else {
			ComparingSalaryBonusReportData reportData = fakeData(companyCode, comparingQuery, lstDetailFinal);
			if (reportData.getGrandTotal().getDifferentSalary().isEmpty()
					&& reportData.getGrandTotal().getMonth1().isEmpty()
					&& reportData.getGrandTotal().getMonth2().isEmpty()) {
				throw new BusinessException(new RawErrorMessage(" amount item1  and amount item 2 is not updated"));
			} else {
				this.compareGenertor.generate(context.getGeneratorContext(), reportData);
			}
		}
	}

	private ComparingSalaryBonusReportData fakeData(String companyCode, ComparingSalaryBonusQuery comparingQuery,
			List<DetailDifferentialDto> lstDetailComparing) {
		ComparingSalaryBonusReportData reportData = new ComparingSalaryBonusReportData();
		ComparingSalaryBonusHeaderReportData headerData = new ComparingSalaryBonusHeaderReportData();
		HeaderTable headerTable = new HeaderTable();
		this.setDataHeader(comparingQuery, headerData, headerTable);
		reportData.setHeaderData(headerData);
		reportData.setHeaderTable(headerTable);

		// set List Detail Employee
		List<DetailEmployee> lstDetailEmployee = this.setListDataEmployee(comparingQuery, lstDetailComparing);

		// fake data of department
		List<DeparmentInf> lstDepartmentData = new ArrayList<>();
		// for (int i = 0; i < 10; i++) {
		// lstDepartmentData.add(new DeparmentInf("00" + i, "部門" + i,
		// lstDetailEmployee));
		// }
		reportData.setDeparmentInf(lstDepartmentData);

		// set total of department
		List<DataRowComparingSalaryBonusDto> lstDivionsTotal = new ArrayList<>();
		List<DataRowComparingSalaryBonusDto> lstTotalA = new ArrayList<>();
		List<DataRowComparingSalaryBonusDto> lstTotalC = new ArrayList<>();
		this.setTotalOfDepartment(lstDivionsTotal, lstTotalA, lstTotalC, lstDepartmentData);
		reportData.setLstDivisionTotal(lstDivionsTotal);
		reportData.setLstTotalA(lstTotalA);
		reportData.setLstTotalC(lstTotalC);

		// caculate and set data of grand total
		this.caculateGrandTotal(lstDepartmentData);
		DataRowComparingSalaryBonusDto grandTotal = new DataRowComparingSalaryBonusDto();
		this.setGrandTotal(grandTotal);
		reportData.setGrandTotal(grandTotal);
		return reportData;
	}

	private void setDataHeader(ComparingSalaryBonusQuery comparingQuery,
			ComparingSalaryBonusHeaderReportData headerData, HeaderTable headerTable) {
		// set Header of Report
		headerData.setTitleReport("明細金額比較表");
		headerData.setNameCompany("日通システム株式会社");
		headerData.setNameDeparment("【部門：役員　販売促進1課　役員～製造部　製造課　製造（31部門）】");
		headerData.setTypeDeparment("【分類：正社員～アルバイト（５分類）】");
		headerData.setPostion("【職位：参事～主任（10職位）】");
		headerData.setTargetYearMonth("【処理年月：平成12年2月度】");

		// set Header of Title
		headerTable.setItemName("項目名称");
		headerTable.setMonth1(comparingQuery.getMonthJapan1() + "(A)");
		headerTable.setMonth2(comparingQuery.getMonthJapan2() + "(B)");
		headerTable.setDifferentSalary("差額(C)");
		headerTable.setRegistrationStatus1("登録状況1" + "(" + comparingQuery.getMonthJapan1() + ")");
		headerTable.setRegistrationStatus2("登録状況2" + "(" + comparingQuery.getMonthJapan2() + ")");
		headerTable.setReason("差異理由");
		headerTable.setConfirmed("確認済");
	}

	/**
	 * nhung item code co employee ID giong nhau thi sap xep nhan vien do co
	 * 
	 * @param comparingQuery
	 * @param lstDetailComparing
	 * @return
	 */
	private List<DetailEmployee> setListDataEmployee(ComparingSalaryBonusQuery comparingQuery,
			List<DetailDifferentialDto> lstDetailComparing) {
		List<DetailEmployee> lstDetailEmployee = new ArrayList<>();
		comparingQuery.getEmployeeCodeList().stream().forEach(c -> {
			List<DetailDifferentialDto> comparingDto = null;
			comparingDto = lstDetailComparing.stream().filter(s -> s.getEmployeeCode().equals(c))
					.collect(Collectors.toList());
			if (!comparingDto.isEmpty()) {
				DetailEmployee detailEmployee = new DetailEmployee();
				detailEmployee.setPersonID(c);
				detailEmployee.setPersonName(comparingDto.get(0).getEmployeeName());
				List<DataRowComparingSalaryBonus> lstDataRowComparingSalaryBonus = comparingDto.stream()
						.map(comparing -> {
							DataRowComparingSalaryBonus dataRow = new DataRowComparingSalaryBonus();
							dataRow.setItemName(comparing.getItemName());
							dataRow.setMonth1(comparing.getComparisonValue1().doubleValue());
							dataRow.setMonth2(comparing.getComparisonValue2().doubleValue());
							dataRow.setDifferentSalary(comparing.getValueDifference().doubleValue());
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
				List<DataRowComparingSalaryBonusDto> lstDataDto = detailEmployee
						.convertDataRowComparingSalaryBonusDto(lstDataRowComparingSalaryBonus);
				detailEmployee.setLstDataDto(lstDataDto);
				detailEmployee.setLstData(lstDataRowComparingSalaryBonus);
				lstDetailEmployee.add(detailEmployee);
			}
		});

		return lstDetailEmployee;
	}

	/**
	 * set total of department
	 * 
	 * @param lstDivionsTotal
	 * @param lstTotalA
	 * @param lstTotalC
	 * @param lstDepartmentData
	 */
	private void setTotalOfDepartment(List<DataRowComparingSalaryBonusDto> lstDivionsTotal,
			List<DataRowComparingSalaryBonusDto> lstTotalA, List<DataRowComparingSalaryBonusDto> lstTotalC,
			List<DeparmentInf> lstDepartmentData) {
		lstDepartmentData.stream().forEach(c -> {
			DataRowComparingSalaryBonusDto divionsTotal = new DataRowComparingSalaryBonusDto();
			divionsTotal.setItemName("部門計");
			if (c.getTotalMonth1() >= 0) {
				divionsTotal.setMonth1(c.getTotalMonth1().toString());
			} else {
				divionsTotal.setMonth1("");
			}
			if (c.getTotalMonth2() >= 0) {
				divionsTotal.setMonth2(c.getTotalMonth2().toString());
			} else {
				divionsTotal.setMonth2("");
			}
			if (c.getTotalDifferent() >= 0) {
				divionsTotal.setDifferentSalary(c.getTotalDifferent().toString());
			} else {
				divionsTotal.setDifferentSalary("");
			}
			lstDivionsTotal.add(divionsTotal);

			DataRowComparingSalaryBonusDto totalA = new DataRowComparingSalaryBonusDto();
			totalA.setItemName("部門階層累計");
			if (c.getTotalMonth1() >= 0) {
				totalA.setMonth1(c.getTotalMonth1().toString());
			} else {
				divionsTotal.setMonth1("");
			}
			if (c.getTotalMonth2() >= 0) {
				totalA.setMonth2(c.getTotalMonth2().toString());
			} else {
				totalA.setMonth2("");
			}
			if (c.getTotalDifferent() >= 0) {
				totalA.setDifferentSalary(c.getTotalDifferent().toString());
			} else {
				totalA.setDifferentSalary("");
			}
			lstTotalA.add(totalA);

			DataRowComparingSalaryBonusDto totalC = new DataRowComparingSalaryBonusDto();
			totalC.setItemName("C 部門階層累計");
			if (c.getTotalDifferent() >= 0) {
				totalC.setDifferentSalary(c.getTotalDifferent().toString());
			} else {
				totalC.setDifferentSalary("");
			}
			lstTotalC.add(totalC);
		});

	}

	/**
	 * set grand total
	 * 
	 * @param grandTotal
	 */
	private void setGrandTotal(DataRowComparingSalaryBonusDto grandTotal) {
		grandTotal.setItemName("総合計");
		if (this.grandTotalMonth1 >= 0) {
			grandTotal.setMonth1(String.valueOf(this.grandTotalMonth1));
		} else {
			grandTotal.setMonth1("");
		}
		if (this.grandTotalMonth2 >= 0) {
			grandTotal.setMonth2(String.valueOf(this.grandTotalMonth2));
		} else {
			grandTotal.setMonth2("");
		}
		if (this.grandTotalDifferent >= 0) {
			grandTotal.setDifferentSalary(String.valueOf(this.grandTotalDifferent));
		} else {
			grandTotal.setDifferentSalary("");
		}
	}

	/**
	 * caculate grand total
	 * 
	 * @param lstDepartmentData
	 */
	private void caculateGrandTotal(List<DeparmentInf> lstDepartmentData) {
		this.grandTotalMonth1 = 0.0;
		this.grandTotalMonth2 = 0.0;
		this.grandTotalDifferent = 0.0;
		lstDepartmentData.stream().forEach(c -> {
			this.grandTotalMonth1 += c.getTotalMonth1();
			this.grandTotalMonth2 += c.getTotalMonth2();
			this.grandTotalDifferent += c.getTotalDifferent();
		});
	}

}
