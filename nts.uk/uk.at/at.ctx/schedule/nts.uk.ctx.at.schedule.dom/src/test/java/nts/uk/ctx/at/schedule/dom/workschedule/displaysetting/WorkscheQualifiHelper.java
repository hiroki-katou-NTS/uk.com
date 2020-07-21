package nts.uk.ctx.at.schedule.dom.workschedule.displaysetting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import nts.uk.ctx.at.schedule.dom.schedule.setting.displaycontrol.PersonSymbolQualify;

public class WorkscheQualifiHelper {

	public static WorkscheQualifi Dummy = new WorkscheQualifi(
			 new PersonSymbolQualify("QualificationMark"),
			new ArrayList<QualificationCD>(
					Arrays.asList(
							new QualificationCD("QualificationCD1"),
							new QualificationCD("QualificationCD2"),
							new QualificationCD("QualificationCD3"))));
	
	
	
	public static WorkscheQualifi getListEmpty(){
		return new WorkscheQualifi(new PersonSymbolQualify("QualificationMark"),Collections.emptyList());
	}
	
	public static WorkscheQualifi getListError(){
		return new WorkscheQualifi(
				new PersonSymbolQualify("QualificationMark"),
				new ArrayList<QualificationCD>(
						Arrays.asList(
								new QualificationCD("QualificationCD1"),
								new QualificationCD("QualificationCD2"),
								new QualificationCD("QualificationCD3"),
								new QualificationCD("QualificationCD4"),
								new QualificationCD("QualificationCD5"),
								new QualificationCD("QualificationCD6"))));
	}
}
