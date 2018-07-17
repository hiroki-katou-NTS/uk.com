package nts.uk.ctx.exio.dom.exo.dataformat.init;

import java.util.Optional;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

/**
 * 
 * データ形式初期値
 *
 */
@Getter
public class DataFormatInit extends AggregateRoot{
	
	/**
	* 会社ID
	*/
	private String cid;
	
	/**
	* 在職区分型データ形式設定
	*/
	private Optional<AwDataFormatSet> awDataFormatSet;
	
	/**
	* 文字型データ形式設定
	*/
	private Optional<ChacDataFmSet> chacDataFmSet;
	
	/**
	* 日付型データ形式設定
	*/
	private Optional<DateFormatSet> dateFormatSet;
	
	/**
	* 時刻型データ形式設定
	*/
	private Optional<InTimeDataFmSet> inTimeDataFmSet;
	
	/**
	* 時間型データ形式設定
	*/
	private Optional<TimeDataFmSet> timeDataFmSet;
	
	/**
	* 数値型データ形式設定
	*/
	private Optional<NumberDataFmSet> numberDataFmSet;
}
