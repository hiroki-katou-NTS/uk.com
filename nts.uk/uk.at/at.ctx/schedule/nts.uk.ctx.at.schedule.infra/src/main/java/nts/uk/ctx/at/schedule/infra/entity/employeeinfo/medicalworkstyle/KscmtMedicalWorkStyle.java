package nts.uk.ctx.at.schedule.infra.entity.employeeinfo.medicalworkstyle;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.schedule.dom.employeeinfo.medicalworkstyle.EmpMedicalWorkFormHisItem;
import nts.uk.ctx.at.schedule.dom.employeeinfo.medicalworkstyle.EmpMedicalWorkStyleHistory;
import nts.uk.ctx.at.schedule.dom.employeeinfo.medicalworkstyle.FulltimeRemarks;
import nts.uk.ctx.at.schedule.dom.employeeinfo.medicalworkstyle.MedicalCareWorkStyle;
import nts.uk.ctx.at.schedule.dom.employeeinfo.medicalworkstyle.MedicalWorkFormInfor;
import nts.uk.ctx.at.schedule.dom.employeeinfo.medicalworkstyle.NightShiftRemarks;
import nts.uk.ctx.at.schedule.dom.employeeinfo.medicalworkstyle.NurseClassifiCode;
import nts.uk.ctx.at.schedule.dom.employeeinfo.medicalworkstyle.NursingWorkFormInfor;
import nts.uk.shr.com.history.DateHistoryItem;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

@Getter
@Setter
@Entity
@Table(name = "KSCMT_MEDICAL_WORK_STYLE")
@AllArgsConstructor
@NoArgsConstructor
public class KscmtMedicalWorkStyle extends ContractUkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public KscmtMedicalWorkStylePk pk;

	/** 開始日 **/
	@Column(name = "START_DATE")
	public GeneralDate startDate;

	/** 終了日 **/
	@Column(name = "END_DATE")
	public GeneralDate endDate;

	/** 夜勤専従者か **/
	@Column(name = "IS_ONLY_NIGHT_SHIFT")
	public boolean isOnlyNightShift;

	/** 医療介護勤務形態 **/
	@Column(name = "MEDICAL_CARE_WORK_STYLE")
	public int medicalCareWorkStyle;

	/** 看護区分 **/
	@Column(name = "NURSE_LICENSE_CD")
	public String nurseLicenseCd;

	/** 他部署兼務か **/
	@Column(name = "MEDICAL_CONCURRENT_POST")
	public Boolean medicalConcurrentPost;

	/** 介護の兼務可能か **/
	@Column(name = "CARE_CONCURRENT_POST")
	public Boolean careConcurrentPost;

	/** 常勤換算表備考 **/
	@Column(name = "CARE_RPT_NOTE")
	public String careRptNote;

	/** 夜勤職員配置加算表備考 **/
	@Column(name = "CARE_NIGHT_RPT_NOTE")
	public String careNightRptNote;

	@Override
	protected Object getKey() {
		return this.pk;
	}

	public KscmtMedicalWorkStyle(KscmtMedicalWorkStylePk pk, GeneralDate startDate, GeneralDate endDate) {
		super();
		this.pk = pk;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public KscmtMedicalWorkStyle(KscmtMedicalWorkStylePk pk, boolean isOnlyNightShift, int medicalCareWorkStyle,
			String nurseLicenseCd, boolean medicalConcurrentPost, boolean careConcurrentPost, String careRptNote,
			String careNightRptNote) {
		super();
		this.pk = pk;
		this.isOnlyNightShift = isOnlyNightShift;
		this.medicalCareWorkStyle = medicalCareWorkStyle;
		this.nurseLicenseCd = nurseLicenseCd;
		this.medicalConcurrentPost = medicalConcurrentPost;
		this.careConcurrentPost = careConcurrentPost;
		this.careRptNote = careRptNote;
		this.careNightRptNote = careNightRptNote;
	}

	public static KscmtMedicalWorkStyle toEntityMedicalWorkStyle(EmpMedicalWorkStyleHistory his,
			EmpMedicalWorkFormHisItem hisItem) {
		KscmtMedicalWorkStyle kscmtMedicalWorkStyle = new KscmtMedicalWorkStyle();
		KscmtMedicalWorkStylePk pk = new KscmtMedicalWorkStylePk(his.getEmpID(), hisItem.getHistoryID());
		if (his.getEmpID().equals(hisItem.getEmpID())) {
			Optional<DateHistoryItem> optional = his.getListDateHistoryItem().stream()
					.filter(predicate -> predicate.identifier() == hisItem.getHistoryID()).findFirst();
			if (optional.isPresent()) {

				kscmtMedicalWorkStyle = new KscmtMedicalWorkStyle(pk, optional.get().span().start(),
						optional.get().span().end(), 
						hisItem.isNightShiftFullTime(),
						hisItem.getOptMedicalWorkFormInfor().isPresent()
								? hisItem.getOptMedicalWorkFormInfor().get().getMedicalCareWorkStyle().value
								: hisItem.getOpyNursingWorkFormInfor().get().getMedicalCareWorkStyle().value,
						hisItem.getOptMedicalWorkFormInfor().isPresent()
								? hisItem.getOptMedicalWorkFormInfor().get().getNurseClassifiCode().v() : null,
						hisItem.getOptMedicalWorkFormInfor().isPresent()
								? hisItem.getOptMedicalWorkFormInfor().get().isOtherDepartmentConcurrently() : null,
										
						hisItem.getOpyNursingWorkFormInfor().isPresent()
								? hisItem.getOpyNursingWorkFormInfor().get().isAsNursingCare() : null,
						hisItem.getOpyNursingWorkFormInfor().isPresent()
								? hisItem.getOpyNursingWorkFormInfor().get().getFulltimeRemarks().v() : null,
						hisItem.getOpyNursingWorkFormInfor().isPresent()
								? hisItem.getOpyNursingWorkFormInfor().get().getNightShiftRemarks().v() : null);
			}
		}

		return kscmtMedicalWorkStyle;

	}

	public EmpMedicalWorkFormHisItem toDomainHisItem() {
		EmpMedicalWorkFormHisItem domain  = new EmpMedicalWorkFormHisItem(
				this.getPk().getSid(),
				this.getPk().getHistId(),
				this.isOnlyNightShift ,
				Optional.ofNullable( new MedicalWorkFormInfor((EnumAdaptor.valueOf(this.getMedicalCareWorkStyle(), MedicalCareWorkStyle.class)) ,new NurseClassifiCode(this.getNurseLicenseCd()),this.getMedicalConcurrentPost()) ),
				Optional.ofNullable(new NursingWorkFormInfor( (EnumAdaptor.valueOf(this.getMedicalCareWorkStyle(), MedicalCareWorkStyle.class)), this.getCareConcurrentPost() , new FulltimeRemarks(this.getCareRptNote()), new NightShiftRemarks(this.getCareNightRptNote())  ) ));
		return domain;
	}
	
	public  EmpMedicalWorkStyleHistory toDomainHis(){
		DateHistoryItem dateHistoryItem = new DateHistoryItem(this.pk.getHistId(), new DatePeriod(this.startDate,this.endDate ));				
		List<DateHistoryItem> listDateHistoryItem = Arrays.asList(dateHistoryItem);
		EmpMedicalWorkStyleHistory domain =  new EmpMedicalWorkStyleHistory(this.pk.getSid(), listDateHistoryItem);
		return domain;
	}
}
