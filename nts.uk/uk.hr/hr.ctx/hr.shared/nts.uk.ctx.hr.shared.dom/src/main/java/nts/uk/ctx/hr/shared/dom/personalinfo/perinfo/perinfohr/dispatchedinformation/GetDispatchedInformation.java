package nts.uk.ctx.hr.shared.dom.personalinfo.perinfo.perinfohr.dispatchedinformation;

import java.util.ArrayList;
import java.util.List;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.hr.shared.dom.personalinfo.perinfo.PersonalInformation;

/**
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.人事.shared.個人情報（人事）.個人情報.アルゴリズム.出向派遣履歴
 * (History phái cử đi công tác).基準日から出向派遣情報を取得する (Get thông tin phái cử đi công
 * tác từ base date).基準日から出向派遣情報を取得する
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
		List<PersonalInformation> informations = require.getDispatchedInfos(contractCode, companyId, baseDate,
				include);

		boolean check = false;

		if (!employeeIds.isEmpty()) {
			for (int i = 0; i < employeeIds.size(); i++) {
				if (!informations.isEmpty()) {
					for (int j = 0; j < informations.size(); j++) {
						if (informations.get(j).getSid() != null) {
							if (informations.get(j).getSid().isPresent()) {
								if (employeeIds.get(i).equals(informations.get(j).getSid().get())) {
									check = true;
									TemporaryDispatchInformation t = new TemporaryDispatchInformation();
									PersonalInformation p = informations.get(j);

									t.setTemporaryDispatcher(check);

									if (p.getSid() != null) {
										t.setEmployeeId(p.getSid().map(m -> m).orElse(""));
									}

									if (employeeCode) {
										if (p.getScd() != null) {
											t.setEmployeeCode(p.getScd().map(m -> m).orElse(""));
										}
									}

									if (employeeName) {
										if (p.getPersonName() != null) {
											t.setEmployeeName(p.getPersonName().map(m -> m).orElse(""));
										}
									}

									if (expirationDate) {
										if (p.getDate01() != null) {
											t.setExpirationDate(p.getDate01().map(m -> m).orElse(null));
										}
									}

									if (classification1) {
										if (p.getSelectCode01() != null) {
											t.setClassify(Integer.parseInt(p.getSelectCode01().map(m -> m).orElse("")));
										}
									}

									if (classification1 && nameSelectedMaster) {
										if (p.getSelectName01() != null) {
											t.setNameClassify(p.getSelectName01().map(m -> m).orElse(""));
										}
									}

									if (classification2) {
										if (p.getSelectCode02() != null) {
											t.setTemporaryDispatch(p.getSelectCode02().map(m -> m).orElse(""));
										}
									}

									if (classification2 && nameSelectedMaster) {
										if (p.getSelectName02() != null) {
											t.setTemporaryDispatchCategory(p.getSelectName02().map(m -> m).orElse(""));
										}
									}

									if (classification3) {
										if (p.getSelectCode03() != null) {
											t.setTemporaryAssignment(p.getSelectCode03().map(m -> m).orElse(""));
										}
									}

									if (classification3 && nameSelectedMaster) {
										if (p.getSelectName03() != null) {
											t.setTemporaryAssignmentCategory(
													p.getSelectName03().map(m -> m).orElse(""));
										}
									}

									if (nameCompany) {
										if (p.getStr12() != null) {
											t.setSecondedCompanyName(p.getStr12().map(m -> m).orElse(""));
										}
									}

									if (address) {
										if (p.getSelectName06() != null) {
											t.setCountry(p.getSelectName06().map(m -> m).orElse(""));
										}

										if (p.getStr15() != null) {
											t.setPostalCode(p.getStr15().map(m -> m).orElse(""));
										}

										if (p.getSelectName07() != null) {
											t.setPrefectures(p.getSelectName07().map(m -> m).orElse(""));
										}

										if (p.getStr16() != null) {
											t.setAddress1(p.getStr16().map(m -> m).orElse(""));
										}

										if (p.getStr17() != null) {
											t.setAddress2(p.getStr17().map(m -> m).orElse(""));
										}

										if (p.getStr20() != null) {
											t.setPhoneNumber(p.getStr20().map(m -> m).orElse(""));
										}
									}

									if (addressKana) {
										if (p.getStr18() != null) {
											t.setKanaAddress1(p.getStr18().map(m -> m).orElse(""));
										}

										if (p.getStr19() != null) {
											t.setKanaAddress2(p.getStr19().map(m -> m).orElse(""));
										}
									}

									temporaryDispatchInformations.add(t);
								}
							}
						}

						if (j == informations.size() - 1) {
							if (!check) {
								check = false;
								TemporaryDispatchInformation t = new TemporaryDispatchInformation();

								t.setTemporaryDispatcher(check);
								t.setEmployeeId(employeeIds.get(i));

								temporaryDispatchInformations.add(t);
							} else {
								check = false;
							}
						}
					}
				} else {
					check = false;
					TemporaryDispatchInformation t = new TemporaryDispatchInformation();

					t.setTemporaryDispatcher(check);
					t.setEmployeeId(employeeIds.get(i));

					temporaryDispatchInformations.add(t);

				}
			}
		} else {
			for (PersonalInformation p : informations) {
				TemporaryDispatchInformation t = new TemporaryDispatchInformation();
				t.setTemporaryDispatcher(true);

				if (p.getSid() != null) {
					t.setEmployeeId(p.getSid().map(m -> m).orElse(""));
				}

				if (employeeCode) {
					if (p.getScd() != null) {
						t.setEmployeeCode(p.getScd().map(m -> m).orElse(""));
					}
				}

				if (employeeName) {
					if (p.getPersonName() != null) {
						t.setEmployeeName(p.getPersonName().map(m -> m).orElse(""));
					}
				}

				if (expirationDate) {
					if (p.getDate01() != null) {
						t.setExpirationDate(p.getDate01().map(m -> m).orElse(null));
					}
				}

				if (classification1) {
					if (p.getSelectCode01() != null) {
						t.setClassify(Integer.parseInt(p.getSelectCode01().map(m -> m).orElse("")));
					}
				}

				if (classification1 && nameSelectedMaster) {
					if (p.getSelectName01() != null) {
						t.setNameClassify(p.getSelectName01().map(m -> m).orElse(""));
					}
				}

				if (classification2) {
					if (p.getSelectCode02() != null) {
						t.setTemporaryDispatch(p.getSelectCode02().map(m -> m).orElse(""));
					}
				}

				if (classification2 && nameSelectedMaster) {
					if (p.getSelectName02() != null) {
						t.setTemporaryDispatchCategory(p.getSelectName02().map(m -> m).orElse(""));
					}
				}

				if (classification3) {
					if (p.getSelectCode03() != null) {
						t.setTemporaryAssignment(p.getSelectCode03().map(m -> m).orElse(""));
					}
				}

				if (classification3 && nameSelectedMaster) {
					if (p.getSelectName03() != null) {
						t.setTemporaryAssignmentCategory(p.getSelectName03().map(m -> m).orElse(""));
					}
				}

				if (nameCompany) {
					if (p.getStr12() != null) {
						t.setSecondedCompanyName(p.getStr12().map(m -> m).orElse(""));
					}
				}

				if (address) {
					if (p.getSelectName06() != null) {
						t.setCountry(p.getSelectName06().map(m -> m).orElse(""));
					}

					if (p.getStr15() != null) {
						t.setPostalCode(p.getStr15().map(m -> m).orElse(""));
					}

					if (p.getSelectName07() != null) {
						t.setPrefectures(p.getSelectName07().map(m -> m).orElse(""));
					}

					if (p.getStr16() != null) {
						t.setAddress1(p.getStr16().map(m -> m).orElse(""));
					}

					if (p.getStr17() != null) {
						t.setAddress2(p.getStr17().map(m -> m).orElse(""));
					}

					if (p.getStr20() != null) {
						t.setPhoneNumber(p.getStr20().map(m -> m).orElse(""));
					}
				}

				if (addressKana) {
					if (p.getStr18() != null) {
						t.setKanaAddress1(p.getStr18().map(m -> m).orElse(""));
					}

					if (p.getStr19() != null) {
						t.setKanaAddress2(p.getStr19().map(m -> m).orElse(""));
					}
				}

				temporaryDispatchInformations.add(t);
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
		List<PersonalInformation> getDispatchedInfos(String contractCd, String cId, GeneralDate baseDate,
				boolean include);
	}
}