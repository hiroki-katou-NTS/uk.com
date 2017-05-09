package nts.uk.ctx.sys.portal.app.find.toppage;

public class StringDto {
	
	/** The new layout id. */
	public String newLayoutId;
	
	public StringDto Reval(String val){
		 StringDto s = new StringDto();
		 s.newLayoutId =val;
		 return s;
	}
}
