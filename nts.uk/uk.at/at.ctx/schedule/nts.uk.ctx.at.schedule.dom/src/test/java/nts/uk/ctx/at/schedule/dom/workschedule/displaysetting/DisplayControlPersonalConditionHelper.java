package nts.uk.ctx.at.schedule.dom.workschedule.displaysetting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import nts.arc.enums.EnumAdaptor;
import nts.uk.shr.com.enumcommon.NotUseAtr;
import nts.uk.ctx.at.schedule.dom.schedule.setting.displaycontrol.PersonSymbolQualify;

public class DisplayControlPersonalConditionHelper {
	
	

		
		
		public static List<QualificationCD> getLst(){
			String qualificationCD1 = "QualificationCD1";
			String qualificationCD2 = "QualificationCD2";
			String qualificationCD3 = "QualificationCD3";
			String qualificationCD4 = "QualificationCD4";
			String qualificationCD5 = "QualificationCD5";
			List<QualificationCD> lstQualificationCD = Arrays.asList(
					new QualificationCD(qualificationCD1),
					new QualificationCD(qualificationCD2),
					new QualificationCD(qualificationCD3),
					new QualificationCD(qualificationCD4),
					new QualificationCD(qualificationCD5));
			return lstQualificationCD;
			
		}
		public static WorkscheQualifi  optWorkscheQualifi = new WorkscheQualifi(new PersonSymbolQualify("01"), getLst());
		
		public static List<PersonInforDisplayControl> getListPersonInforDisplayControl(){
			List<PersonInforDisplayControl> result = Arrays.asList(
					new PersonInforDisplayControl(
							EnumAdaptor.valueOf(0, ConditionATRWorkSchedule.class),
							EnumAdaptor.valueOf(1, NotUseAtr.class)),
					new PersonInforDisplayControl(
							EnumAdaptor.valueOf(1, ConditionATRWorkSchedule.class),
							EnumAdaptor.valueOf(1, NotUseAtr.class)),
					new PersonInforDisplayControl(
							EnumAdaptor.valueOf(2, ConditionATRWorkSchedule.class),
							EnumAdaptor.valueOf(1, NotUseAtr.class)),
					new PersonInforDisplayControl(
							EnumAdaptor.valueOf(3, ConditionATRWorkSchedule.class),
							EnumAdaptor.valueOf(1, NotUseAtr.class)));
			return result;
		}
		
		public static List<PersonInforDisplayControl> getListPersonInforDisplayControlSucces(){
			List<PersonInforDisplayControl> result = Arrays.asList(
					new PersonInforDisplayControl(
							EnumAdaptor.valueOf(0, ConditionATRWorkSchedule.class),
							EnumAdaptor.valueOf(1, NotUseAtr.class)),
					new PersonInforDisplayControl(
							EnumAdaptor.valueOf(1, ConditionATRWorkSchedule.class),
							EnumAdaptor.valueOf(1, NotUseAtr.class)),
					new PersonInforDisplayControl(
							EnumAdaptor.valueOf(2, ConditionATRWorkSchedule.class),
							EnumAdaptor.valueOf(1, NotUseAtr.class)));
			return result;
		}
		public static DisplayControlPersonalCondition getData(){
			DisplayControlPersonalCondition data = new DisplayControlPersonalCondition(
					//companyID
					"00000000000000000000000000000000001",
					getListPersonInforDisplayControl(),
				Optional.of(optWorkscheQualifi));
			return data;
			
		}
		public static DisplayControlPersonalCondition getOptionalData(){
			DisplayControlPersonalCondition data = new DisplayControlPersonalCondition(
					//companyID
					"00000000000000000000000000000000001",
					getListPersonInforDisplayControl(),
				Optional.empty());
			return data;
			
		}
		
}
