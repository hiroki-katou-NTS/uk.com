package nts.uk.ctx.at.record.pub.remainnumber.specialleave.export;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class InPeriodOfSpecialLeaveResultInforExport {

	/** 特休情報（期間終了日時点） */
	private SpecialLeaveInfoExport asOfPeriodEnd;
	/** 特休情報（期間終了日の翌日開始時点） */
	private SpecialLeaveInfoExport asOfStartNextDayOfPeriodEnd;
	/** 特休情報（付与時点） */
	private List<SpecialLeaveInfoExport> asOfGrant;
	/** 特休情報（消滅） */
	private List<SpecialLeaveInfoExport> lapsed;
	/** 特休エラー情報 */
	private List<Integer> specialLeaveErrors;

	/**
	 * コンストラクタ
	 */
	public InPeriodOfSpecialLeaveResultInforExport(){
		this.asOfPeriodEnd = new SpecialLeaveInfoExport();
		this.asOfStartNextDayOfPeriodEnd = new SpecialLeaveInfoExport();
		this.asOfGrant = new ArrayList<SpecialLeaveInfoExport>();
		this.lapsed = new ArrayList<SpecialLeaveInfoExport>();
		this.specialLeaveErrors = new ArrayList<Integer>();
	}

}
