package nts.uk.ctx.sys.assist.dom.storage;

import lombok.Getter;

@Getter
public enum SelectCategoryScreenMode {
	NEW(0),
	UPDATE(1);
	
	public final int value;

	private SelectCategoryScreenMode(int value) {
		this.value = value;
	}
}
