package nts.uk.ctx.bs.person.pub.contact;

import java.util.List;

public interface PersonContactPub {
	
	/**
	 * 個人ID（List）から個人連絡先を取得
	 * RequestList 379
	 * @param personIds
	 * @return
	 */
	List<PersonContactObject> getList(List<String> personIds);

}
