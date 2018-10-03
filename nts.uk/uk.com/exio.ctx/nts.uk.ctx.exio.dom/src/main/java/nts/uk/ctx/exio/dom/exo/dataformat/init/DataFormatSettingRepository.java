package nts.uk.ctx.exio.dom.exo.dataformat.init;

import java.util.Optional;
import java.util.List;

/**
 * 日付型データ形式設定
 */
public interface DataFormatSettingRepository {

    List<AwDataFormatSet> getAllAwDataFormatSet();

    Optional<AwDataFormatSet> getAwDataFormatSetById(String cid);

    void add(AwDataFormatSet domain);

    void update(AwDataFormatSet domain);

    void remove(AwDataFormatSet domain);
    
    List<ChacDataFmSet> getAllChacDataFmSet();

    Optional<ChacDataFmSet> getChacDataFmSetById(String cid);
    
    List<ChacDataFmSet> getChacDataFmSetByConvertCd(String cid, String convertCd);

    void add(ChacDataFmSet domain);

    void update(ChacDataFmSet domain);

    void remove(ChacDataFmSet domain);
    
	List<DateFormatSet> getAllDateFormatSet();

	Optional<DateFormatSet> getDateFormatSetById(String cId);

	void add(DateFormatSet domain);

	void update(DateFormatSet domain);

	void remove(DateFormatSet domain);

	Optional<DateFormatSet> getDateFormatSetByCid(String cid);
	
    List<NumberDataFmSet> getAllNumberDataFmSet();

    Optional<NumberDataFmSet> getNumberDataFmSetById(String id);

    void add(NumberDataFmSet domain);

    void update(NumberDataFmSet domain);

    void remove(NumberDataFmSet domain);
    
    Optional<NumberDataFmSet> getNumberDataFmSetByCid(String cid);
    
    List<TimeDataFmSet> getAllTimeDataFmSet();

    Optional<TimeDataFmSet> getTimeDataFmSetById();
    
    Optional<TimeDataFmSet> getTimeDataFmSetByCid(String cid);

    void add(TimeDataFmSet domain);

    void update(TimeDataFmSet domain);

    void remove(TimeDataFmSet domain);
    
	List<InTimeDataFmSet> getAllInTimeDataFmSet();

	Optional<InTimeDataFmSet> getInTimeDataFmSetByCid(String cid);

	void add(InTimeDataFmSet domain);

	void update(InTimeDataFmSet domain);

	void remove(InTimeDataFmSet domain);
}
