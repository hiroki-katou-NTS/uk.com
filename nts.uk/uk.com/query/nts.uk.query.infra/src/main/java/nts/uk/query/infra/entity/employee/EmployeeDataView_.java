/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.query.infra.entity.employee;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import nts.arc.layer.infra.data.entity.type.GeneralDateTimeToDBConverter;
import nts.arc.layer.infra.data.entity.type.GeneralDateToDBConverter;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;

/**
 * The Class EmployeeDataView_.
 */
@StaticMetamodel(EmployeeDataView.class)
public class EmployeeDataView_ {

	/** The business name kana. */
	public static volatile SingularAttribute<EmployeeDataView, String> businessName;

	/** The job end date. */
	public static volatile SingularAttribute<EmployeeDataView, GeneralDateTime> jobEndDate;

	/** The class str date. */
	public static volatile SingularAttribute<EmployeeDataView, GeneralDateTime> classStrDate;

	/** The job title id. */
	public static volatile SingularAttribute<EmployeeDataView, String> jobTitleId;

	/** The job str date. */
	public static volatile SingularAttribute<EmployeeDataView, GeneralDateTime> jobStrDate;

	/** The abs end date. */
	public static volatile SingularAttribute<EmployeeDataView, GeneralDateTime> absEndDate;

	/** The business english name. */
	public static volatile SingularAttribute<EmployeeDataView, String> businessEnglishName;

	/** The abs str date. */
	public static volatile SingularAttribute<EmployeeDataView, GeneralDateTime> absStrDate;

	/** The emp cd. */
	public static volatile SingularAttribute<EmployeeDataView, String> empCd;

	/** The employment str date. */
	public static volatile SingularAttribute<EmployeeDataView, GeneralDateTime> employmentStrDate;

	/** The com str date. */
	public static volatile SingularAttribute<EmployeeDataView, GeneralDateTime> comStrDate;

	/** The sid. */
	public static volatile SingularAttribute<EmployeeDataView, String> sid;

	/** The work type end date. */
	public static volatile SingularAttribute<EmployeeDataView, GeneralDate> workTypeEndDate;

	/** The business other name. */
	public static volatile SingularAttribute<EmployeeDataView, String> businessOtherName;

	/** The scd. */
	public static volatile SingularAttribute<EmployeeDataView, String> scd;

	/** The temp abs frame no. */
	public static volatile SingularAttribute<EmployeeDataView, Integer> tempAbsFrameNo;

	/** The class end date. */
	public static volatile SingularAttribute<EmployeeDataView, GeneralDateTime> classEndDate;

	/** The work type str date. */
	public static volatile SingularAttribute<EmployeeDataView, GeneralDate> workTypeStrDate;

	/** The del status atr. */
	public static volatile SingularAttribute<EmployeeDataView, Integer> delStatusAtr;

	/** The job info str date. */
	public static volatile SingularAttribute<EmployeeDataView, GeneralDateTime> jobInfoStrDate;

	/** The job info end date. */
	public static volatile SingularAttribute<EmployeeDataView, GeneralDateTime> jobInfoEndDate;

	/** The work type cd. */
	public static volatile SingularAttribute<EmployeeDataView, String> workTypeCd;

	/** The classification code. */
	public static volatile SingularAttribute<EmployeeDataView, String> classificationCode;

	/** The person name kana. */
	public static volatile SingularAttribute<EmployeeDataView, String> personNameKana;

	/** The job cd. */
	public static volatile SingularAttribute<EmployeeDataView, String> jobCd;

	/** The employment end date. */
	public static volatile SingularAttribute<EmployeeDataView, GeneralDateTime> employmentEndDate;

	/** The cid. */
	public static volatile SingularAttribute<EmployeeDataView, String> cid;

	/** The com end date. */
	public static volatile SingularAttribute<EmployeeDataView, GeneralDateTime> comEndDate;

	/** The closure id. */
	public static volatile SingularAttribute<EmployeeDataView, Integer> closureId;

	/** The job seq disp. */
	public static volatile SingularAttribute<EmployeeDataView, Integer> jobSeqDisp;
	
	/** The business name kana. */
	public static volatile SingularAttribute<EmployeeDataView, String> businessNameKana;

	/** The department id. */
	public static volatile SingularAttribute<EmployeeDataView, String> depId;

	/** The department start date. */
	public static volatile SingularAttribute<EmployeeDataView, GeneralDateTime> depStrDate;

	/** The department end date. */
	public static volatile SingularAttribute<EmployeeDataView, GeneralDateTime> depEndDate;

	/** The department code. */
	public static volatile SingularAttribute<EmployeeDataView, String> depCode;

	/** The department name. */
	public static volatile SingularAttribute<EmployeeDataView, String> depName;

	/** The department delete flag. */
	public static volatile SingularAttribute<EmployeeDataView, Boolean> depDeleteFlag;

	/** The department generic. */
	public static volatile SingularAttribute<EmployeeDataView, String> depGeneric;

	/** The department display name. */
	public static volatile SingularAttribute<EmployeeDataView, String> depDisplayName;

	/** The department hierarchy code. */
	public static volatile SingularAttribute<EmployeeDataView, String> depHierarchyCode;

	/** The department external code. */
	public static volatile SingularAttribute<EmployeeDataView, String> depExternalCode;

	/** The department history id. */
	public static volatile SingularAttribute<EmployeeDataView, String> depHistoryId;

	/** The department config start date. */
	public static volatile SingularAttribute<EmployeeDataView, GeneralDate> depConfStrDate;

	/** The department config end date. */
	public static volatile SingularAttribute<EmployeeDataView, GeneralDate> depConfEndDate;

	/** The workplace id. */
	public static volatile SingularAttribute<EmployeeDataView, String> wkpId;

	/** The workplace start date. */
	public static volatile SingularAttribute<EmployeeDataView, GeneralDateTime> wkpStrDate;

	/** The workplace end date. */
	public static volatile SingularAttribute<EmployeeDataView, GeneralDateTime> wkpEndDate;

	/** The workplace code. */
	public static volatile SingularAttribute<EmployeeDataView, String> wkpCode;

	/** The workplace name. */
	public static volatile SingularAttribute<EmployeeDataView, String> wkpName;

	/** The workplace delete flag. */
	public static volatile SingularAttribute<EmployeeDataView, Boolean> wkpDeleteFlag;

	/** The workplace generic. */
	public static volatile SingularAttribute<EmployeeDataView, String> wkpGeneric;

	/** The workplace display name. */
	public static volatile SingularAttribute<EmployeeDataView, String> wkpDisplayName;

	/** The workplace hierarchy code. */
	public static volatile SingularAttribute<EmployeeDataView, String> wkpHierarchyCode;

	/** The workplace external code. */
	public static volatile SingularAttribute<EmployeeDataView, String> wkpExternalCode;

	/** The workplace history id. */
	public static volatile SingularAttribute<EmployeeDataView, String> wkpHistoryId;

	/** The workplace config start date. */
	public static volatile SingularAttribute<EmployeeDataView, GeneralDate> wkpConfStrDate;

	/** The workplace config end date. */
	public static volatile SingularAttribute<EmployeeDataView, GeneralDate> wkpConfEndDate;
}