package nts.uk.file.pr.app.export.comparingsalarybonus;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.error.RawErrorMessage;
import nts.arc.layer.app.file.export.ExportService;
import nts.arc.layer.app.file.export.ExportServiceContext;
import nts.uk.ctx.pr.report.dom.payment.comparing.confirm.ComfirmDifferentRepository;
import nts.uk.ctx.pr.report.dom.payment.comparing.confirm.PaycompConfirm;
import nts.uk.ctx.pr.report.dom.payment.comparing.setting.ComparingPrintSet;
import nts.uk.ctx.pr.report.dom.payment.comparing.setting.ComparingPrintSetRepository;
import nts.uk.file.pr.app.export.comparingsalarybonus.data.ComparingSalaryBonusHeaderReportData;
import nts.uk.file.pr.app.export.comparingsalarybonus.data.ComparingSalaryBonusReportData;
import nts.uk.file.pr.app.export.comparingsalarybonus.data.DataRowComparingSalaryBonus;
import nts.uk.file.pr.app.export.comparingsalarybonus.data.DataRowComparingSalaryBonusDto;
import nts.uk.file.pr.app.export.comparingsalarybonus.data.DeparmentInf;
import nts.uk.file.pr.app.export.comparingsalarybonus.data.DepartmentDto;
import nts.uk.file.pr.app.export.comparingsalarybonus.data.DepartmentFlatMap;
import nts.uk.file.pr.app.export.comparingsalarybonus.data.DepartmentHyrachi;
import nts.uk.file.pr.app.export.comparingsalarybonus.data.DetailEmployee;
import nts.uk.file.pr.app.export.comparingsalarybonus.data.DetailEmployeeDto;
import nts.uk.file.pr.app.export.comparingsalarybonus.data.HeaderTable;
import nts.uk.file.pr.app.export.comparingsalarybonus.data.SalaryBonusDetail;
import nts.uk.file.pr.app.export.comparingsalarybonus.data.TotalByHyrachi;
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
	private ComfirmDifferentRepository comfirmDiffRepository;

	@Inject
	private ComparingPrintSetRepository comparingPrintSetRepository;

	private double grandTotalMonth1 = 0.0;
	private double grandTotalMonth2 = 0.0;
	private double grandTotalDifferent = 0.0;

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
		// error 07
		if (comparingQuery.getEmployeeCodeList().isEmpty()) {
			throw new BusinessException(new RawErrorMessage("雇員リストのが選択されていません。"));
		}
		if (comparingQuery.getFormCode().isEmpty()) {
			throw new BusinessException(new RawErrorMessage("設定が正しくありません。"));
		}
		List<DeparmentInf> lstDepartmentFinal = this.filterData(comparingQuery, companyCode);
		// error 10
		if (lstDepartmentFinal.isEmpty()) {
			throw new BusinessException(new RawErrorMessage("対象データがありません。"));
		} else {
			ComparingSalaryBonusReportData reportData = fakeData(companyCode, comparingQuery, lstDepartmentFinal);
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
			List<DeparmentInf> lstDepartmentData) {
		ComparingSalaryBonusReportData reportData = new ComparingSalaryBonusReportData();
		ComparingSalaryBonusHeaderReportData headerData = new ComparingSalaryBonusHeaderReportData();
		ComparingPrintSet comparingPrintSet = this.comparingPrintSetRepository.getComparingPrintSet(companyCode)
				.orElse(null);
		HeaderTable headerTable = new HeaderTable();
		this.setDataHeader(comparingQuery, headerData, headerTable);
		reportData.setHeaderData(headerData);
		reportData.setHeaderTable(headerTable);
		reportData.setDeparmentInf(lstDepartmentData);
		// set total of department
		List<DataRowComparingSalaryBonusDto> lstDivionsTotal = new ArrayList<>();
		List<DataRowComparingSalaryBonusDto> lstTotalA = new ArrayList<>();
		List<DataRowComparingSalaryBonusDto> lstTotalC = new ArrayList<>();
		this.setTotalOfDepartment(lstDivionsTotal, lstTotalA, lstTotalC, lstDepartmentData);
		reportData.setLstDivisionTotal(lstDivionsTotal);
		reportData.setLstTotalA(lstTotalA);
		reportData.setLstTotalC(lstTotalC);

		// set total Hyrachi of department
		List<Integer> lstHyrachi = new ArrayList<>();
		if (comparingPrintSet.getHrchyIndex1().value > 0) {
			lstHyrachi.add(comparingPrintSet.getHrchyIndex1().value);
		}
		if (comparingPrintSet.getHrchyIndex2().value > 0) {
			lstHyrachi.add(comparingPrintSet.getHrchyIndex2().value);
		}
		if (comparingPrintSet.getHrchyIndex3().value > 0) {
			lstHyrachi.add(comparingPrintSet.getHrchyIndex3().value);
		}
		if (comparingPrintSet.getHrchyIndex4().value > 0) {
			lstHyrachi.add(comparingPrintSet.getHrchyIndex4().value);
		}
		if (comparingPrintSet.getHrchyIndex5().value > 0) {
			lstHyrachi.add(comparingPrintSet.getHrchyIndex5().value);
		}

		// caculate and set data of grand total
		this.caculateGrandTotal(lstDepartmentData);
		List<TotalByHyrachi> lstTotal = this.calculateTotalByHyrachi(lstDepartmentData, lstHyrachi, comparingQuery,
				companyCode);
		List<DataRowComparingSalaryBonusDto> lstTotalHyrachi = lstTotal.stream().map(c -> {
			DataRowComparingSalaryBonusDto dataRow = new DataRowComparingSalaryBonusDto();
			dataRow.setItemName("部門階層累計");
			dataRow.setHyrachi(c.getHyrachi());
			dataRow.setMonth1(String.valueOf(c.getGrandTotalHyrachi1()));
			dataRow.setMonth2(String.valueOf(c.getGrandTotalHyrachi2()));
			dataRow.setDifferentSalary(String.valueOf(c.getGrandTotalDifferentHyrachi()));
			return dataRow;
		}).collect(Collectors.toList());
		reportData.setLstHyrachiTotal(lstTotalHyrachi);
		List<DepartmentHyrachi> lstDepartmentHyrachi = this.caculateParent(comparingQuery, companyCode, lstDepartmentData);
		reportData.setLstDepartmentHyrachi(lstDepartmentHyrachi);
		DataRowComparingSalaryBonusDto grandTotal = new DataRowComparingSalaryBonusDto();
		this.setGrandTotal(grandTotal);
		reportData.setGrandTotal(grandTotal);
		if (comparingPrintSet != null) {
			reportData.setConfigPrint(comparingPrintSet);
		}

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

	private List<DeparmentInf> filterData(ComparingSalaryBonusQuery comparingQuery, String companyCode) {
		List<SalaryBonusDetail> lstEarlyMonth = this.compareSalaryBonusQueryRepo.getContentReportEarlyMonth(companyCode,
				comparingQuery.getEmployeeCodeList(), comparingQuery.getMonth1(), comparingQuery.getFormCode());
		List<SalaryBonusDetail> lstLaterMonth = this.compareSalaryBonusQueryRepo.getContentReportEarlyMonth(companyCode,
				comparingQuery.getEmployeeCodeList(), comparingQuery.getMonth2(), comparingQuery.getFormCode());
		List<PaycompConfirm> payCompComfirm = this.comfirmDiffRepository.getPayCompComfirm(companyCode,
				comparingQuery.getEmployeeCodeList(), comparingQuery.getMonth1(), comparingQuery.getMonth2());
		List<DepartmentDto> lstDepcodeMonth1 = this.compareSalaryBonusQueryRepo.getDepartmentCodeList(companyCode,
				comparingQuery.getEmployeeCodeList(), comparingQuery.getMonth1(), comparingQuery.getFormCode());
		List<DepartmentFlatMap> lstDepartment = lstEarlyMonth.stream().map(c -> {
			SalaryBonusDetail salary = lstLaterMonth.stream().filter(s -> s.getItemCode().equals(c.getItemCode())
					&& s.getPersonId().equals(c.getPersonId()) && s.getDepartmentCode().equals(c.getDepartmentCode())
					&& s.getCategoryATR().equals(c.getCategoryATR()) && s.getHierarchyId().equals(c.getHierarchyId())
					&& s.getMakeMethodFlag().equals(c.getMakeMethodFlag()) && s.getScd().equals(c.getScd())
					&& s.getSpecificationCode().equals(c.getSpecificationCode())).findFirst().orElse(null);
			DataRowComparingSalaryBonus dataRow = new DataRowComparingSalaryBonus();
			dataRow.setItemCode(c.getItemCode());
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
					.filter(p -> p.getPersonID().equals(c.getPersonId()) && p.getItemCode().equals(c.getItemCode())
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
			DetailEmployeeDto detailEmployee = new DetailEmployeeDto(c.getPersonId(), c.getEmployeeName(), dataRow);
			DepartmentFlatMap deparmentInf = new DepartmentFlatMap(c.getDepartmentCode(), c.getDepartmentName(),
					c.getHierarchyId(), detailEmployee);
			return deparmentInf;

		}).collect(Collectors.toList());

		List<DepartmentFlatMap> lstDepartmentLasterYM = lstLaterMonth.stream().map(c -> {
			DataRowComparingSalaryBonus dataRow = new DataRowComparingSalaryBonus();
			dataRow.setItemCode(c.getItemCode());
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
					.filter(p -> p.getPersonID().equals(c.getPersonId()) && p.getItemCode().equals(c.getItemCode())
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
			DetailEmployeeDto detailEmployee = new DetailEmployeeDto(c.getPersonId(), c.getEmployeeName(), dataRow);
			DepartmentFlatMap deparmentInf = new DepartmentFlatMap(c.getDepartmentCode(), c.getDepartmentName(),
					c.getHierarchyId(), detailEmployee);
			return deparmentInf;
		}).collect(Collectors.toList());
		lstDepartment.addAll(lstDepartmentLasterYM);

		List<DeparmentInf> lstDepartmentFinal = new ArrayList<>();
		lstDepcodeMonth1.stream().forEach(c -> {
			List<DetailEmployee> lstEmployee = new ArrayList<>();
			DepartmentFlatMap department = lstDepartment.stream().filter(dep -> dep.getDepcode().equals(c.getDepCode()))
					.findFirst().orElse(null);
			comparingQuery.getEmployeeCodeList().stream().forEach(em -> {
				List<DepartmentFlatMap> lstDepartment2 = lstDepartment.stream()
						.filter(dep -> dep.getDepcode().equals(c.getDepCode())
								&& dep.getEmployee().getPersonID().equals(em))
						.collect(Collectors.toList()).stream().distinct().collect(Collectors.toList());
				if (lstDepartment2.size() > 0) {
					List<DataRowComparingSalaryBonus> lstDataRow = new ArrayList<>();
					lstDepartment2.stream().forEach(dep -> {
						lstDataRow.add(dep.getEmployee().getDataRow());
					});
					DetailEmployee detail = new DetailEmployee();
					detail.setPersonID(lstDepartment2.get(0).getEmployee().getPersonID());
					detail.setPersonName(lstDepartment2.get(0).getEmployee().getPersonName());
					detail.setLstData(lstDataRow);
					List<DataRowComparingSalaryBonusDto> lstDataDto = detail
							.convertDataRowComparingSalaryBonusDto(lstDataRow);
					detail.setLstDataDto(lstDataDto);
					lstEmployee.add(detail);
				}
			});
			if (department != null) {
				lstDepartmentFinal.add(new DeparmentInf(c.getDepCode(), department.getDepname(), c.getHistoryId(),
						department.getHyrachi(), lstEmployee));
			}
		});
		return lstDepartmentFinal;
	}

	private List<TotalByHyrachi> calculateTotalByHyrachi(List<DeparmentInf> lstDepartInf, List<Integer> lstHyrachi,
			ComparingSalaryBonusQuery comparingQuery, String companyCode) {
		List<TotalByHyrachi> lstTotalByHyrachi = new ArrayList<>();
		List<DepartmentDto> lstDepcodeMonth1 = this.compareSalaryBonusQueryRepo.getDepartmentCodeList(companyCode,
				comparingQuery.getEmployeeCodeList(), comparingQuery.getMonth1(), comparingQuery.getFormCode());
		List<DepartmentDto> departmentParents = lstDepcodeMonth1.stream().filter(dm -> dm.getHyrachi().length() == 3)
				.collect(Collectors.toList());

		departmentParents.stream().forEach(c -> {
			List<DeparmentInf> lstDepartFilter = lstDepartInf.stream()
					.filter(s -> (s.getHyrachi().substring(0, 3)).equals(c.getHyrachi())).collect(Collectors.toList());
			TotalByHyrachi totalByHyrachi = new TotalByHyrachi();
			totalByHyrachi.setHyrachi(c.getHyrachi());
			for (int hyrachi : lstHyrachi) {
				lstDepartFilter.stream().forEach(dep -> {
					if (dep.getLengthHyrachi() == hyrachi) {
						totalByHyrachi
								.setGrandTotalHyrachi1(totalByHyrachi.getGrandTotalHyrachi1() + dep.getTotalMonth1());
						totalByHyrachi
								.setGrandTotalHyrachi2(totalByHyrachi.getGrandTotalHyrachi2() + dep.getTotalMonth2());
						totalByHyrachi.setGrandTotalDifferentHyrachi(
								totalByHyrachi.getGrandTotalDifferentHyrachi() + dep.getTotalDifferent());
					}
				});
			}
			lstTotalByHyrachi.add(totalByHyrachi);

		});
		return lstTotalByHyrachi;
	}

	private List<DepartmentHyrachi> caculateParent(ComparingSalaryBonusQuery comparingQuery, String companyCode,
			List<DeparmentInf> lstDepartInf) {
		List<DepartmentDto> lstDepcodeMonth1 = this.compareSalaryBonusQueryRepo.getDepartmentCodeList(companyCode,
				comparingQuery.getEmployeeCodeList(), comparingQuery.getMonth1(), comparingQuery.getFormCode());
		List<DepartmentHyrachi> lstDepartmentHyrachi = new ArrayList<>();
		List<DepartmentDto> departmentParents = lstDepcodeMonth1.stream().filter(dm -> dm.getHyrachi().length() == 3)
				.collect(Collectors.toList());

		departmentParents.stream().forEach(c -> {
			List<DeparmentInf> lstDepartFilter = lstDepartInf.stream()
					.filter(s -> (s.getHyrachi().substring(0, 3)).equals(c.getHyrachi())).collect(Collectors.toList());
			DepartmentHyrachi department = new DepartmentHyrachi();
			department.setDepcode(c.getDepCode());
			department.setHyrachiParent(c.getHyrachi());
			department.setNumberOfChild(lstDepartFilter.size());
			lstDepartmentHyrachi.add(department);
		});
		return lstDepartmentHyrachi;
	}

}
