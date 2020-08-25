package nts.uk.ctx.at.request.infra.entity.application.stamp;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.stampsetting.AppStampSetting;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.stampsetting.StampAtr;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="KRQMT_APP_STAMP")
public class KrqstAppStamp extends ContractUkJpaEntity{
	
	@EmbeddedId
	public KrqstAppStampPk krqstAppStampPk; 
	
	
	@Column(name="SUP_DISP_CNT")
	public Integer supDispCnt;
	
	@Column(name="STAMP_PLACE_DISP_ATR")
	public Integer stampPlaceDispAtr;
	
	@Column(name="CANCEL_DISP_ATR")
	public Integer cancelDispAtr;
	
	@Column(name="STAMP_OUT_PRI_DISP_ATR")
	public Integer stampOutPriDispAtr;
	
	@Column(name="STAMP_OUT_PUB_DISP_ATR")
	public Integer stampOutPubDispAtr;
	
	@Column(name="STAMP_OUT_COMP_DISP_ATR")
	public Integer stampOutCompDispAtr;
	
	@Column(name="STAMP_OUT_UNION_DISP_ATR")
	public Integer stampOutUnionDispAtr;
	
	@Column(name="WK_CMT_FONT_COLOR1")
	public String wkCmtFontColor1;
	
	@Column(name="WK_CMT_CONTENT1")
	public String wkCmtContent1;
	
	@Column(name="WK_CMT_FONT_WEIGHT1")
	public Integer wkCmtFontWieght1;
	
	
	@Column(name="WK_CMT_FONT_COLOR2")
	public String wkCmtFontColor2;
	
	@Column(name="WK_CMT_CONTENT2")
	public String wkCmtContent2;
	
	@Column(name="WK_CMT_FONT_WEIGHT2")
	public Integer wkCmtFontWieght2;
	
	
	
	
	
	@Column(name="GOOUT_CMT_FONT_COLOR1")
	public String goOutCmtFontColor1;
	
	@Column(name="GOOUT_CMT_CONTENT1")
	public String goOutCmtContent1;
	
	@Column(name="GOOUT_CMT_FONT_WEIGHT1")
	public Integer goOutCmtFontWieght1;
	
	
	@Column(name="GOOUT_CMT_FONT_COLOR2")
	public String goOutCmtFontColor2;
	
	@Column(name="GOOUT_CMT_CONTENT2")
	public String goOutCmtContent2;
	
	@Column(name="GOOUT_CMT_FONT_WEIGHT2")
	public Integer goOutCmtFontWieght2;
	
	
	
	
	
	
	@Column(name="CHILD_CMT_FONT_COLOR1")
	public String childCmtFontColor1;
	
	@Column(name="CHILD_CMT_CONTENT1")
	public String childCmtContent1;
	
	@Column(name="CHILD_CMT_FONT_WEIGHT1")
	public Integer childCmtFontWieght1;
	
	
	@Column(name="CHILD_CMT_FONT_COLOR2")
	public String childCmtFontColor2;
	
	@Column(name="CHILD_CMT_CONTENT2")
	public String childCmtContent2;
	
	@Column(name="CHILD_CMT_FONT_WEIGHT2")
	public Integer childCmtFontWieght2;
	
	
	
	
	
	@Column(name="SUP_CMT_FONT_COLOR1")
	public String supCmtFontColor1;
	
	@Column(name="SUP_CMT_CONTENT1")
	public String supCmtContent1;
	
	@Column(name="SUP_CMT_FONT_WEIGHT1")
	public Integer supCmtFontWieght1;
	
	
	@Column(name="SUP_CMT_FONT_COLOR2")
	public String supCmtFontColor2;
	
	@Column(name="SUP_CMT_CONTENT2")
	public String supCmtContent2;
	
	@Column(name="SUP_CMT_FONT_WEIGHT2")
	public Integer supCmtFontWieght2;
	
	
	
	
	@Column(name="CARE_CMT_FONT_COLOR1")
	public String careCmtFontColor1;
	
	@Column(name="CARE_CMT_CONTENT1")
	public String careCmtContent1;
	
	@Column(name="CARE_CMT_FONT_WEIGHT1")
	public Integer careCmtFontWieght1;
	
	
	@Column(name="CARE_CMT_FONT_COLOR2")
	public String careCmtFontColor2;
	
	@Column(name="CARE_CMT_CONTENT2")
	public String careCmtContent2;
	
	@Column(name="CARE_CMT_FONT_WEIGHT2")
	public Integer careCmtFontWieght2;
	

	
	
	
	@Column(name="BREAK_CMT_FONT_COLOR1")
	public String breakCmtFontColor1;
	
	@Column(name="BREAK_CMT_CONTENT1")
	public String breakCmtContent1;
	
	@Column(name="BREAK_CMT_FONT_WEIGHT1")
	public Integer breakCmtFontWieght1;
	
	
	@Column(name="BREAK_CMT_FONT_COLOR2")
	public String breakCmtFontColor2;
	
	@Column(name="BREAK_CMT_CONTENT2")
	public String breakCmtContent2;
	
	@Column(name="BREAK_CMT_FONT_WEIGHT2")
	public Integer breakCmtFontWieght2;
	
	
	
	
	
	@Column(name="NR_CMT_FONT_COLOR1")
	public String nrCmtFontColor1;
	
	@Column(name="NR_CMT_CONTENT1")
	public String nrCmtContent1;
	
	@Column(name="NR_CMT_FONT_WEIGHT1")
	public Integer nrCmtFontWieght1;
	
	
	@Column(name="NR_CMT_FONT_COLOR2")
	public String nrCmtFontColor2;
	
	@Column(name="NR_CMT_CONTENT2")
	public String nrCmtContent2;
	
	@Column(name="NR_CMT_FONT_WEIGHT2")
	public Integer nrCmtFontWieght2;
	
	
	
	
	
	@Column(name="CHILD_CARE_TIME_REFLECT_ATR")
	public Integer childCareTimeReflectAtr;
	
	@Column(name="SUP_TIME_REFLECT_ATR")
	public Integer supTimeReflectAtr;
	
	@Column(name="CARE_TIME_REFLECT_ATR")
	public Integer careTimeReflectAtr;
	
	@Column(name="GOOUT_TIME_REFLECT_ATR")
	public Integer goOutTimeReflectAtr;
	
	@Column(name="BREAK_TIME_REFLECT_ATR")
	public Integer breakTimeReflectAtr;
	
	@Column(name="WORK_TIME_REFLECT_ATR")
	public Integer workTimeReflectAtr;
	
	@Column(name="EXTRA_WORK_TIME_REFLECT_ATR")
	public Integer extraWorkTimeReflectAtr;
	
	
	//
	public static KrqstAppStamp fromDomain(AppStampSetting appStampSetting) {
		KrqstAppStamp krqstAppStamp = new KrqstAppStamp();
		
		krqstAppStamp.supDispCnt = appStampSetting.getSupportFrameDispNO().v();
		// remove atrribute
//		 Integer stampPlaceDispAtr = 1;
//		
//		 Integer cancelDispAtr = appStampSetting.getUseCancelFunction().value;
		
//		 Integer stampOutPriDispAtr = appStampSetting.getGoOutTypeDispControl().get(0);
		
//		 Integer stampOutPubDispAtr;
		
//		 Integer stampOutCompDispAtr;
		
//		 Integer stampOutUnionDispAtr;
		
		

		 
		 
		 appStampSetting.getSettingForEachTypeLst().forEach( item -> {
			 
//				 出勤/退勤
			 if (item.getStampAtr() == StampAtr.ATTENDANCE_RETIREMENT) {
				 
				 krqstAppStamp.wkCmtFontColor1 = item.getBottomComment().getColorCode().v();
					
				 krqstAppStamp.wkCmtContent1 = item.getBottomComment().getComment().v();
				
				 krqstAppStamp.wkCmtFontWieght1 = item.getBottomComment().isBold() ? 1 : 0;
				
				 krqstAppStamp.wkCmtFontColor2 = item.getTopComment().getColorCode().v();
				
				 krqstAppStamp.wkCmtContent2 = item.getTopComment().getComment().v();
				
				 krqstAppStamp.wkCmtFontWieght2 = item.getTopComment().isBold() ?  1 : 0;
				 
				 
				
//			 	外出/戻りー下部	 
			 } else if (item.getStampAtr() == StampAtr.GOING_OUT_RETURNING) {
				 
				 krqstAppStamp.goOutCmtFontColor1 = item.getBottomComment().getColorCode().v();
					
				 krqstAppStamp.goOutCmtContent1 = item.getBottomComment().getComment().v();
				
				 krqstAppStamp.goOutCmtFontWieght1 = item.getBottomComment().isBold() ? 1 : 0;
				
				 krqstAppStamp.goOutCmtFontColor2 = item.getTopComment().getColorCode().v();
				
				 krqstAppStamp.goOutCmtContent2 = item.getTopComment().getComment().v();
				
				 krqstAppStamp.goOutCmtFontWieght2 = item.getTopComment().isBold() ?  1 : 0;
				 
//				 育児外出/育児戻りー上部
			 } else if (item.getStampAtr() == StampAtr.CHILDCARE_OUT_RETURN) {
				 
				 
				 krqstAppStamp.childCmtFontColor1 = item.getBottomComment().getColorCode().v();
					
				 krqstAppStamp.childCmtContent1 = item.getBottomComment().getComment().v();
				
				 krqstAppStamp.childCmtFontWieght1 = item.getBottomComment().isBold() ? 1 : 0;
				
				 krqstAppStamp.childCmtFontColor2 = item.getTopComment().getColorCode().v();
				
				 krqstAppStamp.childCmtContent2 = item.getTopComment().getComment().v();
				
				 krqstAppStamp.childCmtFontWieght2 = item.getTopComment().isBold() ?  1 : 0;
				 
//				 応援入/応援出ー上部
			 } else if (item.getStampAtr() == StampAtr.SUPPORT_IN_SUPPORT_OUT) {
				 
				 krqstAppStamp.supCmtFontColor1 = item.getBottomComment().getColorCode().v();
					
				 krqstAppStamp.supCmtContent1 = item.getBottomComment().getComment().v();
				
				 krqstAppStamp.supCmtFontWieght1 = item.getBottomComment().isBold() ? 1 : 0;
				
				 krqstAppStamp.supCmtFontColor2 = item.getTopComment().getColorCode().v();
				
				 krqstAppStamp.supCmtContent2 = item.getTopComment().getComment().v();
				
				 krqstAppStamp.supCmtFontWieght2 = item.getTopComment().isBold() ?  1 : 0;
				
//				 介護外出/介護戻りー下部
			 } else if (item.getStampAtr() == StampAtr.OUT_OF_CARE_RETURN_OF_CARE) {
				 
				 krqstAppStamp.careCmtFontColor1 = item.getBottomComment().getColorCode().v();
					
				 krqstAppStamp.careCmtContent1 = item.getBottomComment().getComment().v();
				
				 krqstAppStamp.careCmtFontWieght1 = item.getBottomComment().isBold() ? 1 : 0;
				
				 krqstAppStamp.careCmtFontColor2 = item.getTopComment().getColorCode().v();
				
				 krqstAppStamp.careCmtContent2 = item.getTopComment().getComment().v();
				
				 krqstAppStamp.careCmtFontWieght2 = item.getTopComment().isBold() ?  1 : 0;
			
//				 休憩ー上部
			 } else if (item.getStampAtr() == StampAtr.BREAK) {
				 
				 krqstAppStamp.breakCmtFontColor1 = item.getBottomComment().getColorCode().v();
					
				 krqstAppStamp.breakCmtContent1 = item.getBottomComment().getComment().v();
				
				 krqstAppStamp.breakCmtFontWieght1 = item.getBottomComment().isBold() ? 1 : 0;
				
				 krqstAppStamp.breakCmtFontColor2 = item.getTopComment().getColorCode().v();
				
				 krqstAppStamp.breakCmtContent2 = item.getTopComment().getComment().v();
				
				 krqstAppStamp.breakCmtFontWieght2 = item.getTopComment().isBold() ?  1 : 0;
				 
//				 レコーダーイメージー上部
			 } else {
				 
				 krqstAppStamp.nrCmtFontColor1 = item.getBottomComment().getColorCode().v();
					
				 krqstAppStamp.nrCmtContent1 = item.getBottomComment().getComment().v();
				
				 krqstAppStamp.nrCmtFontWieght1 = item.getBottomComment().isBold() ? 1 : 0;
				
				 krqstAppStamp.nrCmtFontColor2 = item.getTopComment().getColorCode().v();
				
				 krqstAppStamp.nrCmtContent2 = item.getTopComment().getComment().v();
				
				 krqstAppStamp.nrCmtFontWieght2 = item.getTopComment().isBold() ?  1 : 0;
				 
			 }
			 
			 
		 });
		
		
		
		
		
//		 krqstAppStamp.childCareTimeReflectAtr = 
		
//		 Integer supTimeReflectAtr;
		
//		 Integer careTimeReflectAtr;
		
//		 Integer goOutTimeReflectAtr;
		
//		 Integer breakTimeReflectAtr;
		
//		 Integer workTimeReflectAtr;
		
//		 Integer extraWorkTimeReflectAtr;
		
		
		
		return krqstAppStamp;
	}
	
//	public AppStampSetting toDomainApplicationSetting () {
//		
//	}
	
	
	@Override
	protected Object getKey() {
		return krqstAppStampPk;
	}

}
