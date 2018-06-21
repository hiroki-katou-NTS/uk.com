package nts.uk.ctx.at.shared.dom.remainingnumber.algorithm;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.CreateAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.work.DayoffTranferInfor;
import nts.uk.ctx.at.shared.dom.remainingnumber.work.InforFormerRemainData;
import nts.uk.ctx.at.shared.dom.remainingnumber.work.TranferTimeInfor;
import nts.uk.ctx.at.shared.dom.remainingnumber.work.WorkTypeRemainInfor;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeClassification;

public interface InterimRemainOffDateCreateData {
	/**
	 * 指定日の暫定残数管理データを作成する
	 * @param cid
	 * @param sid
	 * @param baseDate
	 * @param dayOffTimeIsUse: 時間代休利用: True: 利用, False: 未利用 
	 * @param detailData
	 * @return
	 */
	public DailyInterimRemainMngData createData(String sid, GeneralDate baseDate, boolean dayOffTimeIsUse, InterimRemainCreateInfor detailData);
	/**
	 * 残数作成元情報を作成する
	 * @param sid
	 * @param baseDate
	 * @param detailData
	 * @return
	 */
	public InforFormerRemainData createInforFormerRemainData(String sid, GeneralDate baseDate, InterimRemainCreateInfor detailData, boolean dayOffTimeIsUse);
	/**
	 * 実績から残数作成元情報を設定する
	 * @param recordData
	 * @return
	 */
	public InforFormerRemainData createInterimDataFromecord(RecordRemainCreateInfor recordData, InforFormerRemainData outputData);
	/**
	 * 勤務種類別残数情報を作成する
	 * @param cid
	 * @param createAtr
	 * @param workTypeCode
	 * @return
	 */
	public WorkTypeRemainInfor createWorkTypeRemainInfor(CreateAtr createAtr, String workTypeCode);
	/**
	 * 1日勤務時の残数発生使用明細を作成する
	 * @param workType
	 * @param dataOutput
	 * @return
	 */
	public WorkTypeRemainInfor createWithOneDayWorkType(WorkType workType, WorkTypeRemainInfor dataOutput);
	/**
	 * 午前と午後勤務時の残数発生使用明細を作成する
	 * @param workType
	 * @param dataOutput
	 * @return
	 */
	public WorkTypeRemainInfor createWithHaftDayWorkType(WorkType workType, WorkTypeRemainInfor dataOutput);
	/**
	 * 実績から代休振替情報を作成する
	 * @param recordData
	 * @param dayOffTimeIsUse
	 * @return
	 */
	public DayoffTranferInfor createDayOffTranferFromRecord(CreateAtr createAtr, RecordRemainCreateInfor recordData, boolean dayOffTimeIsUse);
	/**
	 * 残数作成元情報から暫定残数管理データを作成する
	 * @param inforData
	 * @return
	 */
	public DailyInterimRemainMngData createDataInterimRemain(InforFormerRemainData inforData);
	/**
	 * 残数作成元情報から暫定振休管理データを作成する
	 * @param inforData
	 * @return
	 */
	DailyInterimRemainMngData createInterimAbsData(InforFormerRemainData inforData, WorkTypeClassification workTypeClass,
			DailyInterimRemainMngData mngData);
	/**
	 * 残数作成元情報から暫定代休管理データを作成する
	 * @param inforData
	 * @param workTypeClass
	 * @param mngData
	 * @return
	 */
	DailyInterimRemainMngData createInterimDayOffData(InforFormerRemainData inforData, WorkTypeClassification workTypeClass,
			DailyInterimRemainMngData mngData);
	/**
	 * 予定から残数作成元情報を設定する
	 * @param scheData
	 * @param outputData
	 * @return
	 */
	InforFormerRemainData createInterimDataFromSche(ScheRemainCreateInfor scheData, InforFormerRemainData outputData,
			boolean dayOffTimeIsUse);
	/**
	 * 就業時間帯から代休振替情報を作成する
	 * @param remainInfor
	 * @param workTimeCode
	 * @param isTranferTime: True: 設定あり, False: 設定なし
	 * @return
	 */
	DayoffTranferInfor createDayoffFromWorkTime(WorkTypeRemainInfor remainInfor, String workTimeCode,
			Integer timeSetting, CreateAtr createAtr, Integer timeOverSetting, boolean dayOffTimeIsUse);
	/**
	 * 代休振替時間を算出する
	 * @param workTimeCode
	 * @param timeSetting
	 * @param dayoffChange
	 * @return
	 */
	TranferTimeInfor calDayoffTranferTime(CreateAtr createAtr,String workTimeCode, Integer timeSetting, DayoffChangeAtr dayoffChange);
}
