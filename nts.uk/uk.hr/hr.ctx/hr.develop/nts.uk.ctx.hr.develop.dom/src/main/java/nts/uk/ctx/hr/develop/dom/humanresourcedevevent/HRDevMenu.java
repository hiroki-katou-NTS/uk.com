package nts.uk.ctx.hr.develop.dom.humanresourcedevevent;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;

/** 人材育成メニュー */
@AllArgsConstructor
@Getter
public class HRDevMenu extends AggregateRoot{
	// イベントID
	private EventId eventId;
	// プログラムID
	private ProgramId programId;
	// プログラム名
	private ProgramName programName;
	// 利用できる
	private AvailableEvent availableMenu;
	//承認機能が利用できる
	private AvailableEvent availableApproval;
	// 表示順
	private int dispOrder;
	// 通知機能が利用できる
	private AvailableEvent availableNotice;
	/**
	 * create domain from java type
	 * @param eventId
	 * @param programId
	 * @param programName
	 * @param availableMenu
	 * @param availableApproval
	 * @param dispOrder
	 * @param availableNotice
	 * @return
	 */
	public static HRDevMenu createFromJavaType(int eventId, String programId, String programName, int availableMenu, int availableApproval, int dispOrder, int availableNotice) {
		return new HRDevMenu(EnumAdaptor.valueOf(eventId, EventId.class), 
				new ProgramId(programId), 
				new ProgramName(programName), 
				EnumAdaptor.valueOf(availableMenu, AvailableEvent.class), 
				EnumAdaptor.valueOf(availableApproval, AvailableEvent.class), 
				dispOrder, 
				EnumAdaptor.valueOf(availableNotice, AvailableEvent.class));
	}
}
