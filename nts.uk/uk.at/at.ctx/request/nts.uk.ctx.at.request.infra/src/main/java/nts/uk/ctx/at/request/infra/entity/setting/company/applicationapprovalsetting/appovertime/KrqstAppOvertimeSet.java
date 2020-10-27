package nts.uk.ctx.at.request.infra.entity.setting.company.applicationapprovalsetting.appovertime;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 *
 * @author loivt
 */
@Entity
@Table(name = "KRQST_APP_OVERTIME_SET")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class KrqstAppOvertimeSet extends ContractUkJpaEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "CID")
    private String cid;
    
    @Column(name = "FLEX_EXCESS_USE_SET_ATR")
    private int flexExcessUseSetAtr;
    
    @Column(name = "PRE_TYPE_SIFT_REFLECT_FLG")
    private int preTypeSiftReflectFlg;
    
    @Column(name = "PRE_OVERTIME_REFLECT_FLG")
    private int preOvertimeReflectFlg;
    
    @Column(name = "POST_TYPESIFT_REFLECT_FLG")
    private int postTypesiftReflectFlg;
    
    @Column(name = "POST_BREAK_REFLECT_FLG")
    private int postBreakReflectFlg;
    
    @Column(name = "POST_WORKTIME_REFLECT_FLG")
    private int postWorktimeReflectFlg;
    
    @Column(name = "CALENDAR_DISP_ATR")
    private int calendarDispAtr;
    
    @Column(name = "EARLY_OVER_TIME_USE_ATR")
    private int earlyOverTimeUseAtr;
    
    @Column(name = "INSTRUCT_EXCESS_OT_ATR")
    private int instructExcessOtAtr;
    
    @Column(name = "PRIORITY_STAMP_SET_ATR")
    private int priorityStampSetAtr;
    
    @Column(name = "UNIT_ASSIGNMENT_OVERTIME")
    private int unitAssignmentOvertime;
    
    @Column(name = "NORMAL_OVERTIME_USE_ATR")
    private int normalOvertimeUseAtr;
    
    @Column(name = "DATTENDANCE_ID")
    private int attendanceId;
    
    @Column(name = "USE_OT_WORK")
    private int useOt;
    
    @Column(name = "REST_ATR")
    private int restAtr;
    
    // 勤種変更可否フラグ
    @Column(name = "WORKTYPE_PERMISSION_FLAG")
    private int workTypeChangeFlag;
    
	@Override
	protected Object getKey() {
		// TODO Auto-generated method stub
		return cid;
	}

   
}

