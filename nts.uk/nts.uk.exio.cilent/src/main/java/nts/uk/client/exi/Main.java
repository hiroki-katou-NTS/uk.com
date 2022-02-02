package nts.uk.client.exi;

public class Main {
	public static void main(String[] args) {
		String filePath = "";
		if(args.length > 0){
			filePath = args[0];
		}
		ExcecuteImportService service = new ExcecuteImportService(filePath);
		service.doWork();
	}
}
