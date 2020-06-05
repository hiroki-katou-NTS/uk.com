package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.toppagealarm;

import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.persistence.internal.xr.ValueObject;

import lombok.Getter;
import nts.arc.time.GeneralDateTime;
import nts.uk.shr.com.context.AppContexts;

/**
 * トップページアラーム
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.システム.shared.トップページアラーム.トップページアラーム詳細
 * @author chungnt
 *
 */
@Getter
public class TopPageArm extends ValueObject {

	/**
	 * 	会社ID
	 */
	private final String cid;
	
	/**
	 *  実行完了日時
	 *   */
	private GeneralDateTime finishDateTime;
	
	/**
	 * 	エラーの有無
	 */
	private final ExistenceError error;
	
	/**
	 * 	中止フラグ
	 */
	private final IsCancelled isCancelled;
	
	/**
	 * 	既読状態
	 */
	private final List<ReadStatusManagementEmployee> lstManagementEmployee;
	
	/**
	 * [C-1] 新規作成する
	 * @param error
	 * @param lstsid
	 */
	public TopPageArm(ExistenceError error , List<String> lstsid) {
		super();
		
		this.cid = AppContexts.user().companyId();
		this.finishDateTime = GeneralDateTime.now();
		this.error = error;
		this.isCancelled = IsCancelled.NOT_CANCELLED;
		this.lstManagementEmployee = lstsid.stream().map(x -> new ReadStatusManagementEmployee(x)).collect(Collectors.toList());
	}
}
