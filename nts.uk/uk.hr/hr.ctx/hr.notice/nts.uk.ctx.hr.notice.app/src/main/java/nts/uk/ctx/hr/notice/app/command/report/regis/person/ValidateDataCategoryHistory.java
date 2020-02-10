/**
 * 
 */
package nts.uk.ctx.hr.notice.app.command.report.regis.person;

import java.util.Optional;

import nts.uk.shr.pereg.app.ItemValue;
import nts.uk.shr.pereg.app.command.ItemsByCategory;

/**
 * @author laitv
 *
 */
public class ValidateDataCategoryHistory {
	
	public static final String MAX_DATE = "9999/12/31";
	public static final String MIN_DATE = "1900/01/01";
	
	public static void validate(SaveReportInputContainer command) {
		for (int i = 0; i < command.inputs.size(); i++) {
			ItemsByCategory itemsByCategory = command.inputs.get(i);
			
			// category 所属会社履歴  AffCompanyHist
			if (itemsByCategory.getCategoryId().endsWith("CS00003")) {
				Optional<ItemValue> itStartDate = command.getInputs().get(i).getItems().stream().filter(it -> it.itemCode().equals("IS00020")).findFirst();
				Optional<ItemValue> itEndDate = command.getInputs().get(i).getItems().stream().filter(it -> it.itemCode().equals("IS00021")).findFirst();

				if (itStartDate.isPresent()) {
					if (itStartDate.get().value() == null) {
						command.getInputs().get(i).getItems().stream().filter(it -> it.itemCode().equals("IS00020")).findFirst().get().setValue(MIN_DATE);
					}
				}

				if (itEndDate.isPresent()) {
					if (itEndDate.get().value() == null) {
						command.getInputs().get(i).getItems().stream().filter(it -> it.itemCode().equals("IS00021")).findFirst().get().setValue(null);
					}
				}
			}

			// category 部門本務  AffDepartmentHistory 
			if (itemsByCategory.getCategoryId().endsWith("CS00015")) {
				Optional<ItemValue> itStartDate = command.getInputs().get(i).getItems().stream().filter(it -> it.itemCode().equals("IS00071")).findFirst();
				Optional<ItemValue> itEndDate = command.getInputs().get(i).getItems().stream().filter(it -> it.itemCode().equals("IS00072")).findFirst();

				if (itStartDate.isPresent()) {
					if (itStartDate.get().value() == null) {
						command.getInputs().get(i).getItems().stream().filter(it -> it.itemCode().equals("IS00071")).findFirst().get().setValue(MIN_DATE);
					}
				}

				if (itEndDate.isPresent()) {
					if (itEndDate.get().value() == null) {
						command.getInputs().get(i).getItems().stream().filter(it -> it.itemCode().equals("IS00072")).findFirst().get().setValue(MAX_DATE);
					}
				}
			}
			
			//CS00004	分類１ AffClassification
			if (itemsByCategory.getCategoryId().endsWith("CS00004")) {
				Optional<ItemValue> itStartDate = command.getInputs().get(i).getItems().stream().filter(it -> it.itemCode().equals("IS00026")).findFirst();
				Optional<ItemValue> itEndDate = command.getInputs().get(i).getItems().stream().filter(it -> it.itemCode().equals("IS00027")).findFirst();

				if (itStartDate.isPresent()) {
					if (itStartDate.get().value() == null) {
						command.getInputs().get(i).getItems().stream().filter(it -> it.itemCode().equals("IS00026")).findFirst().get().setValue(MIN_DATE);
					}
				}

				if (itEndDate.isPresent()) {
					if (itEndDate.get().value() == null) {
						command.getInputs().get(i).getItems().stream().filter(it -> it.itemCode().equals("IS00027")).findFirst().get().setValue(MAX_DATE);
					}
				}
			}

			//CS00014	雇用  EmploymentHistory
			if (itemsByCategory.getCategoryId().endsWith("CS00014")) {
				Optional<ItemValue> itStartDate = command.getInputs().get(i).getItems().stream().filter(it -> it.itemCode().equals("IS00066")).findFirst();
				Optional<ItemValue> itEndDate = command.getInputs().get(i).getItems().stream().filter(it -> it.itemCode().equals("IS00067")).findFirst();

				if (itStartDate.isPresent()) {
					if (itStartDate.get().value() == null) {
						command.getInputs().get(i).getItems().stream().filter(it -> it.itemCode().equals("IS00066")).findFirst().get().setValue(MIN_DATE);
					}
				}

				if (itEndDate.isPresent()) {
					if (itEndDate.get().value() == null) {
						command.getInputs().get(i).getItems().stream().filter(it -> it.itemCode().equals("IS00067")).findFirst().get().setValue(MAX_DATE);
					}
				}
			}
			
			//CS00016	職位本務  AffJobTitleMain
			if (itemsByCategory.getCategoryId().endsWith("CS00016")) {
				Optional<ItemValue> itStartDate = command.getInputs().get(i).getItems().stream().filter(it -> it.itemCode().equals("IS00077")).findFirst();
				Optional<ItemValue> itEndDate = command.getInputs().get(i).getItems().stream().filter(it -> it.itemCode().equals("IS00078")).findFirst();

				if (itStartDate.isPresent()) {
					if (itStartDate.get().value() == null) {
						command.getInputs().get(i).getItems().stream().filter(it -> it.itemCode().equals("IS00077")).findFirst().get().setValue(MIN_DATE);
					}
				}

				if (itEndDate.isPresent()) {
					if (itEndDate.get().value() == null) {
						command.getInputs().get(i).getItems().stream().filter(it -> it.itemCode().equals("IS00078")).findFirst().get().setValue(MAX_DATE);
					}
				}
			}
			
			//CS00017	職場 AffWorkplaceHistory
			if (itemsByCategory.getCategoryId().endsWith("CS00017")) {
				Optional<ItemValue> itStartDate = command.getInputs().get(i).getItems().stream().filter(it -> it.itemCode().equals("IS00082")).findFirst();
				Optional<ItemValue> itEndDate = command.getInputs().get(i).getItems().stream().filter(it -> it.itemCode().equals("IS00083")).findFirst();

				if (itStartDate.isPresent()) {
					if (itStartDate.get().value() == null) {
						command.getInputs().get(i).getItems().stream().filter(it -> it.itemCode().equals("IS00082")).findFirst().get().setValue(MIN_DATE);
					}
				}

				if (itEndDate.isPresent()) {
					if (itEndDate.get().value() == null) {
						command.getInputs().get(i).getItems().stream().filter(it -> it.itemCode().equals("IS00083")).findFirst().get().setValue(MAX_DATE);
					}
				}
			}

			//CS00018	休職休業 TemporaryAbsence
			if (itemsByCategory.getCategoryId().endsWith("CS00018")) {
				Optional<ItemValue> itStartDate = command.getInputs().get(i).getItems().stream().filter(it -> it.itemCode().equals("IS00087")).findFirst();
				Optional<ItemValue> itEndDate = command.getInputs().get(i).getItems().stream().filter(it -> it.itemCode().equals("IS00088")).findFirst();

				if (itStartDate.isPresent()) {
					if (itStartDate.get().value() == null) {
						command.getInputs().get(i).getItems().stream().filter(it -> it.itemCode().equals("IS00087")).findFirst().get().setValue(MIN_DATE);
					}
				}

				if (itEndDate.isPresent()) {
					if (itEndDate.get().value() == null) {
						command.getInputs().get(i).getItems().stream().filter(it -> it.itemCode().equals("IS00088")).findFirst().get().setValue(MAX_DATE);
					}
				}
			}
			
			//CS00019	短時間勤務 ShortWorkTime
			if (itemsByCategory.getCategoryId().endsWith("CS00019")) {
				Optional<ItemValue> itStartDate = command.getInputs().get(i).getItems().stream().filter(it -> it.itemCode().equals("IS00102")).findFirst();
				Optional<ItemValue> itEndDate = command.getInputs().get(i).getItems().stream().filter(it -> it.itemCode().equals("IS00103")).findFirst();

				if (itStartDate.isPresent()) {
					if (itStartDate.get().value() == null) {
						command.getInputs().get(i).getItems().stream().filter(it -> it.itemCode().equals("IS00102")).findFirst().get().setValue(MIN_DATE);
					}
				}

				if (itEndDate.isPresent()) {
					if (itEndDate.get().value() == null) {
						command.getInputs().get(i).getItems().stream().filter(it -> it.itemCode().equals("IS00103")).findFirst().get().setValue(MAX_DATE);
					}
				}
			}

			//CS00020	労働条件 WorkingCondition
			if (itemsByCategory.getCategoryId().endsWith("CS00020")) {
				Optional<ItemValue> itEndDate = command.getInputs().get(i).getItems().stream().filter(it -> it.itemCode().equals("IS00120")).findFirst();

				if (itEndDate.isPresent()) {
					if (itEndDate.get().value() == null) {
						command.getInputs().get(i).getItems().stream().filter(it -> it.itemCode().equals("IS00120")).findFirst().get().setValue(MAX_DATE);
					}
				}
			}

			//CS00021	勤務種別
			if (itemsByCategory.getCategoryId().endsWith("CS00021")) {
				Optional<ItemValue> itStartDate = command.getInputs().get(i).getItems().stream().filter(it -> it.itemCode().equals("IS00255")).findFirst();
				Optional<ItemValue> itEndDate = command.getInputs().get(i).getItems().stream().filter(it -> it.itemCode().equals("IS00256")).findFirst();

				if (itStartDate.isPresent()) {
					if (itStartDate.get().value() == null) {
						command.getInputs().get(i).getItems().stream().filter(it -> it.itemCode().equals("IS00255")).findFirst().get().setValue(MIN_DATE);
					}
				}

				if (itEndDate.isPresent()) {
					if (itEndDate.get().value() == null) {
						command.getInputs().get(i).getItems().stream().filter(it -> it.itemCode().equals("IS00256")).findFirst().get().setValue(MAX_DATE);
					}
				}
			}
		}
	}
}
