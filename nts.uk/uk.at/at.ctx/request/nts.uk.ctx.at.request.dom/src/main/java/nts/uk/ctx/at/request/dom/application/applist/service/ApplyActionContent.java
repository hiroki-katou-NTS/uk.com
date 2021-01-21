package nts.uk.ctx.at.request.dom.application.applist.service;

import lombok.AllArgsConstructor;

/**
 * refactor 4
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
public enum ApplyActionContent {
	/**
	 * 0: 確認する
	 */
	CONFIRM(0, "確認する"),
	/**
	 * 1: 確認しない
	 */
	NOT_CONFIRM(1, "確認しない"),
	
	/**
	 * 2: 全てはい
	 */
	ALL_YES(2, "全てはい"),
	
	/**
	 * 3: 全ていいえ
	 */
	ALL_NO(3, "全ていいえ");
	
	public int value;
	
	public String name;
}
