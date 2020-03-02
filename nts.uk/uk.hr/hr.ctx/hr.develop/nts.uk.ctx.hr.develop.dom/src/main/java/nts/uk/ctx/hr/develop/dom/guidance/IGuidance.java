package nts.uk.ctx.hr.develop.dom.guidance;

/*操作ガイド*/
public interface IGuidance {
	
	/*操作ガイドの取得*/
	IGuidanceExportDto getGuidance(String companyId, String programId, String screenId);
}
