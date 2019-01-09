package nts.uk.ctx.at.shared.infra.repository.yearholidaygrant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.GrantCondition;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.GrantHdTbl;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.GrantHdTblSet;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.GrantReferenceDate;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.GrantSimultaneity;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.GrantYearHolidayRepository;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.LengthServiceRepository;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.LengthServiceTbl;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.StandardCalculation;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.UseConditionAtr;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.UseSimultaneousGrant;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.YearHolidayRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.i18n.TextResource;
import nts.uk.shr.infra.file.report.masterlist.annotation.DomainID;
import nts.uk.shr.infra.file.report.masterlist.data.ColumnTextAlign;
import nts.uk.shr.infra.file.report.masterlist.data.MasterCellStyle;
import nts.uk.shr.infra.file.report.masterlist.data.MasterData;
import nts.uk.shr.infra.file.report.masterlist.data.MasterHeaderColumn;
import nts.uk.shr.infra.file.report.masterlist.data.MasterListData;
import nts.uk.shr.infra.file.report.masterlist.webservice.MasterListExportQuery;

/**
 * 
 * @author minhvv
 *
 */

@Stateless
@DomainID(value = "YearHoliday")
public class YearHolidayRepositoryImpl implements MasterListData{
	
	@Inject
	private YearHolidayRepository yearHolidayRepo;
	
	@Inject
	private LengthServiceRepository lengthServiceRepository;
	
	@Inject
	private GrantYearHolidayRepository grantYearHolidayRepository;

	
	List<GrantHdTbl> listYearHoliday = new ArrayList<>();
	List<GrantHdTbl> listYearHoliday2 = new ArrayList<>();
	List<GrantHdTbl> listYearHoliday3 = new ArrayList<>();
	List<GrantHdTbl> listYearHoliday4 = new ArrayList<>();
	List<GrantHdTbl> listYearHoliday5 = new ArrayList<>();
	
	@Override
	public List<MasterData> getMasterDatas(MasterListExportQuery query) {
		// TODO Auto-generated method stub
		String companyId = AppContexts.user().companyId();
		
		List<MasterData> datas = new ArrayList<>();
		
		List<GrantHdTblSet> listFindAll = yearHolidayRepo.findAll(companyId);
		
		if(CollectionUtil.isEmpty(listFindAll)) {
			return  null;
		}else{
			listFindAll.stream().forEach(c->{
				Map<String, Object> data = new HashMap<>();
				
				putEmptyData(data);
				
				List<GrantCondition> listGrantConditionCha = c.getGrantConditions();
				
				String dataYearHolidayCode = c.getYearHolidayCode().toString();
				
				//conditionNo
				List<LengthServiceTbl> listFindByCode = lengthServiceRepository.findByCode(companyId,dataYearHolidayCode);

				int listGrantCondition1 = listGrantConditionCha.get(0).getConditionNo();
				listYearHoliday = grantYearHolidayRepository
					.findByCode(companyId,listGrantCondition1, dataYearHolidayCode);
				
				int listGrantCondition2 = listGrantConditionCha.get(1).getConditionNo();
				listYearHoliday2 = grantYearHolidayRepository
					.findByCode(companyId,listGrantCondition2, dataYearHolidayCode);
				
				int listGrantCondition3 = listGrantConditionCha.get(2).getConditionNo();
				listYearHoliday3 = grantYearHolidayRepository
					.findByCode(companyId,listGrantCondition3, dataYearHolidayCode);
				
				int listGrantCondition4 = listGrantConditionCha.get(3).getConditionNo();
				listYearHoliday4 = grantYearHolidayRepository
					.findByCode(companyId,listGrantCondition4, dataYearHolidayCode);
				
				int listGrantCondition5 = listGrantConditionCha.get(4).getConditionNo();
				listYearHoliday5 = grantYearHolidayRepository
					.findByCode(companyId,listGrantCondition5, dataYearHolidayCode);
				
				for(int i=0 ; i<20; i++){
					if(i==0){
						data.put("コード", c.getYearHolidayCode());
                        data.put("名称", c.getYearHolidayName());
                        data.put("備考 54", c.getYearHolidayNote());
                        if(c.getUseSimultaneousGrant() == UseSimultaneousGrant.NOT_USE){
                              data.put("一斉付与 3", "-");
                        }else{
                        	  data.put("一斉付与 3", "○");
                              String getSub = c.getSimultaneousGrandMonthDays().toString();
                              String subDay = "";
                              String subMonth = "";
                              if(getSub.length()==3){
                                     subMonth = getSub.substring(0, 1);
                                     subDay = getSub.substring(1,3);
                              }else{
                                     subMonth = getSub.substring(0,2);
                                     subDay = getSub.substring(2,4);
                              }
                              data.put("一斉付与月4", subMonth+"月");
                              data.put("付与日 5", subDay+ "日");
                        }
                        if(c.getStandardCalculation() == StandardCalculation.YEAR_HD_AWARD_DATE){
                              data.put("年間労働日数の計算基準 6", "付与日");
                        }else{
                              data.put("年間労働日数の計算基準 6", "締め日");
                        }
                        // 6
                        if(c.getCalculationMethod().value == 0){
                              //%
                              data.put("年休付与基準の設定 7", "出勤率");
                              
                              if(c.getGrantConditions().get(0).getConditionValue() ==null){
                                     data.put("基準設定下限 8", "0.0 %");
                              }else{
                                     data.put("基準設定下限 8", c.getGrantConditions().get(0).getConditionValue()+" %");
                              }
                              data.put("上限 9", "100 %");

                        }else{
                              //日
                              data.put("年休付与基準の設定 7", "労働日数");
                           if(c.getGrantConditions().get(0).getConditionValue() ==null){
                                  data.put("基準設定下限 8", "0.0	日");
                           }else{
                                  data.put("基準設定下限 8", c.getGrantConditions().get(0).getConditionValue()+" 日");
                           }
                           data.put("上限 9", "366 日"); 
                        }
                        
                        data.put("付与回 10", i+1);
                        
                        if(listFindByCode.size()==0 || listYearHoliday.size()== 0){

                        }else{
                        	LengthServiceTbl dataFindByCode = listFindByCode.get(i);
                            data.put("勤続年数年 11", dataFindByCode.getYear()+"年");
                        	data.put("勤続年数月 12", dataFindByCode.getMonth()+"ヶ月");
     						data.put("付与日数 13", listYearHoliday.get(0).getGrantDays()+"日");
     						data.put("時間年休上限日数 14",listYearHoliday.get(i).getLimitDayYear().get()+ "日");
                    		data.put("半休上限回数 15", listYearHoliday.get(i).getLimitTimeHd().get()+"回");
                    		
                    		// kiem tra cot thu 3 co check hay ko? 
                    		if(c.getUseSimultaneousGrant() == UseSimultaneousGrant.NOT_USE){
                    			data.put("基準日 16", "年末年始およびベンチマーク日付 ");
                    			// neu check thu kt dk 16, 17 , 18
                    		}else{
                    			if(listFindByCode.get(i).getAllowStatus() == GrantSimultaneity.NOT_USE){
                    			
                    				if(listFindByCode.get(i).getStandGrantDay() == GrantReferenceDate.HIRE_DATE){
                    					data.put("基準日 16", "入社日 ");
                    				}else{
                    					data.put("基準日 16", "年休付与基準日 ");                    				
                    				}
                    				data.put("一斉付与 17", "-");
                    				
                    			}else{
                    				data.put("基準日 16", "年末年始およびベンチマーク日付 ");
                                    data.put("一斉付与 17", "○");
                    			}
                    		}
                        }
        				
                        if(c.getGrantConditions().get(1).getUseConditionAtr() == UseConditionAtr.NOT_USE){
                    		data.put("基準設定２ 18", "-");
                    		data.put("基準設定下限２ 19", "");
                    		data.put("上限２ 20", "");
                    		data.put("付与回 21","");
                    		data.put("勤続年数年 22", "");
                    		data.put("勤続年数月 23", "");
                    		data.put("付与日数 24", "");
                    		data.put("時間年休上限日数 25", "");
                    		data.put("半休上限回数 26", "");
                    	}else{
                    		data.put("基準設定２ 18", "○");
                    		if(c.getCalculationMethod().value == 0){//%
                    			data.put("基準設定下限２ 19", c.getGrantConditions().get(1).getConditionValue()+" %");
                    			data.put("上限２ 20", c.getGrantConditions().get(0).getConditionValue().v()-1+"%");
                    		}else{
                    			data.put("基準設定下限２ 19", c.getGrantConditions().get(1).getConditionValue()+" 日");
                    			data.put("上限２ 20", c.getGrantConditions().get(0).getConditionValue().v()-1+"日");
                    		}
                    		// 21
                            data.put("付与回 21", i+1);
                    		//22
                    		if(listYearHoliday2.size() !=0){
                    			LengthServiceTbl dataFindByCode = listFindByCode.get(i);
                    			data.put("勤続年数年 22",dataFindByCode.getYear()+"年");
                        		data.put("勤続年数月 23",dataFindByCode.getMonth()+"ヶ月");
                        		data.put("付与日数 24", listYearHoliday2.get(i).getGrantDays()+"日");
                        		data.put("時間年休上限日数 25", listYearHoliday2.get(i).getLimitDayYear().get()+ "日");
                        		data.put("半休上限回数 26", listYearHoliday2.get(i).getLimitTimeHd().get()+"回");
                    		}
                    		
                    	}
                        
                        //27 28 29 30 31 32 33 34 35 
                        if(c.getGrantConditions().get(2).getUseConditionAtr() == UseConditionAtr.NOT_USE){
                        	data.put("基準設定３ 27", "-");
                    		data.put("基準設定下限３ 28", "");
                    		data.put("上限３ 29", "");
                    		data.put("付与回 30", "");
                        }else{
                        	data.put("基準設定３ 27", "○");
                        	if(c.getCalculationMethod().value == 0){//%
                    			data.put("基準設定下限３ 28", c.getGrantConditions().get(2).getConditionValue()+" %");
                    			data.put("上限３ 29", c.getGrantConditions().get(1).getConditionValue().v()-1+"%");
                    		}else{
                    			data.put("基準設定下限３ 28", c.getGrantConditions().get(2).getConditionValue()+" 日");
                    			data.put("上限３ 29", c.getGrantConditions().get(1).getConditionValue().v()-1+"日");
                    		}
                        	// 30
                        	
                        	if(listYearHoliday3.size() !=0){
                        		data.put("付与回 30", i+1);
                        		LengthServiceTbl dataFindByCode = listFindByCode.get(i);
                    			data.put("勤続年数年 31",dataFindByCode.getYear()+"年");
                        		data.put("勤続年数月 32",dataFindByCode.getMonth()+"ヶ月");
                        		data.put("付与日数 33", listYearHoliday3.get(i).getGrantDays()+"日");
                        		data.put("時間年休上限日数 34", listYearHoliday3.get(i).getLimitDayYear().get()+ "日");
                        		data.put("半休上限回数 35", listYearHoliday3.get(i).getLimitTimeHd().get()+"回");
                        	}
                        	
                        }
                        
                        // 36 37 38 39 40 41 42 43 44
                        if(c.getGrantConditions().get(3).getUseConditionAtr() == UseConditionAtr.NOT_USE){
                        	data.put("基準設定４ 36", "-");
                        	data.put("基準設定下限４ 37", "");
                        	data.put("上限４ 38", "");
                        	data.put("付与回 39", "");
                        }else{
                        	data.put("基準設定４ 36", "○");
                        	if(c.getCalculationMethod().value == 0){//%
                    			data.put("基準設定下限４ 37", c.getGrantConditions().get(3).getConditionValue()+" %");
                    			if(c.getGrantConditions().get(2).getConditionValue().v() == 0){
                    				data.put("上限４ 38", c.getGrantConditions().get(2).getConditionValue().v()+"%");
                    			}else{
                    				data.put("上限４ 38", c.getGrantConditions().get(2).getConditionValue().v()-1+"%");
                    			}
                    			
                    		}else{
                    			data.put("基準設定下限４ 37", c.getGrantConditions().get(3).getConditionValue()+" 日");
                    			if(c.getGrantConditions().get(2).getConditionValue().v() ==0){
                    				data.put("上限４ 38", c.getGrantConditions().get(2).getConditionValue().v()+"日");
                    			}else{
                    				data.put("上限４ 38", c.getGrantConditions().get(2).getConditionValue().v()-1+"日");
                    			}
                    			
                    		}
                        	if(listYearHoliday4.size() !=0){
                        		data.put("付与回 39", i+1);
                        		LengthServiceTbl dataFindByCode = listFindByCode.get(i);
                        		data.put("勤続年数年 40", dataFindByCode.getYear()+"年");
                        		data.put("勤続年数月 41", dataFindByCode.getMonth()+"ヶ月");
                        		data.put("付与日数  42", listYearHoliday4.get(i).getGrantDays()+"日");
                        		data.put("時間年休上限日数 43", listYearHoliday4.get(i).getLimitDayYear().get()+ "日");
                        		data.put("半休上限回数  44", listYearHoliday4.get(i).getLimitTimeHd().get()+"回");
                        		
                        	}
                        	
                        }
                        
                        //45 46 47 48 49 50 51 52 53
                        if(c.getGrantConditions().get(4).getUseConditionAtr() == UseConditionAtr.NOT_USE){
                        	data.put("基準設定５ 45", "-");
                        	data.put("基準設定下限５ 46", "");
                    		data.put("上限５ 47", "");
                    		data.put("付与回 48", "");
                        }else{
                        	data.put("基準設定５ 45", "○");
                        	if(c.getCalculationMethod().value == 0){//%
                    			data.put("基準設定下限４ 37", c.getGrantConditions().get(4).getConditionValue()+" %");
                    			if(c.getGrantConditions().get(3).getConditionValue().v() == 0){
                    				data.put("上限４ 38", c.getGrantConditions().get(3).getConditionValue().v()+"%");
                    			}else{
                    				data.put("上限４ 38", c.getGrantConditions().get(3).getConditionValue().v()-1+"%");
                    			}
                    			
                    		}else{
                    			data.put("基準設定下限４ 37", c.getGrantConditions().get(4).getConditionValue()+" 日");
                    			if(c.getGrantConditions().get(3).getConditionValue().v() ==0){
                    				data.put("上限４ 38", c.getGrantConditions().get(3).getConditionValue().v()+"日");
                    			}else{
                    				data.put("上限４ 38", c.getGrantConditions().get(3).getConditionValue().v()-1+"日");
                    			}
                    		}
                        	
                        	if(listYearHoliday5.size() !=0){
                        		data.put("付与回 48", i+1);
                        		LengthServiceTbl dataFindByCode = listFindByCode.get(i);
                        		data.put("勤続年数年 49", dataFindByCode.getYear()+"年");
                        		data.put("勤続年数月  50", dataFindByCode.getMonth()+"ヶ月");
                        		data.put("付与日数 51", listYearHoliday5.get(i).getGrantDays()+"日");
                        		data.put("時間年休上限日数 52", listYearHoliday5.get(i).getLimitDayYear().get()+ "日");
                        		data.put("半休上限回数 53", listYearHoliday5.get(i).getLimitTimeHd().get()+"回");
                        		
                        	}
                        }
                        
					}else {// i>0
                        data.put("コード", "");
                        data.put("名称", "");
                        data.put("一斉付与 3", "");
                        data.put("一斉付与月4", "");
                        data.put("付与日 5", "");
                        data.put("年間労働日数の計算基準 6", "");
                        data.put("年休付与基準の設定 7", "");
                        data.put("基準設定下限 8", "");
                        data.put("上限 9", "");
                        data.put("付与回 10", i+1);
                        data.put("備考 54","");
                        
                        // su ly 11
                        if (i < listFindByCode.size()) {
                               LengthServiceTbl dataFindByCode = listFindByCode.get(i);
                               data.put("勤続年数年 11", dataFindByCode.getYear() + "年");
                               data.put("勤続年数月 12", dataFindByCode.getMonth() + "ヶ月");
                               data.put("付与日数 13", listYearHoliday.get(i).getGrantDays() + "日");
                               data.put("時間年休上限日数 14", listYearHoliday.get(i).getLimitDayYear().get() + "日");
                               data.put("半休上限回数 15", listYearHoliday.get(i).getLimitTimeHd().get() + "回");
                               
                               // kiem tra cot thu 3 co check hay ko?
                               if (c.getUseSimultaneousGrant() == UseSimultaneousGrant.NOT_USE) {
                                     data.put("一斉付与 17", "");
                                     // neu check thu kt dk 16, 17 , 18
                               } else {
                                     if (listFindByCode.get(i).getAllowStatus() == GrantSimultaneity.NOT_USE) {
                                            data.put("一斉付与 17", "-");
                               if(listFindByCode.get(i).getStandGrantDay() == GrantReferenceDate.HIRE_DATE){
                                     data.put("基準日 16", "入社日 ");
                               }else{
                                     data.put("基準日 16", "年休付与基準日 ");                                               
                               }
                                     } else {
                                            data.put("基準日 16", "年末年始およびベンチマーク日付 ");
                                            data.put("一斉付与 17", "○");
                                     }
                               }
                        } else {
                               data.put("勤続年数年 11", "");
                               data.put("勤続年数月 12", "");
                               data.put("付与日数 13", "");
                               data.put("時間年休上限日数 14", "");
                               data.put("半休上限回数 15", "");
                               
                               data.put("基準日 16", "年末年始およびベンチマーク日付");
                               if (c.getUseSimultaneousGrant() == UseSimultaneousGrant.NOT_USE) {
                                     data.put("一斉付与 17", "");
                               }else{
                                     data.put("一斉付与 17", "○");
                               }
                        }
                        
                        data.put("基準設定２ 18", "");
                        data.put("基準設定下限２ 19", "");
                        data.put("上限２ 20", "");
                        
                        if(i<listYearHoliday2.size()){
							if(c.getGrantConditions().get(1).getUseConditionAtr() == UseConditionAtr.USE){
								data.put("付与回 21", i + 1);
								LengthServiceTbl dataFindByCode = listFindByCode.get(i);
                    			data.put("勤続年数年 22",dataFindByCode.getYear()+"年");
                        		data.put("勤続年数月 23",dataFindByCode.getMonth()+"ヶ月");
								data.put("付与日数 24", listYearHoliday2.get(i).getGrantDays() + "日");
								data.put("時間年休上限日数 25", listYearHoliday2.get(i).getLimitDayYear().get() + "日");
								data.put("半休上限回数 26", listYearHoliday2.get(i).getLimitTimeHd().get() + "回");
							}
                        }else{
                            data.put("付与回 21", "");
                            data.put("勤続年数年 22", "");
                    		data.put("勤続年数月 23", "");
                    		data.put("付与日数 24", "");
                    		data.put("時間年休上限日数 25", "");
                    		data.put("半休上限回数 26", "");
                        }
                        
                        data.put("基準設定３ 27", "");
                		data.put("基準設定下限３ 28", "");
                		data.put("上限３ 29", "");
                        
                		if(i<listYearHoliday3.size()){
							if(c.getGrantConditions().get(2).getUseConditionAtr() == UseConditionAtr.USE){
								data.put("付与回 30", i+1);
								LengthServiceTbl dataFindByCode = listFindByCode.get(i);
                    			data.put("勤続年数年 31",dataFindByCode.getYear()+"年");
                        		data.put("勤続年数月 32",dataFindByCode.getMonth()+"ヶ月");
                        		data.put("付与日数 33", listYearHoliday3.get(i).getGrantDays()+"日");
                        		data.put("時間年休上限日数 34", listYearHoliday3.get(i).getLimitDayYear().get()+ "日");
                        		data.put("半休上限回数 35", listYearHoliday3.get(i).getLimitTimeHd().get()+"回");
							}
                		}else{
                			data.put("付与回 30", "");
                			data.put("勤続年数年 31","");
                			data.put("勤続年数月 32", "");
                			data.put("付与日数 33", "");
                			data.put("時間年休上限日数 34", "");
                			data.put("半休上限回数 35", "");
                		}
                		data.put("基準設定４ 36", "");
                    	data.put("基準設定下限４ 37", "");
                    	data.put("上限４ 38", "");
                		
                		if(i<listYearHoliday4.size()){
							if(c.getGrantConditions().get(3).getUseConditionAtr() == UseConditionAtr.USE){
								data.put("付与回 39", i+1);
								LengthServiceTbl dataFindByCode = listFindByCode.get(i);
                    			data.put("勤続年数年 40",dataFindByCode.getYear()+"年");
                        		data.put("勤続年数月 41",dataFindByCode.getMonth()+"ヶ月");
                        		data.put("付与日数  42", listYearHoliday4.get(i).getGrantDays()+"日");
                        		data.put("時間年休上限日数 43", listYearHoliday4.get(i).getLimitDayYear().get()+ "日");
                        		data.put("半休上限回数  44", listYearHoliday4.get(i).getLimitTimeHd().get()+"回");
							}
                		}else{
                			data.put("付与回 39", "");
                    		data.put("勤続年数年 40", "");
                    		data.put("勤続年数月 41", "");
                    		data.put("付与日数  42", "");
                    		data.put("時間年休上限日数 43", "");
                    		data.put("半休上限回数  44", "");
                		}
                		
                		data.put("基準設定５ 45", "");
                		data.put("基準設定下限５ 46", "");
                		data.put("上限５ 47", "");
                		
                		if(i<listYearHoliday5.size()){
							if(c.getGrantConditions().get(4).getUseConditionAtr() == UseConditionAtr.USE){
                        		data.put("付与回 48", i+1);
                        		LengthServiceTbl dataFindByCode = listFindByCode.get(i);
                        		data.put("勤続年数年 49", dataFindByCode.getYear()+"年");
                        		data.put("勤続年数月  50", dataFindByCode.getMonth()+"ヶ月");
                        		data.put("付与日数 51", listYearHoliday5.get(i).getGrantDays()+"日");
                        		data.put("時間年休上限日数 52", listYearHoliday5.get(i).getLimitDayYear().get()+ "日");
                        		data.put("半休上限回数 53", listYearHoliday5.get(i).getLimitTimeHd().get()+"回");
							}
                		}else{
                			data.put("付与回 48", "");
                			data.put("勤続年数年 49", "");
                			data.put("勤続年数月  50", "");
                			data.put("付与日数 51", "");
                			data.put("時間年休上限日数 52", "");
                			data.put("半休上限回数 53", "");
                		}
                		
					}
					
					MasterData masterData = new MasterData(data, null, "");	
					masterData.cellAt("コード").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
					masterData.cellAt("名称").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
					masterData.cellAt("一斉付与").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
					masterData.cellAt("一斉付与月4").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT));
					masterData.cellAt("付与日 5").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT));
					masterData.cellAt("年間労働日数の計算基準 6").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
					masterData.cellAt("年休付与基準の設定 7").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
					masterData.cellAt("基準設定下限 8").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT));
					masterData.cellAt("上限 9").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT));
					masterData.cellAt("付与回 10").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT));
					masterData.cellAt("勤続年数年 11").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT));
					masterData.cellAt("勤続年数月 12").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT));
					masterData.cellAt("付与日数 13").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT));
					masterData.cellAt("時間年休上限日数 14").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT));
					masterData.cellAt("半休上限回数 15").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT));
					masterData.cellAt("基準日 16").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
					masterData.cellAt("一斉付与 17").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
					masterData.cellAt("基準設定２ 18").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
					masterData.cellAt("基準設定下限２ 19").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT));
					masterData.cellAt("上限２ 20").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT));
					masterData.cellAt("付与回 21").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT));
					masterData.cellAt("勤続年数年 22").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT));
					masterData.cellAt("勤続年数月 23").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT));
					masterData.cellAt("付与日数 24").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT));
					masterData.cellAt("時間年休上限日数 25").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT));
					masterData.cellAt("半休上限回数 26").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT));
					masterData.cellAt("基準設定３ 27").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
					masterData.cellAt("基準設定下限３ 28").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT));
					masterData.cellAt("上限３ 29").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT));
					masterData.cellAt("付与回 30").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT));
					masterData.cellAt("勤続年数年 31").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT));
					masterData.cellAt("勤続年数月 32").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT));
					masterData.cellAt("付与日数 33").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT));
					masterData.cellAt("時間年休上限日数 34").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT));
					masterData.cellAt("半休上限回数 35").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT));
					masterData.cellAt("基準設定４ 36").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
					masterData.cellAt("基準設定下限４ 37").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT));
					masterData.cellAt("上限４ 38").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT));
					masterData.cellAt("付与回 39").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT));
					masterData.cellAt("勤続年数年 40").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT));
					masterData.cellAt("勤続年数月 41").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT));
					masterData.cellAt("付与日数  42").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT));
					masterData.cellAt("時間年休上限日数 43").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT));
					masterData.cellAt("半休上限回数  44").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT));
					masterData.cellAt("基準設定５ 45").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
					masterData.cellAt("基準設定下限５ 46").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT));
					masterData.cellAt("上限５ 47").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT));
					masterData.cellAt("付与回 48").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT));
					masterData.cellAt("勤続年数年 49").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT));
					masterData.cellAt("勤続年数月  50").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT));
					masterData.cellAt("付与日数 51").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT));
					masterData.cellAt("時間年休上限日数 52").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT));
					masterData.cellAt("半休上限回数 53").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT));
					masterData.cellAt("備考 54").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
					datas.add(masterData);
				}
			});
		}

		return datas;
	}

	@Override
	public List<MasterHeaderColumn> getHeaderColumns(MasterListExportQuery query) {
		// TODO Auto-generated method stub
		List<MasterHeaderColumn> columns = new ArrayList<>();
		// 
		columns.add(new MasterHeaderColumn("コード", TextResource.localize("KMF003_8"),// chưa co
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("名称", TextResource.localize("KMF003_9"),// chua co
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("一斉付与 3", TextResource.localize("KMF003_39"), // A4_1 
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("一斉付与月4", TextResource.localize("KMF003_15"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("付与日 5", TextResource.localize("KMF003_17"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("年間労働日数の計算基準 6", TextResource.localize("KMF003_16"),// A6_1
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("年休付与基準の設定 7", TextResource.localize("KMF003_19"),// A7_1
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("基準設定下限 8", TextResource.localize("KMF003_59"),// THEM MOI
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("上限 9", TextResource.localize("KMF003_60"),// THEM MOI
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("付与回 10",TextResource.localize("KMF003_12"),//B2_12
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("勤続年数年 11", TextResource.localize("KMF003_48")+ ""+TextResource.localize("KMF003_41"),// B2_14 + B2_5
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("勤続年数月 12",TextResource.localize("KMF003_48")+" "+TextResource.localize("KMF003_42"),// B2_14 + B2_6
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("付与日数 13", TextResource.localize("KMF003_43"),//B2_7
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("時間年休上限日数 14", TextResource.localize("KMF003_44"),//B2_8
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("半休上限回数 15", TextResource.localize("KMF003_45"),//B2_9
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("基準日 16", TextResource.localize("KMF003_46"),//B2_10
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("一斉付与 17", TextResource.localize("KMF003_39"),//B2_3
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("基準設定２ 18", TextResource.localize("KMF003_61"),//CHUA CO
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("基準設定下限２ 19", TextResource.localize("KMF003_62"),// CHUA CO
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("上限２ 20", TextResource.localize("KMF003_63"),// CHUA CO
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("付与回 21", TextResource.localize("KMF003_12"),//B2_12
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("勤続年数年 22",TextResource.localize("KMF003_48")+ ""+TextResource.localize("KMF003_41"),// B2_14 + B2_5
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("勤続年数月 23", TextResource.localize("KMF003_48")+" "+TextResource.localize("KMF003_42"),// B2_14 + B2_6
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("付与日数 24",  TextResource.localize("KMF003_43"),//B2_7
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("時間年休上限日数 25",  TextResource.localize("KMF003_44"),//B2_8
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("半休上限回数 26", TextResource.localize("KMF003_45"),//B2_9
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("基準設定３ 27", TextResource.localize("KMF003_64"),//CHUA CO
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("基準設定下限３ 28", TextResource.localize("KMF003_65"),// CHUA CO
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("上限３ 29",TextResource.localize("KMF003_66"),// CHUA CO
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("付与回 30",TextResource.localize("KMF003_12"),//B2_12
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("勤続年数年 31",TextResource.localize("KMF003_48")+ ""+TextResource.localize("KMF003_41"),// B2_14 + B2_5
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("勤続年数月 32",TextResource.localize("KMF003_48")+" "+TextResource.localize("KMF003_42"),// B2_14 + B2_6
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("付与日数 33", TextResource.localize("KMF003_43"),//B2_7
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("時間年休上限日数 34",  TextResource.localize("KMF003_44"),//B2_8
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("半休上限回数 35", TextResource.localize("KMF003_45"),//B2_9
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("基準設定４ 36", TextResource.localize("KMF003_67"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("基準設定下限４ 37", TextResource.localize("KMF003_68"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("上限４ 38", TextResource.localize("KMF003_69"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("付与回 39", TextResource.localize("KMF003_12"),//B2_12
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("勤続年数年 40", TextResource.localize("KMF003_48")+ ""+TextResource.localize("KMF003_41"),// B2_14 + B2_5
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("勤続年数月 41", TextResource.localize("KMF003_48")+" "+TextResource.localize("KMF003_42"),// B2_14 + B2_6
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("付与日数  42", TextResource.localize("KMF003_43"),//B2_7
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("時間年休上限日数 43", TextResource.localize("KMF003_44"),//B2_8
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("半休上限回数  44", TextResource.localize("KMF003_45"),//B2_9
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("基準設定５ 45", TextResource.localize("KMF003_70"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("基準設定下限５ 46", TextResource.localize("KMF003_71"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("上限５ 47", TextResource.localize("KMF003_72"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("付与回 48", TextResource.localize("KMF003_12"),//B2_12
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("勤続年数年 49", TextResource.localize("KMF003_48")+ ""+TextResource.localize("KMF003_41"),// B2_14 + B2_5
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("勤続年数月  50", TextResource.localize("KMF003_48")+" "+TextResource.localize("KMF003_42"),// B2_14 + B2_6
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("付与日数 51", TextResource.localize("KMF003_43"),//B2_7
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("時間年休上限日数 52",TextResource.localize("KMF003_44"),//B2_8
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("半休上限回数 53", TextResource.localize("KMF003_45"),//B2_9
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("備考 54", TextResource.localize("KMF003_30"),// B_81
				ColumnTextAlign.LEFT, "", true));
		return columns;
	}
	
	
	
	private void putEmptyData (Map<String, Object> data){
		data.put("コード", "");
		data.put("名称", "");
		data.put("一斉付与", "");
		data.put("一斉付与月4", "");
		data.put("付与日 5", "");
		data.put("年間労働日数の計算基準 6", "");
		data.put("年休付与基準の設定 7", "");
		data.put("基準設定下限 8", "");
		data.put("上限 9", "");
		data.put("付与回 10", "");
		data.put("勤続年数年 11", "");
		data.put("勤続年数月 12", "");
		data.put("付与日数 13", "");
		data.put("時間年休上限日数 14", "");
		data.put("半休上限回数 15", "");
		data.put("基準日 16", "");
		data.put("一斉付与 17", "");
		data.put("基準設定２ 18", "");
		data.put("基準設定下限２ 19", "");
		data.put("上限２ 20", "");
		data.put("付与回 21", "");
		data.put("勤続年数年 22", "");
		data.put("勤続年数月 23", "");
		data.put("付与日数 24", "");
		data.put("時間年休上限日数 25", "");
		data.put("半休上限回数 26", "");
		data.put("基準設定３ 27", "");
		data.put("基準設定下限３ 28", "");
		data.put("上限３ 29", "");
		data.put("付与回 30", "");
		data.put("勤続年数年 31", "");
		data.put("勤続年数月 32", "");
		data.put("付与日数 33", "");
		data.put("時間年休上限日数 34", "");
		data.put("半休上限回数 35", "");
		data.put("基準設定４ 36", "");
		data.put("基準設定下限４ 37", "");
		data.put("上限４ 38", "");
		data.put("付与回 39", "");
		data.put("勤続年数年 40", "");
		data.put("勤続年数月 41", "");
		data.put("付与日数  42", "");
		data.put("時間年休上限日数 43", "");
		data.put("半休上限回数  44", "");
		data.put("基準設定５ 45", "");
		data.put("基準設定下限５ 46", "");
		data.put("上限５ 47", "");
		data.put("付与回 48", "");
		data.put("勤続年数年 49", "");
		data.put("勤続年数月  50", "");
		data.put("付与日数 51", "");
		data.put("時間年休上限日数 52", "");
		data.put("半休上限回数 53", "");
		data.put("備考 54", "");
	}

	@Override
	public String mainSheetName() {
		// TODO Auto-generated method stub
		return TextResource.localize("KMF003_75");
	}

	
	
	
}