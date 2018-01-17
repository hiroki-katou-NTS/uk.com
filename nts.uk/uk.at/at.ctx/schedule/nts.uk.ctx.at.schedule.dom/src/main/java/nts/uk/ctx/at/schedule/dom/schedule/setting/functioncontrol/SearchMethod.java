package nts.uk.ctx.at.schedule.dom.schedule.setting.functioncontrol;

import lombok.AllArgsConstructor;

/**
 * 就業時間帯の検索方法
 * 
 * @author TanLV
 *
 */
@AllArgsConstructor
public enum SearchMethod {
	/* リスト内から検索 */
	FROM_THE_LIST(0),
	/* 全件から検索 */
	FROM_ALL_ITEM(1);
	
	public final int value;
}
