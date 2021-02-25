package nts.uk.ctx.at.record.dom.monthly.agreement.monthlyresult.specialprovision;

/**
 * ３６協定申請種類
 * @author quang.nh1
 */
public enum TypeAgreementApplication {
	/**
	 * 1ヶ月(one month)
	 */
	ONE_MONTH(0,"1ヶ月"),
	/**
	 * 1年間(one year)
	 */
	ONE_YEAR(1,"1年間");

	public int value;

	public String nameId;

	TypeAgreementApplication(int type, String nameId){
		this.value = type;
		this.nameId = nameId;
	}
}
