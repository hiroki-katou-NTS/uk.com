package nts.uk.ctx.hr.shared.dom.personalinfo.perinfo.perinfohr.dispatchedinformation;

import java.util.ArrayList;
import java.util.List;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.hr.shared.dom.personalinfo.perinfo.PersonalInformation;

/**
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.人事.shared.個人情報（人事）.個人情報.アルゴリズム.出向派遣履歴
 * (History phái cử đi công tác).基準日から受入出向派遣情報を取得する (Get thông tin tiếp nhận
 * phái cử đi công tác từ base date).基準日から受入出向派遣情報を取得する
 * 
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
	 * @param getEmployee        *1
	 * @param employeeName       *2
	 * @param expirationDate     *3
	 * @param nameSelectedMaster *4
	 * @param classification     *5
	 * @param dispatchedCategory *6
	 * @param acquire            *7
	 * @param companyName        *8
	 * @param address            *9
	 * @param addressKana        *10
	 * @param include
	 * @param emplyeeIds
	 * @return
	 */
	public static List<TemporaryDispatchInformation> get(Require require, String contractCode, String cId,
			GeneralDate baseDate, boolean employeeCode, boolean employeeName, boolean expirationDate,
			boolean nameSelectedMaster, boolean classification, boolean dispatchedCategory, boolean acquire,
			boolean companyName, boolean address, boolean addressKana, boolean include, List<String> emplyeeIds) {

		List<PersonalInformation> informations = require.getDispatchedInfoByStr10s(contractCode, cId, baseDate,
				include);
		List<TemporaryDispatchInformation> dispatchInformations = new ArrayList<>();
		boolean check = false;

		if (!emplyeeIds.isEmpty()) {
			for (int i = 0; i < emplyeeIds.size(); i++) {
				if (!informations.isEmpty()) {
					for (int j = 0; j < informations.size(); j++) {
						if (informations.get(j).getStr13() != null) {
							if (informations.get(j).getStr13().isPresent()) {
								if (emplyeeIds.get(i).equals(informations.get(j).getStr13().get())) {
									check = true;
									TemporaryDispatchInformation t = new TemporaryDispatchInformation();
									PersonalInformation p = informations.get(j);

									t.setTemporaryDispatcher(check);

									if (p.getStr13() != null) {
										t.setEmployeeId(p.getStr13().map(m -> m).orElse(""));
									}

									if (p.getStr14() != null) {
										if (employeeCode) {
											t.setEmployeeCode(p.getStr14().map(m -> m).orElse(""));
										}
									}

									if (p.getPersonName() != null) {
										if (employeeName) {
											t.setEmployeeName(p.getPersonName().map(m -> m).orElse(""));
										}
									}

									if (p.getDate01() != null) {
										if (expirationDate) {
											t.setExpirationDate(p.getDate01().map(m -> m).orElse(null));
										}
									}

									if (p.getSelectCode01() != null) {
										if (classification) {
											t.setClassify(Integer.parseInt(p.getSelectCode01().map(m -> m).orElse("")));
										}
									}

									if (p.getSelectName01() != null) {
										if (classification && nameSelectedMaster) {
											t.setNameClassify(p.getSelectName01().map(m -> m).orElse(""));
										}
									}

									if (p.getSelectCode02() != null) {
										if (dispatchedCategory) {
											t.setTemporaryDispatch(p.getSelectCode02().map(m -> m).orElse(""));
										}
									}

									if (p.getSelectName02() != null) {
										if (dispatchedCategory && nameSelectedMaster) {
											t.setTemporaryDispatchCategory(p.getSelectName02().map(m -> m).orElse(""));
										}
									}

									if (p.getSelectCode03() != null) {
										if (acquire) {
											t.setTemporaryAssignment(p.getSelectCode03().map(m -> m).orElse(""));
										}
									}

									if (p.getSelectName03() != null) {
										if (acquire && nameSelectedMaster) {
											t.setTemporaryAssignmentCategory(
													p.getSelectName03().map(m -> m).orElse(""));
										}
									}

									if (p.getStr03() != null) {
										if (companyName) {
											t.setSecondedCompanyName(p.getStr03().map(m -> m).orElse(""));
										}
									}

									if (address) {
										if (p.getSelectName04() != null) {
											t.setCountry(p.getSelectName04().map(m -> m).orElse(""));
										}

										if (p.getStr04() != null) {
											t.setPostalCode(p.getStr04().map(m -> m).orElse(""));
										}

										if (p.getSelectName05() != null) {
											t.setPrefectures(p.getSelectName05().map(m -> m).orElse(""));
										}

										if (p.getStr05() != null) {
											t.setAddress1(p.getStr05().map(m -> m).orElse(""));
										}

										if (p.getStr06() != null) {
											t.setAddress2(p.getStr06().map(m -> m).orElse(""));
										}

										if (p.getStr09() != null) {
											t.setPhoneNumber(p.getStr09().map(m -> m).orElse(""));
										}
									}

									if (addressKana) {

										if (p.getStr07() != null) {
											t.setKanaAddress1(p.getStr07().map(m -> m).orElse(""));
										}

										if (p.getStr08() != null) {
											t.setKanaAddress2(p.getStr08().map(m -> m).orElse(""));
										}
									}

									dispatchInformations.add(t);
								}
							}
						}

						if (j == informations.size() - 1) {
							if (!check) {
								check = false;
								TemporaryDispatchInformation t = new TemporaryDispatchInformation();

								t.setTemporaryDispatcher(check);
								t.setEmployeeId(emplyeeIds.get(i));

								dispatchInformations.add(t);
							} else {
								check = false;
							}
						}
					}
				} else {
					check = false;
					TemporaryDispatchInformation t = new TemporaryDispatchInformation();

					t.setTemporaryDispatcher(check);
					t.setEmployeeId(emplyeeIds.get(i));

					dispatchInformations.add(t);

				}
			}
		} else {
			for (PersonalInformation p : informations) {
				TemporaryDispatchInformation t = new TemporaryDispatchInformation();
				t.setTemporaryDispatcher(true);

				if (p.getStr13() != null) {
					t.setEmployeeId(p.getStr13().map(m -> m).orElse(""));
				}

				if (p.getStr14() != null) {
					if (employeeCode) {
						t.setEmployeeCode(p.getStr14().map(m -> m).orElse(""));
					}
				}

				if (p.getPersonName() != null) {
					if (employeeName) {
						t.setEmployeeName(p.getPersonName().map(m -> m).orElse(""));
					}
				}

				if (p.getDate01() != null) {
					if (expirationDate) {
						t.setExpirationDate(p.getDate01().map(m -> m).orElse(null));
					}
				}

				if (p.getSelectCode01() != null) {
					if (classification) {
						t.setClassify(Integer.parseInt(p.getSelectCode01().map(m -> m).orElse("")));
					}
				}

				if (p.getSelectName01() != null) {
					if (classification && nameSelectedMaster) {
						t.setNameClassify(p.getSelectName01().map(m -> m).orElse(""));
					}
				}

				if (p.getSelectCode02() != null) {
					if (dispatchedCategory) {
						t.setTemporaryDispatch(p.getSelectCode02().map(m -> m).orElse(""));
					}
				}

				if (p.getSelectName02() != null) {
					if (dispatchedCategory && nameSelectedMaster) {
						t.setTemporaryDispatchCategory(p.getSelectName02().map(m -> m).orElse(""));
					}
				}

				if (p.getSelectCode03() != null) {
					if (acquire) {
						t.setTemporaryAssignment(p.getSelectCode03().map(m -> m).orElse(""));
					}
				}

				if (p.getSelectName03() != null) {
					if (acquire && nameSelectedMaster) {
						t.setTemporaryAssignmentCategory(
								p.getSelectName03().map(m -> m).orElse(""));
					}
				}

				if (p.getStr03() != null) {
					if (companyName) {
						t.setSecondedCompanyName(p.getStr03().map(m -> m).orElse(""));
					}
				}

				if (address) {
					if (p.getSelectName04() != null) {
						t.setCountry(p.getSelectName04().map(m -> m).orElse(""));
					}

					if (p.getStr04() != null) {
						t.setPostalCode(p.getStr04().map(m -> m).orElse(""));
					}

					if (p.getSelectName05() != null) {
						t.setPrefectures(p.getSelectName05().map(m -> m).orElse(""));
					}

					if (p.getStr05() != null) {
						t.setAddress1(p.getStr05().map(m -> m).orElse(""));
					}

					if (p.getStr06() != null) {
						t.setAddress2(p.getStr06().map(m -> m).orElse(""));
					}

					if (p.getStr09() != null) {
						t.setPhoneNumber(p.getStr09().map(m -> m).orElse(""));
					}
				}

				if (addressKana) {

					if (p.getStr07() != null) {
						t.setKanaAddress1(p.getStr07().map(m -> m).orElse(""));
					}

					if (p.getStr08() != null) {
						t.setKanaAddress2(p.getStr08().map(m -> m).orElse(""));
					}
				}

				dispatchInformations.add(t);
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
		List<PersonalInformation> getDispatchedInfoByStr10s(String contractCd, String cId, GeneralDate baseDate,
				boolean include);
	}
}