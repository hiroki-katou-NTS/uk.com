package nts.uk.ctx.sys.portal.app.command.generalsearch;

import lombok.Setter;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.sys.portal.dom.generalsearch.GeneralSearchHistory;

@Setter
public class GeneralSearchHistoryCommand implements GeneralSearchHistory.MementoGetter {

	/** The user ID.
	 * ユーザID
	 **/
	private String userID;
	
	/** The company ID. 
	 * 会社ID
	 **/
	private String companyID;
	
	/** The search category. 
	 * 検索区分
	 **/
	private int searchCategory;
	
	/** The date. 
	 * 日時
	 **/
	private GeneralDateTime searchDate;
	
	/** The contents. 
	 * 内容
	 * 検索の内容
	 **/
	private String contents;

	public String getUserID() {
		return this.userID;
	}

	public String getCompanyID() {
		return this.companyID;
	}

	public int getSearchCategory() {
		return this.searchCategory;
	}

	public GeneralDateTime getSearchDate() {
		return this.searchDate;
	}

	public String getContents() {
		return this.contents;
	}
	
}
