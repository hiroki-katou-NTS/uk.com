package nts.uk.ctx.at.shared.dom.workrule.overtime;

/**
 * 法定内優先設定
 * @author keisuke_hoshina
 *
 */
public enum StatutoryPrioritySet {
	priorityNormalOverTimeWork,
	priorityEarlyOverTimeWork;
	
	/**
	 * 普通残業を優先するであるか判定する
	 * @return 普通残業を優先する
	 */
	public boolean isPriorityNormal() {
		return this.equals(priorityNormalOverTimeWork);
	}
}
