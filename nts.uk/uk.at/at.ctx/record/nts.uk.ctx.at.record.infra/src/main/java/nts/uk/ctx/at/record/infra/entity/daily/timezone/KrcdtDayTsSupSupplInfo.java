package nts.uk.ctx.at.record.infra.entity.daily.timezone;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.PrimaryKeyJoinColumns;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.infra.entity.daily.ouen.KrcdtDayOuenTimeSheet;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.ChoiceCode;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.SuppInfoCommentItem;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.SuppInfoNo;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.SuppInfoNumItem;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.SuppInfoSelectionItem;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.SuppInfoTimeItem;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.SuppNumValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.WorkSuppComment;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.WorkSuppInfo;
import nts.uk.shr.infra.data.entity.ContractCompanyUkJpaEntity;

/**
 * @name 日別時間帯別実績
 * @author tutt
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KRCDT_DAY_TS_SUP_SUPPL_INFO")
public class KrcdtDayTsSupSupplInfo extends ContractCompanyUkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public KrcdtDayTsSupSupplInfoPk pk;

	@Column(name = "SUPPL_INFO_TIME1")
	public Integer supplInfoTime1;
	
	@Column(name = "SUPPL_INFO_TIME2")
	public Integer supplInfoTime2;
	
	@Column(name = "SUPPL_INFO_TIME3")
	public Integer supplInfoTime3;
	
	@Column(name = "SUPPL_INFO_TIME4")
	public Integer supplInfoTime4;
	
	@Column(name = "SUPPL_INFO_TIME5")
	public Integer supplInfoTime5;
	
	@Column(name = "SUPPL_INFO_NUMBER1")
	public Integer supplInfoNumber1;
	
	@Column(name = "SUPPL_INFO_NUMBER2")
	public Integer supplInfoNumber2;
	
	@Column(name = "SUPPL_INFO_NUMBER3")
	public Integer supplInfoNumber3;
	
	@Column(name = "SUPPL_INFO_NUMBER4")
	public Integer supplInfoNumber4;
	
	@Column(name = "SUPPL_INFO_NUMBER5")
	public Integer supplInfoNumber5;
	
	@Column(name = "SUPPL_INFO_COMMENT1")
	public String supplInfoComment1;
	
	@Column(name = "SUPPL_INFO_COMMENT2")
	public String supplInfoComment2;
	
	@Column(name = "SUPPL_INFO_COMMENT3")
	public String supplInfoComment3;
	
	@Column(name = "SUPPL_INFO_COMMENT4")
	public String supplInfoComment4;
	
	@Column(name = "SUPPL_INFO_COMMENT5")
	public String supplInfoComment5;
	
	@Column(name = "SUPPL_INFO_CODE1")
	public String supplInfoCode1;
	
	@Column(name = "SUPPL_INFO_CODE2")
	public String supplInfoCode2;
	
	@Column(name = "SUPPL_INFO_CODE3")
	public String supplInfoCode3;
	
	@Column(name = "SUPPL_INFO_CODE4")
	public String supplInfoCode4;
	
	@Column(name = "SUPPL_INFO_CODE5")
	public String supplInfoCode5;
	
	@OneToOne
	@PrimaryKeyJoinColumns({
		@PrimaryKeyJoinColumn(name = "SID", referencedColumnName = "SID"),
		@PrimaryKeyJoinColumn(name = "YMD", referencedColumnName = "YMD"),
		@PrimaryKeyJoinColumn(name = "SUP_NO", referencedColumnName = "SUP_NO")})
	public KrcdtDayOuenTimeSheet krcdtDayOuenTimeSheet;
	
	@Override
	protected Object getKey() {
		return this.pk;
	}

	public WorkSuppInfo domain() {
		
		//補足時間情報
		List<SuppInfoTimeItem> suppInfoTimeItems = new ArrayList<>();
		SuppInfoTimeItem suppInfoTimeItem1 = new SuppInfoTimeItem(new SuppInfoNo(1), this.supplInfoTime1 == null ? null: new AttendanceTime(this.supplInfoTime1));
		SuppInfoTimeItem suppInfoTimeItem2 = new SuppInfoTimeItem(new SuppInfoNo(2), this.supplInfoTime2 == null ? null: new AttendanceTime(this.supplInfoTime2));
		SuppInfoTimeItem suppInfoTimeItem3 = new SuppInfoTimeItem(new SuppInfoNo(3), this.supplInfoTime3 == null ? null: new AttendanceTime(this.supplInfoTime3));
		SuppInfoTimeItem suppInfoTimeItem4 = new SuppInfoTimeItem(new SuppInfoNo(4), this.supplInfoTime4 == null ? null: new AttendanceTime(this.supplInfoTime4));
		SuppInfoTimeItem suppInfoTimeItem5 = new SuppInfoTimeItem(new SuppInfoNo(5), this.supplInfoTime5 == null ? null: new AttendanceTime(this.supplInfoTime5));
		suppInfoTimeItems.add(suppInfoTimeItem1);
		suppInfoTimeItems.add(suppInfoTimeItem2);
		suppInfoTimeItems.add(suppInfoTimeItem3);
		suppInfoTimeItems.add(suppInfoTimeItem4);
		suppInfoTimeItems.add(suppInfoTimeItem5);
		
		//補足数値情報
		List<SuppInfoNumItem> suppInfoNumItems = new ArrayList<>();
		SuppInfoNumItem suppInfoNumItem1 = new SuppInfoNumItem(new SuppInfoNo(1), this.supplInfoNumber1 == null ? null : new SuppNumValue(this.supplInfoNumber1));
		SuppInfoNumItem suppInfoNumItem2 = new SuppInfoNumItem(new SuppInfoNo(2), this.supplInfoNumber2 == null ? null : new SuppNumValue(this.supplInfoNumber2));
		SuppInfoNumItem suppInfoNumItem3 = new SuppInfoNumItem(new SuppInfoNo(3), this.supplInfoNumber3 == null ? null : new SuppNumValue(this.supplInfoNumber3));
		SuppInfoNumItem suppInfoNumItem4 = new SuppInfoNumItem(new SuppInfoNo(4), this.supplInfoNumber4 == null ? null : new SuppNumValue(this.supplInfoNumber4));
		SuppInfoNumItem suppInfoNumItem5 = new SuppInfoNumItem(new SuppInfoNo(5), this.supplInfoNumber5 == null ? null : new SuppNumValue(this.supplInfoNumber5));
		suppInfoNumItems.add(suppInfoNumItem1);
		suppInfoNumItems.add(suppInfoNumItem2);
		suppInfoNumItems.add(suppInfoNumItem3);
		suppInfoNumItems.add(suppInfoNumItem4);
		suppInfoNumItems.add(suppInfoNumItem5);
		
		//補足コメント情報
		List<SuppInfoCommentItem> suppInfoCommentItems = new ArrayList<>();
		SuppInfoCommentItem suppInfoCommentItem1 = new SuppInfoCommentItem(new SuppInfoNo(1), this.supplInfoComment1 == null ? null :  new WorkSuppComment(this.supplInfoComment1));
		SuppInfoCommentItem suppInfoCommentItem2 = new SuppInfoCommentItem(new SuppInfoNo(2), this.supplInfoComment2 == null ? null :  new WorkSuppComment(this.supplInfoComment2));
		SuppInfoCommentItem suppInfoCommentItem3 = new SuppInfoCommentItem(new SuppInfoNo(3), this.supplInfoComment3 == null ? null :  new WorkSuppComment(this.supplInfoComment3));
		SuppInfoCommentItem suppInfoCommentItem4 = new SuppInfoCommentItem(new SuppInfoNo(4), this.supplInfoComment4 == null ? null :  new WorkSuppComment(this.supplInfoComment4));
		SuppInfoCommentItem suppInfoCommentItem5 = new SuppInfoCommentItem(new SuppInfoNo(5), this.supplInfoComment5 == null ? null :  new WorkSuppComment(this.supplInfoComment5));
		suppInfoCommentItems.add(suppInfoCommentItem1);
		suppInfoCommentItems.add(suppInfoCommentItem2);
		suppInfoCommentItems.add(suppInfoCommentItem3);
		suppInfoCommentItems.add(suppInfoCommentItem4);
		suppInfoCommentItems.add(suppInfoCommentItem5);
		
		//補足選択項目情報
		List<SuppInfoSelectionItem> suppInfoSelectionItems = new ArrayList<>();
		SuppInfoSelectionItem suppInfoSelectionItem1 = new SuppInfoSelectionItem(new SuppInfoNo(1), this.supplInfoCode1 == null ? null : new ChoiceCode(this.supplInfoCode1));
		SuppInfoSelectionItem suppInfoSelectionItem2 = new SuppInfoSelectionItem(new SuppInfoNo(2), this.supplInfoCode2 == null ? null :  new ChoiceCode(this.supplInfoCode2));
		SuppInfoSelectionItem suppInfoSelectionItem3 = new SuppInfoSelectionItem(new SuppInfoNo(3), this.supplInfoCode3 == null ? null :  new ChoiceCode(this.supplInfoCode3));
		SuppInfoSelectionItem suppInfoSelectionItem4 = new SuppInfoSelectionItem(new SuppInfoNo(4), this.supplInfoCode4 == null ? null :  new ChoiceCode(this.supplInfoCode4));
		SuppInfoSelectionItem suppInfoSelectionItem5 = new SuppInfoSelectionItem(new SuppInfoNo(5), this.supplInfoCode5 == null ? null :  new ChoiceCode(this.supplInfoCode5));
		suppInfoSelectionItems.add(suppInfoSelectionItem1);
		suppInfoSelectionItems.add(suppInfoSelectionItem2);
		suppInfoSelectionItems.add(suppInfoSelectionItem3);
		suppInfoSelectionItems.add(suppInfoSelectionItem4);
		suppInfoSelectionItems.add(suppInfoSelectionItem5);
		
		return new WorkSuppInfo(suppInfoTimeItems, suppInfoNumItems, suppInfoCommentItems, suppInfoSelectionItems);
	}
}
