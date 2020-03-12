package nts.uk.ctx.hr.develop.dom.sysoperationset.eventoperation;

import java.math.BigInteger;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.hr.develop.dom.humanresourcedevevent.AvailableEvent;
import nts.uk.ctx.hr.develop.dom.humanresourcedevevent.ProgramId;

/** メニュー管理 */
@AllArgsConstructor
@Getter
public class MenuOperation extends AggregateRoot{
	// プログラムID
	private ProgramId programId;
	// メニューを使用する
	private AvailableEvent useMenu;
	// 会社ID
	private String companyId;
	//承認機能を使用する
	private AvailableEvent useApproval;
	// 通知機能を使用する
	private AvailableEvent useNotice;
	
	// 下位序列承認無
	private boolean noRankOrder;
	// 会社コード
	private BigInteger ccd;
	
	public static MenuOperation createFromJavaType(String programId, int useMenu, String companyId, 
													int useApproval, int useNotice, int noRankOrder, BigInteger ccd) {
		return new MenuOperation(new ProgramId(programId),
				EnumAdaptor.valueOf(useMenu, AvailableEvent.class),
				companyId,
				EnumAdaptor.valueOf(useApproval, AvailableEvent.class),
				EnumAdaptor.valueOf(useNotice, AvailableEvent.class),
				noRankOrder == 1,
				ccd);
	}
}
