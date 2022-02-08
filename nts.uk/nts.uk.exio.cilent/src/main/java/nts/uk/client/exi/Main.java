package nts.uk.client.exi;

public class Main {
	public static void main(String[] args) {
		String filePath = "";
		if(args.length > 0){
			filePath = args[0].trim();
		}
		if (filePath.isEmpty()) {
			LogManager.err("コマンドライン引数が指定されていません。取り込み対象リストファイルのパスを指定してください");
			return;
		}
		ExcecuteImportService service = new ExcecuteImportService(filePath);
		service.doWork();
	}
}
