package nts.uk.ctx.at.shared.dom.remainingnumber.algorithm;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.InterimRemain;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class InterimEachData {
	/**
	 * 振休か振出の暫定残数管理
	 */
	List<InterimRemain> interimMngAbsRec = new ArrayList<>();
	/**
	 * 休出か代休の暫定残数管理
	 */
	List<InterimRemain> interimMngBreakDayOff = new ArrayList<>();
	/**
	 * 特別休暇の暫定残数管理
	 */
	List<InterimRemain> interimSpecial = new ArrayList<>();
	/**
	 * 年休の暫定残数管理
	 */
	List<InterimRemain> annualMng = new ArrayList<>();
	/**
	 * 積立年休の暫定残数管理
	 */
	List<InterimRemain> resereMng = new ArrayList<>();
	
	/**
     * 暫定子の看護休暇データ
     */
    List<InterimRemain> childCareData = new ArrayList<>();
    /**
     * 暫定介護休暇データ
     */
    List<InterimRemain> careData = new ArrayList<>();
}
