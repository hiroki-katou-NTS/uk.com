package nts.uk.ctx.pereg.app.command.process.checkdata;

import java.util.ArrayList;
import java.util.List;

import nts.uk.ctx.pereg.app.find.layoutdef.classification.LayoutPersonInfoClsDto;

public class CategoryData {
	
	public String employeeId;
	
	public String employeeCode; 
	
	public String bussinessName;
	
	public String clsCategoryCheck;

	private String personInfoCategoryID;

	private String personInfoCategoryCD;

	private int ctgType;
	
	private List<LayoutPersonInfoClsDto> classificationItems;
	
	public CategoryData(){
		this.classificationItems = new ArrayList<>();
	}
	
	public CategoryData(List<LayoutPersonInfoClsDto> classificationItems) {
		this.classificationItems = classificationItems;
	}
}
