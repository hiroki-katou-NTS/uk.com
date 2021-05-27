package nts.uk.ctx.exio.dom.input.validation.classtype;

public class ValidationEnum {
	public static void run(String fqn, Object value) {
		try {
			Class<?> pvClass = Class.forName(fqn);
			pvClass.getConstructors()[0].newInstance(value);
		} catch (Exception ex) {
			//エラー時処理
		}
	}
}
