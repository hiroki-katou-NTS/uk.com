package nts.uk.ctx.hr.shared.dom.personalinfo.perinfo.perinfohr.dispatchedinformation.get;

import java.util.ArrayList;
import java.util.List;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.hr.shared.dom.personalinfo.perinfo.PersonalInformation;
import nts.uk.ctx.hr.shared.dom.personalinfo.perinfo.perinfohr.dispatchedinformation.TemporaryDispatchInformation;

/**
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.人事.shared.個人情報（人事）.個人情報.アルゴリズム.出向派遣履歴
 * .基準日から受入出向派遣情報を取得する
 * 
 * @author chungnt
 *
 */

public class GetDispatchedInformation {

	/**
	 * 
	 * @param require
	 * @param contractCode
	 * @param companyId
	 * @param baseDate
	 * @param employeeCode       *1
	 * @param employeeName       *2
	 * @param expirationDate     *3
	 * @param nameSelectedMaster *4
	 * @param classification1    *5
	 * @param classification2    *6
	 * @param classification3    *7
	 * @param nameCompany        *8
	 * @param address            *9
	 * @param addressKana        *10
	 * @param include
	 * @param employeeIds
	 * @return
	 */
	public static List<TemporaryDispatchInformation> get(Require require, String contractCode, String companyId,
			GeneralDate baseDate, boolean employeeCode, boolean employeeName, boolean expirationDate,
			boolean nameSelectedMaster, boolean classification1, boolean classification2, boolean classification3,
			boolean nameCompany, boolean address, boolean addressKana, boolean include, List<String> employeeIds) {
		
		List<TemporaryDispatchInformation> temporaryDispatchInformations = new ArrayList<>();
		List<PersonalInformation> informations = require.getdDispatchedInformation(contractCode, companyId, 6, baseDate);
		List<PersonalInformation> informations1 = new ArrayList<>();
		boolean checkfinal = false;

		if (employeeIds.isEmpty()) {
			
			for (int i = 0 ; i < informations.size() ; i++) {
				if (informations.get(i).getSid().isPresent()) {
					informations1.add(informations.get(i));
					TemporaryDispatchInformation information = new TemporaryDispatchInformation(employeeIds.get(i));
					information.setTemporaryDispatcher(true);
					temporaryDispatchInformations.add(information);
				}
			}
			
			informations = informations1;
			
			return temporaryDispatchInformations;
		}

		for (int i = 0; i < employeeIds.size(); i++) {
			for (int j = 0; j < informations.size(); j++) {
				if (informations.get(j).getSid().equals(employeeIds.get(i))) {
					informations1.add(informations.get(j));
					TemporaryDispatchInformation information = new TemporaryDispatchInformation(employeeIds.get(i));
					information.setTemporaryDispatcher(true);
					temporaryDispatchInformations.add(information);
					checkfinal = true;
				}

				if (j == informations.size() - 1) {
					
					if (!checkfinal) {
						TemporaryDispatchInformation information = new TemporaryDispatchInformation(employeeIds.get(i));
						information.setTemporaryDispatcher(false);
					}
					checkfinal = false;
				}
			}
		}

		informations = informations1;

		if (employeeCode) {
			for (int i = 0; i < informations.size(); i++) {
				for (int j = 0 ; j < temporaryDispatchInformations.size() ; j++) {
					if (temporaryDispatchInformations.get(j).getEmployeeId().equals(informations.get(i).getSid())) {
						temporaryDispatchInformations.get(j).setEmployeeCode(informations.get(i).getScd().map(m -> m).orElse(""));
					}
				}
			}
		}
		
		if (employeeName) {
			for (int i = 0; i < informations.size(); i++) {
				for (int j = 0 ; j < temporaryDispatchInformations.size() ; j++) {
					if (temporaryDispatchInformations.get(j).getEmployeeId().equals(informations.get(i).getSid())) {
						temporaryDispatchInformations.get(j).setEmployeeName(informations.get(i).getPersonName().map(m -> m).orElse(""));
					}
				}
			}
		}
		
		if (expirationDate) {
			for (int i = 0; i < informations.size(); i++) {
				for (int j = 0 ; j < temporaryDispatchInformations.size() ; j++) {
					if (temporaryDispatchInformations.get(j).getEmployeeId().equals(informations.get(i).getSid())) {
						temporaryDispatchInformations.get(j).setExpirationDate(informations.get(i).getDate01().map(m -> m).orElse(null));
					}
				}
			}
		}
		
		if (classification1) {
			for (int i = 0; i < informations.size(); i++) {
				for (int j = 0 ; j < temporaryDispatchInformations.size() ; j++) {
					if (temporaryDispatchInformations.get(j).getEmployeeId().equals(informations.get(i).getSid())) {
						temporaryDispatchInformations.get(j).setClassify(Integer.parseInt(informations.get(i).getSelectCode01().map(m -> m).orElse("")));
					}
				}
			}
		}
		
		if(nameSelectedMaster && classification1) {
			for (int i = 0; i < informations.size(); i++) {
				for (int j = 0 ; j < temporaryDispatchInformations.size() ; j++) {
					if (temporaryDispatchInformations.get(j).getEmployeeId().equals(informations.get(i).getSid())) {
						temporaryDispatchInformations.get(j).setNameClassify(informations.get(i).getSelectName01().map(m -> m).orElse(""));
					}
				}
			}
		}
		
		if (classification2) {
			for (int i = 0; i < informations.size(); i++) {
				for (int j = 0 ; j < temporaryDispatchInformations.size() ; j++) {
					if (temporaryDispatchInformations.get(j).getEmployeeId().equals(informations.get(i).getSid())) {
						temporaryDispatchInformations.get(j).setTemporaryDispatch(informations.get(i).getSelectCode02().map(m -> m).orElse(""));
					}
				}
			}
		}
		
		if (classification2 && nameSelectedMaster) {
			for (int i = 0; i < informations.size(); i++) {
				for (int j = 0 ; j < temporaryDispatchInformations.size() ; j++) {
					if (temporaryDispatchInformations.get(j).getEmployeeId().equals(informations.get(i).getSid())) {
						temporaryDispatchInformations.get(j).setTemporaryDispatchCategory(informations.get(i).getSelectName02().map(m -> m).orElse(""));
					}
				}
			}
		}
		
		if (classification3) {
			for (int i = 0; i < informations.size(); i++) {
				for (int j = 0 ; j < temporaryDispatchInformations.size() ; j++) {
					if (temporaryDispatchInformations.get(j).getEmployeeId().equals(informations.get(i).getSid())) {
						temporaryDispatchInformations.get(j).setTemporaryAssignment(informations.get(i).getSelectCode03().map(m -> m).orElse(""));
					}
				}
			}	
		}
		
		if (classification3 && nameSelectedMaster) {
			for (int i = 0; i < informations.size(); i++) {
				for (int j = 0 ; j < temporaryDispatchInformations.size() ; j++) {
					if (temporaryDispatchInformations.get(j).getEmployeeId().equals(informations.get(i).getSid())) {
						temporaryDispatchInformations.get(j).setTemporaryAssignmentCategory(informations.get(i).getSelectName03().map(m -> m).orElse(""));
					}
				}
			}
		}
		
		// QA *8
		
		if (address) {
			for (int i = 0; i < informations.size(); i++) {
				for (int j = 0 ; j < temporaryDispatchInformations.size() ; j++) {
					if (temporaryDispatchInformations.get(j).getEmployeeId().equals(informations.get(i).getSid())) {
						TemporaryDispatchInformation t = temporaryDispatchInformations.get(j);
						t.setCountry(informations.get(i).getSelectName06().map(m -> m).orElse(""));
						t.setPostalCode(informations.get(i).getStr15().map(m -> m).orElse(""));
						t.setPrefectures(informations.get(i).getSelectName15().map(m -> m).orElse(""));
						t.setAddress1(informations.get(i).getStr16().map(m -> m).orElse(""));
						t.setAddress2(informations.get(i).getStr17().map(m -> m).orElse(""));
						t.setPhoneNumber(informations.get(i).getStr20().map(m -> m).orElse(""));
					}
				}
			}
		}
		
		if (addressKana) {
			for (int i = 0; i < informations.size(); i++) {
				for (int j = 0 ; j < temporaryDispatchInformations.size() ; j++) {
					if (temporaryDispatchInformations.get(j).getEmployeeId().equals(informations.get(i).getSid())) {
						TemporaryDispatchInformation t = temporaryDispatchInformations.get(j);
						t.setKanaAddress1(informations.get(i).getStr18().map(m -> m).orElse(""));
						t.setKanaAddress2(informations.get(i).getStr19().map(m -> m).orElse(""));
					}
				}
			}
		}
		
		return temporaryDispatchInformations;
	}

	public static interface Require {
		/**
		 * 
		 * @param contractCd
		 * @param cId
		 * @param workId
		 * @param baseDate
		 * @return
		 */
		List<PersonalInformation> getdDispatchedInformation(String contractCd, String cId, int workId,
				GeneralDate baseDate);
	}
}