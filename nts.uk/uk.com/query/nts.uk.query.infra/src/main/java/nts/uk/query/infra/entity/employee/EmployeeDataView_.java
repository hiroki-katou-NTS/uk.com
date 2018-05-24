/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.query.infra.entity.employee;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

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

	/** The wpl end date. */
	public static volatile SingularAttribute<EmployeeDataView, GeneralDateTime> wplEndDate;

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

	/** The wpl info end date. */
	public static volatile SingularAttribute<EmployeeDataView, GeneralDateTime> wplInfoEndDate;

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

	/** The workplace id. */
	public static volatile SingularAttribute<EmployeeDataView, String> workplaceId;

	/** The class end date. */
	public static volatile SingularAttribute<EmployeeDataView, GeneralDateTime> classEndDate;

	/** The work type str date. */
	public static volatile SingularAttribute<EmployeeDataView, GeneralDate> workTypeStrDate;

	/** The wpl str date. */
	public static volatile SingularAttribute<EmployeeDataView, GeneralDateTime> wplStrDate;

	/** The del status atr. */
	public static volatile SingularAttribute<EmployeeDataView, Integer> delStatusAtr;

	/** The job info str date. */
	public static volatile SingularAttribute<EmployeeDataView, GeneralDateTime> jobInfoStrDate;

	/** The wpl name. */
	public static volatile SingularAttribute<EmployeeDataView, String> wplName;

	/** The job info end date. */
	public static volatile SingularAttribute<EmployeeDataView, GeneralDateTime> jobInfoEndDate;

	/** The work type cd. */
	public static volatile SingularAttribute<EmployeeDataView, String> workTypeCd;

	/** The classification code. */
	public static volatile SingularAttribute<EmployeeDataView, String> classificationCode;

	/** The person name kana. */
	public static volatile SingularAttribute<EmployeeDataView, String> personNameKana;

	/** The wpl cd. */
	public static volatile SingularAttribute<EmployeeDataView, String> wplCd;

	/** The job cd. */
	public static volatile SingularAttribute<EmployeeDataView, String> jobCd;

	/** The employment end date. */
	public static volatile SingularAttribute<EmployeeDataView, GeneralDateTime> employmentEndDate;

	/** The wpl info str date. */
	public static volatile SingularAttribute<EmployeeDataView, GeneralDateTime> wplInfoStrDate;

	/** The cid. */
	public static volatile SingularAttribute<EmployeeDataView, String> cid;

	/** The com end date. */
	public static volatile SingularAttribute<EmployeeDataView, GeneralDateTime> comEndDate;

	/** The closure id. */
	public static volatile SingularAttribute<EmployeeDataView, Integer> closureId;

	/** The wpl hierarchy code. */
	public static volatile SingularAttribute<EmployeeDataView, String> wplHierarchyCode;

	/** The wkp conf end date. */
	public static volatile SingularAttribute<EmployeeDataView, GeneralDateTime> wkpConfEndDate;

	/** The wkp conf str date. */
	public static volatile SingularAttribute<EmployeeDataView, GeneralDateTime> wkpConfStrDate;

	/** The job seq disp. */
	public static volatile SingularAttribute<EmployeeDataView, Integer> jobSeqDisp;
	
	/** The business name kana. */
	public static volatile SingularAttribute<EmployeeDataView, String> businessNameKana;

}