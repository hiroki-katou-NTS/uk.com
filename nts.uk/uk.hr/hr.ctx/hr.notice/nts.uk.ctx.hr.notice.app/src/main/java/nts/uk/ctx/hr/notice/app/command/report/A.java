package nts.uk.ctx.hr.notice.app.command.report;

public class A {
	private final static int ITEM_CODE_DEFAUT_NUMBER = 0;
	private final static String SPECIAL_ITEM_CODE = "SEPA";
	private static String createNewCode(String codeLastest, String strSpecial) {
		String numberCode = String.valueOf(ITEM_CODE_DEFAUT_NUMBER + 1);
		if (codeLastest != null) {
			numberCode = String.valueOf(Integer.parseInt(codeLastest.substring(2, 7)) + 1);
		}
		for (int i = 5; i > 0; i--) {
			if (i == numberCode.length()) {
				break;
			}
			strSpecial += "0";
		}
		return strSpecial + numberCode;
	}
	public static void main(String[] args) {
		String numberCode = String.valueOf(Integer.parseInt("SEPA001".substring(4, 7)) + 1);
		System.out.println(numberCode);
		String z = createNewCode("IO00001",SPECIAL_ITEM_CODE);
		System.out.println(z);
		

	}

}
