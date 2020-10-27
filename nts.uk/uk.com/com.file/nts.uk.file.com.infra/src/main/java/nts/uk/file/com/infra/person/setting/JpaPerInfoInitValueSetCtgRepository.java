package nts.uk.file.com.infra.person.setting;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.Query;

import nts.arc.enums.EnumAdaptor;
import nts.arc.i18n.I18NText;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.file.com.app.person.setting.PerInfoInitValueSetCtgRepository;
import nts.uk.file.com.app.person.setting.PerInfoInitValueSetCtgUtils;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.infra.file.report.masterlist.data.ColumnTextAlign;
import nts.uk.shr.infra.file.report.masterlist.data.MasterCellData;
import nts.uk.shr.infra.file.report.masterlist.data.MasterCellStyle;
import nts.uk.shr.infra.file.report.masterlist.data.MasterData;

@Stateless
public class JpaPerInfoInitValueSetCtgRepository extends JpaRepository implements PerInfoInitValueSetCtgRepository {
	private static final String GET_EXPORT_DATA;
	  static {
	     StringBuilder exportSQL = new StringBuilder();
	     exportSQL.append("SELECT ");
	     exportSQL.append(" IIF (TABLE_RESULT.ROW_NUMBER = 1, TABLE_RESULT.PER_INIT_SET_CD, Null) INIT_CD,");
	     exportSQL.append(" IIF (TABLE_RESULT.ROW_NUMBER = 1, TABLE_RESULT.PER_INIT_SET_NAME, Null) INIT_NAME,");
	     exportSQL.append(" IIF (TABLE_RESULT.ROW_NUMBER2 = 1, TABLE_RESULT.CATEGORY_NAME, Null) CTG_NAME,");
	     exportSQL.append(" TABLE_RESULT.ITEM_NAME,");
	     exportSQL.append(" TABLE_RESULT.REF_METHOD_ATR,");
	     exportSQL.append(" CASE ");
	     exportSQL.append("	WHEN (TABLE_RESULT.SELECTION_ITEM_REF_CODE IS NULL) THEN ");
	     exportSQL.append("				CONCAT(TABLE_RESULT.STRING_VAL, TABLE_RESULT.INT_VALUE, TABLE_RESULT.DATE_VAL)");
	     exportSQL.append("	ELSE ");
	     exportSQL.append("			IIF((TABLE_RESULT.MASTER_VALUE IS NOT NULL AND RTRIM(TABLE_RESULT.MASTER_VALUE) <> '') OR TABLE_RESULT.EnumName IS NOT NULL,");
	     exportSQL.append("					CONCAT(TABLE_RESULT.MASTER_VALUE,TABLE_RESULT.EnumName), ");
	     exportSQL.append("					IIF(TABLE_RESULT.REF_METHOD_ATR_VAL = 2, ");
	     exportSQL.append("						IIF(TABLE_RESULT.SELECTION_ITEM_REF_CODE <> 'M00002' AND TABLE_RESULT.SELECTION_ITEM_REF_CODE <> 'M00005' ");
	     exportSQL.append("						AND TABLE_RESULT.SELECTION_ITEM_REF_CODE <> 'M00006' AND TABLE_RESULT.SELECTION_ITEM_REF_CODE <> 'M00016'");
	     exportSQL.append("						AND TABLE_RESULT.SELECTION_ITEM_REF_CODE <> 'M00017' AND TABLE_RESULT.SELECTION_ITEM_REF_TYPE <> 2");
	     exportSQL.append("						AND TABLE_RESULT.SELECTION_ITEM_REF_TYPE <> 3,");
	     exportSQL.append(" 						CONCAT(TABLE_RESULT.STRING_VAL, ?MasterUnregisted), ?MasterUnregisted), NULL))");
	     exportSQL.append(" END C_Value,");
	     exportSQL.append(" TABLE_RESULT.Align");
	     exportSQL.append(" FROM (");
	     exportSQL.append("  SELECT initset.PER_INIT_SET_CD, initset.PER_INIT_SET_NAME, ");
	     exportSQL.append("  ctg.CATEGORY_CD, ctg.CATEGORY_NAME, ");
	     exportSQL.append("  item.ITEM_CD, item.ITEM_NAME,");
	     exportSQL.append("  itemcm.SELECTION_ITEM_REF_CODE, ");
	     exportSQL.append("  itemcm.DATA_TYPE,");
	     exportSQL.append("  initsetitem.REF_METHOD_ATR as REF_METHOD_ATR_VAL,");
	     exportSQL.append("  itemcm.SELECTION_ITEM_REF_TYPE,");
	     exportSQL.append("  CASE initsetitem.REF_METHOD_ATR");
	     exportSQL.append("    WHEN 1 THEN ?Enum_ReferenceMethodType_NOSETTING");
	     exportSQL.append("    WHEN 2 THEN ?Enum_ReferenceMethodType_FIXEDVALUE");
	     exportSQL.append("    WHEN 3 THEN ?Enum_ReferenceMethodType_SAMEASLOGIN");
	     exportSQL.append("    WHEN 4 THEN ?Enum_ReferenceMethodType_SAMEASEMPLOYMENTDATE");
	     exportSQL.append("    WHEN 5 THEN ?Enum_ReferenceMethodType_SAMEASEMPLOYEECODE");
	     exportSQL.append("    WHEN 6 THEN ?Enum_ReferenceMethodType_SAMEASSYSTEMDATE");
	     exportSQL.append("    WHEN 7 THEN ?Enum_ReferenceMethodType_SAMEASNAME");
	     exportSQL.append("    WHEN 8 THEN ?Enum_ReferenceMethodType_SAMEASKANANAME");
	     exportSQL.append("    ELSE ?Enum_ReferenceMethodType_NOSETTING");
	     exportSQL.append("  END REF_METHOD_ATR,");
	     exportSQL.append(" CONCAT(");
	     // Workplace
	     exportSQL.append("  CONCAT(RTRIM(wkp.WKP_CD), wkp.WKP_NAME), ");
	     // Employment
	     exportSQL.append("  CONCAT(RTRIM(emp.CODE), emp.NAME), ");
	     // Classification
	     exportSQL.append("  CONCAT(RTRIM(cls.CLSCD), cls.CLSNAME), ");
	     // Job title
	     exportSQL.append("  CONCAT(IIF (job.END_DATE >= CONVERT(datetime, '9999-12-31'),RTRIM(job.JOB_CD),''),IIF (job.END_DATE >= CONVERT(datetime, '9999-12-31'), job.JOB_NAME, ");
	     exportSQL.append("      IIF (job.JOB_CD IS NOT NULL,?MasterUnregisted, NULL))),");
	     // TempAbsence
		 exportSQL.append("IIF (temp.USE_ATR = 1 ,temp.TEMP_ABSENCE_FR_NAME,  ");
		 exportSQL.append("			IIF(temp.TEMP_ABSENCE_FR_NO IS NOT NULL,'MasterUnregisted',NULL)) ,");
		 // BussType
	     exportSQL.append("  CONCAT(RTRIM(busstype.BUSINESS_TYPE_CD),busstype.BUSINESS_TYPE_NAME),");
	     // WorkType
	     exportSQL.append("  CONCAT(RTRIM(wrktype.CD),IIF(wrktype.ABOLISH_ATR = 0, wrktype.NAME, ");
	     exportSQL.append("				IIF(wrktype.CD IS NOT NULL, ?MasterUnregisted, NULL))),");
	     // WorkTime
	     exportSQL.append("  CONCAT(RTRIM(wrktime.WORKTIME_CD),IIF (wrktime.ABOLITION_ATR = 1,wrktime.NAME,");
	     exportSQL.append("				IIF(wrktime.WORKTIME_CD IS NOT NULL, ?MasterUnregisted, NULL))),");
	     // WorkType
	     exportSQL.append("  CONCAT(RTRIM(wrktype2.CD), IIF(wrktype2.ABOLISH_ATR = 0");
	     exportSQL.append("				  AND((wrktype2.WORK_ATR = 0 AND wrktype2.ONE_DAY_CLS = 0)");
	     exportSQL.append("				  OR (wrktype2.WORK_ATR = 0 AND wrktype2.ONE_DAY_CLS = 7)");
	     exportSQL.append("				  OR (wrktype2.WORK_ATR = 0 AND wrktype2.ONE_DAY_CLS = 10)),wrktype2.NAME, ");
	     exportSQL.append("					IIF(wrktype2.CD IS NOT NULL, ?MasterUnregisted,NULL))),");
	     // WorkType
	     exportSQL.append("CONCAT(RTRIM(wrktype3.CD), IIF(wrktype3.ABOLISH_ATR = 0");
	     exportSQL.append("		  AND	(wrktype3.WORK_ATR = 0 AND wrktype3.ONE_DAY_CLS = 1) ,wrktype3.NAME, ");
	     exportSQL.append("				IIF(wrktype3.CD IS NOT NULL,?MasterUnregisted, NULL))),");
	     // WorkType
	     exportSQL.append("	CONCAT(RTRIM(wrktype4.CD),IIF(wrktype4.ABOLISH_ATR = 0");
		 exportSQL.append("				AND	((wrktype4.WORK_ATR = 0 AND wrktype4.ONE_DAY_CLS = 0)");
		 exportSQL.append("				OR (wrktype4.WORK_ATR = 0 AND wrktype4.ONE_DAY_CLS = 7)");
		 exportSQL.append("				OR (wrktype4.WORK_ATR = 0 AND wrktype4.ONE_DAY_CLS = 11)), wrktype4.NAME, ");
		 exportSQL.append("				IIF(wrktype4.CD IS NOT NULL,?MasterUnregisted, NULL))),");
		 // WorkType
		 exportSQL.append("CONCAT(RTRIM(wrktype5.CD),IIF(wrktype5.ABOLISH_ATR = 0");
		 exportSQL.append("		  AND	(wrktype5.WORK_ATR = 0 AND wrktype5.ONE_DAY_CLS = 1), wrktype5.NAME,");
		 exportSQL.append("					IIF(wrktype5.CD IS NOT NULL, ?MasterUnregisted, NULL))),");
		 // Monthy pattern
	     exportSQL.append("  CONCAT(RTRIM(monthPattern.M_PATTERN_CD),monthPattern.M_PATTERN_NAME),");
	     // BOnus pay
	     exportSQL.append("  CONCAT(RTRIM(bonuspay.BONUS_PAY_SET_CD),bonuspay.BONUS_PAY_SET_NAME),");
	     // Special holiday table set
	     exportSQL.append("  hdtblset.YEAR_HD_NAME,");
	     // Grant day table code
	     exportSQL.append("  CONCAT(dateset.GRANT_NAME,dateset1.GRANT_NAME,dateset2.GRANT_NAME,dateset3.GRANT_NAME,dateset4.GRANT_NAME,");
	     exportSQL.append("  	dateset5.GRANT_NAME,dateset6.GRANT_NAME,dateset7.GRANT_NAME,dateset8.GRANT_NAME,dateset9.GRANT_NAME,");
	     exportSQL.append("  	dateset10.GRANT_NAME,dateset11.GRANT_NAME,dateset12.GRANT_NAME,dateset13.GRANT_NAME,dateset14.GRANT_NAME,");
	     exportSQL.append("  	dateset15.GRANT_NAME,dateset16.GRANT_NAME,dateset17.GRANT_NAME,dateset18.GRANT_NAME,dateset19.GRANT_NAME),");
	     //  -- Selection
	     exportSQL.append("  CONCAT(RTRIM(selection.SELECTION_CD), selection.SELECTION_NAME)) MASTER_VALUE,");
	     //  -- Code name
	     exportSQL.append("  CASE itemcm.SELECTION_ITEM_REF_CODE ");
	     // -- 性別 GenderPerson");
	     exportSQL.append("   WHEN 'E00001' THEN ");
	     exportSQL.append("    CASE initsetitem.STRING_VAL");
	     exportSQL.append("     WHEN '1' THEN ?Enum_GenderPerson_Male");
	     exportSQL.append("     WHEN '2' THEN ?Enum_GenderPerson_Female");
	     exportSQL.append("     ELSE NULL");
	     exportSQL.append("    END");
	     // -- 血液型 BloodType
	     exportSQL.append("   WHEN 'E00002' THEN");
	     exportSQL.append("    CASE initsetitem.STRING_VAL");
	     exportSQL.append("     WHEN '1' THEN ?Enum_BloodType_ARhPlus");
	     exportSQL.append("     WHEN '2' THEN ?Enum_BloodType_BRhPlus");
	     exportSQL.append("     WHEN '3' THEN ?Enum_BloodType_ORhPlus");
	     exportSQL.append("     WHEN '4' THEN ?Enum_BloodType_ABRhPlus");
	     exportSQL.append("     WHEN '5' THEN ?Enum_BloodType_ARhSub");
	     exportSQL.append("     WHEN '6' THEN ?Enum_BloodType_BRhSub");
	     exportSQL.append("     WHEN '7' THEN ?Enum_BloodType_ORhSub");
	     exportSQL.append("     WHEN '8' THEN ?Enum_BloodType_ABRhSub");
	     exportSQL.append("     ELSE NULL");
	     exportSQL.append("    END");
	     //   -- 給与区分 SalarySegment
	     exportSQL.append("   WHEN 'E00003' THEN");
	     exportSQL.append("    CASE initsetitem.STRING_VAL");
	     exportSQL.append("     WHEN '1' THEN ?Enum_SalarySegment_DailySalary");
	     exportSQL.append("     WHEN '2' THEN ?Enum_SalarySegment_DailyMonthlySalary");
	     exportSQL.append("     WHEN '3' THEN ?Enum_SalarySegment_HourlySalary");
	     exportSQL.append("     WHEN '4' THEN ?Enum_SalarySegment_MonthlySalary");
	     exportSQL.append("     ELSE NULL");
	     exportSQL.append("    END");
	     //  -- 育児介護区分 ChildCareAtr
	     exportSQL.append("   WHEN 'E00004' THEN");
	     exportSQL.append("    CASE initsetitem.STRING_VAL");
	     exportSQL.append("     WHEN '0' THEN ?Enum_ChildCareAtr_ChildCare");
	     exportSQL.append("     WHEN '1' THEN ?Enum_ChildCareAtr_Care");
	     exportSQL.append("     ELSE NULL");
	     exportSQL.append("    END");
	     // -- するしない区分 NotUseAtr
	     exportSQL.append("   WHEN 'E00005' THEN");
	     exportSQL.append("    CASE initsetitem.STRING_VAL");
	     exportSQL.append("     WHEN '0' THEN ?Enum_NotUseAtr_NOT_USE");
	     exportSQL.append("     WHEN '1' THEN ?Enum_NotUseAtr_USE");
	     exportSQL.append("     ELSE NULL");
	     exportSQL.append("    END");
		 // ManageAtr
	     exportSQL.append("   WHEN 'E00011' THEN");
	     exportSQL.append("    CASE initsetitem.STRING_VAL");
	     exportSQL.append("     WHEN '0' THEN ?Enum_ManageAtr_NotManage");
	     exportSQL.append("     WHEN '1' THEN ?Enum_ManageAtr_Manage");
	     exportSQL.append("     ELSE NULL");
	     exportSQL.append("    END");
	     // -- 労働制 WorkingSystem
	     exportSQL.append("   WHEN 'E00006' THEN");
	     exportSQL.append("    CASE initsetitem.STRING_VAL");
	     exportSQL.append("     WHEN '0' THEN ?Enum_WorkingSystem_REGULAR_WORK");
	     exportSQL.append("     WHEN '1' THEN ?Enum_WorkingSystem_FLEX_TIME_WORK");
	     exportSQL.append("     WHEN '2' THEN ?Enum_WorkingSystem_VARIABLE_WORKING_TIME_WORK");
	     exportSQL.append("     WHEN '3' THEN ?Enum_WorkingSystem_EXCLUDED_WORKING_CALCULATE");
	     exportSQL.append("     ELSE NULL");
	     exportSQL.append("    END");
	     // -- 勤務予定基本作成方法 WorkScheduleBasicCreMethod
	     exportSQL.append("   WHEN 'E00007' THEN");
	     exportSQL.append("    CASE initsetitem.STRING_VAL");
	     exportSQL.append("     WHEN '0' THEN ?CPS001_101");
	     exportSQL.append("     WHEN '1' THEN ?CPS001_102");
	     exportSQL.append("     WHEN '2' THEN ?CPS001_103");
	     exportSQL.append("     ELSE NULL");
	     exportSQL.append("    END");
	     // -- 勤務予定作成マスタ参照区分 WorkScheduleMasterReferenceAtr
	     exportSQL.append("   WHEN 'E00008' THEN");
	     exportSQL.append("    CASE initsetitem.STRING_VAL");
	     exportSQL.append("     WHEN '0' THEN ?CPS001_104");
	     exportSQL.append("     WHEN '1' THEN ?CPS001_105");
	     exportSQL.append("     ELSE NULL");
	     exportSQL.append("    END");
	     //-- 勤務予定の時間帯マスタ参照区分 TimeZoneScheduledMasterAtr
	     exportSQL.append("   WHEN 'E00009' THEN");
	     exportSQL.append("    CASE initsetitem.STRING_VAL");
	     exportSQL.append("     WHEN '0' THEN ?Enum_TimeZoneScheduledMasterAtr_FollowMasterReference");
	     exportSQL.append("     WHEN '1' THEN ?Enum_TimeZoneScheduledMasterAtr_PersonalWorkDaily");
	     exportSQL.append("     WHEN '2' THEN ?Enum_TimeZoneScheduledMasterAtr_PersonalDayOfWeek");
	     exportSQL.append("     ELSE NULL");
	     exportSQL.append("    END");
	     // -- 時給者区分 HourlyPaymentAtr
	     exportSQL.append("   WHEN 'E00010' THEN");
	     exportSQL.append("    CASE initsetitem.STRING_VAL");
	     exportSQL.append("     WHEN '0' THEN ?Enum_UseAtr_Hourly_Pay");
	     exportSQL.append("     WHEN '1' THEN ?Enum_UseAtr_Outside_Time_Pay");
	     exportSQL.append("     ELSE NULL");
	     exportSQL.append("    END");
	     // -- 特別休暇適用設定 SpecialLeaveAppSetting
	     exportSQL.append("   WHEN 'E00012' THEN");
	     exportSQL.append("    CASE initsetitem.STRING_VAL");
	     exportSQL.append("     WHEN '0' THEN ?Enum_SpecialLeaveAppSetting_PRESCRIBED");
	     exportSQL.append("     WHEN '1' THEN ?Enum_SpecialLeaveAppSetting_PERSONAL");
	     exportSQL.append("     ELSE NULL");
	     exportSQL.append("    END");
	     // -- 60H超過時の精算方法 PaymentMethod
	     exportSQL.append("   WHEN 'E00013' THEN");
	     exportSQL.append("    CASE initsetitem.STRING_VAL");
	     exportSQL.append("     WHEN '0' THEN ?Enum_PaymentMethod_VACATION_OCCURRED");
	     exportSQL.append("     WHEN '1' THEN ?Enum_PaymentMethod_AMOUNT_PAYMENT");
	     exportSQL.append("     ELSE NULL");
	     exportSQL.append("    END");
	     // -- 子の看護・介護休暇上限設定 UpperLimitSetting
	     exportSQL.append("   WHEN 'E00014' THEN");
	     exportSQL.append("    CASE initsetitem.STRING_VAL");
	     exportSQL.append("     WHEN '1' THEN ?Enum_UpperLimitSetting_FAMILY_INFO");
	     exportSQL.append("     WHEN '2' THEN ?Enum_UpperLimitSetting_PER_INFO_EVERY_YEAR");
	     exportSQL.append("     WHEN '3' THEN ?Enum_UpperLimitSetting_PER_INFO_FISCAL_YEAR");
	     exportSQL.append("     ELSE NULL");
	     exportSQL.append("    END");
	     // -- 休暇期限切れ状態 LeaveExpirationStatus
	     exportSQL.append("   WHEN 'E00015' THEN");
	     exportSQL.append("    CASE initsetitem.STRING_VAL");
	     exportSQL.append("     WHEN '0' THEN ?Enum_LeaveExpirationStatus_EXPIRED");
	     exportSQL.append("     WHEN '1' THEN ?Enum_LeaveExpirationStatus_AVAILABLE");
	     exportSQL.append("     ELSE NULL");
	     exportSQL.append("    END");

		 // -- 算定区分 CalculationAtr
		 exportSQL.append("   WHEN 'E00042' THEN");
		 exportSQL.append("    CASE initsetitem.STRING_VAL");
		 exportSQL.append("     WHEN '0' THEN ?Enum_CalculationAtr_SCHEDULED");
		 exportSQL.append("     WHEN '1' THEN ?Enum_CalculationAtr_CHANGE_OVER_TIME");
		 exportSQL.append("     WHEN '2' THEN ?Enum_CalculationAtr_CHANGE_BEFORE_AND_AFTER_CHILDBIRTH");
		 exportSQL.append("     WHEN '3' THEN ?Enum_CalculationAtr_CHANGE_AFTER_CHILDCARE_LEAVE");
		 exportSQL.append("     WHEN '4' THEN ?Enum_CalculationAtr_OBTAINING_QUALIFICATION");
		 exportSQL.append("     ELSE NULL");
		 exportSQL.append("    END");

	     exportSQL.append("   ELSE NULL");
	     exportSQL.append("  END EnumName,");
	     exportSQL.append("  CASE itemcm.DATA_TYPE");

	     // -- 時間 TIME
	     exportSQL.append("   WHEN 4 THEN");
	     exportSQL.append("    CASE ");
	     exportSQL.append("     WHEN initsetitem.INT_VAL IS NULL THEN NULL");
	     exportSQL.append("     ELSE");
	     exportSQL.append("      CONCAT(CONVERT(VARCHAR,CONVERT(INT,initsetitem.INT_VAL)/60),':',");
	     exportSQL.append("         RIGHT('0'+CONVERT(VARCHAR,CONVERT(INT,initsetitem.INT_VAL)%60),2))");
	     exportSQL.append("     END");
	     // -- 時刻 TIMEPOINT
	     exportSQL.append("   WHEN 5 THEN");
	     exportSQL.append("    CASE ");
	     exportSQL.append("    WHEN initsetitem.INT_VAL IS NULL THEN NULL");
	     exportSQL.append("    WHEN initsetitem.INT_VAL <0 THEN");
	     exportSQL.append("     CONCAT('前日',CONVERT(VARCHAR,ABS(CONVERT(INT,initsetitem.INT_VAL))/60),':',");
	     exportSQL.append("         RIGHT('0'+CONVERT(VARCHAR,ABS(CONVERT(INT,initsetitem.INT_VAL))%60),2))");
	     exportSQL.append("    WHEN initsetitem.INT_VAL >= 2880 THEN");
	     exportSQL.append("     CONCAT('翌々日',CONVERT(VARCHAR,CONVERT(INT,(initsetitem.INT_VAL-2880))/60),':',");
	     exportSQL.append("         RIGHT('0'+CONVERT(VARCHAR,CONVERT(INT,(initsetitem.INT_VAL-2880))%60),2))");
	     exportSQL.append("    WHEN initsetitem.INT_VAL >= 1440 THEN");
	     exportSQL.append("     CONCAT('翌日',CONVERT(VARCHAR,CONVERT(INT,(initsetitem.INT_VAL-1440))/60),':',");
	     exportSQL.append("         RIGHT('0'+CONVERT(VARCHAR,CONVERT(INT,(initsetitem.INT_VAL-1440))%60),2))");
	     exportSQL.append("    ELSE ");
	     exportSQL.append("     CONCAT('当日',CONVERT(VARCHAR,CONVERT(INT,initsetitem.INT_VAL)/60),':',");
	     exportSQL.append("         RIGHT('0'+CONVERT(VARCHAR,CONVERT(INT,initsetitem.INT_VAL)%60),2))");
	     exportSQL.append("    END");
	     exportSQL.append("   ELSE");
	     exportSQL.append("		CASE WHEN itemcm.NUMERIC_ITEM_DECIMAL_PART IS NOT NULL THEN");
		 exportSQL.append("			LTRIM(STR(initsetitem.INT_VAL, 30,CAST(itemcm.NUMERIC_ITEM_DECIMAL_PART as Integer)))");
		 exportSQL.append("		ELSE ");
		 exportSQL.append("			CAST( format(convert(decimal(30,8),initsetitem.INT_VAL),'0.#########') as NVARCHAR)");
		 exportSQL.append("		END");
	     exportSQL.append("  END INT_VALUE,");
	     exportSQL.append(" initsetitem.STRING_VAL,");
	     exportSQL.append("CASE itemcm.DATE_ITEM_TYPE");
	     exportSQL.append("	WHEN 1 THEN");
		 exportSQL.append(" 	convert(varchar, initsetitem.DATE_VAL, 111) ");
		 exportSQL.append("	WHEN 2 THEN");
		 exportSQL.append("		LEFT(convert(varchar, initsetitem.DATE_VAL, 111),7) ");
		 exportSQL.append("	WHEN 3 THEN");
		 exportSQL.append("		CONCAT(LEFT(convert(varchar, initsetitem.DATE_VAL, 111),4),'年') ");
		 exportSQL.append("	ELSE NULL");
		 exportSQL.append("	END DATE_VAL, ");
	     exportSQL.append("  CASE ");
	     exportSQL.append("   WHEN initsetitem.SAVE_DATA_TYPE = '1' THEN 7 ");
	     exportSQL.append("   WHEN initsetitem.SAVE_DATA_TYPE IN ('2','3') THEN 8");
	     exportSQL.append("   ELSE 7");
	     exportSQL.append("  END Align,");
	     exportSQL.append("  ROW_NUMBER() OVER (PARTITION BY initset.PER_INIT_SET_ID ORDER BY initset.PER_INIT_SET_CD, ");
	     exportSQL.append("             ctgorder.DISPORDER, itemorder.DISPORDER) AS ROW_NUMBER,");
	     exportSQL.append("  ROW_NUMBER() OVER (PARTITION BY initset.PER_INIT_SET_ID, ctg.PER_INFO_CTG_ID ORDER BY initset.PER_INIT_SET_CD, ");
	     exportSQL.append("             ctgorder.DISPORDER, itemorder.DISPORDER) AS ROW_NUMBER2,");
	     exportSQL.append("  ROW_NUMBER() OVER (ORDER BY initset.PER_INIT_SET_CD, ctgorder.DISPORDER, itemorder.DISPORDER) AS ROW_INDEX");
	     exportSQL.append("  FROM ");
		exportSQL.append("	(SELECT initsett.PER_INIT_SET_ID, initsett.PER_INIT_SET_CD, initsett.PER_INIT_SET_NAME, initsett.CID ");
		exportSQL.append("			FROM PPEMT_ENTRY_INIT initsett WHERE initsett.CID = ?CID ) initset");
	     exportSQL.append("  INNER JOIN PPEMT_ENTRY_INIT_CTG initctg ON initset.PER_INIT_SET_ID = initctg.PER_INIT_SET_ID");
	     exportSQL.append("  INNER JOIN PPEMT_CTG ctg ON ctg.PER_INFO_CTG_ID = initctg.PER_INFO_CTG_ID ");
	     exportSQL.append("       AND initset.CID = ctg.CID AND ctg.ABOLITION_ATR = 0 ");
	     exportSQL.append("  INNER JOIN PPEMT_CTG_COMMON ctgcm ON ctg.CATEGORY_CD = ctgcm.CATEGORY_CD AND ctgcm.CONTRACT_CD = ?CONTRACT_CD");
	     exportSQL.append("       AND ctgcm.CATEGORY_PARENT_CD IS NULL AND ctgcm.PERSON_EMPLOYEE_TYPE = 2");
	     exportSQL.append("       AND ctgcm.CATEGORY_TYPE <>2 AND ctgcm.CATEGORY_TYPE <>5");
	     exportSQL.append("       AND ctgcm.INIT_VAL_MASTER_OBJ_ATR = 1");
	     exportSQL.append("       AND ((ctgcm.SALARY_USE_ATR = 1 AND ?salaryUseAtr = 1) OR  (ctgcm.PERSONNEL_USE_ATR = 1 AND ?personnelUseAtr = 1)");
	     exportSQL.append("       OR  (ctgcm.EMPLOYMENT_USE_ATR = 1 AND ?employmentUseAtr = 1))");
	     exportSQL.append("       OR (?salaryUseAtr =  0 AND  ?personnelUseAtr = 0 AND ?employmentUseAtr = 0 )");
	     exportSQL.append("   INNER JOIN PPEMT_CTG_SORT ctgorder ON  ctg.PER_INFO_CTG_ID = ctgorder.PER_INFO_CTG_ID");
	     exportSQL.append("       AND ctg.CID = ctgorder.CID ");
	     exportSQL.append("  INNER JOIN PPEMT_ITEM item ON ctg.PER_INFO_CTG_ID = item.PER_INFO_CTG_ID AND item.ABOLITION_ATR = 0");
	     exportSQL.append("  INNER JOIN PPEMT_ITEM_COMMON itemcm ON item.ITEM_CD = itemcm.ITEM_CD AND ctgcm.CONTRACT_CD  = itemcm.CONTRACT_CD");
	     // Item code are not  IS00001 - 社員CD AND IS00020 - 入社年月日
	     exportSQL.append("       AND itemcm.CATEGORY_CD = ctgcm.CATEGORY_CD AND itemcm.ITEM_CD <> 'IS00001' AND itemcm.ITEM_CD <> 'IS00020'");
	     exportSQL.append("       AND itemcm.ITEM_TYPE = 2 AND itemcm.DATA_TYPE <>9 AND itemcm.DATA_TYPE <>10 AND itemcm.DATA_TYPE <>12");
	     exportSQL.append("  INNER JOIN PPEMT_ITEM_SORT itemorder");
	     exportSQL.append("       ON  item.PER_INFO_ITEM_DEFINITION_ID = itemorder.PER_INFO_ITEM_DEFINITION_ID");
	     exportSQL.append("       AND item.PER_INFO_CTG_ID = itemorder.PER_INFO_CTG_ID");
	     exportSQL.append("  LEFT JOIN PPEMT_ENTRY_INIT_ITEM initsetitem ON initset.PER_INIT_SET_ID = initsetitem.PER_INIT_SET_ID");
	     exportSQL.append("       AND initsetitem.PER_INFO_ITEM_DEF_ID = item.PER_INFO_ITEM_DEFINITION_ID");
	     exportSQL.append("		  AND initsetitem.PER_INFO_CTG_ID = item.PER_INFO_CTG_ID");
	     // -- DESIGNATED_MASTER");
	     exportSQL.append("  LEFT JOIN (SELECT wkp.WKP_CD, wkpt.WKP_NAME, wkp.CID, wkp.WKP_ID FROM BSYMT_WKP_INFO) wkp ");
	     exportSQL.append("       ON initsetitem.STRING_VAL = wkp.WKP_ID AND initset.CID  = wkp.CID AND itemcm.SELECTION_ITEM_REF_CODE = 'M00002'");
	     exportSQL.append("  LEFT JOIN BSYMT_EMPLOYMENT emp ON initsetitem.STRING_VAL = emp.CODE AND initset.CID= emp.CID ");
	     exportSQL.append("       AND itemcm.SELECTION_ITEM_REF_CODE = 'M00003'");
	     exportSQL.append("  LEFT JOIN BSYMT_CLASSIFICATION cls ON initsetitem.STRING_VAL = cls.CLSCD AND initset.CID = cls.CID ");
	     exportSQL.append("       AND itemcm.SELECTION_ITEM_REF_CODE = 'M00004'");
	     exportSQL.append("  LEFT JOIN (SELECT jobt.JOB_CD, jobt.JOB_NAME, jobt.CID, jobt.JOB_ID, jobhist.END_DATE FROM BSYMT_JOB_INFO jobt");
	     exportSQL.append("  INNER JOIN BSYMT_JOB_HIST jobhist ON jobt.CID = jobhist.CID AND jobt.HIST_ID = jobhist.HIST_ID ");
	     exportSQL.append("       AND jobhist.END_DATE = (SELECT MAX(END_DATE) FROM BSYMT_JOB_HIST jobhistory where jobt.CID = jobhistory.CID ");
	     exportSQL.append("       AND jobhistory.JOB_ID = jobt.JOB_ID )) job ");
	     exportSQL.append("     ON initsetitem.STRING_VAL = job.JOB_ID AND initset.CID  = job.CID ");
	     exportSQL.append("       AND itemcm.SELECTION_ITEM_REF_CODE = 'M00005'");
	     exportSQL.append("  LEFT JOIN BSYST_TEMP_ABSENCE_FRAME temp ON initsetitem.STRING_VAL = CAST(temp.TEMP_ABSENCE_FR_NO AS NVARCHAR) ");
	     exportSQL.append("       AND initset.CID= temp.CID AND itemcm.SELECTION_ITEM_REF_CODE = 'M00006'");
	     exportSQL.append("  LEFT JOIN KRCMT_BUSINESS_TYPE busstype ON initsetitem.STRING_VAL = busstype.BUSINESS_TYPE_CD AND initset.CID= busstype.CID ");
	     exportSQL.append("       AND itemcm.SELECTION_ITEM_REF_CODE = 'M00007'");
	     // 廃止されていない勤務種類をすべて取得する
	     exportSQL.append("  LEFT JOIN KSHMT_WKTP wrktype ON initsetitem.STRING_VAL = wrktype.CD AND initset.CID= wrktype.CID");
	     exportSQL.append("       AND itemcm.SELECTION_ITEM_REF_CODE = 'M00008'");
	     exportSQL.append("  LEFT JOIN KSHMT_WT wrktime ON initsetitem.STRING_VAL = wrktime.WORKTIME_CD AND initset.CID= wrktime.CID ");
	     exportSQL.append("       AND itemcm.SELECTION_ITEM_REF_CODE = 'M00009'");
	     // 出勤系の勤務種類を取得する
	     exportSQL.append("  LEFT JOIN KSHMT_WKTP wrktype2 ON initsetitem.STRING_VAL = wrktype2.CD AND initset.CID= wrktype2.CID ");
	     exportSQL.append("       AND itemcm.SELECTION_ITEM_REF_CODE = 'M00010'");
	     // 休日系の勤務種類を取得する
	     exportSQL.append("  LEFT JOIN KSHMT_WKTP wrktype3 ON initsetitem.STRING_VAL = wrktype3.CD AND initset.CID= wrktype3.CID ");
	     exportSQL.append("       AND itemcm.SELECTION_ITEM_REF_CODE = 'M00011'");
	     // 休出系の勤務種類を取得する
	     exportSQL.append("  LEFT JOIN KSHMT_WKTP wrktype4 ON initsetitem.STRING_VAL = wrktype4.CD AND initset.CID= wrktype4.CID ");
	     exportSQL.append("       AND itemcm.SELECTION_ITEM_REF_CODE = 'M00012'");
	     // 公休系の勤務種類を取得する
	     exportSQL.append("  LEFT JOIN KSHMT_WKTP wrktype5 ON initsetitem.STRING_VAL = wrktype5.CD AND initset.CID= wrktype5.CID ");
	     exportSQL.append("       AND itemcm.SELECTION_ITEM_REF_CODE = 'M00013'");
	     exportSQL.append("  LEFT JOIN KSCMT_MONTH_PATTERN monthPattern ON initsetitem.STRING_VAL = monthPattern.M_PATTERN_CD ");
	     exportSQL.append("       AND initset.CID= monthPattern.CID AND itemcm.SELECTION_ITEM_REF_CODE = 'M00014'");
	     exportSQL.append("  LEFT JOIN KRCMT_BONUS_PAY_SET bonuspay ON initsetitem.STRING_VAL = bonuspay.BONUS_PAY_SET_CD ");
	     exportSQL.append("       AND initset.CID= bonuspay.CID AND itemcm.SELECTION_ITEM_REF_CODE = 'M00015'");
	     exportSQL.append("  LEFT JOIN KSHMT_HDPAID_TBL_SET hdtblset ON initsetitem.STRING_VAL = hdtblset.YEAR_HD_CD ");
	     exportSQL.append("       AND initset.CID= hdtblset.CID AND itemcm.SELECTION_ITEM_REF_CODE = 'M00016'");
	     exportSQL.append("  LEFT JOIN KSHMT_HDSP_GRANT_TBL dateset ON initsetitem.STRING_VAL = dateset.GD_TBL_CD AND initset.CID= dateset.CID ");
	     exportSQL.append("       AND itemcm.SELECTION_ITEM_REF_CODE = 'M00017' AND ctg.CATEGORY_CD = 'CS00025' AND dateset.SPHD_CD = 1");
	     exportSQL.append("  LEFT JOIN KSHMT_HDSP_GRANT_TBL dateset1 ON initsetitem.STRING_VAL = dateset1.GD_TBL_CD AND initset.CID= dateset1.CID ");
	     exportSQL.append("       AND itemcm.SELECTION_ITEM_REF_CODE = 'M00017' AND ctg.CATEGORY_CD = 'CS00026' AND dateset1.SPHD_CD = 2");
	     exportSQL.append("  LEFT JOIN KSHMT_HDSP_GRANT_TBL dateset2 ON initsetitem.STRING_VAL = dateset2.GD_TBL_CD AND initset.CID= dateset2.CID ");
	     exportSQL.append("       AND itemcm.SELECTION_ITEM_REF_CODE = 'M00017' AND ctg.CATEGORY_CD = 'CS00027' AND dateset2.SPHD_CD = 3");
	     exportSQL.append("  LEFT JOIN KSHMT_HDSP_GRANT_TBL dateset3 ON initsetitem.STRING_VAL = dateset3.GD_TBL_CD AND initset.CID= dateset3.CID ");
	     exportSQL.append("       AND itemcm.SELECTION_ITEM_REF_CODE = 'M00017' AND ctg.CATEGORY_CD = 'CS00028' AND dateset3.SPHD_CD = 4");
	     exportSQL.append("  LEFT JOIN KSHMT_HDSP_GRANT_TBL dateset4 ON initsetitem.STRING_VAL = dateset4.GD_TBL_CD AND initset.CID= dateset4.CID ");
	     exportSQL.append("       AND itemcm.SELECTION_ITEM_REF_CODE = 'M00017' AND ctg.CATEGORY_CD = 'CS00029' AND dateset4.SPHD_CD = 5");
	     exportSQL.append("  LEFT JOIN KSHMT_HDSP_GRANT_TBL dateset5 ON initsetitem.STRING_VAL = dateset5.GD_TBL_CD AND initset.CID= dateset5.CID ");
	     exportSQL.append("       AND itemcm.SELECTION_ITEM_REF_CODE = 'M00017' AND ctg.CATEGORY_CD = 'CS00030' AND dateset5.SPHD_CD = 6");
	     exportSQL.append("  LEFT JOIN KSHMT_HDSP_GRANT_TBL dateset6 ON initsetitem.STRING_VAL = dateset6.GD_TBL_CD AND initset.CID= dateset6.CID ");
	     exportSQL.append("       AND itemcm.SELECTION_ITEM_REF_CODE = 'M00017' AND ctg.CATEGORY_CD = 'CS00031' AND dateset6.SPHD_CD = 7");
	     exportSQL.append("  LEFT JOIN KSHMT_HDSP_GRANT_TBL dateset7 ON initsetitem.STRING_VAL = dateset7.GD_TBL_CD AND initset.CID= dateset7.CID ");
	     exportSQL.append("       AND itemcm.SELECTION_ITEM_REF_CODE = 'M00017' AND ctg.CATEGORY_CD = 'CS00032' AND dateset7.SPHD_CD = 8");
	     exportSQL.append("  LEFT JOIN KSHMT_HDSP_GRANT_TBL dateset8 ON initsetitem.STRING_VAL = dateset8.GD_TBL_CD AND initset.CID= dateset8.CID ");
	     exportSQL.append("       AND itemcm.SELECTION_ITEM_REF_CODE = 'M00017' AND ctg.CATEGORY_CD = 'CS00033' AND dateset8.SPHD_CD = 9");
	     exportSQL.append("  LEFT JOIN KSHMT_HDSP_GRANT_TBL dateset9 ON initsetitem.STRING_VAL = dateset9.GD_TBL_CD AND initset.CID= dateset9.CID ");
	     exportSQL.append("       AND itemcm.SELECTION_ITEM_REF_CODE = 'M00017' AND ctg.CATEGORY_CD = 'CS00034' AND dateset9.SPHD_CD = 10");
	     exportSQL.append("  LEFT JOIN KSHMT_HDSP_GRANT_TBL dateset10 ON initsetitem.STRING_VAL = dateset10.GD_TBL_CD AND initset.CID= dateset10.CID ");
	     exportSQL.append("       AND itemcm.SELECTION_ITEM_REF_CODE = 'M00017' AND ctg.CATEGORY_CD = 'CS00049' AND dateset10.SPHD_CD = 11");
	     exportSQL.append("  LEFT JOIN KSHMT_HDSP_GRANT_TBL dateset11 ON initsetitem.STRING_VAL = dateset11.GD_TBL_CD AND initset.CID= dateset11.CID ");
	     exportSQL.append("       AND itemcm.SELECTION_ITEM_REF_CODE = 'M00017' AND ctg.CATEGORY_CD = 'CS00050' AND dateset11.SPHD_CD = 12");
	     exportSQL.append("  LEFT JOIN KSHMT_HDSP_GRANT_TBL dateset12 ON initsetitem.STRING_VAL = dateset12.GD_TBL_CD AND initset.CID= dateset12.CID ");
	     exportSQL.append("       AND itemcm.SELECTION_ITEM_REF_CODE = 'M00017' AND ctg.CATEGORY_CD = 'CS00051' AND dateset12.SPHD_CD = 13");
	     exportSQL.append("  LEFT JOIN KSHMT_HDSP_GRANT_TBL dateset13 ON initsetitem.STRING_VAL = dateset13.GD_TBL_CD AND initset.CID= dateset13.CID ");
	     exportSQL.append("       AND itemcm.SELECTION_ITEM_REF_CODE = 'M00017' AND ctg.CATEGORY_CD = 'CS00052' AND dateset13.SPHD_CD = 14");
	     exportSQL.append("  LEFT JOIN KSHMT_HDSP_GRANT_TBL dateset14 ON initsetitem.STRING_VAL = dateset14.GD_TBL_CD AND initset.CID= dateset14.CID ");
	     exportSQL.append("       AND itemcm.SELECTION_ITEM_REF_CODE = 'M00017' AND ctg.CATEGORY_CD = 'CS00053' AND dateset14.SPHD_CD = 15");
	     exportSQL.append("  LEFT JOIN KSHMT_HDSP_GRANT_TBL dateset15 ON initsetitem.STRING_VAL = dateset15.GD_TBL_CD AND initset.CID= dateset15.CID ");
	     exportSQL.append("       AND itemcm.SELECTION_ITEM_REF_CODE = 'M00017' AND ctg.CATEGORY_CD = 'CS00054' AND dateset15.SPHD_CD = 16");
	     exportSQL.append("  LEFT JOIN KSHMT_HDSP_GRANT_TBL dateset16 ON initsetitem.STRING_VAL = dateset16.GD_TBL_CD AND initset.CID= dateset16.CID ");
	     exportSQL.append("       AND itemcm.SELECTION_ITEM_REF_CODE = 'M00017' AND ctg.CATEGORY_CD = 'CS00055' AND dateset16.SPHD_CD = 17");
	     exportSQL.append("  LEFT JOIN KSHMT_HDSP_GRANT_TBL dateset17 ON initsetitem.STRING_VAL = dateset17.GD_TBL_CD AND initset.CID= dateset17.CID ");
	     exportSQL.append("       AND itemcm.SELECTION_ITEM_REF_CODE = 'M00017' AND ctg.CATEGORY_CD = 'CS00056' AND dateset17.SPHD_CD = 18");
	     exportSQL.append("  LEFT JOIN KSHMT_HDSP_GRANT_TBL dateset18 ON initsetitem.STRING_VAL = dateset18.GD_TBL_CD AND initset.CID= dateset18.CID ");
	     exportSQL.append("       AND itemcm.SELECTION_ITEM_REF_CODE = 'M00017' AND ctg.CATEGORY_CD = 'CS00057' AND dateset18.SPHD_CD = 19");
	     exportSQL.append("  LEFT JOIN KSHMT_HDSP_GRANT_TBL dateset19 ON initsetitem.STRING_VAL = dateset19.GD_TBL_CD AND initset.CID= dateset19.CID ");
	     exportSQL.append("       AND itemcm.SELECTION_ITEM_REF_CODE = 'M00017' AND ctg.CATEGORY_CD = 'CS00058' AND dateset19.SPHD_CD = 20");

	     exportSQL.append("  LEFT JOIN KSHMT_HDSP_GRANT_TBL dateset20 ON initsetitem.STRING_VAL = dateset20.GD_TBL_CD AND initset.CID= dateset20.CID ");
	     exportSQL.append("       AND itemcm.SELECTION_ITEM_REF_CODE = 'M00019' AND ctg.CATEGORY_CD = 'CS00075' AND dateset20.SPHD_CD = 21");

	     //  -- CODE_NAME");
	     exportSQL.append("  LEFT JOIN PPEMT_SELECTION_ITEM selection ON initsetitem.STRING_VAL = selection.SELECTION_ID");
	     exportSQL.append("  AND itemcm.SELECTION_ITEM_REF_TYPE = 2"); 
	     exportSQL.append(" ) TABLE_RESULT");
	     exportSQL.append(" ORDER BY TABLE_RESULT.ROW_INDEX");

	      GET_EXPORT_DATA = exportSQL.toString();
	  }
	  
	@Override
	public List<MasterData> getDataExport(int payroll,int personnel,int atttendance) {
//		Parameters parameters = Parameters.parse(GET_EXPORT_DATA);

//		PreparedStatement pstm = this.connection().prepareStatement(GET_EXPORT_DATA);
//		pstm.setStri
		
//		NtsStatement stm = new NtsStatement(GET_EXPORT_DATA, this.jdbcProxy());
//		stm.param(key, values)
		Query queryString = this.getEntityManager().createNativeQuery(GET_EXPORT_DATA)
				.setParameter("Enum_ReferenceMethodType_NOSETTING",
						I18NText.getText("Enum_ReferenceMethodType_NOSETTING"))
				.setParameter("Enum_ReferenceMethodType_FIXEDVALUE",
						I18NText.getText("Enum_ReferenceMethodType_FIXEDVALUE"))
				.setParameter("Enum_ReferenceMethodType_SAMEASLOGIN",
						I18NText.getText("Enum_ReferenceMethodType_SAMEASLOGIN"))
				.setParameter("Enum_ReferenceMethodType_SAMEASEMPLOYMENTDATE",
						I18NText.getText("Enum_ReferenceMethodType_SAMEASEMPLOYMENTDATE"))
				.setParameter("Enum_ReferenceMethodType_SAMEASEMPLOYEECODE",
						I18NText.getText("Enum_ReferenceMethodType_SAMEASEMPLOYEECODE"))
				.setParameter("Enum_ReferenceMethodType_SAMEASSYSTEMDATE",
						I18NText.getText("Enum_ReferenceMethodType_SAMEASSYSTEMDATE"))
				.setParameter("Enum_ReferenceMethodType_SAMEASNAME",
						I18NText.getText("Enum_ReferenceMethodType_SAMEASNAME"))
				.setParameter("Enum_ReferenceMethodType_SAMEASKANANAME",
						I18NText.getText("Enum_ReferenceMethodType_SAMEASKANANAME"))
				.setParameter("Enum_GenderPerson_Male", I18NText.getText("Enum_GenderPerson_Male"))
				.setParameter("Enum_GenderPerson_Female", I18NText.getText("Enum_GenderPerson_Female"))
				.setParameter("Enum_BloodType_ARhPlus", I18NText.getText("Enum_BloodType_ARhPlus"))
				.setParameter("Enum_BloodType_BRhPlus", I18NText.getText("Enum_BloodType_BRhPlus"))
				.setParameter("Enum_BloodType_ORhPlus", I18NText.getText("Enum_BloodType_ORhPlus"))
				.setParameter("Enum_BloodType_ABRhPlus", I18NText.getText("Enum_BloodType_ABRhPlus"))
				.setParameter("Enum_BloodType_ARhSub", I18NText.getText("Enum_BloodType_ARhSub"))
				.setParameter("Enum_BloodType_BRhSub", I18NText.getText("Enum_BloodType_BRhSub"))
				.setParameter("Enum_BloodType_ORhSub", I18NText.getText("Enum_BloodType_ORhSub"))
				.setParameter("Enum_BloodType_ABRhSub", I18NText.getText("Enum_BloodType_ABRhSub"))
				.setParameter("Enum_SalarySegment_DailySalary", I18NText.getText("Enum_SalarySegment_DailySalary"))
				.setParameter("Enum_SalarySegment_DailyMonthlySalary",
						I18NText.getText("Enum_SalarySegment_DailyMonthlySalary"))
				.setParameter("Enum_SalarySegment_HourlySalary", I18NText.getText("Enum_SalarySegment_HourlySalary"))
				.setParameter("Enum_SalarySegment_MonthlySalary", I18NText.getText("Enum_SalarySegment_MonthlySalary"))
				.setParameter("Enum_ChildCareAtr_ChildCare", I18NText.getText("Enum_ChildCareAtr_ChildCare"))
				.setParameter("Enum_ChildCareAtr_Care", I18NText.getText("Enum_ChildCareAtr_Care"))
				.setParameter("Enum_NotUseAtr_NOT_USE",
						I18NText.getText("Enum_NotUseAtr_NOT_USE"))
				.setParameter("Enum_NotUseAtr_USE", I18NText.getText("Enum_NotUseAtr_USE"))
				.setParameter("Enum_ManageAtr_NotManage",
						I18NText.getText("Enum_ManageAtr_NotManage"))
				.setParameter("Enum_ManageAtr_Manage", I18NText.getText("Enum_ManageAtr_Manage"))
				.setParameter("Enum_WorkingSystem_REGULAR_WORK", I18NText.getText("Enum_WorkingSystem_REGULAR_WORK"))
				.setParameter("Enum_WorkingSystem_FLEX_TIME_WORK",
						I18NText.getText("Enum_WorkingSystem_FLEX_TIME_WORK"))
				.setParameter("Enum_WorkingSystem_VARIABLE_WORKING_TIME_WORK",
						I18NText.getText("Enum_WorkingSystem_VARIABLE_WORKING_TIME_WORK"))
				.setParameter("Enum_WorkingSystem_EXCLUDED_WORKING_CALCULATE",
						I18NText.getText("Enum_WorkingSystem_EXCLUDED_WORKING_CALCULATE"))
				.setParameter("CPS001_101", I18NText.getText("CPS001_101"))
				.setParameter("CPS001_102", I18NText.getText("CPS001_102"))
				.setParameter("CPS001_103", I18NText.getText("CPS001_103"))
				.setParameter("CPS001_104", I18NText.getText("CPS001_104"))
				.setParameter("CPS001_105", I18NText.getText("CPS001_105"))
				.setParameter("Enum_TimeZoneScheduledMasterAtr_FollowMasterReference",
						I18NText.getText("Enum_TimeZoneScheduledMasterAtr_FollowMasterReference"))
				.setParameter("Enum_TimeZoneScheduledMasterAtr_PersonalWorkDaily",
						I18NText.getText("Enum_TimeZoneScheduledMasterAtr_PersonalWorkDaily"))
				.setParameter("Enum_TimeZoneScheduledMasterAtr_PersonalDayOfWeek",
						I18NText.getText("Enum_TimeZoneScheduledMasterAtr_PersonalDayOfWeek"))
				.setParameter("Enum_UseAtr_Hourly_Pay", I18NText.getText("Enum_UseAtr_Hourly_Pay"))
				.setParameter("Enum_UseAtr_Outside_Time_Pay", I18NText.getText("Enum_UseAtr_Outside_Time_Pay"))
				.setParameter("Enum_SpecialLeaveAppSetting_PRESCRIBED",
						I18NText.getText("Enum_SpecialLeaveAppSetting_PRESCRIBED"))
				.setParameter("Enum_SpecialLeaveAppSetting_PERSONAL",
						I18NText.getText("Enum_SpecialLeaveAppSetting_PERSONAL"))
				.setParameter("Enum_PaymentMethod_VACATION_OCCURRED",
						I18NText.getText("Enum_PaymentMethod_VACATION_OCCURRED"))
				.setParameter("Enum_PaymentMethod_AMOUNT_PAYMENT",
						I18NText.getText("Enum_PaymentMethod_AMOUNT_PAYMENT"))
				.setParameter("Enum_UpperLimitSetting_FAMILY_INFO",
						I18NText.getText("Enum_UpperLimitSetting_FAMILY_INFO"))
				.setParameter("Enum_UpperLimitSetting_PER_INFO_EVERY_YEAR",
						I18NText.getText("Enum_UpperLimitSetting_PER_INFO_EVERY_YEAR"))
				.setParameter("Enum_UpperLimitSetting_PER_INFO_FISCAL_YEAR",
						I18NText.getText("Enum_UpperLimitSetting_PER_INFO_FISCAL_YEAR"))
				.setParameter("Enum_LeaveExpirationStatus_EXPIRED",
						I18NText.getText("Enum_LeaveExpirationStatus_EXPIRED"))
				.setParameter("Enum_LeaveExpirationStatus_AVAILABLE",
						I18NText.getText("Enum_LeaveExpirationStatus_AVAILABLE"))
				.setParameter("Enum_CalculationAtr_SCHEDULED",
						I18NText.getText("Enum_CalculationAtr_SCHEDULED"))
				.setParameter("Enum_CalculationAtr_CHANGE_OVER_TIME",
						I18NText.getText("Enum_CalculationAtr_CHANGE_OVER_TIME"))
				.setParameter("Enum_CalculationAtr_CHANGE_BEFORE_AND_AFTER_CHILDBIRTH",
						I18NText.getText("Enum_CalculationAtr_CHANGE_BEFORE_AND_AFTER_CHILDBIRTH"))
				.setParameter("Enum_CalculationAtr_CHANGE_AFTER_CHILDCARE_LEAVE",
						I18NText.getText("Enum_CalculationAtr_CHANGE_AFTER_CHILDCARE_LEAVE"))
				.setParameter("Enum_CalculationAtr_OBTAINING_QUALIFICATION",
						I18NText.getText("Enum_CalculationAtr_OBTAINING_QUALIFICATION"))
				.setParameter("payroll", payroll)
				.setParameter("personnel", personnel)
				.setParameter("atttendance", atttendance)
				.setParameter("salaryUseAtr", payroll)
				.setParameter("personnelUseAtr", personnel)
				.setParameter("employmentUseAtr", atttendance)
				.setParameter("CID", AppContexts.user().companyId())
				.setParameter("MasterUnregisted",I18NText.getText(PerInfoInitValueSetCtgUtils.MasterUnregisted))
				.setParameter("CONTRACT_CD", AppContexts.user().contractCode());

		@SuppressWarnings("unchecked")
		List<Object[]> result = queryString.getResultList();
		List<MasterData> masterResult = new ArrayList<>();
		result.forEach(obj-> {
			masterResult.add(toMasterDate(obj));
		});
		return masterResult;
	}

	private MasterData toMasterDate(Object[] obj){
		Map<String, MasterCellData> data = new HashMap<>();
		data.put(PerInfoInitValueSetCtgUtils.CPS009_41, MasterCellData.builder()
                .columnId(PerInfoInitValueSetCtgUtils.CPS009_41)
                .value((String) obj[0])
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
		data.put(PerInfoInitValueSetCtgUtils.CPS009_42, MasterCellData.builder()
                .columnId(PerInfoInitValueSetCtgUtils.CPS009_42)
                .value((String) obj[1])
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
		data.put(PerInfoInitValueSetCtgUtils.CPS009_43, MasterCellData.builder()
                .columnId(PerInfoInitValueSetCtgUtils.CPS009_43)
                .value((String) obj[2])
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
		data.put(PerInfoInitValueSetCtgUtils.CPS009_44, MasterCellData.builder()
                .columnId(PerInfoInitValueSetCtgUtils.CPS009_44)
                .value((String) obj[3])
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
		data.put(PerInfoInitValueSetCtgUtils.CPS009_45, MasterCellData.builder()
                .columnId(PerInfoInitValueSetCtgUtils.CPS009_45)
                .value((String) obj[4])
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
		data.put(PerInfoInitValueSetCtgUtils.CPS009_46, MasterCellData.builder()
                .columnId(PerInfoInitValueSetCtgUtils.CPS009_46)
                .value((String) obj[5])
                .style(MasterCellStyle.build().horizontalAlign(EnumAdaptor.valueOf(Integer.valueOf(obj[6].toString()), ColumnTextAlign.class)))
                .build());
		return MasterData.builder().rowData(data).build();
	}
}
