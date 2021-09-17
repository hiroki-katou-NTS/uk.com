package nts.uk.ctx.exio.dom.input.validation.condition.system.helper;

public enum TestEnum {
	/**	履歴を継続しなくてもよい */
	NO_HIS_CONTINUE(0),
	/**	履歴を継続しなくてはいけない */
	HIS_CONTINUE(1);
	
	public final Integer value;

	TestEnum(Integer value) {
		this.value = value;
	}
}
