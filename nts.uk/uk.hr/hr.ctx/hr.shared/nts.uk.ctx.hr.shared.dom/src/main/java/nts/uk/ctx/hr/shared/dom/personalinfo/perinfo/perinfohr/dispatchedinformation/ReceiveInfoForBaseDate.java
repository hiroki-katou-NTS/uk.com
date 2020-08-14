package nts.uk.ctx.hr.shared.dom.personalinfo.perinfo.perinfohr.dispatchedinformation;

import java.util.ArrayList;
import java.util.List;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.hr.shared.dom.personalinfo.perinfo.PersonalInformation;

/**
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.人事.shared.個人情報（人事）.個人情報.アルゴリズム.出向派遣履歴  (History phái cử đi công tác).基準日から受入出向派遣情報を取得する  (Get thông tin tiếp nhận phái cử đi công tác từ base date).基準日から受入出向派遣情報を取得する
 * @author chungnt
 *
 */

public class ReceiveInfoForBaseDate {

	/**
	 * 
	 * @param require
	 * @param contractCode
	 * @param cId
	 * @param baseDate
	 * @param getEmployee				*1
	 * @param getEmployeeName			*2
	 * @param getExpirationDate			*3
	 * @param GetNameSelectedMaster		*4
	 * @param getClassification			*5
	 * @param getDispatchedCategory		*6
	 * @param getAcquire				*7
	 * @param getCompanyName			*8
	 * @param getAddress				*9
	 * @param getAddressKana			*10
	 * @param getInclude
	 * @param emplyeeIds
	 * @return
	 */
	public static List<TemporaryDispatchInformation> get(Require require, String contractCode, String cId,
			GeneralDate baseDate, boolean getEmployeeCode, boolean getEmployeeName, boolean getExpirationDate,
			boolean getNameSelectedMaster, boolean getClassification, boolean getDispatchedCategory, boolean getAcquire,
			boolean getCompanyName, boolean getAddress, boolean getAddressKana, boolean getInclude, List<String> emplyeeIds) {
		
		
		List<PersonalInformation> informations = require.getDispatchedInfoByStr10s(contractCode, cId, baseDate);
		List<TemporaryDispatchInformation> dispatchInformations = new ArrayList<>();
		boolean check = false; 
		
		
		if (emplyeeIds.isEmpty()) {

			for (PersonalInformation p : informations) {
				TemporaryDispatchInformation t = new TemporaryDispatchInformation();
				t.setTemporaryDispatcher(true);
				t.setEmployeeId(p.getStr13().map(m -> m).orElse(""));

				if (getEmployeeCode) {
					t.setEmployeeCode(p.getStr14().map(m -> m).orElse(""));
				}

				if (getEmployeeName) {
					t.setEmployeeName(p.getPersonName().map(m -> m).orElse(""));
				}

				if (getExpirationDate) {
					t.setExpirationDate(p.getDate01().map(m -> m).orElse(null));
				}

				if (getClassification) {
					t.setClassify(Integer.parseInt(p.getSelectCode01().map(m -> m).orElse("")));
				}

				if (getClassification && getNameSelectedMaster) {
					t.setNameClassify(p.getSelectName01().map(m -> m).orElse(""));
				}

				if (getDispatchedCategory) {
					t.setTemporaryDispatch(p.getSelectCode02().map(m -> m).orElse(""));
				}

				if (getDispatchedCategory && getNameSelectedMaster) {
					t.setTemporaryDispatchCategory(p.getSelectName02().map(m -> m).orElse(""));
				}

				if (getAcquire) {
					t.setTemporaryAssignment(p.getSelectCode03().map(m -> m).orElse(""));
				}

				if (getAcquire && getNameSelectedMaster) {
					t.setTemporaryAssignmentCategory(p.getSelectCode03().map(m -> m).orElse(""));
				}

				// QA *8
//					if (getCompanyName) {
//						t.set
//					}

				if (getAddress) {
					t.setCountry(p.getSelectName04().map(m -> m).orElse(""));
					t.setPostalCode(p.getStr04().map(m -> m).orElse(""));
					t.setPrefectures(p.getSelectName05().map(m -> m).orElse(""));
					t.setAddress1(p.getStr05().map(m -> m).orElse(""));
					t.setAddress2(p.getStr06().map(m -> m).orElse(""));
					t.setPhoneNumber(p.getStr09().map(m -> m).orElse(""));
				}

				if (getAddressKana) {
					t.setKanaAddress1(p.getStr07().map(m -> m).orElse(""));
					t.setKanaAddress2(p.getStr08().map(m -> m).orElse(""));
				}

				dispatchInformations.add(t);
			}

			return dispatchInformations;
		}
		
		for (PersonalInformation p : informations) {
			for (String employeeId: emplyeeIds) {
				if(employeeId.equals(p.getStr13().map(m -> m).orElse(""))) {
					check = true;
					TemporaryDispatchInformation t = new TemporaryDispatchInformation();
					t.setTemporaryDispatcher(check);
					t.setEmployeeId(employeeId);
					
					if (getEmployeeCode) {
						t.setEmployeeCode(p.getStr14().map(m ->m).orElse(""));
					}
					
					if (getEmployeeName) {
						t.setEmployeeName(p.getPersonName().map(m -> m).orElse(""));
					}
					
					if (getExpirationDate) {
						t.setExpirationDate(p.getDate01().map(m -> m).orElse(null));
					}
					
					if (getClassification) {
						t.setClassify(Integer.parseInt(p.getSelectCode01().map(m -> m).orElse("")));
					}
					
					if (getClassification && getNameSelectedMaster) {
						t.setNameClassify(p.getSelectName01().map(m -> m).orElse(""));
					}
					
					if (getDispatchedCategory) {
						t.setTemporaryDispatch(p.getSelectCode02().map(m -> m).orElse(""));
					}
					
					if (getDispatchedCategory && getNameSelectedMaster) {
						t.setTemporaryDispatchCategory(p.getSelectName02().map(m -> m).orElse(""));
					}
					
					if (getAcquire) {
						t.setTemporaryAssignment(p.getSelectCode03().map(m -> m).orElse(""));
					}
					
					if(getAcquire && getNameSelectedMaster) {
						t.setTemporaryAssignmentCategory(p.getSelectCode03().map(m -> m).orElse(""));
					}
					
					// QA *8
//					if (getCompanyName) {
//						t.set
//					}
					
					if (getAddress) {
						t.setCountry(p.getSelectName04().map(m -> m).orElse(""));
						t.setPostalCode(p.getStr04().map(m -> m).orElse(""));
						t.setPrefectures(p.getSelectName05().map(m -> m).orElse(""));
						t.setAddress1(p.getStr05().map(m -> m).orElse(""));
						t.setAddress2(p.getStr06().map(m -> m).orElse(""));
						t.setPhoneNumber(p.getStr09().map(m -> m).orElse(""));
					}
					
					if (getAddressKana) {
						t.setKanaAddress1(p.getStr07().map(m -> m).orElse(""));
						t.setKanaAddress2(p.getStr08().map(m -> m).orElse(""));
					}
					
					dispatchInformations.add(t);
				}
			}
			if(!check) {
				TemporaryDispatchInformation t = new TemporaryDispatchInformation();
				t.setTemporaryDispatcher(check);
				dispatchInformations.add(t);
			}else {
				check = false;
			}
		}
		
		return dispatchInformations;
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
		List<PersonalInformation> getDispatchedInfoByStr10s(String contractCd, String cId,
				GeneralDate baseDate);
	}
}